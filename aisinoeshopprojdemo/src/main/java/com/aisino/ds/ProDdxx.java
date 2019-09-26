package com.aisino.ds;

import com.aisino.common.util.PassWordCheck;
import com.aisino.common.util.XMLShellFactory;
import com.aisino.common.util.XmlPar;
import com.aisino.ds.WS.WsClient;
import com.aisino.log.domain.Password;
import com.aisino.protocol.bean.*;
import com.aisino.servlet.MsqlData;

import java.io.ByteArrayOutputStream;
import java.sql.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

public class ProDdxx {

    private String nsrsbh;
    private String dsptbm;
    private String zch;
    private String spfsbh;
    private String spfmc;
    private String sjh;
    private String yx;
    private String kplx;
    private String yfp_dm;
    private String yfp_hm;
    private String[] fpmxxx;
    
    //updated by liangguodong
    private String fplx;
    
    public String getFplx() {
		return fplx;
	}

	public void setFplx(String fplx) {
		this.fplx = fplx;
	}

	public String[] getFpmxxx() {
        return fpmxxx;
    }

    public void setFpmxxx(String[] fpmxxx) {
        this.fpmxxx = fpmxxx;
    }

    @SuppressWarnings({ "static-access", "rawtypes", "unchecked" })
    public String insertDdxx(Object[] objs) {
        // boolean b = false;
        Connection con = null;
        PreparedStatement pst = null;
        PreparedStatement pstwp = null;
        try {
        	double kphjse = 0;
        	double se = 0;
            String hy_dm = "";
            String hy_mc = "";
            String nsrmc = "";
            String sl = "17%";
            Ddxx ddxx = (Ddxx) objs[0];
            String dsptmb = dsptbm;
            String platform_code = "";
            Password mm = PassWordCheck.passWordCreate(zch);
////////////////////////////////////////////////////
            String nsrxxSql = "select nsrmc,hy_dm,hy_mc,platform_code from dj_nsrxx,dj_dzswpt t where nsrsbh = '" + nsrsbh + "' and t.dsptbm = '" + dsptbm + "'";
            con = MsqlData.getConn();
            // con.setAutoCommit(false);
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(nsrxxSql);
            while (rs.next()) {
                nsrmc = rs.getString("nsrmc");
                hy_mc = rs.getString("hy_mc");
                hy_dm = rs.getString("hy_dm");
                platform_code = rs.getString("platform_code");
            }
            rs.close();
            con.close();
            st.close();
            REQUEST_COMMON_FPKJ REQUEST_COMMON_FPKJ = new REQUEST_COMMON_FPKJ();
            COMMON_FPKJ_FPT COMMON_FPKJ_FPT = new COMMON_FPKJ_FPT();
            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMddHHmmssSSS");
            Calendar cal = Calendar.getInstance();
            String sqlsh = sdf2.format(cal.getTime());
            for (int i = 0; i < 3; i++) {
                sqlsh = "0" + sqlsh;
            }
            COMMON_FPKJ_FPT.setGHF_QYLX("03");
            COMMON_FPKJ_FPT.setFPQQLSH(sqlsh);
            COMMON_FPKJ_FPT.setDSPTBM(dsptbm);
            COMMON_FPKJ_FPT.setNSRSBH(nsrsbh);
            COMMON_FPKJ_FPT.setNSRMC(nsrmc);
            COMMON_FPKJ_FPT.setDKBZ("0");
            COMMON_FPKJ_FPT.setKPXM(ddxx.getFpnr());
            COMMON_FPKJ_FPT.setXHF_NSRSBH(nsrsbh);
            COMMON_FPKJ_FPT.setXHF_MC(nsrmc);
            COMMON_FPKJ_FPT.setGHF_MC(new String(spfmc.getBytes("iso-8859-1"), "utf-8"));
            COMMON_FPKJ_FPT.setGHF_NSRSBH(spfsbh);
            COMMON_FPKJ_FPT.setGHF_DZ("北京市海淀区杏石口路甲18号（航天信息股份有限公司）");
//            COMMON_FPKJ_FPT.setGHF_GDDH("");
            COMMON_FPKJ_FPT.setHY_DM(hy_dm);
            COMMON_FPKJ_FPT.setHY_MC(hy_mc);
            COMMON_FPKJ_FPT.setKPY("京东商城");
            COMMON_FPKJ_FPT.setSKY("京东商城");
            COMMON_FPKJ_FPT.setPYDM("00001");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            COMMON_FPKJ_FPT.setKPRQ(sdf.format(cal.getTime()));
            
            if("1".equals(kplx)){
                COMMON_FPKJ_FPT.setKPLX("1");
                COMMON_FPKJ_FPT.setKPHJJE("" + String.valueOf(ddxx.getTallje()));
                COMMON_FPKJ_FPT.setTSCHBZ("");
                COMMON_FPKJ_FPT.setCZDM("10");
                COMMON_FPKJ_FPT.setYFP_DM("");
                COMMON_FPKJ_FPT.setYFP_HM("");
            }else if("2".equals(kplx)){
                COMMON_FPKJ_FPT.setKPLX("2");
                COMMON_FPKJ_FPT.setKPHJJE("-" + String.valueOf(ddxx.getTallje()));
                COMMON_FPKJ_FPT.setTSCHBZ("0");
                COMMON_FPKJ_FPT.setCZDM("20");
                COMMON_FPKJ_FPT.setYFP_DM(yfp_dm);
                COMMON_FPKJ_FPT.setYFP_HM(yfp_hm);
            }
            /**
             * 发票开具新增
        	 * @Date Created on 2015-0319 14:19  begin
        	 */
            SimpleDateFormat format = new SimpleDateFormat("MM");
            String month = format.format(new Date());
            double kpje = ddxx.getTallje();
//            COMMON_FPKJ_FPT.setFHR("Zaft");
//            COMMON_FPKJ_FPT.setXHQD("");
//            COMMON_FPKJ_FPT.setFKFKHYH("中国工商银行");
//            COMMON_FPKJ_FPT.setFKF_YHZH("中国工商银行,6228481234567890011");
//            COMMON_FPKJ_FPT.setSKFKHYH("中国建设银行北京市观海淀支行");
            COMMON_FPKJ_FPT.setSKF_YHZH("中国建设银行北京市观海淀支行,44201588400051008281");
            COMMON_FPKJ_FPT.setXHF_DH("010-27977555");
            COMMON_FPKJ_FPT.setXHF_DZ("北京市海淀区区九龙镇九佛建设路115号305室");
            /**
        	 * @Date Created on 2015-0319 14:19  end
        	 */
//            COMMON_FPKJ_FPT.setBZ("备注");
            COMMON_FPKJ_FPT.setGHF_SJ(sjh);
            COMMON_FPKJ_FPT.setGHF_YX(yx);
            REQUEST_COMMON_FPKJ.setCOMMON_FPKJ_FPT(COMMON_FPKJ_FPT);
            
            if("明细".equals(ddxx.getFpnr())){
                COMMON_FPKJ_XMXX[] COMMON_FPKJ_XMXXS = new COMMON_FPKJ_XMXX[fpmxxx.length];
                for (int i = 0; i < fpmxxx.length; i++) {
                    COMMON_FPKJ_XMXX COMMON_FPKJ_XMXX = new COMMON_FPKJ_XMXX();
                    String name = fpmxxx[i].split("=")[0];
//                    name = name.substring(0, 92);
                    String je = fpmxxx[i].split("=")[1];
                    DecimalFormat df = new DecimalFormat("#.##");
                    double bhsje = Double.parseDouble(je)/((Double.parseDouble(sl.substring(0, 2))+100)/100);
                    if("1".equals(kplx)){
                    	if((new String(name.getBytes("ISO-8859-1"),"utf-8")).length()<46){
                    		COMMON_FPKJ_XMXX.setXMMC(new String(name.getBytes("ISO-8859-1"),"utf-8"));
                    	}else{
                    		 COMMON_FPKJ_XMXX.setXMMC((new String(name.getBytes("ISO-8859-1"),"utf-8")).substring(0, 46));
                    	}
                       
                        COMMON_FPKJ_XMXX.setGGXH("");
                        COMMON_FPKJ_XMXX.setXMDJ(String.valueOf(bhsje));
                        COMMON_FPKJ_XMXX.setXMDW("部");
                        COMMON_FPKJ_XMXX.setXMJE(df.format(bhsje));
                        COMMON_FPKJ_XMXX.setXMSL("1");
                        /**
                         * 明细新增
                    	 * @Date Created on 2015-0319 14:19  begin
                    	 */
                        COMMON_FPKJ_XMXX.setSL(sl);
//                        COMMON_FPKJ_XMXX.setSM("1001");
                        se = Double.parseDouble(je)-Double.valueOf(df.format(bhsje));
                        COMMON_FPKJ_XMXX.setSE(df.format(Double.parseDouble(je)-Double.valueOf(df.format(bhsje))));
                        COMMON_FPKJ_XMXX.setHSJBZ("0");
                        
                        /**
                    	 * @Date Created on 2015-0319 14:19  end
                    	 */
                        
                    }else if("2".equals(kplx)){
                    	if((new String(name.getBytes("ISO-8859-1"),"utf-8")).length()<46){
                    		COMMON_FPKJ_XMXX.setXMMC(new String(name.getBytes("ISO-8859-1"),"utf-8"));
                    	}else{
                    		 COMMON_FPKJ_XMXX.setXMMC((new String(name.getBytes("ISO-8859-1"),"utf-8")).substring(0, 46));
                    	}
                        COMMON_FPKJ_XMXX.setGGXH("");
                        COMMON_FPKJ_XMXX.setXMDJ(String.valueOf(bhsje));
                        COMMON_FPKJ_XMXX.setXMDW("部");
                        COMMON_FPKJ_XMXX.setXMJE("-"+df.format(bhsje));
                        COMMON_FPKJ_XMXX.setXMSL("-1");
                        /**
                         * 明细新增
                    	 * @Date Created on 2015-0319 14:19  begin
                    	 */
                        COMMON_FPKJ_XMXX.setSL(sl);
//                        COMMON_FPKJ_XMXX.setSM("1001");
                        se =Double.parseDouble(je)-Double.valueOf(df.format(bhsje));
                        COMMON_FPKJ_XMXX.setSE("-"+df.format(Double.parseDouble(je)-Double.valueOf(df.format(bhsje))));
//                        COMMON_FPKJ_XMXX.setHSJBZ("0");
                        /**
                    	 * @Date Created on 2015-0319 14:19  end
                    	 */
                    }
                    COMMON_FPKJ_XMXXS[i] = COMMON_FPKJ_XMXX;
                    kphjse = kphjse + se;
                }
                REQUEST_COMMON_FPKJ.setCOMMON_FPKJ_XMXXS(COMMON_FPKJ_XMXXS);
            }else{
                COMMON_FPKJ_XMXX[] COMMON_FPKJ_XMXXS = new COMMON_FPKJ_XMXX[1];
                COMMON_FPKJ_XMXX COMMON_FPKJ_XMXX = new COMMON_FPKJ_XMXX();
                if("1".equals(kplx)){
                    COMMON_FPKJ_XMXX.setXMMC(ddxx.getFpnr());
                    COMMON_FPKJ_XMXX.setGGXH("");
//                    double t = ddxx.getTallje();
//                    ddxx.getTallje()/((Double.parseDouble(sl.substring(0, 2))+100)/100);
                    COMMON_FPKJ_XMXX.setXMDJ(String.valueOf(ddxx.getTallje()/((Double.parseDouble(sl.substring(0, 2))+100)/100)));
                    COMMON_FPKJ_XMXX.setXMDW("");
                    COMMON_FPKJ_XMXX.setXMJE(String.valueOf(ddxx.getTallje()/((Double.parseDouble(sl.substring(0, 2))+100)/100)));
                    COMMON_FPKJ_XMXX.setXMSL("1");
                    
                    /**
                     * 明细新增
                	 * @Date Created on 2015-0319 14:19  begin
                	 */
                    COMMON_FPKJ_XMXX.setSL(sl);
//                    COMMON_FPKJ_XMXX.setSM("1002");
                    double t = ddxx.getTallje()-ddxx.getTallje()/((Double.parseDouble(sl.substring(0, 2))+100)/100);
//                    se = Double.parseDouble(sl.substring(0, 1))*ddxx.getTallje()/100;
                    COMMON_FPKJ_XMXX.setSE(String.valueOf(t));
//                    COMMON_FPKJ_XMXX.setHSJBZ("0");
                    /**
                	 * @Date Created on 2015-0319 14:19  end
                	 */
                    
                }else if("2".equals(kplx)){
                    COMMON_FPKJ_XMXX.setXMMC(ddxx.getFpnr());
                    COMMON_FPKJ_XMXX.setGGXH("");
                    COMMON_FPKJ_XMXX.setXMDJ(String.valueOf(ddxx.getTallje()/((Double.parseDouble(sl.substring(0, 2))+100)/100)));
                    COMMON_FPKJ_XMXX.setXMDW("");
                    COMMON_FPKJ_XMXX.setXMJE("-"+String.valueOf(ddxx.getTallje()/((Double.parseDouble(sl.substring(0, 2))+100)/100)));
                    COMMON_FPKJ_XMXX.setXMSL("-1");
                    /**
                     * 明细新增
                	 * @Date Created on 2015-0319 14:19  begin
                	 */
                    COMMON_FPKJ_XMXX.setSL(sl);
//                    COMMON_FPKJ_XMXX.setSM("1002");
//                    se = Double.parseDouble(sl.substring(0, 1))*ddxx.getTallje()/100;
                    COMMON_FPKJ_XMXX.setSE("-"+String.valueOf(ddxx.getTallje()-ddxx.getTallje()/((Double.parseDouble(sl.substring(0, 2))+100)/100)));
//                    COMMON_FPKJ_XMXX.setHSJBZ("0");
                    /**
                	 * @Date Created on 2015-0319 14:19  end
                	 */
                }
                kphjse = kphjse + se;
                COMMON_FPKJ_XMXXS[0] = COMMON_FPKJ_XMXX;
                REQUEST_COMMON_FPKJ.setCOMMON_FPKJ_XMXXS(COMMON_FPKJ_XMXXS);
            }
            if("1".equals(kplx)){
                COMMON_FPKJ_FPT.setKPHJSE(String.valueOf(kphjse));
                COMMON_FPKJ_FPT.setHJBHSJE(String.valueOf(kpje-kphjse));
                COMMON_FPKJ_FPT.setKPHJJE(String.valueOf(kpje));
            }else if("2".equals(kplx)){
                COMMON_FPKJ_FPT.setKPHJSE("-"+String.valueOf(kphjse));
                COMMON_FPKJ_FPT.setHJBHSJE("-"+String.valueOf(kpje-kphjse));
                COMMON_FPKJ_FPT.setKPHJJE("-"+String.valueOf(kpje));
            }
//            订单信息
            COMMON_FPKJ_DDXX COMMON_FPKJ_DDXX = new COMMON_FPKJ_DDXX();
            COMMON_FPKJ_DDXX.setDDH(ddxx.getDdh() + "");
            COMMON_FPKJ_DDXX.setDDSJ(sdf.format(cal.getTime()));
            REQUEST_COMMON_FPKJ.setCOMMON_FPKJ_DDXX(COMMON_FPKJ_DDXX);
//            订单明细信息
            /*COMMON_FPKJ_DDMXXX[] COMMON_FPKJ_DDMXXXS = new COMMON_FPKJ_DDMXXX[1];
            COMMON_FPKJ_DDMXXX COMMON_FPKJ_DDMXXX = new COMMON_FPKJ_DDMXXX();
            COMMON_FPKJ_DDMXXX.setDDMC(ddxx.getFpnr());
            COMMON_FPKJ_DDMXXX.setDW(ddxx.getDwxx());
            COMMON_FPKJ_DDMXXX.setSL(String.valueOf(fpmxxx.length));
            COMMON_FPKJ_DDMXXX.setGGXH("");
            COMMON_FPKJ_DDMXXX.setDJ(String.valueOf(ddxx.getTallje()));
//            ddxx.getTallje()*fpmxxx.length
            COMMON_FPKJ_DDMXXX.setJE(String.valueOf(ddxx.getTallje()));
            COMMON_FPKJ_DDMXXXS[0] = COMMON_FPKJ_DDMXXX;
            REQUEST_COMMON_FPKJ.setCOMMON_FPKJ_DDMXXXS(COMMON_FPKJ_DDMXXXS);*/
            
//            支付信息
            COMMON_FPKJ_ZFXX COMMON_FPKJ_ZFXX = new COMMON_FPKJ_ZFXX();
            REQUEST_COMMON_FPKJ.setCOMMON_FPKJ_ZFXX(COMMON_FPKJ_ZFXX);
//            物流信息
            COMMON_FPKJ_WLXX COMMON_FPKJ_WLXX = new COMMON_FPKJ_WLXX();
            COMMON_FPKJ_WLXX.setCYGS("申通快递");
            COMMON_FPKJ_WLXX.setSHDZ("");
            COMMON_FPKJ_WLXX.setSHSJ(sdf.format(new Date()));
            COMMON_FPKJ_WLXX.setWLDH("13011111111");
            REQUEST_COMMON_FPKJ.setCOMMON_FPKJ_WLXX(COMMON_FPKJ_WLXX);
            
            
            /**
             * Ddxx ddxx = (Ddxx) objs[0]; String dsptmb = dsptbm; Password mm =
             * PassWordCheck.passWordCreate(zch);
             * 
             * REQUEST_COMMON_FPKJ REQUEST_COMMON_FPKJ = new REQUEST_COMMON_FPKJ();
             * COMMON_FPKJ_FPT COMMON_FPKJ_FPT = new COMMON_FPKJ_FPT(); SimpleDateFormat
             * sdf2 = new SimpleDateFormat("yyyyMMddHHmmss"); Calendar cal =
             * Calendar.getInstance(); String sqlsh =
             * sdf2.format(cal.getTime()); for (int i = 0; i < 6; i++) { sqlsh =
             * "0" + sqlsh; } COMMON_FPKJ_FPT.setFPQQLSH(sqlsh);
             * COMMON_FPKJ_FPT.setDSPTBM(dsptmb); COMMON_FPKJ_FPT.setNSRSBH(nsrsbh);
             * COMMON_FPKJ_FPT.setNSRMC("XX省XX汽配制造有限公司");
             * COMMON_FPKJ_FPT.setDKBZ("0"); COMMON_FPKJ_FPT.setKPXM(ddxx.getFpnr());
             * COMMON_FPKJ_FPT.setXHF_NSRSBH(nsrsbh); COMMON_FPKJ_FPT.setXHFMC("京东");
             * COMMON_FPKJ_FPT.setGHFMC(spfmc); COMMON_FPKJ_FPT.setGHF_NSRSBH(spfsbh);
             * COMMON_FPKJ_FPT.setGHF_DZ("北京市海淀区");
             * COMMON_FPKJ_FPT.setGHF_GDDH("010-00000000");
             * COMMON_FPKJ_FPT.setGHF_EMAIL(""); COMMON_FPKJ_FPT.setHY_DM("");
             * COMMON_FPKJ_FPT.setHY_MC(""); COMMON_FPKJ_FPT.setKPY("开票员");
             * COMMON_FPKJ_FPT.setSKY("收款员"); COMMON_FPKJ_FPT.setPYDM("00001");
             * SimpleDateFormat sdf = new
             * SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
             * COMMON_FPKJ_FPT.setKPRQ(sdf.format(cal.getTime()));
             * 
             * COMMON_FPKJ_FPT.setKPLX("1");
             * COMMON_FPKJ_FPT.setKPHJJE(""+String.valueOf(ddxx.getTallje()));
             * COMMON_FPKJ_FPT.setTSCHBZ("0"); COMMON_FPKJ_FPT.setCZDM("10");
             * COMMON_FPKJ_FPT.setYFP_DM(""); COMMON_FPKJ_FPT.setYFP_HM("");
             * 
             * COMMON_FPKJ_FPT.setBZ("备注"); COMMON_FPKJ_FPT.setGHF_SJ(sjh);
             * COMMON_FPKJ_FPT.setGHF_EMAIL(yx); COMMON_FPKJ_FPT.setGHFQYLX("03");
             * REQUEST_COMMON_FPKJ.setCOMMON_FPKJ_FPT(COMMON_FPKJ_FPT); COMMON_FPKJ_XMXX[]
             * COMMON_FPKJ_XMXXS = new COMMON_FPKJ_XMXX[1]; COMMON_FPKJ_XMXX COMMON_FPKJ_XMXX = new
             * COMMON_FPKJ_XMXX(); COMMON_FPKJ_XMXX.setXMMC(ddxx.getFpnr());
             * COMMON_FPKJ_XMXX.setGGXH("");
             * COMMON_FPKJ_XMXX.setXMDJ(String.valueOf(ddxx.getTallje()));
             * COMMON_FPKJ_XMXX.setXMDW("");
             * COMMON_FPKJ_XMXX.setXMJE(String.valueOf(ddxx.getTallje()));
             * COMMON_FPKJ_XMXX.setXMSL("1"); COMMON_FPKJ_XMXXS[0] = COMMON_FPKJ_XMXX;
             * REQUEST_COMMON_FPKJ.setCOMMON_FPKJ_XMXXS(COMMON_FPKJ_XMXXS); COMMON_FPKJ_DDXX
             * COMMON_FPKJ_DDXX = new COMMON_FPKJ_DDXX(); COMMON_FPKJ_DDXX.setDDH(ddxx.getDdh()
             * + ""); COMMON_FPKJ_DDXX.setDDDATE(sdf.format(cal.getTime()));
             * COMMON_FPKJ_DDXX.setTHDH(ddxx.getDdh() + "");
             * REQUEST_COMMON_FPKJ.setCOMMON_FPKJ_DDXX(COMMON_FPKJ_DDXX);
             **/
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            XMLShellFactory.newInstance().saveXml(out, REQUEST_COMMON_FPKJ);
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd");
            StringBuffer sbstr = new StringBuffer();
            Random random = new Random();
            for (int i = 0; i < 9; i++) {
                sbstr.append(random.nextInt(10));
            }
            com.aisino.common.util.GlobalInfo glo = new com.aisino.common.util.GlobalInfo();
            Map map = new HashMap();
            map.put(XmlPar.MAP_KEY_DSPTBM, dsptmb);
            map.put(XmlPar.MAP_KEY_YWBM, "ECXML.FPKJ.UPLOAD.E_INV");
            map.put(XmlPar.MAP_KEY_ZCM, "88343512");
            map.put(XmlPar.REQUESTTIME, new Date());
            com.aisino.common.util.ProXml.getGlobalInfo(map, glo);
            glo.setRequestTime(sdf.format(cal.getTime()));
            glo.setUserName(nsrsbh);
            glo.setPassWord(mm.getSjm() + mm.getPass());
            glo.setTaxpayerIdentifyNo(nsrsbh);
            com.aisino.common.util.Data data = com.aisino.common.util.ProXml.getData(out);
            data.setEncryptCode("");
            data.setZipCode("");
            String request = com.aisino.common.util.ProXml.getXml(glo, new com.aisino.common.util.ReturnStateInfo(), data);
            WsClient ws = new WsClient();
            ws.getClient(ddxx.getFplx());
            String res = ws.pushData(request);
            if ("0000".equals(res)) {
//                List<Object> list = (List<Object>) objs[1];
//                String sql = "insert into ddxx(ddh,zffs,fpt,dwxx,fpnr,fplx,tallje,dddate,yh,type,address,shr,dh,wl) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
//                String sqlwp = "insert into ddxx_wp(ddh ,wpname,wpjg,wpid,type) values(?,?,?,?,?)";
//                con = DataBaseUtil.getCon();
//                con.setAutoCommit(false);
//                pst = con.prepareStatement(sql);
//                pst.setString(1, ddxx.getDdh() + "");
//                pst.setString(2, CharsetConvert.localToDb(ddxx.getZffs()));
//                pst.setString(3, CharsetConvert.localToDb(ddxx.getFpt()));
//                pst.setString(4, CharsetConvert.localToDb(ddxx.getDwxx()));
//                pst.setString(5, CharsetConvert.localToDb(ddxx.getFpnr()));
//                pst.setString(6, CharsetConvert.localToDb(ddxx.getFplx()));
//                pst.setDouble(7, ddxx.getTallje());
//                Date date = ddxx.getDddate();
//                SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                String time = df1.format(date);
//                Timestamp tp = Timestamp.valueOf(time);
//                pst.setTimestamp(8, tp);
//                pst.setString(9, CharsetConvert.localToDb(ddxx.getYh()));
//                pst.setString(10, ddxx.getType());
//                pst.setString(11, CharsetConvert.localToDb(ddxx.getAddress()));
//                pst.setString(12, CharsetConvert.localToDb(ddxx.getShr()));
//                pst.setString(13, ddxx.getDh());
//                pst.setString(14, CharsetConvert.localToDb(ddxx.getWl()));
//                pst.execute();
//                pstwp = con.prepareStatement(sqlwp);
//                for (int i = 0; i < list.size(); i++) {
//                    Wpxx wpxx = (Wpxx) list.get(i);
//                    pstwp.setString(1, ddxx.getDdh() + "");
//                    pstwp.setString(2, CharsetConvert.localToDb(wpxx.getWpname()));
//                    pstwp.setDouble(3, wpxx.getWpjg().doubleValue());
//                    pstwp.setInt(4, wpxx.getWpid());
//                    pstwp.setInt(5, wpxx.getType());
//                    pstwp.execute();
//                }
//                con.commit();
                System.out.println(res);
                return ddxx.getDdh() + "";
            }
            System.out.println(res);
            return "9999";
        } catch (Exception ce) {
            try {
                ce.printStackTrace();
                con.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } finally {
            DataBaseUtil.close(null, pst, null);
            DataBaseUtil.close(con, pstwp, null);
        }
        return null;
    }

    public Object[] getDdxxByDdh(String ddh) throws SQLException {
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        PreparedStatement pstwp = null;
        ResultSet rswp = null;
        try {
            Object[] objs = new Object[2];
            String sql = "SELECT t.FP_KJID KJID, t.DDH ddh, t.DDSJ dddates,k.KPHJJE tallje, k.KPXM shr  from fp_ddxx t, fp_kj k WHERE t.FP_KJID = k.ID and ddh=?  order by dddates desc";
            String sqlwp = "SELECT *  from fp_kjmx t where KJID = ?";
//            String sqlwp = "select * from fp_ddxx where ddh=?";
//            con = DataBaseUtil.getCon();
            con = MsqlData.getConn();
            pst = con.prepareStatement(sql);
            pst.setString(1, ddh);
            rs = pst.executeQuery();
            if (rs.next()) {
                Ddxx ddxx = new Ddxx();
                ddxx.setDdh(rs.getString("ddh"));
                ddxx.setKJID(rs.getString("KJID"));
//              ddxx.setZffs(CharsetConvert.dbToLocal("在线付款"));
              ddxx.setZffs("在线付款");
                
//              ddxx.setFpt(CharsetConvert.dbToLocal(rs.getString("fpt")));
              ddxx.setDwxx(CharsetConvert.dbToLocal("部"));
//              ddxx.setFpnr(CharsetConvert.dbToLocal(rs.getString("fpnr")));
//              ddxx.setFplx(CharsetConvert.dbToLocal(rs.getString("fplx")));
              ddxx.setTallje(rs.getDouble("tallje"));
              ddxx.setYh("工商");
//              ddxx.setYh(CharsetConvert.dbToLocal("工商"));
              ddxx.setType("1");
//              ddxx.setAddress(CharsetConvert.dbToLocal(rs.getString("address")));
//              ddxx.setShr(CharsetConvert.dbToLocal(rs.getString("shr")));
              ddxx.setShr(rs.getString("shr"));
//              ddxx.setDh(rs.getString("dh"));
              ddxx.setDddates(rs.getString("dddates"));
                objs[0] = ddxx;
            }
            pstwp = con.prepareStatement(sqlwp);
            pstwp.setString(1, rs.getString("KJID"));
            rswp = pstwp.executeQuery();
            List<Wpxx> listwp = new ArrayList<Wpxx>();
            int type=1;
            while (rswp.next()) {
            	Wpxx wpxx = new Wpxx();
          	  wpxx.setType(rswp.getInt(type));
          	  wpxx.setWpjg(new Double(rswp.getDouble("SPJE")+rswp.getDouble("SE")));
          	  wpxx.setWpname(rswp.getString("SPMC"));
//          	rswp
          	  listwp.add(wpxx);
            }
            objs[1] = listwp;
            return objs;
        } catch (Exception ce) {
            ce.printStackTrace();
            return null;
        } finally {
        	rs.close();
        	con.close();
//            DataBaseUtil.close(null, pst, rs);
//            DataBaseUtil.close(con, pstwp, rswp);
        }
    }

    public List<Object[]> getDdxx() throws SQLException {
    	int n = 0;
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rss = null;
        PreparedStatement pstwp = null;
        ResultSet rswp = null;
        try {
//        	SELECT * from fp_ddxx t GROUP BY t.DDSJ DESC
            String sql = "SELECT t.FP_KJID KJID, t.DDH ddh, DATE_FORMAT(t.DDSJ,'%Y-%m-%d %T') dddates,k.KPHJJE tallje, k.GHFMC shr  from fp_ddxx t, fp_kj k WHERE t.FP_KJID = k.ID and k.FKDW = '张三'  order by dddates desc";
            String sqlwp = "SELECT *  from fp_kjmx t where KJID = ?";
            con = MsqlData.getConn();
            Statement st = con.createStatement();
            
            pst = con.prepareStatement(sql);
            pstwp = con.prepareStatement(sqlwp);
            rss = pst.executeQuery();
            List<Object[]> ddxxs = new ArrayList<Object[]>();
//            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            while (rss.next()) {
                Object[] objs = new Object[2];
                Ddxx ddxx = new Ddxx();
                ddxx.setDdh(rss.getString("ddh"));
                ddxx.setKJID(rss.getString("KJID"));
                ddxx.setTallje(rss.getDouble("tallje"));
                ddxx.setType("1");
                ddxx.setShr(rss.getString("shr"));
                ddxx.setDddates(rss.getString("dddates"));
                objs[0] = ddxx;
                pstwp.setString(1, ddxx.getKJID() + "");
                rswp = pstwp.executeQuery();
                List<Wpxx> listwp = new ArrayList<Wpxx>();
                int type = 1;
              while(  rswp.next()){
            	  Wpxx wpxx = new Wpxx();
            	  wpxx.setType(rswp.getInt(type));
            	  wpxx.setWpjg(new Double(rswp.getDouble("SPJE")));
            	  wpxx.setWpname(CharsetConvert.dbToLocal(rss.getString("shr")));
            	  listwp.add(wpxx);
            	  
              }
                objs[1] = listwp;
                ddxxs.add(objs);
            }
            return ddxxs;
        } catch (Exception ce) {
            ce.printStackTrace();
            return null;
        } finally {
        	rss.close();
        	con.close();
        	
//            MsqlData.close(null, pst, rs);
//            DataBaseUtil.close(con, pstwp, rswp);
        }
    }

