package com.aisino.domain.einvoice.entity;

import com.aisino.domain.AbstractBaseDomain;

import static com.google.common.base.MoreObjects.toStringHelper;

/**
 * Created by Tommy.wang on 2014/9/17.
 * <p/>
 * 发票结存信息实体(FP_NSRJC)
 *
 * @see com.aisino.domain.AbstractBaseDomain
 */
public final class InvoiceBalance extends AbstractBaseDomain {

    private static final long serialVersionUID = 5525606819994855296L;

    /**
     * 税纳人识别号(NSRSBH)
     */
    private String taxpayerIdentifyNo;

    /**
     * 开票方电子档案号\纳税人电子档案号(NSRDZDAH)
     */
    private String taxpayerEDNo;

    /**
     * 电商平台编码(dsptbm)
     */
    private String eshopCode;

    /**
     * 发票代码(FP_DM)
     */
    private String invoiceCode;

    /**
     * 发票号码(发票起号FP_HM)
     */
    private String invoiceNo;

    /**
     * 发票止号(FPZH)
     */
    private String invoiceEndNo;

    /**
     * 发票种类代码(FPZL_DM)
     */
    private String invoiceKindCode;

    /**
     * 份数(FS)
     */
    private String invoiceCopies;

    /**
     * 税务机构代码(SWJG_DM)
     */
    private String taxOfficeRegCode;

    /**
     * 发售日期（FSRQ）
     */
    private String releaseDate;

    /**
     * 纳税人税务机关代码（NSRSWJGDM）
     */
    private String taxpayerTaxOfficeRegCode;

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

    public String getEshopCode() {
        return eshopCode;
    }

    public void setEshopCode(String eshopCode) {
        this.eshopCode = eshopCode;
    }

    public String getInvoiceCode() {
        return invoiceCode;
    }

    public void setInvoiceCode(String invoiceCode) {
        this.invoiceCode = invoiceCode;
    }

    public String getInvoiceKindCode() {
        return invoiceKindCode;
    }

    public void setInvoiceKindCode(String invoiceKindCode) {
        this.invoiceKindCode = invoiceKindCode;
    }

    public String getInvoiceCopies() {
        return invoiceCopies;
    }

    public void setInvoiceCopies(String invoiceCopies) {
        this.invoiceCopies = invoiceCopies;
    }

    public String getTaxOfficeRegCode() {
        return taxOfficeRegCode;
    }

    public void setTaxOfficeRegCode(String taxOfficeRegCode) {
        this.taxOfficeRegCode = taxOfficeRegCode;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getTaxpayerTaxOfficeRegCode() {
        return taxpayerTaxOfficeRegCode;
    }

    public void setTaxpayerTaxOfficeRegCode(String taxpayerTaxOfficeRegCode) {
        this.taxpayerTaxOfficeRegCode = taxpayerTaxOfficeRegCode;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public String getInvoiceEndNo() {
        return invoiceEndNo;
    }

    public void setInvoiceEndNo(String invoiceEndNo) {
        this.invoiceEndNo = invoiceEndNo;
    }

    @Override
    public Boolean isNullObject() {
        return getTaxpayerIdentifyNo() == null;
    }

    @Override
    public String toString() {
        return toStringHelper(this)
                .add("taxpayerIdentifyNo", taxpayerIdentifyNo)
                .add("taxpayerEDNo", taxpayerEDNo)
                .add("eshopCode", eshopCode)
                .add("invoiceCode", invoiceCode)
                .add("invoiceNo", invoiceNo)
                .add("invoiceEndNo", invoiceEndNo)
                .add("invoiceKindCode", invoiceKindCode)
                .add("invoiceCopies", invoiceCopies)
                .add("taxOfficeRegCode", taxOfficeRegCode)
                .add("releaseDate", releaseDate)
                .add("taxpayerTaxOfficeRegCode", taxpayerTaxOfficeRegCode)
                .toString();
    }
}
