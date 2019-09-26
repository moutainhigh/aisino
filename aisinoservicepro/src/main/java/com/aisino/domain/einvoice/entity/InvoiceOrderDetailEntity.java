package com.aisino.domain.einvoice.entity;

import com.aisino.domain.AbstractBaseDomain;

import static com.google.common.base.MoreObjects.toStringHelper;

/**
 * Created with IntelliJ IDEA.
 * User: Schiffer.huang
 * Date: 14-10-8
 * Time: 上午8:52
 * 发票订单详细信息实体(fp_ddmxxx)
 */
public final class InvoiceOrderDetailEntity extends AbstractBaseDomain {

    private static final long serialVersionUID = 7060880469369877899L;

    /*
     * 发票ID(KJID)
     */
    private Long invoiceId;

    /*
     *商品行序号(SPHXH)
     */
    private Long itemIndex;

    /*
     *订单号(DDH)
     */
    private String orderNo;

    /*
     *商品名称(SPMC/XMMC)
     */
    private String itemName;

    /*
     *计量单位(XMDW/JLDW)
     */
    private String unitName;

    /*
     *规格型号(GGXH)
     */
    private String specificationModel;

    /*
     *商品数量(XMSL/SPSL)
     */
    private Double itemCount;

    /*
     *商品单价(XMDJ/SPDJ)
     */
    private Double itemUnitCost;

    /*
     *商品金额(XMJE/SPJE)
     */
    private Double itemAmount;


    public Long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Long invoiceId) {
        this.invoiceId = invoiceId;
    }

    public Long getItemIndex() {
        return itemIndex;
    }

    public void setItemIndex(Long itemIndex) {
        this.itemIndex = itemIndex;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getSpecificationModel() {
        return specificationModel;
    }

    public void setSpecificationModel(String specificationModel) {
        this.specificationModel = specificationModel;
    }

    public Double getItemCount() {
        return itemCount;
    }

    public void setItemCount(Double itemCount) {
        this.itemCount = itemCount;
    }

    public Double getItemUnitCost() {
        return itemUnitCost;
    }

    public void setItemUnitCost(Double itemUnitCost) {
        this.itemUnitCost = itemUnitCost;
    }

    public Double getItemAmount() {
        return itemAmount;
    }

    public void setItemAmount(Double itemAmount) {
        this.itemAmount = itemAmount;
    }

    @Override
    public Boolean isNullObject() {
        return invoiceId == null;
    }

    @Override
    public String toString() {
        return toStringHelper(this)
                .add("invoiceId", invoiceId)
                .add("itemIndex", itemIndex)
                .add("orderNo", orderNo)
                .add("itemName", itemName)
                .add("unitName", unitName)
                .add("specificationModel", specificationModel)
                .add("itemCount", itemCount)
                .add("itemUnitCost", itemUnitCost)
                .add("itemAmount", itemAmount)
                .toString();
    }
}
