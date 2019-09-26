package com.aisino.protocol.bean;

import com.aisino.protocol.bean.FP_JB_REQUEST_DATA;
import com.aisino.protocol.bean.PROTOCOLINFO;

public class FP_JB_DJ_QQ {

	private com.aisino.protocol.bean.PROTOCOLINFO PROTOCOLINFO;
	private com.aisino.protocol.bean.FP_JB_REQUEST_DATA FP_JB_REQUEST_DATA;

	public PROTOCOLINFO getPROTOCOLINFO() {
		return PROTOCOLINFO;
	}

	public void setPROTOCOLINFO(PROTOCOLINFO protocolinfo) {
		PROTOCOLINFO = protocolinfo;
	}

	public FP_JB_REQUEST_DATA getFP_JB_REQUEST_DATA() {
		return FP_JB_REQUEST_DATA;
	}

	public void setFP_JB_REQUEST_DATA(FP_JB_REQUEST_DATA fp_jb_request_data) {
		FP_JB_REQUEST_DATA = fp_jb_request_data;
	}

}
