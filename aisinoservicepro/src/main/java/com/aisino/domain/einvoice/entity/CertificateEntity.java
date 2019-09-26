package com.aisino.domain.einvoice.entity;

import com.aisino.domain.AbstractBaseDomain;

import static com.google.common.base.MoreObjects.toStringHelper;

/**
 * Created by Bourne.Lv on 2014/11/14.
 * <p/>
 * 客户端证书实体(t_client_certificate)
 *
 * @see com.aisino.domain.AbstractBaseDomain
 */
public final class CertificateEntity extends AbstractBaseDomain {

    private static final long serialVersionUID = -610265234170890098L;

    /**
     * 公钥(cer)存放路径
     */
    private String publicKey;

    /**
     * 私钥(pfx)存放路径
     */
    private String privateKey;

    /**
     * 公共信任链
     */
    private String trust;

    /**
     * 电商平台编码(dsptbm)
     */
    private String eshopCode;

    /**
     * 税纳人识别号(NSRSBH)
     */
    private String taxpayerIdentifyNo;

    /**
     * 私钥密钥库密码
     */
    private String password;

    /**
     * 有效标识
     */
    private String available;

    /**
     * 录入日期
     */
    private String createdDate;

    /**
     * 51平台编码
     */
    private String platformCode;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    @Override
    public Boolean isNullObject() {
        return taxpayerIdentifyNo == null;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public String getTrust() {
        return trust;
    }

    public void setTrust(String trust) {
        this.trust = trust;
    }

    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getPlatformCode() {
        return platformCode;
    }

    public void setPlatformCode(String platformCode) {
        this.platformCode = platformCode;
    }

    @Override
    public String toString() {
        return toStringHelper(this).
                add("publicKey", publicKey).
                add("privateKey", privateKey).
                add("trust", trust).
                add("eshopCode", eshopCode).
                add("taxpayerIdentifyNo", taxpayerIdentifyNo).
                add("password", password).
                add("available", available).
                add("createdDate", createdDate).
                add("platformCode",platformCode).
                toString();
    }
}
