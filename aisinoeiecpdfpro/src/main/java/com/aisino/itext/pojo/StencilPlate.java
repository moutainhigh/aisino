package com.aisino.itext.pojo;


/**
 * @author Administrator
 * 生成PDF文件BEAN
 */
public class StencilPlate {
	

    private String fp_dm;//发票代码
    private String fp_hm;//发票号码
    private String kprq;//开票日期
    private String kprq_n;//开票日期
    private String kprq_y;//开票日期
    private String kprq_r;//开票日期
    private String jym;//校验码
    private String ghdwmc;//购货单位名称
    private String ghdwnsrsbh;//购货单位纳税人识别号
    private String ghdwdz_dh;//购货单位地址和电话
    private String ghdwkhyh_zh;//购货单位开户银行和帐号
    private String mmq;//密码区
    private String mmq1;//密码区1
    private String mmq2;//密码区2
    private String mmq3;//密码区3
    private String mmq_B;//密码区
    private String mmq1_B;//密码区1
    private String mmq2_B;//密码区2
    private String mmq3_B;//密码区3
    private String bz;//备注
    private String kpy;//开票员
    private String xhdwz;//销货单位章
    private String fhr;//复核人
    private String jshj;//价税合计
    private String jshjdx;//价税合计大写
    private String xhdwmc;//销货单位名称
    private String xhdwnsrsbh;//销货单位纳税人识别号
    private String xhdwdz_dh;//销货单位地址和电话
    private String xhdwkhyh_zh;//销货单位开户银行和帐号
    private String sky;//收款人
    private String ewm;//二维码
    private String hjje;//合计金额
    private String hjse;//合计税额
    private String kplx;//开票类型
    private String slbz;//清单模版税率标志（如果所有明细税率一致，则第一页显示税率）
    private String jqbh;//机器编号
    
    public String getJqbh() {
        return jqbh;
    }

    public void setJqbh(String jqbh) {
        this.jqbh = jqbh;
    }

    public String getSlbz() {
        return slbz;
    }

    public void setSlbz(String slbz) {
        this.slbz = slbz;
    }

    private StencilPlageMx[] StencilPlageMxs; //明细数据
    
    public String getMmq_B() {
        return mmq_B;
    }

    public void setMmq_B(String mmqB) {
        mmq_B = mmqB;
    }

    public String getMmq1_B() {
        return mmq1_B;
    }

    public void setMmq1_B(String mmq1B) {
        mmq1_B = mmq1B;
    }

    public String getMmq2_B() {
        return mmq2_B;
    }

    public void setMmq2_B(String mmq2B) {
        mmq2_B = mmq2B;
    }

    public String getMmq3_B() {
        return mmq3_B;
    }

    public void setMmq3_B(String mmq3B) {
        mmq3_B = mmq3B;
    }

    
    
    public String getKprq_n() {
        return kprq_n;
    }

    public void setKprq_n(String kprqN) {
        kprq_n = kprqN;
    }

    public String getKprq_y() {
        return kprq_y;
    }

    public void setKprq_y(String kprqY) {
        kprq_y = kprqY;
    }

    public String getKprq_r() {
        return kprq_r;
    }

    public void setKprq_r(String kprqR) {
        kprq_r = kprqR;
    }

    public String getKplx() {
        return kplx;
    }

    public void setKplx(String kplx) {
        this.kplx = kplx;
    }

    public String getFp_dm() {
        return fp_dm;
    }

    public void setFp_dm(String fpDm) {
        fp_dm = fpDm;
    }

    public String getFp_hm() {
        return fp_hm;
    }

    public void setFp_hm(String fpHm) {
        fp_hm = fpHm;
    }

    public String getKprq() {
        return kprq;
    }

    public void setKprq(String kprq) {
        this.kprq = kprq;
    }

    public String getJym() {
        return jym;
    }

    public void setJym(String jym) {
        this.jym = jym;
    }

    public String getGhdwmc() {
        return ghdwmc;
    }

    public void setGhdwmc(String ghdwmc) {
        this.ghdwmc = ghdwmc;
    }

    public String getGhdwnsrsbh() {
        return ghdwnsrsbh;
    }

    public void setGhdwnsrsbh(String ghdwnsrsbh) {
        this.ghdwnsrsbh = ghdwnsrsbh;
    }

    public String getGhdwdz_dh() {
        return ghdwdz_dh;
    }

    public void setGhdwdz_dh(String ghdwdzDh) {
        ghdwdz_dh = ghdwdzDh;
    }

    public String getGhdwkhyh_zh() {
        return ghdwkhyh_zh;
    }

    public void setGhdwkhyh_zh(String ghdwkhyhZh) {
        ghdwkhyh_zh = ghdwkhyhZh;
    }

    public String getMmq() {
        return mmq;
    }

