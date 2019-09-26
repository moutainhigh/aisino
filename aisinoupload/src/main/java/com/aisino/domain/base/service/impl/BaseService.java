package com.aisino.domain.base.service.impl;

import com.aisino.domain.base.dao.IBaseDao;
import com.aisino.domain.base.service.IBaseService;
import com.aisino.web.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Service
public class BaseService implements IBaseService {
	@Autowired
	protected IBaseDao baseDao;

	public int delete(String statementName, Object parameterObject) throws SQLException {
		return baseDao.delete(statementName, parameterObject);
	}

	public Object insert(String statementName, Object parameterObject) throws SQLException {
		return baseDao.insert(statementName, parameterObject);
	}

	public List<?> queryForList(String statementName, Object parameterObject) throws SQLException {
		return baseDao.queryForList(statementName, parameterObject);
	}

	public Object queryForObject(String statementName, Object parameterObject) throws SQLException {
		return baseDao.queryForObject(statementName, parameterObject);
	}

	public void queryForPage(String statementName, Map<String, Object> parameterMap, Page page) throws SQLException {
		baseDao.queryForPage(statementName, parameterMap, page);
	}

	public Page queryForPage(String statementName, Map<String, Object> parameterMap, int pageNo) throws SQLException {
		return baseDao.queryForPage(statementName, parameterMap, pageNo);
	}

	public Page queryForPage(String statementName, Map<String, Object> parameterMap, int pageNo, int pageSize) throws SQLException {
		return baseDao.queryForPage(statementName, parameterMap, pageNo, pageSize);
	}

	public int update(String statementName, Object parameterObject) throws SQLException {
		return baseDao.update(statementName, parameterObject);
	}
}
