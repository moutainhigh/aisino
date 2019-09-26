package com.aisino.domain.task.service.impl;

import com.aisino.domain.base.dao.IBaseDao;
import com.aisino.domain.einvoice.model.Dsptxxkt;
import com.aisino.domain.rabbit.entity.PushInvoiceQueueEntity;
import com.aisino.domain.task.dao.ITaskDao;
import com.aisino.domain.task.service.IFpxxTaskService;
import com.aisino.protocol.bean.*;
import com.aisino.web.util.IbatisConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import static com.google.common.base.Objects.firstNonNull;
import static com.google.common.collect.Maps.newHashMapWithExpectedSize;

@Service
public class FpxxTaskServiceImp implements IFpxxTaskService {

	@Autowired
	private ITaskDao taskDao;
	@Autowired
	protected IBaseDao baseDao;
	
	public List<Dsptxxkt> queryDsptxxForList() throws SQLException {
		return (List<Dsptxxkt>) baseDao.queryForList(IbatisConstants.KEY_DSPTXXKT_FOR_LIST, null);
	}

	@Override
	@Transactional(readOnly = true)
	public REQUEST_FPKJ queryInvoiceInforById(String invoiceId) throws SQLException {
		final Map<String, String> map = newHashMapWithExpectedSize(1);
		map.put("invoiceId", invoiceId);

		return firstNonNull(taskDao.queryInvoiceInforById(map), new REQUEST_FPKJ());
	}

	public String queryFpZt(String id)throws SQLException {
		return taskDao.queryFpZt(id);
	}

	/**
	 * <p>查询待删除的pdf发票</p>
	 *
	 * @author: 张双超
	 * @date: Created on 2014-1-24 下午02:17:13
	 * @see com.aisino.domain.task.service.IFpxxTaskService#queryPdf(Map)
	 */
	@Override
	public List<REQUEST_FPKJ> queryPdf(Map<String, Object> map) throws SQLException {
		return taskDao.queryPdf(map);
	}


	@Override
	public String qeryFpscZt(String id) throws SQLException {
		return taskDao.qeryFpscZt(id);
	}

	@Override
	public PushInvoiceQueueEntity queryPushInforById(String invoiceIds)
			throws SQLException {
		 
		return taskDao.queryPushInforById(invoiceIds);
	}

	@Override
	public List<PushInvoiceQueueEntity> queryPushInvoiceForList(Map<String, Object> queryMap)
			throws SQLException {
		return taskDao.queryPushInvoiceForList(queryMap);
	}

	@Override
	public void updateInvoiceStatusById(Map<String, Object> map)
			throws SQLException {
		   taskDao.updateInvoiceStatusById(map);
	}

	@Override
	public void update51StatusById(Map<String, Object> map) throws SQLException {
		taskDao.update51StatusById(map);
		
	}



}
