package com.aisino.protocol.bean;

import com.aisino.protocol.bean.*;
import com.aisino.protocol.bean.DZXX;

public class REQUEST_FPDZ implements com.aisino.protocol.bean.REQUEST_BEAN {
	private String DZLSH;
	private String DSBM;
	private String KPSL;
    private com.aisino.protocol.bean.DZXX[] DZXXS;
	public String getDZLSH() {
		return DZLSH;
	}
	public void setDZLSH(String dZLSH) {
		DZLSH = dZLSH;
	}
	public String getDSBM() {
		return DSBM;
	}
	public void setDSBM(String dSBM) {
		DSBM = dSBM;
	}
	public String getKPSL() {
		return KPSL;
	}
	public void setKPSL(String kPSL) {
		KPSL = kPSL;
	}
	public com.aisino.protocol.bean.DZXX[] getDZXXS() {
		return DZXXS;
	}
	public void setDZXXS(DZXX[] dZXXS) {
		DZXXS = dZXXS;
	}
	public String getDSPTBM() {
		return DSBM;
	}
}
