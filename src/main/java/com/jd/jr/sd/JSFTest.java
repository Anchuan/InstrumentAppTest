/**
 * @author liuifengyi
 *  下午2:41:01
 * @version 1.0
 * 文件描述
 */
package com.jd.jr.sd;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.jd.jr.sd.inter.TestInterFace;
import com.jd.jr.sd.inter.bean.TestReqBean;
import com.jd.jr.sd.inter.bean.TestResBean;
import com.wangyin.fp.warwolf.facade.api.SensitiveDataEncryptFacade;

/**
 * 
  * @author liuifengyi
 *  下午2:41:01
 * @version 1.0
 * 类描述
 *  
 */
public class JSFTest {
	private static Logger logger = LoggerFactory.getLogger(JSFTest.class);

	private static AbstractApplicationContext context;

	private static AbstractApplicationContext providerContext;

	@BeforeClass
	public static void setUpBeforeClass() {
		context = new ClassPathXmlApplicationContext("main.xml");
		providerContext = new ClassPathXmlApplicationContext("provider.xml");
	}

	@AfterClass
	public static void tearDownAfterClass() {
		context.close();
		providerContext.close();
	}

	@Test
	public void testPlanText() {
		SensitiveDataEncryptFacade facade = context.getBean(SensitiveDataEncryptFacade.class);
		String encrypted = facade.encrypt("xxxxxx");
		logger.info(encrypted);
	}

	@Test
	public void testJSFHTTP() throws URISyntaxException, ClientProtocolException, IOException {
		String path = "/com.jd.jr.sd.inter.TestInterFace/test/test1/test";
		    HttpClient client = HttpClientBuilder.create().setKeepAliveStrategy(new DefaultConnectionKeepAliveStrategy() {
		        @Override
		        public long getKeepAliveDuration(HttpResponse response, HttpContext context) {
		            long keepAlive = super.getKeepAliveDuration(response, context);
		            if (keepAlive == -1) {
		                return 5000;
		            }
		            return keepAlive;
		        }
		    }).build();
		    URIBuilder uriBuilder = new URIBuilder()
		            .setScheme("http")
		            .setHost("g.jsf.jd.local");
		    uriBuilder.setPath(path);
		    URI uri = uriBuilder.build();
		    HttpPost httpPost = new HttpPost(uri);
		    httpPost.setHeader("Content-type", "application/json");
		    String response = client.execute(httpPost, new ResponseHandler<String>() {
		        public String handleResponse(HttpResponse response) throws IOException {
		            int status = response.getStatusLine().getStatusCode();
		            if (status == 200 ) {
		                HttpEntity entity = response.getEntity();
		                if ( entity == null ){
		                    return null;
		                } else {
		                    String json_string = EntityUtils.toString(response.getEntity());
		                    //json_string = json_string.substring(1,json_string.length() - 1);
		                    return json_string;
		                }
		            } else {
		                String responseStr = EntityUtils.toString(response.getEntity());
		                return responseStr;
		            }
		        }
		 
		    });
		logger.info(response);
	}

	@Test
	public void testObject() {
		TestInterFace facade = context.getBean(TestInterFace.class);
		TestReqBean req = new TestReqBean();
		req.setParam1(12);
		req.setParam2("test");
		TestResBean resp = facade.test2(req);
		logger.info("test2 resp ===========" + resp.toString());

		resp = facade.test3(req);
		logger.info("test3 resp ===========" + resp.toString());

		resp = facade.test1("test");
		logger.info("test1 resp ===========" + resp.toString());

		resp = facade.test();
		logger.info("test resp ===========" + resp.toString());

		String respStr = facade.test4(req);
		logger.info("test4 resp ===========" + respStr);

		int respInt = facade.test5(req);
		logger.info("test5 resp ===========" + respInt);
	}

}
