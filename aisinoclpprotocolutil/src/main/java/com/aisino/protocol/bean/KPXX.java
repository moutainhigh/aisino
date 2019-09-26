package com.aisino.protocol.bean;

import org.apache.commons.lang.StringUtils;

import sun.misc.BASE64Encoder;

/**
 * 开票信息
 * 
 * @author Carl
 * @date Nov 13, 2010
 */
public class KPXX {

	private String NSRSBH;
	private String NSRMC;
	private String FKDW;
	private String FKFDM;
	private String FP_DM;
	private String FPHM;
	private String KPRQ;
	private String KPHJJE;
	private String SKM;
	private String FWM;
	private String MM;
	private String FKFDZ;
	private String FKFDH;
	private String XHFDZ;
	private String XHFDH;
	private String SPMC;
	
	public String getNSRSBH() {
		return NSRSBH;
	}
	public void setNSRSBH(String nSRSBH) throws Exception {
		BASE64Encoder encode = new BASE64Encoder();
		//修改String串为空的判断
		if( StringUtils.isNotEmpty(nSRSBH)){
			//nSRSBH = new String(nSRSBH.getBytes("UTF-8"),"GBK");
			NSRSBH = encode.encode(nSRSBH.getBytes("UTF-8"));
		}else{
			NSRSBH = "";
		}
	}
	public String getNSRMC() {
		return NSRMC;
	}
	public void setNSRMC(String nSRMC) throws Exception {
		BASE64Encoder encode = new BASE64Encoder();
		//修改String串为空的判断
		if( StringUtils.isNotEmpty(nSRMC)){
			//nSRMC = new String(nSRMC.getBytes("UTF-8"),"GBK");
			NSRMC = encode.encode(nSRMC.getBytes("UTF-8"));
		}else{
			NSRMC = "";
		}
	}
	public String getFKDW() {
		return FKDW;
	}
	public void setFKDW(String fKDW)throws Exception  {
		BASE64Encoder encode = new BASE64Encoder();
		//修改String串为空的判断
		if( StringUtils.isNotEmpty(fKDW)){
			//fKDW = new String(fKDW.getBytes("UTF-8"),"GBK");
			FKDW = encode.encode(fKDW.getBytes("UTF-8"));
		}else{
			FKDW = "";
		}
	}
	public String getFKFDM() {
		return FKFDM;
	}
	public void setFKFDM(String fKFDM) throws Exception {
		BASE64Encoder encode = new BASE64Encoder();
		//修改String串为空的判断
		if( StringUtils.isNotEmpty(fKFDM)){
			//fKFDM = new String(fKFDM.getBytes("UTF-8"),"GBK");
			FKFDM = encode.encode(fKFDM.getBytes("UTF-8"));
		}else{
			FKFDM = "";
		}
	}
	public String getFP_DM() {
		return FP_DM;
	}
	public void setFP_DM(String fPDM) throws Exception {
		BASE64Encoder encode = new BASE64Encoder();
		//修改String串为空的判断
		if( StringUtils.isNotEmpty(fPDM)){
			//fPDM = new String(fPDM.getBytes("UTF-8"),"GBK");
			FP_DM = encode.encode(fPDM.getBytes("UTF-8"));
		}else{
			FP_DM = "";
		}
	}
	public String getFPHM() {
		return FPHM;
	}
	public void setFPHM(String fPHM) throws Exception {
		BASE64Encoder encode = new BASE64Encoder();
		//修改String串为空的判断
		if( StringUtils.isNotEmpty(fPHM)){
			//fPHM = new String(fPHM.getBytes("UTF-8"),"GBK");
			FPHM = encode.encode(fPHM.getBytes("UTF-8"));
		}else{
			FPHM = "";
		}
	}
	public String getKPRQ() {
		return KPRQ;
	}
	public void setKPRQ(String kPRQ) throws Exception {
		BASE64Encoder encode = new BASE64Encoder();
		//修改String串为空的判断
		if( StringUtils.isNotEmpty(kPRQ)){
			//kPRQ = new String(kPRQ.getBytes("UTF-8"),"GBK");
			KPRQ = encode.encode(kPRQ.getBytes("UTF-8"));
		}else{
			KPRQ = "";
		}
	}
	public String getKPHJJE() {
		return KPHJJE;
	}
	public void setKPHJJE(String kPHJJE) throws Exception {
		BASE64Encoder encode = new BASE64Encoder();
		//修改String串为空的判断
		if( StringUtils.isNotEmpty(kPHJJE)){
			//kPHJJE = new String(kPHJJE.getBytes("UTF-8"),"GBK");
			KPHJJE = encode.encode(kPHJJE.getBytes("UTF-8"));
		}else{
			KPHJJE = "";
		}
	}
	public String getSKM() {
		return SKM;
	}
	public void setSKM(String sKM) throws Exception {
		BASE64Encoder encode = new BASE64Encoder();
		//修改String串为空的判断
		if( StringUtils.isNotEmpty(sKM)){
			//sKM = new String(sKM.getBytes("UTF-8"),"GBK");
			SKM = encode.encode(sKM.getBytes("UTF-8"));
		}else{
			SKM = "";
		}
	}
	public String getFWM() {
		return FWM;
	}
	public void setFWM(String fWM) throws Exception {
		BASE64Encoder encode = new BASE64Encoder();
		//修改String串为空的判断
		if( StringUtils.isNotEmpty(fWM)){
			//fWM = new String(fWM.getBytes("UTF-8"),"GBK");
			FWM = encode.encode(fWM.getBytes("UTF-8"));
		}else{
			FWM = "";
		}
	}
	public String getMM() {
		return MM;
	}
	public void setMM(String mM) throws Exception {
		BASE64Encoder encode = new BASE64Encoder();
		//修改String串为空的判断
		if( StringUtils.isNotEmpty(mM)){
			//mM = new String(mM.getBytes("UTF-8"),"GBK");
			MM = encode.encode(mM.getBytes("UTF-8"));
		}else{
			MM = "";
		}
	}
	public String getFKFDZ() {
		return FKFDZ;
	}
	public void setFKFDZ(String fKFDZ) throws Exception {
		BASE64Encoder encode = new BASE64Encoder();
		//修改String串为空的判断
		if( StringUtils.isNotEmpty(fKFDZ)){
			//fKFDZ = new String(fKFDZ.getBytes("UTF-8"),"GBK");
			FKFDZ = encode.encode(fKFDZ.getBytes("UTF-8"));
		}else{
			FKFDZ = "";
		}
	}
	public String getFKFDH() {
		return FKFDH;
	}
	public void setFKFDH(String fKFDH) throws Exception {
		BASE64Encoder encode = new BASE64Encoder();
		//修改String串为空的判断
		if( StringUtils.isNotEmpty(fKFDH)){
			//fKFDH = new String(fKFDH.getBytes("UTF-8"),"GBK");
			FKFDH = encode.encode(fKFDH.getBytes("UTF-8"));
		}else{
			FKFDH = "";
		}
	}
	public String getXHFDZ() {
		return XHFDZ;
	}
	public void setXHFDZ(String xHFDZ) throws Exception {
		BASE64Encoder encode = new BASE64Encoder();
		//修改String串为空的判断
		if( StringUtils.isNotEmpty(xHFDZ)){
			//xHFDZ = new String(xHFDZ.getBytes("UTF-8"),"GBK");
			XHFDZ = encode.encode(xHFDZ.getBytes("UTF-8"));
		}else{
			XHFDZ = "";
		}
	}
	public String getXHFDH() {
		return XHFDH;
	}
	public void setXHFDH(String xHFDH) throws Exception {
		BASE64Encoder encode = new BASE64Encoder();
		//修改String串为空的判断
		if( StringUtils.isNotEmpty(xHFDH)){
			//xHFDH = new String(xHFDH.getBytes("UTF-8"),"GBK");
			XHFDH = encode.encode(xHFDH.getBytes("UTF-8"));
		}else{
			XHFDH = "";
		}
	}
	public String getSPMC() {
		return SPMC;
	}
	public void setSPMC(String sPMC) throws Exception {
		BASE64Encoder encode = new BASE64Encoder();
		//修改String串为空的判断
		if( StringUtils.isNotEmpty(sPMC)){
			//xHFDH = new String(xHFDH.getBytes("UTF-8"),"GBK");
			SPMC = encode.encode(sPMC.getBytes("UTF-8"));
		}else{
			SPMC = "";
		}
	}
	
	
}
