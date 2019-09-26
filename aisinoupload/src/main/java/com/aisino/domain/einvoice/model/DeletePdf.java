/**
 * 文件名：DeletePdf.java
 *
 * 创建人：张双超
 *
 * 创建时间：2014-1-24 下午01:54:34
 *
 * 版权所有：航天信息股份有限公司
 */
package com.aisino.domain.einvoice.model;

/**
 * <p>[删除pdf文件]</p>
 *
 * @author 张双超
 * @version 1.0 Created on 2014-1-24 下午01:54:34
 */
public class DeletePdf {
	private String id;
	private String nsrsbh;
	private String dsptbm;
	private String szswjgdm;
	private String xgsj;
	private String pdfpath;

	public String getPdfpath() {
		return pdfpath;
	}

	public void setPdfpath(String pdfpath) {
		this.pdfpath = pdfpath;
	}

	public String getXgsj() {
		return xgsj;
	}

	public void setXgsj(String xgsj) {
		this.xgsj = xgsj;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNsrsbh() {
		return nsrsbh;
	}

	public void setNsrsbh(String nsrsbh) {
		this.nsrsbh = nsrsbh;
	}

	public String getDsptbm() {
		return dsptbm;
	}

	public void setDsptbm(String dsptbm) {
		this.dsptbm = dsptbm;
	}

	public String getSzswjgdm() {
		return szswjgdm;
	}

	public void setSzswjgdm(String szswjgdm) {
		this.szswjgdm = szswjgdm;
	}
}
