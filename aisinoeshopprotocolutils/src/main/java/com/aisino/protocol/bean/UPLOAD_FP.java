/**
 * 
 */
package com.aisino.protocol.bean;

/**
 * @author fanlb
 *
 */
public class UPLOAD_FP
{
	private com.aisino.protocol.bean.PROTOCOLINFO PROTOCOLINFO;
	private com.aisino.protocol.bean.FPZT_JL[] FPZT_JLS;
	private com.aisino.protocol.bean.SB_HZXX SB_HZXX;
	private com.aisino.protocol.bean.FPPD_JL[] FPPD_JLS;
	
	private com.aisino.protocol.bean.SB_PTFPSB_BTXX SB_PTFPSB_BTXX;
	
	private com.aisino.protocol.bean.ESC_JL[] ESC_JLS;
	
	private com.aisino.protocol.bean.JCK_JL[] JCK_JLS;
	
	private com.aisino.protocol.bean.JDC_JL[] JDC_JLS;
	
	public PROTOCOLINFO getPROTOCOLINFO()
	{
		return PROTOCOLINFO;
	}
	public void setPROTOCOLINFO(PROTOCOLINFO protocolinfo)
	{
		PROTOCOLINFO = protocolinfo;
	}
	public com.aisino.protocol.bean.FPZT_JL[] getFPZT_JLS()
	{
		return FPZT_JLS;
	}
	public void setFPZT_JLS(com.aisino.protocol.bean.FPZT_JL[] fpzt_jls)
	{
		FPZT_JLS = fpzt_jls;
	}
	public SB_PTFPSB_BTXX getSB_PTFPSB_BTXX() {
		return SB_PTFPSB_BTXX;
	}
	public void setSB_PTFPSB_BTXX(SB_PTFPSB_BTXX sb_ptfpsb_btxx) {
		SB_PTFPSB_BTXX = sb_ptfpsb_btxx;
	}
	public void setSB_HZXX(com.aisino.protocol.bean.SB_HZXX sB_HZXX) {
		SB_HZXX = sB_HZXX;
	}
	public com.aisino.protocol.bean.SB_HZXX getSB_HZXX() {
		return SB_HZXX;
	}
	public void setFPPD_JLS(com.aisino.protocol.bean.FPPD_JL[] fPPD_JLS) {
		FPPD_JLS = fPPD_JLS;
	}
	public FPPD_JL[] getFPPD_JLS() {
		return FPPD_JLS;
		
	}
	public com.aisino.protocol.bean.ESC_JL[] getESC_JLS() {
		return ESC_JLS;
	}
	public void setESC_JLS(ESC_JL[] eSC_JLS) {
		ESC_JLS = eSC_JLS;
	}
	public com.aisino.protocol.bean.JCK_JL[] getJCK_JLS() {
		return JCK_JLS;
	}
	public void setJCK_JLS(JCK_JL[] jCK_JLS) {
		JCK_JLS = jCK_JLS;
	}
	public com.aisino.protocol.bean.JDC_JL[] getJDC_JLS() {
		return JDC_JLS;
	}
	public void setJDC_JLS(JDC_JL[] jDC_JLS) {
		JDC_JLS = jDC_JLS;
	}
	
}