    public void setMmq(String mmq) {
        this.mmq = mmq;
    }
    
    public String getMmq1() {
        return mmq1;
    }

    public void setMmq1(String mmq1) {
        this.mmq1 = mmq1;
    }
    
    public String getMmq2() {
        return mmq2;
    }

    public void setMmq2(String mmq2) {
        this.mmq2 = mmq2;
    }
    
    public String getMmq3() {
        return mmq3;
    }

    public void setMmq3(String mmq3) {
        this.mmq3 = mmq3;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    public String getKpy() {
        return kpy;
    }

    public void setKpy(String kpy) {
        this.kpy = kpy;
    }

    public String getXhdwz() {
        return xhdwz;
    }

    public void setXhdwz(String xhdwz) {
        this.xhdwz = xhdwz;
    }

    public String getFhr() {
        return fhr;
    }

    public void setFhr(String fhr) {
        this.fhr = fhr;
    }

    public String getJshj() {
        return jshj;
    }

    public void setJshj(String jshj) {
        this.jshj = jshj;
    }

    public String getJshjdx() {
        return jshjdx;
    }

    public void setJshjdx(String jshjdx) {
        this.jshjdx = jshjdx;
    }

    public String getXhdwmc() {
        return xhdwmc;
    }

    public void setXhdwmc(String xhdwmc) {
        this.xhdwmc = xhdwmc;
    }

    public String getXhdwnsrsbh() {
        return xhdwnsrsbh;
    }

    public void setXhdwnsrsbh(String xhdwnsrsbh) {
        this.xhdwnsrsbh = xhdwnsrsbh;
    }

    public String getXhdwdz_dh() {
        return xhdwdz_dh;
    }

    public void setXhdwdz_dh(String xhdwdzDh) {
        xhdwdz_dh = xhdwdzDh;
    }

    public String getXhdwkhyh_zh() {
        return xhdwkhyh_zh;
    }

    public void setXhdwkhyh_zh(String xhdwkhyhZh) {
        xhdwkhyh_zh = xhdwkhyhZh;
    }

    public String getSky() {
        return sky;
    }

    public void setSky(String sky) {
        this.sky = sky;
    }

    public String getEwm() {
        return ewm;
    }

    public void setEwm(String ewm) {
        this.ewm = ewm;
    }

    public String getHjje() {
        return hjje;
    }

    public void setHjje(String hjje) {
        this.hjje = hjje;
    }

    public String getHjse() {
        return hjse;
    }

    public void setHjse(String hjse) {
        this.hjse = hjse;
    }

    public StencilPlageMx[] getStencilPlageMxs() {
        return StencilPlageMxs;
    }

    public void setStencilPlageMxs(StencilPlageMx[] stencilPlageMxs) {
        StencilPlageMxs = stencilPlageMxs;
    }

    public StencilPlate copy() {
        StencilPlate plate = new StencilPlate();
        plate.setFp_dm(this.fp_dm);
        plate.setFp_hm(this.fp_hm);
        plate.setKprq(this.kprq);
        plate.setKprq_n(this.kprq_n);
        plate.setKprq_y(this.kprq_y);
        plate.setKprq_r(this.kprq_r);
        plate.setFhr(this.fhr);
        plate.setJym(this.jym);
        plate.setGhdwmc(this.ghdwmc);
        plate.setGhdwnsrsbh(this.ghdwnsrsbh);
        plate.setGhdwdz_dh(this.ghdwdz_dh);
        plate.setGhdwkhyh_zh(this.ghdwkhyh_zh);
        plate.setBz(this.bz);
        plate.setMmq(this.mmq);
        plate.setMmq1(this.mmq1);
        plate.setMmq2(this.mmq2);
        plate.setMmq3(this.mmq3);
        plate.setMmq_B(this.mmq_B);
        plate.setMmq1_B(this.mmq1_B);
        plate.setMmq2_B(this.mmq2_B);
        plate.setMmq3_B(this.mmq3_B);
        plate.setXhdwz(this.xhdwz);
        plate.setKpy(this.kpy);
        plate.setJshj(this.jshj);
        plate.setJshjdx(this.jshjdx);
        plate.setStencilPlageMxs(this.StencilPlageMxs);
        plate.setXhdwmc(this.xhdwmc);
        plate.setXhdwnsrsbh(this.xhdwnsrsbh);
        plate.setXhdwdz_dh(this.xhdwdz_dh);
        plate.setXhdwkhyh_zh(this.xhdwkhyh_zh);
        plate.setSky(this.sky);
        plate.setEwm(this.ewm);
        plate.setHjje(this.hjje);
        plate.setHjse(this.hjse);
        plate.setKplx(this.kplx);
        plate.setSlbz(this.slbz);
        plate.setJqbh(this.jqbh);
        return plate;
    }

}
