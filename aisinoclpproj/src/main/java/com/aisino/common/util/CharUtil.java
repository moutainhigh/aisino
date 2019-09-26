package com.aisino.common.util;

import java.io.UnsupportedEncodingException;
import java.lang.Character.UnicodeBlock;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CharUtil {
	private static Logger log = LoggerFactory.getLogger(CharUtil.class);
	private static String AVAILABLE_CHAR="";
	/**
	 * 字符串装换成16进制
	 * @param s
	 * @return
	 */
	public static String toStringHex(String s) {
		byte[] baKeyword = new byte[s.length() / 2];
		for (int i = 0; i < baKeyword.length; i++) {
			try {
				baKeyword[i] = (byte) (0xff & Integer.parseInt(
						s.substring(i * 2, i * 2 + 2), 16));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		try {
			s = new String(baKeyword, "UTF-8");
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return s;
	}


	/**
	 * utf-8编码下的空格转换为ASCII的空格
	 * 
	 * @param str
	 *            待替换字符串
	 * @return 替换后的字符串
	 */
	public static String replaceSpace(String str) {
		byte bytes[] = { (byte) 0xC2, (byte) 0xA0 };
		byte bt[] = { (byte) 0x20};
		try {
			String UTFSpace = new String(bytes, "utf-8");
			String AscallSpace = new String(bt, "utf-8");
			return str.replaceAll(UTFSpace, AscallSpace);
		} catch (UnsupportedEncodingException e) {
			log.error("不支持的编码格式，UTF-8空格替换失败",e);
		}
		return null;
	}
	/**
	 * 通过正则表达式，检测输入String中是否含有非GBK字符。 
	 * 
	 * @author wangfusheng
	 * 2015.8.24 Unicode2GBK更新GBK范围
	 * 原因：¤开票软件的保留字符，其他会导致开票软件验签失败
	 * 对matchesX(String orstr)和replaceX(String orstr)
	 * 剔除以下字符│  ┃ ǚ ┄  ǔ ü ǘ        ù ¤ 
	 * 对应编码
	 * \u2502\uE7F0\u2503\uE7EF \u01DA \u2504 \uE7E8 \u01D4 
	 * \u00FC\u01D8\uE7ED\uE7EA\uE7EC\uE7EE\uE7F1\uE7F2\uE7F3\u00F9\u00A4\uE76C
	 * 按顺序编码	
	 * \u00A4\u00F9\u00FC\u01D4\u01D8\u01DA\u2502\u2503\u2504\uE76C\uE7E8\uE7EA\uE7ED
	 * \uE7EC\uE7EE\uE7EF\uE7F0\uE7F1\uE7F2\uE7F3
	 */
	public static boolean  matchesX(String orstr){
		if(orstr == null){
			return false;
		}
		String regex = "^[\u0000-\u007F|"+
				"|\u00A7|\u00A8|\u00B0|\u00B1|\u00B7|\u00D7|\u00E0|\u00E1|\u00E8-\u00EA|\u00EC|\u00ED|\u00F2|\u00F3|\u00F7|\u00FA|"+
				"\u0101|\u0113|\u011B|\u012B|\u0144|\u0148|\u014D|\u016B|\u01CE|\u01D0|\u01D2|\u01D6|\u01DC|"+
				"\u0251|\u0261|"+
				"\u02C7|\u02C9|\u02CA|\u02CB|\u02D9|" +
				"\u0391-\u03A1|\u03A3-\u03A9|\u03B1-\u03C1|\u03C3-\u03C9|"+
				"\u0401|\u0410-\u044F|\u0451|" +
				"\u2010|\u2013-\u2016|\u2018|\u2019|\u201C|\u201D|\u2025|\u2026|\u2030|\u2032|\u2033|\u2035|\u203B|"+
				"\u20AC|"+
				"\u2103|\u2105|\u2109|\u2116|\u2121|"+
				"\u2160-\u216B|\u2170-\u2179|"+
				"\u2190-\u2193|\u2196-\u2199|"+
				"\u2208|\u220F|\u2211|\u2215|\u221A|\u221D-\u221F|\u2220|\u2223|\u2225|\u2227-\u222B|\u222E|"+
				"\u2234-\u2237|\u223D|\u2248|\u224C|\u2252|\u2260|\u2261|\u2264-\u2267|\u226E|\u226F|\u2295|\u2299|\u22A5|\u22BF|"+
				"\u2312|"+
				"\u2460-\u2469|\u2474-\u249B|"+
				"\u2500|\u2501|\u2505-\u254B|"+
				"\u2550-\u2573|"+
				"\u2581-\u258F|\u2593-\u2595|"+
				"\u25A0|\u25A1|\u25B2|\u25B3|\u25BC|\u25BD|\u25C6|\u25C7|\u25CB|\u25CE|\u25CF|\u25E2-\u25E5|"+
				"\u2605|\u2606|\u2609|\u2640|\u2642|"+
				"\u3000-\u3003|\u3005-\u3017|\u301D-\u301E|\u3021-\u3029|"+
				"\u3041-\u3093|\u309B-\u309E|"+
				"\u30A1-\u30F6|\u30FC-\u30FE|"+
				"\u3105-\u3129|"+
				"\u3220-\u3229|\u3231|\u32A3|"+
				"\u338E|\u338F|\u339C-\u339E|\u33A1|\u33C4|\u33CE|\u33D1|\u33D2|\u33D5|"+
				"\u4e00-\u9fa5|"+
				//除去\uE7E8\uE7EA\uE7ED\uE7EC\uE7EE\uE7EF\uE7F0\uE7F1\uE7F2\uE7F3
				//"\ue000-\ue864|"+
				"\uE000-\uE76B|\uE76D-\uE7E7|\uE7E9|\uE7EB|\uE7F4-\uE864|"+
				"\uF92C|\uF979|\uF995|\uF9E7|\uF9F1|\uFA0C-\uFA0F|\uFA11|\uFA13|\uFA14|\uFA18|\uFA1F|\uFA20|\uFA21|\uFA23|\uFA24|\uFA27-\uFA29|"+
				"\uFE30|\uFE31|\uFE33-\uFE44|\uFE49-\uFE4F|"+
				"\uFE50-\uFE52|\uFE54-\uFE57|\uFE59-\uFE66|\uFE68-\uFE6B|"+
				"\uFF01-\uFF5E|\uFFE0-\uFFE5]*";
		return orstr.matches(regex);
	}

	/**
	 * 通过正则表达式，将输入String中非GBK字符替换为空格。
	 */
	public static String replaceX(String orstr) {

		if (orstr == null) {
			return null;
		}
		/*
		 * ASCII 标点符号 C0 Controls and Basic Latin (Basic Latin) (0000–007F)
		 * 0x000-0x007F C1 Controls and Latin-1 Supplement (0080–00FF) 0x00A4
		 * 0x00A7 0x00A8 0x00B0 0x00B1 0x00B7 0x00D7 0x00E0 0x00E1 0x00E8-0x00EA
		 * 0x00EC 0x00ED 0x00F2 0x00F3 0x00F7 0x00FA Latin Extended-A
		 * (0100–017F) Latin Extended-B (0180–024F) 0x0101 0x0113 0x011B 0x012B
		 * 0x0144 0x0148 0x014D 0x016B 0x01CE 0x01D0 0x01D2 0x01D4 0x01D6 0x01D8
		 * 0x01DA 0x01DC IPA Extensions (0250–02AF) 0x0251 0x0261 间隔 Spacing
		 * Modifier Letters (02B0–02FF) 0x02C7 0x02C9 0x02CA 0x02CB 0x02D9 希腊字符
		 * Greek and Coptic (0370–03FF) 0x0391-0x03a1 0x03a3-0x03a9
		 * 0x03b1-0x03c1 0x03c3-0x03c9 Cyrillic (0400–04FF) 0x0401 0x0410-0x044F
		 * 0x0451 通用标点 General Punctuation (2000–206F) 0x2010 0x2013-0x2016
		 * 0x2018-0x2019 0x201C-0x201D 0x2025-0x2026 0x2030 0x2032-0x2033 0x2035
		 * 0x203B Currency Symbols (20A0–20CF) 0x20AC #EURO SIGN 似字母符号
		 * Letterlike Symbols (2100–214F) 0x2103 0x2105 0x2109 0x2116 0x2121
		 * Number Forms (2150–218F) 0x2160-0x216B 0x2170-0x2179 Arrows
		 * (2190–21FF) 0x2190-0x2193 0x2196-0x2199 Mathematical Operators
		 * (2200–22FF) 0x2208 0x220F 0x2211 0x2215 0x221A 0x221D-0x221F 0x2220
		 * 0x2223 0x2225 0x2227-0x222B 0x222E 0x2234-0x2237 0x223D 0x2248 0x224C
		 * 0x2252 0x2260 0x2261 0x2264-0x2267 0x226E 0x226F 0x2295 0x2299 0x22A5
		 * 0x22BF 杂项技术 Miscellaneous Technical (2300–23FF) 0x2312 封闭式字母数字
		 * Enclosed Alphanumerics (2460–24FF) 0x2460-0x2469 0x2474-0x249B 箱图 Box
		 * Drawing (2500–257F) 0x2500-0x254B 0x2550-0x2573 块元素 Block Elements
		 * (2580–259F) 0x2581-0x258F 0x2593-0x2595 几何形态 Geometric Shapes
		 * (25A0–25FF) 0x25A0 0x25A1 0x25B2 0x25B3 0x25BC 0x25BD 0x25C6 0x25C7
		 * 0x25CB 0x25CE 0x25CF 0x25E2-0x25E5 杂项符号Miscellaneous Symbols
		 * (2600–26FF) 0x2605 0x2606 0x2609 0x2640 0x2642 中日韩标点 CJK Symbols and
		 * Punctuation (3000–303F) 0x3000-0x3003 0x3005-0x3017 0x301D-0x301E
		 * 0x3021-0x3029 平假名Hiragana (3040–309F) 0x3041-0x3093 0x309B-0x309E 片假名
		 * Katakana (30A0–30FF) 30A1–30F6 30FC–30FE 汉语拼音 Bopomofo (3100–312F)
		 * 0x3105-0x3129 Enclosed CJK Letters and Months (3200–32FF)
		 * 0x3220-0x3229 0x3231 0x32A3 CJK兼容 CJK Compatibility (3300–33FF)
		 * 0x338E 0x338F 0x339C-0x339E 0x33A1 0x33C4 0x33CE 0x33D1 0x33D2 0x33D5
		 * 中日韩统一汉字 CJK Unified Ideographs (4E00–9FFF) 0x4e00-0x9fa5
		 * 新增用字私人使用领域Private Use Areas 0x000-0xE7E7 0xE7E9 0xE7EB 0xE7F4-0xE864
		 * 中日韩兼容表意文字CJK Compatibility Ideographs (F900–FAFF) 0xF92C 0xF979
		 * 0xF995 0xF9E7 0xF9F1 0xFA0C-0xFA0F 0xFA11 0xFA13 0xFA14 0xFA18 0xFA1F
		 * 0xFA20-0xFA24 0xFA27-0xFA29 CJK Compatibility Forms (FE30–FE4F)
		 * 0xFE30 0xFE31 0xFE33-0xFE44 0xFE49-0xFE4F 小形变量Small Form Variants
		 * (FE50–FE6F) 0xFE50-0xFE52 0xFE54-0xFE57 0xFE59-0xFE66 0xFE68-0xFE6B
		 * 全角字符 Halfwidth and Fullwidth Forms (FF00–FFEF) 0xFF01-0xFF5E
		 * 0xFFE0-0xFFE5
		 */
		// 剔除│ ┃ ǚ ┄  ǔ ü ǘ        ù¤
		// \u00f9\u00fc\u01D4\u01D8\u01DA\u2502\u2503\u2504
		// \uE7E8\uE7EA\uE7ED\uE7EC\uE7EE\uE7EF\uE7F0\uE7F1\uE7F2\uE7F3
		// \u2502\u2503\u2504\u00a4
		return orstr.replaceAll(
						"[^\u0000-\u007F "
								+ "\u00A7\u00A8\u00B0\u00B1\u00B7\u00D7\u00E0 \u00E1\u00E8-\u00EA\u00EC\u00ED \u00F2\u00F3\u00F7\u00FA"
								+ "\u0101\u0113\u011B\u012B\u0144\u0148\u014D\u016B\u01CE\u01D0\u01D2\u01D6\u01DC"
								+ "\u0251\u0261"
								+ "\u02C7\u02C9\u02CA\u02CB\u02D9"
								+ "\u0391-\u03A1\u03A3-\u03A9\u03B1-\u03C1\u03C3-\u03C9"
								+ "\u0401\u0410-\u044F\u0451"
								+ "\u2010\u2013-\u2016\u2018\u2019\u201C\u201D\u2025\u2026\u2030\u2032\u2033\u2035\u203B"
								+ "\u20AC"
								+ "\u2103\u2105\u2109\u2116\u2121"
								+ "\u2160-\u216B\u2170-\u2179"
								+ "\u2190-\u2193\u2196-\u2199"
								+ "\u2208\u220F\u2211\u2215\u221A\u221D-\u221F\u2220\u2223\u2225\u2227-\u222B\u222E"
								+ "\u2234-\u2237\u223D\u2248\u224C\u2252\u2260\u2261\u2264-\u2267\u226E\u226F\u2295\u2299\u22A5\u22BF"
								+ "\u2312"
								+ "\u2460-\u2469\u2474-\u249B"
								+
								// 过滤掉\u2502\u2503\u2504
								// "\u2500-\u254B"+
								"\u2500\u2501\u2505-\u254B"
								+ "\u2550\u2573"
								+ "\u2581-\u258F\u2593-\u2595"
								+ "\u25A0\u25A1\u25B2\u25B3\u25BC\u25BD\u25C6 \u25C7\u25CB\u25CE\u25CF\u25E2-\u25E5"
								+ "\u2605\u2606\u2609\u2640\u2642"
								+ "\u3000-\u3003\u3005-\u3017\u301D-\u301E\u3021-\u3029"
								+ "\u3041-\u3093\u309B-\u309E"
								+ "\u30A1-\u30F6\u30FC-\u30FE"
								+ "\u3105-\u3129"
								+ "\u3220-\u3229 \u3231 \u32A3"
								+ "\u338E\u338F\u339C-\u339E\u33A1\u33C4\u33CE\u33D1\u33D2\u33D5"
								+ "\u4e00-\u9fa5"
								+
								// 剔除\uE7F0\uE7EF\uE7E8\uE7ED\uE7EA\uE7EC\uE7EE\uE7F1\uE7F2\uE7F3
								// "\ue000-\ue864"+
								"\uE000-\uE76B\uE76D-\uE7E7\uE7E9\uE7EB\uE7F4-\uE864"
								+ "\uF92C\uF979\uF995\uF9E7\uF9F1 \uFA0C-\uFA0F\uFA11\uFA13\uFA14\uFA18\uFA1F \uFA20\uFA21\uFA23\uFA24\uFA27-\uFA29"
								+ "\uFE30\uFE31\uFE33-\uFE44\uFE49-\uFE4F"
								+ "\uFE50-\uFE52\uFE54-\uFE57\uFE59-\uFE66\uFE68-\uFE6B"
								+ "\uFF01-\uFF5E\uFFE0-\uFFE5]", " ");

	}

	/**
	 * 将不换行空格（NO-BREAK SPACE，Unicode 0x00a0，UTF-8编码：0xC2A0）替换为普通空格。
	 * 用于避免因数据库字符集不兼容导致这个字符变为问号“?”的情况。
	 */
	public static String nobreakSpaceToSpace(String str) {
		if (str == null) {
			return null;
		}
		return str.replaceAll("[\ua000\u00a0\u3000]", "\u0020");
	}

	/**
	 * 替换 string 中的不可见字符，注意：string 为16进制字符串</br> 将来如果发现新的不可见字符，需修改此方法
	 * 
	 * @param string
	 * @return
	 */
	public static String replaceInvisibleChar(String string) {
		// 将16进制字符C2A0替换为20（空格），因 C2A0 看起来像一个空格
		string = string.toUpperCase().replaceAll("C2A0", "20");
		return string;
	}

	/**
	 * 通过UnicodeBlock设置过滤Unicode 规范中字符块中的字符,并将范围外的字符替换为空格。 specified character
	 * Filter
	 */
	/*
	 * public static String specCharFilter(String info){ if (info == null) {
	 * return null; } StringBuffer tmp = new StringBuffer() ; for(char cc :
	 * info.toCharArray()){ tmp.append(switchChar(cc)); } return tmp.toString();
	 * }
	 */

	private static char switchChar(char source) {
		UnicodeBlock ub = UnicodeBlock.of(source);
		if (Arrays.asList(ubs).contains(ub)) {
			return source;
		}
		return ' ';
	}

	/**
	 * UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS：CJK 统一表意符号
	 * UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION：CJK 符号和标点
	 * CJK的意思是“Chinese，Japanese，Korea”的简写 ，实际上就是指中日韩三国的象形文字的Unicode编码
	 * 下面的四个是显示拉丁字符 UnicodeBlock.BASIC_LATIN, UnicodeBlock.LATIN_1_SUPPLEMENT,
	 * UnicodeBlock.LATIN_EXTENDED_A, UnicodeBlock.LATIN_EXTENDED_B,
	 * 下面两个是显示日语中的平假名和片假名 UnicodeBlock.KATAKANA, UnicodeBlock.HIRAGANA,
	 */

	private static final UnicodeBlock[] ubs = new UnicodeBlock[] {
			UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS,
			UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION, UnicodeBlock.BASIC_LATIN,
			UnicodeBlock.LATIN_1_SUPPLEMENT, UnicodeBlock.LATIN_EXTENDED_A,
			UnicodeBlock.LATIN_EXTENDED_B, UnicodeBlock.KATAKANA,
			UnicodeBlock.HIRAGANA, UnicodeBlock.BOPOMOFO };

	public static String repaceToUtf8(String str) throws UnsupportedEncodingException{
//		byte bytes[] = { (byte) 0xC2, (byte) 0xA0 };
		byte bytes[] = { (byte) 0x00a0};
		String UTFSpace = new String(bytes, "UTF-8");
		return str.replaceAll("[∑]", UTFSpace);
	}
}
