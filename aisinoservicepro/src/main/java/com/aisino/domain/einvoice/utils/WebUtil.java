package com.aisino.domain.einvoice.utils;

import static com.google.common.io.Closer.create;
import static org.slf4j.LoggerFactory.getLogger;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;

import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;

import com.aisino.domain.SystemConfig;
import com.google.common.io.CharStreams;
import com.google.common.io.Closer;

/**
 * Created by Bourne.Lv on 2015/11/09.
 * <p/>
 * 网络工具类
 */
public final class WebUtil {
	private static final Logger LOGGER = getLogger(WebUtil.class);

	private WebUtil() {
	}

	private static LayeredConnectionSocketFactory sslsf = null;
	private static PoolingHttpClientConnectionManager poolingHttpClientConnectionManager = null;
	private static RequestConfig requestConfig = null;

	static {
		try {
			sslsf = new SSLConnectionSocketFactory(SSLContext.getDefault());
		} catch (NoSuchAlgorithmException e) {
			LOGGER.error("<线程{}>ssl初始化异常:{}", Thread.currentThread().getId(), e.getMessage());
		}
		Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory> create()
				.register("https", sslsf).register("http", new PlainConnectionSocketFactory()).build();
		poolingHttpClientConnectionManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
		poolingHttpClientConnectionManager.setMaxTotal(1000);
		poolingHttpClientConnectionManager.setDefaultMaxPerRoute(500);
		requestConfig = RequestConfig.custom().setSocketTimeout(60000).setConnectTimeout(3000)
				.setConnectionRequestTimeout(500).build();
	}

	/**
	 * getStreamAsString
	 *
	 * @param stream
	 * @param charset
	 * @return
	 * @throws IOException
	 */
	public static String getStreamAsString(InputStream stream, String charset) throws IOException {
		final Closer closer = create();
		try {
			final Reader reader = new InputStreamReader(stream, charset);
			closer.register(reader);
			return CharStreams.toString(reader);
		} finally {
			try {
				closer.close();
			} catch (IOException e) {
				LOGGER.error("通讯发生错误, 异常:{}", e);
			}
		}
	}

	public static String doPost(String url, Map<String, String> params) throws Exception {
		CloseableHttpResponse response = null;
		try {
			HttpPost httpPost = new HttpPost(url);
			List<NameValuePair> parameters = new ArrayList<NameValuePair>();
			for (String key : params.keySet()) {
				parameters.add(new BasicNameValuePair(key, params.get(key)));
			}
			UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(parameters, SystemConfig.dataCharset);
			httpPost.setEntity(formEntity);

			// 连接池创建httpClient实例
			CloseableHttpClient httpClient = HttpClients.custom()
					.setConnectionManager(poolingHttpClientConnectionManager).setDefaultRequestConfig(requestConfig)
					.build();
			response = httpClient.execute(httpPost);
			return EntityUtils.toString(response.getEntity(), SystemConfig.dataCharset);
		} finally {
			if (response != null) {
				response.close();
			}
		}
	}

	public static String doPostString(String url, String content) throws Exception {
		CloseableHttpResponse response = null;
		try {
			HttpPost httpPost = new HttpPost(url);
			StringEntity stringEntity = new StringEntity(content, ContentType.APPLICATION_JSON);
			httpPost.setEntity(stringEntity);
			// 连接池创建httpClient实例
			CloseableHttpClient httpClient = HttpClients.custom()
					.setConnectionManager(poolingHttpClientConnectionManager).setDefaultRequestConfig(requestConfig)
					.build();
			response = httpClient.execute(httpPost);
			return EntityUtils.toString(response.getEntity(), SystemConfig.dataCharset);
		} finally {
			if (response != null) {
				response.close();
			}
		}

	}

	public static String doCallQzfw(String url, String content) throws Exception {
		CloseableHttpResponse response = null;
		try {
			HttpPost httpPost = new HttpPost(url);
			StringEntity stringEntity = new StringEntity(content, ContentType.APPLICATION_JSON);
			httpPost.setEntity(stringEntity);
			// 连接池创建httpClient实例
			CloseableHttpClient httpClient = HttpClients.custom()
					.setConnectionManager(poolingHttpClientConnectionManager).setDefaultRequestConfig(requestConfig)
					.build();
			response = httpClient.execute(httpPost);
			return EntityUtils.toString(response.getEntity(), SystemConfig.dataCharset);
		} finally {
			if (response != null) {
				response.close();
			}
		}

	}

	public static void main(String[] args) {}
}
