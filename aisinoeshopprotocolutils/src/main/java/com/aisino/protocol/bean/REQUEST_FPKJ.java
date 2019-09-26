package com.aisino.protocol.bean;


import com.aisino.protocol.bean.*;
import com.aisino.protocol.bean.FP_DDXX;
import com.aisino.protocol.bean.FP_KJMX;
import com.aisino.protocol.bean.FP_WLXX;
import com.aisino.protocol.bean.FP_ZFXX;

public class REQUEST_FPKJ implements com.aisino.protocol.bean.REQUEST_BEAN {
    private String KPLSH;
    private String SKM;
    private String EWM;
    private String NSRSBH;
    private String NSRMC;
    private String NSRDZDAH;
    private String NSRDZ;//可为空
    private String SWJG_DM; 
    private String DKBZ;
    private String PYDM;//上一版本为 PYCODE
    private String KPXM;
    private String XHF_NSRSBH ;//上一版本为 XHFSBH
    private String XHFMC ;
    private String GHF_NSRSBH;//上一版本为FKF_DM
    private String GHFMC;//上一版本为
    private String GHF_DZ;//上一版本为FKFDZ
    private String GHF_DH;//上一版本为FKFDH
    private String GHF_EMAIL;
    private String HY_DM;
    private String HY_MC; 
    private String KPY;
    private String SKY;
    private String FPZL_DM;
    private String FP_DM;
    private String FP_HM;
    private String YFP_DM;
    private String YFP_HM;
    private String KPRQ ;
    private String GHFQYLX ;//购货方企业类型 //不可为空
    private String KPHJJE; //不可为空
    private String KPLX;    //可为空
    private String CZDM; //冲红标志
    private String TSCHBZ; //特殊冲红标志
   
