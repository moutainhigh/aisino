package com.aisino.einvoice.controller.impl;

import static com.aisino.common.util.EIProtocolFactory.getContent;

import java.io.ByteArrayOutputStream;
import java.sql.SQLException;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;

import com.aisino.common.util.Base64;
import com.aisino.common.util.Data;
import com.aisino.common.util.EIProtocolFactory;
import com.aisino.common.util.EShopCertificateBytesInfo;
import com.aisino.common.util.GlobalInfo;
import com.aisino.common.util.ReturnStateInfo;
import com.aisino.common.util.XmlPar;
import com.aisino.domain.SystemConfig;
import com.aisino.einvoice.controller.IEInvWebController;
import com.aisino.einvoice.service.EInvoiceBusinessMap;
import com.aisino.einvoice.service.IInvUploadService;
import com.aisino.einvoice.service.OrderBusinessMap;

/**
 * 获取电商客户端、电商web端 请求webservice协议接口类 Created by Schiffer.huang on 2014/11/12.
 */
public final class EInvWebControllerImpl implements IEInvWebController {

	private final static Logger LOGGER = LoggerFactory.getLogger(EInvWebControllerImpl.class);
	private IInvUploadService invUploadService;
	private EInvoiceBusinessMap businessHandler;
	// TODO 增加新的ip使用的队列
	private OrderBusinessMap orderHandler;
	@Value("${rabbit.queue.invoice.orderinfo}")
	private String orderinfoQueue;

