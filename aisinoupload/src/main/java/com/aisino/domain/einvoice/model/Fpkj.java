package com.aisino.domain.einvoice.model;

import org.apache.commons.lang.StringUtils;

import java.text.DecimalFormat;

public class Fpkj {

	//id，nsrdzdah，nsrsbh，fkdw，fkf_dm，nsrmc，skf_dm
	//fp_dm,fp_hm,kprq,yfp_dm,yfp_hm,kphjje,sykchje,kpy,
	//sky,kplx,swjg_dm,sphsl,kpxm,skm,ewm,fpzl_dm,zg_swjg_dm,
	// xhfsbh,xhfmc,zyspmc,skfyhzh,skfkhyh,fkfkhyh,fkfyhzh,
	//cjsj,xgsj,fplb,byzd,hy_mc,hy_dm,pycode,fkfdz,fkfdh,
	private DecimalFormat format = new DecimalFormat("0.00");

	private String id;
	private String nsrdzdah;
	private String nsrsbh;
	private String fkdw;
	private String fkf_dm;
	private String nsrmc;
	private String skf_dm;
	private String fp_dm;
	private String fp_hm;
	private String kprq;
	//fp_dm,fp_hm,kprq,yfp_dm,yfp_hm,kphjje,sykchje,kpy,
	private String yfp_dm;
	private String yfp_hm;
	private String kphjje;
	private String sykchje; //剩余可冲红金额
	private String kpy;
	//sky,kplx,swjg_dm,sphsl,kpxm,skm,ewm,fpzl_dm,zg_swjg_dm,
	private String sky;
	private String kplx;
	private String swjg_dm;
	private String sphsl;
	private String kpxm;
	private String ewm;
	private String skm;
	private String fpzl_dm;
	private String zg_swjg_dm;
	private String xhfsbh;
	private String xhfmc;
	private String zyspmc;
	private String skfyhzh;
	private String skfkhyh;
	private String fkfkhyh;
	private String fkfyhzh;
	private String cjsj;
	private String xgsj;
	private String fplb;
	private String byzd;
	private String hy_mc;
	//cjsj,xgsj,fplb,byzd,hy_mc,hy_dm,pycode,fkfdz,fkfdh,
	private String hy_dm;
	private String pycode;
	private String fkfdz;
	private String fkfdh;
	private String xhfdz;
	private String xhfdh;
	private String kpfs;
	private String ch_bz;
	private String mac;
	// xhfdz,xhfdh,kpfs,ch_bz,mac,kpzt,jsje,se,jsfs,kplsh,ghf_email,


	private String kpzt;
	private String jsje;
	private String se;
	private String jsfs;

	//sl,slbz,version,bz,sfcz,dsptbm,scbz,fpqqlsh,dkbz,pydm,xhf_nsrsbh
	private String kplsh;
	private String sl;
	private String slbz;
	private String version;
	private String bz;
	private String sfcz;
	private String dsptbm;
	private String scbz;
	private String fpqqlsh;
	private String dkbz;
	private String pydm;
	private String xhf_nsrsbh;
	private String ghfmc;
	private String ghf_nsrsbh;
	private String ghf_dz;
	private String ghf_gddh;
	private String ghf_sj;
	private String ghf_email;
	private String pdfpatch;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNsrdzdah() {
		return nsrdzdah;
	}

	public void setNsrdzdah(String nsrdzdah) {
		this.nsrdzdah = nsrdzdah;
	}

	public String getNsrsbh() {
		return nsrsbh;
	}

	public void setNsrsbh(String nsrsbh) {
		this.nsrsbh = nsrsbh;
	}

	public String getFkdw() {
		return fkdw;
	}

	public void setFkdw(String fkdw) {
		this.fkdw = fkdw;
	}

	public String getFkf_dm() {
		return fkf_dm;
	}

	public void setFkf_dm(String fkf_dm) {
		this.fkf_dm = fkf_dm;
	}

	public String getNsrmc() {
		return nsrmc;
	}

	public void setNsrmc(String nsrmc) {
		this.nsrmc = nsrmc;
	}

	public String getSkf_dm() {
		return skf_dm;
	}

	public void setSkf_dm(String skf_dm) {
		this.skf_dm = skf_dm;
	}

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

	public String getKprq() {
		return kprq;
	}

	public void setKprq(String kprq) {
		this.kprq = kprq;
	}

