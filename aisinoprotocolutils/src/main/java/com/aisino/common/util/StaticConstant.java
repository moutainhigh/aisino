package com.aisino.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StaticConstant {
	public static String SPZLCX_URL="";
	public static String PDFSTREAM_N_OR_Y="y";
	public static String PDF_URL_FPXZ="";
	public static String PDFURL_N_OR_Y="y";

	public static String CHARSET = "UTF-8";
	public static String PROTOCOL_ENCRYPTCODE = "0";
	public static String PROTOCOL_CODETYPE = "0";
	public static String PROTOCOL_ZCH_DEFAULT = "00000000";
	public static String DEFAULTZCM = "";
	public static String SJ_Pin; // 密码
	public static String SJ_StoreName;// 税局
	public static String QY_Pin; // 密码
	public static String QY_StoreName;// 企业
	public static String CA_STATE = "1";
	public static String CA_TEST_PATH = "";
	public static int SJMYBEFORELENG = 10;
	public static String TAX_DER = "";// 税局端公钥
	public static String TAX_CA_TYPE = "";
	public static String FPSLFLAG = "N";

	public static Map caPublicKeyMap = new HashMap();
	public static Map caPublicKeypasswordMap = new HashMap();// 电商端存放密钥证书密码
	private final static Logger log = LoggerFactory
			.getLogger(StaticConstant.class);
	static {
		Properties properties = new Properties();
		InputStream in = null;
		try {
			String url = StaticConstant.class.getResource(
					"/protocol.properties").getPath();
			in = new FileInputStream(new File(url));
			properties.load(in);
			SJ_Pin = properties.getProperty("SJ.Pin");
			SJ_StoreName = properties.getProperty("SJ.StoreName");
			QY_Pin = properties.getProperty("QY.Pin");
			QY_StoreName = properties.getProperty("QY.StoreName");
			CHARSET = properties.getProperty("application.conf.charset");
			PROTOCOL_ENCRYPTCODE = properties
					.getProperty("application.conf.encryptcode");
			PROTOCOL_CODETYPE = properties
					.getProperty("application.conf.codetype");
			PROTOCOL_ZCH_DEFAULT = properties
					.getProperty("application.conf.zch_default");
			DEFAULTZCM = properties.getProperty("application.conf.default.zcm");
			FPSLFLAG = properties.getProperty("application.conf.default.spsl");
			CA_STATE = properties.getProperty("application.conf.ca.state");
			if ("2".equals(CA_STATE)) {
				CA_TEST_PATH = URLDecoder.decode(StaticConstant.class
						.getResource("/ca").getPath());
				// CA_TEST_PATH=StaticConstant.class.getResource("/ca").getFile();
			} else {
				CA_TEST_PATH = properties
						.getProperty("application.conf.ca.test");
			}

			TAX_CA_TYPE = properties
					.getProperty("application.conf.ca.tax_ca_type");
			TAX_DER = properties.getProperty("SJ.Der");// 税局端公钥
		} catch (IOException e) {
			log.error("未知：" + e);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					log.error("关闭流失败");
				}
			}
		}
	}
}
