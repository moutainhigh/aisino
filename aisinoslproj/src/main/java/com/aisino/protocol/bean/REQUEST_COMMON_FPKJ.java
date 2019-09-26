package com.aisino.protocol.bean;

public class REQUEST_COMMON_FPKJ {

    // 发票开具头部信息
    private COMMON_FPKJ_FPT COMMON_FPKJ_FPT;
    // 项目信息（发票明细）（多条）
    private COMMON_FPKJ_XMXX[] COMMON_FPKJ_XMXXS;
    // 订单信息
    private COMMON_FPKJ_DDXX COMMON_FPKJ_DDXX;

//    private COMMON_FPKJ_DDMXXX[] COMMON_FPKJ_DDMXXXS;
    // 支付信息
    private COMMON_FPKJ_ZFXX COMMON_FPKJ_ZFXX;
    // 物流信息
    private COMMON_FPKJ_WLXX COMMON_FPKJ_WLXX;

    public COMMON_FPKJ_FPT getCOMMON_FPKJ_FPT() {
        return COMMON_FPKJ_FPT;
    }

	public void setCOMMON_FPKJ_FPT(COMMON_FPKJ_FPT cOMMON_FPKJ_FPT) {
        COMMON_FPKJ_FPT = cOMMON_FPKJ_FPT;
    }

    public COMMON_FPKJ_XMXX[] getCOMMON_FPKJ_XMXXS() {
        return COMMON_FPKJ_XMXXS;
    }

    public void setCOMMON_FPKJ_XMXXS(COMMON_FPKJ_XMXX[] cOMMON_FPKJ_XMXXS) {
        COMMON_FPKJ_XMXXS = cOMMON_FPKJ_XMXXS;
    }

    public COMMON_FPKJ_DDXX getCOMMON_FPKJ_DDXX() {
        return COMMON_FPKJ_DDXX;
    }

    public void setCOMMON_FPKJ_DDXX(COMMON_FPKJ_DDXX cOMMON_FPKJ_DDXX) {
        COMMON_FPKJ_DDXX = cOMMON_FPKJ_DDXX;
    }


    public COMMON_FPKJ_ZFXX getCOMMON_FPKJ_ZFXX() {
        return COMMON_FPKJ_ZFXX;
    }

    public void setCOMMON_FPKJ_ZFXX(COMMON_FPKJ_ZFXX cOMMON_FPKJ_ZFXX) {
        COMMON_FPKJ_ZFXX = cOMMON_FPKJ_ZFXX;
    }

    public COMMON_FPKJ_WLXX getCOMMON_FPKJ_WLXX() {
        return COMMON_FPKJ_WLXX;
    }

    public void setCOMMON_FPKJ_WLXX(COMMON_FPKJ_WLXX cOMMON_FPKJ_WLXX) {
        COMMON_FPKJ_WLXX = cOMMON_FPKJ_WLXX;
    }

}
