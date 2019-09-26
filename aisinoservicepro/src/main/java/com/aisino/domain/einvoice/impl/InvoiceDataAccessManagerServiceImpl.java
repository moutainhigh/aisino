package com.aisino.domain.einvoice.impl;

import static com.aisino.domain.SystemConfig.SL_CA_PASSWORD;
import static com.aisino.domain.SystemConfig.SL_CA_PRIVATE_KEY;
import static com.aisino.domain.SystemConfig.SL_CA_PUBLIC_KEY;
import static com.aisino.domain.SystemConfig.SL_CA_TRUST;
import static com.aisino.domain.constantenum.InvoiceStatusEnum.INV_CREATE_SUCCESS;
import static com.aisino.domain.einvoice.EInvoiceNullObjects.nullCertificateEntity;
import static com.aisino.domain.einvoice.EInvoiceNullObjects.nullEShopInfo;
import static com.aisino.domain.einvoice.EInvoiceNullObjects.nullInvoiceDetailEntity;
import static com.aisino.domain.einvoice.EInvoiceNullObjects.nullInvoiceEntity;
import static com.aisino.domain.einvoice.EInvoiceNullObjects.nullInvoiceLogisticsEntity;
import static com.aisino.domain.einvoice.EInvoiceNullObjects.nullInvoiceOrderEntity;
import static com.aisino.domain.einvoice.EInvoiceNullObjects.nullInvoicePaymentEntity;
import static com.aisino.domain.einvoice.EInvoiceNullObjects.nullTaxpayerRouteEntity;
import static com.aisino.domain.einvoice.EInvoiceNullObjects.nullTaxcodeRouteEntity;
import static com.google.common.base.MoreObjects.firstNonNull;
import static com.google.common.base.Strings.isNullOrEmpty;
import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.beans.BeanUtils.copyProperties;
import static org.springframework.util.Assert.notNull;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.aisino.domain.AbstractBaseService;
import com.aisino.domain.SystemConfig;
import com.aisino.domain.einvoice.EInvoiceQueryCondition;
import com.aisino.domain.einvoice.InvoiceDataAccessManagerService;
import com.aisino.domain.einvoice.entity.CertificateEntity;
import com.aisino.domain.einvoice.entity.EShopInfo;
import com.aisino.domain.einvoice.entity.InvoiceBalance;
import com.aisino.domain.einvoice.entity.InvoiceDetailEntity;
import com.aisino.domain.einvoice.entity.InvoiceEntity;
import com.aisino.domain.einvoice.entity.InvoiceLogisticsEntity;
import com.aisino.domain.einvoice.entity.InvoiceOrderEntity;
import com.aisino.domain.einvoice.entity.InvoicePaymentEntity;
import com.aisino.domain.einvoice.entity.TaxcodeRouteEntity;
import com.aisino.domain.einvoice.entity.TaxpayerRouteEntity;
import com.aisino.domain.einvoice.repository.EInvoiceRepository;

/**
 * Created by Bourne.Lv on 2014/09/04.
 * <p/>
 * 发票相关业务数据库操作服务接口实现类
 * 
 * @see com.aisino.domain.AbstractBaseService
 * @see com.aisino.domain.BaseService
 * @see com.aisino.domain.einvoice.InvoiceDataAccessManagerService
 */
@Transactional(propagation = Propagation.REQUIRED)
public final class InvoiceDataAccessManagerServiceImpl extends AbstractBaseService implements InvoiceDataAccessManagerService {

    private static final Logger LOGGER = getLogger(InvoiceDataAccessManagerServiceImpl.class);

    private EInvoiceRepository repository;

    public void setRepository(EInvoiceRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional(readOnly = true)
    public Boolean verifyInvoiceStatus(String verifyType, Long invoiceId) {

        notNull(verifyType, "verifyType required.");
        notNull(invoiceId, "invoiceId required.");

        final String invoiceState = repository.queryInvoiceStatusById(invoiceId);

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("verifyInvoiceStatus method: InvoiceId: {}, InvoiceState: {}", invoiceId, invoiceState);
        }

        return verifyType.equals(invoiceState);
    }

    @Override
    @Transactional(readOnly = true)
    public InvoiceEntity getInvoiceEntity(Long invoiceId) {

        notNull(invoiceId, "invoiceId required.");

        return firstNonNull(repository.getInvoiceEntityById(invoiceId), nullInvoiceEntity());
    }

