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
import java.net.InetAddress;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.nio.charset.CodingErrorAction;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.HttpClientConnection;
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.AuthSchemes;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpOptions;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.config.MessageConstraints;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.DnsResolver;
import org.apache.http.conn.HttpConnectionFactory;
import org.apache.http.conn.ManagedHttpClientConnection;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.DefaultBHttpClientConnection;
import org.apache.http.impl.DefaultHttpResponseFactory;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.ManagedHttpClientConnectionFactory;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.impl.conn.SystemDefaultDnsResolver;
import org.apache.http.impl.io.DefaultHttpRequestWriterFactory;
import org.apache.http.impl.io.DefaultHttpResponseParser;
import org.apache.http.impl.io.DefaultHttpResponseParserFactory;
import org.apache.http.impl.pool.BasicConnPool;
import org.apache.http.impl.pool.BasicPoolEntry;
import org.apache.http.io.HttpMessageParser;
import org.apache.http.io.HttpMessageParserFactory;
import org.apache.http.io.HttpMessageWriterFactory;
import org.apache.http.io.SessionInputBuffer;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicHttpRequest;
import org.apache.http.message.BasicLineParser;
import org.apache.http.message.LineParser;
import org.apache.http.pool.PoolStats;
import org.apache.http.protocol.HttpCoreContext;
import org.apache.http.protocol.HttpProcessor;
import org.apache.http.protocol.HttpProcessorBuilder;
import org.apache.http.protocol.HttpRequestExecutor;
import org.apache.http.protocol.RequestConnControl;
import org.apache.http.protocol.RequestContent;
import org.apache.http.protocol.RequestExpectContinue;
import org.apache.http.protocol.RequestTargetHost;
import org.apache.http.protocol.RequestUserAgent;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.CharArrayBuffer;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
  * @author liuifengyi
 *  下午12:20:36
 * @version 1.0
 * 类描述
 *  
 */