	public String getYfp_dm() {
		return yfp_dm;
	}

	public void setYfp_dm(String yfp_dm) {
		this.yfp_dm = yfp_dm;
	}

	public String getYfp_hm() {
		return yfp_hm;
	}

	public void setYfp_hm(String yfp_hm) {
		this.yfp_hm = yfp_hm;
	}

	public String getKphjje() {
		return kphjje;
	}

	public String getKphjjeFormat() {
		/*	判断字符串为空	2014年7月28日16:12:36			*/
		if (StringUtils.isNotEmpty(kphjje)) {
			return format.format(new Double(kphjje));
		}
		return "";
	}

	public void setKphjje(String kphjje) {
		this.kphjje = kphjje;
	}

	public String getSykchje() {
		return sykchje;
	}

	public void setSykchje(String sykchje) {
		this.sykchje = sykchje;
	}

	public String getKpy() {
		return kpy;
	}

	public void setKpy(String kpy) {
		this.kpy = kpy;
	}

	public String getSky() {
		return sky;
	}

	public void setSky(String sky) {
		this.sky = sky;
	}

	public String getKplx() {
		return kplx;
	}

	public void setKplx(String kplx) {
		this.kplx = kplx;
	}

	public String getSwjg_dm() {
		return swjg_dm;
	}

	public void setSwjg_dm(String swjg_dm) {
		this.swjg_dm = swjg_dm;
	}

	public String getSphsl() {
		return sphsl;
	}

	public void setSphsl(String sphsl) {
		this.sphsl = sphsl;
	}

	public String getKpxm() {
		return kpxm;
	}

	public void setKpxm(String kpxm) {
		this.kpxm = kpxm;
	}

	public String getEwm() {
		return ewm;
	}

	public void setEwm(String ewm) {
		this.ewm = ewm;
	}

	public String getSkm() {
		return skm;
	}

	public void setSkm(String skm) {
		this.skm = skm;
	}

	public String getFpzl_dm() {
		return fpzl_dm;
	}

	public void setFpzl_dm(String fpzl_dm) {
		this.fpzl_dm = fpzl_dm;
	}

	public String getZg_swjg_dm() {
		return zg_swjg_dm;
	}

	public void setZg_swjg_dm(String zg_swjg_dm) {
		this.zg_swjg_dm = zg_swjg_dm;
	}

	public String getXhfsbh() {
		return xhfsbh;
	}

	public void setXhfsbh(String xhfsbh) {
		this.xhfsbh = xhfsbh;
	}

	public String getXhfmc() {
		return xhfmc;
	}

	public void setXhfmc(String xhfmc) {
		this.xhfmc = xhfmc;
	}

	public String getZyspmc() {
		return zyspmc;
	}

	public void setZyspmc(String zyspmc) {
		this.zyspmc = zyspmc;
	}

	public String getSkfyhzh() {
		return skfyhzh;
	}

	public void setSkfyhzh(String skfyhzh) {
		this.skfyhzh = skfyhzh;
	}

	public String getSkfkhyh() {
		return skfkhyh;
	}

	public void setSkfkhyh(String skfkhyh) {
		this.skfkhyh = skfkhyh;
	}

	public String getFkfkhyh() {
		return fkfkhyh;
	}

	public void setFkfkhyh(String fkfkhyh) {
		this.fkfkhyh = fkfkhyh;
	}

	public String getFkfyhzh() {
		return fkfyhzh;
	}

	public void setFkfyhzh(String fkfyhzh) {
		this.fkfyhzh = fkfyhzh;
	}

	public String getCjsj() {
		return cjsj;
	}

	public void setCjsj(String cjsj) {
		this.cjsj = cjsj;
	}

	public String getXgsj() {
		return xgsj;
	}

	public void setXgsj(String xgsj) {
		this.xgsj = xgsj;
	}

	public String getFplb() {
		return fplb;
	}

	public void setFplb(String fplb) {
		this.fplb = fplb;
	}

	public String getByzd() {
		return byzd;
	}

	public void setByzd(String byzd) {
		this.byzd = byzd;
	}

	public String getHy_mc() {
		return hy_mc;
	}

	public void setHy_mc(String hy_mc) {
		this.hy_mc = hy_mc;
	}

	public String getHy_dm() {
		return hy_dm;
	}

	public void setHy_dm(String hy_dm) {
		this.hy_dm = hy_dm;
	}

