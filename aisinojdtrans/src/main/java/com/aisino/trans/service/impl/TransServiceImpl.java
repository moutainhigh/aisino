package com.aisino.trans.service.impl;

import static org.joda.time.DateTime.now;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import com.aisino.client.WebserviceClient;
import com.aisino.common.util.XMLShellFactory;
import com.aisino.domain.NsrRoute;
import com.aisino.protocol.bean.FPKJXX_XMXX;
import com.aisino.protocol.bean.NSRXX;
import com.aisino.protocol.bean.REQUEST_COMMON_FPKJ;
import com.aisino.protocol.bean.REQUEST_FPKJXX;
import com.aisino.protocol.bean.REQUEST_ZZFP;
import com.aisino.trans.service.ITransService;
import com.aisino.trans.util.Data;
import com.aisino.trans.util.DzfpInterfaceBean;
import com.aisino.trans.util.GlobalInfo;
import com.aisino.trans.util.HttpClientUtil;
import com.aisino.trans.util.OuterLayerProtocol;
import com.aisino.trans.util.ProXml;
import com.aisino.trans.util.ProtoclConvert;
import com.aisino.trans.util.ReturnStateInfo;
import com.aisino.trans.util.SystemConfig;
import com.aisino.trans.util.XmlPar;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

@Service
public class TransServiceImpl implements ITransService {
	private Logger log = LoggerFactory.getLogger(TransServiceImpl.class);

