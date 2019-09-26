package com.aisino.protocol.bean;

public class REQUEST_COMMON_FPKJ {

	// 发票开具头部信息
	private com.aisino.protocol.bean.COMMON_FPKJ_FPT COMMON_FPKJ_FPT;
	// 项目信息（发票明细）（多条）
	private COMMON_FPKJ_XMXX[] COMMON_FPKJ_XMXXS;
	// 订单信息
	private COMMON_FPKJ_DDXX COMMON_FPKJ_DDXX;

//	private COMMON_FPKJ_DDMXXX[] COMMON_FPKJ_DDMXXXS;
	
	// 支付信息
	private com.aisino.protocol.bean.COMMON_FPKJ_ZFXX COMMON_FPKJ_ZFXX;
	// 物流信息
	private com.aisino.protocol.bean.COMMON_FPKJ_WLXX COMMON_FPKJ_WLXX;
	
	public COMMON_FPKJ_DDXX getCOMMON_FPKJ_DDXX() {
		return COMMON_FPKJ_DDXX;
	}
	public void setCOMMON_FPKJ_DDXX(COMMON_FPKJ_DDXX cOMMONFPKJDDXX) {
		COMMON_FPKJ_DDXX = cOMMONFPKJDDXX;
	}
	public com.aisino.protocol.bean.COMMON_FPKJ_FPT getCOMMON_FPKJ_FPT() {
		return COMMON_FPKJ_FPT;
	}
	public void setCOMMON_FPKJ_FPT(com.aisino.protocol.bean.COMMON_FPKJ_FPT cOMMONFPKJFPT) {
		COMMON_FPKJ_FPT = cOMMONFPKJFPT;
	}
	public COMMON_FPKJ_XMXX[] getCOMMON_FPKJ_XMXXS() {
		return COMMON_FPKJ_XMXXS;
	}
	public void setCOMMON_FPKJ_XMXXS(COMMON_FPKJ_XMXX[] cOMMONFPKJXMXXS) {
		COMMON_FPKJ_XMXXS = cOMMONFPKJXMXXS;
	}
	public com.aisino.protocol.bean.COMMON_FPKJ_ZFXX getCOMMON_FPKJ_ZFXX() {
		return COMMON_FPKJ_ZFXX;
	}
	public void setCOMMON_FPKJ_ZFXX(com.aisino.protocol.bean.COMMON_FPKJ_ZFXX cOMMONFPKJZFXX) {
		COMMON_FPKJ_ZFXX = cOMMONFPKJZFXX;
	}
	public com.aisino.protocol.bean.COMMON_FPKJ_WLXX getCOMMON_FPKJ_WLXX() {
		return COMMON_FPKJ_WLXX;
	}
	public void setCOMMON_FPKJ_WLXX(com.aisino.protocol.bean.COMMON_FPKJ_WLXX cOMMONFPKJWLXX) {
		COMMON_FPKJ_WLXX = cOMMONFPKJWLXX;
	}
	

}
