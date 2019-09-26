package com.aisino.protocol.bean;

import com.aisino.protocol.bean.*;
import com.aisino.protocol.bean.NSRZGXX;
import com.aisino.protocol.bean.PROTOCOLINFO;

public class RESULT_FPXXSB  extends com.aisino.protocol.bean.RESPONSE_BEAN {
	private com.aisino.protocol.bean.PROTOCOLINFO PROTOCOLINFO;
	private com.aisino.protocol.bean.NSRZGXX NSRZGXX;
	public com.aisino.protocol.bean.PROTOCOLINFO getPROTOCOLINFO() {
		return PROTOCOLINFO;
	}
	public void setPROTOCOLINFO(com.aisino.protocol.bean.PROTOCOLINFO protocolinfo) {
		PROTOCOLINFO = protocolinfo;
	}
	public com.aisino.protocol.bean.NSRZGXX getNSRZGXX() {
		return NSRZGXX;
	}
	public void setNSRZGXX(com.aisino.protocol.bean.NSRZGXX nsrzgxx) {
		NSRZGXX = nsrzgxx;
	}

}
