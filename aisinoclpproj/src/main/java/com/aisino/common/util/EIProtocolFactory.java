package com.aisino.common.util;

import com.aisino.PKCS7;
import com.aisino.log.domain.Password;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.aisino.common.util.StaticConstant.CHARSET;
import static com.aisino.common.util.TripleDESUtil.encryptMode;
import static com.aisino.common.util.XmlPar.JQBH;
import static com.google.common.base.Strings.isNullOrEmpty;
import static com.google.common.base.Strings.nullToEmpty;

/**
 * 第三方平台与电子发票开票企业协议工厂类
 * <p/>
 * Created by Bourne.Lv on 2014/12/08.
 */
public final class EIProtocolFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(EIProtocolFactory.class);

    /**
     * BASE64加密
     */
    private static String encode(String res) {
        try {
            return new String(Base64.encodeBase64(res.getBytes(CHARSET)));
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
     * @return
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

    /**
     * 根据原始生成传输数据
     *
     * @param globalInfo
     * @param returnStateInfo
     * @param data
     * @param certificate
     * @return
     */
    public static String getXml(GlobalInfo globalInfo, ReturnStateInfo returnStateInfo, Data data, EShopCertificateBytesInfo certificate) {
        return getXml(globalInfo, returnStateInfo, data, certificate, true);
    }
    
    /**
     * 根据原始生成传输数据
     *
     * @param globalInfo
     * @param returnStateInfo
     * @param data
     * @param certificate
     * @return
     */
    public static String getXmlPush(GlobalInfoPush globalInfo, ReturnStateInfo returnStateInfo, Data data, EShopCertificateBytesInfo certificate) {
        return getXmlPush(globalInfo, returnStateInfo, data, certificate, true);
    }

    /**
     * 根据原始生成传输数据
     *
     * @param globalInfo
     * @param returnStateInfo
     * @param data
     * @param certificate
     * @param useCA
     * @return
     */
    private static String getXml(GlobalInfo globalInfo, ReturnStateInfo returnStateInfo, Data data, EShopCertificateBytesInfo certificate, Boolean useCA) {
        String resData = "";
        try {
            Element root = new Element(XmlPar.ROOT_BASE);
            Namespace ns = Namespace.getNamespace(XmlPar.NS_ONE);
            root.addNamespaceDeclaration(ns);
            Namespace ns1 = Namespace.getNamespace("xsi", XmlPar.NS_TWO);
            root.addNamespaceDeclaration(ns1);
            Namespace ns2 = Namespace.getNamespace("schemaLocation", XmlPar.NS_THREE);
            root.addNamespaceDeclaration(ns2);
            Document doc = new Document(root);
            Element eRoot = doc.getRootElement();
            eRoot.setAttribute("version", XmlPar.VERSION_NO);

            // 全局信息
            Element eGlobalInfo = new Element(XmlPar.GLOBALINFO);

//            Element eTerminalCode = new Element(XmlPar.TERMINALCODE);
//            eTerminalCode.setText(globalInfo.getTerminalCode());
//            eGlobalInfo.addContent(eTerminalCode);

            Element eAppID = new Element(XmlPar.APPID);
            eAppID.setText(globalInfo.getAppId());
            eGlobalInfo.addContent(eAppID);

//            Element eVersion = new Element(XmlPar.VERSION);
//            eVersion.setText(globalInfo.getVersion());
//            eGlobalInfo.addContent(eVersion);

            Element eInterfaceCode;
            eInterfaceCode = new Element(XmlPar.INTERFACECODE);
            eInterfaceCode.setText(globalInfo.getInterfaceCode());
            eGlobalInfo.addContent(eInterfaceCode);

            Element eUserName = new Element(XmlPar.USERNAME);
            eUserName.setText(globalInfo.getUserName());
            eGlobalInfo.addContent(eUserName);

            Element ePassWord = new Element(XmlPar.PASSWORD);
            ePassWord.setText(globalInfo.getPassWord());
            eGlobalInfo.addContent(ePassWord);

//            Element eNSRSBH = new Element(XmlPar.NSRSBH);
//            eNSRSBH.setText(globalInfo.getNSRSBH());
//            eGlobalInfo.addContent(eNSRSBH);
//
//            Element eSQM = new Element(XmlPar.SQM);
//            eSQM.setText(globalInfo.getSQM());
//            eGlobalInfo.addContent(eSQM);

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

            Element efjh = new Element(XmlPar.FJH);
            efjh.setText(globalInfo.getFjh());
            eGlobalInfo.addContent(efjh);
            
            final Element jqbhElement = new Element(JQBH);
            jqbhElement.setText("0000000");
            eGlobalInfo.addContent(jqbhElement);

            // 返回信息
            Element eReturnStateInfo = new Element(XmlPar.RETURNSTATEINFO);
            Element eReturnCode = new Element(XmlPar.RETURNCODE);
            eReturnCode.setText(returnStateInfo.getReturnCode());
            eReturnStateInfo.addContent(eReturnCode);
            Element eReturnMessage = new Element(XmlPar.RETURNMESSAGE);
            //修改String串为空的判断
            if (StringUtils.isNotEmpty(returnStateInfo.getReturnMessage())) {
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
            String iszip = "";
            if (data != null && data.getZipCode() != null) {
                iszip = data.getZipCode();
            } else {
                iszip = isZip(data.getContent(), 10);//大于10K要压缩
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
            if (useCA) {
                String content = encodeData(data.getContent(), iszip, data.getEncryptCode(), globalInfo.getPassWord(), certificate);
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
            LOGGER.error("未知：" + e);
            e.printStackTrace();
        }

        return resData;
    }

    private static String getXmlPush(GlobalInfoPush globalInfo, ReturnStateInfo returnStateInfo, Data data, EShopCertificateBytesInfo certificate, Boolean useCA) {
        String resData = "";
        try {
            Element root = new Element(XmlPar.ROOT_BASE);
            Namespace ns = Namespace.getNamespace(XmlPar.NS_ONE);
            root.addNamespaceDeclaration(ns);
            Namespace ns1 = Namespace.getNamespace("xsi", XmlPar.NS_TWO);
            root.addNamespaceDeclaration(ns1);
            Namespace ns2 = Namespace.getNamespace("schemaLocation", XmlPar.NS_THREE);
            root.addNamespaceDeclaration(ns2);
            Document doc = new Document(root);
            Element eRoot = doc.getRootElement();
            eRoot.setAttribute("version", XmlPar.VERSION_NO);

            // 全局信息
            Element eGlobalInfo = new Element(XmlPar.GLOBALINFO);

            Element eTerminalCode = new Element(XmlPar.TERMINALCODE);
            eTerminalCode.setText(globalInfo.getTerminalCode());
            eGlobalInfo.addContent(eTerminalCode);

            Element eAppID = new Element(XmlPar.APPID);
            eAppID.setText(globalInfo.getAppId());
            eGlobalInfo.addContent(eAppID);

            Element eVersion = new Element(XmlPar.VERSION);
            eVersion.setText(globalInfo.getVersion());
            eGlobalInfo.addContent(eVersion);

            Element eInterfaceCode;
            eInterfaceCode = new Element(XmlPar.INTERFACECODE);
            eInterfaceCode.setText(globalInfo.getInterfaceCode());
            eGlobalInfo.addContent(eInterfaceCode);

            Element eUserName = new Element(XmlPar.USERNAME);
            eUserName.setText(globalInfo.getUserName());
            eGlobalInfo.addContent(eUserName);

            Element ePassWord = new Element(XmlPar.PASSWORD);
            ePassWord.setText(globalInfo.getPassWord());
            eGlobalInfo.addContent(ePassWord);

            Element eTAXPAYERID = new Element(XmlPar.TAXPAYERID);
            eTAXPAYERID.setText(globalInfo.getTaxpayerId());
            eGlobalInfo.addContent(eTAXPAYERID);
//
            Element eAUTHORIZATIONCODE = new Element(XmlPar.AUTHORIZATIONCODE);
            eAUTHORIZATIONCODE.setText(globalInfo.getAuthorizationCode());
            eGlobalInfo.addContent(eAUTHORIZATIONCODE);

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

//            Element efjh = new Element(XmlPar.FJH);
//            efjh.setText(globalInfo.getFjh());
//            eGlobalInfo.addContent(efjh);

            // 返回信息
            Element eReturnStateInfo = new Element(XmlPar.RETURNSTATEINFO);
            Element eReturnCode = new Element(XmlPar.RETURNCODE);
            eReturnCode.setText(returnStateInfo.getReturnCode());
            eReturnStateInfo.addContent(eReturnCode);
            Element eReturnMessage = new Element(XmlPar.RETURNMESSAGE);
            //修改String串为空的判断
            if (StringUtils.isNotEmpty(returnStateInfo.getReturnMessage())) {
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
            String iszip = "";
            if (data != null && data.getZipCode() != null) {
                iszip = data.getZipCode();
            } else {
                iszip = isZip(data.getContent(), 10);//大于10K要压缩
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
            if (useCA) {
                String content = encodeData(data.getContent(), iszip, data.getEncryptCode(), globalInfo.getPassWord(), certificate);
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
            LOGGER.error("未知：" + e);
            e.printStackTrace();
        }

        return resData;
    }


    /**
     * 取得全部明文信息, 将传入的xml参数字符串转换成为map
     *
     * @param requestMessage
     * @param certificate
     * @return
     * @throws Exception
     */
    public static Map<String, Object> getInterface(String requestMessage, EShopCertificateBytesInfo certificate) throws Exception {
        return getInterface(requestMessage, certificate, true);
    }


    /**
     * 取得全部明文信息, 将传入的xml参数字符串转换成为map
     *
     * @param requestMessage
     * @param certificate
     * @param useCA
     * @return
     * @throws Exception
     */
    public static Map<String, Object> getInterface(String requestMessage, EShopCertificateBytesInfo certificate, Boolean useCA) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        GlobalInfo globalInfo = new GlobalInfo();
        try {
            StringReader read = new StringReader(requestMessage);
            InputSource source = new InputSource(read);
            SAXBuilder sb = new SAXBuilder();
            Document doc = sb.build(source);
            Element root = doc.getRootElement();
            List<?> node = root.getChildren();
            if (node != null && node.size() > 0) {
                for (int i = 0; i < node.size(); i++) {
                    Element e1 = (Element) node.get(i);
                    if (e1.getName().equals(XmlPar.GLOBALINFO)) {
                        globalInfo.setAppId(e1.getChild(XmlPar.APPID).getText() == null ? "" : e1.getChild(XmlPar.APPID).getText());
                        globalInfo.setInterfaceCode(e1.getChild(XmlPar.INTERFACECODE).getText() == null ? "" : e1.getChild(XmlPar.INTERFACECODE).getText());
                        globalInfo.setRequestCode(e1.getChild(XmlPar.REQUESTCODE).getText() == null ? "" : e1.getChild(XmlPar.REQUESTCODE).getText());
                        globalInfo.setRequestTime(e1.getChild(XmlPar.REQUESTTIME).getText() == null ? "" : e1.getChild(XmlPar.REQUESTTIME).getText());
                        globalInfo.setResponseCode(e1.getChild(XmlPar.RESPONSECODE).getText() == null ? "" : e1.getChild(XmlPar.RESPONSECODE).getText());
                        globalInfo.setDataExchangeId(e1.getChild(XmlPar.DATAEXCHANGEID).getText() == null ? "" : e1.getChild(XmlPar.DATAEXCHANGEID).getText());
                        globalInfo.setUserName(e1.getChild(XmlPar.USERNAME).getText() == null ? "" : e1.getChild(XmlPar.USERNAME).getText());
                        globalInfo.setPassWord(e1.getChild(XmlPar.PASSWORD).getText() == null ? "" : e1.getChild(XmlPar.PASSWORD).getText());
                        if (!"ECXML.FPKJ.PUSH.E_INV".equals(globalInfo.getInterfaceCode())) {
                            globalInfo.setFjh(e1.getChild(XmlPar.FJH).getText() == null ? "" : e1.getChild(XmlPar.FJH).getText());
                            globalInfo.setJqbh(e1.getChild(XmlPar.JQBH).getText() == null ? "" : e1.getChild(XmlPar.JQBH).getText());
                        }
                        map.put(e1.getName(), globalInfo);
                    }
                    if (e1.getName().equals(XmlPar.RETURNSTATEINFO)) {
                        ReturnStateInfo returneStateInfo = new ReturnStateInfo();
                        returneStateInfo.setReturnCode(e1.getChild(XmlPar.RETURNCODE).getText() == null ? "" : e1.getChild(XmlPar.RETURNCODE).getText());
                        if (!isNullOrEmpty(e1.getChild(XmlPar.RETURNMESSAGE).getText())) {
                            returneStateInfo.setReturnMessage(decode(e1.getChild(XmlPar.RETURNMESSAGE).getText()));
                        } else {
                            returneStateInfo.setReturnMessage("");
                        }
                        map.put(e1.getName(), returneStateInfo);
                    }
                    if (e1.getName().equals(XmlPar.DATA)) {
                        Data data = new Data();
                        data.setDataDescription(e1.getChild(XmlPar.DATADESCRIPTION).getText() == null ? "" : e1.getChild(XmlPar.DATADESCRIPTION).getText());
                        data.setZipCode(e1.getChild(XmlPar.DATADESCRIPTION).getChild(XmlPar.ZIPCODE).getText() == null ? "" : e1.getChild(XmlPar.DATADESCRIPTION).getChild(XmlPar.ZIPCODE).getText());
                        data.setEncryptCode(e1.getChild(XmlPar.DATADESCRIPTION).getChild(XmlPar.ENCRYPTCODE).getText() == null ? "" : e1.getChild(XmlPar.DATADESCRIPTION).getChild(XmlPar.ENCRYPTCODE).getText());
                        data.setCodeType(e1.getChild(XmlPar.DATADESCRIPTION).getChild(XmlPar.CODETYPE).getText() == null ? "" : e1.getChild(XmlPar.DATADESCRIPTION).getChild(XmlPar.CODETYPE).getText());

                        if (useCA) {
                            String content = decodeData(e1.getChild(XmlPar.CONTENT).getText(), data.getZipCode(), data.getEncryptCode(), globalInfo.getPassWord(), certificate);
                            data.setContent(content);
                        } else {
                            data.setData(e1.getChild(XmlPar.CONTENT).getText());
                        }
                        map.put(e1.getName(), data);
                    }
                }
            }
        } catch (JDOMException e) {
            LOGGER.error("未知：" + e);
            e.printStackTrace();
        } catch (IOException e) {
            LOGGER.error("未知：" + e);
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 取得系统当前格式化时间
     *
     * @param formatStyle
     * @return
     */
    private static String getCurDate(String formatStyle) {
        return getFormatDate(new Date(), formatStyle);
    }

    /**
     * 取得系统当前格式化时间
     *
     * @param date
     * @param formatStyle
     * @return
     */
    private static String getFormatDate(Date date, String formatStyle) {
        final DateTimeFormatter fmt = DateTimeFormat.forPattern(formatStyle);
        return fmt.print(new DateTime(date));
    }

    /**
     * 解密
     *
     * @param org
     * @param zipCode
     * @param encryptCode
     * @param password
     * @param certificate
     * @return
     * @throws Exception
     */
    private static String decodeData(String org, String zipCode, String encryptCode, String password, EShopCertificateBytesInfo certificate) throws Exception {
        byte[] tempContent;
        if (!isNullOrEmpty(org)) {
            tempContent = org.getBytes(CHARSET);
            tempContent = decode(tempContent);

            //TODO 暂时屏蔽压缩处理
               if ("1".equals(zipCode)) {

                    ////内容经过zip处理，需要首先解压数据
                    if (LOGGER.isDebugEnabled()) {
                        LOGGER.debug("启用ZIP压缩数据功能......");
                    }

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
     * 加密
     *
     * @param org
     * @param zipCode
     * @param encryptCode
     * @param password
     * @param certificate
     * @return
     * @throws UnsupportedEncodingException
     */
    private static String encodeData(String org, String zipCode, String encryptCode, String password, EShopCertificateBytesInfo certificate) throws UnsupportedEncodingException {
        byte[] temp_content = nullToEmpty(org).getBytes(CHARSET);
        try {
            if (!isNullOrEmpty(org)) {

                if ("1".equals(encryptCode)) {

                    if (LOGGER.isDebugEnabled()) {
                        LOGGER.debug("3DES加密方式......");
                    }

                    //3DES加密方式
                    temp_content = encryptMode(password.substring(StaticConstant.SJMYBEFORELENG, password.length()), temp_content);

                } else if ("2".equals(encryptCode)) {

                    //证书加密方式
                    if (LOGGER.isDebugEnabled()) {
                        LOGGER.debug("使用CA证书加密方式......");
                    }

                    final PKCS7 pkcs7 = new PKCS7(certificate.getTrustsBytes(), certificate.getPrivatePFXBytes(), certificate.getPrivatePFXKey());
                    temp_content = pkcs7.pkcs7Encrypt(org, certificate.getPublicPFXBytes());
                } else {

                    if (LOGGER.isDebugEnabled()) {
                        LOGGER.debug("未使用任何加密方式......");
                    }

                }

                //TODO 暂时屏蔽压缩处理
                if ("1".equals(zipCode)) {

                    //内容需要经过zip处理，需要首先压缩数据
                    if (LOGGER.isDebugEnabled()) {
                        LOGGER.debug("启用ZIP压缩数据功能......");
                    }

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
     * 取得返回信息对像
     *
     * @param returnStateCode
     * @param returnMessage
     * @return
     */
    public static ReturnStateInfo getReturnStateInfo(String returnStateCode,
                                                     String returnMessage) {
        ReturnStateInfo returnStateInfo = new ReturnStateInfo();
        returnStateInfo.setReturnCode(returnStateCode);
        returnStateInfo.setReturnMessage(returnMessage);
        return returnStateInfo;
    }

    /**
     * <p>
     * 取得交互数据对像
     * </p>
     *
     * @param
     * @author: 张士锋
     * @date: Created on Jul 10, 2012 9:03:04 AM
     */
    public static Data getData(ByteArrayOutputStream out) {
        Data data = new Data();
        try {
            data.setEncryptCode(StaticConstant.PROTOCOL_ENCRYPTCODE);
            data.setCodeType(StaticConstant.PROTOCOL_CODETYPE);
            String temp_str = new String(out.toByteArray(), StaticConstant.CHARSET);
            data.setZipCode(ProXml.isZip(temp_str, 10));//大于10K就压缩

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
     * @param
     */
    public static void getGlobalInfo(Map<String, Object> map, GlobalInfo globalInfo) {
        Password password;
        //修改String串为空的判断
        if (map.get(XmlPar.MAP_KEY_ZCM) == null || StringUtils.isEmpty(map.get(XmlPar.MAP_KEY_ZCM).toString())) {
            password = PassWordCheck.passWordCreate(StaticConstant.DEFAULTZCM);
        } else {
            password = PassWordCheck.passWordCreate(map.get(XmlPar.MAP_KEY_ZCM).toString());
        }
        globalInfo.setTerminalCode(XmlPar.TERMINALCODE);
        globalInfo.setAppId(XmlPar.DZFP);
        globalInfo.setInterfaceCode((String) map.get(XmlPar.MAP_KEY_YWBM));
        globalInfo.setPassWord(password.getSjm() + password.getPass());
        globalInfo.setRequestTime(ProXml.getCurDate("yyyyMMddHHmmss"));
        globalInfo.setResponseCode(XmlPar.SJBM);
        globalInfo.setRequestCode((String) map.get(XmlPar.MAP_KEY_DSPTBM));
        globalInfo.setUserName((String) map.get("dsptbm"));
        globalInfo.setDataExchangeId((String) map.get(XmlPar.MAP_KEY_DSPTBM) + (String) map.get(XmlPar.MAP_KEY_YWBM) + ProXml.getCurDate("yyyyMMdd") + getRandom(9));
    }

    public static GlobalInfo getGlobalInfo(String busiType, String dsptbm, Date requestTime) {
        GlobalInfo globalInfo = new GlobalInfo();
        globalInfo.setTerminalCode(XmlPar.TERMINALCODE);
        globalInfo.setAppId(XmlPar.DZFP);
        globalInfo.setInterfaceCode(busiType);
        globalInfo.setRequestTime(ProXml.getCurDate("yyyyMMddHHmmss"));
        globalInfo.setResponseCode(XmlPar.SJBM);
        globalInfo.setRequestCode(dsptbm);
        globalInfo.setUserName(dsptbm);
        return globalInfo;
    }

    public static GlobalInfo getGlobalInfo(String busiType, String dsptbm, String dataExId) {
        GlobalInfo globalInfo = new GlobalInfo();
        globalInfo.setAppId(XmlPar.DZFP);
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
     *
     * @param ws
     * @return
     */
    private static String getRandom(int ws) {
        return RandomStringUtils.randomAlphanumeric(ws);
    }

    public static String beanToXml(Object obj) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        XMLShellFactory.newInstance().saveXml(out, obj);
        Data data = ProXml.getData(out);
        return data.getContent();
    }
}
