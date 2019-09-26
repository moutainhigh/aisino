package com.aisino.trans.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.aisino.protocol.bean.COMMON_FPKJ_DDXX;
import com.aisino.protocol.bean.COMMON_FPKJ_FPT;
import com.aisino.protocol.bean.COMMON_FPKJ_WLXX;
import com.aisino.protocol.bean.COMMON_FPKJ_XMXX;
import com.aisino.protocol.bean.COMMON_FPKJ_ZFXX;
import com.aisino.protocol.bean.FPKJXX_DDMXXX;
import com.aisino.protocol.bean.FPKJXX_DDXX;
import com.aisino.protocol.bean.FPKJXX_FPTXX;
import com.aisino.protocol.bean.FPKJXX_WLXX;
import com.aisino.protocol.bean.FPKJXX_XMXX;
import com.aisino.protocol.bean.FPKJXX_ZFXX;
import com.aisino.protocol.bean.REQUEST_COMMON_FPKJ;
import com.aisino.protocol.bean.REQUEST_FPKJXX;
import com.aisino.util.DecimalCalculateUtil;

public class ProtoclConvert {
	public static REQUEST_COMMON_FPKJ convertComm(REQUEST_FPKJXX REQUEST_FPKJXX) throws Exception{
		DecimalFormat df = new DecimalFormat("######0.00"); 
		df.setRoundingMode(RoundingMode.HALF_UP);
		REQUEST_COMMON_FPKJ REQUEST_COMMON_FPKJ = new REQUEST_COMMON_FPKJ();
		COMMON_FPKJ_FPT COMMON_FPKJ_FPT = convertFpt(REQUEST_FPKJXX.getFPKJXX_FPTXX());
		COMMON_FPKJ_XMXX[] COMMON_FPKJ_XMXXS = convertXmxx(REQUEST_FPKJXX.getFPKJXX_XMXXS(),COMMON_FPKJ_FPT.getKPLX());
		
		if(REQUEST_FPKJXX.getFPKJXX_XMXXS()[0].getHSBZ().equals(SystemConfig.HSBZ_HS)){
			double bhse = 0;
			double hjse = 0;
			if(COMMON_FPKJ_XMXXS != null && COMMON_FPKJ_XMXXS.length > 0){
				for(int i = 0; i < COMMON_FPKJ_XMXXS.length; i++){
					//bhse += Double.parseDouble(COMMON_FPKJ_XMXXS[i].getXMJE());
				    bhse = MathUtil.add(COMMON_FPKJ_XMXXS[i].getXMJE(), String.valueOf(bhse));
					//hjse += Double.parseDouble(COMMON_FPKJ_XMXXS[i].getSE());
				    hjse = MathUtil.add(COMMON_FPKJ_XMXXS[i].getSE(), String.valueOf(hjse));
				}
			}
			COMMON_FPKJ_FPT.setHJBHSJE(df.format(bhse));
			COMMON_FPKJ_FPT.setKPHJSE(df.format(hjse));
		}else if(REQUEST_FPKJXX.getFPKJXX_XMXXS()[0].getHSBZ().equals(SystemConfig.HSBZ_BHS)){
			COMMON_FPKJ_FPT.setKPHJSE(REQUEST_FPKJXX.getFPKJXX_FPTXX().getHJSE());
		}
		
		REQUEST_COMMON_FPKJ.setCOMMON_FPKJ_FPT(COMMON_FPKJ_FPT);
		REQUEST_COMMON_FPKJ.setCOMMON_FPKJ_XMXXS(COMMON_FPKJ_XMXXS);
		REQUEST_COMMON_FPKJ.setCOMMON_FPKJ_DDXX(convertDdxx(REQUEST_FPKJXX.getFPKJXX_DDXX()));
		if(REQUEST_FPKJXX.getFPKJXX_ZFXX() != null){
			REQUEST_COMMON_FPKJ.setCOMMON_FPKJ_ZFXX(convertZfxx(REQUEST_FPKJXX.getFPKJXX_ZFXX()));
		}else{
			REQUEST_COMMON_FPKJ.setCOMMON_FPKJ_ZFXX(null);
		}
		if(REQUEST_FPKJXX.getFPKJXX_WLXX() != null){
			REQUEST_COMMON_FPKJ.setCOMMON_FPKJ_WLXX(convertWlxx(REQUEST_FPKJXX.getFPKJXX_WLXX()));
		}else{
			REQUEST_COMMON_FPKJ.setCOMMON_FPKJ_WLXX(null);
		}
		return REQUEST_COMMON_FPKJ;
	}
	/**
	 * @author skye.he
	 * @param FPKJXX_XMXXS 商品行信息
	 * @return FPKJXX_XMXXS 商品行信息
	 * @Date 20150819 created
	 */
	public static FPKJXX_XMXX[] joinOne(FPKJXX_XMXX[] FPKJXX_XMXXS){
		FPKJXX_XMXX[] newXMXXS = null ;
		DecimalFormat df = new DecimalFormat("######0.00");
		df.setRoundingMode(RoundingMode.HALF_UP);
		DecimalFormat djDf = new DecimalFormat("######0.00000000");
		djDf.setRoundingMode(RoundingMode.HALF_UP);
		String XMMC = "",XMDW = "批",SL;
		double XMJE = 0;
		if(FPKJXX_XMXXS != null && FPKJXX_XMXXS.length > 0){
			newXMXXS = new FPKJXX_XMXX[1];
			FPKJXX_XMXX  FPKJXX_XMXX = new FPKJXX_XMXX();
			List<Double> sl = new ArrayList<Double>() ;
			XMMC = FPKJXX_XMXXS[0].getXMMC() ;
			for(int i = 0; i < FPKJXX_XMXXS.length;i++){
				XMJE += Double.valueOf(FPKJXX_XMXXS[i].getXMJE()).doubleValue();
				sl.add(Double.parseDouble(FPKJXX_XMXXS[i].getSL())) ;
			}
			Collections.sort(sl) ;
			SL = df.format(sl.get(sl.size()-1));
			FPKJXX_XMXX.setHSBZ(FPKJXX_XMXXS[0].getHSBZ());
			FPKJXX_XMXX.setXMMC(XMMC) ;
			FPKJXX_XMXX.setXMDW(XMDW);
			FPKJXX_XMXX.setXMSL("1") ;
			FPKJXX_XMXX.setXMDJ(djDf.format(XMJE)) ;
			FPKJXX_XMXX.setXMJE(df.format(XMJE)) ;
			FPKJXX_XMXX.setSL(SL) ;
			FPKJXX_XMXX.setFPHXZ("0");
			FPKJXX_XMXX.setSPBM(FPKJXX_XMXXS[0].getSPBM());
			FPKJXX_XMXX.setZXBM(FPKJXX_XMXXS[0].getZXBM());
			FPKJXX_XMXX.setYHZCBS(FPKJXX_XMXXS[0].getYHZCBS());
			FPKJXX_XMXX.setLSLBS(FPKJXX_XMXXS[0].getLSLBS());
			FPKJXX_XMXX.setZZSTSGL(FPKJXX_XMXXS[0].getZZSTSGL());
			FPKJXX_XMXX.setKCE(FPKJXX_XMXXS[0].getKCE());
			
			newXMXXS[0] = FPKJXX_XMXX;
		}else{
			newXMXXS = new FPKJXX_XMXX[0];
		}
		return newXMXXS;
	}
	
