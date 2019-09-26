package com.aisino.domain.einvoice.entity;

import com.aisino.domain.AbstractBaseDomain;
import com.aisino.protocol.bean.FPKJXX_FPJGXX;
import com.aisino.protocol.bean.FPKJXX_XMXX;

import static com.google.common.base.MoreObjects.toStringHelper;

/**
 * 发票PDF实体(生成发票时传递参数使用)
 * <p/>
 * Created by Martin.Ou on 2014/9/12.
 *
 * @see com.aisino.domain.AbstractBaseDomain
 * @see com.aisino.domain.einvoice.entity.InvoiceHeaderEntity
 * @see EShopInfo
 * @see com.aisino.protocol.bean.FPKJXX_FPJGXX
 * @see com.aisino.protocol.bean.FPKJXX_XMXX
 */
public final class InvoicePDFEntity extends AbstractBaseDomain {

    private static final long serialVersionUID = 3327921778241721799L;

    /**
     * 发票头信息实体
     */
    private InvoiceHeaderEntity invoiceHeaderEntity;

    /**
     * 电商平台信息实体
     */
    private EShopInfo eShopInfo;

    /**
     * 发票开具以及发票结果 协议Bean
     */
    private FPKJXX_FPJGXX fpkjxxFpjgxx;

    /**
     * 发票开具明细 协议Bean
     */
    private FPKJXX_XMXX[] fpkjxxXmxxes;

    /**
     * 纳税人签章ID
     */
    private String taxerSignatureId;

    public String getTaxerSignatureId() {
        return taxerSignatureId;
    }

    public void setTaxerSignatureId(String taxerSignatureId) {
        this.taxerSignatureId = taxerSignatureId;
    }

    public InvoiceHeaderEntity getInvoiceHeaderEntity() {
        return invoiceHeaderEntity;
    }

    public void setInvoiceHeaderEntity(InvoiceHeaderEntity invoiceHeaderEntity) {
        this.invoiceHeaderEntity = invoiceHeaderEntity;
    }

    public EShopInfo geteShopInfo() {
        return eShopInfo;
    }

    public void seteShopInfo(EShopInfo eShopInfo) {
        this.eShopInfo = eShopInfo;
    }

    public FPKJXX_FPJGXX getFpkjxxFpjgxx() {
        return fpkjxxFpjgxx;
    }

    public void setFpkjxxFpjgxx(FPKJXX_FPJGXX fpkjxxFpjgxx) {
        this.fpkjxxFpjgxx = fpkjxxFpjgxx;
    }

    public FPKJXX_XMXX[] getFpkjxxXmxxes() {
        return fpkjxxXmxxes;
    }

    public void setFpkjxxXmxxes(FPKJXX_XMXX[] fpkjxxXmxxes) {
        this.fpkjxxXmxxes = fpkjxxXmxxes;
    }

    @Override
    public Boolean isNullObject() {
        return false;
    }

    @Override
    public String toString() {
        return toStringHelper(this)
                .add("invoiceHeaderEntity", invoiceHeaderEntity)
                .add("eShopInfo", eShopInfo)
                .add("fpkjxxFpjgxx", fpkjxxFpjgxx)
                .add("fpkjxxXmxxes", fpkjxxXmxxes)
                .toString();
    }
}
