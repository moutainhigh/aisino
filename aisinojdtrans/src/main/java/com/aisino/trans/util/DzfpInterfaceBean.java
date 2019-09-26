package com.aisino.trans.util;

import java.io.Serializable;

public class DzfpInterfaceBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6946246081345859539L;
	
	private GlobalInfo globalInfo;
	private ReturnStateInfo returneStateInfo;
	private Data data;
	
	public GlobalInfo getGlobalInfo() {
		return globalInfo;
	}
	public void setGlobalInfo(GlobalInfo globalInfo) {
		this.globalInfo = globalInfo;
	}
	public ReturnStateInfo getReturneStateInfo() {
		return returneStateInfo;
	}
	public void setReturneStateInfo(ReturnStateInfo returneStateInfo) {
		this.returneStateInfo = returneStateInfo;
	}
	public Data getData() {
		return data;
	}
	public void setData(Data data) {
		this.data = data;
	}

}