	public static COMMON_FPKJ_FPT convertFpt(FPKJXX_FPTXX FPKJXX_FPTXX){
		COMMON_FPKJ_FPT COMMON_FPKJ_FPT = new COMMON_FPKJ_FPT();
		BeanUtils.copyProperties(FPKJXX_FPTXX, COMMON_FPKJ_FPT);
		COMMON_FPKJ_FPT.setFKF_YHZH(FPKJXX_FPTXX.getGHF_YHZH());
		COMMON_FPKJ_FPT.setGHF_MC(FPKJXX_FPTXX.getGHFMC());
		COMMON_FPKJ_FPT.setGHF_QYLX(FPKJXX_FPTXX.getGHFQYLX());
		COMMON_FPKJ_FPT.setGHF_YX(FPKJXX_FPTXX.getGHF_EMAIL());
		COMMON_FPKJ_FPT.setSKF_YHZH(FPKJXX_FPTXX.getXHF_YHZH());
		COMMON_FPKJ_FPT.setXHF_MC(FPKJXX_FPTXX.getXHFMC());
		return COMMON_FPKJ_FPT;
	}
	
	public static COMMON_FPKJ_XMXX[] convertXmxx(FPKJXX_XMXX[] FPKJXX_XMXXS,String kplx) throws Exception{
		COMMON_FPKJ_XMXX[] COMMON_FPKJ_XMXXS = null;
		DecimalFormat df = new DecimalFormat("######0.00");
		df.setRoundingMode(RoundingMode.HALF_UP);
		DecimalFormat djDf = new DecimalFormat("######0.00000000");
		djDf.setRoundingMode(RoundingMode.HALF_UP);
		DecimalFormat slDf = new DecimalFormat("######0");
		if(FPKJXX_XMXXS != null && FPKJXX_XMXXS.length > 0){
			COMMON_FPKJ_XMXXS = new COMMON_FPKJ_XMXX[FPKJXX_XMXXS.length];
			for(int i = 0; i < FPKJXX_XMXXS.length;i++){
				COMMON_FPKJ_XMXX COMMON_FPKJ_XMXX =new COMMON_FPKJ_XMXX();
				BeanUtils.copyProperties(FPKJXX_XMXXS[i], COMMON_FPKJ_XMXX);
				if(FPKJXX_XMXXS[i].getHSBZ().equals(SystemConfig.HSBZ_HS)){
					if(isMoreZkh(FPKJXX_XMXXS[i]) && getZkl(FPKJXX_XMXXS[i])==1){
						double hjje = 0;
						double se = 0;
						int zkhs = getZkhs(FPKJXX_XMXXS[i]);
						if(i-zkhs>=0){
							for(int k=i-1;k>=0;k--){
								if(zkhs == 0){
									break;
								}
								hjje += Double.valueOf(COMMON_FPKJ_XMXXS[k].getXMJE()).doubleValue();
								se += Double.valueOf(COMMON_FPKJ_XMXXS[k].getSE()).doubleValue();
								zkhs -= 1;
							}
						}
						// 将重新计算的税额 项目金额 单价 注入到折扣行中
                        if(SystemConfig.KPLX_LZFP.equals(kplx)){ // 蓝票
                            // 蓝票 折扣行的 项目金额  税额 为负
                            COMMON_FPKJ_XMXX.setSE("-"+Math.abs(se));
                            COMMON_FPKJ_XMXX.setXMJE("-"+Math.abs(hjje));
                        }else if (SystemConfig.KPLX_HZFP.equals(kplx)) { // 红票
                            // 红票 折扣行的 项目金额  税额  为正
                            COMMON_FPKJ_XMXX.setSE(Math.abs(se)+"");
                            COMMON_FPKJ_XMXX.setXMJE(Math.abs(hjje)+"");
                        }else {
                            throw new Exception();
                        }
                        COMMON_FPKJ_XMXX.setXMDJ(Math.abs(hjje)+"");
					}else {
					    double bhse = MathUtil.div(COMMON_FPKJ_XMXX.getXMJE(), String.valueOf(MathUtil.add("1", COMMON_FPKJ_XMXX.getSL())), 8);
                        double se = MathUtil.sub(COMMON_FPKJ_XMXX.getXMJE(), String.valueOf(bhse));
                        double bhsdj = MathUtil.div(COMMON_FPKJ_XMXX.getXMDJ(), String.valueOf(MathUtil.add("1", COMMON_FPKJ_XMXX.getSL())), 8);
                        COMMON_FPKJ_XMXX.setSE(df.format(se));
                        COMMON_FPKJ_XMXX.setXMJE(df.format(bhse));
                        COMMON_FPKJ_XMXX.setXMDJ(djDf.format(bhsdj));
                    }
				//(Double.parseDouble(COMMON_FPKJ_XMXX.getSL())*100)
				COMMON_FPKJ_XMXX.setSL(slDf.format(MathUtil.mul(COMMON_FPKJ_XMXX.getSL(), "100"))+"%");
				}
				COMMON_FPKJ_XMXXS[i] = COMMON_FPKJ_XMXX;
				/**
				COMMON_FPKJ_XMXX COMMON_FPKJ_XMXX =new COMMON_FPKJ_XMXX();
				BeanUtils.copyProperties(FPKJXX_XMXXS[i], COMMON_FPKJ_XMXX);
				if(FPKJXX_XMXXS[i].getHSBZ().equals(SystemConfig.HSBZ_HS)){
					double bhse = Double.parseDouble(COMMON_FPKJ_XMXX.getXMJE())/(1+Double.parseDouble(COMMON_FPKJ_XMXX.getSL()));
					String bhseStr = df.format(bhse);
					COMMON_FPKJ_XMXX.setSE(df.format(Double.parseDouble(COMMON_FPKJ_XMXX.getXMJE())-Double.parseDouble(bhseStr)));
					COMMON_FPKJ_XMXX.setXMJE(bhseStr);
					COMMON_FPKJ_XMXX.setXMDJ(djDf.format(Double.parseDouble(COMMON_FPKJ_XMXX.getXMDJ())/(1+Double.parseDouble(COMMON_FPKJ_XMXX.getSL()))));
				}
				COMMON_FPKJ_XMXX.setSL(slDf.format((Double.parseDouble(COMMON_FPKJ_XMXX.getSL())*100))+"%");
				COMMON_FPKJ_XMXXS[i] = COMMON_FPKJ_XMXX;
				**/
			}
			return COMMON_FPKJ_XMXXS;
		}else{
			return new COMMON_FPKJ_XMXX[0];
		}
	}
	
