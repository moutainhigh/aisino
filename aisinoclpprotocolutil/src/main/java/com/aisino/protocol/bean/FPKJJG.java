package com.aisino.protocol.bean;
/**
 * 
 * <p>[描述信息：发票开具信息bean]</p>
 *
 * @author scott.li
 * @version 1.0 Created on Nov 11, 2013 9:37:30 AM
 */
public class FPKJJG {
	private String KPLSH;
    private String FP_DM;
    private String FP_HM;
    private String CWMS;
    public String getKPLSH() {
		return KPLSH;
	}
	public void setKPLSH(String kPLSH) {
		KPLSH = kPLSH;
	}
	public String getFP_DM() {
        return FP_DM;
    }
    public void setFP_DM(String fp_dm) {
        FP_DM = fp_dm;
    }
    public String getFP_HM() {
        return FP_HM;
    }
    public void setFP_HM(String fp_hm) {
        FP_HM = fp_hm;
    }
    public String getCWMS() {
        return CWMS;
    }
    public void setCWMS(String cwms) {
        CWMS = cwms;
    }
    
}
