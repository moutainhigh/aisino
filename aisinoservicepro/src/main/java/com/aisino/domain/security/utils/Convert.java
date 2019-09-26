package com.aisino.domain.security.utils;

import com.aisino.domain.SystemConfig;

/**
 * Created by Martin.Ou on 2014/9/4.
 */
public final class Convert {
    private Convert() {

    }

    public static String fillWithZero(String str, int length) throws Exception {
        int byteLength = 10;
        if (str != null) {
            byteLength = str.getBytes(SystemConfig.dataCharset).length;
        }

        for (int i = 0; i < length - byteLength; i++) {
            str = "0" + str;
        }

        return str;
    }

    public static byte hexStringToByte(String hexString) {
        if ((hexString == null) || (hexString.equals(""))
                || (hexString.length() != 4)
                || (!hexString.toLowerCase().startsWith("0x"))) {
            throw new NumberFormatException();
        }

        hexString = hexString.substring(2).toUpperCase();
        final char[] hexChars = hexString.toCharArray();
        byte d = (byte) (charToByte(hexChars[0]) << 4 | charToByte(hexChars[1]));

        return d;
    }

    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }
}
