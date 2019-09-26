package com.aisino.itext.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class SystemConfig {
	public static Logger LOGGER = LoggerFactory.getLogger(SystemConfig.class);

	public static String pyUrl = "";
	public static String A5pyFileName="";
	public static String A4pyFileName="";
	public static String N6pyFileName="";
	public static String LISTPYFILENAME="";
	
	public static String pdfFactory="";
	public static int threedSleepTime=100;
	public static float _2wm_width=70;
	public static float _2wm_heigth=70;
	public static float A5_2wm_left=450;
	public static float A5_2wm_right=200;
	public static float A6_2wm_left=450;
    public static float A6_2wm_right=200;
	public static float A4_2wm_left=450;
	public static float A4_2wm_right=200;
	public static String socketAddress="";
	public static int socketPort;
	public static String RESULT_OK = "OK";
	public static String RESULT_ER = "ER";
	public static String OUTPDFDIR = "";
	public static String CHARSET="UTF-8";
	static{
		Properties properties = new Properties();
		try{
			properties.load(SystemConfig.class.getResourceAsStream("/config/config.properties"));
			pyUrl = properties.getProperty("PYURL")==null?pyUrl:properties.getProperty("PYURL").toString();
			A5pyFileName = properties.getProperty("A5PYFILENAME")==null?A5pyFileName:properties.getProperty("A5PYFILENAME").toString();
			A4pyFileName = properties.getProperty("A4PYFILENAME")==null?A4pyFileName:properties.getProperty("A4PYFILENAME").toString();
			N6pyFileName= properties.getProperty("N6PYFILENAME")==null?N6pyFileName:properties.getProperty("N6PYFILENAME").toString();
			LISTPYFILENAME = properties.getProperty("LISTPYFILENAME")==null?LISTPYFILENAME:properties.getProperty("LISTPYFILENAME").toString();
			pdfFactory = properties.getProperty("PDFFACTORY")==null?pdfFactory:properties.getProperty("PDFFACTORY").toString();
			_2wm_width = properties.getProperty("2WM_WIDTH")==null?_2wm_width:Float.parseFloat(properties.getProperty("2WM_WIDTH"));
			_2wm_heigth = properties.getProperty("2WM_HEIGTH")==null?_2wm_heigth:Float.parseFloat(properties.getProperty("2WM_HEIGTH"));
			A5_2wm_left = properties.getProperty("A5_2WM_LEFT")==null?A5_2wm_left:Float.parseFloat(properties.getProperty("A5_2WM_LEFT"));
			A5_2wm_right = properties.getProperty("A5_2WM_RIGHT")==null?A5_2wm_right:Float.parseFloat(properties.getProperty("A5_2WM_RIGHT"));
			A6_2wm_left = properties.getProperty("A6_2WM_LEFT")==null?A6_2wm_left:Float.parseFloat(properties.getProperty("A6_2WM_LEFT"));
            A6_2wm_right = properties.getProperty("A6_2WM_RIGHT")==null?A6_2wm_right:Float.parseFloat(properties.getProperty("A6_2WM_RIGHT"));
			A4_2wm_left = properties.getProperty("A4_2WM_LEFT")==null?A4_2wm_left:Float.parseFloat(properties.getProperty("A4_2WM_LEFT"));
			A4_2wm_right = properties.getProperty("A4_2WM_RIGHT")==null?A4_2wm_right:Float.parseFloat(properties.getProperty("A4_2WM_RIGHT"));
			socketAddress = properties.getProperty("SOCKET.ADDRESS");
			OUTPDFDIR = properties.getProperty("PDFOUTPDFDIR");
			CHARSET = properties.getProperty("CHARSET");
		}catch (Exception e) {
			LOGGER.error("初始化itest模块失败...."+e.getMessage());
		}
	}
}
