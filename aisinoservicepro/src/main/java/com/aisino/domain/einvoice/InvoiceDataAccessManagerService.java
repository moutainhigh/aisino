package com.aisino.domain.einvoice;

import java.util.List;
import java.util.Map;

import com.aisino.domain.BaseService;
import com.aisino.domain.einvoice.entity.CertificateEntity;
import com.aisino.domain.einvoice.entity.EShopInfo;
import com.aisino.domain.einvoice.entity.InvoiceBalance;
import com.aisino.domain.einvoice.entity.InvoiceDetailEntity;
import com.aisino.domain.einvoice.entity.InvoiceEntity;
import com.aisino.domain.einvoice.entity.TaxcodeRouteEntity;
import com.aisino.domain.einvoice.entity.TaxpayerRouteEntity;

/**
 * Created by Bourne.Lv on 2014/09/04.
 * <p/>
 * 发票相关业务数据库操作服务接口
 */
public interface InvoiceDataAccessManagerService extends BaseService {

    /**
     * 校验发票状态(根据发票ID和校验类型)
     * 
     * @param verifyType
     *            校验类别(例:校验可生成发票的1000状态)
     * @param invoiceId
     *            发票ID
     * @return 是否通过检验，True通过，False不通过
     */
    Boolean verifyInvoiceStatus(String verifyType, Long invoiceId);

    /**
     * 查询当前发票实体信息
     * 
     * @param invoiceId
     *            发票ID
     * @return 发票实体
     */
    InvoiceEntity getInvoiceEntity(Long invoiceId);

    /**
     * 将balance返回的发票信息信息(发票号码，发票代码，发票种类，开票日期)持久化到FP_KJ_LOG，FP_KJ表
     * 
     * @param invoiceEntity
     *            发票实体
     * @return 是否执行成功，True成功，False失败
     */
    Boolean saveInvoiceEntityBalanceInfo(InvoiceEntity invoiceEntity);

    /**
     * 保存发票错误信息，更新错误状态，自增发票操作次数
     * 
     * @param invoiceId
     *            发票ID
     * @param invoiceStatus
     *            发票错误状态
     * @param errorMsg
     *            发票业务错误信息
     * @return 是否执行成功，True成功，False失败
     */
    Boolean saveInvoiceErrorInfo(Long invoiceId, String invoiceStatus, String errorMsg);

    /**
     * 发票开具检验(验证签章ID等)
     * 
     * @param invoiceEntity
     *            发票交互实体
     * @return 是否验证成功，True成功，False失败
     */
    Boolean verifyIssueInvoice(InvoiceEntity invoiceEntity);

    /**
     * 获取电商平台信息(根据电商平台编码)
     * 
     * @param condition
     *            查询条件(需要包含电商平台编码)
     * @return 电商平台信息
     */
    EShopInfo queryEShopInfo(EInvoiceQueryCondition condition);

    /**
     * 根据纳税人Client信息 获取通讯CA密钥证书信息
     * 
     * @param condition
     *            查询条件需要包含"taxpayerIdentifyNo 纳税人识别号"、" platformCode 51平台编码"
     *            "registerNo 电商平台注册码"、" authCode 纳税人授权码（clinet）"
     * @return 证书信息实体
     */
    CertificateEntity getCertificateEntity(EInvoiceQueryCondition condition);

    /***
     * 获取通讯平台CA密钥证书信息
     * 
     * @return 证书信息实体(平台)
     */
    CertificateEntity getPlatformCertificateEntity();

    /**
     * 根据纳税人识别号 获取证书信息
     * 
     * @param taxpayerIdentifyNo
     *            纳税人识别号
     * @return 证书信息实体
     */
    CertificateEntity getCertificateEntity(String taxpayerIdentifyNo);

    /**
     * 获取发票开具明细信息 (根据发票ID)
     * 
     * @param condition
     *            查询条件(需要包含发票ID)
     * @return 发票明细信息 List<InvoiceDetailEntity>
     */
    List<InvoiceDetailEntity> queryInvoiceDetailInfo(EInvoiceQueryCondition condition);

    /**
     * 获取发票开具明细信息 (根据发票ID)
     * 
     * @param condition
     *            查询条件(需要包含发票ID)
     * @return 发票明细信息 List<InvoiceDetailEntity>
     */
    List<Map<String, String>> queryFjh(Map<String, String> map);

    String queryFpqqlsh();

    void updateFpqqlsh();

    /**
     * 获取发票票种信息 (根据纳税人识别号)
     * 
     * @param 纳税人识别号
     * @return 发票票种<InvoiceBalance>
     */
    InvoiceBalance queryInvoiceKindCodeInfo(EInvoiceQueryCondition condition);

    /**
     * 生成(开具)发票操作(包括订单信息,开具明细,物流信息,支付信息,发票存放位置等)
     * 
     * @param invoiceEntity
     *            发票交互实体(request_fpkjxx_fpjgxx)，包括开具信息和结果信息
     * @return 是否执行成功，True成功，False失败
     */
    Boolean generatorInvoice(InvoiceEntity invoiceEntity);

    /**
     * 获取纳税人路由信息(根据传入的查询条件)
     * 
     * @param condition
     *            查询条件(必须包含纳税人识别号)
     * @return 纳税人路由信息
     */
    TaxpayerRouteEntity queryTaxpayerRouteInfo(EInvoiceQueryCondition condition);
    
    /**
     * 根据纳税人识别号获取发票赋码的路由表
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
     * 更新发票推送状态(fp_kj_log表的fpkj_zt)
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

    /**
     * 根据防伪码获取PDF路径
     * 
     * @param fiscalCode
     *            防伪码
     * @return PDF路径
     */
    String getInvoicePdfPath(String fiscalCode);

    Boolean updateInvoiceBillingType(Long invoiceId, String string);
}
