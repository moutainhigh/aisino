package com.aisino.protocol.bean;

/**
 * <p>
 * [描述信息：订单信息bean]
 * </p>
 * 
 * @author wuyong@aisino.com
 * @version 1.0 Created on Nov 4, 2013 8:58:33 PM
 */
public class COMMON_FPKJ_DDXX {

	private String DDH;// 订单号
	private String THDH;// 退货单号
	private String DDSJ;// 订单时间

	public String getDDH() {
		return DDH;
	}

	public void setDDH(String ddh) {
		DDH = ddh;
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

	public void setTHDH(String thdh) {
		THDH = thdh;
	}
}
