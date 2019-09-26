package com.aisino.einvoice.rabbit.handler;

import java.io.File;

import com.aisino.common.util.EShopCertificateBytesInfo;
import com.aisino.common.util.FpztConstants;
import com.aisino.common.util.FpztException;
import com.aisino.domain.SystemConfig;
import com.aisino.domain.einvoice.InvoiceDataAccessManagerService;
import com.aisino.domain.einvoice.entity.InvoiceEntity;
import com.aisino.domain.einvoice.entity.InvoiceHeaderEntity;
import com.aisino.domain.rabbit.entity.PushInvoiceQueueEntity;
import com.aisino.domain.rabbit.entity.SendPlatformQyeueEntity;
import com.aisino.domain.rabbit.entity.SentToTaxQueueEntity;
import com.aisino.einvoice.service.IInvUploadService;
import com.google.gson.Gson;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import static com.aisino.domain.constantenum.InvoiceStatusEnum.INV_CREATE_SUCCESS;
import static com.aisino.domain.einvoice.EInvoiceNullObjects.nullInvoiceEntity;
import static com.aisino.domain.einvoice.EInvoiceNullObjects.nullInvoiceHeaderEntity;
import static org.joda.time.DateTime.now;
import static org.springframework.beans.BeanUtils.copyProperties;

/**
 * 处理发票推送电商
 */
@Service
public final class InvoicePushHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(InvoicePushHandler.class);

    @Autowired
    private IInvUploadService invUploadService;
    
    @Autowired
    private AmqpTemplate amqpTemplate;
    
    @Value("${rabbit.queue.invoice.send51server}")
    private String send51serverQueue;

    @Autowired
    private InvoiceDataAccessManagerService invoiceDataAccessManagerService;

    public void handleMessage(final PushInvoiceQueueEntity pushInvoiceQueueEntity) {

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("(发票推送)从发票推送MQ中获取到的发票信息:{}", new Gson().toJson(pushInvoiceQueueEntity));
        }

        final InvoiceHeaderEntity invoiceHeaderEntity = nullInvoiceHeaderEntity();
        wrapInvoiceHeaderEntity(pushInvoiceQueueEntity, invoiceHeaderEntity);

        //企业WS地址
