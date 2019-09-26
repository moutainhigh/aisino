package com.aisino.protocol.bean;

/**增值税接口对接
 * @author jerome
 *
 */
public class COMMON_FPKJ_WLXX {
	private String CYGS;//承运公司</CYGS>
	private String SHSJ;//送货时间</SHSJ>
	private String WLDH;//物流单号</WLDH>
	private String SHDZ;//送货地址</SHDZ>
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
