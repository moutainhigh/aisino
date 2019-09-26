package com.aisino.einv.ws.test;

import java.io.UnsupportedEncodingException;

public class TestXMMC {
    /**
     * 
     * @param text
     *            目标字符串
     * @param length
     *            截取长度
     * @param encode
     *            采用的编码方式
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String xmmcsubstring(String text, int length, String encode)
            throws UnsupportedEncodingException {
        if (text == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        int currentLength = 0;
        for (char c : text.toCharArray()) {
            currentLength += String.valueOf(c).getBytes(encode).length;
            if (currentLength <= length) {
                sb.append(c);
            } else {
                break;
            }
        }
        return sb.toString();

    }

    public static void main(String[] args) throws Exception {
        String temp = "格之格（G&G）NT-CN0436CT 易加粉硒鼓CB436A（适用于HP P1505/M1120/M1522/M1550/Canon LBP-3250/436）";
        System.out.println((temp.toCharArray()).length);
        System.out.println(xmmcsubstring(temp, 100, "UTF-8"));
    }
}
