<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>京东前置系统配置管理</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
  </head>
  
  <body style="background-color: #F4FAFF">
    <table border="0" width="100%" height="100%">
    	<tr>
    		<td style="text-align: center;padding-top: 5px; font-weight: bold;color: #0076C8;">
    			<a style="font-size: 22px">京东前置系统配置管理</a>
    		</td>
    	</tr>
    	<tr>
    		<td>&nbsp;</td>
    	</tr>
    	<tr>
    		<td style="text-align: center;">
    			<input type="button" value="读取配置文件" onclick="loadXml()">
    		</td>
    	</tr>
    	<tr>
    		<td>&nbsp;</td>
    	</tr>
    	<tr>
    		<td>&nbsp;</td>
    	</tr>
    	<tr>
    		<td>&nbsp;</td>
    	</tr>
    	<tr>
    		<td>&nbsp;</td>
    	</tr>
    	<tr>
    		<td>&nbsp;</td>
    	</tr>
    	<tr>
    		<td>&nbsp;</td>
    	</tr>
    </table>
    <form action="viewServlet" method="post">
    	<input type="hidden" name="falg" value="load" id="falg">
    </form>
  </body>
  <script type="text/javascript">
  	function loadXml(){
  		 document.forms[0].submit();
  	}
  </script>
</html>
