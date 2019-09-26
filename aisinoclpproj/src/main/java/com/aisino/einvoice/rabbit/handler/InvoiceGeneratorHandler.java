package com.aisino.einvoice.rabbit.handler;

import static com.aisino.domain.constantenum.InvoiceStatusEnum.INV_CREATE_FAILURE;
import static com.aisino.domain.constantenum.InvoiceStatusEnum.INV_TAXCODE_SUCCESS;
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
import com.aisino.domain.einvoice.EInvoiceQueryCondition;
import com.aisino.domain.einvoice.InvoiceDataAccessManagerService;
import com.aisino.domain.einvoice.entity.InvoiceEntity;
import com.aisino.domain.einvoice.entity.InvoiceHeaderEntity;
import com.aisino.domain.einvoice.entity.TaxpayerRouteEntity;
import com.aisino.domain.rabbit.entity.GeneratorInvoiceQueueEntity;
import com.aisino.domain.rabbit.entity.PushInvoiceQueueEntity;
import com.aisino.domain.rabbit.entity.SendPlatformQyeueEntity;
import com.aisino.domain.rabbit.entity.SentToTaxQueueEntity;
import com.aisino.einvoice.service.IInvUploadService;
import com.google.gson.Gson;

/**
 * 处理生成发票文件,并向下一步MQ发送信息
 */
