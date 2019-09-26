package com.aisino.protocol.bean;

public class DOWNLOAD_REQUEST_ZZSQ {
	private com.aisino.protocol.bean.PROTOCOLINFO PROTOCOLINFO;
	/*//该属性与xml中相关规范不一致
	private REQUEST_CONDITION REQUEST_CONDITION;*/
	
	private com.aisino.protocol.bean.REQUEST_PCDATA_CONDITION REQUEST_PCDATA_CONDITION;
	
	public REQUEST_PCDATA_CONDITION getREQUEST_PCDATA_CONDITION() {
		return REQUEST_PCDATA_CONDITION;
	}
	
	public void setREQUEST_PCDATA_CONDITION(REQUEST_PCDATA_CONDITION request_pcdata_condition) {
		REQUEST_PCDATA_CONDITION = request_pcdata_condition;
	}
	public com.aisino.protocol.bean.PROTOCOLINFO getPROTOCOLINFO() {
		return PROTOCOLINFO;
	}
	public void setPROTOCOLINFO(com.aisino.protocol.bean.PROTOCOLINFO protocolinfo) {
		PROTOCOLINFO = protocolinfo;
	}
	/*public REQUEST_CONDITION getREQUEST_CONDITION() {
		return REQUEST_CONDITION;
	}
	public void setREQUEST_CONDITION(REQUEST_CONDITION request_condition) {
		REQUEST_CONDITION = request_condition;
	}*/
}
