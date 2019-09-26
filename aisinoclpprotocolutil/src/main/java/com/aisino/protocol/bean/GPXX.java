package com.aisino.protocol.bean;

import org.apache.commons.lang.StringUtils;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class GPXX {

	private String NSRSBH;
	private String NSRMC;
	private String XS_SWJG_DM;
	private String XS_SWJG_MC;
	private String GPSJ;

	public String getNSRSBH() {
		return NSRSBH;
	}

	public void setNSRSBH(String nsrsbh) throws Exception {
		BASE64Encoder encode = new BASE64Encoder();
		//修改String串为空的判断
		if(StringUtils.isNotEmpty(nsrsbh)){
			//nsrsbh = new String(nsrsbh.getBytes("UTF-8"),"GBK");
			NSRSBH = encode.encode(nsrsbh.getBytes("UTF-8"));
		}else{
			NSRSBH = "";
		}
	}

	public String getNSRMC() {
		return NSRMC;
	}

	public void setNSRMC(String nsrmc) throws Exception{
		BASE64Encoder encode = new BASE64Encoder();
		BASE64Decoder decode  = new BASE64Decoder();
		//修改String串为空的判断
		if(StringUtils.isNotEmpty(nsrmc)){
			//nsrmc = new String(nsrmc.getBytes("UTF-8"),"GBK");
			nsrmc = new String(decode.decodeBuffer(nsrmc));
			NSRMC = encode.encode(nsrmc.getBytes("UTF-8"));
		}else{
			NSRMC = "";
		}
	}

	public String getXS_SWJG_DM() {
		return XS_SWJG_DM;
	}

	public void setXS_SWJG_DM(String xs_swjg_dm) throws Exception{
		BASE64Encoder encode = new BASE64Encoder();
		//修改String串为空的判断
		if(StringUtils.isNotEmpty(xs_swjg_dm)){
			//xs_swjg_dm = new String(xs_swjg_dm.getBytes("UTF-8"),"GBK");
			XS_SWJG_DM = encode.encode(xs_swjg_dm.getBytes("UTF-8"));
		}else{
			XS_SWJG_DM = "";
		}
	}

	public String getXS_SWJG_MC() {
		return XS_SWJG_MC;
	}

	public void setXS_SWJG_MC(String xs_swjg_mc) throws Exception{
		BASE64Encoder encode = new BASE64Encoder();
		//修改String串为空的判断
		if(StringUtils.isNotEmpty(xs_swjg_mc)){
			//xs_swjg_mc = new String(xs_swjg_mc.getBytes("UTF-8"),"GBK");
			XS_SWJG_MC = encode.encode(xs_swjg_mc.getBytes("UTF-8"));
		}else{
			XS_SWJG_MC = "";
		}
	}

	public String getGPSJ() {
		return GPSJ;
	}

	public void setGPSJ(String gpsj) throws Exception{
		BASE64Encoder encode = new BASE64Encoder();
		//修改String串为空的判断
		if(StringUtils.isNotEmpty(gpsj)){
			//gpsj = new String(gpsj.getBytes("UTF-8"),"GBK");
			GPSJ = encode.encode(gpsj.getBytes("UTF-8"));
		}else{
			GPSJ = "";
		}
	}

}
