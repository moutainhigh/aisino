/**
 * 
 */
package com.aisino.protocol.bean;

/**
 * @author fanlb
 * 
 */
public class UPDATE_RESPONSE_CORP_INFO {
	private PROTOCOLINFO PROTOCOLINFO;
	private QYXX_JL QYXX_JL;
	private QYXX_JL QYXX_JLS[];
	// 票样信息
	private PY_XX PY_XXS[];

	private SJZDXX SJZDXX;// 数据字典版本

	public PROTOCOLINFO getPROTOCOLINFO() {
		return PROTOCOLINFO;
	}

	public void setPROTOCOLINFO(PROTOCOLINFO protocolinfo) {
		PROTOCOLINFO = protocolinfo;
	}

	public QYXX_JL getQYXX_JL() {
		return QYXX_JL;
	}

	public void setQYXX_JL(QYXX_JL qyxx_jl) {
		QYXX_JL = qyxx_jl;
	}

	public QYXX_JL[] getQYXX_JLS() {
		return QYXX_JLS;
	}

	public void setQYXX_JLS(QYXX_JL[] qyxx_jls) {
		QYXX_JLS = qyxx_jls;
	}

	public SJZDXX getSJZDXX() {
		return SJZDXX;
	}

	public void setSJZDXX(SJZDXX sjzdxx) {
		SJZDXX = sjzdxx;
	}

	public PY_XX[] getPY_XXS() {
		return PY_XXS;
	}

	public void setPY_XXS(PY_XX[] pY_XXS) {
		PY_XXS = pY_XXS;
	}

}
