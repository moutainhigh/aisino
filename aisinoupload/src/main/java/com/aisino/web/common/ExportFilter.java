package com.aisino.web.common;

import org.apache.commons.lang.StringUtils;
import org.extremecomponents.table.filter.AbstractExportFilter;
import org.extremecomponents.table.filter.ExportResponseWrapper;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

public class ExportFilter extends AbstractExportFilter {

	private boolean responseHeadersSetBeforeDoFilter;

	public void init(FilterConfig filterConfig) throws ServletException {
		String responseHeadersSetBeforeDoFilter = filterConfig.getInitParameter("responseHeadersSetBeforeDoFilter");
		if (StringUtils.isNotBlank(responseHeadersSetBeforeDoFilter))
			this.responseHeadersSetBeforeDoFilter = new Boolean(responseHeadersSetBeforeDoFilter).booleanValue();
	}

	public void destroy() {
	}

	protected void doFilterInternal(ServletRequest request,
									ServletResponse response, FilterChain chain, String exportFileName) throws IOException, ServletException {
		exportFileName = URLEncoder.encode(exportFileName, "UTF-8");
		if (this.responseHeadersSetBeforeDoFilter) {
			setResponseHeaders((HttpServletResponse) response, exportFileName);
		}
		chain.doFilter(request, new ExportResponseWrapper((HttpServletResponse) response));
		if (!(this.responseHeadersSetBeforeDoFilter))
			setResponseHeaders((HttpServletResponse) response, exportFileName);
	}

}

