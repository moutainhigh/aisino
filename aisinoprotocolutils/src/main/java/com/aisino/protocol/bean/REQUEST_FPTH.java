package com.aisino.protocol.bean;

public class REQUEST_FPTH implements REQUEST_BEAN {
	private String DSPTBM;
	private String NSRSBH;
	private String NSRDZDAH;
	private String THSJ;
	private String THYY;
	private THXX[] THXXS;

	public String getDSPTBM() {
		return DSPTBM;
	}

	public void setDSPTBM(String dSPTBM) {
		DSPTBM = dSPTBM;
	}

	public String getNSRSBH() {
		return NSRSBH;
	}

	public void setNSRSBH(String nSRSBH) {
		NSRSBH = nSRSBH;
	}

	public String getNSRDZDAH() {
		return NSRDZDAH;
	}

	public void setNSRDZDAH(String nSRDZDAH) {
		NSRDZDAH = nSRDZDAH;
	}

	public String getTHSJ() {
		return THSJ;
	}

	public void setTHSJ(String tHSJ) {
		THSJ = tHSJ;
	}

	public String getTHYY() {
		return THYY;
	}

	public void setTHYY(String tHYY) {
		THYY = tHYY;
	}

	public THXX[] getTHXXS() {
		return THXXS;
	}

	public void setTHXXS(THXX[] tHXXS) {
		THXXS = tHXXS;
	}
}
