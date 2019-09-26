/**
 * 文件名：ProXml.java
 *
 * 创建人：张士锋
 *
 * 创建时间：Jul 6, 2012 10:16:03 AM
 *
 * 版权所有：航天信息股份有限公司
 */
package com.aisino.common.util;

import static com.aisino.common.util.XmlPar.TERMINALCODE;
import static com.aisino.common.util.XmlPar.VERSION;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.Namespace;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.xml.sax.InputSource;

import com.aisino.log.domain.DataLogBean;
import com.aisino.log.domain.LogBean;
import com.aisino.log.domain.Password;

/**
 * <p>
 * 处理xml数据，转码，打zip包,生成xml
 * </p>
 * @author 张士锋
 * @version 1.0 Created on Jul 6, 2012 10:16:03 AM
 */
public class ProXml {
	private final static Logger  log = Logger.getLogger(ProXml.class);
	/**
	 * 
	 * <p>
	 * 转码
	 * </p>
	 * @param string
	 * @return String
	 * @author: 张士锋 
	 * @date: Created on Jul 6, 2012 12:52:12 PM
	 */
	public static String encode(String res){
		try{
		Base64 base = new Base64();
		return new String(base.encode(res.getBytes(StaticConstant.CHARSET)));
		}catch (Exception e) {
			log.error("未知：" + e);
			return "";
		}
	}
	
	public static byte[] encode(byte[] res){
		try{
		Base64 base = new Base64();
		return base.encode(res);
		}catch (Exception e) {
			log.error("未知：" + e);
			return null;
		}
	}

	/**
	 * 
	 * <p>
	 * 解码
	 * </p>
	 * 
	 * @param str
	 * @return
	 * @throws UnsupportedEncodingException
	 *             byte[]
	 * @author: 张士锋
	 * @date: Created on Jul 6, 2012 2:25:06 PM
	 */
	public static String decode(String str) throws UnsupportedEncodingException {
		return new String(new Base64().decode(str.getBytes()),StaticConstant.CHARSET);
	}
	
	public static byte[] decodeByte(byte[] str) {
		return new Base64().decode(str);
	}
	
	/**
	 * 
	 * <p>
	 * 解码
	 * </p>
	 * 
	 * @param str
	 * @return
	 * @throws UnsupportedEncodingException
	 *             byte[]
	 * @author: 张士锋
	 * @date: Created on Jul 6, 2012 2:25:06 PM
	 */
	public static byte[] decode(byte[] str) throws UnsupportedEncodingException {
		return new Base64().decode(str);
	}
	
	/**
	 * 
	 * <p>
	 * 传输数据与固定值比较大小
	 * </p>
	 * 
	 * @param byteRsp
	 * @param size
	 * @return boolean
	 * @author: 张士锋
	 * @date: Created on Jul 6, 2012 2:29:15 PM
	 */
	public static String isZip(String xml, int size) {
		if(xml == null){
			xml = "";
		}
		String isz = "0";
		if (xml.getBytes().length > 1024 * size){
			isz = "1";
		}
		return isz;
	}

	/**
	 * 
	 * <p>
	 * 根据原始生成传输数据 
	 * </p>
	 * 
	 * @param data
	 * @param globalInfo
	 * @param responseStateInfo
	 * @param content
	 * @return String
	 * @author: 张士锋
	 * @throws UnsupportedEncodingException 
	 * @date: Created on Jul 6, 2012 2:25:57 PM
	 */

