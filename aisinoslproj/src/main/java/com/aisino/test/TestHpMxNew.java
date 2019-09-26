package com.aisino.test;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.aisino.common.util.ValidateUtil;
import com.aisino.domain.einvoice.entity.InvoiceDetailEntity;
import com.aisino.protocol.bean.COMMON_FPKJ_XMXX;

public class TestHpMxNew {
    
    public static void main(String[] args) {
        COMMON_FPKJ_XMXX[] protocolProjectBean=null;
        //这个是通过金额小数点后两位进行计算的
//      protocolProjectBean = LpMx();
//        protocolProjectBean = LpMxList();
//      protocolProjectBean = HpMx();
//      protocolProjectBean = HpMxList();
      String kplx = "12";
//      processInsertRedInvoiceDetail(protocolProjectBean);
      if(kplx.equals("1")){
          protocolProjectBean = LpMx();
          processInsertInvoiceDetail(protocolProjectBean);
          
      }else if(kplx.equals("2")){
          protocolProjectBean = HpMxzk100();
          processInsertInvoiceDetail(processInsertRedInvoiceDetail(protocolProjectBean));
      }else if(kplx.equals("3")){
          protocolProjectBean = LpMxList();
          processInsertInvoiceDetail(protocolProjectBean);
      }else if(kplx.equals("4")){
          protocolProjectBean = HpMxList();
          processInsertInvoiceDetail(processInsertRedInvoiceDetail(protocolProjectBean));
      }else if(kplx.equals("5")){
          protocolProjectBean = LpMxZk();
          processInsertInvoiceDetail(protocolProjectBean);
      }else if(kplx.equals("6")){
          protocolProjectBean = HpMxZk();
          processInsertInvoiceDetail(processInsertRedInvoiceDetail(protocolProjectBean));
      }else if(kplx.equals("7")){
          protocolProjectBean = HpMxListzk100();
          processInsertInvoiceDetail(processInsertRedInvoiceDetail(protocolProjectBean));
      }else if(kplx.equals("8")){//红票折扣,折扣率为0的  2明细
          protocolProjectBean = HpMxzkl0();
          processInsertInvoiceDetail(processInsertRedInvoiceDetail(protocolProjectBean));
      }else if(kplx.equals("9")){//红票折扣行数2,折扣率为0的  3明细
          protocolProjectBean = HpMxdhzkl0();
          processInsertInvoiceDetail(processInsertRedInvoiceDetail(protocolProjectBean));
      }else if(kplx.equals("10")){//红票折扣行数3,折扣率为0的  4明细
          protocolProjectBean = HpMxdhzkl01();
          processInsertInvoiceDetail(processInsertRedInvoiceDetail(protocolProjectBean));
      }else if(kplx.equals("11")){//红票折扣行数3,加上折扣,折扣率为0的  4明细
          protocolProjectBean = HpMxdhzkl02();
          processInsertInvoiceDetail(processInsertRedInvoiceDetail(protocolProjectBean));
      }else if(kplx.equals("12")){//红票折扣212221 正常折扣率, 6明细
          protocolProjectBean = HpMxList2();
          processInsertInvoiceDetail(processInsertRedInvoiceDetail(protocolProjectBean));
      }else if(kplx.equals("13")){//红票折扣 2221212221,加上折扣,折扣率为0的  10明细
          protocolProjectBean = HpMxdhzkl03();
          processInsertInvoiceDetail(processInsertRedInvoiceDetail(protocolProjectBean));
      }
      
    }
    private static void processInsertInvoiceDetail(final COMMON_FPKJ_XMXX[] protocolProjectBean) {

        if (protocolProjectBean != null && protocolProjectBean.length > 0) {
            int zkhs= 0;    //折扣行数数量
            for (int i = protocolProjectBean.length-1; i >= 0; i--) {   //反向循环,计算他们的发票行性质
                final COMMON_FPKJ_XMXX eachBean = protocolProjectBean[i];
               if (String.valueOf(eachBean.getXMMC()).startsWith("折扣行数")) { //如果为折扣行数开头的,就获取他的数量,并且给fphxz赋值
                  zkhs = Integer.parseInt(String.valueOf(eachBean.getXMMC().replace("(", " ").replace(")", " ").split(" ")[0].substring(4)));
                  protocolProjectBean[i].setFPHXZ("1");
              }else if (String.valueOf(eachBean.getXMMC()).startsWith("折扣(")) {     //如果为折扣行数开头的,折扣行数赋值为1,并且给fphxz赋值
                  zkhs = 1;
                  protocolProjectBean[i].setFPHXZ("1");
              } else {  //没有以折扣行数或者是折扣开头的,要么是正常商品行,要么就是被折扣行,下面根据折扣行数是否大于0,来判断他是否是被折扣行
                  if (zkhs>0) {     //如果折扣行数大于0,那么就是被折扣行,fphxz赋值为2,并且行数自减1
                      protocolProjectBean[i].setFPHXZ("2");
                      zkhs-=1;
                  } else {      //如果折扣行数为0,则是正常商品行
                      protocolProjectBean[i].setFPHXZ("0");
                  }

              }
            }
            for (int i = 0; i < protocolProjectBean.length; i++) {
                final COMMON_FPKJ_XMXX eachBean = protocolProjectBean[i];
                System.out.println("发票行性质："+eachBean.getFPHXZ()+ eachBean.getXMMC());
            }
        }

    }
    private static COMMON_FPKJ_XMXX[] processInsertRedInvoiceDetail(final COMMON_FPKJ_XMXX[] protocolProjectBean) {
        COMMON_FPKJ_XMXX[] protocolProjectBeannew = null;
        if (protocolProjectBean != null && protocolProjectBean.length > 0) {
            List<COMMON_FPKJ_XMXX> mxlist = new ArrayList<COMMON_FPKJ_XMXX>();
            double xmzje = 0.00; // 项目总金额
            double xmzse = 0.00; // 项目总税额
            double xmljzkje = 0.00; // 项目累计折扣金额
            double xmljzkse = 0.00; // 项目累计折扣税额
            double firstzk = 0.17D; // 第一个折扣
            int maxxmjeszh = 0; // 最大项目金额所在行

            for (int i = 0; i < protocolProjectBean.length; i++) {

                //循环获取到的list数据,并判断是否是折扣行,如果是折扣行,就给zklvbl赋值为true,下次循环的时候执行计算不含税价的折扣率并跳出该次循环
                final COMMON_FPKJ_XMXX eachBean = protocolProjectBean[i];
                if (String.valueOf(eachBean.getXMMC()).startsWith("折扣行数") || String.valueOf(eachBean.getXMMC()).startsWith("折扣(")) {
                    continue;
                }

                /*
                 * 2015年6月10日 11:45:11 如果红票的项目数量不为空,并且没有"-"开头,就给他添加一个"-"
                 */
                if (String.valueOf(eachBean.getXMSL()) != null && !eachBean.getXMSL().startsWith("-")) {
                    eachBean.setXMSL("-" + eachBean.getXMSL());
                } else {
                    eachBean.setXMSL(eachBean.getXMSL());
                }

                int zkhs = 1000; // 折扣行数
                int zkhscount = 500; // 折扣行数后面的count值
                Boolean zk = false; //如果有折扣行数这样的字样就为true
                Boolean zks = false;    //如果有折扣这样的字样就为true
                //for循环获取折扣行的折扣率,和折扣行数的行数值,并获取折扣行的金额和税额
                for (int j = i + 1; j < protocolProjectBean.length; j++) {
                    final COMMON_FPKJ_XMXX eachBeanj = protocolProjectBean[j];
                    if (String.valueOf(eachBeanj.getXMMC()).startsWith("折扣行数")) {
                        //获取折扣率
                        firstzk = Double.parseDouble(String.valueOf(eachBeanj.getXMMC().substring(eachBeanj.getXMMC().indexOf("(")+1, eachBeanj.getXMMC().indexOf("%)")))) / 100;
                        zkhs = j;   //如果是折扣行数,给折扣行数赋值,值为该折扣行数在所有明细行的多少行
                        zkhscount = Integer.parseInt(String.valueOf(eachBeanj.getXMMC().substring(4,eachBeanj.getXMMC().indexOf("("))));    //获取折扣行数的行数值
                        //获取折扣行的金额和税额,并且进行格式化
                        xmzje = Math.abs(Double.parseDouble(ValidateUtil.decimalFormat(eachBeanj.getXMJE(),2)));
                        xmzse = Math.abs(Double.parseDouble(ValidateUtil.decimalFormat(eachBeanj.getSE(),2)));
                        zk = true;
                        break;
                    } else if (String.valueOf(eachBeanj.getXMMC()).startsWith("折扣(")) {
                        //获取折扣率
                        firstzk = Double.parseDouble(String.valueOf(eachBeanj.getXMMC().substring(eachBeanj.getXMMC().indexOf("(")+1, eachBeanj.getXMMC().indexOf("%)")))) / 100;
                        zkhs = j;
                        zks = true;
                        zkhscount = 1;
                        xmzje = Math.abs(Double.parseDouble(String.valueOf(eachBeanj.getXMJE())));
                        xmzse = Math.abs(Double.parseDouble(String.valueOf(eachBeanj.getSE())));
                        break;
                    }
                }
                //处理折扣行数开头的,折扣行数n-1行的处理逻辑
                if (i < zkhs - 1 && i>=zkhs-zkhscount && zk) {
                    /*
                     * 因为项目金额是负的,项目金额乘以折扣得到的是折扣的金额,该金额也是负的,所以,这里使用减号,
                     */
                    //判断最大项目金额所在行,因为初始化是0,这里赋值为折扣行数的首行
                    if(maxxmjeszh==0){
                        maxxmjeszh=i;
                    }
                    //判断默认行和下一行的绝对值金额大小,如果后面的金额比默认行大,记录后一行,否则保留当前行
                    if(Math.abs(Double.parseDouble(protocolProjectBean[maxxmjeszh].getXMJE()))<Math.abs(Double.parseDouble(protocolProjectBean[i+1].getXMJE()))){
                        maxxmjeszh=i+1;
                    }
                    //获取折扣行数中的每行金额和税额,然后计算出合并后的金额和税额,进行累加,并且根据合并后的金额反算单价,金额和税额进行格式化,保留小数点后2位
                    double xmje = Double.parseDouble(ValidateUtil.decimalFormat(-1*(Math.abs(Double.parseDouble(String.valueOf(eachBean.getXMJE()))))*(1-firstzk),2));
                    double se = Double.parseDouble(ValidateUtil.decimalFormat(-1*Math.abs(Double.parseDouble(String.valueOf(eachBean.getSE()))) *(1- firstzk),2));
                    double xmdj = -1*(Math.abs(Double.parseDouble(String.valueOf(eachBean.getXMJE()))))*(1-firstzk) / Double.parseDouble(String.valueOf(eachBean.getXMSL()).replace("-", ""));
                    xmljzkje += Double.parseDouble(ValidateUtil.decimalFormat(Math.abs(Double.parseDouble(String.valueOf(eachBean.getXMJE()))) * firstzk,2));
                    xmljzkse +=  Double.parseDouble(ValidateUtil.decimalFormat(Math.abs(Double.parseDouble(String.valueOf(eachBean.getSE())))* firstzk,2));
                    eachBean.setXMJE(String.valueOf(xmje));
                    eachBean.setSE(String.valueOf(se));
                    eachBean.setXMDJ(String.valueOf(xmdj));
                } else if (i == zkhs - 1 && zk) {   //判断折扣行数n-1行数据处理
                    /*
                     * 因为项目金额是负的,项目总结额是正的,项目累计折扣金额是负的,后面括号里面获得的值是正的,项目金额的绝对值大于括号里值的绝对值
                     * ,所以这里使用加号
                     */
                    //该行为折扣行数的n-1行,如果该行数据的金额不够多余的减的话,那么就找最大行金额的减去这个差值,
                    if((Math.abs(Double.valueOf(eachBean.getXMJE()))-(xmzje-Math.abs(xmljzkje)))<=0){//如果折扣率为0,并且最后一行,不够补充的,则补充到金额最大行中
                        protocolProjectBean[maxxmjeszh].setXMJE(ValidateUtil.decimalFormat(Double.parseDouble(protocolProjectBean[maxxmjeszh].getXMJE())+(xmzje-Math.abs(xmljzkje)),2));
                        protocolProjectBean[maxxmjeszh].setXMDJ(ValidateUtil.decimalFormat(Double.parseDouble(protocolProjectBean[maxxmjeszh].getXMJE())/Double.parseDouble(eachBean.getXMSL()),8));
                        protocolProjectBean[maxxmjeszh].setSE(ValidateUtil.decimalFormat(Double.parseDouble(protocolProjectBean[maxxmjeszh].getSE())+(xmzse-Math.abs(xmljzkse)),2));
                        maxxmjeszh=zkhs+1;
                    }else{
                        //被折扣行n-1行处理逻辑:该行金额为折扣行金额减去前面几行的累加金额,该行税额为折扣行税额减去前面几行的累加税额,并格式化,反算单价,
                        double xmje = Double.parseDouble(ValidateUtil.decimalFormat(-1*(Math.abs(Double.parseDouble(String.valueOf(eachBean.getXMJE()))) - Math.abs(xmljzkje - xmzje)),2));
                        double se = Double.parseDouble(ValidateUtil.decimalFormat(-1*(Math.abs(Double.parseDouble(String.valueOf(eachBean.getSE()))) - Math.abs(xmljzkse-xmzse )),2));
                        double xmdj = -1*(Math.abs(Double.parseDouble(String.valueOf(eachBean.getXMJE()))) - Math.abs(xmljzkje - xmzje)) / Double.parseDouble(String.valueOf(eachBean.getXMSL()).replace("-", ""));
                        //累计金额置零,防止出现多个折扣行数的商品无法计算的问题
                        xmljzkje = 0.00;
                        xmljzkse = 0.00;
                        eachBean.setXMJE(String.valueOf(xmje));
                        eachBean.setSE(String.valueOf(se));
                        eachBean.setXMDJ(String.valueOf(xmdj));
                    }
                } else if (zks && zkhs-i ==zkhscount ) {    //折扣行对应的商品行的处理

                    //单行折扣处理逻辑:把折扣行合并到被折扣行中,格式化金额和税额,反算单价
                    double xmje = Double.parseDouble(ValidateUtil.decimalFormat(-1*Math.abs(Double.parseDouble(String.valueOf(eachBean.getXMJE()))) + xmzje,2));
                    double se = Double.parseDouble(ValidateUtil.decimalFormat(-1*Math.abs(Double.parseDouble(String.valueOf(eachBean.getSE()))) + xmzse,2));
                    double xmdj = -1*Math.abs(Double.parseDouble(String.valueOf(eachBean.getXMJE()))) + xmzje / Double.parseDouble(String.valueOf(eachBean.getXMSL()).replace("-", ""));
                    eachBean.setXMJE(String.valueOf(xmje));
                    eachBean.setSE(String.valueOf(se));
                    eachBean.setXMDJ(String.valueOf(xmdj));
                }else if(i<zkhs-zkhscount){ //非折扣行的处理
                    
                    double xmje = Double.parseDouble(ValidateUtil.decimalFormat(-1*Math.abs(Double.parseDouble(String.valueOf(eachBean.getXMJE()))),2));
                    double se = Double.parseDouble(ValidateUtil.decimalFormat(-1*Math.abs(Double.parseDouble(String.valueOf(eachBean.getSE()))),2));
                    double xmdj = Math.abs(Double.parseDouble(String.valueOf(eachBean.getXMDJ())));
                    eachBean.setXMJE(String.valueOf(xmje));
                    eachBean.setSE(String.valueOf(se));
                    eachBean.setXMDJ(String.valueOf(xmdj));
                    firstzk=0.00;
                }
                /*
                 * 2015年6月10日 11:45:11
                 * 如果红票的项目单价不为空,并且没有"-"开头,就直接给他赋值,如果有"-"开头,就给他删除"-"
                 */
                if (String.valueOf(eachBean.getXMDJ()) != null && !eachBean.getXMDJ().startsWith("-")) {
                    eachBean.setXMDJ(eachBean.getXMDJ());
                } else {
                    eachBean.setXMDJ(eachBean.getXMDJ().replace("-", ""));
                }
                if (i != zkhs && firstzk != 1) {
                    mxlist.add(eachBean);
                }
            }
            protocolProjectBeannew = new COMMON_FPKJ_XMXX[mxlist.size()];
            double a = 0.00;
            double b = 0.00;
            for (int j = 0; j < mxlist.size(); j++) {
                protocolProjectBeannew[j] = mxlist.get(j);
                System.out.println(protocolProjectBeannew[j].getXMMC()+"-----单价:"+protocolProjectBeannew[j].getXMDJ()+"-----金额:"+protocolProjectBeannew[j].getXMJE()+"----税额:"+protocolProjectBeannew[j].getSE()+"--计算后的税额:"+"----税额:"+Double.parseDouble(protocolProjectBeannew[j].getXMJE())*Double.parseDouble(protocolProjectBeannew[j].getSL().replace("%", ""))/100);
//              System.out.println(protocolProjectBeannew[j].getXMJE());
//              System.out.println(protocolProjectBeannew[j].getSE());
//              System.out.println(Double.parseDouble(protocolProjectBeannew[j].getXMJE())*Double.parseDouble(protocolProjectBeannew[j].getSL().replace("%", ""))/100);
                
                a+= Double.valueOf(protocolProjectBeannew[j].getXMJE());
                b+= Double.valueOf(protocolProjectBeannew[j].getSE());
            }
            System.out.println("合计金额:"+a);
            System.out.println("合计税额:"+b);
//System.out.println("a");
        }
        return protocolProjectBeannew;

    }

