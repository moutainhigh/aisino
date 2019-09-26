package com.aisino.einvoice.service.impl;

import static com.aisino.domain.SystemConfig.invoiceNote;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.aisino.common.util.MathUtil;
import com.aisino.common.util.ValidateUtil;
import com.aisino.common.util.XmlPar;
import com.aisino.domain.SystemConfig;
import com.aisino.domain.einvoice.entity.InvoiceHeaderEntity;
import com.aisino.domain.einvoice.entity.TaxpayerEntity;
import com.aisino.domain.einvoice.repository.EInvoiceSubRepository;
import com.aisino.einvoice.service.IInvoiceDataCheckService;
import com.aisino.protocol.bean.COMMON_FPKJ_FPT;
import com.aisino.protocol.bean.COMMON_FPKJ_XMXX;
import com.aisino.protocol.bean.REQUEST_COMMON_FPKJ;
import com.mysql.jdbc.StringUtils;

/**
 * 
 * <p>[描述信息：发票数据校验实现服务]</p>
 * 
 * @author lichunhui lichunhui1314@126.com
 * @version 1.0 Created on 2015-8-4 下午08:30:37
 */
@Service("invoiceDataCheckService")
public class InvoiceDataCheckServiceImpl implements IInvoiceDataCheckService {

	private final static Logger LOGGER = LoggerFactory.getLogger(InvoiceDataCheckServiceImpl.class);
	
	/**
	 * 
	 * <p>发票数据校验</p>
	 * <p>1.非空校验</p>
	 * <p>2.数据长度校验</p>
	 * <p>3.发票数据校验</p>
	 * 
	 * @see com.aisino.einvoice.service.IInvoiceDataCheckService#checkInvParam(com.aisino.protocol.bean.REQUEST_COMMON_FPKJ, com.aisino.protocol.bean.COMMON_FPKJ_FPT, com.aisino.protocol.bean.COMMON_FPKJ_XMXX[], com.aisino.domain.einvoice.repository.EInvoiceSubRepository)
	 * @author: lichunhui lichunhui1314@126.com
	 * @throws Exception 
	 * @date: Created on 2015-8-5 上午10:36:20
	 */
	@Override
	public Map<String, String> checkInvParam(REQUEST_COMMON_FPKJ REQUEST_COMMON_FPKJ,
			COMMON_FPKJ_FPT COMMON_FPKJ_FPT, COMMON_FPKJ_XMXX[] fpkjxx_xmxxs,EInvoiceSubRepository repository) throws Exception {

		Map<String, String> checkResultMap = null; // 声明校验结果map
		
		// 1.非空校验
		checkResultMap = this.checkEmptyData(REQUEST_COMMON_FPKJ,COMMON_FPKJ_FPT, fpkjxx_xmxxs, repository);
		if (!XmlPar.RESPONSEYYSSUCCESS.equals(checkResultMap.get(XmlPar.ERRORCODE))) {
			return checkResultMap;
		}
		// 2.纳税人状态和平台信息校验,行业名称和代码校验
        checkResultMap = this.checkAuthority(REQUEST_COMMON_FPKJ,COMMON_FPKJ_FPT, fpkjxx_xmxxs, repository);
        if (!XmlPar.RESPONSEYYSSUCCESS.equals(checkResultMap.get(XmlPar.ERRORCODE))) {
            return checkResultMap;
        }
		
		// 3.数据长度校验
		checkResultMap = this.checkDataLength(REQUEST_COMMON_FPKJ,COMMON_FPKJ_FPT, fpkjxx_xmxxs, repository);
		if (!XmlPar.RESPONSEYYSSUCCESS.equals(checkResultMap.get(XmlPar.ERRORCODE))) {
			return checkResultMap;
		}

		// 4.发票数据校验
		checkResultMap = this.checkInvoiceData(REQUEST_COMMON_FPKJ,COMMON_FPKJ_FPT, fpkjxx_xmxxs, repository);
		if (!XmlPar.RESPONSEYYSSUCCESS.equals(checkResultMap.get(XmlPar.ERRORCODE))) {
			return checkResultMap;
		}

		return checkResultMap;
	}

	/**
	 * 
	 * <p>发票数据校验（蓝票和红票数据校验）</p>
	 * 
	 * @param REQUEST_COMMON_FPKJ
	 * @param COMMON_FPKJ_FPT
	 * @param fpkjxx_xmxxs
	 * @param repository
	 * @return Map<String,String>
	 * @author: lichunhui lichunhui1314@126.com
	 * @throws Exception 
	 * @date: Created on 2015-8-5 上午01:39:31
	 */
    private Map<String, String> checkInvoiceData(
            REQUEST_COMMON_FPKJ REQUEST_COMMON_FPKJ,
            COMMON_FPKJ_FPT COMMON_FPKJ_FPT, COMMON_FPKJ_XMXX[] fpkjxx_xmxxs,
            final EInvoiceSubRepository repository) throws Exception {

		Map<String, String> checkResultMap = new HashMap<String, String>();
		checkResultMap.put(XmlPar.ERRORCODE, XmlPar.RESPONSEYYSSUCCESS);

		// 发票数据校验（即蓝票和红票共有的校验）
		checkResultMap = this.checkCommonInvoiceData(REQUEST_COMMON_FPKJ, COMMON_FPKJ_FPT, fpkjxx_xmxxs, repository);
		if (!XmlPar.RESPONSEYYSSUCCESS.equals(checkResultMap.get(XmlPar.ERRORCODE))) {
			return checkResultMap;
		}
		
		if ("1".equals(COMMON_FPKJ_FPT.getKPLX())) { // 蓝票数据校验
			return this.checkBlueInvoiceData(REQUEST_COMMON_FPKJ, COMMON_FPKJ_FPT, fpkjxx_xmxxs, repository);
		}else if ("2".equals(COMMON_FPKJ_FPT.getKPLX())) { // 红票数据校验
			return this.checkRedInvoiceData(REQUEST_COMMON_FPKJ, COMMON_FPKJ_FPT, fpkjxx_xmxxs, repository);
		} else {
			checkResultMap = generateErrorMap(COMMON_FPKJ_FPT,XmlPar.ERRORCODE_DATA_FPT_KPLX,"发票开具类型错误！！");
			LOGGER.error(checkResultMap.get(XmlPar.ERRORMSG));
			return checkResultMap;
		}
	}

	/**
	 * 
	 * <p>相同发票数据校验（不区分红票和蓝票）</p>
	 * 
	 * @param REQUEST_COMMON_FPKJ
	 * @param COMMON_FPKJ_FPT
	 * @param fpkjxx_xmxxs
	 * @param repository
	 * @return Map<String,String>
	 * @author: lichunhui lichunhui1314@126.com
	 * @date: Created on 2015-8-4 下午09:46:14
	 */
	private Map<String, String> checkCommonInvoiceData(REQUEST_COMMON_FPKJ REQUEST_COMMON_FPKJ,
			COMMON_FPKJ_FPT COMMON_FPKJ_FPT, COMMON_FPKJ_XMXX[] fpkjxx_xmxxs,final EInvoiceSubRepository repository) {

		Map<String, String> checkResultMap = new HashMap<String, String>();
		checkResultMap.put(XmlPar.ERRORCODE, XmlPar.RESPONSEYYSSUCCESS);
		
		// 判断购货方企业类型是否正确
		// 01:企业 02：机关事业单位 03：个人 04：其它
		if (!"01".equals(COMMON_FPKJ_FPT.getGHF_QYLX())
				&& !"02".equals(COMMON_FPKJ_FPT.getGHF_QYLX())
				&& !"03".equals(COMMON_FPKJ_FPT.getGHF_QYLX())
				&& !"04".equals(COMMON_FPKJ_FPT.getGHF_QYLX())) {
			checkResultMap = generateErrorMap(COMMON_FPKJ_FPT,XmlPar.ERRORCODE_DATA_FPT_GHFQYLX,"购货方企业类型错误！！");
			LOGGER.error(checkResultMap.get(XmlPar.ERRORMSG));
			return checkResultMap;
		}
		
		// 判断开票类型是否正确 1 正数发票 2红字发票
		if (!"1".equals(COMMON_FPKJ_FPT.getKPLX())
				&& !"2".equals(COMMON_FPKJ_FPT.getKPLX())) {
			checkResultMap = generateErrorMap(COMMON_FPKJ_FPT,XmlPar.ERRORCODE_DATA_FPT_KPLX,"发票开具类型错误！！");
			LOGGER.error(checkResultMap.get(XmlPar.ERRORMSG));
			return checkResultMap;
		}
		
		// 判断开票模式（DKBZ）是否正确  自开(0)代开(1)
        if (!"0".equals(COMMON_FPKJ_FPT.getDKBZ())
                && !"1".equals(COMMON_FPKJ_FPT.getDKBZ())) {
            checkResultMap = generateErrorMap(COMMON_FPKJ_FPT,XmlPar.ERRORCODE_DATA_FPT_DKBZ,"开票模式错误 自开(0)代开(1)！！");
            LOGGER.error(checkResultMap.get(XmlPar.ERRORMSG));
            return checkResultMap;
        }
		
		// 判断开票类型是否正确 10正票正常开具 11正票错票重开 20 退货折让红票、21 错票重开红票、22换票冲红
        if ("1".equals(COMMON_FPKJ_FPT.getKPLX())
                && !"10".equals(COMMON_FPKJ_FPT.getCZDM())
                && !"11".equals(COMMON_FPKJ_FPT.getCZDM())){
            checkResultMap = generateErrorMap(COMMON_FPKJ_FPT,XmlPar.ERRORCODE_DATA_FPT_CZDM,"蓝票发票开具操作代码类型错误！！");
            LOGGER.error(checkResultMap.get(XmlPar.ERRORMSG));
            return checkResultMap;
        }
		
        if ("2".equals(COMMON_FPKJ_FPT.getKPLX())
                && !"20".equals(COMMON_FPKJ_FPT.getCZDM())
                && !"21".equals(COMMON_FPKJ_FPT.getCZDM())
                && !"22".equals(COMMON_FPKJ_FPT.getCZDM())) {
			checkResultMap = generateErrorMap(COMMON_FPKJ_FPT,XmlPar.ERRORCODE_DATA_FPT_CZDM,"红票发票开具操作代码类型错误！！");
			LOGGER.error(checkResultMap.get(XmlPar.ERRORMSG));
			return checkResultMap;
		}
        //殊冲红标志应限定为0或1 2016-8-9 14:04:19 阳开国
        if (!"0".equals(COMMON_FPKJ_FPT.getTSCHBZ())
                && !"1".equals(COMMON_FPKJ_FPT.getTSCHBZ())) {
            checkResultMap = generateErrorMap(COMMON_FPKJ_FPT,XmlPar.ERRORCODE_DATA_FPT_TSCHBZ,"殊冲红标志类型错误,0正常冲红(电子发票) 1特殊冲红(冲红纸质等)，在支持特殊冲红之前默认为”0”！！");
            LOGGER.error(checkResultMap.get(XmlPar.ERRORMSG));
            return checkResultMap;
        }

//        //最大开票限额校验 2016-11-1 上午10:05:12
//        if (!StringUtils.isNullOrEmpty(COMMON_FPKJ_FPT.getNSRSBH()) && !StringUtils.isNullOrEmpty(COMMON_FPKJ_FPT.getKPHJJE()) ) {
//            if (checkMaxLimitAmount(COMMON_FPKJ_FPT)) {
//                checkResultMap = generateErrorMap(COMMON_FPKJ_FPT,XmlPar.ERRORCODE_DATA_FPT_KPHJJE,"发票开具:开票合计金额超限或者该纳税人识别号未在最大开票限额的配置！");
//                LOGGER.error(checkResultMap.get(XmlPar.ERRORMSG));
//                return checkResultMap;
//            }
//        }
		
        /**
         * 添加发票头信息的数据校验:
         * 发票头信息中的:合计税额+合计不含税金额=开票合计金额
         */
        //如果发票头信息的开票合计金额,合计税额,合计不含税金额不为空,则执行校验业务
        if(!StringUtils.isNullOrEmpty(COMMON_FPKJ_FPT.getKPHJJE()) && !StringUtils.isNullOrEmpty(COMMON_FPKJ_FPT.getKPHJSE()) && !StringUtils.isNullOrEmpty(COMMON_FPKJ_FPT.getHJBHSJE())){
            if(checkKphjje(COMMON_FPKJ_FPT,fpkjxx_xmxxs)){
                checkResultMap = generateErrorMap(COMMON_FPKJ_FPT,XmlPar.ERRORCODE_DATA_FPT,"发票开具:合计税额+合计不含税金额不等于开票合计金额！");
                LOGGER.error(checkResultMap.get(XmlPar.ERRORMSG));
                return checkResultMap;
            }
        }
		
		
		for (int i = 0; i < fpkjxx_xmxxs.length; i++) {
			COMMON_FPKJ_XMXX fpkjxx_xmxx = fpkjxx_xmxxs[i];
			// 零税率标识做校验（空：非零税率，0:出口零税率， 1：免税，2：不征收，3普通零税率）2016-8-9 10:47:59 阳开国
            if (!"".equals(fpkjxx_xmxx.getLSLBS())
                    && !"0".equals(fpkjxx_xmxx.getLSLBS())
                    && !"1".equals(fpkjxx_xmxx.getLSLBS())
                    && !"2".equals(fpkjxx_xmxx.getLSLBS())
                    && !"3".equals(fpkjxx_xmxx.getLSLBS())) {
	            checkResultMap = generateErrorMap(COMMON_FPKJ_FPT,XmlPar.ERRORCODE_DATA_MXXX_LSLBS,"零税率标识错误（空：非零税率，0：出口零税率， 1：免税，2：不征收，3普通零税率）！！");
	            LOGGER.error(checkResultMap.get(XmlPar.ERRORMSG));
	            return checkResultMap;
	        }
            //优惠政策标识限定为0：不使用，1：使用 2016-8-9 10:55:55 阳开国
            if (!"0".equals(fpkjxx_xmxx.getYHZCBS())
                    && !"1".equals(fpkjxx_xmxx.getYHZCBS())) {
                checkResultMap = generateErrorMap(COMMON_FPKJ_FPT,XmlPar.ERRORCODE_DATA_MXXX_YHZCBS,"优惠政策标识错误（优惠政策标识限定为0：不使用，1：使用）！！");
                LOGGER.error(checkResultMap.get(XmlPar.ERRORMSG));
                return checkResultMap;
            }
            
		}
		
		return checkResultMap;
	}

	/**
	 * 
	 * <p>检查开票合计金额是否超限</p>
	 * 
	 * @param cOMMON_FPKJ_FPT
	 * @return boolean
	 * @author: 阳开国
	 * @date: Created on 2016-11-1 上午10:05:12
	 */
   /* private boolean checkMaxLimitAmount(COMMON_FPKJ_FPT COMMON_FPKJ_FPT) {
        final String nsrsbh = COMMON_FPKJ_FPT.getNSRSBH();
        String kphjje = COMMON_FPKJ_FPT.getKPHJJE();
        if (SystemConfig.maxLimitMap.containsKey(nsrsbh)) {
            String kphjjeTemp = SystemConfig.maxLimitMap.get(nsrsbh);
            double differ = MathUtil.sub(kphjjeTemp, kphjje);
            if (differ < 0) {
                return true;// 超限额
            } else {
                return false;// 未超限额
            }
        } else {
            return true;// 该税号没有在最大开票限额配置文件中配置
        }
    }*/

