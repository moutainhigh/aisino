package com.aisino.domain.einvoice.entity;

import com.aisino.domain.AbstractBaseDomain;

import java.sql.Timestamp;

import static com.google.common.base.MoreObjects.toStringHelper;

/**
 * 发票头信息实体(FPTXX)
 * <p/>
 * Created by Martin.Ou on 2014/9/12.
 * 
 * @see com.aisino.domain.AbstractBaseDomain
 * @see InvoiceEntity
 */
public final class InvoiceHeaderEntity extends AbstractBaseDomain {

    private static final long serialVersionUID = 2745255715523987960L;

    /**
     * 发票实体
     */
    private InvoiceEntity invoiceEntity;

    /**
     * 发票请求唯一流水号(FPQQLSH)
     */
    private String invoiceRequestSerialNo;

    /**
     * 开票方名称(NSRMC)
     */
    private String taxpayer;

    /**
     * 开票方电子档案号(NSRDZDAH)
     */
    private String taxpayerEDNo;

    /**
     * 税务机构代码(SWJG_DM)
     */
    private String taxOfficeRegCode;

    /**
     * 代开标志(DKBZ)
     */
    private String agentInvoiceFlag;

    /**
     * 票样代码(PYDM)
     */
    private String sampleInvoiceCode;

    /**
     * 开票项目(KPXM)
     */
    private String billingItem;

    /**
     * 销货方识别号(XHF_NSRSBH)
     */
    private String sellerTaxpayerIdentifyNo;

    /**
     * 销货方名称(XHFMC)
     */
    private String sellerName;

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
     * 购货方固定电话(GHF_GDDH)
     */
    private String buyerFixedPhone;

    /**
     * 购货方手机(GHF_SJ)
     */
    private String buyerMobile;

    /**
     * 购货方邮箱(GHF_EMAIL)
     */
    private String buyerEmail;

    /**
     * 行业代码(HY_DM)
     */
    private String industryCode;

    /**
     * 行业名称(HY_MC)
     */
    private String industryName;

    /**
     * 开票员(KPY)
     */
    private String billingStaff;

    /**
     * 收款员(SKY)
     */
    private String cashier;

    /**
     * 原发票代码(YFP_DM)
     */
    private String oldInvoiceCode;

    /**
     * 原发票号码(YFP_HM)
     */
    private String oldInvoiceNo;

    /**
     * 冲红原因(CHYY)
     */
    private String redInvoiceReason;

    /**
     * 备注(BZ)
     */
    private String memo;

    /**
     * 备用字段1
     */
    private String standbyFieldOne;

    /**
     * 备用字段2
     */
    private String standbyFieldTwo;

    /**
     * 备用字段3
     */
    private String standbyFieldThree;

    /**
     * 备用字段4
     */
    private String standbyFieldFour;

    /**
     * 备用字段5
     */
    private String standbyFieldFive;

    /**
     * 是否特殊冲红(TSCHBZ)
     */
    private String specRedInvoiceFlag;

    /**
     * 订单号码(DDH)
     */
    private String orderNo;

    /**
     * 注册号(zch/zcm)
     */
    private String registerNo;

    /**
     * 纳税人所在税务机关代码(SZ_SWJG_DM)
     */
    private String taxAuthorityCode;

    /**
     * 商品行数量(SPHSL)
     */
    private String itemCount;

    /**
     * 主营商品名称(ZYSPMC)
     */
    private String mainProductName;

    /**
     * 生成发票和pdf文件次数(SCCOUNT)
     */
    private String producedInvoiceCount;

    /**
     * 购货方企业类型(GHFQYLX)
     */
    private String buyerEnterpriseTypeCode;

    /**
     * 操作代码(CZDM)
     */
    private String operatorNo;

    /**
     * 电商平台编码(DSPTBM)
     */
    private String eshopCode;

    /**
     * 税纳人识别号(NSRSBH)
     */
    private String taxpayerIdentifyNo;

    /**
     * 开票日期(KPRQ)
     */
    private Timestamp billingDate;

    /**
     * 开票类型 (KPLX,1正票 2红票)
     */
    private Long billingType;

