package com.aisino.protocol.bean;

public class FP_DDXX {
    //订单信息
    private String DDH;   /*订单号*/
    private String THDH;
    private String DDDATE;   /*订单日期*/
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
