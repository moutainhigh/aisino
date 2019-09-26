package com.aisino.trans.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.aisino.common.util.XMLShellFactory;
import com.aisino.core.util.StringUtil;
import com.aisino.domain.NsrRoute;
import com.aisino.protocol.bean.FP_XS;
import com.aisino.protocol.bean.NSRXX;
import com.aisino.protocol.bean.ROUTE;

public class SystemConfig {
	
	private static Logger log = LoggerFactory.getLogger(SystemConfig.class);
	
	public static Map<String, NSRXX> routeMap= new HashMap<String, NSRXX>();
	public static String charSet = "UTF-8";
	public static int SJMYBEFORELENG=10;
	public static String PROTOCOL_ENCRYPTCODE="0";
	public static String PROTOCOL_CODETYPE="0";
	private static String NO="N";
	private static String YES="Y";
	public static String HSBZ_HS="1";
	public static String HSBZ_BHS="0";
	public static String KPLX_LZFP="1";
	public static String KPLX_HZFP="2";
	public static Map<String,String> MERGEDSPTMap;

	//添加西部CA加密解密 
	public static String SL_CA_PRIVATE_KEY;
	public static String SL_CA_PUBLIC_KEY;
	public static String SL_CA_TRUST;
	public static String SL_CA_PASSWORD;
	public static String NSR_CA_PUBLIC_KEY;
	
	public static String INVOICE_FPXZ_URL="";//发票下载的url
	public static String INVOICE_FPXZ_METHOD="";//发票下载的方法名
 
	//51证书路径
	public static String FILE_PATH_OF_51_FP = "";
	
	static{
		loadRoute();
		loadProperties() ;
	}
	
	public static void loadRoute(){
		try{
			ROUTE[] routes = getRoute();
			log.info("routes.length:"+routes.length);
			if(routes != null && routes.length > 0){
				for(int i = 0; i < routes.length; i++){
					ROUTE route = routes[i];
					if(route.getNSRXXS() != null && route.getNSRXXS().length > 0){
						NSRXX[] nsrxxs = route.getNSRXXS();
						for(int j = 0; j < nsrxxs.length; j++){
							NSRXX nsrxx = nsrxxs[j];
							nsrxx.setDQMC(route.getDQMC());
							nsrxx.setDSPTBM(route.getDSPTBM());
							if(nsrxx.getFP_XSS()!=null&&nsrxx.getFP_XSS().length>0){
								Map<String, FP_XS> fpxsMap = new HashMap<String,FP_XS>();
								for(int k = 0; k < nsrxx.getFP_XSS().length; k++){
									fpxsMap.put(nsrxx.getFP_XSS()[k].getFP_DM(), nsrxx.getFP_XSS()[k]);
								}
								nsrxx.setFpxsMap(fpxsMap);
							}
							routeMap.put(nsrxxs[j].getNSRSBH(), nsrxx);
						}
					}else{
						log.error("加载前置路由信息出错....<未配置纳税人路由信息>");
					}
				}
			}else{
				log.error("加载前置路由信息出错....<未配置路由信息>");
			}
		}catch (Exception e) {
			log.error("加载前置路由信息出错....", e);
		}
	}
	
	/**
	 * @author skye.he
	 * @see 加载general.properties 中的DSPTBM 用来对普通电子发票冲红
	 * @Date 2015.08.18 16:34 created
	 * @
	 */
	public static void loadProperties(){
		InputStream io = null;
        Properties properties = new Properties();
        try {
        	io = SystemConfig.class.getResourceAsStream("/general.properties");
			properties.load(io);
			String temp = properties.getProperty("DSPTBM") ;
			INVOICE_FPXZ_URL=properties.getProperty("invoice_fpxz_url") ;
			INVOICE_FPXZ_METHOD=properties.getProperty("invoice_fpxz_method") ;
			if(temp != null && !temp.equals("")){
				MERGEDSPTMap = new HashMap<String, String>();
				String[] dsptmbs = temp.split(",");
				if(dsptmbs != null && dsptmbs.length > 0){
					for(int i = 0; i < dsptmbs.length;i++){
						MERGEDSPTMap.put(dsptmbs[i], dsptmbs[i]);
					}
				}
				}
			//添加CA加密解密配置参数
			SL_CA_PRIVATE_KEY = properties.getProperty("SL_CA_PRIVATE_KEY") ;
			SL_CA_PUBLIC_KEY = properties.getProperty("SL_CA_PUBLIC_KEY") ;
			SL_CA_TRUST = properties.getProperty("SL_CA_TRUST") ;
			SL_CA_PASSWORD = properties.getProperty("SL_CA_PASSWORD") ;
			NSR_CA_PUBLIC_KEY = properties.getProperty("NSR_CA_PUBLIC_KEY");
		} catch (IOException e) {
			log.error("读取配置文件失败",e);
		} finally {
			try {
				if(io != null){
					io.close() ;
				}
			} catch (IOException e) {
				log.error("关闭配置文件流失败",e);
			}
		}
        
	}
	
