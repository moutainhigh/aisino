package com.aisino.task;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Lists.transform;
import static org.joda.time.DateTime.now;
import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.util.CollectionUtils.isEmpty;

import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.slf4j.Logger;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.aisino.domain.SystemConfig;
import com.aisino.domain.einvoice.EInvoiceQueryCondition;
import com.aisino.domain.rabbit.entity.PushInvoiceQueueEntity;
import com.aisino.domain.rabbit.entity.SendPlatformQyeueEntity;
import com.aisino.einvoice.service.IInvUploadService;
import com.google.common.base.Function;

/**
 * 发票推送定时器
 * <p/>
 * Created by Bourne.Lv on 2014/12/04.
 */
@Component
public final class EinvoicePushTask {
    private static final Logger LOGGER = getLogger(EinvoiceGenerateTask.class);

    @Autowired
    private IInvUploadService invUploadService;

    @Autowired
    private AmqpTemplate amqpTemplate;

  /*  @Value("${rabbit.queue.invoice.push}")
    private String pushInvoiceQueue;*/
    @Value("${rabbit.queue.invoice.sendplatformserver}")
    private String sentToPlatformServerQueue;

    public void scheduler() {

        LOGGER.info("(反补任务2)开始执行待推送发票数据反补MQ任务......");

        final DateTime begin1 = now();

        final EInvoiceQueryCondition queryCondition = new EInvoiceQueryCondition();
        queryCondition.setAmount(SystemConfig.SIZE_GET_ESHOP_UPLOAD);
//        queryCondition.setInvoicePushStatus(SystemConfig.INVOICE_PUSH_STATE_FAILURE);
        /*
         * TODO 新需求：发票生成后先上传大象再下发给京东再上传给航信51队列中
         * 此处反补：是将数据反补到上传大象MQ队列中
    	 * 20171009-FWH
         */
        queryCondition.setInvoicePushStatus(SystemConfig.INVOICE_PUSH_STATE_WAIT);//状态为2000
        queryCondition.setInvoiceUploadStatus(SystemConfig.INVOICE_UPLOAD_STATE_FAILURE);//状态为2
        final List<PushInvoiceQueueEntity> list = invUploadService.queryEInvoicePush(queryCondition);

        final Long millSeconds1 = new Duration(begin1, now()).getMillis();

        if (LOGGER.isDebugEnabled()) {
//            LOGGER.debug("(反补任务2)查询待推送发票数据用时{}毫秒......", millSeconds1);
//            LOGGER.debug("(反补任务2)获取待推送发票数据{}条......", list.size());
            LOGGER.debug("(反补任务2)查询待上传大象发票数据用时{}毫秒......", millSeconds1);
            LOGGER.debug("(反补任务2)获取待上传大象发票数据{}条......", list.size());
        }

        if (!isEmpty(list)) {
            final DateTime begin3 = now();

            final List<Long> invoiceIds = newArrayList(transform(list, new Function<PushInvoiceQueueEntity, Long>() {
                @Override
                public Long apply(PushInvoiceQueueEntity queueEntity) {
                    return queueEntity.getInvoiceId();
                }
            }));
            final EInvoiceQueryCondition updateCondition = new EInvoiceQueryCondition();
            updateCondition.setInvoiceIds(invoiceIds);
//            updateCondition.setInvoicePushStatus(SystemConfig.INVOICE_PUSH_STATE_WAIT);
            updateCondition.setInvoiceUploadStatus(SystemConfig.INVOICE_UPLOAD_STATE_WAIT);//FWH-20101009-上传状态置为0
            //将上传大象失败的数据上传状态置为零,并放入队列中。
            final Integer count = invUploadService.updateInvoicePushStatus(updateCondition);
            LOGGER.info("(反补任务2)批量更新发票推送状态条数:{}", count);

            final Long millSeconds3 = new Duration(begin3, now()).getMillis();

            final DateTime begin2 = now();
            
            //20171009-反补数据放入上传大象MQ中
            final SendPlatformQyeueEntity sendPlatformQyeueEntity = new SendPlatformQyeueEntity();
            for (PushInvoiceQueueEntity each : list) {
                //amqpTemplate.convertAndSend(pushInvoiceQueue, each);
            	 sendPlatformQyeueEntity.setInvoiceId(each.getInvoiceId());
                 sendPlatformQyeueEntity.setEshopCode(each.getEshopCode());
                 amqpTemplate.convertAndSend(sentToPlatformServerQueue, sendPlatformQyeueEntity);
            	//amqpTemplate.convertAndSend(sentToPlatformServerQueue, each);//FWH-20171009-放入上传大象队列中
            }

            final Long millSeconds2 = new Duration(begin2, now()).getMillis();

            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("(反补任务2)待上传大象发票数据反补MQ用时{}毫秒......", millSeconds2);
                LOGGER.debug("(反补任务2)批量更新发票上传大象状态用时{}毫秒......", millSeconds3);
            }

        }
    }
}
