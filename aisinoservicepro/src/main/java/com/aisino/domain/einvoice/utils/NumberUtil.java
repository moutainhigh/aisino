package com.aisino.domain.einvoice.utils;

import java.util.Random;

/**
 * Created by Bourne.Lv on 2014/11/17.
 */
public final class NumberUtil {

    private NumberUtil() {
    }

    /**
     * 生成指定长度的数字型字符串
     *
     * @param length 数字型字符串长度
     * @return 指定长度的数字型字符串
     */
    public static String obtainRandomNum(Integer length) {
        final StringBuilder result = new StringBuilder();
        final Random random = new Random();
        for (int i = 0; i < length; i++) {
            result.append(random.nextInt(10));
        }
        return result.toString();
    }
}
