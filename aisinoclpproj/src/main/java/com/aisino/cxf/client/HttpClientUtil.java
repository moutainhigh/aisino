package com.aisino.cxf.client;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.httpclient.params.HttpConnectionParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.lang.StringUtils;
public class HttpClientUtil {
	private static final Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);
	public static String postSubmit(String url, String requestXML) throws HttpException{
		PostMethod postMethod = null;
		try {
			// 构造HttpClient的实例
			HttpConnectionParams hcp = new HttpConnectionParams();
//			hcp.setSoTimeout(120000);//120秒
			hcp.setSoTimeout(60000);
			hcp.setConnectionTimeout(40000); //连接超时40s
			HttpClientParams hcpd = new HttpClientParams(hcp);
//			HttpClient httpClient = new HttpClient(hcpd,new SimpleHttpConnectionManager(true));
			HttpClient httpClient = new HttpClient(hcpd);
			postMethod = new PostMethod(url);
			httpClient.getParams().setContentCharset("UTF-8");
//			postMethod.addRequestHeader("Content","application/xml; charset=GBK");
			RequestEntity request = new StringRequestEntity(requestXML,"text/html","UTF-8");
			postMethod.setRequestEntity(request);
			int statusCode = httpClient.executeMethod(postMethod);
			// HttpClient对于要求接受后继服务的请求，象POST和PUT等不能自动处理转发
			// 301或者302
			if (statusCode == HttpStatus.SC_MOVED_PERMANENTLY
					|| statusCode == HttpStatus.SC_MOVED_TEMPORARILY) {
				throw new RuntimeException("请求[" + url + "]地址失败,状态代码:"
						+ statusCode);	
			} else {
				// 执行getMethod
				if (statusCode != HttpStatus.SC_OK) {
					throw new RuntimeException("请求["+url+"]地址失败,状态代码:"+statusCode);
				}
				// 读取内容
				byte[] responseBody = postMethod.getResponseBody();
				String respXML = StringUtils.EMPTY;
				if(responseBody == null){
					throw new RuntimeException("请求["+url+"]地址失败,返回的报文为空");
				}else{
					// 处理内容
					 respXML = new String(responseBody, "UTF-8");
				}
				// 处理内容
//				String respXML = new String(responseBody, "UTF-8");
//				System.out.println(respXML);
				return respXML;
			}
		} catch (UnsupportedEncodingException e) {
			logger.error("调用"+url+"出错",e);
		} catch (IOException e) {
			logger.error("调用"+url+"出错",e);
		} finally {
			postMethod.releaseConnection();
		}
		 throw  new HttpException();
	}

	public static void main(String args[]) throws Exception{
//		String resp = HttpClientUtil.postSubmit("http://192.168.15.126:8080/zzs_balance/fpkjsuccess", "");
//		System.out.println(resp);
//		System.err.println(new String(resp.getBytes("GBK"),"UTF-8"));
	    String str = "Mon Dec 31 00:00:00 CST 2012";
        Date date1 = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy",Locale.US ).parse(str);
        System.out.printf("%tF %<tT%n", date1);
        System.out.printf(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date1));
	}
	
 
}
