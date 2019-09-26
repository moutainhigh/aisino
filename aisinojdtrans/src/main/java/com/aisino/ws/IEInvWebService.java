package com.aisino.ws;

import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * <p>电子发票webservice接口</p>
 *
 * @author jerome.wang
 * @version 1.0 Created on 2015-4-23 03:04:38
 */
@WebService
public interface IEInvWebService {
	
	/**
	 * <p>电子发票webservice接口</p>
	 * 
	 * @param requestMessage
	 * @return String
	 * @author: jerome.wang
	 * @date: Created on 2015-4-23 03:55:57
	 */
	String eiInterface(@WebParam(name = "requestXml") String xml);
}
