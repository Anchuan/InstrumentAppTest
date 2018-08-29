/**
 * @author liuifengyi
 *  下午1:10:46
 * @version 1.0
 * 文件描述
 */
package com.jd.jr.sd.inter.bean;

/**
 * 
  * @author liuifengyi
 *  下午1:10:46
 * @version 1.0
 * 类描述
 *  
 */
public class TestResBean {

	private int param1;

	private String param2;

	public int getParam1() {
		return param1;
	}

	public void setParam1(int param1) {
		this.param1 = param1;
	}

	public String getParam2() {
		return param2;
	}

	public void setParam2(String param2) {
		this.param2 = param2;
	}

	@Override
	public String toString() {
		return "TestResBean [param1=" + param1 + ", param2=" + param2 + "]";
	}

}
