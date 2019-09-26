 	package com.aisino.trans.util;

	import org.apache.http.HttpResponse;
	import org.apache.http.HttpStatus;
	import org.apache.http.client.methods.HttpGet;
	import org.apache.http.client.methods.HttpPost;
	import org.apache.http.entity.StringEntity;
	import org.apache.http.impl.client.DefaultHttpClient;
	import org.apache.http.util.EntityUtils;
	import org.slf4j.Logger;
	import org.slf4j.LoggerFactory;

	import java.io.IOException;
	import java.net.URLDecoder;
	import java.util.concurrent.TimeUnit;

	public class HttpClientUtil {
        private static final String  DEFAULT_CHARSET = "UTF-8" ;
        private static final String  DEFAULT_ContentType ="application/json";
        private static Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);    //日志记录
            /**
             * httpPost
             * @param url  路径
             * @param param 参数
             * @return
             */
            public static String httpPost(String url,String param){
                return httpPost(url, param, false);
            }
            /**
             * post请求
             * @param url         url地址
             * @param param     参数
             * @param noNeedResponse    不需要返回结果
             * @return
             */
            public static String httpPost(String url,String param, boolean noNeedResponse){
                //post请求返回结果
                DefaultHttpClient httpClient = new DefaultHttpClient();
                String str = "";
                HttpPost method = new HttpPost(url);
                try {
                    if (null != param) {
                        //解决中文乱码问题
                        StringEntity entity = new StringEntity(param, DEFAULT_CHARSET);
                        entity.setContentEncoding(DEFAULT_CHARSET);
                        entity.setContentType(DEFAULT_ContentType);
                        method.setEntity(entity);
                    }
                    HttpResponse result = httpClient.execute(method);
                    url = URLDecoder.decode(url, DEFAULT_CHARSET);
                    /**请求发送成功，并得到响应**/
                    if (result.getStatusLine().getStatusCode() == 200) {
                        try {
                            /**读取服务器返回过来的json字符串数据**/
                            str = EntityUtils.toString(result.getEntity());
                            if (noNeedResponse) {
                                return null;
                            }
                        } catch (Exception e) {
                            logger.error("post请求提交失败:" + url, e);
                        }
                    }
                } catch (Exception e) {
                    logger.error("post请求提交失败:" + url, e);
                } finally {
                    httpClient.getConnectionManager().closeExpiredConnections();
                    httpClient.getConnectionManager().closeIdleConnections(60, TimeUnit.SECONDS);
                }
                return str;
            }

            /**
             * 发送get请求
             * @param url    路径
             * @return
             */
            public static String httpGet(String url){
                //get请求返回结果
                 String str = "";
                 DefaultHttpClient client = new DefaultHttpClient();
                try {
                    //发送get请求
                    HttpGet request = new HttpGet(url);
                    HttpResponse response = client.execute(request);
                    /**请求发送成功，并得到响应**/
                    if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                        /**读取服务器返回过来的json字符串数据**/
                         str = EntityUtils.toString(response.getEntity());
                        /**把json字符串转换成json对象**/
                        url = URLDecoder.decode(url, DEFAULT_CHARSET);
                    } else {
                        logger.error("get请求提交失败:" + url);
                    }
                } catch (Exception e) {
                    logger.error("get请求提交失败:" + url, e);
                }
                finally {
                    client.getConnectionManager().closeExpiredConnections();
                    client.getConnectionManager().closeIdleConnections(60, TimeUnit.SECONDS);
                }
                return str;
            }
    }