    /**
	 * 
	 * <p>检查空数据(即非空校验)</p>
	 * 
	 * @param REQUEST_COMMON_FPKJ
	 * @param COMMON_FPKJ_FPT
	 * @param fpkjxx_xmxxs
	 * @param repository
	 * @return Map<String,String>
	 * @author: lichunhui lichunhui1314@126.com
	 * @date: Created on 2015-8-5 上午01:39:13
	 */
	private Map<String, String> checkEmptyData(REQUEST_COMMON_FPKJ REQUEST_COMMON_FPKJ,
			COMMON_FPKJ_FPT COMMON_FPKJ_FPT, COMMON_FPKJ_XMXX[] fpkjxx_xmxxs,final EInvoiceSubRepository repository) {

		Map<String, String> checkResultMap = new HashMap<String, String>();
		checkResultMap.put(XmlPar.ERRORCODE, XmlPar.RESPONSEYYSSUCCESS); // 校验前，默认为0000，表示校验成功，后续校验不通过

		if (REQUEST_COMMON_FPKJ == null) {
			checkResultMap = generateErrorMap(XmlPar.RESPONSEFAIL,"发票开具请求数据格式不正确,REQUEST_COMMON_FPKJ节点为空！");
			LOGGER.error(checkResultMap.get(XmlPar.ERRORMSG));
			return checkResultMap;
		}

		if (COMMON_FPKJ_FPT == null) {
			checkResultMap = generateErrorMap(XmlPar.RESPONSEFAIL,"发票开具请求数据格式不正确,COMMON_FPKJ_FPT节点为空！");
			LOGGER.error(checkResultMap.get(XmlPar.ERRORMSG));
			return checkResultMap;
		}
		
		if (fpkjxx_xmxxs == null) {
			checkResultMap = generateErrorMap(COMMON_FPKJ_FPT,XmlPar.ERRORCODE_DATA_FPMXXXISEXIST,"发票开具明细信息不能为空！");
			LOGGER.error(checkResultMap.get(XmlPar.ERRORMSG));
			return checkResultMap;
		}

		if (ValidateUtil.checkParameterIsEmpty(COMMON_FPKJ_FPT.getFPQQLSH())) {
			checkResultMap = generateErrorMap(COMMON_FPKJ_FPT,XmlPar.ERRORCODE_DATA_FPT_FPQQLSH,"发票开票发票请求唯一流水号不能为空");
			LOGGER.error(checkResultMap.get(XmlPar.ERRORMSG));
			return checkResultMap;
		}

		if (ValidateUtil.checkParameterIsEmpty(COMMON_FPKJ_FPT.getNSRSBH())) {
			checkResultMap = generateErrorMap(COMMON_FPKJ_FPT,XmlPar.ERRORCODE_DATA_FPT_NSRSBH,"发票开具纳税人识别号不能为空！");
			LOGGER.error(checkResultMap.get(XmlPar.ERRORMSG));
			return checkResultMap;
		}
		
		if (ValidateUtil.checkParameterIsEmpty(COMMON_FPKJ_FPT.getNSRMC())) {
			checkResultMap = generateErrorMap(COMMON_FPKJ_FPT,XmlPar.ERRORCODE_DATA_FPT_NSRMC,"发票开具纳税人名称不能为空");
			LOGGER.error(checkResultMap.get(XmlPar.ERRORMSG));
			return checkResultMap;
		}

		if (ValidateUtil.checkParameterIsEmpty(COMMON_FPKJ_FPT.getDKBZ())) {
			checkResultMap = generateErrorMap(COMMON_FPKJ_FPT,XmlPar.ERRORCODE_DATA_FPT_DKBZ,"发票开票模式不能为空");
			LOGGER.error(checkResultMap.get(XmlPar.ERRORMSG));
			return checkResultMap;
		}

		if (ValidateUtil.checkParameterIsEmpty(COMMON_FPKJ_FPT.getKPXM())) {
			checkResultMap = generateErrorMap(COMMON_FPKJ_FPT,XmlPar.ERRORCODE_DATA_FPT_KPXM,"发票开具主开票项目不能为空");
			LOGGER.error(checkResultMap.get(XmlPar.ERRORMSG));
			return checkResultMap;
		}
		
		if (ValidateUtil.checkParameterIsEmpty(COMMON_FPKJ_FPT.getXHF_MC())) {
			checkResultMap = generateErrorMap(COMMON_FPKJ_FPT,XmlPar.ERRORCODE_DATA_FPT_XHFMC,"发票销货方名称不能为空");
			LOGGER.error(checkResultMap.get(XmlPar.ERRORMSG));
			return checkResultMap;
		}
		
		if (ValidateUtil.checkParameterIsEmpty(COMMON_FPKJ_FPT.getXHF_DZ())) {
			checkResultMap = generateErrorMap(COMMON_FPKJ_FPT,XmlPar.ERRORCODE_DATA_FPT_XHFDZ,"发票销货方地址不能为空");
			LOGGER.error(checkResultMap.get(XmlPar.ERRORMSG));
			return checkResultMap;
		}
		
		if (ValidateUtil.checkParameterIsEmpty(COMMON_FPKJ_FPT.getXHF_DH())) {
			checkResultMap = generateErrorMap(COMMON_FPKJ_FPT,XmlPar.ERRORCODE_DATA_FPT_XHFDH,"发票销货方电话不能为空");
			LOGGER.error(checkResultMap.get(XmlPar.ERRORMSG));
			return checkResultMap;
		}

		if (ValidateUtil.checkParameterIsEmpty(COMMON_FPKJ_FPT.getGHF_MC())) {
			checkResultMap = generateErrorMap(COMMON_FPKJ_FPT,XmlPar.ERRORCODE_DATA_FPT_GHFMC,"发票开具购货方名称不能为空");
			LOGGER.error(checkResultMap.get(XmlPar.ERRORMSG));
			return checkResultMap;
		}
		
		/*if (ValidateUtil.checkParameterIsEmpty(COMMON_FPKJ_FPT.getGHF_SJ())) {
			checkResultMap = generateErrorMap(COMMON_FPKJ_FPT,XmlPar.ERRORCODE_DATA_FPT_GHF_SJ,"购货方手机号不能为空");
			LOGGER.error(checkResultMap.get(XmlPar.ERRORMSG));
			return checkResultMap;
		}*/
		
		// 判断购货方企业类型是否正确
		if (ValidateUtil.checkParameterIsEmpty(COMMON_FPKJ_FPT.getGHF_QYLX())) {
			checkResultMap = generateErrorMap(COMMON_FPKJ_FPT,XmlPar.ERRORCODE_DATA_FPT_GHFQYLX,"购货方企业类型不能为空！！");
			LOGGER.error(checkResultMap.get(XmlPar.ERRORMSG));
			return checkResultMap;
		}
		
		if (ValidateUtil.checkParameterIsEmpty(COMMON_FPKJ_FPT.getKPY())) {
			checkResultMap = generateErrorMap(COMMON_FPKJ_FPT,XmlPar.ERRORCODE_DATA_FPT_KPY, "开票员不能为空");
			LOGGER.error(checkResultMap.get(XmlPar.ERRORMSG));
			return checkResultMap;
		}
		
		if (ValidateUtil.checkParameterIsEmpty(COMMON_FPKJ_FPT.getKPLX())) {
			checkResultMap = generateErrorMap(COMMON_FPKJ_FPT,XmlPar.ERRORCODE_DATA_FPT_KPLX,"发票开票类型不能为空");
			LOGGER.error(checkResultMap.get(XmlPar.ERRORMSG));
			return checkResultMap;
		}
		
		if (ValidateUtil.checkParameterIsEmpty(COMMON_FPKJ_FPT.getKPHJJE())) {
			checkResultMap = generateErrorMap(COMMON_FPKJ_FPT,XmlPar.ERRORCODE_DATA_FPT_KPHJJE,"发票开票合计金额不能为空");
			LOGGER.error(checkResultMap.get(XmlPar.ERRORMSG));
			return checkResultMap;
		}
		
		if (ValidateUtil.checkParameterIsEmpty(REQUEST_COMMON_FPKJ.getCOMMON_FPKJ_DDXX().getDDH())) {
			checkResultMap = generateErrorMap(COMMON_FPKJ_FPT,XmlPar.ERRORCODE_DATA_FPT_DDH, "订单号码不能为空");
			LOGGER.error(checkResultMap.get(XmlPar.ERRORMSG));
			return checkResultMap;
		}
		//编码表版本号不能为空
		if (ValidateUtil.checkParameterIsEmpty(REQUEST_COMMON_FPKJ.getCOMMON_FPKJ_FPT().getBMB_BBH())) {
		    checkResultMap = generateErrorMap(COMMON_FPKJ_FPT,XmlPar.ERRORCODE_DATA_FPT_BMB_BBH, "编码表版本号不能为空");
            LOGGER.error(checkResultMap.get(XmlPar.ERRORMSG));
            return checkResultMap;
        }
		
		//开票类型为2时原发票代码号码必填 2016-8-9 09:17:05 阳开国
        if ("2".equals(COMMON_FPKJ_FPT.getKPLX())) {
            if (ValidateUtil.checkParameterIsEmpty(COMMON_FPKJ_FPT.getYFP_DM())) {
                checkResultMap = generateErrorMap(COMMON_FPKJ_FPT,XmlPar.ERRORCODE_DATA_FPT_YFP_DM, "开票类型为2时原发票代码不能为空");
                LOGGER.error(checkResultMap.get(XmlPar.ERRORMSG));
                return checkResultMap;
            }
            
            if (ValidateUtil.checkParameterIsEmpty(COMMON_FPKJ_FPT.getYFP_HM())) {
                checkResultMap = generateErrorMap(COMMON_FPKJ_FPT,XmlPar.ERRORCODE_DATA_FPT_YFP_HM, "开票类型为2时原发票号码不能为空");
                LOGGER.error(checkResultMap.get(XmlPar.ERRORMSG));
                return checkResultMap;
            }
            //开票类型为2时特殊冲红标识必填
            if (ValidateUtil.checkParameterIsEmpty(COMMON_FPKJ_FPT.getTSCHBZ())) {
                checkResultMap = generateErrorMap(COMMON_FPKJ_FPT,XmlPar.ERRORCODE_DATA_FPT_TSCHBZ, "开票类型为2时特殊冲红标识不能为空");
                LOGGER.error(checkResultMap.get(XmlPar.ERRORMSG));
                return checkResultMap;
            }
            //开票类型为2时冲红原因必填
            if (ValidateUtil.checkParameterIsEmpty(COMMON_FPKJ_FPT.getCHYY())) {
                checkResultMap = generateErrorMap(COMMON_FPKJ_FPT,XmlPar.ERRORCODE_DATA_FPT_CHYY, "开票类型为2时冲红原因不能为空");
                LOGGER.error(checkResultMap.get(XmlPar.ERRORMSG));
                return checkResultMap;
            }
            
        }
        
		
		// 判断发票开具明细信息是否为空
		for (int i = 0; i < fpkjxx_xmxxs.length; i++) {
			COMMON_FPKJ_XMXX fpkjxx_xmxx = fpkjxx_xmxxs[i];
			if (ValidateUtil.checkParameterIsEmpty(fpkjxx_xmxx.getXMMC())) {
				checkResultMap = generateErrorMap(COMMON_FPKJ_FPT,XmlPar.ERRORCODE_DATA_MXXX_XMMC,"开具发票信息明细中项目名称不能为空");
				LOGGER.error(checkResultMap.get(XmlPar.ERRORMSG));
				return checkResultMap;
			}
			
			//  发票明细行(FPHXZ)非空校验 2016-7-5 18:27:10   阳开国
            if (ValidateUtil.checkParameterIsEmpty(fpkjxx_xmxx.getFPHXZ())) {
                checkResultMap = generateErrorMap(COMMON_FPKJ_FPT,XmlPar.ERRORCODE_DATA_MXXX_FPHXZ,"开具发票信息明细中发票行性质不能为空");
                LOGGER.error(checkResultMap.get(XmlPar.ERRORMSG));
                return checkResultMap;
            }
			
			/**
			 *  商品行的 数量 和 单价不能为空
			 */
			if(!("1".equals(fpkjxx_xmxx.getFPHXZ()))){ // 商品行(根据发票行性质来判断)
				if (ValidateUtil.checkParameterIsEmpty(fpkjxx_xmxx.getXMDJ())) {
					checkResultMap = generateErrorMap(COMMON_FPKJ_FPT,XmlPar.ERRORCODE_DATA_MXXX_XMDJ,"开具发票信息明细中项目单价不能为空");
					LOGGER.error(checkResultMap.get(XmlPar.ERRORMSG));
					return checkResultMap;
				}
				
				if (ValidateUtil.checkParameterIsEmpty(fpkjxx_xmxx.getXMSL())) {
					checkResultMap = generateErrorMap(COMMON_FPKJ_FPT,XmlPar.ERRORCODE_DATA_MXXX_XMDJ,"开具发票信息明细中项目数量不能为空");
					LOGGER.error(checkResultMap.get(XmlPar.ERRORMSG));
					return checkResultMap;
				}
			}else {
				// 折扣行
			}
			
			if (ValidateUtil.checkParameterIsEmpty(fpkjxx_xmxx.getXMJE())) {
				checkResultMap = generateErrorMap(COMMON_FPKJ_FPT,XmlPar.ERRORCODE_DATA_MXXX_XMJE,"开具发票信息明细中项目金额不能为空");
				LOGGER.error(checkResultMap.get(XmlPar.ERRORMSG));
				return checkResultMap;
			}
			
			if (ValidateUtil.checkParameterIsEmpty(fpkjxx_xmxx.getSL())) {
				checkResultMap = generateErrorMap(COMMON_FPKJ_FPT,XmlPar.ERRORCODE_DATA_MXXX_SL,"开具发票信息明细中税率不能为空");
				LOGGER.error(checkResultMap.get(XmlPar.ERRORMSG));
				return checkResultMap;
			}
			
			 //发票明细行(SPBM,YHZCBS)非空校验 2016-7-5 18:27:10   阳开国
			if (!("2".equals(COMMON_FPKJ_FPT.getKPLX()) && (fpkjxx_xmxx.getXMMC().startsWith("折扣行数") || fpkjxx_xmxx.getXMMC().startsWith("折扣")) && ("1".equals(fpkjxx_xmxx.getFPHXZ())) )){
			    if (ValidateUtil.checkParameterIsEmpty(fpkjxx_xmxx.getSPBM())) {
	                checkResultMap = generateErrorMap(COMMON_FPKJ_FPT,XmlPar.ERRORCODE_DATA_MXXX_SPBM,"开具发票信息明细中商品编码不能为空");
	                LOGGER.error(checkResultMap.get(XmlPar.ERRORMSG));
	                return checkResultMap;
	            }
            }
			////发票明细行(YHZCBS)非空校验 2016-7-5 18:27:10   阳开国
			if (ValidateUtil.checkParameterIsEmpty(fpkjxx_xmxx.getYHZCBS())) {
                checkResultMap = generateErrorMap(COMMON_FPKJ_FPT,XmlPar.ERRORCODE_DATA_MXXX_YHZCBS,"开具发票信息明细中优惠政策标识不能为空");
                LOGGER.error(checkResultMap.get(XmlPar.ERRORMSG));
                return checkResultMap;  
            }
			
			//当 YHZCBS 是 1 时对增值税特殊管理（ZZSTSGL）进行非空校验 2016-7-21 09:20:39 阳开国
			if ("1".equals(fpkjxx_xmxx.getYHZCBS())) {
			    if (ValidateUtil.checkParameterIsEmpty(fpkjxx_xmxx.getZZSTSGL())) {
	                checkResultMap = generateErrorMap(COMMON_FPKJ_FPT,XmlPar.ERRORCODE_DATA_MXXX_ZZSTSGL,"当 YHZCBS 是 1 时增值税特殊管理（ZZSTSGL）必填");
	                LOGGER.error(checkResultMap.get(XmlPar.ERRORMSG));
	                return checkResultMap;
	            }
            }
			
            // 当 HSBS是0(不含税)时税额（SE）进行非空校验 2016-7-21 09:20:39 阳开国    (因为如果含税标识为1<含税>在前置需要做价税分离，税额不可能为空，这里只能是含税标识为0的情况)
            if (ValidateUtil.checkParameterIsEmpty(fpkjxx_xmxx.getSE())) {
                checkResultMap = generateErrorMap(COMMON_FPKJ_FPT,XmlPar.ERRORCODE_DATA_MXXX_SE,"当 HSBS 是 0 税额（ SE ）必填");
                LOGGER.error(checkResultMap.get(XmlPar.ERRORMSG));
                return checkResultMap;
            }
			
			
		}
		
		return checkResultMap;
	}

