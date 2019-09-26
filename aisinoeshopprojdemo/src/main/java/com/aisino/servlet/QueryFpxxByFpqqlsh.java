package com.aisino.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public class QueryFpxxByFpqqlsh extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		String fpqqlsh = request.getParameter("fpqqlsh");
		String sql = "select k.FPQQLSH, k.FP_DM, k.FP_HM, k.KPHJJE, k.KPRQ, k.pdfpatch from fp_kj_log t, fp_kj k where t.ID = k.ID and k.FPQQLSH = '"+fpqqlsh+"'";
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Statement st = null;
		ResultSet rs = null;
		Connection con = MsqlData.getConn();
		try {
			st = con.createStatement();
			rs = st.executeQuery(sql);
			Map<String, String> map = new HashMap<String, String>();
			while(rs.next()){
				/*String kplx = Integer.toString(rs.getInt("KPLX"));
				if(kplx.equals("1")){
					map.put("kplx", "蓝票");
				}else if(kplx.equals("2")){
					map.put("kplx", "红票");
				}*/
				map.put("fpqqlsh", rs.getString("FPQQLSH"));
				map.put("fphm", rs.getString("FP_HM"));
				map.put("fpdm", rs.getString("FP_DM"));
//				map.put("xhfmc", rs.getString("XHFMC"));
//				map.put("xhfsbh", rs.getString("XHFSBH"));
//				map.put("ghfmc", rs.getString("GHFMC"));
				map.put("pdfpatch", rs.getString("pdfpatch"));
				map.put("kphjje", String.valueOf(rs.getDouble("KPHJJE")));
				map.put("kprq", format.format(rs.getDate("KPRQ")));
			
				
				/*String kplx = Integer.toString(rs.getInt("KPLX"));
				if(kplx.equals("1")){
					request.setAttribute("kplx", "蓝票");
				}else if(kplx.equals("2")){
					request.setAttribute("kplx", "红票");
				}
				request.setAttribute("fphm", rs.getString("FP_HM"));
				request.setAttribute("fpdm", rs.getString("FP_DM"));
				request.setAttribute("xhfmc", rs.getString("XHFMC"));
				request.setAttribute("xhfsbh", rs.getString("XHFSBH"));
				request.setAttribute("ghfmc", rs.getString("GHFMC"));
				request.setAttribute("kphjje", String.valueOf(rs.getDouble("KPHJJE")));
				request.setAttribute("kprq", format.format(rs.getDate("KPRQ")));
				
				request.setAttribute("fpkjzt", rs.getString("FPKJ_ZT"));*/
			}
			request.setAttribute("list", map);
			request.getRequestDispatcher("gwc/fpxx.jsp").forward(request, response);
			//response.sendRedirect("gwc/fpxx.jsp");
			
		} catch (Exception e) {
			// XXX Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				rs.close();
				con.close();
			} catch (SQLException e) {
				// XXX Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}
	
	
	
	
	/*public Fpxx fpxxByfpqqlah(String fpqqlsh){
		String sql = "select k.FPQQLSH, k.FP_DM, k.FP_HM, k.XHFSBH, k.XHFMC, k.GHFMC, k.KPHJJE, k.KPRQ, k.KPLX, t.FPKJ_ZT from fp_kj_log t, fp_kj k where t.ID = k.ID and k.FPQQLSH = '"+fpqqlsh+"'";
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Connection con = MsqlData.getConn();
		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(sql);
			while(rs.next()){
				Fpxx fpxx = new Fpxx();
				fpxx.setFpdm(rs.getString("FP_HM"));
				fpxx.setFphm(rs.getString("FP_DM"));
				fpxx.setXhfmc(rs.getString("XHFMC"));
				fpxx.setXhfsbh(rs.getString("XHFSBH"));
				fpxx.setGhfmc(rs.getString("GHFMC"));
				fpxx.setKphjje(String.valueOf(rs.getDouble("KPHJJE")));
				fpxx.setKprq(format.format(rs.getDate("KPRQ")));
				fpxx.setKplx(Integer.toString(rs.getInt("KPLX")));
				fpxx.setFpkjzt(rs.getString("FPKJ_ZT"));
			}
			
		} catch (SQLException e) {
			// XXX Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}*/
	
	

}
