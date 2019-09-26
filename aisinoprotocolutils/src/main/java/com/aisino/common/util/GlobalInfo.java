package com.aisino.common.util;

/**
 * <p>
 * 全局信息
 * </p>
 * 
 * @author 张士锋
 * @version 1.0 Created on Jul 9, 2012 3:10:36 PM
 */
public class GlobalInfo {
	private String taxpayerIdentifyNo;//纳税人识别号
	private String authorizationCode; //接入系统平台授权码
	private String terminalCode;      //终端标识符
	private String version;           //接口版本
	private String NSRSBH;   //纳税人识别号
	private String SQM; //接入系统平台授权码
	private String fjh; //分机号
	private String jqbh; //机器编号
	private String globalInfo;
	private String appId;
	private String interfaceId;
	private String interfaceCode;
	private String requestCode;
	private String requestTime;
	private String responseCode;
	private String dataExchangeId;
	private String passWord; /* 10位随机数+{（10位随机数+密码）MD5} */
	private String userName;

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

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getInterfaceId() {
		return interfaceId;
	}

	public void setInterfaceId(String interfaceId) {
		this.interfaceId = interfaceId;
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

	public String getGlobalInfo() {
		return globalInfo;
	}

	public void setGlobalInfo(String globalInfo) {
		this.globalInfo = globalInfo;
	}

	public String getInterfaceCode() {
		return interfaceCode;
	}

	public void setInterfaceCode(String interfaceCode) {

		this.interfaceCode = interfaceCode;
	}

	public String getTerminalCode() {
		return terminalCode;
	}

	public void setTerminalCode(String terminalCode) {
		this.terminalCode = terminalCode;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getNSRSBH() {
		return NSRSBH;
	}

	public void setNSRSBH(String NSRSBH) {
		this.NSRSBH = NSRSBH;
	}

	public String getSQM() {
		return SQM;
	}

	public void setSQM(String SQM) {
		this.SQM = SQM;
	}

	public String getFjh() {
		return fjh;
	}

	public void setFjh(String fjh) {
		this.fjh = fjh;
	}

	public String getJqbh() {
		return jqbh;
	}

	public void setJqbh(String jqbh) {
		this.jqbh = jqbh;
	}

	public String getTaxpayerIdentifyNo() {
		return taxpayerIdentifyNo;
	}

	public void setTaxpayerIdentifyNo(String taxpayerIdentifyNo) {
		this.taxpayerIdentifyNo = taxpayerIdentifyNo;
	}

	public String getAuthorizationCode() {
		return authorizationCode;
	}

	public void setAuthorizationCode(String authorizationCode) {
		this.authorizationCode = authorizationCode;
	}
}