	/**
	 * 
	 * <p>检查数据长度(即数据长度校验)</p>
	 * 
	 * @param REQUEST_COMMON_FPKJ
	 * @param COMMON_FPKJ_FPT
	 * @param fpkjxx_xmxxs
	 * @param repository
	 * @return Map<String,String>
	 * @author: lichunhui lichunhui1314@126.com
	 * @date: Created on 2015-8-5 下午08:19:21
	 */
	private Map<String, String> checkDataLength(REQUEST_COMMON_FPKJ REQUEST_COMMON_FPKJ,
			COMMON_FPKJ_FPT COMMON_FPKJ_FPT, COMMON_FPKJ_XMXX[] fpkjxx_xmxxs,final EInvoiceSubRepository repository) {

		Map<String, String> checkResultMap = new HashMap<String, String>();
		checkResultMap.put(XmlPar.ERRORCODE, XmlPar.RESPONSEYYSSUCCESS);

		if (ValidateUtil.checkParameterLength(COMMON_FPKJ_FPT.getKPY(), 0, 100)) {
			checkResultMap = generateErrorMap(COMMON_FPKJ_FPT,XmlPar.ERRORCODE_DATA_FPT_KPY,"发票开票员长度存在问题");
			LOGGER.error(checkResultMap.get(XmlPar.ERRORMSG));
			return checkResultMap;
		}
		
		if (fpkjxx_xmxxs.length < 1) {
			checkResultMap = generateErrorMap(COMMON_FPKJ_FPT,XmlPar.ERRORCODE_DATA_FPMXXXISEXIST,"发票开具明细信息不能为空！");
			LOGGER.error(checkResultMap.get(XmlPar.ERRORMSG));
			return checkResultMap;
		}
		// 发票开具明细不能超过100行，2016年10月31日 10:33:39  阳开国 
		/*if (fpkjxx_xmxxs.length > 100) {
            checkResultMap = generateErrorMap(COMMON_FPKJ_FPT,XmlPar.ERRORCODE_DATA_FPMXXXISEXIST,"发票开具明细行条数超过100！");
            LOGGER.error(checkResultMap.get(XmlPar.ERRORMSG));
            return checkResultMap;
        }*/
		
		if (ValidateUtil.checkParameterLength(COMMON_FPKJ_FPT.getFPQQLSH(), 0,20)) {
			checkResultMap = generateErrorMap(COMMON_FPKJ_FPT,XmlPar.ERRORCODE_DATA_FPT_FPQQLSH,"发票开票发票请求唯一流水号长度存在问题");
			LOGGER.error(checkResultMap.get(XmlPar.ERRORMSG));
			return checkResultMap;
		}
		
		if (ValidateUtil.checkParameterLength(COMMON_FPKJ_FPT.getNSRSBH(), 15,20)) {
			checkResultMap = generateErrorMap(COMMON_FPKJ_FPT,XmlPar.ERRORCODE_DATA_FPT_NSRSBH,"发票开具纳税人识别号不合法！");
			LOGGER.error(checkResultMap.get(XmlPar.ERRORMSG));
			return checkResultMap;
		}
		
//		if (ValidateUtil.checkParameterLength(COMMON_FPKJ_FPT.getNSRMC(), 4,200)) {
//			checkResultMap = generateErrorMap(COMMON_FPKJ_FPT,XmlPar.ERRORCODE_DATA_FPT_NSRMC,"发票开票方名称长度存在问题");
//			LOGGER.error(checkResultMap.get(XmlPar.ERRORCODE));
//			return checkResultMap;
//		}
		
		// 纳税人电子档案号为非必填项，但在有值的情况下，需要对其做长度校验
		if(!ValidateUtil.checkParameterIsEmpty(COMMON_FPKJ_FPT.getNSRDZDAH())){
			if (ValidateUtil.checkParameterLength(COMMON_FPKJ_FPT.getNSRDZDAH(),15, 20)) {
				checkResultMap = generateErrorMap(COMMON_FPKJ_FPT,XmlPar.ERRORCODE_DATA_FPT_NSRDZDAH,"发票开具开票方电子档案号长度错误");
				LOGGER.error(checkResultMap.get(XmlPar.ERRORMSG));
				return checkResultMap;
			}
		}
		
		// 税务机构代码为非必填项，但在有值的情况下，需要对其做长度校验
		if(!ValidateUtil.checkParameterIsEmpty(COMMON_FPKJ_FPT.getSWJG_DM())){
			if (ValidateUtil.checkParameterLength(COMMON_FPKJ_FPT.getSWJG_DM(), 4,11)) {
				checkResultMap = generateErrorMap(COMMON_FPKJ_FPT,XmlPar.ERRORCODE_DATA_FPT_SWJG_DM,"发票开具税务机构代码长度错误");
				LOGGER.error(checkResultMap.get(XmlPar.ERRORMSG));
				return checkResultMap;
			}
		}
		
		// 票样代码为非必填项，但在有值的情况下，需要对其做长度校验
		if(!ValidateUtil.checkParameterIsEmpty(COMMON_FPKJ_FPT.getPYDM())){
			if (ValidateUtil.checkParameterLength(COMMON_FPKJ_FPT.getPYDM(), 0, 6)) {
				checkResultMap = generateErrorMap(COMMON_FPKJ_FPT,XmlPar.ERRORCODE_DATA_FPT_PYDM,"票样代码长度错误");
				LOGGER.error(checkResultMap.get(XmlPar.ERRORMSG));
				return checkResultMap;
			}
		}
		
		if (ValidateUtil.checkParameterLength(COMMON_FPKJ_FPT.getNSRMC(), 0,200)) {
			checkResultMap = generateErrorMap(COMMON_FPKJ_FPT,XmlPar.ERRORCODE_DATA_FPT_NSRSBH,"发票开票方名称长度存在问题");
			LOGGER.error(checkResultMap.get(XmlPar.ERRORMSG));
			return checkResultMap;
		}
		
		if (ValidateUtil.checkParameterLength(COMMON_FPKJ_FPT.getXHF_MC(), 0,200)) {
			checkResultMap = generateErrorMap(COMMON_FPKJ_FPT,XmlPar.ERRORCODE_DATA_FPT_XHFMC,"发票销货方名称长度存在问题");
			LOGGER.error(checkResultMap.get(XmlPar.ERRORMSG));
			return checkResultMap;
		}
		
		if (ValidateUtil.checkParameterLength(COMMON_FPKJ_FPT.getGHF_MC(), 0,200)) {
			checkResultMap = generateErrorMap(COMMON_FPKJ_FPT,XmlPar.ERRORCODE_DATA_FPT_GHFMC,"发票购货方名称长度存在问题");
			LOGGER.error(checkResultMap.get(XmlPar.ERRORMSG));
			return checkResultMap;
		}
		
		// 购货方纳税人识别号为非必填项，在有值情况下，需要对其做长度校验
		if (!ValidateUtil.checkParameterIsEmpty(COMMON_FPKJ_FPT.getGHF_NSRSBH())) { // 非空情况下，长度校验
			if (ValidateUtil.checkParameterLength(COMMON_FPKJ_FPT.getGHF_NSRSBH(),10, 20)) {
				checkResultMap = generateErrorMap(COMMON_FPKJ_FPT,XmlPar.ERRORCODE_DATA_FPT_GHF_NSRSBH,"发票购货方识别号长度存在问题");
				LOGGER.error(checkResultMap.get(XmlPar.ERRORMSG));
				return checkResultMap;
			}
			
			//购货方纳税人识别号在有值情况下，需要对其值进行数据校验，对于不符合规则的值要返回错误数据，不能入库  2017-11-30 宋雪冬
			if(ValidateUtil.checkParameterIsEmpty(getGhfNsrsbhByRules(COMMON_FPKJ_FPT.getGHF_NSRSBH()))){
				checkResultMap = generateErrorMap(COMMON_FPKJ_FPT,XmlPar.ERRORCODE_DATA_FPT_GHF_NSRSBH,"发票购货方纳税人识别号存在问题,请检查税号信息是否正确");
				LOGGER.error(checkResultMap.get(XmlPar.ERRORMSG));
				return checkResultMap;
			}
			
			
		}
		
		// 购货方地址为非必填项，在有值情况下，需要对其做长度校验
		if (!ValidateUtil.checkParameterIsEmpty(COMMON_FPKJ_FPT.getGHF_DZ())) { // 非空情况下，长度校验
			if (ValidateUtil.checkParameterLength(COMMON_FPKJ_FPT.getGHF_DZ(), 0,200)) {
				checkResultMap = generateErrorMap(COMMON_FPKJ_FPT,XmlPar.ERRORCODE_DATA_FPT_GHF_DZ,"发票购货方地址长度存在问题");
				LOGGER.error(checkResultMap.get(XmlPar.ERRORMSG));
				return checkResultMap;
			}
		}
		
		// 发票购货方省份为非必填项，在有值情况下，需要对其做长度校验
		if (!ValidateUtil.checkParameterIsEmpty(COMMON_FPKJ_FPT.getGHF_SF())) { // 非空情况下，长度校验
			if (ValidateUtil.checkParameterLength(COMMON_FPKJ_FPT.getGHF_SF(), 0,20)) {
				checkResultMap = generateErrorMap(COMMON_FPKJ_FPT,XmlPar.ERRORCODE_DATA_FPT_GHF_SF,"发票购货方省份长度存在问题");
				LOGGER.error(checkResultMap.get(XmlPar.ERRORMSG));
				return checkResultMap;
			}
		}
		
		// 购货方固定电话为非必填项，在有值情况下，需要对其做长度校验
		if (!ValidateUtil.checkParameterIsEmpty(COMMON_FPKJ_FPT.getGHF_GDDH())) { // 非空情况下，长度校验
			if (ValidateUtil.checkParameterLength(COMMON_FPKJ_FPT.getGHF_GDDH(), 0,20)) {
				checkResultMap = generateErrorMap(COMMON_FPKJ_FPT,XmlPar.ERRORCODE_DATA_FPT_GHF_GDDH,"发票购货方固定电话长度存在问题");
				LOGGER.error(checkResultMap.get(XmlPar.ERRORMSG));
				return checkResultMap;
			}
		}
		
		// 购货方邮箱为非必填项，在有值情况下，需要对其做长度校验
		if (!ValidateUtil.checkParameterIsEmpty(COMMON_FPKJ_FPT.getGHF_YX())) { // 非空情况下，长度校验
			if (ValidateUtil.checkParameterLength(COMMON_FPKJ_FPT.getGHF_YX(), 0,100)) {
				checkResultMap = generateErrorMap(COMMON_FPKJ_FPT,XmlPar.ERRORCODE_DATA_FPT_GHF_YX,"购货方邮箱长度存在问题");
				LOGGER.error(checkResultMap.get(XmlPar.ERRORMSG));
				return checkResultMap;
			}
		}
		
		// 收款员为非必填项，在有值情况下，需要对其做长度校验
		if (!ValidateUtil.checkParameterIsEmpty(COMMON_FPKJ_FPT.getSKY())) { // 非空情况下，长度校验
			if (ValidateUtil.checkParameterLength(COMMON_FPKJ_FPT.getSKY(), 0,100)) {
				checkResultMap = generateErrorMap(COMMON_FPKJ_FPT,XmlPar.ERRORCODE_DATA_FPT_SKY,"收款员长度存在问题");
				LOGGER.error(checkResultMap.get(XmlPar.ERRORMSG));
				return checkResultMap;
			}
		}
		
		// 发票备注为非必填项，在有值情况下，需要对其做长度校验
		if (!ValidateUtil.checkParameterIsEmpty(COMMON_FPKJ_FPT.getBZ())) { // 非空情况下，长度校验
			if (ValidateUtil.checkParameterLength(COMMON_FPKJ_FPT.getBZ(), 0, 200)) {
				checkResultMap = generateErrorMap(COMMON_FPKJ_FPT,XmlPar.ERRORCODE_DATA_FPT_BZ, "发票备注长度存在问题(0-200)");
				LOGGER.error(checkResultMap.get(XmlPar.ERRORMSG));
				return checkResultMap;
			}
		}
		 //项目信息长度校验 2016-11-28 09:54:43 阳开国
        for (int i = 0; i < fpkjxx_xmxxs.length; i++) {
            COMMON_FPKJ_XMXX xmxx = fpkjxx_xmxxs[i];
            if (ValidateUtil.checkParameterLength(xmxx.getSPBM().trim(), 0, 19)) {
                checkResultMap = generateErrorMap(COMMON_FPKJ_FPT,XmlPar.ERRORCODE_DATA_MXXX_SPBM, "商品编码长度存在问题(0-19)");
                LOGGER.error(checkResultMap.get(XmlPar.ERRORMSG));
                return checkResultMap;
          }
        }
		
		return checkResultMap;
	}

	/**
	 * 
	 * <p>检查开票相关权限</p>
	 * 
	 * @param REQUEST_COMMON_FPKJ
	 * @param COMMON_FPKJ_FPT
	 * @param fpkjxx_xmxxs
	 * @param repository
	 * @return Map<String,String>
	 * @author: lichunhui lichunhui1314@126.com
	 * @date: Created on 2015-8-4 下午09:32:32
	 */
	private Map<String, String> checkAuthority(REQUEST_COMMON_FPKJ REQUEST_COMMON_FPKJ,
			COMMON_FPKJ_FPT COMMON_FPKJ_FPT, COMMON_FPKJ_XMXX[] fpkjxx_xmxxs,final EInvoiceSubRepository repository) {

		Map<String, String> checkResultMap = new HashMap<String, String>();
		checkResultMap.put(XmlPar.ERRORCODE, XmlPar.RESPONSEYYSSUCCESS);

		/* 判断纳税人信息、开票状态,平台传递的行业类型与行业代码赋值 */
		final String theTaxpayerIdentifyNo = COMMON_FPKJ_FPT.getNSRSBH();
		final String theEShopCode = COMMON_FPKJ_FPT.getDSPTBM();

		final TaxpayerEntity taxpayerEntity = repository.getTaxpayer(theTaxpayerIdentifyNo, theEShopCode);

		if (taxpayerEntity == null) {
			checkResultMap = generateErrorMap(COMMON_FPKJ_FPT,XmlPar.ERRORCODE_DATA_FPT_NSRSBH,"该纳税人对应平台不存在请确认！");
			LOGGER.error(checkResultMap.get(XmlPar.ERRORMSG));
			return checkResultMap;
		}
		
		// 平台启用是 0，停用是1
		/**
		 * 目前先不对这个平台是否启用停用做校验
		 */
//		if (eshopEnabled.equals(taxpayerEntity.getEshopEnabled())) {
//			checkResultMap = generateErrorMap(COMMON_FPKJ_FPT,XmlPar.ERRORCODE_DSPT_UNAVAILABLE,"平台已经停用!");
//			LOGGER.error(checkResultMap.get(XmlPar.ERRORMSG));
//			return checkResultMap;
//		}
		
		if (ValidateUtil.checkParameterIsEmpty(COMMON_FPKJ_FPT.getHY_MC())) {
			COMMON_FPKJ_FPT.setHY_MC(taxpayerEntity.getIndustryName());
		}
		if (ValidateUtil.checkParameterIsEmpty(COMMON_FPKJ_FPT.getHY_DM())) {
			COMMON_FPKJ_FPT.setHY_DM(taxpayerEntity.getIndustryCode());
		}
		
		if (!ValidateUtil.checkParameterIsEmpty(COMMON_FPKJ_FPT.getHY_DM())) {
            if (ValidateUtil.checkParameterLength(COMMON_FPKJ_FPT.getHY_DM(), 0, 10)) {
                checkResultMap = generateErrorMap(COMMON_FPKJ_FPT,XmlPar.ERRORCODE_DATA_FPT_HY_DM, "发票行业代码长度存在问题");
                LOGGER.error(checkResultMap.get(XmlPar.ERRORMSG));
				return checkResultMap;
            }
        }
        if (!ValidateUtil.checkParameterIsEmpty(COMMON_FPKJ_FPT.getHY_MC())) {
            if (ValidateUtil.checkParameterLength(COMMON_FPKJ_FPT.getHY_MC(), 0, 40)) {
                checkResultMap = generateErrorMap(COMMON_FPKJ_FPT,XmlPar.ERRORCODE_DATA_FPT_HY_DM, "发票行业名称长度存在问题");
                LOGGER.error(checkResultMap.get(XmlPar.ERRORMSG));
				return checkResultMap;
            }
        }

		if (!"1".equals(taxpayerEntity.getTaxpayerInvoiceStatus())) {
			checkResultMap = generateErrorMap(COMMON_FPKJ_FPT,XmlPar.ERRORCODE_DATA_FPT_NSRSBH,"请求纳税人开票状态已经停用不能开具发票");
			LOGGER.error(checkResultMap.get(XmlPar.ERRORMSG));
			return checkResultMap;
		}

		return checkResultMap;
	}

	/**
	 * 
	 * <p>检查蓝票数据(即使蓝票数据校验)</p>
	 * 
	 * @param REQUEST_COMMON_FPKJ
	 * @param COMMON_FPKJ_FPT
	 * @param fpkjxx_xmxxs
	 * @param repository
	 * @return Map<String,String>
	 * @author: lichunhui lichunhui1314@126.com
	 * @date: Created on 2015-8-5 上午12:47:34
	 */
	private Map<String, String> checkBlueInvoiceData(REQUEST_COMMON_FPKJ REQUEST_COMMON_FPKJ,
			COMMON_FPKJ_FPT COMMON_FPKJ_FPT, COMMON_FPKJ_XMXX[] fpkjxx_xmxxs,final EInvoiceSubRepository repository) {

		Map<String, String> checkResultMap = new HashMap<String, String>();
		checkResultMap.put(XmlPar.ERRORCODE, XmlPar.RESPONSEYYSSUCCESS);
		
		if (Double.parseDouble(COMMON_FPKJ_FPT.getKPHJJE()) <= 0) {
			checkResultMap = generateErrorMap(COMMON_FPKJ_FPT,XmlPar.ERRORCODE_DATA_FPT_KPHJJE,"蓝票开票合计金额不能小于或者等于0");
			LOGGER.error(checkResultMap.get(XmlPar.ERRORMSG));
			return checkResultMap;
		}

		// 正票错票重开 原发票代码和号码不能为空
		if ("11".equals(COMMON_FPKJ_FPT.getCZDM())) {
			// 正票错票重开
			if (ValidateUtil.checkParameterIsEmpty(COMMON_FPKJ_FPT.getYFP_DM())) {
				checkResultMap = generateErrorMap(COMMON_FPKJ_FPT,XmlPar.ERRORCODE_DATA_FPT_GHF_CZDM,"正票错票重开原发票代码不能为空");
				LOGGER.error(checkResultMap.get(XmlPar.ERRORMSG));
				return checkResultMap;
			}
			
			if(ValidateUtil.checkParameterIsEmpty(COMMON_FPKJ_FPT.getYFP_HM())) {
				checkResultMap = generateErrorMap(COMMON_FPKJ_FPT,XmlPar.ERRORCODE_DATA_FPT_GHF_CZDM,"正票错票重开原发票号码不能为空");
				LOGGER.error(checkResultMap.get(XmlPar.ERRORMSG));
				return checkResultMap;
			}
			
			final String oldInvoiceCode = COMMON_FPKJ_FPT.getYFP_DM();
			final String oldInvoiceNo = COMMON_FPKJ_FPT.getYFP_HM();
			final Boolean beInvoiceExistBlue = repository.verifyInvoiceExistBlue(oldInvoiceCode, oldInvoiceNo);
			
			if (!beInvoiceExistBlue) {
				checkResultMap = generateErrorMap(COMMON_FPKJ_FPT,XmlPar.ERRORCODE_DATA_FPT_GHF_CZDM,"错票重开对应的蓝字发票不存在");
				LOGGER.error(checkResultMap.get(XmlPar.ERRORMSG));
				return checkResultMap;
			}
		}
		
		checkResultMap = this.checkDetailsDiscount(COMMON_FPKJ_FPT, fpkjxx_xmxxs, COMMON_FPKJ_FPT.getKPLX());
		
		return checkResultMap;
	}
	
