package com.aisino.domain.security;

/**
 * Created by Martin.Ou on 2014/9/5.
 */
public final class SecurityConstant {

    /**
     * 报文错误长度
     */
    public static final Integer PACKET_ERRORCODELENGTH = 4;
    public static final String KEY_STATE = "state";
    public static final String KEY_ERRORMESSAGE = "errorMessage";
    public static final String KEY_SKM = "skm";
    public static final String KEY_EWM = "ewm";
    public static final String Result_OK = "OK";
    public static final String Result_ER = "ER";

    public static final String qzfwAisino = "aisino";
    public static final String qzfwSmyjy = "smyjy";
    public static final String INV_UPLOAD_SUCCESS = "4000"; /* 发票上传成功 */
    public static final String INV_UPLOAD_FAILED = "4001"; /* 发票上传失败 */
    public static final String INV_SEND_SUCCESS = "3000"; /* 下发发票成功 */
    public static final String INV_SEND_FAILED = "3001"; /* 下发发票失败 */
    public static final String INV_SEND_FAILED_BYDSPT = "3002"; /* 下发发票失败 */
    public static final String INV_SEND_SUCCESS_BYDSPT = "3003"; /* 下发发票失败 */
    public static final String INV_CREATE_SUCCESS = "2000"; /* 生成发票成功 */
    public static final String INV_CREATE_FAILED = "2001"; /* 生成发票失败 */
    public static final String INV_CREATE_FAILED_BYDSPT = "2002"; /* 生成发票失败 */
    public static final String INV_GETDATA_SUCCESS = "1000"; /* 从京东获取数据成功 */
    public static final String INV_GETDATA_FAILED = "1001"; /* 从京东获取数据失败 */
    public static final String INV_CSH = "0000"; /* 初始化状态 */
    public static final String INV_WCLZT = "0"; /* 发票未处理状态 */
    public static final String INV_UPLOAD_SUCCESS_BF = "4002";//内网受理成功

    public static final String SIZE_GET_ESHOP_UPLOAD = "1000";//获取SIZE:上传电商的电子发票信息

}
