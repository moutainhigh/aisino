package com.aisino.domain.einvoice.pdf;

/*
 * pdf盖章请求返回数据类
 */
public class AddStampRec {
    public int resValue; // 返回值
    public byte[] pdf; // 蓋章的pdf

    public AddStampRec() {
        resValue = 0;
        pdf = null;
    }
}