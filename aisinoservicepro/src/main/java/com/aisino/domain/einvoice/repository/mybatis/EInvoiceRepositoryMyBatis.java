package com.aisino.domain.einvoice.repository.mybatis;

import static com.aisino.domain.einvoice.EInvoiceMyBatisStatement.QUERYFJH;
import static com.aisino.domain.einvoice.EInvoiceMyBatisStatement.QUERYFPQQLSH;
import static com.aisino.domain.einvoice.EInvoiceMyBatisStatement.STMT_EINVOICE_CERTIFICATE_INFO_GET;
import static com.aisino.domain.einvoice.EInvoiceMyBatisStatement.STMT_EINVOICE_CERTIFICATE_INFO_GETBYTAXPAYERIDENTIFYNO;
import static com.aisino.domain.einvoice.EInvoiceMyBatisStatement.STMT_EINVOICE_ESHOP_QUERY;
import static com.aisino.domain.einvoice.EInvoiceMyBatisStatement.STMT_EINVOICE_INVOICEDETAILENTITY_CODEANDNO_UPDATE;
import static com.aisino.domain.einvoice.EInvoiceMyBatisStatement.STMT_EINVOICE_INVOICEDETAIL_QUERY;
import static com.aisino.domain.einvoice.EInvoiceMyBatisStatement.STMT_EINVOICE_INVOICEENTITYBALANCEINFO_FPKJLOG_UPDATE;
import static com.aisino.domain.einvoice.EInvoiceMyBatisStatement.STMT_EINVOICE_INVOICEENTITYBALANCEINFO_FPKJ_UPDATE;
import static com.aisino.domain.einvoice.EInvoiceMyBatisStatement.STMT_EINVOICE_INVOICEENTITY_GET;
import static com.aisino.domain.einvoice.EInvoiceMyBatisStatement.STMT_EINVOICE_INVOICEERROR_UPDATE;
import static com.aisino.domain.einvoice.EInvoiceMyBatisStatement.STMT_EINVOICE_INVOICEEXTENDINFOFORGENERATEINVOICE_UPDATE;
import static com.aisino.domain.einvoice.EInvoiceMyBatisStatement.STMT_EINVOICE_INVOICEINFOFORGENERATEINVOICE_UPDATE;
import static com.aisino.domain.einvoice.EInvoiceMyBatisStatement.STMT_EINVOICE_INVOICEKINDCODE;
import static com.aisino.domain.einvoice.EInvoiceMyBatisStatement.STMT_EINVOICE_INVOICELOGISTICSENTITY_CODEANDNO_UPDATE;
import static com.aisino.domain.einvoice.EInvoiceMyBatisStatement.STMT_EINVOICE_INVOICEORDERENTITY_CODEANDNO_UPDATE;
import static com.aisino.domain.einvoice.EInvoiceMyBatisStatement.STMT_EINVOICE_INVOICEPAYMENTENTITY_CODEANDNO_UPDATE;
import static com.aisino.domain.einvoice.EInvoiceMyBatisStatement.STMT_EINVOICE_INVOICESTATUS_QUERY;
import static com.aisino.domain.einvoice.EInvoiceMyBatisStatement.STMT_EINVOICE_INVOICESTATUS_UPDATE_FOR_ISSUED;
import static com.aisino.domain.einvoice.EInvoiceMyBatisStatement.STMT_EINVOICE_PDFPATH_GET;
import static com.aisino.domain.einvoice.EInvoiceMyBatisStatement.STMT_EINVOICE_PDFTEMPLATE_GET;
import static com.aisino.domain.einvoice.EInvoiceMyBatisStatement.STMT_EINVOICE_PUSH_STATE_UPDATE;
import static com.aisino.domain.einvoice.EInvoiceMyBatisStatement.STMT_EINVOICE_REMAININGCREDITNOTEAMOUNT_UPDATE;
import static com.aisino.domain.einvoice.EInvoiceMyBatisStatement.STMT_EINVOICE_REMAININGINVOICENOTEAMOUNT_UPDATE;
import static com.aisino.domain.einvoice.EInvoiceMyBatisStatement.STMT_EINVOICE_SIGNATURE_QUERY;
import static com.aisino.domain.einvoice.EInvoiceMyBatisStatement.STMT_EINVOICE_TAXCODE_STATE_UPDATE;
import static com.aisino.domain.einvoice.EInvoiceMyBatisStatement.STMT_EINVOICE_TAXPAYER_ROUTE_DELETE;
import static com.aisino.domain.einvoice.EInvoiceMyBatisStatement.STMT_EINVOICE_TAXPAYER_ROUTE_ENABLE;
import static com.aisino.domain.einvoice.EInvoiceMyBatisStatement.STMT_EINVOICE_TAXPAYER_ROUTE_GET;
import static com.aisino.domain.einvoice.EInvoiceMyBatisStatement.STMT_EINVOICE_TAXCODE_ROUTE_GET;
import static com.aisino.domain.einvoice.EInvoiceMyBatisStatement.STMT_EINVOICE_TAXPAYER_ROUTE_SAVE;
import static com.aisino.domain.einvoice.EInvoiceMyBatisStatement.STMT_EINVOICE_UNIQUEID_GETCACHEBYTYPENAME;
import static com.aisino.domain.einvoice.EInvoiceMyBatisStatement.STMT_EINVOICE_UNIQUEID_UPDATECACHEBYTYPENAME;
import static com.aisino.domain.einvoice.EInvoiceMyBatisStatement.STMT_GETSIGNCAID_BYTAXPAYERIDENTIFYNO;
import static com.aisino.domain.einvoice.EInvoiceMyBatisStatement.UPDATEFPQQLSH;
import static com.google.common.collect.Maps.newHashMap;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;

