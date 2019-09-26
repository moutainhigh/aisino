package com.aisino.protocol.bean;

public class RESPONSE_GETFPSC extends RESPONSE_BEAN {

	private GETFPSCXX[] GETFPSCXXS;
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

	public GETFPSCXX[] getGETFPSCXXS() {
		return GETFPSCXXS;
	}

	public void setGETFPSCXXS(GETFPSCXX[] gETFPSCXXS) {
		GETFPSCXXS = gETFPSCXXS;
	}
}
