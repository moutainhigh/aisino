package com.aisino.domain.einvoice.entity;

import com.aisino.domain.AbstractBaseDomain;

import static com.google.common.base.MoreObjects.toStringHelper;
import static com.google.common.base.Strings.isNullOrEmpty;

/**
 * Created by tommy.wang on 12/1/2014.
 * 纳税人路由信息表(t_taxpayer_route)
 *
 * @see com.aisino.domain.AbstractBaseDomain
 */
public final class TaxcodeRouteEntity extends AbstractBaseDomain {

    private static final long serialVersionUID = 1947580310785175439L;

    /*
     * 纳税人识别号(NSRSBH)
     */
    private String taxpayerIdentifyNo;

    /*
     * 路由URL
     */
    private String lpurl;
    private String hpurl;

    /*
     * 企业webservice方法名(WSFFM)
     */
	private String wsMethodName;
	
	/**
     * 推送报文加密方式(JMLX)
     */
	private String encrypType;
	
    /*
     * 是否启用推送功能
     */
    private String available;

    /**
     * 录入日期
     */
    private String createdDate;
   
    /**
     * 版本号标识
     * */
    private String versionNo;
    
   
    public String getVersionNo() {
		return versionNo;
	}

	public void setVersionNo(String versionNo) {
		this.versionNo = versionNo;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getTaxpayerIdentifyNo() {
        return taxpayerIdentifyNo;
    }

    public void setTaxpayerIdentifyNo(String taxpayerIdentifyNo) {
        this.taxpayerIdentifyNo = taxpayerIdentifyNo;
    }


    public String getLpurl() {
		return lpurl;
	}

	public void setLpurl(String lpurl) {
		this.lpurl = lpurl;
	}

	public String getHpurl() {
		return hpurl;
	}

	public void setHpurl(String hpurl) {
		this.hpurl = hpurl;
	}

	/**
	 * @return the wsMethodName
	 */
	public String getWsMethodName() {
		return wsMethodName;
	}

	/**
	 * @param wsMethodName the wsMethodName to set
	 */
	public void setWsMethodName(String wsMethodName) {
		this.wsMethodName = wsMethodName;
	}

	/**
	 * @return the encrypType
	 */
	public String getEncrypType() {
		return encrypType;
	}

	/**
	 * @param encrypType the encrypType to set
	 */
	public void setEncrypType(String encrypType) {
		this.encrypType = encrypType;
	}

	public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public Boolean isNullObject() {
        return isNullOrEmpty(taxpayerIdentifyNo) || isNullOrEmpty(lpurl) || isNullOrEmpty(hpurl);
    }

    @Override
    public String toString() {
        return toStringHelper(this)
                .add("taxpayerIdentifyNo", taxpayerIdentifyNo)
                .add("lpurl", lpurl)
                .add("hpurl", hpurl)
                .add("wsMethodName", wsMethodName)
                .add("encrypType", encrypType)
                .add("available", available)
                .add("createdDate", createdDate)
                .add("versionNo", versionNo)
                .toString();
    }
}
