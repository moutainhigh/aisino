package com.aisino.protocol.bean;

public class RESPONSE_KBFPMB extends RESPONSE_BEAN {
	private KBFPMBXX[] KBFPMBXXS;
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

	public KBFPMBXX[] getKBFPMBXXS() {
		return KBFPMBXXS;
	}

	public void setKBFPMBXXS(KBFPMBXX[] kBFPMBXXS) {
		KBFPMBXXS = kBFPMBXXS;
	}
}
