package com.aisino.test;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import com.aisino.common.util.MathUtil;
import com.aisino.common.util.ValidateUtil;
import com.aisino.common.util.XmlPar;
import com.aisino.protocol.bean.COMMON_FPKJ_FPT;
import com.aisino.protocol.bean.COMMON_FPKJ_XMXX;
import com.mysql.jdbc.StringUtils;

public class TestZkh {
    public static void main(String[] args) {
        /**
         * 折扣行判断
         */
//        String xmString = "折扣行数(0.00%)";
//      //判断红票和蓝票的折扣行没有折扣率的,如果以折扣开头的进行校验
////        if(xmString.startsWith("折扣")){
//            if(checkSphIsZkh(xmString)){
//            
//            String xmmc = xmString;
//                
//            if(!xmmc.contains("(")||!xmmc.contains(")")||!xmmc.contains("%")){   //如果以折扣开头的项目名称中,不包含()和%的返回折扣行格式错误
//                System.out.println("折扣行不合法");
//            }else if (StringUtils.isNullOrEmpty(xmmc.substring(xmmc.indexOf("(")+1, xmmc.indexOf(")")).replace("%", ""))||Double.parseDouble(xmmc.substring(xmmc.indexOf("(")+1, xmmc.indexOf(")")).replace("%", ""))<0 || Double.parseDouble(xmmc.substring(xmmc.indexOf("(")+1, xmmc.indexOf(")")).replace("%", ""))>100){
//                System.out.println("折扣行折扣率不能为空且折扣率在0%到100%之间,包含0和100");
//            
//            }else if(("折扣行数").equals(xmmc.substring(0,4))&&(StringUtils.isNullOrEmpty(xmmc.substring(xmmc.indexOf("折扣行数")+4, xmmc.indexOf("(")))||Integer.parseInt(xmmc.substring(xmmc.indexOf("折扣行数")+4, xmmc.indexOf("(")))<=1)){
//                System.out.println("折扣行不合法,折扣行数不能小于等于1");
//            }else if(("折扣").equals(xmmc.substring(0,2))&& !("行数").equals(xmmc.substring(2,4))&&!StringUtils.isNullOrEmpty(xmmc.substring(xmmc.indexOf("折扣")+2, xmmc.indexOf("(")))){
//                System.out.println("折扣行不合法,折扣不能有值");
//            }
//        }
//        System.out.println("折扣行名称:"+xmString);
        
//        /**
//         * 商品行单价误差一分钱校验
//         */
//        COMMON_FPKJ_XMXX[] protocolProjectBean = new COMMON_FPKJ_XMXX[7];
//        COMMON_FPKJ_XMXX mx = new COMMON_FPKJ_XMXX();
//        mx.setXMDJ("4.42477876");
//        mx.setXMSL("2.0000");
//        mx.setXMJE("8.85");
//        mx.setXMMC("鼠标");
//        mx.setSL("13%");
//        mx.setSE("1.15");
//        protocolProjectBean[0]=mx;
//        COMMON_FPKJ_XMXX mx1 = new COMMON_FPKJ_XMXX();
//        mx1.setXMDJ("8.84955752");
//        mx1.setXMSL("2");
//        mx1.setXMJE("17.70");
//        mx1.setXMMC("鼠标1");
//        mx1.setSL("17%");
//        mx1.setSE("2.30");
//        protocolProjectBean[1]=mx1;
//        
//        COMMON_FPKJ_XMXX mx3 = new COMMON_FPKJ_XMXX();
//        mx3.setXMDJ("-6.19469027");
//        mx3.setXMSL("1.0000");
//        mx3.setXMJE("-6.19");
//        mx3.setXMMC("折扣行数2(23.300%)");
//        mx3.setSL("13%");
//        mx3.setSE("-0.81");
//        protocolProjectBean[2]=mx3;
//                   
//        COMMON_FPKJ_XMXX mx4 = new COMMON_FPKJ_XMXX();
//        mx4.setXMDJ("12.82051282");
//        mx4.setXMSL("10");
//        mx4.setXMJE("128.21");
//        mx4.setXMMC("ddddddd等等");
//        mx4.setSL("17%");
//        mx4.setSE("21.79");
//        protocolProjectBean[3]=mx4;
//        
//        COMMON_FPKJ_XMXX mx7 = new COMMON_FPKJ_XMXX();
//        mx7.setXMDJ("12.82051282");
//        mx7.setXMSL("10");
//        mx7.setXMJE("128.21");
//        mx7.setXMMC("ddddddd等等11111");
//        mx7.setSL("17%");
//        mx7.setSE("21.79");
//        protocolProjectBean[4]=mx7;
//                   
//        COMMON_FPKJ_XMXX mx5 = new COMMON_FPKJ_XMXX();
//        mx5.setXMDJ("-17.09401709");
//        mx5.setXMSL("1.000");
//        mx5.setXMJE("-17.09");
//        mx5.setXMMC("折扣(13.300%)");
//        mx5.setSL("17%");
//        mx5.setSE("-2.91");
//        protocolProjectBean[5]=mx5;
//                   
//        COMMON_FPKJ_XMXX mx6 = new COMMON_FPKJ_XMXX();
//        mx6.setXMDJ("57.52212389");
//        mx6.setXMSL("1.000");
//        mx6.setXMJE("57.52212389");
//        mx6.setXMMC("运费");
//        mx6.setSL("13%");
//        mx6.setSE("7.48");
//        protocolProjectBean[6]=mx6;
//        for (int i = 0; i < protocolProjectBean.length; i++) {
//            COMMON_FPKJ_XMXX fpkjxx_xmxx  = protocolProjectBean[i];
//            if (!ValidateUtil.checkParameterIsEmpty(fpkjxx_xmxx.getXMDJ()) && !ValidateUtil.checkParameterIsEmpty(fpkjxx_xmxx.getXMSL()) && !fpkjxx_xmxx.getXMMC().startsWith("折扣")) {
//                /**
//                 * 金额（不含税） = 项目单价 * 项目数量
//                 * 计算出的项目金额 与 订单传递的项目金额 比较，误差不能大于1分钱（即误差小于或等于1分钱）
//                 */
//                double js_xmje = MathUtil.mul(fpkjxx_xmxx.getXMDJ(), fpkjxx_xmxx.getXMSL()); // 金额 = 项目单价 * 项目数量
//                double yxmje = Double.parseDouble(fpkjxx_xmxx.getXMJE()); // 项目金额
//                double xmjeCompareResult = yxmje - js_xmje;
//                if(Math.abs(xmjeCompareResult) > 0.01){ // 误差不能大于1分钱（即误差小于或等于1分钱），否则项目金额有误
//                    System.out.println("cuowu"+(i+1));
//                }
//            }
//            System.out.println("商品:"+fpkjxx_xmxx.getXMMC()+"-------当前行:"+(i+1));
//            
//            
//        }
        
//        /**
//         * 根据金额来校验是否是折扣行
//         */
//        COMMON_FPKJ_XMXX[] protocolProjectBean = new COMMON_FPKJ_XMXX[7];
//        COMMON_FPKJ_XMXX mx = new COMMON_FPKJ_XMXX();
//        mx.setXMDJ("4.42477876");
//        mx.setXMSL("2.0000");
//        mx.setXMJE("8.85");
//        mx.setXMMC("鼠标");
//        mx.setSL("13%");
//        mx.setSE("1.15");
//        protocolProjectBean[0]=mx;
//        COMMON_FPKJ_XMXX mx1 = new COMMON_FPKJ_XMXX();
//        mx1.setXMDJ("8.84955752");
//        mx1.setXMSL("2");
//        mx1.setXMJE("17.70");
//        mx1.setXMMC("鼠标1");
//        mx1.setSL("17%");
//        mx1.setSE("2.30");
//        protocolProjectBean[1]=mx1;
//        
//        COMMON_FPKJ_XMXX mx3 = new COMMON_FPKJ_XMXX();
//        mx3.setXMDJ("-6.19469027");
//        mx3.setXMSL("1.0000");
//        mx3.setXMJE("-6.19");
//        mx3.setXMMC("折扣行数2(23.300%)");
//        mx3.setSL("13%");
//        mx3.setSE("-0.81");
//        protocolProjectBean[2]=mx3;
//                   
//        COMMON_FPKJ_XMXX mx4 = new COMMON_FPKJ_XMXX();
//        mx4.setXMDJ("12.82051282");
//        mx4.setXMSL("10");
//        mx4.setXMJE("128.21");
//        mx4.setXMMC("ddddddd等等");
//        mx4.setSL("17%");
//        mx4.setSE("21.79");
//        protocolProjectBean[3]=mx4;
//        
//        COMMON_FPKJ_XMXX mx7 = new COMMON_FPKJ_XMXX();
//        mx7.setXMDJ("12.82051282");
//        mx7.setXMSL("10");
//        mx7.setXMJE("128.21");
//        mx7.setXMMC("ddddddd等等11111");
//        mx7.setSL("17%");
//        mx7.setSE("21.79");
//        protocolProjectBean[4]=mx7;
//                   
//        COMMON_FPKJ_XMXX mx5 = new COMMON_FPKJ_XMXX();
//        mx5.setXMDJ("-17.09401709");
//        mx5.setXMSL("1.000");
//        mx5.setXMJE("-17.09");
//        mx5.setXMMC("折扣(13.300%)");
//        mx5.setSL("17%");
//        mx5.setSE("-2.91");
//        protocolProjectBean[5]=mx5;
//                   
//        COMMON_FPKJ_XMXX mx6 = new COMMON_FPKJ_XMXX();
//        mx6.setXMDJ("57.52212389");
//        mx6.setXMSL("1.000");
//        mx6.setXMJE("57.52212389");
//        mx6.setXMMC("运费");
//        mx6.setSL("13%");
//        mx6.setSE("7.48");
//        protocolProjectBean[6]=mx6;
//        String kplx = "2";
//        for (int i = 0; i < protocolProjectBean.length; i++) {
//            COMMON_FPKJ_XMXX fpkjxx_xmxx  = protocolProjectBean[i];
//            if("1".equals(kplx)){ // 蓝票
//                if (Double.parseDouble(fpkjxx_xmxx.getXMJE()) >= 0 && checkSphIsZkh(fpkjxx_xmxx)) {
//                    System.out.println("蓝票错误");
//                }
//            }else if("2".equals(kplx)){ // 红票
//                if (Double.parseDouble(fpkjxx_xmxx.getXMJE()) <= 0 && checkSphIsZkh(fpkjxx_xmxx)) {
//                    System.out.println("红票错误");
//                }
//            }else{ // 非法开票类型
//                System.out.println("1111");
//            }
////            System.out.println("商品:"+fpkjxx_xmxx.getXMMC()+"-------当前行:"+(i+1));
//            
//            String xmmc = "折扣行数2（";
//            if(xmmc.indexOf("(")==-1){
//                System.out.println("111adf1");
//            }
//            
//        }
        
        /**
         * 折扣行总方法
         */

      COMMON_FPKJ_FPT COMMON_FPKJ_FPT = new COMMON_FPKJ_FPT();
      COMMON_FPKJ_FPT.setKPHJJE("0.02");
      COMMON_FPKJ_FPT.setKPHJSE("0.02");
      COMMON_FPKJ_FPT.setHJBHSJE("0.00");
      COMMON_FPKJ_XMXX[] fpkjxx_xmxxs = new COMMON_FPKJ_XMXX[5];
      COMMON_FPKJ_XMXX mx = new COMMON_FPKJ_XMXX();
      mx.setXMDJ("0.354700855");
      mx.setXMSL("1.0000");
      mx.setXMJE("0.35");
      mx.setXMMC("鼠标");
      mx.setSL("17%");
      mx.setSE("0.06");
      fpkjxx_xmxxs[0]=mx;
      COMMON_FPKJ_XMXX mx1 = new COMMON_FPKJ_XMXX();
      mx1.setXMDJ("0.50000000");
      mx1.setXMSL("1.0000");
      mx1.setXMJE("0.50");
      mx1.setXMMC("22222");
      mx1.setSL("17%");
      mx1.setSE("0.09");
      fpkjxx_xmxxs[1]=mx1;
    COMMON_FPKJ_XMXX mx3 = new COMMON_FPKJ_XMXX();
    mx3.setXMDJ("-0.846153846");
    mx3.setXMSL("1.0000");
    mx3.setXMJE("-0.85");
    mx3.setXMMC("折扣行数2(99.000%)");
    mx3.setSL("17%");
    mx3.setSE("-0.14");
    fpkjxx_xmxxs[2]=mx3;
//      
      COMMON_FPKJ_XMXX mx7 = new COMMON_FPKJ_XMXX();
      mx7.setXMDJ("0.854700855");
      mx7.setXMSL("1.0000");
      mx7.setXMJE("0.85");
      mx7.setXMMC("鼠标");
      mx7.setSL("17%");
      mx7.setSE("0.15");
      fpkjxx_xmxxs[3]=mx7;
                 
      COMMON_FPKJ_XMXX mx5 = new COMMON_FPKJ_XMXX();
      mx5.setXMDJ("-0.846153846");
      mx5.setXMSL("1.0000");
      mx5.setXMJE("-0.85");
      mx5.setXMMC("折扣(99.000%)");
      mx5.setSL("17%");
      mx5.setSE("-0.14");
      fpkjxx_xmxxs[4]=mx5;
//                 
//      COMMON_FPKJ_XMXX mx6 = new COMMON_FPKJ_XMXX();
//      mx6.setXMDJ("57.52212389");
//      mx6.setXMSL("1.000");
//      mx6.setXMJE("57.52212389");
//      mx6.setXMMC("运费");
//      mx6.setSL("13%");
//      mx6.setSE("7.48");
//      fpkjxx_xmxxs[6]=mx6;
//      
      
//        COMMON_FPKJ_FPT COMMON_FPKJ_FPT = new COMMON_FPKJ_FPT();
//        COMMON_FPKJ_FPT.setKPHJJE("0.01");
//        COMMON_FPKJ_FPT.setKPHJSE("0.01");
//        COMMON_FPKJ_FPT.setHJBHSJE("0.00");
//        COMMON_FPKJ_XMXX[] fpkjxx_xmxxs = new COMMON_FPKJ_XMXX[3];
//        COMMON_FPKJ_XMXX mx = new COMMON_FPKJ_XMXX();
//        mx.setXMDJ("0.354700855");
//        mx.setXMSL("1.0000");
//        mx.setXMJE("0.35");
//        mx.setXMMC("鼠标");
//        mx.setSL("17%");
//        mx.setSE("0.06");
//        fpkjxx_xmxxs[0]=mx;
//        COMMON_FPKJ_XMXX mx1 = new COMMON_FPKJ_XMXX();
//        mx1.setXMDJ("0.50000000");
//        mx1.setXMSL("1.0000");
//        mx1.setXMJE("0.50");
//        mx1.setXMMC("22222");
//        mx1.setSL("17%");
//        mx1.setSE("0.09");
//        fpkjxx_xmxxs[1]=mx1;
//      COMMON_FPKJ_XMXX mx3 = new COMMON_FPKJ_XMXX();
//      mx3.setXMDJ("-0.846153846");
//      mx3.setXMSL("1.0000");
//      mx3.setXMJE("-0.85");
//      mx3.setXMMC("折扣行数2(99.000%)");
//      mx3.setSL("17%");
//      mx3.setSE("-0.14");
//      fpkjxx_xmxxs[2]=mx3;
      
//      COMMON_FPKJ_FPT COMMON_FPKJ_FPT = new COMMON_FPKJ_FPT();
//      COMMON_FPKJ_FPT.setKPHJJE("0.01");
//      COMMON_FPKJ_FPT.setKPHJSE("0.01");
//      COMMON_FPKJ_FPT.setHJBHSJE("0.00");
//      COMMON_FPKJ_XMXX[] fpkjxx_xmxxs = new COMMON_FPKJ_XMXX[2];
//      COMMON_FPKJ_XMXX mx = new COMMON_FPKJ_XMXX();
//      mx.setXMDJ("0.854700855");
//      mx.setXMSL("1.0000");
//      mx.setXMJE("0.85");
//      mx.setXMMC("鼠标");
//      mx.setSL("17%");
//      mx.setSE("0.15");
//      fpkjxx_xmxxs[0]=mx;
//    COMMON_FPKJ_XMXX mx3 = new COMMON_FPKJ_XMXX();
//    mx3.setXMDJ("-0.846153846");
//    mx3.setXMSL("1.0000");
//    mx3.setXMJE("-0.85");
//    mx3.setXMMC("折扣(99.000%)");
//    mx3.setSL("17%");
//    mx3.setSE("-0.14");
//    fpkjxx_xmxxs[1]=mx3;
        

        System.out.println("yuan发票外层的开票合计金额:"+COMMON_FPKJ_FPT.getKPHJJE());
        System.out.println("yuan发票外层的合计税额:"+COMMON_FPKJ_FPT.getKPHJSE());
        System.out.println("yuan发票外层的合计不含税金额:"+COMMON_FPKJ_FPT.getHJBHSJE());
        
      String kplx = "1";
        Map<String, String> checkResultMap = new HashMap<String, String>();
        checkResultMap.put(XmlPar.ERRORCODE, XmlPar.RESPONSEYYSSUCCESS);
        
        /**
         * 发票明细行数据校验----------------------------------------------------------------
         * 1.明细行税额校验，根据 金额 * 税率 = 税额，与 订单中  传递的税额比较，误差不能大于6分钱。
         * 2.明细行金额校验，根据 单价 * 数量 = 金额，与 订单中  传递的金额比较，误差不能大于1分钱。
         * 3.明细行累计税额与合计税额比较，误差不能大于127分钱。
         * 4.明细行第一行不能为折扣行
         * 5.不能连续两行为折扣行
         * 6.折扣行数校验，即必须有足够的被折扣行数。
         * 7.折扣率校验，折扣率不能大于100%，或者 不能小于或者等于0%
         * 8.红票折扣金额不能小于或等于零
         * 9.蓝票折扣金额不能大于或等于零
         */

        double mxse_total = 0;
        double xmje_total = 0;//项目明细金额之和170.94-17.09
        boolean upIsZkh = false; // 更新是否折扣行标志（连续折扣行标记）
        for (int i = 0; i < fpkjxx_xmxxs.length; i++) {
            String errorMsgString = "第"+(i+1)+"行:";
            COMMON_FPKJ_XMXX fpkjxx_xmxx = fpkjxx_xmxxs[i];
            
            // 商品行金额不能为零
            if (Double.parseDouble(fpkjxx_xmxx.getXMJE()) == 0) {
                System.out.println("商品行金额不能为零");
            }
            
            String xmmc = fpkjxx_xmxx.getXMMC();
            mxse_total = mxse_total + Double.parseDouble(fpkjxx_xmxx.getSE());
            xmje_total += Double.parseDouble(fpkjxx_xmxx.getXMJE());
            
            // 项目金额不能为0
            double xmje = Double.parseDouble(fpkjxx_xmxx.getXMJE());
            if(xmje == 0){
                System.out.println(errorMsgString+"项目金额有误，不能为0，请确认");
            }
            
            /**
             * 金额（不含税） * 税率 = 税额
             * 计算出的税额 与 订单传递的税额 比较，误差不能超过6分（即小于或等于6分）
             */
            String str=fpkjxx_xmxx.getSL();//把税率的％去掉，转换成小数
            if(str.contains("%")){
                str=str.replace("%", "");
                Double d=Double.valueOf(str)/100;
                str=String.valueOf(d);
            }
            double jsse = MathUtil.mul(fpkjxx_xmxx.getXMJE(), str); // 计算出的税额
            double se = Double.parseDouble(fpkjxx_xmxx.getSE()); // 订单中传递的税额
            double seCompareResult = jsse - se;
            if(Math.abs(seCompareResult) > 0.06){ // 误差大于6分钱，则税额有误
                System.out.println(errorMsgString+"税额有误，请确认");
            }
            
            /**
             * 项目数量、项目单价都不为空,并且非折扣行的情况下，要求 项目金额  与  （项目数量 * 项目单价） 之差，误差不能大于1分钱（即误差小于或等于1分钱）。
             */
            //非折扣行计算,折扣行不进行计算
            if (!ValidateUtil.checkParameterIsEmpty(fpkjxx_xmxx.getXMDJ()) && !ValidateUtil.checkParameterIsEmpty(fpkjxx_xmxx.getXMSL()) && !fpkjxx_xmxx.getXMMC().startsWith("折扣")) {
                /**
                 * 金额（不含税） = 项目单价 * 项目数量
                 * 计算出的项目金额 与 订单传递的项目金额 比较，误差不能大于1分钱（即误差小于或等于1分钱）
                 */
                double js_xmje = MathUtil.mul(fpkjxx_xmxx.getXMDJ(), fpkjxx_xmxx.getXMSL()); // 金额 = 项目单价 * 项目数量
                double yxmje = Double.parseDouble(fpkjxx_xmxx.getXMJE()); // 项目金额
                double xmjeCompareResult = yxmje - js_xmje;
                if(Math.abs(xmjeCompareResult) > 0.01){ // 误差不能大于1分钱（即误差小于或等于1分钱），否则项目金额有误
                    System.out.println(errorMsgString+"项目金额有误，请确认");
                }
            }
            
            /**
             * 根据金额来校验是否是折扣行
             */
            if("1".equals(kplx)){ // 蓝票
                if (Double.parseDouble(fpkjxx_xmxx.getXMJE()) >= 0 && checkSphIsZkh(fpkjxx_xmxx)) {
                    System.out.println(errorMsgString+"蓝票折扣金额不能大于或者等于零请确认！");
                    
                }
            }else if("2".equals(kplx)){ // 红票
                if (Double.parseDouble(fpkjxx_xmxx.getXMJE()) <= 0 && checkSphIsZkh(fpkjxx_xmxx)) {
                    System.out.println(errorMsgString+"红票折扣金额不能小于或者等于零请确认！");
                }
            }else{ // 非法开票类型
                System.out.println(errorMsgString+"不能识别的开票类型请确认！");
            }
            
            if (checkSphIsZkh(fpkjxx_xmxx)) { // 是折扣行
                
              //判断红票和蓝票的折扣行没有折扣率的,如果以折扣开头的进行校验
                /**
                 * 折扣行格式校验:
                 * 1.如果以折扣开头的项目名称中,不包含英文()和%的返回折扣行格式错误
                 * 2.括号内去掉百分后后值为空或者折扣率小于0%或者是大与100%,需抛异常
                 * 3.折扣行数没有行数或折扣行数小于等于1,需抛异常
                 * 4.单独一个折扣的折扣行,如果折扣两个字和后面的(之间有值,抛异常
                 */
                    
                if(!xmmc.contains("(")||!xmmc.contains(")")||!xmmc.contains("%")){   //如果以折扣开头的项目名称中,不包含()和%的返回折扣行格式错误
                    System.out.println(errorMsgString+"折扣行数据不合法,没有英文括号或百分号");
                }else if (StringUtils.isNullOrEmpty(xmmc.substring(xmmc.indexOf("(")+1, xmmc.indexOf(")")).replace("%", ""))||Double.parseDouble(xmmc.substring(xmmc.indexOf("(")+1, xmmc.indexOf(")")).replace("%", ""))<0 || Double.parseDouble(xmmc.substring(xmmc.indexOf("(")+1, xmmc.indexOf(")")).replace("%", ""))>100){//括号内去掉百分后后值为空或者折扣率小于0%或者是大与100%,需抛异常
                    System.out.println(errorMsgString+"折扣不能没有折扣率,并且折扣率不能小于0%大于100%");
                }else if(("折扣行数").equals(xmmc.substring(0,4))&&(StringUtils.isNullOrEmpty(xmmc.substring(xmmc.indexOf("折扣行数")+4, xmmc.indexOf("(")))||Integer.parseInt(xmmc.substring(xmmc.indexOf("折扣行数")+4, xmmc.indexOf("(")))<=1)){//折扣行数没有行数或折扣行数小于等于1,需抛异常
                    System.out.println(errorMsgString+"折扣行数据不合法,折扣行数不能小于等于1");
                }else if(("折扣").equals(xmmc.substring(0,2))&& !("行数").equals(xmmc.substring(2,4))&&!StringUtils.isNullOrEmpty(xmmc.substring(xmmc.indexOf("折扣")+2, xmmc.indexOf("(")))){//单独一个折扣的折扣行,如果折扣两个字和后面的(之间有值,抛异常
                    System.out.println(errorMsgString+"折扣行不合法,折扣后面不能有行数");
                }
                
                //折扣行不能为第一行或不能连续两个折扣行！
                if(i == 0 || upIsZkh){
                    System.out.println(errorMsgString+"折扣行不能为第一行或不能连续两个折扣行！");
                }
                
                /*
                 * ====校验“被折扣商品行金额” 乘以 “折扣率” 是否等于 “折扣额”=============
                 * 逻辑：
                 *  1、如果第i行商品行是折扣行：商品名称判断是单行折扣还是多行折扣
                 *      1.1 、单行折扣：(折扣额) /(第i-1行“商品金额”)，如果计算结果和折扣率不相等，返回错误信息。
                 *      1.2、 多行折扣：(折扣额) /(第i-n行到i-1行“商品金额”之和)，如果计算结果和折扣率不相等，返回错误信息。
                 */
                Double zkl = 0.0;           // 折扣率
                int zkNum = 0;              // 折扣行数
                double bzkje_total = 0.0;   // 被折扣行金额之和
                double bzkzje_total = 0.0;  // 被折扣行金额加税额之和
                Double zke = MathUtil.add(fpkjxx_xmxx.getXMJE(), fpkjxx_xmxx.getSE());
                DecimalFormat df = new DecimalFormat("######0.00000");
                System.out.println("yuan折扣行的开票合计金额:"+fpkjxx_xmxx.getXMJE());
                System.out.println("yuan折扣行的开票合计税额:"+fpkjxx_xmxx.getSE());
                if (xmmc.length() > 3 && (xmmc.substring(0, 3)).equals("折扣(")) {// 单行折扣的类型
                    
                  //单行折扣 判断第一行是否为折扣行,并且是否相邻两行是折扣行
                    if(i-1<0){ // 第一行
                        System.out.println(errorMsgString+"第一行不能为折扣行!");
                    }else{ // 非第一行
                        COMMON_FPKJ_XMXX sph = fpkjxx_xmxxs[i-1];
                        if(checkSphIsZkh(sph)){ // 判断是否是折扣行
                            System.out.println(errorMsgString+"相邻两行不能是折扣行");
                        }
                    }
                    
                    //单行折扣  校验“折扣额” 除以 “被折扣商品行金额” 是否等于 “折扣率”
                    int beginIndex = xmmc.indexOf("折扣(");
                    beginIndex = beginIndex + 3;
                    int endIndex = xmmc.indexOf("%)");
                    //获取折扣率并且格式化
                    zkl = Double.parseDouble(df.format(Double.parseDouble(xmmc.substring(beginIndex,endIndex)) / 100.0));// 提取出折扣率
                    //获取被折扣行的不含税金额
                    bzkje_total = Double.parseDouble(ValidateUtil.decimalFormat(fpkjxx_xmxxs[i - 1].getXMJE(),2));//获取被折扣行金额
                    //获取被折扣行的不含税金额加上税额,即反推含税金额
                    bzkzje_total = MathUtil.add(fpkjxx_xmxxs[i - 1].getXMJE().trim(),fpkjxx_xmxxs[i - 1].getSE().trim());//获取被折扣行金额
                    //判断 折扣额/被折扣商品行金额的值是否与传递的折扣率相同
                    if (Double.parseDouble(df.format(Math.abs(zke/bzkzje_total))) != zkl) {
                        System.out.println(errorMsgString+"折扣额/被折扣金额之和   != 折扣率");
                    }
                    
                    /**
                     * 单行折扣,校验 税率是否相等
                     */
                    
                    double bzkhslDouble = Double.parseDouble(ValidateUtil.decimalFormat(fpkjxx_xmxxs[i - 1].getSL().trim().replace("%", ""),0));   //被折扣行税率
                    double zkhslDouble = Double.parseDouble(ValidateUtil.decimalFormat(fpkjxx_xmxx.getSL().trim().replace("%", ""),0));    //折扣行税率
                    //判断折扣行税率是否等于被折扣行税率
                    if(zkhslDouble != bzkhslDouble){
                        System.out.println(errorMsgString+"折扣行和被折扣行税率不相等!");
                    }
                    
//                    if(Double.parseDouble(ValidateUtil.decimalFormat(Math.abs(bzkje_total),2)) == Double.parseDouble(ValidateUtil.decimalFormat(Math.abs(Double.valueOf(fpkjxx_xmxx.getXMJE())),2)) && Double.parseDouble(ValidateUtil.decimalFormat(Math.abs(Double.valueOf(fpkjxx_xmxx.getXMJE())),2)) >=0.01 && Double.parseDouble(ValidateUtil.decimalFormat(Math.abs(Double.valueOf(fpkjxx_xmxx.getSE())),2)) >=0.01 ){
//                        
//                        if("1".equals(kplx)){  //如果是蓝票,明细折扣行金额加上一分钱,税额减去一分钱,重新计算单价 合计不含税金额加上一分钱,合计税额减去一分钱
//                            fpkjxx_xmxx.setXMJE(ValidateUtil.decimalFormat(Double.parseDouble(fpkjxx_xmxx.getXMJE())+0.01,2));
//                            fpkjxx_xmxx.setXMDJ(ValidateUtil.decimalFormat((Double.parseDouble(fpkjxx_xmxx.getXMJE())+0.01)/Double.parseDouble(fpkjxx_xmxx.getXMSL()),8));
//                            fpkjxx_xmxx.setSE(ValidateUtil.decimalFormat(Double.parseDouble(fpkjxx_xmxx.getSE())-0.01,2));
//                            //发票头信息中的合计不含税金额和合计税额一分钱调整
//                            COMMON_FPKJ_FPT.setHJBHSJE(ValidateUtil.decimalFormat(Double.parseDouble(COMMON_FPKJ_FPT.getHJBHSJE())+0.01,2));
//                            COMMON_FPKJ_FPT.setKPHJSE(ValidateUtil.decimalFormat(Double.parseDouble(COMMON_FPKJ_FPT.getKPHJSE())-0.01,2));
//                        }else if("2".equals(kplx)){//如果是红票,明细折扣行金额减去一分钱,税额加上一分钱,重新计算单价 合计不含税金额加上一分钱,合计税额减去一分钱
//                            
//                            fpkjxx_xmxx.setXMJE(ValidateUtil.decimalFormat(Double.parseDouble(fpkjxx_xmxx.getXMJE())-0.01,2));
//                            fpkjxx_xmxx.setXMDJ(ValidateUtil.decimalFormat((Double.parseDouble(fpkjxx_xmxx.getXMJE())-0.01)/Double.valueOf(fpkjxx_xmxx.getXMSL()),8));
//                            fpkjxx_xmxx.setSE(ValidateUtil.decimalFormat(Double.parseDouble(fpkjxx_xmxx.getSE())+0.01,2));
//                            //发票头信息中的合计不含税金额和合计税额一分钱调整
//                            COMMON_FPKJ_FPT.setHJBHSJE(ValidateUtil.decimalFormat(Double.parseDouble(COMMON_FPKJ_FPT.getHJBHSJE())-0.01,2));
//                            COMMON_FPKJ_FPT.setKPHJSE(ValidateUtil.decimalFormat(Double.parseDouble(COMMON_FPKJ_FPT.getKPHJSE())+0.01,2));
//                        }
//                    }
                    
                }else if (xmmc.length() > 4 && (xmmc.substring(0, 4)).equals("折扣行数")) {// 多行折扣的类型
                    
                    String zkhsStr = xmmc.substring(4, xmmc.indexOf("(")); // 取折扣行数
                    int zkhs = Integer.parseInt(zkhsStr);
                    //多行折扣 判断第一行是否为折扣行,并且是否相邻两行是折扣行
                    if((i-zkhs)<0){
                        System.out.println(errorMsgString+"多行折扣,被折扣行不足!");
                    }else{ // 从行数的角度上出发，有足够的行数来被折扣，但是不确定是否都为商品行，所以需要进一步进行逻辑判断
                        for(int k=i-1;k > -1;k--){
                            COMMON_FPKJ_XMXX sph = fpkjxx_xmxxs[k];
                            if(zkhs == 0){ // 折扣行数等于0，说明有足够的商品行被折扣，并验证通过。
                                break;
                            }else{
                                if(!checkSphIsZkh(sph)){ // 非折扣行，即商品行
                                    zkhs = zkhs -1; // 从此折扣行往前循环，如果循环到的行为商品行则折扣数减去1
                                }else{
                                    System.out.println(errorMsgString+"多行折扣,被折扣行中存在折扣行!");
                                }
                            }
                        }
                    }
                    //多行折扣  校验“折扣额” 除以 “被折扣商品行金额” 是否等于 “折扣率”
                    int beginIndex = xmmc.indexOf("(") + 1;
                    int endIndex = xmmc.indexOf("%)");
                    zkl = Double.parseDouble(df.format(Double.parseDouble(xmmc.substring(beginIndex,endIndex)) / 100));
                    zkNum = Integer.parseInt(zkhsStr);  //折扣行数
                    for (int j = 0; j < zkNum; j++) {
                        
                        /**
                         * 多行折扣,校验被折扣行税率是否和折扣行税率相等
                         */
                        double bzkhslDouble = Double.parseDouble(ValidateUtil.decimalFormat(fpkjxx_xmxxs[i- zkNum + j].getSL().trim().replace("%", ""),0));   //被折扣行税率
                        double zkhslDouble = Double.parseDouble(ValidateUtil.decimalFormat(fpkjxx_xmxx.getSL().trim().replace("%", ""),0));    //折扣行税率
                        //判断折扣行税率是否等于被折扣行税率
                        if(zkhslDouble != bzkhslDouble){
                            System.out.println(errorMsgString+"折扣行和被折扣行税率不相等!");
                        }
                        
                        //获取被折扣行的累计金额
                        bzkje_total = MathUtil.add(String.valueOf(bzkje_total),String.valueOf(fpkjxx_xmxxs[i- zkNum + j].getXMJE().trim()));
                      //获取被折扣行的累计金额和税额之和
                        bzkzje_total = MathUtil.add(String.valueOf(bzkzje_total),String.valueOf(MathUtil.add(fpkjxx_xmxxs[i- zkNum + j].getXMJE().trim(),fpkjxx_xmxxs[i- zkNum + j].getSE().trim())));
                        

                    }
                    if (Double.parseDouble(df.format(Math.abs(zke/bzkzje_total))) != zkl) {
                        System.out.println(errorMsgString+"折扣额/被折扣金额之和   != 折扣率");
                    }
                    
                }
                /**
                 * 多行折扣, 校验:折扣行金额和被折扣行金额相等,折扣行做一分钱金额调整
                 */
                //如果被折扣行累计金额 与 折扣行金额去绝对之后相等并且折扣行的金额和税额大于等于0.01,多折扣行做一分钱调整
                if(Double.parseDouble(ValidateUtil.decimalFormat(Math.abs(bzkje_total),2)) == Double.parseDouble(ValidateUtil.decimalFormat(Math.abs(Double.valueOf(fpkjxx_xmxx.getXMJE())),2)) && Double.parseDouble(ValidateUtil.decimalFormat(Math.abs(Double.valueOf(fpkjxx_xmxx.getXMJE())),2)) >=0.01 && Double.parseDouble(ValidateUtil.decimalFormat(Math.abs(Double.valueOf(fpkjxx_xmxx.getSE())),2)) >=0.01 ){
                    
                    if("1".equals(kplx)){  //如果是蓝票,明细折扣行金额加上一分钱,税额减去一分钱,重新计算单价 合计不含税金额加上一分钱,合计税额减去一分钱
                        fpkjxx_xmxx.setXMJE(ValidateUtil.decimalFormat(Double.parseDouble(fpkjxx_xmxx.getXMJE())+0.01,2));
                        fpkjxx_xmxx.setXMDJ(ValidateUtil.decimalFormat((Double.parseDouble(fpkjxx_xmxx.getXMJE())+0.01)/Double.parseDouble(fpkjxx_xmxx.getXMSL()),8));
                        fpkjxx_xmxx.setSE(ValidateUtil.decimalFormat(Double.parseDouble(fpkjxx_xmxx.getSE())-0.01,2));
                        //发票头信息中的合计不含税金额和合计税额一分钱调整
                        COMMON_FPKJ_FPT.setHJBHSJE(ValidateUtil.decimalFormat(Double.parseDouble(COMMON_FPKJ_FPT.getHJBHSJE())+0.01,2));
                        COMMON_FPKJ_FPT.setKPHJSE(ValidateUtil.decimalFormat(Double.parseDouble(COMMON_FPKJ_FPT.getKPHJSE())-0.01,2));
                    }else if("2".equals(kplx)){//如果是红票,明细折扣行金额减去一分钱,税额加上一分钱,重新计算单价 合计不含税金额加上一分钱,合计税额减去一分钱
                        
                        fpkjxx_xmxx.setXMJE(ValidateUtil.decimalFormat(Double.parseDouble(fpkjxx_xmxx.getXMJE())-0.01,2));
                        fpkjxx_xmxx.setXMDJ(ValidateUtil.decimalFormat((Double.parseDouble(fpkjxx_xmxx.getXMJE())-0.01)/Double.valueOf(fpkjxx_xmxx.getXMSL()),8));
                        fpkjxx_xmxx.setSE(ValidateUtil.decimalFormat(Double.parseDouble(fpkjxx_xmxx.getSE())+0.01,2));
                        //发票头信息中的合计不含税金额和合计税额一分钱调整
                        COMMON_FPKJ_FPT.setHJBHSJE(ValidateUtil.decimalFormat(Double.parseDouble(COMMON_FPKJ_FPT.getHJBHSJE())-0.01,2));
                        COMMON_FPKJ_FPT.setKPHJSE(ValidateUtil.decimalFormat(Double.parseDouble(COMMON_FPKJ_FPT.getKPHJSE())+0.01,2));
                    }
                }
                System.out.println("发票外层的开票合计金额:"+COMMON_FPKJ_FPT.getKPHJJE());
                System.out.println("发票外层的合计税额:"+COMMON_FPKJ_FPT.getKPHJSE());
                System.out.println("发票外层的合计不含税金额:"+COMMON_FPKJ_FPT.getHJBHSJE());
                System.out.println("折扣行的开票合计金额:"+fpkjxx_xmxx.getXMJE());
                System.out.println("折扣行的开票合计税额:"+fpkjxx_xmxx.getSE());
                
                upIsZkh = true;
            }else{ // 非折扣行
                upIsZkh = false;
                /**
                 * 根据金额来校验是否是折扣行
                 */
                if("1".equals(kplx)){ // 非折扣行蓝票处理,明细行的数量,单价,金额都不能小于等于0
                    if (Double.parseDouble(fpkjxx_xmxx.getXMJE()) <= 0) {
                        System.out.println(errorMsgString+"蓝票明细行金额不能小于或者等于零请确认！");
                    }
                    if (Double.parseDouble(fpkjxx_xmxx.getXMDJ()) <= 0) {
                        System.out.println(errorMsgString+"蓝票明细行单价不能小于或者等于零请确认！");
                    }
                    if (Double.parseDouble(fpkjxx_xmxx.getXMSL()) <= 0) {
                        System.out.println(errorMsgString+"蓝票明细行数量不能小于或者等于零请确认！");
                    }
                }else if("2".equals(kplx)){ // 非折扣行红票处理,明细行的金额不能大于等于0
                    if (Double.parseDouble(fpkjxx_xmxx.getXMJE()) >= 0 ) {
                        System.out.println(errorMsgString+"蓝票明细行金额不能大于或者等于零请确认！");
                    }
                }else{ // 非法开票类型
                    System.out.println(errorMsgString+"不能识别的开票类型请确认！");
                }
            }
        }
    
        
    }
    private static boolean checkSphIsZkh(String  st){
        String xmmc = st;
        if((xmmc.length() > 3 && (xmmc.substring(0, 3)).equals("折扣(")) || (xmmc.length() > 4 && (xmmc.substring(0, 4)).equals("折扣行数"))){
            return true;
        }
        return false;
    }
    private static boolean checkSphIsZkh(COMMON_FPKJ_XMXX fpkjxx_xmxx){
        String xmmc = fpkjxx_xmxx.getXMMC();
        if((xmmc.length() > 3 && (xmmc.substring(0, 3)).equals("折扣(")) || (xmmc.length() > 4 && (xmmc.substring(0, 4)).equals("折扣行数"))){
            return true;
        }
        return false;
    }
}
