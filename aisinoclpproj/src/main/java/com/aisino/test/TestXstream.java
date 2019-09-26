package com.aisino.test;

import java.io.ByteArrayOutputStream;

import com.aisino.common.util.Data;
import com.aisino.common.util.EIProtocolFactory;
import com.aisino.common.util.XMLShellFactory;
import com.aisino.protocol.bean.COMMON_FPKJ_FPT;
import com.aisino.protocol.bean.COMMON_FPKJ_XMXX;
import com.aisino.protocol.bean.REQUEST_COMMON_FPKJ;
import com.aisino.protocol.bean.RESPONSE_COMMON_FPKJ;
import com.aisino.protocol.bean.business;
import com.thoughtworks.xstream.XStream;

public class TestXstream {
    public static void main(String[] args) {
//        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        business bus = new business();
//        REQUEST_COMMON_FPKJ refpkj =new REQUEST_COMMON_FPKJ();
//        COMMON_FPKJ_FPT fpt = new COMMON_FPKJ_FPT();
//        COMMON_FPKJ_XMXX xmmx = new COMMON_FPKJ_XMXX();
//        COMMON_FPKJ_XMXX[] xmmxs = new COMMON_FPKJ_XMXX[2];
//        fpt.setBZ("bz");
//        xmmx.setDW("dw1");
//        xmmxs[0]=xmmx;
//        COMMON_FPKJ_XMXX xmmx1 = new COMMON_FPKJ_XMXX();
//        xmmx1.setDW("dw2");
//        xmmxs[1] = xmmx1;
//        refpkj.setCOMMON_FPKJ_FPT(fpt);
//        refpkj.setCOMMON_FPKJ_XMXXS(xmmxs);
//        bus.setREQUEST_COMMON_FPKJ(refpkj);
//        
//        XMLShellFactory.newInstance().saveXml(outputStream,
//                refpkj);
//        // 把content放入整个报文中
//        final Data data = EIProtocolFactory.getData(outputStream);
//        String d = data.getContent();
//        System.out.println(d);
//        XStream xstream = new XStream();
////        System.out.println("未转换前:"+xstream.toXML(bus));
//        
//        xstream.alias("business", business.class);
//        xstream.alias("REQUEST_COMMON_FPKJ", REQUEST_COMMON_FPKJ.class);
//        xstream.alias("COMMON_FPKJ_FPT", COMMON_FPKJ_FPT.class);
//        xstream.alias("COMMON_FPKJ_XMXX", COMMON_FPKJ_XMXX.class);
//        xstream.useAttributeFor(business.class, "id");
//        xstream.aliasField("id", business.class, "id");
//        
//        System.out.println("转换后"+xstream.toXML(bus).replace("__", "_"));
        
       
        String ybz = "退货折让红票,原发票代码:1100152650,原发票号码:04734283;";
        if(ybz.contains("原发票代码")){
            ybz = ybz.substring(0,ybz.indexOf("原发票代码")-1);
//            System.out.println(ybz.indexOf("原发票代码"));
            System.out.println(ybz);
            System.out.println("1");
        }else{
            
            System.out.println("2");
        }
        
    }
}
