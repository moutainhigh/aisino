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

import java.io.ByteArrayOutputStream;
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
import org.apache.commons.lang.StringUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.Namespace;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.InputSource;

import com.aisino.log.domain.DataLogBean;
import com.aisino.log.domain.LogBean;
import com.aisino.log.domain.Password;

/**
 * <p>
 * 处理xml数据，转码，打zip包,生成xml
 * </p>
 * 
 * @author 张士锋
 * @version 1.0 Created on Jul 6, 2012 10:16:03 AM
 */
public class ProXml_DSCLD {
	private final static Logger log = LoggerFactory
			.getLogger(ProXml_DSCLD.class);

	/**
	 * 
	 * <p>
	 * 转码
	 * </p>
	 * 
	 * @param string
	 * @return String
	 * @author: 张士锋
	 * @date: Created on Jul 6, 2012 12:52:12 PM
	 */
	public static String encode(String res) {
		try {
			Base64 base = new Base64();
			return new String(base.encode(res.getBytes(StaticConstant.CHARSET)));
		} catch (Exception e) {
			log.error("未知：" + e);
			return "";
		}
	}

	public static byte[] encode(byte[] res) {
		try {
			Base64 base = new Base64();
			return base.encode(res);
		} catch (Exception e) {
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
		return new String(new Base64().decode(str.getBytes()),
				StaticConstant.CHARSET);
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
		if (xml == null) {
			xml = "";
		}
		String isz = "0";
		if (xml.getBytes().length > 1024 * size) {
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

	public static String getXml(GlobalInfo globalInfo,
			ReturnStateInfo returnStateInfo, Data data) {
		return getXml(globalInfo, returnStateInfo, data, true);
	}

	/**
	 * 业务数据已加密，只封装外层xml
	 * 
	 * @param globalInfo
	 * @param returnStateInfo
	 * @param data
	 * @return
	 */
	public static String getXmlOuter(GlobalInfo globalInfo,
			ReturnStateInfo returnStateInfo, Data data) {
		return getXml(globalInfo, returnStateInfo, data, false);
	}

	private static String getXml(GlobalInfo globalInfo,
			ReturnStateInfo returnStateInfo, Data data, boolean flag) {
		String resData = "";
		try {
			Element root = new Element(XmlPar.ROOT_BASE);
			Namespace ns = Namespace.getNamespace(XmlPar.NS_ONE);
			root.addNamespaceDeclaration(ns);
			Namespace ns1 = Namespace.getNamespace("xsi", XmlPar.NS_TWO);
			root.addNamespaceDeclaration(ns1);
			Namespace ns2 = Namespace.getNamespace("schemaLocation",
					XmlPar.NS_THREE);
			root.addNamespaceDeclaration(ns2);
			Document doc = new Document(root);
			Element eRoot = doc.getRootElement();
			eRoot.setAttribute("version", XmlPar.VERSION_NO);

			// 全局信息
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
			Element ePassWord = new Element(XmlPar.PASSWORD);
			ePassWord.setText(globalInfo.getPassWord());
			eGlobalInfo.addContent(ePassWord);
			// 返回信息
			Element eReturnStateInfo = new Element(XmlPar.RETURNSTATEINFO);
			Element eReturnCode = new Element(XmlPar.RETURNCODE);
			eReturnCode.setText(returnStateInfo.getReturnCode());
			eReturnStateInfo.addContent(eReturnCode);
			Element eReturnMessage = new Element(XmlPar.RETURNMESSAGE);
			// 修改String串为空的判断
			if (StringUtils.isNotEmpty(returnStateInfo.getReturnMessage())) {
				eReturnMessage.setText(encode(returnStateInfo
						.getReturnMessage()));
			} else {
				eReturnMessage.setText("");
			}
			eReturnStateInfo.addContent(eReturnMessage);
			// 交换数据
			Element eData = new Element(XmlPar.DATA);
			Element eDataDescription;
			eDataDescription = new Element(XmlPar.DATADESCRIPTION);
			Element eZipCode = new Element(XmlPar.ZIPCODE);
			String iszip = "";
			if (data != null && data.getZipCode() != null) {
				iszip = data.getZipCode();
			} else {
				iszip = isZip(data.getContent(), 10);
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
			if (flag) {
				String content = encodeData(data.getContent(), iszip,
						data.getEncryptCode(), globalInfo.getPassWord());
				eContent.setText(content);
			} else {
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
	 * 
	 * @deprecated 将传入的xml参数字符串转换成为map by 苏海林
	 * @return Map
	 * @author: 张士锋
	 * @date: Created on Jul 7, 2012 3:20:54 PM
	 */

	public static Map getInterface(String requestMessage) {
		return getInterface(requestMessage, true);
	}

	/**
	 * 只解密第一层协议
	 */
	public static Map getInterfaceOuter(String requestMessage) {
		return getInterface(requestMessage, false);
	}

	public static Map getInterface(String requestMessage, boolean flag) {
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
						globalInfo
								.setAppId(e1.getChild(XmlPar.APPID).getText() == null ? ""
										: e1.getChild(XmlPar.APPID).getText());
						globalInfo.setInterfaceCode(e1.getChild(
								XmlPar.INTERFACECODE).getText() == null ? ""
								: e1.getChild(XmlPar.INTERFACECODE).getText());
						globalInfo.setRequestCode(e1.getChild(
								XmlPar.REQUESTCODE).getText() == null ? "" : e1
								.getChild(XmlPar.REQUESTCODE).getText());
						globalInfo.setRequestTime(e1.getChild(
								XmlPar.REQUESTTIME).getText() == null ? "" : e1
								.getChild(XmlPar.REQUESTTIME).getText());
						globalInfo.setResponseCode(e1.getChild(
								XmlPar.RESPONSECODE).getText() == null ? ""
								: e1.getChild(XmlPar.RESPONSECODE).getText());
						globalInfo.setDataExchangeId(e1.getChild(
								XmlPar.DATAEXCHANGEID).getText() == null ? ""
								: e1.getChild(XmlPar.DATAEXCHANGEID).getText());
						globalInfo.setUserName(e1.getChild(XmlPar.USERNAME)
								.getText() == null ? "" : e1.getChild(
								XmlPar.USERNAME).getText());
						globalInfo.setPassWord(e1.getChild(XmlPar.PASSWORD)
								.getText() == null ? "" : e1.getChild(
								XmlPar.PASSWORD).getText());
						map.put(e1.getName(), globalInfo);
					}
					if (e1.getName().equals(XmlPar.RETURNSTATEINFO)) {
						ReturnStateInfo returneStateInfo = new ReturnStateInfo();
						returneStateInfo.setReturnCode(e1.getChild(
								XmlPar.RETURNCODE).getText() == null ? "" : e1
								.getChild(XmlPar.RETURNCODE).getText());
						// 修改String串为空的判断
						if (StringUtils.isNotEmpty(e1.getChild(
								XmlPar.RETURNMESSAGE).getText())) {
							returneStateInfo
									.setReturnMessage(decode(e1.getChild(
											XmlPar.RETURNMESSAGE).getText()) == null ? ""
											: e1.getChild(XmlPar.RETURNMESSAGE)
													.getText());
						} else {
							returneStateInfo.setReturnMessage("");
						}
						map.put(e1.getName(), returneStateInfo);
					}
					if (e1.getName().equals(XmlPar.DATA)) {
						Data data = new Data();
						data.setDataDescription(e1.getChild(
								XmlPar.DATADESCRIPTION).getText() == null ? ""
								: e1.getChild(XmlPar.DATADESCRIPTION).getText());
						data.setZipCode(e1.getChild(XmlPar.DATADESCRIPTION)
								.getChild(XmlPar.ZIPCODE).getText() == null ? ""
								: e1.getChild(XmlPar.DATADESCRIPTION)
										.getChild(XmlPar.ZIPCODE).getText());
						data.setEncryptCode(e1.getChild(XmlPar.DATADESCRIPTION)
								.getChild(XmlPar.ENCRYPTCODE).getText() == null ? ""
								: e1.getChild(XmlPar.DATADESCRIPTION)
										.getChild(XmlPar.ENCRYPTCODE).getText());
						data.setCodeType(e1.getChild(XmlPar.DATADESCRIPTION)
								.getChild(XmlPar.CODETYPE).getText() == null ? ""
								: e1.getChild(XmlPar.DATADESCRIPTION)
										.getChild(XmlPar.CODETYPE).getText());
						log.debug(e1.getChild(XmlPar.CONTENT).getText());
						if (flag) {
							String content = decodeData(
									e1.getChild(XmlPar.CONTENT).getText(),
									data.getZipCode(), data.getEncryptCode(),
									globalInfo.getPassWord());
							data.setContent(content);
						} else {
							data.setData(e1.getChild(XmlPar.CONTENT).getText());
						}

						if (log.isDebugEnabled())
							log.debug(data.getContent() + "+=============请求数据");
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

	public static String getFormatDate(Date date, String formatStyle) {
		DateFormat format1 = new SimpleDateFormat(formatStyle);
		return format1.format(date);
	}

	public static String decodeData(String org, String zipCode,
			String encryptCode, String password) {
		byte[] temp_coutnent;
		try {
			// 修改String串为空的判断
			if (StringUtils.isNotEmpty(org)) {
				// temp_coutnent = org.getBytes(StaticConstant.CHARSET);
				// temp_coutnent = decode(temp_coutnent);
				temp_coutnent = decode(org.getBytes());
				if ("1".equals(zipCode)) {
					temp_coutnent = GZipUtils.decompress(temp_coutnent);
				}
				if ("1".equals(encryptCode)) {
					temp_coutnent = TripleDESUtil.decryptMode(password
							.substring(StaticConstant.SJMYBEFORELENG,
									password.length()), temp_coutnent);
				} else if ("2".equals(encryptCode)) {
					// if(StaticConstant.CA_STATE.equals("1")){
					// SJCaCryptUtil sjutil = new
					// SJCaCryptUtil(FileUtils.readFileToByteArray(new
					// File(StaticConstant.CA_TEST_PATH+"\\qyCA\\UserCert.der")),false);
					// temp_coutnent=sjutil.decrypt(temp_coutnent);
					// }else if(StaticConstant.CA_STATE.equals("2")){
					// QYCaCryptUtil qyutil = new
					// QYCaCryptUtil(FileUtils.readFileToByteArray(new
					// File(StaticConstant.CA_TEST_PATH+"\\CorpCA\\UserCert.der")));
					// temp_coutnent=qyutil.decrypt(temp_coutnent);
					// }
				}
				return new String(temp_coutnent, StaticConstant.CHARSET);

			}
		} catch (Exception e) {
			log.error("请求数据", e);
		}
		return "";
	}

	public static String encodeData(String org, String zipCode,
			String encryptCode, String password) {
		byte[] temp_content = org == null ? "".getBytes() : org.getBytes();
		// try {
		if (StringUtils.isNotEmpty(org)) {
			if ("1".equals(encryptCode)) {
				temp_content = TripleDESUtil.encryptMode(password.substring(
						StaticConstant.SJMYBEFORELENG, password.length()),
						temp_content);
			} else if ("2".equals(encryptCode)) {
				// if (StaticConstant.CA_STATE.equals("1")) {
				// SJCaCryptUtil sjutil = new
				// SJCaCryptUtil(FileUtils.readFileToByteArray(new
				// File(StaticConstant.CA_TEST_PATH +
				// "\\qyCA\\UserCert.der")),false);
				// temp_content = sjutil.encrypt(temp_content);
				// } else if (StaticConstant.CA_STATE.equals("2")) {
				// QYCaCryptUtil qyutil = new QYCaCryptUtil(
				// FileUtils.readFileToByteArray(new
				// File(StaticConstant.CA_TEST_PATH +
				// "\\CorpCA\\UserCert.der")));
				// temp_content = qyutil.encrypt(temp_content);
				// }
			}
			if ("1".equals(zipCode)) {
				temp_content = GZipUtils.compress(temp_content);
			}
			// return new String(encode(temp_content), StaticConstant.CHARSET);
			return encode(new String(temp_content));
		}
		// } catch (UnsupportedEncodingException e) {
		// e.printStackTrace();
		// } catch (IOException e) {
		// e.printStackTrace();
		// }

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
	public static Data getData(ByteArrayOutputStream out) {
		Data data = new Data();
		try {
			data.setEncryptCode(StaticConstant.PROTOCOL_ENCRYPTCODE);
			data.setCodeType(StaticConstant.PROTOCOL_CODETYPE);
			String temp_str = new String(out.toByteArray(),
					StaticConstant.CHARSET);
			data.setZipCode(ProXml_DSCLD.isZip(temp_str, 10));
			// 修改String串为空的判断
			if (StringUtils.isNotEmpty(temp_str)) {
				temp_str = temp_str.substring(temp_str.indexOf("<ROOT>") + 6,
						temp_str.lastIndexOf("</ROOT>"));
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
			return (List) factory.generateDomainObject(
					xmlRootStart + xml + xmlRootEnd).get(0);
		}

	}

	/**
	 * 构建GlobalInfo对象
	 * 
	 * @param map
	 *            map中必须包含key为“ywbm”的接口编码、key为”dsptbm“的电商平台编码
	 * @param globalInfo
	 *            必须实例化
	 * @param data
	 */
	public static void getGlobalInfo(Map map, GlobalInfo globalInfo) {
		Password password;
		// 修改String串为空的判断
		if (map.get(XmlPar.MAP_KEY_ZCM) == null
				|| StringUtils.isEmpty(map.get(XmlPar.MAP_KEY_ZCM).toString())) {
			password = PassWordCheck.passWordCreate(StaticConstant.DEFAULTZCM);
		} else {
			password = PassWordCheck.passWordCreate(map.get(XmlPar.MAP_KEY_ZCM)
					.toString());
		}
		globalInfo.setAppId(XmlPar.WLFP);
		globalInfo.setInterfaceCode((String) map.get(XmlPar.MAP_KEY_YWBM));
		globalInfo.setPassWord(password.getSjm() + password.getPass());
		globalInfo.setRequestTime(ProXml_DSCLD.getCurDate("yyyyMMddHHmmss"));
		globalInfo.setResponseCode(XmlPar.SJBM);
		globalInfo.setRequestCode((String) map.get(XmlPar.MAP_KEY_DSPTBM));
		globalInfo.setUserName((String) map.get("dsptbm"));
		globalInfo.setDataExchangeId((String) map.get(XmlPar.MAP_KEY_DSPTBM)
				+ (String) map.get(XmlPar.MAP_KEY_YWBM)
				+ ProXml_DSCLD.getCurDate("yyyyMMdd") + getRdom(9));
	}

	public static GlobalInfo getGlobalInfo(String busiType, String dsptbm,
			Date requestTime) {
		GlobalInfo globalInfo = new GlobalInfo();
		globalInfo.setAppId(XmlPar.WLFP);
		globalInfo.setInterfaceCode(busiType);
		globalInfo.setRequestTime(ProXml_DSCLD.getCurDate("yyyyMMddHHmmss"));
		globalInfo.setResponseCode(XmlPar.SJBM);
		globalInfo.setRequestCode(dsptbm);
		globalInfo.setUserName(dsptbm);
		return globalInfo;
	}

	public static GlobalInfo getGlobalInfo(String busiType, String dsptbm,
			String dataExId) {
		GlobalInfo globalInfo = new GlobalInfo();
		globalInfo.setAppId(XmlPar.WLFP);
		globalInfo.setInterfaceCode(busiType);
		globalInfo.setRequestTime(ProXml_DSCLD.getCurDate("yyyyMMddHHmmss"));
		globalInfo.setResponseCode(XmlPar.SJBM);
		globalInfo.setRequestCode(dsptbm);
		globalInfo.setUserName(dsptbm);
		globalInfo.setDataExchangeId(dataExId);
		return globalInfo;
	}

	/**
	 * 获取随机数
	 * 
	 * @param ws
	 * @return
	 * @author peterli
	 * @date Sep 23, 2013
	 */
	public static String getRdom(int ws) {
		Random r = new Random();
		String nums = Integer
				.toString((Math.abs(r.nextInt(Integer.MAX_VALUE))));
		if (nums.length() >= ws)
			return nums.substring(nums.length() - ws);
		else
			return StringUtils.leftPad(nums, ws, "0");
	}

	/**
	 * 
	 * <p>
	 * 日志bean
	 * </p>
	 * 
	 * @param map
	 * @return LogBean
	 * @author: 张士锋
	 * @date: Created on Jul 26, 2012 11:29:51 AM
	 */
	public static LogBean getLogBean(Map map, String type) {
		LogBean logBean = new LogBean();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			GlobalInfo globalInfoR = (GlobalInfo) map.get(XmlPar.GLOBALINFO);
			ReturnStateInfo returnStateInfoR = (ReturnStateInfo) map
					.get(XmlPar.RETURNSTATEINFO);
			if (returnStateInfoR == null) {
				returnStateInfoR = new ReturnStateInfo();
			}
			logBean.setAppId(globalInfoR.getAppId());
			logBean.setDataEcChangeId(globalInfoR.getDataExchangeId());
			logBean.setInterFaceCode(globalInfoR.getInterfaceCode());
			logBean.setRequestCode(globalInfoR.getRequestCode());
			try {
				logBean.setRequestTime(sdf.parse(globalInfoR.getRequestTime()));
			} catch (Exception e) {
				log.error("未知：" + e);
				logBean.setRequestTime(new Date());
			}
			logBean.setResponseCode(globalInfoR.getResponseCode());
			logBean.setReturnCode(returnStateInfoR.getReturnCode());
			logBean.setReturnMessage(returnStateInfoR.getReturnMessage());
			if (type.equals("response")) {
				logBean.setReturnMessage(ProXml_DSCLD.encode(returnStateInfoR
						.getReturnMessage()));
			}
			return logBean;
		} catch (Exception ce) {
			log.error("获取请求或响应的报文数据失败，请检查报文数据！" + ce.getMessage());
			ce.printStackTrace();
			logBean.setAppId(XmlPar.WLFP);
			logBean.setDataEcChangeId("");
			logBean.setInterFaceCode("");
			logBean.setRequestCode("");
			logBean.setRequestTime(new Date());
			logBean.setResponseCode("");
			logBean.setReturnCode("");
			logBean.setReturnMessage("获取请求或响应的报文数据失败，请检查报文数据！");
			if (type.equals("response")) {
				logBean.setReturnMessage(ProXml_DSCLD
						.encode("获取请求或响应的报文数据失败，请检查报文数据！"));
			}
			return logBean;
		}
	}

	/**
	 * 
	 * <p>
	 * 数据bean
	 * </p>
	 * 
	 * @param map
	 * @return DataLogBean
	 * @author: 张士锋
	 * @date: Created on Jul 26, 2012 11:30:05 AM
	 */
	public static DataLogBean getDataLogBean(Map map) {
		DataLogBean dataLogBean = new DataLogBean();
		try {
			Data dataR = (Data) map.get(XmlPar.DATA);
			GlobalInfo globalInfoR = (GlobalInfo) map.get(XmlPar.GLOBALINFO);
			dataLogBean.setZipCode(dataR.getZipCode());
			dataLogBean.setResponseCode(globalInfoR.getResponseCode());
			dataLogBean.setRequestCode(globalInfoR.getRequestCode());
			dataLogBean.setEncryptCode(dataR.getEncryptCode());
			dataLogBean.setDataExChangeId(globalInfoR.getDataExchangeId());
			dataLogBean.setInterFaceCode(globalInfoR.getInterfaceCode());
			if (dataR.getContent() != null) {
				dataLogBean.setContent((dataR.getContent()).getBytes());
			}
			return dataLogBean;
		} catch (Exception ce) {
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
		Data data = ProXml_DSCLD.getData(out);
		return data.getContent();
	}

}
