package com.aisino.protocol.bean;

import static com.google.common.base.MoreObjects.toStringHelper;

/**
 * Created by Martin.Ou on 2014/11/14.
 */
public class RESPONSE_COMMON_DOWNLOAD_CA {
    /**
     * 电商平台编码
     */
    private String DSPTBM;
    /**
     * 电商平台注册码
     */

    private String DSPTZCM;
    /**
     * 纳税人识别号
     */

    private String NSRSBH;
    /**
     * 纳税人授权码
     */

    private String NSRSQM;
    /**
     * Cer文件
     */

    private String CER_File;
    /**
     * Pfx文件
     */

    private String PFX_File;

    /***
     *   Pfx证书密码
     */
    private String PFX_Key;
    /**
     * Trust文件
     */

    private String TRUST_File;

    public String getDSPTBM() {
        return DSPTBM;
    }

    public void setDSPTBM(String DSPTBM) {
        this.DSPTBM = DSPTBM;
    }

    public String getDSPTZCM() {
        return DSPTZCM;
    }

    public void setDSPTZCM(String DSPTZCM) {
        this.DSPTZCM = DSPTZCM;
    }

    public String getNSRSBH() {
        return NSRSBH;
    }

    public void setNSRSBH(String NSRSBH) {
        this.NSRSBH = NSRSBH;
    }

    public String getNSRSQM() {
        return NSRSQM;
    }

    public void setNSRSQM(String NSRSQM) {
        this.NSRSQM = NSRSQM;
    }



    public String getCER_File() {
        return CER_File;
    }

    public void setCER_File(String CER_File) {
        this.CER_File = CER_File;
    }

    public String getPFX_File() {
        return PFX_File;
    }

    public void setPFX_File(String PFX_File) {
        this.PFX_File = PFX_File;
    }

    public String getTRUST_File() {
        return TRUST_File;
    }

    public void setTRUST_File(String TRUST_File) {
        this.TRUST_File = TRUST_File;
    }

    public String getPFX_Key() {
        return PFX_Key;
    }

    public void setPFX_Key(String PFX_Key) {
        this.PFX_Key = PFX_Key;
    }

    @Override
    public String toString() {
        return toStringHelper(this)
                .add("DSPTBM", DSPTBM)
                .add("DSPTZCM", DSPTZCM)
                .add("NSRSBH", NSRSBH)
                .add("NSRSQM", NSRSQM)
                .add("CER_File", CER_File)
                .add("PFX_File", PFX_File)
                .add("TRUST_File", TRUST_File)
                .add("PFX_Key", PFX_Key)
                .toString();
    }
}
