package com.aisino.domain.einvoice.model;

import com.aisino.domain.base.model.BaseObject;
import org.apache.commons.lang.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * 电商平台信息
 */
public final class Dsptxxkt extends BaseObject {

	private static final long serialVersionUID = 1L;

	private final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private String dsptbm;

	private String blrmc;

	private String blrzjhm;

	private String blrgddh;

	private String blrsjh;

	private String blryx;

	private String zcdssj;

	private String zczt;

	private String zch;

	private String sz_swjg_dm;

	private String swjgMc;

	/**
	 * 返回 updatetime 的值
	 *
	 * @return updatetime
	 */
	public Dsptxxkt() {
	}

	public String getDsptbm() {
		return dsptbm;
	}

	public void setDsptbm(String dsptbm) {
		this.dsptbm = dsptbm;
	}

	public String getBlrmc() {
		return blrmc;
	}

	public void setBlrmc(String blrmc) {
		this.blrmc = blrmc;
	}

	public String getBlrzjhm() {
		return blrzjhm;
	}

	public void setBlrzjhm(String blrzjhm) {
		this.blrzjhm = blrzjhm;
	}

	public String getBlrgddh() {
		return blrgddh;
	}

	public void setBlrgddh(String blrgddh) {
		this.blrgddh = blrgddh;
	}

	public String getBlrsjh() {
		return blrsjh;
	}

	public void setBlrsjh(String blrsjh) {
		this.blrsjh = blrsjh;
	}

	public String getBlryx() {
		return blryx;
	}

	public void setBlryx(String blryx) {
		this.blryx = blryx;
	}

	public String getZcdssj() {
		return zcdssj;
	}

	public String getZcdssjFormat() throws ParseException {
		/*	判断字符串为空	2014年7月28日16:12:36			*/
		if (StringUtils.isNotEmpty(zcdssj)) {
			if (zcdssj.length() >= 8) {
				return format.format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(zcdssj));
			} else {
				return "";
			}
		} else {
			return "";
		}
	}

	public void setZcdssj(String zcdssj) {
		this.zcdssj = zcdssj;
	}

	public String getZczt() {
		return zczt;
	}

	public void setZczt(String zczt) {
		this.zczt = zczt;
	}

	public String getZch() {
		return zch;
	}

	public void setZch(String zch) {
		this.zch = zch;
	}


	public String getSz_swjg_dm() {
		return sz_swjg_dm;
	}

	public void setSz_swjg_dm(String sz_swjg_dm) {
		this.sz_swjg_dm = sz_swjg_dm;
	}

	public String getSwjgMc() {
		return swjgMc;
	}

	public void setSwjgMc(String swjgMc) {
		this.swjgMc = swjgMc;
	}


}
