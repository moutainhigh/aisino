package com.aisino.itext.pojo;

public class PdfOut {
	private String swjg_dm;
	private String kprq;
	private String fphm;
	private String fileName;
	private byte[] pdfFileData;
	public String getSwjg_dm() {
		return swjg_dm;
	}
	public void setSwjg_dm(String swjg_dm) {
		this.swjg_dm = swjg_dm;
	}
	public String getKprq() {
		return kprq;
	}
	public void setKprq(String kprq) {
		this.kprq = kprq;
	}
	public String getFphm() {
		return fphm;
	}
	public void setFphm(String fphm) {
		this.fphm = fphm;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public byte[] getPdfFileData() {
		return pdfFileData;
	}
	public void setPdfFileData(byte[] pdfFileData) {
		this.pdfFileData = pdfFileData;
	}
}
