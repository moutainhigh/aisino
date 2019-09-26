package com.aisino.domain.einvoice.resultService.impl;

import static com.aisino.web.util.IbatisConstants.KEY_QUERYDDXXFORFPSC;
import static com.aisino.web.util.IbatisConstants.KEY_QUERYFPKJIDBYKPLSH;
import static com.aisino.web.util.IbatisConstants.KEY_QUERYMXFORFPSC;
import static com.aisino.web.util.IbatisConstants.KEY_QUERYWLXXFORFPSC;
import static com.aisino.web.util.IbatisConstants.KEY_UPDATEFAILFORFPSC;
import static com.aisino.web.util.IbatisConstants.KEY_QUERYZFXXFORFPSC;
import static com.aisino.web.util.IbatisConstants.UPDATE_INVUPLOADRESULTFORPLATFORM;
import static com.aisino.web.util.IbatisConstants.UPDATE_INVUPLOADRESULTFOR51;

import static com.google.common.collect.Maps.newHashMapWithExpectedSize;
import static org.apache.commons.io.FileUtils.deleteDirectory;
import static org.apache.commons.io.FileUtils.deleteQuietly;
import static org.joda.time.DateTime.now;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aisino.common.util.DateUtil;
import com.aisino.common.util.GlobalInfo;
import com.aisino.common.util.ProXml;
import com.aisino.common.util.XmlPar;
import com.aisino.domain.base.service.IBaseService;
import com.aisino.domain.einvoice.inter.IPlatformProcess;
import com.aisino.domain.einvoice.model.FpDoneLog;
import com.aisino.domain.einvoice.resultService.IBusinessDataService;
import com.aisino.domain.sys.model.Route;
import com.aisino.domain.task.dao.ITaskDao;
import com.aisino.protocol.bean.FP_DDXX;
import com.aisino.protocol.bean.FP_KJMX;
import com.aisino.protocol.bean.FP_WLXX;
import com.aisino.protocol.bean.FP_ZFXX;
import com.aisino.protocol.bean.REQUEST_FPKJ;
import com.aisino.protocol.bean.REQUEST_FPKJSCGLOBLE;
import com.aisino.protocol.bean.RESPONSE_FPKJJG;
import com.aisino.web.util.CommonsUtil;
import com.aisino.web.util.PlatformConstants;
import com.aisino.web.util.SystemConfig;

/**
 * @author zsf
 * @version 1.0
 * @created 2013-11-9 上午10:05:07
 */
@Service
public final class BusinessDataServiceImpl implements IBusinessDataService {

    private final static Logger LOGGER = LoggerFactory.getLogger(BusinessDataServiceImpl.class);

    @Resource(name = "baseService")
    protected IBaseService baseService;

    @Autowired
    private IPlatformProcess platformProcess;

    @Autowired
    protected ITaskDao taskDao;

