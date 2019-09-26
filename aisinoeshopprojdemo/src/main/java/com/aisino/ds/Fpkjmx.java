package com.aisino.ds;


import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Date;

/**
 * 
 * <p>
 * [描述信息：发票开具明细]
 * </p>
 * 
 * @author jason - jason.liu@safesoftinc.com
 * @version 1.0 Created on Mar 22, 2010 1:37:25 PM
 */
public class Fpkjmx implements Serializable {
    private Long kjid;// 发票开具ID
    private Long sphxh;// 商品行序号
    private String spmc;// 商品名称
    private String spsl;// 商品数量
    private Double spje;// 商品金额
    private String spdj;// 商品单价
    private String bz;
    private Date cjsj;
    private String jldwDm;
    private String jldwMc;
    private String spzlDm;
    private String ggxh;
    private Date kprq;// 开票日期
    private String smDm;
    private Double sl;// 税率
    private Double zsl;// 征收率
    private String kprqStr;
    private String spjeStr;
    private String spdjStr;
    private String spslStr;
    private String spsl2Str;
    private Double spsl2;// 商品数量2
    private String jldwMc2;// 计量单位2
    private String zdy1;
    // 宁波新增 水费20110625
    private String bycs;// 本月抄数
    private String syds;// 上月底数
    private String syl;// 使用量(立方米)
    private String fpdm;
    private String fphm;
    
    private String zk; // Roinli 2013年01月21日 [折扣]
    
	//税额
	private String se;
	//完税凭证号码
	private String wspzhm;
	
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
	
    public String getZdy_val1() {
		return zdy_val1;
	}

	public void setZdy_val1(String zdy_val1) {
		this.zdy_val1 = zdy_val1;
	}
	/*public String getZdy_val1Format() {
        if (zdy_val1 != null && !"".equals(zdy_val1)) {
            return format.format(Double.parseDouble(zdy_val1));
        }
        return null;
    }*/
	public String getZdy_val2() {
		return zdy_val2;
	}

	public void setZdy_val2(String zdy_val2) {
		this.zdy_val2 = zdy_val2;
	}
	/*public String getZdy_val2Format() {
        if (zdy_val2 != null && !"".equals(zdy_val2)) {
            return format.format(Double.parseDouble(zdy_val2));
        }
        return null;
    }*/
	public String getZdy_val3() {
		return zdy_val3;
	}

	public void setZdy_val3(String zdy_val3) {
		this.zdy_val3 = zdy_val3;
	}
	/*public String getZdy_val3Format() {
        if (zdy_val3 != null&& !"".equals(zdy_val3)) {
            return format.format(Double.parseDouble(zdy_val3));
        }
        return null;
    }*/
	public String getZdy_val4() {
		return zdy_val4;
	}

	public void setZdy_val4(String zdy_val4) {
		this.zdy_val4 = zdy_val4;
	}
	/*public String getZdy_val4Format() {
        if (zdy_val4 != null&& !"".equals(zdy_val4)) {
            return format.format(Double.parseDouble(zdy_val4));
        }
        return null;
    }*/
	public String getZdy_val5() {
		return zdy_val5;
	}

	public void setZdy_val5(String zdy_val5) {
		this.zdy_val5 = zdy_val5;
	}
	/*public String getZdy_val5Format() {
        if (zdy_val5 != null&& !"".equals(zdy_val5)) {
            return format.format(Double.parseDouble(zdy_val5));
        }
        return null;
    }*/
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

	public DecimalFormat getFormat() {
		return format;
	}

	public void setFormat(DecimalFormat format) {
		this.format = format;
	}

	public DecimalFormat getFormat1() {
		return format1;
	}

	public void setFormat1(DecimalFormat format1) {
		this.format1 = format1;
	}

	public String getSe() {
		return se;
	}

	public void setSe(String se) {
		this.se = se;
	}

	public String getWspzhm() {
		return wspzhm;
	}

	public void setWspzhm(String wspzhm) {
		this.wspzhm = wspzhm;
	}

	public String getFpdm() {
        return fpdm;
    }

    public void setFpdm(String fpdm) {
        this.fpdm = fpdm;
    }

    public String getFphm() {
        return fphm;
    }

    public void setFphm(String fphm) {
        this.fphm = fphm;
    }

    public String getSpsl2Str() {
        return spsl2Str;
    }

    public void setSpsl2Str(String spsl2Str) {
        this.spsl2Str = spsl2Str;
    }

