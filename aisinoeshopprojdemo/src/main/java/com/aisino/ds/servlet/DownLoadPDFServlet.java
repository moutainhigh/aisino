package com.aisino.ds.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class DownLoadPDFServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(req, resp);
	}
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String fileName = req.getParameter("fileName");
		InputStream inStream = new FileInputStream("E:\\dzfpPDF\\"+fileName);
		resp.reset();
		resp.setContentType("bin");
		resp.addHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
		byte [] by = new byte[100];
		int len;
		try{
			while ((len = inStream.read(by)) > 0){
				resp.getOutputStream().write(by, 0, len);
			}
            inStream.close();
            resp.flushBuffer();
		}catch (Exception e) {
		}
	}
}
