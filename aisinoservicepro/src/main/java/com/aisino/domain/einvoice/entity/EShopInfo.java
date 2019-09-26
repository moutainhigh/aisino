package com.aisino.domain.einvoice.entity;

import com.aisino.domain.AbstractBaseDomain;
import com.aisino.domain.SystemConfig;
import com.google.common.base.Strings;
import org.joda.time.DateTime;

import java.sql.Timestamp;

import static com.google.common.base.MoreObjects.toStringHelper;

/**
 * Created by Bourne.Lv on 2014/09/04.
 * <p/>
 * 电商平台信息实体(Dsptxxkt)
 *
 * @see com.aisino.domain.AbstractBaseDomain
 */
public final class EShopInfo extends AbstractBaseDomain {

    private static final long serialVersionUID = -153378635676277521L;

    /**
     * 51平台编码
     */
    private String platformCode;

    /**
     * 电商平台编码(dsptbm)
     */
    private String eshopCode;

    /**
     * 电商平台名称(dsptmc)
     */
    private String eshopName;

    /**
     * 办理人名称(blrmc)
     */
    private String transactorName;

    /**
     * 办理人证件号码(blrzjhm)
     */
    private String transactorCertNo;

    /**
     * 办理人固定电话(blrgddh)
     */
    private String transactorTel;

    /**
     * 办理人手机号(blrsjh)
     */
    private String transactorPhone;

    /**
     * 办理人邮箱(blryx)
     */
    private String transactorEmail;

    /**
     * 注册电商时间(zcdssj)
     */
    private Timestamp eshopRegisterDate;

    /**
     * 注册状态(zczt)
     */
    private String registerStatus;

    /**
     * 注册号(zch)
     */
    private String registerNo;

    /**
     * 所在税务机关代码(sz_swjg_dm)
     */
    private String taxAuthorityCode;

    /**
     * 税务机关名称(swjgmc)
     */
    private String taxAuthorityName;

    /**
     * icp(icp)
     */
    private String icp;

    /**
     * 电商平台IP(DSPTIP)
     */
    private String eShopIp;

    /**
     * 电商平台网站域名(DSWZYM)
     */
    private String eShopDomain;

    /**
     * 主办单位名称(ZBDWMC)
     */
    private String hostCompany;

    /**
     * 主办单位地址(ZBDWDZ)
     */
    private String hostAddress;

    /**
     * 主办单位税务登记证号(ZBDWSWDJZH)
     */
    private String hostCompanyTaxRegisterNo;

    /**
     * 法人代表(FRDB)
     */
    private String legalName;

    /**
     * 法人代表证件号码(FRDBZJHM)
     */
    private String legalNameCertNo;

    /**
     * 注册电商时间字符型(zcdssj)
     */
    private String eshopRegisterDateValue;

    /**
     * 虚拟电商平台标识位( 1是0否)
     */
    private String virtualFlag;


    public String getPlatformCode() {
        return platformCode;
    }

    public void setPlatformCode(String platformCode) {
        this.platformCode = platformCode;
    }

    public String getVirtualFlag() {
        return virtualFlag;
    }

    public void setVirtualFlag(String virtualFlag) {
        this.virtualFlag = virtualFlag;
    }

    public String getTaxAuthorityName() {
        return taxAuthorityName;
    }

    public void setTaxAuthorityName(String taxAuthorityName) {
        this.taxAuthorityName = taxAuthorityName;
    }

    public String getEshopCode() {
        return eshopCode;
    }

    public void setEshopCode(String eshopCode) {
        this.eshopCode = eshopCode;
    }

    public String getEshopName() {
        return eshopName;
    }

    public void setEshopName(String eshopName) {
        this.eshopName = eshopName;
    }

    public String getTransactorName() {
        return transactorName;
    }

    public void setTransactorName(String transactorName) {
        this.transactorName = transactorName;
    }

    public String getTransactorCertNo() {
        return transactorCertNo;
    }

    public void setTransactorCertNo(String transactorCertNo) {
        this.transactorCertNo = transactorCertNo;
    }

