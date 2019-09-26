package com.aisino.domain.task;

import static org.joda.time.DateTime.now;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.aisino.domain.base.service.IBaseService;
import com.aisino.domain.rabbit.entity.PushInvoiceQueueEntity;
import com.aisino.domain.task.service.IFpxxTaskService;
import com.aisino.web.util.Constants;

@Service
public final class SelectPushFailedTask {

    private final static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(SelectPushFailedTask.class);

    @Autowired
    private IFpxxTaskService fpxxTaskService;

    @Autowired
    private IBaseService baseService;

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Value("${rabbit.queue.invoice.push}")
    private String pushInvoiceQueue;

    /**
     * 查询发票状态为3001,上传状态1的发票
     */
    public void selectPushFailedJob() {
        DateTime begin1 = now();
        try {
            // 获取电商平台信息
            DateTime begin2 = now();
            Map<String, Object> queryMap = new HashMap<String, Object>();
            queryMap.put("fpkj_zt", Constants.INV_ESHOP_FAIL);
            queryMap.put("fpsc_zt", "1");
            int startq = 0;
            int sizeq = Integer.parseInt(Constants.FPSC51_SL);
            queryMap.put("start", startq);
            queryMap.put("count", sizeq);
            final List<PushInvoiceQueueEntity> pushInvoiceList = fpxxTaskService.queryPushInvoiceForList(queryMap);
            Long time2 = new Duration(begin2, now()).getMillis();
            LOGGER.warn("SelectPushFailedTask定时器,获取推送失败发票,耗时:{}", time2);
          
            for (PushInvoiceQueueEntity pushInvoice : pushInvoiceList) { 
              
                    DateTime begin4 = now();
                    amqpTemplate.convertAndSend(pushInvoiceQueue, pushInvoice);
                    //TODO -FWH-20171013 -打印日志
                    LOGGER.debug("ID为:{},放入推送MQ队列数据信息为:{}",pushInvoice.getInvoiceId(),pushInvoice);
                    
                    Long time4 = new Duration(begin4, now()).getMillis();
                    LOGGER.warn("SelectPushFailedTask定时器,放入上传推送mq中,耗时:{}", time4);
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("fpkj_id", pushInvoice.getInvoiceId());
                    map.put("fpkj_zt", "2000");
                    
                    fpxxTaskService.updateInvoiceStatusById(map);
            }
        } catch (Exception e) {
            LOGGER.error("发票推送反补任务失败..." + e.getMessage());
        }
        LOGGER.info("发票推送反补任务结束...");
        Long time1 = new Duration(begin1, now()).getMillis();
        LOGGER.warn("SelectPushFailedTask定时器,selectPushFailedJob定时器结束,耗时:{}", time1);
    }
}
