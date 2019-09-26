package com.aisino.protocol.bean;


public class COMMON_FPKJ_XMXX {

	private String XMMC;// 项目名称
	private String XMDW;// 项目单位
	private String GGXH;// 规格型号
	private String XMSL;// 项目数量
	private String XMDJ;// 项目单价
	private String XMJE;// 项目金额
	private String BYZD1;// 备用字段1
	private String BYZD2;// 备用字段2
	private String BYZD3;// 备用字段3
	private String BYZD4;// 备用字段4
	private String BYZD5;// 备用字段5
	
	/**
	 * @Date Created on 2015-0319 14:19  begin
	 */
	private String SL;//税率
//	private String SM;//税目
	private String SE;//税额
	private String HSJBZ;//含税价标志
	public String getHSJBZ() {
        return HSJBZ;
    }

    public void setHSJBZ(String hSJBZ) {
        HSJBZ = hSJBZ;
    }

    /**
	 * @Date Created on 2015-0319 14:19  end
	 */
	
	public String getXMMC() {
		return XMMC;
	}

	public String getSL() {
		return SL;
	}

	public void setSL(String sL) {
		SL = sL;
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
}
