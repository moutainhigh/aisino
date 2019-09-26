package com.aisino.protocol.bean;

/**京东接口对接
 * @author jerome
 *
 */
public class FPKJXX_DDXX {
	private String DDH;//订单号
	private String THDH;//退货单号
	private String DDDATE;//订单时间
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
}
