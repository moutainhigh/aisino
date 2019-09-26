/**
 * 文件名：REQUEST_PCDATA_CONDITION.java
 *
 * 创建人：曹健 - James.Cao@safesoftinc.com
 *
 * 创建时间：2010-3-28 下午12:48:24
 *
 * 版权所有：航天信息股份有限公司
 */
package com.aisino.protocol.bean;

/**
 * <p>[描述信息：说明类的基本功能]</p>
 * 
 * @author 曹健 - James.Cao@safesoftinc.com
 * @version 1.0 Created on 2010-3-28 下午12:48:24
 */
public class REQUEST_PCDATA_CONDITION implements REQUEST_BEAN{

	private String NSRDZDAH;
	private String NSRSBH;
	private String JQBH;
	private String FJH;
	private String CZLX;
	private String CPU;
	private String ZBXLH;
	private String YPXLH;
	private String MAC;
	private String SQ_MW;
	private String ZC_XX_DATA;
	private String DSPTBM;
	public String getDSPTBM() {
		return DSPTBM;
	}

	public void setDSPTBM(String dSPTBM) {
		DSPTBM = dSPTBM;
	}
	public String getNSRDZDAH() {
		return NSRDZDAH;
	}
	
	public void setNSRDZDAH(String nsrdzdah) {
		NSRDZDAH = nsrdzdah;
	}
	
	public String getNSRSBH() {
		return NSRSBH;
	}
	
	public void setNSRSBH(String nsrsbh) {
		NSRSBH = nsrsbh;
	}
	
	public String getJQBH() {
		return JQBH;
	}
	
	public void setJQBH(String jqbh) {
		JQBH = jqbh;
	}
	
	public String getFJH() {
		return FJH;
	}
	
	public void setFJH(String fjh) {
		FJH = fjh;
	}
	
	public String getCZLX() {
		return CZLX;
	}
	
	public void setCZLX(String czlx) {
		CZLX = czlx;
	}
	
	public String getCPU() {
		return CPU;
	}
	
	public void setCPU(String cpu) {
		CPU = cpu;
	}
	
	public String getZBXLH() {
		return ZBXLH;
	}
	
	public void setZBXLH(String zbxlh) {
		ZBXLH = zbxlh;
	}
	
	public String getYPXLH() {
		return YPXLH;
	}
	
	public void setYPXLH(String ypxlh) {
		YPXLH = ypxlh;
	}
	
	public String getMAC() {
		return MAC;
	}
	
	public void setMAC(String mac) {
		MAC = mac;
	}
	
	public String getSQ_MW() {
		return SQ_MW;
	}
	
	public void setSQ_MW(String sq_mw) {
		SQ_MW = sq_mw;
	}

	public String getZC_XX_DATA() {
		return ZC_XX_DATA;
	}

	public void setZC_XX_DATA(String zCXXDATA) {
		ZC_XX_DATA = zCXXDATA;
	}	

	
}
