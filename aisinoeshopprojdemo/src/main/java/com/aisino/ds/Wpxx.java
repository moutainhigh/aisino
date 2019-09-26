package com.aisino.ds;

import java.io.Serializable;

public class Wpxx implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String wpname;
	private Double wpjg;
	private int wpid;
	private int type;

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getWpname() {
		return wpname;
	}

	public void setWpname(String wpname) {
		this.wpname = wpname;
	}

	public Double getWpjg() {
		return wpjg;
	}

	public void setWpjg(Double wpjg) {
		this.wpjg = wpjg;
	}

	public int getWpid() {
		return wpid;
	}

	public void setWpid(int wpid) {
		this.wpid = wpid;
	}

}
