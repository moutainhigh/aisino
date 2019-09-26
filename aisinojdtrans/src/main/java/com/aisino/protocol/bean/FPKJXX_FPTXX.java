package com.aisino.protocol.bean;

/**
 * 和京东对接接口报文
 * 
 * @author jerome update by Su shiqi in 2016.07.05
 * 
 */
public class FPKJXX_FPTXX {
	private String FPQQLSH;// 发票请求唯一流水号FPQQLSH;//
	private String DSPTBM;// 电商平台编码DSPTBM;//
	private String NSRSBH;// 开票方识别号NSRSBH;//
	private String NSRMC;// 开票方名称NSRMC;//
	private String NSRDZDAH;// 开票方电子档案号NSRDZDAH;//
	private String SWJG_DM;// 税务机构代码SWJG_DM;//
	private String DKBZ;// 代开标志DKBZ;//
	private String PYDM;// 票样代码PYDM;//
	private String KPXM;// 主要开票项目KPXM;//
	private String XHF_NSRSBH;// 销货方识别号XHF_NSRSBH;//
	private String XHFMC;// 销货方名称XHFMC;//
	private String XHF_DZ;// 销货方地址XHF_DZ;//
	private String XHF_DH;// 销货方电话XHF_DH;//
	private String XHF_YHZH;// 销货方银行账号XHF_YHZH;//
	private String GHFMC;// 购货方名称GHFMC;//
	private String GHF_NSRSBH;// 购货方识别号GHF_NSRSBH;//
	private String GHF_SF;// 购货方省份GHF_SF;//
	private String GHF_DZ;// 购货方地址GHF_DZ;//
	private String GHF_GDDH;// 购货方固定电话GHF_GDDH;//
	private String GHF_SJ;// 购货方手机GHF_SJ;//
	private String GHF_EMAIL;// 购货方邮箱GHF_EMAIL;//
	private String GHFQYLX;// 01GHFQYLX;//
	private String GHF_YHZH;// 01GHF_YHZH;//
	private String HY_DM;// 行业代码HY_DM;//
	private String HY_MC;// 行业名称HY_MC;//
	private String KPY;// 开票员KPY;//
	private String SKY;// 收款员SKY;//
	private String FHR;// 复核人FHR;//
	private String KPRQ;// 开票日期KPRQ;//
	private String KPLX;// 开票类型KPLX;//
	private String YFP_DM;// 原发票代码YFP_DM;//
	private String YFP_HM;// 原发票号码YFP_HM;//
	private String CZDM;// 操作代码CZDM;//
	private String CHYY;// 冲红原因CHYY;//
	private String TSCHBZ;// 特殊冲红标志TSCHBZ;//
	private String KPHJJE;// 价税合计金额KPHJJE;//
	private String HJBHSJE;// 合计不含税金额HJBHSJE;//
	private String HJSE;// 合计税额HJSE;//
	private String BZ;// 备注BZ;//
	private String BMB_BBH;//编码表版本号;//
	private String BYZD1;// 备用字段BYZD1;//
	private String BYZD2;// 备用字段BYZD2;//
	private String BYZD3;// 备用字段BYZD3;//
	private String BYZD4;// 备用字段BYZD4;//
	private String BYZD5;// 备用字段BYZD5;//
	//TODO FWH -2017-07-21
	private String QD_BZ;       //           清单标志,
    private String QDXMMC;
	
	
	public String getQD_BZ() {
		return QD_BZ;
	}
	public void setQD_BZ(String qD_BZ) {
		QD_BZ = qD_BZ;
	}
	public String getQDXMMC() {
		return QDXMMC;
	}
	public void setQDXMMC(String qDXMMC) {
		QDXMMC = qDXMMC;
	}
	public String getFPQQLSH() {
		return FPQQLSH;
	}
	public void setFPQQLSH(String fPQQLSH) {
		FPQQLSH = fPQQLSH;
	}
	public String getDSPTBM() {
		return DSPTBM;
	}
	public void setDSPTBM(String dSPTBM) {
		DSPTBM = dSPTBM;
	}
	public String getNSRSBH() {
		return NSRSBH;
	}
	public void setNSRSBH(String nSRSBH) {
		NSRSBH = nSRSBH;
	}
	public String getNSRMC() {
		return NSRMC;
	}
	public void setNSRMC(String nSRMC) {
		NSRMC = nSRMC;
	}
	public String getNSRDZDAH() {
		return NSRDZDAH;
	}
	public void setNSRDZDAH(String nSRDZDAH) {
		NSRDZDAH = nSRDZDAH;
	}
	public String getSWJG_DM() {
		return SWJG_DM;
	}
	public void setSWJG_DM(String sWJG_DM) {
		SWJG_DM = sWJG_DM;
	}
	public String getDKBZ() {
		return DKBZ;
	}
	public void setDKBZ(String dKBZ) {
		DKBZ = dKBZ;
	}
	public String getPYDM() {
		return PYDM;
	}
	public void setPYDM(String pYDM) {
		PYDM = pYDM;
	}
	public String getKPXM() {
		return KPXM;
	}
	public void setKPXM(String kPXM) {
		KPXM = kPXM;
	}
	public String getXHF_NSRSBH() {
		return XHF_NSRSBH;
	}
	public void setXHF_NSRSBH(String xHF_NSRSBH) {
		XHF_NSRSBH = xHF_NSRSBH;
	}
	public String getXHFMC() {
		return XHFMC;
	}
	public void setXHFMC(String xHFMC) {
		XHFMC = xHFMC;
	}
	public String getXHF_DZ() {
		return XHF_DZ;
	}
	public void setXHF_DZ(String xHF_DZ) {
		XHF_DZ = xHF_DZ;
	}
	public String getXHF_DH() {
		return XHF_DH;
	}
	public void setXHF_DH(String xHF_DH) {
		XHF_DH = xHF_DH;
	}
	public String getXHF_YHZH() {
		return XHF_YHZH;
	}
	public void setXHF_YHZH(String xHF_YHZH) {
		XHF_YHZH = xHF_YHZH;
	}
	public String getGHFMC() {
		return GHFMC;
	}
	public void setGHFMC(String gHFMC) {
		GHFMC = gHFMC;
	}
	public String getGHF_NSRSBH() {
		return GHF_NSRSBH;
	}
	public void setGHF_NSRSBH(String gHF_NSRSBH) {
		GHF_NSRSBH = gHF_NSRSBH;
	}
	public String getGHF_SF() {
		return GHF_SF;
	}
	public void setGHF_SF(String gHF_SF) {
		GHF_SF = gHF_SF;
	}
	public String getGHF_DZ() {
		return GHF_DZ;
	}
	public void setGHF_DZ(String gHF_DZ) {
		GHF_DZ = gHF_DZ;
	}
	public String getGHF_GDDH() {
		return GHF_GDDH;
	}
	public void setGHF_GDDH(String gHF_GDDH) {
		GHF_GDDH = gHF_GDDH;
	}
	public String getGHF_SJ() {
		return GHF_SJ;
	}
	public void setGHF_SJ(String gHF_SJ) {
		GHF_SJ = gHF_SJ;
	}
	public String getGHF_EMAIL() {
		return GHF_EMAIL;
	}
	public void setGHF_EMAIL(String gHF_EMAIL) {
		GHF_EMAIL = gHF_EMAIL;
	}
	public String getGHFQYLX() {
		return GHFQYLX;
	}
	public void setGHFQYLX(String gHFQYLX) {
		GHFQYLX = gHFQYLX;
	}
	public String getGHF_YHZH() {
		return GHF_YHZH;
	}
	public void setGHF_YHZH(String gHF_YHZH) {
		GHF_YHZH = gHF_YHZH;
	}
	public String getHY_DM() {
		return HY_DM;
	}
	public void setHY_DM(String hY_DM) {
		HY_DM = hY_DM;
	}
	public String getHY_MC() {
		return HY_MC;
	}
	public void setHY_MC(String hY_MC) {
		HY_MC = hY_MC;
	}
	public String getKPY() {
		return KPY;
	}
	public void setKPY(String kPY) {
		KPY = kPY;
	}
	public String getSKY() {
		return SKY;
	}
	public void setSKY(String sKY) {
		SKY = sKY;
	}
	public String getFHR() {
		return FHR;
	}
	public void setFHR(String fHR) {
		FHR = fHR;
	}
	public String getKPRQ() {
		return KPRQ;
	}
	public void setKPRQ(String kPRQ) {
		KPRQ = kPRQ;
	}
	public String getKPLX() {
		return KPLX;
	}
	public void setKPLX(String kPLX) {
		KPLX = kPLX;
	}
	public String getYFP_DM() {
		return YFP_DM;
	}
	public void setYFP_DM(String yFP_DM) {
		YFP_DM = yFP_DM;
	}
	public String getYFP_HM() {
		return YFP_HM;
	}
	public void setYFP_HM(String yFP_HM) {
		YFP_HM = yFP_HM;
	}
	public String getCZDM() {
		return CZDM;
	}
	public void setCZDM(String cZDM) {
		CZDM = cZDM;
	}
	public String getCHYY() {
		return CHYY;
	}
	public void setCHYY(String cHYY) {
		CHYY = cHYY;
	}
	public String getTSCHBZ() {
		return TSCHBZ;
	}
	public void setTSCHBZ(String tSCHBZ) {
		TSCHBZ = tSCHBZ;
	}
	public String getKPHJJE() {
		return KPHJJE;
	}
	public void setKPHJJE(String kPHJJE) {
		KPHJJE = kPHJJE;
	}
	public String getHJBHSJE() {
		return HJBHSJE;
	}
	public void setHJBHSJE(String hJBHSJE) {
		HJBHSJE = hJBHSJE;
	}
	public String getHJSE() {
		return HJSE;
	}
	public void setHJSE(String hJSE) {
		HJSE = hJSE;
	}
	public String getBZ() {
		return BZ;
	}
	public void setBZ(String bZ) {
		BZ = bZ;
	}
	public String getBYZD1() {
		return BYZD1;
	}
	public void setBYZD1(String bYZD1) {
		BYZD1 = bYZD1;
	}
	public String getBYZD2() {
		return BYZD2;
	}
	public void setBYZD2(String bYZD2) {
		BYZD2 = bYZD2;
	}
	public String getBYZD3() {
		return BYZD3;
	}
	public void setBYZD3(String bYZD3) {
		BYZD3 = bYZD3;
	}
	public String getBYZD4() {
		return BYZD4;
	}
	public void setBYZD4(String bYZD4) {
		BYZD4 = bYZD4;
	}
	public String getBYZD5() {
		return BYZD5;
	}
	public void setBYZD5(String bYZD5) {
		BYZD5 = bYZD5;
	}
	public String getBMB_BBH() {
		return BMB_BBH;
	}
	public void setBMB_BBH(String bMB_BBH) {
		BMB_BBH = bMB_BBH;
	}
	
	
}
