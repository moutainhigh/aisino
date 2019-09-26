package com.aisino.protocol.bean;

import com.aisino.protocol.bean.*;
import com.aisino.protocol.bean.GETFPSCXX;

public class RESPONSE_GETFPSC extends com.aisino.protocol.bean.RESPONSE_BEAN {

	private com.aisino.protocol.bean.GETFPSCXX[] GETFPSCXXS;
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
	public com.aisino.protocol.bean.GETFPSCXX[] getGETFPSCXXS() {
		return GETFPSCXXS;
	}
	public void setGETFPSCXXS(GETFPSCXX[] gETFPSCXXS) {
		GETFPSCXXS = gETFPSCXXS;
	} 
}
