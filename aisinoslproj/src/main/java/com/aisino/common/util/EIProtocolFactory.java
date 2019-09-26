package com.aisino.common.util;

import static com.aisino.common.util.StaticConstant.CHARSET;
import static com.aisino.common.util.StaticConstant.PROTOCOL_CODETYPE;
import static com.aisino.common.util.StaticConstant.PROTOCOL_ENCRYPTCODE;
import static com.aisino.common.util.TripleDESUtil.encryptMode;
import static com.aisino.common.util.XmlPar.APPID;
import static com.aisino.common.util.XmlPar.AUTHORIZATIONCODE;
import static com.aisino.common.util.XmlPar.CODETYPE;
import static com.aisino.common.util.XmlPar.CONTENT;
import static com.aisino.common.util.XmlPar.DATADESCRIPTION;
import static com.aisino.common.util.XmlPar.DATAEXCHANGEID;
import static com.aisino.common.util.XmlPar.ENCRYPTCODE;
import static com.aisino.common.util.XmlPar.GLOBALINFO;
import static com.aisino.common.util.XmlPar.INTERFACECODE;
import static com.aisino.common.util.XmlPar.NSRSBH;
import static com.aisino.common.util.XmlPar.NS_ONE;
import static com.aisino.common.util.XmlPar.NS_THREE;
import static com.aisino.common.util.XmlPar.NS_TWO;
import static com.aisino.common.util.XmlPar.PASSWORD;
import static com.aisino.common.util.XmlPar.REQUESTCODE;
import static com.aisino.common.util.XmlPar.REQUESTTIME;
import static com.aisino.common.util.XmlPar.RESPONSECODE;
import static com.aisino.common.util.XmlPar.RETURNCODE;
import static com.aisino.common.util.XmlPar.RETURNMESSAGE;
import static com.aisino.common.util.XmlPar.RETURNSTATEINFO;
import static com.aisino.common.util.XmlPar.ROOT_BASE;
import static com.aisino.common.util.XmlPar.TERMINALCODE;
import static com.aisino.common.util.XmlPar.USERNAME;
import static com.aisino.common.util.XmlPar.VERSION;
import static com.aisino.common.util.XmlPar.VERSION_NO;
import static com.aisino.common.util.XmlPar.ZIPCODE;
import static com.aisino.domain.SystemConfig.PROTOCOL_ENCRYPTCODE_3DES;
import static com.google.common.base.Strings.isNullOrEmpty;
import static com.google.common.base.Strings.nullToEmpty;
import static com.google.common.collect.Maps.newHashMap;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.RandomStringUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.Namespace;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.InputSource;

import com.aisino.PKCS7;
import com.aisino.log.domain.Password;

/**
 * 第三方平台与电子发票开票企业协议工厂类
 * Migrate by Schiffer.huang on 2014/11/10.
 */
public final class EIProtocolFactory {
    private final static Logger LOGGER = LoggerFactory.getLogger(EIProtocolFactory.class);

    /**
     * BASE64加密
     */
    private static String encode(String res) {
        try {
            //Base64 base = new Base64();
            return new String(Base64.encodeBase64(res.getBytes(CHARSET)));
            //return new String(base.encode(res.getBytes(StaticConstant.CHARSET)));
        } catch (Exception e) {
            LOGGER.error("未知：" + e);
            return "";
        }
    }

    /**
     * BASE64加密
     */
    private static byte[] encode(byte[] res) {
        try {
            /*Base64 base = new Base64();

            return base.encode(res);*/
            return Base64.encodeBase64(res);
        } catch (Exception e) {
            LOGGER.error("未知：" + e);
            return null;
        }
    }

    public static String encodeToString(byte[] res) {
        try {
            return new String(encode(res), CHARSET);
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("catch UnsupportedEncodingException-encodeToString：", e.fillInStackTrace());
            return null;
        }
    }

    /**
     * base64解码
     */
    private static String decode(String str) throws UnsupportedEncodingException {
        return new String(Base64.decodeBase64(str.getBytes()), CHARSET);
    }

    /**
     * base64解码
     */
    private static byte[] decode(byte[] str) {
        return Base64.decodeBase64(str);
    }

