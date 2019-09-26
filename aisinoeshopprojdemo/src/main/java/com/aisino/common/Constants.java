package com.aisino.common;

import com.aisino.servlet.MsqlData;

import java.util.Properties;

public class Constants {
	public static String url = "";
	public static String user="";
	public static String password="";
	public static String webserviceURl="";
	public static String webserviceURlZzs="";
	static {
		Properties pro = new Properties();
		try {
			pro.load(MsqlData.class.getResourceAsStream("/jdbc.properties"));
			url = pro.getProperty("url");
			user = pro.getProperty("user");
			password = pro.getProperty("password");
			webserviceURl = pro.getProperty("webserviceURl");
			webserviceURlZzs = pro.getProperty("webserviceURlZzs");
		} catch (Exception e) {
			System.out.print(e.getMessage());
		}
	}
}