    public void modifyUploadResults(RESPONSE_FPKJJG RESPONSE_FPKJJG, REQUEST_FPKJ[] uploads, String fp_id) throws SQLException {
        Map<String, Object> upCondition = new HashMap<String, Object>();
        if (RESPONSE_FPKJJG.getRETURNCODE().equals(XmlPar.BUSI_SSUCCESS)) {
            if (uploads != null && uploads.length > 0) {// 将log表全改成成功
                final DateTime begin1 = now();

                for (REQUEST_FPKJ request_fpkj : uploads) {
                    upCondition.put("FPSC_ZT", "1");
                    upCondition.put("FP_DM", request_fpkj.getFP_DM());
                    upCondition.put("FP_HM", request_fpkj.getFP_HM());
                    upCondition.put("fp_id", fp_id);
                    baseService.update(UPDATE_INVUPLOADRESULTFORPLATFORM, upCondition);
                    upCondition.clear();
                }

                final Long millSeconds1 = new Duration(begin1, now()).getMillis();
                LOGGER.warn("(上传51发票)所有数据(共{}条)发票上传状态修改为1,用时{}毫秒", uploads.length, millSeconds1);
            }
            if (RESPONSE_FPKJJG.getFPKJJGS() != null && RESPONSE_FPKJJG.getFPKJJGS().length > 0) {
                final DateTime begin2 = now();
                for (int i = 0; i < RESPONSE_FPKJJG.getFPKJJGS().length; i++) {
                    upCondition.put("FPSC_ZT", "2");
                    upCondition.put("FP_DM", RESPONSE_FPKJJG.getFPKJJGS()[i].getFP_DM());
                    upCondition.put("FP_HM", RESPONSE_FPKJJG.getFPKJJGS()[i].getFP_HM());
                    upCondition.put("fp_id", fp_id);
                    baseService.update(UPDATE_INVUPLOADRESULTFORPLATFORM, upCondition);// LOG表部分改成失败
                    upCondition.clear();
                }

                final Long millSeconds2 = new Duration(begin2, now()).getMillis();
                LOGGER.warn("(上传51发票)失败结果数据(共{}条)发票上传状态修改为2,用时{}毫秒", uploads.length + "条,失败" + RESPONSE_FPKJJG.getFPKJJGS().length, millSeconds2);
            }
        } else {
            final DateTime begin3 = now();
            for (REQUEST_FPKJ request_fpkj : uploads) {
                upCondition.put("FPSC_ZT", "2");
                upCondition.put("FP_DM", request_fpkj.getFP_DM());
                upCondition.put("FP_HM", request_fpkj.getFP_HM());
                upCondition.put("fp_id", fp_id);
                baseService.update(UPDATE_INVUPLOADRESULTFORPLATFORM, upCondition);
                upCondition.clear();
            }
            final Long millSeconds3 = new Duration(begin3, now()).getMillis();
            LOGGER.warn("(上传51发票)失败数据({}条)发票上传状态修改为2,用时{}毫秒", uploads.length, millSeconds3);
        }
    }
    //TODO 修改数据库上传大象状态
    public void modifyUploadResults(String returnCode,String fp_id) throws SQLException{
    	 Map<String, Object> upCondition = new HashMap<String, Object>();
    	  {
    		  upCondition.put("FPSC_ZT",returnCode);
              upCondition.put("fp_id", fp_id);
              baseService.update(UPDATE_INVUPLOADRESULTFORPLATFORM, upCondition);
              upCondition.clear();
    	  }
    }
    //TODO 修改数据库上传51状态
    public void modifyUpload51Results(String returnCode,String fp_id) throws SQLException{
    	Map<String, Object> upCondition = new HashMap<String, Object>();
    	{
    		upCondition.put("FPSC_ZT",returnCode);
    		upCondition.put("fp_id", fp_id);
    		baseService.update(UPDATE_INVUPLOADRESULTFOR51, upCondition);
    		upCondition.clear();
    	}
    }
    

