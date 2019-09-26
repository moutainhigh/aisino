package com.aisino.protocol.bean;
/**
 * 电商企业查询请求信息
 * @author scott.li
 * @date： Aug 13, 2013 3:12:19 PM
 */
public class REQUEST_DSQYXX implements REQUEST_BEAN{
    private String NSRSBH;  /*纳税人识别号*/
    private String DSPTBM;   /*电商平台编码*/
    public String getNSRSBH() {
        return NSRSBH;
    }
    public void setNSRSBH(String nsrsbh) {
        NSRSBH = nsrsbh;
    }
    public String getDSPTBM() {
        return DSPTBM;
    }
    public void setDSPTBM(String dsptbm) {
        DSPTBM = dsptbm;
    }
}