    /**
     * 开票合计金额(KPHJJE)
     */
    private Double billingAmount;

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
     * 发票PDF文件保存路径(PDFPATCH)
     */
    private String pdfPath;

    /**
     * 操作次数(与上传次数,失败次数等同义ERROR_COUNT,SCCOUNT)
     */
    private Long operateCount;

    /**
     * 发票状态(开具状态FPKJ_ZT)
     */
    private String invoiceStatus;

    /**
     * 签章ID(QZID)
     */
    private String signatureId;

    /**
     * 创建时间(CJSJ)
     */
    private Timestamp createdTime;

    /**
     * 发票处理失败原因(ERROR_MSG)
     */
    private String errorMsg;

    // 付款单位(FKDW)
    private String payCompany;

    // 付款方代码(FKF_DM)
    private String payCode;

    // 收款方代码(SKF_DM)
    private String receiveCode;

    // 销货方识别号(XHFSBH)
    private String sellerIdentifyNo;

    // 剩余可冲红金额(SYKCHJE)
    private Double remainingRedAmount;

    /**
     * 复核人(FHR)
     */
    private String infoChecker;

    /**
     * 销货清单(XHQD)
     */
    private String infoClientAddressPhone;

    private String infoListName;
    /**
     * 合计税额（KPHJSE）
     */
    private Double infoTaxAmount;
    /**
     * 合计不含税金额（HJBHSJE）
     */
    private Double infoAmount;
   /**
    * 所属月份（SSYF）
    */
    private String infoMonth;
    /**
     * 销货清单标志（XHQDBZ）
     */
    private String goodsListFlag;
    /**
     * 返回编码（RETCODE）
     */
    private String retCode;
    /**
     * 防伪密文（FWMW）
     */
    private String ciphertext;
    /**
     * 校验码（JYM）
     */
    private String checkCode;
    /**
     * 数字签名（SZQM）
     */
    private String infoInvoicer;
    /**
     * 购方开户行(FKFKHYH)
     */
    private String infoClientBank;
    /**
     * 购方银行账号(FKFYHZH)
     */
    private String infoClientBankAccount;
    /**
     * 销方开户行(XHFKHYH)
     */
    private String infoSellerBank;
    /**
     * 销方银行账号(SKFYHZH)
     */
    private String infoSellerBankAccount;
    
    private String infoSellerAddressPhone;
    
    /**
     * 销方地址(XHFDZ)
     */

    private String infoSellerAddress;
    /**
     * 销方电话(XHFDH)
     */
    private String infoSellerPhone;
    
    /**
     * 编码表版本号(BMB_BBH)
     */
    private String infoSellerVersion;
    

	public String getInfoSellerVersion() {
        return infoSellerVersion;
    }

    public void setInfoSellerVersion(String infoSellerVersion) {
        this.infoSellerVersion = infoSellerVersion;
    }

    public String getInfoClientAddressPhone() {
        return infoClientAddressPhone;
    }

    public void setInfoClientAddressPhone(String infoClientAddressPhone) {
        this.infoClientAddressPhone = infoClientAddressPhone;
    }

    public String getInfoSellerAddressPhone() {
        return infoSellerAddressPhone;
    }

