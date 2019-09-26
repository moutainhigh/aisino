package com.aisino.test;

import java.io.UnsupportedEncodingException;

import org.apache.commons.lang.StringUtils;

import com.aisino.common.util.ValidateUtil;
import com.aisino.common.util.XmlPar;

public class TestcutString {

    /**
     * 
     * @param text 目标字符串
     * @param length 截取长度
     * @param encode 采用的编码方式
     * @return
     * @throws UnsupportedEncodingException
     */
    public String substring(String text, int length, String encode)
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

    public void test() throws Exception {
     String text = "尼康（NIKON）Coolpix S3700 便携数码相机 银色（2005万像素 2.7英寸屏 8倍光学变焦 内置Wi-Fi®112312）";
     int length1 = 3;
     int length2 = 6;
     String[] encodes = new String[] { "GB2312", "GBK", "GB18030", "CP936",
       "CNS11643" };
     for (String encode : encodes) {
      System.out.println(new StringBuilder().append("用").append(encode)
        .append("编码截取字符串 -- 【").append(text).append("】").append(
          length1).append("个字节的结果是【").append(
          substring(text, length1, encode)).append("】")
        .toString());
      System.out.println(new StringBuilder().append("用").append(encode)
        .append("编码截取字符串 -- 【").append(text).append("】").append(
          length2).append("个字节的结果是【").append(
          substring(text, length2, encode)).append("】")
        .toString());

     }
    }

    public static void main(String[] args) throws Exception {
//        String c = "尼康（NIKON）Coolpix S3700 便携数码相机 银色（2005万像素 2.7英寸屏 8倍光学变焦 内置Wi-Fi®112312）";
        String c = "护舒宝（Whisper） 超净棉丝薄 日夜组合装28片卫生巾（日用240mm20片+夜用284mm8片 棉柔 瞬吸 超级12312312";
        
        
//        String e = new String(c.getBytes("UTF-8"));
////        String b = new String("我ABC汉DEF","UTF-8");
//     String a =new TestcutString().substring(e, 6, "GBK");
//     System.out.println(a);
     
//        String remark = " ,原发票代码";
//        if(!StringUtils.isBlank(remark)){
//            int endIndex = 0;
//            if(remark.contains(",原发票代码")){
//                endIndex = remark.indexOf(",原发票代码");
//                remark = remark.substring(0, endIndex);
//            }
//            
        String remark = "退货折让,原发票代码:1100111650,原发票号码:00534942";
        if(!StringUtils.isBlank(remark)){
            int endIndex = 0;
            if(remark.contains(",原发票代码")){
                endIndex = remark.indexOf(",原发票代码");
                remark = remark.substring(0, endIndex);
            }
            
            // 开具红票时，处理下备注信息
            System.out.println(remark);
        }else {
            System.out.println("11111111");
        }
        String zk = "折扣（";
        String zk1 = "折扣(";
        if(String.valueOf(zk).startsWith("折扣（")||String.valueOf(zk).startsWith("折扣(")) {
            System.out.println(zk);
        }else{
            
        }
     String xmmc = new TestcutString().substring(c, 90, "GBK");
     String bString = new String(xmmc.getBytes("UTF-8"),"UTF-8");
     System.out.println(bString);
     System.out.println(xmmc.getBytes("GBK").length);
    }
}
