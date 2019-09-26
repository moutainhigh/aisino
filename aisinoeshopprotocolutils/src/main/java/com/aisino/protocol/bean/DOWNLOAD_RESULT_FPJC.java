/**
 * 
 */
package com.aisino.protocol.bean;

import com.aisino.protocol.bean.*;
import com.aisino.protocol.bean.FPJC_JL;

/**
 * @author fanlb
 * 
 */
public class DOWNLOAD_RESULT_FPJC
{
	private com.aisino.protocol.bean.PROTOCOLINFO PROTOCOLINFO;
	private com.aisino.protocol.bean.FPJC_JL[] FPJC_JLS;

	public com.aisino.protocol.bean.PROTOCOLINFO getPROTOCOLINFO()
	{
		return PROTOCOLINFO;
	}

	public void setPROTOCOLINFO(com.aisino.protocol.bean.PROTOCOLINFO protocolinfo)
	{
		PROTOCOLINFO = protocolinfo;
	}

	public com.aisino.protocol.bean.FPJC_JL[] getFPJC_JLS()
	{
		return FPJC_JLS;
	}

	public void setFPJC_JLS(FPJC_JL[] fpjc_jls)
	{
		FPJC_JLS = fpjc_jls;
	}
}