    public static COMMON_FPKJ_XMXX[] HpMxZk(){
        COMMON_FPKJ_XMXX[] protocolProjectBean = new COMMON_FPKJ_XMXX[19];
        COMMON_FPKJ_XMXX mx = new COMMON_FPKJ_XMXX();
        mx.setXMDJ("-57.26495726");
        mx.setXMSL("2.0000");
        mx.setXMJE("-114.53");
        mx.setXMMC("鼠标");
        mx.setSL("17%");
        mx.setSE("-19.47");
        protocolProjectBean[0]=mx;
        
        
        COMMON_FPKJ_XMXX mx1 = new COMMON_FPKJ_XMXX();
        mx1.setXMDJ("-85.47008547");
        mx1.setXMSL("1");
        mx1.setXMJE("-85.47");
        mx1.setXMMC("鼠标1");
        mx1.setSL("17%");
        mx1.setSE("-14.53");
        protocolProjectBean[1]=mx1;
        
        COMMON_FPKJ_XMXX mx2 = new COMMON_FPKJ_XMXX();
        mx2.setXMDJ("-98.29059829");
        mx2.setXMSL("1");
        mx2.setXMJE("-98.29");
        mx2.setXMMC("鼠标2");
        mx2.setSL("17%");
        mx2.setSE("-16.71");
        protocolProjectBean[2]=mx2;
        
        COMMON_FPKJ_XMXX mx3 = new COMMON_FPKJ_XMXX();
        mx3.setXMDJ("-170.9401709");
        mx3.setXMSL("1");
        mx3.setXMJE("-170.94");
        mx3.setXMMC("鼠标3");
        mx3.setSL("17%");
        mx3.setSE("-29.06");
        protocolProjectBean[3]=mx3;
        
        COMMON_FPKJ_XMXX mx4 = new COMMON_FPKJ_XMXX();
        mx4.setXMDJ("34.18803419");
        mx4.setXMSL("1.0000");
        mx4.setXMJE("34.19");
        mx4.setXMMC("折扣行数2(12.698%)");
        mx4.setSL("17%");
        mx4.setSE("5.81");
        protocolProjectBean[4]=mx4;
        
        COMMON_FPKJ_XMXX mx5 = new COMMON_FPKJ_XMXX();
        mx5.setXMDJ("-33.62831858");
        mx5.setXMSL("2");
        mx5.setXMJE("-67.26");
        mx5.setXMMC("鼠标4");
        mx5.setSL("13%");
        mx5.setSE("-8.74");
        protocolProjectBean[5]=mx5;
        
        COMMON_FPKJ_XMXX mx6 = new COMMON_FPKJ_XMXX();
        mx6.setXMDJ("-26.54867257");
        mx6.setXMSL("2");
        mx6.setXMJE("-53.10");
        mx6.setXMMC("鼠标5");
        mx6.setSL("13%");
        mx6.setSE("-6.90");
        protocolProjectBean[6]=mx6;
        
        
        COMMON_FPKJ_XMXX mx7 = new COMMON_FPKJ_XMXX();
        mx7.setXMDJ("-44.24778761");
        mx7.setXMSL("3");
        mx7.setXMJE("-132.74");
        mx7.setXMMC("鼠标6");
        mx7.setSL("13%");
        mx7.setSE("-17.26");
        protocolProjectBean[7]=mx7;
        
        COMMON_FPKJ_XMXX mx8 = new COMMON_FPKJ_XMXX();
        mx8.setXMDJ("-53.09734513");
        mx8.setXMSL("4");
        mx8.setXMJE("-212.39");
        mx8.setXMMC("鼠标7");
        mx8.setSL("13%");
        mx8.setSE("-27.61");
        protocolProjectBean[8]=mx8;
        
        COMMON_FPKJ_XMXX mx9 = new COMMON_FPKJ_XMXX();
        mx9.setXMDJ("13.27433628");
        mx9.setXMSL("1.0000");
        mx9.setXMJE("13.27");
        mx9.setXMMC("折扣行数3(3.333%)");
        mx9.setSL("13%");
        mx9.setSE("1.73");
        protocolProjectBean[9]=mx9;
        
        COMMON_FPKJ_XMXX mx10 = new COMMON_FPKJ_XMXX();
        mx10.setXMDJ("-19.46902655");
        mx10.setXMSL("2");
        mx10.setXMJE("-38.94");
        mx10.setXMMC("鼠标8");
        mx10.setSL("13%");
        mx10.setSE("-5.06");
        protocolProjectBean[10]=mx10;
                   
        COMMON_FPKJ_XMXX mx11 = new COMMON_FPKJ_XMXX();
        mx11.setXMDJ("-53.09734513");
        mx11.setXMSL("3");
        mx11.setXMJE("-159.29");
        mx11.setXMMC("ddddddd等等");
        mx11.setSL("13%");
        mx11.setSE("-20.71");
        protocolProjectBean[11]=mx11;
                   
        COMMON_FPKJ_XMXX mx12 = new COMMON_FPKJ_XMXX();
        mx12.setXMDJ("15.04424779");
        mx12.setXMSL("1");
        mx12.setXMJE("15.04");
        mx12.setXMMC("折扣(9.444%)");
        mx12.setSL("13%");
        mx12.setSE("1.96");
        protocolProjectBean[12]=mx12;
        
        COMMON_FPKJ_XMXX mx13 = new COMMON_FPKJ_XMXX();
        mx13.setXMDJ("-32.47863248");
        mx13.setXMSL("1");
        mx13.setXMJE("-32.48");
        mx13.setXMMC("鼠标9");
        mx13.setSL("17%");
        mx13.setSE("-5.52");
        protocolProjectBean[13]=mx13;
        
        COMMON_FPKJ_XMXX mx14 = new COMMON_FPKJ_XMXX();
        mx14.setXMDJ("-25.64102564");
        mx14.setXMSL("2");
        mx14.setXMJE("-51.28");
        mx14.setXMMC("鼠标10");
        mx14.setSL("17%");
        mx14.setSE("-8.72");
        protocolProjectBean[14]=mx14;
        
        
        COMMON_FPKJ_XMXX mx15 = new COMMON_FPKJ_XMXX();
        mx15.setXMDJ("-42.73504274");
        mx15.setXMSL("4");
        mx15.setXMJE("-170.94");
        mx15.setXMMC("鼠标11");
        mx15.setSL("17%");
        mx15.setSE("-29.06");
        protocolProjectBean[15]=mx15;
        
        COMMON_FPKJ_XMXX mx16 = new COMMON_FPKJ_XMXX();
        mx16.setXMDJ("-51.28205128");
        mx16.setXMSL("2");
        mx16.setXMJE("-102.56");
        mx16.setXMMC("鼠标12");
        mx16.setSL("17%");
        mx16.setSE("-17.44");
        protocolProjectBean[16]=mx16;
        
        COMMON_FPKJ_XMXX mx17 = new COMMON_FPKJ_XMXX();
        mx17.setXMDJ("12.82051282");
        mx17.setXMSL("1.0000");
        mx17.setXMJE("12.82");
        mx17.setXMMC("折扣行数4(3.589%)");
        mx17.setSL("13%");
        mx17.setSE("2.18");
        protocolProjectBean[17]=mx17;
        
        COMMON_FPKJ_XMXX mx18 = new COMMON_FPKJ_XMXX();
        mx18.setXMDJ("-38.46153846");
        mx18.setXMSL("1");
        mx18.setXMJE("-38.46");
        mx18.setXMMC("鼠标13");
        mx18.setSL("17%");
        mx18.setSE("-6.54");
        protocolProjectBean[18]=mx18;
                   
//        COMMON_FPKJ_XMXX mx19 = new COMMON_FPKJ_XMXX();
//        mx19.setXMDJ("15.38461538");
//        mx19.setXMSL("1");
//        mx19.setXMJE("-15.38");
//        mx19.setXMMC("运费");
//        mx19.setSL("17%");
//        mx19.setSE("-2.62");
//        protocolProjectBean[19]=mx19;
        return protocolProjectBean;
    }
    
