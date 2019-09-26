package com.aisino.domain.base.service;

import com.aisino.web.util.Page;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface IBaseService {

	public Object insert(String statementName, Object parameterObject) throws SQLException;

	public int delete(String statementName, Object parameterObject) throws SQLException;

	public int update(String statementName, Object parameterObject) throws SQLException;

	public Object queryForObject(String statementName, Object parameterObject) throws SQLException;

	public List<?> queryForList(String statementName, Object parameterObject) throws SQLException;

	public void queryForPage(String statementName, Map<String, Object> parameterMap, Page page) throws SQLException;

	public Page queryForPage(String statementName, Map<String, Object> parameterMap, int pageNo) throws SQLException;

	public Page queryForPage(String statementName, Map<String, Object> parameterMap, int pageNo, int pageSize) throws SQLException;

}
