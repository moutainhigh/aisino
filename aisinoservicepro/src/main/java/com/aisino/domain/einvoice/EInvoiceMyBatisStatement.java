package com.aisino.domain.einvoice;

/**
 * Created by Martin.Ou on 2014/9/3.
 * <p/>
 * 电子发票相关业务的数据操作的Mybatis 配置文件定义
 */
public final class EInvoiceMyBatisStatement {

    private EInvoiceMyBatisStatement() {
    }

    /**
     * 通过纳税人识别号获取发票结存信息
     */
    public static final String STMT_EINVOICE_INVOICEBALANCE_GET = "getInvoiceBalanceInfo";

    /**
     * 删除纳税人发票结存信息，根据纳税人结存信息
     */
    public static final String STMT_EINVOICE_INVOICEBALANCE_DEL = "delInvoiceBalanceInfo";

    /**
     * 修改纳税人发票结存信息，根据纳税人结存信息
     *
     * @see com.aisino.domain.einvoice.entity.InvoiceBalance
     */
    public static final String STMT_EINVOICE_INVOICEBALANCE_UPDATE = "updateInvoiceBalanceInfo";

    /**
     * 根据序列号类别获取指定序列号的当前值
     * 要做这个查询，在相应的T_UNIQUEID#TYPENAME 必须存在
     */
    public static final String STMT_EINVOICE_UNIQUEID_GETCACHEBYTYPENAME = "obtainUniqueCacheIdByTypeName";

    /**
     * 根据序列号类别和已缓存的多少，更新数据库制定的序列号当前值
     */
    public static final String STMT_EINVOICE_UNIQUEID_UPDATECACHEBYTYPENAME = "updateUniqueCacheIdByTypeName";

    /**
     * 根据发票ID，查询发票状态
     */
    public static final String STMT_EINVOICE_INVOICESTATUS_QUERY = "queryInvoiceStatusById";

    /**
     * 根据发票ID，查询发票信息
     *
     * @see com.aisino.domain.einvoice.entity.InvoiceEntity
     */
    public static final String STMT_EINVOICE_INVOICEENTITY_GET = "getInvoiceEntityById";

    /**
     * 更新发票下发过程中的发票信息
     *
     * @see com.aisino.domain.einvoice.entity.InvoiceEntity
     */
    public static final String STMT_EINVOICE_INVOICESTATUS_UPDATE_FOR_ISSUED = "updateInvoiceEntityForIssued";

    /**
     * 更新发票业务错误信息
     *
     * @see com.aisino.domain.einvoice.entity.InvoiceEntity
     */
    public static final String STMT_EINVOICE_INVOICEERROR_UPDATE = "updateInvoiceErrorInfo";

    /**
     * 将结存信息(发票号码，发票代码，发票种类，开票日期)持久化到FP_KJ表
     *
     * @see com.aisino.domain.einvoice.entity.InvoiceEntity
     */
    public static final String STMT_EINVOICE_INVOICEENTITYBALANCEINFO_FPKJ_UPDATE = "updateInvoiceEntityBalanceInfoForFPKJ";

    /**
     * 将结存信息(发票号码，发票代码，开票日期)持久化到FP_KJ_LOG表
     *
     * @see com.aisino.domain.einvoice.entity.InvoiceEntity
     */
    public static final String STMT_EINVOICE_INVOICEENTITYBALANCEINFO_FPKJLOG_UPDATE = "updateInvoiceEntityBalanceInfoForFPKJLOG";

    /**
     * 根据发票ID查询签章ID
     */
    public static final String STMT_EINVOICE_SIGNATURE_QUERY = "querySignatureByInvoiceId";

    /**
     * 获取电商平台信息
     *
     * @see com.aisino.domain.einvoice.entity.EShopInfo
     */
    public static final String STMT_EINVOICE_ESHOP_QUERY = "queryEShopInfoByCondition";

    /**
     * 获取电商平台丰富信息
     *
     * @see com.aisino.domain.einvoice.entity.EShopInfo
     */
    public static final String STMT_EINVOICE_ESHOPDETAILS_QUERY = "queryEShopDetailsInfoByCondition";

    /**
     * 获取发票开具明细信息
     *
     * @see com.aisino.domain.einvoice.entity.InvoiceDetailEntity
     */
    public static final String STMT_EINVOICE_INVOICEDETAIL_QUERY = "queryInvoiceDetailEntityByCondition";
    public static final String QUERYFJH = "queryfjh";
    public static final String QUERYFPQQLSH = "queryfpqqlsh";
    public static final String UPDATEFPQQLSH = "updatefpqqlsh";

