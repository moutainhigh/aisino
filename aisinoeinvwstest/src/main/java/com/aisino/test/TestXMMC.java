package com.aisino.test;//package com.aisino.test;
//
//import com.aisino.protocol.bean.FPKJXX_XMXX;
//
//public class TestXMMC {
//    public static void main(String[] args) {
//        FPKJXX_XMXX fpkjxx_XMXX = new FPKJXX_XMXX();
//        fpkjxx_XMXX.setXMMC("折扣行数2(100.000%)");
//        try {
//            int zkhs = getZkhs(fpkjxx_XMXX);
//            double zkl = getZkl(fpkjxx_XMXX);
//            System.out.println("折扣行数："+ zkhs );
//            System.out.println("折扣率："+ zkl );
//        } catch (Exception e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * <p>判断是否是折扣行</p>
//     *
//     * @param FPKJXX_XMXX
//     * @return boolean
//     * @author: jerome.wang
//     * @date: Created on 2015-8-10 上午10:10:28
//     */
//    private static boolean isZkh(FPKJXX_XMXX FPKJXX_XMXX){
//        if(FPKJXX_XMXX.getXMMC().indexOf("折扣") != -1 || FPKJXX_XMXX.getXMMC().indexOf("折扣行数") != -1){
//            return true;
//        }
//        return false;
//    }
//
//
//    /**
//     * <p>判断是否是多折扣行</p>
//     *
//     * @param FPKJXX_XMXX
//     * @return boolean
//     * @author: jerome.wang
//     * @date: Created on 2015-8-10 上午10:53:06
//     */
//    private static boolean isMoreZkh(FPKJXX_XMXX FPKJXX_XMXX){
//        if(FPKJXX_XMXX.getXMMC().indexOf("折扣行数") != -1){
//            return true;
//        }
//        return false;
//    }
//
//    /**
//     * <p>获取折扣行数</p>
//     *
//     * @param FPKJXX_XMXX
//     * @return
//     * @throws Exception int
//     * @author: jerome.wang
//     * @date: Created on 2015-8-10 上午10:55:05
//     */
//    private static int getZkhs(FPKJXX_XMXX FPKJXX_XMXX) throws Exception{
//        String xmmc = FPKJXX_XMXX.getXMMC();
//        String zkhsStr = xmmc.substring(xmmc.indexOf("折扣行数")+4, xmmc.indexOf("("));   //获取折扣行数
//        if(null == zkhsStr || "".equals(zkhsStr)){
//            throw new Exception("未能找到折扣行数");
//        }else{
//            int zkhs = Integer.valueOf(zkhsStr).intValue();
//            if(zkhs == 0){
//                throw new Exception("折扣行数不能为0");
//            }
//            return zkhs;
//        }
//    }
//
//    /**
//     * <p>获取税率</p>
//     *
//     * @param FPKJXX_XMXX
//     * @return
//     * @throws Exception double
//     * @author: jerome.wang
//     * @date: Created on 2015-8-10 上午10:53:23
//     */
//    private static double getZkl(FPKJXX_XMXX FPKJXX_XMXX) throws Exception{
//        String xmmc = FPKJXX_XMXX.getXMMC();
//        String zklStr = xmmc.substring(xmmc.indexOf("(")+1, xmmc.indexOf(")")).replace("%", "");  //获取折扣率
//        zklStr = String.valueOf(Double.parseDouble(zklStr)/100);  //折扣率除以100
//        if(null == zklStr || "".equals(zklStr)){
//            throw new Exception("未能找到折扣率");
//        }else{
//            return Double.parseDouble(zklStr);
//        }
//    }
//}