	/**
	 * 
	 * <p>校验明细行折扣</p>
	 * 
	 * @param COMMON_FPKJ_FPT
	 * @param fpkjxx_xmxxs
	 * @return Map<String,String>
	 * @author: lichunhui lichunhui1314@126.com
	 * @date: Created on 2015-8-5 下午02:04:22
	 */
	private Map<String, String> checkDetailsDiscount(COMMON_FPKJ_FPT COMMON_FPKJ_FPT, COMMON_FPKJ_XMXX[] fpkjxx_xmxxs,String kplx) {
		
		Map<String, String> checkResultMap = new HashMap<String, String>();
		checkResultMap.put(XmlPar.ERRORCODE, XmlPar.RESPONSEYYSSUCCESS);
		
		/**
		 * 发票明细行数据校验----------------------------------------------------------------
		 * 1.明细行税额校验，根据 金额 * 税率 = 税额，与 订单中  传递的税额比较，误差不能大于6分钱。
		 * 2.明细行金额校验，根据 单价 * 数量 = 金额，与 订单中  传递的金额比较，误差不能大于1分钱。
		 * 3.明细行累计税额与合计税额比较，误差不能大于127分钱。
		 * 4.明细行第一行不能为折扣行
		 * 5.不能连续两行为折扣行
		 * 6.折扣行数校验，即必须有足够的被折扣行数。
		 * 7.折扣率校验，折扣率不能大于100%，或者 不能小于或者等于0%
		 * 8.红票折扣金额不能小于或等于零
		 * 9.蓝票折扣金额不能大于或等于零
		 */

		double mxse_total = 0;
		double xmje_total = 0;//项目明细金额之和170.94-17.09
		boolean upIsZkh = false; // 更新是否折扣行标志（连续折扣行标记）
		for (int i = 0; i < fpkjxx_xmxxs.length; i++) {
		    String errorMsgString = "第"+(i+1)+"行:";
			COMMON_FPKJ_XMXX fpkjxx_xmxx = fpkjxx_xmxxs[i];
//			// 商品行金额不能为零
//            if (Double.parseDouble(fpkjxx_xmxx.getXMJE()) == 0) {
//                checkResultMap = generateErrorMap(COMMON_FPKJ_FPT,XmlPar.ERRORCODE_DATA_FPMXXX_SPJE_NOT_ZERO,"商品行金额不能为零");
//                LOGGER.error(checkResultMap.get(XmlPar.ERRORMSG));
//                return checkResultMap;
//            }
            
			String xmmc = fpkjxx_xmxx.getXMMC();
			
			
			// 判断发票行性质是否正确 0正常商品行 1折扣行 2被折扣行
	        if (!"0".equals(fpkjxx_xmxx.getFPHXZ()) && !"1".equals(fpkjxx_xmxx.getFPHXZ()) && !"2".equals(fpkjxx_xmxx.getFPHXZ())){
	            checkResultMap = generateErrorMap(COMMON_FPKJ_FPT,XmlPar.ERRORCODE_DATA_MXXX_FPHXZ,errorMsgString+"发票行性质有误请核对，0正常商品行 1折扣行 2被折扣行！！");
	            LOGGER.error(checkResultMap.get(XmlPar.ERRORMSG));
	            return checkResultMap;
	        }
			
			
			// 项目金额不能为0
			double xmje = Double.parseDouble(fpkjxx_xmxx.getXMJE());
			if(xmje == 0){
				checkResultMap = generateErrorMap(COMMON_FPKJ_FPT,XmlPar.ERRORCODE_DATA_MXXX_XMJE,errorMsgString+"项目金额有误，不能为0，请确认");
				LOGGER.error(checkResultMap.get(XmlPar.ERRORCODE));
				return checkResultMap;
			}
			
			/**
			 * 金额（不含税） * 税率 = 税额
			 * 计算出的税额 与 订单传递的税额 比较，误差不能超过6分（即小于或等于6分）
			 */
			String str=fpkjxx_xmxx.getSL();//把税率的％去掉，转换成小数
			if(str.contains("%")){
				str=str.replace("%", "");
				Double d=Double.valueOf(str)/100;
				str=String.valueOf(d);
			}
			double jsse = MathUtil.mul(fpkjxx_xmxx.getXMJE(), str); // 计算出的税额
			double se = Double.parseDouble(fpkjxx_xmxx.getSE()); // 订单中传递的税额
//			double seCompareResult = jsse - se;
			double seCompareResult = MathUtil.sub(ValidateUtil.decimalFormat(jsse, 2), String.valueOf(se));
			if(Math.abs(seCompareResult) > 0.06){ // 误差大于6分钱，则税额有误
				checkResultMap = generateErrorMap(COMMON_FPKJ_FPT,XmlPar.ERRORCODE_DATA_MXXX_SE,errorMsgString+"税额有误,误差大于6分钱,请确认");
				LOGGER.error(checkResultMap.get(XmlPar.ERRORCODE));
				return checkResultMap;
			}
			
			/**
			 * 项目数量、项目单价都不为空,并且非折扣行的情况下，要求 项目金额  与  （项目数量 * 项目单价） 之差，误差不能大于1分钱（即误差小于或等于1分钱）。
			 */
			//非折扣行计算,折扣行不进行计算
			if (!ValidateUtil.checkParameterIsEmpty(fpkjxx_xmxx.getXMDJ()) && !ValidateUtil.checkParameterIsEmpty(fpkjxx_xmxx.getXMSL()) && !("1" == fpkjxx_xmxx.getFPHXZ())) {
				/**
				 * 金额（不含税） = 项目单价 * 项目数量
				 * 计算出的项目金额 与 订单传递的项目金额 比较，误差不能大于1分钱（即误差小于或等于1分钱）
				 */
			  //yxmje - js_xmje; 有误差
				double js_xmje = MathUtil.mul(fpkjxx_xmxx.getXMDJ(), fpkjxx_xmxx.getXMSL()); // 金额 = 项目单价 * 项目数量
				double yxmje = Double.parseDouble(fpkjxx_xmxx.getXMJE()); // 项目金额
//				double xmjeCompareResult = yxmje - js_xmje;
				double xmjeCompareResult = MathUtil.sub(String.valueOf(yxmje), ValidateUtil.decimalFormat(js_xmje,2));
				if(Math.abs(xmjeCompareResult) > 0.01){ // 误差不能大于1分钱（即误差小于或等于1分钱），否则项目金额有误
					checkResultMap = generateErrorMap(COMMON_FPKJ_FPT,XmlPar.ERRORCODE_DATA_MXXX_XMJE,errorMsgString+"项目金额有误,误差不能大于1分钱,请确认");
					LOGGER.error(checkResultMap.get(XmlPar.ERRORCODE));
					return checkResultMap;
				}
			}
			
			/**
			 * 根据金额来校验是否是折扣行
			 */
			if("1".equals(kplx)){ // 蓝票
                if (Double.parseDouble(fpkjxx_xmxx.getXMJE()) >= 0 && ("1".equals(fpkjxx_xmxx.getFPHXZ()))) {
                    checkResultMap = generateErrorMap(COMMON_FPKJ_FPT,XmlPar.ERRORCODE_DATA_MXXX_ZKHJE,errorMsgString+"蓝票折扣金额不能大于或者等于零请确认！");
                    LOGGER.error(checkResultMap.get(XmlPar.ERRORMSG));
                    return checkResultMap;
                }
            }else if( ("2".equals(kplx)) || ("9".equals(kplx))){ // 红票
                if (Double.parseDouble(fpkjxx_xmxx.getXMJE()) <= 0 && ("1".equals(fpkjxx_xmxx.getFPHXZ()))) {
                    checkResultMap = generateErrorMap(COMMON_FPKJ_FPT,XmlPar.ERRORCODE_DATA_MXXX_ZKHJE,errorMsgString+"红票折扣金额不能小于或者等于零请确认！");
                    LOGGER.error(checkResultMap.get(XmlPar.ERRORMSG));
                    return checkResultMap;
                }
            }else{ // 非法开票类型
                checkResultMap = generateErrorMap(COMMON_FPKJ_FPT,XmlPar.ERRORCODE_DATA_FPT_KPLX,errorMsgString+"不能识别的开票类型请确认！");
                LOGGER.error(checkResultMap.get(XmlPar.ERRORMSG));
                return checkResultMap;
            }
			
			
			
 			if ("1".equals(fpkjxx_xmxx.getFPHXZ())) { // 是折扣行(根据发票行性质判断是否是折扣行)
			    
			  //判断红票和蓝票的折扣行没有折扣率的,如果以折扣开头的进行校验
			    /**
			     * 折扣行格式校验:
			     * 1.如果以折扣开头的项目名称中,不包含英文()和%的返回折扣行格式错误(2016年7月11日 15:14:43 版本升级后不存在这个校验)
			     * 2.括号内去掉百分后后值为空或者折扣率小于0%或者是大与100%,需抛异常(2016年7月11日 15:14:43 版本升级后不存在这个校验)
			     * 3.折扣行数没有行数或折扣行数小于等于1,需抛异常(2016年7月11日 15:14:43 版本升级后不存在这个校验)
			     * 4.单独一个折扣的折扣行,如果折扣两个字和后面的(之间有值,抛异常(2016年7月11日 15:14:43 版本升级后不存在这个校验)
			     */
	                
	            /*if(!xmmc.contains("(")||!xmmc.contains(")")||!xmmc.contains("%")){   //如果以折扣开头的项目名称中,不包含()和%的返回折扣行格式错误
	                checkResultMap = generateErrorMap(COMMON_FPKJ_FPT,XmlPar.ERRORCODE_DATA_FPMXXX_ZKHERROR,"折扣行数据不合法,没有英文括号或百分号");
	                LOGGER.error(checkResultMap.get(XmlPar.ERRORMSG));
	                return checkResultMap;
	            }else if (StringUtils.isNullOrEmpty(xmmc.substring(xmmc.indexOf("(")+1, xmmc.indexOf(")")).replace("%", ""))||Double.parseDouble(xmmc.substring(xmmc.indexOf("(")+1, xmmc.indexOf(")")).replace("%", ""))<0 || Double.parseDouble(xmmc.substring(xmmc.indexOf("(")+1, xmmc.indexOf(")")).replace("%", ""))>100){//括号内去掉百分后后值为空或者折扣率小于0%或者是大与100%,需抛异常
	                checkResultMap = generateErrorMap(COMMON_FPKJ_FPT, XmlPar.ERRORCODE_DATA_FPMXXX_ZKH, "折扣不能没有折扣率,并且折扣率不能小于0%大于100%");
	                LOGGER.error(checkResultMap.get(XmlPar.ERRORMSG));
	                return checkResultMap;
	            }else if(("折扣行数").equals(xmmc.substring(0,4))&&(StringUtils.isNullOrEmpty(xmmc.substring(xmmc.indexOf("折扣行数")+4, xmmc.indexOf("(")))||Integer.parseInt(xmmc.substring(xmmc.indexOf("折扣行数")+4, xmmc.indexOf("(")))<=1)){//折扣行数没有行数或折扣行数小于等于1,需抛异常
	                checkResultMap = generateErrorMap(COMMON_FPKJ_FPT,XmlPar.ERRORCODE_DATA_FPMXXX_ZKHERROR,"折扣行数据不合法,折扣行数不能小于等于1");
	                LOGGER.error(checkResultMap.get(XmlPar.ERRORMSG));
	                return checkResultMap;
	            }else if(("折扣").equals(xmmc.substring(0,2))&& !("行数").equals(xmmc.substring(2,4))&&!StringUtils.isNullOrEmpty(xmmc.substring(xmmc.indexOf("折扣")+2, xmmc.indexOf("(")))){//单独一个折扣的折扣行,如果折扣两个字和后面的(之间有值,抛异常
	                checkResultMap = generateErrorMap(COMMON_FPKJ_FPT,XmlPar.ERRORCODE_DATA_FPMXXX_ZKHERROR,"折扣行不合法,折扣后面不能有行数");
	                LOGGER.error(checkResultMap.get(XmlPar.ERRORMSG));
	                return checkResultMap;
	            }*/
			    
			  //折扣行不能为第一行或不能连续两个折扣行！
                if(i == 0 || upIsZkh){
                    checkResultMap = generateErrorMap(COMMON_FPKJ_FPT,XmlPar.ERRORCODE_DATA_MXXX_ZKH,errorMsgString+"折扣行不能为第一行或不能连续两个折扣行！");
                    LOGGER.error(checkResultMap.get(XmlPar.ERRORMSG));
                    return checkResultMap;
                }
                
                //如果走到这里说明第一行不是折扣行
                if(!"2".equals(fpkjxx_xmxxs[i-1].getFPHXZ())){   //对于蓝字发票，金额为负的商品名称必须与与之相邻的上一行的商品名称相同
                    checkResultMap = generateErrorMap(COMMON_FPKJ_FPT,XmlPar.ERRORCODE_DATA_FPMXXX_ZKHERROR,"被折扣行数据不合法,被折扣行的发票行性质必须为2！");
                    LOGGER.error(checkResultMap.get(XmlPar.ERRORMSG));
                    return checkResultMap;
                }
                
                //如果是老蓝票对应得新红票报文不校验SPBM和XMMC
                if (!(("2".equals(COMMON_FPKJ_FPT.getKPLX()) ||("9".equals(COMMON_FPKJ_FPT.getKPLX())) ) && (fpkjxx_xmxx.getXMMC().startsWith("折扣行数") || fpkjxx_xmxx.getXMMC().startsWith("折扣")) && ("1".equals(fpkjxx_xmxx.getFPHXZ())) )){
                    //如果走到这里说明第一行不是折扣行
                    if(!(fpkjxx_xmxx.getXMMC()).equals(fpkjxx_xmxxs[i-1].getXMMC())){   //对于蓝字发票，金额为负的商品名称必须与与之相邻的上一行的商品名称相同
                        checkResultMap = generateErrorMap(COMMON_FPKJ_FPT,XmlPar.ERRORCODE_DATA_FPMXXX_ZKHERROR,errorMsgString+"折扣行数据不合法,折扣行商品名称必须与与之相邻的上一行的商品名称相同！");
                        LOGGER.error(checkResultMap.get(XmlPar.ERRORMSG));
                        return checkResultMap;
                    }
                    
                    //折扣行与被折扣行的商品编码相同
                    if (!(fpkjxx_xmxx.getSPBM().equals(fpkjxx_xmxxs[i - 1].getSPBM()))) {
                        checkResultMap = generateErrorMap(COMMON_FPKJ_FPT, XmlPar.ERRORCODE_DATA_MXXX_SPBM_UNEQUAL, errorMsgString+"折扣行数据不合法,折扣行商品编码与被折扣行的商品编码不相同！");
                        LOGGER.error(checkResultMap.get(XmlPar.ERRORMSG));
                        return checkResultMap;
                    }
                }
               
                
			    
	            
	            
				//判断折扣金额不能大于被折扣商品行的金额和         单折扣行判断
//				if(xmmc.length() > 3 && (xmmc.substring(0, 3)).equals("折扣(")){
//					if(i-1<0){ // 第一行
//						checkResultMap = generateErrorMap(COMMON_FPKJ_FPT,XmlPar.ERRORCODE_DATA_MXXX_ZKH,errorMsgString+"没有足够的商品行抵扣！");
//						LOGGER.error(checkResultMap.get(XmlPar.ERRORMSG));
//						return checkResultMap;
//					}else{ // 非第一行
//						COMMON_FPKJ_XMXX sph = fpkjxx_xmxxs[i-1];
//						if(checkSphIsZkh(sph)){ // 是折扣行
//							checkResultMap = generateErrorMap(COMMON_FPKJ_FPT,XmlPar.ERRORCODE_DATA_MXXX_ZKH,errorMsgString+"没有足够的商品行抵扣！");
//							LOGGER.error(checkResultMap.get(XmlPar.ERRORMSG));
//							return checkResultMap;
//						}
//					}
//				}
				
				/*
				 * ====校验“被折扣商品行金额” 乘以 “折扣率” 是否等于 “折扣额”=============
				 * 逻辑：
				 * 	1、如果第i行商品行是折扣行：商品名称判断是单行折扣还是多行折扣
				 * 		1.1 、单行折扣：(折扣额) /(第i-1行“商品金额”)，如果计算结果和折扣率不相等，返回错误信息。（版本升级后不存在多行折扣，只坐单行折扣校验 2016年7月11日 15:33:43）
				 * 		1.2、 多行折扣：(折扣额) /(第i-n行到i-1行“商品金额”之和)，如果计算结果和折扣率不相等，返回错误信息。（版本升级后不存在多行折扣，只坐单行折扣校验 2016年7月11日 15:33:52）
				 */
				Double zkl = 0.0;			// 折扣率
				int zkNum = 0;				// 折扣行数
				double bzkje_total = 0.0;	// 被折扣行金额之和
				double bzkse_total = 0.0;	// 被折扣行金额之和
				double bzkzje_total = 0.0;	// 被折扣行金额加税额之和
				Double zke = MathUtil.add(fpkjxx_xmxx.getXMJE(), fpkjxx_xmxx.getSE());
				
				if ((fpkjxx_xmxx.getXMMC()).equals(fpkjxx_xmxxs[i-1].getXMMC()) && "1".equals( fpkjxx_xmxx.getFPHXZ())) {// 单行折扣的类型
				    
                    // 单行折扣 判断第一行是否为折扣行,并且是否相邻两行是折扣行
                    if (i - 1 < 0) { // 第一行
                        checkResultMap = generateErrorMap(COMMON_FPKJ_FPT,XmlPar.ERRORCODE_DATA_MXXX_ZKH, errorMsgString+ "第一行不能为折扣行!");
                        LOGGER.error(checkResultMap.get(XmlPar.ERRORMSG));
                        return checkResultMap;
                    } else { // 非第一行
                        COMMON_FPKJ_XMXX sph = fpkjxx_xmxxs[i - 1];
                        if ("1".equals(sph.getFPHXZ())) { // 判断是否是折扣行
                            checkResultMap = generateErrorMap(COMMON_FPKJ_FPT,XmlPar.ERRORCODE_DATA_MXXX_ZKH,errorMsgString + "相邻两行不能是折扣行");
                            LOGGER.error(checkResultMap.get(XmlPar.ERRORMSG));
                            return checkResultMap;
                        }
                    }
	                
				    //单行折扣  校验“折扣额” 除以 “被折扣商品行金额” 是否等于 “折扣率”
//					int beginIndex = xmmc.indexOf("折扣(");
//					beginIndex = beginIndex + 3;
//					int endIndex = xmmc.indexOf("%)");
//					//获取折扣率并且格式化
//					zkl = Double.parseDouble(ValidateUtil.decimalFormat(Double.parseDouble(xmmc.substring(beginIndex,endIndex)) / 100.0,5));// 提取出折扣率
					//获取被折扣行的不含税金额
					bzkje_total = Double.parseDouble(ValidateUtil.decimalFormat(fpkjxx_xmxxs[i - 1].getXMJE(),2));//获取被折扣行金额
					//获取被折扣行的不含税税额
					bzkse_total = Double.parseDouble(ValidateUtil.decimalFormat(fpkjxx_xmxxs[i - 1].getSE(),2));
					//获取被折扣行的不含税金额加上税额,即反推含税金额
					bzkzje_total = MathUtil.add(fpkjxx_xmxxs[i - 1].getXMJE().trim(),fpkjxx_xmxxs[i - 1].getSE().trim());//获取被折扣行金额加税额
					//判断 折扣额/被折扣商品行金额的值是否与传递的折扣率相同
					
					/*京东--注释--折扣校验
					if (Math.abs(MathUtil.div(String.valueOf(zke), String.valueOf(bzkzje_total), 5)) != zkl) {
						checkResultMap = generateErrorMap(COMMON_FPKJ_FPT,XmlPar.ERRORCODE_DATA_MXXX_ZKHS,"折扣额/被折扣金额之和   != 折扣率");
						LOGGER.error(checkResultMap.get(XmlPar.ERRORMSG));
						return checkResultMap;
					}
					*/
					
					//折扣校验
					if (( Math.abs(bzkzje_total) < Math.abs(zke) )) {
					    checkResultMap = generateErrorMap(COMMON_FPKJ_FPT,XmlPar.ERRORCODE_DATA_MXXX_ZKHS,errorMsgString+"折扣额不能大于被折扣金额");
                        LOGGER.error(checkResultMap.get(XmlPar.ERRORMSG));
                        return checkResultMap;
                    }
					
					
					/**
					 * 单行折扣,校验 税率是否相等
					 */
					
					double bzkhslDouble = Double.parseDouble(ValidateUtil.decimalFormat(fpkjxx_xmxxs[i - 1].getSL().trim().replace("%", ""),0));   //被折扣行税率
					double zkhslDouble = Double.parseDouble(ValidateUtil.decimalFormat(fpkjxx_xmxx.getSL().trim().replace("%", ""),0));    //折扣行税率
					//判断折扣行税率是否等于被折扣行税率
					if(zkhslDouble != bzkhslDouble){
					    checkResultMap = generateErrorMap(COMMON_FPKJ_FPT,XmlPar.ERRORCODE_DATA_MXXX_ZKH,errorMsgString+"折扣行和被折扣行税率不相等!");
					    LOGGER.error(checkResultMap.get(XmlPar.ERRORMSG));
					    return checkResultMap;
					}
                    
				}/*else if (xmmc.length() > 4 && (xmmc.substring(0, 4)).equals("折扣行数")) {// 多行折扣的类型
				    
				    String zkhsStr = xmmc.substring(4, xmmc.indexOf("(")); // 取折扣行数
                    int zkhs = Integer.parseInt(zkhsStr);
                    //多行折扣 判断第一行是否为折扣行,并且是否相邻两行是折扣行
                    if((i-zkhs)<0){
                        checkResultMap = generateErrorMap(COMMON_FPKJ_FPT,XmlPar.ERRORCODE_DATA_MXXX_ZKHS,errorMsgString+"多行折扣,被折扣行不足!");
                        LOGGER.error(checkResultMap.get(XmlPar.ERRORMSG));
                        return checkResultMap;
                    }else{ // 从行数的角度上出发，有足够的行数来被折扣，但是不确定是否都为商品行，所以需要进一步进行逻辑判断
                        for(int k=i-1;k > -1;k--){
                            COMMON_FPKJ_XMXX sph = fpkjxx_xmxxs[k];
                            if(zkhs == 0){ // 折扣行数等于0，说明有足够的商品行被折扣，并验证通过。
                                break;
                            }else{
                                if(!checkSphIsZkh(sph)){ // 非折扣行，即商品行
                                    zkhs = zkhs -1; // 从此折扣行往前循环，如果循环到的行为商品行则折扣数减去1
                                }else{
                                    checkResultMap = generateErrorMap(COMMON_FPKJ_FPT,XmlPar.ERRORCODE_DATA_MXXX_ZKHS,errorMsgString+"多行折扣,被折扣行中存在折扣行!");
                                    LOGGER.error(checkResultMap.get(XmlPar.ERRORMSG));
                                    return checkResultMap;
                                }
                            }
                        }
                    }
                    //多行折扣  校验“折扣额” 除以 “被折扣商品行金额” 是否等于 “折扣率”
				    int beginIndex = xmmc.indexOf("(") + 1;
					int endIndex = xmmc.indexOf("%)");
					zkl = Double.parseDouble(ValidateUtil.decimalFormat(Double.parseDouble(xmmc.substring(beginIndex,endIndex)) / 100,5));
					zkNum = Integer.parseInt(zkhsStr);  //折扣行数
					for (int j = 0; j < zkNum; j++) {
					    
					    *//**
					     * 多行折扣,校验被折扣行税率是否和折扣行税率相等
					     *//*
					    double bzkhslDouble = Double.parseDouble(ValidateUtil.decimalFormat(fpkjxx_xmxxs[i- zkNum + j].getSL().trim().replace("%", ""),0));   //被折扣行税率
	                    double zkhslDouble = Double.parseDouble(ValidateUtil.decimalFormat(fpkjxx_xmxx.getSL().trim().replace("%", ""),0));    //折扣行税率
	                    //判断折扣行税率是否等于被折扣行税率
	                    if(zkhslDouble != bzkhslDouble){
	                        checkResultMap = generateErrorMap(COMMON_FPKJ_FPT,XmlPar.ERRORCODE_DATA_MXXX_ZKH,"折扣行和被折扣行税率不相等!");
	                        LOGGER.error(checkResultMap.get(XmlPar.ERRORMSG));
	                        return checkResultMap;
	                    }
	                    
	                    //获取被折扣行的累计金额
	                    bzkje_total = MathUtil.add(String.valueOf(bzkje_total),String.valueOf(fpkjxx_xmxxs[i- zkNum + j].getXMJE().trim()));
	                    //获取被折扣行的累计税额
	                    bzkse_total = MathUtil.add(String.valueOf(bzkse_total),String.valueOf(fpkjxx_xmxxs[i- zkNum + j].getSE().trim()));
	                    //获取被折扣行的累计金额和税额之和
	                    bzkzje_total = MathUtil.add(String.valueOf(bzkzje_total),String.valueOf(MathUtil.add(fpkjxx_xmxxs[i- zkNum + j].getXMJE().trim(),fpkjxx_xmxxs[i- zkNum + j].getSE().trim())));
	                    
					}
					//获取被折扣行的累计金额
					 京东--注释--折扣校验
					if (Math.abs(MathUtil.div(String.valueOf(zke), String.valueOf(bzkzje_total),5)) != zkl) {
						checkResultMap = generateErrorMap(COMMON_FPKJ_FPT,XmlPar.ERRORCODE_DATA_MXXX_ZKHS,"折扣额/被折扣金额之和   != 折扣率");
						LOGGER.error(checkResultMap.get(XmlPar.ERRORMSG));
						return checkResultMap;
					}
				}*/
				
				/**
				 * 折扣行, 校验:折扣行金额和被折扣行金额相等,折扣行做一分钱金额调整
				 */
				//如果被折扣行累计金额 与 折扣行金额去绝对之后相等并且折扣行的金额和税额大于等于0.01,多折扣行做一分钱调整
				if((Double.parseDouble(ValidateUtil.decimalFormat(Math.abs(bzkje_total),2)) - Double.parseDouble(ValidateUtil.decimalFormat(Math.abs(Double.valueOf(fpkjxx_xmxx.getXMJE())),2))) == 0 && (Double.parseDouble(ValidateUtil.decimalFormat(Math.abs(bzkse_total),2)) - Double.parseDouble(ValidateUtil.decimalFormat(Math.abs(Double.valueOf(fpkjxx_xmxx.getSE())),2))) > 0 && Double.parseDouble(ValidateUtil.decimalFormat(Math.abs(Double.valueOf(fpkjxx_xmxx.getXMJE())),2)) >=0.01 && Double.parseDouble(ValidateUtil.decimalFormat(Math.abs(Double.valueOf(fpkjxx_xmxx.getSE())),2)) >=0.01 ){
				    
				    if("1".equals(kplx)){  //如果是蓝票,明细折扣行金额加上一分钱,税额减去一分钱,重新计算单价 合计不含税金额加上一分钱,合计税额减去一分钱
				        fpkjxx_xmxx.setXMJE(ValidateUtil.decimalFormat(MathUtil.add(fpkjxx_xmxx.getXMJE(), "0.01"),2));
				        fpkjxx_xmxx.setXMDJ(ValidateUtil.decimalFormat(MathUtil.div(String.valueOf(MathUtil.add(fpkjxx_xmxx.getXMJE(), "0.01")), fpkjxx_xmxx.getXMSL(), 8),8));
				        fpkjxx_xmxx.setSE(ValidateUtil.decimalFormat(MathUtil.sub(fpkjxx_xmxx.getSE(), "0.01"),2));
				        //发票头信息中的合计不含税金额和合计税额一分钱调整
				        COMMON_FPKJ_FPT.setHJBHSJE(ValidateUtil.decimalFormat(MathUtil.add(COMMON_FPKJ_FPT.getHJBHSJE(), "0.01"),2));
				        COMMON_FPKJ_FPT.setKPHJSE(ValidateUtil.decimalFormat(MathUtil.sub(COMMON_FPKJ_FPT.getKPHJSE(), "0.01"),2));
				    }else if("2".equals(kplx)){//如果是红票,明细折扣行金额减去一分钱,税额加上一分钱,重新计算单价 合计不含税金额加上一分钱,合计税额减去一分钱
				        
				        fpkjxx_xmxx.setXMJE(ValidateUtil.decimalFormat(MathUtil.sub(fpkjxx_xmxx.getXMJE(), "0.01"),2));
				        fpkjxx_xmxx.setXMDJ(ValidateUtil.decimalFormat(MathUtil.div(String.valueOf(MathUtil.sub(fpkjxx_xmxx.getXMJE(), "0.01")), fpkjxx_xmxx.getXMSL(), 8),8));
				        fpkjxx_xmxx.setSE(ValidateUtil.decimalFormat(MathUtil.add(fpkjxx_xmxx.getSE(), "0.01"),2));
				        //发票头信息中的合计不含税金额和合计税额一分钱调整
				        COMMON_FPKJ_FPT.setHJBHSJE(ValidateUtil.decimalFormat(MathUtil.sub(COMMON_FPKJ_FPT.getHJBHSJE(), "0.01"),2));
				        COMMON_FPKJ_FPT.setKPHJSE(ValidateUtil.decimalFormat(MathUtil.add(COMMON_FPKJ_FPT.getKPHJSE(), "0.01"),2));
				    }
				}
			upIsZkh = true;
			}else{ // 非折扣行
				upIsZkh = false;
				
				//只有一个商品行时，发票行性质为必须为0
				if (1 == fpkjxx_xmxxs.length && "2".equals(fpkjxx_xmxx.getFPHXZ())) {
                    checkResultMap = generateErrorMap(COMMON_FPKJ_FPT,XmlPar.ERRORCODE_DATA_MXXX_FPHXZ,errorMsgString+"只有一个商品行时，发票行性质为必须为0（正常行）,请确认！");
                    LOGGER.error(checkResultMap.get(XmlPar.ERRORMSG));
                    return checkResultMap;
                }
				
				//项目明细最后一行的FPHXZ发票行性质不能为2！2016年12月9日16:40:00  阳开国
                if ((i == (fpkjxx_xmxxs.length-1)) && "2".equals(fpkjxx_xmxx.getFPHXZ())) {
                    checkResultMap = generateErrorMap(COMMON_FPKJ_FPT,XmlPar.ERRORCODE_DATA_MXXX_FPHXZ,errorMsgString+"发票行性质有误,项目明细最后一行的FPHXZ发票行性质不能为2,请确认！");
                    LOGGER.error(checkResultMap.get(XmlPar.ERRORMSG));
                    return checkResultMap;
                }
                
				// 蓝票正常行的发票行性质必须为0
				if ("1".equals(COMMON_FPKJ_FPT.getKPLX()) && "2".equals(fpkjxx_xmxx.getFPHXZ()) && !(i == (fpkjxx_xmxxs.length-1))) {
                    if (!("1".equals(fpkjxx_xmxxs[i+1].getFPHXZ()))) {
                        checkResultMap = generateErrorMap(COMMON_FPKJ_FPT,XmlPar.ERRORCODE_DATA_MXXX_FPHXZ,errorMsgString+"发票行性质有误,正常商品行性质必须为0,请确认！");
                        LOGGER.error(checkResultMap.get(XmlPar.ERRORMSG));
                        return checkResultMap;
                    }
                }
				
				
				/**
	             * 根据金额来校验是否是折扣行
	             */
	            if("1".equals(kplx)){ // 非折扣行蓝票处理,明细行的数量,单价,金额都不能小于等于0
	                if (Double.parseDouble(fpkjxx_xmxx.getXMJE()) <= 0) {
	                    checkResultMap = generateErrorMap(COMMON_FPKJ_FPT,XmlPar.ERRORCODE_DATA_MXXX_ZKHJE,errorMsgString+"蓝票明细行金额不能小于或者等于零请确认！");
	                    LOGGER.error(checkResultMap.get(XmlPar.ERRORMSG));
	                    return checkResultMap;
	                }
	                if (Double.parseDouble(fpkjxx_xmxx.getXMDJ()) <= 0) {
                        checkResultMap = generateErrorMap(COMMON_FPKJ_FPT,XmlPar.ERRORCODE_DATA_MXXX_ZKH,errorMsgString+"蓝票明细行单价不能小于或者等于零请确认！");
                        LOGGER.error(checkResultMap.get(XmlPar.ERRORMSG));
                        return checkResultMap;
                    }
	                if (Double.parseDouble(fpkjxx_xmxx.getXMSL()) <= 0) {
                        checkResultMap = generateErrorMap(COMMON_FPKJ_FPT,XmlPar.ERRORCODE_DATA_MXXX_ZKH,errorMsgString+"蓝票明细行数量不能小于或者等于零请确认！");
                        LOGGER.error(checkResultMap.get(XmlPar.ERRORMSG));
                        return checkResultMap;
                    }
	            }else if("2".equals(kplx) || "9".equals(kplx)){ // 非折扣行红票处理,明细行的金额不能大于等于0
	                if (Double.parseDouble(fpkjxx_xmxx.getXMJE()) >= 0 ) {
	                    checkResultMap = generateErrorMap(COMMON_FPKJ_FPT,XmlPar.ERRORCODE_DATA_MXXX_ZKHJE,errorMsgString+"红票明细行金额不能大于或者等于零请确认！");
	                    LOGGER.error(checkResultMap.get(XmlPar.ERRORMSG));
	                    return checkResultMap;
	                }
	            }else{ // 非法开票类型
	                checkResultMap = generateErrorMap(COMMON_FPKJ_FPT,XmlPar.ERRORCODE_DATA_FPT_KPLX,errorMsgString+"不能识别的开票类型请确认！");
	                LOGGER.error(checkResultMap.get(XmlPar.ERRORMSG));
	                return checkResultMap;
	            }
			}
			// 使用BigDecimal作运算
//			mxse_total = mxse_total + Double.parseDouble(fpkjxx_xmxx.getSE());
 			mxse_total = MathUtil.add(String.valueOf(mxse_total), fpkjxx_xmxx.getSE());
//          xmje_total += Double.parseDouble(fpkjxx_xmxx.getXMJE());
 			xmje_total = MathUtil.add(String.valueOf(xmje_total), fpkjxx_xmxx.getXMJE());
		}
		//开具合计金额和明细金额不相等****************************************************************
//		BigDecimal total_bd = new BigDecimal(xmje_total);//四舍五入，保留两位小数
//		xmje_total = total_bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
//		BigDecimal HJBHSJE_bd = new BigDecimal(Double.parseDouble(COMMON_FPKJ_FPT.getHJBHSJE()));
//		Double HJBHSJE = HJBHSJE_bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		
		xmje_total = Double.parseDouble(ValidateUtil.decimalFormat(xmje_total, 2));
		Double  HJBHSJE = Double.parseDouble(ValidateUtil.decimalFormat(COMMON_FPKJ_FPT.getHJBHSJE(), 2)); 
		
		if(HJBHSJE!=xmje_total){
			checkResultMap = generateErrorMap(COMMON_FPKJ_FPT,XmlPar.ERRORCODE_DATA_FPT_HJSE,"开具合计金额和明细金额不相等，请确认");
			LOGGER.error(checkResultMap.get(XmlPar.ERRORCODE));
			return checkResultMap;
		}
		/**
		 * 明细税额累加 与 发票头中的开票合计税额 比较，误差不能超过127分钱（即小于或等于127分钱）
		 */
//		double mxse_total_compareResult = Double.parseDouble(COMMON_FPKJ_FPT.getKPHJSE()) - mxse_total;
		double mxse_total_compareResult = MathUtil.sub(COMMON_FPKJ_FPT.getKPHJSE(), String.valueOf(mxse_total));
		if(Math.abs(mxse_total_compareResult) > 1.27){ // 误差大于127分钱，则合计税额有误
			checkResultMap = generateErrorMap(COMMON_FPKJ_FPT,XmlPar.ERRORCODE_DATA_FPT_HJSE,"合计税额有误,误差大于127分钱,请确认");
			LOGGER.error(checkResultMap.get(XmlPar.ERRORCODE));
			return checkResultMap;
		}
		return checkResultMap;
	}
	
