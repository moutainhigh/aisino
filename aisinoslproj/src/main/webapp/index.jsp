<%@ page language="java" import="org.apache.commons.codec.binary.Base64" pageEncoding="ISO-8859-1"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'index.jsp' starting page</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
  </head>
  
  <body>
    <%
    String strs="<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+
		"<interface xmlns=\"\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:schemaLocation=\"http://www.chinatax.gov.cn/tirip/dataspec/interfaces.xsd\" version=\"WLFP1.0\">"+
		"<globalInfo><appId>aa</appId><interfaceId>213</interfaceId><interfaceCode>asd</interfaceCode><requestCode>ta</requestCode>"+
		 "<requestTime>2012-07-11</requestTime><responseCode>aaa</responseCode><dataExchangeId>asdf</dataExchangeId></globalInfo>"+
		 "<returnStateInfo><returnCode>0000</returnCode><returnMessage>YWRhZg==</returnMessage></returnStateInfo>"+
		 "<Data><dataDescription><zipCode>0</zipCode><encryptCode /><codeType /></dataDescription><content>"+new String(new Base64().encode(("<djnsrxx class=\"djnsrxx\"><nsrsbh>460001735802290</nsrsbh><zjhm>620104590424031</zjhm></djnsrxx>").getBytes()))+"</content></Data></interface>";
		
                                                                                                                        
     	//com.aisino.djnsrxx.service.IDjnsrxxService s=(com.aisino.djnsrxx.service.IDjnsrxxService)SpringContext.getBean("djnsrxxServiceImp");
       
        //String str=s.queryDjnsrxx(strs);
        
      
       
        
     %>

  </body>
</html>