	@SuppressWarnings("rawtypes")
	public static ROUTE[] getRoute() throws Exception{
		log.info("开始读取配置文件...");
		byte[] routeXmlByte = FileUtils.readFileToByteArray(new File(SystemConfig.class.getResource("/route.xml").getFile()));
		@SuppressWarnings("unchecked")
		List<List> routList = XMLShellFactory.newInstance().generateDomainObject(new String(routeXmlByte,charSet));
		log.info("读取配置文件完成...");
		return (ROUTE[]) routList.get(0).get(0);
	}
	
	public static NsrRoute getPtdzfpRouteUrl(String nsrsbh) throws Exception{
		NSRXX nsrxx = routeMap.get(nsrsbh);
		NsrRoute nsrRoute = null;
		if(nsrxx != null){
			nsrRoute = new NsrRoute();
//			nsrRoute.setIszzsbz(false);
//			nsrRoute.setDqmc(nsrxx.getDQMC());
//			nsrRoute.setMethod(nsrxx.getPTDZFPMETHOD());
//			nsrRoute.setUrl(nsrxx.getPTDZFPURL());
			nsrRoute = getRouteUrl(nsrxx, Boolean.FALSE);
		}else{
			log.error("未配置纳税人识别号为{}的配置路由信息", nsrsbh);
			throw new Exception("未配置纳税人识别号为"+nsrsbh+"的配置路由信息");
		}
		return nsrRoute;
	}
	
