/**
 * 
 */
package com.aisino.protocol.bean;

/**
 * @author fanlb
 * 
 */
public class UPLOAD_FP {
	private PROTOCOLINFO PROTOCOLINFO;
	private FPZT_JL[] FPZT_JLS;
	private SB_HZXX SB_HZXX;
	private FPPD_JL[] FPPD_JLS;

	private SB_PTFPSB_BTXX SB_PTFPSB_BTXX;

	private ESC_JL[] ESC_JLS;

	private JCK_JL[] JCK_JLS;

	private JDC_JL[] JDC_JLS;

	public PROTOCOLINFO getPROTOCOLINFO() {
		return PROTOCOLINFO;
	}

	public void setPROTOCOLINFO(PROTOCOLINFO protocolinfo) {
		PROTOCOLINFO = protocolinfo;
	}

	public FPZT_JL[] getFPZT_JLS() {
		return FPZT_JLS;
	}

	public void setFPZT_JLS(FPZT_JL[] fpzt_jls) {
		FPZT_JLS = fpzt_jls;
	}

	public SB_PTFPSB_BTXX getSB_PTFPSB_BTXX() {
		return SB_PTFPSB_BTXX;
	}

	public void setSB_PTFPSB_BTXX(SB_PTFPSB_BTXX sb_ptfpsb_btxx) {
		SB_PTFPSB_BTXX = sb_ptfpsb_btxx;
	}

	public void setSB_HZXX(SB_HZXX sB_HZXX) {
		SB_HZXX = sB_HZXX;
	}

	public SB_HZXX getSB_HZXX() {
		return SB_HZXX;
	}

	public void setFPPD_JLS(FPPD_JL[] fPPD_JLS) {
		FPPD_JLS = fPPD_JLS;
	}

	public FPPD_JL[] getFPPD_JLS() {
		return FPPD_JLS;

	}

	public ESC_JL[] getESC_JLS() {
		return ESC_JLS;
	}

	public void setESC_JLS(ESC_JL[] eSC_JLS) {
		ESC_JLS = eSC_JLS;
	}

	public JCK_JL[] getJCK_JLS() {
		return JCK_JLS;
	}

	public void setJCK_JLS(JCK_JL[] jCK_JLS) {
		JCK_JLS = jCK_JLS;
	}

	public JDC_JL[] getJDC_JLS() {
		return JDC_JLS;
	}

	public void setJDC_JLS(JDC_JL[] jDC_JLS) {
		JDC_JLS = jDC_JLS;
	}

}
