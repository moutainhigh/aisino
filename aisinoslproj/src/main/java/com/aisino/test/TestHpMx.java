package com.aisino.test;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.aisino.domain.einvoice.entity.InvoiceDetailEntity;
import com.aisino.protocol.bean.COMMON_FPKJ_XMXX;

public class TestHpMx {
    
    public static void main(String[] args) {
        COMMON_FPKJ_XMXX[] protocolProjectBean=null;
        
//      protocolProjectBean = LpMx();
//        protocolProjectBean = LpMxList();
//      protocolProjectBean = HpMx();
//      protocolProjectBean = HpMxList();
      String kplx = "4";
//      processInsertRedInvoiceDetail(protocolProjectBean);
      if(kplx.equals("1")){
          protocolProjectBean = LpMx();
          processInsertInvoiceDetail(protocolProjectBean);
          
      }else if(kplx.equals("2")){
          protocolProjectBean = HpMx();
          processInsertInvoiceDetail(processInsertRedInvoiceDetail(protocolProjectBean));
      }else if(kplx.equals("3")){
          protocolProjectBean = LpMxList();
          processInsertInvoiceDetail(protocolProjectBean);
      }else if(kplx.equals("4")){
          protocolProjectBean = HpMxList();
          processInsertInvoiceDetail(processInsertRedInvoiceDetail(protocolProjectBean));
      }
      String a = "折扣行数31(111)".replace("(", " ").replace(")", " ").split(" ")[0].substring(4);
      System.out.println(a.substring(4));
    }
    private static void processInsertInvoiceDetail(final COMMON_FPKJ_XMXX[] protocolProjectBean) {

        if (protocolProjectBean != null && protocolProjectBean.length > 0) {


            for (int i = 0; i < protocolProjectBean.length; i++) {

                final COMMON_FPKJ_XMXX eachBean = protocolProjectBean[i];

                if (String.valueOf(eachBean.getXMMC()).startsWith("折扣行数") || String.valueOf(eachBean.getXMMC()).startsWith("折扣(")) {
                    eachBean.setFPHXZ("1");
                } else if (String.valueOf(eachBean.getXMMC()).startsWith("运费")) {
                    eachBean.setFPHXZ("0");
                } else {
                    if (i + 1 < protocolProjectBean.length) {
                        for (int j = i + 1; j < protocolProjectBean.length; j++) {

                            final COMMON_FPKJ_XMXX eachBeanj = protocolProjectBean[j];

                            // 发票行性质:0代表的是正常商品,1,代表的值折扣商品,2,代表的是被折扣行
                            if (String.valueOf(eachBeanj.getXMMC()).startsWith("折扣行数") || String.valueOf(eachBeanj.getXMMC()).startsWith("折扣(")) {
                                eachBean.setFPHXZ("2");
                                break;
                            } else {
                                eachBean.setFPHXZ("0");
                            }
                        }
                    } else {
                        eachBean.setFPHXZ("0");
                    }

                }

                final InvoiceDetailEntity eachEntityCompose = new InvoiceDetailEntity();


                eachEntityCompose.setItemIndex(Long.parseLong(String.valueOf(i + 1)));
                eachEntityCompose.setItemName(eachBean.getXMMC());
                // eachEntityCompose.setUnitName(eachBean.getXMDW());
                eachEntityCompose.setSpecificationModel(eachBean.getGGXH());
                eachEntityCompose.setItemCount(Double.parseDouble(eachBean.getXMSL()));
                eachEntityCompose.setItemUnitCost(Double.parseDouble(eachBean.getXMDJ()));
                eachEntityCompose.setItemAmount(Double.parseDouble(eachBean.getXMJE()));
                eachEntityCompose.setItemCode(eachBean.getXMBM());
                /*
                 * 2015年3月10日 13:47:30 新增的明细字段 张双超 begin
                 */
                // eachEntityCompose.setListTaxItem(eachBean.getSM());
                eachEntityCompose.setInfoTaxRate(eachBean.getSL());
                eachEntityCompose.setListUnit(eachBean.getXMDW());
                // eachEntityCompose.setListPriceKind(eachBean.getHSJBZ());
                eachEntityCompose.setInvoiceLineProperty(eachBean.getFPHXZ());
                eachEntityCompose.setListTaxAmount(Double.parseDouble(eachBean.getSE()));
                /*
                 * 2015年3月10日 13:47:30 新增的明细字段 张双超 end
                 */
//                System.out.println(eachBean.getXMMC()+"-----金额:"+eachBean.getXMJE()+"----税额:"+eachBean.getSE()+"--计算后的税额:"+"----税额:"+Double.parseDouble(eachBean.getXMJE())*Double.parseDouble(eachBean.getSL().replace("%", ""))/100);
            

            }
        }

    }
    