public class HttpTest {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

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
		String urlStr = "https://www.baidu.com/s?cl=3&tn=baidutop10&fr=top1000&wd=%E4%BA%AC%E4%B8%9C+%E7%BD%91%E7%BA%A6%E8%BD%A6&rsv_idx=2&rsv_dl=fyb_n_homepage";
		String str = HttpUtil.exctueRequestWithGet(urlStr);
		System.out.println("http client test:" + str);
	}

	@Test
	public void testHttpClientOption() throws IOException {
		HttpOptions options = new HttpOptions("http://jakarta.apache.org");
		HttpResponse response = HttpUtil.getClient().execute(options);
		logger.info(EntityUtils.toString(response.getEntity()));
		Set<String> allowedMethods = options.getAllowedMethods(response);
		logger.info("allow method:" + allowedMethods);
		options.releaseConnection();
	}
	
	@Test
	public void testHttpClientHead() throws IOException {
		 HttpHead head = new HttpHead("http://jakarta.apache.org");
		HttpResponse response = HttpUtil.getClient().execute(head);
		logger.info(EntityUtils.toString(response.getEntity()));
		 Header[] lastModified =
	          head.getHeaders("last-modified");
		logger.info("lastModified:" + lastModified);
	}
	
	@Test
	public void testHttpClientPost() throws IOException {
		Map<String, String> params = new HashMap<String,String>();
		params.put("name", "joy");
		params.put("pwd", "joyyyy");
		String resp = HttpUtil.exctueRequest("http://www.baidu.com", params);
		logger.info("resp:" + resp);
	}
	
	@Test
	public void testHttpFluent() throws IOException {
		logger.info("resp:" + Request.Get("https://www.baidu.com/s").execute().returnContent());
	}

	@Test
	public void testHttpCore() throws HttpException, IOException {
		HttpClientConnection conn = new DefaultBHttpClientConnection(8 * 1024);
		HttpProcessor httpproc = HttpProcessorBuilder.create().add(new RequestContent()).add(new RequestTargetHost())
				.add(new RequestConnControl()).add(new RequestUserAgent("MyClient/1.1"))
				.add(new RequestExpectContinue(true)).build();
		HttpRequestExecutor httpexecutor = new HttpRequestExecutor();

		HttpRequest request = new BasicHttpRequest("GET", "/");
		HttpCoreContext context = HttpCoreContext.create();
		context.setTargetHost(new HttpHost("www.baidu.com"));
		httpexecutor.preProcess(request, httpproc, context);
		HttpResponse response = httpexecutor.execute(request, conn, context);
		httpexecutor.postProcess(response, httpproc, context);

		HttpEntity entity = response.getEntity();
		EntityUtils.consume(entity);
		logger.info(entity.getContentLength() + "");
	}

	@Test
	public void testHttpCoreConnPool() throws HttpException, IOException, InterruptedException, ExecutionException {
		HttpHost target = new HttpHost("www.baidu.com", 443, "https");
		// HttpHost target = new HttpHost("www.baidu.com");
		BasicConnPool connpool = new BasicConnPool();
		connpool.setMaxTotal(200);
		connpool.setDefaultMaxPerRoute(10);
		connpool.setMaxPerRoute(target, 20);
		Future<BasicPoolEntry> future = connpool.lease(target, null);
		BasicPoolEntry poolEntry = future.get();
		HttpClientConnection conn = poolEntry.getConnection();
		HttpProcessor httpproc = HttpProcessorBuilder.create().add(new RequestContent()).add(new RequestTargetHost())
				.add(new RequestConnControl()).add(new RequestUserAgent("MyClient/1.1"))
				.add(new RequestExpectContinue(true)).build();
		HttpRequestExecutor httpexecutor = new HttpRequestExecutor();

		HttpRequest request = new BasicHttpRequest("GET", "/");
		HttpCoreContext context = HttpCoreContext.create();
		context.setTargetHost(target);
		httpexecutor.preProcess(request, httpproc, context);
		HttpResponse response = httpexecutor.execute(request, conn, context);
		httpexecutor.postProcess(response, httpproc, context);

		HttpEntity entity = response.getEntity();
		BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent()));
		String line = reader.readLine();
		logger.info(line);
		while (line != null) {
			// logger.info(line);
			line = reader.readLine();
		}
		EntityUtils.consume(entity);
		logger.info(entity.getContentType().toString());
		logger.info(entity.getContentLength() + "");
		PoolStats totalStats = connpool.getTotalStats();
		System.out.println("total available: " + totalStats.getAvailable());
		System.out.println("total leased: " + totalStats.getLeased());
		System.out.println("total pending: " + totalStats.getPending());
		connpool.closeExpired();
		connpool.closeIdle(1, TimeUnit.MINUTES);
	}

	@Test
	public void testHttpCoreConnPoolHttps()
			throws HttpException, IOException, InterruptedException, ExecutionException {
		HttpHost target = new HttpHost("www.baidu.com");
		// BasicConnPool connpool = new BasicConnPool();
		// connpool.setMaxTotal(200);
		// connpool.setDefaultMaxPerRoute(10);
		// connpool.setMaxPerRoute(target, 20);
		// Future<BasicPoolEntry> future = connpool.lease(target, null);
		// BasicPoolEntry poolEntry = future.get();
		// HttpClientConnection conn = poolEntry.getConnection();
		SSLContext sslcontext = SSLContexts.createSystemDefault();
		SSLSocketFactory sf = sslcontext.getSocketFactory();
		SSLSocket socket = (SSLSocket) sf.createSocket("www.baidu.com", 443);
		// Enforce TLS and disable SSL
		socket.setEnabledProtocols(new String[] { "TLSv1", "TLSv1.1", "TLSv1.2" });
		// Enforce strong ciphers
		// socket.setEnabledCipherSuites(new String[] { "TLS_RSA_WITH_AES_256_CBC_SHA",
		// "TLS_DHE_RSA_WITH_AES_256_CBC_SHA",
		// "TLS_DHE_DSS_WITH_AES_256_CBC_SHA" });
		DefaultBHttpClientConnection conn = new DefaultBHttpClientConnection(8 * 1204);
		conn.bind(socket);
		HttpProcessor httpproc = HttpProcessorBuilder.create().add(new RequestContent()).add(new RequestTargetHost())
				.add(new RequestConnControl()).add(new RequestUserAgent("MyClient/1.1"))
				.add(new RequestExpectContinue(true)).build();
		HttpRequestExecutor httpexecutor = new HttpRequestExecutor();

		HttpRequest request = new BasicHttpRequest("GET", "/aaa");
		HttpCoreContext context = HttpCoreContext.create();
		context.setTargetHost(target);
		httpexecutor.preProcess(request, httpproc, context);
		HttpResponse response = httpexecutor.execute(request, conn, context);
		httpexecutor.postProcess(response, httpproc, context);

		HttpEntity entity = response.getEntity();
		BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent()));
		String line = reader.readLine();
		logger.info(line);
		while (line != null) {
			// logger.info(line);
			line = reader.readLine();
		}
		EntityUtils.consume(entity);
		logger.info(entity.getContentType().toString());
		logger.info(entity.getContentLength() + "");

	}

	@Test
	public void testHttpClientConfig() throws ClientProtocolException, IOException {
		// Use custom message parser / writer to customize the way HTTP
		// messages are parsed from and written out to the data stream.
		HttpMessageParserFactory<HttpResponse> responseParserFactory = new DefaultHttpResponseParserFactory() {

			@Override
			public HttpMessageParser<HttpResponse> create(SessionInputBuffer buffer, MessageConstraints constraints) {
				LineParser lineParser = new BasicLineParser() {

					@Override
					public Header parseHeader(final CharArrayBuffer buffer) {
						try {
							return super.parseHeader(buffer);
						} catch (ParseException ex) {
							return new BasicHeader(buffer.toString(), null);
						}
					}

				};
				return new DefaultHttpResponseParser(buffer, lineParser, DefaultHttpResponseFactory.INSTANCE,
						constraints) {

					protected boolean reject(final CharArrayBuffer line, int count) {
						// try to ignore all garbage preceding a status line infinitely
						return false;
					}

				};
			}

		};
		HttpMessageWriterFactory<HttpRequest> requestWriterFactory = new DefaultHttpRequestWriterFactory();

		// Use a custom connection factory to customize the process of
		// initialization of outgoing HTTP connections. Beside standard connection
		// configuration parameters HTTP connection factory can define message
		// parser / writer routines to be employed by individual connections.
		HttpConnectionFactory<HttpRoute, ManagedHttpClientConnection> connFactory = new ManagedHttpClientConnectionFactory(
				requestWriterFactory, responseParserFactory);

		// Client HTTP connection objects when fully initialized can be bound to
		// an arbitrary network socket. The process of network socket initialization,
		// its connection to a remote address and binding to a local one is controlled
		// by a connection socket factory.

		// SSL context for secure connections can be created either based on
		// system or application specific properties.
		SSLContext sslcontext = SSLContexts.createSystemDefault();

		// Create a registry of custom connection socket factories for supported
		// protocol schemes.
		Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
				.register("http", PlainConnectionSocketFactory.INSTANCE)
				.register("https", new SSLConnectionSocketFactory(sslcontext)).build();

		// Use custom DNS resolver to override the system DNS resolution.
		DnsResolver dnsResolver = new SystemDefaultDnsResolver() {

			@Override
			public InetAddress[] resolve(final String host) throws UnknownHostException {
				if (host.equalsIgnoreCase("myhost")) {
					return new InetAddress[] { InetAddress.getByAddress(new byte[] { 127, 0, 0, 1 }) };
				} else {
					return super.resolve(host);
				}
			}

		};

		// Create a connection manager with custom configuration.
		PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry,
				connFactory, dnsResolver);

		// Create socket configuration
		SocketConfig socketConfig = SocketConfig.custom().setTcpNoDelay(true).build();
		// Configure the connection manager to use socket configuration either
		// by default or for a specific host.
		connManager.setDefaultSocketConfig(socketConfig);
		connManager.setSocketConfig(new HttpHost("somehost", 80), socketConfig);
		// Validate connections after 1 sec of inactivity
		connManager.setValidateAfterInactivity(1000);

		// Create message constraints
		MessageConstraints messageConstraints = MessageConstraints.custom().setMaxHeaderCount(200)
				.setMaxLineLength(2000).build();
		// Create connection configuration
		ConnectionConfig connectionConfig = ConnectionConfig.custom().setMalformedInputAction(CodingErrorAction.IGNORE)
				.setUnmappableInputAction(CodingErrorAction.IGNORE).setCharset(Consts.UTF_8)
				.setMessageConstraints(messageConstraints).build();
		// Configure the connection manager to use connection configuration either
		// by default or for a specific host.
		connManager.setDefaultConnectionConfig(connectionConfig);
		connManager.setConnectionConfig(new HttpHost("somehost", 80), ConnectionConfig.DEFAULT);

		// Configure total max or per route limits for persistent connections
		// that can be kept in the pool or leased by the connection manager.
		connManager.setMaxTotal(100);
		connManager.setDefaultMaxPerRoute(10);
		connManager.setMaxPerRoute(new HttpRoute(new HttpHost("somehost", 80)), 20);

		// Use custom cookie store if necessary.
		CookieStore cookieStore = new BasicCookieStore();
		// Use custom credentials provider if necessary.
		CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
		// Create global request configuration
		RequestConfig defaultRequestConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.DEFAULT)
				.setExpectContinueEnabled(true)
				.setTargetPreferredAuthSchemes(Arrays.asList(AuthSchemes.NTLM, AuthSchemes.DIGEST))
				.setProxyPreferredAuthSchemes(Arrays.asList(AuthSchemes.BASIC)).build();

		// Create an HttpClient with the given custom dependencies and configuration.
		CloseableHttpClient httpclient = HttpClients.custom().setConnectionManager(connManager)
				.setDefaultCookieStore(cookieStore).setDefaultCredentialsProvider(credentialsProvider)
				.setProxy(new HttpHost("myproxy", 8080)).setDefaultRequestConfig(defaultRequestConfig).build();

		try {
			HttpGet httpget = new HttpGet("http://httpbin.org/get");
			// Request configuration can be overridden at the request level.
			// They will take precedence over the one set at the client level.
			RequestConfig requestConfig = RequestConfig.copy(defaultRequestConfig).setSocketTimeout(5000)
					.setConnectTimeout(5000).setConnectionRequestTimeout(5000)
					.setProxy(new HttpHost("myotherproxy", 8080)).build();
			httpget.setConfig(requestConfig);

			// Execution context can be customized locally.
			HttpClientContext context = HttpClientContext.create();
			// Contextual attributes set the local context level will take
			// precedence over those set at the client level.
			context.setCookieStore(cookieStore);
			context.setCredentialsProvider(credentialsProvider);

			System.out.println("executing request " + httpget.getURI());
			CloseableHttpResponse response = httpclient.execute(httpget, context);
			try {
				System.out.println("----------------------------------------");
				System.out.println(response.getStatusLine());
				System.out.println(EntityUtils.toString(response.getEntity()));
				System.out.println("----------------------------------------");

				// Once the request has been executed the local context can
				// be used to examine updated state and various objects affected
				// by the request execution.

				// Last executed request
				context.getRequest();
				// Execution route
				context.getHttpRoute();
				// Target auth state
				context.getTargetAuthState();
				// Proxy auth state
				context.getTargetAuthState();
				// Cookie origin
				context.getCookieOrigin();
				// Cookie spec used
				context.getCookieSpec();
				// User security token
				context.getUserToken();

			} finally {
				response.close();
			}
		} finally {
			httpclient.close();
		}
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
