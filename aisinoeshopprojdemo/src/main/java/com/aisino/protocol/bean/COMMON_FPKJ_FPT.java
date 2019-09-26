package com.aisino.protocol.bean;

public class COMMON_FPKJ_FPT {

    private String FPQQLSH;// 发票请求唯一流水号
    private String DSPTBM;// 电商平台编码
    private String NSRSBH;// 开票方识别号
    private String NSRMC;// 开票方名称
    private String NSRDZDAH;// 开票方电子档案号
    private String SWJG_DM;// 税务机构代码
    private String DKBZ;// 代开标志
    private String PYDM;// 票样代码
    private String KPXM;// 开票项目
    private String XHF_NSRSBH;// 销货方识别号
    private String XHF_MC;// 销货方名称
    private String GHF_MC;// 购货方名称
    private String GHF_NSRSBH;// 购货方识别号
    private String GHF_DZ;// 购货方地址
    private String GHF_GDDH;// 购货方固定电话
    private String GHF_SJ;// 购货方手机
    private String GHF_YX;// 购货方邮箱
    private String HY_DM;// 行业代码
    private String HY_MC;// 行业名称
    private String KPY;// 开票员
    private String SKY;// 收款员
//    private String KPRQ;// 开票日期
    private String KPLX;// 开票类型
    private String YFP_DM;// 原发票代码
    private String YFP_HM;// 原发票号码
    private String CHYY;// 冲红原因
    private String KPHJJE;// 开票合计金额
    private String BZ;// 备注
    private String BYZD1;// 备用字段1
    private String BYZD2;// 备用字段2
    private String BYZD3;// 备用字段3
    private String BYZD4;// 备用字段4
    private String BYZD5;// 备用字段5
    private String TSCHBZ;// 是否特殊冲红
    private String GHF_SF;// 购货方省份

    private String GHF_QYLX;// 购货方企业类型
    private String CZDM;// 操作代码
    
    /**
     * fp开具新增
     * @date: Created on 2015-3-19 上午09:57:32  begin
     * 
     */
    private String FHR;// 复核人
    private String HJBHSJE;//合计不含税金额
    private String KPHJSE;//合计税额
//    private String FKFKHYH;//购方开户行
    private String FKF_YHZH;//购方银行账号
//    private String SKFKHYH;//销方开户行
    private String SKF_YHZH;//销方银行账号
    private String XHF_DZ;//销方地址
    private String XHF_DH;//销方电话
    /**
     * @date: Created on 2015-3-19 上午09:57:32  end
     */

    
    public String getXHF_DZ() {
		return XHF_DZ;
	}

	public String getFKF_YHZH() {
		return FKF_YHZH;
	}

	public void setFKF_YHZH(String fKFYHZH) {
		FKF_YHZH = fKFYHZH;
	}

	public String getSKF_YHZH() {
		return SKF_YHZH;
	}

	public void setSKF_YHZH(String sKFYHZH) {
		SKF_YHZH = sKFYHZH;
	}

	public void setXHF_DZ(String xHFDZ) {
		XHF_DZ = xHFDZ;
	}

	public String getXHF_DH() {
		return XHF_DH;
	}

	public void setXHF_DH(String xHFDH) {
		XHF_DH = xHFDH;
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

	public void setSWJG_DM(String sWJGDM) {
		SWJG_DM = sWJGDM;
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

	public void setXHF_NSRSBH(String xHFNSRSBH) {
		XHF_NSRSBH = xHFNSRSBH;
	}

	public String getGHF_NSRSBH() {
		return GHF_NSRSBH;
	}

	public void setGHF_NSRSBH(String gHFNSRSBH) {
		GHF_NSRSBH = gHFNSRSBH;
	}

	public String getGHF_DZ() {
		return GHF_DZ;
	}

	public void setGHF_DZ(String gHFDZ) {
		GHF_DZ = gHFDZ;
	}

	public String getGHF_GDDH() {
		return GHF_GDDH;
	}

	public void setGHF_GDDH(String gHFGDDH) {
		GHF_GDDH = gHFGDDH;
	}

	public String getGHF_SJ() {
		return GHF_SJ;
	}

	public void setGHF_SJ(String gHFSJ) {
		GHF_SJ = gHFSJ;
	}

	public String getHY_DM() {
		return HY_DM;
	}

	public void setHY_DM(String hYDM) {
		HY_DM = hYDM;
	}

	public String getHY_MC() {
		return HY_MC;
	}

	public void setHY_MC(String hYMC) {
		HY_MC = hYMC;
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

	public String getKPLX() {
		return KPLX;
	}

	public void setKPLX(String kPLX) {
		KPLX = kPLX;
	}

	public String getYFP_DM() {
		return YFP_DM;
	}

	public void setYFP_DM(String yFPDM) {
		YFP_DM = yFPDM;
	}

	public String getYFP_HM() {
		return YFP_HM;
	}

	public void setYFP_HM(String yFPHM) {
		YFP_HM = yFPHM;
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

	public String getTSCHBZ() {
		return TSCHBZ;
	}

	public void setTSCHBZ(String tSCHBZ) {
		TSCHBZ = tSCHBZ;
	}

	public String getGHF_SF() {
		return GHF_SF;
	}

	public void setGHF_SF(String gHFSF) {
		GHF_SF = gHFSF;
	}

	public String getCZDM() {
		return CZDM;
	}

	public void setCZDM(String cZDM) {
		CZDM = cZDM;
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

	public String getXHF_MC() {
		return XHF_MC;
	}

	public void setXHF_MC(String xHFMC) {
		XHF_MC = xHFMC;
	}

	public String getGHF_MC() {
		return GHF_MC;
	}

	public void setGHF_MC(String gHFMC) {
		GHF_MC = gHFMC;
	}

	public String getGHF_YX() {
		return GHF_YX;
	}

	public void setGHF_YX(String gHFYX) {
		GHF_YX = gHFYX;
	}

	public String getFHR() {
		return FHR;
	}

	public void setFHR(String fHR) {
		FHR = fHR;
	}

	public String getGHF_QYLX() {
		return GHF_QYLX;
	}

	public void setGHF_QYLX(String gHFQYLX) {
		GHF_QYLX = gHFQYLX;
	}
	
}
