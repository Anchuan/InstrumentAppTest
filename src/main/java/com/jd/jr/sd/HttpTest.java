/**
 * @author liuifengyi
 *  下午12:20:36
 * @version 1.0
 * 文件描述
 */
package com.jd.jr.sd;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.junit.Test;

/**
 * 
  * @author liuifengyi
 *  下午12:20:36
 * @version 1.0
 * 类描述
 *  
 */
public class HttpTest {

	/**
	 * 
	 * @author liuifengyi 下午3:24:19
	 * @version 1.0
	 * @throws IOException
	 * 
	 *             测试MOCK返回值
	 */
	@Test
	public void simpleHttp() throws IOException {
		String urlStr = "https://www.baidu.com";
		URL url = new URL(urlStr);
		URLConnection connect = url.openConnection();
		BufferedReader reader = new BufferedReader(new InputStreamReader(connect.getInputStream()));
		StringBuilder strBuilder = new StringBuilder();
		String str = reader.readLine();
		while (str != null) {
			strBuilder.append(str);
			str = reader.readLine();
		}
		String result = strBuilder.toString();
		System.out.println("simple baidu.com test:" + result);
	}

	/**
	 * 
	 * @author liuifengyi 下午3:26:29
	 * @version 1.0
	 * @throws IOException
	 * 
	 *             测试MOCK 返回值和 contentType
	 */
	@Test
	public void simpleHttp1() throws IOException {
		String urlStr = "https://www.baidu.com";
		URL url = new URL(urlStr);
		URLConnection connect = url.openConnection();
		System.out.println("simple baidu.com test contentType:" + connect.getContentType());
		Object obj = connect.getContent();
		System.out.println("simple baidu.com test:" + obj);
	}

	/**
	 * 
	 * @author liuifengyi 下午3:26:29
	 * @version 1.0
	 * @throws IOException
	 * 
	 *             测试MOCK header 返回值和 contentType contentLength
	 */
	@Test
	public void simpleHttpHeader() throws IOException {
		String urlStr = "https://www.baidu.com";
		URL url = new URL(urlStr);
		URLConnection connect = url.openConnection();
		System.out.println("simpleHttpHeader baidu.com test contentType:" + connect.getContentType());
		System.out.println("simpleHttpHeader baidu.com test conentLength:" + connect.getContentLength());
		System.out.println("simpleHttpHeader baidu.com test header u:" + connect.getHeaderField("u"));
		Object obj = connect.getContent();
		System.out.println("simpleHttpHeader baidu.com test:" + obj);
	}

	/**
	 * 
	 * @author liuifengyi 下午5:24:10
	 * @version 1.0
	 * @throws IOException
	 * 
	 *             测试返回值是个JSON对象字符串 和 多个地址同时存在MOCK
	 */
	@Test
	public void simpleHttp2() throws IOException {
		String urlStr = "https://www.jd.com";
		URL url = new URL(urlStr);
		URLConnection connect = url.openConnection();
		System.out.println("simple jd.com test contentType:" + connect.getContentType());
		Object obj = connect.getContent();
		System.out.println("simple jd.com test:" + obj);
	}

	@Test
	public void testHttpClient() throws IOException {
		String urlStr = "https://www.baidu.com";
		String str = HttpUtil.exctueRequestWithGet(urlStr);
		System.out.println("http client test:" + str);
	}

	public static void main(String[] args) throws IOException {
		// HttpTest.simpleHttp();
//		HttpTest.simpleHttp1();
//		HttpTest.simpleHttp2();
		//
		// String urlStr = "https://www.baidu.com";
		// String str = HttpUtil.exctueRequestWithGet(urlStr);
		// System.out.println("http client test:" + str);
		// String str1 = HttpUtil.exctueRequest(urlStr, new HashMap<String, String>());
		// System.out.println("http client post test:" + str1);

		// System.out.println("ccccc\"dddd".replaceAll("\"", "\\\\\""));
	}
}
