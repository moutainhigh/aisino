package com.aisino.domain.einvoice.impl;

import static com.aisino.domain.security.SecurityConstant.Result_ER;
import static com.aisino.domain.security.SecurityConstant.Result_OK;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aisino.common.util.FpztConstants;
import com.aisino.common.util.FpztException;
import com.aisino.common.util.TranslateUtil;
import com.aisino.domain.AbstractBaseService;
import com.aisino.domain.SystemConfig;
import com.aisino.domain.einvoice.CommonPacketService;
import com.aisino.domain.einvoice.InvoicePDFProducerService;
import com.aisino.domain.einvoice.entity.EShopInfo;
import com.aisino.domain.einvoice.entity.InvoiceEntity;
import com.aisino.domain.einvoice.entity.InvoiceHeaderEntity;
import com.aisino.domain.einvoice.entity.InvoicePDFEntity;
import com.aisino.domain.einvoice.pdf.AddStampRec;
import com.aisino.domain.einvoice.pdf.Client;
import com.aisino.domain.einvoice.pdf.LoadBalancing;
import com.aisino.domain.einvoice.utils.ChineseCurrency;
import com.aisino.domain.einvoice.utils.WebUtil;
import com.aisino.domain.security.InvSign;
import com.aisino.domain.security.SecurityCodeException;
import com.aisino.domain.security.SecurityConstant;
import com.aisino.domain.security.entity.CommonPacket;
import com.aisino.domain.security.entity.UserRequestPacket;
import com.aisino.domain.security.entity.UserResponsePacket;
import com.aisino.fpqz.domain.Jar_fpqz_info;
import com.aisino.fpqz.domain.Jar_fpqz_kj;
import com.aisino.fpqz.domain.Jar_fpqz_kjmx;
import com.aisino.itext.ItestFactory;
import com.aisino.itext.dao.IItextDao;
import com.aisino.itext.pojo.PdfOut;
import com.aisino.itext.pojo.StencilPlageMx;
import com.aisino.itext.pojo.StencilPlate;
import com.aisino.itext.util.PdfOutFileUtil;
import com.aisino.protocol.bean.FPKJXX_FPJGXX;
import com.aisino.protocol.bean.FPKJXX_XMXX;
import com.google.common.base.Strings;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mysql.jdbc.StringUtils;

/**
 * Created by Martin.Ou on 2014/9/15.
 * <p/>
 * 生成PDF文件服务接口实现类
 * 
 * @see com.aisino.domain.AbstractBaseService
 */

public final class InvoicePDFProducerServiceImpl extends AbstractBaseService implements InvoicePDFProducerService {

	private static Gson gson = new GsonBuilder().create();
	
	
    private static final Logger LOGGER = LoggerFactory.getLogger(InvoicePDFProducerServiceImpl.class);

    private CommonPacketService commonPacketService;

    private IItextDao textDao;

    public void setCommonPacketService(CommonPacketService commonPacketService) {
        this.commonPacketService = commonPacketService;
    }

    public void setTextDao(IItextDao textDao) {
        this.textDao = textDao;
    }

