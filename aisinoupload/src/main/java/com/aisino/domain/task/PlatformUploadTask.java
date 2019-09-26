package com.aisino.domain.task;

import com.aisino.domain.base.service.IBaseService;
import com.aisino.domain.einvoice.model.Dsptxxkt;
import com.aisino.domain.rabbit.entity.SendPlatformQyeueEntity;
import com.aisino.domain.task.service.IFpxxTaskService;
import com.aisino.protocol.bean.FPKJXX_FPTXX;
import com.aisino.web.util.Constants;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import static org.joda.time.DateTime.now;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public final class PlatformUploadTask {

    private final static Logger LOGGER = LoggerFactory.getLogger(PlatformUploadTask.class);

    @Autowired
    private IFpxxTaskService fpxxTaskService;

    @Autowired
    private IBaseService baseService;

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Value("${rabbit.queue.invoice.sendplatformserver}")
    private String sentToPlatformServerQueue;

    /**
     * 发票上传51服务平台定时器
     * 
     * @throws SQLException
     */
    @SuppressWarnings("unchecked")
    public void upload() {
        LOGGER.info("服务平台发票上传任务开始...");
        DateTime begin1 = now();
        try {
            // 获取电商平台信息
            DateTime begin2 = now();
            final List<Dsptxxkt> dsptList = fpxxTaskService.queryDsptxxForList();
            Long time2 = new Duration(begin2, now()).getMillis();
            LOGGER.warn("PlatformUploadTask定时器,获取平台编码queryDsptxxForList,耗时:{}", time2);
            Map<String, Object> map = new HashMap<String, Object>();
            // Route route = PlatformConstants.getRoute();
            for (Dsptxxkt dsptxx : dsptList) { /* 按电商分别扫描 */
                boolean bl = false;
                String dsptbm = dsptxx.getDsptbm();
                // map.put("zt", "2");
                map.put("dsptbm", dsptbm);
                int start = 0; // 分页开始数量
                int count = Integer.parseInt(Constants.FPSC51_SL); // 每次需要处理的数量
                int size = Integer.parseInt(Constants.FPSC51_PAGECOUNT); // 分页后每页的数量
                int page = count / size; // 分页
                if (count % size != 0)
                    ++page; // 余数大于0的,增加一页
                for (int i = 1; i <= page; i++) { // 按照页数循环
                     start = (i - 1) * size; //每页的起始数
                    DateTime begin6 = now();
                    map.put("start", start);
                    map.put("count", size);
                    DateTime begin3 = now();
                    List<FPKJXX_FPTXX> fpkjList = (List<FPKJXX_FPTXX>) baseService.queryForList("queryInvUploadForPlatform", map);
                    Long time3 = new Duration(begin3, now()).getMillis();
                    LOGGER.warn("PlatformUploadTask定时器,获取数据库数据queryInvUploadForPlatform,耗时:{}", time3);
                    Map<String, Object> mapIds = new HashMap<String, Object>();
                    if (fpkjList != null && fpkjList.size() > 0) {
                        for (int j = 0; j < fpkjList.size(); j++) {

                            DateTime begin4 = now();
                            final SendPlatformQyeueEntity sendPlatformQyeueEntity = new SendPlatformQyeueEntity();
                            FPKJXX_FPTXX fpkj = fpkjList.get(j);
                            sendPlatformQyeueEntity.setInvoiceId(fpkj.getID());
                            sendPlatformQyeueEntity.setEshopCode(dsptbm);
                            amqpTemplate.convertAndSend(sentToPlatformServerQueue, sendPlatformQyeueEntity);
                            Long time4 = new Duration(begin4, now()).getMillis();
                            LOGGER.warn("PlatformUploadTask定时器,放入上传51发票mq中,耗时:{}", time4);
                            mapIds.put("id", fpkj.getID());
                            DateTime begin5 = now();
                            baseService.update("updateById", mapIds);
                            Long time5 = new Duration(begin5, now()).getMillis();
                            LOGGER.warn("PlatformUploadTask定时器,updateById修改发票上传状态为0,耗时:{}", time5);
                        }

                    } else {
                        bl = true;
                    }
                    Long time6 = new Duration(begin6, now()).getMillis();
                    LOGGER.warn("SelectStatusTask定时器,for循环单条,耗时:{}", time6);
                    // 如果没有数据跳出循环
                    if (bl) {
                        break;
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("发票上传任务失败..." + e.getMessage());
        }
        LOGGER.info("发票上传任务结束...");
        Long time1 = new Duration(begin1, now()).getMillis();
        LOGGER.warn("PlatformUploadTask定时器,upload定时器结束,耗时:{}", time1);
    }
}
