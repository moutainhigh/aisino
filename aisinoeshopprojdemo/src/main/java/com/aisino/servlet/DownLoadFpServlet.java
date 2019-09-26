package com.aisino.servlet;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class DownLoadFpServlet extends HttpServlet{
	 private final static Logger LOGGER = Logger.getLogger(DownLoadFpServlet.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int k = 0;
		String ddh = request.getParameter("ddh");
		String kplx = request.getParameter("kplx");
		String fpqqlsh = request.getParameter("fpqqlsh");
		String fileUrl = "";
		Connection con = MsqlData.getConn();
		OutputStream out = null;
		FileInputStream in = null;
		String sql;
		if(!fpqqlsh.equals("") || !fpqqlsh.isEmpty()){
			k = 1;
		}
		if(k == 1){
			sql = "select pdfpatch from fp_kj where fpqqlsh = '"+fpqqlsh+"'";
			File file=null;
			try{
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(sql);
			while(rs.next()){
				fileUrl = rs.getString("pdfpatch");
				
			}
			file = new File(fileUrl);
			byte[] fileByte = FileUtils.readFileToByteArray(file);
			response.reset();
			response.setContentType("bin");
//			response.addHeader("Content-Disposition", "attachment; filename=\"dzfp.pdf\"");
			response.addHeader("Content-Disposition", "attachment; filename="+fileUrl.substring(fileUrl.lastIndexOf("/")+1));
//			System.out.println(fileUrl.substring(fileUrl.lastIndexOf("/")+1));
			response.getOutputStream().write(fileByte);
			response.flushBuffer();
			}catch (Exception e) {
				e.printStackTrace();
			}
		}else {
			if(kplx.equals("1")){
				sql = "select pdfpatch from fp_kj   where id = (select fp_kjid from fp_ddxx where ddh = '"+ddh+"')";
			}else{
				sql = "select pdfpatch from fp_kj   where    yfp_dm = (select fp_dm from fp_kj where id = (select fp_kjid from fp_ddxx where ddh = '"+ddh+"')) and yfp_hm=(select fp_hm from fp_kj where id = (select fp_kjid from fp_ddxx where ddh = '"+ddh+"'))";
			}
			File file=null;
			try{
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(sql);
			while(rs.next()){
				fileUrl = rs.getString("pdfpatch");
			}
//			fileUrl.substring(fileUrl.lastIndexOf("/")+1)
			file = new File(fileUrl);
			byte[] fileByte = FileUtils.readFileToByteArray(file);
			response.reset();
			response.setContentType("bin");
//			response.addHeader("Content-Disposition", "attachment; filename=\"dzfp.pdf\"");
			response.addHeader("Content-Disposition", "attachment; filename="+fileUrl.substring(fileUrl.lastIndexOf("/")+1));
//			System.out.println(fileUrl.substring(fileUrl.lastIndexOf("/")+1));
			response.getOutputStream().write(fileByte);
			response.flushBuffer();
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