	/**
	 * 
	 * <p>检查商品行是否折扣</p>
	 * 
	 * @param COMMON_FPKJ_XMXX
	 * @return boolean 折扣行：true 非折扣行：false
	 * @author: lichunhui lichunhui1314@126.com
	 * @date: Created on 2015-8-5 下午04:20:39
	 */
	private boolean checkSphIsZkh(COMMON_FPKJ_XMXX COMMON_FPKJ_XMXX){
		String xmmc = COMMON_FPKJ_XMXX.getXMMC();
		if((xmmc.length() > 3 && (xmmc.substring(0, 3)).equals("折扣(")) || (xmmc.length() > 4 && (xmmc.substring(0, 4)).equals("折扣行数"))){
			return true;
		}
		return false;
	}
	
	
	/**
	 * 
	 * <p>用项目金额来检查商品行是否折扣</p>
	 * 
	 * @param COMMON_FPKJ_XMXX
	 * @return boolean
	 * @author: 阳开国
	 * @date: Created on 2016-7-11 上午10:42:27
	 */
	private  boolean  checkSphIsZkhByXmje(COMMON_FPKJ_XMXX COMMON_FPKJ_XMXX) {
	      
	    return ((Double.parseDouble(COMMON_FPKJ_XMXX.getXMJE())) <= 0) ? true : false ;
	    
	}
	
	
	/**
     * 
     * <p>开票合计金额校验</p>
     * 
     * @param COMMON_FPKJ_XMXX
     * @return boolean
     * @author: 张双超
     * @date: Created on Sep 2, 2015 10:01:56 AM
     */
    private boolean checkKphjje(COMMON_FPKJ_FPT COMMON_FPKJ_FPT,COMMON_FPKJ_XMXX[] fpkjxx_xmxxs){
        double kphjje = Double.parseDouble(COMMON_FPKJ_FPT.getKPHJJE());
        double kphjse = Double.parseDouble(COMMON_FPKJ_FPT.getKPHJSE());
        double hjbhsje = Double.parseDouble(COMMON_FPKJ_FPT.getHJBHSJE());
        //differ = kphjje - (kphjse+hjbhsje) ;
        double differ = MathUtil.sub(String.valueOf(kphjje), String.valueOf(MathUtil.add(String.valueOf(kphjse), String.valueOf(hjbhsje))));
        if (Math.abs(differ) > 0.06) {
            return true ;
        }
        if( 0.00 == differ){
            return false; 
        }
        if(Math.abs(differ) < 0.06 ){
            fpkjxx_xmxxs[0].setSE(ValidateUtil.decimalFormat(MathUtil.add(fpkjxx_xmxxs[0].getSE(), String.valueOf(differ)) ,2));
            COMMON_FPKJ_FPT.setKPHJSE(ValidateUtil.decimalFormat(MathUtil.add(COMMON_FPKJ_FPT.getKPHJSE(), String.valueOf(differ)) ,2));
            return false ;
        }
        return true;
        
    }
    
    
	
	
	/**
	 * <p>判断折扣率是否合法</p>
	 * 
	 * @return boolean
	 * @author: lichunhui lichunhui1314@126.com
	 * @date: Created on 2015-8-5 下午03:38:27
	 */
	private boolean checkZklIsLegitimate(String zkhMc){
		String zklString="";
		if(zkhMc.indexOf("(")!= -1 && zkhMc.indexOf(")") != -1){
			zklString = zkhMc.substring(zkhMc.indexOf("(")+1, zkhMc.indexOf(")")).replaceAll("%", "");
		}
		if(zkhMc.indexOf("(")== -1 && zkhMc.indexOf(")") == -1){ // 折扣率如果大于100% 则返回错误信息
			LOGGER.error("不接受中文括号，请确认");
			return false;
		}
		if(zklString.equals("") || Double.parseDouble(zklString) > 100 || Double.parseDouble(zklString) <=0){ // 折扣率如果大于100% 则返回错误信息
			LOGGER.error("折扣率不合法，请确认");
			return false;
		}
		return true;
	}

