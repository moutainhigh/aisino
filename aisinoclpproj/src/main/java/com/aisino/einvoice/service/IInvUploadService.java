package com.aisino.einvoice.service;

import com.aisino.common.util.EShopCertificateBytesInfo;
import com.aisino.domain.einvoice.EInvoiceQueryCondition;
import com.aisino.domain.einvoice.entity.InvoiceHeaderEntity;
import com.aisino.domain.rabbit.entity.GeneratorInvoiceQueueEntity;
import com.aisino.domain.rabbit.entity.PushInvoiceQueueEntity;
import com.aisino.domain.rabbit.entity.SentToTaxQueueEntity;

import java.io.IOException;
import java.util.List;

/**
 * Created by Bourne.Lv on 2014/09/04.
 * <p/>
 * 处理端业务Service接口
 */
public interface IInvUploadService {

    /**
     * 发票赋码
     *
     * @param invoiceHeaderEntity 发票头实体
     * @throws Exception
     */
    Boolean taxcodeInvoice(final InvoiceHeaderEntity invoiceHeaderEntity) throws Exception;
    /**
     * 发票赋码
     *
     * @param invoiceHeaderEntity 发票头实体
     * @throws Exception
     */
    Boolean zctaxcodeInvoice(final InvoiceHeaderEntity invoiceHeaderEntity) throws Exception;

    /**
     * 生产发票开具信息和pdf文件
     *
     * @param invoiceHeaderEntity 发票头实体
     * @throws Exception
     */
    Boolean generatorInvoice(final InvoiceHeaderEntity invoiceHeaderEntity) throws Exception;

    /**
     * 发票推送
     *
     * @param invoiceHeaderEntity 发票头实体
     * @param taxpayerUrl         企业WS地址
     * @param certificate         CA证书
     * @throws Exception
     */
    Boolean pushInvoice(final InvoiceHeaderEntity invoiceHeaderEntity, final PushInvoiceQueueEntity pushInvoiceQueueEntity, final EShopCertificateBytesInfo certificate) throws Exception;

    /**
     * 获取受理待生成的电子发票
     * 状态为 INV_GETDATA_SUCCESS:1000
     *
     * @param queryCondition EInvoiceQueryCondition 发票查询条件封装实体
     * @return List<GeneratorInvoiceQueueEntity> GeneratorInvoiceQueueEntity集合
     */
    List<GeneratorInvoiceQueueEntity> queryEInvoiceGenerate(final EInvoiceQueryCondition queryCondition);

    /**
     * 获取受理待生成的电子发票
     * 推送状态为 FPSC_ZT=2
     *
     * @param queryCondition EInvoiceQueryCondition 发票查询条件封装实体
     * @return List<PushInvoiceQueueEntity> PushInvoiceQueueEntity集合
     */
    List<PushInvoiceQueueEntity> queryEInvoicePush(final EInvoiceQueryCondition queryCondition);

    /**
     * 获取待上传到税局的电子发票信息
     * 状态为INV_CREATE_SUCCESS:2000
     *
     * @param queryCondition EInvoiceQueryCondition 发票查询条件封装实体
     * @return List<SentToEShopQueueEntity> SentToEShopQueueEntity集合
     */
    List<SentToTaxQueueEntity> queryTaxUpload(final EInvoiceQueryCondition queryCondition);

    /**
     * 批量更新发票状态
     *
     * @param queryCondition EInvoiceQueryCondition 发票查询条件封装实体
     * @return 对数据进行操作数目
     */
    Integer updateInvoiceStatus(final EInvoiceQueryCondition queryCondition);

    /**
     * 批量更新发票推送状态
     *
     * @param queryCondition EInvoiceQueryCondition 发票查询条件封装实体
     * @return 对数据进行操作数目
     */
    Integer updateInvoicePushStatus(final EInvoiceQueryCondition queryCondition);

    /**
     * 根据税控码获取发票pdf Byte[]
     *
     * @param fiscalCode
     * @return byte[]  pdf Byte[]
     */
    byte[] getPdfBytes(String fiscalCode);

    /**
     * 根据纳税人识别码 获取平台CA证书信息
     *
     * @param taxpayerIdentifyNo 纳税人识别码
     * @return EShopCertificateBytesInfo 证书字节(byte)信息
     * @throws IOException 当执行IO操作证书异常时throw
     */
    EShopCertificateBytesInfo obtainEShopCAInfo(String taxpayerIdentifyNo) throws IOException;
    
    List queryfpkj(int i);
    List queryfpkjmx(String id);
    List queryfpddmx(String id);
}
