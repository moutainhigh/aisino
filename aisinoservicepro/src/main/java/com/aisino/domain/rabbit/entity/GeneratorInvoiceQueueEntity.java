package com.aisino.domain.rabbit.entity;

import static com.google.common.base.MoreObjects.toStringHelper;

/**
 * 生成发票队列Bean
 * <p/>
 * Created by Bourne.Lv on 2014/09/25.
 */
public final class GeneratorInvoiceQueueEntity extends AbstractQueueDomain {

	private static final long serialVersionUID = -7681244754559559090L;

	/**
	 * 发票ID
	 */
	private Long invoiceId;

	/**
	 * 税纳人识别号(NSRSBH)
	 */
	private String taxpayerIdentifyNo;

	/**
	 * 纳税人所在税务机关代码(SZ_SWJG_DM)
	 */
	private String taxAuthorityCode;

	/**
	 * 电商平台编码(DSPTBM)
	 */
	private String eshopCode;

	/**
	 * 开票合计金额(KPHJJE)
	 */
	private Double billingAmount;

	/**
	 * 开票类型 (KPLX,1正票 2红票)
	 */
	private Long billingType;

	/**
	 * 签章ID(QZID)
	 */
	private String signatureId;

	/**
	 * 订单号码(DDH)
	 */
	private String orderNo;

	/**
	 * 发票请求唯一流水号(FPQQLSH)
	 */
	private String invoiceRequestSerialNo;

	/**
	 * 原发票代码(YFP_DM)
	 */
	private String oldInvoiceCode;

	/**
	 * 原发票号码(YFP_HM)
	 */
	private String oldInvoiceNo;

	/**
	 * 备注(BZ)
	 */
	private String memo;

	/**
	 * 购货方手机(GHF_SJ)
	 */
	private String buyerMobile;

	/**
	 * 购货方固定电话(GHF_GDDH)
	 */
	private String buyerFixedPhone;

	/**
	 * 购货方邮箱(GHF_EMAIL)
	 */
	private String buyerEmail;

	/**
	 * 购货方名称(GHFMC)
	 */
	private String buyerName;

	/**
	 * 购货方识别号(GHF_NSRSBH)
	 */
	private String buyerTaxpayerIdentifyNo;

	/**
	 * 购货方地址(GHF_DZ)
	 */
	private String buyerAddress;

	/**
	 * 开票员(KPY)
	 */
	private String billingStaff;

	/**
	 * 销货方识别号(XHF_NSRSBH)
	 */
	private String sellerTaxpayerIdentifyNo;

	/**
	 * 销货方名称(XHFMC)
	 */
	private String sellerName;

	/**
	 * 行业名称(HY_MC)
	 */
	private String industryName;

	/**
	 * 开票方名称(NSRMC)
	 */
	private String taxpayer;

	/**
	 * 操作代码(CZDM)
	 */
	private String operatorNo;

	/**
	 * 购方开户行及账号(FKFKHYH_FKFYHZH)
	 */
	private String infoClientBankAccount;

	/**
	 * 购方地址电话(FKFDZ_FKFDH)
	 */
	private String infoClientAddressPhone;

	/**
	 * 销方开户行及账号(XHFKHYH_SKFYHZH)
	 */
	private String infoSellerBankAccount;

	/**
	 * 销方地址电话(XHFDZ_XHFDH)
	 */
	private String infoSellerAddressPhone;

	/**
	 * 复核人(FHR)
	 */
	private String infoChecker;

	/**
	 * 销货清单(XHQD)
	 */
	// private String infoListName;

	/**
	 * 合计税额（KPHJSE）
	 */
	private Double InfoTaxAmount;
	/**
	 * 合计不含税金额（HJBHSJE）
	 */
	private Double InfoAmount;
	/**
	 * 所属月份（SSYF）
	 */
	// private String InfoMonth;
	/**
	 * 销货清单标志（XHQDBZ）
	 */
	// private String GoodsListFlag;
	/**
	 * 返回编码（RETCODE）
	 */
	// private String RetCode;
	/**
	 * 防伪密文（FWMW）
	 */
	// private String Ciphertext;
	/**
	 * 校验码（JYM）
	 */
	// private String checkCode;
	/**
	 * 数字签名（SZQM）
	 */
	// private String infoInvoicer;
	/**
	 * 收款员（SKY）
	 */
	private String cashier;
	private String jqbh;

	// 新增字段对应log表中的error_msg 2017-07-14
	private String errorMsg;

	/**
	 * 编码表版本号（BMB_BBH） 2016年7月5日 20:28:52 新增字段
	 */
	private String infoSellerVersion;

	public String getInfoSellerVersion() {
		return infoSellerVersion;
	}

	public void setInfoSellerVersion(String infoSellerVersion) {
		this.infoSellerVersion = infoSellerVersion;
	}

	public String getJqbh() {
		return jqbh;
	}

	public void setJqbh(String jqbh) {
		this.jqbh = jqbh;
	}

	public Double getInfoTaxAmount() {
		return InfoTaxAmount;
	}

	public void setInfoTaxAmount(Double infoTaxAmount) {
		InfoTaxAmount = infoTaxAmount;
	}

	public String getCashier() {
		return cashier;
	}

	public void setCashier(String cashier) {
		this.cashier = cashier;
	}

	public String getInfoClientBankAccount() {
		return infoClientBankAccount;
	}

	public Double getInfoAmount() {
		return InfoAmount;
	}

	public void setInfoAmount(Double infoAmount) {
		InfoAmount = infoAmount;
	}

