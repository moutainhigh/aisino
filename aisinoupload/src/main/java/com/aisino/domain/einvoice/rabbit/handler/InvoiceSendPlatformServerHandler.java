package com.aisino.domain.einvoice.rabbit.handler;

import static com.google.common.base.Strings.isNullOrEmpty;
import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.newHashMapWithExpectedSize;
import static org.joda.time.DateTime.now;

import java.io.File;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.aisino.domain.einvoice.model.FpDoneLog;
import com.aisino.domain.einvoice.resultService.IBusinessDataService;
import com.aisino.domain.rabbit.entity.PushInvoiceQueueEntity;
import com.aisino.domain.rabbit.entity.SendPlatformQyeueEntity;
import com.aisino.domain.sys.model.Route;
import com.aisino.domain.task.service.IFpxxTaskService;
import com.aisino.protocol.bean.REQUEST_FPKJ;
import com.aisino.web.util.Constants;
import com.aisino.web.util.PlatformConstants;
import com.aisino.web.util.SystemConfig;
import com.google.gson.Gson;

/**
 * 
 * <p>
 * [描述信息：发票上传51平台]
 * </p>
 * 
 * @author: zhongsiwei
 * @version 1.0 Created on 2015-3-17 下午05:09:26
 */
@Service
public final class InvoiceSendPlatformServerHandler {

    private final static Logger LOGGER = LoggerFactory.getLogger(InvoiceSendPlatformServerHandler.class);

    @Autowired
    private IFpxxTaskService fpxxTaskService;

    @Autowired
    private IBusinessDataService businessDataService;
    
    @Autowired
    private AmqpTemplate amqpTemplate;
    
    @Value("${rabbit.queue.invoice.push}")
    private String pushInvoiceQueue;
    

    public void handleMessage(final SendPlatformQyeueEntity sendPlatformQyeueEntity) {
        LOGGER.warn("receive mq invoice message:{}", new Gson().toJson(sendPlatformQyeueEntity));
        final DateTime begin3 = now();
        sendPlatformServer(sendPlatformQyeueEntity);
        final Long millSeconds = new Duration(begin3, now()).getMillis();
        LOGGER.warn("调用单张发票上传大象服务sendPlatformServer方法用时{}毫秒", millSeconds);
    }

    private void sendPlatformServer(final SendPlatformQyeueEntity sendPlatformQyeueEntity) {
        LOGGER.warn("发票单张上传服务平台(大象大数据)任务开始...");
        // final DateTime begin = now();
        try {
            // MQ传递来的发票ID
            final Long invoiceId = sendPlatformQyeueEntity.getInvoiceId();
            String invoiceIds = String.valueOf(invoiceId);

            // 以下if判断是用于用于队列验重是否需要上传51
            // 查询到的发票上传状态:0是初始化状态,1是上传成功状态,2是上传失败状态
            final DateTime begin2 = now();
            String fpsczt = fpxxTaskService.qeryFpscZt(invoiceIds);// 查询发票的状态
            final Long millSeconds2 = new Duration(begin2, now()).getMillis();
            LOGGER.warn("上传大象大数据发票,查询发票状态用时{}毫秒", millSeconds2);
            // 如果发票上传状态等于1,跳出本次业务
            if (fpsczt.equals(Constants.INV_DXHY_SUCCESS)) {
                LOGGER.error("ID为{}的发票状态为{}，未执行发票上传大象大象大数据", invoiceId, fpsczt);
                return;
            }
            // 查询要上传51的发票
            final DateTime begin4 = now();
            final REQUEST_FPKJ fpkj = fpxxTaskService.queryInvoiceInforById(invoiceIds);
            final Long millSeconds4 = new Duration(begin4, now()).getMillis();
            LOGGER.warn("上传51发票,查询要上传的发票数据用时{}毫秒", millSeconds4);
            if (!isNullOrEmpty(fpkj.getFP_DM()) && !isNullOrEmpty(fpkj.getFP_HM())) {
                // /当存在未上传51的发票时
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug("查询到未上传服务平台(大象大数据)的发票代码:{}, 纳税人识别号:{} 开始上传...", fpkj.getFP_DM(), fpkj.getNSRSBH());
                }

                // 获取51平台路由URL
                final DateTime begin5 = now();
                final Route route = PlatformConstants.getRoute();
                final Long millSeconds5 = new Duration(begin5, now()).getMillis();
                LOGGER.warn("上传大象大数据发票,获取大象大数据平台路由url用时{}毫秒", millSeconds5);
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug("获取路由Route:{}", route.toString());
                }

                // 设置参数
                Map<String, Object> map = newHashMapWithExpectedSize(5);
                // map.put("zt", "0");
                map.put("dsptbm", sendPlatformQyeueEntity.getEshopCode());
                // map.put("start", 0);
                // map.put("count", 1);
                map.put("invoiceId", invoiceIds);

                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug("上传大象大数据发票,Map Param:{}", map.toString());
                }

                String pdfFileString = fpkj.getPDF_FILE();
                String swjgsString = fpkj.getSZ_SWJG_DM();

                final DateTime begin1 = now();
                // 目前传递的都是单张的发票数据.
                // 调用上传51发票的接口,处理数据
                boolean blresult = businessDataService.uploadInv(map, newArrayList(fpkj), route,1);
//                 boolean blresult = true;
                // 传多张发票时，路由信息（根据电商平台所在税务机关代码）
                // businessDataService.uploadInv(map, newArrayList(fpkj),
                // route);

