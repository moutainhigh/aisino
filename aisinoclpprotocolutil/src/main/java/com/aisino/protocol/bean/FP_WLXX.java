package com.aisino.protocol.bean;

public class FP_WLXX {
	  //物流信息
    private String CYGS;   /*承运公司*/
    private String SHSJ;   /*送货时间*/
    private String WLDH;   /*物流单号*/
    private String SHDZ;   /*送货地址*/
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
}
