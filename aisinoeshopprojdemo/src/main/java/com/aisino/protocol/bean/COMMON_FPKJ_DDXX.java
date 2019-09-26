package com.aisino.protocol.bean;

public class COMMON_FPKJ_DDXX {

    private String DDH;// 订单号
    private String DDSJ;// 订单时间
    private String THDH;

    public String getTHDH() {
        return THDH;
    }

    public void setTHDH(String tHDH) {
        THDH = tHDH;
    }

    public String getDDH() {
        return DDH;
    }

    public void setDDH(String ddh) {
        DDH = ddh;
    }

	public String getDDSJ() {
		return DDSJ;
	}

	public void setDDSJ(String dDSJ) {
		DDSJ = dDSJ;
	}
    
}
