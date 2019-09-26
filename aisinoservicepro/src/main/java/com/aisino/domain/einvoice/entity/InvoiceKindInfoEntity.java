package com.aisino.domain.einvoice.entity;

import com.aisino.domain.AbstractBaseDomain;

import static com.google.common.base.MoreObjects.toStringHelper;

/**
 * 电子发票票种信息实体(SL获取电商企业信息时使用)
 * Created by Schiffer.huang on 2014/11/12.
 * see 'com.aisino.protocol.bean.RESPONSE_COMMON_DSQYPZHDXX'
 */
public final class InvoiceKindInfoEntity extends AbstractBaseDomain {

    private static final long serialVersionUID = -8339032356506874620L;

    /*
    *纳税人识别号(NSRSBH)
    */
    private String taxpayerIdentifyNo;

    /*
     *纳税人电子档案号(NSRDZDAH)
    */
    private String taxpayerEDNo;

    /*
    *发票种类代码(FPZL_DM)
    */
    private String invoiceKindCode;

    /*
    *发票种类名称(FPZL_MC)
    */
    private String invoiceKind;

    /***
     * 每月购票最高数量(MYGPZGSL)
     * (按协议bean类型为String,Schema为int)
     */
    private String purchaseInvoiceLimited;

    /***
     * 每月最高持票量(MYZGCPL)
     * (按协议bean类型为String,Schema为int)
     */
    private String holdInvoiceLimited;

    /***
     * 开票最高限额(KPZGXE)
     * (按协议bean类型为String,Schema为int)
     */
    private String invoiceLimited;

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

    public String getInvoiceKindCode() {
        return invoiceKindCode;
    }

    public void setInvoiceKindCode(String invoiceKindCode) {
        this.invoiceKindCode = invoiceKindCode;
    }

    public String getInvoiceKind() {
        return invoiceKind;
    }

    public void setInvoiceKind(String invoiceKind) {
        this.invoiceKind = invoiceKind;
    }

    public String getPurchaseInvoiceLimited() {
        return purchaseInvoiceLimited;
    }

    public void setPurchaseInvoiceLimited(String purchaseInvoiceLimited) {
        this.purchaseInvoiceLimited = purchaseInvoiceLimited;
    }

    public String getHoldInvoiceLimited() {
        return holdInvoiceLimited;
    }

    public void setHoldInvoiceLimited(String holdInvoiceLimited) {
        this.holdInvoiceLimited = holdInvoiceLimited;
    }

    public String getInvoiceLimited() {
        return invoiceLimited;
    }

    public void setInvoiceLimited(String invoiceLimited) {
        this.invoiceLimited = invoiceLimited;
    }

    @Override
    public Boolean isNullObject() {
        return getId()==null;
    }

    @Override
    public String toString() {
        return toStringHelper(this)
                .add("taxpayerIdentifyNo", taxpayerIdentifyNo)
                .add("taxpayerEDNo", taxpayerEDNo)
                .add("invoiceKindCode", invoiceKindCode)
                .add("invoiceKind", invoiceKind)
                .add("purchaseInvoiceLimited", purchaseInvoiceLimited)
                .add("holdInvoiceLimited", holdInvoiceLimited)
                .add("invoiceLimited", invoiceLimited)
                .toString();
    }
}
