package com.aisino.protocol.bean;

public class RESPONSE_FPMXDZJG extends RESPONSE_BEAN {

	private String DZLSH;
	private String SBSL;
	private String RETURNCODE;
	private String RETURNMESSAGE;
	private DZXX[] DZXXS;

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

	public void setSBSL(String sbsl) {
		SBSL = sbsl;
	}

	public DZXX[] getDZXXS() {
		return DZXXS;
	}

	public void setDZXXS(DZXX[] dzxxs) {
		DZXXS = dzxxs;
	}

	public String getDZLSH() {
		return DZLSH;
	}

	public void setDZLSH(String dzlsh) {
		DZLSH = dzlsh;
	}
}
