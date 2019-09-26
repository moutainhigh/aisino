package com.aisino.trans.service;

import java.util.Map;

import com.aisino.trans.util.DzfpInterfaceBean;

public interface ITransService {
	@Deprecated
	public String trans(String xml);
	
	public String transReq(String xml);
	
	/**
	 * 一号店前置转发方法
	 * 
	 * @param dzfpInterfaceBean
	 * @return Map
	 * @author: jerome.wang
	 * @date: Created on 2015-7-14 下午01:54:32
	 */
	public Map<String, Object> YHDTransreq(DzfpInterfaceBean dzfpInterfaceBean);
}