    @Override
    public Boolean saveInvoiceEntityBalanceInfo(InvoiceEntity invoiceEntity) {

        notNull(invoiceEntity.getInvoiceId(), "invoiceId required.");
        notNull(invoiceEntity.getInvoiceCode(), "invoiceCode required.");
        notNull(invoiceEntity.getInvoiceNo(), "invoiceNo required.");
        notNull(invoiceEntity.getInfoDate(), "infoDate required.");
        // notNull(invoiceEntity.getInvoiceKindCode(),
        // "invoiceKindCode required.");
        // notNull(invoiceEntity.getInfoAmount(), "infoAmount required.");
        // notNull(invoiceEntity.getInfoTaxAmount(), "infoTaxAmount required.");
        // notNull(invoiceEntity.getInfoMonth(), "infoMonth required.");
        // notNull(invoiceEntity.getGoodsListFlag(), "goodsListFlag required.");
        // notNull(invoiceEntity.getRetCode(), "retCode required.");
        // notNull(invoiceEntity.getCiphertext(), "ciphertext required.");
        // notNull(invoiceEntity.getCheckCode(), "checkCode required.");
        // notNull(invoiceEntity.getInfoInvoicer(), "infoInvoicer required.");

        final Boolean firstStep = repository.updateInvoiceEntityBalanceInfo(invoiceEntity);

        if (firstStep) {
            // 用于查找丢票，更新发票ID
            repository.updateTLost(String.valueOf(invoiceEntity.getInvoiceId()), invoiceEntity.getInvoiceNo());
        }

        return firstStep;
    }

    @Override
    public Boolean saveInvoiceErrorInfo(Long invoiceId, String invoiceStatus, String errorMsg) {

        notNull(invoiceId, "invoiceId required.");
        notNull(invoiceStatus, "invoiceStatus required.");

        final InvoiceEntity invoiceEntity = nullInvoiceEntity();
        invoiceEntity.setInvoiceId(invoiceId);
        invoiceEntity.setInvoiceStatus(invoiceStatus);
        invoiceEntity.setErrorMsg(errorMsg);

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("updateInvoiceErrorInfo method, InvoiceEntity: {}", invoiceEntity.toString());
        }

        return repository.updateInvoiceErrorInfo(invoiceEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public Boolean verifyIssueInvoice(InvoiceEntity invoiceEntity) {

        notNull(invoiceEntity.getInvoiceId(), "invoiceId required.");

        // 查询签章ID
        final String signatureId = repository.querySignatureByInvoiceId(invoiceEntity.getInvoiceId());

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("verifyIssueInvoice method, SignatureId: {}", signatureId);
        }

        return !isNullOrEmpty(signatureId);
    }

    @Override
    public Boolean generatorInvoice(InvoiceEntity invoiceEntity) {

        notNull(invoiceEntity.getInvoiceId(), "invoiceId required.");
        notNull(invoiceEntity.getTaxpayerIdentifyNo(), "taxpayerIdentifyNo required.");
//        notNull(invoiceEntity.getInvoiceKindCode(), "invoiceKindCode required.");
        notNull(invoiceEntity.getInvoiceCode(), "invoiceCode required.");
        notNull(invoiceEntity.getInvoiceNo(), "invoiceNo required.");
//        notNull(invoiceEntity.getFiscalCode(), "fiscalCode required.");
        notNull(invoiceEntity.getTwoDimensionCode(), "twoDimensionCode required.");
//        notNull(invoiceEntity.getPdfPath(), "pdfPath required.");
        notNull(invoiceEntity.getBillingDate(), "billingDate required.");

        //前面已经判断过一次是否生成pdf,这里只处理路径
        if ("Y".equals(SystemConfig.pdfDisjunctor)) {
            notNull(invoiceEntity.getPdfPath(), "pdfPath required.");
        }else {
            invoiceEntity.setPdfPath("");
        }

        // 设置发票状态: 生成发票成功
        invoiceEntity.setInvoiceStatus(INV_CREATE_SUCCESS.getParameterValue());

        // 设置开票流水号
        invoiceEntity.setInvoiceSerialNo(invoiceEntity.getInvoiceCode().concat(invoiceEntity.getInvoiceNo()));

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("generatorInvoice method, InvoiceEntity: {}", invoiceEntity);
        }

        // 更新开票过程中的FP_KJ表的相关字段
        final Boolean firstStep = repository.updateInvoiceInfoForGenerateInvoice(invoiceEntity);
        Boolean secondStep = Boolean.FALSE;

