package com.aisino.domain.einvoice.model;

import com.aisino.domain.base.model.BaseObject;
import org.apache.commons.lang.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Dsptxx extends BaseObject {
	private static final long serialVersionUID = 1L;
	private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private String dsptbm;
	private String dsptmc;
	private String icp;
	private String dsptip;
	private String dswzym;
	private String zbdwmc;
	private String zbdwswdjzh;
	private String frdb;
	private String blrmc;
	private String blrzjhm;
	private String blrgddh;
	private String blrsjh;
	private String blryx;
	private String zcdssj;
	private String zczt;
	private String frdbzjhm;
	private String zbdwdz;
	private String zch;
	private String swjgMc;
	private String sz_swjg_dm;
	private String updatetime;

	/**
	 * 返回 updatetime 的值
	 *
	 * @return updatetime
	 */

	public String getUpdatetime() {
		return updatetime;
	}

	/**
	 * 设置 updatetime 的值
	 *
	 * @param updatetime
	 */
	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}

	public Dsptxx() {

	}

	public String getDsptbm() {
		return dsptbm;
	}

	public void setDsptbm(String dsptbm) {
		this.dsptbm = dsptbm;
	}

	public String getDsptmc() {
		return dsptmc;
	}

	public void setDsptmc(String dsptmc) {
		this.dsptmc = dsptmc;
	}

	public String getIcp() {
		return icp;
	}

	public void setIcp(String icp) {
		this.icp = icp;
	}

	public String getDsptip() {
		return dsptip;
	}

	public void setDsptip(String dsptip) {
		this.dsptip = dsptip;
	}

	public String getDswzym() {
		return dswzym;
	}

	public void setDswzym(String dswzym) {
		this.dswzym = dswzym;
	}

	public String getZbdwmc() {
		return zbdwmc;
	}

	public void setZbdwmc(String zbdwmc) {
		this.zbdwmc = zbdwmc;
	}

	public String getZbdwswdjzh() {
		return zbdwswdjzh;
	}

	public void setZbdwswdjzh(String zbdwswdjzh) {
		this.zbdwswdjzh = zbdwswdjzh;
	}

	public String getFrdb() {
		return frdb;
	}

	public void setFrdb(String frdb) {
		this.frdb = frdb;
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

	public String getFrdbzjhm() {
		return frdbzjhm;
	}

	public void setFrdbzjhm(String frdbzjhm) {
		this.frdbzjhm = frdbzjhm;
	}

	public String getZbdwdz() {
		return zbdwdz;
	}

	public void setZbdwdz(String zbdwdz) {
		this.zbdwdz = zbdwdz;
	}

	public String getZch() {
		return zch;
	}

	public void setZch(String zch) {
		this.zch = zch;
	}

	public String getSwjgMc() {
		return swjgMc;
	}

	public void setSwjgMc(String swjgMc) {
		this.swjgMc = swjgMc;
	}

	public String getSz_swjg_dm() {
		return sz_swjg_dm;
	}

	public void setSz_swjg_dm(String sz_swjg_dm) {
		this.sz_swjg_dm = sz_swjg_dm;
	}


}