	public static COMMON_FPKJ_DDXX convertDdxx(FPKJXX_DDXX FPKJXX_DDXX){
		COMMON_FPKJ_DDXX COMMON_FPKJ_DDXX = new COMMON_FPKJ_DDXX();
		BeanUtils.copyProperties(FPKJXX_DDXX, COMMON_FPKJ_DDXX);
		return COMMON_FPKJ_DDXX;
	}
	
	public static COMMON_FPKJ_ZFXX convertZfxx(FPKJXX_ZFXX FPKJXX_ZFXX){
		COMMON_FPKJ_ZFXX COMMON_FPKJ_ZFXX = new COMMON_FPKJ_ZFXX();
		BeanUtils.copyProperties(FPKJXX_ZFXX, COMMON_FPKJ_ZFXX);
		return COMMON_FPKJ_ZFXX;
	}
	
	public static COMMON_FPKJ_WLXX convertWlxx(FPKJXX_WLXX FPKJXX_WLXX){
		COMMON_FPKJ_WLXX COMMON_FPKJ_WLXX = new COMMON_FPKJ_WLXX();
		BeanUtils.copyProperties(FPKJXX_WLXX, COMMON_FPKJ_WLXX);
		return COMMON_FPKJ_WLXX;
	}
	
	public static FPKJXX_DDMXXX[] convertDdmxxx(FPKJXX_DDMXXX[] FPKJXX_DDMXXXS){
		return FPKJXX_DDMXXXS;
	}
	
