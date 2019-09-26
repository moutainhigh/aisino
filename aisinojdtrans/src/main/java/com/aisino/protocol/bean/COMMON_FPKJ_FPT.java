package com.aisino.protocol.bean;

/**增值税接口对接
 * @author jerome update by Su shiqi in 2016.07.05
 *
 */
public class COMMON_FPKJ_FPT {
	private String FPQQLSH;//发票请求流水号/FPQQLSH;//
	private String DSPTBM;//电商平台编码/DSPTBM;//
	private String NSRSBH;//纳税人识别号/NSRSBH;//
	private String NSRMC;//纳税人名称/NSRMC;//
	private String GHFMC;//适应普通电子发票报文
	private String NSRDZDAH;//
	private String SWJG_DM;//
	private String DKBZ;//代开标志/DKBZ;//
	private String PYDM;//票源代码/PYDM;//
	private String KPXM;//开票明细/KPXM;//
	private String XHF_NSRSBH;//销货方纳税人识别号/XHF_NSRSBH;//
	private String XHF_MC;//销货方名称/XHF_MC;// 
	private String XHF_DZ;//销货方地址/XHF_DZ;// 
	private String XHF_DH;//销货方电话/XHF_DH;// 
	private String GHF_MC;//购货方名称/GHF_MC;// 
	private String GHF_SF;//购货方省份/GHF_SF;//
	private String GHF_NSRSBH;//购货方纳税人识别号/GHF_NSRSBH;//
	private String GHF_QYLX;//购货方企业类型/GHF_QYLX;// 
	private String GHF_DZ;//购货方地址/GHF_DZ;//
	private String GHF_GDDH;//购货方固定电话/GHF_GDDH;//
	private String GHF_SJ;//购货方手机/GHF_SJ;//
	private String GHF_YX;//购货方邮箱/GHF_YX;// 
	private String FKF_YHZH;//付款方银行账号/FKF_YHZH;//
	private String SKF_YHZH;//销货方银行账号/SKF_YHZH;// 
	private String HY_DM;//行业代码/HY_DM;//
	private String HY_MC;//行业名称/HY_MC;//
	private String KPY;//开票员/KPY;//
	private String SKY;//收款员/SKY;//
	private String FHR;//复核人/FHR;// 
	private String KPLX;//开票类型/KPLX;//
	private String YFP_DM;//原发票代码/YFP_DM;//
	private String YFP_HM;//原发票号码/YFP_HM;//
	private String CHYY;//冲红原因/CHYY;//
	private String KPHJJE;//开票合计金额/KPHJJE;//
	private String HJBHSJE;//合计不含税金额/HJBHSJE;// 
	private String KPHJSE;//开票合计税额/KPHJSE;// 
	private String TSCHBZ;//是否特殊冲红发票/TSCHBZ;//
	private String CZDM;//操作代码/CZDM;//
	private String BZ;//备注/BZ;//
	private String BMB_BBH;//编码表版本号;//
	private String BYZD1;//
	private String BYZD2;//
	private String BYZD3;//
	private String BYZD4;//
	private String BYZD5;//
	private String KPRQ;// 
	
	public String getGHFMC() {
		return GHFMC;
	}
	public void setGHFMC(String gHFMC) {
		GHFMC = gHFMC;
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
	public String getXHF_MC() {
		return XHF_MC;
	}
	public void setXHF_MC(String xHF_MC) {
		XHF_MC = xHF_MC;
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
	public String getGHF_MC() {
		return GHF_MC;
	}
	public void setGHF_MC(String gHF_MC) {
		GHF_MC = gHF_MC;
	}
	public String getGHF_SF() {
		return GHF_SF;
	}
	public void setGHF_SF(String gHF_SF) {
		GHF_SF = gHF_SF;
	}
	public String getGHF_NSRSBH() {
		return GHF_NSRSBH;
	}
	public void setGHF_NSRSBH(String gHF_NSRSBH) {
		GHF_NSRSBH = gHF_NSRSBH;
	}
	public String getGHF_QYLX() {
		return GHF_QYLX;
	}
	public void setGHF_QYLX(String gHF_QYLX) {
		GHF_QYLX = gHF_QYLX;
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
	public String getGHF_YX() {
		return GHF_YX;
	}
	public void setGHF_YX(String gHF_YX) {
		GHF_YX = gHF_YX;
	}
	public String getFKF_YHZH() {
		return FKF_YHZH;
	}
	public void setFKF_YHZH(String fKF_YHZH) {
		FKF_YHZH = fKF_YHZH;
	}
	public String getSKF_YHZH() {
		return SKF_YHZH;
	}
	public void setSKF_YHZH(String sKF_YHZH) {
		SKF_YHZH = sKF_YHZH;
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
	public String getCHYY() {
		return CHYY;
	}
	public void setCHYY(String cHYY) {
		CHYY = cHYY;
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
	public String getKPHJSE() {
		return KPHJSE;
	}
	public void setKPHJSE(String kPHJSE) {
		KPHJSE = kPHJSE;
	}
	public String getTSCHBZ() {
		return TSCHBZ;
	}
	public void setTSCHBZ(String tSCHBZ) {
		TSCHBZ = tSCHBZ;
	}
	public String getCZDM() {
		return CZDM;
	}
	public void setCZDM(String cZDM) {
		CZDM = cZDM;
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
	public String getKPRQ() {
		return KPRQ;
	}
	public void setKPRQ(String kPRQ) {
		KPRQ = kPRQ;
	}
	public String getBMB_BBH() {
		return BMB_BBH;
	}
	public void setBMB_BBH(String bMB_BBH) {
		BMB_BBH = bMB_BBH;
	}
}
