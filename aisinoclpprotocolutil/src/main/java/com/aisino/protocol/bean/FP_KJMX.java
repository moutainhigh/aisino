package com.aisino.protocol.bean;

public class FP_KJMX {
    // 项目信息
    private String SPMC;// 商品或劳务名称
    private String SM;// 税目
    private String SL;// 税率
    private String GGXH;
    private String JLDW;// 计量单位
    private String SPSL;
    private String SPDJ;
    private String SPJE;
    private String FPHXZ;//发票行性质
    private String HSJBZ;// 含税价标志
    private String SE;// 含税价标志
    //新增明细字段 2016年7月5日 21:00 王韬
    private String SPBM;//商品编码
    private String ZXBM;//自行编码
    private String YHZCBS;//优惠政策标识
    private String LSLBS;//零税率标识
    private String ZZSTSGL;//增值税特殊管理
    private String KCE;//扣除额
    public String getGGXH() {
		return GGXH;
	}

	public void setGGXH(String gGXH) {
		GGXH = gGXH;
	}

	public String getSE() {
        return SE;
    }

    public void setSE(String sE) {
        SE = sE;
    }

    public String getHSJBZ() {
        return HSJBZ;
    }

    public void setHSJBZ(String hSJBZ) {
        HSJBZ = hSJBZ;
    }

    public String getSPSL() {
        return SPSL;
    }

    public void setSPSL(String sPSL) {
        SPSL = sPSL;
    }

    public String getSPDJ() {
        return SPDJ;
    }

    public void setSPDJ(String sPDJ) {
        SPDJ = sPDJ;
    }

    public String getSPJE() {
        return SPJE;
    }

    public void setSPJE(String sPJE) {
        SPJE = sPJE;
    }

    public String getSPMC() {
        return SPMC;
    }

    public void setSPMC(String sPMC) {
        SPMC = sPMC;
    }

    public String getSM() {
        return SM;
    }

    public void setSM(String sM) {
        SM = sM;
    }

    public String getSL() {
        return SL;
    }

    public void setSL(String sL) {
        SL = sL;
    }

    public String getJLDW() {
        return JLDW;
    }

    public void setJLDW(String jLDW) {
        JLDW = jLDW;
    }

	public String getSPBM() {
		return SPBM;
	}

	public void setSPBM(String sPBM) {
		SPBM = sPBM;
	}

	public String getZXBM() {
		return ZXBM;
	}

	public void setZXBM(String zXBM) {
		ZXBM = zXBM;
	}

	public String getYHZCBS() {
		return YHZCBS;
	}

	public void setYHZCBS(String yHZCBS) {
		YHZCBS = yHZCBS;
	}

	public String getLSLBS() {
		return LSLBS;
	}

	public void setLSLBS(String lSLBS) {
		LSLBS = lSLBS;
	}

	public String getZZSTSGL() {
		return ZZSTSGL;
	}

	public void setZZSTSGL(String zZSTSGL) {
		ZZSTSGL = zZSTSGL;
	}

	public String getKCE() {
		return KCE;
	}

	public void setKCE(String kCE) {
		KCE = kCE;
	}

	public String getFPHXZ() {
		return FPHXZ;
	}

	public void setFPHXZ(String fPHXZ) {
		FPHXZ = fPHXZ;
	}
    
}
