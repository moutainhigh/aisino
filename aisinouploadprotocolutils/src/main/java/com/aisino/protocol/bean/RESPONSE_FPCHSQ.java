package com.aisino.protocol.bean;

public class RESPONSE_FPCHSQ extends RESPONSE_BEAN{
	private String SQLSH;
	private String RETURNCODE;
    private String RETURNMESSAGE;
    public String getRETURNCODE() {
        return RETURNCODE;
    }
    public void setRETURNCODE(String returncode) {
        RETURNCODE = returncode;
    }
    public String getRETURNMESSAGE() {
        return RETURNMESSAGE;
    }
    public void setRETURNMESSAGE(String returnmessage) {
        RETURNMESSAGE = returnmessage;
    }
	/**
	 * @return the sQLSH
	 */
	public String getSQLSH() {
		return SQLSH;
	}

	/**
	 * @param sqlsh the sQLSH to set
	 */
	public void setSQLSH(String sqlsh) {
		SQLSH = sqlsh;
	}
}
