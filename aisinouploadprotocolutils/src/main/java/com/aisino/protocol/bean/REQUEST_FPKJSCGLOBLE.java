package com.aisino.protocol.bean;


public class REQUEST_FPKJSCGLOBLE implements REQUEST_BEAN{
    private String FPKJZLS;    /*发票开具总数量*/
    private String FPSCLSH;    /*发票上传流水号*/
    private String DSPTBM;    /*电商平台编码*/
    private REQUEST_FPKJ[] REQUEST_FPKJS;    /*发票开具集合*/
    public String getFPKJZLS() {
        return FPKJZLS;
    }
    public void setFPKJZLS(String fpkjzls) {
        FPKJZLS = fpkjzls;
    }
    public String getFPSCLSH() {
        return FPSCLSH;
    }
    public void setFPSCLSH(String fpsclsh) {
        FPSCLSH = fpsclsh;
    }
    public String getDSPTBM() {
        return DSPTBM;
    }
    public void setDSPTBM(String dsptbm) {
        DSPTBM = dsptbm;
    }
    public REQUEST_FPKJ[] getREQUEST_FPKJS() {
        return REQUEST_FPKJS;
    }
    public void setREQUEST_FPKJS(REQUEST_FPKJ[] rEQUEST_FPKJS) {
        REQUEST_FPKJS = rEQUEST_FPKJS;
    }
}
