package com.aisino.domain.einvoice.model;

import java.sql.Timestamp;

import static com.google.common.base.Objects.toStringHelper;

/**
 * Created with IntelliJ IDEA.
 * User: bourne.lv
 * Date: 7/08/14
 * Time: 5:26 PM
 */
public final class FpDoneLog {

	//ID
	private Long fpkj_id;

	//发送税局花费时间
	private Long stax;

	//发送51花费时间
	private Long s51;

	//结束时间
	private Timestamp lasteddate;

	public Long getS51() {
		return s51;
	}

	public void setS51(Long s51) {
		this.s51 = s51;
	}

	public Long getFpkj_id() {
		return fpkj_id;
	}

	public void setFpkj_id(Long fpkj_id) {
		this.fpkj_id = fpkj_id;
	}

	public Long getStax() {
		return stax;
	}

	public void setStax(Long stax) {
		this.stax = stax;
	}

	public Timestamp getLasteddate() {
		return lasteddate;
	}

	public void setLasteddate(Timestamp lasteddate) {
		this.lasteddate = lasteddate;
	}

	@Override
	public String toString() {
		return toStringHelper(this).
				add("fpkj_id", fpkj_id).
				add("stax", stax).
				add("s51", s51).
				add("lasteddate", lasteddate).
				toString();
	}
}