    public static COMMON_FPKJ_XMXX[] HpMx(){
        COMMON_FPKJ_XMXX[] protocolProjectBean = new COMMON_FPKJ_XMXX[5];
        COMMON_FPKJ_XMXX mx = new COMMON_FPKJ_XMXX();
        mx.setXMDJ("-4.42477876");
        mx.setXMSL("2.0000");
        mx.setXMJE("-8.85");
        mx.setXMMC("鼠标");
        mx.setSL("13%");
        mx.setSE("-1.1505");
        protocolProjectBean[0]=mx;
        COMMON_FPKJ_XMXX mx1 = new COMMON_FPKJ_XMXX();
        mx1.setXMDJ("-8.84955752");
        mx1.setXMSL("2");
        mx1.setXMJE("-17.70");
        mx1.setXMMC("鼠标1");
        mx1.setSL("17%");
        mx1.setSE("-3.0088495568");
        protocolProjectBean[1]=mx1;
        COMMON_FPKJ_XMXX mx2 = new COMMON_FPKJ_XMXX();
        mx2.setXMDJ("-30.01");
        mx2.setXMSL("3");
        mx2.setXMJE("-90.03");
        mx2.setXMMC("鼠标2");
        mx2.setSL("13%");
        mx2.setSE("-11.7039");
        protocolProjectBean[2]=mx2;
                   
        COMMON_FPKJ_XMXX mx4 = new COMMON_FPKJ_XMXX();
        mx4.setXMDJ("-12.82051282");
        mx4.setXMSL("10");
        mx4.setXMJE("-128.21");
        mx4.setXMMC("ddddddd等等");
        mx4.setSL("17%");
        mx4.setSE("-21.794871794");
        protocolProjectBean[3]=mx4;
                   
        COMMON_FPKJ_XMXX mx6 = new COMMON_FPKJ_XMXX();
        mx6.setXMDJ("57.52212389");
        mx6.setXMSL("1");
        mx6.setXMJE("-57.52212389");
        mx6.setXMMC("运费");
        mx6.setSL("13%");
        mx6.setSE("-7.48");
        protocolProjectBean[4]=mx6;
        return protocolProjectBean;
    }
    
