package com.aisino.servlet;

import com.aisino.common.util.*;
import com.aisino.ds.WS.WsClient;
import com.aisino.protocol.bean.COMMON_FPKJ_DDXX;
import com.aisino.protocol.bean.COMMON_FPKJ_FPT;
import com.aisino.protocol.bean.COMMON_FPKJ_XMXX;
import com.aisino.protocol.bean.REQUEST_COMMON_FPKJ;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.*;

public class FpchServlet extends HttpServlet {

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
		String ddh = request.getParameter("ddh");
		String fpkjid = "";
		REQUEST_COMMON_FPKJ REQUEST_FPKJXX = new REQUEST_COMMON_FPKJ();
		COMMON_FPKJ_FPT FPKJXX_FPTXX = new COMMON_FPKJ_FPT();
		SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sql = "select k.dsptbm,k.nsrsbh,k.nsrmc,k.nsrdzdah,k.swjg_dm,k.dkbz,k.pydm,k.kpxm,k.xhf_nsrsbh,k.xhfmc,k.ghfmc,k.ghf_nsrsbh,k.ghf_dz,k.ghf_gddh,k.ghf_sj,k.ghf_email,"
+" k.kpy,k.sky,k.kprq,k.kplx,k.fp_dm,k.fp_hm,k.kphjje,k.bz,k.id from fp_kj k left join fp_ddxx d on k.id =d.fp_kjid  where ddh = '"+ddh+"'";
		String mxsql = "";
		try{
			Connection conn = MsqlData.getConn();
			ResultSet rs = null;
			Statement st = conn.createStatement();
			rs = st.executeQuery(sql);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put(XmlPar.MAP_KEY_YWBM, "ECXML.FPKJ.BC.E_INV");
			map.put(XmlPar.MAP_KEY_ZCM, "21075448");
			while(rs.next()){
				fpkjid = rs.getString("id");
				mxsql = "select spmc,jldw,ggxh,spsl,spdj,spje from fp_kjmx where kjid= '"+fpkjid+"'";
				SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMddHHmmss");
				Calendar cal = Calendar.getInstance();
				String sqlsh = sdf2.format(cal.getTime());
				for (int i = 0; i < 6; i++) {
					sqlsh = "0" + sqlsh;
				}
				map.put(XmlPar.MAP_KEY_DSPTBM, rs.getString("dsptbm"));
				FPKJXX_FPTXX.setFPQQLSH(sqlsh);
				FPKJXX_FPTXX.setTSCHBZ("0");
				FPKJXX_FPTXX.setDSPTBM(rs.getString("dsptbm"));
				FPKJXX_FPTXX.setNSRSBH(rs.getString("nsrsbh"));
				FPKJXX_FPTXX.setNSRMC(rs.getString("nsrmc"));
				FPKJXX_FPTXX.setDKBZ(rs.getString("dkbz"));
				FPKJXX_FPTXX.setKPXM(rs.getString("kpxm"));
				FPKJXX_FPTXX.setXHF_NSRSBH(rs.getString("xhf_nsrsbh"));
				FPKJXX_FPTXX.setXHF_MC(rs.getString("xhfmc"));
				FPKJXX_FPTXX.setGHF_MC(rs.getString("ghfmc"));
				FPKJXX_FPTXX.setGHF_NSRSBH(rs.getString("ghf_nsrsbh"));
				FPKJXX_FPTXX.setGHF_DZ(rs.getString("ghf_dz"));
				FPKJXX_FPTXX.setGHF_GDDH(rs.getString("ghf_gddh"));
				FPKJXX_FPTXX.setGHF_YX(rs.getString("ghf_email"));
//				FPKJXX_FPTXX.setHY_DM(rs.getString(""));
//				FPKJXX_FPTXX.setHY_MC("");
				FPKJXX_FPTXX.setKPY(rs.getString("kpy"));
				FPKJXX_FPTXX.setSKY(rs.getString("sky"));
				FPKJXX_FPTXX.setPYDM(rs.getString("pydm"));
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//				FPKJXX_FPTXX.setKPRQ(sdf.format(cal.getTime()));
				FPKJXX_FPTXX.setKPLX("2");
				FPKJXX_FPTXX.setKPHJJE("-"+rs.getString("kphjje"));
				FPKJXX_FPTXX.setBZ(rs.getString("bz"));
				FPKJXX_FPTXX.setGHF_SJ(rs.getString("ghf_sj"));
				FPKJXX_FPTXX.setYFP_DM(rs.getString("fp_dm"));
				FPKJXX_FPTXX.setYFP_HM(rs.getString("fp_hm"));
				REQUEST_FPKJXX.setCOMMON_FPKJ_FPT(FPKJXX_FPTXX);
			}
			rs.close();
			st.close();
			st = conn.createStatement();
			rs = st.executeQuery(mxsql);
			List mxList = new ArrayList();
			while(rs.next()){
				COMMON_FPKJ_XMXX FPKJXX_XMXX = new COMMON_FPKJ_XMXX();
				FPKJXX_XMXX.setXMMC(rs.getString("spmc"));
				FPKJXX_XMXX.setGGXH(rs.getString("ggxh"));
				FPKJXX_XMXX.setXMDJ(rs.getString("spdj"));
				FPKJXX_XMXX.setXMDW(rs.getString("jldw"));
				FPKJXX_XMXX.setXMJE("-"+rs.getString("spje"));
				FPKJXX_XMXX.setXMSL("-"+rs.getString("spsl"));
				mxList.add(FPKJXX_XMXX);
			}
			COMMON_FPKJ_DDXX FPKJXX_DDXX = new COMMON_FPKJ_DDXX();
			FPKJXX_DDXX.setDDH(ddh);
			FPKJXX_DDXX.setDDSJ(sdf3.format(new Date()));
			REQUEST_FPKJXX.setCOMMON_FPKJ_DDXX(FPKJXX_DDXX);
			COMMON_FPKJ_XMXX[] fpkjxx_xmxxs = new COMMON_FPKJ_XMXX[mxList.size()];
			mxList.toArray(fpkjxx_xmxxs);
			REQUEST_FPKJXX.setCOMMON_FPKJ_XMXXS(fpkjxx_xmxxs);
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			XMLShellFactory.newInstance().saveXml(out, REQUEST_FPKJXX);
			Data data = ProXml.getData(out);
			GlobalInfo glo = new GlobalInfo();
			ProXml.getGlobalInfo(map, glo);
			ReturnStateInfo returnstate = ProXml.getReturnStateInfo("0000", "");
			String xml = ProXml.getXml(glo, returnstate, data);
			String b = WsClient.pushData(xml);
			if(b==null){
				request.getRequestDispatcher("gwc/ddlist.jsp").forward(request, response);
			}else{
			    request.setAttribute("res", b);
				request.getRequestDispatcher("gwc/error.jsp").forward(request, response);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
