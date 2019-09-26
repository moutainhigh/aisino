package com.aisino.domain.einvoice.impl;

import com.aisino.domain.AbstractBaseService;
import com.aisino.domain.SystemConfig;
import com.aisino.domain.einvoice.UniqueResourceManagerService;
import com.aisino.domain.einvoice.entity.InvoiceBalance;
import com.aisino.domain.einvoice.repository.EInvoiceRepository;
import com.aisino.domain.einvoice.utils.NumberUtil;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.slf4j.Logger;
import org.springframework.beans.factory.InitializingBean;

import javax.annotation.PreDestroy;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.locks.ReentrantLock;

import static com.aisino.domain.constantenum.BusinessIdTypeEnum.PLATFORM_CODE;
import static com.aisino.domain.einvoice.EInvoiceMyBatisStatement.*;
import static com.aisino.domain.einvoice.EInvoiceNullObjects.nullInvoiceBalance;
import static com.google.common.base.MoreObjects.firstNonNull;
import static com.google.common.base.Strings.padStart;
import static org.joda.time.DateTime.now;
import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.util.Assert.notNull;

/**
 * Created by Martin.Ou on 2014/10/9.
 */
public class UniqueResourceManagerServiceImpl extends AbstractBaseService implements UniqueResourceManagerService, InitializingBean {
    private static final Logger LOGGER = getLogger(UniqueResourceManagerServiceImpl.class);

    private static final Long MAX_UNIQUEIDCASE_SIZE = 1L;
    private static final Integer PLATFORM_CODE_SIZE = 7;
    private static final String PLATFORM_CODE_PREFIX = "P";

    private EInvoiceRepository eInvoiceRepository;

    private final ReentrantLock lock = new ReentrantLock();
    private static final String SINGLELOCK = "";
    private static final String UNIQUELOCK = "";
    private static final String BATCHLOCK = "";
    private static final String UNIQUELOCKQUEUE = "";
    private final ReentrantLock uniqueIdLock = new ReentrantLock();
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

    public void seteInvoiceRepository(EInvoiceRepository eInvoiceRepository) {
        this.eInvoiceRepository = eInvoiceRepository;
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
    public InvoiceBalance obtainInvoiceBalance(String taxpayerIdentifyNo, String taxOrgCode) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("内存模式获取结存...");
        }

        synchronized (SINGLELOCK) {
            InvoiceBalance invoiceBalance = new InvoiceBalance();
            lock.lock();
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

                //用于查找丢票
                eInvoiceRepository.insertTLost(taxpayerIdentifyNo, invoiceBalance.getInvoiceNo());

                //用于验证票号唯一
                eInvoiceRepository.insertTUnique(invoiceBalance.getInvoiceNo());

            } catch (Exception e) {
                LOGGER.error("exception:{}", e.fillInStackTrace());
            } finally {
                lock.unlock();
            }

            return invoiceBalance;
        }
    }

    @Override
    public Long obtainBusinessId(String businessIdType) {
        Long queryResult = 0L;
        synchronized (UNIQUELOCK) {
            try {
                uniqueIdLock.lock();
                queryResult = eInvoiceRepository.obtainUniqueCacheIdByTypeName(businessIdType);
                eInvoiceRepository.updateUniqueCacheIdByTypeName(businessIdType, MAX_UNIQUEIDCASE_SIZE);

                eInvoiceRepository.insertTUnique(queryResult + businessIdType);
            } catch (Exception e) {
                LOGGER.error("exception:{}", e.fillInStackTrace());
            } finally {
                uniqueIdLock.unlock();
            }

            return queryResult;
        }
    }

    @Override
    public Long[] obtainBusinessId(String businessIdType, Integer cacheSize) {
        if (cacheSize < 1) {
            return new Long[0];
        }

        synchronized (BATCHLOCK) {
            uniqueIdLock.lock();
            Long queryResult = 0L;
            Long[] idArray = new Long[cacheSize];
            try {

                queryResult = eInvoiceRepository.obtainUniqueCacheIdByTypeName(businessIdType);

                for (Integer index = 0; index < cacheSize; index++) {
                    idArray[index] = queryResult + index;

                    eInvoiceRepository.insertTUnique(idArray[index] + businessIdType);
                }

                eInvoiceRepository.updateUniqueCacheIdByTypeName(businessIdType, Long.valueOf(cacheSize));
            } catch (Exception e) {
                LOGGER.error("exception:{}", e.fillInStackTrace());
            } finally {
                uniqueIdLock.unlock();
            }

            return idArray;
        }
    }

    @Override
    public Boolean verifyInvoiceBalance(String taxpayerIdentifyNo) {

        notNull(taxpayerIdentifyNo, "taxpayerIdentifyNo required.");

        final InvoiceBalance invoiceBalance = firstNonNull(eInvoiceRepository.get(STMT_EINVOICE_INVOICEBALANCE_GET, taxpayerIdentifyNo), nullInvoiceBalance());

        return !invoiceBalance.isNullObject();
    }

    @Override
    public String obtainPlatformCode() {
        Long queryResult = 0L;
        synchronized (UNIQUELOCK) {
            try {
                uniqueIdLock.lock();
                queryResult = eInvoiceRepository.obtainUniqueCacheIdByTypeName(PLATFORM_CODE.name());
                eInvoiceRepository.updateUniqueCacheIdByTypeName(PLATFORM_CODE.name(), MAX_UNIQUEIDCASE_SIZE);

                eInvoiceRepository.insertTUnique(queryResult + PLATFORM_CODE.name());
            } catch (Exception e) {
                LOGGER.error("exception:{}", e.fillInStackTrace());
            } finally {
                uniqueIdLock.unlock();
            }

            return PLATFORM_CODE_PREFIX + padStart(String.valueOf(queryResult), PLATFORM_CODE_SIZE, '0');
        }
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
		/*
		 * 如果有一个id用完，全部去数据库取一次，放入队列中；
		 */
		synchronized (UNIQUELOCKQUEUE) {
			
			if (FPKJ_ID.size() * FPKJ_LOG_ID.size() * FPKJMX_ID.size()* FPDDXX_ID.size() == 0) {
				long start = System.currentTimeMillis();
				for (String key : idMap.keySet()) {
					long queryResult = eInvoiceRepository.obtainUniqueCacheIdByTypeName(key);
					long needAddNum = SystemConfig.getIdMxaNum- idMap.get(key).size();
					long end = queryResult + needAddNum;
					for (long i = queryResult; i < end; i++) {
						idMap.get(key).add(i);
					}
					eInvoiceRepository.updateUniqueCacheIdByTypeName(key,needAddNum);
				}
				long end = System.currentTimeMillis();
				LOGGER.debug("--线程（ID：" + Thread.currentThread().getId()
						+  "缓存2000个ID到缓存中"
						+ (end - start) + "毫秒--\n");
			}
			Long returnVal = idMap.get(businessIdType).poll();
			if (returnVal != null) {
				return returnVal;
			} else {
				LOGGER.debug("没有匹配的businessIdType:" + businessIdType);
				return null;
			}
		}
	}

}
