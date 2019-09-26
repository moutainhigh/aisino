package com.aisino.domain.rabbit.entity;

import static com.google.common.base.MoreObjects.toStringHelper;

import org.springframework.stereotype.Service;

public class SendPlatformQyeueEntity extends AbstractQueueDomain {
	
	/**
	 * @date: Created on 2015-3-16 by zhongsiwei 
	 */

	private Long invoiceId;

	private String eshopCode;

	public Long getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(Long invoiceId) {
		this.invoiceId = invoiceId;
	}

	public String getEshopCode() {
		return eshopCode;
	}

	public void setEshopCode(String eshopCode) {
		this.eshopCode = eshopCode;
	}

	@Override
	public Boolean isNullObject() {
		return null;
	}
    @Override
    public String toString() {
        return toStringHelper(this).
                add("invoiceId", invoiceId).
                add("eshopCode", eshopCode).
                toString();
    }

}
