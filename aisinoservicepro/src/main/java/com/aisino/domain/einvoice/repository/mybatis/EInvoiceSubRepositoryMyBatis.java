package com.aisino.domain.einvoice.repository.mybatis;

import com.aisino.domain.einvoice.entity.*;
import com.aisino.domain.einvoice.repository.EInvoiceSubRepository;
import com.google.common.collect.Maps;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.support.SqlSessionDaoSupport;

import java.util.List;
import java.util.Map;

import static com.aisino.domain.einvoice.EInvoiceMyBatisStatement.*;
import static com.google.common.collect.Maps.newHashMap;
import static org.springframework.util.Assert.notNull;

/**
 * Created with IntelliJ IDEA.
 * User: Schiffer.huang
 * Date: 14-9-28
 * Time: 下午1:56
 * 生成电子发票获取数据-系列 Repository接口-实现
 */
public final class EInvoiceSubRepositoryMyBatis extends SqlSessionDaoSupport implements EInvoiceSubRepository {

    public void setTemplate(SqlSessionTemplate template) {
        this.setSqlSessionTemplate(template);
    }

    @Override
    public EShopInfo getEShopInfo(String eshopCode) {
        final Map<String, String> param = Maps.newHashMap();
        param.put("eshopCode", eshopCode);
        return getSqlSession().selectOne(STMT_EINVOICE_ESHOP_QUERY, eshopCode);
    }

    @Override
    public EShopInfo getEShopPlatformInfo(String eshopCode) {
        final Map<String, String> param = Maps.newHashMap();
        param.put("eshopCode", eshopCode);
        return getSqlSession().selectOne(STMT_EINVOICE_ESHOPDETAILS_QUERY, eshopCode);
    }

    @Override
    public TaxpayerEntity getTaxpayer(String taxpayerIdentifyNo) {
        final Map<String, String> param = newHashMap();
        param.put("taxpayerIdentifyNo", taxpayerIdentifyNo);

        return getSqlSession().selectOne(STMT_EINVOICE_TAXPAYER_GET, param);
    }

    @Override
    public TaxpayerEntity getTaxpayer(String taxpayerIdentifyNo, String platformCode) {
        final Map<String, String> param = newHashMap();
        param.put("taxpayerIdentifyNo", taxpayerIdentifyNo);
        param.put("platformCode", platformCode);

        return getSqlSession().selectOne(STMT_EINVOICE_TAXPAYER_GET_ALIAS, param);
    }

    @Override
    public Boolean verifyTaxpayerStatus(String taxpayerIdentifyNo, String taxpayerStatusCode) {
        final Map<String, String> param = newHashMap();
        param.put("taxpayerIdentifyNo", taxpayerIdentifyNo);
        param.put("taxpayerStatusCode", taxpayerStatusCode);

        return (Integer) getSqlSession().selectOne(STMT_EINVOICE_TAXPAYERSTATUS_COUNT, param) > 0;
    }


    @Override
    public Boolean insertInvoice(InvoiceHeaderEntity entity) {
        return getSqlSession().insert(STMT_EINVOICE_INVOICE_INSERT, entity) > 0;
    }

    @Override
    public Boolean insertInvoiceLog(InvoiceEntity entity) {
        return getSqlSession().insert(STMT_EINVOICE_INVOICELOG_INSERT, entity) > 0;
    }

    @Override
    public Boolean updateInvoiceLog(InvoiceEntity entity) {
        return getSqlSession().update(STMT_EINVOICE_INVOICELOG_UPDATE, entity) > 0;
    }

    @Override
    public Boolean verifyInvoiceLogByInvoiceId(Long invoiceId) {

        return (Integer) getSqlSession().selectOne(STMT_EINVOICE_INVOICELOG_COUNT, invoiceId) > 0;
    }

    @Override
    public Boolean insertInvoiceRed(InvoiceRedEntity entity) {
        return getSqlSession().insert(STMT_EINVOICE_INVOICERED_INSERT, entity) > 0;
    }

    @Override
    public Boolean insertInvoiceDetail(InvoiceDetailEntity entity) {
        return getSqlSession().insert(STMT_EINVOICE_INVOICEDETAIL_INSERT, entity) > 0;
    }

    @Override
    public Boolean insertInvoiceOrder(InvoiceOrderEntity entity) {
        return getSqlSession().insert(STMT_EINVOICE_INVOICEORDER_INSERT, entity) > 0;
    }

    @Override
    public Boolean insertInvoiceOrderDetail(InvoiceOrderDetailEntity entity) {
        return getSqlSession().insert(STMT_EINVOICE_INVOICEORDERDETAIL_INSERT, entity) > 0;
    }

    @Override
    public Boolean insertInvoicePayment(InvoicePaymentEntity entity) {
        return getSqlSession().insert(STMT_EINVOICE_INVOICEPAYMENT_INSERT, entity) > 0;
    }

    @Override
    public Boolean insertInvoiceLogistics(InvoiceLogisticsEntity entity) {
        return getSqlSession().insert(STMT_EINVOICE_INVOICELOGISTICS_INSERT, entity) > 0;
    }

