package com.aisino.protocol.bean;
/**
 * 纸质发票系统--实体
 * @author FWH
 *
 */
public class REQUEST_ZZFP {
	private String XHF_NSRSBH;
	private String YFP_DM;
	private String YFP_HM;
	private String RETURNCODE;//处理结果代码
	private String RETURNMESSAGE;//处理结果描述
	public String getXHF_NSRSBH() {
		return XHF_NSRSBH;
	}
	public void setXHF_NSRSBH(String xHF_NSRSBH) {
		XHF_NSRSBH = xHF_NSRSBH;
	}
	public String getYFP_DM() {
		return YFP_DM;
	}
	public void setYFP_DM(String yFP_DM) {
		YFP_DM = yFP_DM;
	}
	public String getYFP_HM() {
		return YFP_HM;
	}
	public void setYFP_HM(String yFP_HM) {
		YFP_HM = yFP_HM;
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
	
}