    public static COMMON_FPKJ_XMXX[] HpMxzk100(){
        COMMON_FPKJ_XMXX[] protocolProjectBean = new COMMON_FPKJ_XMXX[3];
        COMMON_FPKJ_XMXX mx = new COMMON_FPKJ_XMXX();
        mx.setXMDJ("-4.42477876");
        mx.setXMSL("2.0000");
        mx.setXMJE("-8.85");
        mx.setXMMC("鼠标");
        mx.setSL("13%");
        mx.setSE("-1.1505");
        protocolProjectBean[1]=mx;
        COMMON_FPKJ_XMXX mx1 = new COMMON_FPKJ_XMXX();
        mx1.setXMDJ("4.42477876");
        mx1.setXMSL("2");
        mx1.setXMJE("8.85");
        mx1.setXMMC("折扣(100.00%)");
        mx1.setSL("13%");
        mx1.setSE("1.1505");
        protocolProjectBean[2]=mx1;
        COMMON_FPKJ_XMXX mx2 = new COMMON_FPKJ_XMXX();
        mx2.setXMDJ("-0.0088495575221239");
        mx2.setXMSL("1");
        mx2.setXMJE("-0.01");
        mx2.setXMMC("鼠标2");
        mx2.setSL("13%");
        mx2.setSE("-0.00");
        protocolProjectBean[0]=mx2;
                   
        return protocolProjectBean;
    }
    
    public static COMMON_FPKJ_XMXX[] HpMxzkl0(){
        COMMON_FPKJ_XMXX[] protocolProjectBean = new COMMON_FPKJ_XMXX[2];
        COMMON_FPKJ_XMXX mx = new COMMON_FPKJ_XMXX();
        mx.setXMDJ("-100000.00");
        mx.setXMSL("1.0000");
        mx.setXMJE("-100000.00");
        mx.setXMMC("鼠标");
        mx.setSL("17%");
        mx.setSE("-17000.00");
        protocolProjectBean[0]=mx;
        COMMON_FPKJ_XMXX mx1 = new COMMON_FPKJ_XMXX();
        mx1.setXMDJ("0.02");
        mx1.setXMSL("1");
        mx1.setXMJE("0.02");
        mx1.setXMMC("折扣(0.000%)");
        mx1.setSL("17%");
        mx1.setSE("0.00");
        protocolProjectBean[1]=mx1;
        return protocolProjectBean;
    }
    
    public static COMMON_FPKJ_XMXX[] HpMxdhzkl0(){
        COMMON_FPKJ_XMXX[] protocolProjectBean = new COMMON_FPKJ_XMXX[3];
        COMMON_FPKJ_XMXX mx = new COMMON_FPKJ_XMXX();
        mx.setXMDJ("-100000.00");
        mx.setXMSL("1.0000");
        mx.setXMJE("-100000.00");
        mx.setXMMC("鼠标");
        mx.setSL("17%");
        mx.setSE("-17000.00");
        protocolProjectBean[0]=mx;
        COMMON_FPKJ_XMXX mx1 = new COMMON_FPKJ_XMXX();
        mx1.setXMDJ("-0.01");
        mx1.setXMSL("1");
        mx1.setXMJE("-0.01");
        mx1.setXMMC("鼠标2");
        mx1.setSL("17%");
        mx1.setSE("-0.00");
        protocolProjectBean[1]=mx1;
        COMMON_FPKJ_XMXX mx2 = new COMMON_FPKJ_XMXX();
        mx2.setXMDJ("0.01");
        mx2.setXMSL("1");
        mx2.setXMJE("0.01");
        mx2.setXMMC("折扣行数2(0.000%)");
        mx2.setSL("17%");
        mx2.setSE("0.00");
        protocolProjectBean[2]=mx2;
                   
        return protocolProjectBean;
    }
    
    public static COMMON_FPKJ_XMXX[] HpMxdhzkl01(){
        COMMON_FPKJ_XMXX[] protocolProjectBean = new COMMON_FPKJ_XMXX[4];
        COMMON_FPKJ_XMXX mx = new COMMON_FPKJ_XMXX();
        mx.setXMDJ("-100000.00");
        mx.setXMSL("1.0000");
        mx.setXMJE("-100000.00");
        mx.setXMMC("鼠标");
        mx.setSL("17%");
        mx.setSE("-17000.00");
        protocolProjectBean[1]=mx;
        COMMON_FPKJ_XMXX mx1 = new COMMON_FPKJ_XMXX();
        mx1.setXMDJ("-0.01");
        mx1.setXMSL("1");
        mx1.setXMJE("-0.01");
        mx1.setXMMC("鼠标2");
        mx1.setSL("17%");
        mx1.setSE("-0.00");
        protocolProjectBean[0]=mx1;
        COMMON_FPKJ_XMXX mx2 = new COMMON_FPKJ_XMXX();
        mx2.setXMDJ("-0.01");
        mx2.setXMSL("1");
        mx2.setXMJE("-0.01");
        mx2.setXMMC("键盘");
        mx2.setSL("17%");
        mx2.setSE("-0.00");
        protocolProjectBean[2]=mx2;
        COMMON_FPKJ_XMXX mx3 = new COMMON_FPKJ_XMXX();
        mx3.setXMDJ("0.03");
        mx3.setXMSL("1");
        mx3.setXMJE("0.03");
        mx3.setXMMC("折扣行数3(0.000%)");
        mx3.setSL("17%");
        mx3.setSE("0.01");
        protocolProjectBean[3]=mx3;
                   
        return protocolProjectBean;
    }
    
