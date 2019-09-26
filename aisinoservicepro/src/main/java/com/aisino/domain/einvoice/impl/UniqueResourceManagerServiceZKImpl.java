package com.aisino.domain.einvoice.impl;

import com.aisino.domain.AbstractBaseService;
import com.aisino.domain.SystemConfig;
import com.aisino.domain.einvoice.UniqueResourceManagerService;
import com.aisino.domain.einvoice.entity.InvoiceBalance;
import com.aisino.domain.einvoice.repository.EInvoiceRepository;
import com.aisino.domain.einvoice.utils.NumberUtil;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.CloseableUtils;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import javax.annotation.PreDestroy;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

import static com.aisino.domain.constantenum.BusinessIdTypeEnum.PLATFORM_CODE;
import static com.aisino.domain.einvoice.EInvoiceMyBatisStatement.*;
import static com.aisino.domain.einvoice.EInvoiceNullObjects.nullInvoiceBalance;
import static com.google.common.base.MoreObjects.firstNonNull;
import static com.google.common.base.Strings.padStart;
import static org.joda.time.DateTime.now;
import static org.springframework.util.Assert.notNull;

/**
 * Created by Martin.Ou on 2014/9/10.
 * <p/>
 * 利用Zookeeper，组建分布式锁，以协调线程的单一访问
 */
