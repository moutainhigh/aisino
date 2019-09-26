package com.aisino.client;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aisino.ws.IEInvWebService;

public class WSStaticClient {
	private static final Logger LOGGER = LoggerFactory.getLogger(WSStaticClient.class);

	/**
	 * <p>功能实现描述</p>
	 * 
	 * @param args void
	 * @author: lichunhui lichunhui1314@126.com
	 * @date: Created on 2016-8-26 下午09:40:34
	 */
	public static void main(String[] args) {
		
		String rootPath = WSStaticClient.class.getResource("/").getPath();
		String pathname = rootPath + "response.xml";
		File file = new File(pathname);
		String requestXml = "?";
		try {
			requestXml = FileUtils.readFileToString(file,"UTF-8");
		} catch (IOException e) {
			System.out.println("读取文件发生异常：" + e);
			e.printStackTrace();
		}
		
		String interfaceUrl = "http://192.168.15.43:8080/zzs_jdProxy/webservice/eInvWS?wsdl";
		String interfaceMethod = "eiInterface";
		String namespace = "http://ws.aisino.com/";
		String response = clientData(interfaceMethod, requestXml, interfaceUrl);
		System.out.println(response);
	}
	
	public static String clientData(String reqMethod, String reqXml, String url) {
        LOGGER.warn("创建factory开始----------------");
        String responeXml = "";
        JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
        factory.setServiceClass(IEInvWebService.class);
        factory.setAddress(url);
        IEInvWebService ieInvWebService = (IEInvWebService) factory.create();
        LOGGER.warn("创建factory结束----------------");
        LOGGER.warn("发送报文开始----------------");
        if ("eiInterface".equals(reqMethod)) {
        	responeXml = ieInvWebService.eiInterface(reqXml);
		}else {
			LOGGER.error("webservice method name is error, This method name is :" + reqMethod);
		}
        
        LOGGER.warn("发送报文结束----------------");
        if (responeXml == null) {
            return "";
        }
        return responeXml;
    }
}