    @Override
    public String generatePdfFile(InvoicePDFEntity pdfEntity) throws Exception {
    	/*//判断是否加
	    if(SystemConfig.flagAd.equals("y")){
	        // 获取传递pdf参数
	        final InvoiceHeaderEntity pdfHeader = pdfEntity.getInvoiceHeaderEntity();
	        final FPKJXX_FPJGXX fpkjxx_fpjgxx = pdfEntity.getFpkjxxFpjgxx();
	        final EShopInfo eShopInfo = pdfEntity.geteShopInfo();
	        final FPKJXX_XMXX[] fpkjxx_xmxxes = pdfEntity.getFpkjxxXmxxes();
	        // 组织pdf文件协议数据、生产pdf文件方法
	        final Map<String, Object> qzMap = new HashMap<String, Object>();
	        Map<String, Object> qzAdMap = new HashMap<String, Object>();
	        final StencilPlate stencilPlate = generatePdfFptxx(pdfHeader, fpkjxx_fpjgxx, eShopInfo, fpkjxx_xmxxes);
	        if (LOGGER.isDebugEnabled()) {
	            LOGGER.debug("StencilPlate, nsrsbh:{}", stencilPlate.getGhdwnsrsbh());
	        }
//	        byte[] fileByte = ItestFactory.getDocument().producePDF(stencilPlate, textDao, qzMap);
	        byte[] fileByte = null;
	        stencilPlate.setStencilPlageMxs(ItestFactory.rebuild(stencilPlate.getStencilPlageMxs()));
	        AdService adService=AdService.getInstance();
	    	com.dxhy.pdf.bean.StencilPlate stencilPlate2=new com.dxhy.pdf.bean.StencilPlate();
	    	int length =  stencilPlate.getStencilPlageMxs().length;
	    	com.dxhy.pdf.bean.StencilPlageMx[] sencilPlangeMxs = new com.dxhy.pdf.bean.StencilPlageMx[length];
	    	BeanUtils.copyProperties(stencilPlate, stencilPlate2);
	    	//把明细信息更新到jar包中的类
	    	for(int i=0;i<length;i++){
	    		StencilPlageMx localMx = stencilPlate.getStencilPlageMxs()[i];
	    		com.dxhy.pdf.bean.StencilPlageMx jarMx = new com.dxhy.pdf.bean.StencilPlageMx();
	    		BeanUtils.copyProperties(localMx, jarMx);
	    		sencilPlangeMxs[i]=jarMx;
	    	}
	    	stencilPlate2.setStencilPlageMxs(sencilPlangeMxs);
	    	try {
	    		LOGGER.debug("stencilPlate2的发票代码为{}，发票号码为{},电商平台编码{}",stencilPlate2.getFp_dm(),stencilPlate2.getFp_hm(),eShopInfo.getEshopCode());
	    		qzAdMap = adService.pdfProduceAddAd(eShopInfo.getEshopCode(), stencilPlate2);
			} catch (AdException e1) {
				LOGGER.debug("调用生成广告pdf异常,异常信息为：{}",e1);
			}
	    	if(qzAdMap.get("fpfile")==null){
	    		if (LOGGER.isDebugEnabled()) {
	    		LOGGER.debug("调用普通生成pdf方法");
	    		}
	    		fileByte = com.aisino.itext.ItestFactory.getDocument().producePDF(stencilPlate, textDao, qzMap);
	    	}
	        // 是否对PDF文件添加签章
	        if ("Y".equals(SystemConfig.pdfAppend)) {
	        	if (LOGGER.isDebugEnabled()) {
	        		LOGGER.debug("开始调用签章方法");
	        		}
	        	if(qzAdMap.get("fpfile")==null){
	        		if (LOGGER.isDebugEnabled()) {
	        			LOGGER.debug("普通pdf开始调用签章方法");
	        		}
	        		fileByte = appendPdfSignature(pdfEntity, fileByte, qzMap);
	        	}else{
	        		if (LOGGER.isDebugEnabled()) {
	        			LOGGER.debug("广告pdf开始调用签章方法");
	        		}
	        		fileByte=(byte[]) qzAdMap.get("fpfile");
	        		fileByte = appendPdfSignature(pdfEntity, fileByte, qzAdMap);
	        	}
	        }
	        // 生产pdf文件
	        final PdfOut pdfout = new PdfOut();
	        pdfout.setFileName(fpkjxx_fpjgxx.getFP_DM() + fpkjxx_fpjgxx.getFP_HM() + ".pdf");
	        pdfout.setFphm(fpkjxx_fpjgxx.getFP_HM());
	        final InvoiceEntity invoiceEntity = pdfHeader.getInvoiceEntity();
	        final DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd");
	        final DateTime dt = new DateTime(invoiceEntity.getBillingDate());
	        pdfout.setKprq(fmt.print(dt));
	        if(qzAdMap.get("fpfile")!=null&&"N".equals(SystemConfig.pdfAppend)){
	        	if (LOGGER.isDebugEnabled()) {
	    			LOGGER.debug("使用未签章的生成广告的pdf");
	    		}
	        	fileByte=(byte[]) qzAdMap.get("fpfile");
	        }
	        pdfout.setPdfFileData(fileByte);
	        pdfout.setSwjg_dm(pdfHeader.getTaxAuthorityCode());
	
	        final String pdfFilePath = PdfOutFileUtil.getPdfOutFileUtil().outFileToLocal(pdfout);
	        return pdfFilePath;
	    }else{*/
	    	
	    	 // 不添加广告 --获取传递pdf参数
	        final InvoiceHeaderEntity pdfHeader = pdfEntity.getInvoiceHeaderEntity();
	        final FPKJXX_FPJGXX fpkjxx_fpjgxx = pdfEntity.getFpkjxxFpjgxx();
	        final EShopInfo eShopInfo = pdfEntity.geteShopInfo();
	        final FPKJXX_XMXX[] fpkjxx_xmxxes = pdfEntity.getFpkjxxXmxxes();  //明细信息
	        // 组织pdf文件协议数据、生产pdf文件方法
	        final Map<String, Object> qzMap = new HashMap<String, Object>();
	        final StencilPlate stencilPlate = generatePdfFptxx(pdfHeader, fpkjxx_fpjgxx, eShopInfo, fpkjxx_xmxxes);
	        if (LOGGER.isDebugEnabled()) {
	            LOGGER.debug("StencilPlate, nsrsbh:{}", stencilPlate.getGhdwnsrsbh());
	        }
	       
	        //TODO 调用京东云签章服务器
	        byte[] fileByte = transfer(stencilPlate,fpkjxx_xmxxes,pdfHeader,qzMap);
	        
	        // TODO 临时注释 
//	        byte[] fileByte = ItestFactory.getDocument().producePDF(stencilPlate, textDao, qzMap);
	        
	        // 是否对PDF文件添加签章
	       /* if ("Y".equals(SystemConfig.pdfAppend)) {
	           fileByte = appendPdfSignature(pdfEntity, fileByte, qzMap);
	        }*/
	        // 生产pdf文件
	        final PdfOut pdfout = new PdfOut();
	        pdfout.setFileName(fpkjxx_fpjgxx.getFP_DM() + fpkjxx_fpjgxx.getFP_HM() + ".pdf");
	        pdfout.setFphm(fpkjxx_fpjgxx.getFP_HM());
	        final InvoiceEntity invoiceEntity = pdfHeader.getInvoiceEntity();
	        final DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd");
	        final DateTime dt = new DateTime(invoiceEntity.getBillingDate());
	        pdfout.setKprq(fmt.print(dt));
	        pdfout.setPdfFileData(fileByte);
	        pdfout.setSwjg_dm(pdfHeader.getTaxAuthorityCode());
	        final String pdfFilePath = PdfOutFileUtil.getPdfOutFileUtil().outFileToLocal(pdfout);
	        return pdfFilePath;
	  //  }
    }
    //TODO 调用京东云签章服务器
    public byte[] transfer(StencilPlate stencilPlate,FPKJXX_XMXX[] fpkjxx_xmxxes, InvoiceHeaderEntity pdfHeader,Map<String, Object> qzMap) throws Exception{
    	
    	Jar_fpqz_kj jar_fpqz_kj = new Jar_fpqz_kj();
//		jar_fpqz_kj.setGMF_DZDH("");// 购买方地址和电话 由于京东需求不让显示，所以不传值 
		/*
		 * 应京东方要求-请将购货方地址电话放入GHF_DZ字段，开票时校验此字段，若此字段为空，则票面不显示，若此字段非空，则在票面显示地址电话；
		 * TODO-fwh-20180326
		 * 
		 */
		jar_fpqz_kj.setGmf_dzdh(stencilPlate.getGhdwdz_dh()); 
//		jar_fpqz_kj.setGmf_dzdh("");// 购买方地址和电话 由于京东需求不让显示，所以不传值 
		jar_fpqz_kj.setGmf_yhzh(stencilPlate.getGhdwkhyh_zh());// 购买方银行帐号
		jar_fpqz_kj.setGmf_mc(stencilPlate.getGhdwmc());// 购买方名称
		jar_fpqz_kj.setGmf_nsrsbh(stencilPlate.getGhdwnsrsbh());// 购买方识别号
		jar_fpqz_kj.setJshj(stencilPlate.getJshj());// 价税合计
		jar_fpqz_kj.setHjje(stencilPlate.getHjje());// 合计金额
		jar_fpqz_kj.setHjse(stencilPlate.getHjse());// 合计税额
		/*
		 * 京东自营开票类型'1_正票 2 红票',
		 * 京东云开票类型 '0_正票 1 红票',
		 */
//		//TODO linshi 修改
//		pdfHeader.setBillingType(3l);
		//TODO kplx传入为null 有问题
		if(1==(pdfHeader.getBillingType())){
			jar_fpqz_kj.setKplx("0");// 开票类型（如果是0，代表的是蓝票；如果是1，代表的是红票）
			
		}else if((2==(pdfHeader.getBillingType())) || (9==(pdfHeader.getBillingType()))){
			jar_fpqz_kj.setKplx("1");// 开票类型（如果是0，代表的是蓝票；如果是1，代表的是红票）
		}else{
			LOGGER.error("开票类型不是正常红蓝票类型,kplx非1和2");
			throw new Exception();
		}
		jar_fpqz_kj.setBz(stencilPlate.getBz());// 备注
		jar_fpqz_kj.setKprq(stencilPlate.getKprq());// 开票日期保留14位
		jar_fpqz_kj.setKpr(stencilPlate.getKpy());// 开票人
		jar_fpqz_kj.setSkr(stencilPlate.getSky());// 收款人
		jar_fpqz_kj.setFhr(stencilPlate.getFhr());// 复核人
		jar_fpqz_kj.setXsf_yhzh(stencilPlate.getXhdwkhyh_zh());// 销售方银行帐号
		jar_fpqz_kj.setXsf_dzdh(stencilPlate.getXhdwdz_dh());// 销售方地址电话
		jar_fpqz_kj.setXsf_mc(stencilPlate.getXhdwmc());// 销售方名称
//		jar_fpqz_kj.setXSF_NSRSBH("91110112575938948G");// 销售方纳税人识别号	
		jar_fpqz_kj.setXsf_nsrsbh(stencilPlate.getXhdwnsrsbh());// 销售方纳税人识别号
		jar_fpqz_kj.setFp_mw(pdfHeader.getInvoiceEntity().getCiphertext());//密码区 |防伪密文
		/*jar_fpqz_kj.setFP_MW(
				"279+9>*6+57</5571196<02*0555+++13*+521/787<714+*153/1*9*+04378/->74+13//92/0-2<3-<155/85+13*+521/787<774*531");// 发票密文
		*/
		jar_fpqz_kj.setJym(pdfHeader.getInvoiceEntity().getCheckCode());// 校验码
		jar_fpqz_kj.setEwm(stencilPlate.getEwm());// 二维码
		jar_fpqz_kj.setFp_dm(stencilPlate.getFp_dm());// 发票代码
		jar_fpqz_kj.setFp_hm(stencilPlate.getFp_hm());// 发票号码
		jar_fpqz_kj.setJqbh(pdfHeader.getInvoiceEntity().getJqbh());// 机器编号
		jar_fpqz_kj.setBmb_bbh(pdfHeader.getInfoSellerVersion());// 编码表版本号
		if(null!=pdfHeader.getGoodsListFlag()&&pdfHeader.getGoodsListFlag().equals("4")){//判断是否为成品油发票
			jar_fpqz_kj.setSgbz("C");// 收购标志
		}else{
			jar_fpqz_kj.setSgbz("");// 收购标志--默认为空
		}
		jar_fpqz_kj.setQd_bz("0");// 清单标志 0自动清单 1强制清单
		jar_fpqz_kj.setQdxmmc("");// 清单发票项目名称 需要打印清单时对应发票票面项目名称
									// 清单标识（QD_BZ）为1时必填。为0不进行处理。
		
		
		// TODO 获取模版代码   修改获取值
		Object template = textDao.getTemplate(stencilPlate.getXhdwnsrsbh().trim(), "8");
		qzMap.putAll((Map)template);
		jar_fpqz_kj.setMbdm(qzMap.get("code").toString());// 模版代码 4位 北京1100……
		//TODO数据库获取签章ID
		String signCAIdByTaxpayerIdentifyNo = textDao.getSignCAIdByTaxpayerIdentifyNo(stencilPlate.getXhdwnsrsbh());
		jar_fpqz_kj.setQzid(signCAIdByTaxpayerIdentifyNo);// 签章ID
		
		jar_fpqz_kj.setIac("{'dj_p':'2'}");//项目单价固定写死取后面两位
		jar_fpqz_kj.setSjly("");// 数据来源
		jar_fpqz_kj.setDkbz(pdfHeader.getAgentInvoiceFlag());// 代开标志
		jar_fpqz_kj.setYfp_dm(pdfHeader.getOldInvoiceCode());// 原发票代码
		jar_fpqz_kj.setYfp_hm(pdfHeader.getOldInvoiceNo());// 原发票号码
		Jar_fpqz_kjmx jar_fpqz_kjmx = null;
		Jar_fpqz_kjmx[] list = new Jar_fpqz_kjmx[fpkjxx_xmxxes.length];
		//获取开具明细
//		StencilPlageMx[] stencilPlageMxs= stencilPlate.getStencilPlageMxs();
		for (int j = 0; j < fpkjxx_xmxxes.length; j++) {
			jar_fpqz_kjmx = new Jar_fpqz_kjmx();
			jar_fpqz_kjmx.setXmmc(fpkjxx_xmxxes[j].getXMMC());
			jar_fpqz_kjmx.setDw(fpkjxx_xmxxes[j].getXMDW());//项目单位
			jar_fpqz_kjmx.setGgxh(fpkjxx_xmxxes[j].getGGXH());// 规格型号
			jar_fpqz_kjmx.setXmsl(fpkjxx_xmxxes[j].getXMSL());// 项目数量
			jar_fpqz_kjmx.setXmdj(fpkjxx_xmxxes[j].getXMDJ());// 项目单价
			jar_fpqz_kjmx.setXmje(fpkjxx_xmxxes[j].getXMJE());// 项目金额
			// 根据京东云税率规则 标准为0.17  不要17%
			if(fpkjxx_xmxxes[j].getSL().contains("%")){
				String replace = fpkjxx_xmxxes[j].getSL().replace("%", "");
				double parseInt = Integer.parseInt(replace);
				double d = parseInt/100;
				String valueSL = String.valueOf(d);
				jar_fpqz_kjmx.setSl(valueSL);// 税率
			//0税率的 *** 需要重新定义	
			} else if(fpkjxx_xmxxes[j].getSL().equals("***")){
				jar_fpqz_kjmx.setSl("0.00");
			}else{
				jar_fpqz_kjmx.setSl(fpkjxx_xmxxes[j].getSL());// 税率
			}
			jar_fpqz_kjmx.setSe(fpkjxx_xmxxes[j].getSE());// 税额
		
			// 实体没有从受理端传递过来  在实体“InvoiceDetailEntity”中
			jar_fpqz_kjmx.setFphxz(fpkjxx_xmxxes[j].getFPHXZ());// 发票行性质 0 正常行、 1 折扣行、 2 被折扣行
			jar_fpqz_kjmx.setSpbm(fpkjxx_xmxxes[j].getSPBM());// 商品编码
			jar_fpqz_kjmx.setYhzcbs(fpkjxx_xmxxes[j].getYHZCBS());// 优惠政策标识 0：不使用， 1：使用
			jar_fpqz_kjmx.setLslbs(fpkjxx_xmxxes[j].getLSLBS());// 零税率标识 空：非零税率， 1：免税， 2：不征税， 3 普通零税率
			jar_fpqz_kjmx.setZzstsgl(fpkjxx_xmxxes[j].getZZSTSGL());// 增值税特殊管理，当有优惠政策时，必填
			jar_fpqz_kjmx.setKce(fpkjxx_xmxxes[j].getKCE());
			
			jar_fpqz_kjmx.setZxbm("");// 自行编码 
			
			list[j] = jar_fpqz_kjmx;
		}
		jar_fpqz_kj.setJar_fpqz_kjmxs(list);
		//京东云签章地址
		String baseurl = SystemConfig.jdCloudUrlQZ;
		LOGGER.debug("调用京东云签章地址URL:"+baseurl);
			DateTime lbegin = DateTime.now();
			String request = gson.toJson(jar_fpqz_kj);
			
			LOGGER.debug("调用京东云签章的请求信息为:"+request);
			String response = "";
			try {
				response = WebUtil.doPostString(baseurl, request);
			} catch (Exception e) {
				LOGGER.error("调用京东云签章异常："+e);
				throw new Exception();
			}
			LOGGER.debug("调用京东云签章的响应信息为:"+response);
			Jar_fpqz_info j = gson.fromJson(response, Jar_fpqz_info.class);
			Long lend = new Duration(lbegin, DateTime.now()).getMillis();
			LOGGER.warn("调用京东云签章反馈代码为："+j.getCode() + ",用时:" + lend + "ms");
			LOGGER.debug("调用京东云签章反馈代码为："+j.getCode()+"，反馈信息为："+j.getMsg()+"，用时为："+j.getTime());
			//成功1000
			if(j.getCode()==1000){
				LOGGER.error("调用京东云签章成功");
				/*签章调用jar包由1.05更新1.08版本,所以协议由大写改为小写;
				 * 新jar包中的PDFfile由byte改为string类型
				 * 1、返回pdfFile修改为String，需要先解base64
				 * 2、开票日期修改为14位，yyyyMMddHHmmss
				 * TODO-fwh-20180321
				 */
				Base64 base64 = new Base64();
				return base64.decodeBase64(new String(j.getPdfFile()).getBytes());  
			}else{
				LOGGER.error("调用京东云签章失败，反馈代码："+j.getCode()+"，反馈信息："+j.getMsg()+"，用时："+j.getTime());
				throw new Exception();
			}
    }
    
    

