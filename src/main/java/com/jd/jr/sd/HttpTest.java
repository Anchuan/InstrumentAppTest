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

/**
 * 
  * @author liuifengyi
 *  下午12:20:36
 * @version 1.0
 * 类描述
 *  
 */
public class HttpTest {

	public static String simpleHttp() throws IOException {
		System.out.println(HttpTest.class.getClassLoader());
		String urlStr = "https://www.baidu.com";
		URL url = new URL(urlStr);
		URLConnection connect = url.openConnection();
		System.out.println("simple http test contentType:" + connect.getContentType());
		BufferedReader reader = new BufferedReader(new InputStreamReader(connect.getInputStream()));
		StringBuilder strBuilder = new StringBuilder();
		String str = reader.readLine();
		while (str != null) {
			strBuilder.append(str);
			str = reader.readLine();
		}
		return strBuilder.toString();
	}

	public static void main(String[] args) throws IOException {
		String result = HttpTest.simpleHttp();
		System.out.println("simple http test:" + result);

		String urlStr = "https://www.baidu.com";
		String str = HttpUtil.exctueRequestWithGet(urlStr);
		System.out.println("http client test:" + str);
	}
}