import com.aisino.domain.AbstractBaseRepository;
import com.aisino.domain.einvoice.EInvoiceQueryCondition;
import com.aisino.domain.einvoice.entity.CertificateEntity;
import com.aisino.domain.einvoice.entity.EShopInfo;
import com.aisino.domain.einvoice.entity.InvoiceBalance;
import com.aisino.domain.einvoice.entity.InvoiceDetailEntity;
import com.aisino.domain.einvoice.entity.InvoiceEntity;
import com.aisino.domain.einvoice.entity.InvoiceLogisticsEntity;
import com.aisino.domain.einvoice.entity.InvoiceOrderEntity;
import com.aisino.domain.einvoice.entity.InvoicePaymentEntity;
import com.aisino.domain.einvoice.entity.TaxcodeRouteEntity;
import com.aisino.domain.einvoice.entity.TaxpayerRouteEntity;
import com.aisino.domain.einvoice.repository.EInvoiceRepository;
import com.aisino.itext.dao.IItextDao;
import com.aisino.protocol.bean.KBFPMBXX;
import com.google.common.collect.Maps;

/**
 * Created by Martin.Ou on 2014/9/3.
 * 
 * @see com.aisino.domain.AbstractBaseRepository
 * @see com.aisino.domain.BaseRepository
 * @see com.aisino.domain.einvoice.repository.EInvoiceRepository
 * @see InvoiceEntity
 * @see CertificateEntity
 */
public final class EInvoiceRepositoryMyBatis extends AbstractBaseRepository<InvoiceBalance> implements EInvoiceRepository, IItextDao {

    public void setTemplate(SqlSessionTemplate template) {
        this.setSqlSessionTemplate(template);
    }

    @Override
    public Long obtainUniqueCacheIdByTypeName(String uniqueIdTypeName) {
        final Map<String, String> param = newHashMap();
        param.put("typeName", uniqueIdTypeName);

        return getSqlSession().selectOne(STMT_EINVOICE_UNIQUEID_GETCACHEBYTYPENAME, param);
    }