    /**
     * 传输数据与固定值比较大小
     *
     * @param xml
     * @param size
     * @return boolean
     * @author: 张士锋
     * @date: Created on Jul 6, 2012 2:29:15 PM
     */
    private static String isZip(String xml, int size) {
        if (isNullOrEmpty(xml)) {
            xml = "";
        }

        String isz = "0";
        if (xml.getBytes().length > 1024 * size) {
            isz = "1";
        }

        return isz;
    }


    public static String getContent(GlobalInfo globalInfo, ReturnStateInfo returnStateInfo, Data data,final EShopCertificateBytesInfo certificate) {
        String resData = "";
        if(data==null){return resData;}
        try {
            final Element root = new Element(ROOT_BASE);
            final Namespace ns = Namespace.getNamespace(NS_ONE);
            root.addNamespaceDeclaration(ns);
            final Namespace ns1 = Namespace.getNamespace("xsi", NS_TWO);
            root.addNamespaceDeclaration(ns1);
            final Namespace ns2 = Namespace.getNamespace("schemaLocation", NS_THREE);
            root.addNamespaceDeclaration(ns2);
            final Document doc = new Document(root);
            final Element eRoot = doc.getRootElement();
            eRoot.setAttribute("version", VERSION_NO);

            // 全局信息
            final Element eGlobalInfo = new Element(GLOBALINFO);
            
            Element eTerminalCode = new Element(TERMINALCODE);
            eTerminalCode.setText(globalInfo.getTerminalCode());
            eGlobalInfo.addContent(eTerminalCode);
            
            final Element eAppID = new Element(APPID);
            eAppID.setText(globalInfo.getAppId());
            eGlobalInfo.addContent(eAppID);
            
            Element eVersion = new Element(VERSION);
            eVersion.setText(globalInfo.getVersion());
            eGlobalInfo.addContent(eVersion);

            final Element eInterfaceCode = new Element(INTERFACECODE);
            eInterfaceCode.setText(globalInfo.getInterfaceCode());
            eGlobalInfo.addContent(eInterfaceCode);

            final Element eRequestCode = new Element(REQUESTCODE);
            eRequestCode.setText(globalInfo.getRequestCode());
            eGlobalInfo.addContent(eRequestCode);

            final Element eRequestTime = new Element(REQUESTTIME);
            eRequestTime.setText(globalInfo.getRequestTime());
            eGlobalInfo.addContent(eRequestTime);

            final Element eResponseCode = new Element(RESPONSECODE);
            eResponseCode.setText(globalInfo.getResponseCode());
            eGlobalInfo.addContent(eResponseCode);

            final Element eDataExchangeId = new Element(DATAEXCHANGEID);
            eDataExchangeId.setText(globalInfo.getDataExchangeId());
            eGlobalInfo.addContent(eDataExchangeId);

            final Element eUserName = new Element(USERNAME);
            eUserName.setText(globalInfo.getUserName());
            eGlobalInfo.addContent(eUserName);

            final Element ePassWord = new Element(PASSWORD);
            ePassWord.setText(globalInfo.getPassWord());
            eGlobalInfo.addContent(ePassWord);
            
            Element eTAXPAYERID = new Element(XmlPar.TAXPAYERID);
            eTAXPAYERID.setText(globalInfo.getTaxpayerIdentifyNo());
            eGlobalInfo.addContent(eTAXPAYERID);
            
            Element eAUTHORIZATIONCODE = new Element(XmlPar.AUTHORIZATIONCODE);
            eAUTHORIZATIONCODE.setText(globalInfo.getAuthorizationCode());
            eGlobalInfo.addContent(eAUTHORIZATIONCODE);

            // 返回信息
            final Element eReturnStateInfo = new Element(RETURNSTATEINFO);
            final Element eReturnCode = new Element(RETURNCODE);
            eReturnCode.setText(returnStateInfo.getReturnCode());
            eReturnStateInfo.addContent(eReturnCode);

            final Element eReturnMessage = new Element(RETURNMESSAGE);
            if (!isNullOrEmpty(returnStateInfo.getReturnMessage()) && !returnStateInfo.getReturnMessage().equals("null")) {
                eReturnMessage.setText(encode(returnStateInfo.getReturnMessage()));
            } else {
                eReturnMessage.setText("");
            }

            eReturnStateInfo.addContent(eReturnMessage);

            // 交换数据
            final Element eData = new Element(XmlPar.DATA);
            final Element eDataDescription = new Element(DATADESCRIPTION);
            final Element eZipCode = new Element(ZIPCODE);
            final String isZip;
            if (data != null && data.getZipCode() != null) {
                isZip = data.getZipCode();
            } else {
                isZip = isZip(data.getContent(), 10);
            }

            eZipCode.setText(isZip);
            eDataDescription.addContent(eZipCode);

            final Element eEncryptCode = new Element(ENCRYPTCODE);
            eEncryptCode.setText(data.getEncryptCode());
            eDataDescription.addContent(eEncryptCode);

            final Element eCodeType = new Element(CODETYPE);
            eCodeType.setText(data.getCodeType());
            eDataDescription.addContent(eCodeType);
            eData.addContent(eDataDescription);

            final Element eContent = new Element(CONTENT);
            final String content = encodeData(data.getContent(), isZip, data.getEncryptCode(), globalInfo.getPassWord(), certificate);
            eContent.setText(content);
            eData.addContent(eContent);
            root.addContent(eGlobalInfo);
            root.addContent(eReturnStateInfo);
            root.addContent(eData);

            final Format format = Format.getCompactFormat();
            format.setEncoding(CHARSET);
            format.setIndent(" ");

            final XMLOutputter xmlOut = new XMLOutputter(format);
            final ByteArrayOutputStream byteRsp = new ByteArrayOutputStream();
            xmlOut.output(doc, byteRsp);
            resData = byteRsp.toString(CHARSET);
            byteRsp.close();
        } catch (IOException e) {
            LOGGER.error("未知：", e.fillInStackTrace());
        }

        return resData;
    }


