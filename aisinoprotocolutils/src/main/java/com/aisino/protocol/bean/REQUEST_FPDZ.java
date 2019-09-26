package com.aisino.protocol.bean;

public class REQUEST_FPDZ implements REQUEST_BEAN {
	private String DZLSH;
	private String DSBM;
	private String KPSL;
	private DZXX[] DZXXS;

	public String getDZLSH() {
		return DZLSH;
	}

	public void setDZLSH(String dZLSH) {
		DZLSH = dZLSH;
	}

	public String getDSBM() {
		return DSBM;
	}

	public void setDSBM(String dSBM) {
		DSBM = dSBM;
	}

	public String getKPSL() {
		return KPSL;
	}

	public void setKPSL(String kPSL) {
		KPSL = kPSL;
	}

	public DZXX[] getDZXXS() {
		return DZXXS;
	}

	public void setDZXXS(DZXX[] dZXXS) {
		DZXXS = dZXXS;
	}

	public String getDSPTBM() {
		return DSBM;
	}
}
