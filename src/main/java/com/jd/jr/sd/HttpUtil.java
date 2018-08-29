/**
 * @author liuifengyi
 *  下午5:05:20
 * @version 1.0
 * 文件描述
 */
package com.jd.jr.sd;

import java.io.UnsupportedEncodingException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpUtil {

	private static Logger logger = LoggerFactory.getLogger(HttpUtil.class);

	public static String exctueRequest(String url, Map<String, String> param) {
		logger.info("HttpUtils.exctueRequest url:" + url + ", param:" + param);
		HttpResponse response = null;
		HttpPost post = new HttpPost(url);
		List<NameValuePair> list = new ArrayList<NameValuePair>();
		Iterator<String> it = param.keySet().iterator();
		while (it.hasNext()) {
			String key = it.next();
			String value = param.get(key);
			NameValuePair pair = new BasicNameValuePair(key, value);
			list.add(pair);
		}
		UrlEncodedFormEntity formEntity = null;
		try {
			formEntity = new UrlEncodedFormEntity(list);
		} catch (UnsupportedEncodingException e1) {
			logger.error("请求编码异常");
			return null;
		}
		post.setEntity(formEntity);
		String result = null;
		try {
			CloseableHttpClient httpClient = createSSLClientDefault();
			response = httpClient.execute(post);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode == HttpStatus.SC_OK) {
				HttpEntity entity = response.getEntity();
				result = EntityUtils.toString(entity, "UTF-8");
				logger.info("HttpUtils.exctueRequest result:" + result == null ? "" : result);
				return result;
			} else {
				logger.error("exctueRequest 调用http返回码不正常:code:" + statusCode + ",返回值："
						+ EntityUtils.toString(response.getEntity(), "UTF-8"));
			}
		} catch (Exception e) {
			logger.error("exctueRequest http网络执行异常" + e.toString(), e);
		} finally {
			releaseConnection(post);
		}
		return null;
	}

	public static String exctueRequestWithGet(String url) {
		logger.info("HttpUtils.exctueRequest url:" + url);
		System.out.println("HttpUtils.exctueRequest url:" + url);
		HttpResponse response = null;
		HttpGet post = new HttpGet(url);
		String result = null;
		try {
			CloseableHttpClient httpClient = createSSLClientDefault();
			response = httpClient.execute(post);
			Header[] headers = response.getAllHeaders();
			logger.info("all headers :" + Arrays.toString(headers));
			logger.info("if contains header u :" + response.containsHeader("u"));
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode == HttpStatus.SC_OK) {
				HttpEntity entity = response.getEntity();
				result = EntityUtils.toString(entity, "UTF-8");
				logger.info("HttpUtils.exctueRequest result:" + result == null ? "" : result);
				return result;
			} else {
				logger.error("exctueRequest 调用http返回码不正常:code:" + statusCode + ",返回值："
						+ EntityUtils.toString(response.getEntity(), "UTF-8"));
			}
		} catch (Exception e) {
			logger.error("exctueRequest http网络执行异常" + e.toString(), e);
		} finally {
			releaseConnection(post);
		}
		return null;
	}


	public static HttpClient getClient() {
		RequestConfig.Builder requestBuilder = RequestConfig.custom();
		requestBuilder = requestBuilder.setSocketTimeout(30000);
		CloseableHttpClient client = HttpClientBuilder.create().setDefaultRequestConfig(requestBuilder.build()).build();
		return client;
	}

	public static CloseableHttpClient createSSLClientDefault() {
		try {
			SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
				// 信任所有
				public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
					return true;
				}
			}).build();
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext,
					SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
			return HttpClients.custom().setSSLSocketFactory(sslsf).build();
		} catch (KeyManagementException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (KeyStoreException e) {
			e.printStackTrace();
		}
		return HttpClients.createDefault();
	}

	public static String exctueRequest(HttpRequestBase request) {
		HttpResponse response = null;
		String result = null;
		try {
			response = getClient().execute(request);
			int statuscode = response.getStatusLine().getStatusCode();
			if (statuscode == HttpStatus.SC_OK) {
				HttpEntity entity = response.getEntity();
				result = EntityUtils.toString(entity, "UTF-8");
			} else {
				logger.error("调用http返回码不正常:code:" + statuscode + ",返回值："
						+ EntityUtils.toString(response.getEntity(), "UTF-8"));
				result = null;
			}
		} catch (Exception e) {
			logger.error("http网络执行异常" + e.toString(), e);
		} finally {
			releaseConnection(request);
		}
		return result;
	}

	private static void releaseConnection(HttpRequestBase request) {
		if (request != null) {
			request.releaseConnection();
		}
	}
}