    /**
     * 仿照方法 getInterface,从报文中预解析出 taxpayerIdentifyNo、interfaceCode.
     *
     * @param requestMessage 报文String
     * @return GlobalInfo 报文总的 taxpayerIdentifyNo与interfaceCode
     */
    public static GlobalInfo getInterfacePrior(final String requestMessage) {

        try {
            final StringReader read = new StringReader(requestMessage);
            final InputSource source = new InputSource(read);
            final SAXBuilder sb = new SAXBuilder();
            final Document doc = sb.build(source);
            final Element root = doc.getRootElement();
            final List<?> node = root.getChildren();

            if (node != null && node.size() > 0) {
                for (int i = 0; i < node.size(); i++) {
                    final Element e1 = (Element) node.get(i);
                    final String elementName = e1.getName();
                    if (GLOBALINFO.equals(elementName)) {
                        final GlobalInfo globalInfo = new GlobalInfo();
                        globalInfo.setInterfaceCode(nullToEmptyOrText(e1.getChild(INTERFACECODE)));
                        globalInfo.setTaxpayerIdentifyNo(nullToEmptyOrText(e1.getChild(NSRSBH)));

                        globalInfo.setAppId(nullToEmptyOrText(e1.getChild(APPID)));
                        globalInfo.setRequestCode(nullToEmptyOrText(e1.getChild(REQUESTCODE)));
                        globalInfo.setRequestTime(nullToEmptyOrText(e1.getChild(REQUESTTIME)));
                        globalInfo.setResponseCode(nullToEmptyOrText(e1.getChild(RESPONSECODE)));
                        globalInfo.setDataExchangeId(nullToEmptyOrText(e1.getChild(DATAEXCHANGEID)));
                        globalInfo.setUserName(nullToEmptyOrText(e1.getChild(USERNAME)));
                        globalInfo.setPassWord(nullToEmptyOrText(e1.getChild(PASSWORD)));

                        return globalInfo;
                    }
                }
            }
        } catch (JDOMException e) {
            LOGGER.error("未知：", e.fillInStackTrace());
        } catch (IOException e) {
            LOGGER.error("未知：", e.fillInStackTrace());
        }

        return null;
    }

