package com.aisino.ds;

import java.io.IOException;
import java.util.Properties;

public class SystemConfig {
	public static String dbCharset;
	public static String localCharset;
	static {
		Properties properties = new Properties();
		try {
			properties.load(SystemConfig.class
					.getResourceAsStream("/com/aisino/ds/general.properties"));
			dbCharset = properties.getProperty("application.conf.db.charset");
			localCharset = properties
					.getProperty("application.conf.local.charset");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
