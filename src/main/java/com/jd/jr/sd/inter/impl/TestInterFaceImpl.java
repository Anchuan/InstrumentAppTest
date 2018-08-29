/**
 * @author liuifengyi
 *  下午1:15:04
 * @version 1.0
 * 文件描述
 */
package com.jd.jr.sd.inter.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jd.jr.sd.inter.TestInterFace;
import com.jd.jr.sd.inter.bean.TestReqBean;
import com.jd.jr.sd.inter.bean.TestResBean;

/**
 * 
  * @author liuifengyi
 *  下午1:15:04
 * @version 1.0
 * 类描述
 *  
 */
public class TestInterFaceImpl implements TestInterFace {

	/* (non-Javadoc)
	 * @see com.jd.jr.sd.inter.TestInterFace#test()
	 */

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	public TestResBean test() {
		logger.info("test");
		TestResBean bean = new TestResBean();
		bean.setParam1(0);
		bean.setParam2("param");
		return bean;
	}

	/* (non-Javadoc)
	 * @see com.jd.jr.sd.inter.TestInterFace#test(java.lang.String)
	 */
	public TestResBean test1(String param) {
		logger.info("test1 param");
		TestResBean bean = new TestResBean();
		bean.setParam1(1);
		bean.setParam2("param1");
		return bean;
	}

	/* (non-Javadoc)
	 * @see com.jd.jr.sd.inter.TestInterFace#test(com.jd.jr.sd.inter.bean.TestReqBean)
	 */
	public TestResBean test2(TestReqBean req) {
		logger.info("test2 req");
		TestResBean bean = new TestResBean();
		bean.setParam1(2);
		bean.setParam2("param2");
		return bean;
	}

	/* (non-Javadoc)
	 * @see com.jd.jr.sd.inter.TestInterFace#test1(com.jd.jr.sd.inter.bean.TestReqBean)
	 */
	public TestResBean test3(TestReqBean req) {
		logger.info("test3 req");
		TestResBean bean = new TestResBean();
		bean.setParam1(3);
		bean.setParam2("param3");
		return bean;
	}

}
