package com.aisino.protocol.bean;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class FPKJXX_FPTXX {
    private Long iD;// 主键
    private String nsrdzdah;
    private String jqbh;// 机器编号
    private String fjh;// 分机号
    private String nsrsbh;// 纳税人识别号
    private String fpzlDm;// 发票种类代码
    private String fpzl;// 发票种类
    private String fpDm;// 发票代码
    private String fpMc;// 发票名称
    private String fpHm;// 发票号码
    private Date kprq;// 开票日期
    private String kprqStr;
    private String xgsjStr;
    private Integer kplx;// 开票类型
    private String swjgDm;// 税务机关代码
    private Double sl;// 税率
    private String ewm;// 二维码
    private String nsrmc;// 纳税人名称
    private String fkdw;//付款单位
    private String fkfDm;//付款方代码
    private String skfDm;// 收款方代码
    private String yfpDm;
    private String yfpHm;
    private Double kphjje;
    private String sky;
    private String kpy;
    private Double sphsl;
    private String kpxm;
    private String cxm;// 查询码
    private String skm;// 税控码
    private String bz;// 备注
    private String zgSwryDm;// 主管税务人员代码
    private String zgSwryMc;// 主管税务人员名称
    private String xhfsbh;
    private String xhfmc;
    private Double zsl;
    private String zyspmc;// 主营商品名称
    private Long dycs;
    private String skfyhzh;// 收款方银行帐号
    private String fkfyhzh;// 付款方银行帐号
    private Date cjsj;
    private Date xgsj;
    private Long xxpzlb;// 票种类别
    private String fplb;
    private String byzd;
    private String hyMc;// 行业名称
    private String hyDm;// 行业代码
    private String czy;// 开票员
    private String pycode;// 票样编码
    private String fkfkhyh;
    private String xkfkhyh;
    private String fkfdz;
    private String fkfdh;
    private String xhfdz;
    private String xhfdh;
    private String swjgMc;
    private String xhfkhyh;
    private String signDate;
    private String mac;
    private String repIdx;
    private String cyrNsrsbh;
    private String zk;

    private String fpqh;
    private String fpzh;

    private String yhid;
    private String yhIndex;
    private String jzy_zbr;//建筑业总包人
    private String jzy_fbr;//建筑业分包人
    private String bdc_sl; //不动产税率
    private String bdc_se; //不动产税额
    private String wspzhm; //完税凭证号
    private String fs;
    private String kcztdm;
    private String czydm;

    /**
     * 标志状态 0 未冲红 1已被冲红
     *
     * @return
     */
    private String bz_kz;
    private String kpzt;
    private String cyzt;// 查验状态
    // 宁波新增 农产品收购20110625
    private String jsje;// 计税金额、
    private String kcl;// 扣除率、
    private String se;// 税额
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private List<Object> fpkjmxList;// 发票开具明细

    DecimalFormat format = new DecimalFormat("0.00");
    private String FSSJ;
    private String FS_SWJG_DM;
    /**
     * 开票类型 描述
     */
    private String kplxDesc;
    private String kplxRes;
    private String cphm;// 车牌号码
    private String skjhm;// 税控机号码
    private String jsfs;// 结算方式
    private String fky;// 付款人

    //纳税人自定义项
    private String zdy_val1;	//自定义值1
    private String zdy_val2;	//自定义值2
    private String zdy_val3;	//自定义值3
    private String zdy_val4;	//自定义值4
    private String zdy_val5;	//自定义值5
    private String zdy_mc1;		//自定义名称1
    private String zdy_mc2;		//自定义名称2
    private String zdy_mc3;		//自定义名称3
    private String zdy_mc4;		//自定义名称4
    private String zdy_mc5;		//自定义名称5
    private String pyversion;	//票样版本号

    private Date skrq;//收款日期
    private String skrqStr;
    private String listkjSign;//清单开具标记

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
    private String XHFMC;// 销货方名称
    private String GHFMC;// 购货方名称
    private String GHF_NSRSBH;// 购货方识别号
    private String GHF_DZ;// 购货方地址
    private String GHF_GDDH;// 购货方固定电话
    private String GHF_SJ;// 购货方手机
    private String GHF_EMAIL;// 购货方邮箱
    private String HY_DM;// 行业代码
    private String HY_MC;// 行业名称
    private String KPY;// 开票员
    private String SKY;// 收款员
    private String KPRQ;// 开票日期
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
    private String GHFQYLX;// 购货方企业类型
    private String CZDM;// 操作代码

    public String getGHFQYLX() {
        return GHFQYLX;
    }

    public void setGHFQYLX(String gHFQYLX) {
        GHFQYLX = gHFQYLX;
    }

    public String getCZDM() {
        return CZDM;
    }

    public void setCZDM(String cZDM) {
        CZDM = cZDM;
    }

    public String getDSPTBM() {
        return DSPTBM;
    }

    public void setDSPTBM(String dsptbm) {
        DSPTBM = dsptbm;
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

    public String getGHFMC() {
        return GHFMC;
    }

    public void setGHFMC(String ghfmc) {
        GHFMC = ghfmc;
    }

    public String getGHF_NSRSBH() {
        return GHF_NSRSBH;
    }

    public void setGHF_NSRSBH(String ghf_nsrsbh) {
        GHF_NSRSBH = ghf_nsrsbh;
    }

    public String getGHF_DZ() {
        return GHF_DZ;
    }

    public void setGHF_DZ(String ghf_dz) {
        GHF_DZ = ghf_dz;
    }

    public String getGHF_GDDH() {
        return GHF_GDDH;
    }

    public void setGHF_GDDH(String ghf_gddh) {
        GHF_GDDH = ghf_gddh;
    }

    public String getGHF_SJ() {
        return GHF_SJ;
    }

    public void setGHF_SJ(String ghf_sj) {
        GHF_SJ = ghf_sj;
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

    public String getKPRQ() {
        return KPRQ;
    }

    public void setKPRQ(String kprq) {
        KPRQ = kprq;
    }

    public String getKPLX() {
        return KPLX;
    }

    public void setKPLX(String kplx) {
        KPLX = kplx;
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

    public String getCHYY() {
        return CHYY;
    }

    public void setCHYY(String chyy) {
        CHYY = chyy;
    }

    public String getKPHJJE() {
        return KPHJJE;
    }

    public void setKPHJJE(String kphjje) {
        KPHJJE = kphjje;
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

    public String getFPQQLSH() {
        return FPQQLSH;
    }

    public void setFPQQLSH(String fpqqlsh) {
        FPQQLSH = fpqqlsh;
    }

    public String getTSCHBZ() {
        return TSCHBZ;
    }

    public void setTSCHBZ(String tschbz) {
        TSCHBZ = tschbz;
    }

    public String getGHF_SF() {
        return GHF_SF;
    }

    public void setGHF_SF(String ghf_sf) {
        GHF_SF = ghf_sf;
    }

    public Long getID() {
        return iD;
    }

    public void setID(Long id) {
        this.iD = id;
    }

    public String getNsrdzdah() {
        return nsrdzdah;
    }

    public void setNsrdzdah(String nsrdzdah) {
        this.nsrdzdah = nsrdzdah;
    }

    public String getJqbh() {
        return jqbh;
    }

    public void setJqbh(String jqbh) {
        this.jqbh = jqbh;
    }

    public String getFjh() {
        return fjh;
    }

    public void setFjh(String fjh) {
        this.fjh = fjh;
    }

    public String getNsrsbh() {
        return nsrsbh;
    }

    public void setNsrsbh(String nsrsbh) {
        this.nsrsbh = nsrsbh;
    }

    public String getFpzlDm() {
        return fpzlDm;
    }

    public void setFpzlDm(String fpzlDm) {
        this.fpzlDm = fpzlDm;
    }

    public String getFpzl() {
        return fpzl;
    }

    public void setFpzl(String fpzl) {
        this.fpzl = fpzl;
    }

    public String getFpDm() {
        return fpDm;
    }

    public void setFpDm(String fpDm) {
        this.fpDm = fpDm;
    }

    public String getFpMc() {
        return fpMc;
    }

    public void setFpMc(String fpMc) {
        this.fpMc = fpMc;
    }

    public String getFpHm() {
        return fpHm;
    }

    public void setFpHm(String fpHm) {
        this.fpHm = fpHm;
    }

    public Date getKprq() {
        return kprq;
    }

    public void setKprq(Date kprq) {
        this.kprq = kprq;
    }

    public String getKprqStr() {
        return kprqStr;
    }

    public void setKprqStr(String kprqStr) {
        this.kprqStr = kprqStr;
    }

    public String getXgsjStr() {
        return xgsjStr;
    }

    public void setXgsjStr(String xgsjStr) {
        this.xgsjStr = xgsjStr;
    }

    public Integer getKplx() {
        return kplx;
    }

    public void setKplx(Integer kplx) {
        this.kplx = kplx;
    }

    public String getSwjgDm() {
        return swjgDm;
    }

    public void setSwjgDm(String swjgDm) {
        this.swjgDm = swjgDm;
    }

    public Double getSl() {
        return sl;
    }

    public void setSl(Double sl) {
        this.sl = sl;
    }

    public String getEwm() {
        return ewm;
    }

    public void setEwm(String ewm) {
        this.ewm = ewm;
    }

    public String getNsrmc() {
        return nsrmc;
    }

    public void setNsrmc(String nsrmc) {
        this.nsrmc = nsrmc;
    }

    public String getFkdw() {
        return fkdw;
    }

    public void setFkdw(String fkdw) {
        this.fkdw = fkdw;
    }

    public String getFkfDm() {
        return fkfDm;
    }

    public void setFkfDm(String fkfDm) {
        this.fkfDm = fkfDm;
    }

    public String getSkfDm() {
        return skfDm;
    }

    public void setSkfDm(String skfDm) {
        this.skfDm = skfDm;
    }

    public String getYfpDm() {
        return yfpDm;
    }

    public void setYfpDm(String yfpDm) {
        this.yfpDm = yfpDm;
    }

    public String getYfpHm() {
        return yfpHm;
    }

    public void setYfpHm(String yfpHm) {
        this.yfpHm = yfpHm;
    }

    public Double getKphjje() {
        return kphjje;
    }

    public void setKphjje(Double kphjje) {
        this.kphjje = kphjje;
    }

    public String getSky() {
        return sky;
    }

    public void setSky(String sky) {
        this.sky = sky;
    }

    public String getKpy() {
        return kpy;
    }

    public void setKpy(String kpy) {
        this.kpy = kpy;
    }

    public Double getSphsl() {
        return sphsl;
    }

    public void setSphsl(Double sphsl) {
        this.sphsl = sphsl;
    }

    public String getKpxm() {
        return kpxm;
    }

    public void setKpxm(String kpxm) {
        this.kpxm = kpxm;
    }

    public String getCxm() {
        return cxm;
    }

    public void setCxm(String cxm) {
        this.cxm = cxm;
    }

    public String getSkm() {
        return skm;
    }

    public void setSkm(String skm) {
        this.skm = skm;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    public String getZgSwryDm() {
        return zgSwryDm;
    }

    public void setZgSwryDm(String zgSwryDm) {
        this.zgSwryDm = zgSwryDm;
    }

    public String getZgSwryMc() {
        return zgSwryMc;
    }

    public void setZgSwryMc(String zgSwryMc) {
        this.zgSwryMc = zgSwryMc;
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

    public Double getZsl() {
        return zsl;
    }

    public void setZsl(Double zsl) {
        this.zsl = zsl;
    }

    public String getZyspmc() {
        return zyspmc;
    }

    public void setZyspmc(String zyspmc) {
        this.zyspmc = zyspmc;
    }

    public Long getDycs() {
        return dycs;
    }

    public void setDycs(Long dycs) {
        this.dycs = dycs;
    }

    public String getSkfyhzh() {
        return skfyhzh;
    }

    public void setSkfyhzh(String skfyhzh) {
        this.skfyhzh = skfyhzh;
    }

    public String getFkfyhzh() {
        return fkfyhzh;
    }

    public void setFkfyhzh(String fkfyhzh) {
        this.fkfyhzh = fkfyhzh;
    }

    public Date getCjsj() {
        return cjsj;
    }

    public void setCjsj(Date cjsj) {
        this.cjsj = cjsj;
    }

    public Date getXgsj() {
        return xgsj;
    }

    public void setXgsj(Date xgsj) {
        this.xgsj = xgsj;
    }

    public Long getXxpzlb() {
        return xxpzlb;
    }

    public void setXxpzlb(Long xxpzlb) {
        this.xxpzlb = xxpzlb;
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

    public String getHyMc() {
        return hyMc;
    }

    public void setHyMc(String hyMc) {
        this.hyMc = hyMc;
    }

    public String getHyDm() {
        return hyDm;
    }

    public void setHyDm(String hyDm) {
        this.hyDm = hyDm;
    }

    public String getCzy() {
        return czy;
    }

    public void setCzy(String czy) {
        this.czy = czy;
    }

    public String getPycode() {
        return pycode;
    }

    public void setPycode(String pycode) {
        this.pycode = pycode;
    }

    public String getFkfkhyh() {
        return fkfkhyh;
    }

    public void setFkfkhyh(String fkfkhyh) {
        this.fkfkhyh = fkfkhyh;
    }

    public String getXkfkhyh() {
        return xkfkhyh;
    }

    public void setXkfkhyh(String xkfkhyh) {
        this.xkfkhyh = xkfkhyh;
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

    public String getSwjgMc() {
        return swjgMc;
    }

    public void setSwjgMc(String swjgMc) {
        this.swjgMc = swjgMc;
    }

    public String getXhfkhyh() {
        return xhfkhyh;
    }

    public void setXhfkhyh(String xhfkhyh) {
        this.xhfkhyh = xhfkhyh;
    }

    public String getSignDate() {
        return signDate;
    }

    public void setSignDate(String signDate) {
        this.signDate = signDate;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getRepIdx() {
        return repIdx;
    }

    public void setRepIdx(String repIdx) {
        this.repIdx = repIdx;
    }

    public String getCyrNsrsbh() {
        return cyrNsrsbh;
    }

    public void setCyrNsrsbh(String cyrNsrsbh) {
        this.cyrNsrsbh = cyrNsrsbh;
    }

    public String getZk() {
        return zk;
    }

    public void setZk(String zk) {
        this.zk = zk;
    }

    public String getFpqh() {
        return fpqh;
    }

    public void setFpqh(String fpqh) {
        this.fpqh = fpqh;
    }

    public String getFpzh() {
        return fpzh;
    }

    public void setFpzh(String fpzh) {
        this.fpzh = fpzh;
    }

    public String getYhid() {
        return yhid;
    }

    public void setYhid(String yhid) {
        this.yhid = yhid;
    }

    public String getYhIndex() {
        return yhIndex;
    }

    public void setYhIndex(String yhIndex) {
        this.yhIndex = yhIndex;
    }

    public String getJzy_zbr() {
        return jzy_zbr;
    }

    public void setJzy_zbr(String jzy_zbr) {
        this.jzy_zbr = jzy_zbr;
    }

    public String getJzy_fbr() {
        return jzy_fbr;
    }

    public void setJzy_fbr(String jzy_fbr) {
        this.jzy_fbr = jzy_fbr;
    }

    public String getBdc_sl() {
        return bdc_sl;
    }

    public void setBdc_sl(String bdc_sl) {
        this.bdc_sl = bdc_sl;
    }

    public String getBdc_se() {
        return bdc_se;
    }

    public void setBdc_se(String bdc_se) {
        this.bdc_se = bdc_se;
    }

    public String getWspzhm() {
        return wspzhm;
    }

    public void setWspzhm(String wspzhm) {
        this.wspzhm = wspzhm;
    }

    public String getFs() {
        return fs;
    }

    public void setFs(String fs) {
        this.fs = fs;
    }

    public String getKcztdm() {
        return kcztdm;
    }

    public void setKcztdm(String kcztdm) {
        this.kcztdm = kcztdm;
    }

    public String getCzydm() {
        return czydm;
    }

    public void setCzydm(String czydm) {
        this.czydm = czydm;
    }

    public String getBz_kz() {
        return bz_kz;
    }

    public void setBz_kz(String bz_kz) {
        this.bz_kz = bz_kz;
    }

    public String getKpzt() {
        return kpzt;
    }

    public void setKpzt(String kpzt) {
        this.kpzt = kpzt;
    }

    public String getCyzt() {
        return cyzt;
    }

    public void setCyzt(String cyzt) {
        this.cyzt = cyzt;
    }

    public String getJsje() {
        return jsje;
    }

    public void setJsje(String jsje) {
        this.jsje = jsje;
    }

    public String getKcl() {
        return kcl;
    }

    public void setKcl(String kcl) {
        this.kcl = kcl;
    }

    public String getSe() {
        return se;
    }

    public void setSe(String se) {
        this.se = se;
    }

    public SimpleDateFormat getSdf() {
        return sdf;
    }

    public void setSdf(SimpleDateFormat sdf) {
        this.sdf = sdf;
    }

    public List<Object> getFpkjmxList() {
        return fpkjmxList;
    }

    public void setFpkjmxList(List<Object> fpkjmxList) {
        this.fpkjmxList = fpkjmxList;
    }

    public DecimalFormat getFormat() {
        return format;
    }

    public void setFormat(DecimalFormat format) {
        this.format = format;
    }

    public String getFSSJ() {
        return FSSJ;
    }

    public void setFSSJ(String FSSJ) {
        this.FSSJ = FSSJ;
    }

    public String getFS_SWJG_DM() {
        return FS_SWJG_DM;
    }

    public void setFS_SWJG_DM(String FS_SWJG_DM) {
        this.FS_SWJG_DM = FS_SWJG_DM;
    }

    public String getKplxDesc() {
        return kplxDesc;
    }

    public void setKplxDesc(String kplxDesc) {
        this.kplxDesc = kplxDesc;
    }

    public String getKplxRes() {
        return kplxRes;
    }

    public void setKplxRes(String kplxRes) {
        this.kplxRes = kplxRes;
    }

    public String getCphm() {
        return cphm;
    }

    public void setCphm(String cphm) {
        this.cphm = cphm;
    }

    public String getSkjhm() {
        return skjhm;
    }

    public void setSkjhm(String skjhm) {
        this.skjhm = skjhm;
    }

    public String getJsfs() {
        return jsfs;
    }

    public void setJsfs(String jsfs) {
        this.jsfs = jsfs;
    }

    public String getFky() {
        return fky;
    }

    public void setFky(String fky) {
        this.fky = fky;
    }

    public String getZdy_val1() {
        return zdy_val1;
    }

    public void setZdy_val1(String zdy_val1) {
        this.zdy_val1 = zdy_val1;
    }

    public String getZdy_val2() {
        return zdy_val2;
    }

    public void setZdy_val2(String zdy_val2) {
        this.zdy_val2 = zdy_val2;
    }

    public String getZdy_val3() {
        return zdy_val3;
    }

    public void setZdy_val3(String zdy_val3) {
        this.zdy_val3 = zdy_val3;
    }

    public String getZdy_val4() {
        return zdy_val4;
    }

    public void setZdy_val4(String zdy_val4) {
        this.zdy_val4 = zdy_val4;
    }

    public String getZdy_val5() {
        return zdy_val5;
    }

    public void setZdy_val5(String zdy_val5) {
        this.zdy_val5 = zdy_val5;
    }

    public String getZdy_mc1() {
        return zdy_mc1;
    }

    public void setZdy_mc1(String zdy_mc1) {
        this.zdy_mc1 = zdy_mc1;
    }

    public String getZdy_mc2() {
        return zdy_mc2;
    }

    public void setZdy_mc2(String zdy_mc2) {
        this.zdy_mc2 = zdy_mc2;
    }

    public String getZdy_mc3() {
        return zdy_mc3;
    }

    public void setZdy_mc3(String zdy_mc3) {
        this.zdy_mc3 = zdy_mc3;
    }

    public String getZdy_mc4() {
        return zdy_mc4;
    }

    public void setZdy_mc4(String zdy_mc4) {
        this.zdy_mc4 = zdy_mc4;
    }

    public String getZdy_mc5() {
        return zdy_mc5;
    }

    public void setZdy_mc5(String zdy_mc5) {
        this.zdy_mc5 = zdy_mc5;
    }

    public String getPyversion() {
        return pyversion;
    }

    public void setPyversion(String pyversion) {
        this.pyversion = pyversion;
    }

    public Date getSkrq() {
        return skrq;
    }

    public void setSkrq(Date skrq) {
        this.skrq = skrq;
    }

    public String getSkrqStr() {
        return skrqStr;
    }

    public void setSkrqStr(String skrqStr) {
        this.skrqStr = skrqStr;
    }

    public String getListkjSign() {
        return listkjSign;
    }

    public void setListkjSign(String listkjSign) {
        this.listkjSign = listkjSign;
    }
}
