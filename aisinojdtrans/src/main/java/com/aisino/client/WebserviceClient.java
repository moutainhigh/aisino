package com.aisino.client;

import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.namespace.QName;

import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aisino.domain.NsrRoute;
import com.aisino.trans.util.SystemConfig;

public class WebserviceClient {
	
	private static Logger log = LoggerFactory.getLogger(WebserviceClient.class);

	public static String invokeApi(String requestXml, NsrRoute route)
			throws Exception {
		
		/*if (null == route) {
			log.error("route为空！");
			return "";
		}
		
		JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
		org.apache.cxf.endpoint.Client client = dcf.createClient(route.getUrl());

		// sayHello 为接口中定义的方法名称 张三为传递的参数 返回一个Object数组
		Object[] objects = null;
		QName opName = new QName("http://ws.aisino.com/", route.getMethod());
		try {
			objects = client.invoke(opName, requestXml);
		} catch (Exception e) {
			log.error("调用cxf服务端接口时，出现异常，详情为：--------------" + e);
			throw new Exception(e);
		}

		String responseXml = objects[0].toString();*/
		
		return WSStaticClient.clientData(route.getMethod(), requestXml, route.getUrl());
	}

	/**
	 * 
	 * <p>调用受理端的发票信息查询接口</p>
	 * 
	 * @param requestXml 请求报文
	 * @return
	 * @throws Exception String
	 * @author: lichunhui lichunhui1314@126.com
	 * @date: Created on 2016-7-5 下午04:17:49
	 */
	public static String invokeApiForDownloadInvInfo(String requestXml) throws Exception {
		NsrRoute route = new NsrRoute();
		route.setUrl(SystemConfig.INVOICE_FPXZ_URL);
		route.setMethod(SystemConfig.INVOICE_FPXZ_METHOD);
		return invokeApi(requestXml, route);
	}

}
