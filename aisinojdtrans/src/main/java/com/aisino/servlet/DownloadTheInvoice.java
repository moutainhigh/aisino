package com.aisino.servlet;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.aisino.client.WebserviceClient;
import com.aisino.trans.util.ProXml;

/**
 * <p>下载pdf的httpServlet</p>
 *
 * @author summer.wang
 * @version 1.0 Created on 2015-8-14 下午2:18:14
 */
public class DownloadTheInvoice extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request,response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String pdfFwm = request.getParameter("p");
		if(pdfFwm !=null || !"".equals(pdfFwm)){
			final OutputStream os = response.getOutputStream();
			try {
					String resXml = WebserviceClient.invokeApiForDownloadInvInfo(pdfFwm);
					if(resXml != null && !resXml.equals("")){
						final byte[] stream = ProXml.decode(resXml.getBytes("UTF-8"));
						response.setContentType("application/pdf");
				        response.addHeader("Content-Disposition", "attachment; filename=" + pdfFwm + ".pdf");
				        response.setContentLength(stream.length);
			            os.write(stream);
			            os.flush();
					}else{
						// TODO 返回内容待定
					}
		        } catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
		            os.close();
		        }
		}else{
			
		}

	}

}