package com.aisino.protocol.bean;

import java.util.List;
import java.util.Map;

public class NSRXX {
	private String DQMC;
	private String DSPTBM;
	private String NSRSBH;
	private String NSRMC;
	private String SFKTZZS;
	private String ZZSDZFPURL;
	private String ZZSDZFPMETHOD;
	private String ZZSFPBL;
	private String SFKTPTDZFP;
	private String PTDZFPURL;
	private String PTDZFPMETHOD;
	private FP_XS[] FP_XSS;
	private Map<String, FP_XS> fpxsMap;
	
	public String getDQMC() {
		return DQMC;
	}
	public void setDQMC(String dQMC) {
		DQMC = dQMC;
	}
	public String getNSRSBH() {
		return NSRSBH;
	}
	public void setNSRSBH(String nSRSBH) {
		NSRSBH = nSRSBH;
	}
	public String getNSRMC() {
		return NSRMC;
	}
	public void setNSRMC(String nSRMC) {
		NSRMC = nSRMC;
	}
	public String getSFKTZZS() {
		return SFKTZZS;
	}
	public void setSFKTZZS(String sFKTZZS) {
		SFKTZZS = sFKTZZS;
	}
	public String getZZSDZFPURL() {
		return ZZSDZFPURL;
	}
	public void setZZSDZFPURL(String zZSDZFPURL) {
		ZZSDZFPURL = zZSDZFPURL;
	}
	public String getZZSFPBL() {
		return ZZSFPBL;
	}
	public void setZZSFPBL(String zZSFPBL) {
		ZZSFPBL = zZSFPBL;
	}
	public String getSFKTPTDZFP() {
		return SFKTPTDZFP;
	}
	public void setSFKTPTDZFP(String sFKTPTDZFP) {
		SFKTPTDZFP = sFKTPTDZFP;
	}
	public String getPTDZFPURL() {
		return PTDZFPURL;
	}
	public void setPTDZFPURL(String pTDZFPURL) {
		PTDZFPURL = pTDZFPURL;
	}
	public String getZZSDZFPMETHOD() {
		return ZZSDZFPMETHOD;
	}
	public void setZZSDZFPMETHOD(String zZSDZFPMETHOD) {
		ZZSDZFPMETHOD = zZSDZFPMETHOD;
	}
	public String getPTDZFPMETHOD() {
		return PTDZFPMETHOD;
	}
	public void setPTDZFPMETHOD(String pTDZFPMETHOD) {
		PTDZFPMETHOD = pTDZFPMETHOD;
	}
	public FP_XS[] getFP_XSS() {
		return FP_XSS;
	}
	public void setFP_XSS(FP_XS[] fP_XSS) {
		FP_XSS = fP_XSS;
	}
	public Map<String, FP_XS> getFpxsMap() {
		return fpxsMap;
	}
	public void setFpxsMap(Map<String, FP_XS> fpxsMap) {
		this.fpxsMap = fpxsMap;
	}
	public String getDSPTBM() {
		return DSPTBM;
	}
	public void setDSPTBM(String dSPTBM) {
		DSPTBM = dSPTBM;
	}
}