    public Double getSpsl2() {
        return spsl2;
    }

    public void setSpsl2(Double spsl2) {
        this.spsl2 = spsl2;
    }

    public String getJldwMc2() {
        return jldwMc2;
    }

    public void setJldwMc2(String jldwMc2) {
        this.jldwMc2 = jldwMc2;
    }

    public String getSpjeStr() {
        return spjeStr;
    }

    public void setSpjeStr(String spjeStr) {
        this.spjeStr = spjeStr;
    }

    public String getSpdjStr() {
        return spdjStr;
    }

    public void setSpdjStr(String spdjStr) {
        this.spdjStr = spdjStr;
    }

    public String getSpslStr() {
        return spslStr;
    }

    public void setSpslStr(String spslStr) {
        this.spslStr = spslStr;
    }

    DecimalFormat format = new DecimalFormat("0.00");
    DecimalFormat format1 = new DecimalFormat("#.######");

    public String getSpjeFormat() {
        if (spje != null)
            return format.format(spje.doubleValue());
        return null;
    }

    public String getSpdjFormat() {

        if (spdj != null) {
            return format.format(Double.parseDouble(spdj));
        }
        ;
        return null;
    }

    public Long getKjid() {
        return kjid;
    }

    public void setKjid(Long kjid) {
        this.kjid = kjid;
    }

    public Long getSphxh() {
        return sphxh;
    }

    public void setSphxh(Long sphxh) {
        this.sphxh = sphxh;
    }

    public String getSpmc() {
        return spmc;
    }

    public void setSpmc(String spmc) {
        this.spmc = spmc;
    }

    public String getSpsl() {
        return spsl;
    }

    public String getStrSpsl() {
        if (spsl != null)
            return format1.format(Double.parseDouble(spsl));
        return null;
    }

    public void setSpsl(String spsl) {
        this.spsl = spsl;
    }

    public Double getSpje() {
        return spje;
    }

    public void setSpje(Double spje) {
        this.spje = spje;
    }

    public String getSpdj() {
        return spdj;
    }

    public void setSpdj(String spdj) {
        this.spdj = spdj;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    public Date getCjsj() {
        return cjsj;
    }

    public void setCjsj(Date cjsj) {
        this.cjsj = cjsj;
    }

    public String getJldwDm() {
        return jldwDm;
    }

    public void setJldwDm(String jldwDm) {
        this.jldwDm = jldwDm;
    }

    public String getJldwMc() {
        return jldwMc;
    }

    public void setJldwMc(String jldwMc) {
        this.jldwMc = jldwMc;
    }

    public String getSpzlDm() {
        return spzlDm;
    }

    public void setSpzlDm(String spzlDm) {
        this.spzlDm = spzlDm;
    }

    public String getGgxh() {
        return ggxh;
    }

    public void setGgxh(String ggxh) {
        this.ggxh = ggxh;
    }

    public Date getKprq() {
        return kprq;
    }

    public void setKprq(Date kprq) {
        this.kprq = kprq;
    }

    public String getSmDm() {
        return smDm;
    }

    public void setSmDm(String smDm) {
        this.smDm = smDm;
    }

    public Double getSl() {
        return sl;
    }

    public void setSl(Double sl) {
        this.sl = sl;
    }

    public Double getZsl() {
        return zsl;
    }

    public void setZsl(Double zsl) {
        this.zsl = zsl;
    }

    public String getKprqStr() {
        return kprqStr;
    }

    public void setKprqStr(String kprqStr) {
        this.kprqStr = kprqStr;
    }

    public String getBycs() {
        return bycs;
    }

    public void setBycs(String bycs) {
        this.bycs = bycs;
    }

    public String getSyds() {
        return syds;
    }

    public void setSyds(String syds) {
        this.syds = syds;
    }

    public String getSyl() {
        return syl;
    }

    public void setSyl(String syl) {
        this.syl = syl;
    }

    public String getSylStr() {
        if (syl != null)
            return format1.format(Double.parseDouble(syl));
        return null;
    }

    public String getZdy1() {
        return zdy1;
    }

    public void setZdy1(String zdy1) {
        this.zdy1 = zdy1;
    }

	public String getZk() {
		return zk;
	}

	public void setZk(String zk) {
		this.zk = zk;
	}
	public String getZkFormat() throws ParseException {
        if (zk != null) {
            return format.format(Double.parseDouble(zk));
        }
        return null;
    }
}