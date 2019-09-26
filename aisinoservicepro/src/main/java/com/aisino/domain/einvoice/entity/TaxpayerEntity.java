package com.aisino.domain.einvoice.entity;

import com.aisino.domain.AbstractBaseDomain;
import com.google.common.base.Strings;

import static com.google.common.base.MoreObjects.toStringHelper;

/**
 * Created by Schiffer.huang on 2014/09/29.
 * 纳税人节本信息表(dj_nsrxx)
 *
 * @see com.aisino.domain.AbstractBaseDomain
 */
public final class TaxpayerEntity extends AbstractBaseDomain {

    private static final long serialVersionUID = -5064068648062784910L;

    /*
     *纳税人识别号(NSRSBH)
     */
    private String taxpayerIdentifyNo;

    /*
     *纳税人电子档案号(NSRDZDAH)
     */
    private String taxpayerEDNo;

    /*
     *纳税人名称(NSRMC)
     */
    private String taxpayer;

    /*
     *行业代码(HY_DM)
     */
    private String industryCode;

    /*
     *行业名称(HY_MC)
     */
    private String industryName;

    /*
     *税人纳状态(NSRZT_DM)
     */
    private String taxpayerStatusCode;

    /*
     *登记注册类型代码(DJZCLX_DM)
     */
    private String registerTypeCode;

    /*
     *主管税务人员代码(ZGSWRY_DM)
     */
    private String taxChargePersonCode;

    /*
     *法定代表人名称(FDDBRMC)
     */
    private String legalName;

    /*
     *办税人名称(BSRMC)
     */
    private String taxBusinessPerson;

    /*
     *税务登记表类型代码(SWDJBLX_DM)
     */
    private String taxRegisterTableTypeCode;

    /*
     *税务机构代码(SWJG_DM)
     */
    private String taxOfficeRegCode;

    /*
     *生产经营地址(SCJYDZ)
     */
    private String businessAddress;

    /*
     *电商平台编码(DSPTBM)
     */
    private String eshopCode;

    /*
     *所在税务机关代码(SZ_SWJG_DM)
     */
    private String taxAuthorityCode;

    /*
     *开票状态(KPZT 1正常 0停用)
     */
    private String taxpayerInvoiceStatus;

    /*
   *所在电商平台是否可用(0不可用,1可用)
   */
    private String eshopEnabled;

    public String getTaxpayerIdentifyNo() {
        return taxpayerIdentifyNo;
    }

    public void setTaxpayerIdentifyNo(String taxpayerIdentifyNo) {
        this.taxpayerIdentifyNo = taxpayerIdentifyNo;
    }

    public String getTaxpayerEDNo() {
        return taxpayerEDNo;
    }

    public void setTaxpayerEDNo(String taxpayerEDNo) {
        this.taxpayerEDNo = taxpayerEDNo;
    }

    public String getTaxpayer() {
        return taxpayer;
    }

    public void setTaxpayer(String taxpayer) {
        this.taxpayer = taxpayer;
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

    public String getTaxpayerStatusCode() {
        return taxpayerStatusCode;
    }

    public void setTaxpayerStatusCode(String taxpayerStatusCode) {
        this.taxpayerStatusCode = taxpayerStatusCode;
    }

    public String getRegisterTypeCode() {
        return registerTypeCode;
    }

    public void setRegisterTypeCode(String registerTypeCode) {
        this.registerTypeCode = registerTypeCode;
    }

    public String getTaxChargePersonCode() {
        return taxChargePersonCode;
    }

    public void setTaxChargePersonCode(String taxChargePersonCode) {
        this.taxChargePersonCode = taxChargePersonCode;
    }

    public String getLegalName() {
        return legalName;
    }

    public void setLegalName(String legalName) {
        this.legalName = legalName;
    }

    public String getTaxBusinessPerson() {
        return taxBusinessPerson;
    }

    public void setTaxBusinessPerson(String taxBusinessPerson) {
        this.taxBusinessPerson = taxBusinessPerson;
    }

    public String getTaxRegisterTableTypeCode() {
        return taxRegisterTableTypeCode;
    }

    public void setTaxRegisterTableTypeCode(String taxRegisterTableTypeCode) {
        this.taxRegisterTableTypeCode = taxRegisterTableTypeCode;
    }

    public String getTaxOfficeRegCode() {
        return taxOfficeRegCode;
    }

    public void setTaxOfficeRegCode(String taxOfficeRegCode) {
        this.taxOfficeRegCode = taxOfficeRegCode;
    }

    public String getBusinessAddress() {
        return businessAddress;
    }

    public void setBusinessAddress(String businessAddress) {
        this.businessAddress = businessAddress;
    }

    public String getEshopCode() {
        return eshopCode;
    }

    public void setEshopCode(String eshopCode) {
        this.eshopCode = eshopCode;
    }

    public String getTaxAuthorityCode() {
        return taxAuthorityCode;
    }

    public void setTaxAuthorityCode(String taxAuthorityCode) {
        this.taxAuthorityCode = taxAuthorityCode;
    }

    public String getTaxpayerInvoiceStatus() {
        return taxpayerInvoiceStatus;
    }

    public void setTaxpayerInvoiceStatus(String taxpayerInvoiceStatus) {
        this.taxpayerInvoiceStatus = taxpayerInvoiceStatus;
    }


    public String getEshopEnabled() {
        return eshopEnabled;
    }

    public void setEshopEnabled(String eshopEnabled) {
        this.eshopEnabled = eshopEnabled;
    }

    @Override
    public Boolean isNullObject() {
        return getId() == null || Strings.isNullOrEmpty(taxpayerIdentifyNo);
    }

    @Override
    public String toString() {
        return toStringHelper(this)
                .add("taxpayerIdentifyNo", taxpayerIdentifyNo)
                .add("taxpayerEDNo", taxpayerEDNo)
                .add("taxpayer", taxpayer)
                .add("industryCode", industryCode)
                .add("industryName", industryName)
                .add("taxpayerStatusCode", taxpayerStatusCode)
                .add("registerTypeCode", registerTypeCode)
                .add("taxChargePersonCode", taxChargePersonCode)
                .add("legalName", legalName)
                .add("taxBusinessPerson", taxBusinessPerson)
                .add("taxRegisterTableTypeCode", taxRegisterTableTypeCode)
                .add("taxOfficeRegCode", taxOfficeRegCode)
                .add("businessAddress", businessAddress)
                .add("eshopCode", eshopCode)
                .add("taxAuthorityCode", taxAuthorityCode)
                .add("taxpayerInvoiceStatus", taxpayerInvoiceStatus)
                .toString();
    }

}
