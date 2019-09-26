package com.aisino.einvoice.dao;

import com.aisino.domain.einvoice.EInvoiceQueryCondition;
import com.aisino.domain.rabbit.entity.GeneratorInvoiceQueueEntity;
import com.aisino.domain.rabbit.entity.PushInvoiceQueueEntity;
import com.aisino.domain.rabbit.entity.SentToTaxQueueEntity;

import java.util.List;

/**
 * Created by Bourne.Lv on 2014/09/04.
 * <p/>
 * 处理端业务DAO接口
 */
public interface IInvUploadDao {

    /**
     * 获取受理待生成的电子发票
     * 状态为 INV_GETDATA_SUCCESS:1000
     *
     * @param queryCondition EInvoiceQueryCondition 发票查询条件封装实体
     * @return List<GeneratorInvoiceQueueEntity> GeneratorInvoiceQueueEntity集合
     */
    List<GeneratorInvoiceQueueEntity> queryEInvoiceGenerate(final EInvoiceQueryCondition queryCondition);

    /**
     * 获取上传到税局的电子发票信息
     * 状态为INV_CREATE_SUCCESS:2000
     *
     * @param queryCondition EInvoiceQueryCondition 发票查询条件封装实体
     * @return List<SentToEShopQueueEntity> SentToEShopQueueEntity集合
     */
    List<SentToTaxQueueEntity> queryTaxUpload(final EInvoiceQueryCondition queryCondition);

    /**
     * 获取受理待生成的电子发票
     * 推送状态为 FPSC_ZT=2
     *
     * @param queryCondition EInvoiceQueryCondition 发票查询条件封装实体
     * @return List<PushInvoiceQueueEntity> PushInvoiceQueueEntity集合
     */
    List<PushInvoiceQueueEntity> queryEInvoicePush(final EInvoiceQueryCondition queryCondition);

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
    List queryfpkj(int i);
    List queryfpkjmx(String id);
    List queryfpddmx(String id);
}
