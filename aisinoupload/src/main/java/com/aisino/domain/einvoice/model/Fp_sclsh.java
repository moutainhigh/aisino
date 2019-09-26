package com.aisino.domain.einvoice.model;


/**
 * @author zsf
 * @version 1.0
 * @created 2013-11-18 下午08:09:11
 */

public class Fp_sclsh {
	public String getFp_dm() {
		return fp_dm;
	}

	public void setFp_dm(String fp_dm) {
		this.fp_dm = fp_dm;
	}

	public String getFp_hm() {
		return fp_hm;
	}

	public void setFp_hm(String fp_hm) {
		this.fp_hm = fp_hm;
	}

	private String fp_dm;
	private String fp_hm;
	private String fpkj_zt;

	public String getFpkj_zt() {
		return fpkj_zt;
	}

	public void setFpkj_zt(String fpkj_zt) {
		this.fpkj_zt = fpkj_zt;
	}
}