    public List<Object[]> getDdxx(String ddh) {
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        PreparedStatement pstwp = null;
        ResultSet rswp = null;
        try {

            String sql = "select ddh,zffs,fpt,dwxx,fpnr,fplx,tallje,to_char(dddate,'yyyy-MM-dd hh:mm:ss') dddates,yh,type,address,shr,dh,wl from ddxx where  ddh=? order by dddates desc";
            String sqlwp = "select * from ddxx_wp where ddh=?";
            con = DataBaseUtil.getCon();
            pst = con.prepareStatement(sql);
            pst.setString(1, ddh);

            pstwp = con.prepareStatement(sqlwp);
            rs = pst.executeQuery();
            List<Object[]> ddxxs = new ArrayList<Object[]>();
            while (rs.next()) {
                Object[] objs = new Object[2];
                Ddxx ddxx = new Ddxx();
                ddxx.setDdh(rs.getString("ddh"));
                ddxx.setZffs(CharsetConvert.dbToLocal(rs.getString("zffs")));
                ddxx.setFpt(CharsetConvert.dbToLocal(rs.getString("fpt")));
                ddxx.setDwxx(CharsetConvert.dbToLocal(rs.getString("dwxx")));
                ddxx.setFpnr(CharsetConvert.dbToLocal(rs.getString("fpnr")));
                ddxx.setFplx(CharsetConvert.dbToLocal(rs.getString("fplx")));
                ddxx.setTallje(rs.getDouble("tallje"));
                ddxx.setYh(CharsetConvert.dbToLocal(rs.getString("yh")));
                ddxx.setType(rs.getString("type"));
                ddxx.setAddress(CharsetConvert.dbToLocal(rs.getString("address")));
                ddxx.setShr(CharsetConvert.dbToLocal(rs.getString("shr")));
                ddxx.setDh(rs.getString("dh"));
                ddxx.setDddates(rs.getString("dddates"));
                ddxx.setWl(CharsetConvert.dbToLocal(rs.getString("wl")));
                objs[0] = ddxx;

                if ("明细".equals(ddxx.getFpnr())) {
                    pstwp.setString(1, ddxx.getDdh() + "");
                    rswp = pstwp.executeQuery();
                    List<Wpxx> listwp = new ArrayList<Wpxx>();
                    while (rswp.next()) {
                        Wpxx wpxx = new Wpxx();
                        wpxx.setType(rswp.getInt("type"));
                        wpxx.setWpid(rswp.getInt("wpid"));
                        wpxx.setWpjg(new Double(rswp.getDouble("wpjg")));
                        wpxx.setWpname(CharsetConvert.dbToLocal(rswp.getString("wpname")));
                        listwp.add(wpxx);
                    }
                    objs[1] = listwp;
                }
                ddxxs.add(objs);
            }
            return ddxxs;
        } catch (Exception ce) {
            ce.printStackTrace();
            return null;
        } finally {
            DataBaseUtil.close(null, pst, rs);
            DataBaseUtil.close(con, pstwp, rswp);
        }
    }

