/**
 * 
 */
package com.aisino.protocol.bean;

/**
 * @author fanlb
 * 
 */
public class GXSYQY_SQ
{
	private PROTOCOLINFO PROTOCOLINFO;
	private CHANGE_REQUEST_CONDITION CHANGE_REQUEST_CONDITION;

	public PROTOCOLINFO getPROTOCOLINFO()
	{
		return PROTOCOLINFO;
	}

	public void setPROTOCOLINFO(PROTOCOLINFO protocolinfo)
	{
		PROTOCOLINFO = protocolinfo;
	}

	public void setCHANGE_REQUEST_CONDITION(CHANGE_REQUEST_CONDITION cHANGE_REQUEST_CONDITION) {
		CHANGE_REQUEST_CONDITION = cHANGE_REQUEST_CONDITION;
	}

	public CHANGE_REQUEST_CONDITION getCHANGE_REQUEST_CONDITION() {
		return CHANGE_REQUEST_CONDITION;
	}
}