    @Override
    public void updateUniqueCacheIdByTypeName(String uniqueIdTypeName, Long cacheSize) {
        final Map<String, Object> param = newHashMap();
        param.put("typeName", uniqueIdTypeName);
        param.put("cacheSize", cacheSize);

        this.update(STMT_EINVOICE_UNIQUEID_UPDATECACHEBYTYPENAME, param);
    }

    @Override
    public String queryInvoiceStatusById(Long invoiceId) {
        final Map<String, String> param = newHashMap();
        param.put("invoiceId", String.valueOf(invoiceId));

        return getSqlSession().selectOne(STMT_EINVOICE_INVOICESTATUS_QUERY, param);
    }

    @Override
    public InvoiceEntity getInvoiceEntityById(Long invoiceId) {
        final Map<String, String> param = newHashMap();
        param.put("invoiceId", String.valueOf(invoiceId));

        return getSqlSession().selectOne(STMT_EINVOICE_INVOICEENTITY_GET, param);
    }

    @Override
    public Boolean updateInvoiceEntityBalanceInfo(InvoiceEntity invoiceEntity) {
        final Boolean first = this.update(STMT_EINVOICE_INVOICEENTITYBALANCEINFO_FPKJLOG_UPDATE, invoiceEntity) > 0;
        Boolean second = Boolean.FALSE;
        if (first) {
            second = this.update(STMT_EINVOICE_INVOICEENTITYBALANCEINFO_FPKJ_UPDATE, invoiceEntity) > 0;
        }
        return first && second;
    }

    @Override
    public Boolean updateInvoiceEntityForIssued(InvoiceEntity invoiceEntity) {

        return this.update(STMT_EINVOICE_INVOICESTATUS_UPDATE_FOR_ISSUED, invoiceEntity) > 0;
    }

    @Override
    public Boolean updateInvoiceErrorInfo(InvoiceEntity invoiceEntity) {

        return this.update(STMT_EINVOICE_INVOICEERROR_UPDATE, invoiceEntity) > 0;
    }

    @Override
    public String querySignatureByInvoiceId(Long invoiceId) {
        final Map<String, String> param = newHashMap();
        param.put("invoiceId", String.valueOf(invoiceId));

        return getSqlSession().selectOne(STMT_EINVOICE_SIGNATURE_QUERY, param);
    }

    @Override
    public EShopInfo queryEShopInfo(EInvoiceQueryCondition condition) {

        return getSqlSession().selectOne(STMT_EINVOICE_ESHOP_QUERY, condition);
    }

    @Override
    public List<InvoiceDetailEntity> queryInvoiceDetailInfo(EInvoiceQueryCondition condition) {

        return getSqlSession().selectList(STMT_EINVOICE_INVOICEDETAIL_QUERY, condition);
    }

    @Override
    public List<Map<String, String>> queryFjh(Map<String, String> map) {

        return getSqlSession().selectList(QUERYFJH, map);
    }

    @Override
    public String queryFpqqlsh() {
        return getSqlSession().selectOne(QUERYFPQQLSH, "");
    }

    @Override
    public void updateFpqqlsh() {
        this.update(UPDATEFPQQLSH, "");
    }

    @Override
    public InvoiceBalance queryInvoiceKindCodeInfo(EInvoiceQueryCondition condition) {

        return getSqlSession().selectOne(STMT_EINVOICE_INVOICEKINDCODE, condition);
    }

    @Override
    public Boolean modifyRemainingInvoiceNoteAmount(InvoiceEntity invoiceEntity) {

        return this.update(STMT_EINVOICE_REMAININGINVOICENOTEAMOUNT_UPDATE, invoiceEntity) > 0;
    }

    @Override
    public Boolean modifyRemainingCreditNoteAmount(InvoiceEntity invoiceEntity) {

        return this.update(STMT_EINVOICE_REMAININGCREDITNOTEAMOUNT_UPDATE, invoiceEntity) > 0;
    }

