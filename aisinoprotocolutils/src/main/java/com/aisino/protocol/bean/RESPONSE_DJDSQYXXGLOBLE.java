package com.aisino.protocol.bean;

/**
 * 电商企业信息返回类
 * 
 * @author scott.li
 * @date： Aug 13, 2013 4:49:42 PM
 */
public class RESPONSE_DJDSQYXXGLOBLE extends RESPONSE_BEAN {
	private String RETURNCODE;
	private String RETURNMESSAGE;
	private RESPONSE_DJDSQYXX[] RESPONSE_DJDSQYXXS;
	private RESPONSE_DSQYPZHDXX[] RESPONSE_DSQYPZHDXXS;

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

	public RESPONSE_DSQYPZHDXX[] getRESPONSE_DSQYPZHDXXS() {
		return RESPONSE_DSQYPZHDXXS;
	}

	public void setRESPONSE_DSQYPZHDXXS(
			RESPONSE_DSQYPZHDXX[] response_dsqypzhdxxs) {
		RESPONSE_DSQYPZHDXXS = response_dsqypzhdxxs;
	}

	public RESPONSE_DJDSQYXX[] getRESPONSE_DJDSQYXXS() {
		return RESPONSE_DJDSQYXXS;
	}

	public void setRESPONSE_DJDSQYXXS(RESPONSE_DJDSQYXX[] RESPONSE_DJDSQYXXS) {
		this.RESPONSE_DJDSQYXXS = RESPONSE_DJDSQYXXS;
	}

}
