package com.aisino.domain.einvoice;

import com.aisino.domain.einvoice.entity.*;

/**
 * Created by Bourne.Lv on 2014/09/05.
 * <p/>
 * NullObjects
 */
public final class EInvoiceNullObjects {

    private EInvoiceNullObjects() {
    }

    public static InvoiceBalance nullInvoiceBalance() {
        return new InvoiceBalance();
    }

    public static InvoiceEntity nullInvoiceEntity() {
        return new InvoiceEntity();
    }

    public static InvoiceHeaderEntity nullInvoiceHeaderEntity() {
        return new InvoiceHeaderEntity();
    }

    public static EShopInfo nullEShopInfo() {
        return new EShopInfo();
    }

    public static TaxpayerEntity nullTaxpayerEntity() {
        return new TaxpayerEntity();
    }

    public static CertificateEntity nullCertificateEntity() {
        return new CertificateEntity();
    }

    public static InvoiceOrderEntity nullInvoiceOrderEntity() {
        return new InvoiceOrderEntity();
    }

    public static InvoiceDetailEntity nullInvoiceDetailEntity() {
        return new InvoiceDetailEntity();
    }

    public static InvoiceLogisticsEntity nullInvoiceLogisticsEntity() {
        return new InvoiceLogisticsEntity();
    }

    public static InvoicePaymentEntity nullInvoicePaymentEntity() {
        return new InvoicePaymentEntity();
    }

    public static TaxpayerRouteEntity nullTaxpayerRouteEntity() {
        return new TaxpayerRouteEntity();
    }
    
    public static TaxcodeRouteEntity nullTaxcodeRouteEntity() {
    	return new TaxcodeRouteEntity();
    }
}
