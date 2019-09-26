package com.aisino.protocol.bean;

/**
 *
 */
public class QYJB_XX {
	private String NSRDZDAH;
	private String NSRSBH;
	private String NSRMC;
	private String FRDB;
	private String FRZJHM;
	private String HY_DM;
	private String ZGSWGY;
	private String YXBZ = "1";
	private String SDH_BZ = "1";
	private String XGM_BZ = "1";
	private String YBNSR_BZ = "1";
	private String ZSFS_DM = "1";
	private String SBQSBZ = "1";
	private String ZDY_QYLX = "1";
	private String KPGLY;
	private String KPGLYPWD;
	private String KPMS;
	private String KPMSMC;
	private String NSRLX;
	private String NSRLXMC;
	private String KPJSL;
	private String HY_MC;
	private String NSRZT_MC;
	private String NSRZT_DM;
	private String GSYLB_DM;

	// 最大离线开票天数
	private String ZDLXKPSJ;
	// 抄报周期
	private String CBZQ;
	// 最大开票张数
	private String ZDKPZS;
	// 累计开票金额
	private String LJKPZE;
	// 终端类型
	private String ZDLX;
	/*
	 * 广西增加 自定义行业代码和名称
	 */
	private String ZDY_HY_DM;
	private String ZDY_HYMC;
	private String ESC_LX;

	public String getNSR_SWJG_DM() {
		return NSR_SWJG_DM;
	}

	public void setNSR_SWJG_DM(String nsr_swjg_dm) {
		NSR_SWJG_DM = nsr_swjg_dm;
	}

	public String getNSR_SWJG_MC() {
		return NSR_SWJG_MC;
	}

	public void setNSR_SWJG_MC(String nsr_swjg_mc) {
		NSR_SWJG_MC = nsr_swjg_mc;
	}

	public String getHZDJRQ() {
		return HZDJRQ;
	}

	public void setHZDJRQ(String hzdjrq) {
		HZDJRQ = hzdjrq;
	}

	public String getZY() {
		return ZY;
	}

	public String getJYFW() {
		return JYFW;
	}

	private String ZY;
	private String JYFW;
	private String NSR_SWJG_DM;
	private String NSR_SWJG_MC;
	private String HZDJRQ;

	public String getNSRDZDAH() {
		return NSRDZDAH;
	}

	public void setNSRDZDAH(String nsrdzdah) {
		NSRDZDAH = nsrdzdah;
	}

	public String getKPMS() {
		return KPMS;
	}

	public void setKPMS(String kpms) {
		KPMS = kpms;
		if ("1".equals(kpms)) {
			setKPMSMC("一户一机");
		} else if ("2".equals(kpms)) {
			setKPMSMC("一机多户");
		} else if ("3".equals(kpms)) {
			setKPMSMC("一户多机");
		}
	}

	public String getKPMSMC() {
		return KPMSMC;
	}

	public void setKPMSMC(String kpmsmc) {
		KPMSMC = kpmsmc;
	}

	public String getNSRLX() {
		return NSRLX;
	}

	public void setNSRLX(String nsrlx) {
		NSRLX = nsrlx;
	}

	public String getNSRLXMC() {
		return NSRLXMC;
	}

	public void setNSRLXMC(String nsrlxmc) {
		NSRLXMC = nsrlxmc;
	}

	public String getNSRSBH() {
		return NSRSBH;
	}

	public void setNSRSBH(String nsrsbh) {
		NSRSBH = nsrsbh;
	}

	public String getNSRMC() {
		return NSRMC;
	}

	public void setNSRMC(String nsrmc) {
		NSRMC = nsrmc;
	}

	public String getFRDB() {
		return FRDB;
	}

	public void setFRDB(String frdb) {
		FRDB = frdb;
	}

	public String getFRZJHM() {
		return FRZJHM;
	}

	public void setFRZJHM(String frzjhm) {
		FRZJHM = frzjhm;
	}

	public String getHY_DM() {
		return HY_DM;
	}

	public void setHY_DM(String hy_dm) {
		HY_DM = hy_dm;
	}

	public String getJYXMZWMC() {
		if (JYFW != null) {
			return JYFW;
		} else {
			return ZY;
		}
	}

