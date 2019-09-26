package com.aisino.domain.einvoice.entity;

public class OrderInfoEntity {
	
	 /**
     * 订单号码(DDH)
     */
    private String ddh;

	 /**
     * 税纳人识别号(NSRSBH)
     */
    private String nsrsbh;

    /**
     * 开票类型 (KPLX,1正票 2红票)
     */
//    private Long kplx;
    
    /**
     * 开票合计金额(HJJE)
     */
    private Double hjje;

    /**
     * 状态码 (ztm)
     * 
     * 	受理成功（6000）
    	生成失败（7001）
		生成成功（7002）
		开具成功（8000）
     */
    private	 String ztm;
    
    /**
     * 错误描述(cwms) 错误消息 error_msg
     */
    private String cwms;
   

    /**
     * 发票请求唯一流水号(FPQQLSH)
     */
 //   private String fpqqlsh;

    /**
     * 原发票代码(YFP_DM)
     */
//    private String YFP_DM;

    /**
     * 原发票号码(YFP_HM)
     */
  //  private String YFP_HM;

    
    
    
    
	public Double getHjje() {
		return hjje;
	}

	public void setHjje(Double hjje) {
		this.hjje = hjje;
	}

	public OrderInfoEntity() {
		super();
	}

	public String getNsrsbh() {
		return nsrsbh;
	}

	public void setNsrsbh(String nsrsbh) {
		this.nsrsbh = nsrsbh;
	}

	
	public String getDdh() {
		return ddh;
	}

	public void setDdh(String ddh) {
		this.ddh = ddh;
	}
	
	/*public Long getKplx() {
		return kplx;
	}

	public void setKplx(Long kplx) {
		this.kplx = kplx;
	}


	public String getFpqqlsh() {
		return fpqqlsh;
	}

	public void setFpqqlsh(String fpqqlsh) {
		this.fpqqlsh = fpqqlsh;
	}

	public String getYFP_DM() {
		return YFP_DM;
	}

	public void setYFP_DM(String yFP_DM) {
		YFP_DM = yFP_DM;
	}

	public String getYFP_HM() {
		return YFP_HM;
	}

	public void setYFP_HM(String yFP_HM) {
		YFP_HM = yFP_HM;
	}
*/
	public String getZtm() {
		return ztm;
	}

	public void setZtm(String ztm) {
		this.ztm = ztm;
	}

	public String getCwms() {
		return cwms;
	}

	public void setCwms(String cwms) {
		this.cwms = cwms;
	}

	@Override
	public String toString() {
		return "OrderInfoEntity [ddh=" + ddh + ", nsrsbh=" + nsrsbh + ", hjje="
				+ hjje + ", ztm=" + ztm + ", cwms=" + cwms + "]";
	}

}
