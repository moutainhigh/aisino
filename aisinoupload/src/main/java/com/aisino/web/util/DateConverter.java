package com.aisino.web.util;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebBindingInitializer;
import org.springframework.web.context.request.WebRequest;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p>[描述信息：时间日期格式化类]</p>
 *
 * @author bruce.yang
 * @version 1.0 Created on 2013-8-28 上午9:26:55
 */
public class DateConverter implements WebBindingInitializer {

	public void initBinder(WebDataBinder binder, WebRequest request) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(df, false));
	}

	/**
	 * <p>功能实现描述:Date转字符串</p>
	 *
	 * @param date
	 * @return String
	 * @author: bruce.yang
	 * @date: Created on 2013-8-28 上午9:28:37
	 */
	public static String getYYYYMMDDHHMISS(Date date) {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddhhmmss");
		return df.format(date);
	}
}
