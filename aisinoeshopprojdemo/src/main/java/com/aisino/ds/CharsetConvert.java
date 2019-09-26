package com.aisino.ds;

import java.io.UnsupportedEncodingException;

public class CharsetConvert {
	public static String convert(String str, String fromCoding, String toCoding)
			throws UnsupportedEncodingException {
		if (fromCoding == null || toCoding == null
				|| fromCoding.equalsIgnoreCase(toCoding))
			return str;
		if (str == null)
			return null;
		return new String(str.getBytes(fromCoding), toCoding);
	}

	public static String dbToLocal(String str) {
		try {
			return convert(str, SystemConfig.dbCharset,
					SystemConfig.localCharset);
		} catch (UnsupportedEncodingException e) {
			return str;
		}
	}

	public static String localToDb(String str) {
		try {
			return convert(str, SystemConfig.localCharset,
					SystemConfig.dbCharset);
		} catch (UnsupportedEncodingException e) {
			return str;
		}
	}
}