    private static COMMON_FPKJ_XMXX[] processInsertRedInvoiceDetail(final COMMON_FPKJ_XMXX[] protocolProjectBean) {
        COMMON_FPKJ_XMXX[] protocolProjectBeannew = null;
        if (protocolProjectBean != null && protocolProjectBean.length > 0) {
            List<COMMON_FPKJ_XMXX> mxlist = new ArrayList<COMMON_FPKJ_XMXX>();
            Double xmzje = 0.00; // 项目总金额
            Double xmzse = 0.00; // 项目总税额
            Double xmljzkje = 0.00; // 项目累计折扣金额
            Double xmljzkse = 0.00; // 项目累计折扣税额
            Double zklvjelj = 0.00; // 把折扣率变成不含税的--金额累计
            Boolean zklvbl = true;
            Double firstzk = 0.17D; // 第一个折扣
            
            DecimalFormat df = new DecimalFormat("######0000.00");
            df.setRoundingMode(RoundingMode.HALF_UP);

            for (int i = 0; i < protocolProjectBean.length; i++) {

                final COMMON_FPKJ_XMXX eachBean = protocolProjectBean[i];
                if (String.valueOf(eachBean.getXMMC()).startsWith("折扣行数") || String.valueOf(eachBean.getXMMC()).startsWith("折扣(")) {
                    zklvbl=true;
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
                Boolean zk = false;
                Boolean zks = false;
                
                while (zklvbl) {
                    boolean zkh = false;
                    int j=i+1;
                    zklvjelj = Math.abs(Double.parseDouble(String.valueOf(eachBean.getXMDJ()))*Double.parseDouble(String.valueOf(eachBean.getXMSL())));//获取
                    
                    for (j = i + 1; j < protocolProjectBean.length; j++) {
                        final COMMON_FPKJ_XMXX eachBeanj = protocolProjectBean[j];
                        if (String.valueOf(eachBeanj.getXMMC()).startsWith("折扣行数")) {
                            xmzje = Math.abs(Double.parseDouble(String.valueOf(eachBeanj.getXMDJ()))*Double.parseDouble(String.valueOf(eachBeanj.getXMSL())));
                            zkh = true;
                            zklvbl=false;
                            xmljzkje=0.00;
                            break;
                        } else if (String.valueOf(eachBeanj.getXMMC()).startsWith("折扣(")) {
                            zkh = true;
                            zklvbl=false;
                            xmljzkje= 0.00;
                            xmzje = Math.abs(Double.parseDouble(String.valueOf(eachBeanj.getXMDJ()))*Double.parseDouble(String.valueOf(eachBeanj.getXMSL())));
                            break;
                        }
                        zklvjelj += Math.abs(Double.parseDouble(String.valueOf(eachBeanj.getXMDJ()))* Double.parseDouble(String.valueOf(eachBeanj.getXMSL())));
                    }
                    firstzk = xmzje/zklvjelj;
                    if(zkh || (j+1) >= protocolProjectBean.length){
                        break;
                    }
                }
                for (int j = i + 1; j < protocolProjectBean.length; j++) {
                    final COMMON_FPKJ_XMXX eachBeanj = protocolProjectBean[j];
                    if (String.valueOf(eachBeanj.getXMMC()).startsWith("折扣行数")) {
//                        firstzk = Double.parseDouble(String.valueOf(eachBeanj.getXMMC()).replace("(", " ").replace(")", " ").split(" ")[1].replace("%", "")) / 100;
                        zkhs = j;
                        xmzje = Math.abs(Double.parseDouble(String.valueOf(eachBeanj.getXMDJ()))*Double.parseDouble(String.valueOf(eachBeanj.getXMSL())));
                        xmzse = xmzje*Double.parseDouble(String.valueOf(eachBeanj.getSL().replace("%", "")))/100;
                        zk = true;
                        break;
                    } else if (String.valueOf(eachBeanj.getXMMC()).startsWith("折扣(")) {
//                        firstzk = Double.parseDouble(String.valueOf(eachBeanj.getXMMC()).replace("(", " ").replace(")", " ").split(" ")[1].replace("%", "")) / 100;
                        zkhs = j;
                        zks = true;
                        xmzje = Math.abs(Double.parseDouble(String.valueOf(eachBeanj.getXMDJ()))*Double.parseDouble(String.valueOf(eachBeanj.getXMSL())));
                        xmzse = xmzje*Double.parseDouble(String.valueOf(eachBeanj.getSL().replace("%", "")))/100;
                        break;
                    }
                }
                if (i < zkhs - 1 && zk) {
                    /*
                     * 因为项目金额是负的,项目金额乘以折扣得到的是折扣的金额,该金额也是负的,所以,这里使用减号,
                     */
//                    Double xmje = Double.parseDouble(eachBean.getXMJE()) - (Double.parseDouble(eachBean.getXMJE()) * firstzk);
                    Double xmje = -1*(Math.abs(Math.abs(Double.parseDouble(String.valueOf(eachBean.getXMDJ()))*Double.parseDouble(String.valueOf(eachBean.getXMSL()))))*(1-firstzk));
                    Double se = -1*(Math.abs(Double.parseDouble(String.valueOf(eachBean.getXMDJ()))*Double.parseDouble(String.valueOf(eachBean.getXMSL())))*Double.parseDouble(String.valueOf(eachBean.getSL().replace("%", "")))/100 *(1- firstzk));
                    Double xmdj = xmje / Double.parseDouble(String.valueOf(eachBean.getXMSL()).replace("-", ""));
                    xmljzkje += Math.abs(Double.parseDouble(String.valueOf(eachBean.getXMDJ()))*Double.parseDouble(String.valueOf(eachBean.getXMSL()))) * firstzk;
                    xmljzkse +=  Math.abs(Double.parseDouble(String.valueOf(eachBean.getXMDJ()))*Double.parseDouble(String.valueOf(eachBean.getXMSL())))*Double.parseDouble(String.valueOf(eachBean.getSL().replace("%", "")))/100* firstzk;
                    eachBean.setXMJE(xmje.toString());
                    eachBean.setSE(se.toString());
                    eachBean.setXMDJ(xmdj.toString());
                } else if (i == zkhs - 1 && zk) {
                    /*
                     * 因为项目金额是负的,项目总结额是正的,项目累计折扣金额是负的,后面括号里面获得的值是正的,项目金额的绝对值大于括号里值的绝对值
                     * ,所以这里使用加号
                     */
                    Double xmje = -1*(Math.abs(Double.parseDouble(String.valueOf(eachBean.getXMJE()))) - Math.abs(xmljzkje - xmzje));
                    Double se = -1*(Math.abs(Double.parseDouble(String.valueOf(eachBean.getSE()))) - Math.abs(xmljzkse-xmzse ));
                    Double xmdj = xmje / Double.parseDouble(String.valueOf(eachBean.getXMSL()).replace("-", ""));
                    eachBean.setXMJE(xmje.toString());
                    eachBean.setSE(se.toString());
                    eachBean.setXMDJ(xmdj.toString());
                } else if (zks) {

                    Double xmje = -1*Math.abs(Double.parseDouble(String.valueOf(eachBean.getXMDJ()))*Double.parseDouble(String.valueOf(eachBean.getXMSL()))) + xmzje;
                    Double se = -1*Math.abs(Double.parseDouble(String.valueOf(eachBean.getXMDJ()))*Double.parseDouble(String.valueOf(eachBean.getXMSL())))*Double.parseDouble(String.valueOf(eachBean.getSL().replace("%", "")))/100 + xmzse;
                    Double xmdj = xmje / Double.parseDouble(String.valueOf(eachBean.getXMSL()).replace("-", ""));
                    eachBean.setXMJE(xmje.toString());
                    eachBean.setSE(se.toString());
                    eachBean.setXMDJ(xmdj.toString());
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
                if (i != zkhs) {
                    mxlist.add(eachBean);
                }
            }
            protocolProjectBeannew = new COMMON_FPKJ_XMXX[mxlist.size()];
            for (int j = 0; j < mxlist.size(); j++) {
                protocolProjectBeannew[j] = mxlist.get(j);
//                System.out.println(protocolProjectBeannew[j].getXMMC()+"-----金额:"+protocolProjectBeannew[j].getXMJE()+"----税额:"+protocolProjectBeannew[j].getSE()+"--计算后的税额:"+"----税额:"+Double.parseDouble(protocolProjectBeannew[j].getXMJE())*Double.parseDouble(protocolProjectBeannew[j].getSL().replace("%", ""))/100);
//                System.out.println(protocolProjectBeannew[j].getXMJE());
                System.out.println(protocolProjectBeannew[j].getSE());
//                System.out.println(Double.parseDouble(protocolProjectBeannew[j].getXMJE())*Double.parseDouble(protocolProjectBeannew[j].getSL().replace("%", ""))/100);
            }
//System.out.println("a");
        }
        return protocolProjectBeannew;

    }
    
    
    
//    private static COMMON_FPKJ_XMXX[] processInsertRedInvoiceDetail(final COMMON_FPKJ_XMXX[] protocolProjectBean) {
//        COMMON_FPKJ_XMXX[] protocolProjectBeannew = null;
//        if (protocolProjectBean != null && protocolProjectBean.length > 0) {
//            List<COMMON_FPKJ_XMXX> mxlist = new ArrayList<COMMON_FPKJ_XMXX>();
//            Double xmzje = 0.00; // 项目总金额
//            Double xmzse = 0.00; // 项目总税额
//            Double xmljzkje = 0.00; // 项目累计折扣金额
//            Double xmljzkse = 0.00; // 项目累计折扣税额
//
//            for (int i = 0; i < protocolProjectBean.length; i++) {
//
//                final COMMON_FPKJ_XMXX eachBean = protocolProjectBean[i];
//                if (String.valueOf(eachBean.getXMMC()).startsWith("折扣行数") || String.valueOf(eachBean.getXMMC()).startsWith("折扣(")) {
//                    continue;
//                }
//
//                /*
//                 * 2015年6月10日 11:45:11 如果红票的项目数量不为空,并且没有"-"开头,就给他添加一个"-"
//                 */
//                if (String.valueOf(eachBean.getXMSL()) != null && !eachBean.getXMSL().startsWith("-")) {
//                    eachBean.setXMSL("-" + eachBean.getXMSL());
//                } else {
//                    eachBean.setXMSL(eachBean.getXMSL());
//                }
//                Double firstzk = 0.17D; // 第一个折扣
//                int zkhs = 1000; // 折扣行数
//                Boolean zk = false;
//                Boolean zks = false;
//                for (int j = i + 1; j < protocolProjectBean.length; j++) {
//                    final COMMON_FPKJ_XMXX eachBeanj = protocolProjectBean[j];
//                    if (String.valueOf(eachBeanj.getXMMC()).startsWith("折扣行数")) {
//                        firstzk = Double.parseDouble(String.valueOf(eachBeanj.getXMMC()).replace("(", " ").replace(")", " ").split(" ")[1].replace("%", "")) / 100;
//                        zkhs = j;
//                        xmzje = Double.parseDouble(String.valueOf(eachBeanj.getXMJE()));
//                        xmzse = Double.parseDouble(String.valueOf(eachBeanj.getSE()));
//                        zk = true;
//                        break;
//                    } else if (String.valueOf(eachBeanj.getXMMC()).startsWith("折扣(")) {
//                        firstzk = Double.parseDouble(String.valueOf(eachBeanj.getXMMC()).replace("(", " ").replace(")", " ").split(" ")[1].replace("%", "")) / 100;
//                        zkhs = j;
//                        zks = true;
//                        xmzje = Double.parseDouble(String.valueOf(eachBeanj.getXMJE()));
//                        xmzse = Double.parseDouble(String.valueOf(eachBeanj.getSE()));
//                        break;
//                    }
//                }
//                if (i < zkhs - 1 && zk) {
//                    /*
//                     * 因为项目金额是负的,项目金额乘以折扣得到的是折扣的金额,该金额也是负的,所以,这里使用减号,
//                     */
//                    Double xmje = Double.parseDouble(eachBean.getXMJE()) - (Double.parseDouble(eachBean.getXMJE()) * firstzk);
//                    Double se = Double.parseDouble(eachBean.getSE()) - (Double.parseDouble(eachBean.getSE()) * firstzk);
//                    Double xmdj = xmje / Double.parseDouble(String.valueOf(eachBean.getXMSL()).replace("-", ""));
//                    xmljzkje += Double.parseDouble(eachBean.getXMJE()) * firstzk;
//                    xmljzkse += Double.parseDouble(eachBean.getSE()) * firstzk;
//                    eachBean.setXMJE(xmje.toString());
//                    eachBean.setSE(se.toString());
//                    eachBean.setXMDJ(xmdj.toString());
//                } else if (i == zkhs - 1 && zk) {
//                    /*
//                     * 因为项目金额是负的,项目总结额是正的,项目累计折扣金额是负的,后面括号里面获得的值是正的,项目金额的绝对值大于括号里值的绝对值
//                     * ,所以这里使用加号
//                     */
//                    Double xmje = Double.parseDouble(eachBean.getXMJE()) - Math.abs(xmzje + xmljzkje);
//                    Double se = Double.parseDouble(eachBean.getSE()) + (xmzse + xmljzkse);
//                    Double xmdj = xmje / Double.parseDouble(String.valueOf(eachBean.getXMSL()).replace("-", ""));
//                    eachBean.setXMJE(xmje.toString());
//                    eachBean.setSE(se.toString());
//                    eachBean.setXMDJ(xmdj.toString());
//                } else if (zks) {
//
//                    Double xmje = Double.parseDouble(eachBean.getXMJE()) + xmzje;
//                    Double se = Double.parseDouble(eachBean.getSE()) + xmzse;
//                    Double xmdj = xmje / Double.parseDouble(String.valueOf(eachBean.getXMSL()).replace("-", ""));
//                    eachBean.setXMJE(xmje.toString());
//                    eachBean.setSE(se.toString());
//                    eachBean.setXMDJ(xmdj.toString());
//                }
//
//                /*
//                 * 2015年6月10日 11:45:11
//                 * 如果红票的项目单价不为空,并且没有"-"开头,就直接给他赋值,如果有"-"开头,就给他删除"-"
//                 */
//                if (String.valueOf(eachBean.getXMDJ()) != null && !eachBean.getXMDJ().startsWith("-")) {
//                    eachBean.setXMDJ(eachBean.getXMDJ());
//                } else {
//                    eachBean.setXMDJ(eachBean.getXMDJ().replace("-", ""));
//                }
//                if (i != zkhs) {
//                    mxlist.add(eachBean);
//                }
//            }
//            protocolProjectBeannew = new COMMON_FPKJ_XMXX[mxlist.size()];
//            for (int j = 0; j < mxlist.size(); j++) {
//                protocolProjectBeannew[j] = mxlist.get(j);
//            }
//System.out.println("1");
//        }
//        return protocolProjectBeannew;
//
//    }
//    
    public static COMMON_FPKJ_XMXX[] HpMxZk(){
        COMMON_FPKJ_XMXX[] protocolProjectBean = new COMMON_FPKJ_XMXX[6];
        COMMON_FPKJ_XMXX mx = new COMMON_FPKJ_XMXX();
        mx.setXMDJ("-4.42477876");
        mx.setXMSL("2.0000");
        mx.setXMJE("-8.85");
        mx.setXMMC("鼠标");
        mx.setSL("13%");
        mx.setSE("-1.15");
        protocolProjectBean[0]=mx;
        COMMON_FPKJ_XMXX mx1 = new COMMON_FPKJ_XMXX();
        mx1.setXMDJ("-8.84955752");
        mx1.setXMSL("2");
        mx1.setXMJE("-17.70");
        mx1.setXMMC("鼠标1");
        mx1.setSL("17%");
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
        mx3.setXMDJ("6.19469027");
        mx3.setXMSL("1.0000");
        mx3.setXMJE("6.19");
        mx3.setXMMC("折扣行数2(23.300%)");
        mx3.setSL("13%");
        mx3.setSE("0.81");
        protocolProjectBean[2]=mx3;
                   
        COMMON_FPKJ_XMXX mx4 = new COMMON_FPKJ_XMXX();
        mx4.setXMDJ("-12.82051282");
        mx4.setXMSL("10");
        mx4.setXMJE("-128.21");
        mx4.setXMMC("ddddddd等等");
        mx4.setSL("17%");
        mx4.setSE("-21.79");
        protocolProjectBean[3]=mx4;
                   
        COMMON_FPKJ_XMXX mx5 = new COMMON_FPKJ_XMXX();
        mx5.setXMDJ("17.09401709");
        mx5.setXMSL("1");
        mx5.setXMJE("17.09");
        mx5.setXMMC("折扣(13.300%)");
        mx5.setSL("17%");
        mx5.setSE("2.91");
        protocolProjectBean[4]=mx5;
                   
        COMMON_FPKJ_XMXX mx6 = new COMMON_FPKJ_XMXX();
        mx6.setXMDJ("57.52212389");
        mx6.setXMSL("1");
        mx6.setXMJE("-57.52212389");
        mx6.setXMMC("运费");
        mx6.setSL("13%");
        mx6.setSE("-7.48");
        protocolProjectBean[5]=mx6;
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
    public static COMMON_FPKJ_XMXX[] LpMxZk(){
        COMMON_FPKJ_XMXX[] protocolProjectBean = new COMMON_FPKJ_XMXX[6];
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
                   
        COMMON_FPKJ_XMXX mx5 = new COMMON_FPKJ_XMXX();
        mx5.setXMDJ("-17.09401709");
        mx5.setXMSL("1.000");
        mx5.setXMJE("-17.09");
        mx5.setXMMC("折扣(13.300%)");
        mx5.setSL("17%");
        mx5.setSE("-2.91");
        protocolProjectBean[4]=mx5;
                   
        COMMON_FPKJ_XMXX mx6 = new COMMON_FPKJ_XMXX();
        mx6.setXMDJ("57.52212389");
        mx6.setXMSL("1.000");
        mx6.setXMJE("57.52212389");
        mx6.setXMMC("运费");
        mx6.setSL("13%");
        mx6.setSE("7.48");
        protocolProjectBean[5]=mx6;
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
        protocolProjectBean[6]=mx7;
        
        COMMON_FPKJ_XMXX mx8 = new COMMON_FPKJ_XMXX();
        mx8.setXMDJ("-51.28205128");
        mx8.setXMSL("1");
        mx8.setXMJE("-51.28");
        mx8.setXMMC("图书四");
        mx8.setSL("17%");
        mx8.setSE("-8.72");
        protocolProjectBean[7]=mx8;
        
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

}
