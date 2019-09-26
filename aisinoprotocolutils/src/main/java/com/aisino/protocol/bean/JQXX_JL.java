package com.aisino.protocol.bean;

/**
 * 文件名：JQXX_JL.java
 * 
 * 创建人：曹健 - James.Cao@safesoftinc.com
 * 
 * 创建时间：2010-3-30 下午05:22:21
 * 
 * 版权所有：航天信息股份有限公司
 */

public class JQXX_JL {

	/**
	 * 机器编号
	 */
	private String JQBH;

	/**
	 * 分机号
	 */
	private String FJH;

	private String CSHSH;

	public String getCSHSH() {
		return CSHSH;
	}

	public void setCSHSH(String cshsh) {
		CSHSH = cshsh;
	}

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
}