    public void updateDdxx(String ddh, String type, String wl) {
        Connection con = null;
        PreparedStatement pst = null;
        try {
            String sql = "update ddxx set type=?,wl=wl||?,shsj=sysdate where ddh=?";
            if (type.equals("2")) {
                sql = "update ddxx set type=?,wl=wl||?,thsj=sysdate where ddh=?";
            }
            con = DataBaseUtil.getCon();
            con.setAutoCommit(false);
            pst = con.prepareStatement(sql);
            pst.setString(1, type);
            pst.setString(2, CharsetConvert.localToDb(wl));
            pst.setString(3, ddh);
            pst.executeUpdate();
            con.commit();
        } catch (Exception ce) {
            ce.printStackTrace();
        } finally {
            DataBaseUtil.close(con, pst, null);
        }
    }

    public boolean ddkfp(String nsrsbh, String fpzldm, String fpdm, String qh, String zh, String czydm, String nsrswjg) {
        Connection con = null;
        CallableStatement cs;
        try {
            con = DataBaseUtil.getCon();
            cs = con.prepareCall("{call WLKP_P_DEL_NSRJC(?,?,?,?,?,?,?)}");
            cs.setString(1, nsrsbh);
            cs.setString(2, fpzldm);
            cs.setString(3, fpdm);
            cs.setString(4, qh);
            cs.setString(5, zh);
            cs.setString(6, czydm);
            cs.setString(7, nsrswjg);
            cs.execute();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 冲红开具
     * 
     * @param objs
     * @param map
     * @param fpkj
     */
    public boolean insertFpkjCH(Object[] objs, Map<String, Object> map, Fpkj fpkj, Fpkjmx fpkjmx) {
        Connection con = null;
        PreparedStatement pstseq = null;
        PreparedStatement pst = null;
        PreparedStatement pstwp = null;
        ResultSet rs = null;
        try {
            int kjid = -1;
            Ddxx ddxx = (Ddxx) objs[0];
            @SuppressWarnings("unchecked")
            List<Object> list = (List<Object>) objs[1];
            String sqlid = "select wlkp_SEQ_FP_KJ_ID.nextval kjid from dual ";
            String sql = "insert into wlkp_fp_kj("
                    + "id,nsrdzdah,nsrsbh,nsrmc,fp_dm,fp_hm,kprq,kphjje,kplx,swjg_dm,sphsl,fpzl_dm,xhfsbh,xhfmc,hy_mc,hy_dm,pycode,FKFDZ,FKFDH,kpzt,ddh,BZ) values(?,?,?,?,?,?,sysdate,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            String sqlwp = "insert into wlkp_fp_kjmx(kjid,sphxh,spmc,spsl,spje,kprq) values(?,?,?,?,?,sysdate)";
            con = DataBaseUtil.getCon();
            con.setAutoCommit(false);
            pstseq = con.prepareStatement(sqlid);
            rs = pstseq.executeQuery();
            if (rs.next()) {
                kjid = rs.getInt("kjid");
            }
            pst = con.prepareStatement(sql);
            pst.setInt(1, kjid);
            pst.setString(2, map.get("nsrdzdah").toString());
            pst.setString(3, map.get("nsrsbh").toString());
            pst.setString(4, CharsetConvert.localToDb(map.get("nsrmc").toString()));
            pst.setString(5, map.get("fpdm").toString());
            pst.setString(6, map.get("fphm").toString());
            pst.setDouble(7, -ddxx.getTallje());
            pst.setInt(8, 2); /* 开票类型 */
            pst.setString(9, map.get("swjgdm").toString());
            pst.setInt(10, list.size());
            pst.setString(11, map.get("fpzldm").toString());
            pst.setString(12, map.get("nsrsbh").toString());
            pst.setString(13, CharsetConvert.localToDb(map.get("nsrmc").toString()));
            pst.setString(14, CharsetConvert.localToDb("商业"));
            pst.setString(15, null);
            pst.setString(16, "221001");
            pst.setString(17, ddxx.getAddress());
            pst.setString(18, ddxx.getDh());
            pst.setString(19, "1");
            pst.setString(20, ddxx.getDdh() + "");
            pst.setString(21, "原发票代码" + fpkj.getFpDm() + "原发票号码" + fpkj.getFpHm());
            pst.execute();
            pstwp = con.prepareStatement(sqlwp);
            for (int i = 0; i < list.size(); i++) {
                Wpxx wpxx = (Wpxx) list.get(i);

                pstwp.setInt(1, kjid);
                pstwp.setInt(2, i);
                pstwp.setString(3, CharsetConvert.localToDb(wpxx.getWpname()).substring(0, 51));
                pstwp.setString(4, "-" + fpkjmx.getSpsl()); /* 商品数量是负数 */
                pstwp.setDouble(5, wpxx.getWpjg().doubleValue());
                pstwp.execute();
            }
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
            DataBaseUtil.close(null, pstseq, rs);
            DataBaseUtil.close(null, pst, null);
            DataBaseUtil.close(con, pstwp, null);
        }
    }

    public Map<String, Object> getMinjc(String nsrsbh) {
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            String sql = "select * from (select jc.fpqh,jc.fp_dm,jc.fpzl_dm,jc.fssj from wlkp_fp_nsrjc jc where nsrsbh='" + nsrsbh
                    + "' order by to_number(fpqh) asc) where rownum=1";
            con = DataBaseUtil.getCon();
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            if (rs.next()) {
                map.put("fp_dm", rs.getString("fp_dm"));
                map.put("fpqh", rs.getString("fpqh"));
                map.put("fpzl_dm", rs.getString("fpzl_dm"));
                map.put("fssj", rs.getDate("fssj"));
            }
            return map;
        } catch (Exception ce) {
            ce.printStackTrace();
            return null;
        } finally {
            DataBaseUtil.close(con, pst, rs);
        }
    }

    public Map<String, Object> getNsrxx(String dsptbm) {
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            String sql = "select nsr.nsrsbh,nsr.nsrmc,nsr.swjg_dm,zgswry_dm,nsr_swjg_dm,nsrdzdah from mv_djnsr nsr,mv_dj dj where nsr.nsrsbh=dj.dsswdjzh and dj.dsptbm=?";
            con = DataBaseUtil.getCon();
            pst = con.prepareStatement(sql);
            pst.setString(1, dsptbm);
            rs = pst.executeQuery();
            if (rs.next()) {
                map.put("nsrsbh", rs.getString("nsrsbh"));
                map.put("nsrmc", CharsetConvert.dbToLocal(rs.getString("nsrmc")));
                map.put("swjg_dm", rs.getString("swjg_dm"));
                map.put("zgswry_dm", rs.getString("zgswry_dm"));
                map.put("nsr_swjg_dm", rs.getString("nsr_swjg_dm"));
                map.put("nsrdzdah", rs.getString("nsrdzdah"));
            }
            return map;
        } catch (Exception ce) {
            ce.printStackTrace();
            return null;
        } finally {
            DataBaseUtil.close(con, pst, rs);
        }
    }