    public String getTransactorTel() {
        return transactorTel;
    }

    public void setTransactorTel(String transactorTel) {
        this.transactorTel = transactorTel;
    }

    public String getTransactorPhone() {
        return transactorPhone;
    }

    public void setTransactorPhone(String transactorPhone) {
        this.transactorPhone = transactorPhone;
    }

    public String getTransactorEmail() {
        return transactorEmail;
    }

    public void setTransactorEmail(String transactorEmail) {
        this.transactorEmail = transactorEmail;
    }

    public Timestamp getEshopRegisterDate() {
        return obtainValidTimestamp(eshopRegisterDate);
    }

    public void setEshopRegisterDate(Timestamp eshopRegisterDate) {
        this.eshopRegisterDate = obtainValidTimestamp(eshopRegisterDate);
    }

    public String getRegisterStatus() {
        return registerStatus;
    }

    public void setRegisterStatus(String registerStatus) {
        this.registerStatus = registerStatus;
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

    public String getZcdssjFormat() {
        if (null != eshopRegisterDate) {
            return new DateTime(eshopRegisterDate).toString(SystemConfig.default_date_format);
        } else {
            return "";
        }
    }


    public String getIcp() {
        return icp;
    }

    public void setIcp(String icp) {
        this.icp = icp;
    }

    public String geteShopIp() {
        return eShopIp;
    }

    public void seteShopIp(String eShopIp) {
        this.eShopIp = eShopIp;
    }

    public String geteShopDomain() {
        return eShopDomain;
    }

    public void seteShopDomain(String eShopDomain) {
        this.eShopDomain = eShopDomain;
    }

    public String getHostCompany() {
        return hostCompany;
    }

    public void setHostCompany(String hostCompany) {
        this.hostCompany = hostCompany;
    }

    public String getHostCompanyTaxRegisterNo() {
        return hostCompanyTaxRegisterNo;
    }

    public void setHostCompanyTaxRegisterNo(String hostCompanyTaxRegisterNo) {
        this.hostCompanyTaxRegisterNo = hostCompanyTaxRegisterNo;
    }

    public String getLegalName() {
        return legalName;
    }

    public void setLegalName(String legalName) {
        this.legalName = legalName;
    }

    public String getLegalNameCertNo() {
        return legalNameCertNo;
    }

    public void setLegalNameCertNo(String legalNameCertNo) {
        this.legalNameCertNo = legalNameCertNo;
    }

    public String getEshopRegisterDateValue() {
        return eshopRegisterDateValue;
    }

    public void setEshopRegisterDateValue(String eshopRegisterDateValue) {
        this.eshopRegisterDateValue = eshopRegisterDateValue;
    }

    public String getHostAddress() {
        return hostAddress;
    }

    public void setHostAddress(String hostAddress) {
        this.hostAddress = hostAddress;
    }

    @Override
    public Boolean isNullObject() {
        return getId() != null && !Strings.isNullOrEmpty(eshopCode);
    }

    @Override
    public String toString() {
        return toStringHelper(this).
                add("platformCode", platformCode).
                add("eshopCode", eshopCode).
                add("eshopName", eshopName).
                add("transactorName", transactorName).
                add("transactorCertNo", transactorCertNo).
                add("transactorTel", transactorTel).
                add("transactorPhone", transactorPhone).
                add("transactorEmail", transactorEmail).
                add("eshopRegisterDate", eshopRegisterDate).
                add("registerStatus", registerStatus).
                add("registerNo", registerNo).
                add("taxAuthorityCode", taxAuthorityCode).
                add("taxAuthorityName", taxAuthorityName).
                add("icp", icp).
                add("eShopIp", eShopIp).
                add("eShopDomain", eShopDomain).
                add("hostCompany", hostCompany).
                add("hostAddress", hostAddress).
                add("hostCompanyTaxRegisterNo", hostCompanyTaxRegisterNo).
                add("legalName", legalName).
                add("legalNameCertNo", legalNameCertNo).
                add("eshopRegisterDateValue", eshopRegisterDateValue).
                add("virtualFlag", virtualFlag).
                toString();
    }
}
