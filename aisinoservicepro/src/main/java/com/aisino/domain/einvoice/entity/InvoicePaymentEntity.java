package com.aisino.domain.einvoice.entity;

import com.aisino.domain.AbstractBaseDomain;

import static com.google.common.base.MoreObjects.toStringHelper;

/**
 * Created by Bourne.Lv on 2014/09/09.
 * <p/>
 * 发票支付信息实体(fp_zfxx)
 *
 * @see com.aisino.domain.AbstractBaseDomain
 */
public final class InvoicePaymentEntity extends AbstractBaseDomain {

    private static final long serialVersionUID = 649165404687174934L;

    /**
     * 发票ID
     */
    private Long invoiceId;

    /**
     * 发票代码(FP_DM)
     */
    private String invoiceCode;

    /**
     * 发票号码(FP_HM)
     */
    private String invoiceNo;

    /**
     * 电商平台编码(DSPTBM)
     */
    private String eshopCode;

    /**
     * 支付方式(ZFFS)
     */
    private String paymentWay;

    /**
     * 支付平台(ZFPT)
     */
    private String paymentPlatform;

    /**
     * 支付流水号(ZFLSH)
     */
    private String paymentSerialNo;


    public Long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Long invoiceId) {
        this.invoiceId = invoiceId;
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

    public String getEshopCode() {
        return eshopCode;
    }

    public void setEshopCode(String eshopCode) {
        this.eshopCode = eshopCode;
    }

    public String getPaymentWay() {
        return paymentWay;
    }

    public void setPaymentWay(String paymentWay) {
        this.paymentWay = paymentWay;
    }

    public String getPaymentPlatform() {
        return paymentPlatform;
    }

    public void setPaymentPlatform(String paymentPlatform) {
        this.paymentPlatform = paymentPlatform;
    }

    public String getPaymentSerialNo() {
        return paymentSerialNo;
    }

    public void setPaymentSerialNo(String paymentSerialNo) {
        this.paymentSerialNo = paymentSerialNo;
    }

    @Override
    public Boolean isNullObject() {
        return invoiceId == null;
    }

    @Override
    public String toString() {
        return toStringHelper(this)
                .add("invoiceId", invoiceId)
                .add("invoiceCode", invoiceCode)
                .add("invoiceNo", invoiceNo)
                .add("eshopCode", eshopCode)
                .add("paymentWay", paymentWay)
                .add("paymentPlatform", paymentPlatform)
                .add("paymentSerialNo", paymentSerialNo)
                .toString();
    }
}
