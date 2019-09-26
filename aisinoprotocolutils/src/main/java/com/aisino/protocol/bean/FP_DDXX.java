package com.aisino.protocol.bean;

public class FP_DDXX {
	// 订单信息
	private String DDH; /* 订单号 */
	private String THDH;
	private String DDDATE; /* 订单日期 */
	/**
	 * 新增部分未上传51发票的字段
	 */
	private String FP_DM;// 发票代码
	private String FP_HM;// 发票号码
	private String DSPTBM;// 电商平台编码

	/**
	 * 新增部分未上传51发票的字段
	 */
	public String getDDH() {
		return DDH;
	}

	public void setDDH(String dDH) {
		DDH = dDH;
	}

	public String getTHDH() {
		return THDH;
	}

	public void setTHDH(String tHDH) {
		THDH = tHDH;
	}

	public String getDDDATE() {
		return DDDATE;
	}

	public void setDDDATE(String dDDATE) {
		DDDATE = dDDATE;
	}

	public String getFP_DM() {
		return FP_DM;
	}

	public void setFP_DM(String fPDM) {
		FP_DM = fPDM;
	}

	public String getFP_HM() {
		return FP_HM;
	}

	public void setFP_HM(String fPHM) {
		FP_HM = fPHM;
	}

	public String getDSPTBM() {
		return DSPTBM;
	}

	public void setDSPTBM(String dSPTBM) {
		DSPTBM = dSPTBM;
	}
}
