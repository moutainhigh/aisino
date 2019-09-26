package com.aisino.protocol.bean;

public class RESPONSE_PYXZGLOBLE extends RESPONSE_BEAN {
	private RESPONSE_PYXZ[] RESPONSE_PYXZS;
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

	public RESPONSE_PYXZ[] getRESPONSE_PYXZS() {
		return RESPONSE_PYXZS;
	}

	public void setRESPONSE_PYXZS(RESPONSE_PYXZ[] response_pyxzs) {
		this.RESPONSE_PYXZS = response_pyxzs;
	}

}
