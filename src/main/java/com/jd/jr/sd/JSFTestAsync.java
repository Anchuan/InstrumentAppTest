/**
 * @author liuifengyi
 *  下午2:41:01
 * @version 1.0
 * 文件描述
 */
package com.jd.jr.sd;

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
import com.jd.jsf.gd.msg.ResponseFuture;
import com.jd.jsf.gd.util.RpcContext;

/**
 * 
  * @author liuifengyi
 *  下午2:41:01
 * @version 1.0
 * 类描述
 *  
 */
public class JSFTestAsync {
	private static Logger logger = LoggerFactory.getLogger(JSFTestAsync.class);

	private static AbstractApplicationContext context;

	private static AbstractApplicationContext providerContext;

	@BeforeClass
	public static void setUpBeforeClass() {
		context = new ClassPathXmlApplicationContext("main-jsf-async.xml");
		providerContext = new ClassPathXmlApplicationContext("provider.xml");
	}

	@AfterClass
	public static void tearDownAfterClass() {
		context.close();
		providerContext.close();
	}


	@Test
	public void testObject() {
		TestInterFace facade = context.getBean(TestInterFace.class);
		TestReqBean req = new TestReqBean();
		req.setParam1(12);
		req.setParam2("test");
		TestResBean resp = facade.test2(req);
		logger.info("test2 resp ===========" + resp);
		ResponseFuture<TestResBean> future2 = RpcContext.getContext().getFuture(); // 得到第一次调用的Future
		resp = facade.test3(req);
		logger.info("test3 resp ===========" + resp);
		ResponseFuture<TestResBean> future3 = RpcContext.getContext().getFuture(); // 得到第二次调用的Future

		resp = facade.test1("test");
		logger.info("test1 resp ===========" + resp);
		ResponseFuture<TestResBean> future1 = RpcContext.getContext().getFuture(); // 得到第三次调用的Future

		resp = facade.test();
		logger.info("test resp ===========" + resp);
		ResponseFuture<TestResBean> future = RpcContext.getContext().getFuture(); // 得到第三次调用的Future

		try {
			resp = future2.get(); // 可能会抛异常，请捕获
			logger.info("test2 resp ********* " + resp.toString());
		} catch (Throwable e) {
			e.printStackTrace();
		}
		try {
			resp = future3.get(); // 可能会抛异常，请捕获
			logger.info("test3 resp ********* " + resp.toString());
		} catch (Throwable e) {
			e.printStackTrace();
		}
		try {
			resp = future1.get(); // 可能会抛异常，请捕获
			logger.info("test1 resp ********* " + resp.toString());
		} catch (Throwable e) {
			e.printStackTrace();
		}
		try {
			resp = future.get(); // 可能会抛异常，请捕获
			logger.info("test resp ********* " + resp.toString());
		} catch (Throwable e) {
			e.printStackTrace();
		}

	}

}