	/**
	 * 
	 * <p>检查红票数据(即使红票数据校验)</p>
	 * 
	 * @param REQUEST_COMMON_FPKJ
	 * @param COMMON_FPKJ_FPT
	 * @param fpkjxx_xmxxs
	 * @param repository
	 * @return Map<String,String>
	 * @author: lichunhui lichunhui1314@126.com
	 * @throws Exception 
	 * @throws Exception 
	 * @date: Created on 2015-8-5 下午08:27:20
	 */
	private Map<String, String> checkRedInvoiceData(REQUEST_COMMON_FPKJ REQUEST_COMMON_FPKJ,
			COMMON_FPKJ_FPT COMMON_FPKJ_FPT, COMMON_FPKJ_XMXX[] fpkjxx_xmxxs,final EInvoiceSubRepository repository) throws Exception {

		Map<String, String> checkResultMap = new HashMap<String, String>();
		checkResultMap.put(XmlPar.ERRORCODE, XmlPar.RESPONSEYYSSUCCESS);
		
		if (Double.parseDouble(COMMON_FPKJ_FPT.getKPHJJE()) >= 0) {
			checkResultMap = generateErrorMap(COMMON_FPKJ_FPT,XmlPar.ERRORCODE_DATA_FPT_KPHJJE,"红票开票合计金额不能大于或者等于0");
			LOGGER.error(checkResultMap.get(XmlPar.ERRORMSG));
			return checkResultMap;
		}
		
		
		/*if ("0".equals(COMMON_FPKJ_FPT.getTSCHBZ())) { // 0正常冲红
			
		}else { // 非正常冲红  1特殊冲红，即冲红纸质等
			LOGGER.debug("-----------------------------非正常冲红");
			// 特殊冲红其原发票代码和原发票号码可为空
		}*/

		
		/**
		 * 增值税电子发票--允许在没有找到对应蓝票的情况下开具红票
		 * 		1.存在蓝票---对蓝票剩余可冲红金额进行校验
		 * 		2.不存在蓝票---跳过剩余可冲红金额校验，继续后续数据校验
		 */
		/* 通过条件获取发票头信息实体实体信息,包含剩余可冲红金额、是否特殊冲红、开票合计金额，用于查验 */
		final InvoiceHeaderEntity invoiceAmountInfo = repository.getInvoiceAmountInfoForRed(COMMON_FPKJ_FPT.getYFP_DM(),
						COMMON_FPKJ_FPT.getYFP_HM(), COMMON_FPKJ_FPT.getNSRSBH(), invoiceNote.toString());

		if (invoiceAmountInfo == null) { // 不存在对应蓝票
			if("1".equals(SystemConfig.RushRedWay)){//标识:无对应蓝票,本地特殊冲红
				COMMON_FPKJ_FPT.setKPLX("9");
				LOGGER.error("该红票无对应蓝票,自营系统特殊冲红,税号为:"+COMMON_FPKJ_FPT.getNSRSBH()+"原发票代码:"+COMMON_FPKJ_FPT.getYFP_DM()+"原发票号码:"+COMMON_FPKJ_FPT.getYFP_HM());
			}else if("2".equals(SystemConfig.RushRedWay)){//标识：无对应蓝票,跳转到京东云开票系统冲红
				/* 青岛系统冲红,昌益达税号转至京东云系统开票冲红
				 * 青岛大药房本地特殊冲红
				 */
				Boolean tschbz = false;//特殊冲红标识
				if(SystemConfig.nsrsbhTSCH.length()>30){//单个税号长度十八位
					String[] split = SystemConfig.nsrsbhTSCH.split(",");
					for (int i = 0; i < split.length; i++) {
						if(COMMON_FPKJ_FPT.getNSRSBH().equals(split[i])){
							tschbz=true;
						}
					}
				}else if(COMMON_FPKJ_FPT.getNSRSBH().equals(SystemConfig.nsrsbhTSCH)){
					tschbz=true;
				}
				if(tschbz){
					LOGGER.error("该红票无对应蓝票,自营系统本地特殊冲红,税号为:"+COMMON_FPKJ_FPT.getNSRSBH()+"原发票代码:"+COMMON_FPKJ_FPT.getYFP_DM()+"原发票号码:"+COMMON_FPKJ_FPT.getYFP_HM());
				}else{
					checkResultMap = generateErrorMap(COMMON_FPKJ_FPT,XmlPar.ERRORCODE_DATA_FPT_WDYLP,"该红票对应的蓝票不存在,跳转至京东云系统冲红!税号为:"+COMMON_FPKJ_FPT.getNSRSBH()+"原发票代码:"+COMMON_FPKJ_FPT.getYFP_DM()+"原发票号码:"+COMMON_FPKJ_FPT.getYFP_HM());
					LOGGER.error(checkResultMap.get(XmlPar.ERRORMSG));
					return checkResultMap;
				}
			}else if("3".equals(SystemConfig.RushRedWay)){//标识：无对应蓝票,跳转到另一开票系统冲红<成都武汉拆分>
				Boolean tschbz = false;//特殊冲红标识
				if(SystemConfig.nsrsbhTSCH.length()>30){//单个税号长度十八位
					String[] split = SystemConfig.nsrsbhTSCH.split(",");
					for (int i = 0; i < split.length; i++) {
						if(COMMON_FPKJ_FPT.getNSRSBH().equals(split[i])){
							tschbz=true;
						}
					}
				}else if(COMMON_FPKJ_FPT.getNSRSBH().equals(SystemConfig.nsrsbhTSCH)){
					tschbz=true;
				}
				if(tschbz){
					LOGGER.error("该红票无对应蓝票,自营系统特殊冲红,税号为:"+COMMON_FPKJ_FPT.getNSRSBH()+"原发票代码:"+COMMON_FPKJ_FPT.getYFP_DM()+"原发票号码:"+COMMON_FPKJ_FPT.getYFP_HM());
				}else{
					checkResultMap = generateErrorMap(COMMON_FPKJ_FPT,XmlPar.ERRORCODE_DATA_FPT_WDYLPCH,"红票对应的蓝票不存在,转至京东自营新系统冲红!");
					LOGGER.error("该红票无对应蓝票,转至京东自营新系统冲红,税号为:"+COMMON_FPKJ_FPT.getNSRSBH()+",原发票代码:"+COMMON_FPKJ_FPT.getYFP_DM()+"原发票号码:"+COMMON_FPKJ_FPT.getYFP_HM());
					return checkResultMap;
				}
			}
			/* 源代码机制:此类中抛出exception,进入EInvWebControllerImpl中catch. */
			//LOGGER.error("在未知e之前打印异常点: nullPoint - invoiceAmountInfo==null--该红票对应的蓝票不存在");
			/*	//TODO 添加返回无对应蓝票--转发京东云
			checkResultMap = generateErrorMap(COMMON_FPKJ_FPT,XmlPar.ERRORCODE_DATA_FPT_WDYLP,"该红票对应的蓝票不存在请确认!");
			LOGGER.error(checkResultMap.get(XmlPar.ERRORMSG));
			return checkResultMap;*/
			
			//TODO 成都武汉拆分,特殊冲红校验 -FWH-20171023
			/*
			 * 如果是成都税号此处特殊冲红; 如果是武汉税号则打回到新系统去冲红;
			 * 由于是从成都库中将武汉拆分出去,所以保留成都的税号数据特殊冲红  
			 */
			//判断是一个税号冲红还是多个税号冲红
		/*	Boolean tschbz = false;//特殊冲红标识
			if(SystemConfig.nsrsbhTSCH.length()>30){//单个税号长度十八位
				String[] split = SystemConfig.nsrsbhTSCH.split(",");
				for (int i = 0; i < split.length; i++) {
					if(COMMON_FPKJ_FPT.getNSRSBH().equals(split[i])){
						tschbz=true;
					}
				}
			}else if(COMMON_FPKJ_FPT.getNSRSBH().equals(SystemConfig.nsrsbhTSCH)){
				tschbz=true;
			}
			if(tschbz){
				LOGGER.error("该红票无对应蓝票,自营系统特殊冲红,税号为:"+COMMON_FPKJ_FPT.getNSRSBH()+"原发票代码:"+COMMON_FPKJ_FPT.getYFP_DM()+"原发票号码:"+COMMON_FPKJ_FPT.getYFP_HM());
			}else{
				checkResultMap = generateErrorMap(COMMON_FPKJ_FPT,XmlPar.ERRORCODE_DATA_FPT_WDYLPCH,"红票对应的蓝票不存在,转至京东自营新系统冲红!");
				LOGGER.error("该红票无对应蓝票,转至京东自营新系统冲红,税号为:"+COMMON_FPKJ_FPT.getNSRSBH()+"原发票代码:"+COMMON_FPKJ_FPT.getYFP_DM()+"原发票号码:"+COMMON_FPKJ_FPT.getYFP_HM());
				return checkResultMap;
			}*/
			
			
			/*
			 * TODO-FWH-20180119
			 * 青岛系统冲红,昌益达税号转至京东云系统开票冲红
			 * 青岛大药房本地特殊冲红
			 */
			/*Boolean tschbz = false;//特殊冲红标识
			if(SystemConfig.nsrsbhTSCH.length()>30){//单个税号长度十八位
				String[] split = SystemConfig.nsrsbhTSCH.split(",");
				for (int i = 0; i < split.length; i++) {
					if(COMMON_FPKJ_FPT.getNSRSBH().equals(split[i])){
						tschbz=true;
					}
				}
			}else if(COMMON_FPKJ_FPT.getNSRSBH().equals(SystemConfig.nsrsbhTSCH)){
				tschbz=true;
			}
			if(tschbz){
				LOGGER.error("该红票无对应蓝票,自营系统特殊冲红,税号为:"+COMMON_FPKJ_FPT.getNSRSBH()+"原发票代码:"+COMMON_FPKJ_FPT.getYFP_DM()+"原发票号码:"+COMMON_FPKJ_FPT.getYFP_HM());
			}else{
				checkResultMap = generateErrorMap(COMMON_FPKJ_FPT,XmlPar.ERRORCODE_DATA_FPT_WDYLP,"该红票对应的蓝票不存在请确认!"+"原发票代码:"+COMMON_FPKJ_FPT.getYFP_DM()+"原发票号码:"+COMMON_FPKJ_FPT.getYFP_HM());
				LOGGER.error(checkResultMap.get(XmlPar.ERRORMSG));
				return checkResultMap;
			}*/
			
			
			
			
			/*TODO 冲红修改 -FWH-2017-09-28
			 * 业务逻辑：第一次冲红没有对应蓝票,转至纸质发票系统；
			 * 如纸质系统有对应蓝票 将请求报文发送至纸票系统；
			 * 如无对应蓝票，转回自营系统特殊冲红。
			 */
		/*	LOGGER.error("自营系统中该红票对应的蓝票不存在,yfpdm:"+COMMON_FPKJ_FPT.getYFP_DM()+",yfphm"+COMMON_FPKJ_FPT.getYFP_HM());
			REQUEST_ZZFP zzfp = new REQUEST_ZZFP();
			zzfp.setXHF_NSRSBH(COMMON_FPKJ_FPT.getNSRSBH());
			zzfp.setYFP_DM(COMMON_FPKJ_FPT.getYFP_DM());
			zzfp.setYFP_HM(COMMON_FPKJ_FPT.getYFP_HM());
			String jsonString = JSONObject.toJSONString(zzfp);
			//发送请求报文http
			try {
				String reqUrl =SystemConfig.zzfpUrl;
                String res = HttpClientUtil.httpPost(reqUrl, jsonString);//发送至纸票系统
                //解析响应报文
                if(null!=res&&!res.isEmpty()){
	                REQUEST_ZZFP info = (REQUEST_ZZFP) JSON.parseObject(res, REQUEST_ZZFP.class);//返回的信息
	                String resCode =info.getRETURNCODE();
	                String resMes  =info.getRETURNMESSAGE();
	                //* 判断返回code值        * 0000有对应蓝票                * 其他无对应蓝票
	                if(resCode.equals(XmlPar.RESPONSE_SSUCCESS)){
	                	LOGGER.warn("纸质发票系统有对应蓝票,自营系统冲红,原发票代码:"+COMMON_FPKJ_FPT.getYFP_DM()+"原发票号码:"+COMMON_FPKJ_FPT.getYFP_HM()+"税号:"+COMMON_FPKJ_FPT.getNSRSBH());
	                }else{
	                	//纸票系统无对应蓝票,自营系统继续特殊冲红,跳过剩余可冲红金额校验，继续后续数据校验
	                	LOGGER.warn("纸质发票系统返回CODE值:"+resCode+",描述信息为:"+resMes);
	                	LOGGER.error("纸票系统中该红票对应的蓝票不存在,继续校验走特殊冲红;原发票代码:"+COMMON_FPKJ_FPT.getYFP_DM()+"原发票号码:"+COMMON_FPKJ_FPT.getYFP_HM()+"税号:"+COMMON_FPKJ_FPT.getNSRSBH());
	                }
                }else{
                	throw new NullPointerException("纸票系统返回信息为空,地址:"+reqUrl);
                }
			} catch(Exception e) {
				LOGGER.error("发送至纸票系统异常:"+e.getMessage());
				checkResultMap = generateErrorMap(COMMON_FPKJ_FPT,XmlPar.RESPONSE_FAIL,"调用纸票系统异常");
    			LOGGER.error(checkResultMap.get(XmlPar.ERRORMSG));
    			return checkResultMap;//直接返回9999
			}*/
			
			
			
		} else { // 存在对应蓝票

			/**
			 * @Description  增值税电子发票--允许红票冲红金额大于蓝票金额
			 * @author skye.he
			 * @date created at 2015-8-25 上午08:42:14
			 *  	
			 * @begin begin now
			 */
			/* 通过发票头里的原发票代码在数据库中查找对应的蓝票信息（主要是合计不含税金额），用于校验 */
//			String hjbhsje = repository.getBlueInvEntityByYFP_HM(COMMON_FPKJ_FPT.getYFP_DM(),COMMON_FPKJ_FPT.getYFP_HM()) ;
			Double hjbhsje = invoiceAmountInfo.getInfoAmount();  //从数据库获取蓝票的合计不含税金额
			//蓝票的合计不含税金额不等于空,并且蓝票和红票的开票合计金额相等
			if(!StringUtils.isNullOrEmpty(String.valueOf(hjbhsje)) && invoiceAmountInfo.getBillingAmount() == Math.abs(Double.parseDouble(COMMON_FPKJ_FPT.getKPHJJE()))){
				//获取红票的合计不含税金额和蓝票的合计不含税金额的差值
			    //TODO 
			    double differ = MathUtil.sub(String.valueOf(Math.abs(Double.parseDouble(COMMON_FPKJ_FPT.getHJBHSJE()))), String.valueOf(hjbhsje));
				if(differ > 0){//红票的不含税金额大于蓝票的不含税金额
					//对红票进行金额调整
					REQUEST_COMMON_FPKJ = resetRedInvoice(REQUEST_COMMON_FPKJ,COMMON_FPKJ_FPT,fpkjxx_xmxxs,differ) ;
					COMMON_FPKJ_FPT = REQUEST_COMMON_FPKJ.getCOMMON_FPKJ_FPT() ;
					fpkjxx_xmxxs = REQUEST_COMMON_FPKJ.getCOMMON_FPKJ_XMXXS() ;
				}
			}
			/**
			 * @end here is end
			 * @date created at 2015-8-25 上午08:45:10
			 */
			
			// 验证原票的剩余可冲红金额是否等于0 如果等于0 不能再次冲红
			if (invoiceAmountInfo.getRemainingRedAmount() == 0) { // 蓝票的剩余可冲红金额等于0
				checkResultMap = generateErrorMap(COMMON_FPKJ_FPT,XmlPar.ERRORCODE_DATA_FPT_CHJEZERO,"此红票的原发票金额已冲完，不能再冲红");
				LOGGER.error(checkResultMap.get(XmlPar.ERRORMSG));
				return checkResultMap;
			} else if (Math.abs(invoiceAmountInfo.getRemainingRedAmount()) - Math.abs(Double.parseDouble(COMMON_FPKJ_FPT.getKPHJJE())) < 0) {
				// 蓝票的剩余可冲红金额 - 冲红金额  < 0 ====说明“冲红金额” 大于 “蓝票的剩余可冲红金额”,此情况不允许冲红
				checkResultMap = generateErrorMap(COMMON_FPKJ_FPT,XmlPar.ERRORCODE_DATA_FPT_CHJESMALL,"此红票的开票金额大于原发票剩余可冲红金额，不能冲红");
				LOGGER.error(checkResultMap.get(XmlPar.ERRORMSG));
				return checkResultMap;
			}else {
				LOGGER.debug("冲红金额 小于或者等于 蓝票可剩余冲红金额，允许冲红");
			}
			
			if ("0".equals(invoiceAmountInfo.getSpecRedInvoiceFlag())) {
				checkResultMap = generateErrorMap(COMMON_FPKJ_FPT,XmlPar.ERRORCODE_DATA_FPT_TSCHBZ,"此蓝票的对应红票已开具，不能重复冲红");
				LOGGER.error(checkResultMap.get(XmlPar.ERRORMSG));
				return checkResultMap;
			}
			
			// 20 退货折让红票 目前只支持全部冲红
			// 21 错票重开红票 目前只支持全部冲红
			// 22 换票冲红 目前只支持全部冲红
			if ("20".equals(COMMON_FPKJ_FPT.getCZDM()) || 
				"21".equals(COMMON_FPKJ_FPT.getCZDM()) ||
				"22".equals(COMMON_FPKJ_FPT.getCZDM()) ) {
				if ((Double.parseDouble(COMMON_FPKJ_FPT.getKPHJJE().replace("-", "")) != invoiceAmountInfo.getBillingAmount())) {
					checkResultMap = generateErrorMap(COMMON_FPKJ_FPT,XmlPar.ERRORCODE_DATA_FPT_CZDM,"开具红字发票金额不等于正数发票金额请确认!");
					LOGGER.error(checkResultMap.get(XmlPar.ERRORMSG));
					return checkResultMap;
				}
			}
		}

		if (!ValidateUtil.checkParameterIsEmpty(COMMON_FPKJ_FPT.getCHYY())) {
			if (ValidateUtil.checkParameterLength(COMMON_FPKJ_FPT.getCHYY(), 0,190)) {
				checkResultMap = generateErrorMap(COMMON_FPKJ_FPT,XmlPar.ERRORCODE_DATA_FPT_CHYY,"发票冲红原因长度存在问题");
				LOGGER.error(checkResultMap.get(XmlPar.ERRORMSG));
				return checkResultMap;
			}
		}
		
		checkResultMap = this.checkDetailsDiscount(COMMON_FPKJ_FPT, fpkjxx_xmxxs, COMMON_FPKJ_FPT.getKPLX());
		
		/**
		 * 数据校验通过,进行合并折扣行
		 */
		fpkjxx_xmxxs =processInsertRedInvoiceDetail(fpkjxx_xmxxs);
		
		/**
		 * 红票合并折扣行之后在进行一次明细的校验。
		 */
		//checkResultMap = this.checkDetailsDiscount(COMMON_FPKJ_FPT, fpkjxx_xmxxs, COMMON_FPKJ_FPT.getKPLX());
		
		//合并折扣行之后,校验明细行商品金额之和是否和合计不含税金额相等,不相等进行金额调整.
		
		REQUEST_COMMON_FPKJ.setCOMMON_FPKJ_XMXXS(processRedInvoiceDetailForCheckListSumAmount(fpkjxx_xmxxs, COMMON_FPKJ_FPT));
		fpkjxx_xmxxs = REQUEST_COMMON_FPKJ.getCOMMON_FPKJ_XMXXS() ;
		return checkResultMap;
	}

