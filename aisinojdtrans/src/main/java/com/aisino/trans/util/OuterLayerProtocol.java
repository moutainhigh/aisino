package com.aisino.trans.util;

import java.io.IOException;
import static org.joda.time.DateTime.now;
import java.io.StringReader;
import java.util.List;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.InputSource;

public class OuterLayerProtocol {
	private static Logger log = LoggerFactory.getLogger(OuterLayerProtocol.class);
	
	public static GlobalInfo getOuterProtocol(String xml){
		final DateTime begin = now();
		GlobalInfo globalInfo = null;
		StringReader read = null;
		try {
			read = new StringReader(xml);
			InputSource source = new InputSource(read);
			SAXBuilder sb = new SAXBuilder();
			Document doc = sb.build(source);
			Element root = doc.getRootElement();
			List node = root.getChildren();
			if (node != null && node.size() > 0) {
				for (int i = 0; i < node.size(); i++) {
					Element e1 = (Element) node.get(i);
					if (e1.getName().equals(XmlPar.GLOBALINFO)) {
						globalInfo = new GlobalInfo();
						if(e1.getChild(XmlPar.TERMINALCODE)==null){
							globalInfo.setTerminalCode(null);
						}else{
							globalInfo.setTerminalCode(e1.getChild(XmlPar.TERMINALCODE).getText()==null?"":e1.getChild(XmlPar.TERMINALCODE).getText());
						}
						globalInfo.setAppId(e1.getChild(XmlPar.APPID)==null||e1.getChild(XmlPar.APPID).getText()==null?"":e1.getChild(XmlPar.APPID).getText());
						if(e1.getChild(XmlPar.VERSION)==null){
							globalInfo.setVersion(null);
						}else{
							globalInfo.setVersion(e1.getChild(XmlPar.VERSION).getText()==null?"":e1.getChild(XmlPar.VERSION).getText());
						}
						globalInfo.setInterfaceCode(e1.getChild(XmlPar.INTERFACECODE)==null||e1.getChild(XmlPar.INTERFACECODE).getText()==null?"":e1.getChild(XmlPar.INTERFACECODE).getText());
						globalInfo.setRequestCode(e1.getChild(XmlPar.REQUESTCODE)==null||e1.getChild(XmlPar.REQUESTCODE).getText()==null?"":e1.getChild(XmlPar.REQUESTCODE).getText());
						globalInfo.setRequestTime(e1.getChild(XmlPar.REQUESTTIME)==null||e1.getChild(XmlPar.REQUESTTIME).getText()==null?"":e1.getChild(XmlPar.REQUESTTIME).getText());
						globalInfo.setResponseCode(e1.getChild(XmlPar.RESPONSECODE)==null||e1.getChild(XmlPar.RESPONSECODE).getText()==null?"":e1.getChild(XmlPar.RESPONSECODE).getText());
						globalInfo.setDataExchangeId(e1.getChild(XmlPar.DATAEXCHANGEID)==null||e1.getChild(XmlPar.DATAEXCHANGEID).getText()==null?"":e1.getChild(XmlPar.DATAEXCHANGEID).getText());
						globalInfo.setUserName(e1.getChild(XmlPar.USERNAME)==null||e1.getChild(XmlPar.USERNAME).getText()==null?"":e1.getChild(XmlPar.USERNAME).getText());
						globalInfo.setPassWord(e1.getChild(XmlPar.PASSWORD)==null||e1.getChild(XmlPar.PASSWORD).getText()==null?"":e1.getChild(XmlPar.PASSWORD).getText());
						if(e1.getChild(XmlPar.TAXPAYERID)==null){
							globalInfo.setTaxpayerId(null);
						}else{
							globalInfo.setTaxpayerId(e1.getChild(XmlPar.TAXPAYERID).getText()==null?"":e1.getChild(XmlPar.TAXPAYERID).getText());
						}
						if(e1.getChild(XmlPar.AUTHORIZATIONCODE)==null){
							globalInfo.setAuthorizationCode(null);
						}else{
							globalInfo.setAuthorizationCode(e1.getChild(XmlPar.AUTHORIZATIONCODE).getText()==null?"":e1.getChild(XmlPar.AUTHORIZATIONCODE).getText());
						}
						
					}
				}
			}
		} catch (JDOMException e) {
			log.error("未知：" + e);
		} catch (IOException e) {
			log.error("未知：" + e);
		}finally{
			if(read != null){
				read.close();
			}
		}
		final long millSeconds = new Duration(begin, now()).getMillis();
		log.info("解析外层globalInfo耗时{}毫秒",millSeconds);
		return globalInfo;
	}
	
	public static  Data getOuterProtocolData(String xml,String passWord){
		final DateTime begin = now();
		Data data  = null;
		StringReader read = null;
		try {
			read = new StringReader(xml);
			InputSource source = new InputSource(read);
			SAXBuilder sb = new SAXBuilder();
			Document doc = sb.build(source);
			Element root = doc.getRootElement();
			List node = root.getChildren();
			if (node != null && node.size() > 0) {
				for (int i = 0; i < node.size(); i++) {
					Element e1 = (Element) node.get(i);
					if (e1.getName().equals(XmlPar.DATA)) {
						data = new Data();
						data.setDataDescription(e1.getChild(XmlPar.DATADESCRIPTION).getText()==null?"":e1.getChild(XmlPar.DATADESCRIPTION).getText());
						data.setZipCode(e1.getChild(XmlPar.DATADESCRIPTION).getChild(XmlPar.ZIPCODE).getText()==null?"":e1.getChild(XmlPar.DATADESCRIPTION).getChild(XmlPar.ZIPCODE).getText());
						data.setEncryptCode(e1.getChild(XmlPar.DATADESCRIPTION).getChild(XmlPar.ENCRYPTCODE).getText()==null?"":e1.getChild(XmlPar.DATADESCRIPTION).getChild(XmlPar.ENCRYPTCODE).getText());
						data.setCodeType(e1.getChild(XmlPar.DATADESCRIPTION).getChild(XmlPar.CODETYPE).getText()==null?"":e1.getChild(XmlPar.DATADESCRIPTION).getChild(XmlPar.CODETYPE).getText());
						String content=ProXml.decodeData(e1.getChild(XmlPar.CONTENT).getText(), data.getZipCode(), data.getEncryptCode(), passWord);
						log.debug(content);
						data.setContent(content);
						if(log.isDebugEnabled())
						log.debug(data.getContent()+"+=============请求数据");
					}
				}
			}
		} catch (JDOMException e) {
			log.error("未知：" + e);
		} catch (IOException e) {
			log.error("未知：" + e);
		}finally{
			if(read != null){
				read.close();
			}
		}
		final long millSeconds = new Duration(begin, now()).getMillis();
		log.info("解析外层Data耗时{}毫秒",millSeconds);
		return data;
	}
}
