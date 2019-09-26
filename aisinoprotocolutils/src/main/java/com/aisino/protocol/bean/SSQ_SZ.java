package com.aisino.protocol.bean;

public class SSQ_SZ {

	/**
	 * 所属期
	 */
	private String SSQ;

	/**
	 * 所属期起
	 */
	private String SSQ_Q;

	/**
	 * 所属期止
	 */
	private String SSQ_Z;

	/**
	 * 下月锁死期
	 */
	private String XY_SSQ;

	public void setSSQ_Z(String sSQ_Z) {
		SSQ_Z = sSQ_Z;
	}

	public String getSSQ_Z() {
		return SSQ_Z;
	}

	public void setXY_SSQ(String xY_SSQ) {
		XY_SSQ = xY_SSQ;
	}

	public String getXY_SSQ() {
		return XY_SSQ;
	}

	public void setSSQ_Q(String sSQ_Q) {
		SSQ_Q = sSQ_Q;
	}

	public String getSSQ_Q() {
		return SSQ_Q;
	}

	public void setSSQ(String sSQ) {
		SSQ = sSQ;
	}

	public String getSSQ() {
		return SSQ;
	}

}
