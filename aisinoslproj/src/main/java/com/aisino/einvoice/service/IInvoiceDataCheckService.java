package com.aisino.einvoice.service;

import java.util.Map;

import com.aisino.domain.einvoice.repository.EInvoiceSubRepository;
import com.aisino.protocol.bean.COMMON_FPKJ_FPT;
import com.aisino.protocol.bean.COMMON_FPKJ_XMXX;
import com.aisino.protocol.bean.REQUEST_COMMON_FPKJ;
/**
 * 
 * <p>[描述信息：发票数据校验接口]</p>
 *
 * @author lichunhui lichunhui1314@126.com
 * @version 1.0 Created on 2015-8-4 下午08:27:43
 */
public interface IInvoiceDataCheckService {
	
	/**
	 * 
	 * <p>检查发票参数（受理接收电商交易数据时进行数据项校验）</p>
	 * 
	 * @param REQUEST_COMMON_FPKJ
	 * @param COMMON_FPKJ_FPT
	 * @param fpkjxx_xmxxs
	 * @param repository
	 * @return Map<String,String>
	 * @author: lichunhui lichunhui1314@126.com
	 * @throws Exception 
	 * @date: Created on 2015-8-4 下午08:26:20
	 */
	public Map<String, String> checkInvParam(REQUEST_COMMON_FPKJ REQUEST_COMMON_FPKJ, COMMON_FPKJ_FPT COMMON_FPKJ_FPT,
                                             COMMON_FPKJ_XMXX[] fpkjxx_xmxxs, final EInvoiceSubRepository repository) throws Exception;
}