	private String SL;  //税率 可为空
    private String SLBZ;//税率标志
    private String BZ;//可为空
    private String BYZD1;//可为空
    private String BYZD2;//可为空
    private String BYZD3;//可为空
    private String BYZD4;//可为空
    private String BYZD5;//可为空
    private com.aisino.protocol.bean.FP_KJMX[] FP_KJMXS;
    private com.aisino.protocol.bean.FP_DDXX FP_DDXX;
    private com.aisino.protocol.bean.FP_WLXX[] FP_WLXXS;
    private com.aisino.protocol.bean.FP_ZFXX FP_ZFXX;
    private String PDF_FILE;
    private String GHF_SF;
    private String CHYY;
    public String getKPLSH() {
        return KPLSH;
    }
    public void setKPLSH(String kplsh) {
        KPLSH = kplsh;
    }
    public String getSKM() {
        return SKM;
    }
    public void setSKM(String skm) {
        SKM = skm;
    }
    public String getEWM() {
        return EWM;
    }
    public void setEWM(String ewm) {
        EWM = ewm;
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
    public String getNSRDZDAH() {
        return NSRDZDAH;
    }
    public void setNSRDZDAH(String nsrdzdah) {
        NSRDZDAH = nsrdzdah;
    }
    public String getNSRDZ() {
        return NSRDZ;
    }
    public void setNSRDZ(String nsrdz) {
        NSRDZ = nsrdz;
    }
    public String getSWJG_DM() {
        return SWJG_DM;
    }
    public void setSWJG_DM(String swjg_dm) {
        SWJG_DM = swjg_dm;
    }
    public String getDKBZ() {
        return DKBZ;
    }
    public void setDKBZ(String dkbz) {
        DKBZ = dkbz;
    }
    public String getPYDM() {
        return PYDM;
    }
    public void setPYDM(String pydm) {
        PYDM = pydm;
    }
    public String getKPXM() {
        return KPXM;
    }
    public void setKPXM(String kpxm) {
        KPXM = kpxm;
    }
    public String getXHF_NSRSBH() {
        return XHF_NSRSBH;
    }
    public void setXHF_NSRSBH(String xhf_nsrsbh) {
        XHF_NSRSBH = xhf_nsrsbh;
    }
    public String getXHFMC() {
        return XHFMC;
    }
    public void setXHFMC(String xhfmc) {
        XHFMC = xhfmc;
    }
    public String getGHF_NSRSBH() {
        return GHF_NSRSBH;
    }
    public void setGHF_NSRSBH(String ghf_nsrsbh) {
        GHF_NSRSBH = ghf_nsrsbh;
    }
    public String getGHFMC() {
        return GHFMC;
    }
    public void setGHFMC(String ghfmc) {
        GHFMC = ghfmc;
    }
    public String getGHF_DZ() {
        return GHF_DZ;
    }
    public void setGHF_DZ(String ghf_dz) {
        GHF_DZ = ghf_dz;
    }
    public String getGHF_DH() {
        return GHF_DH;
    }
    public void setGHF_DH(String ghf_dh) {
        GHF_DH = ghf_dh;
    }
    public String getGHF_EMAIL() {
        return GHF_EMAIL;
    }
    public void setGHF_EMAIL(String ghf_email) {
        GHF_EMAIL = ghf_email;
    }
    public String getHY_DM() {
        return HY_DM;
    }
    public void setHY_DM(String hy_dm) {
        HY_DM = hy_dm;
    }
    public String getHY_MC() {
        return HY_MC;
    }
    public void setHY_MC(String hy_mc) {
        HY_MC = hy_mc;
    }
    public String getKPY() {
        return KPY;
    }
    public void setKPY(String kpy) {
        KPY = kpy;
    }
    public String getSKY() {
        return SKY;
    }
    public void setSKY(String sky) {
        SKY = sky;
    }
    public String getFPZL_DM() {
        return FPZL_DM;
    }
    public void setFPZL_DM(String fpzl_dm) {
        FPZL_DM = fpzl_dm;
    }
    public String getFP_DM() {
        return FP_DM;
    }
    public void setFP_DM(String fp_dm) {
        FP_DM = fp_dm;
    }
    public String getFP_HM() {
        return FP_HM;
    }
    public void setFP_HM(String fp_hm) {
        FP_HM = fp_hm;
    }
    public String getYFP_DM() {
        return YFP_DM;
    }
    public void setYFP_DM(String yfp_dm) {
        YFP_DM = yfp_dm;
    }
    public String getYFP_HM() {
        return YFP_HM;
    }
    public void setYFP_HM(String yfp_hm) {
        YFP_HM = yfp_hm;
    }
    public String getKPRQ() {
        return KPRQ;
    }
    public void setKPRQ(String kprq) {
        KPRQ = kprq;
    }
    public String getGHFQYLX() {
        return GHFQYLX;
    }
    public void setGHFQYLX(String ghfqylx) {
        GHFQYLX = ghfqylx;
    }
    public String getKPHJJE() {
        return KPHJJE;
    }
    public void setKPHJJE(String kphjje) {
        KPHJJE = kphjje;
    }
    public String getKPLX() {
        return KPLX;
    }
    public void setKPLX(String kplx) {
        KPLX = kplx;
    }
    public String getSL() {
        return SL;
    }
    public void setSL(String sl) {
        SL = sl;
    }
    public String getSLBZ() {
        return SLBZ;
    }
    public void setSLBZ(String slbz) {
        SLBZ = slbz;
    }
    public String getBZ() {
        return BZ;
    }
    public void setBZ(String bz) {
        BZ = bz;
    }
    public String getBYZD1() {
        return BYZD1;
    }
    public void setBYZD1(String byzd1) {
        BYZD1 = byzd1;
    }
    public String getBYZD2() {
        return BYZD2;
    }
    public void setBYZD2(String byzd2) {
        BYZD2 = byzd2;
    }
    public String getBYZD3() {
        return BYZD3;
    }
    public void setBYZD3(String byzd3) {
        BYZD3 = byzd3;
    }
    public String getBYZD4() {
        return BYZD4;
    }
    public void setBYZD4(String byzd4) {
        BYZD4 = byzd4;
    }
    public String getBYZD5() {
        return BYZD5;
    }
    public void setBYZD5(String byzd5) {
        BYZD5 = byzd5;
    }
    public com.aisino.protocol.bean.FP_KJMX[] getFP_KJMXS() {
        return FP_KJMXS;
    }
    public void setFP_KJMXS(FP_KJMX[] fp_kjmxs) {
        FP_KJMXS = fp_kjmxs;
    }
    public com.aisino.protocol.bean.FP_DDXX getFP_DDXX() {
        return FP_DDXX;
    }
    public void setFP_DDXX(com.aisino.protocol.bean.FP_DDXX fp_ddxx) {
        FP_DDXX = fp_ddxx;
    }
    public com.aisino.protocol.bean.FP_WLXX[] getFP_WLXXS() {
        return FP_WLXXS;
    }
    public void setFP_WLXXS(FP_WLXX[] fp_wlxxs) {
        FP_WLXXS = fp_wlxxs;
    }
    public com.aisino.protocol.bean.FP_ZFXX getFP_ZFXX() {
        return FP_ZFXX;
    }
    public void setFP_ZFXX(com.aisino.protocol.bean.FP_ZFXX fp_zfxx) {
        FP_ZFXX = fp_zfxx;
    }
    public String getDSPTBM() {
        return null;
    }
	public String getPDF_FILE() {
		return PDF_FILE;
	}
	public void setPDF_FILE(String pDF_FILE) {
		PDF_FILE = pDF_FILE;
	}
	
	public String getCZDM() {
        return CZDM;
    }
    public void setCZDM(String czdm) {
        CZDM = czdm;
    }
/**
    *
    * @return 
    */
   public String getTSCHBZ() {
       return TSCHBZ;
   }
   /**
    *
    * @param tSCHBZ
    */
   public void setTSCHBZ(String tSCHBZ) {
       TSCHBZ = tSCHBZ;
   }
public String getGHF_SF() {
    return GHF_SF;
}
public void setGHF_SF(String ghf_sf) {
    GHF_SF = ghf_sf;
}
public String getCHYY() {
    return CHYY;
}
public void setCHYY(String chyy) {
    CHYY = chyy;
}
   
}
