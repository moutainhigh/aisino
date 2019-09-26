package com.aisino.test;

import java.math.RoundingMode;
import java.text.DecimalFormat;

import org.joda.time.DateTime;
import org.joda.time.Duration;

import com.aisino.common.util.XmlPar;
import com.alibaba.druid.sql.visitor.functions.Now;
import com.mysql.jdbc.StringUtils;

public class TestDouble {
    
    public static void main(String[] args) {
        //测试double类型
//        String a = "0.11";
//        DecimalFormat df = new DecimalFormat();
//        
//        df.setRoundingMode(RoundingMode.HALF_UP);
//        double b = Double.parseDouble(a);
//        Double c =null;
//        if(c!=null){
//            System.out.println("11");
//        }
        
        //-----------------------------------------
        
//        //测试  发票开具:合计税额+合计不含税金额不等于开票合计金额！
//        String hjbhsjeString = "0.02";
//        String kphjseString = "-0.00";
//        String kphjjeString = "0.01";
//        
//        if(!StringUtils.isNullOrEmpty(kphjjeString) && !StringUtils.isNullOrEmpty(kphjseString) && !StringUtils.isNullOrEmpty(hjbhsjeString)){
//            if(Double.parseDouble(hjbhsjeString)+Double.parseDouble(kphjseString) != Double.parseDouble(kphjjeString)){
//                System.out.println("发票开具:合计税额+合计不含税金额不等于开票合计金额！！");
//            }else{
//                System.out.println("金额相等!");
//            }
//        }else{
//            System.out.println("合计金额或者是合计税额为空!");
//        }
        
        //=========================================
        
        
//            float hjse = 0;
//            DecimalFormat df = new DecimalFormat("######0.00"); 
//            hjse += Double.parseDouble("2.12");
//            hjse += Double.parseDouble("4.05");
//            hjse += Double.parseDouble("1.44");
//            hjse += Double.parseDouble("2.6");
//            hjse += Double.parseDouble("-10.21");
//            hjse += Double.parseDouble("0.0");
////          double round = Math.round(hjse*100);
////          hjse = round/100;
//            System.out.println(hjse);  //-1.7763568394002505E-15
//            System.out.println(df.format(hjse)); //-0.00
//            
//            System.out.println(Double.parseDouble("-0.0") == Double.parseDouble("0.0"));
            
//        String zkhMc="折扣(100.000%)";
//          String zklString = "";
//          if ((zkhMc.indexOf("(") != -1) && (zkhMc.indexOf(")") != -1)) {
//            zklString = zkhMc.substring(zkhMc.indexOf("(") + 1, zkhMc.indexOf(")")).replaceAll("%", "");
//          }
//          if ((zkhMc.indexOf("(") == -1) && (zkhMc.indexOf(")") == -1)) {
//            System.out.println("不接受中文括号，请确认");
//          }
//          if ((zklString.equals("")) || (Double.parseDouble(zklString) > 100.0D) || (Double.parseDouble(zklString) <= 0.0D)) {
//              System.out.println("折扣率不合法，请确认");
//          }
//          String xmmc = zkhMc;
//          if((xmmc.length() > 3 && (xmmc.substring(0, 3)).equals("折扣（")) || (xmmc.length() > 4 && (xmmc.substring(0, 4)).equals("折扣行数"))){
//              System.out.println("折扣率不合法1111111，请确认");
//          }
        
        
        String a = "折扣行数2(12.234%)";
        DateTime date1 = new DateTime().now();
//        double firstzk = Double.parseDouble(String.valueOf(a).replace("(", " ").replace(")", " ").split(" ")[1].replace("%", "")) / 100;
        int zkhscount = Integer.parseInt(String.valueOf(a.replace("(", " ").replace(")", " ").split(" ")[0].substring(4)));
        Long dateLong = new Duration(date1, new DateTime().now()).getMillis();
        System.out.println("第一种耗时:"+dateLong);
        
        DateTime date2 = new DateTime().now();
//        double firstzk1 = Double.parseDouble(String.valueOf(a.substring(a.indexOf("(")+1, a.indexOf("%)")))) / 100;
        int zkhscount1 = Integer.parseInt(String.valueOf(a.substring(4, a.indexOf("("))));
        Long dateLong2 = new Duration(date2, new DateTime().now()).getMillis();
        System.out.println("第一种耗时:"+dateLong2);
        
        
        
        
    }
}
