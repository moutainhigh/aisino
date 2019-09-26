package com.aisino.protocol.bean;

import java.sql.Blob;
import java.util.Date;

/**
 * @author fanlb
 */

public class JsYdDataBean
{

	// Fields

	private long id;
	private String prover;
	private String transver;
	private String nsrsbh;
	private String fjh;
	private String reqtypeDm;
	private String reqno;
	private String clstatusDm;
	private String cwms;
	private Date writetime;
	private Date receivetime;
	private Blob jsdata;
	private Blob yddata;
	private int jsdatalen;
	private int yddatalen;
	private int crc;

	// Constructors

	/** default constructor */
	public JsYdDataBean()
	{
	}

	/** minimal constructor */
	public JsYdDataBean(long id)
	{
		this.id = id;
	}

	/** full constructor */
	public JsYdDataBean(long id, String prover, String transver, String nsrsbh, String fjh,
			String reqtypeDm, String reqno, String clstatusDm, String cwms, Date writetime,
			Date receivetime, Blob jsdata, Blob yddata, int jsdatalen, int yddatalen, int crc)
	{
		this.id = id;
		this.prover = prover;
		this.transver = transver;
		this.nsrsbh = nsrsbh;
		this.fjh = fjh;
		this.reqtypeDm = reqtypeDm;
		this.reqno = reqno;
		this.clstatusDm = clstatusDm;
		this.cwms = cwms;
		this.writetime = writetime;
		this.receivetime = receivetime;
		this.jsdata = jsdata;
		this.yddata = yddata;
		this.jsdatalen = jsdatalen;
		this.yddatalen = yddatalen;
		this.crc = crc;
	}

	public long getId()
	{
		return id;
	}

	public void setId(long id)
	{
		this.id = id;
	}

	public String getProver()
	{
		return prover;
	}

	public void setProver(String prover)
	{
		this.prover = prover;
	}

	public String getTransver()
	{
		return transver;
	}

	public void setTransver(String transver)
	{
		this.transver = transver;
	}

	public String getNsrsbh()
	{
		return nsrsbh;
	}

	public void setNsrsbh(String nsrsbh)
	{
		this.nsrsbh = nsrsbh;
	}

	public String getFjh()
	{
		return fjh;
	}

	public void setFjh(String fjh)
	{
		this.fjh = fjh;
	}

	public String getReqtypeDm()
	{
		return reqtypeDm;
	}

	public void setReqtypeDm(String reqtypeDm)
	{
		this.reqtypeDm = reqtypeDm;
	}

	public String getReqno()
	{
		return reqno;
	}

	public void setReqno(String reqno)
	{
		this.reqno = reqno;
	}

	public String getClstatusDm()
	{
		return clstatusDm;
	}

	public void setClstatusDm(String clstatusDm)
	{
		this.clstatusDm = clstatusDm;
	}

	public String getCwms()
	{
		return cwms;
	}

	public void setCwms(String cwms)
	{
		this.cwms = cwms;
	}

	public Date getWritetime()
	{
		return writetime;
	}

	public void setWritetime(Date writetime)
	{
		this.writetime = writetime;
	}

	public Date getReceivetime()
	{
		return receivetime;
	}

	public void setReceivetime(Date receivetime)
	{
		this.receivetime = receivetime;
	}

	public Blob getJsdata()
	{
		return jsdata;
	}

	public void setJsdata(Blob jsdata)
	{
		this.jsdata = jsdata;
	}

	public Blob getYddata()
	{
		return yddata;
	}

	public void setYddata(Blob yddata)
	{
		this.yddata = yddata;
	}

	public int getJsdatalen()
	{
		return jsdatalen;
	}

	public void setJsdatalen(int jsdatalen)
	{
		this.jsdatalen = jsdatalen;
	}

	public int getYddatalen()
	{
		return yddatalen;
	}

	public void setYddatalen(int yddatalen)
	{
		this.yddatalen = yddatalen;
	}

	public int getCrc()
	{
		return crc;
	}

	public void setCrc(int crc)
	{
		this.crc = crc;
	}
}