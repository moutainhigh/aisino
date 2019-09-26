package com.aisino.domain.einvoice.repository;

import java.util.List;
import java.util.Map;

import com.aisino.domain.BaseRepository;
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

/**
 * Created by Martin.Ou on 2014/9/3.
 * 
 * @see com.aisino.domain.BaseRepository
 */
public interface EInvoiceRepository extends BaseRepository<InvoiceBalance> {

    /**
     * 通过id类别名称获取当前数据
     * 
     * @param uniqueIdTypeName
     *            id类别名称
     * @return id类别名称获取当前数据
     */
    Long obtainUniqueCacheIdByTypeName(String uniqueIdTypeName);

    /**
     * 通过id类别名称获取当前数据
     * 
     * @param uniqueIdTypeName
     *            id类别名称
     * @param cacheSize
     *            已经缓存的大小
     */
    void updateUniqueCacheIdByTypeName(String uniqueIdTypeName, Long cacheSize);

    /**
     * 根据发票开具ID，获取指定发票的状态信息
     * 
     * @param invoiceId
     *            发票ID
     * @return 当前状态信息
     */
    String queryInvoiceStatusById(Long invoiceId);

    /**
     * 根据发票开具ID，获取发票信息
     * 
     * @param invoiceId
     *            发票ID
     * @return 发票信息
     */
    InvoiceEntity getInvoiceEntityById(Long invoiceId);

    /**
     * 将balance返回的发票信息信息(发票号码，发票代码，发票种类，开票日期)持久化到FP_KJ_LOG，FP_KJ表
     * 
     * @param invoiceEntity
     *            发票实体
     * @return 是否执行成功，True成功，False失败
     */
    Boolean updateInvoiceEntityBalanceInfo(InvoiceEntity invoiceEntity);

    /**
     * 更新发票下发过程中的发票信息
     * 
     * @param invoiceEntity
     *            发票实体
     * @return 是否执行成功，True成功，False失败
     */
    Boolean updateInvoiceEntityForIssued(InvoiceEntity invoiceEntity);

    /**
     * 更新发票业务错误信息
     * 
     * @param invoiceEntity
     *            发票实体
     * @return 是否执行成功，True成功，False失败
     */
    Boolean updateInvoiceErrorInfo(InvoiceEntity invoiceEntity);

    /**
     * 根据发票ID查询签章ID
     * 
     * @param invoiceId
     *            发票ID
     * @return 签章ID
     */
    String querySignatureByInvoiceId(Long invoiceId);

    /**
     * 获取电商平台信息(根据电商平台编码)
     * 
     * @param condition
     *            查询条件(需要包含电商平台编码)
     * @return 电商平台信息
     */
    EShopInfo queryEShopInfo(EInvoiceQueryCondition condition);

    /**
     * 获取发票开具明细信息 (根据发票ID)
     * 
     * @param condition
     *            查询条件(需要包含发票ID)
     * @return 发票明细信息 List<InvoiceDetailEntity>
     */
    List<InvoiceDetailEntity> queryInvoiceDetailInfo(EInvoiceQueryCondition condition);

    List<Map<String, String>> queryFjh(Map<String, String> map);

    String queryFpqqlsh();

    void updateFpqqlsh();

    /**
     * 获取发票票种信息 (根据发票纳税人识别号)
     * 
     * @param
     * @return 发票票种信息
     */
    InvoiceBalance queryInvoiceKindCodeInfo(EInvoiceQueryCondition condition);

    /**
     * 更新原蓝票的"冲红标志"以及"剩余可冲红金额"(归零)
     * 
     * @param invoiceEntity
     *            发票(红票)实体
     * @return 是否执行成功，True成功，False失败
     */
    Boolean modifyRemainingInvoiceNoteAmount(InvoiceEntity invoiceEntity);

    /**
     * 更新蓝票的"剩余可冲红金额"(归零)
     * 
     * @param invoiceEntity
     *            发票(蓝票)实体
     * @return 是否执行成功，True成功，False失败
     */
    Boolean modifyRemainingCreditNoteAmount(InvoiceEntity invoiceEntity);

    /**
     * 更新发票订单信息的发票代码，发票号码
     * 
     * @param invoiceOrderEntity
     *            发票订单实体
     * @return 是否执行成功，True成功，False失败
     */
    Boolean updateInvoiceCodeAndInvoiceNo(InvoiceOrderEntity invoiceOrderEntity);

    /**
     * 更新发票开具明细信息的发票代码，发票号码
     * 
     * @param invoiceDetailEntity
     *            发票开具明细实体
     * @return 是否执行成功，True成功，False失败
     */
    Boolean updateInvoiceCodeAndInvoiceNo(InvoiceDetailEntity invoiceDetailEntity);

    /**
     * 更新发票物流信息的发票代码，发票号码
     * 
     * @param invoiceLogisticsEntity
     *            发票物流信息实体
     * @return 是否执行成功，True成功，False失败
     */
    Boolean updateInvoiceCodeAndInvoiceNo(InvoiceLogisticsEntity invoiceLogisticsEntity);

