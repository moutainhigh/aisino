package com.aisino.domain.einvoice.model;

import org.apache.commons.lang.StringUtils;

import java.text.DecimalFormat;

public class Fpkjmx {
	private DecimalFormat format = new DecimalFormat("0.00");
	private String kjid;
	private String sphxh;
	private String spmc;
	private String jldw;
	private String ggxh;
	private String spsl;
	private String spdj;
	private String spje;

	public String getKjid() {
		return kjid;
	}

	public void setKjid(String kjid) {
		this.kjid = kjid;
	}

	public String getSphxh() {
		return sphxh;
	}

	public void setSphxh(String sphxh) {
		this.sphxh = sphxh;
	}

	public String getSpmc() {
		return spmc;
	}

	public void setSpmc(String spmc) {
		this.spmc = spmc;
	}

	public String getJldw() {
		return jldw;
	}

	public void setJldw(String jldw) {
		this.jldw = jldw;
	}

	public String getGgxh() {
		return ggxh;
	}

	public void setGgxh(String ggxh) {
		this.ggxh = ggxh;
	}

	public String getSpsl() {
		return spsl;
	}

	public String getSpslStr() {
		/*	判断字符串为空	2014年7月28日16:12:36			*/
		if (StringUtils.isNotEmpty(spsl)) {
			return format.format(new Double(spsl));
		}
		return "";
	}

	public void setSpsl(String spsl) {
		this.spsl = spsl;
	}

	public String getSpdj() {
		return spdj;
	}

	public String getSpdjStr() {

    	/*	判断字符串为空	2014年7月28日16:12:36			*/
		if (StringUtils.isNotEmpty(spdj)) {
			return format.format(new Double(spdj));
		}
		return "";
	}

	public void setSpdj(String spdj) {
		this.spdj = spdj;
	}

	public String getSpje() {
		return spje;
	}

	public String getSpjeStr() {

    	/*	判断字符串为空	2014年7月28日16:12:36			*/
		if (StringUtils.isNotEmpty(spje)) {
			return format.format(new Double(spje));
		}
		return "";
	}

	public void setSpje(String spje) {
		this.spje = spje;
	}

	public String getZk() {
    	/*	判断字符串为空	2014年7月28日16:12:36			*/
		if (StringUtils.isNotEmpty(spsl)) {
			Double spjeDouble = new Double(spje);
			Double spdjDouble = new Double(spdj);
			Double spslDouble = new Double(spsl);
			Double zdDouble = spjeDouble - (spdjDouble * spslDouble);
			return format.format(zdDouble);
		}
		return "0.00";
	}
}
