/**
 * @author liuifengyi
 *  下午1:09:31
 * @version 1.0
 * 文件描述
 */
package com.jd.jr.sd.inter;

import com.jd.jr.sd.inter.bean.TestReqBean;
import com.jd.jr.sd.inter.bean.TestResBean;

/**
 * 
  * @author liuifengyi
 *  下午1:09:31
 * @version 1.0
 * 类描述
 *  
 */
public interface TestInterFace {


	public TestResBean test();

	public TestResBean test1(String param);

	public TestResBean test2(TestReqBean req);

	public TestResBean test3(TestReqBean req);

}
