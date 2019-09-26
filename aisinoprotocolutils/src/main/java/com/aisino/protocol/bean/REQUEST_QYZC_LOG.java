package com.aisino.protocol.bean;

public class REQUEST_QYZC_LOG implements REQUEST_BEAN {
	private String NSRSBH;
	private String USER_ACCOUNT;
	private String PASSWD;
	private String ZCSJ;
	private String USERNAME;

	public String getNSRSBH() {
		return NSRSBH;
	}

	public void setNSRSBH(String nSRSBH) {
		NSRSBH = nSRSBH;
	}

	public String getUSER_ACCOUNT() {
		return USER_ACCOUNT;
	}

	public void setUSER_ACCOUNT(String uSER_ACCOUNT) {
		USER_ACCOUNT = uSER_ACCOUNT;
	}

	public String getPASSWD() {
		return PASSWD;
	}

	public void setPASSWD(String pASSWD) {
		PASSWD = pASSWD;
	}

	public String getZCSJ() {
		return ZCSJ;
	}

	public void setZCSJ(String zCSJ) {
		ZCSJ = zCSJ;
	}

	public String getUSERNAME() {
		return USERNAME;
	}

	public void setUSERNAME(String uSERNAME) {
		USERNAME = uSERNAME;
	}

	/**
	 * <p>
	 * 功能实现描述
	 * </p>
	 * 
	 * @see REQUEST_BEAN#getDSPTBM()
	 * @author: jerome.wang
	 * @date: Created on 2013-11-8 下午2:58:19
	 */
	public String getDSPTBM() {
		return null;
	}

}