        if (firstStep) {

            // 更新订单信息
            InvoiceOrderEntity invoiceOrderEntity = nullInvoiceOrderEntity();
            copyProperties(invoiceEntity, invoiceOrderEntity);
            repository.updateInvoiceCodeAndInvoiceNo(invoiceOrderEntity);

            // 更新开具明细
            InvoiceDetailEntity invoiceDetailEntity = nullInvoiceDetailEntity();
            copyProperties(invoiceEntity, invoiceDetailEntity);
            final Boolean updateInvoiceDetailEntityStep = repository.updateInvoiceCodeAndInvoiceNo(invoiceDetailEntity);

            // 更新物流信息
            InvoiceLogisticsEntity invoiceLogisticsEntity = nullInvoiceLogisticsEntity();
            copyProperties(invoiceEntity, invoiceLogisticsEntity);
            repository.updateInvoiceCodeAndInvoiceNo(invoiceLogisticsEntity);

            // 更新支付信息
            InvoicePaymentEntity invoicePaymentEntity = nullInvoicePaymentEntity();
            copyProperties(invoiceEntity, invoicePaymentEntity);
            repository.updateInvoiceCodeAndInvoiceNo(invoicePaymentEntity);

            // 更新开票过程中的FP_KJ_LOG表的相关字段，操作次数归零(error_count重置为0)
            final Boolean updateInvoiceExtendStep = repository.updateInvoiceExtendInfoForGenerateInvoice(invoiceEntity);

            // 处理冲红
            handleCreditNote(invoiceEntity);

            // 用于查找丢票,更新flag=1
            repository.updateTLost(invoiceEntity.getInvoiceNo());

            secondStep = updateInvoiceExtendStep && updateInvoiceDetailEntityStep;

        }

