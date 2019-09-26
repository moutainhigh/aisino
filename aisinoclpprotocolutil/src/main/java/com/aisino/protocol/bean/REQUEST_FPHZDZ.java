package com.aisino.protocol.bean;

public class REQUEST_FPHZDZ implements REQUEST_BEAN{
	private String DZLSH;//对账流水号
	private String DSPTBM;//电商平台编码
	
    private String DZSJ;//对账时间
    private String KPSJQ;//开票时间起
    private String KPSJZ;//开票时间止
    private String LPZSL;//蓝票总数量
    private String LPZJE;//蓝票总金额
    private String HPZSL;//红票总数量
    private String HPZJE;//红票总金额
    
	public String getDSPTBM() {
		return DSPTBM;
	}
	public void setDSPTBM(String dSPTBM) {
		DSPTBM = dSPTBM;
	}
	public String getDZLSH() {
		return DZLSH;
	}
	public void setDZLSH(String dZLSH) {
		DZLSH = dZLSH;
	}
	
	public String getDZSJ() {
		return DZSJ;
	}
	public void setDZSJ(String dZSJ) {
		DZSJ = dZSJ;
	}
	public String getKPSJQ() {
        return KPSJQ;
    }
    public void setKPSJQ(String kpsjq) {
        KPSJQ = kpsjq;
    }
    public String getKPSJZ() {
        return KPSJZ;
    }
    public void setKPSJZ(String kpsjz) {
        KPSJZ = kpsjz;
    }
    public String getLPZSL() {
		return LPZSL;
	}
	public void setLPZSL(String lPZSL) {
		LPZSL = lPZSL;
	}
	public String getLPZJE() {
		return LPZJE;
	}
	public void setLPZJE(String lPZJE) {
		LPZJE = lPZJE;
	}
	public String getHPZSL() {
		return HPZSL;
	}
	public void setHPZSL(String hPZSL) {
		HPZSL = hPZSL;
	}
	public String getHPZJE() {
		return HPZJE;
	}
	public void setHPZJE(String hPZJE) {
		HPZJE = hPZJE;
	}

}
