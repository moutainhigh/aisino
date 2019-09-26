package com.aisino.protocol.bean;

import com.aisino.protocol.bean.*;
import com.aisino.protocol.bean.SLXX;

public class REQUEST_FPSL implements com.aisino.protocol.bean.REQUEST_BEAN {
	private String SLDH;
	private String DSPTBM;
	private String NSRSBH;
	private String NSRDZDAH;
	private String SQSJ;
	private com.aisino.protocol.bean.SLXX[] SLXXS;

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
	
	public String getSQSJ() {
		return SQSJ;
	}
	
	public void setSQSJ(String sQSJ) {
		SQSJ = sQSJ;
	}
	
	public com.aisino.protocol.bean.SLXX[] getSLXXS() {
		return SLXXS;
	}
	
	public void setSLXXS(SLXX[] sLXXS) {
		SLXXS = sLXXS;
	}

	public String getSLDH() {
		return SLDH;
	}

	public void setSLDH(String sldh) {
		SLDH = sldh;
	}
}