        return firstStep && secondStep;
    }

    @Override
    @Transactional(readOnly = true)
    public EShopInfo queryEShopInfo(EInvoiceQueryCondition condition) {

        notNull(condition.getEshopCode(), "eshopCode required.");

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("queryEShopInfo method, EInvoiceQueryCondition: {}", condition.toString());
        }

        return firstNonNull(repository.queryEShopInfo(condition), nullEShopInfo());
    }

    @Override
    @Transactional(readOnly = true)
    public CertificateEntity getCertificateEntity(EInvoiceQueryCondition condition) {
        notNull(condition.getPlatformCode(), "platformCode required.");
        notNull(condition.getTaxpayerIdentifyNo(), "taxpayerIdentifyNo required.");
        notNull(condition.getAuthCode(), "authCode required.");
        notNull(condition.getRegisterNo(), "registerNo required.");

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("getCertificateEntity method, EInvoiceQueryCondition: {}", condition.toString());
        }

        return firstNonNull(repository.getCertificateEntity(condition), nullCertificateEntity());
    }

    @Override
    public CertificateEntity getPlatformCertificateEntity() {
        final CertificateEntity entity = new CertificateEntity();

        entity.setTrust(SL_CA_TRUST);
        entity.setPrivateKey(SL_CA_PRIVATE_KEY);
        entity.setPassword(SL_CA_PASSWORD);
        entity.setPublicKey(SL_CA_PUBLIC_KEY);

        return entity;
    }

    @Override
    public CertificateEntity getCertificateEntity(String taxpayerIdentifyNo) {
        notNull(taxpayerIdentifyNo, "taxpayerIdentifyNo required.");

        return firstNonNull(repository.getCertificateEntity(taxpayerIdentifyNo), nullCertificateEntity());
    }

    @Override
    @Transactional(readOnly = true)
    public List<InvoiceDetailEntity> queryInvoiceDetailInfo(EInvoiceQueryCondition condition) {

        notNull(condition.getInvoiceId(), "invoiceId required.");

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("queryInvoiceDetailInfo method, EInvoiceQueryCondition: {}", condition.toString());
        }

        return repository.queryInvoiceDetailInfo(condition);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Map<String, String>> queryFjh(Map<String, String> map) {

        notNull(map.get("yfp_dm"), "原发票代码 required.");
        notNull(map.get("yfp_hm"), "原发票号码 required.");

        return repository.queryFjh(map);
    }

    @Override
    @Transactional(readOnly = true)
    public String queryFpqqlsh() {
        return repository.queryFpqqlsh();
    }

    @Override
    public void updateFpqqlsh() {
        repository.updateFpqqlsh();
    }

    @Override
    @Transactional(readOnly = true)
    public InvoiceBalance queryInvoiceKindCodeInfo(EInvoiceQueryCondition condition) {

        notNull(condition.getTaxpayerIdentifyNo(), "taxpayerIdentifyNo required.");

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("queryInvoiceKindCodeInfo method, EInvoiceQueryCondition: {}", condition.toString());
        }

        return repository.queryInvoiceKindCodeInfo(condition);
    }

    @Override
    @Transactional(readOnly = true)
    public TaxpayerRouteEntity queryTaxpayerRouteInfo(EInvoiceQueryCondition condition) {
        notNull(condition.getTaxpayerIdentifyNo(), "taxpayerIdentifyNo required.");

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("queryTaxpayerRouteInfo method, EInvoiceQueryCondition: {}", condition.toString());
        }

        return firstNonNull(repository.queryTaxpayerRouteInfo(condition), nullTaxpayerRouteEntity());
    }
    
    @Override
    @Transactional(readOnly = true)
    public TaxcodeRouteEntity queryTaxcodeRouteInfo(String nsrsbh) {
        notNull(nsrsbh, "taxpayerIdentifyNo required.");

        return firstNonNull(repository.queryTaxcodeRouteInfo(nsrsbh), nullTaxcodeRouteEntity());
    }

    @Override
    public Boolean saveTaxpayerRoute(TaxpayerRouteEntity taxpayerRouteEntity) {
        notNull(taxpayerRouteEntity.getTaxpayerIdentifyNo(), "taxpayerIdentifyNo required.");
        notNull(taxpayerRouteEntity.getUrl(), "url required.");

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("saveTaxpayerRoute method, TaxpayerRouteEntity: {}", taxpayerRouteEntity.toString());
        }

        return repository.saveTaxpayerRoute(taxpayerRouteEntity);
    }

    @Override
    public Boolean deleteTaxpayerRoute(Long id) {
        notNull(id, "id required.");

        return repository.deleteTaxpayerRoute(id);
    }

    @Override
    public Boolean enableTaxpayerRouteAvailable(TaxpayerRouteEntity taxpayerRouteEntity) {
        notNull(taxpayerRouteEntity.getAvailable(), "available required.");
        notNull(taxpayerRouteEntity.getId(), "id required.");

        return repository.enableTaxpayerRouteAvailable(taxpayerRouteEntity);
    }

    @Override
    public Boolean updateInvoicePushState(Long invoiceId, String pushState) {
        notNull(invoiceId, "invoiceId required.");
        notNull(pushState, "pushState required.");

        return repository.updateInvoicePushState(invoiceId, pushState);
    }

    @Override
    public Boolean updateInvoiceTaxCodeState(Long invoiceId, String taxcodeState) {
        notNull(invoiceId, "invoiceId required.");
        notNull(taxcodeState, "taxcodeState required.");

        return repository.updateInvoiceTaxCodeState(invoiceId, taxcodeState);
    }
    
    @Override
    public Boolean updateInvoiceBillingType(Long invoiceId, String billingType) {
        notNull(invoiceId, "invoiceId required.");
        notNull(billingType, "billingType required.");
        
       return repository.updateInvoiceBillingType(invoiceId, billingType);
        
    }
    @Override
    public String getInvoicePdfPath(String fiscalCode) {
        notNull(fiscalCode, "fiscalCode required.");

        return repository.getInvoicePdfPath(fiscalCode);
    }

    /**
     * 处理红票业务
     * 
     * @param invoiceEntity
     *            红票实体
     * @return 执行是否成功
     */
    private Boolean handleCreditNote(InvoiceEntity invoiceEntity) {

        Boolean result = Boolean.FALSE;

        notNull(invoiceEntity.getBillingType(), "billingType required.");
        notNull(invoiceEntity.getInvoiceCode(), "invoiceCode required.");
        notNull(invoiceEntity.getInvoiceNo(), "invoiceNo required.");
        notNull(invoiceEntity.getTaxpayerIdentifyNo(), "taxpayerIdentifyNo required.");

        if (SystemConfig.creditNote.equals(invoiceEntity.getBillingType())) {

            notNull(invoiceEntity.getOldInvoiceCode(), "oldInvoiceCode required.");
            notNull(invoiceEntity.getOldInvoiceNo(), "oldInvoiceNo required.");

            // 更新原蓝票的冲红标志以及剩余可冲红金额
            invoiceEntity.setBillingType(SystemConfig.invoiceNote);

            LOGGER.info("Modify Remaining Invoice Note Amount , invoiceEntity  {}", invoiceEntity);
            result = repository.modifyRemainingInvoiceNoteAmount(invoiceEntity);

            if (result) {
                // 更新红票的"剩余可冲红金额"(归零) 以及 是否特殊冲红发票
                invoiceEntity.setBillingType(SystemConfig.creditNote);
                invoiceEntity.setSpecRedInvoiceFlag(SystemConfig.specRedInvoiceFlag);

                LOGGER.info("Modify Remaining Credit Note Amount , invoiceEntity  {}", invoiceEntity);
                result = repository.modifyRemainingCreditNoteAmount(invoiceEntity);
            }

        }

        return result;
    }

}