    /**
     * <p>
     * 组装pdf文件发票头文件协议
     * </p>
     * 
     * @param fptxx
     * @param fpkjxx_fpjgxx
     * @param djDsptxx
     * @param fpkjxx_xmxxes
     * @return
     * @throws ParseException
     *             StencilPlate
     * @author: wuyong@aisino.com
     * @date: Created on Sep 6, 2013 3:36:02 PM
     */
    private StencilPlate generatePdfFptxx(InvoiceHeaderEntity fptxx, FPKJXX_FPJGXX fpkjxx_fpjgxx, EShopInfo djDsptxx, FPKJXX_XMXX[] fpkjxx_xmxxes)
            throws ParseException {
        DecimalFormat df = new DecimalFormat("#########0.00");
        final InvoiceEntity invoiceEntity = fptxx.getInvoiceEntity();
        final StencilPlate stencilPlate = new StencilPlate();
        
        stencilPlate.setXhdwnsrsbh(fptxx.getTaxpayerIdentifyNo());// 销货单位纳税人识别号

        //购货方地址和电话
        if(fptxx.getInfoClientAddressPhone().startsWith(" ")||fptxx.getInfoClientAddressPhone().endsWith(" ")){
            stencilPlate.setGhdwdz_dh(fptxx.getInfoClientAddressPhone().replace(" ", ""));
        }else{
            stencilPlate.setGhdwdz_dh(fptxx.getInfoClientAddressPhone());
        }
        //购货方银行帐号
        if(fptxx.getInfoClientBankAccount().startsWith(" ")||fptxx.getInfoClientBankAccount().endsWith(" ")){
            stencilPlate.setGhdwkhyh_zh(fptxx.getInfoClientBankAccount().replace(" ", ""));
        }else{
            stencilPlate.setGhdwkhyh_zh(fptxx.getInfoClientBankAccount());
        }
        stencilPlate.setGhdwmc(fptxx.getBuyerName());// 购货方名称
        stencilPlate.setGhdwnsrsbh(fptxx.getBuyerTaxpayerIdentifyNo());// 购货方识别号

        final Double billingAmount = invoiceEntity.getBillingAmount();
        final Double Jshj = invoiceEntity.getInfoAmount()+ invoiceEntity.getInfoTaxAmount();
        //TODO 根据京东云的校验规则不能添加"¥"  注释掉 2017-06-22 FWH
        /*stencilPlate.setHjje("¥"+df.format(invoiceEntity.getInfoAmount()));// 合计金额
        stencilPlate.setJshj("¥"+df.format(Jshj));// 价税合计金额
        stencilPlate.setHjse("¥"+df.format(invoiceEntity.getInfoTaxAmount()));// 合计税额
*/
        stencilPlate.setHjje(df.format(invoiceEntity.getInfoAmount()));// 合计金额
		stencilPlate.setJshj(df.format(Jshj));// 价税合计金额
		stencilPlate.setHjse(df.format(invoiceEntity.getInfoTaxAmount()));// 合计税额

        if (billingAmount < 0) {
            stencilPlate.setJshjdx("(负数)" + ChineseCurrency.toChineseCurrency(Math.abs(Jshj)));
        } else {
            stencilPlate.setJshjdx(ChineseCurrency.toChineseCurrency(Jshj));
        }
        // 根据开票类型判断pdf发票页面显示（如果是1，代表的是蓝票；如果是2，代表的是红票，红票显示销项负数）
        if (fptxx.getBillingType().toString().equals("1")) {
            stencilPlate.setKplx("");
            stencilPlate.setBz(fptxx.getMemo());// 备注
        } else if ((fptxx.getBillingType().toString().equals("2")) || (fptxx.getBillingType().toString().equals("9"))) {
            stencilPlate.setKplx("销项负数");
            if(!StringUtils.isNullOrEmpty(fptxx.getMemo()) && fptxx.getMemo().startsWith("对应正数发票代码")){
                stencilPlate.setBz(fptxx.getMemo());
            }else{
                stencilPlate.setBz("对应正数发票代码:"+invoiceEntity.getOldInvoiceCode()+"号码:"+invoiceEntity.getOldInvoiceNo()+" "+fptxx.getMemo());// 备注
            }
        }
        /*
         * 开票日期格式更新为yyyyMMddHHmmss
         * fwh-20180321 
         */
        final DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyyMMddHHmmss");
        final DateTime dt = new DateTime(invoiceEntity.getBillingDate());
        stencilPlate.setKprq(fmt.print(dt));
        stencilPlate.setKprq_n(fmt.print(dt).substring(0,4));
        stencilPlate.setKprq_y(fmt.print(dt).substring(4,6));
        stencilPlate.setKprq_r(fmt.print(dt).substring(6,8));
        stencilPlate.setKpy(fptxx.getBillingStaff());// 开票员
        stencilPlate.setSky(fptxx.getCashier());// 收款人
        stencilPlate.setFhr(fptxx.getInfoChecker());// 复核人
        //销货方银行帐号
        if(fptxx.getInfoSellerBankAccount().startsWith(" ")||fptxx.getInfoSellerBankAccount().endsWith(" ")){
            stencilPlate.setXhdwkhyh_zh(fptxx.getInfoSellerBankAccount().replace(" ", ""));
        }else{
            stencilPlate.setXhdwkhyh_zh(fptxx.getInfoSellerBankAccount());
        }
        //销货方地址电话
        if(fptxx.getInfoSellerAddressPhone().startsWith(" ")||fptxx.getInfoSellerAddressPhone().endsWith(" ")){
            stencilPlate.setXhdwdz_dh(fptxx.getInfoSellerAddressPhone().replace(" ", ""));
        }else{
            stencilPlate.setXhdwdz_dh(fptxx.getInfoSellerAddressPhone());
        }
        stencilPlate.setXhdwmc(fptxx.getTaxpayer());// 销货方名称
        stencilPlate.setXhdwnsrsbh(fptxx.getSellerTaxpayerIdentifyNo());// 销货方纳税人识别号

        // 此处去掉密文区的米字符换成*号,2015年7月7日 14:48:22 应对模版修改
        String Ciphertext = invoiceEntity.getCiphertext();
        int mmqlength=Ciphertext.length();
        int l=mmqlength/4;
        
        stencilPlate.setMmq(Ciphertext.substring(0, l));// 密码区对应的防伪密文
        stencilPlate.setMmq1(Ciphertext.substring(l, 2*l));// 密码区对应的防伪密文
        stencilPlate.setMmq2(Ciphertext.substring(2*l, 3*l));// 密码区对应的防伪密文
        stencilPlate.setMmq3(Ciphertext.substring(3*l, mmqlength));// 密码区对应的防伪密文

        
        final StringBuilder sbf = new StringBuilder();
        sbf.append(invoiceEntity.getCheckCode().substring(0, 5)).append(" ");
        sbf.append(invoiceEntity.getCheckCode().substring(5, 10)).append(" ");
        sbf.append(invoiceEntity.getCheckCode().substring(10, 15)).append(" ");
        sbf.append(invoiceEntity.getCheckCode().substring(15, 20));
        stencilPlate.setJym(sbf.toString());// 校验码
        
        stencilPlate.setEwm(fpkjxx_fpjgxx.getEWM());//二维码
        stencilPlate.setFp_dm(fpkjxx_fpjgxx.getFP_DM());//发票代码
        stencilPlate.setFp_hm(fpkjxx_fpjgxx.getFP_HM());//发票号码
        stencilPlate.setJqbh(invoiceEntity.getJqbh());//机器编号
        stencilPlate.setStencilPlageMxs(generatePdfFpMxxx(fpkjxx_xmxxes, fptxx));//明细信息赋值
        String slbz="";
        for (int i = 0; i < 1; i++) {
            final StencilPlageMx fpkjxxXmxxsl = stencilPlate.getStencilPlageMxs()[0];
            for (int j = i+1; j < stencilPlate.getStencilPlageMxs().length; j++) {
                final StencilPlageMx fpkjxxXmxxsl1 = stencilPlate.getStencilPlageMxs()[j];
                if(fpkjxxXmxxsl.getSlv().equals(fpkjxxXmxxsl1.getSlv())){
                    slbz=fpkjxxXmxxsl.getSlv();
                }else{
                    slbz="";
                }
            }
        }
        stencilPlate.setSlbz(slbz);//税率标志

        return stencilPlate;
    }

