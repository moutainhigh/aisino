package com.aisino.protocol.bean;


public class REQUEST_FPKJ implements REQUEST_BEAN{
    /**
     * 发票开具新增字段
     *
     * @date Created on 2015-03-16 09:02 by zhongsiwei begin
     */
    private String FHR;// 复核人
    private String XHQD;// 销货清单
    private String HJBHSJE;// 合计不含税金额
    private String KPHJSE;// 合计税额
    private String SSYF;// 所属月份
    private String XHQDBZ;// 销货清单标志
    private String RETCODE;// 返回编码
    private String FWMW;// 防伪密文
    private String JYM;// 校验码
    private String SZQM;// 数字签名
    private String FKFKHYH;// 购方开户行
    private String FKFYHZH;// 购方银行账号
    private String XHFKHYH;// 销方开户行
    private String SKFYHZH;// 销方银行账号
    private String XHFDZ;// 销方地址
    private String XHFDH;// 销方电话
    private String SZ_SWJG_DM;// 所在税务机关代码,用于删除pdf文件夹的时候
    /**
     * @date Created on 2015-03-16 09:02 by zhongsiwei end
     */
    /**
     * 新增部分未上传51发票的字段
     */
    private String SPHSL;// 商品行数量
    private String ZG_SWRY_DM;// 主管税务人员代码
    private String CH_BZ;// 冲红标志
    private String GHF_SJ;// 购货方手机
    private String DZBZ;// 对账标志
    private String FJH;// 分机号
    private String JQBH;// 机器编号
    private String PTJYLSH;// 平台交易流水号
    private String FPQQLSH;// 发票请求流水号


    private String BMB_BBH;//编码表版本号 //TODO 增添字段 -FWH-20171026

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
    private FP_KJMX[] FP_KJMXS;
    private FP_DDXX FP_DDXX;
    private FP_WLXX[] FP_WLXXS;
    private FP_ZFXX FP_ZFXX;
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
    public FP_KJMX[] getFP_KJMXS() {
        return FP_KJMXS;
    }
    public void setFP_KJMXS(FP_KJMX[] fp_kjmxs) {
        FP_KJMXS = fp_kjmxs;
    }
    public FP_DDXX getFP_DDXX() {
        return FP_DDXX;
    }
    public void setFP_DDXX(FP_DDXX fp_ddxx) {
        FP_DDXX = fp_ddxx;
    }
    public FP_WLXX[] getFP_WLXXS() {
        return FP_WLXXS;
    }
    public void setFP_WLXXS(FP_WLXX[] fp_wlxxs) {
        FP_WLXXS = fp_wlxxs;
    }
    public FP_ZFXX getFP_ZFXX() {
        return FP_ZFXX;
    }
    public void setFP_ZFXX(FP_ZFXX fp_zfxx) {
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

    public String getFHR() {
        return FHR;
    }

    public void setFHR(String FHR) {
        this.FHR = FHR;
    }

    public String getXHQD() {
        return XHQD;
    }

    public void setXHQD(String XHQD) {
        this.XHQD = XHQD;
    }

    public String getHJBHSJE() {
        return HJBHSJE;
    }

    public void setHJBHSJE(String HJBHSJE) {
        this.HJBHSJE = HJBHSJE;
    }

    public String getKPHJSE() {
        return KPHJSE;
    }

    public void setKPHJSE(String KPHJSE) {
        this.KPHJSE = KPHJSE;
    }

    public String getSSYF() {
        return SSYF;
    }

    public void setSSYF(String SSYF) {
        this.SSYF = SSYF;
    }

    public String getXHQDBZ() {
        return XHQDBZ;
    }

    public void setXHQDBZ(String XHQDBZ) {
        this.XHQDBZ = XHQDBZ;
    }

    public String getRETCODE() {
        return RETCODE;
    }

    public void setRETCODE(String RETCODE) {
        this.RETCODE = RETCODE;
    }

    public String getFWMW() {
        return FWMW;
    }

    public void setFWMW(String FWMW) {
        this.FWMW = FWMW;
    }

    public String getJYM() {
        return JYM;
    }

    public void setJYM(String JYM) {
        this.JYM = JYM;
    }

    public String getSZQM() {
        return SZQM;
    }

    public void setSZQM(String SZQM) {
        this.SZQM = SZQM;
    }

    public String getFKFKHYH() {
        return FKFKHYH;
    }

    public void setFKFKHYH(String FKFKHYH) {
        this.FKFKHYH = FKFKHYH;
    }

    public String getFKFYHZH() {
        return FKFYHZH;
    }

    public void setFKFYHZH(String FKFYHZH) {
        this.FKFYHZH = FKFYHZH;
    }

    public String getXHFKHYH() {
        return XHFKHYH;
    }

    public void setXHFKHYH(String XHFKHYH) {
        this.XHFKHYH = XHFKHYH;
    }

    public String getSKFYHZH() {
        return SKFYHZH;
    }

    public void setSKFYHZH(String SKFYHZH) {
        this.SKFYHZH = SKFYHZH;
    }

    public String getXHFDZ() {
        return XHFDZ;
    }

    public void setXHFDZ(String XHFDZ) {
        this.XHFDZ = XHFDZ;
    }

    public String getXHFDH() {
        return XHFDH;
    }

    public void setXHFDH(String XHFDH) {
        this.XHFDH = XHFDH;
    }

    public String getSZ_SWJG_DM() {
        return SZ_SWJG_DM;
    }

    public void setSZ_SWJG_DM(String SZ_SWJG_DM) {
        this.SZ_SWJG_DM = SZ_SWJG_DM;
    }

    public String getSPHSL() {
        return SPHSL;
    }

    public void setSPHSL(String SPHSL) {
        this.SPHSL = SPHSL;
    }

    public String getZG_SWRY_DM() {
        return ZG_SWRY_DM;
    }

    public void setZG_SWRY_DM(String ZG_SWRY_DM) {
        this.ZG_SWRY_DM = ZG_SWRY_DM;
    }

    public String getCH_BZ() {
        return CH_BZ;
    }

    public void setCH_BZ(String CH_BZ) {
        this.CH_BZ = CH_BZ;
    }

    public String getGHF_SJ() {
        return GHF_SJ;
    }

    public void setGHF_SJ(String GHF_SJ) {
        this.GHF_SJ = GHF_SJ;
    }

    public String getDZBZ() {
        return DZBZ;
    }

    public void setDZBZ(String DZBZ) {
        this.DZBZ = DZBZ;
    }

    public String getFJH() {
        return FJH;
    }

    public void setFJH(String FJH) {
        this.FJH = FJH;
    }

    public String getJQBH() {
        return JQBH;
    }

    public void setJQBH(String JQBH) {
        this.JQBH = JQBH;
    }

    public String getPTJYLSH() {
        return PTJYLSH;
    }

    public void setPTJYLSH(String PTJYLSH) {
        this.PTJYLSH = PTJYLSH;
    }

    public String getFPQQLSH() {
        return FPQQLSH;
    }

    public void setFPQQLSH(String FPQQLSH) {
        this.FPQQLSH = FPQQLSH;
    }

    public String getBMB_BBH() {
        return BMB_BBH;
    }

    public void setBMB_BBH(String BMB_BBH) {
        this.BMB_BBH = BMB_BBH;
    }
}