//        final String taxpayerUrl = pushInvoiceQueueEntity.getTaxpayerUrl();
//        final String wsMethodName = pushInvoiceQueueEntity.getWsMethodName();

        pushInvoice(invoiceHeaderEntity, pushInvoiceQueueEntity);
    }

    /**
     * 发票推送
     *
     * @param invoiceHeaderEntity 发票头信息实体
     * @param taxpayerUrl         企业WS地址
     */
    private void pushInvoice(final InvoiceHeaderEntity invoiceHeaderEntity, final PushInvoiceQueueEntity pushInvoiceQueueEntity) {
        Boolean result = false;
        //发票ID
        final Long invoiceId = invoiceHeaderEntity.getInvoiceEntity().getInvoiceId();

        try {
            LOGGER.info("..................发票推送开始.........................., invoiceId:{}", invoiceId);

            final DateTime begin1 = now();

            final Boolean verifyResult = invoiceDataAccessManagerService.verifyInvoiceStatus(INV_CREATE_SUCCESS.getParameterValue(), invoiceId);

            final Long millSeconds1 = new Duration(begin1, now()).getMillis();
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("(发票推送)发票{}, 校验发票状态用时{}毫秒......", invoiceId, millSeconds1);
            }

            //验证当前是否符合发票推送，FP_KJZT=2000
            if (!verifyResult) {

                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug("(发票推送)无效的状态, invoiceId:{}", invoiceId);
                }

                LOGGER.info("..................发票推送结束(无效状态)........................, invoiceId:{}", invoiceId);

            }else{

                // 获取与客户方交互的CA证书
                // final EShopCertificateBytesInfo certificate =
                // invUploadService.obtainEShopCAInfo(invoiceHeaderEntity.getTaxpayerIdentifyNo());
                //
                // if (certificate == null) {
                // LOGGER.error("获取CA密钥证书信息异常.");
                // throw new FpztException(FpztConstants.SCPDFFIAL,
                // "获取CA密钥证书信息异常.");
                // }

                final DateTime begin = now();

                // 发票推送
                result = invUploadService.pushInvoice(invoiceHeaderEntity, pushInvoiceQueueEntity, null);

                final Long millSeconds = new Duration(begin, now()).getMillis();
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug("(发票推送)发票{}, 发票推送总共耗时{}毫秒", invoiceId, millSeconds);
                }
                /*// 如果推送成功,放入mq中
                if (result) {
                    final SendPlatformQyeueEntity sendPlatformQyeueEntity = new SendPlatformQyeueEntity();
                    sendPlatformQyeueEntity.setInvoiceId(invoiceHeaderEntity.getInvoiceEntity().getInvoiceId());
                    sendPlatformQyeueEntity.setEshopCode(invoiceHeaderEntity.getInvoiceEntity().getEshopCode());
                    amqpTemplate.convertAndSend(sentToPlatformServerQueue, sendPlatformQyeueEntity);
                }*/
                
                /*
                 * TODO 新需求：发票生成后先上传大象再下发给京东再上传给航信51队列中
            	 * 20171009-FWH
                 */
                // 如果推送成功,放入上传航信51MQ中
                if (result) {
                    final SendPlatformQyeueEntity sendPlatformQyeueEntity = new SendPlatformQyeueEntity();
                    sendPlatformQyeueEntity.setInvoiceId(invoiceHeaderEntity.getInvoiceEntity().getInvoiceId());
                    sendPlatformQyeueEntity.setEshopCode(invoiceHeaderEntity.getInvoiceEntity().getEshopCode());
                    amqpTemplate.convertAndSend(send51serverQueue, sendPlatformQyeueEntity);
//                    amqpTemplate.convertAndSend(send51serverQueue, invoiceHeaderEntity);//将整个实体放入队列中
                    if (LOGGER.isDebugEnabled()) {
                        LOGGER.debug("(发票推送)发票{}, 发票推送航信51队列中,数据是:{}....", invoiceId, invoiceHeaderEntity.toString());
                    }
                }
                
                
                // 如果推送成功,根据配置文件来判断是否删除pdf
                String pdfBl = SystemConfig.PDF_DELETE;
                if ((result.booleanValue()) && ("Y".equals(pdfBl))) {
                    String pdfpathString = invoiceHeaderEntity.getPdfPath();
                    try {
                        new File(pdfpathString).delete();
                    } catch (Exception e) {
                        LOGGER.error("删除本地pdf文件时出错，路径为：" + pdfpathString + ";", e);
                    }
                }
                //TODO PDF文件删除由数据库来控制--FWH-20171121
               /* String nsrsbh = invoiceHeaderEntity.getInvoiceEntity().getTaxpayerIdentifyNo();
                String queryPdfSwitchConfig = invUploadService.queryPdfSwitchConfig(nsrsbh);
                if (result && "Y".equals(queryPdfSwitchConfig)) {
                    String pdfpathString = invoiceHeaderEntity.getPdfPath();
                    try {
                        new File(pdfpathString).delete();
                    } catch (Exception e) {
                        LOGGER.error("删除本地pdf文件时出错，路径为：" + pdfpathString + ";", e);
                    }
                }*/
            }

            LOGGER.info(".................发票推送结束(成功)........................, invoiceId:{}", invoiceId);

        } catch (FpztException e) {
//            LOGGER.error("..................发票推送结束(失败)........................, invoiceId:{}", invoiceId);
            LOGGER.error("(发票推送)发票推送失败,invoiceId:{},异常为:{}", invoiceId, e);
            invoiceDataAccessManagerService.updateInvoicePushState(invoiceId, SystemConfig.INVOICE_PUSH_STATE_FAILURE);
        } catch (Exception e) {
//            LOGGER.error("..................发票推送结束(失败)........................, invoiceId:{}", invoiceId);
            LOGGER.error("(发票推送)发票推送时业务处理异常,invoiceId:{},异常为:{}",invoiceId, e);
            invoiceDataAccessManagerService.updateInvoicePushState(invoiceId, SystemConfig.INVOICE_PUSH_STATE_FAILURE);
        }
    }

    private void wrapInvoiceHeaderEntity(PushInvoiceQueueEntity sourceObj, InvoiceHeaderEntity targetObj) {
        copyProperties(sourceObj, targetObj);
        final InvoiceEntity invoiceEntity = nullInvoiceEntity();
        wrapInvoiceEntity(sourceObj, invoiceEntity);
        targetObj.setInvoiceEntity(invoiceEntity);
    }

    private void wrapInvoiceEntity(PushInvoiceQueueEntity sourceObj, InvoiceEntity targetObj) {
        copyProperties(sourceObj, targetObj);
    }

}