    public static COMMON_FPKJ_XMXX[] HpMxdhzkl02(){
        COMMON_FPKJ_XMXX[] protocolProjectBean = new COMMON_FPKJ_XMXX[6];
        COMMON_FPKJ_XMXX mx = new COMMON_FPKJ_XMXX();
        mx.setXMDJ("-0.01");
        mx.setXMSL("1");
        mx.setXMJE("-0.01");
        mx.setXMMC("鼠标");
        mx.setSL("17%");
        mx.setSE("-0.00");
        protocolProjectBean[0]=mx;
        COMMON_FPKJ_XMXX mx1 = new COMMON_FPKJ_XMXX();
        mx1.setXMDJ("-100000.00");
        mx1.setXMSL("1");
        mx1.setXMJE("-100000.00");
        mx1.setXMMC("鼠标1");
        mx1.setSL("17%");
        mx1.setSE("-17000.00");
        protocolProjectBean[1]=mx1;
        
        COMMON_FPKJ_XMXX mx3 = new COMMON_FPKJ_XMXX();
        mx3.setXMDJ("-0.01");
        mx3.setXMSL("1.0000");
        mx3.setXMJE("-0.01");
        mx3.setXMMC("键盘");
        mx3.setSL("17%");
        mx3.setSE("-0.00");
        protocolProjectBean[2]=mx3;
                   
        COMMON_FPKJ_XMXX mx4 = new COMMON_FPKJ_XMXX();
        mx4.setXMDJ("0.03");
        mx4.setXMSL("1");
        mx4.setXMJE("0.03");
        mx4.setXMMC("折扣行数3(0.000%)");
        mx4.setSL("17%");
        mx4.setSE("0.01");
        protocolProjectBean[3]=mx4;
        
        COMMON_FPKJ_XMXX mx7 = new COMMON_FPKJ_XMXX();
        mx7.setXMDJ("-100000.00");
        mx7.setXMSL("1");
        mx7.setXMJE("-100000.00");
        mx7.setXMMC("ddddd");
        mx7.setSL("17%");
        mx7.setSE("-17000.00");
        protocolProjectBean[4]=mx7;
                   
        COMMON_FPKJ_XMXX mx5 = new COMMON_FPKJ_XMXX();
        mx5.setXMDJ("0.02");
        mx5.setXMSL("1.000");
        mx5.setXMJE("0.02");
        mx5.setXMMC("折扣(0.000%)");
        mx5.setSL("17%");
        mx5.setSE("0.02");
        protocolProjectBean[5]=mx5;
        return protocolProjectBean;
    }
    
    public static COMMON_FPKJ_XMXX[] HpMxdhzkl03(){
        COMMON_FPKJ_XMXX[] protocolProjectBean = new COMMON_FPKJ_XMXX[10];
        COMMON_FPKJ_XMXX mx = new COMMON_FPKJ_XMXX();
        mx.setXMDJ("-0.01");
        mx.setXMSL("1");
        mx.setXMJE("-0.01");
        mx.setXMMC("商品11");
        mx.setSL("17%");
        mx.setSE("-0.00");
        protocolProjectBean[0]=mx;
        COMMON_FPKJ_XMXX mx1 = new COMMON_FPKJ_XMXX();
        mx1.setXMDJ("-100000.00");
        mx1.setXMSL("1");
        mx1.setXMJE("-100000.00");
        mx1.setXMMC("商品12");
        mx1.setSL("17%");
        mx1.setSE("-17000.00");
        protocolProjectBean[1]=mx1;
        
        COMMON_FPKJ_XMXX mx3 = new COMMON_FPKJ_XMXX();
        mx3.setXMDJ("-0.01");
        mx3.setXMSL("1.0000");
        mx3.setXMJE("-0.01");
        mx3.setXMMC("商品13");
        mx3.setSL("17%");
        mx3.setSE("-0.00");
        protocolProjectBean[2]=mx3;
                   
        COMMON_FPKJ_XMXX mx4 = new COMMON_FPKJ_XMXX();
        mx4.setXMDJ("0.03");
        mx4.setXMSL("1");
        mx4.setXMJE("0.03");
        mx4.setXMMC("折扣行数3(0.000%)");
        mx4.setSL("17%");
        mx4.setSE("0.01");
        protocolProjectBean[3]=mx4;
        
        COMMON_FPKJ_XMXX mx7 = new COMMON_FPKJ_XMXX();
        mx7.setXMDJ("-100000.00");
        mx7.setXMSL("1");
        mx7.setXMJE("-100000.00");
        mx7.setXMMC("商品2");
        mx7.setSL("17%");
        mx7.setSE("-17000.00");
        protocolProjectBean[4]=mx7;
                   
        COMMON_FPKJ_XMXX mx5 = new COMMON_FPKJ_XMXX();
        mx5.setXMDJ("0.02");
        mx5.setXMSL("1.000");
        mx5.setXMJE("0.02");
        mx5.setXMMC("折扣(0.000%)");
        mx5.setSL("17%");
        mx5.setSE("0.02");
        protocolProjectBean[5]=mx5;
        
        COMMON_FPKJ_XMXX mx8 = new COMMON_FPKJ_XMXX();
        mx8.setXMDJ("-0.01");
        mx8.setXMSL("1");
        mx8.setXMJE("-0.01");
        mx8.setXMMC("商品31");
        mx8.setSL("17%");
        mx8.setSE("-0.00");
        protocolProjectBean[6]=mx8;
        COMMON_FPKJ_XMXX mx9 = new COMMON_FPKJ_XMXX();
        mx9.setXMDJ("-100000.00");
        mx9.setXMSL("1");
        mx9.setXMJE("-100000.00");
        mx9.setXMMC("商品32");
        mx9.setSL("17%");
        mx9.setSE("-17000.00");
        protocolProjectBean[7]=mx9;
        
        COMMON_FPKJ_XMXX mx10 = new COMMON_FPKJ_XMXX();
        mx10.setXMDJ("-0.01");
        mx10.setXMSL("1.0000");
        mx10.setXMJE("-0.01");
        mx10.setXMMC("商品33");
        mx10.setSL("17%");
        mx10.setSE("-0.00");
        protocolProjectBean[8]=mx10;
                   
        COMMON_FPKJ_XMXX mx11 = new COMMON_FPKJ_XMXX();
        mx11.setXMDJ("0.03");
        mx11.setXMSL("1");
        mx11.setXMJE("0.03");
        mx11.setXMMC("折扣行数3(0.000%)");
        mx11.setSL("17%");
        mx11.setSE("0.01");
        protocolProjectBean[9]=mx11;
        return protocolProjectBean;
    }
    