	public void setInfoClientBankAccount(String infoClientBankAccount) {
		this.infoClientBankAccount = infoClientBankAccount;
	}

	public String getInfoClientAddressPhone() {
		return infoClientAddressPhone;
	}

	public void setInfoClientAddressPhone(String infoClientAddressPhone) {
		this.infoClientAddressPhone = infoClientAddressPhone;
	}

	public String getInfoSellerBankAccount() {
		return infoSellerBankAccount;
	}

	public void setInfoSellerBankAccount(String infoSellerBankAccount) {
		this.infoSellerBankAccount = infoSellerBankAccount;
	}

	public String getInfoSellerAddressPhone() {
		return infoSellerAddressPhone;
	}

	public void setInfoSellerAddressPhone(String infoSellerAddressPhone) {
		this.infoSellerAddressPhone = infoSellerAddressPhone;
	}

	public String getInfoChecker() {
		return infoChecker;
	}

	public void setInfoChecker(String infoChecker) {
		this.infoChecker = infoChecker;
	}

	public String getTaxpayer() {
		return taxpayer;
	}

	public void setTaxpayer(String taxpayer) {
		this.taxpayer = taxpayer;
	}

	public String getOperatorNo() {
		return operatorNo;
	}

	public void setOperatorNo(String operatorNo) {
		this.operatorNo = operatorNo;
	}

	public Long getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(Long invoiceId) {
		this.invoiceId = invoiceId;
	}

	public String getTaxpayerIdentifyNo() {
		return taxpayerIdentifyNo;
	}

	public void setTaxpayerIdentifyNo(String taxpayerIdentifyNo) {
		this.taxpayerIdentifyNo = taxpayerIdentifyNo;
	}

	public String getTaxAuthorityCode() {
		return taxAuthorityCode;
	}

	public void setTaxAuthorityCode(String taxAuthorityCode) {
		this.taxAuthorityCode = taxAuthorityCode;
	}

	public String getEshopCode() {
		return eshopCode;
	}

	public void setEshopCode(String eshopCode) {
		this.eshopCode = eshopCode;
	}

	public Double getBillingAmount() {
		return billingAmount;
	}

	public void setBillingAmount(Double billingAmount) {
		this.billingAmount = billingAmount;
	}

	public Long getBillingType() {
		return billingType;
	}

	public void setBillingType(Long billingType) {
		this.billingType = billingType;
	}

	public String getSignatureId() {
		return signatureId;
	}

	public void setSignatureId(String signatureId) {
		this.signatureId = signatureId;
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

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getBuyerMobile() {
		return buyerMobile;
	}

	public void setBuyerMobile(String buyerMobile) {
		this.buyerMobile = buyerMobile;
	}

	public String getBuyerFixedPhone() {
		return buyerFixedPhone;
	}

	public void setBuyerFixedPhone(String buyerFixedPhone) {
		this.buyerFixedPhone = buyerFixedPhone;
	}

	public String getBuyerEmail() {
		return buyerEmail;
	}

	public void setBuyerEmail(String buyerEmail) {
		this.buyerEmail = buyerEmail;
	}

	public String getBuyerName() {
		return buyerName;
	}

	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}

	public String getBuyerTaxpayerIdentifyNo() {
		return buyerTaxpayerIdentifyNo;
	}

	public void setBuyerTaxpayerIdentifyNo(String buyerTaxpayerIdentifyNo) {
		this.buyerTaxpayerIdentifyNo = buyerTaxpayerIdentifyNo;
	}

	public String getBuyerAddress() {
		return buyerAddress;
	}

	public void setBuyerAddress(String buyerAddress) {
		this.buyerAddress = buyerAddress;
	}

	public String getBillingStaff() {
		return billingStaff;
	}

	public void setBillingStaff(String billingStaff) {
		this.billingStaff = billingStaff;
	}

	public String getSellerTaxpayerIdentifyNo() {
		return sellerTaxpayerIdentifyNo;
	}

	public void setSellerTaxpayerIdentifyNo(String sellerTaxpayerIdentifyNo) {
		this.sellerTaxpayerIdentifyNo = sellerTaxpayerIdentifyNo;
	}

	public String getSellerName() {
		return sellerName;
	}

	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}

	public String getIndustryName() {
		return industryName;
	}

	public void setIndustryName(String industryName) {
		this.industryName = industryName;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	@Override
	public Boolean isNullObject() {
		return null;
	}

	@Override
	public String toString() {
		return toStringHelper(this).add("invoiceId", invoiceId).add("taxpayerIdentifyNo", taxpayerIdentifyNo).add("taxAuthorityCode", taxAuthorityCode).add("eshopCode", eshopCode).add("billingAmount", billingAmount).add("billingType", billingType).add("signatureId", signatureId).add("orderNo", orderNo).add("invoiceRequestSerialNo", invoiceRequestSerialNo).add("oldInvoiceCode", oldInvoiceCode).add("oldInvoiceNo", oldInvoiceNo).add("memo", memo).add("buyerMobile", buyerMobile).add("buyerFixedPhone", buyerFixedPhone).add("buyerEmail", buyerEmail).add("buyerName", buyerName).add("buyerTaxpayerIdentifyNo", buyerTaxpayerIdentifyNo).add("buyerAddress", buyerAddress).add("billingStaff", billingStaff).add("sellerTaxpayerIdentifyNo", sellerTaxpayerIdentifyNo).add("sellerName", sellerName)
				.add("industryName", industryName).add("taxpayer", taxpayer).add("operatorNo", operatorNo).toString();
	}

}
