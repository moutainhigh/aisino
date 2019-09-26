package com.aisino.protocol.bean;

public class REQUEST_FPKJXX {

	// 发票开具头部信息
	private FPKJXX_FPTXX FPKJXX_FPTXX;
	// 项目信息（发票明细）（多条）
	private FPKJXX_XMXX[] FPKJXX_XMXXS;
	// 订单信息
	private FPKJXX_DDXX FPKJXX_DDXX;

	private FPKJXX_DDMXXX[] FPKJXX_DDMXXXS;
	// 支付信息
	private FPKJXX_ZFXX FPKJXX_ZFXX;
	// 物流信息
	private FPKJXX_WLXX FPKJXX_WLXX;

	public FPKJXX_FPTXX getFPKJXX_FPTXX() {
		return FPKJXX_FPTXX;
	}

	public void setFPKJXX_FPTXX(FPKJXX_FPTXX fpkjxx_fptxx) {
		FPKJXX_FPTXX = fpkjxx_fptxx;
	}

	public FPKJXX_XMXX[] getFPKJXX_XMXXS() {
		return FPKJXX_XMXXS;
	}

	public void setFPKJXX_XMXXS(FPKJXX_XMXX[] fpkjxx_xmxxs) {
		FPKJXX_XMXXS = fpkjxx_xmxxs;
	}

	public FPKJXX_DDXX getFPKJXX_DDXX() {
		return FPKJXX_DDXX;
	}

	public void setFPKJXX_DDXX(FPKJXX_DDXX fpkjxx_ddxx) {
		FPKJXX_DDXX = fpkjxx_ddxx;
	}

	public FPKJXX_ZFXX getFPKJXX_ZFXX() {
		return FPKJXX_ZFXX;
	}

	public void setFPKJXX_ZFXX(FPKJXX_ZFXX fpkjxx_zfxx) {
		FPKJXX_ZFXX = fpkjxx_zfxx;
	}

	public FPKJXX_WLXX getFPKJXX_WLXX() {
		return FPKJXX_WLXX;
	}

	public void setFPKJXX_WLXX(FPKJXX_WLXX fpkjxx_wlxx) {
		FPKJXX_WLXX = fpkjxx_wlxx;
	}

	public FPKJXX_DDMXXX[] getFPKJXX_DDMXXXS() {
		return FPKJXX_DDMXXXS;
	}

	public void setFPKJXX_DDMXXXS(FPKJXX_DDMXXX[] fpkjxx_ddmxxxs) {
		FPKJXX_DDMXXXS = fpkjxx_ddmxxxs;
	}

}
