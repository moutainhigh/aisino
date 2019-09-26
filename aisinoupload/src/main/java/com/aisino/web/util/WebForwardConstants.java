package com.aisino.web.util;

/**
 * 页面跳转静态常量类
 *
 * @author wei
 */
public class WebForwardConstants {
	/**
	 * 系统登录跳转页面
	 */
	public static final String REDIRECT_LOGIN = "redirect:/login.htm";
	public static final String REDIRECT_INDEX = "redirect:/index.htm";
	public static final String INDEX = "index";
	public static final String LOGIN = "login";
	public static final String CMS_WELCOME = "welcome";
	/**
	 * 角色管理跳转页面
	 */
	public static final String ROLE_LIST_REDIRECT = "redirect:/role/list.htm";
	public static final String ROLE_LIST = "role/list";
	public static final String ROLE_EDIT = "role/edit";
	public static final String ROLE_AUTH = "role/auth";
	public static final String REDIRECT_ROLE_LIST = "redirect:/role/list.htm";
	/**
	 * 用户管理跳转页面
	 */
	public static final String USER_LIST_REDIRECT = "redirect:/user/list.htm";
	public static final String USER_LIST = "user/list";
	public static final String USER_EDIT = "user/edit";
	public static final String USER_AUTH = "user/auth";
	/**
	 * 对账功能列表跳转页面
	 */
	public static final String HZGN_LIST = "cxtj/hzcx/list";
	/**
	 * 对账结果查询跳转页面
	 */
	public static final String DZJGXX_LIST = "cxtj/dzjgcx/list";
	public static final String DZJGXX_DETAIL = "cxtj/dzjgcx/invoiceDetail";

	/**
	 * 汇总对账结果查询跳转页面
	 */
	public static final String HZDZ_LIST = "cxtj/hzdzcx/list";
	public static final String CKMX_LIST = "cxtj/hzdzcx/ckmxList";

	/**
	 * 签章查询跳转页面
	 */
	public static final String QZCXXX_LIST = "cxtj/qzcx/list";
	public static final String QZCXXX_DETAIL = "cxtj/qzcx/edit";

	/**
	 * 交易流水查询跳转页面
	 */
	public static final String JYLSCX_LIST = "cxtj/jylscx/jylsList";
	public static final String JYLSCX_DETAIL = "cxtj/jylscx/jylsDetail";

	/**
	 * 发票管理票源下载*
	 */
	public static final String PYXZ_DSPT = "fpgl/pyxz/toPyxz";
	public static final String PYXZ_JCXX = "fpgl/pyxz/loadpy";
	
	public static final String PYXZLS_LIST = "fpgl/pylscx/pyxzlslist";
	/**
	 * 发票管理发票缴销*
	 */
	public static final String FPJX_LIST = "fpgl/fpjx/toFpjx";
	public static final String FPJX_LIST_DSPT_JC = "fpgl/fpjx/toFpjxJccx";	/*电商平台查询的结存信息*/
	/**
	 * 发票管理跳转页面
	 */
	public static final String FP_TH = "fpgl/fpth/list";
	public static final String FPSL_LIST = "fpgl/fpsl/list";
	public static final String FPSL_TOADD = "fpgl/fpsl/toadd";
	public static final String FPSL_EDIT = "fpgl/fpsl/fpsldmx";
	public static final String FP_MBXZ = "fpgl/mbxz/list";
	public static final String FPSL_ADD = "fpgl/fpsl/add";
	public static final String VIEW_MB = "fpgl/mbxz/viewOCXFpxx";//发票预览

	/**
	 * 电商企业信息跳转页面
	 */
	public static final String DSQYXX_TODSQYXX = "jbxxgl/dsqyxx/dsqyxx";
	public static final String DSQYXX_TODSQYXXCONDITION = "jbxxgl/dsqyxx/dsqyxxCondition";
	public static final String DSQYXX_TODSQYPZXX = "jbxxgl/dsqyxx/dsQyPzxx";
	public static final String DSQYXX_TODSYHQYPZXX = "jbxxgl/dsqyxx/dsYhQyxx";

	/**
	 * 电商平台信息跳转页面
	 */
	public static final String Dsptxx_TODSPTXX = "jbxxgl/dsptxx/dsptxx";
	public static final String Dsptxx_TODSPTXXCONDITION = "jbxxgl/dsptxx/dsptxxCondition";
	/**
	 * 路由管理跳转页面
	 */
	public static final String ROUTE_LIST = "route/list";
	public static final String ROUTE_EDIT = "route/edit";
	/**
	 * 发票处理失败跳转页面
	 */
	public static final String FPCLSB_LIST = "cxtj/fpsbcl/fpsblist";
	/**
	 * 电商证书信息页面
	 */
	public static final String DSZSXX_LIST = "jbxxgl/dszsxx/dszsxx";
	/**
	 * 发票状态信息查询页面
	 */
	public static final String FPCXZT_TOFPCXCONDITION = "cxtj/fpztcx/fpztcxCondition";

}
