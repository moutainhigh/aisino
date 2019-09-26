package com.aisino.protocol.bean;

public class REQUEST_DSQYZT implements REQUEST_BEAN{
	private String DSPTBM;  /*电商平台编码*/
	private String NSRSBH;  //纳税人识别号
	private String NSRDZDAH; //纳税人电子档案号

	public String getDSPTBM() {
		return DSPTBM;
	}

	public void setDSPTBM(String dsptbm) {
		DSPTBM = dsptbm;
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
    
}