    @Override
    public Boolean updateInvoiceCodeAndInvoiceNo(InvoiceOrderEntity invoiceOrderEntity) {

        return this.update(STMT_EINVOICE_INVOICEORDERENTITY_CODEANDNO_UPDATE, invoiceOrderEntity) > 0;
    }

    @Override
    public Boolean updateInvoiceCodeAndInvoiceNo(InvoiceDetailEntity invoiceDetailEntity) {

        return this.update(STMT_EINVOICE_INVOICEDETAILENTITY_CODEANDNO_UPDATE, invoiceDetailEntity) > 0;
    }

    @Override
    public Boolean updateInvoiceCodeAndInvoiceNo(InvoiceLogisticsEntity invoiceLogisticsEntity) {

        return this.update(STMT_EINVOICE_INVOICELOGISTICSENTITY_CODEANDNO_UPDATE, invoiceLogisticsEntity) > 0;
    }

    @Override
    public Boolean updateInvoiceCodeAndInvoiceNo(InvoicePaymentEntity invoicePaymentEntity) {

        return this.update(STMT_EINVOICE_INVOICEPAYMENTENTITY_CODEANDNO_UPDATE, invoicePaymentEntity) > 0;
    }

    @Override
    public Boolean updateInvoiceInfoForGenerateInvoice(InvoiceEntity invoiceEntity) {

        return this.update(STMT_EINVOICE_INVOICEINFOFORGENERATEINVOICE_UPDATE, invoiceEntity) > 0;
    }

    @Override
    public Boolean updateInvoiceExtendInfoForGenerateInvoice(InvoiceEntity invoiceEntity) {

        return this.update(STMT_EINVOICE_INVOICEEXTENDINFOFORGENERATEINVOICE_UPDATE, invoiceEntity) > 0;
    }

    @Override
    public CertificateEntity getCertificateEntity(EInvoiceQueryCondition condition) {

        return getSqlSession().selectOne(STMT_EINVOICE_CERTIFICATE_INFO_GET, condition);
    }

    @Override
    public CertificateEntity getCertificateEntity(String taxpayerIdentifyNo) {
        return getSqlSession().selectOne(STMT_EINVOICE_CERTIFICATE_INFO_GETBYTAXPAYERIDENTIFYNO, taxpayerIdentifyNo);
    }

    @Override
    public void insertTUnique(String cValue) {
        final Map<String, String> param = newHashMap();
        param.put("cValue", cValue);

        getSqlSession().insert("insertTUunique", param);
    }

    @Override
    public void insertTLost(String taxpayerIdentifyNo, String invoiceNo) {
        final Map<String, String> param = newHashMap();
        param.put("taxpayerIdentifyNo", taxpayerIdentifyNo);
        param.put("invoiceNo", invoiceNo);

        getSqlSession().insert("insertTLost", param);
    }

    @Override
    public void updateTLost(String invoiceId, String invoiceNo) {
        final Map<String, String> param = newHashMap();
        param.put("invoiceId", invoiceId);
        param.put("invoiceNo", invoiceNo);

        getSqlSession().insert("updateTLost", param);
    }

    @Override
    public void updateTLost(String invoiceNo) {
        final Map<String, String> param = newHashMap();
        param.put("invoiceNo", invoiceNo);

        getSqlSession().insert("updateTLostFlag", param);
    }

    @Override
    public TaxpayerRouteEntity queryTaxpayerRouteInfo(EInvoiceQueryCondition condition) {
        return getSqlSession().selectOne(STMT_EINVOICE_TAXPAYER_ROUTE_GET, condition);
    }
    
    @Override
    public TaxcodeRouteEntity queryTaxcodeRouteInfo(String nsrsbh) {
    	return getSqlSession().selectOne(STMT_EINVOICE_TAXCODE_ROUTE_GET, nsrsbh);
    }

