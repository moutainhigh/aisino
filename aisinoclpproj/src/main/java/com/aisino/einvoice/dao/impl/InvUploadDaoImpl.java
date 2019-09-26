package com.aisino.einvoice.dao.impl;

import com.aisino.domain.AbstractBaseRepository;
import com.aisino.domain.einvoice.EInvoiceQueryCondition;
import com.aisino.domain.einvoice.entity.InvoiceBalance;
import com.aisino.domain.rabbit.entity.GeneratorInvoiceQueueEntity;
import com.aisino.domain.rabbit.entity.PushInvoiceQueueEntity;
import com.aisino.domain.rabbit.entity.SentToTaxQueueEntity;
import com.aisino.einvoice.dao.IInvUploadDao;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.aisino.einvoice.EInvoiceMyBatisStatement.*;
import static org.springframework.util.Assert.notNull;

/**
 * Created by Bourne.Lv on 2014/09/04.
 * <p/>
 * 处理端业务DAO接口实现类
 */
@Repository
public final class InvUploadDaoImpl extends AbstractBaseRepository<InvoiceBalance> implements IInvUploadDao {

    private static final String AMOUNT_REQUIRED = "amount required.";

    private static final String INVOICE_STATUS_REQUIRED = "invoiceStatus required.";

    private static final String INVOICE_PUSH_STATUS_REQUIRED = "invoicePushStatus required.";

    private static final String LIST_REQUIRED = "list required.";

    @Autowired
    public final void init(SqlSessionTemplate sessionTemplate) {
        this.setSqlSessionTemplate(sessionTemplate);
    }

    @Override
    public List<GeneratorInvoiceQueueEntity> queryEInvoiceGenerate(final EInvoiceQueryCondition queryCondition) {
        notNull(queryCondition.getAmount(), AMOUNT_REQUIRED);
        notNull(queryCondition.getInvoiceStatus(), INVOICE_STATUS_REQUIRED);

        return this.getSqlSession().selectList(STMT_FINDEINVOICEGENERATE, queryCondition);
    }

    @Override
    public List<SentToTaxQueueEntity> queryTaxUpload(final EInvoiceQueryCondition queryCondition) {
        notNull(queryCondition.getAmount(), AMOUNT_REQUIRED);
        notNull(queryCondition.getInvoiceStatus(), INVOICE_STATUS_REQUIRED);

        return this.getSqlSession().selectList(STMT_FINDTAXUPLOAD, queryCondition);
    }

    @Override
    public List<PushInvoiceQueueEntity> queryEInvoicePush(EInvoiceQueryCondition queryCondition) {
        notNull(queryCondition.getAmount(), AMOUNT_REQUIRED);
        notNull(queryCondition.getInvoicePushStatus(), INVOICE_PUSH_STATUS_REQUIRED);

        return this.getSqlSession().selectList(STMT_FINDEINVOICEPUSH, queryCondition);
    }

    @Override
    public Integer updateInvoiceStatus(final EInvoiceQueryCondition queryCondition) {
        notNull(queryCondition.getInvoiceIds(), LIST_REQUIRED);
        notNull(queryCondition.getInvoiceStatus(), INVOICE_STATUS_REQUIRED);

        return this.getSqlSession().update(STMT_UPDATEINVOICESTATUS, queryCondition);
    }

    @Override
    public Integer updateInvoicePushStatus(EInvoiceQueryCondition queryCondition) {
        notNull(queryCondition.getInvoiceIds(), LIST_REQUIRED);
//        notNull(queryCondition.getInvoicePushStatus(), INVOICE_PUSH_STATUS_REQUIRED);
        notNull(queryCondition.getInvoiceUploadStatus(), INVOICE_PUSH_STATUS_REQUIRED);//FWH-20171009-状态不能为空

        return this.getSqlSession().update(STMT_UPDATEINVOICEPUSHSTATUS, queryCondition);
    }

    @Override
    public List queryfpkj(int i) {
        return this.getSqlSession().selectList(QUERYFPKJ, i);
    }
    
    @Override
    public List queryfpkjmx(String id) {
        return this.getSqlSession().selectList(QUERYFPKJMX, id);
    }
    @Override
    public List queryfpddmx(String id) {
        return this.getSqlSession().selectList(QUERYFPDDMX, id);
    }

}
