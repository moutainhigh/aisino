<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn"%> 
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
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	
	<script src="<%=basePath%>js/jquery-3.2.1.min.js" type="text/javascript"></script>
  </head>
  <style>
  body{
  	BACKGROUND-COLOR: #F4FAFF;
  }
.tabs{
border: 1px solid #ccc;
border-collapse: collapse;
BACKGROUND-COLOR: #F4FAFF;
}
.tabs td{
border: 1px solid #ccc;

}
.titleTd{
 font-size: 12px;
 COLOR: #0076C8;
}

#bg{ display: none;  position: absolute;  top: 0%;  left: 0%;  width: 100%;  height: 100%;  background-color: #EAEAEA;  z-index:1001;  -moz-opacity: 0.7;  opacity:.70;  filter: alpha(opacity=70);}
#show{display: none;  position: absolute;  top: 25%;  left: 22%;  width: 53%;  height: 500px;  padding: 8px;  border: 8px solid #E8E9F7;  background-color: white;  z-index:1002;  overflow: auto;}
</style>
  <body>
    <table border="0" width="100%" height="80%" cellpadding="2" cellspacing="0" class="tabs">
    	<tr>
    		<td style="text-align: center; COLOR: #0076C8; font-weight: bold";padding-top: 5px;" colspan="12">
    			<a style="font-size: 22px">京东前置系统配置管理</a>
    		</td>
    	</tr>
    	<tr>
    		<td class="titleTd">电商平台编码</td>
    		<td class="titleTd">所属地区</td>
    		<td class="titleTd">纳税人识别号</td>
    		<td class="titleTd">纳税人名称</td>
    		<td style="width: 3%;" class="titleTd">普通电子发票</td>
    		<td class="titleTd">普通电子发票路由地址</td>
    		<td class="titleTd" style="width: 5%">普通电子发票路由方法</td>
    		<td style=" width: 3%;" class="titleTd">增值税普通电子发票</td>
    		<td class="titleTd">增值税普通电子发票路由地址</td>
    		<td style=" width: 5%;" class="titleTd">增值税普通电子发票路由方法</td>
    		<td style=" width: 5%;" class="titleTd">增值税普通电子发票比例</td>
    		<td class="titleTd">操作</td>
    	</tr>
    	<c:forEach items="${routeList}" var="route">
    			<c:forEach items="${route.nsrxxList}" var="nsrxx" varStatus="status">
    			<tr>
    				<c:choose>
    					<c:when  test="${status.count==1}">
	    					<td  class="titleTd" rowspan="${fn:length(route.nsrxxList)}"><c:out value="${route.DSPTBM}" /></td>
	    					<td  class="titleTd" rowspan="${fn:length(route.nsrxxList)}"><c:out value="${route.DQMC}" /></td>
			    			<td class="titleTd"><c:out value="${nsrxx.NSRSBH}" /></td>
			    			<td class="titleTd"><c:out value="${nsrxx.NSRMC}" /></td>
			    			<td class="titleTd"><c:out value="${nsrxx.SFKTPTDZFP}" /></td>
			    			<td class="titleTd"><c:out value="${nsrxx.PTDZFPURL}" /></td>
			    			<td class="titleTd"><c:out value="${nsrxx.PTDZFPMETHOD}" /></td>
			    			<td class="titleTd"><c:out value="${nsrxx.SFKTZZS}" /></td>
			    			<td class="titleTd"><c:out value="${nsrxx.ZZSDZFPURL}" /></td>
			    			<td class="titleTd"><c:out value="${nsrxx.ZZSDZFPMETHOD}" /></td>
			    			<td class="titleTd"><c:out value="${nsrxx.ZZSFPBL}" /></td>
		    			</c:when>
	    			<c:otherwise>
	    					<td class="titleTd"><c:out value="${nsrxx.NSRSBH}" /></td>
			    			<td class="titleTd"><c:out value="${nsrxx.NSRMC}" /></td>
			    			<td class="titleTd"><c:out value="${nsrxx.SFKTPTDZFP}" /></td>
			    			<td class="titleTd"><c:out value="${nsrxx.PTDZFPURL}" /></td>
			    			<td class="titleTd"><c:out value="${nsrxx.PTDZFPMETHOD}" /></td>
			    			<td class="titleTd"><c:out value="${nsrxx.SFKTZZS}" /></td>
			    			<td class="titleTd"><c:out value="${nsrxx.ZZSDZFPURL}" /></td>
			    			<td class="titleTd"><c:out value="${nsrxx.ZZSDZFPMETHOD}" /></td>
			    			<td class="titleTd"><c:out value="${nsrxx.ZZSFPBL}" /></td>
	    			</c:otherwise>
	    			</c:choose>
			    			<td class="titleTd"><input id ="${nsrxx.NSRSBH}" type="button" value="修改" 
			    			onclick="javascript:changeNsrxx('${route.DSPTBM}','${route.DQMC}','${route.DQCODE}','${nsrxx.NSRSBH}','${nsrxx.NSRMC}','${nsrxx.SFKTPTDZFP}','${nsrxx.PTDZFPURL}','${nsrxx.PTDZFPMETHOD}','${nsrxx.SFKTZZS}','${nsrxx.ZZSDZFPURL}','${nsrxx.ZZSDZFPMETHOD}','${nsrxx.ZZSFPBL}')"/></td>
	    			</tr>
    			</c:forEach>
    	</c:forEach>
    	<tr>
    		<td style="text-align: center;BACKGROUND-COLOR: #F4FAFF;" colspan="12">
    			<input type="button" value="新增" onclick="javascript:addNsrxx()">
    			<input type="button" value="读取配置文件" onclick="javascript:loadXml()">
    			<input type="button" value="写 入 缓 存" onclick="javascript:writePz()">
    		</td>
    	</tr>
    </table>
    <form action="viewServlet" method="post">
    	<input type="hidden" name="falg" value="write" id="falg">
    </form>
    <div id="bg"></div>
	<div id="show"> 
	 <form action="viewServlet" method="post" id="form1">
	 <input type="hidden" name="falg"  id="falg" value="">
	   <table border="0" width="100%" height="80%" cellpadding="2" cellspacing="0" class="tabs" style="margin-top: 20px">
	    	<tr>
	    		<td style="text-align: center; COLOR: #0076C8; font-weight: bold;padding-top: 5px;" height="50px" colspan="11">
	    			<a style="font-size: 22px" id="titleAorU"></a>
	    		</td>
	    	</tr>
	    	<tr>
    			<td style="text-align: center; COLOR: #0076C8; font-weight: bold ;padding-top: 5px;text-align: left; " height="30px" colspan="11">
	    			<a style="font-size: 14px">平台信息</a>
	    		</td>
    		</tr>
    		<tr>
	    		<td class="titleTd"  >电商平台编码：<input  name="DSPTBM"  type="text" id="DSPTBM"/></td>
	    		<td class="titleTd"  >所属地区：<input  name="DQMC"  type="text" id="DQMC"/></td>
	    		<td class="titleTd"  >地区编号：<input  name="DQCODE"  type="text" id="DQCODE"/></td>
    		</tr>
	    	<tr>
    			<td style="text-align: center; COLOR: #0076C8; font-weight: bold ;padding-top: 5px;text-align: left; " height="30px" colspan="11">
	    			<a style="font-size: 14px">纳税人信息</a>
	    		</td>
    		</tr>
