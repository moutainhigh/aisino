package com.aisino.protocol.bean;

import com.aisino.protocol.bean.*;
import com.aisino.protocol.bean.FPSLJG;

public class RESPONSE_FPSLJG extends com.aisino.protocol.bean.RESPONSE_BEAN {
	private String RETURNCODE;
	private String RETURNMESSAGE;
	private String SLDH;
	private String NSRSBH;
	private String SHZT;//申领单审核状态
	private String SHBTGYY;//审核不通过原因
    private com.aisino.protocol.bean.FPSLJG[] FPSLJGS;//销售信息 list
	public String getNSRSBH() {
		return NSRSBH;
	}
	public void setNSRSBH(String nSRSBH) {
		NSRSBH = nSRSBH;
	}
	
	
	public String getRETURNCODE() {
		return RETURNCODE;
	}
	public void setRETURNCODE(String rETURNCODE) {
		RETURNCODE = rETURNCODE;
	}
	public String getRETURNMESSAGE() {
		return RETURNMESSAGE;
	}
	public void setRETURNMESSAGE(String rETURNMESSAGE) {
		RETURNMESSAGE = rETURNMESSAGE;
	}
	public String getSLDH() {
		return SLDH;
	}
	public void setSLDH(String sLDH) {
		SLDH = sLDH;
	}
	public String getSHZT() {
		return SHZT;
	}
	public void setSHZT(String sHZT) {
		SHZT = sHZT;
	}
	public String getSHBTGYY() {
		return SHBTGYY;
	}
	public void setSHBTGYY(String sHBTGYY) {
		SHBTGYY = sHBTGYY;
	}
	public com.aisino.protocol.bean.FPSLJG[] getFPSLJGS() {
		return FPSLJGS;
	}
	public void setFPSLJGS(FPSLJG[] fPSLJGS) {
		FPSLJGS = fPSLJGS;
	}
}
