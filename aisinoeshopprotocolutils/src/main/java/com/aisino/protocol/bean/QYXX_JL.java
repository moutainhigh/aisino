/**
 * 
 */
package com.aisino.protocol.bean;

/**
 * @author fanlb
 * 
 */
public class QYXX_JL
{
	private com.aisino.protocol.bean.QYJB_XX QYJB_XX;
	private com.aisino.protocol.bean.DSQC_JL[] DSQC_JLS;
	private com.aisino.protocol.bean.JQXX_JL JQXX_JL;
	private com.aisino.protocol.bean.SSQ_SZ SSQ_SZ;
	//private WTDZ_JL[] WTDZ_JL;
	private SZSM_JL[] SZSM_JLS;
	private com.aisino.protocol.bean.PZ_JL[] PZ_JLS;
	
//	public WTDZ_JL[] getWTDZ_JL() {
//		return WTDZ_JL;
//	}
//
//	public void setWTDZ_JL(WTDZ_JL[] wtdz_jl) {
//		WTDZ_JL = wtdz_jl;
//	}

	public com.aisino.protocol.bean.QYJB_XX getQYJB_XX()
	{
		return QYJB_XX;
	}

	public void setQYJB_XX(com.aisino.protocol.bean.QYJB_XX qyjb_xx)
	{
		QYJB_XX = qyjb_xx;
	}

	public com.aisino.protocol.bean.DSQC_JL[] getDSQC_JLS()
	{
		return DSQC_JLS;
	}

	public void setDSQC_JLS(DSQC_JL[] dsqc_jlS)
	{
		DSQC_JLS = dsqc_jlS;
	}

	public SZSM_JL[] getSZSM_JLS()
	{
		return SZSM_JLS;
	}

	public void setSZSM_JL(SZSM_JL[] szsm_jlS)
	{
		SZSM_JLS = szsm_jlS;
	}

	public com.aisino.protocol.bean.PZ_JL[] getPZ_JLS()
	{
		return PZ_JLS;
	}

	public void setPZ_JLS(PZ_JL[] pz_jlS)
	{
		PZ_JLS = pz_jlS;
	}

	public void setJQXX_JL(com.aisino.protocol.bean.JQXX_JL jQXX_JL) {
		JQXX_JL = jQXX_JL;
	}

	public com.aisino.protocol.bean.JQXX_JL getJQXX_JL() {
		return JQXX_JL;
	}

	public void setSSQ_SZ(com.aisino.protocol.bean.SSQ_SZ sSQ_SZ) {
		SSQ_SZ = sSQ_SZ;
	}

	public com.aisino.protocol.bean.SSQ_SZ getSSQ_SZ() {
		return SSQ_SZ;
	}
}
