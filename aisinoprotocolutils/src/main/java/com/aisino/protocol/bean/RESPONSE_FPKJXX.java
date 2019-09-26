package com.aisino.protocol.bean;

public class RESPONSE_FPKJXX {

	// 发票开具头部信息
	private FPKJXX_FPTXX FPKJXX_FPTXX;
	// 项目信息（发票明细）（多条）
	private FPKJXX_XMXX[] FPKJXX_XMXXS;
	// 返回发票订单信息
	private FPKJXX_FPJGXX FPKJXX_FPJGXX;

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

	public FPKJXX_FPJGXX getFPKJXX_FPJGXX() {
		return FPKJXX_FPJGXX;
	}

	public void setFPKJXX_FPJGXX(FPKJXX_FPJGXX fpkjxx_fpjgxx) {
		FPKJXX_FPJGXX = fpkjxx_fpjgxx;
	}

}
