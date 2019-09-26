package com.aisino.web.util;

import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class WebUtil {
	private final static String START_WITH_DEFAULT = "search_";

	public static Map<String, Object> getQueryConditionStartWith(
			HttpServletRequest request, String startString) {
		Map<String, Object> filterMap = WebUtils.getParametersStartingWith(request, startString);
		return filterMap;
	}

	public static Map<String, Object> getQueryConditionStartWith(HttpServletRequest request) {
		return getQueryConditionStartWith(request, START_WITH_DEFAULT);
	}

	public static void putQueryConditionStartWith(HttpServletRequest request, Map<String, Object> queryCondition, String startString) {
		Iterator<Entry<String, Object>> entryIterator = (Iterator<Entry<String, Object>>) queryCondition.entrySet().iterator();
		while (entryIterator.hasNext()) {
			Entry<String, Object> entry = entryIterator.next();
			request.setAttribute(startString + entry.getKey(), entry.getValue());
		}
	}

	public static void putQueryConditionStartWith(HttpServletRequest request, Map<String, Object> queryCondition) {
		putQueryConditionStartWith(request, queryCondition, START_WITH_DEFAULT);
	}
}
