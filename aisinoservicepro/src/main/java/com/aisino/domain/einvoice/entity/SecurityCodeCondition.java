package com.aisino.domain.einvoice.entity;

import static com.google.common.base.MoreObjects.toStringHelper;

/**
 * Created by Martin.Ou on 2014/9/4.
 */
public final class SecurityCodeCondition {

    /*
     * fscodeVersion
     */
    private String fscodeVersion;

    /*
     * dcodeVersion
     */
    private String dcodeVersion;

    /*
     * 税纳人识别号长度
     */
    private String nsrsbhLength;

    /*
     * 税纳人识别号(NSRSBH)
     */
    private String nsrsbh;

    /*
     * 开票日期
     */
    private String kprq;

    /*
     * 发票代码
     */
    private String fpdm;

    /*
     *发票号码
     */
    private String fphm;

    /*
     * 金额
     */
    private String money;

    /*
     * 税务机关代码
     */
    private String swjgbm;

    /*
     * 开票类型 (KPLX,1正票 2红票)
     */
    private String kplx;

    /*
     * 电商平台编码
     */
    private String dsptbm;

    public String getFscodeVersion() {
        return fscodeVersion;
    }

    public void setFscodeVersion(String fscodeVersion) {
        this.fscodeVersion = fscodeVersion;
    }

    public String getDcodeVersion() {
        return dcodeVersion;
    }

    public void setDcodeVersion(String dcodeVersion) {
        this.dcodeVersion = dcodeVersion;
    }

    public String getNsrsbhLength() {
        return nsrsbhLength;
    }

    public void setNsrsbhLength(String nsrsbhLength) {
        this.nsrsbhLength = nsrsbhLength;
    }

    public String getNsrsbh() {
        return nsrsbh;
    }

    public void setNsrsbh(String nsrsbh) {
        this.nsrsbh = nsrsbh;
    }

    public String getKprq() {
        return kprq;
    }

    public void setKprq(String kprq) {
        this.kprq = kprq;
    }

    public String getFpdm() {
        return fpdm;
    }

    public void setFpdm(String fpdm) {
        this.fpdm = fpdm;
    }

    public String getFphm() {
        return fphm;
    }

    public void setFphm(String fphm) {
        this.fphm = fphm;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getSwjgbm() {
        return swjgbm;
    }

    public void setSwjgbm(String swjgbm) {
        this.swjgbm = swjgbm;
    }

    public String getKplx() {
        return kplx;
    }

    public void setKplx(String kplx) {
        this.kplx = kplx;
    }

    public String getDsptbm() {
        return dsptbm;
    }

    public void setDsptbm(String dsptbm) {
        this.dsptbm = dsptbm;
    }

    @Override
    public String toString() {
        return toStringHelper(this)
                .add("fscodeVersion", fscodeVersion)
                .add("dcodeVersion", dcodeVersion)
                .add("nsrsbhLength", nsrsbhLength)
                .add("nsrsbh", nsrsbh)
                .add("kprq", kprq)
                .add("fpdm", fpdm)
                .add("fphm", fphm)
                .add("money", money)
                .add("swjgbm", swjgbm)
                .add("kplx", kplx)
                .add("dsptbm", dsptbm)
                .toString();
    }
}
