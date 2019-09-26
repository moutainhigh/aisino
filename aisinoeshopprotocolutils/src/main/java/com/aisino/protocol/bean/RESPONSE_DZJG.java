package com.aisino.protocol.bean;

import com.aisino.protocol.bean.*;
import com.aisino.protocol.bean.DZJG;

public class RESPONSE_DZJG extends com.aisino.protocol.bean.RESPONSE_BEAN {
	    private String SBSL;
	    private com.aisino.protocol.bean.DZJG[] DZJGS;
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
		public com.aisino.protocol.bean.DZJG[] getDZJGS() {
			return DZJGS;
		}
		public void setDZJGS(DZJG[] dZJGS) {
			DZJGS = dZJGS;
		}
}
