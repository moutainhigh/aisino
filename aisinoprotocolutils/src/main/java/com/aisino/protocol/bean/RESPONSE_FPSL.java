package com.aisino.protocol.bean;

public class RESPONSE_FPSL extends RESPONSE_BEAN {
	private String SLDH;
	private String RETURNCODE;
	private String RETURNMESSAGE;

	public String getRETURNCODE() {
		return RETURNCODE;
	}

	public void setRETURNCODE(String rETURNCODE) {
		RETURNCODE = rETURNCODE;
	}

	public String getRETURNMESSAGE() {
		return RETURNMESSAGE;
	}

	public void setRETURNMESSAGE(String rETURNMESSAGE) {
		RETURNMESSAGE = rETURNMESSAGE;
	}

	public String getSLDH() {
		return SLDH;
	}

	public void setSLDH(String sldh) {
		SLDH = sldh;
	}
}
