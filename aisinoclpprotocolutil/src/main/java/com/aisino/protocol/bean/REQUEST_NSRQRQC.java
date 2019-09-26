package com.aisino.protocol.bean;

public class REQUEST_NSRQRQC implements REQUEST_BEAN{
	private String NSRSBH;	//纳税人识别号
	private String NSRDZDAH;
	private String DSPTBM;

	public String getDSPTBM() {
		return DSPTBM;
	}

	public void setDSPTBM(String dSPTBM) {
		DSPTBM = dSPTBM;
	}
	
	public String getNSRDZDAH() {
		return NSRDZDAH;
	}

	public void setNSRDZDAH(String nSRDZDAH) {
		NSRDZDAH = nSRDZDAH;
	}

	public String getNSRSBH() {
		return NSRSBH;
	}

	public void setNSRSBH(String nSRSBH) {
		NSRSBH = nSRSBH;
	}
	
}