	public static String getXml(GlobalInfo globalInfo, ReturnStateInfo returnStateInfo, com.aisino.common.util.Data data){
		return getXml(globalInfo, returnStateInfo, data, true);
	}
	/**
	 * 业务数据已加密，只封装外层xml
	 * @param globalInfo
	 * @param returnStateInfo
	 * @param data
	 * @return
	 */
	public static String getXmlOuter(GlobalInfo globalInfo,ReturnStateInfo returnStateInfo, com.aisino.common.util.Data data){
		return getXml(globalInfo, returnStateInfo, data, false);
	}
	
	
	private static String getXml(GlobalInfo globalInfo, ReturnStateInfo returnStateInfo, com.aisino.common.util.Data data, boolean flag){
		String resData = "";
		try {
		Element root = new Element(XmlPar.ROOT_BASE);
		Namespace ns = Namespace.getNamespace(XmlPar.NS_ONE);
		root.addNamespaceDeclaration(ns);
		Namespace ns1 = Namespace.getNamespace("xsi", XmlPar.NS_TWO);
		root.addNamespaceDeclaration(ns1);
		Namespace ns2 = Namespace.getNamespace("schemaLocation",XmlPar.NS_THREE);
		root.addNamespaceDeclaration(ns2);
		Document doc = new Document(root);
		Element eRoot = doc.getRootElement();
		eRoot.setAttribute("version", XmlPar.VERSION_NO);
	
		// 全局信息ns
		Element eGlobalInfo = new Element(XmlPar.GLOBALINFO);
		Element eAppID = new Element(XmlPar.APPID);
		eAppID.setText(globalInfo.getAppId());
		eGlobalInfo.addContent(eAppID);
		Element eInterfaceCode;
		eInterfaceCode = new Element(XmlPar.INTERFACECODE);
		eInterfaceCode.setText(globalInfo.getInterfaceCode());
		eGlobalInfo.addContent(eInterfaceCode);
		Element ERequestCode = new Element(XmlPar.REQUESTCODE);
		ERequestCode.setText(globalInfo.getRequestCode());
		eGlobalInfo.addContent(ERequestCode);
		Element eRequestTime = new Element(XmlPar.REQUESTTIME);
		eRequestTime.setText(globalInfo.getRequestTime());
		eGlobalInfo.addContent(eRequestTime);
		Element eResponseCode;
		eResponseCode = new Element(XmlPar.RESPONSECODE);
		eResponseCode.setText(globalInfo.getResponseCode());
		eGlobalInfo.addContent(eResponseCode);
		Element eDataExchangeId = new Element(XmlPar.DATAEXCHANGEID);
		eDataExchangeId.setText(globalInfo.getDataExchangeId());
		eGlobalInfo.addContent(eDataExchangeId);
		Element eUserName = new Element(XmlPar.USERNAME);
		eUserName.setText(globalInfo.getUserName());
		eGlobalInfo.addContent(eUserName);
		Element eNSRSBH = new Element(XmlPar.NSRSBH);
		eNSRSBH.setText(globalInfo.getTaxpayerIdentifyNo());
		eGlobalInfo.addContent(eNSRSBH);
		Element ePassWord = new Element(XmlPar.PASSWORD);
		ePassWord.setText(globalInfo.getPassWord());
		eGlobalInfo.addContent(ePassWord);
		Element eAUTHORIZATIONCODE = new Element(XmlPar.AUTHORIZATIONCODE);
        eAUTHORIZATIONCODE.setText(globalInfo.getAuthorizationCode());
        eGlobalInfo.addContent(eAUTHORIZATIONCODE);
        Element eVersion = new Element(VERSION);
        eVersion.setText(globalInfo.getVersion());
        eGlobalInfo.addContent(eVersion);
        Element eTerminalCode = new Element(TERMINALCODE);
        eTerminalCode.setText(globalInfo.getTerminalCode());
        eGlobalInfo.addContent(eTerminalCode);
        
		// 返回信息
		Element eReturnStateInfo = new Element(XmlPar.RETURNSTATEINFO);
		Element eReturnCode = new Element(XmlPar.RETURNCODE);
		eReturnCode.setText(returnStateInfo.getReturnCode());
		eReturnStateInfo.addContent(eReturnCode);
		Element eReturnMessage = new Element(XmlPar.RETURNMESSAGE);
		if (returnStateInfo.getReturnMessage() != null
				&& !returnStateInfo.getReturnMessage().equals("null")
				&& !returnStateInfo.getReturnMessage().equals("")) {
			eReturnMessage.setText(encode(returnStateInfo.getReturnMessage()));
		} else {
			eReturnMessage.setText("");
		}
		eReturnStateInfo.addContent(eReturnMessage);
		// 交换数据
		Element eData = new Element(XmlPar.DATA);
		Element eDataDescription;
		eDataDescription = new Element(XmlPar.DATADESCRIPTION);
		Element eZipCode = new Element(XmlPar.ZIPCODE);
		String iszip="";
		if(data!=null&&data.getZipCode()!=null){
			iszip=data.getZipCode();
		}else{
			iszip=isZip(data.getContent(), 10);
		}
		eZipCode.setText(iszip);
		eDataDescription.addContent(eZipCode);
		Element eEncryptCode = new Element(XmlPar.ENCRYPTCODE);
		eEncryptCode.setText(data.getEncryptCode());
		eDataDescription.addContent(eEncryptCode);
		Element eCodeType = new Element(XmlPar.CODETYPE);
		eCodeType.setText(data.getCodeType());
		eDataDescription.addContent(eCodeType);
		eData.addContent(eDataDescription);
		Element eContent = new Element(XmlPar.CONTENT);
		if(flag){
			String content=encodeData(data.getContent(), iszip, data.getEncryptCode(), globalInfo.getPassWord(),globalInfo.getUserName());
			eContent.setText(content);
		}else{
			eContent.setText(data.getData());
		}
		eData.addContent(eContent);
		root.addContent(eGlobalInfo);
		root.addContent(eReturnStateInfo);
		root.addContent(eData);
		ByteArrayOutputStream byteRsp = null;
		
		
		Format format = Format.getCompactFormat();
		format.setEncoding(StaticConstant.CHARSET);
		format.setIndent(" ");
		XMLOutputter xmlout = new XMLOutputter(format);
		byteRsp = new ByteArrayOutputStream();
		xmlout.output(doc, byteRsp);
		resData = byteRsp.toString(StaticConstant.CHARSET);
	} catch (IOException e) {
		log.error("未知：" + e);
		e.printStackTrace();
	}
	return resData;
}

