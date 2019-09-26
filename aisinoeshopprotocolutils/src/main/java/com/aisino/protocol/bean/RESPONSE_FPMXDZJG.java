package com.aisino.protocol.bean;


import com.aisino.protocol.bean.*;
import com.aisino.protocol.bean.DZXX;

public class RESPONSE_FPMXDZJG extends com.aisino.protocol.bean.RESPONSE_BEAN {
	private String DZLSH;
	
	private String SBSL;
	
	private com.aisino.protocol.bean.DZXX[] DZXXS;
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
	public void setSBSL(String sbsl) {
		SBSL = sbsl;
	}
	public com.aisino.protocol.bean.DZXX[] getDZXXS() {
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