	public String eiInterface(String requestMessage) {
		GlobalInfo globalInfo = null;
		try {
			/* 解析电商平台传递报文信息 */
			/* 预:从报文中解析出 taxpayerIdentifyNo、接口编码, */
			// final GlobalInfo pre=getInterfacePrior(requestMessage);
			Map<String, Object> map = null;
			EShopCertificateBytesInfo certificate = null;
			map = EIProtocolFactory.getInterface(requestMessage, null, false);
			/* 解析电商平台传递报文信息-结束 */

			globalInfo = (GlobalInfo) map.get(XmlPar.GLOBALINFO);
			final String theRequestEncryptCode = ((Data) map.get(XmlPar.DATA)).getEncryptCode();
			final String theRequestCodeType = ((Data) map.get(XmlPar.DATA)).getCodeType();

			/* 报文外层公共验证-开始 */
			if (globalInfo == null) {
				LOGGER.error("数据类型或格式不合法 全局对象为空,请确认数据传递是否正确！");
				return this.errorMessageResponse(globalInfo, "", XmlPar.RESPONSEFAIL, "数据类型或格式不合法,请求处理失败!");
			}

			// 如果appid等于空,或者是不等于DZFP和ZZS_PT_DZFP的返回错误
			if ("".equals(globalInfo.getAppId().trim()) || !(SystemConfig.PROTOCOL_APPID.equals(globalInfo.getAppId().trim()) || SystemConfig.PROTOCOL_APPIDTwo.equals(globalInfo.getAppId().trim()))) {
				LOGGER.error("数据类型或格式不合法,请确认应用标识传递是否正确！");
				return this.errorMessageResponse(globalInfo, "", XmlPar.ERRORCODE_APPID, "数据类型或格式不合法,请确认应用标识传递是否正确！");
			}
			if ("".equals(globalInfo.getInterfaceCode().trim()) || globalInfo.getInterfaceCode().length() < 10) {
				LOGGER.error("数据类型或格式不合法,请确认接口编码传递是否正确！");
				return this.errorMessageResponse(globalInfo, "", XmlPar.ERRORCODE_INTERFACECODE, "数据类型或格式不合法,请确认接口编码传递是否正确！");
			}
			if ("".equals(globalInfo.getUserName().trim()) || "".equals(globalInfo.getPassWord().trim()) || "".equals(globalInfo.getRequestCode().trim())) {
				LOGGER.error("数据类型或格式不合法,请确认平台编码和密码传递是否正确！");
				return this.errorMessageResponse(globalInfo, "", XmlPar.ERRORCODE_USERNAME, "数据类型或格式不合法,请确认平台编码和密码传递是否正确！");
			}

			/* 数据交换请求发出时间 格式YYYY-MM-DD HH24:MI:SS ss;判断字符串为空 2014年7月28日16:12:36 */
			if (StringUtils.isEmpty(globalInfo.getRequestTime()) || globalInfo.getRequestTime().length() < 18) {
				LOGGER.error("数据类型或格式不合法,请确认数据交换请求发出时间格式是否正确！");
				return this.errorMessageResponse(globalInfo, globalInfo.getUserName(), XmlPar.ERRORCODE_REQUESTTIME, "数据类型或格式不合法,数据处理失败");
			}

			/* 判断业务流水号是否正确;判断字符串为空 2014年7月28日16:12:36 */
			if (StringUtils.isEmpty(globalInfo.getDataExchangeId()) || globalInfo.getDataExchangeId().length() < 17) {
				LOGGER.error("数据类型或格式不合法,请确认数据交换流水号传递是否正确！");
				return this.errorMessageResponse(globalInfo, globalInfo.getUserName(), XmlPar.ERRORCODE_DATAEXCHANGEID, "数据类型或格式不合法,请确认数据交换流水号传递是否正确！");
			}
			/* 报文外层公共验证-结束 */

			/* 通过报文信息验证后,根据(获取)不同请求处理不同业务 */
			Map<String, Object> returnMap = null;

			final String interfaceCode = globalInfo.getInterfaceCode();

			/* 发票开具，及时获取发票请求信息，暂时必生成发票信息 */
			if (XmlPar.ECXML_FPKJ_BC_E_INV.equals(interfaceCode)) {
				returnMap = invUploadService.processEShopInvoice(map);
			}
			/* 发票信息查询 */
			else if (XmlPar.ECXML_FPKJ_QUERY_E_INV.equals(interfaceCode)) {
				returnMap = invUploadService.processGetInvoicePdf(map);
			}
			/* 获取电商平台信息（新增接口） */
			else if (XmlPar.EI_DSPTXX_U_EC_INTRA.equals(interfaceCode)) {
				returnMap = invUploadService.processGetEShopPlatform(map);
			}
			/* 获取电商平台企业信息（企业返回一个或者多个）（新增接口） */
			else if (XmlPar.EI_DSQYXX_U_EC_INTRA.equals(interfaceCode)) {
				returnMap = invUploadService.processGetEShopEnterprise(map);
			} else if (XmlPar.EI_CAZSXX_U_EC_INTRA.equals(interfaceCode)) {
				returnMap = invUploadService.processGetEShopCAInfo(map);
			} else {
				LOGGER.error("数据类型或格式不合法,请确认数据传递是否正确！");
				return this.errorMessageResponse(globalInfo, globalInfo.getUserName(), XmlPar.RESPONSEFAIL, "数据类型或格式不合法,请求处理失败!");
			}

			/* 返回操作-开始 */
			final ReturnStateInfo returnStateInfo = (ReturnStateInfo) returnMap.get(XmlPar.RETURNSTATEINFO);

			if (returnStateInfo == null) {
				LOGGER.error("数据类型或格式不合法,请确认数据传递是否正确！");
				return this.errorMessageResponse(globalInfo, globalInfo.getUserName(), XmlPar.RESPONSEFAIL, "数据类型或格式不合法,请求处理失败!");
			}

			final GlobalInfo globalInfoR = (GlobalInfo) returnMap.get(XmlPar.GLOBALINFO);
			final ReturnStateInfo returnStateInfoR = (ReturnStateInfo) returnMap.get(XmlPar.RETURNSTATEINFO);
			final Data dataR = (Data) returnMap.get(XmlPar.DATA);
			dataR.setEncryptCode(theRequestEncryptCode);
			dataR.setCodeType(theRequestCodeType);

			if (XmlPar.RESPONSEYYSSUCCESS.equals(returnStateInfo.getReturnCode())) {
				LOGGER.info("请求电商信息接口---结束----处理成功");
				String returnString = EIProtocolFactory.getContent(globalInfoR, returnStateInfoR, dataR, certificate);
				getRabbitTemplate().convertAndSend(map.get("mqEntity"));
				try {
					// 2017-07-19 宋雪冬 添加双rabbitMQ队列配置，用于把发票订单信息上传至大象平台查询发票
					getOrderTemplate().convertAndSend(orderinfoQueue, map.get("orderEntity"));
					
				} catch (Exception e) {
					
					LOGGER.error("订单信息放入orderinfoQueue失败,错误消息:"+e.getMessage());
				}
				
				return returnString;
			} else {
				LOGGER.info("请求电商信息接口---结束----处理失败");
				/*
				 * TRACY MARK:接口调用失败，ReturnCode为非"0000"值；
				 * 返回的Data未从成功后的协议bean构建,此时返回Data为原请求Data,按原方式继续加密;
				 * 此条件下交换报文部分被加解、解密。
				 */
				return getContent(globalInfoR, returnStateInfoR, dataR, certificate);
			}
			/* 返回操作-结束 */

		} catch (Exception e) {
			/*
			 * 对京东重复数据处理。注意：此修改只实用于京东
			 */
			// if("true".equals(SystemConfig.ZZS_DSPT_ISJD)){
			if (e.getMessage().indexOf("for key 'UNI_FP_KJ_FPQQLSH") > 0) {
				String e_msg = e.getMessage();
				String qqlsh = "";
				int start = e_msg.indexOf("Duplicate entry '");
				int end = e_msg.indexOf("' for key 'UNI_FP_KJ_FPQQLSH'");
				qqlsh = e_msg.substring(start + 17, end);
				LOGGER.error("---请求流水号" + qqlsh + "重复:---");
				final ReturnStateInfo returnState = new ReturnStateInfo();
				returnState.setReturnCode(XmlPar.RESPONSEYYSSUCCESS);
				final String returnMessage = "请求流水号重复";

				returnState.setReturnMessage(returnMessage);
				final GlobalInfo returnGlobalInfo = EIProtocolFactory.getGlobalInfo(globalInfo.getInterfaceCode(), "", globalInfo.getDataExchangeId());
				final Data returnData = EIProtocolFactory.getData(new ByteArrayOutputStream());
				return EIProtocolFactory.getContent(returnGlobalInfo, returnState, returnData, new EShopCertificateBytesInfo());
			}
			// }

			LOGGER.error("受理报文解析与处理异常捕捉-catch：", e);
			final ReturnStateInfo returnState = new ReturnStateInfo();
			returnState.setReturnCode(XmlPar.RESPONSEFAIL);
			final String returnMessage = (e instanceof SQLException || e instanceof DataIntegrityViolationException) ? "数据类型或格式不合法,数据处理失败" : e.getMessage();

			returnState.setReturnMessage(returnMessage);

			final GlobalInfo returnGlobalInfo = EIProtocolFactory.getGlobalInfo(globalInfo.getInterfaceCode(), "", globalInfo.getDataExchangeId());

			final Data returnData = EIProtocolFactory.getData(new ByteArrayOutputStream());

			// TRACY
			// MARK:接口调用失败，ReturnCode为非"0000"值，returnData未从返回协议bean构建,不需要针对Data加密.
			return EIProtocolFactory.getContent(returnGlobalInfo, returnState, returnData, new EShopCertificateBytesInfo());

		}
	}

