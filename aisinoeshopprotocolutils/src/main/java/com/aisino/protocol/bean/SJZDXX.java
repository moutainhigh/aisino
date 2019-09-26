package com.aisino.protocol.bean;

import com.aisino.protocol.bean.*;
import com.aisino.protocol.bean.NCPSP;

/**
 * @author korn.yang
 * @project_name wlkp_sc
 * Sep 7, 2010 4:06:48 PM
 */
public class SJZDXX {
	private com.aisino.protocol.bean.XZQH[] XZQHS;
	private com.aisino.protocol.bean.NCPSP[] NCPSPS;
	
	
	public com.aisino.protocol.bean.XZQH[] getXZQHS() {
		return XZQHS;
	}
	public void setXZQHS(com.aisino.protocol.bean.XZQH[] xzqhs) {
		XZQHS = xzqhs;
	}
	public com.aisino.protocol.bean.NCPSP[] getNCPSPS() {
		return NCPSPS;
	}
	public void setNCPSPS(NCPSP[] ncpsps) {
		NCPSPS = ncpsps;
	}
	
	
}
