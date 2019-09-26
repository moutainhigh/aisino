package com.aisino.ws;

import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * <p>发票处理接口入库点，用于接收电商端传递发票开具请求信息 </p>
 * @author wuyong@aisino.com
 * @version 1.0 Created on Nov 4, 2013 7:47:20 PM
 */
@WebService
public interface IEInvWebService {
	/**
	 * <p>电商发票开具信息请求入口点</p>
	 * @param requestMessage
	 * @return String
	 * @author: wuyong@aisino.com
	 * @date: Created on Nov 4, 2013 7:48:42 PM
	 */
	String eiInterface(@WebParam(name = "requestXml") String requestMessage);
	
	/**
     * 发票下载接口
     * @param requestMessage
     * @return String
     */
    String eiInterfpxz(@WebParam(name = "requestXml") String requestMessage);
}