	/**
	 * 取得全部明文信息
	 * 将传入的xml参数字符串转换成为map  by 苏海林
	 * @return Map
	 * @author: 张士锋 
	 * @throws Exception 
	 * @date: Created on Jul 7, 2012 3:20:54 PM
	 */
	
	public static Map getInterface(String requestMessage) throws Exception{
		return getInterface(requestMessage, true);
	}
	/**
	 * 只解密第一层协议
	 * @throws Exception 
	 */
	public static Map getInterfaceOuter(String requestMessage) throws Exception{
		return getInterface(requestMessage, false);
	}
	
	public static Map getInterface(String requestMessage,boolean flag) throws Exception {
		Map map = new HashMap();
		GlobalInfo globalInfo = new GlobalInfo();
		try {
			StringReader read = new StringReader(requestMessage);
			InputSource source = new InputSource(read);
			SAXBuilder sb = new SAXBuilder();
			Document doc = sb.build(source);
			Element root = doc.getRootElement();
			List node = root.getChildren();
			if (node != null && node.size() > 0) {
				for (int i = 0; i < node.size(); i++) {
					Element e1 = (Element) node.get(i);
					if (e1.getName().equals(XmlPar.GLOBALINFO)) {
						globalInfo.setAppId(e1.getChild(XmlPar.APPID).getText()==null?"":e1.getChild(XmlPar.APPID).getText());
						globalInfo.setInterfaceCode(e1.getChild(XmlPar.INTERFACECODE).getText()==null?"":e1.getChild(XmlPar.INTERFACECODE).getText());
						globalInfo.setRequestCode(e1.getChild(XmlPar.REQUESTCODE).getText()==null?"":e1.getChild(XmlPar.REQUESTCODE).getText());
						globalInfo.setRequestTime(e1.getChild(XmlPar.REQUESTTIME).getText()==null?"":e1.getChild(XmlPar.REQUESTTIME).getText());
						globalInfo.setResponseCode(e1.getChild(XmlPar.RESPONSECODE).getText()==null?"":e1.getChild(XmlPar.RESPONSECODE).getText());
						globalInfo.setDataExchangeId(e1.getChild(XmlPar.DATAEXCHANGEID).getText()==null?"":e1.getChild(XmlPar.DATAEXCHANGEID).getText());
						globalInfo.setUserName(e1.getChild(XmlPar.USERNAME).getText()==null?"":e1.getChild(XmlPar.USERNAME).getText());
						globalInfo.setPassWord(e1.getChild(XmlPar.PASSWORD).getText()==null?"":e1.getChild(XmlPar.PASSWORD).getText());
						map.put(e1.getName(), globalInfo);
					}
					if (e1.getName().equals(XmlPar.RETURNSTATEINFO)) {
						ReturnStateInfo returneStateInfo = new ReturnStateInfo();
						returneStateInfo.setReturnCode(e1.getChild(XmlPar.RETURNCODE).getText()==null?"":e1.getChild(XmlPar.RETURNCODE).getText());
						if (e1.getChild(XmlPar.RETURNMESSAGE).getText() != null&& !e1.getChild(XmlPar.RETURNMESSAGE).getText().equals("")) {
							returneStateInfo.setReturnMessage(decode(e1.getChild(XmlPar.RETURNMESSAGE).getText())==null?"":e1.getChild(XmlPar.RETURNMESSAGE).getText());
						} else {
							returneStateInfo.setReturnMessage("");
						}
						map.put(e1.getName(), returneStateInfo);
					}
					if (e1.getName().equals(XmlPar.DATA)) {
						com.aisino.common.util.Data data = new com.aisino.common.util.Data();
						data.setDataDescription(e1.getChild(XmlPar.DATADESCRIPTION).getText()==null?"":e1.getChild(XmlPar.DATADESCRIPTION).getText());
						data.setZipCode(e1.getChild(XmlPar.DATADESCRIPTION).getChild(XmlPar.ZIPCODE).getText()==null?"":e1.getChild(XmlPar.DATADESCRIPTION).getChild(XmlPar.ZIPCODE).getText());
						data.setEncryptCode(e1.getChild(XmlPar.DATADESCRIPTION).getChild(XmlPar.ENCRYPTCODE).getText()==null?"":e1.getChild(XmlPar.DATADESCRIPTION).getChild(XmlPar.ENCRYPTCODE).getText());
						data.setCodeType(e1.getChild(XmlPar.DATADESCRIPTION).getChild(XmlPar.CODETYPE).getText()==null?"":e1.getChild(XmlPar.DATADESCRIPTION).getChild(XmlPar.CODETYPE).getText());
						log.debug(e1.getChild(XmlPar.CONTENT).getText());
//						if(flag){
//							String content=decodeData(e1.getChild(XmlPar.CONTENT).getText(), data.getZipCode(), data.getEncryptCode(), globalInfo.getPassWord(),globalInfo.getUserName());
//							data.setContent(content);
//						}else{
//							data.setData(e1.getChild(XmlPar.CONTENT).getText());
//						}
						Base64 b=new Base64();
						byte[] bs=b.decode(e1.getChild(XmlPar.CONTENT).getText().getBytes());
					
						data.setContent(new String(bs));
//						if(log.isDebugEnabled())
//						    log.debug(data.getContent()+"+=============请求数据");
						map.put(e1.getName(), data);
					}
				}
			}
		} catch (JDOMException e) {
			log.error("未知：" + e);
			e.printStackTrace();
		} catch (IOException e) {
			log.error("未知：" + e);
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * 
	 * <p>
	 * 取得系统当前格式化时间
	 * </p>
	 * 
	 * @param args
	 *            void
	 * @author: 张士锋
	 * @date: Created on Jul 9, 2012 10:04:24 AM
	 */
	public static String getCurDate(String formatStyle) {
		DateFormat format1 = new SimpleDateFormat(formatStyle);
		return format1.format(new Date());
	}
	
	public static String getFormatDate(Date date,String formatStyle) {
		DateFormat format1 = new SimpleDateFormat(formatStyle);
		return format1.format(date);
	}
	
	public static String decodeData(String org,String zipCode,String encryptCode,String password,String dsptbm) throws Exception{
		byte[] temp_coutnent;
			if (org != null && !org.equals("")) {
				temp_coutnent = org.getBytes(StaticConstant.CHARSET);
				temp_coutnent = decode(temp_coutnent);
				if ("1".equals(zipCode)) {
					temp_coutnent = GZipUtils.decompress(temp_coutnent);
				}
				if("1".equals(encryptCode)){
					temp_coutnent = TripleDESUtil.decryptMode(password.substring(StaticConstant.SJMYBEFORELENG, password.length()), temp_coutnent);
				}else if("2".equals(encryptCode)){
					if(StaticConstant.CA_STATE.equals("1")){
						byte[] qyDer=(byte[]) StaticConstant.caPublicKeyMap.get(dsptbm);
						if(qyDer != null){
							SJCaCryptUtil sjutil = new SJCaCryptUtil(qyDer);
							temp_coutnent=sjutil.decrypt(temp_coutnent);
						}
					}else if(StaticConstant.CA_STATE.equals("2")){
						QYCaCryptUtil qyutil = new QYCaCryptUtil(FileUtils.readFileToByteArray(new File(StaticConstant.CA_TEST_PATH+StaticConstant.TAX_DER)));//动态读取税局端的公钥
						temp_coutnent=qyutil.decrypt(temp_coutnent);
					}
				}
				
				/**
				 * author:	peterli 
				 * modify date: 2013-11-26 21:44:01
				 */
				String content = new String(temp_coutnent,StaticConstant.CHARSET);
//				log.info("UTF-8编码下的content xml协议---------------------------" + content);
				
				return content;
				
			}else{
				return "";
			}
	}
	
	public static String encodeData(String org,String zipCode,String encryptCode,String password,String dsptbm) throws UnsupportedEncodingException{
		byte[] temp_content = org==null?"".getBytes():org.getBytes(StaticConstant.CHARSET);
		try {
			if (StringUtils.isNotEmpty(org)) {
				if("1".equals(encryptCode)){
					temp_content = TripleDESUtil.encryptMode(password.substring(StaticConstant.SJMYBEFORELENG, password.length()), temp_content);
				}else if("2".equals(encryptCode)){
					if(StaticConstant.CA_STATE.equals("1")){
						byte[] qyDer=(byte[]) StaticConstant.caPublicKeyMap.get(dsptbm);
						if(qyDer != null){
							SJCaCryptUtil sjutil = new SJCaCryptUtil(qyDer);
							temp_content=sjutil.encrypt(temp_content);
						}
					}else if(StaticConstant.CA_STATE.equals("2")){
						QYCaCryptUtil qyutil = new QYCaCryptUtil(FileUtils.readFileToByteArray(new File(StaticConstant.CA_TEST_PATH+StaticConstant.TAX_DER)));	//动态读取税局端的公钥
						temp_content=qyutil.encrypt(temp_content);
					}
				}
				if ("1".equals(zipCode)) {
					temp_content = GZipUtils.compress(temp_content);
				} 
				
				return new String(encode(temp_content),StaticConstant.CHARSET);
			} 
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return "";
	}

	/**
	 * 
	 * <p>
	 * 取得返回信息对像
	 * </p>
	 * 
	 * @param args
	 *            void
	 * @author: 张士锋
	 * @date: Created on Jul 10, 2012 8:56:35 AM
	 */
	public static ReturnStateInfo getReturnStateInfo(String returnStateCode,
			String returnMessage) {
		ReturnStateInfo returnStateInfo = new ReturnStateInfo();
		returnStateInfo.setReturnCode(returnStateCode);
		returnStateInfo.setReturnMessage(returnMessage);
		return returnStateInfo;
	}

	/**
	 * 
	 * <p>
	 * 取得交互数据对像
	 * </p>
	 * 
	 * @param args
	 *            void
	 * @author: 张士锋
	 * @date: Created on Jul 10, 2012 9:03:04 AM
	 */
	public static com.aisino.common.util.Data getData(ByteArrayOutputStream out) {
		com.aisino.common.util.Data data = new com.aisino.common.util.Data();
		try {
			data.setEncryptCode(StaticConstant.PROTOCOL_ENCRYPTCODE);
			data.setCodeType(StaticConstant.PROTOCOL_CODETYPE);
			String temp_str = new String(out.toByteArray(),StaticConstant.CHARSET);
			data.setZipCode(ProXml.isZip(temp_str, 10));
			if (temp_str != null && !temp_str.equals("null")&& !temp_str.equals("")) {
				temp_str = temp_str.substring(temp_str.indexOf("<ROOT>") + 6,temp_str.lastIndexOf("</ROOT>"));
			} else {
				temp_str = "";
			}
			data.setContent(temp_str);
		} catch (Exception e) {
			log.error("未知：" + e);
			e.printStackTrace();
		}
		return data;
	}
	

	public static String replaceStr(String s) {
		return s.replaceAll(" ", "");
	}

	public static List getDataRoot(String xml) throws Exception {
        String xmlRootStart = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>  <ROOT>";
        String xmlRootEnd = "</ROOT>";
        if (xml.startsWith("<?xml")) {
            XMLShellFactory factory = XMLShellFactory.newInstance();
            return (List) factory.generateDomainObject(xml).get(0);
        } else {
            XMLShellFactory factory = XMLShellFactory.newInstance();
            return (List) factory.generateDomainObject(xmlRootStart + xml + xmlRootEnd).get(0);
        }
	    
	}
	
	/**
     * 构建GlobalInfo对象
     * @param map map中必须包含key为“ywbm”的接口编码、key为”dsptbm“的电商平台编码
     * @param globalInfo 必须实例化
     * @param data
     */
	public static void getGlobalInfo(Map map, GlobalInfo globalInfo) {
		Password password;
		if(map.get(XmlPar.MAP_KEY_ZCM)==null || map.get(XmlPar.MAP_KEY_ZCM).toString().equals("")){
			password = PassWordCheck.passWordCreate(StaticConstant.DEFAULTZCM);
		}else{
			password = PassWordCheck.passWordCreate(map.get(XmlPar.MAP_KEY_ZCM).toString());
		}
        globalInfo.setAppId(XmlPar.WLFP);
        globalInfo.setInterfaceCode((String) map.get(XmlPar.MAP_KEY_YWBM));
        globalInfo.setPassWord(password.getSjm()+password.getPass());
        globalInfo.setRequestTime(ProXml.getCurDate("yyyyMMddHHmmss"));
        globalInfo.setResponseCode(XmlPar.SJBM);
        globalInfo.setRequestCode((String) map.get(XmlPar.MAP_KEY_DSPTBM));
        globalInfo.setUserName((String) map.get("dsptbm"));
        globalInfo.setDataExchangeId((String) map.get(XmlPar.MAP_KEY_DSPTBM) + (String) map.get(XmlPar.MAP_KEY_YWBM) + ProXml.getCurDate("yyyyMMdd") + getRdom(9));
    }
	
	public static GlobalInfo getGlobalInfo(String busiType,String dsptbm,Date requestTime) {
		GlobalInfo globalInfo=new GlobalInfo();
        globalInfo.setAppId(XmlPar.WLFP);
        globalInfo.setInterfaceCode(busiType);
        globalInfo.setRequestTime(ProXml.getCurDate("yyyyMMddHHmmss"));
        globalInfo.setResponseCode(XmlPar.SJBM);
        globalInfo.setRequestCode(dsptbm);
        globalInfo.setUserName(dsptbm);
        return globalInfo;
    }
	
	public static GlobalInfo getGlobalInfo(String busiType,String dsptbm,String dataExId) {
		GlobalInfo globalInfo=new GlobalInfo();
        globalInfo.setAppId(XmlPar.WLFP);
        globalInfo.setInterfaceCode(busiType);
        globalInfo.setRequestTime(ProXml.getCurDate("yyyyMMddHHmmss"));
        globalInfo.setResponseCode(XmlPar.SJBM);
        globalInfo.setRequestCode(dsptbm);
        globalInfo.setUserName(dsptbm);
        globalInfo.setDataExchangeId(dataExId);
        return globalInfo;
    }
	
	
    
	/**
	 * 获取随机数
	 * @param ws
	 * @return
	 * @author peterli
	 * @date Sep 23, 2013
	 */
    public static String getRdom(int ws) {
        Random r = new Random();
        String nums = Integer.toString((Math.abs(r.nextInt(Integer.MAX_VALUE))));
        if (nums.length() >= ws)
            return nums.substring(nums.length() - ws);
        else
            return StringUtils.leftPad(nums, ws, "0");
    }
    
    /**
	 * 
	 * <p>日志bean</p>
	 * 
	 * @param map
	 * @return LogBean
	 * @author: 张士锋
	 * @date: Created on Jul 26, 2012 11:29:51 AM
	 */
	public static LogBean getLogBean(Map map,String type) {
		LogBean logBean = new LogBean();
		try{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		GlobalInfo globalInfoR = (GlobalInfo) map.get(XmlPar.GLOBALINFO);
		ReturnStateInfo returnStateInfoR = (ReturnStateInfo) map.get(XmlPar.RETURNSTATEINFO);
		if(returnStateInfoR == null){
			returnStateInfoR = new ReturnStateInfo();
		}
		logBean.setAppId(globalInfoR.getAppId());
		logBean.setDataEcChangeId(globalInfoR.getDataExchangeId());
		logBean.setInterFaceCode(globalInfoR.getInterfaceCode());
		logBean.setRequestCode(globalInfoR.getRequestCode());
		try{
			logBean.setRequestTime(sdf.parse(globalInfoR.getRequestTime()));
		}catch (Exception e) {
			log.error("未知：" + e);
			logBean.setRequestTime(new Date());
		}
		logBean.setResponseCode(globalInfoR.getResponseCode());
		logBean.setReturnCode(returnStateInfoR.getReturnCode());
		logBean.setReturnMessage(returnStateInfoR.getReturnMessage());
		if(type.equals("response")){
			logBean.setReturnMessage(ProXml.encode(returnStateInfoR.getReturnMessage()));
		}
		return logBean;
		}catch(Exception ce)
		{
			log.error("获取请求或响应的报文数据失败，请检查报文数据！"+ce.getMessage());
			ce.printStackTrace();
			logBean.setAppId(XmlPar.WLFP);
			logBean.setDataEcChangeId("");
			logBean.setInterFaceCode("");
			logBean.setRequestCode("");
			logBean.setRequestTime(new Date());
			logBean.setResponseCode("");
			logBean.setReturnCode("");
			logBean.setReturnMessage("获取请求或响应的报文数据失败，请检查报文数据！");
			if(type.equals("response"))
			{
				logBean.setReturnMessage(ProXml.encode("获取请求或响应的报文数据失败，请检查报文数据！"));
			}
			return logBean;
		}
	}

	/**
	 * 
	 * <p>数据bean</p>
	 * @param map
	 * @return DataLogBean
	 * @author: 张士锋
	 * @date: Created on Jul 26, 2012 11:30:05 AM
	 */
	public static DataLogBean getDataLogBean(Map map) {
		DataLogBean dataLogBean = new DataLogBean();
		try{
		com.aisino.common.util.Data dataR = (com.aisino.common.util.Data) map.get(XmlPar.DATA);
		GlobalInfo globalInfoR = (GlobalInfo) map.get(XmlPar.GLOBALINFO);
		dataLogBean.setZipCode(dataR.getZipCode());
		dataLogBean.setResponseCode(globalInfoR.getResponseCode());
		dataLogBean.setRequestCode(globalInfoR.getRequestCode());
		dataLogBean.setEncryptCode(dataR.getEncryptCode());
		dataLogBean.setDataExChangeId(globalInfoR.getDataExchangeId());
		dataLogBean.setInterFaceCode(globalInfoR.getInterfaceCode());
		if(dataR.getContent()!=null){
			dataLogBean.setContent((dataR.getContent()).getBytes());
		}
		return dataLogBean;
		}catch(Exception ce)
		{
			log.error("未知：" + ce.getMessage());
			ce.printStackTrace();
			dataLogBean.setZipCode("");
			dataLogBean.setResponseCode("");
			dataLogBean.setRequestCode("");
			dataLogBean.setEncryptCode("0");
			dataLogBean.setDataExChangeId("");
			dataLogBean.setContent("".getBytes());
			return dataLogBean;
		}
	}
	
	public static String beanToxml(Object obj) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        XMLShellFactory.newInstance().saveXml(out, obj);
        Data data = ProXml.getData(out);
        return data.getContent();
    }
public static void main(String[] args) {
	String s="CiAgPFJFUVVFU1RfRlBLSlhYIGNsYXNzPSJSRVFVRVNUX0ZQS0pYWCI+CiAgICA8RlBLSlhYX0ZQVFhYIGNsYXNzPSJGUEtKWFhfRlBUWFgiPgogICAgICA8RlBRUUxTSD4wMDAwMDAyMDE0MDIxODE1NDY0MzwvRlBRUUxTSD4KICAgICAgPERTUFRCTT4xMjExMDM1NTwvRFNQVEJNPgogICAgICA8TlNSU0JIPjMxMDEwODA2OTM5MjIyNTwvTlNSU0JIPgogICAgICA8TlNSTUM+yc+6o7fJxaO8r7TvtefX08nMzvHT0M/euavLvjwvTlNSTUM+CiAgICAgIDxOU1JEWkRBSC8+CiAgICAgIDxTV0pHX0RNLz4KICAgICAgPERLQlo+MDwvREtCWj4KICAgICAgPFBZRE0+MDAwMDE8L1BZRE0+CiAgICAgIDxLUFhNPsP3z7g8L0tQWE0+CiAgICAgIDxYSEZfTlNSU0JIPjMxMDEwODA2OTM5MjIyNTwvWEhGX05TUlNCSD4KICAgICAgPFhIRk1DPsnPuqO3ycWjvK+077Xn19PJzM7x09DP3rmry748L1hIRk1DPgogICAgICA8R0hGTUM+MTFzc8rUytQ8L0dIRk1DPgogICAgICA8R0hGX05TUlNCSD48L0dIRl9OU1JTQkg+CiAgICAgIDxHSEZfRFo+sbG+qcrQuqO17cf4PC9HSEZfRFo+CiAgICAgIDxHSEZfR0RESD4wMTAtMDAwMDAwMDA8L0dIRl9HRERIPgogICAgICA8R0hGX1NKPjEzMTIyMjIyMjIyPC9HSEZfU0o+CiAgICAgIDxHSEZfRU1BSUw+PC9HSEZfRU1BSUw+CiAgICAgIDxIWV9ETS8+CiAgICAgIDxIWV9NQz48L0hZX01DPgogICAgICA8S1BZPr6ptqvJzLPHPC9LUFk+CiAgICAgIDxTS1k+vqm2q8nMs8c8L1NLWT4KICAgICAgPEtQUlE+MjAxNC0wMi0xOCAxNTo0Njo0MzwvS1BSUT4KICAgICAgPEtQTFg+MTwvS1BMWD4KICAgICAgPFlGUF9ETT48L1lGUF9ETT4KICAgICAgPFlGUF9ITT48L1lGUF9ITT4KICAgICAgPENIWVkvPgogICAgICA8S1BISkpFPjIzOTkuMDwvS1BISkpFPgogICAgICA8Qlo+sbjXojwvQlo+CiAgICAgIDxCWVpEMS8+CiAgICAgIDxCWVpEMi8+CiAgICAgIDxCWVpEMy8+CiAgICAgIDxCWVpENC8+CiAgICAgIDxCWVpENS8+CiAgICAgIDxUU0NIQlo+PC9UU0NIQlo+CiAgICAgIDxHSEZfU0YvPgogICAgICA8R0hGUVlMWD4wMzwvR0hGUVlMWD4KICAgICAgPENaRE0+MTA8L0NaRE0+CiAgICA8L0ZQS0pYWF9GUFRYWD4KICAgIDxGUEtKWFhfWE1YWFMgY2xhc3M9IkZQS0pYWF9YTVhYOyIgc2l6ZT0iMSI+CiAgICAgIDxGUEtKWFhfWE1YWD4KICAgICAgICA8WE1NQz7Gu7n7o6hBcHBsZaOpIGlQYWQgbWluaSBNRDUzMUNIL0EgNy4506K058a9sOW158TUIKOoMTZHIFdJRkmw5qOpsNfJq9GkwPY3Ljkg06K058/Uyr7GwaOs0KHHyc3i0M4gx+HLydXGztWjoTwvWE1NQz4KICAgICAgICA8WE1EVz48L1hNRFc+CiAgICAgICAgPEdHWEg+PC9HR1hIPgogICAgICAgIDxYTVNMPjE8L1hNU0w+CiAgICAgICAgPFhNREo+MjM5OS4wMDwvWE1ESj4KICAgICAgICA8WE1KRT4yMzk5LjAwPC9YTUpFPgogICAgICAgIDxCWVpEMS8+CiAgICAgICAgPEJZWkQyLz4KICAgICAgICA8QllaRDMvPgogICAgICAgIDxCWVpENC8+CiAgICAgICAgPEJZWkQ1Lz4KICAgICAgPC9GUEtKWFhfWE1YWD4KICAgIDwvRlBLSlhYX1hNWFhTPgogICAgPEZQS0pYWF9ERFhYIGNsYXNzPSJGUEtKWFhfRERYWCI+CiAgICAgIDxEREg+MTM5MjcwOTU4OTczNzwvRERIPgogICAgICA8REREQVRFPjIwMTQtMDItMTggMTU6NDY6NDM8L0REREFURT4KICAgICAgPFRIREgvPgogICAgPC9GUEtKWFhfRERYWD4KICAgIDxGUEtKWFhfRERNWFhYUyBjbGFzcz0iRlBLSlhYX0RETVhYWDsiIHNpemU9IjAiLz4KICA8L1JFUVVFU1RfRlBLSlhYPgo=";
	Base64 b=new Base64();
	byte[] bs=b.decode(s.getBytes());
	try {
		System.out.println(new String(bs));
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
	
	
}
