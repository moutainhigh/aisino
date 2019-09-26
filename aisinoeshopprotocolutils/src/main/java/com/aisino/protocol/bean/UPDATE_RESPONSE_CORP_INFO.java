/**
 * 
 */
package com.aisino.protocol.bean;

import com.aisino.protocol.bean.*;
import com.aisino.protocol.bean.PY_XX;
import com.aisino.protocol.bean.QYXX_JL;
import com.aisino.protocol.bean.SJZDXX;

/**
 * @author fanlb
 * 
 */
public class UPDATE_RESPONSE_CORP_INFO {
	private com.aisino.protocol.bean.PROTOCOLINFO PROTOCOLINFO;
	private com.aisino.protocol.bean.QYXX_JL QYXX_JL;
	private com.aisino.protocol.bean.QYXX_JL QYXX_JLS[];
	//票样信息
	private com.aisino.protocol.bean.PY_XX PY_XXS[];
	
	private com.aisino.protocol.bean.SJZDXX SJZDXX;//数据字典版本

 

	public com.aisino.protocol.bean.PROTOCOLINFO getPROTOCOLINFO() {
		return PROTOCOLINFO;
	}

	public void setPROTOCOLINFO(com.aisino.protocol.bean.PROTOCOLINFO protocolinfo) {
		PROTOCOLINFO = protocolinfo;
	}

	public com.aisino.protocol.bean.QYXX_JL getQYXX_JL() {
		return QYXX_JL;
	}

	public void setQYXX_JL(com.aisino.protocol.bean.QYXX_JL qyxx_jl) {
		QYXX_JL = qyxx_jl;
	}

	public com.aisino.protocol.bean.QYXX_JL[] getQYXX_JLS() {
		return QYXX_JLS;
	}

	public void setQYXX_JLS(com.aisino.protocol.bean.QYXX_JL[] qyxx_jls) {
		QYXX_JLS = qyxx_jls;
	}

	public com.aisino.protocol.bean.SJZDXX getSJZDXX() {
		return SJZDXX;
	}

	public void setSJZDXX(com.aisino.protocol.bean.SJZDXX sjzdxx) {
		SJZDXX = sjzdxx;
	}

	public com.aisino.protocol.bean.PY_XX[] getPY_XXS() {
		return PY_XXS;
	}

	public void setPY_XXS(PY_XX[] pY_XXS) {
		PY_XXS = pY_XXS;
	}

 
}
