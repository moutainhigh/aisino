package com.aisino.protocol.bean;

import com.google.common.base.MoreObjects;

/**
 * Created by Martin.Ou on 2014/11/11.
 */
public class REQUEST_COMMON_DOWNLOAD_CA {
    private String DSPTBM;
    private String DSPTZCM;
    private String NSRSBH;
    private String NSRSQM;

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

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("DSPTBM", DSPTBM)
                .add("DSPTZCM", DSPTZCM)
                .add("NSRSBH", NSRSBH)
                .add("NSRSQM", NSRSQM)
                .toString();
    }
}
