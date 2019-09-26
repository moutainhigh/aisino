package com.aisino.trans.util;

import java.io.Serializable;

/**
 * <p>返回信息</p>
 */
public class ReturnStateInfo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String returnCode;
	private String returnMessage;
	private String returnStateInfo;
	public String getReturnCode() {
		return returnCode;
	}
	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}
	public String getReturnMessage() {
		return returnMessage;
	}
	public void setReturnMessage(String returnMessage) {
		this.returnMessage = returnMessage;
	}
	public String getReturnStateInfo() {
		return returnStateInfo;
	}
	public void setReturnStateInfo(String returnStateInfo) {
		this.returnStateInfo = returnStateInfo;
	}
	
}
