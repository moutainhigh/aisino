package com.aisino.domain.einvoice.entity;

import com.aisino.domain.AbstractBaseDomain;

import static com.google.common.base.MoreObjects.toStringHelper;

/**
 * Created by Schiffer.huang on 2014/09/29.
 * 发票冲红信息实体(fp_chfpsqd)
 *
 * @see com.aisino.domain.AbstractBaseDomain
 */
public final class InvoiceRedEntity extends AbstractBaseDomain {

    private static final long serialVersionUID = -775545995288340156L;

    /*
     *发票ID(KJID)
     */
    private Long invoiceId;

    /*
     *电商平台编码(DSPTBM)
     */
    private String eshopCode;

    /*
     *纳税人识别号(NSRSBH)
     */
    private String taxpayerIdentifyNo;

    /*
     *纳税人电子档案号(NSRDZDAH)
     */
    private String taxpayerEDNo;

    /*
     *发票代码(FP_DM)
     */
    private String invoiceCode;

    /*
     *发票号码(FP_HM)
     */
    private String invoiceNo;

    /*
     *开票类型(KPLX,1正票 2红票)
     */
    private Long billingType;

    /*
     *冲红金额(CHJE)
     */
    private Double redAmount;

    /*
     *冲红原因(CHYY)
     */
    private String redInvoiceReason;

    /*
     *受票方电话(SPFDH)
     */
    private String recipientPhone;

    /*
     *受票方邮箱(SPFYX)
     */
    private String recipientEmail;

    /*
     *申请流水号(SQLSH)
     */
    private String applySerialNo;

    /*
     *处理标志(RETURNCODE)
     */
    private String returnCode;

    /*
     *操作代码(CZDM)
     */
    private String operatorNo;

    /*
     *处理结果信息(RETURNMESSAGE)
     */
    private String returnMessage;

    public Long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Long invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getEshopCode() {
        return eshopCode;
    }

    public void setEshopCode(String eshopCode) {
        this.eshopCode = eshopCode;
    }

    public String getTaxpayerIdentifyNo() {
        return taxpayerIdentifyNo;
    }

    public void setTaxpayerIdentifyNo(String taxpayerIdentifyNo) {
        this.taxpayerIdentifyNo = taxpayerIdentifyNo;
    }

    public String getTaxpayerEDNo() {
        return taxpayerEDNo;
    }

    public void setTaxpayerEDNo(String taxpayerEDNo) {
        this.taxpayerEDNo = taxpayerEDNo;
    }

    public String getInvoiceCode() {
        return invoiceCode;
    }

    public void setInvoiceCode(String invoiceCode) {
        this.invoiceCode = invoiceCode;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public Long getBillingType() {
        return billingType;
    }

    public void setBillingType(Long billingType) {
        this.billingType = billingType;
    }

    public Double getRedAmount() {
        return redAmount;
    }

    public void setRedAmount(Double redAmount) {
        this.redAmount = redAmount;
    }

    public String getRedInvoiceReason() {
        return redInvoiceReason;
    }

    public void setRedInvoiceReason(String redInvoiceReason) {
        this.redInvoiceReason = redInvoiceReason;
    }

    public String getRecipientPhone() {
        return recipientPhone;
    }

    public void setRecipientPhone(String recipientPhone) {
        this.recipientPhone = recipientPhone;
    }

    public String getRecipientEmail() {
        return recipientEmail;
    }

    public void setRecipientEmail(String recipientEmail) {
        this.recipientEmail = recipientEmail;
    }

    public String getApplySerialNo() {
        return applySerialNo;
    }

    public void setApplySerialNo(String applySerialNo) {
        this.applySerialNo = applySerialNo;
    }

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public String getOperatorNo() {
        return operatorNo;
    }

    public void setOperatorNo(String operatorNo) {
        this.operatorNo = operatorNo;
    }

    public String getReturnMessage() {
        return returnMessage;
    }

    public void setReturnMessage(String returnMessage) {
        this.returnMessage = returnMessage;
    }

    @Override
    public Boolean isNullObject() {
        return invoiceId == null;
    }

    @Override
    public String toString() {
        return toStringHelper(this)
                .add("invoiceId", invoiceId)
                .add("eshopCode", eshopCode)
                .add("taxpayerIdentifyNo", taxpayerIdentifyNo)
                .add("taxpayerEDNo", taxpayerEDNo)
                .add("invoiceCode", invoiceCode)
                .add("invoiceNo", invoiceNo)
                .add("billingType", billingType)
                .add("redAmount", redAmount)
                .add("redInvoiceReason", redInvoiceReason)
                .add("recipientPhone", recipientPhone)
                .add("recipientEmail", recipientEmail)
                .add("applySerialNo", applySerialNo)
                .add("returnCode", returnCode)
                .add("operatorNo", operatorNo)
                .add("returnMessage", returnMessage)
                .toString();
    }
}
