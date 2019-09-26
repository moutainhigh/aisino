package com.aisino.domain.einvoice.entity;

import com.aisino.domain.AbstractBaseDomain;

import java.sql.Timestamp;

import static com.google.common.base.MoreObjects.toStringHelper;

/**
 * Created by Bourne.Lv on 2014/09/09.
 * <p/>
 * 发票物流信息实体(fp_wlxx)
 *
 * @see com.aisino.domain.AbstractBaseDomain
 */
public final class InvoiceLogisticsEntity extends AbstractBaseDomain {

    private static final long serialVersionUID = -8705423665938732157L;

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
     * 承运公司(CYGS)
     */
    private String logisticsCompany;

    /**
     * 物流单号(WLDH)
     */
    private String logisticsNo;

    /**
     * 送货地址(SHDZ)
     */
    private String deliveryAddress;

    /**
     * 送货时间(SHSJ)
     */
    private Timestamp deliveryTime;


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

    @Override
    public Boolean isNullObject() {
        return invoiceId == null;
    }

    public String getEshopCode() {
        return eshopCode;
    }

    public void setEshopCode(String eshopCode) {
        this.eshopCode = eshopCode;
    }

    public String getLogisticsCompany() {
        return logisticsCompany;
    }

    public void setLogisticsCompany(String logisticsCompany) {
        this.logisticsCompany = logisticsCompany;
    }

    public String getLogisticsNo() {
        return logisticsNo;
    }

    public void setLogisticsNo(String logisticsNo) {
        this.logisticsNo = logisticsNo;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public Timestamp getDeliveryTime() {
        return obtainValidTimestamp(deliveryTime);
    }

    public void setDeliveryTime(Timestamp deliveryTime) {
        this.deliveryTime = obtainValidTimestamp(deliveryTime);
    }


    @Override
    public String toString() {
        return toStringHelper(this)
                .add("invoiceId", invoiceId)
                .add("invoiceCode", invoiceCode)
                .add("invoiceNo", invoiceNo)
                .add("eshopCode", eshopCode)
                .add("logisticsCompany", logisticsCompany)
                .add("logisticsNo", logisticsNo)
                .add("deliveryAddress", deliveryAddress)
                .add("deliveryTime", deliveryTime)
                .toString();
    }
}