    public boolean insertFpkj(Object[] objs, Map<String, Object> map) {
        Connection con = null;
        PreparedStatement pstseq = null;
        PreparedStatement pst = null;
        PreparedStatement pstwp = null;
        ResultSet rs = null;
        try {
            int kjid = -1;
            Ddxx ddxx = (Ddxx) objs[0];
            @SuppressWarnings("unchecked")
            List<Object> list = (List<Object>) objs[1];
            String sqlid = "select wlkp_SEQ_FP_KJ_ID.nextval kjid from dual";
            String sql = "insert into wlkp_fp_kj("
                    + "id,nsrdzdah,nsrsbh,nsrmc,fp_dm,fp_hm,kprq,kphjje,kplx,swjg_dm,sphsl,fpzl_dm,xhfsbh,xhfmc,hy_mc,hy_dm,pycode,FKFDZ,FKFDH,kpzt,ddh,kpy,sky,fkdw) values(?,?,?,?,?,?,sysdate,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            String sqlwp = "insert into wlkp_fp_kjmx(kjid,sphxh,spmc,spsl,spje,kprq,spdj) values(?,?,?,?,?,sysdate,?)";
            con = DataBaseUtil.getCon();
            con.setAutoCommit(false);
            pstseq = con.prepareStatement(sqlid);
            rs = pstseq.executeQuery();
            if (rs.next()) {
                kjid = rs.getInt("kjid");
            }
            pst = con.prepareStatement(sql);
            pst.setInt(1, kjid);
            pst.setString(2, map.get("nsrdzdah").toString());
            pst.setString(3, map.get("nsrsbh").toString());
            pst.setString(4, CharsetConvert.localToDb(map.get("nsrmc").toString()));
            pst.setString(5, map.get("fpdm").toString());
            pst.setString(6, map.get("fphm").toString());
            pst.setDouble(7, ddxx.getTallje());
            pst.setInt(8, 1);
            pst.setString(9, map.get("swjgdm").toString());
            pst.setInt(10, list.size());
            pst.setString(11, map.get("fpzldm").toString());
            pst.setString(12, map.get("nsrsbh").toString());
            pst.setString(13, CharsetConvert.localToDb(map.get("nsrmc").toString()));
            pst.setString(14, CharsetConvert.localToDb("商业"));
            pst.setString(15, null);
            pst.setString(16, "221001");
            pst.setString(17, CharsetConvert.localToDb(ddxx.getAddress()));
            pst.setString(18, ddxx.getDh());
            pst.setString(19, "1");
            pst.setString(20, ddxx.getDdh() + "");
            pst.setString(21, CharsetConvert.localToDb("网购商城"));
            pst.setString(22, CharsetConvert.localToDb("网购商城"));
            pst.setString(23, CharsetConvert.localToDb("张三"));
            pst.execute();
            pstwp = con.prepareStatement(sqlwp);
            for (int i = 0; i < list.size(); i++) {
                Wpxx wpxx = (Wpxx) list.get(i);

                pstwp.setInt(1, kjid);
                pstwp.setInt(2, i);
                pstwp.setString(3, CharsetConvert.localToDb(wpxx.getWpname()).substring(0, 51));
                pstwp.setInt(4, 1);
                pstwp.setDouble(5, wpxx.getWpjg().doubleValue());
                pstwp.setDouble(6, wpxx.getWpjg().doubleValue());
                pstwp.execute();
            }
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
            DataBaseUtil.close(null, pstseq, rs);
            DataBaseUtil.close(null, pst, null);
            DataBaseUtil.close(con, pstwp, null);
        }
    }

