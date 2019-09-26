package com.aisino.protocol.bean;

public class REQUEST_DOWNLOAD_RESULT implements REQUEST_BEAN {
	private String DATA_EXCHANGE_ID;

	public String getDATA_EXCHANGE_ID() {
		return DATA_EXCHANGE_ID;
	}

	public void setDATA_EXCHANGE_ID(String dATA_EXCHANGE_ID) {
		DATA_EXCHANGE_ID = dATA_EXCHANGE_ID;
	}

	private String DSPTBM;

	public String getDSPTBM() {
		return DSPTBM;
	}

	public void setDSPTBM(String dsptbm) {
		DSPTBM = dsptbm;
	}

}
