package com.aisino.domain.einvoice.entity;

import com.aisino.domain.AbstractBaseDomain;
import com.aisino.domain.SystemConfig;
import org.joda.time.DateTime;

import java.sql.Timestamp;

import static com.google.common.base.MoreObjects.toStringHelper;

/**
 * Created by Bourne.Lv on 2014/09/04.
 * <p/>
 * 发票信息实体(fp_kj, fp_kj_log)
 * 
 * @see com.aisino.domain.AbstractBaseDomain
 */
public final class InvoiceEntity extends AbstractBaseDomain {

    private static final long serialVersionUID = 1464486710614578175L;

    /**
     * 发票ID
     */
    private Long invoiceId;

    /**
     * 电商平台编码(DSPTBM)
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
     * 税纳人识别号(NSRSBH)
     */
    private String taxpayerIdentifyNo;

    /**
     * 操作次数(与上传次数,失败次数等同义ERROR_COUNT,SCCOUNT)
     */
    private Long operateCount;

    /**
     * 发票状态(开具状态FPKJ_ZT)
     */
    private String invoiceStatus;

    /**
     * 发票处理失败原因(ERROR_MSG)
     */
    private String errorMsg;

    /**
     * 签章ID(QZID)
     */
    private String signatureId;

    /**
     * 开票日期(KPRQ)
     */
    private Timestamp billingDate;

    /**
     * 发票PDF文件保存路径(PDFPATCH)
     */
    private String pdfPath;

    /**
     * 税控码, 防伪码(SKM,FWM)
     */
    private String fiscalCode;

    /**
     * 二维码(EWM)
     */
    private String twoDimensionCode;

    /**
     * 开票流水号(KPLSH,发票代码+发票号码)
     */
    private String invoiceSerialNo;

    /**
     * 发票种类代码(FPZL_DM)
     */
    private String invoiceKindCode;

    /**
     * 开票合计金额(KPHJJE)
     */
    private Double billingAmount;

    /**
     * 开票类型 (KPLX,1正票 2红票)
     */
    private Long billingType;

    /**
     * 发票请求唯一流水号(FPQQLSH)
     */
    private String invoiceRequestSerialNo;

    /**
     * 订单号(DDH)
     */
    private String orderNo;

    /**
     * 原发票代码(YFP_DM)
     */
    private String oldInvoiceCode;

    /**
     * 原发票号码(YFP_HM)
     */
    private String oldInvoiceNo;

    /**
     * 是否特殊冲红(TSCHBZ)
     */
    private String specRedInvoiceFlag;

    /**
     * 开票方识别号(KPFSBH)
     */
    private String invoiceIdentifyNo;

    /**
     * 开票方名称(KPFMC)
     */
    private String taxpayer;

    /**
     * 付款方名称(FKFMC)
     */
    private String payName;

    /**
     * 交易流水号(JYLSH)
     */
    private String transactionSerialNo;

    /**
     * 合计不含税金额(HJBHSJE)
     */
    private Double infoAmount;

    /**
     * 合计税额(KPHJSE)
     */
    private Double infoTaxAmount;

    /**
     * 所属月份(SSYF)
     */
    private String infoMonth;

    /**
     * 销货清单标志(XHQDBZ)
     */
    private String goodsListFlag;

    /**
     * 返回编码(RETCODE)
     */
    private String retCode;

    /**
     * 防伪密文(FWMW)
     */
    private String ciphertext;

    /**
     * 校验码(JYM)
     */
    private String checkCode;

    /**
     * 数字签名(SZQM)
     */
    private String infoInvoicer;

    /**
     * 日期(KPRQ)
     */
    private String infoDate;
    /**
     * 分机号
     */
    private String fjh; 
    /**
     * 备注
     */
    private String bz;
    /**
     * 机器编号
     */
    private String jqbh;
    
    /**
     * 销货清单(XHQD)
     */
    private String goodsList;

    public String getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(String goodsList) {
        this.goodsList = goodsList;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    public String getFjh() {
        return fjh;
    }

    public void setFjh(String fjh) {
        this.fjh = fjh;
    }

    public String getInfoDate() {
        return infoDate;
    }

    public void setInfoDate(String infoDate) {
        this.infoDate = infoDate;
    }

    public Double getInfoAmount() {
        return infoAmount;
    }

    public void setInfoAmount(Double infoAmount) {
        this.infoAmount = infoAmount;
    }

    public Double getInfoTaxAmount() {
        return infoTaxAmount;
    }

    public void setInfoTaxAmount(Double infoTaxAmount) {
        this.infoTaxAmount = infoTaxAmount;
    }

    public String getInfoMonth() {
        return infoMonth;
    }

    public void setInfoMonth(String infoMonth) {
        this.infoMonth = infoMonth;
    }

    public String getGoodsListFlag() {
        return goodsListFlag;
    }

    public void setGoodsListFlag(String goodsListFlag) {
        this.goodsListFlag = goodsListFlag;
    }

    public String getRetCode() {
        return retCode;
    }

    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }

    public String getCiphertext() {
        return ciphertext;
    }

    public void setCiphertext(String ciphertext) {
        this.ciphertext = ciphertext;
    }

    public String getCheckCode() {
        return checkCode;
    }

    public void setCheckCode(String checkCode) {
        this.checkCode = checkCode;
    }

    public String getInfoInvoicer() {
        return infoInvoicer;
    }