    public static COMMON_FPKJ_XMXX[] HpMxList2(){
        COMMON_FPKJ_XMXX[] protocolProjectBean = new COMMON_FPKJ_XMXX[6];
        COMMON_FPKJ_XMXX mx = new COMMON_FPKJ_XMXX();
        mx.setXMDJ("-100.00");
        mx.setXMSL("1");
        mx.setXMJE("-100.00");
        mx.setXMMC("鼠标");
        mx.setSL("17%");
        mx.setSE("-1.70");
        protocolProjectBean[0]=mx;
        COMMON_FPKJ_XMXX mx1 = new COMMON_FPKJ_XMXX();
        mx1.setXMDJ("1.00");
        mx1.setXMSL("1");
        mx1.setXMJE("1.00");
        mx1.setXMMC("折扣(1.000%)");
        mx1.setSL("17%");
        mx1.setSE("-0.02");
        protocolProjectBean[1]=mx1;
        
        COMMON_FPKJ_XMXX mx3 = new COMMON_FPKJ_XMXX();
        mx3.setXMDJ("-1000.00");
        mx3.setXMSL("1.0000");
        mx3.setXMJE("-1000.00");
        mx3.setXMMC("键盘");
        mx3.setSL("17%");
        mx3.setSE("-17.00");
        protocolProjectBean[2]=mx3;
                   
        COMMON_FPKJ_XMXX mx4 = new COMMON_FPKJ_XMXX();
        mx4.setXMDJ("-500.00");
        mx4.setXMSL("1");
        mx4.setXMJE("-500.00");
        mx4.setXMMC("鼠标11");
        mx4.setSL("17%");
        mx4.setSE("-85.00");
        protocolProjectBean[3]=mx4;
        
        COMMON_FPKJ_XMXX mx7 = new COMMON_FPKJ_XMXX();
        mx7.setXMDJ("-10.00");
        mx7.setXMSL("1");
        mx7.setXMJE("-10.00");
        mx7.setXMMC("ddddd");
        mx7.setSL("17%");
        mx7.setSE("-0.17");
        protocolProjectBean[4]=mx7;
                   
        COMMON_FPKJ_XMXX mx5 = new COMMON_FPKJ_XMXX();
        mx5.setXMDJ("45.00");
        mx5.setXMSL("1.000");
        mx5.setXMJE("45.00");
        mx5.setXMMC("折扣行数3(2.980%)");
        mx5.setSL("17%");
        mx5.setSE("7.65");
        protocolProjectBean[5]=mx5;
        return protocolProjectBean;
    }
    public static COMMON_FPKJ_XMXX[] LpMxZk(){
        COMMON_FPKJ_XMXX[] protocolProjectBean = new COMMON_FPKJ_XMXX[7];
        COMMON_FPKJ_XMXX mx = new COMMON_FPKJ_XMXX();
        mx.setXMDJ("4.42477876");
        mx.setXMSL("2.0000");
        mx.setXMJE("8.85");
        mx.setXMMC("鼠标");
        mx.setSL("13%");
        mx.setSE("1.15");
        protocolProjectBean[0]=mx;
        COMMON_FPKJ_XMXX mx1 = new COMMON_FPKJ_XMXX();
        mx1.setXMDJ("8.84955752");
        mx1.setXMSL("2");
        mx1.setXMJE("17.70");
        mx1.setXMMC("鼠标1");
        mx1.setSL("17%");
        mx1.setSE("2.30");
        protocolProjectBean[1]=mx1;
        
        COMMON_FPKJ_XMXX mx3 = new COMMON_FPKJ_XMXX();
        mx3.setXMDJ("-6.19469027");
        mx3.setXMSL("1.0000");
        mx3.setXMJE("-6.19");
        mx3.setXMMC("折扣行数2(23.300%)");
        mx3.setSL("13%");
        mx3.setSE("-0.81");
        protocolProjectBean[2]=mx3;
                   
        COMMON_FPKJ_XMXX mx4 = new COMMON_FPKJ_XMXX();
        mx4.setXMDJ("12.82051282");
        mx4.setXMSL("10");
        mx4.setXMJE("128.21");
        mx4.setXMMC("ddddddd等等");
        mx4.setSL("17%");
        mx4.setSE("21.79");
        protocolProjectBean[3]=mx4;
        
        COMMON_FPKJ_XMXX mx7 = new COMMON_FPKJ_XMXX();
        mx7.setXMDJ("12.82051282");
        mx7.setXMSL("10");
        mx7.setXMJE("128.21");
        mx7.setXMMC("ddddddd等等11111");
        mx7.setSL("17%");
        mx7.setSE("21.79");
        protocolProjectBean[4]=mx7;
                   
        COMMON_FPKJ_XMXX mx5 = new COMMON_FPKJ_XMXX();
        mx5.setXMDJ("-17.09401709");
        mx5.setXMSL("1.000");
        mx5.setXMJE("-17.09");
        mx5.setXMMC("折扣(13.300%)");
        mx5.setSL("17%");
        mx5.setSE("-2.91");
        protocolProjectBean[5]=mx5;
                   
        COMMON_FPKJ_XMXX mx6 = new COMMON_FPKJ_XMXX();
        mx6.setXMDJ("57.52212389");
        mx6.setXMSL("1.000");
        mx6.setXMJE("57.52212389");
        mx6.setXMMC("运费");
        mx6.setSL("13%");
        mx6.setSE("7.48");
        protocolProjectBean[6]=mx6;
        return protocolProjectBean;
    }
    
    public static COMMON_FPKJ_XMXX[] LpMx(){
        COMMON_FPKJ_XMXX[] protocolProjectBean = new COMMON_FPKJ_XMXX[5];
        COMMON_FPKJ_XMXX mx = new COMMON_FPKJ_XMXX();
        mx.setXMDJ("4.42477876");
        mx.setXMSL("2.0000");
        mx.setXMJE("8.85");
        mx.setXMMC("鼠标");
        mx.setSL("13%");
        mx.setSE("1.15");
        protocolProjectBean[0]=mx;
        COMMON_FPKJ_XMXX mx1 = new COMMON_FPKJ_XMXX();
        mx1.setXMDJ("8.84955752");
        mx1.setXMSL("2");
        mx1.setXMJE("17.70");
        mx1.setXMMC("鼠标1");
        mx1.setSL("17%");
        mx1.setSE("2.30");
        protocolProjectBean[1]=mx1;
        COMMON_FPKJ_XMXX mx2 = new COMMON_FPKJ_XMXX();
        mx2.setXMDJ("30.01");
        mx2.setXMSL("3");
        mx2.setXMJE("90.01");
        mx2.setXMMC("鼠标2");
        mx2.setSL("13%");
        mx2.setSE("2.30");
        protocolProjectBean[2]=mx2;
                   
        COMMON_FPKJ_XMXX mx4 = new COMMON_FPKJ_XMXX();
        mx4.setXMDJ("12.82051282");
        mx4.setXMSL("10");
        mx4.setXMJE("128.21");
        mx4.setXMMC("ddddddd等等");
        mx4.setSL("17%");
        mx4.setSE("21.79");
        protocolProjectBean[3]=mx4;
                   
        COMMON_FPKJ_XMXX mx6 = new COMMON_FPKJ_XMXX();
        mx6.setXMDJ("57.52212389");
        mx6.setXMSL("1.000");
        mx6.setXMJE("57.52212389");
        mx6.setXMMC("运费");
        mx6.setSL("13%");
        mx6.setSE("7.48");
        protocolProjectBean[4]=mx6;
        return protocolProjectBean;
    }
    
