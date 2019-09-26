package com.aisino.log.domain;

import java.util.Date;

/**
 * 
 * <p>
 * 数据交互日志
 * </p>
 * 
 * @author 张士锋
 * @version 1.0 Created on Jul 26, 2012 3:07:01 PM
 */
public class DataLogBean {
	private String dataExChangeId;
	private String zipCode;
	private String encryptCode;
	private byte[] content;
	private String requestCode;
	private String responseCode;
	private Date date;
	private String interFaceCode;

	public String getRequestCode() {
		return requestCode;
	}

	public void setRequestCode(String requestCode) {
		this.requestCode = requestCode;
	}

	public String getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getDataExChangeId() {
		return dataExChangeId;
	}

	public void setDataExChangeId(String dataExChangeId) {
		this.dataExChangeId = dataExChangeId;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getEncryptCode() {
		return encryptCode;
	}

	public void setEncryptCode(String encryptCode) {
		this.encryptCode = encryptCode;
	}

	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}

	public String getInterFaceCode() {
		return interFaceCode;
	}

	public void setInterFaceCode(String interFaceCode) {
		this.interFaceCode = interFaceCode;
	}

}
