package com.aisino.protocol.bean;

import com.aisino.protocol.bean.FP_CX_REQUEST_CONDITION;
import com.aisino.protocol.bean.PROTOCOLINFO;

public class FP_XX_CX_QQ {

	private com.aisino.protocol.bean.PROTOCOLINFO PROTOCOLINFO;
	private com.aisino.protocol.bean.FP_CX_REQUEST_CONDITION FP_CX_REQUEST_CONDITION;

	public PROTOCOLINFO getPROTOCOLINFO() {
		return PROTOCOLINFO;
	}

	public void setPROTOCOLINFO(PROTOCOLINFO protocolinfo) {
		PROTOCOLINFO = protocolinfo;
	}

	public FP_CX_REQUEST_CONDITION getFP_CX_REQUEST_CONDITION() {
		return FP_CX_REQUEST_CONDITION;
	}

	public void setFP_CX_REQUEST_CONDITION(
			FP_CX_REQUEST_CONDITION fp_cx_request_condition) {
		FP_CX_REQUEST_CONDITION = fp_cx_request_condition;
	}

}
