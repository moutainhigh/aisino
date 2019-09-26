package com.aisino.ds;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DetailServlet extends HttpServlet {

	/**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
	 * Constructor of the object.
	 */
	public DetailServlet() {
		super();
	}
	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//查看发票
       String ddh= request.getParameter("ddh");
       Connection con = null;
       PreparedStatement pst = null;
       ResultSet rs = null;
       String fpdm = "";
       String fphm = "";
       try {
           String sql = "select t.fp_dm,t.fp_hm from wlkp_fp_kj t where t.ddh = ?";
           con = DataBaseUtil.getCon();
           con.setAutoCommit(false);
           pst = con.prepareStatement(sql);
           pst.setString(1, ddh);
           rs = pst.executeQuery();
           while (rs.next()) {
               fpdm = rs.getString("fp_dm");
               fphm = rs.getString("fp_hm");
           }
       } catch (Exception e) {
            
       }finally {
           DataBaseUtil.close(con, pst, rs);
       }
       request.setAttribute("fileName", fpdm+fphm+".pdf");
       request.setAttribute("ddh",ddh);
       request.getRequestDispatcher("gwc/dzfp.jsp").forward(request, response);
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