    public static COMMON_FPKJ_XMXX[] LpMxList(){
        COMMON_FPKJ_XMXX[] protocolProjectBean = new COMMON_FPKJ_XMXX[17];
        COMMON_FPKJ_XMXX mx = new COMMON_FPKJ_XMXX();
        mx.setXMDJ("4.42477876");
        mx.setXMSL("2.0000");
        mx.setXMJE("8.85");
        mx.setXMMC("鼠标");
        mx.setSL("13%");
        mx.setSE("1.15");
        protocolProjectBean[0]=mx;
        COMMON_FPKJ_XMXX mx1 = new COMMON_FPKJ_XMXX();
        mx1.setXMDJ("8.84955752");
        mx1.setXMSL("2");
        mx1.setXMJE("17.70");
        mx1.setXMMC("键盘");
        mx1.setSL("13%");
        mx1.setSE("2.30");
        protocolProjectBean[1]=mx1;
//        COMMON_FPKJ_XMXX mx2 = new COMMON_FPKJ_XMXX();
//        mx2.setXMDJ("-30.01");
//        mx2.setXMSL("3");
//        mx2.setXMJE("-90.01");
//        mx2.setXMMC("鼠标2");
//        mx2.setSL("13%");
//        mx2.setSE("-2.30");
//        protocolProjectBean[1]=mx2;
        COMMON_FPKJ_XMXX mx3 = new COMMON_FPKJ_XMXX();
        mx3.setXMDJ("-17.69911504");
        mx3.setXMSL("1.0000");
        mx3.setXMJE("-17.70");
        mx3.setXMMC("折扣行数2(66.667%)");
        mx3.setSL("13%");
        mx3.setSE("-2.30");
        protocolProjectBean[2]=mx3;
                   
        COMMON_FPKJ_XMXX mx4 = new COMMON_FPKJ_XMXX();
        mx4.setXMDJ("12.82051282");
        mx4.setXMSL("10");
        mx4.setXMJE("128.21");
        mx4.setXMMC("鼠标垫-丝滑");
        mx4.setSL("17%");
        mx4.setSE("21.79");
        protocolProjectBean[3]=mx4;
        
        COMMON_FPKJ_XMXX mx5 = new COMMON_FPKJ_XMXX();
        mx5.setXMDJ("25.64102564");
        mx5.setXMSL("1");
        mx5.setXMJE("25.64");
        mx5.setXMMC("图书一");
        mx5.setSL("17%");
        mx5.setSE("4.36");
        protocolProjectBean[4]=mx5;
        
        COMMON_FPKJ_XMXX mx6 = new COMMON_FPKJ_XMXX();
        mx6.setXMDJ("34.18803419");
        mx6.setXMSL("1");
        mx6.setXMJE("34.19");
        mx6.setXMMC("图书二");
        mx6.setSL("17%");
        mx6.setSE("5.81");
        protocolProjectBean[5]=mx6;
        
        COMMON_FPKJ_XMXX mx7 = new COMMON_FPKJ_XMXX();
        mx7.setXMDJ("42.73504274");
        mx7.setXMSL("1");
        mx7.setXMJE("42.74");
        mx7.setXMMC("图书三");
        mx7.setSL("17%");
        mx7.setSE("7.26");
        protocolProjectBean[6]=mx7;
        
        COMMON_FPKJ_XMXX mx8 = new COMMON_FPKJ_XMXX();
        mx8.setXMDJ("51.28205128");
        mx8.setXMSL("1");
        mx8.setXMJE("51.28");
        mx8.setXMMC("图书四");
        mx8.setSL("17%");
        mx8.setSE("8.72");
        protocolProjectBean[7]=mx8;
        
        COMMON_FPKJ_XMXX mx9 = new COMMON_FPKJ_XMXX();
        mx9.setXMDJ("59.82905983");
        mx9.setXMSL("1");
        mx9.setXMJE("59.83");
        mx9.setXMMC("图书五");
        mx9.setSL("17%");
        mx9.setSE("10.17");
        protocolProjectBean[8]=mx9;
        
        COMMON_FPKJ_XMXX mx10 = new COMMON_FPKJ_XMXX();
        mx10.setXMDJ("68.37606838");
        mx10.setXMSL("1");
        mx10.setXMJE("68.38");
        mx10.setXMMC("图书六");
        mx10.setSL("17%");
        mx10.setSE("11.62");
        protocolProjectBean[9]=mx10;
        
        COMMON_FPKJ_XMXX mx11 = new COMMON_FPKJ_XMXX();
        mx11.setXMDJ("76.92307692");
        mx11.setXMSL("1");
        mx11.setXMJE("76.92");
        mx11.setXMMC("图书七");
        mx11.setSL("17%");
        mx11.setSE("13.08");
        protocolProjectBean[10]=mx11;
        
        COMMON_FPKJ_XMXX mx12 = new COMMON_FPKJ_XMXX();
        mx12.setXMDJ("85.47008547");
        mx12.setXMSL("1");
        mx12.setXMJE("85.47");
        mx12.setXMMC("图书八");
        mx12.setSL("17%");
        mx12.setSE("14.53");
        protocolProjectBean[11]=mx12;
        
        COMMON_FPKJ_XMXX mx13 = new COMMON_FPKJ_XMXX();
        mx13.setXMDJ("86.32478632");
        mx13.setXMSL("1");
        mx13.setXMJE("86.32");
        mx13.setXMMC("图书九");
        mx13.setSL("17%");
        mx13.setSE("14.68");
        protocolProjectBean[12]=mx13;
        
        COMMON_FPKJ_XMXX mx14 = new COMMON_FPKJ_XMXX();
        mx14.setXMDJ("87.17948718");
        mx14.setXMSL("1");
        mx14.setXMJE("87.18");
        mx14.setXMMC("图书十");
        mx14.setSL("17%");
        mx14.setSE("14.82");
        protocolProjectBean[13]=mx14;
        
        COMMON_FPKJ_XMXX mx15 = new COMMON_FPKJ_XMXX();
        mx15.setXMDJ("88.03418803");
        mx15.setXMSL("1");
        mx15.setXMJE("88.03");
        mx15.setXMMC("图书十一");
        mx15.setSL("17%");
        mx15.setSE("14.97");
        protocolProjectBean[14]=mx15;
        
                   
        COMMON_FPKJ_XMXX mx16 = new COMMON_FPKJ_XMXX();
        mx16.setXMDJ("-21.36752137");
        mx16.setXMSL("1.000");
        mx16.setXMJE("-21.37");
        mx16.setXMMC("折扣行数12(2.561%)");
        mx16.setSL("17%");
        mx16.setSE("-3.63");
        protocolProjectBean[15]=mx16;
                   
        COMMON_FPKJ_XMXX mx17 = new COMMON_FPKJ_XMXX();
        mx17.setXMDJ("12.82051282");
        mx17.setXMSL("1.000");
        mx17.setXMJE("12.82");
        mx17.setXMMC("运费");
        mx17.setSL("17%");
        mx17.setSE("2.18");
        protocolProjectBean[16]=mx17;
        return protocolProjectBean;
    }
    
