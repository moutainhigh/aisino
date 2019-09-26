package com.aisino.einvoice.rabbit.handler;

import static com.aisino.domain.constantenum.InvoiceStatusEnum.INV_OBTAIN_ESHOP_DATA_SUCCESS;
import static com.aisino.domain.constantenum.InvoiceStatusEnum.INV_TAXCODE_FAILURE;
import static com.aisino.domain.einvoice.EInvoiceNullObjects.nullInvoiceEntity;
import static com.aisino.domain.einvoice.EInvoiceNullObjects.nullInvoiceHeaderEntity;
import static com.google.common.base.Strings.isNullOrEmpty;
import static org.joda.time.DateTime.now;
import static org.springframework.beans.BeanUtils.copyProperties;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.aisino.common.util.FpztException;
import com.aisino.domain.einvoice.InvoiceDataAccessManagerService;
import com.aisino.domain.einvoice.entity.InvoiceEntity;
import com.aisino.domain.einvoice.entity.InvoiceHeaderEntity;
import com.aisino.domain.rabbit.entity.GeneratorInvoiceQueueEntity;
import com.aisino.einvoice.service.IInvUploadService;
import com.google.gson.Gson;

@Service
public final class InvoiceZcTaxCodeHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(InvoiceZcTaxCodeHandler.class);

    @Autowired
    private IInvUploadService invUploadService;

    @Autowired
    private InvoiceDataAccessManagerService invoiceDataAccessManagerService;

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Value("${rabbit.queue.invoice.generator}")
    private String generatorInvoiceQueue;

    public void handleMessage(final GeneratorInvoiceQueueEntity generatorInvoiceQueueEntity) {

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("(发票赋码)从发票赋码MQ中获取到的发票信息:{}", new Gson().toJson(generatorInvoiceQueueEntity));
        }

        final InvoiceHeaderEntity invoiceHeaderEntity = nullInvoiceHeaderEntity();
        wrapInvoiceHeaderEntity(generatorInvoiceQueueEntity, invoiceHeaderEntity);

        zctaxCode(invoiceHeaderEntity);
    }

    /**
     * 调用接口服务，给发票赋码
     * <p/>
     * 
     * @param invoiceHeaderEntity
     *            发票头信息实体
     */
    private void zctaxCode(final InvoiceHeaderEntity invoiceHeaderEntity) {

        // 发票ID
        final Long invoiceId = invoiceHeaderEntity.getInvoiceEntity().getInvoiceId();

        try {
            final DateTime begin1 = now();

            final Boolean verifyResult = invoiceDataAccessManagerService.verifyInvoiceStatus(INV_OBTAIN_ESHOP_DATA_SUCCESS.getParameterValue(), invoiceId);

            final Long millSeconds1 = new Duration(begin1, now()).getMillis();
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("(发票赋码)发票{}, 校验发票状态用时{}毫秒......", invoiceId, millSeconds1);
            }

            // 验证当前是否符合发票赋码，FP_KJZT=1000
            if (!verifyResult) {

                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug("(发票赋码)无效的状态, invoiceId:{}", invoiceId);
                }

                LOGGER.info("..................发票赋码结束(无效状态)........................, invoiceId:{}", invoiceId);

            } else {

                LOGGER.info("..................发票赋码开始.........................., invoiceId:{}", invoiceId);

                // 发票赋码
                final Boolean result = invUploadService.zctaxcodeInvoice(invoiceHeaderEntity);

                if (result) {
                    // 向发票生成mq中放数据
                    pushInvoice(invoiceHeaderEntity);
                    LOGGER.info("..................发票赋码结束(成功)........................, invoiceId:{}", invoiceId);
                }

            }
        } catch (FpztException e) {
//            LOGGER.error("..................发票赋码结束(失败)........................, invoiceId:{}", invoiceId);
            LOGGER.error("(发票赋码)发票赋码失败,invoiceId:{},异常为:{}", invoiceId, e);
            invoiceDataAccessManagerService.saveInvoiceErrorInfo(invoiceId, INV_TAXCODE_FAILURE.getParameterValue(), e.getMessage());
        } catch (Exception e) {
//            LOGGER.error("..................发票赋码结束(失败)........................, invoiceId:{}", invoiceId);
            LOGGER.error("(发票赋码)发票赋码时业务处理异常,invoiceId:{},异常为:{}", invoiceId, e);
            invoiceDataAccessManagerService.saveInvoiceErrorInfo(invoiceId, INV_TAXCODE_FAILURE.getParameterValue(), e.getMessage());
        }
    }

    /**
     * 发票推送
     * 
     * @param sourceObj
     *            发票实体
     */
    private void pushInvoice(InvoiceHeaderEntity sourceObj) {
        // 获取发票ID
        final Long invoiceId = sourceObj.getInvoiceEntity().getInvoiceId();
        // 获取纳税人识别号
        final String taxpayerIdentifyNo = sourceObj.getTaxpayerIdentifyNo();

        if (isNullOrEmpty(taxpayerIdentifyNo)) {

            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("(发票赋码)发票{}, 推送错误:纳税人识别号为空.", invoiceId);
            }

        } else {
            // 向发票生成MQ队列发送数据
            amqpTemplate.convertAndSend(generatorInvoiceQueue, sourceObj);

            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("(发票赋码)发票{}, 向发票生成MQ队列发送数据{}......", invoiceId, sourceObj.toString());
            }
        }
    }

    private void wrapInvoiceHeaderEntity(GeneratorInvoiceQueueEntity sourceObj, InvoiceHeaderEntity targetObj) {
        copyProperties(sourceObj, targetObj);
        targetObj.setCashier(sourceObj.getCashier());
        final InvoiceEntity invoiceEntity = nullInvoiceEntity();
        wrapInvoiceEntity(sourceObj, invoiceEntity);
        invoiceEntity.setJqbh(sourceObj.getJqbh());
        targetObj.setInvoiceEntity(invoiceEntity);
    }

    private void wrapInvoiceEntity(GeneratorInvoiceQueueEntity sourceObj, InvoiceEntity targetObj) {
        copyProperties(sourceObj, targetObj);
    }

}