	@Deprecated
	public String trans(String xml) {
		String resXml = "";
		NsrRoute nsrRoute;
		try {
			final DateTime begin = now();
			Map map = ProXml.getInterface(xml, true);
			final long millSeconds = new Duration(begin, now()).getMillis();
			log.info("解析外层报文耗时{}毫秒", millSeconds);
			if (CollectionUtils.isEmpty(map)) {
				log.error("数据包传输错误，未能解析报文....");
				ReturnStateInfo returnStateInfo = new ReturnStateInfo();
				returnStateInfo.setReturnCode(XmlPar.BUSI_FAIL);
				returnStateInfo.setReturnMessage("数据包传输错误，未能解析报文");
				return ProXml.getTransXml(new GlobalInfo(), returnStateInfo,new Data());
			}
			GlobalInfo globalInfo = (GlobalInfo) map.get(XmlPar.GLOBALINFO);
			Data data = (Data) map.get(XmlPar.ZZSDATA);
			final DateTime begin1 = now();
			List list = (List) ProXml.getDataRoot(data.getContent());
			final long millSeconds1 = new Duration(begin1, now()).getMillis();
			log.info("解析内层报文耗时{}毫秒", millSeconds1);
			REQUEST_FPKJXX REQUEST_FPKJXX = (REQUEST_FPKJXX) list.get(0);
			String fpqqlsh = REQUEST_FPKJXX.getFPKJXX_FPTXX().getFPQQLSH();
			final DateTime begin2 = now();
			nsrRoute = SystemConfig.getRouteUrl(fpqqlsh,globalInfo.getTaxpayerId(),REQUEST_FPKJXX.getFPKJXX_FPTXX().getKPLX(),REQUEST_FPKJXX.getFPKJXX_FPTXX().getYFP_DM());
			final long millSeconds2 = new Duration(begin2, now()).getMillis();
			log.info("获取{}路由耗时{}毫秒",nsrRoute == null ? "null" : nsrRoute.isIszzsbz() ? "增值税" : "普通电子发票", millSeconds2);
			if (nsrRoute != null && nsrRoute.isIszzsbz()) {
				// 进行报文转换
				final DateTime begin3 = now();
				REQUEST_COMMON_FPKJ REQUEST_COMMON_FPKJ = ProtoclConvert.convertComm(REQUEST_FPKJXX);
				final long millSeconds3 = new Duration(begin3, now()).getMillis();
				log.info("对象拷贝耗时{}毫秒", millSeconds3);
				final DateTime begin4 = now();
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				XMLShellFactory.newInstance().saveXml(out, REQUEST_COMMON_FPKJ);
				Data resData = ProXml.getData(out);
				final long millSeconds4 = new Duration(begin4, now()).getMillis();
				log.info("对象转换为报文{}耗时{}毫秒", resData.getContent(), millSeconds4);
				globalInfo.setInterfaceCode(XmlPar.INTERFACE_FPKJ_CODE);
				final DateTime begin5 = now();
				String reqXml = ProXml.getXml(globalInfo,(ReturnStateInfo) map.get(XmlPar.RETURNSTATEINFO),resData);
				final long millSeconds5 = new Duration(begin5, now()).getMillis();
				log.info("转换外层报文为{}耗时{}毫秒", reqXml, millSeconds5);
				log.warn("将原报文{}转换为增值税报文为{},并转发至增值税受理", xml, reqXml);
				final DateTime begin6 = now();
				resXml = WebserviceClient.invokeApi(reqXml, nsrRoute);
				final long millSeconds6 = new Duration(begin6, now()).getMillis();
				log.info("调用{}地区增值税电子发票受理返回报文为{}耗时{}毫秒", nsrRoute.getDqmc(),resXml, millSeconds6);
			} else if (nsrRoute != null && !nsrRoute.isIszzsbz()) {
				xml = ProXml.getPtdzfpXml(globalInfo,(ReturnStateInfo) map.get(XmlPar.RETURNSTATEINFO),data, true);
				log.warn("转发至普通电子发票报文为{}", xml);
				final DateTime begin7 = now();
				resXml = WebserviceClient.invokeApi(xml, nsrRoute);
				final long millSeconds7 = new Duration(begin7, now()).getMillis();
				log.info("调用{}地区普通电子发票受理返回报文为{}耗时{}毫秒", nsrRoute.getDqmc(),resXml, millSeconds7);
				final DateTime begin8 = now();
				Map resMap = new HashMap();
				resMap = ProXml.getPtdzfpInterface(resXml, false);
				GlobalInfo resGlo = (GlobalInfo) resMap.get(XmlPar.GLOBALINFO);
				resGlo.setTerminalCode("0");
				resGlo.setVersion("2.0");
				resXml = ProXml.getXmlOuter(resGlo,(ReturnStateInfo) resMap.get(XmlPar.RETURNSTATEINFO),(Data) resMap.get(XmlPar.DATA));
				final long millSecounds8 = new Duration(begin8, now()).getMillis();
				log.info("调用{}地区普通电子发票受理返回报文转换为{}耗时{}毫秒,并返回给京东业务系统....",nsrRoute.getDqmc(), resXml, millSecounds8);
			} else if (nsrRoute == null) {
				log.error("未配置纳税人路由信息");
				ReturnStateInfo returnStateInfo = new ReturnStateInfo();
				returnStateInfo.setReturnCode(XmlPar.BUSI_FAIL);
				returnStateInfo.setReturnMessage("未配置纳税人路由信息，请联系电子发票系统管理员..");
				return ProXml.getTransXml(new GlobalInfo(), returnStateInfo,new Data());
			}
		} catch (Exception e) {
			log.error("数据处理失败....", e);
			ReturnStateInfo returnStateInfo = new ReturnStateInfo();
			returnStateInfo.setReturnCode(XmlPar.BUSI_FAIL);
			returnStateInfo.setReturnMessage("数据处理失败");
			return ProXml.getTransXml(new GlobalInfo(), returnStateInfo,new Data());
		}
		if (resXml != null && !resXml.equals("")) {
			return resXml;
		} else {
			String ly = nsrRoute.getDqmc()+ (nsrRoute.isIszzsbz() ? "增值税电子发票" : "普通电子发票");
			log.error("调用{}接口失败，返回空", ly);
			ReturnStateInfo returnStateInfo = new ReturnStateInfo();
			returnStateInfo.setReturnCode(XmlPar.BUSI_FAIL);
			returnStateInfo.setReturnMessage("开具" + ly + "失败");
			return ProXml.getTransXml(new GlobalInfo(), returnStateInfo,new Data());
		}
	}
	
	public String transReq(String xml) {
		
		String respnoseXml ="";
		GlobalInfo globalInfo = OuterLayerProtocol.getOuterProtocol(xml);
		//发票开具
		if(XmlPar.ECXML_FPKJ_BC_E_INV.equals(globalInfo.getInterfaceCode())){
			respnoseXml = transReqFpKj(xml);
			
		//发票下载
		}else if(XmlPar.ECXML_PDFXZ_BC_E_INV.equals(globalInfo.getInterfaceCode())){
			respnoseXml = transReqFpxz(xml);
		}else{
			ReturnStateInfo returnStateInfo = new ReturnStateInfo();
			returnStateInfo.setReturnCode(XmlPar.BUSI_FAIL);
			returnStateInfo.setReturnMessage("该接口不存在");
			log.error("该接口不存在");
			respnoseXml = ProXml.getTransXml(globalInfo, returnStateInfo, new Data());
		}
		
		return respnoseXml;
	}
	