    public boolean ddkfp(String nsrdzdah, String nsrsbh, String fpzldm, String fpdm, String qh, String xgr, String swjg, String nsrswjg, java.sql.Date fssj) {
        Connection con = null;
        CallableStatement cs = null;
        try {
            con = DataBaseUtil.getCon();
            con.setAutoCommit(false);
            cs = con.prepareCall("{call WLKP_P_IN_FP_KJ(?,?,?,?,?,?,sysdate,?,?,?)}");
            cs.setString(1, nsrdzdah);
            cs.setString(2, nsrsbh);
            cs.setString(3, fpzldm);
            cs.setString(4, fpdm);
            cs.setString(5, qh);
            cs.setString(6, xgr);
            cs.setString(7, swjg);
            cs.setString(8, nsrswjg);
            cs.setDate(9, fssj);
            cs.execute();
            con.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (cs != null)
                    cs.close();
                if (con != null)
                    con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }

    public void updateDdxx(String ddh, String url) {
        Connection con = null;
        PreparedStatement pst = null;
        try {
            con = DataBaseUtil.getCon();
            con.setAutoCommit(false);
            String sql = "update DDXX set FPBZ ='1' , FPURL = ? where ddh = ?";
            pst = con.prepareStatement(sql);
            pst.setString(1, url);
            pst.setString(2, ddh);
            pst.execute();
            con.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (pst != null)
                    pst.close();
                if (con != null)
                    con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }

    public String getNsrsbh() {
        return nsrsbh;
    }

    public void setNsrsbh(String nsrsbh) {
        this.nsrsbh = nsrsbh;
    }

    public String getDsptbm() {
        return dsptbm;
    }

    public void setDsptbm(String dsptbm) {
        this.dsptbm = dsptbm;
    }

    public String getZch() {
        return zch;
    }

    public void setZch(String zch) {
        this.zch = zch;
    }

    public String getSpfsbh() {
        return spfsbh;
    }

    public void setSpfsbh(String spfsbh) {
        this.spfsbh = spfsbh;
    }

    public String getSpfmc() {
        return spfmc;
    }

    public void setSpfmc(String spfmc) {
        this.spfmc = spfmc;
    }

    public String getSjh() {
        return sjh;
    }

    public void setSjh(String sjh) {
        this.sjh = sjh;
    }

    public String getYx() {
        return yx;
    }

    public void setYx(String yx) {
        this.yx = yx;
    }

    public String getKplx() {
        return kplx;
    }

    public void setKplx(String kplx) {
        this.kplx = kplx;
    }

    public String getYfp_dm() {
        return yfp_dm;
    }

    public void setYfp_dm(String yfp_dm) {
        this.yfp_dm = yfp_dm;
    }

    public String getYfp_hm() {
        return yfp_hm;
    }

    public void setYfp_hm(String yfp_hm) {
        this.yfp_hm = yfp_hm;
    }

}
