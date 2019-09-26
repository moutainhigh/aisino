package com.aisino.web.common;

import com.aisino.web.util.Constants;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthFilter extends HttpServlet implements Filter {

	private static final long serialVersionUID = 1L;
	static PathMatcher pathMatcher = new AntPathMatcher();
	private FilterConfig fileterConfig;

	public void init(FilterConfig arg0) throws ServletException {
		this.fileterConfig = arg0;

	}


	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException,
			ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		String uri = httpRequest.getRequestURI();
		uri = uri.replaceFirst(httpRequest.getContextPath(), "");
		boolean flag = (httpRequest.getSession().getAttribute(Constants.CURRENT_USER_KEY) == null);
		String anonymousUrls = fileterConfig.getInitParameter("anonymousUrls");
		if (flag) {
			if (anonymousUrls.length() > 0 && anonymousUrls.indexOf(uri) != -1) {
				filterChain.doFilter(request, response);
				return;
			}
			((HttpServletResponse) response).sendRedirect(httpRequest.getContextPath() + "/login.htm");
			return;
		} else {
//			Map<Integer, AuthPattern> authPattern=(Map<Integer, AuthPattern>)GlobalCache.get(GlobalCache.AUTHPATTERN);
//			Role role=user.getRole();
//			if(role!=null){
//				List<RoleAuthpattern> list=role.getList();
//				boolean voter=false;
//				for(RoleAuthpattern pattern:list){
//					AuthPattern authPat=authPattern.get(pattern.getId().getAuthpatternId());
//					String pat=authPat.getAuthPattern();
//					String[] args=pat.split(",");
//					for (int i = 0; i < args.length; i++) {
//						if(pathMatcher.match(args[i], uri)){
//							voter=true;
//							break;
//						}
//					}
//				}
//				if(!voter){
//					((HttpServletResponse) response).sendRedirect(httpRequest.getContextPath()+"/authFailed.html");
//					return;
//				}
//			}
			filterChain.doFilter(request, response);
			return;
		}
	}

	public void destroy() {
		this.fileterConfig = null;
	}


}