package com.aisino.protocol.bean;

public class RESPONSE_DZJG extends RESPONSE_BEAN {
	private String SBSL;
	private DZJG[] DZJGS;
	private String RETURNCODE;
	private String RETURNMESSAGE;

	public String getRETURNCODE() {
		return RETURNCODE;
	}

	public void setRETURNCODE(String returncode) {
		RETURNCODE = returncode;
	}

	public String getRETURNMESSAGE() {
		return RETURNMESSAGE;
	}

	public void setRETURNMESSAGE(String returnmessage) {
		RETURNMESSAGE = returnmessage;
	}

	public String getSBSL() {
		return SBSL;
	}

	public void setSBSL(String sBSL) {
		SBSL = sBSL;
	}

	public DZJG[] getDZJGS() {
		return DZJGS;
	}

	public void setDZJGS(DZJG[] dZJGS) {
		DZJGS = dZJGS;
	}
}
