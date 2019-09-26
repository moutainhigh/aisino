package com.aisino.domain.einvoice.entity;

import com.aisino.domain.AbstractBaseDomain;
import com.google.common.base.MoreObjects;

import java.sql.Timestamp;

/**
 * Created by Bourne.Lv on 2014/09/09.
 * <p/>
 * 发票订单信息实体(fp_ddxx)
 *
 * @see com.aisino.domain.AbstractBaseDomain
 */
public final class InvoiceOrderEntity extends AbstractBaseDomain {

    private static final long serialVersionUID = -3980570796331058579L;

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
     * 订单号(DDH)
     */
    private String orderNo;

    /**
     * 订单时间(DDSJ)
     */
    private Timestamp orderDate;

    /*
     *电商平台编码(DSPTBM)
      */
    private String eshopCode;

    /*
     *退货单号(THDH)
     */
    private String returnOrderNo;

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

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Timestamp getOrderDate() {
        return obtainValidTimestamp(orderDate);
    }

    public void setOrderDate(Timestamp orderDate) {
        this.orderDate = obtainValidTimestamp(orderDate);
    }

    public String getEshopCode() {
        return eshopCode;
    }

    public void setEshopCode(String eshopCode) {
        this.eshopCode = eshopCode;
    }

    public String getReturnOrderNo() {
        return returnOrderNo;
    }

    public void setReturnOrderNo(String returnOrderNo) {
        this.returnOrderNo = returnOrderNo;
    }

    @Override
    public Boolean isNullObject() {
        return invoiceId == null;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).
                add("invoiceId", invoiceId).
                add("invoiceCode", invoiceCode).
                add("invoiceNo", invoiceNo).
                add("orderNo", orderNo).
                add("orderDate", orderDate).
                add("eshopCode", eshopCode).
                add("returnOrderNo", returnOrderNo).
                toString();
    }
}
