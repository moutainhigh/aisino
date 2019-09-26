package com.aisino.task;

import com.aisino.domain.SystemConfig;
import com.aisino.domain.einvoice.EInvoiceQueryCondition;
import com.aisino.domain.rabbit.entity.SentToTaxQueueEntity;
import com.aisino.einvoice.service.IInvUploadService;
import com.google.common.base.Function;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.slf4j.Logger;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.aisino.domain.constantenum.InvoiceStatusEnum.*;
import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Lists.transform;
import static org.joda.time.DateTime.now;
import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.util.CollectionUtils.isEmpty;

/**
 * Created with IntelliJ IDEA.
 * User: Schiffer.huang
 * Date: 14-8-7
 * Time: 上午9:57
 * 获取待上传至税局的电子发票（invoiceId）信息 TASK
 * 电子发票（invoiceId）信息,发送 MQ.
 */
@Component
public final class TaxUploadTask {

    private static final Logger LOGGER = getLogger(TaxUploadTask.class);

    @Autowired
    private IInvUploadService invUploadService;

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Value("${rabbit.queue.invoice.sendtaxserver}")
    private String sentToTaxServerQueue;

    public void scheduler() {

        LOGGER.info("(反补任务3)开始执行待上传至税局的电子发票数据反补MQ任务......");

        final DateTime begin1 = now();

        EInvoiceQueryCondition queryCondition = new EInvoiceQueryCondition();
        queryCondition.setAmount(SystemConfig.SIZE_GET_ESHOP_UPLOAD);
        queryCondition.setInvoiceStatus(INV_UPLOAD_TAX_FAILED.getParameterValue());
        queryCondition.setErrorCount(SystemConfig.default_error_count);
        final List<SentToTaxQueueEntity> list = invUploadService.queryTaxUpload(queryCondition);

        final Long millSeconds1 = new Duration(begin1, now()).getMillis();

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("(反补任务3)查询待生成发票数据用时{}毫秒......", millSeconds1);
            LOGGER.debug("(反补任务3)获取待生成发票数据{}条......", list.size());
        }

        if (!isEmpty(list)) {
            final DateTime begin3 = now();

            final List<Long> invoiceIds = newArrayList(transform(list, new Function<SentToTaxQueueEntity, Long>() {
                @Override
                public Long apply(SentToTaxQueueEntity queueEntity) {
                    return queueEntity.getInvoiceId();
                }
            }));
            final EInvoiceQueryCondition updateCondition = new EInvoiceQueryCondition();
            updateCondition.setInvoiceIds(invoiceIds);
            updateCondition.setInvoiceStatus(INV_CREATE_SUCCESS.getParameterValue());

            final Integer count = invUploadService.updateInvoiceStatus(updateCondition);
            LOGGER.info("(反补任务3)批量更新发票状态条数:{}", count);

            final Long millSeconds3 = new Duration(begin3, now()).getMillis();

            final DateTime begin2 = now();

            for (SentToTaxQueueEntity each : list) {
                amqpTemplate.convertAndSend(sentToTaxServerQueue, each);
            }

            final Long millSeconds2 = new Duration(begin2, now()).getMillis();

            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("(反补任务3)待生成发票数据反补MQ用时{}毫秒......", millSeconds2);
                LOGGER.debug("(反补任务3)批量更新发票状态用时{}毫秒......", millSeconds3);
            }

        }

    }
}
