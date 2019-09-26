package com.aisino.einvoice.service.impl;

import static com.aisino.common.util.EIProtocolFactory.getDataRoot;
import static com.aisino.domain.SystemConfig.INV_GETDATA_SUCCESS;
import static com.aisino.domain.SystemConfig.invoiceNote;
import static com.aisino.domain.constantenum.BusinessIdTypeEnum.FPCHFPSQD_ID;
import static com.aisino.domain.constantenum.BusinessIdTypeEnum.FPDDXX_ID;
import static com.aisino.domain.constantenum.BusinessIdTypeEnum.FPKJMX_ID;
import static com.aisino.domain.constantenum.BusinessIdTypeEnum.FPKJ_ID;
import static com.aisino.domain.constantenum.BusinessIdTypeEnum.FPKJ_LOG_ID;
import static com.aisino.domain.constantenum.BusinessIdTypeEnum.FPWLXX_ID;
import static com.aisino.domain.constantenum.BusinessIdTypeEnum.FPZFXX_ID;
import static com.google.common.base.MoreObjects.toStringHelper;
import static org.apache.commons.io.FileUtils.readFileToByteArray;
import static org.joda.time.DateTime.now;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.cache.annotation.Cacheable;

import com.aisino.common.util.Data;
import com.aisino.common.util.EIProtocolFactory;
import com.aisino.common.util.EShopCertificateBytesInfo;
import com.aisino.common.util.ReturnStateInfo;
import com.aisino.common.util.StaticConstant;
import com.aisino.common.util.ValidateUtil;
import com.aisino.common.util.XMLShellFactory;
import com.aisino.common.util.XmlPar;
import com.aisino.domain.SystemConfig;
import com.aisino.domain.einvoice.EInvoiceQueryCondition;
import com.aisino.domain.einvoice.InvoiceDataAccessManagerService;
import com.aisino.domain.einvoice.UniqueResourceManagerService;
import com.aisino.domain.einvoice.entity.CertificateEntity;
import com.aisino.domain.einvoice.entity.EShopInfo;
import com.aisino.domain.einvoice.entity.InvoiceDetailEntity;
import com.aisino.domain.einvoice.entity.InvoiceEntity;
import com.aisino.domain.einvoice.entity.InvoiceHeaderEntity;
import com.aisino.domain.einvoice.entity.InvoiceKindInfoEntity;
import com.aisino.domain.einvoice.entity.InvoiceLogisticsEntity;
import com.aisino.domain.einvoice.entity.InvoiceOrderEntity;
import com.aisino.domain.einvoice.entity.InvoicePDFInfoEntity;
import com.aisino.domain.einvoice.entity.InvoicePaymentEntity;
import com.aisino.domain.einvoice.entity.InvoiceRedEntity;
import com.aisino.domain.einvoice.entity.OrderInfoEntity;
import com.aisino.domain.einvoice.entity.TaxpayerEntity;
import com.aisino.domain.einvoice.repository.EInvoiceSubRepository;
import com.aisino.domain.einvoice.utils.WebUtil;
import com.aisino.domain.rabbit.entity.GeneratorInvoiceQueueEntity;
import com.aisino.einvoice.service.EInvoiceBusinessMap;
import com.aisino.einvoice.service.IInvUploadService;
import com.aisino.einvoice.service.IInvoiceDataCheckService;
import com.aisino.protocol.bean.COMMON_FPKJ_DDXX;
import com.aisino.protocol.bean.COMMON_FPKJ_FPT;
import com.aisino.protocol.bean.COMMON_FPKJ_WLXX;
import com.aisino.protocol.bean.COMMON_FPKJ_XMXX;
import com.aisino.protocol.bean.COMMON_FPKJ_ZFXX;
import com.aisino.protocol.bean.REQUEST_COMMON_DOWNLOAD_CA;
import com.aisino.protocol.bean.REQUEST_COMMON_DOWNLOAD_DSPTXX;
import com.aisino.protocol.bean.REQUEST_COMMON_DSQYXX;
import com.aisino.protocol.bean.REQUEST_COMMON_FPKJ;
import com.aisino.protocol.bean.REQUEST_FPXXXZ;
import com.aisino.protocol.bean.RESPONSE_COMMON_DJDSQYXX;
import com.aisino.protocol.bean.RESPONSE_COMMON_DJDSQYXXGLOBLE;
import com.aisino.protocol.bean.RESPONSE_COMMON_DOWNLOAD_CA;
import com.aisino.protocol.bean.RESPONSE_COMMON_DOWNLOAD_DSPTXX;
import com.aisino.protocol.bean.RESPONSE_COMMON_DSQYPZHDXX;
import com.aisino.protocol.bean.RESPONSE_FPXXXZ;
import com.alibaba.fastjson.JSON;
import com.google.common.base.Strings;

/**
 * Created with IntelliJ IDEA. User: Schiffer.huang Date: 14-９-25 Time: 上午10:10
 * 平台信息处理-数据持久化与纳税人查验接口实现
 */
public final class InvUploadServiceImpl implements IInvUploadService {

    private final static Logger LOGGER = LoggerFactory.getLogger(InvUploadServiceImpl.class);

    private UniqueResourceManagerService uniqueResourceManagerService;

    private EInvoiceBusinessMap businessHandler;

    private InvoiceDataAccessManagerService invoiceDataAccessManagerService;
    
    private IInvoiceDataCheckService invoiceDataCheckService;
    
    private EInvoiceSubRepository repository;

    private RabbitTemplate getRabbitTemplate() {
        return this.businessHandler.getRabbitTemplate();
    }

    @Override
    public EShopInfo getEShopInfo(String eshopCode) {
        return repository.getEShopInfo(eshopCode);
    }

    @Override
    public Map<String, Object> processEShopInvoice(Map<String, Object> map) throws Exception {

        LOGGER.info("step into processEShopClientInvoice");

        final ReturnStateInfo returnInfo = (ReturnStateInfo) map.get(XmlPar.RETURNSTATEINFO);
        final Data data = (Data) map.get(XmlPar.DATA);

        /* 获取协议bean */
        final REQUEST_COMMON_FPKJ protocolInvoiceBean = (REQUEST_COMMON_FPKJ) getDataRoot(data.getContent()).get(0);

        COMMON_FPKJ_XMXX[] protocolInvoiceProjectBean = protocolInvoiceBean.getCOMMON_FPKJ_XMXXS();
        final COMMON_FPKJ_FPT protocolInvoiceHeaderBean = protocolInvoiceBean.getCOMMON_FPKJ_FPT();

//        final EInvoiceSubRepository repository = getRepository();

        /* 校验发票开具信息合法性 */
        final DateTime begin = now();

        /*final Map<String, String> checkInvParaLegitimateMsg = checkInvParaLegitimate(protocolInvoiceBean, protocolInvoiceHeaderBean,
                protocolInvoiceProjectBean, repository);*/
        final Map<String, String> checkInvParaLegitimateMsg = invoiceDataCheckService.checkInvParam(protocolInvoiceBean, protocolInvoiceHeaderBean,
                protocolInvoiceProjectBean, repository);
        
        protocolInvoiceProjectBean = protocolInvoiceBean.getCOMMON_FPKJ_XMXXS();

        final Long millSeconds = new Duration(begin, now()).getMillis();
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("(51平台发票开具-受理)验证发票开据信息合法性总共耗时{}毫秒", millSeconds);
        }

        if (!XmlPar.RESPONSEYYSSUCCESS.equals(checkInvParaLegitimateMsg.get(XmlPar.ERRORCODE))) {
            LOGGER.info("Before insert InvoiceEntity，check eInvoice kj info fail.");
            return buildReturnStateInfo(map, returnInfo, checkInvParaLegitimateMsg.get(XmlPar.ERRORMSG), checkInvParaLegitimateMsg.get(XmlPar.ERRORCODE));
        }
       //调整反算折扣率位置，注：在校验完数据后，数据合法才进行反算。
      //反算折扣率 注：折扣行的商品名称必须为“折扣(X.XXX%) 2016-8-16 16:46:16 阳开国
        if ("Y".equals(SystemConfig.getDiscountFlag)) {
            protocolInvoiceProjectBean = protocolInvoiceBean.getCOMMON_FPKJ_XMXXS();
            for (int i = 0; i < protocolInvoiceProjectBean.length; i++) {
                if ("1".equals(protocolInvoiceProjectBean[i].getFPHXZ())) {
                    String newXmmc = getZKHMC(protocolInvoiceProjectBean,i);
                    protocolInvoiceProjectBean[i].setXMMC(newXmmc);
                }
            }
            protocolInvoiceBean.setCOMMON_FPKJ_XMXXS(protocolInvoiceProjectBean);
            
        }
        //end 反算折扣率 2016-8-16 15:09:21
        

        final Long uniqueId = uniqueResourceManagerService.obtainBusinessIdFromQueue(FPKJ_ID.name());
        LOGGER.info("GET FPKJ_ID uniqueId {}", uniqueId);

