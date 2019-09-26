package com.aisino.domain.einvoice.repository;

import com.aisino.domain.BaseRepository;
import com.aisino.domain.einvoice.entity.InvoiceBalance;

/**
 * Created by Martin.Ou on 2014/9/3.
 * EInvoiceRepository-路由分库接口
 *
 * @see com.aisino.domain.BaseRepository
 */
@Deprecated
public interface EInvoiceRoutingRepository extends BaseRepository<InvoiceBalance> {
    /**
     * 通过id类别名称获取当前数据
     *
     * @param uniqueIdTypeName id类别名称
     * @return id类别名称获取当前数据
     */
    Long obtainUniqueCacheIdByTypeName(String uniqueIdTypeName);

    /**
     * 通过id类别名称获取当前数据
     *
     * @param uniqueIdTypeName id类别名称
     * @param cacheSize        已经缓存的大小
     */
    void updateUniqueCacheIdByTypeName(String uniqueIdTypeName, Long cacheSize);

    /**
     * 临时使用，上线要删除。测试唯一性资源的获取的唯一性
     *
     * @param cValue
     */
    @Deprecated
    void insertTUnique(String cValue);

}
