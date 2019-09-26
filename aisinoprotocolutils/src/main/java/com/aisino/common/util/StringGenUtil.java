package com.aisino.common.util;

import java.util.Random;

import org.apache.commons.lang.StringUtils;

/**
 * 
 * <p>
 * [描述信息：公共工具类]
 * </p>
 * 
 * @author bruce.yang
 * @version 1.0 Created on 2013-9-4 上午9:46:02
 */
public class StringGenUtil {

	public static String getRdom(int ws) {
		Random r = new Random();
		String nums = Integer.toString(Math.abs(r.nextInt(Integer.MAX_VALUE)));
		if (nums.length() >= ws)
			return nums.substring(nums.length() - ws);
		else
			return StringUtils.leftPad(nums, ws, "0");
	}
}