	/**
	 * <p>发票开具的协议转换</p>
	 * 
	 * @param xml
	 * @return String
	 * @author: summer.wang
	 * @date: Created on 2015-8-18 下午2:23:19
	 */
	public String transReqFpKj(String xml){
		GlobalInfo globalInfo = OuterLayerProtocol.getOuterProtocol(xml);
		String resXml = null;
		NsrRoute nsrRoute = null;
		if (globalInfo.getTerminalCode() == null||"".equals(globalInfo.getTerminalCode())) {// 如果接入终端类型标识为null说明是老版本协议，直接转发至老版本sl
			try {
				Data data = OuterLayerProtocol.getOuterProtocolData(xml, globalInfo.getPassWord());
				final DateTime begin1 = now();
				List list = (List) ProXml.getDataRoot(data.getContent());
				final long millSeconds1 = new Duration(begin1, now()).getMillis();
				log.info("解析内层报文耗时{}毫秒", millSeconds1);
				REQUEST_FPKJXX REQUEST_FPKJXX = (REQUEST_FPKJXX) list.get(0);
				nsrRoute = SystemConfig.getPtdzfpRouteUrl(REQUEST_FPKJXX.getFPKJXX_FPTXX().getNSRSBH());
				final DateTime begin7 = now();
				resXml = WebserviceClient.invokeApi(xml, nsrRoute);
				final long millSeconds7 = new Duration(begin7, now()).getMillis();
				log.info("调用{}地区普通电子发票受理返回报文为{}耗时{}毫秒", nsrRoute.getDqmc(),resXml, millSeconds7);
			} catch (Exception e) {
				ReturnStateInfo returnStateInfo = new ReturnStateInfo();
				returnStateInfo.setReturnCode(XmlPar.BUSI_FAIL);
				returnStateInfo.setReturnMessage(e.getMessage());
				log.error("开具普通电子发票失败",e);
				resXml = ProXml.getTransXml(globalInfo, returnStateInfo, new Data());
			}
		} else {
			try {
				Data data = OuterLayerProtocol.getOuterProtocolData(xml, globalInfo.getPassWord());
				final DateTime begin1 = now();
				List list = (List) ProXml.getDataRoot(data.getContent());
				final long millSeconds1 = new Duration(begin1, now()).getMillis();
				log.info("解析内层报文耗时{}毫秒", millSeconds1);
				REQUEST_FPKJXX REQUEST_FPKJXX = (REQUEST_FPKJXX) list.get(0);
				nsrRoute = SystemConfig.getRouteUrl(REQUEST_FPKJXX.getFPKJXX_FPTXX().getFPQQLSH(), globalInfo.getTaxpayerId(),REQUEST_FPKJXX.getFPKJXX_FPTXX().getKPLX(),REQUEST_FPKJXX.getFPKJXX_FPTXX().getYFP_DM());
				if(nsrRoute.isIszzsbz()){
					/**
					 * @author skye.he
					 * @Date 20150819
					 * @see  对普通发票冲红操作（商品行合并）
					 */
					if(SystemConfig.checkMerge(globalInfo.getUserName())){
						 Object yfpdmObj = SystemConfig.routeMap.get(globalInfo.getTaxpayerId()).getFpxsMap().get(REQUEST_FPKJXX.getFPKJXX_FPTXX().getYFP_DM());
						if(REQUEST_FPKJXX.getFPKJXX_FPTXX().getKPLX().equals(SystemConfig.KPLX_HZFP) && yfpdmObj != null){
							FPKJXX_XMXX[] newXMXX = ProtoclConvert.joinOne(REQUEST_FPKJXX.getFPKJXX_XMXXS()) ;//商品行合并
							REQUEST_FPKJXX.setFPKJXX_XMXXS(newXMXX);
						}
					}
					// 进行报文转换
					//TODO 整理-转换报文 FWH-2017-07-21
					String reqXml =convert( REQUEST_FPKJXX, globalInfo, xml,nsrRoute);
					final DateTime begin6 = now();
					resXml = WebserviceClient.invokeApi(reqXml, nsrRoute);//得到响应报文
					final long millSeconds6 = new Duration(begin6, now()).getMillis();
					log.info("调用{}地区增值税电子发票受理返回报文为{}耗时{}毫秒", nsrRoute.getDqmc(),resXml, millSeconds6);
					/*
					 * TODO 解析返回报文code值，如果返回值是9137表示京东自营系统冲红对应的蓝票不存在;
					 * 则转至京东云系统冲红 -2017-07-21 fwh
					 */
					Map<String, Object> returnMap = ProXml.getInterface(resXml);// 获取响应报文
					ReturnStateInfo returnInfo = (ReturnStateInfo) returnMap.get(XmlPar.RETURNSTATEINFO);
					String returnCode = returnInfo.getReturnCode();
					String returnMess=returnInfo.getReturnMessage();
					log.warn("调用京东自营开票系统响应码为："+returnCode+",响应信息为："+returnMess);
					
					//TODO 冲红无对应蓝票转至自营新系统冲红-FWH-20171023  -由于武汉成都拆分库做此操作 
					if("9157".equals(returnCode)){
						NsrRoute ptdzfpRouteUrl = SystemConfig.getPtdzfpRouteUrl(globalInfo.getTaxpayerId());
						resXml = WebserviceClient.invokeApi(reqXml, ptdzfpRouteUrl);//得到响应报文
					}
					
					//TODO 冲红无对应蓝票转至京东云系统开票
					if("9137".equals(returnCode)){
						/* TODO 冲红修改 -FWH-2017-09-28
						 * 业务逻辑：第一次冲红没有对应蓝票,转至纸质发票系统；
						 * 如纸质系统有对应蓝票 将请求报文发送至纸票系统；
						 * 如无对应蓝票，转回自营系统特殊冲红。
						 
						String Yfpdm =REQUEST_FPKJXX.getFPKJXX_FPTXX().getYFP_DM();
						String Yfphm =REQUEST_FPKJXX.getFPKJXX_FPTXX().getYFP_HM();
						String XFH_nsrsbh=REQUEST_FPKJXX.getFPKJXX_FPTXX().getNSRSBH();
						//转成json
						REQUEST_ZZFP zzfp = new REQUEST_ZZFP();
						zzfp.setXHF_NSRSBH(XFH_nsrsbh);
						zzfp.setYFP_DM(Yfpdm);
						zzfp.setYFP_HM(Yfphm);
						String jsonString = JSONObject.toJSONString(zzfp);
						//发送请求报文http
						try {
							NsrRoute ptdzfpRouteUrl = SystemConfig.getPtdzfpRouteUrl(globalInfo.getTaxpayerId());
							String reqUrl = ptdzfpRouteUrl.getUrl();
			                String res = HttpClientUtil.httpPost(reqUrl, jsonString);//发送至纸票系统
			                //解析响应报文
			                REQUEST_ZZFP info = (REQUEST_ZZFP) JSON.parseObject(res, REQUEST_ZZFP.class);//返回的信息
			                String resCode =info.getRETURNCODE();
			                String resMes  =info.getRETURNMESSAGE();
			                log.warn("纸质发票系统返回CODE值:"+resCode+",描述信息为:"+resMes);
			                //判断返回code值
			                if(resCode.equals("0000")){
			                	//如果有，传递请求报文
			                }else{
			                	//特殊冲红（传入特殊冲红标识，用于判断是否特殊冲红） --传到自营受理工程
			                	resXml = WebserviceClient.invokeApi(reqXml, nsrRoute);//得到响应报文
			                	resXml = WebserviceClient.invokeApi(reqXml, nsrRoute);//得到响应报文
			                }
						} catch(Exception e) {
							log.error("发送至纸票系统异常:"+e.getMessage());
				            //return Boolean.FALSE;
						}*/
						REQUEST_FPKJXX.FPKJXX_FPTXX.setQD_BZ("0");//补全京东云字段<qd_bz>
						//转到京东云系统开票
						if("2".equals(REQUEST_FPKJXX.FPKJXX_FPTXX.getKPLX())){
							if(REQUEST_FPKJXX.FPKJXX_FPTXX.getBZ().contains("退货折让红票,原")){
								REQUEST_FPKJXX.FPKJXX_FPTXX.setBZ(REQUEST_FPKJXX.FPKJXX_FPTXX.getBZ().replaceAll("退货折让红票,原", "对应正数"));//京东云签章会有对BZ字段的校验，要以标准备注规则来设置备注
							}
							if(null==REQUEST_FPKJXX.FPKJXX_FPTXX.getBZ()||"".equals(REQUEST_FPKJXX.FPKJXX_FPTXX.getBZ())){
								REQUEST_FPKJXX.FPKJXX_FPTXX.setBZ("对应正数发票代码:"+REQUEST_FPKJXX.FPKJXX_FPTXX.getYFP_DM()+",号码:"+REQUEST_FPKJXX.FPKJXX_FPTXX.getYFP_HM());
							}
							if(null==REQUEST_FPKJXX.FPKJXX_DDXX.getTHDH()||"".equals(REQUEST_FPKJXX.FPKJXX_DDXX.getTHDH())){
								REQUEST_FPKJXX.FPKJXX_DDXX.setTHDH(REQUEST_FPKJXX.FPKJXX_DDXX.getDDH());
							}
						}
						log.warn("京东自营系统冲红对应的蓝票不存在，转至京东云开票系统，"+"原发票代码为："+REQUEST_FPKJXX.getFPKJXX_FPTXX().getYFP_DM()+",原发票号码为："+REQUEST_FPKJXX.getFPKJXX_FPTXX().getYFP_HM());
						NsrRoute ptdzfpRouteUrl = SystemConfig.getPtdzfpRouteUrl(globalInfo.getTaxpayerId());
						reqXml =convert(REQUEST_FPKJXX, globalInfo, xml,ptdzfpRouteUrl);
						resXml = WebserviceClient.invokeApi(reqXml, ptdzfpRouteUrl);//得到响应报文
					}
					//TODO 转至纸质发票系统开票-FWH-2017-09-28
					/*if("9147".equals(returnCode)){
						log.warn("纸票系统蓝票存在,转至纸票系统,"+"原发票代码为:"+REQUEST_FPKJXX.getFPKJXX_FPTXX().getYFP_DM()+",原发票号码为:"+REQUEST_FPKJXX.getFPKJXX_FPTXX().getYFP_HM());
						NsrRoute ptdzfpRouteUrl = SystemConfig.getPtdzfpRouteUrl(globalInfo.getTaxpayerId());
						reqXml =convert(REQUEST_FPKJXX, globalInfo, xml,ptdzfpRouteUrl);
						resXml = WebserviceClient.invokeApi(reqXml, ptdzfpRouteUrl);//得到响应报文
					}*/
					
				}else{
					//TODO 调用京东云开票系统 -FWH -2017-07-13
					REQUEST_FPKJXX.FPKJXX_FPTXX.setQD_BZ("0");//补全京东云字段<qd_bz> <QDXMMC>可为空
					String reqXml =convert(REQUEST_FPKJXX, globalInfo, xml,nsrRoute);
					final DateTime begin7 = now();
					resXml = WebserviceClient.invokeApi(reqXml, nsrRoute);//得到响应报文
					//resXml=resJDY(xml, nsrRoute);
					final long millSeconds7 = new Duration(begin7, now()).getMillis();
					log.info("调用京东云开票系统{}地区普通电子发票受理返回报文为{}耗时{}毫秒", nsrRoute.getDqmc(),resXml, millSeconds7);
				}
			} catch (Exception e) {
				ReturnStateInfo returnStateInfo = new ReturnStateInfo();
				returnStateInfo.setReturnCode(XmlPar.BUSI_FAIL);
				String message = nsrRoute==null?"数据处理失败":nsrRoute.isIszzsbz()?"开具增值税电子发票失败":"开具普通电子发票失败";
				returnStateInfo.setReturnMessage(message);
				log.error(message,e);
				resXml = ProXml.getTransXml(globalInfo, returnStateInfo, new Data());
			}
		}
		return resXml;
	}
	
