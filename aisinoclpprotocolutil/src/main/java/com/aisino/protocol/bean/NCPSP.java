package com.aisino.protocol.bean;

/**
 * @author korn.yang
 * @project_name wlkp_sc
 * Sep 7, 2010 4:04:37 PM
 */
public class NCPSP {
	private String SP_ID;
	private String SP_DM;
	private String SPMC;
	private String SPQC;
	private String SJSP_ID;
	
	
	public String getSP_ID() {
		return SP_ID;
	}
	public void setSP_ID(String sp_id) {
		SP_ID = sp_id;
	}
	public String getSP_DM() {
		return SP_DM;
	}
	public void setSP_DM(String sp_dm) {
		SP_DM = sp_dm;
	}
	public String getSPMC() {
		return SPMC;
	}
	public void setSPMC(String spmc) {
		SPMC = spmc;
	}
	public String getSPQC() {
		return SPQC;
	}
	public void setSPQC(String spqc) {
		SPQC = spqc;
	}
	public String getSJSP_ID() {
		return SJSP_ID;
	}
	public void setSJSP_ID(String sjsp_id) {
		SJSP_ID = sjsp_id;
	}
	
	
}