	public String getPycode() {
		return pycode;
	}

	public void setPycode(String pycode) {
		this.pycode = pycode;
	}

	public String getFkfdz() {
		return fkfdz;
	}

	public void setFkfdz(String fkfdz) {
		this.fkfdz = fkfdz;
	}

	public String getFkfdh() {
		return fkfdh;
	}

	public void setFkfdh(String fkfdh) {
		this.fkfdh = fkfdh;
	}

	public String getXhfdz() {
		return xhfdz;
	}

	public void setXhfdz(String xhfdz) {
		this.xhfdz = xhfdz;
	}

	public String getXhfdh() {
		return xhfdh;
	}

	public void setXhfdh(String xhfdh) {
		this.xhfdh = xhfdh;
	}

	public String getKpfs() {
		return kpfs;
	}

	public void setKpfs(String kpfs) {
		this.kpfs = kpfs;
	}

	public String getCh_bz() {
		return ch_bz;
	}

	public void setCh_bz(String ch_bz) {
		this.ch_bz = ch_bz;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public String getKpzt() {
		return kpzt;
	}

	public void setKpzt(String kpzt) {
		this.kpzt = kpzt;
	}

	public String getJsje() {
		return jsje;
	}

	public void setJsje(String jsje) {
		this.jsje = jsje;
	}

	public String getSe() {
		return se;
	}

	public void setSe(String se) {
		this.se = se;
	}

	public String getJsfs() {
		return jsfs;
	}

	public void setJsfs(String jsfs) {
		this.jsfs = jsfs;
	}

	public String getKplsh() {
		return kplsh;
	}

	public void setKplsh(String kplsh) {
		this.kplsh = kplsh;
	}

	public String getGhf_email() {
		return ghf_email;
	}

	public void setGhf_email(String ghf_email) {
		this.ghf_email = ghf_email;
	}

	public String getSl() {
		return sl;
	}

	public void setSl(String sl) {
		this.sl = sl;
	}

	public String getSlbz() {
		return slbz;
	}

	public void setSlbz(String slbz) {
		this.slbz = slbz;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getBz() {
		return bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}

	public String getSfcz() {
		return sfcz;
	}

	public void setSfcz(String sfcz) {
		this.sfcz = sfcz;
	}

	public String getDsptbm() {
		return dsptbm;
	}

	public void setDsptbm(String dsptbm) {
		this.dsptbm = dsptbm;
	}

	public String getScbz() {
		return scbz;
	}

	public void setScbz(String scbz) {
		this.scbz = scbz;
	}

	public String getFpqqlsh() {
		return fpqqlsh;
	}

	public void setFpqqlsh(String fpqqlsh) {
		this.fpqqlsh = fpqqlsh;
	}

	public String getDkbz() {
		return dkbz;
	}

	public void setDkbz(String dkbz) {
		this.dkbz = dkbz;
	}

	public String getPydm() {
		return pydm;
	}

	public void setPydm(String pydm) {
		this.pydm = pydm;
	}

	public String getXhf_nsrsbh() {
		return xhf_nsrsbh;
	}

	public void setXhf_nsrsbh(String xhf_nsrsbh) {
		this.xhf_nsrsbh = xhf_nsrsbh;
	}

	public String getGhfmc() {
		return ghfmc;
	}

	public void setGhfmc(String ghfmc) {
		this.ghfmc = ghfmc;
	}

	public String getGhf_nsrsbh() {
		return ghf_nsrsbh;
	}

	public void setGhf_nsrsbh(String ghf_nsrsbh) {
		this.ghf_nsrsbh = ghf_nsrsbh;
	}

	public String getGhf_dz() {
		return ghf_dz;
	}

	public void setGhf_dz(String ghf_dz) {
		this.ghf_dz = ghf_dz;
	}

	public String getGhf_gddh() {
		return ghf_gddh;
	}

	public void setGhf_gddh(String ghf_gddh) {
		this.ghf_gddh = ghf_gddh;
	}

	public String getGhf_sj() {
		return ghf_sj;
	}

	public void setGhf_sj(String ghf_sj) {
		this.ghf_sj = ghf_sj;
	}

	public String getPdfpatch() {
		return pdfpatch;
	}

	public void setPdfpatch(String pdfpatch) {
		this.pdfpatch = pdfpatch;
	}

}