    @Override
    public Boolean verifyInvoiceExistBlue(String oldInvoiceCode, String oldInvoiceNo) {

        final Map<String, String> param = newHashMap();
        param.put("oldInvoiceCode", oldInvoiceCode);
        param.put("oldInvoiceNo", oldInvoiceNo);

        return (Integer) getSqlSession().selectOne(STMT_EINVOICE_INVOICEBLUE_COUNT, param) > 0;
    }

    @Override
    public InvoicePDFInfoEntity getInvoicePdfInfo(String invoiceRequestSerialNo, String taxpayerIdentifyNo, String eshopCode) {
        final Map<String, String> param = newHashMap();
        param.put("invoiceRequestSerialNo", invoiceRequestSerialNo);
        param.put("taxpayerIdentifyNo", taxpayerIdentifyNo);
        param.put("eshopCode", eshopCode);

        return getSqlSession().selectOne(STMT_EINVOICE_PDFINFO_GET, param);
    }

    @Override
    public List<InvoiceKindInfoEntity> getInvoiceKindInfo(String taxpayerIdentifyNo) {
        final Map<String, String> param = newHashMap();
        param.put("taxpayerIdentifyNo", taxpayerIdentifyNo);

        return getSqlSession().selectList(STMT_EINVOICE_KINDINFO_FIND, param);
    }

    @Override
    public Boolean verifyInvoiceRedExistBlue(String oldInvoiceCode, String oldInvoiceNo, String taxpayerIdentifyNo, String billingType) {
        final Map<String, String> param = newHashMap();
        param.put("oldInvoiceCode", oldInvoiceCode);
        param.put("oldInvoiceNo", oldInvoiceNo);
        param.put("taxpayerIdentifyNo", taxpayerIdentifyNo);
        param.put("billingType", billingType);

        return (Integer) getSqlSession().selectOne(STMT_EINVOICE_INVOICERED_BLUE_COUNT, param) > 0;
    }

    @Override
    public InvoiceHeaderEntity getInvoiceAmountInfo(String oldInvoiceCode, String oldInvoiceNo, String taxpayerIdentifyNo, String billingType) {
        final Map<String, String> param = newHashMap();
        param.put("oldInvoiceCode", oldInvoiceCode);
        param.put("oldInvoiceNo", oldInvoiceNo);
        param.put("taxpayerIdentifyNo", taxpayerIdentifyNo);
        param.put("billingType", billingType);

        return getSqlSession().selectOne(STMT_EINVOICE_INVOICEAMOUNTINFO_GET, param);
    }
    
    @Override
    public InvoiceHeaderEntity getInvoiceAmountInfoForRed(String oldInvoiceCode, String oldInvoiceNo, String taxpayerIdentifyNo, String billingType) {
        final Map<String, String> param = newHashMap();
        param.put("oldInvoiceCode", oldInvoiceCode);
        param.put("oldInvoiceNo", oldInvoiceNo);
        param.put("taxpayerIdentifyNo", taxpayerIdentifyNo);
        param.put("billingType", billingType);

        return getSqlSession().selectOne(STMT_EINVOICE_INVOICEAMOUNTINFO_FOR_RED, param);
    }

    @Override
    public String getSignCAIdByTaxpayerIdentifyNo(String taxpayerIdentifyNo) {
        final Map<String, String> param = Maps.newHashMap();
        param.put("taxpayerIdentifyNo", taxpayerIdentifyNo);

        return getSqlSession().selectOne(STMT_GETSIGNCAID_BYTAXPAYERIDENTIFYNO, param);
    }

    @Override
    public Boolean queryTaxpayerInfoByTaxpayerIdentifyNo(final String taxpayerIdentifyNo) {
        notNull(taxpayerIdentifyNo, "taxpayerIdentifyNo can not null");

        return getSqlSession().selectList(STMT_EINVOICE_TAXPAYER_INFO_QUERY, taxpayerIdentifyNo).isEmpty();
    }

    @Override
    public String getInvoicePdfPath(String fiscalCode) {
        final Map<String, String> param = Maps.newHashMap();
        param.put("fiscalCode", fiscalCode);

        return getSqlSession().selectOne(STMT_EINVOICE_PDFPATH_GET, param);
    }

	@Override
	public List<EShopInfo> getEShopPlatformInfoByNsrbh(String nsrsbh) {
		final Map<String, String> param = newHashMap();
        param.put("nsrsbh", nsrsbh);

        return getSqlSession().selectList(STMT_EINVOICE_ESHOPDETAILS_NSRBH_QUERY, param);
	}

	@Override
	public TaxpayerEntity getTaxpayerByNsrbh(String nsrsbh) {
		final Map<String, String> param = Maps.newHashMap();
        param.put("nsrsbh", nsrsbh);

        return getSqlSession().selectOne(STMT_EINVOICE_ESHOPDETAILS_NSRBH_Get, param);
		
	}

}