	/**
	 * <p>生成错误map</p>
	 * 
	 * @param errorCode 错误代码
	 * @param errorMsg 错误消息
	 * @return Map<String,String>
	 * @author: lichunhui lichunhui1314@126.com
	 * @date: Created on 2015-8-4 下午08:51:48
	 */
	public Map<String, String> generateErrorMap(String errorCode,
			String errorMsg) {
		Map<String, String> errorMap = new HashMap<String, String>();
		errorMap.put(XmlPar.ERRORCODE, errorCode);
		errorMap.put(XmlPar.ERRORMSG, errorMsg);
		return errorMap;
	}
	
	/**
	 * <p>生成错误map</p>
	 * 
	 * @param errorCode 错误代码
	 * @param errorMsg 错误消息
	 * @return Map<String,String>
	 * @author: lichunhui lichunhui1314@126.com
	 * @date: Created on 2015-8-4 下午08:51:48
	 */
	public Map<String, String> generateErrorMap(COMMON_FPKJ_FPT COMMON_FPKJ_FPT,String errorCode,
			String errorMsg) {
		StringBuffer sbBuffer = new StringBuffer("请求流水号：");
		sbBuffer.append(COMMON_FPKJ_FPT.getFPQQLSH()).append(" ").append(errorMsg);
		Map<String, String> errorMap = new HashMap<String, String>();
		errorMap.put(XmlPar.ERRORCODE, errorCode);
		errorMap.put(XmlPar.ERRORMSG, sbBuffer.toString());
		return errorMap;
	}
	/**
	 * @author skye.he
	 * @date created at 2015-8-25 上午08:52:41 
	 * @Description 
	 * 			获取这个大于的数值(differ) 先用第一个商品行的合计金额减去这个数值
	 * 			判断是否小于0,如果小于0,就在第一个商品行处理
	 * 					把第一个商品行的合计金额修改成原来的金额加上这个数值的结果（由于红票金额是-，减去就是+）
	 * 					然后税额加上这个数值的结果（税额也是-，加上就是-）
	 * 			如果第一个商品行不满足那个条件,就用第二个商品行去计算,以此类推
	 * 			如果最后一个商品行依旧不能处理,抛异常,返回异常信息
	 * 
	 * 			红票的外层的合计不含税金额赋值成(原来的合计不含税金额减去差值注意正负号)
	 * 			合计税额赋值成原来的合计税额加上差值注意正负号),然后把数据往下层推送。
	 * 
	 *
	 * @param REQUEST_COMMON_FPKJ 红票内层报文信息
	 * @param COMMON_FPKJ_FPT 红票发票头信息
	 * @param fpkjxx_xmxxs	红票项目信息
	 * @param differ	冲红金额跟蓝票金额之差（红票多冲的钱）
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("null")
	private REQUEST_COMMON_FPKJ resetRedInvoice(REQUEST_COMMON_FPKJ REQUEST_COMMON_FPKJ,COMMON_FPKJ_FPT COMMON_FPKJ_FPT
			, COMMON_FPKJ_XMXX[] fpkjxx_xmxxs,double differ) throws Exception{
		//发票头信息的合金不含税金额和税额的调整
	    //TODO 有误差
		double newHjbhsje = Double.parseDouble(COMMON_FPKJ_FPT.getHJBHSJE()) + differ ;
		double newHjse = Double.parseDouble(COMMON_FPKJ_FPT.getKPHJSE()) - differ ;
		if(fpkjxx_xmxxs != null || fpkjxx_xmxxs.length > 0){
			for(int i=0;i<fpkjxx_xmxxs.length;i++){
			    //发票明细行信息的不含税金额和税额的调整
				double newXmje = Double.parseDouble(fpkjxx_xmxxs[i].getXMJE()) + differ ;
				double newSe = Double.parseDouble(fpkjxx_xmxxs[i].getSE()) - differ ;
				//如果当前行金额够调整,进行调整,如果不够就直接抛异常
				if(newXmje < 0){
					fpkjxx_xmxxs[i].setXMJE(String.valueOf(ValidateUtil.decimalFormat(newXmje,2))) ;
					fpkjxx_xmxxs[i].setSE(String.valueOf(ValidateUtil.decimalFormat(newSe,2))) ;
					break ;
				}else if(i == fpkjxx_xmxxs.length-1){
					throw new Exception("冲红发票各商品行的项目金额都小于0，无法冲红！") ;
				}
			}
		}
		//发票头信息赋值
		COMMON_FPKJ_FPT.setHJBHSJE(String.valueOf(ValidateUtil.decimalFormat(newHjbhsje,2))) ;
		COMMON_FPKJ_FPT.setKPHJSE(String.valueOf(ValidateUtil.decimalFormat(newHjse,2))) ;
		
		REQUEST_COMMON_FPKJ.setCOMMON_FPKJ_XMXXS(fpkjxx_xmxxs) ;
		REQUEST_COMMON_FPKJ.setCOMMON_FPKJ_FPT(COMMON_FPKJ_FPT) ;
		return REQUEST_COMMON_FPKJ ;
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
                    if ((eachBeanj.getXMMC().startsWith("折扣行数") || eachBeanj.getXMMC().startsWith("折扣"))){
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
                    }else if ("1".equals(eachBeanj.getFPHXZ())) {
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
                   /* double xmje = Double.parseDouble(ValidateUtil.decimalFormat(MathUtil.mul(String.valueOf(-1*(Math.abs(Double.parseDouble(String.valueOf(eachBean.getXMJE()))))), String.valueOf(1-firstzk)),2));
                    double se = Double.parseDouble(ValidateUtil.decimalFormat(MathUtil.mul(String.valueOf(-1*Math.abs(Double.parseDouble(String.valueOf(eachBean.getSE())))), String.valueOf(1- firstzk)),2));
                    double xmdj = MathUtil.div(String.valueOf(MathUtil.mul(String.valueOf(-1*(Math.abs(Double.parseDouble(String.valueOf(eachBean.getXMJE()))))), String.valueOf(1-firstzk))) , String.valueOf(eachBean.getXMSL()).replace("-", ""), 8);
                    xmljzkje += Double.parseDouble(ValidateUtil.decimalFormat(MathUtil.mul(String.valueOf(Math.abs(Double.parseDouble(String.valueOf(eachBean.getXMJE()))))  , String.valueOf(firstzk)),2));
                    xmljzkse +=  Double.parseDouble(ValidateUtil.decimalFormat(MathUtil.mul(String.valueOf(Math.abs(Double.parseDouble(String.valueOf(eachBean.getSE())))), String.valueOf(firstzk)),2));
                    */
                    
                    //TODO 四舍五不入（xmje  se）
                    double tttt = MathUtil.mul(String.valueOf(-1*(Math.abs(Double.parseDouble(String.valueOf(eachBean.getXMJE()))))), String.valueOf(1-firstzk));
                    BigDecimal t = new BigDecimal(tttt);
 //                   BigDecimal xmje = t.setScale(2, BigDecimal.ROUND_HALF_EVEN);
                    BigDecimal xmje = t.setScale(2, BigDecimal.ROUND_DOWN);
                    
