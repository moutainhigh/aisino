package com.aisino.protocol.bean;
/**
 * 电商企业票种核定信息返回类
 * @author scott.li
 * @date： Aug 13, 2013 4:49:42 PM
 */
public class RESPONSE_DSQYPZHDXX extends RESPONSE_BEAN{
    private String NSRSBH;  /*纳税人识别号*/
    private String NSRDZDAH;  /*纳税人电子档案号*/
    /*private String DSPTBM;  电商平台编码*/
    private String FPZL_DM;  /*发票种类代码*/
    private String FPZL_MC;  /*发票种类名称*/
    private String MYGPZGSL;  /*每月购票最高数量*/
    private String MYCPZGSL;  /*每月持票最高数量*/
    private String KPZGXE;  /*开票最高限额*/
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
    /*public String getDSPTBM() {
        return DSPTBM;
    }
    public void setDSPTBM(String dsptbm) {
        DSPTBM = dsptbm;
    }*/
    
    public String getFPZL_DM() {
        return FPZL_DM;
    }
    public void setFPZL_DM(String fpzl_dm) {
        FPZL_DM = fpzl_dm;
    }
    public String getFPZL_MC() {
        return FPZL_MC;
    }
    public void setFPZL_MC(String fpzl_mc) {
        FPZL_MC = fpzl_mc;
    }
    public String getMYGPZGSL() {
        return MYGPZGSL;
    }
    public void setMYGPZGSL(String mygpzgsl) {
        MYGPZGSL = mygpzgsl;
    }
    public String getMYCPZGSL() {
        return MYCPZGSL;
    }
    public void setMYCPZGSL(String mycpzgsl) {
        MYCPZGSL = mycpzgsl;
    }
    public String getKPZGXE() {
        return KPZGXE;
    }
    public void setKPZGXE(String kpzgxe) {
        KPZGXE = kpzgxe;
    }
}
