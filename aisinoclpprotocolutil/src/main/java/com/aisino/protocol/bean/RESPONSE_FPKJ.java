package com.aisino.protocol.bean;

/**
 * 
 * <p>
 * [描述信息：发票赋码结果的返回协议bean]
 * </p>
 * 
 * @author 张双超
 * @version 2015年3月11日 17:26:00
 */
public class RESPONSE_FPKJ extends RESPONSE_BEAN {
    private String HJBHSJE;// 合计不含税金额
    private String HJSE;// 合计税额
    private String KPRQ;// 开票日期
    private String SSYF;// 所属月份
    private String FP_DM;// 发票代码
    private String FP_HM;// 发票号码
    private String XHQDBZ;// 销货清单标志
    private String RETCODE;// 返回编码
    private String FWMW;// 防伪密文
    private String JYM;// 校验码
    private String SZQM;// 数字签名
    private String EWM;// 二维码
    private String RETURNCODE;// 结果代码
    private String RETURNMESSAGE;// 结果描述

    public String getHJBHSJE() {
        return HJBHSJE;
    }

    public void setHJBHSJE(String hJBHSJE) {
        HJBHSJE = hJBHSJE;
    }

    public String getHJSE() {
        return HJSE;
    }

    public void setHJSE(String hJSE) {
        HJSE = hJSE;
    }

    public String getKPRQ() {
        return KPRQ;
    }

    public void setKPRQ(String kPRQ) {
        KPRQ = kPRQ;
    }

    public String getSSYF() {
        return SSYF;
    }

    public void setSSYF(String sSYF) {
        SSYF = sSYF;
    }

    public String getFP_DM() {
        return FP_DM;
    }

    public void setFP_DM(String fPDM) {
        FP_DM = fPDM;
    }

    public String getFP_HM() {
        return FP_HM;
    }

    public void setFP_HM(String fPHM) {
        FP_HM = fPHM;
    }

    public String getXHQDBZ() {
        return XHQDBZ;
    }

    public void setXHQDBZ(String xHQDBZ) {
        XHQDBZ = xHQDBZ;
    }

    public String getRETCODE() {
        return RETCODE;
    }

    public void setRETCODE(String rETCODE) {
        RETCODE = rETCODE;
    }

    public String getFWMW() {
        return FWMW;
    }

    public void setFWMW(String fWMW) {
        FWMW = fWMW;
    }

    public String getJYM() {
        return JYM;
    }

    public void setJYM(String jYM) {
        JYM = jYM;
    }

    public String getSZQM() {
        return SZQM;
    }

    public void setSZQM(String sZQM) {
        SZQM = sZQM;
    }

    public String getRETURNCODE() {
        return RETURNCODE;
    }

    public void setRETURNCODE(String rETURNCODE) {
        RETURNCODE = rETURNCODE;
    }

    public String getRETURNMESSAGE() {
        return RETURNMESSAGE;
    }

    public void setRETURNMESSAGE(String rETURNMESSAGE) {
        RETURNMESSAGE = rETURNMESSAGE;
    }

    public String getEWM() {
        return EWM;
    }

    public void setEWM(String eWM) {
        EWM = eWM;
    }
    

}
