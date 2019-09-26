package com.aisino.web.util;

import org.extremecomponents.table.context.Context;
import org.extremecomponents.table.context.HttpServletRequestContext;
import org.extremecomponents.table.limit.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class ExtremeTablePage {
	public ExtremeTablePage() {
	}

	public static Limit getLimit(HttpServletRequest request) {
		return getLimit(request, 10);
	}

	public static Limit getLimit(HttpServletRequest request, int defautPageSize) {
		Context context = new HttpServletRequestContext(request);
		LimitFactory limitFactory = new TableLimitFactory(context);
		TableLimit limit = new TableLimit(limitFactory);
		limit.setRowAttributes(1000000000, defautPageSize);
		return limit;
	}

	public static Map<String, String> getSort(Limit limit) {
		Map<String, String> sortMap = null;
		if (limit != null) {
			Sort sort = limit.getSort();
			if (sort != null && sort.isSorted()) {
				sortMap = new HashMap<String, String>();
				sortMap.put(sort.getProperty(), sort.getSortOrder());
			}
		}
		return sortMap;
	}
}