    public static Map<String, Object> getInterface(String requestMessage,final EShopCertificateBytesInfo certificate,final Boolean ca ) throws Exception {
        final Map<String, Object> map = newHashMap();
        final GlobalInfo globalInfo = new GlobalInfo();
        try {
            final StringReader read = new StringReader(requestMessage);
            final InputSource source = new InputSource(read);
            final SAXBuilder sb = new SAXBuilder();
            final Document doc = sb.build(source);
            final Element root = doc.getRootElement();
            final List<?> node = root.getChildren();

            if (node != null && node.size() > 0) {
                for (int i = 0; i < node.size(); i++) {
                    final Element e1 = (Element) node.get(i);
                    final String elementName = e1.getName();
                    if (GLOBALINFO.equals(elementName)) {
                        globalInfo.setAppId(nullToEmptyOrText(e1.getChild(APPID)));
                        globalInfo.setInterfaceCode(nullToEmptyOrText(e1.getChild(INTERFACECODE)));
                        globalInfo.setRequestCode(nullToEmptyOrText(e1.getChild(REQUESTCODE)));
                        globalInfo.setRequestTime(nullToEmptyOrText(e1.getChild(REQUESTTIME)));
                        globalInfo.setResponseCode(nullToEmptyOrText(e1.getChild(RESPONSECODE)));
                        globalInfo.setDataExchangeId(nullToEmptyOrText(e1.getChild(DATAEXCHANGEID)));
                        globalInfo.setUserName(nullToEmptyOrText(e1.getChild(USERNAME)));
                        globalInfo.setPassWord(nullToEmptyOrText(e1.getChild(PASSWORD)));
                        globalInfo.setTaxpayerIdentifyNo(nullToEmptyOrText(e1.getChild(NSRSBH)));
                        globalInfo.setAuthorizationCode(nullToEmptyOrText(e1.getChild(AUTHORIZATIONCODE)));
                        globalInfo.setVersion(nullToEmptyOrText(e1.getChild(VERSION)));
                        globalInfo.setTerminalCode(nullToEmptyOrText(e1.getChild(TERMINALCODE)));
                        map.put(e1.getName(), globalInfo);
                    }

                    if (RETURNSTATEINFO.equals(elementName)) {
                        final ReturnStateInfo returnStateInfo = new ReturnStateInfo();
                        returnStateInfo.setReturnCode(nullToEmptyOrText(e1.getChild(RETURNCODE)));

                        if (e1.getChild(RETURNMESSAGE).getText() != null && !e1.getChild(RETURNMESSAGE).getText().equals("")) {
                            returnStateInfo.setReturnMessage(decode(nullToEmptyOrText(e1.getChild(RETURNMESSAGE))));
                        } else {
                            returnStateInfo.setReturnMessage("");
                        }

                        map.put(elementName, returnStateInfo);
                    }

                    if (XmlPar.DATA.equals(elementName)) {
                        final Data data = new Data();
                        final Element dataDescriptionElement = e1.getChild(DATADESCRIPTION);

                        data.setDataDescription(nullToEmptyOrText(dataDescriptionElement));
                        data.setZipCode(getDataDescriptionChildText(dataDescriptionElement, ZIPCODE));
                        data.setEncryptCode(getDataDescriptionChildText(dataDescriptionElement, ENCRYPTCODE));
                        data.setCodeType(getDataDescriptionChildText(dataDescriptionElement, CODETYPE));

                        if (LOGGER.isDebugEnabled()) {
                            LOGGER.debug(e1.getChild(CONTENT).getText());
                        }

                        /*MARK:TRACY 2014.11.25*/
                        final String theEncryptCode=ca ? PROTOCOL_ENCRYPTCODE_3DES:data.getEncryptCode();

                        final String content = decodeData(e1.getChild(CONTENT).getText(), data.getZipCode(), theEncryptCode, globalInfo.getPassWord(),certificate);
                        data.setContent(content);

                        map.put(elementName, data);
                    }

                }
            }
        } catch (JDOMException e) {
            LOGGER.error("未知：", e.fillInStackTrace());
        } catch (IOException e) {
            LOGGER.error("未知：", e.fillInStackTrace());
        }

        return map;
    }

    private static String getDataDescriptionChildText(final Element dataDescriptionElement, String childName) {
        return nullToEmptyOrText(dataDescriptionElement.getChild(childName));
    }

    private static String nullToEmptyOrText(Element element) {
        return element.getText() == null ? "" : element.getText();
    }

    /**
     * 取得系统当前格式化时间
     *
     * @param formatStyle void
     * @author: 张士锋
     * @date: Created on Jul 9, 2012 10:04:24 AM
     */
    private static String getCurrentDate(String formatStyle) {
        //DateFormat format1 = new SimpleDateFormat(formatStyle);

        return getFormatDate(new Date(), formatStyle);
    }

