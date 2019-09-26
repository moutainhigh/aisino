package com.aisino.protocol.bean;

/**
 * 电商企业请求
 *
 * @author 梁国栋 2014-10-23
 */
public class REQUEST_COMMON_DSQYXX {

    private String NSRSBH;// 纳税人识别号
    private String DSPTBM;// 电商平台编码
    private String QQLX;//请求类型(0：获取电商平台信息  1：纳税人初始安装注册)

    public String getNSRSBH() {
        return NSRSBH;
    }

    public void setNSRSBH(String nSRSBH) {
        NSRSBH = nSRSBH;
    }

    public String getDSPTBM() {
        return DSPTBM;
    }

    public void setDSPTBM(String dSPTBM) {
        DSPTBM = dSPTBM;
    }

    public String getQQLX() {
        return QQLX;
    }

    public void setQQLX(String qQLX) {
        this.QQLX = qQLX;
    }
}
