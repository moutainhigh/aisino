package com.aisino.trans.util;

/**
 * <p>一号店返回实体组装</p>
 *
 * @author jerome.wang
 * @version 1.0 Created on 2015-7-14 下午01:49:09
 */
public class YHDResPonsePackage {
	/**
	 * 组装返回协议
	 * 
	 * @param returnCode
	 * @param returnMsg
	 * @return DzfpInterfaceBean
	 * @author: jerome.wang
	 * @date: Created on 2015-7-14 下午01:49:55
	 */
	public static DzfpInterfaceBean packageRes(String returnCode,String returnMsg){
		DzfpInterfaceBean dzfpInterfaceBean = new DzfpInterfaceBean();
		dzfpInterfaceBean.setGlobalInfo(new GlobalInfo());
		dzfpInterfaceBean.setData(new Data());
		ReturnStateInfo rs = new ReturnStateInfo();
		rs.setReturnCode(returnCode);
		rs.setReturnMessage(returnMsg);
		dzfpInterfaceBean.setReturneStateInfo(rs);
		return dzfpInterfaceBean;
	}
	
	/**
	 *  组装返回协议
	 * 
	 * @param dzfpInterfaceBean
	 * @param rs
	 * @param data
	 * @return DzfpInterfaceBean
	 * @author: jerome.wang
	 * @date: Created on 2015-7-14 下午01:51:39
	 */
	public static DzfpInterfaceBean packageRes(DzfpInterfaceBean dzfpInterfaceBean,ReturnStateInfo rs,Data data){
		dzfpInterfaceBean.setReturneStateInfo(rs);
		dzfpInterfaceBean.setData(data);
		return dzfpInterfaceBean;
	}
}
