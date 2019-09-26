package com.aisino.protocol.bean;

public class FP_KJMX {
	// 项目信息
	private String XMMC;
	private String XMDW;
	private String GGXH;
	private String XMSL;
	private String XMDJ;
	private String XMJE;
	private String XMBM;
	private String BYZD1;
	private String BYZD2;
	private String BYZD3;
	private String BYZD4;
	private String BYZD5;
	/**
	 * 商品明细新增字段
	 * 
	 * @date:Created ON 2015-3-16 上午09:57:32 by zhongsiwei begin
	 */
	private String SM;// 税目
	private String SL;// 税率
	private String HSJBZ;// 含税价标志
	private String SE;// 税额
	/**
	 * @date:Created ON 2015-3-16 上午09:57:32 by zhongsiwei end
	 */
	/**
	 * 新增部分未上传51发票的字段
	 */
	private String FP_DM;// 发票代码
	private String FP_HM;// 发票号码
	private String FPHXZ;// 发票行性质

	/**
	 * 新增部分未上传大数据/51发票的字段
	 * FWH-20171030
	 */
	private String  SPBM;
	private String  YHZCBS;
	private String  LSLBS;
	private String  ZZSTSGL;
	
	
	
	
	public String getSPBM() {
		return SPBM;
	}

	public void setSPBM(String sPBM) {
		SPBM = sPBM;
	}

	public String getYHZCBS() {
		return YHZCBS;
	}

	public void setYHZCBS(String yHZCBS) {
		YHZCBS = yHZCBS;
	}

	public String getLSLBS() {
		return LSLBS;
	}

	public void setLSLBS(String lSLBS) {
		LSLBS = lSLBS;
	}

	public String getZZSTSGL() {
		return ZZSTSGL;
	}

	public void setZZSTSGL(String zZSTSGL) {
		ZZSTSGL = zZSTSGL;
	}


	public String getXMMC() {
		return XMMC;
	}

	public String getSM() {
		return SM;
	}

	public void setSM(String sM) {
		SM = sM;
	}

	public String getSL() {
		return SL;
	}

	public void setSL(String sL) {
		SL = sL;
	}

	public String getHSJBZ() {
		return HSJBZ;
	}

	public void setHSJBZ(String hSJBZ) {
		HSJBZ = hSJBZ;
	}

	public String getSE() {
		return SE;
	}

	public void setSE(String sE) {
		SE = sE;
	}

	public void setXMMC(String xmmc) {
		XMMC = xmmc;
	}

	public String getXMDW() {
		return XMDW;
	}

	public void setXMDW(String xmdw) {
		XMDW = xmdw;
	}

	public String getGGXH() {
		return GGXH;
	}

	public void setGGXH(String ggxh) {
		GGXH = ggxh;
	}

	public String getXMSL() {
		return XMSL;
	}

	public void setXMSL(String xmsl) {
		XMSL = xmsl;
	}

	public String getXMDJ() {
		return XMDJ;
	}

	public void setXMDJ(String xmdj) {
		XMDJ = xmdj;
	}

	public String getXMJE() {
		return XMJE;
	}

	public void setXMJE(String xmje) {
		XMJE = xmje;
	}

	public String getXMBM() {
		return XMBM;
	}

	public void setXMBM(String xmbm) {
		XMBM = xmbm;
	}

	public String getBYZD1() {
		return BYZD1;
	}

	public void setBYZD1(String byzd1) {
		BYZD1 = byzd1;
	}

	public String getBYZD2() {
		return BYZD2;
	}

	public void setBYZD2(String byzd2) {
		BYZD2 = byzd2;
	}

	public String getBYZD3() {
		return BYZD3;
	}

	public void setBYZD3(String byzd3) {
		BYZD3 = byzd3;
	}

	public String getBYZD4() {
		return BYZD4;
	}

	public void setBYZD4(String byzd4) {
		BYZD4 = byzd4;
	}

	public String getBYZD5() {
		return BYZD5;
	}

	public void setBYZD5(String byzd5) {
		BYZD5 = byzd5;
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

	public String getFPHXZ() {
		return FPHXZ;
	}

	public void setFPHXZ(String fPHXZ) {
		FPHXZ = fPHXZ;
	}

}
