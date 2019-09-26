package com.aisino.protocol.bean;

public class REQUEST_FPMXDZ implements REQUEST_BEAN{
	
	private String DSPTBM;

	private String DZLSH;
	
	private String KPSL;

	private REQUEST_FPMXDZ_ZB[] REQUEST_FPMXDZ_ZBS;
	
	/**
	 * @return the dSPTBM
	 */
	public String getDSPTBM() {
		return DSPTBM;
	}

	/**
	 * @param dsptbm the dSPTBM to set
	 */
	public void setDSPTBM(String dsptbm) {
		DSPTBM = dsptbm;
	}

	/**
	 * @return the dZLSH
	 */
	public String getDZLSH() {
		return DZLSH;
	}

	/**
	 * @param dzlsh the dZLSH to set
	 */
	public void setDZLSH(String dzlsh) {
		DZLSH = dzlsh;
	}

	/**
	 * @return the kPSL
	 */
	public String getKPSL() {
		return KPSL;
	}

	/**
	 * @param kpsl the kPSL to set
	 */
	public void setKPSL(String kpsl) {
		KPSL = kpsl;
	}

	public REQUEST_FPMXDZ_ZB[] getREQUEST_FPMXDZ_ZBS() {
		return REQUEST_FPMXDZ_ZBS;
	}

	public void setREQUEST_FPMXDZ_ZBS(REQUEST_FPMXDZ_ZB[] rEQUEST_FPMXDZ_ZBS) {
		REQUEST_FPMXDZ_ZBS = rEQUEST_FPMXDZ_ZBS;
	}

}
