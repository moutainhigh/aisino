/**
 * 
 */
package com.aisino.protocol.bean;

import com.aisino.protocol.bean.CHANGE_REQUEST_CONDITION;
import com.aisino.protocol.bean.PROTOCOLINFO;

/**
 * @author fanlb
 * 
 */
public class GXSYQY_SQ
{
	private com.aisino.protocol.bean.PROTOCOLINFO PROTOCOLINFO;
	private com.aisino.protocol.bean.CHANGE_REQUEST_CONDITION CHANGE_REQUEST_CONDITION;

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
