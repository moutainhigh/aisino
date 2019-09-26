package com.aisino.protocol.bean;

public class FP_ZFXX {
	// 支付信息
	private String ZFFS; /* 支付方式 */
	private String ZFLSH; /* 支付流水号 */
	private String ZFPT; /* 支付平台 */
	/**
	 * 新增部分未上传51发票的字段
	 */
	private String FP_DM;// 发票代码
	private String FP_HM;// 发票号码
	private String DSPTBM;// 电商平台编码

	/**
	 * 新增部分未上传51发票的字段
	 */
	public String getZFFS() {
		return ZFFS;
	}

	public void setZFFS(String zFFS) {
		ZFFS = zFFS;
	}

	public String getZFLSH() {
		return ZFLSH;
	}

	public void setZFLSH(String zFLSH) {
		ZFLSH = zFLSH;
	}

	public String getZFPT() {
		return ZFPT;
	}

	public void setZFPT(String zFPT) {
		ZFPT = zFPT;
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
