package com.aisino.servlet;

import org.apache.commons.io.FileUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

public class View extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		
		String pdfurl = request.getParameter("pdfurl");
		byte[] fileContent = FileUtils.readFileToByteArray(new File(pdfurl));
		response.setContentType("application/octet-stream");
		OutputStream pw = response.getOutputStream();
		pw.write(fileContent);
		pw.flush();
		pw.close();
		
	}
}
