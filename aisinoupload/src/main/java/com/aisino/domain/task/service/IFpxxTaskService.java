package com.aisino.domain.task.service;

import com.aisino.domain.einvoice.model.Dsptxxkt;
import com.aisino.domain.rabbit.entity.PushInvoiceQueueEntity;
import com.aisino.protocol.bean.REQUEST_FPKJ;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface IFpxxTaskService {

	/**
	 * 查询电商平台信息
	 *
	 * @return
	 * @throws SQLException
	 */
	List<Dsptxxkt> queryDsptxxForList() throws SQLException;


	/**
	 * <p>查询待删除的pdf发票</p>
	 *
	 * @param map
	 * @return
	 * @throws SQLException List<REQUEST_FPKJ>
	 * @author: 张双超
	 * @date: Created on 2014-1-24 下午02:16:19
	 */
	List<REQUEST_FPKJ> queryPdf(Map<String, Object> map) throws SQLException;


	/**
	 * 通过发票ID查询发票信息(上传51的)
	 *
	 */
	REQUEST_FPKJ queryInvoiceInforById(String invoiceId) throws SQLException;

	
	/**
	 * <p>根据发票id查询发票上传51状态</p>
	 * 
	 * @param id
	 * @return
	 * @throws SQLException String
	 * @author: jerome.wang
	 * @date: Created on 2015-1-27 上午09:28:42
	 */
	String qeryFpscZt(String id) throws SQLException;


	PushInvoiceQueueEntity queryPushInforById(String invoiceIds) throws SQLException;


	List<PushInvoiceQueueEntity> queryPushInvoiceForList(Map<String, Object> queryMap)throws SQLException;


	void updateInvoiceStatusById(Map<String, Object> map) throws SQLException;
	
	/**
	 * 更新上传51航信失败数据
	 * @param map
	 * @throws SQLException
	 */
	void update51StatusById(Map<String, Object> map) throws SQLException;
}