    /**
     * <p>
     * 组装pdf文件明细信息
     * </p>
     * 
     * @param fpkjxxXmxxes
     * @return StencilPlageMx[]
     * @author: wuyong@aisino.com
     * @date: Created on Sep 6, 2013 3:36:31 PM
     */
    private StencilPlageMx[] generatePdfFpMxxx(FPKJXX_XMXX[] fpkjxxXmxxes, InvoiceHeaderEntity invoiceHeaderEntity) {
        final DecimalFormat df = new DecimalFormat("#########0.00");
        final StencilPlageMx[] stencilPlageMxes = new StencilPlageMx[fpkjxxXmxxes.length];
        final InvoiceEntity invoiceEntity = invoiceHeaderEntity.getInvoiceEntity();

        for (int i = 0; i < fpkjxxXmxxes.length; i++) {
            final FPKJXX_XMXX fpkjxxXmxx = fpkjxxXmxxes[i];
            final StencilPlageMx stencilPlageMx = new StencilPlageMx();

            stencilPlageMx.setDj(df.format(Double.valueOf(fpkjxxXmxx.getXMDJ())));
            stencilPlageMx.setSl(fpkjxxXmxx.getXMSL());

            // 蓝票中，只有折扣行的金额是负的 、 红票中只有折扣行的金额是正的
            if ("1".equals(invoiceEntity.getBillingType().toString())) {
                /* 判断字符串为空 2014年7月28日16:12:36 */
                if (!Strings.isNullOrEmpty(fpkjxxXmxx.getXMJE()) && Double.parseDouble(fpkjxxXmxx.getXMJE()) < 0) {
                    stencilPlageMx.setDj("");
                    stencilPlageMx.setSl("");
                }
            } else {
                if (!Strings.isNullOrEmpty(fpkjxxXmxx.getXMJE()) && Double.parseDouble(fpkjxxXmxx.getXMJE()) > 0) {
                    stencilPlageMx.setDj("");
                    stencilPlageMx.setSl("");
                }
            }

            stencilPlageMx.setSpmc(fpkjxxXmxx.getXMMC());
            if(stencilPlageMx.getSpmc().startsWith("折扣(")||stencilPlageMx.getSpmc().startsWith("折扣行数")||stencilPlageMx.getSpmc().startsWith("运费")){
                stencilPlageMx.setDj("");
                stencilPlageMx.setSl("");
            }
            stencilPlageMx.setJe(df.format(Double.valueOf(fpkjxxXmxx.getXMJE()))+"  ");
            stencilPlageMx.setDw(fpkjxxXmxx.getXMDW());
            stencilPlageMx.setSlv(fpkjxxXmxx.getSL());
            stencilPlageMx.setSe(df.format(Double.valueOf(fpkjxxXmxx.getSE()))+"  ");
            stencilPlageMx.setGgxh(fpkjxxXmxx.getGGXH());
            stencilPlageMx.setXh(String.valueOf(i+1));

            stencilPlageMxes[i] = stencilPlageMx;
        }

        return stencilPlageMxes;
    }

