package com.aisino.protocol.bean;

public class RESPONSE_FPCY extends RESPONSE_BEAN{
    private String FPZL_MC;// 发票种类名称
    private String KPRQ;// 开票日期
    private String FP_DM;// 发票代码
    private String FP_HM;// 发票号码
    private String KPHJJE;// 开票合计金额
    private String XHFMC;// 销货方名称
    private String FKDW;// 购货方名称
    private String FKF_DM;// 购货方税号
    private String KPLX;// 作废标志
    private String FPZL_DM;// 发票种类代码
    private String RETURNCODE;
    private String RETURNMESSAGE;
    public String getRETURNCODE() {
        return RETURNCODE;
    }
    public void setRETURNCODE(String returncode) {
        RETURNCODE = returncode;
    }
    public String getRETURNMESSAGE() {
        return RETURNMESSAGE;
    }
    public void setRETURNMESSAGE(String returnmessage) {
        RETURNMESSAGE = returnmessage;
    }
    public String getFPZL_MC() {
        return FPZL_MC;
    }

    public void setFPZL_MC(String fpzl_mc) {
        FPZL_MC = fpzl_mc;
    }

    public String getKPRQ() {
        return KPRQ;
    }

    public void setKPRQ(String kprq) {
        KPRQ = kprq;
    }

    public String getFP_DM() {
        return FP_DM;
    }

    public void setFP_DM(String fp_dm) {
        FP_DM = fp_dm;
    }

    public String getFP_HM() {
        return FP_HM;
    }

    public void setFP_HM(String fp_hm) {
        FP_HM = fp_hm;
    }

    public String getKPHJJE() {
        return KPHJJE;
    }

    public void setKPHJJE(String kphjje) {
        KPHJJE = kphjje;
    }

    public String getXHFMC() {
        return XHFMC;
    }

    public void setXHFMC(String xhfmc) {
        XHFMC = xhfmc;
    }

    public String getFKDW() {
        return FKDW;
    }

    public void setFKDW(String fkdw) {
        FKDW = fkdw;
    }

    public String getFKF_DM() {
        return FKF_DM;
    }

    public void setFKF_DM(String fkf_dm) {
        FKF_DM = fkf_dm;
    }

    public String getKPLX() {
        return KPLX;
    }

    public void setKPLX(String kplx) {
        KPLX = kplx;
    }

    public String getFPZL_DM() {
        return FPZL_DM;
    }

    public void setFPZL_DM(String fpzl_dm) {
        FPZL_DM = fpzl_dm;
    }

}
