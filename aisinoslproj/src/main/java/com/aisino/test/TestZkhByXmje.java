package com.aisino.test;

import java.util.ArrayList;
import java.util.List;

import com.aisino.common.util.ValidateUtil;
import com.aisino.protocol.bean.COMMON_FPKJ_FPT;
import com.aisino.protocol.bean.COMMON_FPKJ_XMXX;

public class TestZkhByXmje {
    public static void main(String[] args) {
        COMMON_FPKJ_FPT COMMON_FPKJ_FPT = new COMMON_FPKJ_FPT();
        COMMON_FPKJ_FPT.setKPHJJE("0.02");
        COMMON_FPKJ_FPT.setKPHJSE("0.02");
        COMMON_FPKJ_FPT.setHJBHSJE("0.00");
        COMMON_FPKJ_XMXX[] fpkjxx_xmxxs = new COMMON_FPKJ_XMXX[3];
        COMMON_FPKJ_XMXX mx = new COMMON_FPKJ_XMXX();
        mx.setXMDJ("-0.354700855");
        mx.setXMSL("1.0000");
        mx.setXMJE("-0.35");
        mx.setFPHXZ("0");
        mx.setXMMC("鼠标");
        mx.setSL("17%");
        mx.setSE("0.06");
        fpkjxx_xmxxs[0] = mx;
        COMMON_FPKJ_XMXX mx1 = new COMMON_FPKJ_XMXX();
        mx1.setXMDJ("-0.50000000");
        mx1.setXMSL("1.0000");
        mx1.setXMJE("-0.50");
        mx1.setFPHXZ("2");
        mx1.setXMMC("22222");
        mx1.setSL("17%");
        mx1.setSE("0.09");
        fpkjxx_xmxxs[1] = mx1;
        COMMON_FPKJ_XMXX mx3 = new COMMON_FPKJ_XMXX();
        mx3.setXMDJ("0.846153846");
        mx3.setXMSL("1.0000");
        mx3.setXMJE("0.85");
        mx3.setFPHXZ("1");
        mx3.setXMMC("22222");
        mx3.setSL("17%");
        mx3.setSE("-0.14");
        fpkjxx_xmxxs[2] = mx3;
        processInsertRedInvoiceDetail(fpkjxx_xmxxs);
        
        
        
    }

    /**
     * 
     * <p>红票合并折扣行</p>
     * 
     * @param protocolProjectBean
     * @return COMMON_FPKJ_XMXX[]
     * @author: 张双超
     * @date: Created on Sep 16, 2015 5:54:16 PM
     */
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
                if ("1".equals(eachBean.getFPHXZ())) {
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
                    /*
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
                    }*/
                    
                    if ("1".equals(eachBeanj.getFPHXZ())) {
                        //获取折扣率（ 折扣金额/商品金额 ）* 100%
                        //firstzk = Double.parseDouble(String.valueOf(eachBeanj.getXMMC().substring(eachBeanj.getXMMC().indexOf("(")+1, eachBeanj.getXMMC().indexOf("%)")))) / 100;
                        firstzk = Double.parseDouble(String.valueOf(eachBeanj.getXMJE()))/Double.parseDouble(protocolProjectBean[j-1].getXMJE())*1;
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
//                      double d = xmzje-Math.abs(xmljzkje);
//                      double d1 = -MathUtil.sub(String.valueOf(xmzje), String.valueOf(Math.abs(xmljzkje)));
//                      double d2 = -MathUtil.sub(String.valueOf(xmzse), String.valueOf(Math.abs(xmljzkse)));
//                        protocolProjectBean[maxxmjeszh].setXMJE(ValidateUtil.decimalFormat(Double.parseDouble(protocolProjectBean[maxxmjeszh].getXMJE())+d1,2));
//                        protocolProjectBean[maxxmjeszh].setXMDJ(ValidateUtil.decimalFormat(MathUtil.div(protocolProjectBean[maxxmjeszh].getXMJE(),eachBean.getXMSL(),8),8));
//                        protocolProjectBean[maxxmjeszh].setSE(ValidateUtil.decimalFormat(Double.parseDouble(protocolProjectBean[maxxmjeszh].getSE())+d2,2));
//                        maxxmjeszh=zkhs+1;
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
                    double xmdj = -1*Math.abs(Double.parseDouble(String.valueOf(eachBean.getXMJE())) + xmzje) / Double.parseDouble(String.valueOf(eachBean.getXMSL()).replace("-", ""));
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
                /*
                 * 修改:折扣为100%和小于100%时，红票合并 (2016-01-06)
                 */
                if (i != zkhs && firstzk <= 1) {
                    mxlist.add(eachBean);
                }
            }
            protocolProjectBeannew = new COMMON_FPKJ_XMXX[mxlist.size()];
            for (int j = 0; j < mxlist.size(); j++) {
                protocolProjectBeannew[j] = mxlist.get(j);
            }
            
        }
        return protocolProjectBeannew;

    }
    
}