    private static String getFormatDate(Date date, String formatStyle) {
/*
        DateFormat format1 = new SimpleDateFormat(formatStyle);
        return format1.format(date);
*/
        final DateTimeFormatter fmt = DateTimeFormat.forPattern(formatStyle);
        return fmt.print(new DateTime(date));
    }

    private static String decodeData(String org, String zipCode, String encryptCode, String password, final EShopCertificateBytesInfo certificate) throws Exception {
        byte[] tempContent;
        if (!isNullOrEmpty(org)) {
            tempContent = org.getBytes(CHARSET);
            tempContent = decode(tempContent);
            if ("1".equals(zipCode)) {//内容经过zip处理，需要首先解压数据
                tempContent = GZipUtils.decompress(tempContent);
            }

            if ("1".equals(encryptCode)) {//3DES加密方式
                tempContent = TripleDESUtil.decryptMode(password.substring(StaticConstant.SJMYBEFORELENG, password.length()), tempContent);
            } else if ("2".equals(encryptCode)) {//证书加密方式
                final PKCS7 pkcs7 = new PKCS7(certificate.getTrustsBytes(), certificate.getPrivatePFXBytes(), certificate.getPrivatePFXKey());
                tempContent = pkcs7.pkcs7Decrypt(tempContent);
            }

            return new String(tempContent, CHARSET);
        } else {
            return "";
        }
    }

    /**
     * @param org
     * @param zipCode
     * @param encryptCode
     * @param password
     * @param certificate   客户端证书信息
     * @return
     * @throws UnsupportedEncodingException 2014-9-3
     */
    private static String encodeData(String org, String zipCode, String encryptCode, String password, final EShopCertificateBytesInfo certificate) throws UnsupportedEncodingException {
        byte[] temp_content = nullToEmpty(org).getBytes(CHARSET);//. org == null ? "".getBytes() : org.getBytes(StaticConstant.CHARSET);
        try {
            if (!isNullOrEmpty(org)) {
                if ("1".equals(encryptCode)) {//3DES加密方式
                    temp_content = encryptMode(password.substring(StaticConstant.SJMYBEFORELENG, password.length()), temp_content);

                } else if ("2".equals(encryptCode)) {//证书加密方式
                    final PKCS7 pkcs7 = new PKCS7(certificate.getTrustsBytes(), certificate.getPrivatePFXBytes(), certificate.getPrivatePFXKey());
                    temp_content = pkcs7.pkcs7Encrypt(org, certificate.getPublicPFXBytes());
                }

                //MARK: TRACY 2014.11.20 暂时解除"压缩"

               if ("1".equals(zipCode)) {//内容需要经过zip处理，需要首先压缩数据
                    temp_content = GZipUtils.compress(temp_content);
                }
                return new String(encode(temp_content), CHARSET);
            }
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("UnsupportedEncodingException未知：", e.fillInStackTrace());
        } catch (Exception e) {
            LOGGER.error("Exception未知：", e.fillInStackTrace());
        }

        return "";
    }

    /**
     * <p>
     * 取得返回信息对像
     * </p>
     *
     * @param returnStateCode void
     * @author: 张士锋
     * @date: Created on Jul 10, 2012 8:56:35 AM
     */
    private static ReturnStateInfo getReturnStateInfo(String returnStateCode, String returnMessage) {
        final ReturnStateInfo returnStateInfo = new ReturnStateInfo();
        returnStateInfo.setReturnCode(returnStateCode);
        returnStateInfo.setReturnMessage(returnMessage);

        return returnStateInfo;
    }

    /**
     * <p>
     * 取得交互数据对像
     * </p>
     *
     * @param out void
     * @author: 张士锋
     * @date: Created on Jul 10, 2012 9:03:04 AM
     */
    public static Data getData(ByteArrayOutputStream out) {
        final Data data = new Data();
        try {
            data.setEncryptCode(PROTOCOL_ENCRYPTCODE);
            data.setCodeType(PROTOCOL_CODETYPE);
            String temp_str = new String(out.toByteArray(), CHARSET);
            data.setZipCode(EIProtocolFactory.isZip(temp_str, 10));

            if (!isNullOrEmpty(temp_str) && !temp_str.equals("null")) {
                temp_str = temp_str.substring(temp_str.indexOf("<ROOT>") + 6, temp_str.lastIndexOf("</ROOT>"));
            } else {
                temp_str = "";
            }

            data.setContent(temp_str);
        } catch (Exception e) {
            LOGGER.error("未知：", e.fillInStackTrace());
        }

        return data;
    }


/*
    private static String replaceStr(String s) {
        return s.replaceAll(" ", "");
    }
*/