    /**
     * 
     * <p>
     * 发票批量上传业务实现
     * </p>
     * 
     * @see com.aisino.domain.einvoice.resultService.IBusinessDataService#uploadInv(Map,
     *      List, Route)
     * @author: zhongsiwei
     * @date: Created on 2015-3-17 下午05:11:27
     */
    public boolean uploadInv(Map<String, Object> map, List<REQUEST_FPKJ> fpkjList, Route route,int scpt_lx) throws Exception {
        boolean blresult = false;
        LOGGER.info("发票上传至服务平台调用uploadInv开始.");
        try {

            final DateTime begin0 = now();
            final GlobalInfo globleInfo = new GlobalInfo();
            ProXml.getGlobalInfo(map, globleInfo);
            // 此处的发票ID由MQ中获得
            final String fp_id = (String) map.get("invoiceId");
            // 处理发票开具上传全局变量
            final REQUEST_FPKJSCGLOBLE request_fpkjscGloble = new REQUEST_FPKJSCGLOBLE();
            final REQUEST_FPKJ[] fpkjArray = new REQUEST_FPKJ[fpkjList.size()];
            request_fpkjscGloble.setFPSCLSH(CommonsUtil.getUUID() + DateUtil.getCurrentDateString("yyyyMMddHHmmss"));
            request_fpkjscGloble.setDSPTBM(globleInfo.getUserName());
            fpkjList.toArray(fpkjArray);
            final Long millSeconds0 = new Duration(begin0, now()).getMillis();
            LOGGER.warn("(上传51发票)组装外层报文用时{}毫秒", millSeconds0);
            for (REQUEST_FPKJ request_fpkj : fpkjArray) {

                // 根据开票流水号(发票代码+发票号码)查询开具ID
                String id = fp_id;

                if (fpkjList.size() > 1) {
                    // 批量上传时无法获得发票ID
                    // 根据开票流水号(发票代码+发票号码)查询开具ID
                    final DateTime begin = now();
                    id = String.valueOf(baseService.queryForObject(KEY_QUERYFPKJIDBYKPLSH, request_fpkj.getKPLSH()));
                    final Long millSeconds = new Duration(begin, now()).getMillis();
                    LOGGER.warn("(上传51发票)根据开票流水号查询开具ID用时{}毫秒", millSeconds);
                }
                final DateTime begin1 = now();
                final List<FP_KJMX> mxList = (List<FP_KJMX>) baseService.queryForList(KEY_QUERYMXFORFPSC, id);
                final Long millSeconds1 = new Duration(begin1, now()).getMillis();
                LOGGER.warn("(上传51发票)查询发票开具明细信息用时{}毫秒", millSeconds1);

                final DateTime begin2 = now();
                final List<FP_WLXX> wlxxList = (List<FP_WLXX>) baseService.queryForList(KEY_QUERYWLXXFORFPSC, id);
                final Long millSeconds2 = new Duration(begin2, now()).getMillis();
                LOGGER.warn("(上传51发票)查询发票物流信息用时{}毫秒", millSeconds2);

                final DateTime begin3 = now();
                final FP_DDXX fp_ddxx = (FP_DDXX) baseService.queryForObject(KEY_QUERYDDXXFORFPSC, id);
                final Long millSeconds3 = new Duration(begin3, now()).getMillis();
                LOGGER.warn("(上传51发票)查询发票订单信息用时{}毫秒", millSeconds3);

                final DateTime begin4 = now();
                final FP_ZFXX fp_zfxx = (FP_ZFXX) baseService.queryForObject(KEY_QUERYZFXXFORFPSC, id);
                final Long millSeconds4 = new Duration(begin4, now()).getMillis();
                LOGGER.warn("(上传51发票)查询发票支付信息用时{}毫秒", millSeconds4);

                request_fpkj.setFP_KJMXS(mxList.toArray(new FP_KJMX[mxList.size()]));
                request_fpkj.setFP_DDXX(fp_ddxx);
                request_fpkj.setFP_ZFXX(fp_zfxx);
                request_fpkj.setFP_WLXXS(wlxxList.toArray(new FP_WLXX[wlxxList.size()]));

                final DateTime begin6 = now();
                String pdfuploadString = SystemConfig.PDF_PERSON_PLATFORM;
                if ("Y".equals(pdfuploadString)) {
                    // 根据购货方名称是否是个人开头的来判断是否上传pdf文件,
                    String ghfmcString = request_fpkj.getGHFMC();
                    boolean bl = ghfmcString.startsWith("个人");
                    // 如果是个人开头的购货方,不上传pdf
                    if (bl) {
                        request_fpkj.setPDF_FILE("");
                    } else {
                        // 处理PDF
                        final String filepath = request_fpkj.getPDF_FILE();

                        if (LOGGER.isDebugEnabled()) {
                            LOGGER.debug("PDF_FILE:{}", filepath);
                        }

                        final DateTime begin5 = now();
                        final File f = new File(filepath);
                        if (f.exists()) {
                            final byte[] bb = FileUtils.readFileToByteArray(f);
                            final String ss = new String(ProXml.encode(bb));
                            request_fpkj.setPDF_FILE(ss);
                        } else {
                            request_fpkj.setPDF_FILE("");
                        }
                        final Long millSeconds5 = new Duration(begin5, now()).getMillis();
                        LOGGER.warn("(上传51发票)读取PDF信息用时{}毫秒", millSeconds5);
                    }
                } else {
                    // 处理PDF
                    final String filepath = request_fpkj.getPDF_FILE();

                    if (LOGGER.isDebugEnabled()) {
                        LOGGER.debug("PDF_FILE:{}", filepath);
                    }

                    final DateTime begin5 = now();
                    final File f = new File(filepath);
                    if (f.exists()) {
                        final byte[] bb = FileUtils.readFileToByteArray(f);
                        final String ss = new String(ProXml.encode(bb));
                        request_fpkj.setPDF_FILE(ss);
                    } else {
                        request_fpkj.setPDF_FILE("");
                    }
                    final Long millSeconds5 = new Duration(begin5, now()).getMillis();
                    LOGGER.warn("(上传51发票)读取PDF信息用时{}毫秒", millSeconds5);
                }

                final Long millSeconds6 = new Duration(begin6, now()).getMillis();
                LOGGER.warn("(上传51发票)(购货方名称:{})PDF删除用时{}毫秒", request_fpkj.getGHFMC(), millSeconds6);

            }
            request_fpkjscGloble.setREQUEST_FPKJS(fpkjArray);
            request_fpkjscGloble.setFPKJZLS(Integer.valueOf(fpkjArray.length).toString());

            // 电商平台注册号
            final String zch = PlatformConstants.PROTOCOL_ZCH_DEFAULT;

            final DateTime begin11 = now();
            // 发票上传
            Boolean bool = platformProcess.process(XmlPar.EI_INVUPLOAD_U_EC_IP, request_fpkjscGloble, route, zch, fp_id,scpt_lx);

            final Long millSeconds11 = new Duration(begin11, now()).getMillis();
            LOGGER.warn("((上传51发票))发票{}发票上传51平台耗时{}...", fp_id, millSeconds11);

            if (bool) {
                LOGGER.info("(上传51发票)发票上传至服务平台上传成功.");
                blresult = true;
            } else {
                LOGGER.info("(上传51发票)发票上传至服务平台上传失败.");
                blresult = false;
            }

        } catch (Exception e) {
            LOGGER.error("(上传51发票)发票上传至服务平台上传失败:{}", e.getMessage());
            // throw new Exception("发票上传至服务平台上传失败");
        }
        LOGGER.info("(上传51发票)发票上传至服务平台调用uploadInv结束.");
        return blresult;
    }

