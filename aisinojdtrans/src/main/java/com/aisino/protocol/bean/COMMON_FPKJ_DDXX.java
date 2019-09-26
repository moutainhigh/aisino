package com.aisino.protocol.bean;

/**增值税接口对接
 * @author jerome
 *
 */
public class COMMON_FPKJ_DDXX {
	private String DDH;//订单号/DDH>
	private String DDSJ;//订单日期/DDSJ>
	private String THDH;//
	public String getDDH() {
		return DDH;
	}
	public void setDDH(String dDH) {
		DDH = dDH;
	}
	public String getDDSJ() {
		return DDSJ;
	}
	public void setDDSJ(String dDSJ) {
		DDSJ = dDSJ;
	}
	public String getTHDH() {
		return THDH;
	}
	public void setTHDH(String tHDH) {
		THDH = tHDH;
	}
}
