package com.aisino.protocol.bean;


public class REQUEST_FPKJ implements REQUEST_BEAN{
    private String GHFMC;//上一版本为
    private String GHF_NSRSBH;//上一版本为FKF_DM
    private String FKFKHYH_FKFYHZH;// 购方开户行及账号
    private String FKFDZ_FKFDH;// 购方地址电话
    private String XHFKHYH_SKFYHZH;// 销方开户行及账号
    private String XHFDZ_XHFDH;// 销方地址电话
    private String FPZL_DM;
    private String YFP_DM;
    private String YFP_HM;
    private String BZ;//可为空
    private String KPY;
    private String FHR;// 复核人
    private String SKY;
    private String XHQD;// 销货清单
    private String FPQQLSH;// 销售单据编号
    private String KPLX;    //可为空
    private String JSHJ;//价税合计
    private String HJJE;// 合计金额
    private String HJSE;// 合计税额
    private String BMB_BBH;//编码表版本号
    private FP_KJMX[] FP_KJMXS;
	public String getGHF_NSRSBH() {
		return GHF_NSRSBH;
	}
	public void setGHF_NSRSBH(String gHF_NSRSBH) {
		GHF_NSRSBH = gHF_NSRSBH;
	}
	public String getGHFMC() {
		return GHFMC;
	}
	public void setGHFMC(String gHFMC) {
		GHFMC = gHFMC;
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
	public String getFPZL_DM() {
		return FPZL_DM;
	}
	public void setFPZL_DM(String fPZL_DM) {
		FPZL_DM = fPZL_DM;
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
	public String getKPLX() {
		return KPLX;
	}
	public void setKPLX(String kPLX) {
		KPLX = kPLX;
	}
	public String getBZ() {
		return BZ;
	}
	public void setBZ(String bZ) {
		BZ = bZ;
	}
	public FP_KJMX[] getFP_KJMXS() {
		return FP_KJMXS;
	}
	public void setFP_KJMXS(FP_KJMX[] fP_KJMXS) {
		FP_KJMXS = fP_KJMXS;
	}
	public String getFKFKHYH_FKFYHZH() {
		return FKFKHYH_FKFYHZH;
	}
	public void setFKFKHYH_FKFYHZH(String fKFKHYH_FKFYHZH) {
		FKFKHYH_FKFYHZH = fKFKHYH_FKFYHZH;
	}
	public String getFKFDZ_FKFDH() {
		return FKFDZ_FKFDH;
	}
	public void setFKFDZ_FKFDH(String fKFDZ_FKFDH) {
		FKFDZ_FKFDH = fKFDZ_FKFDH;
	}
	public String getXHFKHYH_SKFYHZH() {
		return XHFKHYH_SKFYHZH;
	}
	public void setXHFKHYH_SKFYHZH(String xHFKHYH_SKFYHZH) {
		XHFKHYH_SKFYHZH = xHFKHYH_SKFYHZH;
	}
	public String getXHFDZ_XHFDH() {
		return XHFDZ_XHFDH;
	}
	public void setXHFDZ_XHFDH(String xHFDZ_XHFDH) {
		XHFDZ_XHFDH = xHFDZ_XHFDH;
	}
	public String getFHR() {
		return FHR;
	}
	public void setFHR(String fHR) {
		FHR = fHR;
	}
	public String getXHQD() {
		return XHQD;
	}
	public void setXHQD(String xHQD) {
		XHQD = xHQD;
	}
	public String getFPQQLSH() {
		return FPQQLSH;
	}
	public void setFPQQLSH(String fPQQLSH) {
		FPQQLSH = fPQQLSH;
	}
	public String getJSHJ() {
		return JSHJ;
	}
	public void setJSHJ(String jSHJ) {
		JSHJ = jSHJ;
	}
	public String getHJJE() {
		return HJJE;
	}
	public void setHJJE(String hJJE) {
		HJJE = hJJE;
	}
	public String getHJSE() {
		return HJSE;
	}
	public void setHJSE(String hJSE) {
		HJSE = hJSE;
	}
	
	public String getBMB_BBH() {
		return BMB_BBH;
	}
	public void setBMB_BBH(String bMB_BBH) {
		BMB_BBH = bMB_BBH;
	}
	@Override
	public String getDSPTBM() {
		return null;
	}
 
}