@Service
public final class InvoiceGeneratorHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(InvoiceGeneratorHandler.class);

    //有效路由标示
    private static final String ROUTE_AVAILABLE = "1";

    @Autowired
    private IInvUploadService invUploadService;

    @Autowired
    private InvoiceDataAccessManagerService invoiceDataAccessManagerService;

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Value("${rabbit.queue.invoice.sendplatformserver}")
    private String sentToPlatformServerQueue;

    @Value("${rabbit.queue.invoice.push}")
    private String pushInvoiceQueue;

    public void handleMessage(final InvoiceHeaderEntity invoiceHeaderEntity) {

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("(生成发票)从生成发票MQ中获取到的发票信息:{}", new Gson().toJson(invoiceHeaderEntity));
        }

        // final InvoiceHeaderEntity invoiceHeaderEntity = nullInvoiceHeaderEntity();
        // wrapInvoiceHeaderEntity(generatorInvoiceQueueEntity,invoiceHeaderEntity);
        // invoiceHeaderEntity.setInfoClientAddressPhone("测试地址");
        // System.out.println(invoiceHeaderEntity.getInfoClientAddressPhone()+"6666666666");
        /*应京东方要求,如果用户填上信息就在票面上显示地址电话信息;
         * 如果用户无填地址电话信息票面不显示
         * TODO-fwh-20180322
         */
        //invoiceHeaderEntity.setInfoClientAddressPhone("");
        sendTax(invoiceHeaderEntity);
    }

    /**
     * 生成发票,并向“上传税局MQ队列”发送数据
     * <p/>
     * 注意:每次操作尝试如果失败，error_count增加1，状态不变，
     * 反补定时器将此记录写入MQ尝试再次操作
     * 直到error_count大于4次，则该记录状态修改为失败，同时error_count重置为0
     *
     * @param invoiceHeaderEntity 发票头信息实体
     */
    private void sendTax(final InvoiceHeaderEntity invoiceHeaderEntity) {

        //发票ID
        final Long invoiceId = invoiceHeaderEntity.getInvoiceEntity().getInvoiceId();

        try {
            final DateTime begin1 = now();

            final Boolean verifyResult = invoiceDataAccessManagerService.verifyInvoiceStatus(INV_TAXCODE_SUCCESS.getParameterValue(), invoiceId);

            final Long millSeconds1 = new Duration(begin1, now()).getMillis();
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("(生成发票)发票{}, 校验发票状态用时{}毫秒......", invoiceId, millSeconds1);
            }

            //验证当前是否符合生成发票，FP_KJZT=2100
            if (!verifyResult) {

                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug("(生成发票)无效的状态, invoiceId:{}", invoiceId);
                }

                LOGGER.info("..................生成发票结束(无效状态)........................, invoiceId:{}", invoiceId);

            } else {

                LOGGER.info("..................生成发票开始.........................., invoiceId:{}", invoiceId);

                //生成发票
                final Boolean result = invUploadService.generatorInvoice(invoiceHeaderEntity);

                if (result) {
                    //发票推送
                    pushInvoice(invoiceHeaderEntity);

                    //向上传税局MQ队列发送数据
//                    final SentToTaxQueueEntity sentToTaxQueueEntity = new SentToTaxQueueEntity();
//                    wrapSentToTaxQueueEntity(invoiceHeaderEntity, sentToTaxQueueEntity);
////                    amqpTemplate.convertAndSend(sentToTaxServerQueue, sentToTaxQueueEntity);
//                    final SendPlatformQyeueEntity sendPlatformQyeueEntity = new SendPlatformQyeueEntity();
//                    sendPlatformQyeueEntity.setInvoiceId(invoiceHeaderEntity.getInvoiceEntity().getInvoiceId());
//                    sendPlatformQyeueEntity.setEshopCode(invoiceHeaderEntity.getInvoiceEntity().getEshopCode());
//                    amqpTemplate.convertAndSend(sentToPlatformServerQueue, sendPlatformQyeueEntity);

                    if (LOGGER.isDebugEnabled()) {
                    	 LOGGER.debug("(生成发票)发票{}, 向上传大象MQ队列发送数据{}......", invoiceId, invoiceHeaderEntity.toString());                    }
                }

                LOGGER.info("..................生成发票结束(成功)........................, invoiceId:{}", invoiceId);

            }
        } catch (FpztException e) {
//            LOGGER.error("..................生成发票结束(失败)........................, invoiceId:{}", invoiceId);
            LOGGER.error("(生成发票)生成发票失败,invoiceId:{},异常为:{}",invoiceId, e);
            invoiceDataAccessManagerService.saveInvoiceErrorInfo(invoiceId, INV_CREATE_FAILURE.getParameterValue(), e.getMessage());
        } catch (Exception e) {
//            LOGGER.error("..................生成发票结束(失败)........................, invoiceId:{}", invoiceId);
            LOGGER.error("(生成发票)生成发票时业务处理异常,invoiceId:{},异常为:{}",invoiceId, e);
            invoiceDataAccessManagerService.saveInvoiceErrorInfo(invoiceId, INV_CREATE_FAILURE.getParameterValue(), "生成发票时业务处理异常");
        }
    }

    /**
     * 发票推送
     *
     * @param sourceObj 发票实体
     */
    private void pushInvoice(InvoiceHeaderEntity sourceObj) {
        //获取发票ID
        final Long invoiceId = sourceObj.getInvoiceEntity().getInvoiceId();
        //获取纳税人识别号
        final String taxpayerIdentifyNo = sourceObj.getTaxpayerIdentifyNo();

        if (isNullOrEmpty(taxpayerIdentifyNo)) {

            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("(生成发票)发票{}, 推送错误:纳税人识别号为空.", invoiceId);
            }

        } else {

            final EInvoiceQueryCondition condition = new EInvoiceQueryCondition();
            condition.setTaxpayerIdentifyNo(taxpayerIdentifyNo);
            condition.setAvailable(ROUTE_AVAILABLE);

            //查询纳税人启用的路由信息
            final TaxpayerRouteEntity taxpayerRoute = invoiceDataAccessManagerService.queryTaxpayerRouteInfo(condition);

            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("(生成发票)纳税人路由信息:{}", taxpayerRoute.toString());
            }

            if (!taxpayerRoute.isNullObject()) {
                //向发票推送MQ队列发送数据
               /* final PushInvoiceQueueEntity pushInvoiceQueueEntity = new PushInvoiceQueueEntity();
                wrapPushInvoiceQueueEntity(sourceObj, pushInvoiceQueueEntity);
                pushInvoiceQueueEntity.setTaxpayerUrl(taxpayerRoute.getUrl());
                pushInvoiceQueueEntity.setWsMethodName(taxpayerRoute.getWsMethodName());
                pushInvoiceQueueEntity.setEncrypType(taxpayerRoute.getEncrypType());
                amqpTemplate.convertAndSend(pushInvoiceQueue, pushInvoiceQueueEntity);
                
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug("(生成发票)发票{}, 向发票推送MQ队列发送数据{}......", invoiceId, pushInvoiceQueueEntity.toString());
                }*/
                
                //TODO 同时放入上传队列当中20170523
                final SendPlatformQyeueEntity sendPlatformQyeueEntity = new SendPlatformQyeueEntity();
                sendPlatformQyeueEntity.setInvoiceId(sourceObj.getInvoiceEntity().getInvoiceId());
                sendPlatformQyeueEntity.setEshopCode(sourceObj.getInvoiceEntity().getEshopCode());
                amqpTemplate.convertAndSend(sentToPlatformServerQueue, sendPlatformQyeueEntity);
                if (LOGGER.isDebugEnabled()) {
                	 LOGGER.debug("(生成发票)发票{}, 发票推送到上传大象队列中,数据是:{}......", invoiceId, sourceObj.toString());                }
                

            } else if (LOGGER.isDebugEnabled()) {

                LOGGER.debug("(生成发票)纳税人{},无有效(启用)的路由信息，没有推送发票.", taxpayerIdentifyNo);
            }
        }
    }

    private void wrapInvoiceHeaderEntity(GeneratorInvoiceQueueEntity sourceObj, InvoiceHeaderEntity targetObj) {
        copyProperties(sourceObj, targetObj);
        final InvoiceEntity invoiceEntity = nullInvoiceEntity();
        wrapInvoiceEntity(sourceObj, invoiceEntity);
        targetObj.setInvoiceEntity(invoiceEntity);
    }

    private void wrapInvoiceEntity(GeneratorInvoiceQueueEntity sourceObj, InvoiceEntity targetObj) {
        copyProperties(sourceObj, targetObj);
    }

    private void wrapSentToTaxQueueEntity(InvoiceHeaderEntity sourceObj, SentToTaxQueueEntity targetObj) {
        copyProperties(sourceObj, targetObj);
        targetObj.setInvoiceId(sourceObj.getInvoiceEntity().getInvoiceId());
    }

    private void wrapPushInvoiceQueueEntity(InvoiceHeaderEntity sourceObj, PushInvoiceQueueEntity targetObj) {
        copyProperties(sourceObj, targetObj);

        final InvoiceEntity invoiceEntity = sourceObj.getInvoiceEntity();
        targetObj.setInvoiceId(invoiceEntity.getInvoiceId());
        targetObj.setPdfPath(invoiceEntity.getPdfPath());
        targetObj.setInvoiceSerialNo(invoiceEntity.getInvoiceSerialNo());
        targetObj.setFiscalCode(invoiceEntity.getFiscalCode());
        targetObj.setTwoDimensionCode(invoiceEntity.getTwoDimensionCode());
        targetObj.setInvoiceKindCode(invoiceEntity.getInvoiceKindCode());
        targetObj.setInvoiceCode(invoiceEntity.getInvoiceCode());
        targetObj.setInvoiceNo(invoiceEntity.getInvoiceNo());
        targetObj.setBillingType(invoiceEntity.getBillingType());
        targetObj.setBillingDate(invoiceEntity.getBillingDate());
    }

}