    @Override
    public Boolean saveTaxpayerRoute(TaxpayerRouteEntity taxpayerRouteEntity) {
        return getSqlSession().insert(STMT_EINVOICE_TAXPAYER_ROUTE_SAVE, taxpayerRouteEntity) > 0;
    }

    @Override
    public Boolean deleteTaxpayerRoute(Long id) {
        return getSqlSession().delete(STMT_EINVOICE_TAXPAYER_ROUTE_DELETE, id) > 0;
    }

    @Override
    public Boolean enableTaxpayerRouteAvailable(TaxpayerRouteEntity taxpayerRouteEntity) {
        return getSqlSession().update(STMT_EINVOICE_TAXPAYER_ROUTE_ENABLE, taxpayerRouteEntity) > 0;
    }

    @Override
    public Boolean updateInvoicePushState(Long invoiceId, String pushState) {
        final Map<String, Object> param = newHashMap();
        param.put("invoiceId", invoiceId);
        param.put("pushState", pushState);

        return getSqlSession().update(STMT_EINVOICE_PUSH_STATE_UPDATE, param) > 0;
    }

    @Override
    public Boolean updateInvoiceTaxCodeState(Long invoiceId, String taxcodeState) {
        final Map<String, Object> param = newHashMap();
        param.put("invoiceId", invoiceId);
        param.put("taxcodeState", taxcodeState);

        return getSqlSession().update(STMT_EINVOICE_TAXCODE_STATE_UPDATE, param) > 0;
    }
    
    @Override
    public Boolean updateInvoiceBillingType(Long invoiceId, String billingType) {
        final Map<String, Object> param = newHashMap();
        param.put("invoiceId", invoiceId);
        param.put("billingType", billingType);

        return  getSqlSession().update("updateInvoiceBillingType", param) > 0;
    }

    @Override
    public String getInvoicePdfPath(String fiscalCode) {
        final Map<String, String> param = Maps.newHashMap();
        param.put("fiscalCode", fiscalCode);

        return getSqlSession().selectOne(STMT_EINVOICE_PDFPATH_GET, param);
    }

    /**
     * 本方法实现EIECPdf工程的IItextDao接口 获取发票模板
     * 
     * @param departCode
     *            纳税人识别号
     * @param len
     * @return 返回值必须为Map不可变化
     */
    @Override
    public Object getTemplate(String departCode, String len) {
        final Map<String, String> param = newHashMap();
        param.put("departCode", departCode);
        param.put("len", len);

        final KBFPMBXX invoiceTemplate = getSqlSession().selectOne(STMT_EINVOICE_PDFTEMPLATE_GET, param);

        if (invoiceTemplate != null) {
            param.clear();
            param.put("fpfile", invoiceTemplate.getFPFILE());
            param.put("twmleft", invoiceTemplate.getTWMLEFT());
            param.put("twmright", invoiceTemplate.getTWMRIGHT());
            param.put("qzleft", invoiceTemplate.getQZLEFT());
            param.put("qztop", invoiceTemplate.getQZTOP());
            param.put("qzright", invoiceTemplate.getQZRIGHT());
            param.put("qzbottom", invoiceTemplate.getQZBOTTOM());
            param.put("qzpageindex", invoiceTemplate.getQZPAGEINDEX());
            //TODO 京东云获取code值 2017-06-22 FWH
            param.put("code", invoiceTemplate.getCODE());
            return param;
        } else {
            return null;
        }
    }

	@Override
	public String getSignCAIdByTaxpayerIdentifyNo(String taxpayerIdentifyNo) {
		   final Map<String, String> param = Maps.newHashMap();
	        param.put("taxpayerIdentifyNo", taxpayerIdentifyNo);
	        return getSqlSession().selectOne(STMT_GETSIGNCAID_BYTAXPAYERIDENTIFYNO, param);
	}
}