    /**
     * 更新发票支付信息的发票代码，发票号码
     * 
     * @param invoicePaymentEntity
     *            发票支付信息实体
     * @return 是否执行成功，True成功，False失败
     */
    Boolean updateInvoiceCodeAndInvoiceNo(InvoicePaymentEntity invoicePaymentEntity);

    /**
     * 更新开票过程中的FP_KJ表的相关字段
     * 
     * @param invoiceEntity
     *            发票信息实体
     * @return 是否执行成功，True成功，False失败
     */
    Boolean updateInvoiceInfoForGenerateInvoice(InvoiceEntity invoiceEntity);

    /**
     * 更新开票过程中的FP_KJ_LOG表的相关字段
     * 
     * @param invoiceEntity
     *            发票信息实体
     * @return 是否执行成功，True成功，False失败
     */
    Boolean updateInvoiceExtendInfoForGenerateInvoice(InvoiceEntity invoiceEntity);

    /**
     * 获取证书信息
     * 
     * @param condition
     *            查询条件需要包含"taxpayerIdentifyNo 纳税人识别号"、" platformCode 51平台编码"
     *            "registerNo 电商平台注册码"、" authCode 纳税人授权码（clinet）"
     * @return 证书信息实体
     */
    CertificateEntity getCertificateEntity(EInvoiceQueryCondition condition);

    /**
     * 根据纳税人识别号 获取证书信息
     * 
     * @param taxpayerIdentifyNo
     *            纳税人识别号
     * @return 证书信息实体
     */
    CertificateEntity getCertificateEntity(String taxpayerIdentifyNo);

    /**
     * 插入“结存领取记录表”，用于查找丢票
     * 
     * @param taxpayerIdentifyNo
     *            税纳人识别号
     * @param invoiceNo
     *            发票号码
     */
    void insertTLost(String taxpayerIdentifyNo, String invoiceNo);

    /**
     * 更新“结存领取记录表”，用于查找丢票
     * 
     * @param invoiceId
     *            发票ID
     * @param invoiceNo
     *            发票号码
     */
    void updateTLost(String invoiceId, String invoiceNo);

    /**
     * 更新“结存领取记录表”，用于查找丢票
     * 
     * @param invoiceNo
     *            发票号码
     */
    void updateTLost(String invoiceNo);

    /**
     * 获取纳税人路由信息(根据传入的查询条件)
     * 
     * @param condition
     *            查询条件(必须包含纳税人识别号)
     * @return 纳税人路由信息
     */
    TaxpayerRouteEntity queryTaxpayerRouteInfo(EInvoiceQueryCondition condition);
    
    /**
     * 根据纳税人识别号，获取发票赋码路由表信息
     * @param condition
     * @return
     */
    TaxcodeRouteEntity queryTaxcodeRouteInfo(String nsrsbh);

    /**
     * 新增纳税人路由信息
     * 
     * @param taxpayerRouteEntity
     *            纳税人路由信息实体
     * @return 是否执行成功，True成功，False失败
     */
    Boolean saveTaxpayerRoute(TaxpayerRouteEntity taxpayerRouteEntity);

    /**
     * 删除纳税人路由信息
     * 
     * @param id
     *            Long
     * @return 是否执行成功，True成功，False失败
     */
    Boolean deleteTaxpayerRoute(Long id);

    /**
     * 启用/停用 纳税人路由推送功能
     * 
     * @param taxpayerRouteEntity
     *            纳税人路由信息实体 包含: id 序号,available 是否启用推送功能
     * @return 是否执行成功，True成功，False失败
     */
    Boolean enableTaxpayerRouteAvailable(TaxpayerRouteEntity taxpayerRouteEntity);

    /**
     * 更新发票推送状态(fp_kj_log表的fpsc_zt)
     * 
     * @param invoiceId
     *            发票ID
     * @param pushState
     *            推送状态(0未推送 1已成功推送 2推送失败)
     * @return
     */
    Boolean updateInvoicePushState(Long invoiceId, String pushState);

    /**
     * 更新发票赋码状态(fp_kj_log表的fpkj_zt)
     * 
     * @param invoiceId
     *            发票ID
     * @param taxcodeState
     *            赋码状态(2100是成功，2101是失败可重试，2102是失败不可重试)
     * @return
     */
    Boolean updateInvoiceTaxCodeState(Long invoiceId, String taxcodeState);

    /***
     * 通过 税控码（防伪码）获取电子发票PDF文件保存路径
     * 
     * @param fiscalCode
     *            税控码（防伪码）
     * @return 电子发票PDF文件保存路径
     */
    String getInvoicePdfPath(String fiscalCode);

    /**
     * 临时使用，上线要删除。测试唯一性资源的获取的唯一性
     * 
     * @param cValue
     */
    @Deprecated
    void insertTUnique(String cValue);

    Boolean updateInvoiceBillingType(Long invoiceId, String billingType);
}
