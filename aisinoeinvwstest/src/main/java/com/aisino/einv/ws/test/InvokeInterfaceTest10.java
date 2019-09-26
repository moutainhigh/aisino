package com.aisino.einv.ws.test;

import java.io.File;
import java.io.IOException;

import javax.xml.namespace.QName;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;


public class InvokeInterfaceTest10 {

    public static String invokeWS(String interfaceUrl, String namespace,String interfaceMethod, Object[] invokeParam, String parameterName) {
        Call call = null;
        Service service = new Service();
        String responseXml = "";
        try {
            call = (Call) service.createCall();
            call.setTargetEndpointAddress(new java.net.URL(interfaceUrl));
            call.setOperationName(new QName(namespace, interfaceMethod));
            call.setEncodingStyle("");
            call.addParameter(parameterName,
                    org.apache.axis.encoding.XMLType.XSD_STRING,
                    javax.xml.rpc.ParameterMode.IN);// 参数名称
            call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);// 返回类型

            // Object[] invokeParam = new Object[] { requestXml
            // };http://marketplace.eclipse.org/marketplace-client-intro?mpc_install=1403812
            responseXml = (String) call.invoke(invokeParam);
        } catch (Exception e) {
            System.out.println(e);
        }
        return responseXml;

    }
    
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
//    	for (int i = 0; i < 1000000; i++) {
		
        String content = "";
        try {                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             
//        	content = FileUtils.readFileToString(new File("D:\\test\\新报文\\广州图书不带折扣清单红票.txt"), "utf-8");//没有问题，正常开票
//        	content = FileUtils.readFileToString(new File("D:\\test\\新报文\\商品编码折扣率冲红0418.xml"), "utf-8");//报文项目金额计算关系有问题；
//            content = FileUtils.readFileToString(new File("D:\\test\\新报文\\商品编码折扣率0418.xml"), "utf-8");//没有问题 正常开票
//            content = FileUtils.readFileToString(new File("D:\\test\\新报文\\广州图书不带折扣A4正票.txt"), "utf-8");//没有问题 正常开票；
 //           content = FileUtils.readFileToString(new File("D:\\test\\新报文\\广州图书不带折扣清单正票.txt"), "utf-8");//没有问题 正常开票；

//            content = FileUtils.readFileToString(new File("D:\\test\\jdhp.txt"), "utf-8");//没有问题 正常开票；
            content = FileUtils.readFileToString(new File("D:\\test\\泰州大药房报文.txt"), "utf-8");//没有问题 正常开票；
//            content = FileUtils.readFileToString(new File("D:\\test\\异常报文20180314.txt"), "utf-8");//没有问题 正常开票；
            //            content = FileUtils.readFileToString(new File("D:\\test\\jd-20180209.txt"), "utf-8");
            

        } catch (IOException e) {
        	
            System.out.println("读取文件发生异常：" + e);
            e.printStackTrace();
        }
        String regex = "<FPQQLSH></FPQQLSH>" ;
        String contentString ;
        contentString = content.replace(regex, "<FPQQLSH>1"+System.currentTimeMillis()+"</FPQQLSH>");
        String regex1 = "<DDH></DDH>" ;
        contentString = contentString.replace(regex1, "<DDH>1"+System.currentTimeMillis()+"</DDH>");
        String regex2 = "<BZ></BZ>" ;
        contentString = contentString.replace(regex2, "<BZ>订单号:1"+System.currentTimeMillis()+"</BZ>");
        String codeContentString = encode(contentString);
        
        String requestXml = "<?xml version='1.0' encoding='utf-8' ?>"
                + "<interface xmlns='' xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance' xsi:schemaLocation='http://www.chinatax.gov.cn/tirip/dataspec/interfaces.xsd' version='DZFP1.0' >"
                + "<globalInfo>"
                + "<terminalCode>1</terminalCode>"
                + "<appId>DZFP</appId>"
                + "<version>2</version>"
                + "<interfaceCode>ECXML.FPKJ.BC.E_INV</interfaceCode>"
                + "<requestCode>P0000001</requestCode>"
                + "<requestTime>2016-7-13 11:33:29</requestTime>"
                + "<responseCode>121</responseCode>"
                + "<dataExchangeId>11100001ECXML.FPKJ.BC.E_INV20160617141652</dataExchangeId>"
//                + "<userName>15000119270105897</userName>"
				+ "<userName>91321291MA1T5DXWXW</userName>"
                + "<passWord>0654254949VSVn/XW5i73wakZblerWCw==</passWord>"
//                + "<taxpayerId>15000119270105897</taxpayerId>"
				+ "<taxpayerId>91321291MA1T5DXWXW</taxpayerId>"
                + "<authorizationCode>6270892641</authorizationCode>"
                + "</globalInfo>" 
                + "<returnStateInfo>" 
                + "<returnCode />"
                + "<returnMessage />" 
                + "</returnStateInfo>" 
                + "<Data>"
                + "<dataDescription>" 
                + " <zipCode>0</zipCode>"
                + "<encryptCode>0</encryptCode>" 
                + "<codeType>0</codeType>"
                + "</dataDescription>" 
                + "<content>"
                + codeContentString 
                + "</content>"
                + "</Data>" 
                + "</interface>";

 //       String interfaceUrl ="http://127.0.0.1:8080/zzs_jdProxy/webservice/eInvWS?wsdl";

        String interfaceUrl = "http://127.0.0.1:8080/zzs_jdProxy/webservice/eInvWS?wsdl";
        //String interfaceUrl = "http://192.168.8.100:8080/zzs_jdProxy/webservice/eInvWS?wsdl";
        //String interfaceUrl = "http://192.168.8.146:8080/zzs_jdProxy/webservice/eInvWS?wsdl";
        String interfaceMethod = "eiInterface";
        String namespace = "http://ws.aisino.com/";
        String parameterName = "requestXml";

        Object[] invokeParam = new Object[] { requestXml };
        String responseXml = InvokeInterfaceTest10.invokeWS(interfaceUrl,
                namespace, interfaceMethod, invokeParam, parameterName);

       System.out.println("调用接口响应报文为：" + responseXml);
   /*     System.out.println("第"+i+"条");
    }*/
    }
}