	//TODO 整理-报文转换
	public String convert(REQUEST_FPKJXX REQUEST_FPKJXX,GlobalInfo globalInfo,String xml,NsrRoute nsrRoute) throws Exception{
		final DateTime begin3 = now();
        if ("2".equals(REQUEST_FPKJXX.getFPKJXX_FPTXX().getKPLX())) {
            REQUEST_FPKJXX = ProtoclConvert.removeFullDiscount(REQUEST_FPKJXX);
        }
		REQUEST_COMMON_FPKJ REQUEST_COMMON_FPKJ = ProtoclConvert.convertComm(REQUEST_FPKJXX);
		final long millSeconds3 = new Duration(begin3, now()).getMillis();
		log.info("对象拷贝耗时{}毫秒", millSeconds3);
		final DateTime begin4 = now();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		/*
		 * /TODO 京东自营 价税分离后传递给受理
		 * 京东云 要传递价税分离前的报文
		 * FWH -2017-07-21
		 */
		if(nsrRoute.isIszzsbz()){
			XMLShellFactory.newInstance().saveXml(out, REQUEST_COMMON_FPKJ);
		}else{
			XMLShellFactory.newInstance().saveXml(out, REQUEST_FPKJXX);
		}
		Data resData = ProXml.getData(out);
		final long millSeconds4 = new Duration(begin4, now()).getMillis();
		log.info("对象转换为报文{}耗时{}毫秒", resData.getContent(), millSeconds4);
		//  FWH interface 京东自营 INTERFACE_FPKJ_CODE   京东云  ECXML_FPKJ_BC_E_INV  
		if(nsrRoute.isIszzsbz()){
			globalInfo.setInterfaceCode(XmlPar.INTERFACE_FPKJ_CODE);
		}else{
			globalInfo.setInterfaceCode(XmlPar.ECXML_FPKJ_BC_E_INV);
		}
		final DateTime begin5 = now();
		String reqXml = ProXml.getXml(globalInfo,new ReturnStateInfo(),resData);
		final long millSeconds5 = new Duration(begin5, now()).getMillis();
		log.info("转换外层报文为{}耗时{}毫秒", reqXml, millSeconds5);
		log.warn("将原报文{}转换为增值税报文为{},并转发至增值税受理", xml, reqXml);
		return reqXml;
	}
	
	
	