    /**
     * 获取发票票种信息
     *
     * @see 
     */
    public static final String STMT_EINVOICE_INVOICEKINDCODE = "queryInvoiceKindCodeByCondition";

    /**
     * 更新原蓝票的"冲红标志"以及"剩余可冲红金额"(归零)
     *
     * @see com.aisino.domain.einvoice.entity.InvoiceEntity
     */
    public static final String STMT_EINVOICE_REMAININGINVOICENOTEAMOUNT_UPDATE = "updateRemainingInvoiceNoteAmount";

    /**
     * 更新蓝票的"剩余可冲红金额"(归零)
     *
     * @see com.aisino.domain.einvoice.entity.InvoiceEntity
     */
    public static final String STMT_EINVOICE_REMAININGCREDITNOTEAMOUNT_UPDATE = "updateRemainingCreditNoteAmount";

    /**
     * 更新发票订单信息的发票代码，发票号码
     *
     * @see com.aisino.domain.einvoice.entity.InvoiceOrderEntity
     */
    public static final String STMT_EINVOICE_INVOICEORDERENTITY_CODEANDNO_UPDATE = "updateCodeAndNoForInvoiceOrder";

    /**
     * 更新发票开具信息的发票代码，发票号码
     *
     * @see com.aisino.domain.einvoice.entity.InvoiceDetailEntity
     */
    public static final String STMT_EINVOICE_INVOICEDETAILENTITY_CODEANDNO_UPDATE = "updateCodeAndNoForInvoiceDetail";

    /**
     * 更新发票物流信息的发票代码，发票号码
     *
     * @see com.aisino.domain.einvoice.entity.InvoiceLogisticsEntity
     */
    public static final String STMT_EINVOICE_INVOICELOGISTICSENTITY_CODEANDNO_UPDATE = "updateCodeAndNoForInvoiceLogistics";

    /**
     * 更新发票支付信息的发票代码，发票号码
     *
     * @see com.aisino.domain.einvoice.entity.InvoicePaymentEntity
     */
    public static final String STMT_EINVOICE_INVOICEPAYMENTENTITY_CODEANDNO_UPDATE = "updateCodeAndNoForInvoicePayment";

    /**
     * 更新开票过程中的FP_KJ表的相关字段
     *
     * @see com.aisino.domain.einvoice.entity.InvoiceEntity
     */
    public static final String STMT_EINVOICE_INVOICEINFOFORGENERATEINVOICE_UPDATE = "updateInvoiceInfoForGenerateInvoice";

    /**
     * 更新开票过程中的FP_KJ_LOG表的相关字段
     *
     * @see com.aisino.domain.einvoice.entity.InvoiceEntity
     */
    public static final String STMT_EINVOICE_INVOICEEXTENDINFOFORGENERATEINVOICE_UPDATE = "updateInvoiceExtendInfoForGenerateInvoice";

    /**
     * 获得发票模板
     */
    public static final String STMT_EINVOICE_PDFTEMPLATE_GET = "getInvoiceTemplate";

    /**
     * 通过纳税人识别码获取签章ID
     */
    public static final String STMT_GETSIGNCAID_BYTAXPAYERIDENTIFYNO = "getSignCAIdByTaxpayerIdentifyNo";

    /*发票开具log 系列*/
    public static final String STMT_EINVOICE_INVOICE_INSERT = "insertInvoice";
    public static final String STMT_EINVOICE_INVOICELOG_INSERT = "insertInvoiceLog";
    public static final String STMT_EINVOICE_INVOICELOG_UPDATE = "updateInvoiceLog";
    public static final String STMT_EINVOICE_INVOICELOG_COUNT = "getCountInvoiceLog";

    /*insert 发票冲红,明细(项目)信息,订单信息,订单信息详细,支付信息*/
    public static final String STMT_EINVOICE_INVOICERED_INSERT = "insertInvoiceRed";
    public static final String STMT_EINVOICE_INVOICEDETAIL_INSERT = "insertInvoiceDetail";
    public static final String STMT_EINVOICE_INVOICEORDER_INSERT = "insertInvoiceOrder";
    public static final String STMT_EINVOICE_INVOICEORDERDETAIL_INSERT = "insertInvoiceOrderDetail";
    public static final String STMT_EINVOICE_INVOICEPAYMENT_INSERT = "insertInvoicePayment";
    public static final String STMT_EINVOICE_INVOICELOGISTICS_INSERT = "insertInvoiceLogistics";