    public static COMMON_FPKJ_XMXX[] HpMxList(){
        COMMON_FPKJ_XMXX[] protocolProjectBean = new COMMON_FPKJ_XMXX[17];
        COMMON_FPKJ_XMXX mx = new COMMON_FPKJ_XMXX();
        mx.setXMDJ("-4.42477876");
        mx.setXMSL("-2.0000");
        mx.setXMJE("-8.85");
        mx.setXMMC("鼠标");
        mx.setSL("13%");
        mx.setSE("-1.15");
        protocolProjectBean[0]=mx;
        COMMON_FPKJ_XMXX mx1 = new COMMON_FPKJ_XMXX();
        mx1.setXMDJ("-8.84955752");
        mx1.setXMSL("2");
        mx1.setXMJE("-17.70");
        mx1.setXMMC("键盘");
        mx1.setSL("13%");
        mx1.setSE("-2.30");
        protocolProjectBean[1]=mx1;
//        COMMON_FPKJ_XMXX mx2 = new COMMON_FPKJ_XMXX();
//        mx2.setXMDJ("-30.01");
//        mx2.setXMSL("3");
//        mx2.setXMJE("-90.01");
//        mx2.setXMMC("鼠标2");
//        mx2.setSL("13%");
//        mx2.setSE("-2.30");
//        protocolProjectBean[1]=mx2;
        COMMON_FPKJ_XMXX mx3 = new COMMON_FPKJ_XMXX();
        mx3.setXMDJ("17.69911504");
        mx3.setXMSL("1.0000");
        mx3.setXMJE("17.70");
        mx3.setXMMC("折扣行数2(66.667%)");
        mx3.setSL("13%");
        mx3.setSE("2.30");
        protocolProjectBean[2]=mx3;
        
        COMMON_FPKJ_XMXX mx4 = new COMMON_FPKJ_XMXX();
        mx4.setXMDJ("-12.82051282");
        mx4.setXMSL("-10");
        mx4.setXMJE("-128.21");
        mx4.setXMMC("鼠标垫-丝滑");
        mx4.setSL("17%");
        mx4.setSE("-21.79");
        protocolProjectBean[3]=mx4;
        
        COMMON_FPKJ_XMXX mx5 = new COMMON_FPKJ_XMXX();
        mx5.setXMDJ("-25.64102564");
        mx5.setXMSL("-1");
        mx5.setXMJE("-25.64");
        mx5.setXMMC("图书一");
        mx5.setSL("17%");
        mx5.setSE("-4.36");
        protocolProjectBean[4]=mx5;
        
        COMMON_FPKJ_XMXX mx6 = new COMMON_FPKJ_XMXX();
        mx6.setXMDJ("-34.18803419");
        mx6.setXMSL("1");
        mx6.setXMJE("-34.19");
        mx6.setXMMC("图书二");
        mx6.setSL("17%");
        mx6.setSE("-5.81");
        protocolProjectBean[5]=mx6;
        
        COMMON_FPKJ_XMXX mx7 = new COMMON_FPKJ_XMXX();
        mx7.setXMDJ("-42.73504274");
        mx7.setXMSL("-1");
        mx7.setXMJE("42.74");
        mx7.setXMMC("图书三");
        mx7.setSL("17%");
        mx7.setSE("-7.26");
        protocolProjectBean[7]=mx7;
        
        COMMON_FPKJ_XMXX mx8 = new COMMON_FPKJ_XMXX();
        mx8.setXMDJ("-51.28205128");
        mx8.setXMSL("1");
        mx8.setXMJE("-51.28");
        mx8.setXMMC("图书四");
        mx8.setSL("17%");
        mx8.setSE("-8.72");
        protocolProjectBean[6]=mx8;
        
        COMMON_FPKJ_XMXX mx9 = new COMMON_FPKJ_XMXX();
        mx9.setXMDJ("-59.82905983");
        mx9.setXMSL("-1");
        mx9.setXMJE("59.83");
        mx9.setXMMC("图书五");
        mx9.setSL("17%");
        mx9.setSE("-10.17");
        protocolProjectBean[8]=mx9;
        
        COMMON_FPKJ_XMXX mx10 = new COMMON_FPKJ_XMXX();
        mx10.setXMDJ("-68.37606838");
        mx10.setXMSL("-1");
        mx10.setXMJE("68.38");
        mx10.setXMMC("图书六");
        mx10.setSL("17%");
        mx10.setSE("-11.62");
        protocolProjectBean[9]=mx10;
        
        COMMON_FPKJ_XMXX mx11 = new COMMON_FPKJ_XMXX();
        mx11.setXMDJ("-76.92307692");
        mx11.setXMSL("-1");
        mx11.setXMJE("76.92");
        mx11.setXMMC("图书七");
        mx11.setSL("17%");
        mx11.setSE("-13.08");
        protocolProjectBean[10]=mx11;
        
        COMMON_FPKJ_XMXX mx12 = new COMMON_FPKJ_XMXX();
        mx12.setXMDJ("-85.47008547");
        mx12.setXMSL("-1");
        mx12.setXMJE("-85.47");
        mx12.setXMMC("图书八");
        mx12.setSL("17%");
        mx12.setSE("-14.53");
        protocolProjectBean[11]=mx12;
        
        COMMON_FPKJ_XMXX mx13 = new COMMON_FPKJ_XMXX();
        mx13.setXMDJ("-86.32478632");
        mx13.setXMSL("1");
        mx13.setXMJE("-86.32");
        mx13.setXMMC("图书九");
        mx13.setSL("17%");
        mx13.setSE("-14.68");
        protocolProjectBean[12]=mx13;
        
        COMMON_FPKJ_XMXX mx14 = new COMMON_FPKJ_XMXX();
        mx14.setXMDJ("-87.17948718");
        mx14.setXMSL("1");
        mx14.setXMJE("-87.18");
        mx14.setXMMC("图书十");
        mx14.setSL("17%");
        mx14.setSE("-14.82");
        protocolProjectBean[13]=mx14;
        
        COMMON_FPKJ_XMXX mx15 = new COMMON_FPKJ_XMXX();
        mx15.setXMDJ("-88.03418803");
        mx15.setXMSL("1");
        mx15.setXMJE("-88.03");
        mx15.setXMMC("图书十一");
        mx15.setSL("17%");
        mx15.setSE("-14.97");
        protocolProjectBean[14]=mx15;
        
        
        COMMON_FPKJ_XMXX mx16 = new COMMON_FPKJ_XMXX();
        mx16.setXMDJ("21.36752137");
        mx16.setXMSL("1.000");
        mx16.setXMJE("21.37");
        mx16.setXMMC("折扣行数12(2.561%)");
        mx16.setSL("17%");
        mx16.setSE("3.63");
        protocolProjectBean[15]=mx16;
        
        COMMON_FPKJ_XMXX mx17 = new COMMON_FPKJ_XMXX();
        mx17.setXMDJ("-12.82051282");
        mx17.setXMSL("-1.000");
        mx17.setXMJE("-12.82");
        mx17.setXMMC("运费");
        mx17.setSL("17%");
        mx17.setSE("-2.18");
        protocolProjectBean[16]=mx17;
        return protocolProjectBean;
    }
    
    public static COMMON_FPKJ_XMXX[] HpMxListzk100(){
        COMMON_FPKJ_XMXX[] protocolProjectBean = new COMMON_FPKJ_XMXX[10];
        
        COMMON_FPKJ_XMXX mx4 = new COMMON_FPKJ_XMXX();
        mx4.setXMDJ("-12.82051282");
        mx4.setXMSL("-10");
        mx4.setXMJE("-128.21");
        mx4.setXMMC("鼠标垫-丝滑");
        mx4.setSL("17%");
        mx4.setSE("-21.79");
        protocolProjectBean[0]=mx4;
        
        COMMON_FPKJ_XMXX mx5 = new COMMON_FPKJ_XMXX();
        mx5.setXMDJ("-25.64102564");
        mx5.setXMSL("-1");
        mx5.setXMJE("-25.64");
        mx5.setXMMC("图书一");
        mx5.setSL("17%");
        mx5.setSE("-4.36");
        protocolProjectBean[1]=mx5;
        
        COMMON_FPKJ_XMXX mx6 = new COMMON_FPKJ_XMXX();
        mx6.setXMDJ("27.94102564");
        mx6.setXMSL("1");
        mx6.setXMJE("153.85");
        mx6.setXMMC("折扣行数2(100.00%)");
        mx6.setSL("17%");
        mx6.setSE("26.15");
        protocolProjectBean[2]=mx6;
        
        COMMON_FPKJ_XMXX mx7 = new COMMON_FPKJ_XMXX();
        mx7.setXMDJ("-42.73504274");
        mx7.setXMSL("-1");
        mx7.setXMJE("42.74");
        mx7.setXMMC("图书三");
        mx7.setSL("17%");
        mx7.setSE("-7.26");
        protocolProjectBean[3]=mx7;
        
        COMMON_FPKJ_XMXX mx8 = new COMMON_FPKJ_XMXX();
        mx8.setXMDJ("-51.28205128");
        mx8.setXMSL("1");
        mx8.setXMJE("-51.28");
        mx8.setXMMC("图书四");
        mx8.setSL("17%");
        mx8.setSE("-8.72");
        protocolProjectBean[4]=mx8;
        
        COMMON_FPKJ_XMXX mx9 = new COMMON_FPKJ_XMXX();
        mx9.setXMDJ("-59.82905983");
        mx9.setXMSL("-1");
        mx9.setXMJE("59.83");
        mx9.setXMMC("图书五");
        mx9.setSL("17%");
        mx9.setSE("-10.17");
        protocolProjectBean[5]=mx9;
        
        COMMON_FPKJ_XMXX mx10 = new COMMON_FPKJ_XMXX();
        mx10.setXMDJ("153.84615385");
        mx10.setXMSL("-1");
        mx10.setXMJE("153.85");
        mx10.setXMMC("折扣行数3(100.00%)");
        mx10.setSL("17%");
        mx10.setSE("26.15");
        protocolProjectBean[6]=mx10;
        
        COMMON_FPKJ_XMXX mx11 = new COMMON_FPKJ_XMXX();
        mx11.setXMDJ("-76.92307692");
        mx11.setXMSL("-1");
        mx11.setXMJE("76.92");
        mx11.setXMMC("图书七");
        mx11.setSL("17%");
        mx11.setSE("-13.08");
        protocolProjectBean[7]=mx11;
        
        COMMON_FPKJ_XMXX mx12 = new COMMON_FPKJ_XMXX();
        mx12.setXMDJ("-76.92307692");
        mx12.setXMSL("-1");
        mx12.setXMJE("-76.92");
        mx12.setXMMC("折扣(100.00%)");
        mx12.setSL("17%");
        mx12.setSE("-13.08");
        protocolProjectBean[8]=mx12;
        
        COMMON_FPKJ_XMXX mx17 = new COMMON_FPKJ_XMXX();
        mx17.setXMDJ("0.0085470085470085");
        mx17.setXMSL("1.000");
        mx17.setXMJE("0.01");
        mx17.setXMMC("运费");
        mx17.setSL("17%");
        mx17.setSE("-0.00");
        protocolProjectBean[9]=mx17;
        return protocolProjectBean;
    }

}