    public void setInfoSellerAddressPhone(String infoSellerAddressPhone) {
        this.infoSellerAddressPhone = infoSellerAddressPhone;
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


	public String getInfoInvoicer() {
		return infoInvoicer;
	}

	public void setInfoInvoicer(String infoInvoicer) {
		this.infoInvoicer = infoInvoicer;
	}

	public String getInfoClientBank() {
		return infoClientBank;
	}

	public void setInfoClientBank(String infoClientBank) {
		this.infoClientBank = infoClientBank;
	}

	public String getInfoClientBankAccount() {
		return infoClientBankAccount;
	}

	public void setInfoClientBankAccount(String infoClientBankAccount) {
		this.infoClientBankAccount = infoClientBankAccount;
	}

	public String getInfoSellerBank() {
		return infoSellerBank;
	}

	public void setInfoSellerBank(String infoSellerBank) {
		this.infoSellerBank = infoSellerBank;
	}

	public String getInfoSellerBankAccount() {
		return infoSellerBankAccount;
	}

	public void setInfoSellerBankAccount(String infoSellerBankAccount) {
		this.infoSellerBankAccount = infoSellerBankAccount;
	}


	public String getInfoSellerAddress() {
		return infoSellerAddress;
	}

	public void setInfoSellerAddress(String infoSellerAddress) {
		this.infoSellerAddress = infoSellerAddress;
	}

	public String getInfoSellerPhone() {
		return infoSellerPhone;
	}

	public void setInfoSellerPhone(String infoSellerPhone) {
		this.infoSellerPhone = infoSellerPhone;
	}



	public String getInfoChecker() {
		return infoChecker;
	}

	public void setInfoChecker(String infoChecker) {
		this.infoChecker = infoChecker;
	}

	public String getInfoListName() {
        return infoListName;
    }

    public void setInfoListName(String infoListName) {
        this.infoListName = infoListName;
    }

    public InvoiceEntity getInvoiceEntity() {
        return invoiceEntity;
    }

    public void setInvoiceEntity(InvoiceEntity invoiceEntity) {
        this.invoiceEntity = invoiceEntity;
    }

    public String getInvoiceRequestSerialNo() {
        return invoiceRequestSerialNo;
    }

    public void setInvoiceRequestSerialNo(String invoiceRequestSerialNo) {
        this.invoiceRequestSerialNo = invoiceRequestSerialNo;
    }

    public String getTaxpayer() {
        return taxpayer;
    }

    public void setTaxpayer(String taxpayer) {
        this.taxpayer = taxpayer;
    }

    public String getTaxpayerEDNo() {
        return taxpayerEDNo;
    }

    public void setTaxpayerEDNo(String taxpayerEDNo) {
        this.taxpayerEDNo = taxpayerEDNo;
    }

    public String getTaxOfficeRegCode() {
        return taxOfficeRegCode;
    }

    public void setTaxOfficeRegCode(String taxOfficeRegCode) {
        this.taxOfficeRegCode = taxOfficeRegCode;
    }

    public String getAgentInvoiceFlag() {
        return agentInvoiceFlag;
    }

    public void setAgentInvoiceFlag(String agentInvoiceFlag) {
        this.agentInvoiceFlag = agentInvoiceFlag;
    }

    public String getSampleInvoiceCode() {
        return sampleInvoiceCode;
    }

    public void setSampleInvoiceCode(String sampleInvoiceCode) {
        this.sampleInvoiceCode = sampleInvoiceCode;
    }

    public String getBillingItem() {
        return billingItem;
    }

    public void setBillingItem(String billingItem) {
        this.billingItem = billingItem;
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

    public String getBuyerFixedPhone() {
        return buyerFixedPhone;
    }

    public void setBuyerFixedPhone(String buyerFixedPhone) {
        this.buyerFixedPhone = buyerFixedPhone;
    }

    public String getBuyerMobile() {
        return buyerMobile;
    }

    public void setBuyerMobile(String buyerMobile) {
        this.buyerMobile = buyerMobile;
    }

    public String getBuyerEmail() {
        return buyerEmail;
    }

    public void setBuyerEmail(String buyerEmail) {
        this.buyerEmail = buyerEmail;
    }

    public String getIndustryCode() {
        return industryCode;
    }

    public void setIndustryCode(String industryCode) {
        this.industryCode = industryCode;
    }

    public String getIndustryName() {
        return industryName;
    }

    public void setIndustryName(String industryName) {
        this.industryName = industryName;
    }

    public String getBillingStaff() {
        return billingStaff;
    }

    public void setBillingStaff(String billingStaff) {
        this.billingStaff = billingStaff;
    }

    public String getCashier() {
        return cashier;
    }

    public void setCashier(String cashier) {
        this.cashier = cashier;
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

    public String getRedInvoiceReason() {
        return redInvoiceReason;
    }

    public void setRedInvoiceReason(String redInvoiceReason) {
        this.redInvoiceReason = redInvoiceReason;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getStandbyFieldOne() {
        return standbyFieldOne;
    }

    public void setStandbyFieldOne(String standbyFieldOne) {
        this.standbyFieldOne = standbyFieldOne;
    }

    public String getStandbyFieldTwo() {
        return standbyFieldTwo;
    }

    public void setStandbyFieldTwo(String standbyFieldTwo) {
        this.standbyFieldTwo = standbyFieldTwo;
    }

    public String getStandbyFieldThree() {
        return standbyFieldThree;
    }

    public void setStandbyFieldThree(String standbyFieldThree) {
        this.standbyFieldThree = standbyFieldThree;
    }

    public String getStandbyFieldFour() {
        return standbyFieldFour;
    }

    public void setStandbyFieldFour(String standbyFieldFour) {
        this.standbyFieldFour = standbyFieldFour;
    }

    public String getStandbyFieldFive() {
        return standbyFieldFive;
    }

    public void setStandbyFieldFive(String standbyFieldFive) {
        this.standbyFieldFive = standbyFieldFive;
    }

    public String getSpecRedInvoiceFlag() {
        return specRedInvoiceFlag;
    }

    public void setSpecRedInvoiceFlag(String specRedInvoiceFlag) {
        this.specRedInvoiceFlag = specRedInvoiceFlag;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getTaxAuthorityCode() {
        return taxAuthorityCode;
    }

    public void setTaxAuthorityCode(String taxAuthorityCode) {
        this.taxAuthorityCode = taxAuthorityCode;
    }

    public String getItemCount() {
        return itemCount;
    }

    public void setItemCount(String itemCount) {
        this.itemCount = itemCount;
    }

    public String getMainProductName() {
        return mainProductName;
    }

    public void setMainProductName(String mainProductName) {
        this.mainProductName = mainProductName;
    }

    public String getProducedInvoiceCount() {
        return producedInvoiceCount;
    }

    public void setProducedInvoiceCount(String producedInvoiceCount) {
        this.producedInvoiceCount = producedInvoiceCount;
    }

    public String getBuyerEnterpriseTypeCode() {
        return buyerEnterpriseTypeCode;
    }

    public void setBuyerEnterpriseTypeCode(String buyerEnterpriseTypeCode) {
        this.buyerEnterpriseTypeCode = buyerEnterpriseTypeCode;
    }

    public String getOperatorNo() {
        return operatorNo;
    }

    public void setOperatorNo(String operatorNo) {
        this.operatorNo = operatorNo;
    }

    public String getEshopCode() {
        return eshopCode;
    }

    public void setEshopCode(String eshopCode) {
        this.eshopCode = eshopCode;
    }

    public String getTaxpayerIdentifyNo() {
        return taxpayerIdentifyNo;
    }

    public void setTaxpayerIdentifyNo(String taxpayerIdentifyNo) {
        this.taxpayerIdentifyNo = taxpayerIdentifyNo;
    }

    public Timestamp getBillingDate() {
        return obtainValidTimestamp(billingDate);
    }

    public void setBillingDate(Timestamp billingDate) {
        this.billingDate = obtainValidTimestamp(billingDate);
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

    public String getPdfPath() {
        return pdfPath;
    }

    public void setPdfPath(String pdfPath) {
        this.pdfPath = pdfPath;
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

    public String getSignatureId() {
        return signatureId;
    }

    public void setSignatureId(String signatureId) {
        this.signatureId = signatureId;
    }

    public Timestamp getCreatedTime() {
        return obtainValidTimestamp(createdTime);
    }

    public void setCreatedTime(Timestamp createdTime) {
        this.createdTime = obtainValidTimestamp(createdTime);
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getPayCompany() {
        return payCompany;
    }

    public void setPayCompany(String payCompany) {
        this.payCompany = payCompany;
    }

    public String getPayCode() {
        return payCode;
    }

    public void setPayCode(String payCode) {
        this.payCode = payCode;
    }

    public String getReceiveCode() {
        return receiveCode;
    }

    public void setReceiveCode(String receiveCode) {
        this.receiveCode = receiveCode;
    }

    public String getSellerIdentifyNo() {
        return sellerIdentifyNo;
    }

    public void setSellerIdentifyNo(String sellerIdentifyNo) {
        this.sellerIdentifyNo = sellerIdentifyNo;
    }

    public Double getRemainingRedAmount() {
        return remainingRedAmount;
    }

    public void setRemainingRedAmount(Double remainingRedAmount) {
        this.remainingRedAmount = remainingRedAmount;
    }

    public String getRegisterNo() {
        return registerNo;
    }

    public void setRegisterNo(String registerNo) {
        this.registerNo = registerNo;
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

    @Override
    public Boolean isNullObject() {
        return false;
    }

    @Override
    public String toString() {
        return toStringHelper(this).add("invoiceEntity", invoiceEntity).add("invoiceRequestSerialNo", invoiceRequestSerialNo).add("taxpayer", taxpayer).add(
                "taxpayerEDNo", taxpayerEDNo).add("taxOfficeRegCode", taxOfficeRegCode).add("agentInvoiceFlag", agentInvoiceFlag).add("sampleInvoiceCode",
                sampleInvoiceCode).add("billingItem", billingItem).add("sellerTaxpayerIdentifyNo", sellerTaxpayerIdentifyNo).add("sellerName", sellerName).add(
                "buyerName", buyerName).add("buyerTaxpayerIdentifyNo", buyerTaxpayerIdentifyNo).add("buyerAddress", buyerAddress).add("buyerFixedPhone",
                buyerFixedPhone).add("buyerMobile", buyerMobile).add("buyerEmail", buyerEmail).add("industryCode", industryCode).add("industryName",
                industryName).add("billingStaff", billingStaff).add("cashier", cashier).add("oldInvoiceCode", oldInvoiceCode).add("oldInvoiceNo", oldInvoiceNo)
                .add("redInvoiceReason", redInvoiceReason).add("memo", memo).add("standbyFieldOne", standbyFieldOne).add("standbyFieldTwo", standbyFieldTwo)
                .add("standbyFieldThree", standbyFieldThree).add("standbyFieldFour", standbyFieldFour).add("standbyFieldFive", standbyFieldFive).add(
                        "specRedInvoiceFlag", specRedInvoiceFlag).add("orderNo", orderNo).add("registerNo", registerNo).add("taxAuthorityCode",
                        taxAuthorityCode).add("itemCount", itemCount).add("mainProductName", mainProductName).add("producedInvoiceCount", producedInvoiceCount)
                .add("buyerEnterpriseTypeCode", buyerEnterpriseTypeCode).add("operatorNo", operatorNo).add("eshopCode", eshopCode).add("taxpayerIdentifyNo",
                        taxpayerIdentifyNo).add("billingDate", billingDate).add("billingType", billingType).add("billingAmount", billingAmount).add(
                        "invoiceSerialNo", invoiceSerialNo).add("fiscalCode", fiscalCode).add("twoDimensionCode", twoDimensionCode).add("invoiceKindCode",
                        invoiceKindCode).add("invoiceCode", invoiceCode).add("invoiceNo", invoiceNo).add("pdfPath", pdfPath).add("operateCount", operateCount)
                .add("invoiceStatus", invoiceStatus).add("signatureId", signatureId).add("createdTime", createdTime).add("errorMsg", errorMsg).add(
                        "payCompany", payCompany).add("payCode", payCode).add("receiveCode", receiveCode).add("sellerIdentifyNo", sellerIdentifyNo).add(
                        "remainingRedAmount", remainingRedAmount).add("infoClientBankAccount", infoClientBankAccount).add("infoClientAddressPhone",
                        infoClientAddressPhone).add("infoSellerBankAccount", infoSellerBankAccount).add("infoSellerAddressPhone", infoSellerAddressPhone).add(
                        "infoChecker", infoChecker).add("infoListName", infoListName).add("infoAmount", infoAmount).add("infoTaxAmount", infoTaxAmount).add(
                        "ciphertext", ciphertext).add("checkCode", checkCode).toString();

    }
}
