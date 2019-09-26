package com.aisino.protocol.bean;

/**
 * 电商企业票源请求信息
 * 
 * @author scott.li
 * @date： Aug 13, 2013 3:12:19 PM
 */
public class REQUEST_DSQYPYXX implements REQUEST_BEAN {

	private String DSPTBM; /* 电商平台编码 */
	private String NSRSBH; /* 纳税人识别号 */
	private String LRRQ_Q; /* 录入日期起 */
	private String LRRQ_Z; /* 录入日期止 */

	public String getDSPTBM() {
		return DSPTBM;
	}

	public void setDSPTBM(String dsptbm) {
		DSPTBM = dsptbm;
	}

	public String getNSRSBH() {
		return NSRSBH;
	}

	public void setNSRSBH(String nsrsbh) {
		NSRSBH = nsrsbh;
	}

	public String getLRRQ_Q() {
		return LRRQ_Q;
	}

	public void setLRRQ_Q(String lrrq_q) {
		LRRQ_Q = lrrq_q;
	}

	public String getLRRQ_Z() {
		return LRRQ_Z;
	}

	public void setLRRQ_Z(String lrrq_z) {
		LRRQ_Z = lrrq_z;
	}

}