    public void setInfoInvoicer(String infoInvoicer) {
        this.infoInvoicer = infoInvoicer;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getInvoiceRequestSerialNo() {
        return invoiceRequestSerialNo;
    }

    public void setInvoiceRequestSerialNo(String invoiceRequestSerialNo) {
        this.invoiceRequestSerialNo = invoiceRequestSerialNo;
    }

    public String getEshopCode() {
        return eshopCode;
    }

    public void setEshopCode(String eshopCode) {
        this.eshopCode = eshopCode;
    }

    public Long getBillingType() {
        return billingType;
    }

    public void setBillingType(Long billingType) {
        this.billingType = billingType;
    }

    public Double getBillingAmount() {
        return billingAmount;
    }

    public void setBillingAmount(Double billingAmount) {
        this.billingAmount = billingAmount;
    }

    public String getPdfPath() {
        return pdfPath;
    }

    public void setPdfPath(String pdfPath) {
        this.pdfPath = pdfPath;
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

    public String getInvoiceSerialNo() {
        return invoiceSerialNo;
    }

    public void setInvoiceSerialNo(String invoiceSerialNo) {
        this.invoiceSerialNo = invoiceSerialNo;
    }

    public String getInvoiceKindCode() {
        return invoiceKindCode;
    }

    public void setInvoiceKindCode(String invoiceKindCode) {
        this.invoiceKindCode = invoiceKindCode;
    }

    public Timestamp getBillingDate() {
        return obtainValidTimestamp(billingDate);
    }

    public void setBillingDate(Timestamp billingDate) {
        this.billingDate = obtainValidTimestamp(billingDate);
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

    public String getSignatureId() {
        return signatureId;
    }

    public void setSignatureId(String signatureId) {
        this.signatureId = signatureId;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public Long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Long invoiceId) {
        this.invoiceId = invoiceId;
    }

    public Long getOperateCount() {
        return operateCount;
    }

    public void setOperateCount(Long operateCount) {
        this.operateCount = operateCount;
    }

    public String getInvoiceStatus() {
        return invoiceStatus;
    }

    public void setInvoiceStatus(String invoiceStatus) {
        this.invoiceStatus = invoiceStatus;
    }

    public String getTaxpayerIdentifyNo() {
        return taxpayerIdentifyNo;
    }

    public void setTaxpayerIdentifyNo(String taxpayerIdentifyNo) {
        this.taxpayerIdentifyNo = taxpayerIdentifyNo;
    }

    @Override
    public Boolean isNullObject() {
        return invoiceId == null;
    }

    public String getOldInvoiceCode() {
        return oldInvoiceCode;
    }

    public void setOldInvoiceCode(String oldInvoiceCode) {
        this.oldInvoiceCode = oldInvoiceCode;
    }

    public String getOldInvoiceNo() {
        return oldInvoiceNo;
    }

    public void setOldInvoiceNo(String oldInvoiceNo) {
        this.oldInvoiceNo = oldInvoiceNo;
    }

    public String getSpecRedInvoiceFlag() {
        return specRedInvoiceFlag;
    }

    public void setSpecRedInvoiceFlag(String specRedInvoiceFlag) {
        this.specRedInvoiceFlag = specRedInvoiceFlag;
    }

    public String getBillingDateFormat() {
        if (null != billingDate) {
            return new DateTime(billingDate).toString(SystemConfig.long_date_format);
        } else {
            return "";
        }
    }

    public String getInvoiceIdentifyNo() {
        return invoiceIdentifyNo;
    }

    public void setInvoiceIdentifyNo(String invoiceIdentifyNo) {
        this.invoiceIdentifyNo = invoiceIdentifyNo;
    }

    public String getTaxpayer() {
        return taxpayer;
    }

    public void setTaxpayer(String taxpayer) {
        this.taxpayer = taxpayer;
    }

    public String getPayName() {
        return payName;
    }

    public void setPayName(String payName) {
        this.payName = payName;
    }

    public String getTransactionSerialNo() {
        return transactionSerialNo;
    }

    public void setTransactionSerialNo(String transactionSerialNo) {
        this.transactionSerialNo = transactionSerialNo;
    }

    public String getJqbh() {
        return jqbh;
    }

    public void setJqbh(String jqbh) {
        this.jqbh = jqbh;
    }

    @Override
    public String toString() {
        return toStringHelper(this).add("invoiceId", invoiceId).add("eshopCode", eshopCode).add("invoiceCode", invoiceCode).add("invoiceNo", invoiceNo).add(
                "taxpayerIdentifyNo", taxpayerIdentifyNo).add("operateCount", operateCount).add("invoiceStatus", invoiceStatus).add("errorMsg", errorMsg).add(
                "signatureId", signatureId).add("billingDate", billingDate).add("pdfPath", pdfPath).add("fiscalCode", fiscalCode).add("twoDimensionCode",
                twoDimensionCode).add("invoiceSerialNo", invoiceSerialNo).add("invoiceKindCode", invoiceKindCode).add("billingAmount", billingAmount).add(
                "billingType", billingType).add("invoiceRequestSerialNo", invoiceRequestSerialNo).add("orderNo", orderNo).add("oldInvoiceCode", oldInvoiceCode)
                .add("oldInvoiceNo", oldInvoiceNo).add("specRedInvoiceFlag", specRedInvoiceFlag).add("InvoiceIdentifyNo", invoiceIdentifyNo).add("taxpayer",
                        taxpayer).add("payName", payName).add("transactionSerialNo", transactionSerialNo).add("infoAmount", infoAmount).add("infoTaxAmount",
                        infoTaxAmount).add("infoMonth", infoMonth).add("goodsListFlag", goodsListFlag).add("fjh", fjh).add("bz", bz).
                        add("goodsList", goodsList).add("retCode", retCode).add("ciphertext", ciphertext)
                .add("checkCode", checkCode).add("infoInvoicer", infoInvoicer).add("infoDate", infoDate).add("transactionSerialNo", transactionSerialNo).add(
                        "transactionSerialNo", transactionSerialNo).toString();
    }
}
