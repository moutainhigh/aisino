package com.aisino.protocol.bean;

import com.aisino.protocol.bean.FP_JB_RESPONSE_DATA;
import com.aisino.protocol.bean.PROTOCOLINFO;

public class FP_JB_DJ_YD {

	private com.aisino.protocol.bean.PROTOCOLINFO PROTOCOLINFO;
	private com.aisino.protocol.bean.FP_JB_RESPONSE_DATA FP_JB_RESPONSE_DATA;

	public PROTOCOLINFO getPROTOCOLINFO() {
		return PROTOCOLINFO;
	}

	public void setPROTOCOLINFO(PROTOCOLINFO protocolinfo) {
		PROTOCOLINFO = protocolinfo;
	}

	public FP_JB_RESPONSE_DATA getFP_JB_RESPONSE_DATA() {
		return FP_JB_RESPONSE_DATA;
	}

	public void setFP_JB_RESPONSE_DATA(FP_JB_RESPONSE_DATA fPJBRESPONSEDATA) {
		FP_JB_RESPONSE_DATA = fPJBRESPONSEDATA;
	}

}
