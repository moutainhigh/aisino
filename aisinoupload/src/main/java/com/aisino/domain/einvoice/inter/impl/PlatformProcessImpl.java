package com.aisino.domain.einvoice.inter.impl;

import static org.joda.time.DateTime.now;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Decoder;
import com.aisino.common.util.Data;
import com.aisino.common.util.GlobalInfo;
import com.aisino.common.util.PassWordCheck;
import com.aisino.common.util.ProXml;
import com.aisino.common.util.ReturnStateInfo;
import com.aisino.common.util.XMLShellFactory;
import com.aisino.common.util.XmlPar;
import com.aisino.domain.base.service.impl.BaseService;
import com.aisino.domain.einvoice.inter.IPlatformProcess;
import com.aisino.domain.einvoice.resultService.IBusinessDataService;
import com.aisino.domain.sys.model.Route;
import com.aisino.log.domain.Password;
import com.aisino.protocol.bean.REQUEST_FPKJSCGLOBLE;
import com.aisino.protocol.bean.RESPONSE_BEAN;
import com.aisino.protocol.bean.RESPONSE_FPKJJG;
import com.aisino.web.util.HttpClientUtil;
import com.aisino.web.util.PlatformConstants;
import com.aisino.web.util.SystemConfig;
import com.aisino.web.util.WebServiceClient;

@Service
public class PlatformProcessImpl extends BaseService implements IPlatformProcess {

	@Autowired
	private IBusinessDataService businessDataService;
	private final static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(PlatformProcessImpl.class);

