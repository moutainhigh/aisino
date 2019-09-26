package com.aisino.protocol.bean;

public class REQUEST_GETFPSC implements REQUEST_BEAN {
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