	public static NsrRoute getRouteUrl(String fpqqlsh,String nsrsbh,String kplx,String yfp_dm){
		NSRXX nsrxx = routeMap.get(nsrsbh);
		NsrRoute nsrRoute = new NsrRoute();;
		if(nsrxx != null){
			if(!nsrxx.getSFKTZZS().equals("") && nsrxx.getSFKTZZS().toUpperCase().equals(YES) && (nsrxx.getSFKTPTDZFP().equals("") || nsrxx.getSFKTPTDZFP().toUpperCase().equals(NO))){
				//如果开通增值税未开通普通电子发票则直接调用增值税电子发票接口
				log.warn("纳税人识别号为{}请求流水号为{}分配到增值税电子发票系统....",nsrsbh,fpqqlsh);
				nsrRoute = getRouteUrl(nsrxx, Boolean.TRUE);
//				nsrRoute.setDqmc(nsrxx.getDQMC());
//				nsrRoute.setIszzsbz(true);
//				nsrRoute.setUrl(nsrxx.getZZSDZFPURL());
//				nsrRoute.setMethod(nsrxx.getZZSDZFPMETHOD());
				return nsrRoute;
			}else if((nsrxx.getSFKTZZS().equals("") || nsrxx.getSFKTZZS().toUpperCase().equals(NO)) && !nsrxx.getSFKTPTDZFP().equals("") && nsrxx.getSFKTPTDZFP().toUpperCase().equals(YES)){
				//如果未开通增值税已开通普通电子发票则直接调用普通电子发票接口
				log.warn("纳税人识别号为{}请求流水号为{}分配到普通电子发票系统....",nsrsbh,fpqqlsh);
//				nsrRoute.setDqmc(nsrxx.getDQMC());
//				nsrRoute.setIszzsbz(false);
//				nsrRoute.setUrl(nsrxx.getPTDZFPURL());
//				nsrRoute.setMethod(nsrxx.getPTDZFPMETHOD());
				nsrRoute = getRouteUrl(nsrxx, Boolean.FALSE);
				return nsrRoute;
			}else if(!nsrxx.getSFKTZZS().equals("") && nsrxx.getSFKTZZS().toUpperCase().equals(YES) && !nsrxx.getSFKTPTDZFP().equals("") && nsrxx.getSFKTPTDZFP().toUpperCase().equals(YES)){
				//如果增值税开通并且普通电子发票也开通，则根据配置的增值税电子发票比例进行分流
				if(kplx.equals(KPLX_LZFP)){
					if(nsrxx.getZZSFPBL() != null && !nsrxx.getZZSFPBL().equals("")){//判断是否配置了比例
						String[] bl = nsrxx.getZZSFPBL().split("/");
						if(!checkBlIsEmpty(bl)){
							log.info("配置比例参数为{}",nsrxx.getZZSFPBL());
							double val = Double.parseDouble(fpqqlsh)%Integer.parseInt(bl[0]);
							log.info("取模值为{}",val);
							/*
							 * TODO 调整比例：<ZZSFPBL>1000/1</ZZSFPBL>
							 * 调整为大于号,多数票转至京东自营,少数转至京东云  -fwh -2017-07-10
							 * 多数调用地址：<ZZSDZFPURL>
							 * 少数调用地址：<PTDZFPURL>
							 */
							if(val > Integer.parseInt(bl[1])){ 
								//如果在比例范围内分配到增值税
								log.warn("纳税人识别号为{}请求流水号为{}分配到增值税电子发票系统....",nsrsbh,fpqqlsh);
								nsrRoute = getRouteUrl(nsrxx, Boolean.TRUE);
//								nsrRoute = getRouteUrl(nsrxx, Boolean.FALSE);
							}else{
								//如果范围外分配到普通电子发票
								log.warn("纳税人识别号为{}请求流水号为{}分配到普通电子发票系统....",nsrsbh,fpqqlsh);
								nsrRoute = getRouteUrl(nsrxx, Boolean.FALSE);
							}
						}else{
							log.error("纳税人识别号位{}普通电子发票已开通增值税普通电子发票也开通，但配置增值税电子发票的比例不正确，请检查...",nsrsbh);
							nsrRoute = null;
						}
					}else{
						log.error("纳税人识别号位{}普通电子发票已开通增值税普通电子发票也开通，但未配置增值税电子发票的比例...",nsrsbh);
						nsrRoute = null;
					}
				}else if(kplx.equals(KPLX_HZFP)){
					/*
					 * TODO 红票冲红业务流程： 都先走京东自营看是否存在对应蓝票，如果没有再转至京东云受理冲红。
					 * 此处注释原代码。冲红都直接转至京东自营。
					 * FWH -2017-07-21
					 */
					log.info("开具红字发票请求，原发票代码为{}", yfp_dm);
					nsrRoute = getRouteUrl(nsrxx, Boolean.TRUE);
					/*log.info("开具红字发票请求，原发票代码为{}", yfp_dm);
					Object obj = nsrxx.getFpxsMap().get(yfp_dm);
					if(obj == null){
						log.info("原发票代码为{}，转至京东自营系统开具红字发票", yfp_dm);
						nsrRoute = getRouteUrl(nsrxx, Boolean.TRUE);
					}else{
						log.info("原发票代码为{}，转至京东云系统开具红字发票", yfp_dm);
						nsrRoute = getRouteUrl(nsrxx, Boolean.FALSE);
					}*/
				}
			}else if((nsrxx.getSFKTZZS().equals("") || nsrxx.getSFKTZZS().equals(NO)) && (nsrxx.getSFKTPTDZFP().equals("") || nsrxx.getSFKTPTDZFP().equals(NO))){
				log.error("纳税人识别号为{}的纳税人未开通普通电子发票也未开通增值税普通电子发票", nsrsbh);
				nsrRoute = null;
			}
		}else{
			log.error("未配置纳税人识别号为{}的配置路由信息", nsrsbh);
			nsrRoute = null;
		}
		return nsrRoute;
	}
	/**判断字符串是否为空，如果为空返回true 不空返回false
	 * @param strs
	 * @return
	 */
	public static boolean checkBlIsEmpty(String[] strs){
		boolean res = false;
		try{
			if(strs!= null && strs.length == 2){
				for(int i =0 ;i<strs.length;i++){
					boolean b = StringUtil.isEmpty(strs[i]);
					if(b){
						res = true;
						break;
					}else{
						Integer.parseInt(strs[i]);
					}
				}
			}else{
				log.error("验证字符串为空");
				res = true;
			}
		}catch (Exception e) {
			log.error("验证比例错误",e);
		}
		return res;
	}
	
	
	public static NsrRoute getRouteUrl(NSRXX nsrxx,boolean isZzs){
		NsrRoute nsrRoute = new NsrRoute();
		if(isZzs){
			nsrRoute.setDqmc(nsrxx.getDQMC());
			nsrRoute.setDsptmb(nsrxx.getDSPTBM());
			nsrRoute.setIszzsbz(isZzs);
			nsrRoute.setUrl(nsrxx.getZZSDZFPURL());
			nsrRoute.setMethod(nsrxx.getZZSDZFPMETHOD());
			return nsrRoute;
		}else{
			nsrRoute.setDqmc(nsrxx.getDQMC());
			nsrRoute.setDsptmb(nsrxx.getDSPTBM());
			nsrRoute.setIszzsbz(isZzs);
			nsrRoute.setUrl(nsrxx.getPTDZFPURL());
			nsrRoute.setMethod(nsrxx.getPTDZFPMETHOD());
			return nsrRoute;
		}
	}
	
	/**
	 * @author skye.he
	 * @Data 20150819 created
	 * @param protocolDsptbm
	 * @return 电商平台编码是否在general.properties里配置
	 */
	public static boolean checkMerge(String protocolDsptbm){
		if(MERGEDSPTMap != null && MERGEDSPTMap.size() > 0 && MERGEDSPTMap.get(protocolDsptbm) != null && !MERGEDSPTMap.get(protocolDsptbm).equals("")){
			return true;
		}
		return false;
	}
}