                final Long millSeconds1 = new Duration(begin1, now()).getMillis();
                LOGGER.warn("(上传大象大数据发票,uploadInv方法)发票{}上传大象大数据平台,总共耗时{}...", invoiceId, millSeconds1);

                
                
                
                if(blresult){
                	final PushInvoiceQueueEntity fppush = fpxxTaskService.queryPushInforById(invoiceIds);
                	LOGGER.warn("推送数据{}...", fppush);
                	amqpTemplate.convertAndSend(pushInvoiceQueue, fppush);
                }
                
                
                
                
                // 记录耗时日志
                // final Long millSeconds = new Duration(begin,
                // now()).getMillis();
                // final FpDoneLog fpDoneLog = new FpDoneLog();
                // fpDoneLog.setFpkj_id(Long.valueOf(invoiceId));
                // fpDoneLog.setS51(millSeconds);
                // 删除log日志
                // businessDataService.updateFpDoneLog("updateFpDoneLogS51",
                // fpDoneLog);
                if (blresult) {
                    // 如果推送51成功,根据配置文件来判断是否删除pdf
                    String pdfBlString = SystemConfig.PDF_DELETE_PLATFORM;
                    if ("Y".equals(pdfBlString)) {
                        LOGGER.warn("删除pdf文件开始----");
                        String pdfpathString = pdfFileString;
                        try {
                            DateTime begin6 = now();
                            final File pdfFile = new File(pdfpathString);
                            // LOGGER.warn("pdfFile.isFile():{}",pdfFile.isFile());
                            // LOGGER.warn("pdfFile.exists():{}",pdfFile.exists());
                            // LOGGER.warn("pdfFile.canWrite():{}",pdfFile.canWrite());
                            DateTime begin7 = now();
                            // 判断是否是文件并且文件存在
                            if (pdfFile.isFile() && pdfFile.exists()) {
                                // 调用工具类,删除文件
                                boolean pdfdelbl = pdfFile.delete();
                                if (pdfdelbl) {
                                    LOGGER.warn("删除{}成功!", pdfpathString);
                                }
                            } else {
                                LOGGER.warn("删除{}失败(不是文件或者pdf不存在)!", pdfpathString);
                            }
                            Long millSeconds7 = new Duration(begin7, now()).getMillis();
                            LOGGER.warn("(上传大象大数据发票,删除pdf,判断是否是文件并且文件存在)总共耗时{}...", millSeconds7);
                            DateTime begin8 = now();
                            // 根据开具id进行判断何时删除文件夹:根据id进行取余计算,如果是1000000的倍数就进行删除一次空文件夹
                            if (invoiceId % 1000000 == 0) {
                                // 读取税务机关代码,然后获取总的文件夹,因为生成pdf之后,是以税务机关代码创建文件夹进行存储的,所以这里读取存储路径之前的地址
                                String fileString = pdfpathString.split(swjgsString)[0] + swjgsString;
                                deleteDirectory(fileString);
                                LOGGER.warn("删除{}下的空文件夹成功!", fileString);
                            }
                            Long millSeconds8 = new Duration(begin8, now()).getMillis();
                            LOGGER.warn("(上传大象大数据发票,删除pdf,扫描空文件夹并删除)总共耗时{}...", millSeconds8);

                            Long millSeconds6 = new Duration(begin6, now()).getMillis();
                            LOGGER.warn("(上传大象大数据发票,删除pdf)总共耗时{}...", millSeconds6);
                        } catch (Exception e) {
                            LOGGER.error("删除本地pdf文件时出错，路径为：" + pdfpathString + ";", e);
                        }
                        LOGGER.warn("删除pdf文件结束----");
                    }
                } else {// 操作失败更新数据库状态为失败状态(2)
                    Map<String, Object> mapIds = new HashMap<String, Object>();
                    mapIds.put("id", invoiceIds);
                    final DateTime begin6 = now();
                    businessDataService.updateFailState(mapIds);
                    final Long millSeconds6 = new Duration(begin6, now()).getMillis();
                    LOGGER.warn("(上传大象大数据发票)更新发票状态为失败状态(2)用时{}毫秒", millSeconds6);
                }
            } else {
                LOGGER.warn("发票单张上传服务平台(大象大数据), 没有获得需要上传的发票数据.");
            }

        } catch (SQLException e) {
            LOGGER.error("发票上传大象大数据业务sql异常", e);
        } catch (Exception e) {
            LOGGER.error("发票上传大象大数据业务异常", e);
        }
    }

    public static boolean deleteDirectory(String sPath) {
        // 如果sPath不以文件分隔符结尾，自动添加文件分隔符
        if (!sPath.endsWith(File.separator)) {
            sPath = sPath + File.separator;
        }
        File dirFile = new File(sPath);
        // 如果dir对应的文件不存在，或者不是一个目录，则退出
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            return false;
        }
        boolean flag = true;
        // 删除文件夹下的所有文件(包括子目录)
        File[] files = dirFile.listFiles();
        for (int i = 0; i < files.length; i++) {
            final DateTime begin = now();
            flag = deleteDirectory(files[i].getAbsolutePath());
            final Long millSeconds = new Duration(begin, now()).getMillis();
            LOGGER.warn("(上传大象大数据发票)删除单张PDF用时{}毫秒", millSeconds);
            if (!flag)
                continue;
        }
        if (!flag)
            return false;
        // 删除当前目录
        if (dirFile.delete()) {
            return true;
        } else {
            return false;
        }
    }
}
