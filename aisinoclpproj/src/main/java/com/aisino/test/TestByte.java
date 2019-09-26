package com.aisino.test;

import static com.google.common.base.Strings.isNullOrEmpty;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.aspectj.apache.bcel.classfile.Field;

import com.aisino.common.util.StaticConstant;
import com.aisino.common.util.XMLShellFactory;
import com.aisino.protocol.bean.COMMON_FPKJ_FPT;
import com.aisino.protocol.bean.COMMON_FPKJ_XMXX;
import com.aisino.protocol.bean.REQUEST_COMMON_FPKJ;
import com.mysql.jdbc.StringUtils;


public class TestByte {
    
//    public static void main(String[] args) throws UnsupportedEncodingException {
//        String a = new String("m".getBytes(),"UTF-8");
//        String c = new String(" ".getBytes(),"GBK");
//        String bString = "我的"+a+"你的";
//        String dString = "我的"+c+"你的";
//        
//        int   size   =   10;   
//        byte[]   byt   =   new   byte[size];   
//        for   (int   i   =   0;   i   <   size;   i++){   
//            byt[i]   =   120;   
//        }  
//        byt[6]=(byte)0xC2;
//        byt[7]=(byte)0xA0;
//        
//        System.out.println(byt.length);
//        System.out.println(new String(byt));
//        System.out.println(a.getBytes());
//        System.out.println(dString.getBytes().length);
//        
//    }
    
    public static void main(String[] args) {
        String bz ="对应正数发票代码";
        if(!StringUtils.isNullOrEmpty(bz) && bz.startsWith("对应正数发票代码")){
            System.out.println("1231231231232");
        }else{
           System.out.println("sadfasdf");
        }
    }
//        int   size   =   10;   
//      byte[]   byt   =   new   byte[size];   
//      for   (int   i   =   0;   i   <   size;   i++){   
//          byt[i]   =   120;   
//      }  
//      byt[6]=(byte)0xC2;
//      byt[7]=(byte)0xA0;
//        
//        String xmlhead="<?xml version=\"1.0\" encoding=\"gbk\"?><business id=\"FPKJ\" comment=\"发票开具\">";
//        
//        String xmlfoot="</business>";
//
//        final REQUEST_COMMON_FPKJ request_fpkj =new REQUEST_COMMON_FPKJ();
//        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        
//        COMMON_FPKJ_FPT cOMMON_FPKJ_FPT = new COMMON_FPKJ_FPT();
//        cOMMON_FPKJ_FPT.setFPQQLSH("000000001");
//        cOMMON_FPKJ_FPT.setKPLX("1");
//        cOMMON_FPKJ_FPT.setXSF_NSRSBH("销货方识别号");
//        cOMMON_FPKJ_FPT.setXSF_MC("销货方名称");
//        cOMMON_FPKJ_FPT.setXSF_DZDH("销售方地址电话");
//        cOMMON_FPKJ_FPT.setXSF_YHZH("销售方银行帐号");
//        cOMMON_FPKJ_FPT.setGMF_NSRSBH("123123143");
//        cOMMON_FPKJ_FPT.setGMF_MC("购买方名称");
//        cOMMON_FPKJ_FPT.setGMF_DZDH("");
//        cOMMON_FPKJ_FPT.setGMF_YHZH("");
//        cOMMON_FPKJ_FPT.setKPR("");
//        cOMMON_FPKJ_FPT.setSKR("");
//        cOMMON_FPKJ_FPT.setFHR("");
//        cOMMON_FPKJ_FPT.setYFP_DM("");
//        cOMMON_FPKJ_FPT.setYFP_HM("");
//        cOMMON_FPKJ_FPT.setJSHJ("150.00");
//        cOMMON_FPKJ_FPT.setHJJE("128.21");
//        cOMMON_FPKJ_FPT.setHJSE("21.79");
//        cOMMON_FPKJ_FPT.setBZ("");
//        
//        
//        request_fpkj.setCOMMON_FPKJ_FPT(cOMMON_FPKJ_FPT);
//        
//        COMMON_FPKJ_XMXX[] conXmxxs = new COMMON_FPKJ_XMXX[1];
//        for (int i = 0; i < conXmxxs.length; i++) {
//            COMMON_FPKJ_XMXX conXmxx = new COMMON_FPKJ_XMXX();
//            conXmxx.setFPHXZ("");
//            conXmxx.setXMMC(new String(byt));
//            conXmxx.setGGXH("");
//            conXmxx.setDW("");
//            conXmxx.setXMSL("10.0000");
//            conXmxx.setXMDJ("12.82051282");
//            conXmxx.setXMJE("128.21");
//            conXmxx.setSL("0.17");
//            conXmxx.setSE("21.79");
//            
//            conXmxxs[i] = conXmxx;
//        }
//        request_fpkj.setCOMMON_FPKJ_XMXXS(conXmxxs);
//        
//        
//        
//        XMLShellFactory.newInstance().saveXml(outputStream,
//                request_fpkj);
//        String reqcon = new String(outputStream.toByteArray(), StaticConstant.CHARSET);
//        if (!isNullOrEmpty(reqcon) && !reqcon.equals("null")) {
//            reqcon = reqcon.substring(reqcon.indexOf("<ROOT>") + 6, reqcon.lastIndexOf("</ROOT>"));
//        } else {
//            reqcon = "";
//        }
//        String reqXml = xmlhead + reqcon + xmlfoot;
//        
//        File f = new File("c:/123.txt");
//        try {
//            FileOutputStream out = new FileOutputStream(f);
//            out.write(reqXml.getBytes());
//            out.flush();
//            out.close();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        
//        System.out.println(reqXml);
//    }
    
}