                    //double xmje = Double.parseDouble(ValidateUtil.decimalFormat(MathUtil.mul(String.valueOf(-1*(Math.abs(Double.parseDouble(String.valueOf(eachBean.getXMJE()))))), String.valueOf(1-firstzk)),2));
                    //double se = Double.parseDouble(ValidateUtil.decimalFormat(MathUtil.mul(String.valueOf(-1*Math.abs(Double.parseDouble(String.valueOf(eachBean.getSE())))), String.valueOf(1- firstzk)),2));
                    double sss = MathUtil.mul(String.valueOf(-1*Math.abs(Double.parseDouble(String.valueOf(eachBean.getSE())))), String.valueOf(1- firstzk));
                    BigDecimal s = new BigDecimal(sss);
 //                   BigDecimal se = s.setScale(2, BigDecimal.ROUND_HALF_EVEN);
                    BigDecimal se = s.setScale(2, BigDecimal.ROUND_DOWN);
                    //======================
                    double xmdj = MathUtil.div(String.valueOf(tttt) , String.valueOf(eachBean.getXMSL()).replace("-", ""), 8);
                    /*                   
                    double aaa = MathUtil.mul(String.valueOf(Math.abs(Double.parseDouble(String.valueOf(eachBean.getXMJE()))))  , String.valueOf(firstzk));
                    BigDecimal ljje = new BigDecimal(String.valueOf(aaa));
                    xmljzkje += Double.parseDouble(ValidateUtil.decimalFormat(String.valueOf(ljje.setScale(2, BigDecimal.ROUND_HALF_EVEN)),2));
                   
                    double bbb = MathUtil.mul(String.valueOf(Math.abs(Double.parseDouble(String.valueOf(eachBean.getSE()))))  , String.valueOf(firstzk));
                    BigDecimal ljse = new BigDecimal(String.valueOf(bbb));
                    xmljzkse += Double.parseDouble(ValidateUtil.decimalFormat(String.valueOf(ljse.setScale(2, BigDecimal.ROUND_HALF_EVEN)),2));
                    */
                    
                   xmljzkje += Double.parseDouble(ValidateUtil.decimalFormat(MathUtil.mul(String.valueOf(Math.abs(Double.parseDouble(String.valueOf(eachBean.getXMJE()))))  , String.valueOf(firstzk)),2));
                   xmljzkse +=  Double.parseDouble(ValidateUtil.decimalFormat(MathUtil.mul(String.valueOf(Math.abs(Double.parseDouble(String.valueOf(eachBean.getSE())))), String.valueOf(firstzk)),2));
                   
//                   xmljzkje += Double.parseDouble(ValidateUtil.decimalFormat(MathUtil.mul(String.valueOf(Math.abs(Double.parseDouble(String.valueOf(eachBean.getXMJE()))))  , String.valueOf(firstzk)),3));
//                   xmljzkse +=  Double.parseDouble(ValidateUtil.decimalFormat(MathUtil.mul(String.valueOf(Math.abs(Double.parseDouble(String.valueOf(eachBean.getSE())))), String.valueOf(firstzk)),3));
    
                    
                    eachBean.setXMJE(String.valueOf(xmje));
                    eachBean.setSE(String.valueOf(se));
                    eachBean.setXMDJ(String.valueOf(xmdj));
                    eachBean.setFPHXZ("0");
                } else if (i == zkhs - 1 && zk) {   //判断折扣行数n-1行数据处理
                    /*
                     * 因为项目金额是负的,项目总结额是正的,项目累计折扣金额是负的,后面括号里面获得的值是正的,项目金额的绝对值大于括号里值的绝对值
                     * ,所以这里使用加号
                     */
                    //该行为折扣行数的n-1行,如果该行数据的金额不够多余的减的话,那么就找最大行金额的减去这个差值,
                    if((MathUtil.sub(String.valueOf(Math.abs(Double.valueOf(eachBean.getXMJE()))), String.valueOf(MathUtil.sub(String.valueOf(xmzje), String.valueOf(Math.abs(xmljzkje))))))<=0){//如果折扣率为0,并且最后一行,不够补充的,则补充到金额最大行中
                    	protocolProjectBean[maxxmjeszh].setXMJE(ValidateUtil.decimalFormat(-1*MathUtil.add(protocolProjectBean[maxxmjeszh].getXMJE(), String.valueOf(MathUtil.sub(String.valueOf(xmzje), String.valueOf(Math.abs(xmljzkje))))),2));
                        protocolProjectBean[maxxmjeszh].setXMDJ(ValidateUtil.decimalFormat(MathUtil.div(protocolProjectBean[maxxmjeszh].getXMJE(), eachBean.getXMSL(), 8),8));
                        protocolProjectBean[maxxmjeszh].setSE(ValidateUtil.decimalFormat(-1*MathUtil.add(protocolProjectBean[maxxmjeszh].getSE(), String.valueOf(MathUtil.sub(String.valueOf(xmzse), String.valueOf(Math.abs(xmljzkse))))),2));
                        protocolProjectBean[maxxmjeszh].setFPHXZ("0");
                        maxxmjeszh=zkhs+1;
                    }else{
                        //被折扣行n-1行处理逻辑:该行金额为折扣行金额减去前面几行的累加金额,该行税额为折扣行税额减去前面几行的累加税额,并格式化,反算单价,
                    	double xmje = Double.parseDouble(ValidateUtil.decimalFormat(-1*(MathUtil.sub(String.valueOf(Math.abs(Double.parseDouble(String.valueOf(eachBean.getXMJE())))) , String.valueOf(Math.abs(MathUtil.sub(String.valueOf(xmljzkje) , String.valueOf(xmzje)))))),2));
                        double se = Double.parseDouble(ValidateUtil.decimalFormat(-1*(MathUtil.sub(String.valueOf(Math.abs(Double.parseDouble(String.valueOf(eachBean.getSE())))) , String.valueOf(Math.abs(MathUtil.sub(String.valueOf(xmljzkse), String.valueOf(xmzse)) )))),2));
                        double xmdj = MathUtil.div(String.valueOf(-1*(MathUtil.sub(String.valueOf(Math.abs(Double.parseDouble(String.valueOf(eachBean.getXMJE())))) ,  String.valueOf(Math.abs(MathUtil.sub(String.valueOf(xmljzkje)  , String.valueOf(xmzje))))))) ,  String.valueOf(eachBean.getXMSL()).replace("-", ""), 8);
                      
                       
                        
                        //累计金额置零,防止出现多个折扣行数的商品无法计算的问题
                        xmljzkje = 0.00;
                        xmljzkse = 0.00;
                        eachBean.setXMJE(String.valueOf(xmje));
                        eachBean.setSE(String.valueOf(se));
                        eachBean.setXMDJ(String.valueOf(xmdj));
                        eachBean.setFPHXZ("0");
                    }
                } else if (zks && zkhs-i ==zkhscount ) {    //折扣行对应的商品行的处理

                    //单行折扣处理逻辑:把折扣行合并到被折扣行中,格式化金额和税额,反算单价
                    double xmje = Double.parseDouble(ValidateUtil.decimalFormat(MathUtil.add(String.valueOf(-1*Math.abs(Double.parseDouble(String.valueOf(eachBean.getXMJE())))) , String.valueOf(xmzje)),2));
                    double se = Double.parseDouble(ValidateUtil.decimalFormat(MathUtil.add(String.valueOf(-1*Math.abs(Double.parseDouble(String.valueOf(eachBean.getSE())))) , String.valueOf(xmzse)),2));
                    double xmdj = MathUtil.div(String.valueOf(-1*Math.abs(MathUtil.add(eachBean.getXMJE() , String.valueOf(xmzje)))), String.valueOf(eachBean.getXMSL()).replace("-", ""), 8);
                    eachBean.setXMJE(String.valueOf(xmje));
                    eachBean.setSE(String.valueOf(se));
                    eachBean.setXMDJ(String.valueOf(xmdj));
                    eachBean.setFPHXZ("0");//合并折扣行之后红票发票行性质（FPHXZ）必须为0
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
	
	/**
     * 
     * <p>红票合并折扣行之后校验红票明细行累计金额之和是否和合计不含税金额相等,不相等的话,进行调整</p>
     * 
     * @param protocolProjectBean
     * @param protocalFpt
     * @return COMMON_FPKJ_XMXX[]
     * @author: 张双超
     * @throws Exception 
     * @date: Created on Sep 16, 2015 4:11:04 PM
     */
    private static COMMON_FPKJ_XMXX[] processRedInvoiceDetailForCheckListSumAmount(COMMON_FPKJ_XMXX[] protocolProjectBean , final COMMON_FPKJ_FPT protocalFpt) throws Exception {//获取发票头信息中的合计不含税金额
        double hjbhsje = Double.parseDouble(protocalFpt.getHJBHSJE());
        //获取发票头信息中的合计税额
        double hjse = Double.parseDouble(protocalFpt.getKPHJSE());
        //明细行总金额
        double mxzje = 0.00;
        //明细行总税额
        double mxzse = 0.00;
        //合计不含税金额和明细总金额的差值
        double differje = 0.00;
        //合计税额和明细总税额的差值
        double differse = 0.00;
        if (protocolProjectBean != null && protocolProjectBean.length > 0) {
            for (int i = 0; i < protocolProjectBean.length; i++) {
                final COMMON_FPKJ_XMXX fpkjxx_xmxxs = protocolProjectBean[i];
                //TODO 改造，将绝对值去掉 2016年12月21日19:57:30 ya
//                mxzje += Math.abs(Double.parseDouble(ValidateUtil.decimalFormat(fpkjxx_xmxxs.getXMJE(), 2)));
//                mxzse += Math.abs(Double.parseDouble(ValidateUtil.decimalFormat(fpkjxx_xmxxs.getSE(), 2)));
                
                mxzje += Double.parseDouble(ValidateUtil.decimalFormat(fpkjxx_xmxxs.getXMJE(), 2));
                mxzse += Double.parseDouble(ValidateUtil.decimalFormat(fpkjxx_xmxxs.getSE(), 2));
                
               
                
            }
//            differje = Double.valueOf(ValidateUtil.decimalFormat(Math.abs(hjbhsje) - mxzje,2));
//            differse = Double.valueOf(ValidateUtil.decimalFormat(Math.abs(hjse) - mxzse,2));
            
            
  			differje = Double.valueOf(ValidateUtil.decimalFormat(Math.abs(Math.abs(hjbhsje) - Math.abs(mxzje)),2));
            differse = Double.valueOf(ValidateUtil.decimalFormat(Math.abs(Math.abs(hjse) - Math.abs(mxzse)),2));
 
/*            differje = Double.valueOf(ValidateUtil.decimalFormat(MathUtil.sub(String.valueOf(hjbhsje), String.valueOf(mxzje)), 2));
            differse = Double.valueOf(ValidateUtil.decimalFormat(MathUtil.sub(String.valueOf(hjse), String.valueOf(mxzse)), 2));
*/
            //如果合计不含税金额和明细总金额的差值 大于0 并且税额差值大于0,进行金额调整
            if(differje != 0 ||  differse != 0 ){
                /**
                 * 倒着循环,去商品行中做金额调整,如果当前行金额调整之后小于0,调整完成,跳出循环,如果循环到第一行,调整金额仍大于等于0 ,抛异常
                 */
                for(int i = protocolProjectBean.length-1;i>=0;i-- ){
                	 //发票明细行信息的不含税金额和税额的调整
 /*              	Double newXmje = null;
                	Double newSe = null;
                   
                	if (differje > 0) {
                         newXmje = Double.parseDouble(protocolProjectBean[i].getXMJE()) - differje ;
					} else {
                         newXmje = Double.parseDouble(protocolProjectBean[i].getXMJE()) + differje ;
					}
                	
                	if (differse > 0) {
                		newSe = Double.parseDouble(protocolProjectBean[i].getSE()) - differse ;
					} else {
						newSe = Double.parseDouble(protocolProjectBean[i].getSE()) + differse ;
					}
*/					
 //                   newXmje = Double.parseDouble(protocolProjectBean[i].getXMJE()) + differje ;
//					newSe = Double.parseDouble(protocolProjectBean[i].getSE()) + differse ;
					
					 double newXmje = Double.parseDouble(protocolProjectBean[i].getXMJE()) - differje;
			          double newSe = Double.parseDouble(protocolProjectBean[i].getSE()) - differse;

                    //如果当前行金额够调整,进行调整,如果不够就直接抛异常
                    
                    if(newXmje < 0){
                        protocolProjectBean[i].setXMJE(String.valueOf(ValidateUtil.decimalFormat(newXmje,2))) ;
                        protocolProjectBean[i].setSE(String.valueOf(ValidateUtil.decimalFormat(newSe,2))) ;
                        break ;
                    }else if(i == 0){
                        if(newXmje >= 0){
                            
                            throw new Exception("冲红发票各商品行的项目金额都小于0，无法冲红！") ;
                        }
                    }
                }
            }
        }
        return protocolProjectBean;
}

    
    /**
     * 购买方纳税人识别号校验规则
     * @param nsrsbh
     * @return 返回空表示不符合规则, 返回非空表示符合规则
     * 2017-11-30
     */
    private  String getGhfNsrsbhByRules(String nsrsbh) {
    	
    	//对税号中是否包含中文进行校验
	    Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
	    Matcher m = p.matcher(nsrsbh);
	    if(m.find()) return "";
    	
        //税号不能小写校验
        for(int i = 0; i < nsrsbh.length(); i++){
            if (Character.isLowerCase(nsrsbh.charAt(i)))  return "";//不能有小写
        }

       if ((nsrsbh.length() > 5) && (nsrsbh.length() < 16)) {
            for (int i = 0; i < nsrsbh.length(); i++){
                if ((!Character.isLetter(nsrsbh.charAt(i))) && (!Character.isDigit(nsrsbh.charAt(i)))){
                    return "";
                }
            }
        } else if(nsrsbh.length() > 20){
            return "";
        }else {
            if ((nsrsbh.length() > 15) && (nsrsbh.length() < 19)) {
                if ((getGhfNsrsbhResultByRules(0, 6, nsrsbh)) || (getGhfNsrsbhResultByRules(1, 6, nsrsbh)) || (getGhfNsrsbhResultByRules(2, 6, nsrsbh))) {
                    return nsrsbh;
                }
                return "";
            }

            if ((nsrsbh.length() > 18) && (nsrsbh.length() < 21)) {
                if ((getGhfNsrsbhSFZResultByRules(15, nsrsbh)) || (getGhfNsrsbhSFZResultByRules(18, nsrsbh)) || (getGhfNsrsbhResultByRules(0, 6, nsrsbh))
                        || (getGhfNsrsbhSFZByRules(15, nsrsbh)) || (getGhfNsrsbhSFZByRules(18, nsrsbh))) {
                    return nsrsbh;
                }
                return "";
            }
            return "000000000000000";
        }
        return nsrsbh;
    }

    private  boolean getGhfNsrsbhResultByRules(int a, int b, String nsrsbh) {
        for (int i = 0; i < a; i++) {
            if (!Character.isLetter(nsrsbh.charAt(i))) {
                return false;
            }
        }
        for (int i = a; i < b; i++) {
            if (!Character.isDigit(nsrsbh.charAt(i))) {
                return false;
            }
        }
        for (int i = b; i < nsrsbh.length(); i++) {
        	
        	if(nsrsbh.length()>18){
        		if(("O".equals(String.valueOf(nsrsbh.charAt(i))))  || ("I".equals(String.valueOf(nsrsbh.charAt(i))))
        			 || ("S".equals(String.valueOf(nsrsbh.charAt(i)))) || ("Z".equals(String.valueOf(nsrsbh.charAt(i)))) ){
        			return false;
        		}
        	}
        	
            if ((!Character.isDigit(nsrsbh.charAt(i))) && (!Character.isLetter(nsrsbh.charAt(i)))) {
                return false;
            }
        }
        return true;
    }

    private   boolean getGhfNsrsbhSFZResultByRules(int a, String nsrsbh) {
        for (int i = 0; i < a - 1; i++) {
            if (!Character.isDigit(nsrsbh.charAt(i))) {
                return false;
            }
        }
        if ((Character.isLetter(nsrsbh.charAt(a - 1))) 
                && (!"X".equals(String.valueOf(nsrsbh.charAt(a - 1))))) {
            return false;
        }
        for (int i = a-1; i < nsrsbh.length(); i++) {
            if ((!Character.isDigit(nsrsbh.charAt(i))) && (!Character.isLetter(nsrsbh.charAt(i)))) {
                return false;
            }
        }
        return true;
    }

    private  boolean getGhfNsrsbhSFZByRules(int a, String nsrsbh) {
        if (!Character.isLetter(nsrsbh.charAt(0))) {
            return false;
        }
        for (int i = 1; i < a - 1; i++) {
            if (!Character.isDigit(nsrsbh.charAt(i))) {
                return false;
            }
        }
        if ((Character.isLetter(nsrsbh.charAt(a)))  && (!"X".equals(String.valueOf(nsrsbh.charAt(a))))) {
            return false;
        }
        for (int i = a-1; i < nsrsbh.length(); i++) {
            if ((!Character.isDigit(nsrsbh.charAt(i))) && (!Character.isLetter(nsrsbh.charAt(i)))) {
                return false;
            }
        }
        return true;
    }
    
}