	private String errorMessageResponse(GlobalInfo globalInfo, String eShopCode, String errorCode, String errorMessage) throws Exception {
		final ReturnStateInfo theState = new ReturnStateInfo();
		theState.setReturnCode(errorCode);
		theState.setReturnMessage(errorMessage);
		final GlobalInfo theGlobalInfo = EIProtocolFactory.getGlobalInfo(globalInfo.getInterfaceCode(), eShopCode, globalInfo.getDataExchangeId());
		final Data theData = EIProtocolFactory.getData(new ByteArrayOutputStream());
		return EIProtocolFactory.getContent(theGlobalInfo, theState, theData, new EShopCertificateBytesInfo());
	}

	/**
	 * <p>
	 * 下载发票pdf文件
	 * </p>
	 * 
	 * @see com.aisino.einvoice.controller.IEInvWebController#eiInterfpxz(String)
	 * @author: summer.wang
	 * @date: Created on 2015-8-14 下午2:31:04
	 */
	@Override
	public String eiInterfpxz(String requestMessage) {
		byte[] arryb = invUploadService.getPdfBytes(requestMessage);
		if (arryb != null && arryb.length > 0) {
			return Base64.encode(arryb);
		} else {
			return null;
		}
	}
	
	private RabbitTemplate getOrderTemplate() {
		return this.orderHandler.getRabbitTemplate();
	}

	public OrderBusinessMap getOrderHandler() {
		return orderHandler;
	}

	public void setOrderHandler(OrderBusinessMap orderHandler) {
		this.orderHandler = orderHandler;
	}

	private RabbitTemplate getRabbitTemplate() {
		return this.businessHandler.getRabbitTemplate();
	}

	public EInvoiceBusinessMap getBusinessHandler() {
		return businessHandler;
	}

	public void setBusinessHandler(EInvoiceBusinessMap businessHandler) {
		this.businessHandler = businessHandler;
	}
	
	public IInvUploadService getInvUploadService() {
		return invUploadService;
	}

	public void setInvUploadService(IInvUploadService invUploadService) {
		this.invUploadService = invUploadService;
	}
}
