package com.aisino.trans.util;

import java.math.BigDecimal;

/**
 * 
 * <p>[描述信息：运算工具类]</p>
 * 
 * @author lichunhui lichunhui1314@126.com
 * @version 1.0 Created on 2015-5-20 下午10:07:30
 */
public class MathUtil {
	/**
	 * 
	 * <p>加法运算</p>
	 * 
	 * @param value1
	 * @param value2
	 * @return double
	 * @author: lichunhui lichunhui1314@126.com
	 * @date: Created on 2015-5-20 下午10:35:53
	 */
	public static double add(String value1, String value2) {
		BigDecimal b1 = new BigDecimal(value1);
		BigDecimal b2 = new BigDecimal(value2);
		return b1.add(b2).doubleValue();
	}
	
	/**
	 * 
	 * <p>减法运算</p>
	 * 
	 * @param value1
	 * @param value2
	 * @return double
	 * @author: lichunhui lichunhui1314@126.com
	 * @date: Created on 2015-5-21 下午01:49:09
	 */
	public static double sub(String value1, String value2) { 
		BigDecimal b1 = new BigDecimal(value1);
		BigDecimal b2 = new BigDecimal(value2);
		return b1.subtract(b2).doubleValue();
	}
	
	/**
	 * 
	 * <p>乘法运算</p>
	 * 
	 * @param value1
	 * @param value2
	 * @return double
	 * @author: lichunhui lichunhui1314@126.com
	 * @date: Created on 2015-5-21 下午01:49:30
	 */
	public static double mul(String value1, String value2) { 
		BigDecimal b1 = new BigDecimal(value1);
		BigDecimal b2 = new BigDecimal(value2);
		return b1.multiply(b2).doubleValue();
	}
	
	/**
	 * 
	 * <p>放大税率专用</p>
	 * 
	 * @param value1
	 * @param value2
	 * @return double
	 * @author: lichunhui lichunhui1314@126.com
	 * @date: Created on 2015-5-21 下午01:49:30
	 */
	public static int mul2(String value1, String value2) { 
		BigDecimal b1 = new BigDecimal(value1);
		BigDecimal b2 = new BigDecimal(value2);
		return b1.multiply(b2).intValue();
	}
	
	/**
	 * 
	 * <p>除法运算</p>
	 * 
	 * @param value1
	 * @param value2
	 * @param len
	 * @return double
	 * @author: lichunhui lichunhui1314@126.com
	 * @date: Created on 2015-5-21 下午05:32:25
	 */
	public static double div(String value1, String value2, int len) {
		BigDecimal b1 = new BigDecimal(value1);
		BigDecimal b2 = new BigDecimal(value2);
		return b1.divide(b2, len, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	
	/**
	 * 
	 * <p>四舍五入</p>
	 * 
	 * @param d
	 * @param len
	 * @return double
	 * @author: lichunhui lichunhui1314@126.com
	 * @date: Created on 2015-5-21 下午01:50:02
	 */
	public static double round(double d, int len) { 
		// 操作
		BigDecimal b1 = new BigDecimal(d);
		BigDecimal b2 = new BigDecimal(1);
		// 任何一个数字除以1都是原数字
		// ROUND_HALF_UP是BigDecimal的一个常量，
		// 表示进行四舍五入的操作
		return b1.divide(b2, len, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * <p>功能实现描述</p>
	 * 
	 * @param args
	 *            void
	 * @author: lichunhui lichunhui1314@126.com
	 * @date: Created on 2015-5-20 下午10:07:25
	 */
	public static void main(String[] args) {
		/*System.out.println(div("17", "100", 2));
		System.out.println(mul("1000", Double.toString(div("17", "100", 2))));
		System.out.println( Double.compare(Double.parseDouble("170"), Double.parseDouble("170.0")) );*/
		System.out.println(mul2("0.037", "100"));
		
		String sl = "0.031";
		String sub = sl.substring(sl.indexOf(".")+1);
		if(sub.length() > 2){
			System.out.println("税率有误");
		}
		System.out.println(sub);
	}

}
