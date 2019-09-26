package com.aisino.protocol.bean;

/**
 * 
 * 
 * @author zsf
 * @version 1.0
 * @created 2013-11-19 下午07:49:39
 */

public class REQUEST_FPSCJG implements REQUEST_BEAN {
	private String DSPTBM;
	private String FPSCLSH;

	public String getDSPTBM() {
		return DSPTBM;
	}

	public void setDSPTBM(String dSPTBM) {
		DSPTBM = dSPTBM;
	}

	public String getFPSCLSH() {
		return FPSCLSH;
	}

	public void setFPSCLSH(String fPSCLSH) {
		FPSCLSH = fPSCLSH;
	}

}
