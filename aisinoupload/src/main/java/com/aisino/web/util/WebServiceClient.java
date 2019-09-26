package com.aisino.web.util;

import static org.joda.time.DateTime.now;

import java.io.ByteArrayOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.codehaus.xfire.client.Client;
import org.codehaus.xfire.transport.http.CommonsHttpMessageSender;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aisino.common.util.Data;
import com.aisino.common.util.GlobalInfo;
import com.aisino.common.util.ProXml;
import com.aisino.common.util.StringGenUtil;
import com.aisino.common.util.XMLShellFactory;
import com.aisino.common.util.XmlPar;
import com.aisino.domain.sys.model.Route;

public class WebServiceClient {
	private static final Logger logger = LoggerFactory.getLogger(WebServiceClient.class);

	public static String clientData(String reqXml, Route route) {
		String resStr = null;
		Client _client = null;
		if (null == route) {
			logger.error("route为空！");
			return "";
		}
		try {
			final DateTime begin11 = now();
			URL _url = new URL(route.getUrl());
			
	        HttpURLConnection httpConnection = (HttpURLConnection)_url.openConnection();
	        //TODO 2017-07-18设置连接超时时间10s，修改http连接的读超时20s为10s
	        httpConnection.setConnectTimeout(1000);
	        httpConnection.setReadTimeout(10000);//设置http连接的读超时,单位是毫秒
	        
	        httpConnection.connect();
	        _client = new Client(httpConnection.getInputStream(), null);
	        _client.setProperty(CommonsHttpMessageSender.HTTP_TIMEOUT, String.valueOf( 20000 ));//设置发送的超时限制,单位是毫秒;
	        _client.setProperty(CommonsHttpMessageSender.DISABLE_KEEP_ALIVE, "true");
	        _client.setProperty(CommonsHttpMessageSender.DISABLE_EXPECT_CONTINUE, "true");
	        
	        Object[] results = _client.invoke(route.getMethod(), new Object[]{reqXml});
	        resStr = results[0].toString();
			final Long millSeconds11 = new Duration(begin11, now()).getMillis();
			logger.info("创建client耗时......." +millSeconds11);
		} catch (Exception e) {
			logger.error("调用webService接口时报错!" + e.getMessage());
		}finally{
			if (null != _client) {
				_client.close();
			}
		}
		return resStr;
	}

	public GlobalInfo assemblingGlobalInfo(String interfaceCode) {
		GlobalInfo info = new GlobalInfo();
		info.setAppId(XmlPar.WLFP);
		info.setInterfaceCode(interfaceCode);
		info.setUserName(Constants.DSPTBM);
		info.setPassWord("");
		info.setRequestCode(Constants.DSPTBM);
		info.setRequestTime(ProXml.getCurDate("yyyy-MM-dd hh:mm:ss"));
		info.setResponseCode(Constants.SJBM);
		info.setDataExchangeId(Constants.DSPTBM + interfaceCode
				+ ProXml.getCurDate("yyyyMMdd") + StringGenUtil.getRdom(9));
		return info;
	}

	public static Data beanToxml(Object obj) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		XMLShellFactory.newInstance().saveXml(out, obj);
		Data data = ProXml.getData(out);
		return data;
	}
}
