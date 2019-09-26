package com.aisino.protocol.bean;

public class RESPONSE_FPMXDZ extends RESPONSE_BEAN{
	private String DZLSH;
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
	public String getDZLSH() {
		return DZLSH;
	}

	public void setDZLSH(String dzlsh) {
		DZLSH = dzlsh;
	}
}