<!--     		<tr style="float: left;width: 100%;border: 0"> -->
    		<tr>
	    		<td class="titleTd"  >纳税人识别号：<input  name="NSRSBH"   type="text" id="NSRSBH"/></td>
	    		<td class="titleTd" style="border-right: 0" >纳税人名称：<input  name="NSRMC" style="width: 60%"  type="text" id="NSRMC"/> </td>
                <td class="titleTd" style="border-left: 0" > </td>
<!-- 	    		<td style="text-align: center; COLOR: #0076C8;padding-top: 5px;text-align: left; font-size: 12px ;float: left;width: 100%" height="30px" colspan="11"  > -->
<!--                                                             纳税人识别号：<input  name="NSRSBH"   type="text" id="NSRSBH"/>	    		 -->
<!--                 </td> -->
<!-- 	    		<td style="text-align: center; COLOR: #0076C8;padding-top: 5px;text-align: left; font-size: 12px ;float: left;width: 100%" height="30px" colspan="11"  > -->
<!--                                                           纳税人名称：<input  name="NSRMC"   type="text" id="NSRMC"/> 		 -->
<!--                 </td> -->

    		</tr>
    		<tr>
	    		<td style="text-align: center; COLOR: #0076C8;padding-top: 5px;text-align: left; font-size: 12px " height="30px" colspan="11">
	    			普通电子发票：
	    			<label><input name="SFKTPTDZFP" type="radio" value="Y" checked="checked" />是 </label> 
                    <label><input name="SFKTPTDZFP" type="radio" value="N" />否 </label> 
	    		</td>
    		</tr>
    		<tr>
	    		<td style="text-align: center; COLOR: #0076C8;padding-top: 5px;text-align: left; font-size: 12px " height="30px" colspan="11">
	    			普通电子发票路由地址：<input style="width: 70%" name="PTDZFPURL"  type="text" id="PTDZFPURL"/>
	    		</td>
    		</tr>
    		<tr>
	    		<td style="text-align: center; COLOR: #0076C8;padding-top: 5px;text-align: left; font-size: 12px " height="30px" colspan="11">
	    			普通电子发票路由方法：<input  name="PTDZFPMETHOD"  type="text" id="PTDZFPMETHOD"/>
	    		</td>
    		</tr>
    		<tr>
	    		<td style="text-align: center; COLOR: #0076C8;padding-top: 5px;text-align: left; font-size: 12px " height="30px" colspan="11">
	    			增值税普通电子发票：
	    			<label><input name="SFKTZZS" type="radio" value="Y" checked="checked"/>是 </label> 
                    <label><input name="SFKTZZS" type="radio" value="N" />否 </label> 
	    		</td>
    		</tr>
    		<tr>
	    		<td style="text-align: center; COLOR: #0076C8;padding-top: 5px;text-align: left; font-size: 12px " height="30px" colspan="11">
	    			增值税普通电子发票路由地址：<input style="width: 70%" name="ZZSDZFPURL"  type="text" id="ZZSDZFPURL"/>
	    		</td>
    		</tr>
    		<tr>
	    		<td style="text-align: center; COLOR: #0076C8;padding-top: 5px;text-align: left; font-size: 12px " height="30px" colspan="11">
	    			增值税普通电子发票路由方法：<input  name="ZZSDZFPMETHOD"  type="text" id="ZZSDZFPMETHOD" value="eiInterface"/>
	    		</td>
    		</tr>
    		<tr>
	    		<td style="text-align: center; COLOR: #0076C8;padding-top: 5px;text-align: left; font-size: 12px " height="30px" colspan="11">
	    			增值税普通电子发票比例：<input  name="ZZSFPBL"  type="text" id="ZZSFPBL" value="100/1"/>
	    		</td>
    		</tr>
    	 	<tr>
	    		<td style="text-align: center;BACKGROUND-COLOR: #F4FAFF;" colspan="11">
	    			<input type="button" id ="aoru" value="新增" onclick="javascript:saveNsrxx()">
	    			<input type="button" value="关闭" onclick="javascript:hidediv();">
	    		</td>
    	    </tr> 
       </table>	
       
       </form>
	</div>
  </body>
  <script type="text/javascript">
  
    var isNsrDupl = 0;
  	function loadXml(){
	  	document.getElementById("falg").value="load";
  		 document.forms[0].submit();
  	}
  	function writePz(){
  		 document.forms[0].submit();
  		 
  	}
  	
  	function addNsrxx() { 
            document.getElementById("bg").style.display ="block";
            document.getElementById("show").style.display ="block";
            document.getElementById("falg").value="add";
            document.getElementById("aoru").value="新增";
            document.getElementById("titleAorU").innerText="新增纳税人信息";
            document.body.scrollTop = document.documentElement.scrollTop = 0;
            document.getElementById("form1").reset();  
            document.getElementById("DSPTBM").removeAttribute("readOnly");
            document.getElementById("DQMC").removeAttribute("readOnly");
            document.getElementById("DQCODE").removeAttribute("readOnly");
            document.getElementById("NSRSBH").removeAttribute("readOnly");
        }
	function hidediv() {
            document.getElementById("bg").style.display ='none';
            document.getElementById("show").style.display ='none';
        }
        
        
    function saveNsrxx() {
            var falg = document.getElementById("falg").value;   
           // alert("---操作类型--->"+falg);
            if(falg=="add"){
	            checkNsrsbhIsDupl();
	            if(isNsrDupl==1){
	              alert("该纳税人信息已经存在，不可重复添加");
	              return;
	            }
            }
              
            if(trim(document.getElementById("DSPTBM").value)==''){
              alert("电商平台编码不能为空");
              return;
            }
            if(trim(document.getElementById("DQMC").value)==''){
              alert("所属地区不能为空");
              return;
            }
            if(trim(document.getElementById("DQCODE").value)==''){
              alert("地区编号不能为空");
              return;
            }
            if(trim(document.getElementById("NSRSBH").value)==''){
              alert("纳税人识别号不能为空");
              return;
            }
            if(trim(document.getElementById("NSRMC").value)==''){
              alert("纳税人名称不能为空");
              return;
            }
                 
             document.forms[1].falg.value =  falg;  
             document.forms[1].submit();
        }
    function trim(str) {
	  return str.replace(/(^\s+)|(\s+$)/g, "");
	}
    function changeNsrxx(DSPTBM,DQMC,DQCODE,NSRSBH,NSRMC,SFKTPTDZFP,PTDZFPURL,PTDZFPMETHOD,SFKTZZS,ZZSDZFPURL,ZZSDZFPMETHOD,ZZSFPBL){
             document.getElementById("aoru").value="保存修改";
             document.getElementById("titleAorU").innerText="修改纳税人信息";
             document.getElementById("bg").style.display ="block";
             document.getElementById("show").style.display ="block";
             document.getElementById("DSPTBM").value=DSPTBM; 
             document.getElementById("DQMC").value=DQMC; 
             document.getElementById("DQCODE").value=DQCODE; 
             document.getElementById("NSRSBH").value=NSRSBH; 
             document.getElementById("NSRMC").value=NSRMC; 
             if(SFKTPTDZFP=="是"){
               document.all("SFKTPTDZFP")[0].checked=true;
             }else{
               document.all("SFKTPTDZFP")[1].checked=true;
             }
             document.getElementById("PTDZFPURL").value=PTDZFPURL; 
             document.getElementById("PTDZFPMETHOD").value=PTDZFPMETHOD; 
             if(SFKTZZS=="是"){
               document.all("SFKTZZS")[0].checked=true;
             }else{
               document.all("SFKTZZS")[1].checked=true;
             }
             document.getElementById("ZZSDZFPURL").value=ZZSDZFPURL; 
             document.getElementById("ZZSDZFPMETHOD").value=ZZSDZFPMETHOD; 
             document.getElementById("ZZSFPBL").value=ZZSFPBL; 
             document.getElementById("falg").value="change";
             
             document.getElementById("DSPTBM").setAttribute("readOnly",'false');
             document.getElementById("DQMC").setAttribute("readOnly",'false');
             document.getElementById("DQCODE").setAttribute("readOnly",'false');
             document.getElementById("NSRSBH").setAttribute("readOnly",'false');
             document.body.scrollTop = document.documentElement.scrollTop = 0;
       }
       function checkNsrsbhIsDupl(){
           var NSRSBH = $("#NSRSBH").val();
           $.ajax({
	            url : "<%=basePath%>viewServlet",
	            type : "POST",
	            data:{'NSRSBH':NSRSBH,'falg':'check'},
	            dataType : "json",
	            async:false,
	            success : function(result) {
	                 isNsrDupl = result;
	            },
	            error:function(msg){
	            }
          })
        }
        
  $(document).ready(function() {
    $('input[type=radio][name=SFKTPTDZFP]').change(function() {
        if (this.value == 'Y') {
            $("#PTDZFPURL").attr("readOnly",false);
            $("#PTDZFPMETHOD").attr("readOnly",false);
        }
        else if (this.value == 'N') {
            $("#PTDZFPURL").val("");
            $("#PTDZFPMETHOD").val("");
            $("#PTDZFPURL").attr("readOnly","true");
            $("#PTDZFPMETHOD").attr("readOnly","true");
        }
    });
    
    $('input[type=radio][name=SFKTZZS]').change(function() {
        if (this.value == 'Y') {
            $("#ZZSDZFPMETHOD").val("eiInterface");
            $("#ZZSFPBL").val("100/1");
            $("#ZZSDZFPURL").attr("readOnly",false);
            $("#ZZSDZFPMETHOD").attr("readOnly",false);
            $("#ZZSFPBL").attr("readOnly",false);
        }
        else if (this.value == 'N') {
            $("#ZZSDZFPURL").val("");
            $("#ZZSDZFPMETHOD").val("");
            $("#ZZSFPBL").val("");
            $("#ZZSDZFPURL").attr("readOnly","true");
            $("#ZZSDZFPMETHOD").attr("readOnly","true");
            $("#ZZSFPBL").attr("readOnly","true");
        }
    });
});
  </script>
</html>