    private UserResponsePacket authenticate(UserRequestPacket request) throws Exception {
        final CommonPacket requestPack = commonPacketService.newInstanceByRequest(request);
        byte[] bytes = null;
        Socket socket = null;
        try {
            socket = new Socket(SystemConfig.serverIp, SystemConfig.serverPort);

            final OutputStream os = socket.getOutputStream();
            os.write(requestPack.getDataPacket());
            os.flush();

            final InputStream ins = socket.getInputStream();
            final ByteArrayOutputStream byteOs = new ByteArrayOutputStream();
            int i = 0;
            int value = 0;
            while (i < SystemConfig.headerLength + 4) {
                i++;
                value = ins.read();
                if (value == -1) {
                    throw new Exception("响应数据不符合规范长度");
                }

                byteOs.write(value);
            }

            byte[] temp = new byte[4];
            byteOs.flush();
            System.arraycopy(byteOs.toByteArray(), SystemConfig.headerLength, temp, 0, temp.length);
            int length = TranslateUtil.byte2Int(temp);
            i = 0;
            value = 0;
            while (i < length + 2) {
                i++;
                value = ins.read();
                if (value == -1) {
                    throw new Exception("响应数据不符合规范长度");
                }

                byteOs.write(value);
            }

            byteOs.flush();
            bytes = byteOs.toByteArray();

        } catch (Exception e) {
            LOGGER.error("authenticate失败：", e);

            final SecurityCodeException auExp = new SecurityCodeException();
            auExp.setOriginalExceptionMsg(e.getMessage());
            auExp.setExceptionMsg("生成请求数据包出现异常");
        } finally {
            if (socket != null) {
                socket.close();
            }
        }

        final CommonPacket responsePack = commonPacketService.newInstanceByResponse(bytes);

        return responsePack.getUserResponse();
    }

