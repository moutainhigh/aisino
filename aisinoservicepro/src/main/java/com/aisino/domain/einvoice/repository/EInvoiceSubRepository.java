package com.aisino.domain.einvoice.repository;

import com.aisino.domain.einvoice.entity.*;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Schiffer.huang
 * Date: 14-9-28
 * Time: 下午1:53
 * 生成电子发票获取数据-系列 Repository接口
 */
public interface EInvoiceSubRepository {

    /**
     * 获取 电商平台信息,通过 电商平台编码
     * @param eshopCode 电商平台编码与
     * @return EShopInfo  电商平台信息实体
     */
    EShopInfo getEShopInfo(String eshopCode);

    /***
     * 获取 电商平台丰富信息,通过 电商平台编码
     * @param eshopCode   电商平台编码与
     * @return EShopInfo  电商平台信息实体
     */
    EShopInfo getEShopPlatformInfo(String eshopCode);

    /**
     * 通过纳税人识别号 获取纳税人信息
     *
     * @param taxpayerIdentifyNo 纳税人识别号
     * @return TaxpayerEntity 获取纳税人信息实体(注:实体中eshopCode为电商平台编码)
     */
    TaxpayerEntity getTaxpayer(String taxpayerIdentifyNo);

    /**
     * 通过纳税人识别号与电商平台编码 获取纳税人信息
     *
     * @param taxpayerIdentifyNo 纳税人识别号
     * @param platformCode          (51)平台编码
     * @return TaxpayerEntity 获取纳税人信息实体 (注:实体中eshopCode为平台编码)
     */
    TaxpayerEntity getTaxpayer(String taxpayerIdentifyNo, String platformCode);

    /**
     * 　检查纳税人状态是否异常
     *
     * @param taxpayerIdentifyNo 纳税人识别号
     * @param taxpayerStatusCode 税人纳状态
     * @return Boolean  true:纳税人状态正常；false:纳税人状态异常
     */
    Boolean verifyTaxpayerStatus(String taxpayerIdentifyNo, String taxpayerStatusCode);

    /**
     * 插入FP_KJ表
     *
     * @param entity InvoiceHeaderEntity实例，发票头信息实体,必要属性id.
     * @return Boolean  true:操作成功；false:操作失败
     */
    Boolean insertInvoice(InvoiceHeaderEntity entity);

    /**
     * 插入FP_KJ_LOG表
     *
     * @param entity InvoiceEntity实例，发票信息实体,必要属性invoiceId,id.
     * @return Boolean  true:操作成功；false:操作失败
     */
    Boolean insertInvoiceLog(InvoiceEntity entity);

    /**
     * 修改 FP_KJ_LOG 表
     *
     * @param entity InvoiceEntity实例，发票信息实体，必要属性id.
     * @return Boolean  true:操作成功；false:操作失败
     */
    Boolean updateInvoiceLog(InvoiceEntity entity);

    /**
     * 通过Id判断 FP_KJ_LOG是否已有数据
     *
     * @param invoiceId 发票ID
     * @return Boolean  true:存在；false:不存在
     */
    Boolean verifyInvoiceLogByInvoiceId(Long invoiceId);

    /**
     * 插入fp_chfpsqd表
     *
     * @param entity InvoiceRedEntity实例，冲红发票申请单信息实体,必要属性invoiceId,Id.
     * @return Boolean  true:操作成功；false:操作失败
     */
    Boolean insertInvoiceRed(InvoiceRedEntity entity);

    /**
     * 插入fp_kjmx表
     *
     * @param entity InvoiceDetailEntity实例，发票开具明细信息实体,必要属性invoiceId,Id.
     * @return Boolean  true:操作成功；false:操作失败
     */
    Boolean insertInvoiceDetail(InvoiceDetailEntity entity);

    /**
     * 插入fp_ddxx表
     *
     * @param entity InvoiceOrder实例，发票订单信息实体,必要属性invoiceId,Id.
     * @return Boolean  true:操作成功；false:操作失败
     */
    Boolean insertInvoiceOrder(InvoiceOrderEntity entity);

    /**
     * 插入 fp_ddmxxx 表
     *
     * @param entity InvoiceOrderDetailEntity实例，发票订单详细信息实体,必要属性invoiceId,Id.
     * @return Boolean  true:操作成功；false:操作失败
     */
    Boolean insertInvoiceOrderDetail(InvoiceOrderDetailEntity entity);

    /**
     * 插入 fp_zfxx 表
     *
     * @param entity InvoicePaymentEntity实例，发票支付信息实体,必要属性invoiceId,Id.
     * @return Boolean  true:操作成功；false:操作失败
     */
    Boolean insertInvoicePayment(InvoicePaymentEntity entity);

