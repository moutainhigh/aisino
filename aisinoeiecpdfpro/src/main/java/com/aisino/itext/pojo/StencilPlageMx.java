package com.aisino.itext.pojo;

public class StencilPlageMx {
    private String xh;    //序号
    private String spmc;  //货物或应税劳务名称
    private String ggxh;  //规格型号
    private String dw;    //单位
    private String sl;    //数量
    private String dj;    //单价
    private String je;    //金额
    private String slv;   //税率
    private String se;    //税额
    
   /* //TODO 调用京东云签章需添加新字段  2017-06-22 FWH
    private String fphxz ;// 发票行性质 0 正常行、 1 折扣行、 2 被折扣行
	private String spbm ;// 商品编码
	private String yhzcbs ;// 优惠政策标识 0：不使用， 1：使用
	private String lslbs ;// 零税率标识 空：非零税率， 1：免税， 2：不征税， 3 普通零税率
	private String zzstsgl ;// 增值税特殊管理，当有优惠政策时，必填
	private String kce ;//扣除额
*/    
    
    public StencilPlageMx(){
        this.xh="";
        this.spmc="";
        this.ggxh="";
        this.dw="";
        this.dw="";
        this.sl="";
        this.dj="";
        this.je="";
        this.slv="";
        this.se="";
        /*//TODO
        this.fphxz="";
        this.spbm="";
        this.yhzcbs="";
        this.lslbs="";
        this.zzstsgl="";
        this.kce="";*/
    }
    
    
    




	public String getXh() {
        return xh;
    }

    public void setXh(String xh) {
        this.xh = xh;
    }

    public String getSpmc() {
        return spmc;
    }

    public void setSpmc(String spmc) {
        this.spmc = spmc;
    }

    public String getGgxh() {
        return ggxh;
    }

    public void setGgxh(String ggxh) {
        this.ggxh = ggxh;
    }

    public String getDw() {
        return dw;
    }

    public void setDw(String dw) {
        this.dw = dw;
    }

    public String getSl() {
        return sl;
    }

    public void setSl(String sl) {
        this.sl = sl;
    }

    public String getDj() {
        return dj;
    }

    public void setDj(String dj) {
        this.dj = dj;
    }

    public String getJe() {
        return je;
    }

    public void setJe(String je) {
        this.je = je;
    }

    public String getSlv() {
        return slv;
    }

    public void setSlv(String slv) {
        this.slv = slv;
    }

    public String getSe() {
        return se;
    }

    public void setSe(String se) {
        this.se = se;
    }
    
}
