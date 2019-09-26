/**
 * 
 */
package com.aisino.protocol.bean;

/**
 * @author fanlb
 * 
 */
public class DOWNLOAD_RESULT_FPJC {
	private PROTOCOLINFO PROTOCOLINFO;
	private FPJC_JL[] FPJC_JLS;

	public PROTOCOLINFO getPROTOCOLINFO() {
		return PROTOCOLINFO;
	}

	public void setPROTOCOLINFO(PROTOCOLINFO protocolinfo) {
		PROTOCOLINFO = protocolinfo;
	}

	public FPJC_JL[] getFPJC_JLS() {
		return FPJC_JLS;
	}

	public void setFPJC_JLS(FPJC_JL[] fpjc_jls) {
		FPJC_JLS = fpjc_jls;
	}
}
