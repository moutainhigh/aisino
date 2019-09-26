package com.aisino.ds;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    public ShServlet() {
        super();
    }

    public void destroy() {
        super.destroy();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 开发票
        String ddh = request.getParameter("ddh");
        request.setAttribute("ddh", ddh);
        String dsptbm = request.getSession().getAttribute("dsptbm") == null ? "1324324342323243" : request.getSession().getAttribute("dsptbm").toString();
        ProDdxx prod = new ProDdxx();
        Map<String, Object> mapnsr = prod.getNsrxx(dsptbm);
        try {
        if (mapnsr != null) {
            Map<String, Object> mapjc = prod.getMinjc(mapnsr.get("nsrsbh").toString());
            if (mapjc != null) {
                Object[] objss;
				
					objss = prod.getDdxxByDdh(ddh);
					 if (objss != null) {
		                    Map<String, Object> mapinsert = new HashMap<String, Object>();
		                    mapinsert.put("nsrdzdah", mapnsr.get("nsrdzdah").toString());
		                    mapinsert.put("nsrsbh", mapnsr.get("nsrsbh").toString());
		                    mapinsert.put("nsrmc", mapnsr.get("nsrmc").toString());
		                    mapinsert.put("fpdm", mapjc.get("fp_dm").toString());
		                    mapinsert.put("fphm", mapjc.get("fpqh").toString());
		                    mapinsert.put("swjgdm", mapnsr.get("swjg_dm").toString());
		                    mapinsert.put("fpzldm", mapjc.get("fpzl_dm").toString());
		                    request.setAttribute("filename", mapjc.get("fp_dm").toString() + mapjc.get("fpqh").toString() + ".pdf");
		                    boolean isinsert = prod.insertFpkj(objss, mapinsert);
		                    if (isinsert == true) {
		                        boolean jjc = prod.ddkfp(mapnsr.get("nsrdzdah").toString(), mapnsr.get("nsrsbh").toString(), mapjc.get("fpzl_dm").toString(), mapjc
		                                .get("fp_dm").toString(), mapjc.get("fpqh").toString(), mapnsr.get("zgswry_dm").toString(), mapnsr.get("swjg_dm").toString(),
		                                mapnsr.get("nsr_swjg_dm").toString(), (java.sql.Date) mapjc.get("fssj"));
		                        if (jjc == true) {
		                            prod.updateDdxx(ddh, "1", "**已完成配送，感谢您在京东商城购物，欢迎您再次光临！|| 陶庆军 ");
		                            List<Object[]> ddxxlist = prod.getDdxx(ddh);
		                            Object[] objs = ddxxlist.get(0);
		                            Ddxx ddxx = (Ddxx) objs[0];
		                            if ("明细".equalsIgnoreCase(ddxx.getFpnr())) {
		                                @SuppressWarnings("unchecked")
		                                List<Object> list = (List<Object>) objs[1];
										@SuppressWarnings("deprecation")
//		                                JSONArray fpmxObj = JSONArray.fromCollection(list);
												JSONArray fpmxObj = null;
										request.setAttribute("fpmxObj", fpmxObj);
		                            } else {
		                                Wpxx wpxx = new Wpxx();
		                                wpxx.setWpname(ddxx.getFpnr());
		                                wpxx.setWpjg(new Double(ddxx.getTallje()));
		                                List<Object> list = new ArrayList<Object>();
		                                list.add(wpxx);
		                                @SuppressWarnings("deprecation")
//		                                JSONArray fpmxObj = JSONArray.fromCollection(list);
		                                JSONArray fpmxObj = null;
		                                request.setAttribute("fpmxObj", fpmxObj);
		                            }
		                            String fpt = ddxx.getFpt();
		                            if (fpt == null) {
		                                ddxx.setFpt(ddxx.getDwxx());
		                            }
		                            JSONObject jsonObj = JSONObject.fromObject(ddxx);
		                            request.setAttribute("fpjson", jsonObj);
		                            request.setAttribute("fileName", mapjc.get("fp_dm").toString() + mapjc.get("fpqh").toString() + ".pdf");
		                            // response.sendRedirect("gwc/ddfp.jsp");
		                            request.getRequestDispatcher("gwc/ddlist.jsp").forward(request, response);
		                        } else {
		                            System.out.println("减结存失败");
		                        }
		                    } else {
		                        System.out.println("发票入库失败");
		                    }
		                } else {
		                    System.out.println("订单不存在");
		                }
		            } else {
		                System.out.println("纳税人无结存");
		            }
		        } else {
		            System.out.println("电商没有对应的纳税人");
		        }
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
               
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    public void init() throws ServletException {
       
    }

}
