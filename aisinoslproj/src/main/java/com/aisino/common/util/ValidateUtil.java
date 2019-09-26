package com.aisino.common.util;

import java.math.RoundingMode;
import java.text.DecimalFormat;

import com.google.common.base.Strings;
import org.apache.commons.lang.math.NumberUtils;

/**
 * <p>
 * [描述信息：基础验证工具类]
 * </p>
 *
 * @author wuyong@aisino.com
 * @version 1.0 Created on Nov 2, 2013 3:14:51 PM
 */
public class ValidateUtil {

    private ValidateUtil(){}

    /**
     * 非空验证操作
     *
     * @param parmaterStr
     * @return is NullOrEmpty return true
     */
    public static boolean checkParameterIsEmpty(String parmaterStr) {
        return Strings.isNullOrEmpty(parmaterStr);
/*
        */
/*	判断字符串为空	2014年7月28日16:12:36			*//*

        if (Strings.isNullOrEmpty(parmaterStr)) {
            return true;
        }

        return false;
*/
    }

    /**
     *
     * <p>校验字符串是否为纯数字</p>
     *
     * @param paramaterStr
     * @return boolean
     * @date: Created on 2014-10-23 下午04:57:30
     */
    public static boolean checkParameterIsNotNum(String paramaterStr){
        return NumberUtils.isDigits(paramaterStr);
/*
        for (int i = 0; i < parmaterStr.length(); i++){
            if (!Character.isDigit(parmaterStr.charAt(i))){
                return false;
            }
        }
        return true;
*/
    }


    /**
     * 长度验证
     *
     * @param parmaterStr
     * @param smallStrNumber
     * @param bigStrNumber
     * @return
     */
    public static boolean checkParameterLength(String parmaterStr, int smallStrNumber, int bigStrNumber) {
        if (parmaterStr.length() < smallStrNumber) {
            return true;
        }
        if (parmaterStr.length() > bigStrNumber) {
            return true;
        }
        return false;
    }
    /**
     * 
     * <p>Double类型的格式化</p>
     * 
     * @param parameterStr
     * @param length
     * @return String
     * @author: 张双超
     * @date: Created on Sep 10, 2015 3:18:51 PM
     */
    public static String decimalFormat(String parameterStr,int length){
        String formatString = "#########0.00";
        if(length == 1){
            formatString = "#########0.0";
        }else if (length == 2) {
            formatString = "#########0.00";
        }else if (length == 3) {
            formatString = "#########0.000";
        }else if (length == 4) {
            formatString = "#########0.0000";
        }else if (length == 5) {
            formatString = "#########0.00000";
        }else if (length == 6) {
            formatString = "#########0.000000";
        }else if (length == 7) {
            formatString = "#########0.0000000";
        }else if (length == 8) {
            formatString = "#########0.00000000";
        }else {
            formatString = "#########0.00";
        }
        
        DecimalFormat decimalFormat = new DecimalFormat(formatString);
        decimalFormat.setRoundingMode(RoundingMode.HALF_UP);
        
        return decimalFormat.format(Double.parseDouble(parameterStr));
    }
    /**
     * 
     * <p>方法重载,double类型的格式化</p>
     * 
     * @param parameterDouble
     * @param length
     * @return String
     * @author: 张双超
     * @date: Created on Sep 10, 2015 3:19:38 PM
     */
    public static String decimalFormat(Double parameterDouble,int length){
        String formatString = "#########0.00";
        if(length == 1){
            formatString = "#########0.0";
        }else if (length == 2) {
            formatString = "#########0.00";
        }else if (length == 3) {
            formatString = "#########0.000";
        }else if (length == 4) {
            formatString = "#########0.0000";
        }else if (length == 5) {
            formatString = "#########0.00000";
        }else if (length == 6) {
            formatString = "#########0.000000";
        }else if (length == 7) {
            formatString = "#########0.0000000";
        }else if (length == 8) {
            formatString = "#########0.00000000";
        }else {
            formatString = "#########0.00";
        }
        
        DecimalFormat decimalFormat = new DecimalFormat(formatString);
        decimalFormat.setRoundingMode(RoundingMode.HALF_UP);
        
        return decimalFormat.format(parameterDouble);
    }
}
