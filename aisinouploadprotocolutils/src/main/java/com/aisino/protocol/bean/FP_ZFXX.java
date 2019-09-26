package com.aisino.protocol.bean;

public class FP_ZFXX {
	//支付信息
    private String ZFFS;   /*支付方式*/
    private String ZFLSH;   /*支付流水号*/
    private String ZFPT;   /*支付平台*/
	public String getZFFS() {
		return ZFFS;
	}
	public void setZFFS(String zFFS) {
		ZFFS = zFFS;
	}
	public String getZFLSH() {
		return ZFLSH;
	}
	public void setZFLSH(String zFLSH) {
		ZFLSH = zFLSH;
	}
	public String getZFPT() {
		return ZFPT;
	}
	public void setZFPT(String zFPT) {
		ZFPT = zFPT;
	}
  
}
