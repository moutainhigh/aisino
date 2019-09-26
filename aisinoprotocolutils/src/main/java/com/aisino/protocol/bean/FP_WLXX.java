package com.aisino.protocol.bean;

public class FP_WLXX {
	// 物流信息
	private String CYGS; /* 承运公司 */
	private String SHSJ; /* 送货时间 */
	private String WLDH; /* 物流单号 */
	private String SHDZ; /* 送货地址 */
	/**
	 * 新增部分未上传51发票的字段
	 */
	private String FP_DM;// 发票代码
	private String FP_HM;// 发票号码
	private String DSPTBM;// 电商平台编码

	/**
	 * 新增部分未上传51发票的字段
	 */
	public String getCYGS() {
		return CYGS;
	}

	public void setCYGS(String cYGS) {
		CYGS = cYGS;
	}

	public String getSHSJ() {
		return SHSJ;
	}

	public void setSHSJ(String sHSJ) {
		SHSJ = sHSJ;
	}

	public String getWLDH() {
		return WLDH;
	}

	public void setWLDH(String wLDH) {
		WLDH = wLDH;
	}

	public String getSHDZ() {
		return SHDZ;
	}

	public void setSHDZ(String sHDZ) {
		SHDZ = sHDZ;
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
