package com.aisino.protocol.bean;

public class RESPONSE_DSQYZT extends RESPONSE_BEAN{
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
	
	private String DSPTBM;  /*电商平台编码*/
	private RESPONSE_DSQYZTMX[] RESPONSE_DSQYZTMXS;
	public String getDSPTBM() {
		return DSPTBM;
	}
	public void setDSPTBM(String dsptbm) {
		DSPTBM = dsptbm;
	}
	public RESPONSE_DSQYZTMX[] getRESPONSE_DSQYZTMXS() {
		return RESPONSE_DSQYZTMXS;
	}
	public void setRESPONSE_DSQYZTMXS(RESPONSE_DSQYZTMX[] response_dsqyztmxs) {
		RESPONSE_DSQYZTMXS = response_dsqyztmxs;
	}
}
