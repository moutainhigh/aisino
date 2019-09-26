package com.aisino.domain.einvoice.repository.mybatis;

import com.aisino.domain.AbstractBaseRepository;
import com.aisino.domain.einvoice.entity.InvoiceBalance;
import com.aisino.domain.einvoice.repository.EInvoiceRoutingRepository;
import org.mybatis.spring.SqlSessionTemplate;

import java.util.Map;

import static com.aisino.domain.einvoice.EInvoiceMyBatisStatement.STMT_EINVOICE_UNIQUEID_GETCACHEBYTYPENAME;
import static com.aisino.domain.einvoice.EInvoiceMyBatisStatement.STMT_EINVOICE_UNIQUEID_UPDATECACHEBYTYPENAME;
import static com.google.common.collect.Maps.newHashMap;

/**
 * Created by Martin.Ou on 2014/9/3.
 *
 * @see com.aisino.domain.AbstractBaseRepository
 * @see com.aisino.domain.BaseRepository
 * @see com.aisino.domain.einvoice.repository.EInvoiceRepository
 * @see com.aisino.domain.einvoice.entity.InvoiceEntity
 *      EInvoiceRepository-路由分库接口-实现类
 */

@Deprecated
public final class EInvoiceRoutingRepositoryMyBatis extends AbstractBaseRepository<InvoiceBalance> implements EInvoiceRoutingRepository {

    public void setSqlSessionTemplateRouting(SqlSessionTemplate template) {
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
    public void insertTUnique(String cValue) {
        final Map<String, String> param = newHashMap();
        param.put("cValue", cValue);

        getSqlSession().insert("insertTUunique", param);
    }

}
