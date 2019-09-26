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
public final class TaxpayerRouteEntity extends AbstractBaseDomain {

    private static final long serialVersionUID = 1947580310785175439L;

    /*
     * 纳税人识别号(NSRSBH)
     */
    private String taxpayerIdentifyNo;

    /*
     * 路由URL
     */
    private String url;

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

    public String getTaxpayerIdentifyNo() {
        return taxpayerIdentifyNo;
    }

    public void setTaxpayerIdentifyNo(String taxpayerIdentifyNo) {
        this.taxpayerIdentifyNo = taxpayerIdentifyNo;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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
        return isNullOrEmpty(taxpayerIdentifyNo) || isNullOrEmpty(url);
    }

    @Override
    public String toString() {
        return toStringHelper(this)
                .add("taxpayerIdentifyNo", taxpayerIdentifyNo)
                .add("url", url)
                .add("wsMethodName", wsMethodName)
                .add("encrypType", encrypType)
                .add("available", available)
                .add("createdDate", createdDate)
                .toString();
    }
}
