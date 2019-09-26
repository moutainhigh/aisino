package com.aisino.domain.security;

/**
 * Created by Martin.Ou on 2014/9/4.
 */

import com.google.common.base.MoreObjects;

public final class SecurityCode {
    private String fscodeVersion;
    private String dcodeVersion;
    private String nsrsbhLength;
    private String nsrsbh;
    private String kprq;
    private String fpdm;
    private String fphm;
    private String je;
    private String swjgbm;
    private String kplx;
    private String dsptbm;

    public SecurityCode(String fscodeVersion, String dcodeVersion, String nsrsbhLength, String nsrsbh, String kprq,
                        String fpdm, String fphm, String je, String swjgbm, String kplx, String dsptbm) {
        this.fscodeVersion = fscodeVersion;
        this.dcodeVersion = dcodeVersion;
        this.nsrsbhLength = nsrsbhLength;
        this.nsrsbh = nsrsbh;
        this.kprq = kprq;
        this.fpdm = fpdm;
        this.fphm = fphm;
        this.je = je;
        this.swjgbm = swjgbm;
        this.kplx = kplx;
        this.dsptbm = dsptbm;
    }

    public String getFscodeVersion() {
        return fscodeVersion;
    }

    public String getDcodeVersion() {
        return dcodeVersion;
    }

    public String getNsrsbhLength() {
        return nsrsbhLength;
    }

    public String getNsrsbh() {
        return nsrsbh;
    }

    public String getKprq() {
        return kprq;
    }

    public String getFpdm() {
        return fpdm;
    }

    public String getFphm() {
        return fphm;
    }

    public String getJe() {
        return je;
    }

    public String getSwjgbm() {
        return swjgbm;
    }

    public String getKplx() {
        return kplx;
    }

    public String getDsptbm() {
        return dsptbm;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("fscodeVersion", fscodeVersion)
                .add("dcodeVersion", dcodeVersion)
                .add("nsrsbhLength", nsrsbhLength)
                .add("nsrsbh", nsrsbh)
                .add("kprq", kprq)
                .add("fpdm", fpdm)
                .add("fphm", fphm)
                .add("je", je)
                .add("swjgbm", swjgbm)
                .add("kplx", kplx)
                .add("dsptbm", dsptbm)
                .toString();
    }
}