	public void setZY(String zy) {
		ZY = zy;
	}

	public void setJYFW(String jyfw) {
		JYFW = jyfw;
	}

	public String getZGSWGY() {
		return ZGSWGY;
	}

	public void setZGSWGY(String zgswgy) {
		ZGSWGY = zgswgy;
	}

	public String getYXBZ() {
		return YXBZ;
	}

	public void setYXBZ(String yxbz) {
		YXBZ = yxbz;
	}

	public String getSDH_BZ() {
		return SDH_BZ;
	}

	public void setSDH_BZ(String sdh_bz) {
		SDH_BZ = sdh_bz;
	}

	public String getXGM_BZ() {
		return XGM_BZ;
	}

	public void setXGM_BZ(String xgm_bz) {
		XGM_BZ = xgm_bz;
	}

	public String getYBNSR_BZ() {
		return YBNSR_BZ;
	}

	public void setYBNSR_BZ(String ybnsr_bz) {
		YBNSR_BZ = ybnsr_bz;
	}

	public String getZSFS_DM() {
		return ZSFS_DM;
	}

	public void setZSFS_DM(String zsfs_dm) {
		ZSFS_DM = zsfs_dm;
	}

	public String getSBQSBZ() {
		return SBQSBZ;
	}

	public void setSBQSBZ(String sbqsbz) {
		SBQSBZ = sbqsbz;
	}

	public String getZDY_QYLX() {
		return ZDY_QYLX;
	}

	public void setZDY_QYLX(String zdy_qylx) {
		ZDY_QYLX = zdy_qylx;
	}

	public void setKPGLY(String kPGLY) {
		KPGLY = kPGLY;
	}

	public String getKPGLY() {
		return KPGLY;
	}

	public void setKPGLYPWD(String kPGLYPWD) {
		KPGLYPWD = kPGLYPWD;
	}

	public String getKPGLYPWD() {
		return KPGLYPWD;
	}

	public void setKPJSL(String kPJSL) {
		KPJSL = kPJSL;
	}

	public String getKPJSL() {
		return KPJSL;
	}

	public String getGSYLB_DM() {
		return GSYLB_DM;
	}

	public void setGSYLB_DM(String gsylb_dm) {
		GSYLB_DM = gsylb_dm;
	}

	public String getHY_MC() {
		return HY_MC;
	}

	public void setHY_MC(String hy_mc) {
		HY_MC = hy_mc;
	}

	public String getNSRZT_MC() {
		return NSRZT_MC;
	}

	public void setNSRZT_MC(String nsrzt_mc) {
		NSRZT_MC = nsrzt_mc;
	}

	public String getNSRZT_DM() {
		return NSRZT_DM;
	}

	public void setNSRZT_DM(String nsrzt_dm) {
		NSRZT_DM = nsrzt_dm;
	}

	public String getZDY_HY_DM() {
		return ZDY_HY_DM;
	}

	public void setZDY_HY_DM(String zDY_HY_DM) {
		ZDY_HY_DM = zDY_HY_DM;
	}

	public String getZDY_HYMC() {
		return ZDY_HYMC;
	}

	public void setZDY_HYMC(String zDY_HYMC) {
		ZDY_HYMC = zDY_HYMC;
	}

	public String getZDLXKPSJ() {
		return ZDLXKPSJ;
	}

	public void setZDLXKPSJ(String zDLXKPSJ) {
		ZDLXKPSJ = zDLXKPSJ;
	}

	public String getCBZQ() {
		return CBZQ;
	}

	public void setCBZQ(String cBZQ) {
		CBZQ = cBZQ;
	}

	public String getZDKPZS() {
		return ZDKPZS;
	}

	public void setZDKPZS(String zDKPZS) {
		ZDKPZS = zDKPZS;
	}

	public String getLJKPZE() {
		return LJKPZE;
	}

	public void setLJKPZE(String lJKPZE) {
		LJKPZE = lJKPZE;
	}

	public String getZDLX() {
		return ZDLX;
	}

	public void setZDLX(String zDLX) {
		ZDLX = zDLX;
	}

	public String getESC_LX() {
		return ESC_LX;
	}

	public void setESC_LX(String eSC_LX) {
		ESC_LX = eSC_LX;
	}

}
