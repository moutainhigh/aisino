package com.aisino.protocol.bean;

import com.aisino.protocol.bean.*;
import com.aisino.protocol.bean.FPKJXX_FPJGXX;
import com.aisino.protocol.bean.FPKJXX_XMXX;

public class RESPONSE_FPKJXX {

	// 发票开具头部信息
	private com.aisino.protocol.bean.FPKJXX_FPTXX FPKJXX_FPTXX;
	// 项目信息（发票明细）（多条）
	private com.aisino.protocol.bean.FPKJXX_XMXX[] FPKJXX_XMXXS;
	// 返回发票订单信息
	private com.aisino.protocol.bean.FPKJXX_FPJGXX FPKJXX_FPJGXX;

	public com.aisino.protocol.bean.FPKJXX_FPTXX getFPKJXX_FPTXX() {
		return FPKJXX_FPTXX;
	}

	public void setFPKJXX_FPTXX(com.aisino.protocol.bean.FPKJXX_FPTXX fpkjxx_fptxx) {
		FPKJXX_FPTXX = fpkjxx_fptxx;
	}

	public com.aisino.protocol.bean.FPKJXX_XMXX[] getFPKJXX_XMXXS() {
		return FPKJXX_XMXXS;
	}

	public void setFPKJXX_XMXXS(FPKJXX_XMXX[] fpkjxx_xmxxs) {
		FPKJXX_XMXXS = fpkjxx_xmxxs;
	}

	public com.aisino.protocol.bean.FPKJXX_FPJGXX getFPKJXX_FPJGXX() {
		return FPKJXX_FPJGXX;
	}

	public void setFPKJXX_FPJGXX(com.aisino.protocol.bean.FPKJXX_FPJGXX fpkjxx_fpjgxx) {
		FPKJXX_FPJGXX = fpkjxx_fpjgxx;
	}

}
