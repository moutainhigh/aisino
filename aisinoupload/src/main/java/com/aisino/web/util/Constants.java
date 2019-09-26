package com.aisino.web.util;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.i18n.LocaleContextHolder;

public class Constants {
	private static Logger logger = LoggerFactory.getLogger(Constants.class);
	public static String PSIZE = "100";
	public static String NSRZT_SIZE = "10";
	public static String DSPTBM = "JD";
	public static String SJBM = "121";
	public static String APPID = "DZFP";
	public static String MB_PATH = "D:/";

	public static String CURRENT_USER_KEY = "CURRENT_USER_KEY";
	public final static String INV_UPLOAD_NOT = "0";//发票未上传的标记
	public final static String INV_REQUEST_SUCCESS = "4000";//初始化 批量上传流水号初始状态
	public final static String INV_SINGLE_REQUEST_SUCCESS = "5000";//初始化 单张上传流水号初始状态
	public final static String INV_UPLOAD_SUCCESS = "4002";//内网受理成功
	public final static String INV_RESPONSE_SUCCESS = "4003";//获取结果成功
	public final static String INV_RESPONSE_FAIL = "4004";//获取结果失败
	public final static String INV_UPLOAD_FAILED = "4001";//发送失败或请求失败
	 /**
     * 发票状态标记 
     */
    public final static String INV_RECEIVE_SUCCESS = "1000";//受理接收成功
    public final static String INV_GENERATE_SUCCESS="2000";//发票生成成功
    public final static String INV_GENERATE_FAIL="2001";//发票生成失败
    public final static String INV_ESHOP_SUCCESS="3000";//发票下发成功
    public final static String INV_ESHOP_FAIL="3001";//发票下发失败
    public final static String INV_TAX_SUCCESS="4002";//发票上传税局成功
    public final static String INV_TAX_FAIL="4005";//发票上传税局成功
    public final static String INV_DXHY_SUCCESS="1";//上传大象慧云成功
    public final static String INV_DXHY_FAIL="2";//上传大象慧云失败
    public final static String INV_51_SUCCESS="3";//51上传成功
    public final static String INV_51_FAIL="4";//51上传失败
    
    public final static int INV_TAX_TRYCOUNT=5;//上传税局的重试次数
    public final static String INV_51_WAITUPLOAD = "3000";//等待上传51状态
	/**
	 * 单张发票上传失败
	 */
	public final static String INV_SINGLEUPLOAD_FAILED = "4005";
	public final static String FPKJ_ZT = "3000";//发票开具正常状态
	public final static String FPSC_SL = "10";//发票上传分页数量
	public final static String FPSC51_SL = "10000";//发票上传51数量
	public final static String FPSC51_PAGECOUNT = "100";//发票上传51每页数量
	public final static String PDFSC_SL = "100";//PDF删除分页数量
	public final static String SINGLE_FPSC = "1";//单张发票上传
	public final static String FPSCJG_SUCCESS = "1";//获取发票上传结果成功
	public final static String FPSCLSH_SCBZ_SUCCESS = "1";//发票流水号上传标志成功
	public final static String FPSCLSH_SCBZ_FAIL = "2";//发票流水号上传标志失败

	public static int PAGESIZE = 10;

	public static String BIZ_MESS = "BIZ_MESS";

	public static int MAX_ARTICLE_FONT_SIZE = 200;

	public static long MAX_FILE_SIZE = 1024 * 1024 * 2;

	public static String FILE_UPLOAD_DIR = "/uploadFiles";

	public static String ONESQL_PREFIX = "HSCMS_BACKUP_";

	public static final String BUNDLE_KEY = "config/i18n/messages";

	public static int getDefaultPageSize() {
		Locale locale = LocaleContextHolder.getLocale();
		String size;
		try {
			size = ResourceBundle.getBundle("config/i18n/messages", locale).getString("DEFAULT_PAGE_SIZE");
		} catch (MissingResourceException mse) {
			size = "10";
			logger.error("未知" + mse.getMessage());
		}
		return Integer.parseInt(size);
	}

	/**
	 * 用户类型-电商平台 0
	 */
	public static final String USER_TYPE_DSPT = "0";
	/**
	 * 用户类型-电商企业 1
	 */
	public static final String USER_TYPE_DSQY = "1";
	public static String FPCL_CSSB = "2001";
	public static String FPCL_SCSB = "4001";
	public static String FPCL_XFSB = "3001";
	public static String FPCL_CSCG = "2000";
	public static String FPCL_XFCG = "3000";
	public static String FPCL_SCCG = "4000";
}
