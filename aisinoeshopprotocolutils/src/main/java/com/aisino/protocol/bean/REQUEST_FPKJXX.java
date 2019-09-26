package com.aisino.protocol.bean;

import com.aisino.protocol.bean.*;
import com.aisino.protocol.bean.FPKJXX_DDMXXX;
import com.aisino.protocol.bean.FPKJXX_DDXX;
import com.aisino.protocol.bean.FPKJXX_WLXX;
import com.aisino.protocol.bean.FPKJXX_XMXX;
import com.aisino.protocol.bean.FPKJXX_ZFXX;

public class REQUEST_FPKJXX {

	// 发票开具头部信息
	private com.aisino.protocol.bean.FPKJXX_FPTXX FPKJXX_FPTXX;
	// 项目信息（发票明细）（多条）
	private com.aisino.protocol.bean.FPKJXX_XMXX[] FPKJXX_XMXXS;
	// 订单信息
	private com.aisino.protocol.bean.FPKJXX_DDXX FPKJXX_DDXX;

	private com.aisino.protocol.bean.FPKJXX_DDMXXX[] FPKJXX_DDMXXXS;
	// 支付信息
	private com.aisino.protocol.bean.FPKJXX_ZFXX FPKJXX_ZFXX;
	// 物流信息
	private com.aisino.protocol.bean.FPKJXX_WLXX FPKJXX_WLXX;

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

	public com.aisino.protocol.bean.FPKJXX_DDXX getFPKJXX_DDXX() {
		return FPKJXX_DDXX;
	}

	public void setFPKJXX_DDXX(com.aisino.protocol.bean.FPKJXX_DDXX fpkjxx_ddxx) {
		FPKJXX_DDXX = fpkjxx_ddxx;
	}

	public com.aisino.protocol.bean.FPKJXX_ZFXX getFPKJXX_ZFXX() {
		return FPKJXX_ZFXX;
	}

	public void setFPKJXX_ZFXX(com.aisino.protocol.bean.FPKJXX_ZFXX fpkjxx_zfxx) {
		FPKJXX_ZFXX = fpkjxx_zfxx;
	}

	public com.aisino.protocol.bean.FPKJXX_WLXX getFPKJXX_WLXX() {
		return FPKJXX_WLXX;
	}

	public void setFPKJXX_WLXX(com.aisino.protocol.bean.FPKJXX_WLXX fpkjxx_wlxx) {
		FPKJXX_WLXX = fpkjxx_wlxx;
	}

	public com.aisino.protocol.bean.FPKJXX_DDMXXX[] getFPKJXX_DDMXXXS() {
		return FPKJXX_DDMXXXS;
	}

	public void setFPKJXX_DDMXXXS(FPKJXX_DDMXXX[] fpkjxx_ddmxxxs) {
		FPKJXX_DDMXXXS = fpkjxx_ddmxxxs;
	}

}
