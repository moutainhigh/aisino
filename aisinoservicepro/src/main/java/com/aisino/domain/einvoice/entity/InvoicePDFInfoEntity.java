package com.aisino.domain.einvoice.entity;

import com.aisino.domain.AbstractBaseDomain;

import java.sql.Timestamp;

import static com.google.common.base.MoreObjects.toStringHelper;

/**
 * 发票PDF信息实体(下载发票PDF时使用)
 * Created by Schiffer.huang on 2014/11/11.
 * see 'com.aisino.protocol.bean.RESPONSE_COMMON_FPXXXZ'
 */
public class InvoicePDFInfoEntity extends AbstractBaseDomain {

    private static final long serialVersionUID = -6750287236767067322L;

    /**
     * 发票ID
     */
    private Long invoiceId;

    /**
     * 发票请求唯一流水号(FPQQLSH)
     */
    private String invoiceRequestSerialNo;

    /**
     * 订单号(DDH)
     */
    private String orderNo;

    /**
     * 开票流水号(KPLSH,发票代码+发票号码)
     */
    private String invoiceSerialNo;

    /**
     * 税控码, 防伪码(SKM,FWM)
     */
    private String fiscalCode;

    /**
     * 二维码(EWM)
     */
    private String twoDimensionCode;

    /**
     * 发票种类代码(FPZL_DM)
     */
    private String invoiceKindCode;

    /**
     * 发票代码(FP_DM)
     */
    private String invoiceCode;

    /**
     * 发票号码(发票起号FP_HM)
     */
    private String invoiceNo;

    /**
     * 开票日期(KPRQ)
     */
    private String billingDate;

    /**
     * 开票类型 (KPLX,1正票 2红票)
     */
    private Long billingType;

    /**
     * 发票PDF文件保存路径(PDFPATCH)
     */
    private String pdfPath;

    /**
     * 操作代码(CZDM)
     */
    private String operatorNo;

    /**
     * 税纳人识别号(NSRSBH)
     */
    private String taxpayerIdentifyNo;

    /**
     * 开票方名称(NSRMC)
     */
    private String taxpayer;

    /**
     * 电商平台编码(dsptbm)
     */
    private String eshopCode;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Long invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getInvoiceRequestSerialNo() {
        return invoiceRequestSerialNo;
    }

    public void setInvoiceRequestSerialNo(String invoiceRequestSerialNo) {
        this.invoiceRequestSerialNo = invoiceRequestSerialNo;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getInvoiceSerialNo() {
        return invoiceSerialNo;
    }

    public void setInvoiceSerialNo(String invoiceSerialNo) {
        this.invoiceSerialNo = invoiceSerialNo;
    }

    public String getFiscalCode() {
        return fiscalCode;
    }

    public void setFiscalCode(String fiscalCode) {
        this.fiscalCode = fiscalCode;
    }

    public String getTwoDimensionCode() {
        return twoDimensionCode;
    }

    public void setTwoDimensionCode(String twoDimensionCode) {
        this.twoDimensionCode = twoDimensionCode;
    }

    public String getInvoiceKindCode() {
        return invoiceKindCode;
    }

    public void setInvoiceKindCode(String invoiceKindCode) {
        this.invoiceKindCode = invoiceKindCode;
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


    public String getBillingDate() {
        return billingDate;
    }

    public void setBillingDate(String billingDate) {
        this.billingDate = billingDate;
    }

    public Long getBillingType() {
        return billingType;
    }

    public void setBillingType(Long billingType) {
        this.billingType = billingType;
    }

    public String getPdfPath() {
        return pdfPath;
    }

    public void setPdfPath(String pdfPath) {
        this.pdfPath = pdfPath;
    }

    public String getOperatorNo() {
        return operatorNo;
    }

    public void setOperatorNo(String operatorNo) {
        this.operatorNo = operatorNo;
    }

    public String getTaxpayerIdentifyNo() {
        return taxpayerIdentifyNo;
    }

    public void setTaxpayerIdentifyNo(String taxpayerIdentifyNo) {
        this.taxpayerIdentifyNo = taxpayerIdentifyNo;
    }

    public String getTaxpayer() {
        return taxpayer;
    }

    public void setTaxpayer(String taxpayer) {
        this.taxpayer = taxpayer;
    }

    public String getEshopCode() {
        return eshopCode;
    }

    public void setEshopCode(String eshopCode) {
        this.eshopCode = eshopCode;
    }

    @Override
    public Boolean isNullObject() {
        return  this.invoiceId == null;
    }

    @Override
    public String toString() {
        return toStringHelper(this)
                .add("invoiceId", invoiceId)
                .add("invoiceRequestSerialNo", invoiceRequestSerialNo)
                .add("orderNo", orderNo)
                .add("invoiceSerialNo", invoiceSerialNo)
                .add("fiscalCode", fiscalCode)
                .add("twoDimensionCode", twoDimensionCode)
                .add("invoiceKindCode", invoiceKindCode)
                .add("invoiceCode", invoiceCode)
                .add("invoiceNo", invoiceNo)
                .add("billingDate", billingDate)
                .add("billingType", billingType)
                .add("pdfPath", pdfPath)
                .add("operatorNo", operatorNo)
                .add("taxpayerIdentifyNo", taxpayerIdentifyNo)
                .add("taxpayer", taxpayer)
                .add("eshopCode", eshopCode)
                .toString();
    }
}
