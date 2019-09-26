package com.aisino.protocol.bean;

public class REQUEST_FPSLJG implements REQUEST_BEAN{
	private String DSPTBM;
	private String SLDH;

	public String getDSPTBM() {
		return DSPTBM;
	}

	public void setDSPTBM(String dsptbm) {
		DSPTBM = dsptbm;
	}

    public String getSLDH() {
        return SLDH;
    }

    public void setSLDH(String sldh) {
        SLDH = sldh;
    }
}