	/**
	 * <p>判断是否是折扣行</p>
	 * 
	 * @param FPKJXX_XMXX
	 * @return boolean
	 * @author: jerome.wang
	 * @date: Created on 2015-8-10 上午10:10:28
	 */
	private static boolean isZkh(FPKJXX_XMXX FPKJXX_XMXX){
		if(FPKJXX_XMXX.getXMMC().indexOf("折扣") != -1 || FPKJXX_XMXX.getXMMC().indexOf("折扣行数") != -1){
			return true;
		}
		return false;
	}
	
	
	/**
	 * <p>判断是否是多折扣行</p>
	 * 
	 * @param FPKJXX_XMXX
	 * @return boolean
	 * @author: jerome.wang
	 * @date: Created on 2015-8-10 上午10:53:06
	 */
	private static boolean isMoreZkh(FPKJXX_XMXX FPKJXX_XMXX){
		if(FPKJXX_XMXX.getXMMC().indexOf("折扣行数") != -1){
			return true;
		}
		return false;
	}
	
	/**
	 * <p>获取折扣行数</p>
	 * 
	 * @param FPKJXX_XMXX
	 * @return
	 * @throws Exception int
	 * @author: jerome.wang
	 * @date: Created on 2015-8-10 上午10:55:05
	 */
	private static int getZkhs(FPKJXX_XMXX FPKJXX_XMXX) throws Exception{
		String xmmc = FPKJXX_XMXX.getXMMC();
		String zkhsStr = xmmc.substring(xmmc.indexOf("折扣行数")+4, xmmc.indexOf("("));   //获取折扣行数
		if(null == zkhsStr || "".equals(zkhsStr)){
			throw new Exception("未能找到折扣行数");
		}else{
			int zkhs = Integer.valueOf(zkhsStr).intValue();
			if(zkhs == 0){
				throw new Exception("折扣行数不能为0");
			}
			return zkhs;
		}
	}
	
