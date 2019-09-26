package com.aisino.protocol.bean;

/**
 * @author 吴永
 * 
 * 发票开具处理后返回给电商平台，把发票开具信息传递给电商平台
 */
public class REQUEST_FPKJXX_FPJGXX {

	private String DSPTBM;// 电商平台编码
	private String NSRSBH;// 纳税人识别号
	private String KPLSH;// 开票流水号
	private String NSRMC;// 纳税人名称
	private String FPQQLSH;// 发票开具流水号码
	private String FWM;// 发票防伪码
	private String EWM;// 发票二维码
	private String FPZL_DM;// 发票种类代码
	private String FP_DM;// 发票代码
	private String FP_HM;// 发票号码
	private String KPLX;// 开具发票类型
	private String PDF_FILE;// 发票开具成功后保存pdf文件路径
	private String DDH;// 发票开具订单号码

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

	public String getKPLSH() {
		return KPLSH;
	}

	public void setKPLSH(String kplsh) {
		KPLSH = kplsh;
	}

	public String getNSRMC() {
		return NSRMC;
	}

	public void setNSRMC(String nsrmc) {
		NSRMC = nsrmc;
	}

	public String getFPQQLSH() {
		return FPQQLSH;
	}

	public void setFPQQLSH(String fpqqlsh) {
		FPQQLSH = fpqqlsh;
	}

	public String getFWM() {
		return FWM;
	}

	public void setFWM(String fwm) {
		FWM = fwm;
	}

	public String getEWM() {
		return EWM;
	}

	public void setEWM(String ewm) {
		EWM = ewm;
	}

	public String getFPZL_DM() {
		return FPZL_DM;
	}

	public void setFPZL_DM(String fpzl_dm) {
		FPZL_DM = fpzl_dm;
	}

	public String getFP_DM() {
		return FP_DM;
	}

	public void setFP_DM(String fp_dm) {
		FP_DM = fp_dm;
	}

	public String getFP_HM() {
		return FP_HM;
	}

	public void setFP_HM(String fp_hm) {
		FP_HM = fp_hm;
	}

	public String getKPLX() {
		return KPLX;
	}

	public void setKPLX(String kplx) {
		KPLX = kplx;
	}

	public String getPDF_FILE() {
		return PDF_FILE;
	}

	public void setPDF_FILE(String pdf_file) {
		PDF_FILE = pdf_file;
	}

	public String getDDH() {
		return DDH;
	}

	public void setDDH(String ddh) {
		DDH = ddh;
	}
}
