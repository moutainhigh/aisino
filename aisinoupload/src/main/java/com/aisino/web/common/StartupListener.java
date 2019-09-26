package com.aisino.web.common;

import com.aisino.web.util.ServletContextUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class StartupListener implements ServletContextListener {
	private final Log log = LogFactory.getLog(StartupListener.class);


	public void contextInitialized(ServletContextEvent event) {
		log.debug("Initializing context...");
		ServletContext context = event.getServletContext();
		ServletContextUtil.readOsInfo(context);
	}

	public void contextDestroyed(ServletContextEvent sce) {

	}

}
