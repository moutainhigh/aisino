package com.aisino.common.util;

import java.util.HashMap;
import java.util.Map;

public class FpztConstants {
	public static Map fpztMap = new HashMap();
	/**
	 * 生成PDF成功
	 */
	public static final int SCPDFSUCCESS = 0;
	/**
	 * 生成PDF失败
	 */
	public static final int SCPDFFIAL = -1;

	/**
	 * PDF签章失败
	 */
	public static final int PDFSIGNFIAL = -2;

	/**
	 * 此企业未发现结存
	 */
	public static final int JCISNULL = -3;

	/**
	 * 获取税控码二维码失败
	 */
	public static final int SKMEWMFIAL = -4;

	/**
	 * 文件本地实例化失败
	 */
	public static final int LOCALSAVEFIAL = -5;

	static {
		fpztMap.clear();
		fpztMap.put(String.valueOf(SCPDFSUCCESS), "PDF生成及签章成功");
		fpztMap.put(String.valueOf(SCPDFFIAL), "PDF生成失败，未签章");
		fpztMap.put(String.valueOf(PDFSIGNFIAL), "PDF生成成功，签章失败");
		fpztMap.put(String.valueOf(JCISNULL), "此企业没有可用结存");
		fpztMap.put(String.valueOf(SKMEWMFIAL), "获取税控码二维码失败");
		fpztMap.put(String.valueOf(LOCALSAVEFIAL), "文件本地实例化失败");
	}

	public static String getFpzt(int fpzt) {
		String fpztStr = String.valueOf(fpztMap.get(new Integer(fpzt)));
		return fpztStr == null ? "未定义的发票状态" : fpztStr;
	}
}
