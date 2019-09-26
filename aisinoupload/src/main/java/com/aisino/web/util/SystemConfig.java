package com.aisino.web.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class SystemConfig {
	private final static Logger logger = LoggerFactory.getLogger(SystemConfig.class);
	public static String localCharset = "GBK";
	public static String dbCharset = "ISO8859_1";
	public static String xmlCharSet = "UTF-8";
	public static String MATCHON = "on";
	public static String FPZL_DM = "22530";
	
	/**
	 * 发票上传最大数量
	 */
	public static String FPSCSLMAX = "";

	/**
	 * pdf文件保存路径
	 */
	public static String TARGETDIR = "";
	public static String swjgdm = "";
	public static int timerIsValible = 1;
	public static String uri = "";
	public static String serviceUrl = "";
	public static String getDsQyPyxx = "";    /*电商企业票源信息开关*/
	public static String getDsPzhdxx = "";  /*电商企业票种核定信息开关*/
	public static String getDsQyxx = "";    /*获取电商企业信息开关*/
	public static String pdfUrl = "";    /*pdf下载路径*/
	public static String pyUrl = "";
	public static String zdslbi = "";/*发票申领阀值*/
	public static String zdslzt = "";/*发票申领方式*/
	
	/**
     * 上传51之后pdf是否删除标志
     */
    public static String PDF_DELETE_PLATFORM;
    
    /**
     * 个人pdf不上传标识
     */
    public static String PDF_PERSON_PLATFORM;
    public static String DXHYURL;//大象慧云地址

	static {

		InputStream io = null;
		try {
			Properties properties = new Properties();
			io = SystemConfig.class.getResourceAsStream("/general.properties");
			properties.load(io);
			pyUrl = properties.getProperty("PYURL") == null ? pyUrl : properties.getProperty("PYURL").toString();
			dbCharset = properties.getProperty("application.conf.db.charset");
			localCharset = properties.getProperty("application.conf.local.charset");
			xmlCharSet = properties.getProperty("application.conf.xml.charset");
			uri = properties.getProperty("uri");
			serviceUrl = properties.getProperty("serviceUrl");
			getDsQyPyxx = properties.getProperty("application.conf.getDsQyPyxx");
			getDsPzhdxx = properties.getProperty("application.conf.getDsPzhdxx");
			getDsQyxx = properties.getProperty("application.conf.getDsQyxx");
			TARGETDIR = properties.getProperty("application.conf.targetdir");
			FPSCSLMAX = properties.getProperty("application.conf.fpscslMax");
			pdfUrl = properties.getProperty("application.conf.pdfurl");
			zdslbi = properties.getProperty("application.conf.zdslbi");
			zdslzt = properties.getProperty("application.conf.zdslzt");
			PDF_DELETE_PLATFORM = properties.getProperty("application.pdf.deleteplatform");
			PDF_PERSON_PLATFORM = properties.getProperty("application.pdf.personpdfupload");
			DXHYURL = properties.getProperty("application.platform.dxhyUrl");
		} catch (IOException e) {
			logger.error("初始化系统参数错误:" + e.getMessage());
		} finally {
			if (io != null) {
				try {
					io.close();
				} catch (IOException e) {
					logger.error("未知：" + e);
				}
			}
		}
	}

}
