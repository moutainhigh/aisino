/**
 * 文件名：CHANGE_REQUEST_CONDITION.java
 *
 * 创建人：曹健 - James.Cao@safesoftinc.com
 *
 * 创建时间：2010-3-30 下午06:15:08
 *
 * 版权所有：航天信息股份有限公司
 */
package com.aisino.protocol.bean;

/**
 * <p>
 * [描述信息：同步企业信息变更请求体]
 * </p>
 * 
 * @author 曹健 - James.Cao@safesoftinc.com
 * @version 1.0 Created on 2010-3-30 下午06:15:08
 */
public class CHANGE_REQUEST_CONDITION {

	/**
	 * 纳税人电子档案号
	 */
	private String NSRDZDAH;

	/**
	 * 纳税人识别号
	 */
	private String NSRSBH;

	/**
	 * 机器编号
	 */
	private String JQBH;

	/**
	 * 分机号
	 */
	private String FJH;

	public void setFJH(String fJH) {
		FJH = fJH;
	}

	public String getFJH() {
		return FJH;
	}

	public void setJQBH(String jQBH) {
		JQBH = jQBH;
	}

	public String getJQBH() {
		return JQBH;
	}

	public void setNSRSBH(String nSRSBH) {
		NSRSBH = nSRSBH;
	}

	public String getNSRSBH() {
		return NSRSBH;
	}

	public void setNSRDZDAH(String nSRDZDAH) {
		NSRDZDAH = nSRDZDAH;
	}

	public String getNSRDZDAH() {
		return NSRDZDAH;
	}
}
