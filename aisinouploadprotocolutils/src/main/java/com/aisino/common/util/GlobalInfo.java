package com.aisino.common.util;

/**
 * 
 * <p>[描述信息：全局信息]</p>
 *
 * @author: zhongsiwei
 * @version 1.0 Created on 2015-3-6 上午09:15:42
 */
public class GlobalInfo {
    private String globalInfo;
    private String appId;//应用标识（ZZSDZFP）
    private String interfaceCode;//业务编码
    private String passWord; /* 10位随机数+{（10位随机数+密码）MD5} */
    private String userName;//纳税人识别号
    private String requestCode;//数据交换请求发出方代码
    private String requestTime;//数据交换请求发出时间（业务请求当前时间）
    private String responseCode;// 数据交换请求接受方代码
    private String dataExchangeId;//数据交换流水号
    private String taxpayerIdentifyNo;//纳税人识别号
    private String version; 
    private String terminalCode; 
    private String authorizationCode; //接入系统平台授权码
    private String fjh; //分机号
    
    
	public String getVersion() {
        return version;
    }
    public void setVersion(String version) {
        this.version = version;
    }
    public String getTerminalCode() {
        return terminalCode;
    }
    public void setTerminalCode(String terminalCode) {
        this.terminalCode = terminalCode;
    }
    public String getAuthorizationCode() {
        return authorizationCode;
    }
    public void setAuthorizationCode(String authorizationCode) {
        this.authorizationCode = authorizationCode;
    }
    public String getFjh() {
        return fjh;
    }
    public void setFjh(String fjh) {
        this.fjh = fjh;
    }
    public String getTaxpayerIdentifyNo() {
		return taxpayerIdentifyNo;
	}
	public void setTaxpayerIdentifyNo(String taxpayerIdentifyNo) {
		this.taxpayerIdentifyNo = taxpayerIdentifyNo;
	}
	public String getGlobalInfo() {
		return globalInfo;
	}
	public void setGlobalInfo(String globalInfo) {
		this.globalInfo = globalInfo;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getInterfaceCode() {
		return interfaceCode;
	}
	public void setInterfaceCode(String interfaceCode) {
		this.interfaceCode = interfaceCode;
	}
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getRequestCode() {
		return requestCode;
	}
	public void setRequestCode(String requestCode) {
		this.requestCode = requestCode;
	}
	public String getRequestTime() {
		return requestTime;
	}
	public void setRequestTime(String requestTime) {
		this.requestTime = requestTime;
	}
	public String getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}
	public String getDataExchangeId() {
		return dataExchangeId;
	}
	public void setDataExchangeId(String dataExchangeId) {
		this.dataExchangeId = dataExchangeId;
	}

}
