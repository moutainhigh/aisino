package com.aisino.trans.util;

import java.io.Serializable;


/**
 * 
 * <p>
 * 交换数据
 * </p>
 * 
 */
public class Data implements Serializable{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String zipCode;
    private String encryptCode;
    private String content;
    private String dataDescription;
    private String data;
    private String codeType;
    private String sjZsbh;
    private String qyZsbh;

    public String getCodeType() {
        return codeType;
    }

    public void setCodeType(String codeType) {
        this.codeType = codeType;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDataDescription() {
        return dataDescription;
    }

    public void setDataDescription(String dataDescription) {
        this.dataDescription = dataDescription;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

	public String getSjZsbh() {
		return sjZsbh;
	}

	public void setSjZsbh(String sjZsbh) {
		this.sjZsbh = sjZsbh;
	}

	public String getQyZsbh() {
		return qyZsbh;
	}

	public void setQyZsbh(String qyZsbh) {
		this.qyZsbh = qyZsbh;
	}
}
