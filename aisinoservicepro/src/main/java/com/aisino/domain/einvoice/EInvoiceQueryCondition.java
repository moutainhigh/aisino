package com.aisino.domain.einvoice;

import java.util.List;

import static com.google.common.base.MoreObjects.toStringHelper;
import static com.google.common.base.Strings.emptyToNull;

/**
 * Created by Bourne.Lv on 2014/09/04.
 * <p/>
 * 发票查询条件封装实体
 */
public final class EInvoiceQueryCondition {

    /**
     * 51平台编码
     */
    private String platformCode;

    /**
     * 电商平台编码
     */
    private String eshopCode;

    /*
     *纳税人识别号
     */
    private String taxpayerIdentifyNo;

    /**
     * 发票ID
     */
    private Long invoiceId;

    /**
     * 四个定时器的获取发票条数
     */
    private Long amount;

    /**
     * 发票状态
     */
    private String invoiceStatus;

    /**
     * 发票推送状态
     */
    private String invoicePushStatus;

    /**
     * 发票上传状态
     */
    private String invoiceUploadStatus;

    /**
     * 发票ID list
     */
    private List<Long> invoiceIds;

    /**
     * 错误次数
     */
    private Long errorCount;

    /**
     * 发票请求唯一流水号
     */
    private String invoiceRequestSerialNo;

    /**
     * 电商平台注册码
     */
    private String registerNo;

    /**
     * 纳税人授权码（clinet）
     */
    private String authCode;

    /**
     * 是否可用
     */
    private String available;

    public String getInvoicePushStatus() {
        return emptyToNull(invoicePushStatus);
    }

    public void setInvoicePushStatus(String invoicePushStatus) {
        this.invoicePushStatus = invoicePushStatus;
    }

    public String getPlatformCode() {
        return emptyToNull(platformCode);
    }

    public void setPlatformCode(String platformCode) {
        this.platformCode = platformCode;
    }

    public String getTaxpayerIdentifyNo() {
        return emptyToNull(taxpayerIdentifyNo);
    }

    public void setTaxpayerIdentifyNo(String taxpayerIdentifyNo) {
        this.taxpayerIdentifyNo = taxpayerIdentifyNo;
    }

    public Long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Long invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getEshopCode() {
        return emptyToNull(eshopCode);
    }

    public void setEshopCode(String eshopCode) {
        this.eshopCode = eshopCode;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getInvoiceStatus() {
        return emptyToNull(invoiceStatus);
    }

    public void setInvoiceStatus(String invoiceStatus) {
        this.invoiceStatus = invoiceStatus;
    }

    public String getInvoiceUploadStatus() {
        return invoiceUploadStatus;
    }

    public void setInvoiceUploadStatus(String invoiceUploadStatus) {
        this.invoiceUploadStatus = invoiceUploadStatus;
    }

    public List<Long> getInvoiceIds() {
        return invoiceIds;
    }

    public void setInvoiceIds(List<Long> invoiceIds) {
        this.invoiceIds = invoiceIds;
    }

    public Long getErrorCount() {
        return errorCount;
    }

    public void setErrorCount(Long errorCount) {
        this.errorCount = errorCount;
    }

    public String getInvoiceRequestSerialNo() {
        return emptyToNull(invoiceRequestSerialNo);
    }

    public void setInvoiceRequestSerialNo(String invoiceRequestSerialNo) {
        this.invoiceRequestSerialNo = invoiceRequestSerialNo;
    }

    public String getRegisterNo() {
        return emptyToNull(registerNo);
    }

    public void setRegisterNo(String registerNo) {
        this.registerNo = registerNo;
    }

    public String getAuthCode() {
        return emptyToNull(authCode);
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available;
    }

    @Override
    public String toString() {
        return toStringHelper(this).
                add("platformCode", platformCode).
                add("eshopCode", eshopCode).
                add("taxpayerIdentifyNo", taxpayerIdentifyNo).
                add("invoiceId", invoiceId).
                add("amount", amount).
                add("invoiceStatus", invoiceStatus).
                add("invoicePushStatus", invoicePushStatus).
                add("invoiceUploadStatus", invoiceUploadStatus).
                add("invoiceIds", invoiceIds).
                add("errorCount", errorCount).
                add("invoiceRequestSerialNo", invoiceRequestSerialNo).
                add("registerNo", registerNo).
                add("authCode", authCode).
                add("available", available).
                toString();
    }
}
