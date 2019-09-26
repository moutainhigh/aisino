package com.aisino.protocol.bean;

public class REQUEST_FPCY implements REQUEST_BEAN {
	private String KPRQ;// 日期
	private double KPHJJE;// 金额
	private String CXM;// 查询码
	private String DSPTBM;

	public String getDSPTBM() {
		return DSPTBM;
	}

	public void setDSPTBM(String dSPTBM) {
		DSPTBM = dSPTBM;
	}

	public String getKPRQ() {
		return KPRQ;
	}

	public void setKPRQ(String kprq) {
		KPRQ = kprq;
	}

	public double getKPHJJE() {
		return KPHJJE;
	}

	public void setKPHJJE(double kphjje) {
		KPHJJE = kphjje;
	}

	public String getCXM() {
		return CXM;
	}

	public void setCXM(String cxm) {
		CXM = cxm;
	}

}