	/*//调用京东云开票
	public String resJDY(String xml,NsrRoute nsrRoute) throws Exception{
		String[] splitOne = xml.split("<content>");
		String contentFront = splitOne[0];
		String[] splitTwo = xml.split("</content>");
		String contentRear = splitTwo[1];
		String datagram = Datagram(xml);// 整理内层请求报文
		String reqXml = contentFront + datagram + contentRear;//拼接请求报文
		String resXml = WebserviceClient.invokeApi(reqXml, nsrRoute);
		return resXml;
	}
	
	 *  base64重整报文,用于发送京东云受理系统  -FWH -2017-07-13
	 * 由于京东云请求报文和京东自营请求报文字段不同，需整理后再发送
	 
	public String Datagram(String xml){
			String decodeXml;
	        String[] split = xml.split("<content>");
	        String s = split[1];
	        String[] split1 = s.split("</content>");
	        String encodeXml = split1[0];//获得base64内层报文
	        //解base64并添加字段
	        decodeXml = getFromBase64(encodeXml);
	        
	         * 冲红时候：判断备注和退货单号两个字段是否有值，无则填充值
	         
	        String KPLX = decodeXml.substring(decodeXml.indexOf("<KPLX>")+6, decodeXml.lastIndexOf("</KPLX>"));//获取原始KPLX
	        if("2".equals(KPLX)){
	        	String BZ = decodeXml.substring(decodeXml.indexOf("<BZ>")+4, decodeXml.lastIndexOf("</BZ>"));//获取原始BZ
	        	String YFP_DM = decodeXml.substring(decodeXml.indexOf("<YFP_DM>")+8, decodeXml.lastIndexOf("</YFP_DM>"));
	        	String YFP_HM = decodeXml.substring(decodeXml.indexOf("<YFP_HM>")+8, decodeXml.lastIndexOf("</YFP_HM>"));
	        	String THDH = decodeXml.substring(decodeXml.indexOf("<THDH>")+6, decodeXml.lastIndexOf("</THDH>"));
	        	String DDH = decodeXml.substring(decodeXml.indexOf("<DDH>")+5, decodeXml.lastIndexOf("</DDH>"));
	        	if(null==BZ||"".equals(BZ)){
	        		decodeXml.replace("<BZ></BZ>","<BZ>对应正数发票代码:"+YFP_DM+",号码:"+YFP_HM+"</BZ>");
	        	}
	        	if(null==THDH||"".equals(THDH)){
	        		decodeXml.replace(THDH,DDH);
	        	}
	        }
	        //添加字段
	        String replaceXml = decodeXml.replace("</CZDM>", "</CZDM>\n" + "\t\t\t<QD_BZ>0</QD_BZ>\n" +"\t\t\t<QDXMMC></QDXMMC>");
//	        String replaceReqXml = replaceXml.replace("</KCE>", "</KCE>\n" + "\t\t\t<QD_BZ>0</QD_BZ>\n" +"\t\t\t<QDXMMC></QDXMMC>");
	        //加密
	        String base64Xml = getBase64(replaceXml);
	        return "<content>"+base64Xml+"</content>";
	}
	//base64解密
	 public static String getFromBase64(String s) {  
	        byte[] b = null;  
	        String result = null;  
	        if (s != null) {  
	            BASE64Decoder decoder = new BASE64Decoder();  
	            try {  
	                b = decoder.decodeBuffer(s);  
	                result = new String(b, "utf-8");  
	            } catch (Exception e) {  
	                e.printStackTrace();  
	            }  
	        }  
	        return result;  
	    }  
	
	//base64加密
	 public static String getBase64(String str) {
	        byte[] b = null;
	        String s = null;
	        try {
	            b = str.getBytes("utf-8");
	        } catch (UnsupportedEncodingException e) {
	            e.printStackTrace();
	        }
	        if (b != null) {
	            s = new BASE64Encoder().encode(b);
	        }
	        return s;
	    }*/
	 