    public static List<?> getDataRoot(String xml) throws Exception {
        final String xmlRootStart = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>  <ROOT>";
        final String xmlRootEnd = "</ROOT>";
        final XMLShellFactory factory = XMLShellFactory.newInstance();

        if (xml.startsWith("<?xml")) {
            return (List<?>) factory.generateDomainObject(xml).get(0);
        } else {
            return (List<?>) factory.generateDomainObject(xmlRootStart + xml + xmlRootEnd).get(0);
        }

    }

    /**
     * 构建GlobalInfo对象
     *
     * @param map        map中必须包含key为“ywbm”的接口编码、key为”dsptbm“的电商平台编码
     * @param globalInfo 必须实例化
     * @param globalInfo
     */
    private static void getGlobalInfo(Map<String, Object> map, GlobalInfo globalInfo) {
        final Password password;
        final Object mapKeyZCM = map.get(XmlPar.MAP_KEY_ZCM);
        if (mapKeyZCM == null || mapKeyZCM.toString().equals("")) {
            password = PassWordCheck.passWordCreate(StaticConstant.DEFAULTZCM);
        } else {
            password = PassWordCheck.passWordCreate(mapKeyZCM.toString());
        }

        final String mapKeyYWBM = (String) map.get(XmlPar.MAP_KEY_YWBM);
        final String mapKeyDSPTBM = (String) map.get(XmlPar.MAP_KEY_DSPTBM);
        globalInfo.setAppId(XmlPar.WLFP);
        globalInfo.setInterfaceCode(mapKeyYWBM);
        globalInfo.setPassWord(password.getSjm() + password.getPass());
        globalInfo.setRequestTime(EIProtocolFactory.getCurrentDate("yyyyMMddHHmmss"));
        globalInfo.setResponseCode(XmlPar.SJBM);
        globalInfo.setRequestCode(mapKeyDSPTBM);
        //globalInfo.setUserName((String) map.get("dsptbm"));
        globalInfo.setUserName(mapKeyDSPTBM);

        globalInfo.setDataExchangeId(mapKeyDSPTBM + mapKeyYWBM + EIProtocolFactory.getCurrentDate("yyyyMMdd") + getRandom(9));
    }

    private static GlobalInfo getGlobalInfo(String busiType, String dsptbm, Date requestTime) {
        final GlobalInfo globalInfo = new GlobalInfo();

        globalInfo.setAppId(XmlPar.WLFP);
        globalInfo.setInterfaceCode(busiType);
        globalInfo.setRequestTime(getCurrentDate("yyyyMMddHHmmss"));
        globalInfo.setResponseCode(XmlPar.SJBM);
        globalInfo.setRequestCode(dsptbm);
        globalInfo.setUserName(dsptbm);

        return globalInfo;
    }

    public static GlobalInfo getGlobalInfo(String busiType, String dsptbm, String dataExId) {
        final GlobalInfo globalInfo = new GlobalInfo();

        globalInfo.setAppId(XmlPar.WLFP);
        globalInfo.setInterfaceCode(busiType);
        globalInfo.setRequestTime(getCurrentDate("yyyyMMddHHmmss"));
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
    private static String getRandom(int ws) {
/*
        Random r = new Random();
        String nums = Integer.toString((Math.abs(r.nextInt(Integer.MAX_VALUE))));
        if (nums.length() >= ws)
            return nums.substring(nums.length() - ws);
        else
            return Strings.padStart(nums, ws, '0');
*/

        return RandomStringUtils.randomAlphanumeric(ws);
    }


    public static String beanToXml(Object obj) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        XMLShellFactory.newInstance().saveXml(out, obj);
        Data data = EIProtocolFactory.getData(out);
        return data.getContent();
    }

}
