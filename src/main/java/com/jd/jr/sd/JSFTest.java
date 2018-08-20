/**
 * @author liuifengyi
 *  下午2:41:01
 * @version 1.0
 * 文件描述
 */
package com.jd.jr.sd;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

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

	public static void main(String[] args) {
		AbstractApplicationContext context = new ClassPathXmlApplicationContext("main.xml");
		SensitiveDataEncryptFacade facade = context.getBean(SensitiveDataEncryptFacade.class);
		String encrypted = facade.encrypt("xxxxxx");
		logger.info(encrypted);
		context.close();
	}

}
