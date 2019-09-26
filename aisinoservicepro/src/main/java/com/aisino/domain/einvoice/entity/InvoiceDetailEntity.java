package com.aisino.domain.einvoice.entity;

import com.aisino.domain.AbstractBaseDomain;
import com.google.common.base.MoreObjects;

/**
 * Created by Bourne.Lv on 2014/09/05.
 * <p/>
 * 发票开具明细信息实体(fp_kjmx)
 *
 * @see com.aisino.domain.AbstractBaseDomain
 */
public final class InvoiceDetailEntity extends AbstractBaseDomain {

    private static final long serialVersionUID = 1775126077536209510L;

    /**
     * 发票开具ID(KJID)
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
     * 商品行序号(SPHXH)
     */
    private Long itemIndex;

    /**
     * 商品名称(XMMC)
     */
    private String itemName;

    /**
     * 计量单位(XMDW)
     */
    private String unitName;

    /**
     * 规格型号(GGXH)
     */
    private String specificationModel;

    /**
     * 商品数量(XMSL)
     */
    private Double itemCount;

    /**
     * 商品单价(XMDJ)
     */
    private Double itemUnitCost;

    /**
     * 商品金额(XMJE)
     */
    private Double itemAmount;

    /**
     * 项目编码(XMBM)
     */
    private String itemCode;
    
    /**
     * 税目(SM)
     */
    private String listTaxItem;
    
    /**
     * 税率(SL)
     */
    private String infoTaxRate;
    
    /**
     * 计量单位(JLDW)
     */
    private String listUnit;
    
    /**
     * 含税价标志(HSJBZ)
     */
    private String listPriceKind;
    
    /**
     * 税额(SE)
     */
    private Double listTaxAmount;
    
    /**
     * 发票行性质(FPHXZ)
     */
    private String invoiceLineProperty;
    
    //新增明细字段 2016年7月5日 20:06:12 阳开国 begin
    /**
     * 商品编码(SPBM)
     */
    private String goodsCode;
    /**
     * 自行编码(ZXBM)
     */
    private String selfCoding ;
    /**
     * 优惠政策标识(YHZCBS)
     */
    private String preferentialMarking;
    /**
     * 零税率标识(LSLBS)
     */
    private String zeroTariff;
    /**
     * 增值税特殊管理(ZZSTSGL)
     */
    private String specialManagement;
    /**
     * 扣除额(KCE)
     */
    private String deductions ;
    
  //新增明细字段 2016年7月5日 20:06:12 阳开国 end 
    
    
    

    public String getListTaxItem() {
        return listTaxItem;
    }

    public String getGoodsCode() {
        return goodsCode;
    }

    public void setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode;
    }

    public String getSelfCoding() {
        return selfCoding;
    }

    public void setSelfCoding(String selfCoding) {
        this.selfCoding = selfCoding;
    }

    public String getPreferentialMarking() {
        return preferentialMarking;
    }

    public void setPreferentialMarking(String preferentialMarking) {
        this.preferentialMarking = preferentialMarking;
    }

    public String getZeroTariff() {
        return zeroTariff;
    }

    public void setZeroTariff(String zeroTariff) {
        this.zeroTariff = zeroTariff;
    }

    public String getSpecialManagement() {
        return specialManagement;
    }

    public void setSpecialManagement(String specialManagement) {
        this.specialManagement = specialManagement;
    }

    public String getDeductions() {
        return deductions;
    }

    public void setDeductions(String deductions) {
        this.deductions = deductions;
    }

    public void setListTaxItem(String listTaxItem) {
        this.listTaxItem = listTaxItem;
    }

    public String getInfoTaxRate() {
        return infoTaxRate;
    }

    public void setInfoTaxRate(String infoTaxRate) {
        this.infoTaxRate = infoTaxRate;
    }

    public String getListUnit() {
        return listUnit;
    }

    public void setListUnit(String listUnit) {
        this.listUnit = listUnit;
    }

    public String getListPriceKind() {
        return listPriceKind;
    }

    public void setListPriceKind(String listPriceKind) {
        this.listPriceKind = listPriceKind;
    }

    public Double getListTaxAmount() {
        return listTaxAmount;
    }

    public void setListTaxAmount(Double listTaxAmount) {
        this.listTaxAmount = listTaxAmount;
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

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getInvoiceLineProperty() {
        return invoiceLineProperty;
    }

    public void setInvoiceLineProperty(String invoiceLineProperty) {
        this.invoiceLineProperty = invoiceLineProperty;
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
                add("itemIndex", itemIndex).
                add("itemName", itemName).
                add("unitName", unitName).
                add("specificationModel", specificationModel).
                add("itemCount", itemCount).
                add("itemUnitCost", itemUnitCost).
                add("itemAmount", itemAmount).
                add("itemCode", itemCode).
                toString();
    }
}
