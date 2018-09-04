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

/**
 * 
  * @author liuifengyi
 *  下午2:41:01
 * @version 1.0
 * 类描述
 *  
 */
public class JSFTestDirect {
	private static Logger logger = LoggerFactory.getLogger(JSFTestDirect.class);

	private static AbstractApplicationContext context;

	private static AbstractApplicationContext providerContext;

	@BeforeClass
	public static void setUpBeforeClass() {
		context = new ClassPathXmlApplicationContext("main-jsf-mock.xml");
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
		logger.info("test2 resp ===========" + resp.toString());

		resp = facade.test3(req);
		logger.info("test3 resp ===========" + resp.toString());

		resp = facade.test1("test");
		logger.info("test1 resp ===========" + resp.toString());

		resp = facade.test();
		logger.info("test resp ===========" + resp.toString());
	}

}
