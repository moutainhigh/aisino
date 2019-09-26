package com.aisino.protocol.bean;
/**
 * 获取发票种类信息
 *
 * @author 梁国栋
 * 2014-10-23
 */
public class RESPONSE_COMMON_DSQYPZHDXX {

	private String NSRSBH;// 纳税人识别号
	private String NSRDZDAH;// 纳税人电子档案号
	private String FPZL_DM;//发票种类代码
	private String FPZL_MC;//发票种类名称
	private String MYGPZGSL;//每月购票最高数量
	private String KPZGXE;//开票最高限额
	private String MYCPZGSL;//持票最高数量
	
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
	public String getFPZL_DM() {
		return FPZL_DM;
	}
	public void setFPZL_DM(String fPZLDM) {
		FPZL_DM = fPZLDM;
	}
	public String getFPZL_MC() {
		return FPZL_MC;
	}
	public void setFPZL_MC(String fPZLMC) {
		FPZL_MC = fPZLMC;
	}
	public String getMYGPZGSL() {
		return MYGPZGSL;
	}
	public void setMYGPZGSL(String mYGPZGSL) {
		MYGPZGSL = mYGPZGSL;
	}
	public String getKPZGXE() {
		return KPZGXE;
	}
	public void setKPZGXE(String kPZGXE) {
		KPZGXE = kPZGXE;
	}
	public String getMYCPZGSL() {
		return MYCPZGSL;
	}
	public void setMYCPZGSL(String mYCPZGSL) {
		MYCPZGSL = mYCPZGSL;
	}
	
	
}
