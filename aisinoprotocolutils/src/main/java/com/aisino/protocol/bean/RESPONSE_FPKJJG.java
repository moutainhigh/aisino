package com.aisino.protocol.bean;

/**
 * 
 * <p>
 * [描述信息：取得发票上传结果的返回协议bean]
 * </p>
 * 
 * @author scott.li
 * @version 1.0 Created on Nov 11, 2013 9:25:07 AM
 */
public class RESPONSE_FPKJJG extends RESPONSE_BEAN {
	private String DSPTBM;
	private String FPSCLSH;
	private FPKJJG[] FPKJJGS;
	private String RETURNCODE;
	private String RETURNMESSAGE;

	public String getFPSCLSH() {
		return FPSCLSH;
	}

	public void setFPSCLSH(String fPSCLSH) {
		FPSCLSH = fPSCLSH;
	}

	public String getDSPTBM() {
		return DSPTBM;
	}

	public void setDSPTBM(String dsptbm) {
		DSPTBM = dsptbm;
	}

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

	public FPKJJG[] getFPKJJGS() {
		return FPKJJGS;
	}

	public void setFPKJJGS(FPKJJG[] fPKJJGS) {
		FPKJJGS = fPKJJGS;
	}

}
