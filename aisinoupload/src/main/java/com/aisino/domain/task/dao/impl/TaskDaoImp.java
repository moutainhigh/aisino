package com.aisino.domain.task.dao.impl;

import com.aisino.domain.base.dao.ibatis.BaseDao;
import com.aisino.domain.einvoice.model.Dsptxx;
import com.aisino.domain.einvoice.model.Dsptxxkt;
import com.aisino.domain.rabbit.entity.PushInvoiceQueueEntity;
import com.aisino.domain.sys.model.Route;
import com.aisino.domain.task.dao.ITaskDao;
import com.aisino.protocol.bean.FpscLsh;
import com.aisino.protocol.bean.REQUEST_FPKJ;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Repository
public class TaskDaoImp extends BaseDao implements ITaskDao {

	@Override
	public REQUEST_FPKJ queryInvoiceInforById(Map<String, String> map) throws SQLException {
		return (REQUEST_FPKJ) this.queryForObject("queryInvoiceInforById", map);
	}

	public String queryFpZt(String id) throws SQLException{

		return (String) this.queryForObject("queryFpZt", id);
	}

	public String querySwjgByDs(String id) throws SQLException {
		return queryForObject("querySwjgByDs", id).toString();
	}

	public Dsptxx queryDsptxxForObject(String dsptbm) throws SQLException {
		return (Dsptxx) queryForObject("queryDsptxxForObject", dsptbm);
	}

	/**
	 * <p>查询待删除的pdf发票</p>
	 *
	 * @author: 张双超
	 * @date: Created on 2014-1-24 下午02:19:49
	 * @see com.aisino.domain.task.dao.ITaskDao#queryPdf(Map)
	 */
	@Override
	public List<REQUEST_FPKJ> queryPdf(Map<String, Object> map) throws SQLException {
		return (List<REQUEST_FPKJ>) this.queryForList("queryPdf", map);
	}

	@Override
	public String qeryFpscZt(String id) throws SQLException {
		return (String)queryForObject("qeryFpscZt", id);
	}

	@Override
	public PushInvoiceQueueEntity queryPushInforById(String invoiceIds)
			throws SQLException {
		return (PushInvoiceQueueEntity) this.queryForObject("queryPushInforById", invoiceIds);
	}

	@Override
	public List<PushInvoiceQueueEntity> queryPushInvoiceForList(Map<String, Object> map)
			throws SQLException {
		return (List<PushInvoiceQueueEntity>) this.queryForList("queryPushFailedInfo",map);
	}

	@Override
	public void updateInvoiceStatusById(Map<String, Object> map)
			throws SQLException {
		this.update("updateInvoiceStatusById", map);
		
	}

	@Override
	public void update51StatusById(Map<String, Object> map) throws SQLException {
		this.update("update51StatusById", map);
	}
	
	
}