	/**
	 * <p>获取税率</p>
	 * 
	 * @param FPKJXX_XMXX
	 * @return
	 * @throws Exception double
	 * @author: jerome.wang
	 * @date: Created on 2015-8-10 上午10:53:23
	 */
	private static double getZkl(FPKJXX_XMXX FPKJXX_XMXX) throws Exception{
		String xmmc = FPKJXX_XMXX.getXMMC();
		String zklStr = xmmc.substring(xmmc.indexOf("(")+1, xmmc.indexOf(")")).replace("%", "");  //获取折扣率
		zklStr = String.valueOf(Double.parseDouble(zklStr)/100);  //折扣率除以100
		if(null == zklStr || "".equals(zklStr)){
			throw new Exception("未能找到折扣率");
		}else{
			return Double.parseDouble(zklStr);
		}
	}
	
	/**
	 * 
	 * <p>发票冲红时去除明细中100%折扣的商品行</p>
	 * 
	 * @param REQUEST_FPKJXX
	 * @return com.aisino.protocol.bean.REQUEST_FPKJXX
	 * @author: 阳开国
	 * @throws Exception 
	 * @date: Created on 2016-10-20 下午1:41:52
	 */
    public static REQUEST_FPKJXX removeFullDiscount(REQUEST_FPKJXX REQUEST_FPKJXX) throws Exception {
        REQUEST_FPKJXX REQUEST_FPKJXX_TEMP = REQUEST_FPKJXX;
        FPKJXX_XMXX[] FPKJXX_XMXXS = REQUEST_FPKJXX_TEMP.getFPKJXX_XMXXS();
        List<FPKJXX_XMXX> temp = new LinkedList<FPKJXX_XMXX>();
        Collections.addAll(temp, FPKJXX_XMXXS);
        int zkhs = 0 ;
        double zkl = 1 ;
        if (temp != null && temp.size() > 0) {
            for (int i = 0; i < temp.size(); i++) {
                if (("1".equals(temp.get(i).getFPHXZ()) && temp.get(i).getXMMC().equals(temp.get(i-1).getXMMC())) || temp.get(i).getXMMC().startsWith("折扣(100.000%)")  ) {
                    int j = i - 1 ;
                    //折扣行金额
                    double zkhje =  Math.abs(Double.parseDouble(temp.get(i).getXMJE()));
                    //被折扣行金额
                    double bzkhje = Math.abs(Double.parseDouble(temp.get(i-1).getXMJE()));
                    double differ = MathUtil.sub(String.valueOf(bzkhje), String.valueOf(zkhje)) ;
                    if (0.00 == differ) {
                        temp.remove(i);
                        temp.remove(i-1);
                        i = j ;
                        i = i -1 ;
                        continue ;
                    }
                }
                
                if(temp.get(i).getXMMC().contains("折扣行数")) {
                    FPKJXX_XMXX fpkjxx_XMXX = temp.get(i);
                    zkhs = getZkhs(fpkjxx_XMXX);
                    zkl = getZkl(fpkjxx_XMXX);
                    int k = i - (zkhs + 1) ;
                    if (1.0 == zkl) {
                        for (int j = i; j >= i - zkhs; j--) {
                            temp.remove(j);
                        }
                        i = k  ;
                    }
                }
            }

        }
        FPKJXX_XMXXS = (FPKJXX_XMXX[]) temp.toArray(new FPKJXX_XMXX[temp.size()]) ;
        REQUEST_FPKJXX_TEMP.setFPKJXX_XMXXS(FPKJXX_XMXXS);
        return REQUEST_FPKJXX_TEMP;
    }
}
