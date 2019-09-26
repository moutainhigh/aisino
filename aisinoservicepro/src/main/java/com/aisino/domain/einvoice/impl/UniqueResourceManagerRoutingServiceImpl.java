package com.aisino.domain.einvoice.impl;

import com.aisino.domain.AbstractBaseService;
import com.aisino.domain.einvoice.UniqueResourceManagerRoutingService;
import com.aisino.domain.einvoice.entity.InvoiceBalance;
import com.aisino.domain.einvoice.repository.EInvoiceRoutingRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PreDestroy;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

import static com.aisino.domain.einvoice.EInvoiceMyBatisStatement.STMT_EINVOICE_INVOICEBALANCE_GET;
import static com.aisino.domain.einvoice.EInvoiceNullObjects.nullInvoiceBalance;
import static com.google.common.base.MoreObjects.firstNonNull;
import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.util.Assert.notNull;

/**
 * Created by Martin.Ou on 2014/10/9.
 * 获取唯一资源服务接口-带路由分库信息接口-实现类
 */
@Deprecated
@Transactional(propagation = Propagation.REQUIRED)
public final class UniqueResourceManagerRoutingServiceImpl extends AbstractBaseService implements UniqueResourceManagerRoutingService, InitializingBean {
    private static final Logger LOGGER = getLogger(UniqueResourceManagerRoutingServiceImpl.class);

    private static final Long MAX_UNIQUEIDCASE_SIZE = 1L;


    private static final String UNIQUELOCK = "";
    private static final String BATCHLOCK = "";
    private final ReentrantLock uniqueIdLock = new ReentrantLock();

    private Map<String, EInvoiceRoutingRepository> uniqueResourceRoutingRepositoryMap;

    public void setUniqueResourceRoutingRepositoryMap(Map<String, EInvoiceRoutingRepository> uniqueResourceRoutingRepositoryMap) {
        this.uniqueResourceRoutingRepositoryMap = uniqueResourceRoutingRepositoryMap;
    }

    private EInvoiceRoutingRepository getRepository(final String taxRouting) {
        return uniqueResourceRoutingRepositoryMap.get(taxRouting);
    }


    @Override
    public void afterPropertiesSet() throws Exception {

        LOGGER.info("start client connection!");
    }

    @PreDestroy
    public void shutResource() {
        //释放已持有资源
    }


    @Override
    public Long obtainBusinessId(String businessIdType, String taxRouting) {
        Long queryResult = 0L;
        synchronized (UNIQUELOCK) {
            try {
                uniqueIdLock.lock();
                final EInvoiceRoutingRepository repository = getRepository(taxRouting);

                queryResult = repository.obtainUniqueCacheIdByTypeName(businessIdType);
                repository.updateUniqueCacheIdByTypeName(businessIdType, MAX_UNIQUEIDCASE_SIZE);

                repository.insertTUnique(queryResult + businessIdType);
            } catch (Exception e) {
                LOGGER.error("exception:{}", e.fillInStackTrace());
            } finally {
                uniqueIdLock.unlock();
            }

            return queryResult;
        }
    }

    @Override
    public Long[] obtainBusinessId(String businessIdType, Integer cacheSize, String taxRouting) {
        if (cacheSize < 1) {
            return new Long[0];
        }

        synchronized (BATCHLOCK) {
            uniqueIdLock.lock();
            Long queryResult = 0L;
            Long[] idArray = new Long[cacheSize];
            try {
                final EInvoiceRoutingRepository repository = getRepository(taxRouting);

                queryResult = repository.obtainUniqueCacheIdByTypeName(businessIdType);

                for (Integer index = 0; index < cacheSize; index++) {
                    idArray[index] = queryResult + index;

                    repository.insertTUnique(idArray[index] + businessIdType);
                }

                repository.updateUniqueCacheIdByTypeName(businessIdType, Long.valueOf(cacheSize));
            } catch (Exception e) {
                LOGGER.error("exception:{}", e.fillInStackTrace());
            } finally {
                uniqueIdLock.unlock();
            }

            return idArray;
        }
    }

    @Override
    public Boolean verifyInvoiceBalance(String taxpayerIdentifyNo, String taxRouting) {

        notNull(taxpayerIdentifyNo, "taxpayerIdentifyNo required.");

        final InvoiceBalance invoiceBalance = firstNonNull(getRepository(taxRouting).get(STMT_EINVOICE_INVOICEBALANCE_GET, taxpayerIdentifyNo), nullInvoiceBalance());

        return !invoiceBalance.isNullObject();
    }
}