    @Override
    public void updateFpDoneLog(String sql, FpDoneLog fpDoneLog) throws SQLException {
        if (null == fpDoneLog.getFpkj_id()) {
            return;
        }

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("FpDoneLog:{}", fpDoneLog.toString());
        }

        final Map<String, Object> param = newHashMapWithExpectedSize(3);
        param.put("fpkj_id", fpDoneLog.getFpkj_id());
        param.put("stax", fpDoneLog.getStax());
        param.put("s51", fpDoneLog.getS51());
        param.put("lasteddate", fpDoneLog.getLasteddate());

        // 更新日志
        baseService.update(sql, param);
    }

    /**
     * 删除发票pdf
     * 
     * @param map
     * @param path
     */
    @Override
    public void modifyDeletePdf(Map<String, Object> map, String path) {
        final File file = new File(path);
        // 路径为文件且不为空则进行删除
        if (file.isFile() && file.exists()) {
            deleteQuietly(file);
        }

    }

    /**
     * 删除空文件夹
     * 
     * @param file
     */
    @Override
    public void modifyDeleteFile(File file) {
        try {
            deleteDirectory(file);
        } catch (IOException e) {
            LOGGER.error("delete dir error", e.fillInStackTrace());
        }
    }

    @Override
    public void updateFailState(Map<String, Object> map) throws Exception {
        baseService.update(KEY_UPDATEFAILFORFPSC, map);
    }

}
