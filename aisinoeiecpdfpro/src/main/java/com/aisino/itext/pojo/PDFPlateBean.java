package com.aisino.itext.pojo;

/**
 * @author Administrator 生成PDF文件BEAN
 */
public class PDFPlateBean {

    private String fp_dm;
    private String fp_hm;
    private String kprq;
    private String jym;
    private String ghdwmc;
    private String ghdwnsrsbh;
    private String ghdwdz_dh;
    private String ghdwkhyh_zh;
    private String mmq;
    private String bz;
    private String kpy;
    private String xhdwz;
    private String fhr;
    private String jshj;
    private String jshjdx;
    private String xhdwmc;
    private String xhdwnsrsbh;
    private String xhdwdz_dh;
    private String xhdwkhyh_zh;
    private String sky;
    private PDFPlageMxBean[] PDFPlageMxBeans;

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

    public PDFPlageMxBean[] getPDFPlageMxBeans() {
        return PDFPlageMxBeans;
    }

    public void setPDFPlageMxBeans(PDFPlageMxBean[] pDFPlageMxBeans) {
        PDFPlageMxBeans = pDFPlageMxBeans;
    }

    public PDFPlateBean copy() {
        PDFPlateBean plate = new PDFPlateBean();
        plate.setFp_dm(this.fp_dm);
        plate.setFp_hm(this.fp_hm);
        plate.setKprq(this.kprq);
        plate.setFhr(this.fhr);
        plate.setJym(this.jym);
        plate.setGhdwmc(this.ghdwmc);
        plate.setGhdwnsrsbh(this.ghdwnsrsbh);
        plate.setGhdwdz_dh(this.ghdwdz_dh);
        plate.setGhdwkhyh_zh(this.ghdwkhyh_zh);
        plate.setBz(this.bz);
        plate.setMmq(this.mmq);
        plate.setXhdwz(this.xhdwz);
        plate.setKpy(this.kpy);
        plate.setJshj(this.jshj);
        plate.setJshjdx(this.jshjdx);
        plate.setPDFPlageMxBeans(this.PDFPlageMxBeans);
        plate.setXhdwmc(this.xhdwmc);
        plate.setXhdwnsrsbh(this.xhdwnsrsbh);
        plate.setXhdwdz_dh(this.xhdwdz_dh);
        plate.setXhdwkhyh_zh(this.xhdwkhyh_zh);
        plate.setSky(this.sky);
        return plate;
    }
}