	/**
	 * <p>发票信息查询协议转换</p>
	 * 
	 * @param xml
	 * @return String
	 * @author: summer.wang
	 * @date: Created on 2015-8-14 下午5:09:59
	 */
	public String transReqFpxz(String xml) {
		String resXml = null;
		GlobalInfo globalInfo = OuterLayerProtocol.getOuterProtocol(xml);
		globalInfo.setInterfaceCode(XmlPar.ECXML_FPKJ_QUERY_E_INV);
		Data data = OuterLayerProtocol.getOuterProtocolData(xml, globalInfo.getPassWord());
		try {
			NSRXX nsrxx = SystemConfig.routeMap.get(globalInfo.getTaxpayerId());
			NsrRoute nsrRoute = SystemConfig.getRouteUrl(nsrxx, Boolean.TRUE);
			String reqXml = ProXml.getXml(globalInfo,new ReturnStateInfo(),data);
			resXml = WebserviceClient.invokeApi(reqXml, nsrRoute);
		} catch (Exception e) {
			ReturnStateInfo returnStateInfo = new ReturnStateInfo();
			returnStateInfo.setReturnCode(XmlPar.BUSI_FAIL);
			returnStateInfo.setReturnMessage("发票查询失败");
			log.error("发票查询失败",e);
			resXml = ProXml.getTransXml(globalInfo, returnStateInfo, new Data());
		}
		return resXml;
	}
	