public class UniqueResourceManagerServiceZKImpl extends AbstractBaseService implements UniqueResourceManagerService, InitializingBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(UniqueResourceManagerServiceZKImpl.class);

    private String zookeeperConnectionString;
    private static final Long MAX_UNIQUEIDCASE_SIZE = 1L;
    private static final Integer PLATFORM_CODE_SIZE = 7;
    private static final String PLATFORM_CODE_PREFIX = "P";
    private CuratorFramework client;

    private String nsrJCLockGrp;

    private String uniqueIdLockGrpPrefix;

    private EInvoiceRepository eInvoiceRepository;
    
    private static Queue<Long> FPKJ_ID = new LinkedList<Long>();
	private static Queue<Long> FPKJ_LOG_ID = new LinkedList<Long>();
	private static Queue<Long> FPKJMX_ID = new LinkedList<Long>();
	private static Queue<Long> FPDDXX_ID = new LinkedList<Long>();
	private static Queue<Long> FPCHFPSQD_ID = new LinkedList<Long>();
	private static Map<String, Queue<Long>> idMap = new HashMap<String, Queue<Long>>();
    static {
		idMap.put("FPKJ_ID", FPKJ_ID);
		idMap.put("FPKJ_LOG_ID", FPKJ_LOG_ID);
		idMap.put("FPKJMX_ID", FPKJMX_ID);
		idMap.put("FPDDXX_ID", FPDDXX_ID);
		idMap.put("FPCHFPSQD_ID", FPCHFPSQD_ID);
	}
    
    public void setZookeeperConnectionString(String zookeeperConnectionString) {
        this.zookeeperConnectionString = zookeeperConnectionString;
    }

    public void setNsrJCLockGrp(String nsrJCLockGrp) {
        this.nsrJCLockGrp = nsrJCLockGrp;
    }

    public void setUniqueIdLockGrpPrefix(String uniqueIdLockGrpPrefix) {
        this.uniqueIdLockGrpPrefix = uniqueIdLockGrpPrefix;
    }

    public void seteInvoiceRepository(EInvoiceRepository eInvoiceRepository) {
        this.eInvoiceRepository = eInvoiceRepository;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        final RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        client = CuratorFrameworkFactory.newClient(zookeeperConnectionString, retryPolicy);
        client.start();

        LOGGER.info("start client connection!");
    }

    @PreDestroy
    public void shutResource() {
        if (client != null) {
            LOGGER.info("shutdown client connection!");

            CloseableUtils.closeQuietly(client);
        } else {
            LOGGER.info("shutdown client connection!client is null!");
        }
    }

    @Override
    public InvoiceBalance obtainInvoiceBalance(String taxpayerIdentifyNo, String taxOrgCode) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("ZK模式获取结存...");
        }
        final InterProcessMutex lock = new InterProcessMutex(client, nsrJCLockGrp);
        InvoiceBalance invoiceBalance = new InvoiceBalance();

        try {
            if (lock.acquire(100, TimeUnit.SECONDS)) {
                try {
                    invoiceBalance.setTaxpayerIdentifyNo(taxpayerIdentifyNo);
                    invoiceBalance = eInvoiceRepository.get(STMT_EINVOICE_INVOICEBALANCE_GET, invoiceBalance);

                    final DateTime begin42 = now();

                    // 判断该端结存是否还剩最后一张，如果是最后一张直接删除结存否则修改结存记录信息
                    if (SystemConfig.invoiceCopy.equals(invoiceBalance.getInvoiceCopies())) {
                        // 删除结存信息
                        eInvoiceRepository.delete(STMT_EINVOICE_INVOICEBALANCE_DEL, invoiceBalance);

                        final Long millSeconds42 = new Duration(begin42, now()).getMillis();

                        if (LOGGER.isDebugEnabled()) {
                            LOGGER.debug("纳税人{}删除结存信息用时{}毫秒......", taxpayerIdentifyNo, millSeconds42);
                        }

                    } else {
                        // 修改结存信息
                        eInvoiceRepository.update(STMT_EINVOICE_INVOICEBALANCE_UPDATE, invoiceBalance);

                        final Long millSeconds43 = new Duration(begin42, now()).getMillis();

                        if (LOGGER.isDebugEnabled()) {
                            LOGGER.debug("纳税人{}更新结存信息用时{}毫秒......", taxpayerIdentifyNo, millSeconds43);
                        }

                    }

                    //用于验证票号唯一
                    eInvoiceRepository.insertTUnique(invoiceBalance.getInvoiceNo());
                } finally {
                    lock.release();
                }
            }
        } catch (Exception e) {
            LOGGER.error("ZK模式获取结存失败:{}", e);
        }

        return invoiceBalance;
    }

    @Override
    public Long obtainBusinessId(String businessIdType) {
        final InterProcessMutex uniqueIdLock = new InterProcessMutex(client, uniqueIdLockGrpPrefix + businessIdType);
        Long queryResult = 0L;
        try {
            if (uniqueIdLock.acquire(120, TimeUnit.SECONDS)) {
                try {
                    queryResult = eInvoiceRepository.obtainUniqueCacheIdByTypeName(businessIdType);
                    eInvoiceRepository.updateUniqueCacheIdByTypeName(businessIdType, MAX_UNIQUEIDCASE_SIZE);

                    eInvoiceRepository.insertTUnique(queryResult + businessIdType);
                } finally {
                    uniqueIdLock.release();
                }
            }
        } catch (Exception e) {
            LOGGER.error("获取BusinessId失败:{}", e);
        }

        return queryResult;
    }

    @Override
    public Long[] obtainBusinessId(String businessIdType, Integer cacheSize) {
        if (cacheSize < 1) {
            return new Long[0];
        }

        final InterProcessMutex uniqueIdLock = new InterProcessMutex(client, uniqueIdLockGrpPrefix + businessIdType);
        Long queryResult = 0L;
        Long[] idArray = new Long[cacheSize];
        try {
            if (uniqueIdLock.acquire(120, TimeUnit.SECONDS)) {
                try {
                    queryResult = eInvoiceRepository.obtainUniqueCacheIdByTypeName(businessIdType);

                    for (Integer index = 0; index < cacheSize; index++) {
                        idArray[index] = queryResult + index;
                    }

                    eInvoiceRepository.updateUniqueCacheIdByTypeName(businessIdType, Long.valueOf(cacheSize));
                } finally {
                    uniqueIdLock.release();
                }
            }
        } catch (Exception e) {
            LOGGER.error("获取BusinessId失败:{}", e);
        }

        return idArray;
    }

    @Override
    public Boolean verifyInvoiceBalance(String taxpayerIdentifyNo) {

        notNull(taxpayerIdentifyNo, "taxpayerIdentifyNo required.");

        final InvoiceBalance invoiceBalance = firstNonNull(eInvoiceRepository.get(STMT_EINVOICE_INVOICEBALANCE_GET, taxpayerIdentifyNo), nullInvoiceBalance());

        return !invoiceBalance.isNullObject();
    }

    @Override
    public String obtainPlatformCode() {
        final InterProcessMutex uniqueIdLock = new InterProcessMutex(client, uniqueIdLockGrpPrefix + PLATFORM_CODE.name());
        Long queryResult = 0L;
        try {
            if (uniqueIdLock.acquire(120, TimeUnit.SECONDS)) {
                try {
                    queryResult = eInvoiceRepository.obtainUniqueCacheIdByTypeName(PLATFORM_CODE.name());
                    eInvoiceRepository.updateUniqueCacheIdByTypeName(PLATFORM_CODE.name(), MAX_UNIQUEIDCASE_SIZE);

                    eInvoiceRepository.insertTUnique(queryResult + PLATFORM_CODE.name());
                } finally {
                    uniqueIdLock.release();
                }
            }
        } catch (Exception e) {
            LOGGER.error("获取BusinessId失败:{}", e);
        }

        return PLATFORM_CODE_PREFIX + padStart(String.valueOf(queryResult), PLATFORM_CODE_SIZE, '0');
    }

    @Override
    public String obtainVirtualRegisterCode() {
        return NumberUtil.obtainRandomNum(10);
    }

    @Override
    public String obtainPlatformAuthCode() {
        return NumberUtil.obtainRandomNum(10);
    }

	@Override
	public Long obtainBusinessIdFromQueue(String businessIdType) {
		final InterProcessMutex uniqueIdLock = new InterProcessMutex(client,uniqueIdLockGrpPrefix);
		LOGGER.info("uniqueIdLock对象{}", uniqueIdLock);
		Long queryResult = 0L;
		Long returnVal = null;

		try {
			if (uniqueIdLock.acquire(2, TimeUnit.SECONDS)) {
				try {
					if (idMap.get(businessIdType).size() < 200) {
						LOGGER.info("id小于200个");
						long start = System.currentTimeMillis();
						for (String key : idMap.keySet()) {
							queryResult = eInvoiceRepository.obtainUniqueCacheIdByTypeName(key);
							long needAddNum = 0;
							if ("FPKJMX_ID".equals(key)) { // 等于 发票开具明细ID
								needAddNum = SystemConfig.getIdMxaNum * 5 - idMap.get(key).size();
								LOGGER.info(key + "发票明细缓存id补充 {}个！", needAddNum);
							} else {
								needAddNum = SystemConfig.getIdMxaNum - idMap.get(key).size();
								LOGGER.info(key + "发票明细缓存id补充 {}个！", needAddNum);
							}
							long end = queryResult + needAddNum;
							LOGGER.info("缓存id起始{}，缓存id结尾{}", queryResult, end);
							for (long i = queryResult; i < end; i++) {
								idMap.get(key).add(i);
							}
							eInvoiceRepository.updateUniqueCacheIdByTypeName(key, needAddNum);
						}
						long end = System.currentTimeMillis();
						LOGGER.debug("--线程（ID："+ Thread.currentThread().getId()+ "缓存"+SystemConfig.getIdMxaNum+"个ID到缓存中(明细行个数*5)" + (end - start) + "毫秒--\n");
					}
					returnVal = idMap.get(businessIdType).poll();
					if (returnVal == null) {
						LOGGER.error("没有可用id或没有匹配的businessIdType:"+ businessIdType);
					}
				} finally {
					uniqueIdLock.release();
				}
			}
		} catch (Exception e) {
			LOGGER.error("获取BusinessId失败:{}", e);
		}

		return returnVal;
	}
}
