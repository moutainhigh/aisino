package com.aisino.domain.einvoice.resultService;

import com.aisino.domain.einvoice.model.FpDoneLog;
import com.aisino.domain.sys.model.Route;
import com.aisino.protocol.bean.*;

import java.io.File;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;


/**
 * @author zsf
 * @version 1.0
 * @created 2013-11-9 上午10:02:04
 */
public interface IBusinessDataService {

	/**
	 * 删除发票pdf
	 *
	 * @param map
	 * @param path
	 * @throws Exception
	 */
	void modifyDeletePdf(Map<String, Object> map, String path) throws Exception;

	/**
	 * <p>删除空文件夹</p>
	 * 
	 */
	void modifyDeleteFile(File file) throws Exception;

	/**
	 * <p>发票上传至服务平台</p>
	 */
	boolean uploadInv(Map<String, Object> map, List<REQUEST_FPKJ> uploadList, Route route, int scpt_lx) throws Exception;

	/**
	 * 
	 * <p>更新发票开具log表的上传51失败状态</p>
	 * 
	 * @param map
	 * @return
	 * @throws Exception boolean
	 * @author: 姚旭光
	 * @date: Created on Nov 27, 2015 5:19:49 PM
	 */
	void updateFailState(Map<String, Object> map) throws Exception;

	void modifyUploadResults(RESPONSE_FPKJJG RESPONSE_FPKJJG, REQUEST_FPKJ[] uploads, String fp_id) throws Exception;
	//TODO 修改数据库上传状态
	void modifyUploadResults(String returnCode, String fp_id) throws Exception;
	/**
	 * 更新上传51状态
	 * @param returnCode
	 * @param fp_id
	 * @throws SQLException
	 */
   void modifyUpload51Results(String returnCode, String fp_id) throws SQLException;

	/**
	 *  更新发票花费时间
	 * @param sql
	 * @param fpDoneLog
	 * @throws Exception
	 */
	void updateFpDoneLog(String sql, FpDoneLog fpDoneLog) throws Exception;
	
	
}