    /**
     * 给发票PDF文件增加签章
     * 
     * @param pdfEntity
     * @param fileByte
     * @param qzMap
     * @return
     * @throws Exception
     */
    private byte[] appendPdfSignature(InvoicePDFEntity pdfEntity, byte[] fileByte, Map<String, Object> qzMap) throws Exception {
        // 签章
        if (SystemConfig.qzfw.trim().equalsIgnoreCase(SecurityConstant.qzfwAisino.trim())) {// 调加密服务器做签章
            // 模版选择
            ByteArrayOutputStream outputStream = null, outPdf = null;
            try {
                outputStream = new ByteArrayOutputStream();
                outputStream.write(SystemConfig.PDF_QZ_ZB_AISINO_LEFT.getBytes());
                outputStream.write(SystemConfig.PDF_QZ_ZB_AISINO_TOP.getBytes());
                outputStream.write(SystemConfig.PDF_QZ_ZB_AISINO_RIGTH.getBytes());
                outputStream.write(SystemConfig.PDF_QZ_ZB_AISINO_BOTTOM.getBytes());
                outputStream.write(SystemConfig.PDF_QZ_ZB_AISINO_PAGEINDEX.getBytes());
                outputStream.write(fileByte);

                // 签章pdf文件
                outPdf = new ByteArrayOutputStream();
                int dataLen = outputStream.size();
                int len = 10 - ((dataLen + "").length());
                String dataLenthpdf = "" + dataLen;
                for (int i = 0; i < len; i++) {
                    dataLenthpdf = "0" + dataLenthpdf;
                }
                outPdf.write(dataLenthpdf.getBytes());
                outPdf.write(outputStream.toByteArray());

                final InvSign code = new InvSign(outPdf.toByteArray());
                final UserResponsePacket packet = authenticate(code.getUserRequestPacket());

                // 当加密机签章服务器不能返回调用成功时,此处可手动设置OK
                // packet.setState("OK");

                if (Result_OK.equals(packet.getState())) {
                    LOGGER.info("OK");

                    fileByte = packet.getResponseData();
                } else if (Result_ER.equals(packet.getState())) {
                    LOGGER.error("aisino 签章失败ER:" + packet.getErrorCode());

                    throw new FpztException(FpztConstants.PDFSIGNFIAL, FpztConstants.getFpzt(FpztConstants.PDFSIGNFIAL));
                }
            } finally {
                if (outPdf != null) {
                    outPdf.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } else if (SystemConfig.qzfw.trim().equalsIgnoreCase(SecurityConstant.qzfwSmyjy.trim())) {// 调签章服务器做签章
            LoadBalancing.getInstance();
            final Client client = new Client();
            client.init(Integer.valueOf(qzMap.get("qzleft").toString()), Integer.valueOf(qzMap.get("qztop").toString()), Integer.valueOf(qzMap.get("qzright")
                    .toString()), Integer.valueOf(qzMap.get("qzbottom").toString()), Integer.valueOf(qzMap.get("qzpageindex").toString()));

            final String nsrqzId = pdfEntity.getTaxerSignatureId().trim();
            final AddStampRec ansData = client.addStamp(nsrqzId, fileByte, new byte[0]);

            if (ansData.resValue == 0) {
                fileByte = ansData.pdf;
            } else {
                LOGGER.error("smyjy签章失败" + ansData.resValue);
                throw new FpztException(FpztConstants.PDFSIGNFIAL, FpztConstants.getFpzt(FpztConstants.PDFSIGNFIAL));
            }
        }

        return fileByte;
    }

    public static void main(String[] args) {
        StencilPlate StencilPlate = new StencilPlate();
        StencilPlate.setBz("备注");
        StencilPlate.setJym("12343123413");
        StencilPlate.setGhdwmc("张双超");
        StencilPlate.setGhdwnsrsbh("310114312356647");
        StencilPlate.setGhdwdz_dh("北京海淀，15652241402");
        StencilPlate.setGhdwkhyh_zh("北京银行，134135143512341241");
        StencilPlate.setMmq("<><<LLU*&^%*JJ12341243214");
        StencilPlate.setHjje("1324.02");
        StencilPlate.setHjse("224.03");
        StencilPlate.setJshj("1100.00.");
        StencilPlate.setJshjdx("一千一百元整");
        StencilPlate.setKprq("2012年01月01日");
        StencilPlate.setKpy("开票员");
        StencilPlate.setXhdwmc("张双超1");
        StencilPlate.setXhdwnsrsbh("1111123213413");
        StencilPlate.setXhdwdz_dh("北京海淀1，15652241402");
        StencilPlate.setXhdwkhyh_zh("北京银行1，134135143512341241");
        StencilPlate.setSky("lll丽丽");
        StencilPlate.setFhr("小名");
        StencilPlate.setEwm("kasdjfair3o923rielkfjsdfpoiwe");
        StencilPlageMx[] mxs = new StencilPlageMx[3];
        for (int i = 0; i < 3; i++) {
            StencilPlageMx StencilPlageMx = new StencilPlageMx();
            StencilPlageMx.setSpmc("地地地地地地地地地地地地地地地地地地地地地地121224344444444444444444444444444444444444444");
            StencilPlageMx.setDj("200.00");
            StencilPlageMx.setSl("1");
            StencilPlageMx.setJe("200.00");
            StencilPlageMx.setDw("个1");
            StencilPlageMx.setGgxh("个1");
            StencilPlageMx.setSlv("个1");
            StencilPlageMx.setSe("个1");
            mxs[i] = StencilPlageMx;
        }
        StencilPlate.setStencilPlageMxs(mxs);
        StencilPlate.setFp_dm("132098765432");
        StencilPlate.setFp_hm("00000001");
        long startTime = System.currentTimeMillis();
        byte[] fileByte = ItestFactory.getDocument().producePDF(StencilPlate, null, null);
        File file = new File("f:\\生成结果.pdf");
        try {
            FileOutputStream out = new FileOutputStream(file);
            out.write(fileByte);
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        long endTime = System.currentTimeMillis();
        System.err.println((endTime - startTime) / 1000);
    }
    

}