	public String getRequestXml(List list,GlobalInfo globalInfo){
		REQUEST_FPKJXX REQUEST_FPKJXX = (REQUEST_FPKJXX) list.get(0);
		REQUEST_COMMON_FPKJ REQUEST_COMMON_FPKJ;
		String reqXml = null;
		try {
			REQUEST_COMMON_FPKJ = ProtoclConvert.convertComm(REQUEST_FPKJXX);
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			XMLShellFactory.newInstance().saveXml(out, REQUEST_COMMON_FPKJ);
			Data resData = ProXml.getData(out);
			reqXml = ProXml.getXml(globalInfo,new ReturnStateInfo(),resData);
		} catch (Exception e) {
			ReturnStateInfo returnStateInfo = new ReturnStateInfo();
			returnStateInfo.setReturnCode(XmlPar.BUSI_FAIL);
			returnStateInfo.setReturnMessage("转换协议失败");
			log.error("转换协议失败",e);
			reqXml = ProXml.getTransXml(globalInfo, returnStateInfo, new Data());
		}
		return reqXml;
	}
	
	public static void main(String[] args) throws IOException {
		byte[] xml = FileUtils.readFileToByteArray(new File("d:\\text.xml"));
		String req = new String(xml, "UTF-8");
		Map mao = ProXml.getInterface(req, true);
		System.out.println(mao.size());
	}