	@Override
	public boolean process(String busiType, REQUEST_FPKJSCGLOBLE REQUEST_FPKJSCGLOBLE, Route route, String zcm, String fp_id,int scpt_xl) {

		final DateTime begin1 = now();
		Date requestDate = new Date();
		GlobalInfo globalInfo = ProXml.getGlobalInfo(busiType, REQUEST_FPKJSCGLOBLE.getDSPTBM(), requestDate);
		Password password = PassWordCheck.passWordCreate(zcm);
		globalInfo.setPassWord(password.getSjm() + password.getPass());

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		XMLShellFactory fac = XMLShellFactory.newInstance();
		fac.saveXml(out, REQUEST_FPKJSCGLOBLE);
		Data data = ProXml.getData(out);
		data.setCodeType(PlatformConstants.PROTOCOL_CODETYPE);
		data.setEncryptCode(PlatformConstants.PROTOCOL_ENCRYPTCODE);

		final Long millSeconds1 = new Duration(begin1, now()).getMillis();
		LOGGER.warn("(上传51发票)组装请求报文的外层报文信息用时{}毫秒", millSeconds1);

		String reqXml = ProXml.getXml(globalInfo, new ReturnStateInfo(), data);
		/*
		 * 新业务逻辑：先上传大象慧云,再上传51平台;
		 * 如上传大象慧云失败,不再上传51平台,修改数据库上传失败状态,;
		 * 如上传大象慧云成功,再上传51平台,不论51平台上传结果如何,都修改数据库上传成功状态；
		 * @auth FWH
		 */
		//开始上传大象慧云/航信51平台
		if(scpt_xl==1){
		     return uploadDXHY(reqXml, route,fp_id);
		}else if(scpt_xl==2){
			 uploadPlatform(reqXml,route,fp_id);
			 return true;
		}else{
			return false;
		}
	}
	//TODO 上传大象慧云
	public boolean uploadDXHY(String reqXml,Route route,String fp_id){
		LOGGER.warn("开始传输至大象云端");
		try {
			final DateTime begin1 = now();
//			String responeMessageDXHY = HttpClientUtil.httpPost(SystemConfig.DXHYURL, reqXml);
			String responeMessageDXHY = resXml("1");//TOOD FWH 挡板服务
			final Long millSeconds1 = new Duration(begin1, now()).getMillis();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("(上传大象慧云)请求的报文为:"+ reqXml);
				LOGGER.debug("(上传大象慧云)请求的地址为:"+ SystemConfig.DXHYURL);
				LOGGER.debug("(上传大象慧云)返回的报文为:"+ responeMessageDXHY);
			}
			if(null!=responeMessageDXHY&&!responeMessageDXHY.equals("")){
				//2017-09-12 -解析返回报文
				Map<String, Object> returnMap = ProXml.getInterface(responeMessageDXHY);// 获取响应报文
				ReturnStateInfo returnInfo = (ReturnStateInfo) returnMap.get(XmlPar.RETURNSTATEINFO);
				String returnCode = returnInfo.getReturnCode();//返回编码值
				String returnMess= returnInfo.getReturnMessage();//返回消息
//				String returnCodeDXHY ="0000";
				if(XmlPar.RESPONSE_SSUCCESS.equals(returnCode)){
					LOGGER.warn("上传大象慧云成功,返回结果用时{}毫秒,", millSeconds1);
					businessDataService.modifyUploadResults("1",fp_id);//更新数据库状态
					LOGGER.warn("上传大象成功,修改数据库上传状态成功;ID="+fp_id);
//					LOGGER.warn("测试输出ID:{},消息:{}",fp_id,returnMess);
					//20170809 上傳大象成功后直接改庫后，再上传51平台
					//uploadPlatform(reqXml,route);
					return true;
				}else{
					//返回编码失败,更新上传状态为2(失败)
					businessDataService.modifyUploadResults("2",fp_id);
					LOGGER.warn("上传大象慧云失败,返回结果用时{}毫秒", millSeconds1);
					LOGGER.warn("上传大象失败,修改数据库上传失败状态;ID="+fp_id);
					LOGGER.warn("上传大象失败,失败原因为:"+returnMess);
				}
			}else{
				//未上传到大象慧云端,更新上传状态为2（失败）；
				String returnCodeDXHY=XmlPar.RESPONSE_FAIL;
				businessDataService.modifyUploadResults(returnCodeDXHY,fp_id);
				LOGGER.warn("上传大象失败,修改数据库上传状态失败;ID="+fp_id);
			}
		}catch (Exception e) {
			try {
				businessDataService.modifyUploadResults("2",fp_id);
			} catch (Exception e1) {
				LOGGER.error("更新上传状态异常");
			}
			LOGGER.error("调用上传大象云端接口异常:" + e.getMessage(), e);
		}
		return false;
	}
	//TODO 上传51平台
	public void uploadPlatform(String reqXml,Route route,String fp_id){
		try {
			// TODO 上传大象慧云成功后，开始上传51平台  调用税局外网webservice
			final DateTime begin5 = now();
			final Long millSeconds5 = new Duration(begin5, now()).getMillis();
//			String responeMessage = WebServiceClient.clientData(reqXml, route);//上传51平台
 			String responeMessage =resXml("2");// FWH-挡板服务
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("(上传51平台)请求的报文为:"+ reqXml);
				LOGGER.debug("(上传51平台)请求的地址为:"+ route);
				LOGGER.debug("(上传51平台)响应的报文为:"+ responeMessage);
			}
			Map<String, Object> returnMap = ProXml.getInterface(responeMessage);// 获取响应报文
			ReturnStateInfo returnInfo = (ReturnStateInfo) returnMap.get(XmlPar.RETURNSTATEINFO);
			String returnCode = returnInfo.getReturnCode();
			if (XmlPar.RESPONSE_SSUCCESS.equals(returnCode)) {// 上传51平台获取结果成功0000
				LOGGER.warn("上传51平台成功,返回结果用时{}毫秒", millSeconds5);
				/*
				 * TODO 更新时间字段-FWH-20171011
				 * 上传大象时间--send51time
				 * 推送京东时间--pushTime
				 * 上传航信51时间--updatetime
				 */
				businessDataService.modifyUpload51Results("3",fp_id);//更新数据库状态
			}else{
				LOGGER.warn("上传51平台失败,返回结果用时{}毫秒", millSeconds5);
				businessDataService.modifyUpload51Results("4",fp_id);//更新数据库状态
			}
		} catch (UnsupportedEncodingException e) {
			LOGGER.error("调用51上传接口字符集错误" + e.getMessage(), e);
			try {
				businessDataService.modifyUpload51Results("4",fp_id);
			} catch (Exception e1) {
				LOGGER.error("更新上传状态异常");
			}//更新数据库状态
		} catch (Exception e) {
			LOGGER.error("调用51上传接口错误" + e.getMessage(), e);
			try {
				businessDataService.modifyUpload51Results("4",fp_id);
			} catch (Exception e1) {
				LOGGER.error("更新上传状态异常");
			}//更新数据库状态
		}
	}
	//TODO 测试用
	public String resXml(String number){
		File file = null;
		if("1".equals(number)){
//			 file = new File("D:\\test\\51upload上传大象\\线上上传大象-成功响应报文.txt");
			 file = new File("/Users/fwh/Downloads/线上上传大象-成功响应报文.txt");
		}else if("2".equals(number)){
//			 file = new File("D:\\test\\51upload上传大象\\线上上传51-成功响应报文.txt");
			 file = new File("/Users/fwh/Downloads/线上上传51-成功响应报文.txt");
		}
		String requestXml = "";
			try {
				requestXml = FileUtils.readFileToString(file, "UTF-8");
			} catch (IOException e) {
				e.printStackTrace();
			}
		return requestXml;
	}

}
