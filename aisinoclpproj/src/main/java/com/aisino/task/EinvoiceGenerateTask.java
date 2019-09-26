package com.aisino.task;

import static com.aisino.domain.constantenum.InvoiceStatusEnum.INV_CREATE_FAILURE;
import static com.aisino.domain.constantenum.InvoiceStatusEnum.INV_TAXCODE_SUCCESS;
import static com.aisino.domain.einvoice.EInvoiceNullObjects.nullInvoiceEntity;
import static com.aisino.domain.einvoice.EInvoiceNullObjects.nullInvoiceHeaderEntity;
import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Lists.transform;
import static org.joda.time.DateTime.now;
import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.beans.BeanUtils.copyProperties;
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
import com.aisino.domain.einvoice.entity.InvoiceEntity;
import com.aisino.domain.einvoice.entity.InvoiceHeaderEntity;
import com.aisino.domain.einvoice.entity.OrderInfoEntity;
import com.aisino.domain.rabbit.entity.GeneratorInvoiceQueueEntity;
import com.aisino.einvoice.service.IInvUploadService;
import com.google.common.base.Function;

/**
 * Created with IntelliJ IDEA.
 * User: Schiffer.huang
 * Date: 14-8-7
 * Time: 上午10:23
 * 获取受理待生成的电子发票（invoiceId）信息 TASK
 * 电子发票（invoiceId）信息,发送 MQ.
 */
@Component
public final class EinvoiceGenerateTask {

    private static final Logger LOGGER = getLogger(EinvoiceGenerateTask.class);

    @Autowired
    private IInvUploadService invUploadService;

    @Autowired
    private AmqpTemplate amqpTemplate;
    
    @Value("${rabbit.queue.invoice.generator}")
    private String generatorInvoiceQueue;
    
    @Autowired
    private AmqpTemplate amqpTemplate2;
    //TODO 使用新队列
    @Value("${rabbit.queue.invoice.generateinvoicefailed}")
    private String generateinvoicefailedQueue;

 /*   @Value("${rabbit.queue.invoice.orderinfo}")
    private String orderinfoQueue;*/

    public void scheduler() {

        LOGGER.info("(反补任务1)开始执行待生成发票数据反补MQ任务......");

        final DateTime begin1 = now();

        final EInvoiceQueryCondition queryCondition = new EInvoiceQueryCondition();
        queryCondition.setAmount(SystemConfig.SIZE_GET_ESHOP_UPLOAD);
        queryCondition.setInvoiceStatus(INV_CREATE_FAILURE.getParameterValue());
        queryCondition.setErrorCount(SystemConfig.default_error_count);
        final List<GeneratorInvoiceQueueEntity> list = invUploadService.queryEInvoiceGenerate(queryCondition);

        final Long millSeconds1 = new Duration(begin1, now()).getMillis();

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("(反补任务1)查询待生成发票数据用时{}毫秒......", millSeconds1);
            LOGGER.debug("(反补任务1)获取待生成发票数据{}条......", list.size());
        }

        if(!isEmpty(list)){
            final DateTime begin3 = now();

            final List<Long> invoiceIds = newArrayList(transform(list, new Function<GeneratorInvoiceQueueEntity, Long>() {
                @Override
                public Long apply(GeneratorInvoiceQueueEntity queueEntity) {
                    return queueEntity.getInvoiceId();
                }
            }));
            final EInvoiceQueryCondition updateCondition = new EInvoiceQueryCondition();
            updateCondition.setInvoiceIds(invoiceIds);
            updateCondition.setInvoiceStatus(INV_TAXCODE_SUCCESS.getParameterValue());

            final Integer count = invUploadService.updateInvoiceStatus(updateCondition);
            LOGGER.info("(反补任务1)批量更新发票状态条数:{}", count);

            final Long millSeconds3 = new Duration(begin3, now()).getMillis();

            final DateTime begin2 = now();

            for (GeneratorInvoiceQueueEntity each : list) {
                final InvoiceHeaderEntity invoiceHeaderEntity = nullInvoiceHeaderEntity();
                copyProperties(each, invoiceHeaderEntity);
                invoiceHeaderEntity.setCashier(each.getCashier());
                final InvoiceEntity invoiceEntity = nullInvoiceEntity();
                copyProperties(each, invoiceEntity);
                invoiceHeaderEntity.setInvoiceEntity(invoiceEntity);
                amqpTemplate.convertAndSend(generatorInvoiceQueue, invoiceHeaderEntity);
             
                //TODO 赋值并放入新队列
                OrderInfoEntity orderInfo = new OrderInfoEntity();
                try {
                	copyPropertiesToOrderInfo(orderInfo,each);
                    amqpTemplate2.convertAndSend(generateinvoicefailedQueue, orderInfo);
				} catch (Exception e) {
					LOGGER.error("---开票数据{}放入生成发票失败队列失败,错误信息:{}---",orderInfo.getDdh(),e.toString());
				}
                
            }

            final Long millSeconds2 = new Duration(begin2, now()).getMillis();

            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("(反补任务1)待生成发票数据反补MQ用时{}毫秒......", millSeconds2);
                LOGGER.debug("(反补任务1)批量更新发票状态用时{}毫秒......", millSeconds3);
            }

        }
    }



	private void copyPropertiesToOrderInfo(OrderInfoEntity orderInfo,GeneratorInvoiceQueueEntity entity) {
		orderInfo.setDdh(entity.getOrderNo());
    	orderInfo.setCwms("发票生成失败:" + entity.getErrorMsg());
    	orderInfo.setNsrsbh(entity.getTaxpayerIdentifyNo());
    	orderInfo.setZtm("7001");
    	orderInfo.setHjje(entity.getBillingAmount());
	}
}
