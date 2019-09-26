package com.aisino.ws.impl;

import java.io.File;
import java.io.IOException;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;

import com.aisino.einv.ws.test.InvokeInterfaceTest;

public class JSFLTest {
    public static String encode(String res){
        try{
            Base64 base = new Base64();
            if(res != null && !"".equals(res)){ // 即将解密串不为null并且不为“”
                return new String(base.encode(res.getBytes("UTF-8")));
            }else {
                System.out.println("内层报文为空");
                return "";
            }
        }catch (Exception e) {
            return "";
        }
    }

    public static void main(String[] args) throws IOException {
        String content = "";
        try {
            content = FileUtils.readFileToString(new File("C:\\Users\\andy\\Desktop\\LPKJ.txt"), "utf-8");
        } catch (IOException e) {
            System.out.println("读取文件发生异常：" + e);
            e.printStackTrace();
        }
        String codeContentString = encode(content);
        
        String requestXml = "<?xml version='1.0' encoding='utf-8' ?>\r\n" + 
        		"<interface xmlns='' xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance'\r\n" + 
        		"           xsi:schemaLocation='http://www.chinatax.gov.cn/tirip/dataspec/interfaces.xsd' version='DZFP1.0'>\r\n" + 
        		"    <globalInfo>\r\n" + 
        		"        <terminalCode>0</terminalCode>\r\n" + 
        		"        <appId>ZZS_PT_DZFP</appId>\r\n" + 
        		"        <version>2.0</version>\r\n" + 
        		"        <interfaceCode>ECXML.FPKJ.BC.E_INV</interfaceCode>\r\n" + 
        		"        <requestCode>11101293</requestCode>\r\n" + 
        		"        <requestTime>2015-08-06 10:30:42 978</requestTime>\r\n" + 
        		"        <responseCode>144</responseCode>\r\n" + 
        		"        <dataExchangeId>P000000120160104270892641</dataExchangeId>\r\n" + 
        		"        <userName>11101293</userName>\r\n" + 
        		"        <passWord>0981202609pEMTGIhFeGkwN90e6wlmGA==</passWord>\r\n" + 
        		"        <taxpayerId>915001020008227984</taxpayerId>\r\n" + 
        		"        <authorizationCode>6270892641</authorizationCode>\r\n" + 
        		"    </globalInfo>\r\n" + 
        		"    <returnStateInfo>\r\n" + 
        		"        <returnCode>0000</returnCode>\r\n" + 
        		"        <returnMessage/>\r\n" + 
        		"    </returnStateInfo>\r\n" + 
        		"    <Data>\r\n" + 
        		"        <dataDescription>\r\n" + 
        		"            <zipCode>0</zipCode>\r\n" + 
        		"            <encryptCode>0</encryptCode>\r\n" + 
        		"            <codeType>3DES</codeType>\r\n" + 
        		"        </dataDescription>\r\n" + 
        		"        <content>" +
        		         codeContentString +
        		"         </content>\r\n" + 
        		"    </Data>\r\n" + 
        		"</interface>";

       
        IEInvWebServiceService service = new IEInvWebServiceService();
        IEInvWebService portType = service.getIEInvWebServicePort();
        String responseXml = portType.eiInterface(requestXml);

        System.out.println("调用接口响应报文为：\n" + responseXml);
    }
}
