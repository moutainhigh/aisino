package com.aisino.protocol.bean;

import java.util.List;

public class ROUTE {
	private String DSPTBM;
	private String DQMC;
	private String DQCODE;
	private NSRXX[] NSRXXS;
	private List<NSRXX> nsrxxList;
	public String getDSPTBM() {
		return DSPTBM;
	}
	public void setDSPTBM(String dSPTBM) {
		DSPTBM = dSPTBM;
	}
	public String getDQMC() {
		return DQMC;
	}
	public void setDQMC(String dQMC) {
		DQMC = dQMC;
	}
	public String getDQCODE() {
		return DQCODE;
	}
	public void setDQCODE(String dQCODE) {
		DQCODE = dQCODE;
	}
	public NSRXX[] getNSRXXS() {
		return NSRXXS;
	}
	public void setNSRXXS(NSRXX[] nSRXXS) {
		NSRXXS = nSRXXS;
	}
	public List<NSRXX> getNsrxxList() {
		return nsrxxList;
	}
	public void setNsrxxList(List<NSRXX> nsrxxList) {
		this.nsrxxList = nsrxxList;
	}
}