    /**
     * 通过纳税人识别号(条件组)获取纳税人信息,判断纳税人状态*
     */
    public static final String STMT_EINVOICE_TAXPAYER_GET = "getInvoiceTaxpayer";
    public static final String STMT_EINVOICE_TAXPAYER_GET_ALIAS = "getInvoiceTaxpayerAlias";
    public static final String STMT_EINVOICE_TAXPAYERSTATUS_COUNT = "getCountTaxpayerStatus";

    /*检验正常开票,冲红票操作是否存在对应的蓝票*/
    public static final String STMT_EINVOICE_INVOICEBLUE_COUNT = "getCountInvoiceBlue";
    public static final String STMT_EINVOICE_INVOICERED_BLUE_COUNT = "getCountInvoiceRedBlue";


    /*通过条件获取发票头信息实体实体信息,包含剩余可冲红金额、是否特殊冲红、开票合计金额．*/
    public static final String STMT_EINVOICE_INVOICEAMOUNTINFO_GET = "getInvoiceAmountInfo";
    
    /*通过条件获取发票头信息实体实体信息,包含剩余可冲红金额、是否特殊冲红、开票合计金额．*/
    public static final String STMT_EINVOICE_INVOICEAMOUNTINFO_FOR_RED = "getInvoiceAmountInfoForRed";

    /*获取电子发票是否存在pdf文件信息*/
    public static final String STMT_EINVOICE_PDFINFO_GET = "getInvoicePdfInfo";

    /*获取电子发票票种信息*/
    public static final String STMT_EINVOICE_KINDINFO_FIND = "findInvoiceKindInfo";

    /*获取电子发票PDF文件保存路径*/
    public static final String STMT_EINVOICE_PDFPATH_GET = "getInvoicePdfPath";
    
    /**
     * 通过 纳税人识别号 查询 纳税人信息 是否存在该电商平台
     */
    public static final String STMT_EINVOICE_TAXPAYER_INFO_QUERY = "queryTaxpayerInfoByTaxpayerIdentifyNo";

    /**
     * 根据纳税人识别号与电商平台编码 获取证书信息
     *
     * @see com.aisino.domain.einvoice.entity.CertificateEntity
     */
    public static final String STMT_EINVOICE_CERTIFICATE_INFO_GET = "getCertificateEntity";

    /**
     * 根据纳税人识别号 获取证书信息
     *
     * @see com.aisino.domain.einvoice.entity.CertificateEntity
     */
    public static final String STMT_EINVOICE_CERTIFICATE_INFO_GETBYTAXPAYERIDENTIFYNO = "getCertificateEntityByTaxpayerIdentifyNo";

    /**
     * 根据纳税人识别号与是否启用推送功能 获取纳税人路由信息
     *
     * @see com.aisino.domain.einvoice.entity.TaxpayerRouteEntity
     */
    public static final String STMT_EINVOICE_TAXPAYER_ROUTE_GET = "getTaxpayerRouteEntity";
    /**
     * 根据纳税人识别号与是否启用推送功能 获取纳税人路由信息
     *
     * @see com.aisino.domain.einvoice.entity.TaxpayerRouteEntity
     */
    public static final String STMT_EINVOICE_TAXCODE_ROUTE_GET = "getTaxcodeRouteEntity";

    /**
     * 新增纳税人路由信息
     *
     * @see com.aisino.domain.einvoice.entity.InvoiceEntity
     */
    public static final String STMT_EINVOICE_TAXPAYER_ROUTE_SAVE = "saveTaxpayerRouteEntity";

    /**
     * 删除纳税人里有信息
     */
    public static final String STMT_EINVOICE_TAXPAYER_ROUTE_DELETE = "deleteTaxpayerRouteEntity";

    /**
     * 启用/停用纳税人路由信息
     */
    public static final String STMT_EINVOICE_TAXPAYER_ROUTE_ENABLE = "enableTaxpayerRouteEntity";

    /**
     * 更新发票推送状态
     */
    public static final String STMT_EINVOICE_PUSH_STATE_UPDATE = "updateInvoicePushState";
    
    /**
     * 更新发票赋码状态
     */
    public static final String STMT_EINVOICE_TAXCODE_STATE_UPDATE = "updateInvoiceTaxCodeState";
    /**
     * 更新发票赋码状态
     */
    public static final String STMT_EINVOICE_ESHOPDETAILS_NSRBH_QUERY = "queryEShopDetailsInfoByNsrbh";
    /**
     * 根据纳税人编号获取纳税人信息
     */
    public static final String STMT_EINVOICE_ESHOPDETAILS_NSRBH_Get = "getInvoiceTaxpayerByNsrbh";
}