	public Map<String, Object> YHDTransreq(DzfpInterfaceBean dzfpInterfaceBean) {
		Map<String, Object> resMap = new HashMap<String, Object>();
		GlobalInfo globalInfo = dzfpInterfaceBean.getGlobalInfo();
		String resXml = null;
		NsrRoute nsrRoute = null;
		if (globalInfo.getTerminalCode() == null) {// 如果接入终端类型标识为null说明是老版本协议，直接转发至老版本sl
			try {
				Data data = dzfpInterfaceBean.getData();
				final DateTime begin1 = now();
				List list = (List) ProXml.getDataRoot(data.getContent());
				final long millSeconds1 = new Duration(begin1, now()).getMillis();
				log.info("解析内层报文耗时{}毫秒", millSeconds1);
				REQUEST_FPKJXX REQUEST_FPKJXX = (REQUEST_FPKJXX) list.get(0);
				nsrRoute = SystemConfig.getPtdzfpRouteUrl(REQUEST_FPKJXX.getFPKJXX_FPTXX().getNSRSBH());
				final DateTime begin7 = now();
				String xml = ProXml.getXmlOuter(globalInfo, dzfpInterfaceBean.getReturneStateInfo(), data);
				resXml = WebserviceClient.invokeApi(xml, nsrRoute);
				final long millSeconds7 = new Duration(begin7, now()).getMillis();
				log.info("调用{}地区普通电子发票受理返回报文为{}耗时{}毫秒", nsrRoute.getDqmc(),resXml, millSeconds7);
			} catch (Exception e) {
				ReturnStateInfo returnStateInfo = new ReturnStateInfo();
				returnStateInfo.setReturnCode(XmlPar.BUSI_FAIL);
				returnStateInfo.setReturnMessage(e.getMessage());
				log.error("开具普通电子发票失败",e);
				resXml = ProXml.getTransXml(globalInfo, returnStateInfo, new Data());
			}
		} else {
			try {
				Data data = dzfpInterfaceBean.getData();
				final DateTime begin1 = now();
				List list = (List) ProXml.getDataRoot(data.getContent());
				final long millSeconds1 = new Duration(begin1, now()).getMillis();
				log.info("解析内层报文耗时{}毫秒", millSeconds1);
				REQUEST_FPKJXX REQUEST_FPKJXX = (REQUEST_FPKJXX) list.get(0);
				nsrRoute = SystemConfig.getRouteUrl(REQUEST_FPKJXX.getFPKJXX_FPTXX().getFPQQLSH(), globalInfo.getTaxpayerId(),REQUEST_FPKJXX.getFPKJXX_FPTXX().getKPLX(),REQUEST_FPKJXX.getFPKJXX_FPTXX().getYFP_DM());
				if(nsrRoute.isIszzsbz()){
					// 进行报文转换
					final DateTime begin3 = now();
					REQUEST_COMMON_FPKJ REQUEST_COMMON_FPKJ = ProtoclConvert.convertComm(REQUEST_FPKJXX);
					final long millSeconds3 = new Duration(begin3, now()).getMillis();
					log.info("对象拷贝耗时{}毫秒", millSeconds3);
					final DateTime begin4 = now();
					ByteArrayOutputStream out = new ByteArrayOutputStream();
					XMLShellFactory.newInstance().saveXml(out, REQUEST_COMMON_FPKJ);
					Data resData = ProXml.getData(out);
					final long millSeconds4 = new Duration(begin4, now()).getMillis();
					log.info("对象转换为报文{}耗时{}毫秒", resData.getContent(), millSeconds4);
					globalInfo.setInterfaceCode(XmlPar.INTERFACE_FPKJ_CODE);
					final DateTime begin5 = now();
					String reqXml = ProXml.getXml(globalInfo,new ReturnStateInfo(),resData);
					final long millSeconds5 = new Duration(begin5, now()).getMillis();
					log.info("转换外层报文为{}耗时{}毫秒", reqXml, millSeconds5);
					log.warn("将原报文{}转换为增值税报文为{},并转发至增值税受理", "一号店协议bean", reqXml);
					final DateTime begin6 = now();
					resXml = WebserviceClient.invokeApi(reqXml, nsrRoute);
					final long millSeconds6 = new Duration(begin6, now()).getMillis();
					log.info("调用{}地区增值税电子发票受理返回报文为{}耗时{}毫秒", nsrRoute.getDqmc(),resXml, millSeconds6);
				}else{
					final DateTime begin7 = now();
					String xml = ProXml.getXml(globalInfo, dzfpInterfaceBean.getReturneStateInfo(), data);
//					String xml = ProXml.getXmlOuter(globalInfo, dzfpInterfaceBean.getReturneStateInfo(), data);
					resXml = WebserviceClient.invokeApi(xml, nsrRoute);
					final long millSeconds7 = new Duration(begin7, now()).getMillis();
					log.info("调用{}地区普通电子发票受理返回报文为{}耗时{}毫秒", nsrRoute.getDqmc(),resXml, millSeconds7);
				}
			} catch (Exception e) {
				ReturnStateInfo returnStateInfo = new ReturnStateInfo();
				returnStateInfo.setReturnCode(XmlPar.BUSI_FAIL);
				String message = nsrRoute==null?"数据处理失败":nsrRoute.isIszzsbz()?"开具增值税电子发票失败":"开具普通电子发票失败";
				returnStateInfo.setReturnMessage(message);
				log.error(message,e);
				//resXml = ProXml.getTransXml(globalInfo, returnStateInfo, new Data());
				resMap.put(XmlPar.RETURNSTATEINFO, returnStateInfo);
				resMap.put(XmlPar.DATA, new Data());
				return resMap;
			}
		}
		Map<String,Object> map = ProXml.getInterfaceOuter(resXml);
		resMap.put(XmlPar.RETURNSTATEINFO, map.get(XmlPar.RETURNSTATEINFO));
		resMap.put(XmlPar.DATA, map.get(XmlPar.DATA));
		return resMap;
	}
}
