package com.aisino.test;

import static org.joda.time.DateTime.now;

import org.joda.time.DateTime;

import com.aisino.domain.einvoice.entity.InvoiceDetailEntity;
import com.aisino.protocol.bean.COMMON_FPKJ_XMXX;

public class TestMX {
    /* FIN: 600行级函数：发票开具请求信息入库校验 */
    public static void main(String[] args) {
        COMMON_FPKJ_XMXX[] protocolProjectBean=null;
//        protocolProjectBean = HpMx();
        protocolProjectBean = LpMx();
        TestFphxz(protocolProjectBean);
    }

    public static void TestFphxz(COMMON_FPKJ_XMXX[] protocolProjectBean){

        if (protocolProjectBean != null && protocolProjectBean.length > 0) {


            for (int i = 0; i < protocolProjectBean.length; i++) {

                final COMMON_FPKJ_XMXX eachBean = protocolProjectBean[i];
                if (String.valueOf(eachBean.getXMMC()).startsWith("折扣行数") || String.valueOf(eachBean.getXMMC()).startsWith("折扣(") ) {
                    eachBean.setFPHXZ("1");
                }else if(String.valueOf(eachBean.getXMMC()).startsWith("运费")){
                    eachBean.setFPHXZ("0");
                }else{
                    for (int j = i+1; j < protocolProjectBean.length; j++) {
                        
                        final COMMON_FPKJ_XMXX eachBeanj = protocolProjectBean[j];
                        
                        //发票行性质:0代表的是正常商品,1,代表的值折扣商品,2,代表的是被折扣行
                        if (String.valueOf(eachBeanj.getXMMC()).startsWith("折扣行数") || String.valueOf(eachBeanj.getXMMC()).startsWith("折扣(")) {
                            eachBean.setFPHXZ("2");
                            break;
                        }else{
                            eachBean.setFPHXZ("0");
                        }
                    }
                    
                }

                final InvoiceDetailEntity eachEntityCompose = new InvoiceDetailEntity();

//                eachEntityCompose.setId(theIds[i]);

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
                System.out.println(eachEntityCompose.getItemName()+"====="+eachEntityCompose.getInvoiceLineProperty());
                /*
                 * 2015年3月10日 13:47:30 新增的明细字段 张双超 end
                 */

//                repository.insertInvoiceDetail(eachEntityCompose);

            }
        }
    }
//   public static void main(String[] args) {
//       Double xmje = 100.00;
//       Double a = 2.0000;
//       String b = String.valueOf(a);
//       Double xmdj = xmje / Double.parseDouble((b.replace("-", "")));
//       System.out.println(xmdj);
//   }
    
    
    public static COMMON_FPKJ_XMXX[] HpMx(){
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
        mx5.setXMSL("");
        mx5.setXMJE("17.09");
        mx5.setXMMC("折扣(13.300%)");
        mx5.setSL("17%");
        mx5.setSE("2.91");
        protocolProjectBean[4]=mx5;
                   
        COMMON_FPKJ_XMXX mx6 = new COMMON_FPKJ_XMXX();
        mx6.setXMDJ("");
        mx6.setXMSL("");
        mx6.setXMJE("-57.52212389");
        mx6.setXMMC("运费");
        mx6.setSL("13%");
        mx6.setSE("-7.48");
        protocolProjectBean[5]=mx6;
        return protocolProjectBean;
    }
    
    public static COMMON_FPKJ_XMXX[] LpMx(){
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
//        COMMON_FPKJ_XMXX mx2 = new COMMON_FPKJ_XMXX();
//        mx2.setXMDJ("-30.01");
//        mx2.setXMSL("3");
//        mx2.setXMJE("-90.01");
//        mx2.setXMMC("鼠标2");
//        mx2.setSL("13%");
//        mx2.setSE("-2.30");
//        protocolProjectBean[1]=mx2;
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
    
}
