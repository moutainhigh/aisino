package com.aisino.web.interceptor;

import com.aisino.web.util.Constants;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class AccessStatisticsIntceptor implements HandlerInterceptor {

	private List<String> anonymous_urls;
	static PathMatcher pathMatcher = new AntPathMatcher();

	public void setAnonymous_urls(List<String> anonymous_urls) {
		this.anonymous_urls = anonymous_urls;
	}

	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object object, Exception ex)
			throws Exception {
	}

	public void postHandle(HttpServletRequest request, HttpServletResponse response,
						   Object object, ModelAndView modelAndView) throws Exception {
	}

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
							 Object object) throws Exception {
		boolean voter = false;
		String url = request.getRequestURI() != null ? request.getRequestURI().toString() : null;
		if (url != null) {
			url = url.replaceFirst(request.getContextPath(), "");
		}
		for (String pattern : anonymous_urls) {
			if (pathMatcher.match(pattern, url)) {
				voter = true;
				break;
			}
		}

		if (request.getSession().getAttribute(Constants.CURRENT_USER_KEY) != null) {
			voter = true;
		}
		if (!voter) {
			response.sendRedirect(request.getContextPath() + "/login.htm");
			return false;
		}
		return true;
	}

}
