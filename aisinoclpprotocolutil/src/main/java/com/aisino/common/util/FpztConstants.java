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
	 * 获取发票开具明细信息错误！
	 */
	public static final int PFMXFIAL = -5;
	

	/**
	 * 文件本地实例化失败
	 */
	public static final int LOCALSAVEFIAL = -6;
	
	/**
	 * 调用京东接口失败
	 */
	public static final int JDJKSB = -7;
	
	   /**
     * 发票赋码失败
     */
    public static final int TAXCODE_FAIL = -8;
    
    /**
     * 发票明细获取数据失败
     */
    public static final int TAXCODE_FPKJMX_FAIL = -9;
    
    /**
     * 调用balance返回的发票代码号码为空
     */
    public static final int TAXCODE_BALANCE_RETURN_FAIL = -10;
    /**
     * 生成发票时从数据库中获取代码号码为空
     */
    public static final int GENERATOR_INVOICE_FAIL = -11;
	
	static {
		fpztMap.clear();
		fpztMap.put(String.valueOf(SCPDFSUCCESS), "PDF生成及签章成功");
		fpztMap.put(String.valueOf(SCPDFFIAL), "PDF生成失败，未签章");
		fpztMap.put(String.valueOf(PDFSIGNFIAL), "PDF生成成功，签章失败");
		fpztMap.put(String.valueOf(JCISNULL), "此企业没有可用结存");
		fpztMap.put(String.valueOf(SKMEWMFIAL), "获取税控码二维码失败");
		fpztMap.put(String.valueOf(PFMXFIAL), "获取发票开具明细信息错误");
		fpztMap.put(String.valueOf(LOCALSAVEFIAL), "文件本地实例化失败");
		fpztMap.put(String.valueOf(JDJKSB), "调用京东接口失败");
		fpztMap.put(String.valueOf(TAXCODE_FAIL), "(发票赋码)签章ID未配置,发票赋码失败.");
		fpztMap.put(String.valueOf(TAXCODE_FPKJMX_FAIL), "(发票赋码)发票明细获取数据失败");
		fpztMap.put(String.valueOf(TAXCODE_BALANCE_RETURN_FAIL), "(发票赋码)发票赋码失败,");
		fpztMap.put(String.valueOf(GENERATOR_INVOICE_FAIL), "生成发票时从数据库中获取代码号码为空,");
	}

	public static String getFpzt(int fpzt) {
		String fpztStr = String.valueOf(fpztMap.get(fpzt+""));
		return fpztStr == null ? "未定义的发票状态" : fpztStr;
	}
}
