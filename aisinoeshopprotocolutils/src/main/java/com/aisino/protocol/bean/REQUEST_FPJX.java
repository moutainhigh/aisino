package com.aisino.protocol.bean;

import com.aisino.protocol.bean.*;
import com.aisino.protocol.bean.JXXX;

public class REQUEST_FPJX implements com.aisino.protocol.bean.REQUEST_BEAN {
	private String DSPTBM;
	private String NSRSBH;
	private String NSRDZDAH;
	private String JXSJ;
	private String JXYY;
	private com.aisino.protocol.bean.JXXX[] JXXXS;
	public String getDSPTBM() {
		return DSPTBM;
	}
	public void setDSPTBM(String dSPTBM) {
		DSPTBM = dSPTBM;
	}
	public String getNSRSBH() {
		return NSRSBH;
	}
	public void setNSRSBH(String nSRSBH) {
		NSRSBH = nSRSBH;
	}
	public String getNSRDZDAH() {
		return NSRDZDAH;
	}
	public void setNSRDZDAH(String nSRDZDAH) {
		NSRDZDAH = nSRDZDAH;
	}
	public String getJXSJ() {
		return JXSJ;
	}
	public void setJXSJ(String jXSJ) {
		JXSJ = jXSJ;
	}
	public String getJXYY() {
		return JXYY;
	}
	public void setJXYY(String jXYY) {
		JXYY = jXYY;
	}
	public com.aisino.protocol.bean.JXXX[] getJXXXS() {
		return JXXXS;
	}
	public void setJXXXS(JXXX[] jXXXS) {
		JXXXS = jXXXS;
	}
}
