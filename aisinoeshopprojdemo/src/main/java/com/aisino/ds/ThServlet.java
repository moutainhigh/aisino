package com.aisino.ds;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class ThServlet extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * Constructor of the object.
     */
    public ThServlet() {
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
     * 本月发票作作废处理，其他发票冲红处理 This method is called when a form has its tag value
     * method equals to get.
     * 
     * @param request
     *            the request send by the client to the server
     * @param response
     *            the response send by the server to the client
     * @throws ServletException
     *             if an error occurred
     * @throws IOException
     *             if an error occurred
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        String ddh = request.getParameter("ddh");
        Fpkj fpkj = new Fpkj();
        Fpkjmx fpkjmx = new Fpkjmx();
        Calendar cal = Calendar.getInstance();
        int month = cal.get(Calendar.MONTH) + 1; /* 获得当前月份 */

        try {
            String sql = "select t.id, t.fp_hm,t.fp_dm,t.nsrsbh,t.nsrmc,t.fkdw,t.kprq,t.kphjje,t.kplx  from WLKP_FP_KJ t where t.ddh = ?";
            String sqlMx = "select mx.kjid,mx.sphxh,mx.spmc,mx.spdj,mx.spsl,mx.spje  from WLKP_FP_KJMX mx where KJID = ?";
            con = DataBaseUtil.getCon();
            // con.setAutoCommit(false);
            pst = con.prepareStatement(sql);
            pst.setString(1, ddh);
            rs = pst.executeQuery();
            if (rs.next()) {
                fpkj.setId(new Long(rs.getLong("id")));
                fpkj.setFpHm(CharsetConvert.localToDb(rs.getString("fp_hm")));
                fpkj.setFpDm(CharsetConvert.localToDb(rs.getString("fp_dm")));
                fpkj.setNsrsbh(CharsetConvert.localToDb(rs.getString("nsrsbh")));
                fpkj.setNsrmc(CharsetConvert.localToDb(rs.getString("nsrmc")));
                fpkj.setFkdw(CharsetConvert.localToDb(rs.getString("fkdw")));
                fpkj.setKprq(rs.getDate("kprq"));
                fpkj.setKphjje(new Double(rs.getDouble("kphjje")));
                fpkj.setKplx(new Integer(rs.getInt("kplx")));
            }
            pst = con.prepareStatement(sqlMx);
            pst.setLong(1, fpkj.getId().longValue());
            rs = pst.executeQuery();
            if (rs.next()) {
                fpkjmx.setKjid(new Long(rs.getLong("kjid")));
                fpkjmx.setSphxh(new Long(rs.getLong("sphxh")));
                fpkjmx.setSpmc(rs.getString("spmc"));
                fpkjmx.setSpdj(rs.getString("spdj"));
                fpkjmx.setSpsl(rs.getString("spsl"));
                fpkjmx.setSpje(new Double(rs.getDouble("spje")));

            }
        } catch (Exception ce) {
            try {
                ce.printStackTrace();
                con.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } finally {
            DataBaseUtil.close(con, pst, rs);
        }

        Integer kpMonth = new Integer(fpkj.getKprqFormat().substring(5, 7));/* 开票月份 */
        ProDdxx prod = new ProDdxx();
        if (kpMonth .equals(new Integer(month))) { /* 当月发票 作废处理 */

            boolean isTrue = updateFpkjToZF(fpkj);
            if(isTrue){
                prod.updateDdxx(ddh, "3", "**退票已完成，感谢您在京东商城购物，欢迎您再次光临！|| 陶庆军 ");
            }else{
                response.sendRedirect("gwc/glyqrth.jsp");
            }
        } else { /* 非当月发票 冲红处理 */
            String dsptbm = request.getSession().getAttribute("dsptbm") == null ? "1324324342323243" : request.getSession().getAttribute("dsptbm").toString();
            Map<String,Object> mapnsr = prod.getNsrxx(dsptbm);
            try {
            if (mapnsr != null) {
                Map<String,Object> mapjc = prod.getMinjc(mapnsr.get("nsrsbh").toString());
                if (mapjc != null) {
                    Object[] objss;
					
						objss = prod.getDdxxByDdh(ddh);
						if (objss != null) {
	                        Map<String,Object> mapinsert = new HashMap<String,Object>();
	                        mapinsert.put("nsrdzdah", mapnsr.get("nsrdzdah").toString());
	                        mapinsert.put("nsrsbh", mapnsr.get("nsrsbh").toString());
	                        mapinsert.put("nsrmc", mapnsr.get("nsrmc").toString());
	                        mapinsert.put("fpdm", mapjc.get("fp_dm").toString());
	                        mapinsert.put("fphm", mapjc.get("fpqh").toString());
	                        mapinsert.put("swjgdm", mapnsr.get("swjg_dm").toString());
	                        mapinsert.put("fpzldm", mapjc.get("fpzl_dm").toString());
	                        boolean isinsert = prod.insertFpkjCH(objss, mapinsert, fpkj, fpkjmx);
	                        if (isinsert == true) {
	                            boolean jjc = prod.ddkfp(mapnsr.get("nsrdzdah").toString(), mapnsr.get("nsrsbh").toString(), mapjc.get("fpzl_dm").toString(), mapjc
	                                    .get("fp_dm").toString(), mapjc.get("fpqh").toString(), mapnsr.get("zgswry_dm").toString(), mapnsr.get("swjg_dm")
	                                    .toString(), mapnsr.get("nsr_swjg_dm").toString(), (java.sql.Date) mapjc.get("fssj"));
	                            if (jjc == true) {
	                                prod.updateDdxx(ddh, "3", "**退票已完成，感谢您在京东商城购物，欢迎您再次光临！|| 陶庆军 ");
	                            }else{
	                                response.sendRedirect("gwc/glyqrth.jsp");
	                            }
	                        }
	                    }
	                }
	            }
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

                    
        }
        response.sendRedirect("gwc/glyqrth.jsp");
    }

    /**
     * 发票作废 李帅 2013-06-18 10:30
     */
    public boolean updateFpkjToZF(Fpkj fpkj) {
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            String sql = "update WLKP_FP_KJ t set t.YFP_DM = ? ,t.YFP_HM = ? , t.kplx = 4 ,t.BZ_KZ = 1 where t.id = ?";
            con = DataBaseUtil.getCon();
            con.setAutoCommit(false);
            pst = con.prepareStatement(sql);
            
            pst.setString(1, fpkj.getFpDm());
            pst.setString(2, fpkj.getFpHm());
            pst.setLong(3, fpkj.getId().longValue());
            pst.executeUpdate();
            con.commit();
            return true;
        } catch (Exception ce) {
            try {
                ce.printStackTrace();
                con.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return false;
        } finally {
            DataBaseUtil.close(con, pst, rs);
        }
    }

    /**
     * The doPost method of the servlet. <br>
     * 
     * This method is called when a form has its tag value method equals to
     * post.
     * 
     * @param request
     *            the request send by the client to the server
     * @param response
     *            the response send by the server to the client
     * @throws ServletException
     *             if an error occurred
     * @throws IOException
     *             if an error occurred
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    /**
     * Initialization of the servlet. <br>
     * 
     * @throws ServletException
     *             if an error occurs
     */
    public void init() throws ServletException {
        // Put your code here
    }

}
