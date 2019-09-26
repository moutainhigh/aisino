package com.aisino.protocol.bean;

/**
 * 发票推送协议Bean
 * 将发票信息推送给企业
 * <p/>
 * Created by Bourne.Lv on 2014/12/01.
 */
public class REQUEST_COMMON_FPJGXX {

    private String FPQQLSH;// 发票请求唯一流水号
    private String DDH;// 订单号
    private String KPLSH;// 开票流水号
    private String FWM;// 发票防伪码
    private String EWM;// 发票二维码
    private String FPZL_DM;// 发票种类代码
    private String FP_DM;// 发票代码
    private String FP_HM;// 发票号码
    private String KPRQ;//开票日期
    private String KPLX;// 开票类型
    private String PDF_FILE;// Base64（pdf文件）
    private String PDF_URL;// pdf下载路径
    private String CZDM;// 操作代码
    private String RETURNCODE;// 结果代码
    private String RETURNMESSAGE;// 结果描述

    public String getFPQQLSH() {
        return FPQQLSH;
    }

    public void setFPQQLSH(String FPQQLSH) {
        this.FPQQLSH = FPQQLSH;
    }

    public String getDDH() {
        return DDH;
    }

    public void setDDH(String DDH) {
        this.DDH = DDH;
    }

    public String getKPLSH() {
        return KPLSH;
    }

    public void setKPLSH(String KPLSH) {
        this.KPLSH = KPLSH;
    }

    public String getFWM() {
        return FWM;
    }

    public void setFWM(String FWM) {
        this.FWM = FWM;
    }

    public String getEWM() {
        return EWM;
    }

    public void setEWM(String EWM) {
        this.EWM = EWM;
    }

    public String getFPZL_DM() {
        return FPZL_DM;
    }

    public void setFPZL_DM(String FPZL_DM) {
        this.FPZL_DM = FPZL_DM;
    }

    public String getFP_DM() {
        return FP_DM;
    }

    public void setFP_DM(String FP_DM) {
        this.FP_DM = FP_DM;
    }

    public String getFP_HM() {
        return FP_HM;
    }

    public void setFP_HM(String FP_HM) {
        this.FP_HM = FP_HM;
    }

    public String getKPRQ() {
        return KPRQ;
    }

    public void setKPRQ(String KPRQ) {
        this.KPRQ = KPRQ;
    }

    public String getKPLX() {
        return KPLX;
    }

    public void setKPLX(String KPLX) {
        this.KPLX = KPLX;
    }

    public String getPDF_FILE() {
        return PDF_FILE;
    }

    public void setPDF_FILE(String PDF_FILE) {
        this.PDF_FILE = PDF_FILE;
    }

    public String getPDF_URL() {
        return PDF_URL;
    }

    public void setPDF_URL(String PDF_URL) {
        this.PDF_URL = PDF_URL;
    }

    public String getCZDM() {
        return CZDM;
    }

    public void setCZDM(String CZDM) {
        this.CZDM = CZDM;
    }

    public String getRETURNCODE() {
        return RETURNCODE;
    }

    public void setRETURNCODE(String RETURNCODE) {
        this.RETURNCODE = RETURNCODE;
    }

    public String getRETURNMESSAGE() {
        return RETURNMESSAGE;
    }

    public void setRETURNMESSAGE(String RETURNMESSAGE) {
        this.RETURNMESSAGE = RETURNMESSAGE;
    }
}