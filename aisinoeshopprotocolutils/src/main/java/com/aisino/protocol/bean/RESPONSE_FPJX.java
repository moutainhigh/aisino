package com.aisino.protocol.bean;

import com.aisino.protocol.bean.*;

public class RESPONSE_FPJX extends com.aisino.protocol.bean.RESPONSE_BEAN {
	private String NSRSBH;
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
	public String getNSRSBH() {
		return NSRSBH;
	}

	public void setNSRSBH(String nsrsbh) {
		NSRSBH = nsrsbh;
	}
	
}
