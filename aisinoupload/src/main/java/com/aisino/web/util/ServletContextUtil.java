package com.aisino.web.util;

import javax.servlet.ServletContext;
import java.util.HashMap;

public class ServletContextUtil {

	@SuppressWarnings("unchecked")
	public static void refreshAuthpattern(ServletContext context) {
//		ApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(context);
//		IBaseService baseService=(IBaseService)ctx.getBean("baseServiceImpl");
//		List<AuthPattern> authPatternList=(List<AuthPattern>) baseService.queryForListByHql("from "+AuthPattern.class.getName());
//		Map<Integer, AuthPattern> globalParams=new HashMap<Integer, AuthPattern>();
//		for(AuthPattern authPattern:authPatternList){
//			globalParams.put(authPattern.getAuthpatternId(), authPattern);
//		}
//		GlobalCache.put(GlobalCache.AUTHPATTERN, globalParams);
	}


	@SuppressWarnings("unchecked")
	public static void readOsInfo(ServletContext context) {
		HashMap<String, String> osInfo = new HashMap<String, String>();
		osInfo.put("osName", (String) System.getProperty("os.name"));
		osInfo.put("osArch", System.getProperty("os.arch"));
		osInfo.put("osVersion", System.getProperty("os.version"));
		osInfo.put("jvmName", System.getProperty("java.vm.name"));
		osInfo.put("jvmVersion", System.getProperty("java.vm.version"));
		osInfo.put("javaVersion", System.getProperty("java.version"));
		osInfo.put("serverInfo", context.getServerInfo());
		context.setAttribute("osInfo", osInfo);
	}

}