        final TaxpayerEntity taxpayer = repository.getTaxpayer(protocolInvoiceHeaderBean.getNSRSBH());
        if(taxpayer == null){        	
       	 	map.put(XmlPar.DATA, new Data());
            returnInfo.setReturnCode(XmlPar.RESPONSEFAIL);
            returnInfo.setReturnMessage("纳税人识别号{"+protocolInvoiceHeaderBean.getNSRSBH()+"}不合法或没有配置！");
            map.put(XmlPar.RETURNSTATEINFO, returnInfo);
       }else{
        final String theEShopCode = taxpayer.getEshopCode();
        
        /*
         * TRACY MARK 2014.12.03 : 请求协议bean中
         * DSPTBM为平台编码，转换为原电商平台编码,此动作发生于验证协议bean之后.
         */
        protocolInvoiceHeaderBean.setDSPTBM(theEShopCode);

        Boolean processInsertInvoiceStep = processInsertInvoice(protocolInvoiceProjectBean, protocolInvoiceHeaderBean, repository, uniqueId, taxpayer);
        Boolean processSaveInvoiceLogStep = processSaveInvoiceLog(protocolInvoiceHeaderBean, repository, uniqueId);
        /* 发票冲红申请处理 */
        if (!(invoiceNote.toString()).equals(protocolInvoiceHeaderBean.getKPLX())) {
            Boolean processInsertInvoiceRedStep = processInsertInvoiceRed(protocolInvoiceHeaderBean, repository, uniqueId);
        }
        
        /* 发票开具明细信息批量处理 */
        processInsertInvoiceDetail(protocolInvoiceProjectBean, repository, uniqueId);
        /* 发票开具订单信息处理 */
        final COMMON_FPKJ_DDXX protocolInvoiceOrderBean = protocolInvoiceBean.getCOMMON_FPKJ_DDXX();
        Boolean processInsertInvoiceOrderStep = processInsertInvoiceOrder(protocolInvoiceHeaderBean, protocolInvoiceOrderBean, repository, uniqueId);

        /* 发票开具订单详细信息批量处理 */
        processInsertInvoiceOrderDetail(protocolInvoiceBean, protocolInvoiceOrderBean, repository, uniqueId);

        /* 支付信息处理 */
        Boolean processInsertInvoicePaymentStep = processInsertInvoicePayment(protocolInvoiceBean, protocolInvoiceHeaderBean, repository, uniqueId);

        /* 物流信息处理 */
        Boolean processInsertInvoiceLogisticsStep = processInsertInvoiceLogistics(protocolInvoiceBean, protocolInvoiceHeaderBean, repository, uniqueId);

        final String processLog = toStringHelper("invoice series persistence process simple record: ")
                .add("processInsertInvoiceStep", processInsertInvoiceStep).add("processSaveInvoiceLogStep", processSaveInvoiceLogStep).add(
                        "processInsertInvoiceOrderStep", processInsertInvoiceOrderStep).add("processInsertInvoicePaymentStep", processInsertInvoicePaymentStep)
                .add("processInsertInvoiceLogisticsStep", processInsertInvoiceLogisticsStep).toString();

        LOGGER.info(processLog);

        /* ORIGIN:设置反馈对象系列状态代码信息 */
        map.put(XmlPar.DATA, new Data());
        returnInfo.setReturnCode(XmlPar.RESPONSEYYSSUCCESS);
        returnInfo.setReturnMessage("接收发票开具数据成功！");
        map.put(XmlPar.RETURNSTATEINFO, returnInfo);

        if (processInsertInvoiceStep && processSaveInvoiceLogStep) {

            final GeneratorInvoiceQueueEntity queueEntity = new GeneratorInvoiceQueueEntity();
            final OrderInfoEntity orderInfo = new OrderInfoEntity();
            
            queueEntity.setInvoiceId(uniqueId);
            queueEntity.setTaxpayerIdentifyNo(protocolInvoiceHeaderBean.getNSRSBH());
            queueEntity.setTaxAuthorityCode(taxpayer.getTaxAuthorityCode());
            queueEntity.setEshopCode(protocolInvoiceHeaderBean.getDSPTBM());
            queueEntity.setBillingAmount(Double.parseDouble(protocolInvoiceHeaderBean.getKPHJJE()));
            queueEntity.setBillingType(Long.parseLong(protocolInvoiceHeaderBean.getKPLX()));
            queueEntity.setSignatureId(repository.getSignCAIdByTaxpayerIdentifyNo(protocolInvoiceHeaderBean.getNSRSBH()));
            queueEntity.setOrderNo(protocolInvoiceOrderBean.getDDH());
            queueEntity.setInvoiceRequestSerialNo(protocolInvoiceHeaderBean.getFPQQLSH());
            queueEntity.setOldInvoiceCode(protocolInvoiceHeaderBean.getYFP_DM());
            queueEntity.setOldInvoiceNo(protocolInvoiceHeaderBean.getYFP_HM());
            queueEntity.setMemo(protocolInvoiceHeaderBean.getBZ());
            queueEntity.setBuyerMobile(protocolInvoiceHeaderBean.getGHF_SJ());
            queueEntity.setBuyerFixedPhone(protocolInvoiceHeaderBean.getGHF_GDDH());
            queueEntity.setBuyerEmail(protocolInvoiceHeaderBean.getGHF_YX());
            queueEntity.setBuyerName(protocolInvoiceHeaderBean.getGHF_MC());
            queueEntity.setBuyerTaxpayerIdentifyNo(protocolInvoiceHeaderBean.getGHF_NSRSBH());
            queueEntity.setBuyerAddress(protocolInvoiceHeaderBean.getGHF_DZ());
            queueEntity.setBillingStaff(protocolInvoiceHeaderBean.getKPY());
            queueEntity.setSellerTaxpayerIdentifyNo(protocolInvoiceHeaderBean.getXHF_NSRSBH());
            queueEntity.setSellerName(protocolInvoiceHeaderBean.getXHF_MC());
            queueEntity.setIndustryName(protocolInvoiceHeaderBean.getHY_MC());
            queueEntity.setTaxpayer(protocolInvoiceHeaderBean.getNSRMC());
            queueEntity.setOperatorNo(protocolInvoiceHeaderBean.getCZDM());
            /*
             * 2015年3月10日 13:45:30 新增的字段 zhongsiwei begin 两个字段连接
             */
            queueEntity.setInfoClientBankAccount(protocolInvoiceHeaderBean.getFKF_YHZH());// 购方银行及账号
            queueEntity.setInfoSellerBankAccount(protocolInvoiceHeaderBean.getSKF_YHZH());// 销方银行及账号
            queueEntity.setInfoSellerAddressPhone(protocolInvoiceHeaderBean.getXHF_DZ() + " " + protocolInvoiceHeaderBean.getXHF_DH());// 销方地址电话
//            queueEntity.setInfoClientAddressPhone(protocolInvoiceHeaderBean.getGHF_DZ() + " " + protocolInvoiceHeaderBean.getGHF_GDDH());
            queueEntity.setInfoClientAddressPhone(protocolInvoiceHeaderBean.getGHF_DZ());
            queueEntity.setInfoChecker(protocolInvoiceHeaderBean.getFHR());
            // queueEntity.setInfoListName(protocolInvoiceHeaderBean.getXHQD());
            queueEntity.setInfoAmount(Double.parseDouble(protocolInvoiceHeaderBean.getHJBHSJE()));
            queueEntity.setInfoTaxAmount(Double.parseDouble(protocolInvoiceHeaderBean.getKPHJSE()));
            // queueEntity.setInfoMonth(protocolInvoiceHeaderBean.getSSYF());
            // queueEntity.setGoodsListFlag(protocolInvoiceHeaderBean.getXHQDBZ());
            // queueEntity.setRetCode(protocolInvoiceHeaderBean.getRETCODE());
            // queueEntity.setCiphertext(protocolInvoiceHeaderBean.getFWMW());
            // queueEntity.setCheckCode(protocolInvoiceHeaderBean.getJYM());
            // queueEntity.setInfoInvoicer(protocolInvoiceHeaderBean.getSZQM());
            queueEntity.setCashier(protocolInvoiceHeaderBean.getSKY());
            /*
             * 2015年3月10日 13:45:30 新增的字段 zhongsiwei end
             */
            /* queueEntity.setSCCOUNT("0"); 原PDF 生产次数OMIT:2014.10.09 */

//            getRabbitTemplate().convertAndSend(queueEntity);
            
            // BMB_BBH 入队列
            /*
             * 2016年7月5日 20:27:36 新增字段（BMB_BBH）阳开国 begin
             */
            queueEntity.setInfoSellerVersion(protocolInvoiceHeaderBean.getBMB_BBH());
            
            /*
             * 2016年7月5日 20:27:36 新增字段（BMB_BBH） 阳开国 end
             */
            map.put("mqEntity", queueEntity);
            
            // 2017-07-19 宋雪冬 添加双rabbitMQ队列配置，用于把发票订单信息上传至大象平台查询发票
        	orderInfo.setDdh(protocolInvoiceOrderBean.getDDH());
        	orderInfo.setCwms("发票数据受理成功");
        	orderInfo.setNsrsbh(protocolInvoiceHeaderBean.getNSRSBH());
        	orderInfo.setZtm("6000");
        	orderInfo.setHjje(Double.parseDouble(protocolInvoiceHeaderBean.getKPHJJE()));
            map.put("orderEntity", orderInfo);
            
            LOGGER.info("send mq:{}", queueEntity);
        }
       }
        return map;
    }

    @Override
    public Map<String, Object> processGetInvoicePdf(Map<String, Object> map) {
        ReturnStateInfo returnInfo = (ReturnStateInfo) map.get(XmlPar.RETURNSTATEINFO);
        Data data = (Data) map.get(XmlPar.DATA);

//        final EInvoiceSubRepository repository = getRepository();

        try {
        	REQUEST_FPXXXZ protocolBean = (REQUEST_FPXXXZ) EIProtocolFactory.getDataRoot(data.getContent()).get(0);

            if (protocolBean != null) {
                if (ValidateUtil.checkParameterIsEmpty(protocolBean.getFPQQLSH())) {
                    returnInfo.setReturnCode(XmlPar.ERRORCODE_DATA_FPT_FPQQLSH);
                    returnInfo.setReturnMessage("发票开票发票请求唯一流水号不能为空");
                    return buildReturnStateInfo(map, returnInfo, "发票开票发票请求唯一流水号不能为空", XmlPar.ERRORCODE_DATA_FPT_FPQQLSH);
                }
                if (ValidateUtil.checkParameterLength(protocolBean.getFPQQLSH(), 0, 20)) {
                    returnInfo.setReturnCode(XmlPar.ERRORCODE_DATA_FPT_FPQQLSH);
                    returnInfo.setReturnMessage("发票开票发票请求唯一流水号长度存在问题");
                    return buildReturnStateInfo(map, returnInfo, "发票开票发票请求唯一流水号长度存在问题", XmlPar.ERRORCODE_DATA_FPT_FPQQLSH);
                }
                // 纳税人识别号是否填写
                if (ValidateUtil.checkParameterIsEmpty(protocolBean.getNSRSBH())) {
                    returnInfo.setReturnCode(XmlPar.ERRORCODE_DATA_FPT_NSRSBH);
                    returnInfo.setReturnMessage("发票开具纳税人识别号不能为空！");
                    return buildReturnStateInfo(map, returnInfo, "发票开具纳税人识别号不能为空！", XmlPar.ERRORCODE_DATA_FPT_NSRSBH);
                }
                if (ValidateUtil.checkParameterLength(protocolBean.getNSRSBH(), 15, 20)) {
                    returnInfo.setReturnCode(XmlPar.ERRORCODE_DATA_FPT_NSRSBH);
                    returnInfo.setReturnMessage("发票开具纳税人识别号不合法！");
                    return buildReturnStateInfo(map, returnInfo, "发票开具纳税人识别号不合法！", XmlPar.ERRORCODE_DATA_FPT_NSRSBH);
                }

                if (ValidateUtil.checkParameterIsEmpty(protocolBean.getDDH())) {
                    returnInfo.setReturnCode(XmlPar.ERRORCODE_DATA_FPT_DDH);
                    returnInfo.setReturnMessage("订单号码不能为空");
                    return buildReturnStateInfo(map, returnInfo, "订单号码不能为空", XmlPar.ERRORCODE_DATA_FPT_DDH);
                }

                final String theTaxpayerIdentifyNo = protocolBean.getNSRSBH();

                final TaxpayerEntity taxpayerEntity = repository.getTaxpayer(theTaxpayerIdentifyNo);

                if (taxpayerEntity == null) {
                    returnInfo.setReturnCode(XmlPar.ERRORCODE_DATA_FPT_NSRSBH);
                    returnInfo.setReturnMessage("该纳税人对应平台不存在请确认！");
                    return buildReturnStateInfo(map, returnInfo, "该纳税人对应平台不存在请确认！", XmlPar.ERRORCODE_DATA_FPT_NSRSBH);
                }

                final String invoiceRequestSerialNo = protocolBean.getFPQQLSH();
                final String taxpayerIdentifyNo = protocolBean.getNSRSBH();
                /* final String eShopCode = protocolBean.getDSPTBM(); */
                final String eShopCode = taxpayerEntity.getEshopCode();

                final InvoicePDFInfoEntity pdfInfo = repository.getInvoicePdfInfo(invoiceRequestSerialNo, taxpayerIdentifyNo, eShopCode);

                if (pdfInfo == null) {
                    returnInfo.setReturnCode(XmlPar.ERRORCODE_DATA_FPT_PDFPRODUCTION);
                    returnInfo.setReturnMessage("pdf电子发票正在生产 或 电子发票pdf请求信息错误.");
                    return buildReturnStateInfo(map, returnInfo, "pdf电子发票正在生产 或 电子发票pdf请求信息错误.", XmlPar.ERRORCODE_DATA_FPT_PDFPRODUCTION);
                }

                final RESPONSE_FPXXXZ protocolResponseBean = convertPdfInfoProtocolBean(pdfInfo);

                // 读取pdf文件字节流与url保存到发送协议里面
//                protocolResponseBean.setPDF_URL(SystemConfig.APP_PREFIX + "/eInvoiceDownload?fiscalCode=" + protocolResponseBean.getFWM());
                if("y".equalsIgnoreCase(StaticConstant.PDFURL_N_OR_Y)){
                	protocolResponseBean.setPDF_URL(StaticConstant.PDF_URL_FPXZ+protocolResponseBean.getFWM());
                  }else{
                	  protocolResponseBean.setPDF_URL("");
                  }
                
                if("y".equalsIgnoreCase(StaticConstant.PDFSTREAM_N_OR_Y)){
                	if( pdfInfo.getPdfPath() != null && !pdfInfo.getPdfPath().equals("") && new File(pdfInfo.getPdfPath()).exists()){
                		final byte[] fileByte = FileUtils.readFileToByteArray(new File(pdfInfo.getPdfPath()));
                		protocolResponseBean.setPDF_FILE(new String(Base64.encodeBase64(fileByte)));
                     }
                }
             
                //PDF文件
//                System.out.println(new String(Base64.encodeBase64(fileByte)));
//                System.out.println(new String(Base64.decodeBase64(protocolResponseBean.getPDF_FILE().getBytes())));
                /*
                 * final byte[] pdfBytes =
                 * getPdfBytes(protocolResponseBean.getFWM());
                 * 
                 * 
                 * 
                 * if (pdfBytes != null) { final
                 * org.apache.commons.codec.binary.Base64 base = new
                 * org.apache.commons.codec.binary.Base64();
                 * protocolResponseBean.setPDF_FILE(new
                 * String(base.encode(pdfBytes), SystemConfig.xmlCharset)); }
                 */

                protocolResponseBean.setRETURNCODE(XmlPar.RESPONSEYYSSUCCESS);

                ByteArrayOutputStream out = new ByteArrayOutputStream();
                XMLShellFactory.newInstance().saveXml(out, protocolResponseBean);
                Data returnData = EIProtocolFactory.getData(out);

                map.put(XmlPar.DATA, returnData);
                returnInfo.setReturnCode(XmlPar.RESPONSEYYSSUCCESS);
                returnInfo.setReturnMessage("发票信息查询成功");
                map.put(XmlPar.RETURNSTATEINFO, returnInfo);
            } else {
                return buildReturnStateInfo(map, returnInfo, "处理业务异常", XmlPar.RESPONSEFAIL);
            }
        } catch (Exception e) {
            LOGGER.error("未知：", e);
            returnInfo.setReturnCode(XmlPar.RESPONSEFAIL);
            returnInfo.setReturnMessage("处理业务异常" + e.getMessage());
            map.put(XmlPar.RETURNSTATEINFO, returnInfo);
        }
        return map;
    }

    @Override
    public Map<String, Object> processGetEShopPlatform(Map<String, Object> map) {
        ReturnStateInfo returnInfo = (ReturnStateInfo) map.get(XmlPar.RETURNSTATEINFO);
        Data data = (Data) map.get(XmlPar.DATA);

        try {
            REQUEST_COMMON_DOWNLOAD_DSPTXX protocolBean = (REQUEST_COMMON_DOWNLOAD_DSPTXX) EIProtocolFactory.getDataRoot(data.getContent()).get(0);
            if (protocolBean != null) {
                String eShopCode = protocolBean.getDSPTBM();

                if (ValidateUtil.checkParameterIsEmpty(eShopCode)) {
                    returnInfo.setReturnCode(XmlPar.RESPONSEFAIL);
                    returnInfo.setReturnMessage("平台编码不能为空！");
                    return buildReturnStateInfo(map, returnInfo, "平台编码不能为空！", XmlPar.RESPONSEFAIL);
                }

                if (ValidateUtil.checkParameterLength(eShopCode, 8, 15)) {
                    returnInfo.setReturnCode(XmlPar.RESPONSEFAIL);
                    returnInfo.setReturnMessage("平台编码长度应在8-15之间！");
                    return buildReturnStateInfo(map, returnInfo, "平台编码长度应在8-15之间！", XmlPar.RESPONSEFAIL);
                }

                final EShopInfo eShopInfoEntity =  getRepository().getEShopPlatformInfo(eShopCode);

                RESPONSE_COMMON_DOWNLOAD_DSPTXX protocolResponseBean = convertEShopInfoProtocolBean(eShopInfoEntity);
                protocolResponseBean.setRETURNCODE(XmlPar.RESPONSEYYSSUCCESS);

                ByteArrayOutputStream out = new ByteArrayOutputStream();
                XMLShellFactory.newInstance().saveXml(out, protocolResponseBean);
                Data returnData = EIProtocolFactory.getData(out);

                map.put(XmlPar.DATA, returnData);
                returnInfo.setReturnCode(XmlPar.RESPONSEYYSSUCCESS);
                returnInfo.setReturnMessage("平台获取成功");
                map.put(XmlPar.RETURNSTATEINFO, returnInfo);
            } else {
                return buildReturnStateInfo(map, returnInfo, "平台获取失败，请重新获取", XmlPar.RESPONSEFAIL);
            }

        } catch (Exception e) {
            LOGGER.error("未知：", e);
            returnInfo.setReturnCode(XmlPar.RESPONSEFAIL);
            returnInfo.setReturnMessage("处理业务异常" + e.getMessage());
            map.put(XmlPar.RETURNSTATEINFO, returnInfo);
        }

        return map;
    }

    @Override
    public Map<String, Object> processGetEShopEnterprise(Map<String, Object> map) {
        RESPONSE_COMMON_DJDSQYXXGLOBLE protocolResponseBean = new RESPONSE_COMMON_DJDSQYXXGLOBLE();

        ReturnStateInfo returnInfo = (ReturnStateInfo) map.get(XmlPar.RETURNSTATEINFO);
        Data data = (Data) map.get(XmlPar.DATA);

        try {
            REQUEST_COMMON_DSQYXX protocolBean = (REQUEST_COMMON_DSQYXX) EIProtocolFactory.getDataRoot(data.getContent()).get(0);

            if (protocolBean != null) {

                if (protocolBean.getNSRSBH() != null && !"".equals(protocolBean.getNSRSBH())) {
                    if (ValidateUtil.checkParameterLength(protocolBean.getNSRSBH(), 15, 20)) {
                        returnInfo.setReturnCode(XmlPar.ERRORCODE_DATA_FPT_NSRSBH);
                        returnInfo.setReturnMessage("纳税人识别号不合法！");
                        return buildReturnStateInfo(map, returnInfo, "纳税人识别号不合法！", XmlPar.ERRORCODE_DATA_FPT_NSRSBH);
                    }
                }
                if (ValidateUtil.checkParameterIsEmpty(protocolBean.getDSPTBM())) {
                    returnInfo.setReturnCode(XmlPar.RESPONSEFAIL);
                    returnInfo.setReturnMessage("平台编码不能为空！");
                    return buildReturnStateInfo(map, returnInfo, "平台编码不能为空！", XmlPar.RESPONSEFAIL);
                }

                if (ValidateUtil.checkParameterLength(protocolBean.getDSPTBM(), 8, 15)) {
                    returnInfo.setReturnCode(XmlPar.RESPONSEFAIL);
                    returnInfo.setReturnMessage("平台编码长度应在8-15之间！");
                    return buildReturnStateInfo(map, returnInfo, "平台编码长度应在8-15之间！", XmlPar.RESPONSEFAIL);
                }

                /* MARK 2014.11.11：原通过纳税人识别号与平台编码获取纳税人LIST，现按照设计约束获取为单一纳税人 */
                final EInvoiceSubRepository repository = getRepository();

                final TaxpayerEntity taxpayerEntity = repository.getTaxpayer(protocolBean.getNSRSBH(), protocolBean.getDSPTBM());

                final RESPONSE_COMMON_DJDSQYXX protocolEShopEnterpriseBean = convertEShopEnterpriseProtocolBean(taxpayerEntity);

                final List<RESPONSE_COMMON_DJDSQYXX> protocolEShopEnterpriseBeanArray = new ArrayList<RESPONSE_COMMON_DJDSQYXX>();
                protocolEShopEnterpriseBeanArray.add(protocolEShopEnterpriseBean);

                /*
                 * MARK 2014.11.11: convert protocolEShopEnterpriseBeanArray -->
                 * composeBeanArray
                 */
                final RESPONSE_COMMON_DJDSQYXX[] composeBeanArray = new RESPONSE_COMMON_DJDSQYXX[protocolEShopEnterpriseBeanArray.size()];

                for (int x = 0; x < protocolEShopEnterpriseBeanArray.size(); x++) {
                    composeBeanArray[x] = protocolEShopEnterpriseBeanArray.get(x);
                }

                /* MARK 2014.11.11:convert composeBeanArray --> invoiceKindList */
                final List<RESPONSE_COMMON_DSQYPZHDXX> invoiceKindList = new ArrayList<RESPONSE_COMMON_DSQYPZHDXX>();

                if (composeBeanArray != null && composeBeanArray.length > 0) {
                    for (int i = 0; i < composeBeanArray.length; i++) {

                        final List<RESPONSE_COMMON_DSQYPZHDXX> eachInvoiceKindProtocolBeanArray = convertInvoiceKindProtocolBeanArray(repository
                                .getInvoiceKindInfo(composeBeanArray[i].getNSRSBH()));

                        if (eachInvoiceKindProtocolBeanArray != null && eachInvoiceKindProtocolBeanArray.size() > 0) {
                            invoiceKindList.addAll(eachInvoiceKindProtocolBeanArray);
                        } else {
                            LOGGER.error("未查询到纳税人为" + composeBeanArray[i].getNSRSBH() + "的票种信息");
                        }

                    }
                }

                /*
                 * MARK 2014.11.11:convert invoiceKindList -->
                 * invoiceKindProtocolBeanArray
                 */
                RESPONSE_COMMON_DSQYPZHDXX[] invoiceKindProtocolBeanArray = new RESPONSE_COMMON_DSQYPZHDXX[invoiceKindList.size()];

                if (invoiceKindList.size() > 0) {
                    for (int i = 0; i < invoiceKindList.size(); i++) {
                        invoiceKindProtocolBeanArray[i] = invoiceKindList.get(i);
                    }
                }

                protocolResponseBean.setRESPONSE_COMMON_DJDSQYXXS(composeBeanArray);
                protocolResponseBean.setRESPONSE_COMMON_DSQYPZHDXXS(invoiceKindProtocolBeanArray);
                protocolResponseBean.setRETURNCODE(XmlPar.RESPONSEYYSSUCCESS);

                ByteArrayOutputStream out = new ByteArrayOutputStream();
                XMLShellFactory.newInstance().saveXml(out, protocolResponseBean);
                Data returnData = EIProtocolFactory.getData(out);

                map.put(XmlPar.DATA, returnData);
                returnInfo.setReturnCode(XmlPar.RESPONSEYYSSUCCESS);
                returnInfo.setReturnMessage("平台企业信息获取成功");
                map.put(XmlPar.RETURNSTATEINFO, returnInfo);

            } else {
                return buildReturnStateInfo(map, returnInfo, "平台企业信息获取失败，请重新获取", XmlPar.RESPONSEFAIL);
            }

        } catch (Exception e) {
            LOGGER.error("未知：", e);
            returnInfo.setReturnCode(XmlPar.RESPONSEFAIL);
            returnInfo.setReturnMessage("处理业务异常" + e.getMessage());
            map.put(XmlPar.RETURNSTATEINFO, returnInfo);
        }

        return map;
    }

    @Override
    public byte[] getPdfBytes(String fiscalCode) {
        final String pdfPath = getRepository().getInvoicePdfPath(fiscalCode);
        if(pdfPath != null && !pdfPath.equals("") && new File(pdfPath).exists()){
        	byte[] out = null;
              try {
                   LOGGER.info("getInvoicePdfPath fiscalCode {}- pdfPath {}", fiscalCode, pdfPath);
                   out = readFileToByteArray(new File(pdfPath));
              } catch (IOException e) {
                  LOGGER.error("catch IOException: pdfPath {}", pdfPath,e);
                 }
            return out;
        }else {
        	LOGGER.error("pdf路径不存在 getInvoicePdfPath fiscalCode {}- pdfPath {}", fiscalCode, pdfPath);
        	return new byte[0];
        }
       
    }

    @Override
    public Map<String, Object> processGetEShopCAInfo(Map<String, Object> paramMap) {
        RESPONSE_COMMON_DOWNLOAD_CA protocolResponseBean;
        final Data data = (Data) paramMap.get(XmlPar.DATA);
        final ReturnStateInfo returnInfo = (ReturnStateInfo) paramMap.get(XmlPar.RETURNSTATEINFO);
        try {
            final REQUEST_COMMON_DOWNLOAD_CA protocolBean = (REQUEST_COMMON_DOWNLOAD_CA) EIProtocolFactory.getDataRoot(data.getContent()).get(0);

            if (protocolBean != null) {

                if (protocolBean.getNSRSBH() != null && !"".equals(protocolBean.getNSRSBH())) {
                    if (ValidateUtil.checkParameterLength(protocolBean.getNSRSBH(), 15, 20)) {
                        returnInfo.setReturnCode(XmlPar.ERRORCODE_DATA_FPT_NSRSBH);
                        returnInfo.setReturnMessage("纳税人识别号不合法！");
                        return buildReturnStateInfo(paramMap, returnInfo, "纳税人识别号不合法！", XmlPar.ERRORCODE_DATA_FPT_NSRSBH);
                    }
                }
                if (ValidateUtil.checkParameterIsEmpty(protocolBean.getDSPTBM())) {
                    returnInfo.setReturnCode(XmlPar.RESPONSEFAIL);
                    returnInfo.setReturnMessage("平台编码不能为空！");
                    return buildReturnStateInfo(paramMap, returnInfo, "平台编码不能为空！", XmlPar.RESPONSEFAIL);
                }
                if (ValidateUtil.checkParameterLength(protocolBean.getDSPTBM(), 8, 15)) {
                    returnInfo.setReturnCode(XmlPar.RESPONSEFAIL);
                    returnInfo.setReturnMessage("平台编码长度应在8-15之间！");
                    return buildReturnStateInfo(paramMap, returnInfo, "平台编码长度应在8-15之间！", XmlPar.RESPONSEFAIL);
                }

                final EInvoiceQueryCondition param = new EInvoiceQueryCondition();
                param.setTaxpayerIdentifyNo(protocolBean.getNSRSBH());
                param.setPlatformCode(protocolBean.getDSPTBM());
                param.setAuthCode(protocolBean.getNSRSQM());
                param.setRegisterNo(protocolBean.getDSPTZCM());

                /*
                 * TRACY MARK:通讯CA加密方式采用2套证书对称进行；客户端事件使用 客户端私钥(pfx)、pwd +
                 * 平台公钥(cer) + 公共信任链
                 */
                final CertificateEntity clientCertificate = invoiceDataAccessManagerService.getCertificateEntity(param);
                final CertificateEntity platformCertificate = invoiceDataAccessManagerService.getPlatformCertificateEntity();
                clientCertificate.setTrust(platformCertificate.getTrust());
                clientCertificate.setPublicKey(platformCertificate.getPublicKey());

                protocolResponseBean = convertEShopCAResponseProtocolBean(clientCertificate, param);

                final ByteArrayOutputStream out = new ByteArrayOutputStream();
                XMLShellFactory.newInstance().saveXml(out, protocolResponseBean);
                final Data returnData = EIProtocolFactory.getData(out);

                paramMap.put(XmlPar.DATA, returnData);
                returnInfo.setReturnCode(XmlPar.RESPONSEYYSSUCCESS);
                returnInfo.setReturnMessage("平台企业CA获取成功");
                paramMap.put(XmlPar.RETURNSTATEINFO, returnInfo);

            } else {
                return buildReturnStateInfo(paramMap, returnInfo, "平台企业CA获取失败，请重新获取", XmlPar.RESPONSEFAIL);
            }

        } catch (Exception e) {
            LOGGER.error("未知：", e);
            returnInfo.setReturnCode(XmlPar.RESPONSEFAIL);
            returnInfo.setReturnMessage("处理业务异常" + e.getMessage());
            paramMap.put(XmlPar.RETURNSTATEINFO, returnInfo);
        }

        return paramMap;
    }

    @Override
    @Cacheable(value = "eshopPCKS7Cache", key = "#taxpayerIdentifyNo")
    public EShopCertificateBytesInfo obtainEShopCAInfo(String taxpayerIdentifyNo) throws IOException {

        /*
         * TRACY MARK:通讯CA加密方式采用2套证书对称进行；平台端事件使用 平台端私钥(pfx)、pwd + 客户端公钥(cer) +
         * 公共信任链
         */
        final CertificateEntity clientCertificate = invoiceDataAccessManagerService.getCertificateEntity(taxpayerIdentifyNo);
        final CertificateEntity platformCertificate = invoiceDataAccessManagerService.getPlatformCertificateEntity();
        platformCertificate.setPublicKey(clientCertificate.getPublicKey());

        try {
            final EShopCertificateBytesInfo certificateBytesInfo = new EShopCertificateBytesInfo();

            certificateBytesInfo.setTaxpayerIdentifyNo(taxpayerIdentifyNo);
            certificateBytesInfo.setPrivatePFXBytes(readFileToByteArray(new File(platformCertificate.getPrivateKey())));
            certificateBytesInfo.setPublicPFXBytes(readFileToByteArray(new File(platformCertificate.getPublicKey())));
            certificateBytesInfo.setTrustsBytes(readFileToByteArray(new File(platformCertificate.getTrust())));
            certificateBytesInfo.setPrivatePFXKey(platformCertificate.getPassword());

            return certificateBytesInfo;

        } catch (Exception e) {
            return null;
        }

    }

    /* TRACY RENEW SECTION 2014.11.11:新协议bean系列转换 */

    /**
     * 从文件目录获取文件字节流Base64后String值
     * 
     * @param thePath
     *            文件目录
     * @return String 文件字节流Base64后String值，若catch异常则返回为null
     */
    private String getFileByteString(final String thePath) {

        String fileByte = null;
        try {
            final byte[] out = readFileToByteArray(new File(thePath));
            fileByte = EIProtocolFactory.encodeToString(out);

        } catch (IOException e) {
            LOGGER.error("catch IOException-readFileToByteArray thePath {}：", thePath);
        }
        return fileByte;
    }

    private RESPONSE_FPXXXZ convertPdfInfoProtocolBean(final InvoicePDFInfoEntity entity) {
        final RESPONSE_FPXXXZ protocolBean = new RESPONSE_FPXXXZ();
        protocolBean.setKPLSH(entity.getInvoiceSerialNo());
        protocolBean.setFPQQLSH(entity.getInvoiceRequestSerialNo());
        protocolBean.setFWM(entity.getFiscalCode());
        protocolBean.setEWM(entity.getTwoDimensionCode());
        protocolBean.setFPZL_DM(entity.getInvoiceKindCode());
        protocolBean.setFP_DM(entity.getInvoiceCode());
        protocolBean.setFP_HM(entity.getInvoiceNo());
        protocolBean.setKPLX(entity.getBillingType().toString());
        protocolBean.setKPRQ(entity.getBillingDate());
        protocolBean.setDDH(entity.getOrderNo());
        protocolBean.setCZDM(entity.getOperatorNo());

        return protocolBean;
    }

    private RESPONSE_COMMON_DOWNLOAD_DSPTXX convertEShopInfoProtocolBean(final EShopInfo entity) {

        final RESPONSE_COMMON_DOWNLOAD_DSPTXX protocolBean = new RESPONSE_COMMON_DOWNLOAD_DSPTXX();

        protocolBean.setDSPTBM(entity.getEshopCode());
        protocolBean.setDSPTMC(entity.getEshopName());
        protocolBean.setICP(entity.getIcp());
        protocolBean.setDSPTIP(entity.geteShopIp());
        protocolBean.setDSWZYM(entity.geteShopDomain());
        protocolBean.setZBDWMC(entity.getHostCompany());
        protocolBean.setZBDWDZ(entity.getHostAddress());
        protocolBean.setZBDWSWDJZH(entity.getHostCompanyTaxRegisterNo());
        protocolBean.setFRDB(entity.getLegalName());
        protocolBean.setFRDBZJHM(entity.getLegalNameCertNo());
        protocolBean.setBLRGDDH(entity.getTransactorTel());
        protocolBean.setBLRMC(entity.getTransactorName());
        protocolBean.setBLRZJHM(entity.getTransactorCertNo());
        protocolBean.setBLRSJH(entity.getTransactorPhone());
        protocolBean.setBLRYX(entity.getTransactorEmail());
        protocolBean.setZCDSSJ(entity.getEshopRegisterDateValue());
        protocolBean.setZCZT(entity.getRegisterStatus());
        protocolBean.setZCH(entity.getRegisterNo());
        protocolBean.setSZ_SWJG_DM(entity.getTaxAuthorityCode());

        return protocolBean;
    }

    private RESPONSE_COMMON_DOWNLOAD_CA convertEShopCAResponseProtocolBean(final CertificateEntity entity, final EInvoiceQueryCondition param) {

        final RESPONSE_COMMON_DOWNLOAD_CA protocolBean = new RESPONSE_COMMON_DOWNLOAD_CA();

        protocolBean.setDSPTBM(entity.getPlatformCode());
        protocolBean.setDSPTZCM(param.getRegisterNo());
        protocolBean.setNSRSBH(entity.getTaxpayerIdentifyNo());
        protocolBean.setNSRSQM(param.getAuthCode());
        protocolBean.setCER_File(getFileByteString(entity.getPublicKey()));
        protocolBean.setPFX_File(getFileByteString(entity.getPrivateKey()));

        protocolBean.setPFX_Key(entity.getPassword());
        protocolBean.setTRUST_File(getFileByteString(entity.getTrust()));

        return protocolBean;
    }

    private RESPONSE_COMMON_DJDSQYXX convertEShopEnterpriseProtocolBean(final TaxpayerEntity entity) {

        final RESPONSE_COMMON_DJDSQYXX protocolBean = new RESPONSE_COMMON_DJDSQYXX();

        protocolBean.setNSRSBH(entity.getTaxpayerIdentifyNo());
        protocolBean.setNSRDZDAH(entity.getTaxpayerEDNo());
        protocolBean.setNSRMC(entity.getTaxpayer());
        protocolBean.setHYDM(entity.getIndustryCode());
        protocolBean.setHYMC(entity.getIndustryName());
        protocolBean.setNSRZT(entity.getTaxpayerStatusCode());
        protocolBean.setDJZCLX(entity.getRegisterTypeCode());
        protocolBean.setZGSWRYDM(entity.getTaxChargePersonCode());
        protocolBean.setFDDBR(entity.getLegalName());
        protocolBean.setBSRMC(entity.getTaxBusinessPerson());
        protocolBean.setSWDJBLX_DM(entity.getTaxRegisterTableTypeCode());
        protocolBean.setSWJG_DM(entity.getTaxOfficeRegCode());
        protocolBean.setSCJYDZ(entity.getBusinessAddress());
        protocolBean.setKPZT(entity.getTaxpayerInvoiceStatus());

        return protocolBean;
    }

    private List<RESPONSE_COMMON_DSQYPZHDXX> convertInvoiceKindProtocolBeanArray(final List<InvoiceKindInfoEntity> entityList) {

        final List<RESPONSE_COMMON_DSQYPZHDXX> protocolBeanArray = new ArrayList<RESPONSE_COMMON_DSQYPZHDXX>();

        for (InvoiceKindInfoEntity eachEntity : entityList) {

            RESPONSE_COMMON_DSQYPZHDXX eachProtocolBean = new RESPONSE_COMMON_DSQYPZHDXX();

            eachProtocolBean.setNSRSBH(eachEntity.getTaxpayerIdentifyNo());
            eachProtocolBean.setNSRDZDAH(eachEntity.getTaxpayerEDNo());
            eachProtocolBean.setFPZL_DM(eachEntity.getInvoiceKindCode());
            eachProtocolBean.setFPZL_MC(eachEntity.getInvoiceKind());
            eachProtocolBean.setMYGPZGSL(eachEntity.getPurchaseInvoiceLimited());
            eachProtocolBean.setMYCPZGSL(eachEntity.getHoldInvoiceLimited());
            eachProtocolBean.setKPZGXE(eachEntity.getInvoiceLimited());

            protocolBeanArray.add(eachProtocolBean);
        }

        return protocolBeanArray;
    }

    /*
     * 1此转换方法依据两个类属性与 被要求转换对象的最近DB操作涉及属性 2 函数过程使用对象包含协议bean，去除
     * 原FPTXX形态类（com.aisino.einvoice.domain） 3 原使用entity 如
     * FPTXX形态类不再使用，保证过程中所有产生属性全部赋于协议bean，保证方法后步骤中 FPTXX形态类再次被引用的属性在协议bean中完整。
     */
    private InvoiceHeaderEntity convertInvoiceEntity(final COMMON_FPKJ_FPT sourceObj, final Long uniqueId, final String theItemCount,
            final String theTaxAuthorityCode) {
        final InvoiceHeaderEntity entity = new InvoiceHeaderEntity();
        entity.setId(uniqueId);
        entity.setInvoiceRequestSerialNo(sourceObj.getFPQQLSH());
        entity.setEshopCode(sourceObj.getDSPTBM());

        entity.setTaxpayerIdentifyNo(sourceObj.getNSRSBH());
        entity.setTaxpayer(sourceObj.getNSRMC());
        entity.setTaxpayerEDNo(sourceObj.getNSRDZDAH());
        entity.setAgentInvoiceFlag(sourceObj.getDKBZ());
        entity.setSampleInvoiceCode(sourceObj.getPYDM());
        entity.setBillingItem(sourceObj.getKPXM());
        entity.setSellerTaxpayerIdentifyNo(sourceObj.getXHF_NSRSBH());
        entity.setSellerName(sourceObj.getXHF_MC());

        entity.setBuyerName(sourceObj.getGHF_MC());
        entity.setBuyerTaxpayerIdentifyNo(sourceObj.getGHF_NSRSBH());
        entity.setBuyerAddress(sourceObj.getGHF_DZ());
        entity.setBuyerFixedPhone(sourceObj.getGHF_GDDH());
        entity.setBuyerMobile(sourceObj.getGHF_SJ());
        entity.setBuyerEmail(sourceObj.getGHF_YX());

        entity.setIndustryCode(sourceObj.getHY_DM());
        entity.setIndustryName(sourceObj.getHY_MC());
        entity.setBillingStaff(sourceObj.getKPY());
        entity.setCashier(sourceObj.getSKY());
        entity.setBillingType(Long.parseLong(sourceObj.getKPLX()));
        entity.setOldInvoiceCode(sourceObj.getYFP_DM());
        entity.setOldInvoiceNo(sourceObj.getYFP_HM());
        entity.setRedInvoiceReason(sourceObj.getCHYY());
        entity.setBillingAmount(Double.parseDouble(sourceObj.getKPHJJE()));
        
        if("1".equals(sourceObj.getKPLX())){//蓝票保留原来传递的值
            entity.setMemo(sourceObj.getBZ());
        }else if(("2".equals(sourceObj.getKPLX())) ||("9".equals(sourceObj.getKPLX()))){//红票判断是否有',原发票代码',有的话,截取前面的字符串
            String remark = sourceObj.getBZ();
            if(!StringUtils.isBlank(remark)){
                int endIndex = 0;
                if(remark.contains(",原发票代码")){
                    endIndex = remark.indexOf(",原发票代码");
                    remark = remark.substring(0, endIndex);
                }
                
                // 开具红票时，处理下备注信息
                entity.setMemo(remark);
                sourceObj.setBZ(remark);
            }else {
                entity.setMemo(sourceObj.getBZ());
            }
                
        }
        entity.setTaxAuthorityCode(theTaxAuthorityCode);
        entity.setPayCompany(sourceObj.getGHF_MC());
        entity.setPayCode(sourceObj.getGHF_NSRSBH());
        entity.setReceiveCode(sourceObj.getNSRSBH());
        entity.setItemCount(theItemCount);
        entity.setSellerIdentifyNo(sourceObj.getXHF_NSRSBH());
        entity.setTaxOfficeRegCode(sourceObj.getSWJG_DM());
        entity.setBuyerEnterpriseTypeCode(sourceObj.getGHF_QYLX());
        entity.setOperatorNo(sourceObj.getCZDM());
        entity.setRemainingRedAmount(Double.parseDouble(sourceObj.getKPHJJE()));
        /**
         * 2015年3月11日 14:47:30 zhongsiwei begin
         */
        entity.setInfoChecker(sourceObj.getFHR());
        // entity.setInfoListName(sourceObj.getXHQD());
        entity.setInfoAmount(Double.parseDouble(sourceObj.getHJBHSJE()));
        entity.setInfoTaxAmount(Double.parseDouble(sourceObj.getKPHJSE()));
        // entity.setInfoMonth(sourceObj.getSSYF());
        // entity.setGoodsListFlag(sourceObj.getXHQDBZ());
        // entity.setRetCode(sourceObj.getRETCODE());
        // entity.setCiphertext(sourceObj.getFWMW());
        // entity.setCheckCode(sourceObj.getJYM());
        // entity.setInfoInvoicer(sourceObj.getSZQM());
        // entity.setInfoClientBank(sourceObj.getFKFKHYH());
        entity.setInfoClientBankAccount(sourceObj.getFKF_YHZH());
        // entity.setInfoSellerBank(sourceObj.getSKFKHYH());
        entity.setInfoSellerBankAccount(sourceObj.getSKF_YHZH());
        entity.setInfoSellerAddress(sourceObj.getXHF_DZ());
        entity.setInfoSellerPhone(sourceObj.getXHF_DH());
        /**
         * 2015年3月11日 14:47:30 zhongsiwei begin
         */

        entity.setMainProductName(sourceObj.getKPXM());
        
        /**
         * 2016年7月5日 19:35:04 将BMB_BBH 保存到数据库  阳开国
         */
        // 将BMB_BBH 保存到数据库 
        entity.setInfoSellerVersion(sourceObj.getBMB_BBH());

        return entity;
    }

    /* 2014.09.30:insert pk_kj */
    private Boolean processInsertInvoice(final COMMON_FPKJ_XMXX[] protocolProjectBean, COMMON_FPKJ_FPT protocolHeaderBean,
            final EInvoiceSubRepository repository, final Long uniqueId, final TaxpayerEntity theTaxpayer) {

        final DateTime begin = now();

        /* ORIGIN CODE: BeanMapUtil.copyLeft2Ritht(fpkjxx_fptxx, fptxx); */
        final COMMON_FPKJ_XMXX projectBean = protocolProjectBean[0];

        protocolHeaderBean.setNSRDZDAH(theTaxpayer.getTaxpayerEDNo());
        protocolHeaderBean.setGHF_SJ(protocolHeaderBean.getGHF_SJ().trim());
        protocolHeaderBean.setKPXM(projectBean.getXMMC());
        // protocolHeaderBean.setKPRQ("");
        protocolHeaderBean.setSWJG_DM(theTaxpayer.getTaxOfficeRegCode());

        final InvoiceHeaderEntity entityCompose = convertInvoiceEntity(protocolHeaderBean, uniqueId, String.valueOf(protocolProjectBean.length), theTaxpayer
                .getTaxAuthorityCode());

        final Boolean result = repository.insertInvoice(entityCompose);

        final Long millSeconds = new Duration(begin, now()).getMillis();
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("(平台信息处理)插入FP_KJ表发票id{}, 总共耗时{}毫秒", entityCompose.getId(), millSeconds);
        }

        return result;
    }

    /* 2014.09.30:insert(update) pk_kj_log */
    private Boolean processSaveInvoiceLog(final COMMON_FPKJ_FPT protocolHeaderBean, final EInvoiceSubRepository repository, final Long uniqueId) {
        final DateTime begin = now();

        final InvoiceEntity entityCompose = new InvoiceEntity();

        entityCompose.setInvoiceId(uniqueId);
        entityCompose.setTaxpayerIdentifyNo(protocolHeaderBean.getNSRSBH());
        entityCompose.setInvoiceIdentifyNo(protocolHeaderBean.getXHF_NSRSBH());
        entityCompose.setTaxpayer(protocolHeaderBean.getXHF_MC());
        entityCompose.setPayName(protocolHeaderBean.getGHF_MC());
        entityCompose.setTransactionSerialNo(protocolHeaderBean.getFPQQLSH());
        entityCompose.setInvoiceStatus(INV_GETDATA_SUCCESS);

        /* 操作InvoiceLog时判断是否存在数据，存在修改不存在新增. */
        final Boolean markInvoiceLog = repository.verifyInvoiceLogByInvoiceId(uniqueId);
        Boolean result = Boolean.FALSE;
        if (markInvoiceLog) {
            result = repository.updateInvoiceLog(entityCompose);
        } else {
            final Long theId = uniqueResourceManagerService.obtainBusinessIdFromQueue(FPKJ_LOG_ID.name());
            LOGGER.info("GET FPKJ_LOG_ID uniqueId {}", theId);
            entityCompose.setId(theId);
            result = repository.insertInvoiceLog(entityCompose);
        }

        final Long millSeconds = new Duration(begin, now()).getMillis();
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("(平台信息处理)插入或更新FP_KJ_LOG表发票开据id{}, 总共耗时{}毫秒", entityCompose.getInvoiceId(), millSeconds);
        }

        return result;
    }

    /* 2014.10.06:insert fp_chfpsqd */
    private Boolean processInsertInvoiceRed(final COMMON_FPKJ_FPT protocolHeaderBean, final EInvoiceSubRepository repository, final Long uniqueId) {
        final DateTime begin = now();

        final InvoiceRedEntity entityCompose = new InvoiceRedEntity();

        final Long theId = uniqueResourceManagerService.obtainBusinessIdFromQueue(FPCHFPSQD_ID.name());
        entityCompose.setId(theId);
        entityCompose.setInvoiceId(uniqueId);
        entityCompose.setEshopCode(protocolHeaderBean.getDSPTBM());
        entityCompose.setTaxpayerIdentifyNo(protocolHeaderBean.getNSRSBH());
        entityCompose.setTaxpayerEDNo(protocolHeaderBean.getNSRDZDAH());

        /*
         * 发票代码invoiceCode(FP_DM)、发票号码invoiceNo(FP_HM)赋值拷贝代码如
         * 'BeanMapUtil.copyLeft2Ritht(protocolHeaderBean,
         * fpchfpsqd);'，通过'beanmap.xml'中映射关系, 即可得出发票冲红申请信息中使用
         * YFP_DM、YFP_HM,即如下set. 赋值拷贝依据原映射XML.
         */
        entityCompose.setInvoiceCode(protocolHeaderBean.getYFP_DM());
        entityCompose.setInvoiceNo(protocolHeaderBean.getYFP_HM());

        entityCompose.setBillingType(Long.parseLong(protocolHeaderBean.getKPLX()));
        entityCompose.setRedAmount(Double.parseDouble(protocolHeaderBean.getKPHJJE()));
        entityCompose.setRedInvoiceReason(protocolHeaderBean.getCHYY());
        entityCompose.setRecipientPhone(protocolHeaderBean.getGHF_GDDH());
        entityCompose.setRecipientEmail(protocolHeaderBean.getGHF_YX());
        entityCompose.setApplySerialNo(protocolHeaderBean.getFPQQLSH());
        entityCompose.setOperatorNo(protocolHeaderBean.getCZDM());

        final Boolean result = repository.insertInvoiceRed(entityCompose);

        final Long millSeconds = new Duration(begin, now()).getMillis();
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("(平台信息处理)插入fp_chfpsqd表发票开据id{}, 总共耗时{}毫秒", entityCompose.getInvoiceId(), millSeconds);
        }

        return result;

    }

    /* 2014.10.06:batch insert fp_kjmx */
    private void processInsertInvoiceDetail(final COMMON_FPKJ_XMXX[] protocolProjectBean, final EInvoiceSubRepository repository, final Long uniqueId) throws UnsupportedEncodingException {
        final DateTime begin = now();

        if (protocolProjectBean != null && protocolProjectBean.length > 0) {
        	
        	/*
        	 *获取fp_kjmx_ID 
        	 */
        	Long theIds[] = new Long[protocolProjectBean.length];
             for (int i = 0; i < protocolProjectBean.length; i++) {
             	theIds[i] = uniqueResourceManagerService.obtainBusinessIdFromQueue(FPKJMX_ID.name());
             	 LOGGER.info("GET FPKJMX_ID uniqueId {}", theIds[i]);
             	
 			}
            /*
             * 发票行性质赋值,反向循环计算发票行性质,
             */
            /*int zkhs= 0;    //折扣行数数量
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
            }*/
            for (int i = 0; i < protocolProjectBean.length; i++) {

                final COMMON_FPKJ_XMXX eachBean = protocolProjectBean[i];

                final InvoiceDetailEntity eachEntityCompose = new InvoiceDetailEntity();

                eachEntityCompose.setId(theIds[i]);
                eachEntityCompose.setInvoiceId(uniqueId);

                eachEntityCompose.setItemIndex(Long.parseLong(String.valueOf(i + 1)));
                //截取项目名称长度
                if(!StringUtils.isBlank(eachBean.getXMMC())){
                	//添加商品类型前缀
                	String splxmc = commodityCategory(eachBean.getXMMC(),eachBean.getSPBM());
                    String xmmc = xmmcsubstring(splxmc, 90, "GBK");
                    eachEntityCompose.setItemName(new String(xmmc.getBytes("UTF-8"),"UTF-8"));
                    eachBean.setXMMC(eachEntityCompose.getItemName());
                }
//                eachEntityCompose.setItemName(eachBean.getXMMC());
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
                // 新增明细字段入库
                /**
                 * 2016年7月5日 20:05:03 新增明细字段 阳开国 begin
                 */
                eachEntityCompose.setGoodsCode(eachBean.getSPBM().trim());
                eachEntityCompose.setSelfCoding(eachBean.getZXBM());
                eachEntityCompose.setPreferentialMarking(eachBean.getYHZCBS());
                eachEntityCompose.setZeroTariff(eachBean.getLSLBS());
                eachEntityCompose.setSpecialManagement(eachBean.getZZSTSGL());
                eachEntityCompose.setDeductions(eachBean.getKCE());
                /**
                 * 2016年7月5日 20:05:03 新增明细字段 阳开国 end
                 */
                repository.insertInvoiceDetail(eachEntityCompose);

            }
        }
        final Long millSeconds = new Duration(begin, now()).getMillis();
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("(平台信息处理)循环插入fp_kjmx表, 总共耗时{}毫秒", millSeconds);
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
    private static COMMON_FPKJ_XMXX[] processRedInvoiceDetailForCheckListSumAmount(final COMMON_FPKJ_XMXX[] protocolProjectBean , final COMMON_FPKJ_FPT protocalFpt) throws Exception {
        COMMON_FPKJ_XMXX[] protocolProjectBeannew = null;
        //获取发票头信息中的合计不含税金额
        double hjbhsje = Double.parseDouble(protocalFpt.getHJBHSJE());
        //明细行总金额
        double mxzje = 0.00;
        //合计不含税金额和明细总金额的差值
        double differ = 0.00;
        if (protocolProjectBean != null && protocolProjectBean.length > 0) {
            List<COMMON_FPKJ_XMXX> mxlist = new ArrayList<COMMON_FPKJ_XMXX>();
            for (int i = 0; i < protocolProjectBean.length; i++) {
                final COMMON_FPKJ_XMXX fpkjxx_xmxxs = protocolProjectBean[i];
                mxzje += Math.abs(Double.parseDouble(ValidateUtil.decimalFormat(fpkjxx_xmxxs.getXMJE(), 2)));
            }
            //如果合计不含税金额和明细总金额的差值 大于0,进行金额调整
            if(differ > 0 ){
                /**
                 * 倒着循环,去商品行中做金额调整,如果当前行金额调整之后小于0,调整完成,跳出循环,如果循环到第一行,调整金额仍大于等于0 ,抛异常
                 */
                for(int i = protocolProjectBean.length;i>=0;i-- ){
                    //发票明细行信息的不含税金额和税额的调整
                    double newXmje = Double.parseDouble(protocolProjectBean[i].getXMJE()) + differ ;
                    double newSe = Double.parseDouble(protocolProjectBean[i].getSE()) - differ ;
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
            
            
            protocolProjectBeannew = new COMMON_FPKJ_XMXX[mxlist.size()];
            for (int j = 0; j < mxlist.size(); j++) {
                protocolProjectBeannew[j] = mxlist.get(j);
            }
            
        }
        return protocolProjectBeannew;

    }

    /* 2014.10.06:insert fp_ddxx */
    private Boolean processInsertInvoiceOrder(final COMMON_FPKJ_FPT protocolHeaderBean, final COMMON_FPKJ_DDXX protocolOrderBean,
            final EInvoiceSubRepository repository, final Long uniqueId) {
        final DateTime begin = now();

        final InvoiceOrderEntity entityCompose = new InvoiceOrderEntity();

        final Long theId = uniqueResourceManagerService.obtainBusinessIdFromQueue(FPDDXX_ID.name());
        LOGGER.info("GET FPDDXX_ID uniqueId {}", theId);
        entityCompose.setId(theId);
        entityCompose.setInvoiceId(uniqueId);
        entityCompose.setEshopCode(protocolHeaderBean.getDSPTBM());

        if (beRed(protocolHeaderBean.getKPLX()) && !Strings.isNullOrEmpty(protocolOrderBean.getTHDH())) {
            entityCompose.setOrderNo(protocolOrderBean.getTHDH());
        } else {
            entityCompose.setOrderNo(protocolOrderBean.getDDH());
        }
        protocolOrderBean.setDDH(entityCompose.getOrderNo());

        entityCompose.setReturnOrderNo(protocolOrderBean.getTHDH());

        if (!Strings.isNullOrEmpty(protocolOrderBean.getDDSJ()) && !Strings.isNullOrEmpty(protocolOrderBean.getDDSJ().trim())) {
            entityCompose.setOrderDate(Timestamp.valueOf(protocolOrderBean.getDDSJ()));  
            // TIMESTAMP
            // PARSE
        }

        final Boolean result = repository.insertInvoiceOrder(entityCompose);

        final Long millSeconds = new Duration(begin, now()).getMillis();
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("(平台信息处理)插入fp_ddxx表发票开据id{}, 总共耗时{}毫秒", entityCompose.getInvoiceId(), millSeconds);
        }

        return result;
    }

    /* 2014.10.06:insert fp_ddmxxx */
    private void processInsertInvoiceOrderDetail(final REQUEST_COMMON_FPKJ protocolBean, final COMMON_FPKJ_DDXX protocolOrderBean,
            final EInvoiceSubRepository repository, final Long uniqueId) {
        final DateTime begin = now();
        // 订单明细信息不入库
        // final COMMON_FPKJ_DDMXXX[] protocolOrderDetailBean =
        // protocolBean.getCOMMON_FPKJ_DDMXXXS();

        /*
         * if (protocolOrderDetailBean != null && protocolOrderDetailBean.length
         * > 0) {
         * 
         * final Long theIds[] =
         * uniqueResourceManagerService.obtainBusinessId(FPDDMXXX_ID.name(),
         * protocolOrderDetailBean.length);
         * 
         * for (int i = 0; i < protocolOrderDetailBean.length; i++) { final
         * COMMON_FPKJ_DDMXXX eachBean = protocolOrderDetailBean[i];
         * 
         * final InvoiceOrderDetailEntity eachEntityCompose = new
         * InvoiceOrderDetailEntity();
         * 
         * eachEntityCompose.setId(theIds[i]);
         * eachEntityCompose.setInvoiceId(uniqueId);
         * eachEntityCompose.setItemIndex(Long.parseLong(String.valueOf(i +
         * 1))); eachEntityCompose.setOrderNo(protocolOrderBean.getDDH());
         * eachEntityCompose.setItemName(eachBean.getDDMC());
         * eachEntityCompose.setUnitName(eachBean.getDW());
         * eachEntityCompose.setSpecificationModel(eachBean.getGGXH());
         * eachEntityCompose.setItemCount(Double.parseDouble(eachBean.getSL()));
         * eachEntityCompose
         * .setItemUnitCost(Double.parseDouble(eachBean.getDJ()));
         * eachEntityCompose
         * .setItemAmount(Double.parseDouble(eachBean.getJE()));
         * 
         * repository.insertInvoiceOrderDetail(eachEntityCompose);
         * 
         * } }
         */

        final Long millSeconds = new Duration(begin, now()).getMillis();
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("(平台信息处理)循环插入fp_ddmxxx表, 总共耗时{}毫秒", millSeconds);
        }
    }

    /* 2014.10.06:insert fp_zfxx */
    private Boolean processInsertInvoicePayment(final REQUEST_COMMON_FPKJ protocolInvoiceBean, final COMMON_FPKJ_FPT protocolHeaderBean,
            final EInvoiceSubRepository repository, final Long uniqueId) {
        final DateTime begin = now();

        Boolean stepResult = true;

        final COMMON_FPKJ_ZFXX protocolPaymentBean = protocolInvoiceBean.getCOMMON_FPKJ_ZFXX();

        if (protocolPaymentBean != null) {

            final InvoicePaymentEntity entityCompose = new InvoicePaymentEntity();

            final Long theId = uniqueResourceManagerService.obtainBusinessId(FPZFXX_ID.name());
            entityCompose.setId(theId);
            entityCompose.setInvoiceId(uniqueId);
            entityCompose.setEshopCode(protocolHeaderBean.getDSPTBM());
            entityCompose.setPaymentWay(protocolPaymentBean.getZFFS());
            entityCompose.setPaymentPlatform(protocolPaymentBean.getZFPT());
            entityCompose.setPaymentSerialNo(protocolPaymentBean.getZFLSH());

            stepResult = repository.insertInvoicePayment(entityCompose);

            final Long millSeconds = new Duration(begin, now()).getMillis();
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("(平台信息处理)插入fp_zfxx表发票开据id{}, 总共耗时{}毫秒", entityCompose.getInvoiceId(), millSeconds);
            }
        }

        return stepResult;
    }

    /* 2014.10.06:insert fp_wlxx */
    private Boolean processInsertInvoiceLogistics(final REQUEST_COMMON_FPKJ protocolInvoiceBean, final COMMON_FPKJ_FPT protocolHeaderBean,
            final EInvoiceSubRepository repository, final Long uniqueId) {
        final DateTime begin = now();
        Boolean stepResult = true;

        final COMMON_FPKJ_WLXX protocolLogisticsBean = protocolInvoiceBean.getCOMMON_FPKJ_WLXX();

        if (protocolLogisticsBean != null) {

            final InvoiceLogisticsEntity entityCompose = new InvoiceLogisticsEntity();

            final Long theId = uniqueResourceManagerService.obtainBusinessId(FPWLXX_ID.name());
            entityCompose.setId(theId);
            entityCompose.setInvoiceId(uniqueId);

            entityCompose.setEshopCode(protocolHeaderBean.getDSPTBM());
            entityCompose.setLogisticsCompany(protocolLogisticsBean.getCYGS());
            entityCompose.setLogisticsNo(protocolLogisticsBean.getWLDH());
            entityCompose.setDeliveryAddress(protocolLogisticsBean.getSHDZ());
            if (protocolLogisticsBean.getSHSJ() == null || "".equals(protocolLogisticsBean.getSHSJ())) {
                entityCompose.setDeliveryTime(null); 
            } else {
                entityCompose.setDeliveryTime(Timestamp.valueOf(protocolLogisticsBean.getSHSJ())); 
                // TIMESTAMP
                // PARSE
            }

            stepResult = repository.insertInvoiceLogistics(entityCompose);
            final Long millSeconds = new Duration(begin, now()).getMillis();
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("(平台信息处理)插入fp_wlxx表发票开据id{}, 总共耗时{}毫秒", entityCompose.getInvoiceId(), millSeconds);
            }
        }

        return stepResult;
    }

    private Boolean beRed(final String theBillingType) {
        return !(invoiceNote.toString()).equals(theBillingType);
    }

    /* FIN : TRACY RENEW SECTION 2014.11.11 */

    /* 600行级函数：发票开具请求信息入库校验 */

    /**
     * 平台客户端APP传递发票开具请求信息入库校验:原校验方法2
     * 
     * @param REQUEST_COMMON_FPKJ
     * @param COMMON_FPKJ_FPT
     * @param fpkjxx_xmxxs
     * @param repository
     * @return
     */
    /*private Map<String, String> checkInvParaLegitimate(REQUEST_COMMON_FPKJ REQUEST_COMMON_FPKJ, COMMON_FPKJ_FPT COMMON_FPKJ_FPT,
            COMMON_FPKJ_XMXX[] fpkjxx_xmxxs, final EInvoiceSubRepository repository) {

        final Map<String, String> errorMap = new HashMap<String, String>();
        errorMap.put(XmlPar.ERRORCODE, XmlPar.RESPONSEYYSSUCCESS);

        if (REQUEST_COMMON_FPKJ == null) {
            LOGGER.error("发票开具请求数据格式不正确！");
            return errorMapCodeOfMsg(XmlPar.RESPONSEFAIL, "发票开具请求数据格式不正确,REQUEST_COMMON_FPKJ节点为空！");
        }

        if (COMMON_FPKJ_FPT == null) {
            LOGGER.error("发票开具请求数据格式不正确！");
            return errorMapCodeOfMsg(XmlPar.RESPONSEFAIL, "发票开具请求数据格式不正确,COMMON_FPKJ_FPT节点为空！");
        }
        if (fpkjxx_xmxxs == null || fpkjxx_xmxxs.length < 1) {
            LOGGER.error("发票开具明细信息不能为空！");
            return errorMapCodeOfMsg(XmlPar.ERRORCODE_DATA_FPMXXXISEXIST, "发票开具明细信息不能为空！");
        }

        if (ValidateUtil.checkParameterIsEmpty(COMMON_FPKJ_FPT.getFPQQLSH())) {
            LOGGER.error("发票开票发票请求唯一流水号不能为空！");
            return errorMapCodeOfMsg(XmlPar.ERRORCODE_DATA_FPT_FPQQLSH, "发票开票发票请求唯一流水号不能为空");
        }
        if (ValidateUtil.checkParameterLength(COMMON_FPKJ_FPT.getFPQQLSH(), 0, 20)) {
            LOGGER.error("发票开票发票请求唯一流水号长度存在问题！");
            return errorMapCodeOfMsg(XmlPar.ERRORCODE_DATA_FPT_FPQQLSH, "发票开票发票请求唯一流水号长度存在问题");
        }
        // 纳税人识别号是否填写
        if (ValidateUtil.checkParameterIsEmpty(COMMON_FPKJ_FPT.getNSRSBH())) {
            LOGGER.error("发票开具纳税人识别号不能为空！");
            return errorMapCodeOfMsg(XmlPar.ERRORCODE_DATA_FPT_NSRSBH, "发票开具纳税人识别号不能为空！");
        }
        if (ValidateUtil.checkParameterLength(COMMON_FPKJ_FPT.getNSRSBH(), 15, 20)) {
            LOGGER.error("发票开具纳税人识别号不合法！");
            return errorMapCodeOfMsg(XmlPar.ERRORCODE_DATA_FPT_NSRSBH, "发票开具纳税人识别号不合法！");
        }

         判断纳税人信息、开票状态,平台传递的行业类型与行业代码赋值 
        final String theTaxpayerIdentifyNo = COMMON_FPKJ_FPT.getNSRSBH();
        final String theEShopCode = COMMON_FPKJ_FPT.getDSPTBM();

        final TaxpayerEntity taxpayerEntity = repository.getTaxpayer(theTaxpayerIdentifyNo, theEShopCode);

        if (!eshopEnabled.equals(taxpayerEntity.getEshopEnabled())) {
            LOGGER.error("平台已经停用！");
            return errorMapCodeOfMsg(XmlPar.ERRORCODE_DSPT_UNAVAILABLE, "平台已经停用!");
        }

        if (taxpayerEntity == null) {
            LOGGER.error("该纳税人对应平台不存在请确认！");
            return errorMapCodeOfMsg(XmlPar.ERRORCODE_DATA_FPT_NSRSBH, "该纳税人对应平台不存在请确认！");
        }

        if (ValidateUtil.checkParameterIsEmpty(COMMON_FPKJ_FPT.getHY_MC())) {
            COMMON_FPKJ_FPT.setHY_MC(taxpayerEntity.getIndustryName());
        }
        if (ValidateUtil.checkParameterIsEmpty(COMMON_FPKJ_FPT.getHY_DM())) {
            COMMON_FPKJ_FPT.setHY_DM(taxpayerEntity.getIndustryCode());
        }

        if (!"1".equals(taxpayerEntity.getTaxpayerInvoiceStatus())) {
            LOGGER.error("请求纳税人开票状态已经停用不能开具发票！");
            return errorMapCodeOfMsg(XmlPar.ERRORCODE_DATA_FPT_NSRSBH, "请求纳税人开票状态已经停用不能开具发票");
        }

        // 纳税人名称是否填写
        if (ValidateUtil.checkParameterIsEmpty(COMMON_FPKJ_FPT.getNSRMC())) {
            LOGGER.error("发票开具纳税人名称不能为空！");
            return errorMapCodeOfMsg(XmlPar.ERRORCODE_DATA_FPT_NSRMC, "发票开具纳税人名称不能为空");
        }
        if (ValidateUtil.checkParameterLength(COMMON_FPKJ_FPT.getNSRMC(), 4, 200)) {
            LOGGER.error("发票开票方名称长度存在问题！");
            return errorMapCodeOfMsg(XmlPar.ERRORCODE_DATA_FPT_NSRMC, "发票开票方名称长度存在问题");
        }

        if (!ValidateUtil.checkParameterIsEmpty(COMMON_FPKJ_FPT.getNSRDZDAH())) {
            if (ValidateUtil.checkParameterLength(COMMON_FPKJ_FPT.getNSRDZDAH(), 15, 20)) {
                LOGGER.error("发票开具开票方电子档案号长度错误！");
                return errorMapCodeOfMsg(XmlPar.ERRORCODE_DATA_FPT_NSRDZDAH, "发票开具开票方电子档案号长度错误");
            }
        }
        if (!ValidateUtil.checkParameterIsEmpty(COMMON_FPKJ_FPT.getSWJG_DM())) {
            if (ValidateUtil.checkParameterLength(COMMON_FPKJ_FPT.getSWJG_DM(), 4, 11)) {
                LOGGER.error("发票开具税务机构代码长度错误！");
                return errorMapCodeOfMsg(XmlPar.ERRORCODE_DATA_FPT_SWJG_DM, "发票开具税务机构代码长度错误");
            }
        }
        if (ValidateUtil.checkParameterIsEmpty(COMMON_FPKJ_FPT.getDKBZ())) {
            LOGGER.error("发票开票模式不能为空！");
            return errorMapCodeOfMsg(XmlPar.ERRORCODE_DATA_FPT_DKBZ, "发票开票模式不能为空");
        }
        if (!"0".equals(COMMON_FPKJ_FPT.getDKBZ())) {
            LOGGER.error("发票代开模式不正确！");
            return errorMapCodeOfMsg(XmlPar.ERRORCODE_DATA_FPT_DKBZ, "发票代开模式不正确");
        }
        if (!ValidateUtil.checkParameterIsEmpty(COMMON_FPKJ_FPT.getPYDM())) {
            if (ValidateUtil.checkParameterLength(COMMON_FPKJ_FPT.getPYDM(), 0, 6)) {
                LOGGER.error("发票开具税务机构代码长度错误！");
                return errorMapCodeOfMsg(XmlPar.ERRORCODE_DATA_FPT_PYDM, "发票开具税务机构代码长度错误");
            }
        }
        if (ValidateUtil.checkParameterIsEmpty(COMMON_FPKJ_FPT.getKPXM())) {
            LOGGER.error("发票开具主开票项目不能为空！");
            return errorMapCodeOfMsg(XmlPar.ERRORCODE_DATA_FPT_KPXM, "发票开具主开票项目不能为空");
        }
        if (ValidateUtil.checkParameterLength(COMMON_FPKJ_FPT.getNSRMC(), 0, 200)) {
            LOGGER.error("发票主开票项目长度存在问题！");
            return errorMapCodeOfMsg(XmlPar.ERRORCODE_DATA_FPT_NSRSBH, "发票主开票项目长度存在问题");
        }
        if (!ValidateUtil.checkParameterIsEmpty(COMMON_FPKJ_FPT.getXHF_MC())) {
            if (ValidateUtil.checkParameterLength(COMMON_FPKJ_FPT.getXHF_MC(), 0, 200)) {
                LOGGER.error("发票销货方名称长度存在问题！");
                return errorMapCodeOfMsg(XmlPar.ERRORCODE_DATA_FPT_XHFMC, "发票销货方名称长度存在问题");
            }
        }
        if (ValidateUtil.checkParameterIsEmpty(COMMON_FPKJ_FPT.getGHF_MC())) {
            LOGGER.error("发票开具购货方名称不能为空！");
            return errorMapCodeOfMsg(XmlPar.ERRORCODE_DATA_FPT_GHFMC, "发票开具购货方名称不能为空");
        }
        if (ValidateUtil.checkParameterLength(COMMON_FPKJ_FPT.getGHF_MC(), 0, 200)) {
            LOGGER.error("发票购货方名称长度存在问题！");
            return errorMapCodeOfMsg(XmlPar.ERRORCODE_DATA_FPT_GHFMC, "发票购货方名称长度存在问题");
        }
        if (!ValidateUtil.checkParameterIsEmpty(COMMON_FPKJ_FPT.getGHF_NSRSBH())) {
            if (ValidateUtil.checkParameterLength(COMMON_FPKJ_FPT.getGHF_NSRSBH(), 10, 20)) {
                LOGGER.error("发票购货方识别号长度存在问题！");
                return errorMapCodeOfMsg(XmlPar.ERRORCODE_DATA_FPT_GHF_NSRSBH, "发票购货方识别号长度存在问题");
            }
        }
        if (!ValidateUtil.checkParameterIsEmpty(COMMON_FPKJ_FPT.getGHF_DZ())) {
            if (ValidateUtil.checkParameterLength(COMMON_FPKJ_FPT.getGHF_DZ(), 0, 200)) {
                LOGGER.error("发票购货方地址长度存在问题！");
                return errorMapCodeOfMsg(XmlPar.ERRORCODE_DATA_FPT_GHF_DZ, "发票购货方地址长度存在问题");
            }
        }
        if (!ValidateUtil.checkParameterIsEmpty(COMMON_FPKJ_FPT.getGHF_SF())) {
            if (ValidateUtil.checkParameterLength(COMMON_FPKJ_FPT.getGHF_SF(), 0, 20)) {
                LOGGER.error("发票购货方省份长度存在问题！");
                return errorMapCodeOfMsg(XmlPar.ERRORCODE_DATA_FPT_GHF_SF, "发票购货方省份长度存在问题");
            }
        }
        if (!ValidateUtil.checkParameterIsEmpty(COMMON_FPKJ_FPT.getGHF_GDDH())) {
            if (ValidateUtil.checkParameterLength(COMMON_FPKJ_FPT.getGHF_GDDH(), 0, 20)) {
                LOGGER.error("发票购货方固定电话长度存在问题！");
                return errorMapCodeOfMsg(XmlPar.ERRORCODE_DATA_FPT_GHF_GDDH, "发票购货方固定电话长度存在问题");
            }
        }
        if (ValidateUtil.checkParameterIsEmpty(COMMON_FPKJ_FPT.getGHF_SJ())) {
            LOGGER.error("购货方手机号不能为空！");
            return errorMapCodeOfMsg(XmlPar.ERRORCODE_DATA_FPT_GHF_SJ, "购货方手机号不能为空");
        }

        if (!ValidateUtil.checkParameterIsEmpty(COMMON_FPKJ_FPT.getGHF_YX())) {
            if (ValidateUtil.checkParameterLength(COMMON_FPKJ_FPT.getGHF_YX(), 0, 100)) {
                LOGGER.error("发票购货方邮箱长度存在问题！");
                return errorMapCodeOfMsg(XmlPar.ERRORCODE_DATA_FPT_GHF_EMAIL, "发票购货方邮箱长度存在问题");
            }
        }
        // 判断购货方企业类型是否正确
        if (ValidateUtil.checkParameterIsEmpty(COMMON_FPKJ_FPT.getGHF_QYLX())) {
            LOGGER.error("购货方企业类型错误！");
            return errorMapCodeOfMsg(XmlPar.ERRORCODE_DATA_FPT_GHFQYLX, "购货方企业类型错误！！");
        }
        // 01:企业 02：机关事业单位 03：个人 04：其它
        if (!"01".equals(COMMON_FPKJ_FPT.getGHF_QYLX()) && !"02".equals(COMMON_FPKJ_FPT.getGHF_QYLX()) && !"03".equals(COMMON_FPKJ_FPT.getGHF_QYLX())
                && !"04".equals(COMMON_FPKJ_FPT.getGHF_QYLX())) {
            LOGGER.error("购货方企业类型错误！");
            return errorMapCodeOfMsg(XmlPar.ERRORCODE_DATA_FPT_GHFQYLX, "购货方企业类型错误！！");
        }

        if (!ValidateUtil.checkParameterIsEmpty(COMMON_FPKJ_FPT.getHY_DM())) {
            if (ValidateUtil.checkParameterLength(COMMON_FPKJ_FPT.getHY_DM(), 0, 10)) {
                LOGGER.error("发票行业代码长度存在问题！");
                return errorMapCodeOfMsg(XmlPar.ERRORCODE_DATA_FPT_HY_DM, "发票行业代码长度存在问题");
            }
        }
        if (!ValidateUtil.checkParameterIsEmpty(COMMON_FPKJ_FPT.getHY_MC())) {
            if (ValidateUtil.checkParameterLength(COMMON_FPKJ_FPT.getHY_MC(), 0, 40)) {
                LOGGER.error("发票行业名称长度存在问题！");
                return errorMapCodeOfMsg(XmlPar.ERRORCODE_DATA_FPT_HY_DM, "发票行业名称长度存在问题");
            }
        }
        if (ValidateUtil.checkParameterIsEmpty(COMMON_FPKJ_FPT.getKPY())) {
            LOGGER.error("开票员不能为空！");
            return errorMapCodeOfMsg(XmlPar.ERRORCODE_DATA_FPT_KPY, "开票员不能为空");
        }
        if (ValidateUtil.checkParameterLength(COMMON_FPKJ_FPT.getKPY(), 0, 100)) {
            LOGGER.error("发票开票员长度存在问题！");
            return errorMapCodeOfMsg(XmlPar.ERRORCODE_DATA_FPT_KPY, "发票开票员长度存在问题");
        }
        if (!ValidateUtil.checkParameterIsEmpty(COMMON_FPKJ_FPT.getSKY())) {
            if (ValidateUtil.checkParameterLength(COMMON_FPKJ_FPT.getSKY(), 0, 100)) {
                LOGGER.error("发票收款员长度存在问题！");
                return errorMapCodeOfMsg(XmlPar.ERRORCODE_DATA_FPT_SKY, "发票收款员长度存在问题");
            }
        }
        
         * if (!ValidateUtil.checkParameterIsEmpty(COMMON_FPKJ_FPT.getKPRQ())) {
         * if (ValidateUtil.checkParameterLength(COMMON_FPKJ_FPT.getKPRQ(), 0,
         * 20)) { LOGGER.error("发票开票日期长度存在问题！"); return
         * errorMapCodeOfMsg(XmlPar.ERRORCODE_DATA_FPT_SKY, "发票开票日期长度存在问题"); } }
         

        if (ValidateUtil.checkParameterIsEmpty(COMMON_FPKJ_FPT.getKPLX())) {
            LOGGER.error("发票开票类型不能为空！");
            return errorMapCodeOfMsg(XmlPar.ERRORCODE_DATA_FPT_KPLX, "发票开票类型不能为空");
        }
        // 判断开票类型是否正确 1 正数发票 2红字发票
        if (!"1".equals(COMMON_FPKJ_FPT.getKPLX()) && !"2".equals(COMMON_FPKJ_FPT.getKPLX())) {
            LOGGER.error("发票开具类型错误！");
            return errorMapCodeOfMsg(XmlPar.ERRORCODE_DATA_FPT_KPLX, "发票开具类型错误！！");
        }

        // 判断开票类型是否正确 10正票正常开具 11正票错票重开 20 退货折让红票、21 错票重开红票、22换票冲红
        if (!"10".equals(COMMON_FPKJ_FPT.getCZDM()) && !"11".equals(COMMON_FPKJ_FPT.getCZDM()) && !"20".equals(COMMON_FPKJ_FPT.getCZDM())
                && !"21".equals(COMMON_FPKJ_FPT.getCZDM()) && !"22".equals(COMMON_FPKJ_FPT.getCZDM())) {
            return errorMapCodeOfMsg(XmlPar.ERRORCODE_DATA_FPT_CZDM, "发票开具操作代码类型错误！！");
        }

        // 开票日期不是必填项目
        if (ValidateUtil.checkParameterIsEmpty(COMMON_FPKJ_FPT.getKPHJJE())) {
            LOGGER.error("发票开票合计金额不能为空！");
            return errorMapCodeOfMsg(XmlPar.ERRORCODE_DATA_FPT_KPHJJE, "发票开票合计金额不能为空");
        }
        if (ValidateUtil.checkParameterIsEmpty(REQUEST_COMMON_FPKJ.getCOMMON_FPKJ_DDXX().getDDH())) {
            LOGGER.error("订单号码不能为空！");
            return errorMapCodeOfMsg(XmlPar.ERRORCODE_DATA_FPT_DDH, "订单号码不能为空");
        }

        if (!ValidateUtil.checkParameterIsEmpty(COMMON_FPKJ_FPT.getBZ())) {
            if (ValidateUtil.checkParameterLength(COMMON_FPKJ_FPT.getBZ(), 0, 230)) {
                LOGGER.error("发票备注长度存在问题！");
                return errorMapCodeOfMsg(XmlPar.ERRORCODE_DATA_FPT_BZ, "发票备注长度存在问题");
            }
        }

        // 判断发票开具类型金额是否正确
        if ("1".equals(COMMON_FPKJ_FPT.getKPLX())) {
            if (Double.parseDouble(COMMON_FPKJ_FPT.getKPHJJE()) <= 0) {
                LOGGER.error("发票开票合计金额不能为小于等于0！");
                return errorMapCodeOfMsg(XmlPar.ERRORCODE_DATA_FPT_KPHJJE, "发票开票合计金额不能为小于等于0");
            }
            // 判断发票开具明细信息是否为空
            for (int i = 0; i < fpkjxx_xmxxs.length; i++) {
                COMMON_FPKJ_XMXX fpkjxx_xmxx = fpkjxx_xmxxs[i];
                if (ValidateUtil.checkParameterIsEmpty(fpkjxx_xmxx.getXMMC())) {
                    LOGGER.error("开具发票信息明细中项目名称不能为空！");
                    return errorMapCodeOfMsg(XmlPar.ERRORCODE_DATA_MXXX_XMMC, "开具发票信息明细中项目名称不能为空");
                }
                if (ValidateUtil.checkParameterIsEmpty(fpkjxx_xmxx.getXMSL())) {
                    LOGGER.error("开具发票信息明细中项目数量不能为空！");
                    return errorMapCodeOfMsg(XmlPar.ERRORCODE_DATA_MXXX_XMSL, "开具发票信息明细中项目数量不能为空");
                }
                if (ValidateUtil.checkParameterIsEmpty(fpkjxx_xmxx.getXMDJ())) {
                    LOGGER.error("开具发票信息明细中项目单价不能为空！");
                    return errorMapCodeOfMsg(XmlPar.ERRORCODE_DATA_MXXX_XMDJ, "开具发票信息明细中项目单价不能为空");
                }
                if (ValidateUtil.checkParameterIsEmpty(fpkjxx_xmxx.getXMJE())) {
                    LOGGER.error("开具发票信息明细中项目金额不能为空！");
                    return errorMapCodeOfMsg(XmlPar.ERRORCODE_DATA_MXXX_XMJE, "开具发票信息明细中项目金额不能为空");
                }
                String xmmc = fpkjxx_xmxx.getXMMC();
                if (xmmc.length() > 2 && (xmmc.substring(0, 3)).equals("折扣(")) {
                    if (Double.parseDouble(fpkjxx_xmxx.getXMJE()) >= 0 && Double.parseDouble(fpkjxx_xmxx.getSE()) >= 0) {
                        LOGGER.error("折扣发票金额大于等于零请确认！");
                        return errorMapCodeOfMsg(XmlPar.ERRORCODE_DATA_MXXX_XMJE, "折扣发票金额大于等于零请确认！");
                    }
                }
                if (xmmc.length() > 4 && (xmmc.substring(0, 4)).equals("折扣行数")) {
                    if (Double.parseDouble(fpkjxx_xmxx.getXMJE()) >= 0 && Double.parseDouble(fpkjxx_xmxx.getSE()) >= 0) {
                        LOGGER.error("折扣发票金额大于等于零请确认！");
                        return errorMapCodeOfMsg(XmlPar.ERRORCODE_DATA_MXXX_XMJE, "折扣发票金额大于等于零请确认！");
                    }
                }
            }
        } else {
            if (Double.parseDouble(COMMON_FPKJ_FPT.getKPHJJE()) >= 0) {
                LOGGER.error("发票开票合计金额不能为大于等于0！");
                return errorMapCodeOfMsg(XmlPar.ERRORCODE_DATA_FPT_KPHJJE, "发票开票合计金额不能为大于等于0");
            }
        }

        // 正票开具
        if ("10".equals(COMMON_FPKJ_FPT.getCZDM()) && (Double.parseDouble(COMMON_FPKJ_FPT.getKPHJJE()) <= 0)) {
            LOGGER.error("开具正数发票金额小于等于零请确认！");
            return errorMapCodeOfMsg(XmlPar.ERRORCODE_DATA_FPT_GHF_CZDM, "开具正数发票金额小于等于零请确认!");
        }

        // 正票错票重开 原发票代码和号码不能为空
        if ("11".equals(COMMON_FPKJ_FPT.getCZDM())) {
            // 正票错票重开
            if ((Double.parseDouble(COMMON_FPKJ_FPT.getKPHJJE()) <= 0)) {
                LOGGER.error("开具正数发票金额小于等于零请确认！");
                return errorMapCodeOfMsg(XmlPar.ERRORCODE_DATA_FPT_GHF_CZDM, "开具正数发票金额小于等于零请确认!");
            } else if (ValidateUtil.checkParameterIsEmpty(COMMON_FPKJ_FPT.getYFP_DM())) {
                LOGGER.error("开具冲红发票原发票代码不能为空！");
                return errorMapCodeOfMsg(XmlPar.ERRORCODE_DATA_FPT_GHF_CZDM, "开具冲红发票原发票代码不能为空");
            } else if (ValidateUtil.checkParameterIsEmpty(COMMON_FPKJ_FPT.getYFP_HM())) {
                LOGGER.error("开具冲红发票原发票号码不能为空！");
                return errorMapCodeOfMsg(XmlPar.ERRORCODE_DATA_FPT_GHF_CZDM, "开具冲红发票原发票号码不能为空");
            }

            final String oldInvoiceCode = COMMON_FPKJ_FPT.getYFP_DM();
            final String oldInvoiceNo = COMMON_FPKJ_FPT.getYFP_HM();
            final Boolean beInvoiceExistBlue = repository.verifyInvoiceExistBlue(oldInvoiceCode, oldInvoiceNo);

            if (!beInvoiceExistBlue) {
                LOGGER.error("错票重开对应的蓝字发票不存在！");
                return errorMapCodeOfMsg(XmlPar.ERRORCODE_DATA_FPT_GHF_CZDM, "错票重开对应的蓝字发票不存在");
            }

        }

        // 修改如果不是正票判断要冲红的正票信息
        if (!"1".equals(COMMON_FPKJ_FPT.getKPLX())) {
            if (!"1".equals(COMMON_FPKJ_FPT.getTSCHBZ())) {
                if (ValidateUtil.checkParameterIsEmpty(COMMON_FPKJ_FPT.getYFP_DM())) {
                    LOGGER.error("开具冲红发票原发票代码不能为空！");
                    return errorMapCodeOfMsg(XmlPar.ERRORCODE_DATA_FPT_YFP_DM, "开具冲红发票原发票代码不能为空");
                }
                if (ValidateUtil.checkParameterIsEmpty(COMMON_FPKJ_FPT.getYFP_HM())) {
                    LOGGER.error("开具冲红发票原发票号码不能为空！");
                    return errorMapCodeOfMsg(XmlPar.ERRORCODE_DATA_FPT_YFP_HM, "开具冲红发票原发票号码不能为空");
                }
            }
            if (ValidateUtil.checkParameterIsEmpty(COMMON_FPKJ_FPT.getTSCHBZ())) {
                LOGGER.error("开具冲红标志不能为空！");
                return errorMapCodeOfMsg(XmlPar.ERRORCODE_DATA_FPT_TSCHBZ, "开具冲红标志不能为空");
            }
            if (!ValidateUtil.checkParameterIsEmpty(COMMON_FPKJ_FPT.getCHYY())) {
                if (ValidateUtil.checkParameterLength(COMMON_FPKJ_FPT.getCHYY(), 0, 190)) {
                    LOGGER.error("发票冲红原因长度存在问题！");
                    return errorMapCodeOfMsg(XmlPar.ERRORCODE_DATA_FPT_CHYY, "发票冲红原因长度存在问题");
                }
            }

            if ("0".equals(COMMON_FPKJ_FPT.getTSCHBZ())) {

                final Boolean beInvoiceRedExistBlue = repository.verifyInvoiceRedExistBlue(COMMON_FPKJ_FPT.getYFP_DM(), COMMON_FPKJ_FPT.getYFP_HM(),
                        COMMON_FPKJ_FPT.getNSRSBH(), invoiceNote.toString());

                if (!beInvoiceRedExistBlue) {
                    LOGGER.error("开具红字发票对应的蓝字发票不存在请确认！");
                    return errorMapCodeOfMsg(XmlPar.ERRORCODE_DATA_FPT_KJXXISEXITS, "开具红字发票对应的蓝字发票不存在请确认!");
                }
            }
            if ("0".equals(COMMON_FPKJ_FPT.getTSCHBZ())) {
                 通过条件获取发票头信息实体实体信息,包含剩余可冲红金额、是否特殊冲红、开票合计金额，用于查验 
                final InvoiceHeaderEntity invoiceAmountInfo = repository.getInvoiceAmountInfo(COMMON_FPKJ_FPT.getYFP_DM(), COMMON_FPKJ_FPT.getYFP_HM(),
                        COMMON_FPKJ_FPT.getNSRSBH(), invoiceNote.toString());

                if (invoiceAmountInfo == null) {
                     源代码机制:此类中抛出exception,进入EInvWebControllerImpl中catch. 
                    LOGGER.error("在未知e之前打印异常点: nullPoint - invoiceAmountInfo==null");
                } else {

                    // 验证原票的剩余可冲红金额是否等于0 如果等于0 不能再次冲红
                    if (invoiceAmountInfo.getRemainingRedAmount() == 0) {
                        LOGGER.error("此红票的原发票金额已冲完，不能再冲红");

                        return errorMapCodeOfMsg(XmlPar.ERRORCODE_DATA_FPT_CHJESMALL, "此红票的原发票金额已冲完，不能再冲红");
                    } else if (invoiceAmountInfo.getRemainingRedAmount() + Double.parseDouble(COMMON_FPKJ_FPT.getKPHJJE()) < 0) {
                        // 验证原票的声音可冲红金额是是否能够冲红
                        LOGGER.error("此红票的开票金额大于原发票剩余可冲红金额，不能冲红");

                        return errorMapCodeOfMsg(XmlPar.ERRORCODE_DATA_FPT_CHJESMALL, "此红票的开票金额大于原发票剩余可冲红金额，不能冲红");
                    }

                    if ("0".equals(invoiceAmountInfo.getSpecRedInvoiceFlag())) {
                        LOGGER.error("开具红字发票对应的正数发票已经开具不能重复冲红！");

                        return errorMapCodeOfMsg(XmlPar.ERRORCODE_DATA_FPT_TSCHBZ, "开具红字发票对应的正数发票已经开具不能重复冲红");
                    }

                    if ("4000".equals(invoiceAmountInfo.getSpecRedInvoiceFlag())) {
                        LOGGER.error("开具红字发票对应的正数发票还未上传到税务局不允许开具！");

                        return errorMapCodeOfMsg(XmlPar.ERRORCODE_DATA_FPT_TSCHBZ, "开具红字发票对应的正数发票还未上传到税务局不允许开具");
                    }
                }
                // 退货折让红票 目前只支持全部冲红
                if ("20".equals(COMMON_FPKJ_FPT.getCZDM()) && (Double.parseDouble(COMMON_FPKJ_FPT.getKPHJJE()) >= 0)) {
                    LOGGER.error("开具红字发票金额大于等于零请确认！");
                    return errorMapCodeOfMsg(XmlPar.ERRORCODE_DATA_FPT_GHF_CZDM, "开具红字发票金额大于等于零请确认!");
                }
                if ("20".equals(COMMON_FPKJ_FPT.getCZDM())) {
                    if ((Double.parseDouble(COMMON_FPKJ_FPT.getKPHJJE().replace("-", "")) != invoiceAmountInfo.getBillingAmount())) {
                        LOGGER.error("开具红字发票金额不等于正数发票金额请确认！");
                        return errorMapCodeOfMsg(XmlPar.ERRORCODE_DATA_FPT_CZDM, "开具红字发票金额不等于正数发票金额请确认!");
                    }
                }

                if ("21".equals(COMMON_FPKJ_FPT.getCZDM())) {
                    if ((Double.parseDouble(COMMON_FPKJ_FPT.getKPHJJE().replace("-", "")) != invoiceAmountInfo.getBillingAmount())) {
                        LOGGER.error("开具红字发票金额不等于正数发票金额请确认！");
                        return errorMapCodeOfMsg(XmlPar.ERRORCODE_DATA_FPT_CZDM, "开具红字发票金额不等于正数发票金额请确认!");
                    }
                }
                // /换票冲红
                if ("22".equals(COMMON_FPKJ_FPT.getCZDM())) {
                    if ((Double.parseDouble(COMMON_FPKJ_FPT.getKPHJJE().replace("-", "")) != invoiceAmountInfo.getBillingAmount())) {
                        LOGGER.error("开具红字发票金额不等于正数发票金额请确认！");
                        return errorMapCodeOfMsg(XmlPar.ERRORCODE_DATA_FPT_CZDM, "开具红字发票金额不等于正数发票金额请确认!");
                    }
                }
                // 错票重开红票
                if ("21".equals(COMMON_FPKJ_FPT.getCZDM()) && (Double.parseDouble(COMMON_FPKJ_FPT.getKPHJJE()) >= 0)) {
                    LOGGER.error("开具红字发票金额大于等于零请确认！");
                    return errorMapCodeOfMsg(XmlPar.ERRORCODE_DATA_FPT_CZDM, "开具红字发票金额大于等于零请确认!");
                }
                if ("22".equals(COMMON_FPKJ_FPT.getCZDM()) && (Double.parseDouble(COMMON_FPKJ_FPT.getKPHJJE()) >= 0)) {
                    LOGGER.error("开具正数发票金额大于等于零请确认！");
                    return errorMapCodeOfMsg(XmlPar.ERRORCODE_DATA_FPT_CZDM, "开具正数发票金额大于等于零请确认!");
                }
            }
            // 判断发票开具明细信息是否为空
            for (int i = 0; i < fpkjxx_xmxxs.length; i++) {
                COMMON_FPKJ_XMXX fpkjxx_xmxx = fpkjxx_xmxxs[i];
                if (ValidateUtil.checkParameterIsEmpty(fpkjxx_xmxx.getXMMC())) {
                    LOGGER.error("开具发票信息明细中项目名称不能为空！");
                    return errorMapCodeOfMsg(XmlPar.ERRORCODE_DATA_MXXX_XMMC, "开具发票信息明细中项目名称不能为空");
                }
                if (ValidateUtil.checkParameterIsEmpty(fpkjxx_xmxx.getXMSL())) {
                    LOGGER.error("开具发票信息明细中项目数量不能为空！");
                    return errorMapCodeOfMsg(XmlPar.ERRORCODE_DATA_MXXX_XMSL, "开具发票信息明细中项目数量不能为空");
                }
                if (ValidateUtil.checkParameterIsEmpty(fpkjxx_xmxx.getXMDJ())) {
                    LOGGER.error("开具发票信息明细中项目单价不能为空！");
                    return errorMapCodeOfMsg(XmlPar.ERRORCODE_DATA_MXXX_XMDJ, "开具发票信息明细中项目单价不能为空");
                }
                if (ValidateUtil.checkParameterIsEmpty(fpkjxx_xmxx.getXMJE())) {
                    LOGGER.error("开具发票信息明细中项目金额不能为空！");
                    return errorMapCodeOfMsg(XmlPar.ERRORCODE_DATA_MXXX_XMJE, "开具发票信息明细中项目金额不能为空");
                }
                String xmmc = fpkjxx_xmxx.getXMMC();
                if (xmmc.length() > 2 && (xmmc.substring(0, 3)).equals("折扣(")) {
                    if (Double.parseDouble(fpkjxx_xmxx.getXMJE()) <= 0 && Double.parseDouble(fpkjxx_xmxx.getSE()) <= 0) {
                        LOGGER.error("折扣发票金额小于等于零请确认！");
                        return errorMapCodeOfMsg(XmlPar.ERRORCODE_DATA_MXXX_XMJE, "折扣发票金额小于等于零请确认！");
                    }
                }
                if (xmmc.length() > 4 && (xmmc.substring(0, 4)).equals("折扣行数")) {
                    if (Double.parseDouble(fpkjxx_xmxx.getXMJE()) <= 0 && Double.parseDouble(fpkjxx_xmxx.getSE()) <= 0) {
                        LOGGER.error("折扣发票金额小于等于零请确认！");
                        return errorMapCodeOfMsg(XmlPar.ERRORCODE_DATA_MXXX_XMJE, "折扣发票金额小于等于零请确认！");
                    }
                }
            }
        }

        return errorMap;
    }*/

    /**
     * 封装错误消息
     * 
     * @param errorCode
     * @param errorMsg
     * @return Map<String, String>
     */
    public Map<String, String> errorMapCodeOfMsg(String errorCode, String errorMsg) {
        final Map<String, String> errorMap = new HashMap<String, String>();
        errorMap.put(XmlPar.ERRORCODE, errorCode);
        errorMap.put(XmlPar.ERRORMSG, errorMsg);

        return errorMap;
    }

    /**
     * 错误信息返回
     * 
     * @param map
     * @param returnInfo
     * @param errMsg
     * @return
     */
    private Map<String, Object> buildReturnStateInfo(final Map<String, Object> map, final ReturnStateInfo returnInfo, String errMsg, final String errorCode) {
        LOGGER.error(errMsg);

        returnInfo.setReturnCode(errorCode);
        returnInfo.setReturnMessage(errMsg);
        map.put(XmlPar.RETURNSTATEINFO, returnInfo);

        return map;
    }
    
    /**
     * 商品种类查询
     * @param spmc 商品名称
     * @param spbm 商品编码
     * @return
     * @throws Exception
     */
    public String commodityCategory(String spmc,String spbm)
    		throws UnsupportedEncodingException {
    	if (spmc == null) {
    		return null;
    	}
    	StringBuilder sb = new StringBuilder();
    	Map<String, String> params = new HashMap<String, String>();
		params.put("spbm", spbm);
    	try {
		    String result = WebUtil.doPost(StaticConstant.SPZLCX_URL, params);
		    if(result!=null){
		    	LOGGER.debug(result);
			    Map mapTypes = JSON.parseObject(result);  
		        String res = String.valueOf(mapTypes.get("code"));
		        if(res.equals(XmlPar.RESPONSE_SSUCCESS)){
		           String sn = String.valueOf(mapTypes.get("shortName"));
		           if(!StringUtils.isBlank(sn)){
		    	     sb.append("*").append(String.valueOf(mapTypes.get("shortName"))).append("*");
		           }
		        }
		    }
		   
		} catch (Exception e) {
			e.printStackTrace();
		}
    	sb.append(spmc);
    	return sb.toString();
    	
    }
    /**
     * 
     * @param text 目标字符串
     * @param length 截取长度
     * @param encode 采用的编码方式
     * @return
     * @throws UnsupportedEncodingException
     */
    public String xmmcsubstring(String text, int length, String encode)
      throws UnsupportedEncodingException {
     if (text == null) {
      return null;
     }
     StringBuilder sb = new StringBuilder();
     int currentLength = 0;
     for (char c : text.toCharArray()) {
      currentLength += String.valueOf(c).getBytes(encode).length;
      if (currentLength <= length) {
       sb.append(c);
      } else {
       break;
      }
     }
     return sb.toString();

    }
    
    /**
     * 
     * <p>反算折扣率，将折扣行的名称改为“折扣（x.xxx%）”</p>
     * 
     * @param protocolInvoiceProjectBean
     * @param i
     * @return String
     * @author: 阳开国
     * @date: Created on 2016-8-16 下午3:49:29
     */
    private String getZKHMC(COMMON_FPKJ_XMXX[] protocolInvoiceProjectBean , int i) {
        //被折扣金额
        Double spje = Math.abs(Double.parseDouble(ValidateUtil.decimalFormat(protocolInvoiceProjectBean[i-1].getXMJE(), 2)));
        //折扣金额
        Double zkje = Math.abs(Double.parseDouble(ValidateUtil.decimalFormat(protocolInvoiceProjectBean[i].getXMJE(), 2)));
        //现价
        Double xj = spje - zkje;
        //（原价-现价）/原价*100%=折扣率
        String discountRate = ValidateUtil.decimalFormat(( (spje - xj) / spje ) * 100, 3) + "%";
        //折扣行名称
        String zkhmcString  = "折扣("+discountRate+")";
        return zkhmcString;
    }
    
    public UniqueResourceManagerService getUniqueResourceManagerService() {
        return uniqueResourceManagerService;
    }

    public void setUniqueResourceManagerService(UniqueResourceManagerService uniqueResourceManagerService) {
        this.uniqueResourceManagerService = uniqueResourceManagerService;
    }

    public InvoiceDataAccessManagerService getInvoiceDataAccessManagerService() {
        return invoiceDataAccessManagerService;
    }

    public void setInvoiceDataAccessManagerService(InvoiceDataAccessManagerService invoiceDataAccessManagerService) {
        this.invoiceDataAccessManagerService = invoiceDataAccessManagerService;
    }

    public IInvoiceDataCheckService getInvoiceDataCheckService() {
        return invoiceDataCheckService;
    }

    public void setInvoiceDataCheckService(IInvoiceDataCheckService invoiceDataCheckService) {
        this.invoiceDataCheckService = invoiceDataCheckService;
    }

    public EInvoiceSubRepository getRepository() {
        return repository;
    }

    public void setRepository(EInvoiceSubRepository repository) {
        this.repository = repository;
    }

	public EInvoiceBusinessMap getBusinessHandler() {
		return businessHandler;
	}

	public void setBusinessHandler(EInvoiceBusinessMap businessHandler) {
		this.businessHandler = businessHandler;
	}
}
