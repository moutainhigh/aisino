package com.aisino.domain.einvoice.utils;

import java.text.DecimalFormat;

/**
 * Created by Martin.Ou on 2014/9/15.
 */
public final class ChineseCurrency {
    private ChineseCurrency() {
    }

    /**
     * <p>
     * 小写金额转化中文
     * </p>
     *
     * @param o
     * @return String
     * @author: wuyong@aisino.com
     * @date: Created on Sep 11, 2013 3:00:05 PM
     */
    public static String toChineseCurrency(Number o) {
        String s = new DecimalFormat("#.00").format(o);
        s = s.replaceAll("\\.", "");
        final char[] digit = {'零', '壹', '贰', '叁', '肆', '伍', '陆', '柒', '捌', '玖'};
        final String unit = "仟佰拾兆仟佰拾亿仟佰拾万仟佰拾圆角分";
        final int l = unit.length();
        StringBuilder sb = new StringBuilder(unit);
        for (int i = s.length() - 1; i >= 0; i--) {
            sb = sb.insert(l - s.length() + i, digit[s.charAt(i) - 0x30]);
        }
        s = sb.substring(l - s.length(), l + s.length());
        s = s.replaceAll("零[拾佰仟]", "零").replaceAll("零{2,}", "零").replaceAll("零([兆圆元])", "$1").replaceAll("零[角分]", "");
        if (s.endsWith("角")) {
            s += "零分";
        }
        if (!s.contains("角") && !s.contains("分") && s.contains("圆")) {
            s += "整";
        }
        if (s.contains("分") && !s.contains("整") && !s.contains("角")) {
            s = s.replace("圆", "圆零");
        }

        return s;
    }
}
