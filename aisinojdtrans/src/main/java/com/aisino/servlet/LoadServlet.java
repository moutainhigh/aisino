package com.aisino.servlet;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aisino.protocol.bean.NSRXX;
import com.aisino.protocol.bean.ROUTE;
import com.aisino.trans.util.SystemConfig;
import com.aisino.trans.util.XmlHandler;

public class LoadServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Logger log = LoggerFactory.getLogger(LoadServlet.class);

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) {
		
		boolean isDuplication = false;
		try{
			request.setCharacterEncoding("UTF-8");
			String falg = request.getParameter("falg");
			if(falg.equals("write")){
				log.info("展示纳税人信息1...");
				SystemConfig.loadRoute();
				List<ROUTE> routeList = loadData();
				request.setAttribute("routeList", routeList);
				request.getRequestDispatcher("view.jsp").forward(request,response);
				return;
			}
			if(falg.equals("load")){
				log.info("load纳税人信息2...");
				List<ROUTE> routeList = loadData();
				request.setAttribute("routeList", routeList);
				request.getRequestDispatcher("view.jsp").forward(request,response);
				return;
			}
			if(falg.equals("add")){
				log.info("新增纳税人信息...");
				ROUTE route = new ROUTE();
				BeanUtils.populate(route, request.getParameterMap());
				NSRXX nsrxx = new NSRXX();
				BeanUtils.populate(nsrxx, request.getParameterMap());
				NSRXX[] nsrxxsArr = {nsrxx};
				route.setNSRXXS(nsrxxsArr);
			    new XmlHandler().addNode(route);
			}
			if(falg.equals("change")){
				log.info("修改纳税人信息...");
				ROUTE route = new ROUTE();
				BeanUtils.populate(route, request.getParameterMap());
				NSRXX nsrxx = new NSRXX();
				BeanUtils.populate(nsrxx, request.getParameterMap());
				NSRXX[] nsrxxsArr = {nsrxx};
				route.setNSRXXS(nsrxxsArr);
				new XmlHandler().updateNode(route);
			}
			if(falg.equals("check")){
				String NSRSBH = request.getParameter("NSRSBH");
				log.info("check Nsrsbh:"+NSRSBH);
				boolean b = new XmlHandler().checkNsrIsDupl(NSRSBH);
				Writer out = response.getWriter(); 
				if(b){
					out.write("1");  
				    out.flush();  
				}else{
					out.write("2");  
				    out.flush();
				}
				return;
			}
			
			//SystemConfig.loadRoute();
			 
			List<ROUTE> routeList = loadData();
			response.setContentType("text/html;charset=UTF-8");
			((ServletRequest) response).setCharacterEncoding("UTF-8");
			request.setAttribute("routeList", routeList);
			request.getRequestDispatcher("view.jsp").forward(request,response);
			 
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
	}

	private List<ROUTE> loadData() throws Exception {
		log.info("开始进行配置读取...");
		ROUTE[] routes = SystemConfig.getRoute();
		List<ROUTE> routeList = new ArrayList<ROUTE>();
		if (routes != null && routes.length > 0) {
			for (int i = 0; i < routes.length; i++) {
				ROUTE route = routes[i];
				if (route.getNSRXXS() != null && route.getNSRXXS().length > 0) {
					List<NSRXX> nsrxxList = new ArrayList<NSRXX>();
					for (int j = 0; j < route.getNSRXXS().length; j++) {
						NSRXX nsrxx = route.getNSRXXS()[j];
						nsrxx.setSFKTPTDZFP(nsrxx.getSFKTPTDZFP().equals("Y") ? "是" : "否");
						nsrxx.setSFKTZZS(nsrxx.getSFKTZZS().equals("Y") ? "是" : "否");
						nsrxxList.add(route.getNSRXXS()[j]);
					}
					route.setNsrxxList(nsrxxList);
					routeList.add(route);
				}
			}
		}
		log.info("读取配置文件完成...");
		return routeList;
	}
}