    /**
     * 插入 fp_wlxx 表
     *
     * @param entity InvoiceLogisticsEntity实例，发票物流信息实体,必要属性invoiceId,Id.
     * @return Boolean  true:操作成功；false:操作失败
     */
    Boolean insertInvoiceLogistics(InvoiceLogisticsEntity entity);

    /**
     * 通过发票代码号码 检验 是否存在正确的蓝字发票
     *
     * @param oldInvoiceCode 原发票代码
     * @param oldInvoiceNo   原发票号码
     * @return Boolean  true:存在正确；false:不存在正确
     */
    Boolean verifyInvoiceExistBlue(String oldInvoiceCode, String oldInvoiceNo);

    /***
     * 通过查询条件 检验 是否存在正确的电子发票pdf文件信息
     * @param invoiceRequestSerialNo 发票请求唯一流水号
     * @param taxpayerIdentifyNo  税纳人识别号
     * @param eshopCode  电商平台编码
     * @return  InvoicePDFInfoEntity  发票PDF信息实体
     */
    InvoicePDFInfoEntity getInvoicePdfInfo(String invoiceRequestSerialNo, String taxpayerIdentifyNo, String eshopCode);

    /***
     * 通过纳税人识别号 获取 电子发票票种信息
     * @param taxpayerIdentifyNo 纳税人识别号
     * @return List<InvoiceKindInfoEntity> 电子发票票种信息集合
     */
    List<InvoiceKindInfoEntity> getInvoiceKindInfo(String taxpayerIdentifyNo);

    /**
     * 通过 发票代码号码查询 检验冲红发票 是否存在正确的蓝字发票
     *
     * @param oldInvoiceCode     原发票代码
     * @param oldInvoiceNo       原发票号码
     * @param taxpayerIdentifyNo 纳税人识别号
     * @param billingType        开票类型
     * @return Boolean  true:存在正确；false:不存在正确
     */
    Boolean verifyInvoiceRedExistBlue(String oldInvoiceCode, String oldInvoiceNo, String taxpayerIdentifyNo, String billingType);

    /**
     * 通过条件获取发票头信息实体实体信息,包含剩余可冲红金额、是否特殊冲红、开票合计金额．
     *
     * @param oldInvoiceCode     原发票代码
     * @param oldInvoiceNo       原发票号码
     * @param taxpayerIdentifyNo 纳税人识别号
     * @param billingType        开票类型
     * @return InvoiceHeaderEntity  发票头信息实体实体,存在null值
     */
    InvoiceHeaderEntity getInvoiceAmountInfo(String oldInvoiceCode, String oldInvoiceNo, String taxpayerIdentifyNo, String billingType);

    /**
     * 通过纳税人识别码获取签章ID
     *
     * @param taxpayerIdentifyNo 纳税人识别码
     * @return 有效的签章ID
     */
    String getSignCAIdByTaxpayerIdentifyNo(String taxpayerIdentifyNo);

    /**
     * 通过 纳税人识别号 查询 纳税人信息 是否存在该电商平台
     *
     * @param taxpayerIdentifyNo String 纳税人识别号
     * @return 是否存在该电商平台 true & false
     */
    Boolean queryTaxpayerInfoByTaxpayerIdentifyNo(final String taxpayerIdentifyNo);

    /***
     * 通过 税控码（防伪码）获取电子发票PDF文件保存路径
     * @param fiscalCode  税控码（防伪码）
     * @return 电子发票PDF文件保存路径
     */
    String getInvoicePdfPath(String fiscalCode);
    /**
     * 通过条件获取发票头信息实体实体信息,包含剩余可冲红金额、是否特殊冲红、开票合计金额．（开具红票使用）
     *
     * @param oldInvoiceCode     原发票代码
     * @param oldInvoiceNo       原发票号码
     * @param taxpayerIdentifyNo 纳税人识别号
     * @param billingType        开票类型
     * @return InvoiceHeaderEntity  发票头信息实体实体,存在null值
     */
    InvoiceHeaderEntity getInvoiceAmountInfoForRed(String oldInvoiceCode, String oldInvoiceNo, String taxpayerIdentifyNo, String billingType);
    /**
     * 通过纳税人识别号获取电商平台信息
     *
     * @param nsrsbh 纳税人识别号
     * @return List<EShopInfo> 获取电商平台List
     */
	List<EShopInfo> getEShopPlatformInfoByNsrbh(String nsrsbh);
	/**
     * 通过纳税人识别号获取电商企业信息
     *
     * @param nsrsbh 纳税人识别号
     * @return List<EShopInfo> 获取电商企业信息
     */
	TaxpayerEntity getTaxpayerByNsrbh(String nsrsbh);
    
}
