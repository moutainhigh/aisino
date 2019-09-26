package com.aisino.domain.rabbit.entity;

import static com.google.common.base.MoreObjects.toStringHelper;

/**
 * 上传税局队列Bean
 * <p/>
 * Created by Bourne.Lv on 2014/09/25.
 */
public final class SentToTaxQueueEntity extends AbstractQueueDomain {

    private static final long serialVersionUID = 7850268818413704207L;

    /**
     * 发票ID
     */
    private Long invoiceId;

    /**
     * 电商平台编码(DSPTBM)
     */
    private String eshopCode;

    /**
     * 注册号(zch/zcm)
     */
    private String registerNo;

    /**
     * 所在税务机关代码(sz_swjg_dm)
     */
    private String taxAuthorityCode;


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

    public String getRegisterNo() {
        return registerNo;
    }

    public void setRegisterNo(String registerNo) {
        this.registerNo = registerNo;
    }

    public String getTaxAuthorityCode() {
        return taxAuthorityCode;
    }

    public void setTaxAuthorityCode(String taxAuthorityCode) {
        this.taxAuthorityCode = taxAuthorityCode;
    }

    @Override
    public Boolean isNullObject() {
        return false;
    }

    @Override
    public String toString() {
        return toStringHelper(this).
                add("invoiceId", invoiceId).
                add("eshopCode", eshopCode).
                add("registerNo", registerNo).
                add("taxAuthorityCode", taxAuthorityCode).
                toString();
    }
}
