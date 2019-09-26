package com.aisino.web.util;

import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aisino.common.util.StaticConstant;
import com.aisino.domain.sys.model.Route;

public class PlatformConstants {

	public static String WEBSERVICE_URL;
	public static String WEBSERVICE_METHOD;
	public static String PROTOCOL_ENCRYPTCODE = "0";
	public static String PROTOCOL_CODETYPE = "0";
	public static String PROTOCOL_ZCH_DEFAULT = "00000000";

	private final static Logger log = LoggerFactory.getLogger(StaticConstant.class);

	static {
		Properties properties = new Properties();
		try {
			properties.load(StaticConstant.class.getResourceAsStream("/platform_protocol.properties"));
			PROTOCOL_ENCRYPTCODE = properties.getProperty("application.platform.encryptcode");
			PROTOCOL_CODETYPE = properties.getProperty("application.platform.codetype");
			PROTOCOL_ZCH_DEFAULT = properties.getProperty("application.platform.zch_default");
			WEBSERVICE_URL = properties.getProperty("application.platform.url");
			WEBSERVICE_METHOD = properties.getProperty("application.platform.method");

		} catch (IOException e) {
			log.error("未知：" + e);
		}
	}

	public static Route getRoute() {
		Route route = new Route();
		route.setUrl(WEBSERVICE_URL);
		route.setMethod(WEBSERVICE_METHOD);
		return route;

	}
}
