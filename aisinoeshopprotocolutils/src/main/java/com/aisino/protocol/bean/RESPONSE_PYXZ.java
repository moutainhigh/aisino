package com.aisino.protocol.bean;


import com.aisino.protocol.bean.*;

public class RESPONSE_PYXZ extends com.aisino.protocol.bean.RESPONSE_BEAN {
    private String NSRSBH;  /*纳税人识别号*/
    private String NSRDZDAH;  /*纳税人电子档案号*/
    private String DSPTBM;  /*电商平台编码*/
    private String FP_DM;  /*发票代码*/
    private String FPQH;  /*发票起号*/
    private String FPZH;  /*发票止号*/
    private String FPZL_DM;  /*发票种类代码*/
    private String FS;  /*份数*/
    private String SWJGDM;  /*税务机构代码*/
    private String FSRQ;  /*发售日期*/
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
    public String getNSRSBH() {
        return NSRSBH;
    }
    public void setNSRSBH(String nsrsbh) {
        NSRSBH = nsrsbh;
    }
    public String getNSRDZDAH() {
        return NSRDZDAH;
    }
    public void setNSRDZDAH(String nsrdzdah) {
        NSRDZDAH = nsrdzdah;
    }
    public String getDSPTBM() {
        return DSPTBM;
    }
    public void setDSPTBM(String dsptbm) {
        DSPTBM = dsptbm;
    }
    
    public String getFPQH() {
        return FPQH;
    }
    public void setFPQH(String fpqh) {
        FPQH = fpqh;
    }
    public String getFPZH() {
        return FPZH;
    }
    public void setFPZH(String fpzh) {
        FPZH = fpzh;
    }
    
    public String getFP_DM() {
        return FP_DM;
    }
    public void setFP_DM(String fp_dm) {
        FP_DM = fp_dm;
    }
    public String getFPZL_DM() {
        return FPZL_DM;
    }
    public void setFPZL_DM(String fpzl_dm) {
        FPZL_DM = fpzl_dm;
    }
    public String getFS() {
        return FS;
    }
    public void setFS(String fs) {
        FS = fs;
    }
    public String getSWJGDM() {
        return SWJGDM;
    }
    public void setSWJGDM(String swjgdm) {
        SWJGDM = swjgdm;
    }
    public String getFSRQ() {
        return FSRQ;
    }
    public void setFSRQ(String fsrq) {
        FSRQ = fsrq;
    }
}
