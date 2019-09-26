package com.aisino.protocol.bean;

import com.aisino.protocol.bean.*;
import com.aisino.protocol.bean.KBFPMBXX;

public class RESPONSE_KBFPMB extends com.aisino.protocol.bean.RESPONSE_BEAN {
    private com.aisino.protocol.bean.KBFPMBXX[] KBFPMBXXS;
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
	public com.aisino.protocol.bean.KBFPMBXX[] getKBFPMBXXS() {
		return KBFPMBXXS;
	}
	public void setKBFPMBXXS(KBFPMBXX[] kBFPMBXXS) {
		KBFPMBXXS = kBFPMBXXS;
	}
}
