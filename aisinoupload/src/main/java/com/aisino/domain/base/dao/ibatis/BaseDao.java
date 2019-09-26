package com.aisino.domain.base.dao.ibatis;


import com.aisino.domain.base.dao.IBaseDao;
import com.aisino.web.util.Page;
import com.ibatis.sqlmap.client.SqlMapClient;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Repository
public class BaseDao extends SqlMapClientDaoSupport implements IBaseDao {
	@Resource(name = "sqlMapClient")
	private SqlMapClient sqlMapClient;

	@PostConstruct
	public void initSqlMapClient() {
		super.setSqlMapClient(sqlMapClient);
	}

	public Object insert(String statementName, Object parameterObject) throws SQLException {
		return sqlMapClient.insert(statementName, parameterObject);
	}

	public int delete(String statementName, Object parameterObject) throws SQLException {
		int i = sqlMapClient.delete(statementName, parameterObject);
		return i;
	}

	public int update(String statementName, Object parameterObject) throws SQLException {
		int i = sqlMapClient.update(statementName, parameterObject);

		return i;
	}

	public Object queryForObject(String statementName, Object parameterObject)
			throws SQLException {
		Object obj = sqlMapClient.queryForObject(statementName,
				parameterObject);
		return obj;
	}

	public List<?> queryForList(String statementName, Object parameterObject)
			throws SQLException {
		List<?> lst = sqlMapClient.queryForList(statementName, parameterObject);
		return lst;
	}

	public void queryForPage(String statementName, Map<String, Object> parameterMap, Page page) throws SQLException {
		if (parameterMap != null) {
			if (page.getTotalRows() == 0) {
				int totalRows = ((Integer) sqlMapClient.queryForObject(statementName + "Count", parameterMap)).intValue();
				page.setTotalRows(totalRows);
			}
			parameterMap.put("beginRow", page.getStartRow());
			parameterMap.put("endRow", page.getEndRow());
			parameterMap.put("pageSize", page.getPageSize());
			List<?> data = sqlMapClient.queryForList(statementName, parameterMap);
			page.setData(data);
		}
	}

	public Page queryForPage(String statementName, Map<String, Object> parameterMap, int pageNo) throws SQLException {
		Page page = new Page(pageNo);
		queryForPage(statementName, parameterMap, page);
		return page;
	}

	public Page queryForPage(String statementName, Map<String, Object> parameterMap, int pageNo, int pageSize) throws SQLException {
		Page page = new Page(pageNo, pageSize);
		queryForPage(statementName, parameterMap, page);
		return page;
	}

}
