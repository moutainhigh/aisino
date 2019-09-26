package com.aisino.einvoice.service.impl;

import static com.aisino.domain.constantenum.InvoiceStatusEnum.INV_TAXCODE_FAILURE;
import static com.aisino.domain.constantenum.InvoiceStatusEnum.INV_TAXCODE_SUCCESS;
import static com.google.common.base.Strings.isNullOrEmpty;
import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Lists.transform;
import static org.apache.commons.io.FileUtils.readFileToByteArray;
import static org.joda.time.DateTime.now;
import static org.springframework.util.CollectionUtils.isEmpty;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.aisino.common.util.CharUtil;
import com.aisino.common.util.Data;
import com.aisino.common.util.EIProtocolFactory;
import com.aisino.common.util.EShopCertificateBytesInfo;
import com.aisino.common.util.FpztConstants;
import com.aisino.common.util.FpztException;
import com.aisino.common.util.GlobalInfo;
import com.aisino.common.util.GlobalInfoPush;
import com.aisino.common.util.PassWordCheck;
import com.aisino.common.util.ReturnStateInfo;
import com.aisino.common.util.StaticConstant;
import com.aisino.common.util.XMLShellFactory;
import com.aisino.common.util.XmlPar;
import com.aisino.cxf.client.HttpClientUtil;
import com.aisino.cxf.client.IWebServiceClient;
import com.aisino.domain.SystemConfig;
import com.aisino.domain.einvoice.EInvoiceQueryCondition;
import com.aisino.domain.einvoice.EncryptionService;
import com.aisino.domain.einvoice.InvoiceDataAccessManagerService;
import com.aisino.domain.einvoice.InvoicePDFProducerService;
import com.aisino.domain.einvoice.UniqueResourceManagerService;
import com.aisino.domain.einvoice.entity.CertificateEntity;
import com.aisino.domain.einvoice.entity.EShopInfo;
import com.aisino.domain.einvoice.entity.InvoiceBalance;
import com.aisino.domain.einvoice.entity.InvoiceDetailEntity;
import com.aisino.domain.einvoice.entity.InvoiceEntity;
import com.aisino.domain.einvoice.entity.InvoiceHeaderEntity;
import com.aisino.domain.einvoice.entity.InvoicePDFEntity;
import com.aisino.domain.einvoice.entity.TaxcodeRouteEntity;
import com.aisino.domain.rabbit.entity.GeneratorInvoiceQueueEntity;
import com.aisino.domain.rabbit.entity.PushInvoiceQueueEntity;
import com.aisino.domain.rabbit.entity.SentToTaxQueueEntity;
import com.aisino.einvoice.dao.IInvUploadDao;
import com.aisino.einvoice.service.IInvUploadService;
import com.aisino.log.domain.Password;
import com.aisino.protocol.bean.COMMON_FPKJ_FPT;
import com.aisino.protocol.bean.COMMON_FPKJ_XMXX;
import com.aisino.protocol.bean.FPKJXX_FPJGXX;
import com.aisino.protocol.bean.FPKJXX_XMXX;
import com.aisino.protocol.bean.FP_KJMX;
import com.aisino.protocol.bean.REQUEST_COMMON_FPKJ;
import com.aisino.protocol.bean.REQUEST_FPKJ;
import com.aisino.protocol.bean.REQUEST_FPKJXX_FPJGXX;
import com.aisino.protocol.bean.RESPONSE_COMMON_FPKJ;
import com.aisino.protocol.bean.RESPONSE_FPKJ;
import com.aisino.protocol.bean.TaxcodeResponse;
import com.google.common.base.Function;
import com.mysql.jdbc.StringUtils;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * Created by Bourne.Lv on 2014/09/04.
 * <p/>
 * 处理端业务Service接口实现类
 */
@Service("eShopGeneratorInvoiceServiceImpl")
public final class InvUploadServiceImpl implements IInvUploadService {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(InvUploadServiceImpl.class);

	@Autowired
	private IInvUploadDao invUploadDao;

	@Autowired
	@Qualifier("uniqueResourceManagerService")
	private UniqueResourceManagerService uniqueResourceManagerService;

	@Autowired
	private EncryptionService encryptionService;

	@Autowired
	private InvoiceDataAccessManagerService invoiceDataAccessManagerService;

	@Autowired
	private InvoicePDFProducerService invoicePDFProducerService;

	@Autowired
	private IWebServiceClient webServiceClient;

	@Value("${websercice.method.push}")
	private String reqMethod;// webservice请求方法名

	/**
	 * 发票赋码
	 * 
	 * @param invoiceHeaderEntity
	 *            发票头实体
	 * @return 数据库执行是否成功
	 * @throws Exception
	 */
	@Override
	public Boolean taxcodeInvoice(final InvoiceHeaderEntity invoiceHeaderEntity)
			throws Exception {
		Boolean result = false;
		// 发票实体
		final InvoiceEntity invoiceEntity = invoiceHeaderEntity.getInvoiceEntity();
		// 发票ID
		final Long invoiceId = invoiceEntity.getInvoiceId();
		// 税纳人识别号(NSRSBH)
		final String taxpayerIdentifyNo = invoiceEntity.getTaxpayerIdentifyNo();

		// 验证签章ID是否已经配置，如果没有配置修改发票记录错误日志
		final Boolean verifyResult = invoiceDataAccessManagerService.verifyIssueInvoice(invoiceEntity);
		if (!verifyResult) {
			LOGGER.error("(发票赋码)签章ID未配置,发票赋码失败.");

			throw new FpztException(FpztConstants.TAXCODE_FAIL,
					FpztConstants.getFpzt(FpztConstants.TAXCODE_FAIL));
		}

		// 验证当前发票是否已经拥有结存信息
		final InvoiceEntity queryInvoiceEntity = invoiceDataAccessManagerService.getInvoiceEntity(invoiceId);

		if (!isNullOrEmpty(queryInvoiceEntity.getInvoiceCode())&& !isNullOrEmpty(queryInvoiceEntity.getInvoiceNo())) {
			// 已经持久化过“发票号码等信息”
			invoiceEntity.setBillingDate(queryInvoiceEntity.getBillingDate());
			invoiceEntity.setInvoiceKindCode(queryInvoiceEntity.getInvoiceKindCode());
			invoiceEntity.setInvoiceCode(queryInvoiceEntity.getInvoiceCode());
			invoiceEntity.setInvoiceNo(queryInvoiceEntity.getInvoiceNo());
			invoiceEntity.setInfoAmount(Double.valueOf(queryInvoiceEntity.getInfoAmount()));
			invoiceEntity.setInfoTaxAmount(Double.valueOf(queryInvoiceEntity.getInfoTaxAmount()));
			invoiceEntity.setInfoDate(queryInvoiceEntity.getInfoDate());
			invoiceEntity.setInfoMonth(queryInvoiceEntity.getInfoMonth());
			invoiceEntity.setGoodsListFlag(queryInvoiceEntity.getGoodsListFlag());
			invoiceEntity.setRetCode(queryInvoiceEntity.getRetCode());
			invoiceEntity.setCiphertext(queryInvoiceEntity.getCiphertext());
			invoiceEntity.setCheckCode(queryInvoiceEntity.getCheckCode());
			invoiceEntity.setInfoInvoicer(queryInvoiceEntity.getInfoInvoicer());
			invoiceEntity.setBz(queryInvoiceEntity.getBz());
            invoiceHeaderEntity.setMemo(queryInvoiceEntity.getBz());
			invoiceEntity.setInvoiceSerialNo(queryInvoiceEntity.getInvoiceCode() + queryInvoiceEntity.getInvoiceNo());
			
			result = true;
			invoiceDataAccessManagerService.updateInvoiceTaxCodeState(invoiceId, INV_TAXCODE_SUCCESS.getParameterValue());

		} else {

			// 没有持久化过“发票号码等信息”，需要获取发票号码

			final DateTime begin1 = now();

			// 组织报文协议头
			final GlobalInfo globalInfo = webServiceClient.assemblingGlobalInfo(XmlPar.ECXML_FPKJ_TAXCODE_E_INV,invoiceEntity.getInvoiceCode(), null,taxpayerIdentifyNo);

			final Long millSeconds1 = new Duration(begin1, now()).getMillis();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("(发票赋码)发票{}组织协议头用时{}毫秒......", invoiceId,millSeconds1);
			}

			final REQUEST_FPKJ request_fpkj = new REQUEST_FPKJ();
			final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			try {
				final DateTime begin11 = now();
				String taxpayerUrlLp = null;
				String taxpayerUrlHp = null;
				// 查询纳税人启用的路由信息
				final TaxcodeRouteEntity taxcodeRoute = invoiceDataAccessManagerService.queryTaxcodeRouteInfo(taxpayerIdentifyNo);

				if (!StringUtils.isNullOrEmpty(taxcodeRoute.getLpurl()) && !StringUtils.isNullOrEmpty(taxcodeRoute.getHpurl())) {

					taxpayerUrlLp = taxcodeRoute.getLpurl();
					taxpayerUrlHp = taxcodeRoute.getHpurl();
				} else {
					LOGGER.error("发票赋码路由表没有配置！");
					throw new FpztException(FpztConstants.TAXCODE_FAIL,"发票赋码路由表没有配置！");
				}
				// 组织发票赋码业务的请求头信息
				taxCodeHeader(invoiceId, taxpayerIdentifyNo, invoiceEntity,globalInfo, invoiceHeaderEntity, request_fpkj,taxcodeRoute.getVersionNo());
				//获取general.property中的配置文件,去判断是否是测试
				String testcard = SystemConfig.TESTCARD;
				if ("y".equals(testcard)) {
					invoiceEntity.setFjh("1");
		            invoiceEntity.setJqbh("661505060904");
		            invoiceEntity.setTwoDimensionCode("Qk1+BgAAAAAAAD4AAAAoAAAAZAAAAGQAAAABAAEAAAAAAEAGAAAAAAAAAAAAAAAAAAACAAAAAAAA///////////////////////wAAAA////////////////8AAAAP////////////////AAAAD////////////////wAAAA////////////////8AAAAP////////////////AAAAD////////////////wAAAA////////////////8AAAAP////////////////AAAAD////////////////wAAAA////////////////8AAAAP////////////////AAAAD////////////////wAAAA////////////////8AAAAP////////////////AAAAD////////////////wAAAA////////////////8AAAAP////////////////AAAAD////////////////wAAAA////////////////8AAAAP////////////////AAAAD////////////////wAAAA////////////////8AAAAP////////////////AAAAD////////////////wAAAA////////////////8AAAAAADPAAAzPPzwD////AAAAAAAzwAAMzz88A////wAAAAP/P8P8/DM//A////8AAAAD/z/D/PwzP/wP////AAAAAwM8D/DzPMM/D////wAAAAMDPA/w8zzDPw////8AAAADAzw888P8Dz/P////AAAAAwM8PPPD/A8/z////wAAAAMDM8/PDzAwAw////8AAAADAzPPzw8wMAMP////AAAAA/8/DMAPAwPwD////wAAAAP/PwzADwMD8A////8AAAAAADDzAPPD8zAP////AAAAAAAw8wDzw/MwD////wAAAA//8DDP8APD88////8AAAAP//Awz/ADw/PP////AAAAD8MAD/Aw/zAAD////wAAAA/DAA/wMP8wAA////8AAAAAw8PwAAAP8MwD////AAAAAMPD8AAAD/DMA////wAAAAAAAzPMDMMA/A////8AAAAAAAMzzAzDAPwP////AAAADPPwPz8MM8zDD////wAAAAzz8D8/DDPMww////8AAAAM8AwP8/P/MzwD////AAAADPAMD/Pz/zM8A////wAAAAPz8MPDwM8M/D////8AAAAD8/DDw8DPDPw/////AAAAD/AMMA88D8zPD////wAAAA/wDDAPPA/Mzw////8AAAAPz8PwzPPADMPP////AAAAD8/D8MzzwAzDz////wAAAA//M8PADM8zPDP///8AAAAP/zPDwAzPMzwz////AAAADPDMMDw8MwzMD////wAAAAzwzDA8PDMMzA////8AAAAPDA/88PM/wMwP////AAAADwwP/PDzP8DMD////wAAAA8w8wwzw/zMw8////8AAAAPMPMMM8P8zMPP////AAAADPMM8P8/MzM8A////wAAAAzzDPD/PzMzPAP///8AAAAD888PwD8P/PwD////AAAAA/PPD8A/D/z8A////wAAAAwwMADM/88PwA////8AAAAMMDAAzP/PD8AP////AAAAAz/Azw/wAMzAD////wAAAAM/wM8P8ADMwA////8AAAAMwA8DPDD/Mzwz////AAAADMAPAzww/zM8M////wAAAAwD8PDAwA8z8D////8AAAAMA/DwwMAPM/A/////AAAADA8Dz8zMAwD/D////wAAAAwPA8/MzAMA/w////8AAAAPwMPM/w8wzMP/////AAAAD8DDzP8PMMzD/////wAAAAPzAADD8/8wAPP///8AAAAD8wAAw/P/MADz////AAAAD//w8//wz8P//////wAAAA//8PP/8M/D//////8AAAAAADMzMzMzMwAD////AAAAAAAzMzMzMzMAA////wAAAAP/MD88w/A/P/P///8AAAAD/zA/PMPwPz/z////AAAAAwMz//zzAM8wM////wAAAAMDM//88wDPMDP///8AAAADAzAAD/MwzzAz////AAAAAwMwAA/zMM8wM////wAAAAMDPDDA/DM/MDP///8AAAADAzwwwPwzPzAz////AAAAA/888/APwDM/8////wAAAAP/PPPwD8AzP/P///8AAAAAADPDM8P8zwAD////AAAAAAAzwzPD/M8AA////wAAAA");
					invoiceEntity.setInvoiceCode("114011015609");
					invoiceEntity.setInvoiceNo(String.format("%08d", invoiceId));
					invoiceEntity.setInvoiceSerialNo("114011015609" + String.format("%08d", invoiceId));
					invoiceEntity.setInfoAmount(Double.valueOf(invoiceEntity.getInfoAmount()));
					invoiceEntity.setInfoTaxAmount(Double.valueOf(invoiceEntity.getInfoTaxAmount()));
					invoiceEntity.setInfoDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
					invoiceEntity.setInfoMonth("");
					invoiceEntity.setGoodsListFlag("0");
					invoiceEntity.setRetCode("4011");
					invoiceEntity.setCiphertext("6134*3>950+75<8/1208-85*</*14>-65-142/+7390*24855672678136749+92184+/8*553-9-8-+5*</*1/670433467<<3<0*24<4*6");
					invoiceEntity.setCheckCode("65560891271390877510");
					invoiceEntity.setInfoInvoicer("+Iz43YnOX/LX3+OYplTn1hgCTTbLjbbefgaplmsD0p7EgIxU3yGvJyURK4ndVpk9/cVivoyx3yIyKot3cS6N9ZClI1R9NgwHhTwciDFjfihaRF94wICl");
					invoiceEntity.setInvoiceKindCode("2");
					invoiceEntity.setBz(request_fpkj.getBZ());
					invoiceEntity.setGoodsList("");

					// 将返回的发票信息信息(发票号码，发票代码，发票种类，开票日期)持久化
					invoiceDataAccessManagerService.saveInvoiceEntityBalanceInfo(invoiceEntity);
					return invoiceDataAccessManagerService.updateInvoiceTaxCodeState(invoiceId,INV_TAXCODE_SUCCESS.getParameterValue());
				} else {
					// 生成XML(第二层报文)
					XMLShellFactory.newInstance().saveXml(outputStream,request_fpkj);
					// 把content放入整个报文中
					final Data data = EIProtocolFactory.getData(outputStream);
					
					//TODO 2017-08-10 报文中备注字段用 ![CDATA[]] 包裹
					String contentStr = data.getContent();
					contentStr.replace("<BZ>", "<BZ><![CDATA[");
					contentStr.replace("</BZ>", "]]></BZ>");
					data.setContent(contentStr);

					//特殊字符替换成空格，utf-8空格替换成功ASCII码空格
					data.setContent(CharUtil.replaceX(data.getContent()));//把特殊GBK之外特殊字符替换为空格
					data.setContent(CharUtil.nobreakSpaceToSpace(data.getContent()));//把utf-8空格替换为gbk空格
					//通过路由表版本号来判断是否是编码版本,如果是不是的话把多余节点信息删除
					if (taxcodeRoute.getVersionNo().equals("1")) {
						String context = data.getContent();
						context=context.replaceAll("\n    <BMB_BBH/>", "");
						context=context.replaceAll("\n        <SPBM/>", "");
						context=context.replaceAll("\n        <ZXBM/>", "");
						context=context.replaceAll("\n        <YHZCBS/>", "");
						context=context.replaceAll("\n        <LSLBS/>", "");
						context=context.replaceAll("\n        <ZZSTSGL/>", "");
						context=context.replaceAll("\n        <KCE/>", "");
						data.setContent(context);
					}
					final Long millSeconds11 = new Duration(begin11, now()).getMillis();
					if (LOGGER.isDebugEnabled()) {
						LOGGER.debug("(发票赋码)第二层报文Content:{}", data.getContent());
						LOGGER.debug("(发票赋码)发票{}组织第二层报文用时{}毫秒......",invoiceId, millSeconds11);
					}
					final DateTime begin12 = now();
					// 组织第一层报文
					String encryptype = null;
					// 不加密
					encryptype = "0";
                    if (null != encryptype && "" != encryptype) {
                        data.setEncryptCode(encryptype);

                        if ("0".equals(encryptype)) {
                            data.setCodeType("0");
                        } else if ("1".equals(encryptype)) {
                            data.setCodeType("3DES");
                        } else if ("2".equals(encryptype)) {
                            data.setCodeType("CA");
                        }
                    }
					// 此处传递空值是因为不使用加密方式
					final String reqXml = EIProtocolFactory.getXml(globalInfo,new ReturnStateInfo(), data, null);
					final Long millSeconds12 = new Duration(begin12, now()).getMillis();
					if (LOGGER.isDebugEnabled()) {
					    LOGGER.debug("(发票赋码)第一层报文:{}", reqXml);
					    LOGGER.debug("(发票赋码)发票{}组织第一层报文用时{}毫秒......",invoiceId, millSeconds12);
					}
			
					final DateTime begin2 = now();

					String responseMessage = null;
					//此处添加重试次数,如果失败,就继续重试,
					for (int i = 1; i < SystemConfig.default_retry_count; i++) {
						final DateTime mill1 = now();
						//根据开票类型判断是否是蓝票和红票,蓝票走蓝票的路由,红票走红票的路由
						if ("1".equals(invoiceEntity.getBillingType().toString())) {
							responseMessage = HttpClientUtil.postSubmit(taxpayerUrlLp, reqXml);
						} else {
							responseMessage = HttpClientUtil.postSubmit(taxpayerUrlHp, reqXml);
						}
						final Long mill2 = new Duration(mill1, now()).getMillis();
						
						LOGGER.debug("请求COM组件的时间------" + mill2);

						if (!isNullOrEmpty(responseMessage)) {

							if (LOGGER.isDebugEnabled()) {
								LOGGER.debug("从企业返回的消息:{}", responseMessage);
							}

							break;
						}

						LOGGER.error("(发票赋码)发票{}推送第{}次失败,开票类型{}: 发票赋码接口返回为空......",invoiceId, i,invoiceEntity.getBillingType());
					}

					final Long millSeconds2 = new Duration(begin2, now()).getMillis();
					
					if (LOGGER.isDebugEnabled()) {
						LOGGER.debug("(发票赋码)电商平台{}调用开票服务器接口用时{}毫秒......",
								invoiceHeaderEntity.getEshopCode(),
								millSeconds2);
					}

					// 返回结果为空处理
					if (isNullOrEmpty(responseMessage)) {
						final StringBuilder sb = new StringBuilder("");
						sb.append("发票:");
						sb.append(invoiceId);
						sb.append(",赋码失败:");
						sb.append("调用开票服务器接口返回空数据.");
						LOGGER.error(sb.toString());

						throw new FpztException(FpztConstants.JDJKSB,
								sb.toString());
					}
					// 处理返回的信息
					result = taxCoceFoot(invoiceId, responseMessage,taxpayerIdentifyNo, invoiceEntity, request_fpkj);
				}
			} finally {
				LOGGER.info("(发票推送)关闭输出流.");
				outputStream.close();
			}
		}

		return result;
	}

	/**
	 * 生产发票开具信息和pdf文件
	 * 
	 * @param invoiceHeaderEntity
	 *            发票头实体
	 * @return 数据库执行是否成功
	 * @throws Exception
	 */
	@Override
	public Boolean generatorInvoice(
			final InvoiceHeaderEntity invoiceHeaderEntity) throws Exception {
		// 发票实体
		final InvoiceEntity invoiceEntity = invoiceHeaderEntity.getInvoiceEntity();
		// 发票ID
		final Long invoiceId = invoiceEntity.getInvoiceId();
		// 税纳人识别号(NSRSBH)
		final String taxpayerIdentifyNo = invoiceEntity.getTaxpayerIdentifyNo();

		// 验证当前发票是否已经拥有代码号码等信息
		final InvoiceEntity queryInvoiceEntity = invoiceDataAccessManagerService.getInvoiceEntity(invoiceId);
		
		if (!isNullOrEmpty(queryInvoiceEntity.getInvoiceCode()) && !isNullOrEmpty(queryInvoiceEntity.getInvoiceNo())) {

			// 已经持久化过“发票号码等信息”
			invoiceEntity.setBillingDate(queryInvoiceEntity.getBillingDate());
			invoiceEntity.setInvoiceKindCode(queryInvoiceEntity.getInvoiceKindCode());
			invoiceEntity.setInvoiceCode(queryInvoiceEntity.getInvoiceCode());
			invoiceEntity.setInvoiceNo(queryInvoiceEntity.getInvoiceNo());
			invoiceEntity.setInfoAmount(Double.valueOf(queryInvoiceEntity.getInfoAmount()));
			invoiceEntity.setInfoTaxAmount(Double.valueOf(queryInvoiceEntity.getInfoTaxAmount()));
			invoiceEntity.setInfoDate(queryInvoiceEntity.getInfoDate());
			invoiceEntity.setInfoMonth(queryInvoiceEntity.getInfoMonth());
			invoiceEntity.setGoodsListFlag(queryInvoiceEntity.getGoodsListFlag());
			invoiceEntity.setRetCode(queryInvoiceEntity.getRetCode());
			invoiceEntity.setCiphertext(queryInvoiceEntity.getCiphertext());
			invoiceEntity.setCheckCode(queryInvoiceEntity.getCheckCode());
			invoiceEntity.setInfoInvoicer(queryInvoiceEntity.getInfoInvoicer());
			invoiceEntity.setBz(queryInvoiceEntity.getBz());
            invoiceHeaderEntity.setMemo(queryInvoiceEntity.getBz());
			invoiceEntity.setInvoiceSerialNo(queryInvoiceEntity.getInvoiceCode() + queryInvoiceEntity.getInvoiceNo());
			if(!StringUtils.isNullOrEmpty(queryInvoiceEntity.getTwoDimensionCode())){
			    invoiceEntity.setTwoDimensionCode(queryInvoiceEntity.getTwoDimensionCode());
			}

		} else {
			LOGGER.error("(生成发票)此税纳人{}未发现发票代码号码,id为{}.", taxpayerIdentifyNo,invoiceId);
			throw new FpztException(FpztConstants.GENERATOR_INVOICE_FAIL,FpztConstants.getFpzt(FpztConstants.GENERATOR_INVOICE_FAIL));
		}
		
		final FPKJXX_FPJGXX fpkjxx_fpjgxx = new FPKJXX_FPJGXX();
		fpkjxx_fpjgxx.setKPLSH(invoiceEntity.getInvoiceCode() + invoiceEntity.getInvoiceNo());
		fpkjxx_fpjgxx.setEWM(invoiceEntity.getTwoDimensionCode());

		final DateTime begin6 = now();

		// 添加生成pdf文件判断，如果为N不生成pdf文件 Y生成pdf文件
		if ("Y".equals(SystemConfig.pdfDisjunctor)) {
			fpkjxx_fpjgxx.setFPZL_DM(invoiceEntity.getInvoiceKindCode());
			fpkjxx_fpjgxx.setFP_DM(invoiceEntity.getInvoiceCode());
			fpkjxx_fpjgxx.setFP_HM(invoiceEntity.getInvoiceNo());
			fpkjxx_fpjgxx.setDDH(invoiceHeaderEntity.getOrderNo());
			fpkjxx_fpjgxx.setFPQQLSH(invoiceHeaderEntity.getInvoiceRequestSerialNo());

			// 获取电商平台信息
			final EInvoiceQueryCondition esCondition = new EInvoiceQueryCondition();
			esCondition.setEshopCode(invoiceEntity.getEshopCode());
			final EShopInfo eShopInfo = invoiceDataAccessManagerService.queryEShopInfo(esCondition);

			// 设置纳税人所在税务机关代码(用于"上传税局"MQ使用)
			invoiceHeaderEntity.setTaxAuthorityCode(eShopInfo.getTaxAuthorityCode());
			// 设置电商注册号(用于"上传税局"MQ使用)
			invoiceHeaderEntity.setRegisterNo(eShopInfo.getRegisterNo());

			// 生产PDF文件返回生产路径
			final InvoicePDFEntity pdfEntity = new InvoicePDFEntity();
			invoiceHeaderEntity.setInvoiceEntity(invoiceEntity);
			pdfEntity.setInvoiceHeaderEntity(invoiceHeaderEntity);
			pdfEntity.setFpkjxxFpjgxx(fpkjxx_fpjgxx);
			pdfEntity.seteShopInfo(eShopInfo);
			// 签章ID由受理端SL传递过来，签章ID与纳税人识别号关联
			pdfEntity.setTaxerSignatureId(invoiceEntity.getSignatureId());

			// 获取发票开具明细
			final EInvoiceQueryCondition diCondition = new EInvoiceQueryCondition();
			diCondition.setInvoiceId(invoiceId);
			final List<InvoiceDetailEntity> invoiceDetailEntityList = invoiceDataAccessManagerService.queryInvoiceDetailInfo(diCondition);

			if (isEmpty(invoiceDetailEntityList)) {
				LOGGER.error("(生成发票)获取发票开具明细信息为空.");

				throw new FpztException(FpztConstants.PFMXFIAL,FpztConstants.getFpzt(FpztConstants.PFMXFIAL));
			}

			// 转为协议Bean
			final List<FPKJXX_XMXX> resultList = newArrayList(transform(invoiceDetailEntityList,new Function<InvoiceDetailEntity, FPKJXX_XMXX>() {
						@Override
						public FPKJXX_XMXX apply(InvoiceDetailEntity invoiceDetailEntity) {
					          FPKJXX_XMXX fpkjxx_xmxx = new FPKJXX_XMXX();
					          fpkjxx_xmxx.setXMMC(invoiceDetailEntity.getItemName());
					          fpkjxx_xmxx.setXMDW(invoiceDetailEntity.getUnitName());
					          fpkjxx_xmxx.setGGXH(invoiceDetailEntity.getSpecificationModel());
					          //TODO 调用京东云签章需格式化项目单价和项目金额  2017-06-22 FWH
					          fpkjxx_xmxx.setXMJE(String.valueOf(new DecimalFormat("#########0.00").format(new BigDecimal(invoiceDetailEntity.getItemAmount().doubleValue()))));
					          //TODO 由于调用京东云签章服务器，默认单价是2位小数点。遂改了回来不宜格式化  2017-07-10 
					          fpkjxx_xmxx.setXMSL(String.valueOf(new BigDecimal(invoiceDetailEntity.getItemCount().doubleValue())));
					          fpkjxx_xmxx.setXMDJ(String.valueOf(new BigDecimal(invoiceDetailEntity.getItemUnitCost().doubleValue())));
					          fpkjxx_xmxx.setXMBM(invoiceDetailEntity.getItemCode());
					          fpkjxx_xmxx.setSL(invoiceDetailEntity.getInfoTaxRate());
					          fpkjxx_xmxx.setSE(String.valueOf(new BigDecimal(invoiceDetailEntity.getListTaxAmount().doubleValue())));
					          //TODO 调用京东云签章需往明细行添加新字段  2017-06-22 FWH
					          fpkjxx_xmxx.setFPHXZ(invoiceDetailEntity.getInvoiceLineProperty());
					          fpkjxx_xmxx.setSPBM(invoiceDetailEntity.getGoodsCode());
					          fpkjxx_xmxx.setYHZCBS(invoiceDetailEntity.getPreferentialMarking());
					          fpkjxx_xmxx.setLSLBS(invoiceDetailEntity.getZeroTariff());
					          fpkjxx_xmxx.setZZSTSGL(invoiceDetailEntity.getSpecialManagement());
					          fpkjxx_xmxx.setKCE(invoiceDetailEntity.getDeductions());
					          return fpkjxx_xmxx;
					          
						}
					}));
			
			
			//判断广州圆周分公司是否0税率发票，如果是0税率发票PDF文件显示修改成 *** 2017-03-21 吴永修改
			String[] FPKJ_LSLBS_NSRSBH = SystemConfig.FPKJ_LSLBS_NSRSBH.split(",");
		      if (FPKJ_LSLBS_NSRSBH.length > 0) {
		        for (int i = 0; i < FPKJ_LSLBS_NSRSBH.length; i++) {
		          for (int j = 0; j < resultList.size(); j++) {
		            FPKJXX_XMXX fpkjxx_XMXX = (FPKJXX_XMXX)resultList.get(j);
		            if ((FPKJ_LSLBS_NSRSBH[i].equals(taxpayerIdentifyNo)) && ("0%".equals(fpkjxx_XMXX.getSL()))) {
		              fpkjxx_XMXX.setSL("***");
		            }
		          }
		        }
		      }

			final FPKJXX_XMXX[] fpkjxx_xmxxes = new FPKJXX_XMXX[resultList.size()];
			resultList.toArray(fpkjxx_xmxxes);
			pdfEntity.setFpkjxxXmxxes(fpkjxx_xmxxes);

			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("(生成发票)PdfEntity:{}", pdfEntity.toString());
			} 
			
			long start=System.currentTimeMillis();
			//TODO 生成发票的路径
			final String pdfPath = invoicePDFProducerService.generatePdfFile(pdfEntity);
			long end=System.currentTimeMillis();
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error("生成pdf用时{}ms",end-start);
			}
			final Long millSeconds6 = new Duration(begin6, now()).getMillis();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("(生成发票)发票{}创建PDF文件并返回文件路径用时{}毫秒......", invoiceId,millSeconds6);
			}
			if (isNullOrEmpty(pdfPath)) {
				LOGGER.error("(生成发票)PDF签章失败.");

				throw new FpztException(FpztConstants.PDFSIGNFIAL,FpztConstants.getFpzt(FpztConstants.PDFSIGNFIAL));
			} else {
				invoiceEntity.setPdfPath(pdfPath);
			}
		}

		final DateTime begin7 = now();

		// 开票数据库操作(包含处理红票)
		final Boolean result = invoiceDataAccessManagerService.generatorInvoice(invoiceEntity);

		final Long millSeconds7 = new Duration(begin7, now()).getMillis();
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("(生成发票)发票{}开票业务数据库操作用时{}毫秒......", invoiceId,
					millSeconds7);
		}
		return result;
	}

	@Override
	public Boolean pushInvoice(InvoiceHeaderEntity invoiceHeaderEntity,
			final PushInvoiceQueueEntity pushInvoiceQueueEntity,
			final EShopCertificateBytesInfo certificate) throws Exception {
		// 发票实体
		final InvoiceEntity invoiceEntity = invoiceHeaderEntity.getInvoiceEntity();
		// 发票ID
		final Long invoiceId = invoiceEntity.getInvoiceId();
		// 纳税人识别号
		final String taxpayerIdentifyNo = invoiceEntity.getTaxpayerIdentifyNo();
		// 数据库操作结果
		Boolean result = Boolean.FALSE;

		// 获取电商平台信息
		final EInvoiceQueryCondition esCondition = new EInvoiceQueryCondition();
		esCondition.setEshopCode(invoiceEntity.getEshopCode());
		final EShopInfo eShopInfo = invoiceDataAccessManagerService.queryEShopInfo(esCondition);

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("(发票推送)获取电商平台信息: {}", eShopInfo.toString());
		}

		final Password password = PassWordCheck.passWordCreate(eShopInfo
				.getRegisterNo());
		final String registerNo = password.getSjm() + password.getPass();

		// 获取平台编码(51平台编码)
		final String platformCode = eShopInfo.getPlatformCode();

		final DateTime begin1 = now();

		// 组织报文协议头
		final GlobalInfoPush globalInfo = webServiceClient.assemblingGlobalInfopush(XmlPar.ECXML_FPKJ_PUSH_E_INV,
						platformCode, registerNo, taxpayerIdentifyNo);

		final Long millSeconds1 = new Duration(begin1, now()).getMillis();
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("(发票推送)发票{}组织协议头用时{}毫秒......", invoiceId, millSeconds1);
		}

		// 协议Bean
		final REQUEST_FPKJXX_FPJGXX request_common_fpjgxx = new REQUEST_FPKJXX_FPJGXX();
		final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		try {

			final DateTime begin11 = now();

			// 判断是否生成PDF文件
			if (!"N".equals(SystemConfig.pdfDisjunctor)) {
				final org.apache.commons.codec.binary.Base64 base = new org.apache.commons.codec.binary.Base64();
				// 把pdf转成byte
				final byte[] fileByte = FileUtils.readFileToByteArray(new File(invoiceEntity.getPdfPath()));
				request_common_fpjgxx.setPDF_FILE(new String(base.encode(fileByte), SystemConfig.xmlCharset));

				// request_common_fpjgxx.setPDF_FILE("");
				// request_common_fpjgxx.setPDF_URL(SystemConfig.APP_PREFIX +
				// "/eInvoiceDownload?fiscalCode=" +
				// invoiceEntity.getFiscalCode());
			}

			// 业务Bean转协议Bean
			
			request_common_fpjgxx.setDSPTBM(invoiceEntity.getEshopCode());
			request_common_fpjgxx.setNSRSBH(invoiceEntity.getTaxpayerIdentifyNo());
			request_common_fpjgxx.setKPLSH(invoiceEntity.getInvoiceSerialNo());
			request_common_fpjgxx.setFPQQLSH(invoiceHeaderEntity.getInvoiceRequestSerialNo());
			request_common_fpjgxx.setFWM(invoiceEntity.getFiscalCode());
			request_common_fpjgxx.setEWM(invoiceEntity.getTwoDimensionCode());
			request_common_fpjgxx.setFPZL_DM(invoiceEntity.getInvoiceKindCode());
			request_common_fpjgxx.setFP_DM(invoiceEntity.getInvoiceCode());
			request_common_fpjgxx.setFP_HM(invoiceEntity.getInvoiceNo());
			request_common_fpjgxx.setKPLX(String.valueOf(invoiceEntity.getBillingType()));
			request_common_fpjgxx.setDDH(invoiceHeaderEntity.getOrderNo());
			request_common_fpjgxx.setCZDM(invoiceHeaderEntity.getOperatorNo());
			request_common_fpjgxx.setKPRQ(new DateTime(invoiceEntity.getBillingDate()).toString(SystemConfig.default_date_format));
			// request_common_fpjgxx.setKPRQ(invoiceEntity.getBillingDateFormat());
			request_common_fpjgxx.setRETURNCODE(XmlPar.RESPONSEYYSSUCCESS);
			request_common_fpjgxx.setRETURNMESSAGE("");

			// 生成XML(第二层报文)
			XMLShellFactory.newInstance().saveXml(outputStream,
					request_common_fpjgxx);
			// 把content放入整个报文中
			final Data data = EIProtocolFactory.getData(outputStream);

			final Long millSeconds11 = new Duration(begin11, now()).getMillis();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("(发票推送)第二层报文:{}",
						outputStream.toString(SystemConfig.dataCharset));
				LOGGER.debug("(发票推送)第二层报文Content:{}", data.getContent());
				LOGGER.debug("(发票推送)发票{}组织第二层报文用时{}毫秒......", invoiceId,
						millSeconds11);
			}

			final DateTime begin12 = now();

			// 组织第一层报文
			String encryptype = null;
			encryptype = pushInvoiceQueueEntity.getEncrypType();
			if (null != encryptype && "" != encryptype) {
				data.setEncryptCode(encryptype);

				if ("0".equals(encryptype)) {
					data.setCodeType("0");
				} else if ("1".equals(encryptype)) {
					data.setCodeType("3DES");
				} else if ("2".equals(encryptype)) {
					data.setCodeType("CA");
				}
			}
			String wsMethod = null;
			final String reqXml = EIProtocolFactory.getXmlPush(globalInfo,
					new ReturnStateInfo(), data, certificate);
			wsMethod = pushInvoiceQueueEntity.getWsMethodName();
			if (null == wsMethod || "" == wsMethod) {
				wsMethod = reqMethod;
			}
			final String taxpayerUrl = pushInvoiceQueueEntity.getTaxpayerUrl();

			final Long millSeconds12 = new Duration(begin12, now()).getMillis();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("(发票推送)第一层报文:{}", reqXml);
				LOGGER.debug("(发票推送)发票{}组织第一层报文用时{}毫秒......", invoiceId,
						millSeconds12);
			}

			final DateTime begin2 = now();

			String responseMessage = null;
			for (int i = 1; i < SystemConfig.default_retry_count; i++) {
				// 调用电商的接口，如果本次失败，当场重试若干次
				responseMessage = webServiceClient.clientData(wsMethod, reqXml,taxpayerUrl);

				if (!isNullOrEmpty(responseMessage)) {

					if (LOGGER.isDebugEnabled()) {
						LOGGER.debug("从企业返回的消息:{}", responseMessage);
					}

					break;
				}

				LOGGER.error("(发票推送)发票{}推送第{}次失败: 企业WS接口返回为空......", invoiceId,i);
			}

			final Long millSeconds2 = new Duration(begin2, now()).getMillis();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("(发票推送)电商平台{}调用电商接口用时{}毫秒......", platformCode,
						millSeconds2);
			}

			// 返回结果为空处理
			if (isNullOrEmpty(responseMessage)) {
				final StringBuilder sb = new StringBuilder("");
				sb.append("发票:");
				sb.append(invoiceId);
				sb.append(",推送失败:");
				sb.append("企业接口返回空数据.");
				LOGGER.error(sb.toString());

				throw new FpztException(FpztConstants.JDJKSB, sb.toString());
			}

			// 处理返回结果(返回信息不使用CA加密)
			final Map<String, Object> map = EIProtocolFactory.getInterface(
					responseMessage, certificate, false);
			final ReturnStateInfo returnInfo = (ReturnStateInfo) map
					.get(XmlPar.RETURNSTATEINFO);

			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("(发票推送)WebService返回结果, ReturnCode: {}",
						returnInfo.getReturnCode());
				LOGGER.debug("(发票推送)WebService返回结果, ReturnMessage: {}",
						returnInfo.getReturnMessage());
			}
			if (XmlPar.RESPONSEYYSSUCCESS.equals(returnInfo.getReturnCode())) {

				// 更新推送状态位
				result = invoiceDataAccessManagerService.updateInvoicePushState(invoiceId,SystemConfig.INVOICE_PUSH_STATE_SUCCESS);
				//=========
				if (LOGGER.isDebugEnabled() && result) {
					LOGGER.debug("(发票推送)发票{}, 获取推送结果成功......", invoiceId);
				}

			}else{
			 // 更新推送状态位
                result = invoiceDataAccessManagerService.updateInvoicePushState(invoiceId,SystemConfig.INVOICE_PUSH_STATE_FAILURE);

                if (LOGGER.isDebugEnabled() && result) {
                    LOGGER.debug("(发票推送)发票{}, 获取推送结果失败......", invoiceId);
                }
                result=false;
			}

		} finally {
			LOGGER.info("(发票推送)关闭输出流.");
			outputStream.close();
		}

		return result;

	}

	@Override
	public List<GeneratorInvoiceQueueEntity> queryEInvoiceGenerate(
			final EInvoiceQueryCondition queryCondition) {
		return invUploadDao.queryEInvoiceGenerate(queryCondition);
	}

	@Override
	public List<PushInvoiceQueueEntity> queryEInvoicePush(
			EInvoiceQueryCondition queryCondition) {
		return invUploadDao.queryEInvoicePush(queryCondition);
	}

	@Override
	public List<SentToTaxQueueEntity> queryTaxUpload(
			final EInvoiceQueryCondition queryCondition) {
		return invUploadDao.queryTaxUpload(queryCondition);
	}

	@Override
	public Integer updateInvoiceStatus(
			final EInvoiceQueryCondition queryCondition) {
		return invUploadDao.updateInvoiceStatus(queryCondition);
	}

	@Override
	public Integer updateInvoicePushStatus(EInvoiceQueryCondition queryCondition) {
		return invUploadDao.updateInvoicePushStatus(queryCondition);
	}

	@Override
	public byte[] getPdfBytes(String fiscalCode) {
		final String pdfPath = invoiceDataAccessManagerService
				.getInvoicePdfPath(fiscalCode);

		byte[] out = null;
		try {
			LOGGER.info("getInvoicePdfPath fiscalCode {}- pdfPath {}",
					fiscalCode, pdfPath);
			out = readFileToByteArray(new File(pdfPath));
		} catch (IOException e) {
			LOGGER.info("catch IOException: pdfPath {}", pdfPath);
		}
		return out;
	}

	@Override
	@Cacheable(value = "eshopPCKS7Cache", key = "#taxpayerIdentifyNo")
	public EShopCertificateBytesInfo obtainEShopCAInfo(String taxpayerIdentifyNo)
			throws IOException {

		/*
		 * TRACY MARK:通讯CA加密方式采用2套证书对称进行；平台端事件使用 平台端私钥(pfx)、pwd + 客户端公钥(cer) +
		 * 公共信任链
		 */
		final CertificateEntity clientCertificate = invoiceDataAccessManagerService
				.getCertificateEntity(taxpayerIdentifyNo);
		final CertificateEntity platformCertificate = invoiceDataAccessManagerService
				.getPlatformCertificateEntity();
		platformCertificate.setPublicKey(clientCertificate.getPublicKey());

		try {
			final EShopCertificateBytesInfo certificateBytesInfo = new EShopCertificateBytesInfo();

			certificateBytesInfo.setTaxpayerIdentifyNo(taxpayerIdentifyNo);
			certificateBytesInfo
					.setPrivatePFXBytes(readFileToByteArray(new File(
							platformCertificate.getPrivateKey())));
			certificateBytesInfo
					.setPublicPFXBytes(readFileToByteArray(new File(
							platformCertificate.getPublicKey())));
			certificateBytesInfo.setTrustsBytes(readFileToByteArray(new File(
					platformCertificate.getTrust())));
			certificateBytesInfo.setPrivatePFXKey(platformCertificate
					.getPassword());

			return certificateBytesInfo;

		} catch (Exception e) {
			return null;
		}

	}

	public static FP_KJMX[] rebuild(FP_KJMX[] mxs)
			throws UnsupportedEncodingException {
		List<FP_KJMX> mxList = new ArrayList<FP_KJMX>();
		for (FP_KJMX mx : mxs) {
			if (mx != null) {
				String xm = mx.getSPMC();
				int len = 0;
				int start = 0;
				for (int i = 0; i < xm.length(); i++) {
					if (xm.charAt(i) > 256) {
						len += 2;
					} else {
						len++;
					}
					if (len > 30) {
						mx.setSPMC(xm.substring(start, i + 1));
						mxList.add(mx);
						mx = new FP_KJMX();
						start = i + 1;
						len = 0;
					}
					if (i == xm.length() - 1 && len > 0) {
						mx.setSPMC(xm.substring(start, i + 1));
						mxList.add(mx);
					}
				}
			}
		}
		FP_KJMX[] news = new FP_KJMX[mxList.size()];
		mxList.toArray(news);
		return news;
	}


	@Override
	public List queryfpkj(int i) {
		return invUploadDao.queryfpkj(i);
	}

	@Override
	public List queryfpkjmx(String id) {
		return invUploadDao.queryfpkjmx(id);
	}

	@Override
	public List queryfpddmx(String id) {
		return invUploadDao.queryfpddmx(id);
	}

	private void taxCodeHeader(Long invoiceId, String taxpayerIdentifyNo,InvoiceEntity invoiceEntity, GlobalInfo globalInfo,
			InvoiceHeaderEntity invoiceHeaderEntity, REQUEST_FPKJ request_fpkj,String versionNo)
			throws FpztException, UnsupportedEncodingException {
		/*
		 * 
		 * 根据发票开具的id，获取明细数据
		 */
		EInvoiceQueryCondition condition = new EInvoiceQueryCondition();
		condition.setInvoiceId(invoiceId);
		condition.setTaxpayerIdentifyNo(taxpayerIdentifyNo);
//		if ("2".equals(invoiceEntity.getBillingType().toString())) {
//			Map<String, String> map = new HashMap<String, String>();
//			map.put("yfp_dm", invoiceEntity.getOldInvoiceCode());
//			map.put("yfp_hm", invoiceEntity.getOldInvoiceNo());
//			final List<Map<String, String>> lpfjh = invoiceDataAccessManagerService
//					.queryFjh(map);
//			if (lpfjh != null) {
//				Map<String, String> m = lpfjh.get(0);
//
//				if (!isNullOrEmpty(m.get("fjh").toString())) {
//					globalInfo.setFjh(m.get("fjh").toString());
					
//				} else {
//					throw new FpztException(FpztConstants.TAXCODE_FAIL,
//							"红票无法获取到蓝票对应的分机号");
//				}
//			}
//		}
		//根据发票开具id去明细表中获取明细数据
		final List<InvoiceDetailEntity> fpkjmx = invoiceDataAccessManagerService.queryInvoiceDetailInfo(condition);
		final FP_KJMX[] fpkjmxs = new FP_KJMX[fpkjmx.size()];
		if (fpkjmx != null) {
			//根据路由版本号来判断是否是编码版本
			if (versionNo.equals("1")) {
				for (int i = 0; i < fpkjmx.size(); i++) {
					final InvoiceDetailEntity eachBean = fpkjmx.get(i);
					final FP_KJMX fpkjmxxx = new FP_KJMX();
					fpkjmxxx.setSPMC(eachBean.getItemName());
					fpkjmxxx.setSM(eachBean.getListTaxItem());
					fpkjmxxx.setSL(String.valueOf(Double.parseDouble(eachBean.getInfoTaxRate().toString().replaceAll("\\%", "")) / 100));
					fpkjmxxx.setGGXH(eachBean.getSpecificationModel());
					fpkjmxxx.setJLDW(eachBean.getListUnit());
					fpkjmxxx.setSPSL(String.valueOf(eachBean.getItemCount()));
					fpkjmxxx.setSPDJ(String.valueOf(eachBean.getItemUnitCost()));
					fpkjmxxx.setSPJE(String.valueOf(eachBean.getItemAmount()));
					fpkjmxxx.setHSJBZ(eachBean.getListPriceKind());
					fpkjmxxx.setSE(String.valueOf(eachBean.getListTaxAmount()));
					fpkjmxxx.setFPHXZ(eachBean.getInvoiceLineProperty());
					fpkjmxs[i] = fpkjmxxx;
				}
			}else{
				for (int i = 0; i < fpkjmx.size(); i++) {
					final InvoiceDetailEntity eachBean = fpkjmx.get(i);
					final FP_KJMX fpkjmxxx = new FP_KJMX();
					fpkjmxxx.setSPMC(eachBean.getItemName());
					fpkjmxxx.setSM(eachBean.getListTaxItem());
					fpkjmxxx.setSL(String.valueOf(Double.parseDouble(eachBean.getInfoTaxRate().toString().replaceAll("\\%", "")) / 100));
					fpkjmxxx.setGGXH(eachBean.getSpecificationModel());
					fpkjmxxx.setJLDW(eachBean.getListUnit());
					fpkjmxxx.setSPSL(String.valueOf(eachBean.getItemCount()));
					fpkjmxxx.setSPDJ(String.valueOf(eachBean.getItemUnitCost()));
					fpkjmxxx.setSPJE(String.valueOf(eachBean.getItemAmount()));
					fpkjmxxx.setHSJBZ(eachBean.getListPriceKind());
					fpkjmxxx.setSE(String.valueOf(eachBean.getListTaxAmount()));
					fpkjmxxx.setSPBM(eachBean.getGoodsCode());
					fpkjmxxx.setZXBM(eachBean.getSelfCoding());
					fpkjmxxx.setYHZCBS(eachBean.getPreferentialMarking());
					fpkjmxxx.setLSLBS(eachBean.getZeroTariff());
					fpkjmxxx.setZZSTSGL(eachBean.getSpecialManagement());
					fpkjmxxx.setKCE(eachBean.getDeductions());
					fpkjmxxx.setFPHXZ(eachBean.getInvoiceLineProperty());
					fpkjmxs[i] = fpkjmxxx;
				}
			}
		} else {
			LOGGER.error("（发票赋码）发票明细信息查询，发票明细信息为空！");
			throw new FpztException(FpztConstants.TAXCODE_FPKJMX_FAIL,FpztConstants.getFpzt(FpztConstants.TAXCODE_FPKJMX_FAIL));
		}
		//判断发票请求流水号是不是空
		if (isNullOrEmpty(invoiceHeaderEntity.getInvoiceRequestSerialNo())) {
			throw new FpztException(Integer.parseInt(INV_TAXCODE_FAILURE
					.toString()), "发票请求流水号为空！");

		} else {
			request_fpkj.setFPQQLSH(invoiceHeaderEntity.getInvoiceRequestSerialNo());
			globalInfo.setDataExchangeId(invoiceHeaderEntity.getInvoiceRequestSerialNo());
		}
		//根据纳税人识别号获取他的发票种类代码
		InvoiceBalance invoiceBalance = invoiceDataAccessManagerService.queryInvoiceKindCodeInfo(condition);
		
		request_fpkj.setFPZL_DM(invoiceBalance.getInvoiceKindCode());
		/*
		 * 2015年3月10日 13:55:46 新增字段的转换 张双超 begin
		 */
		request_fpkj.setGHFMC(invoiceHeaderEntity.getBuyerName());                        //购货方名称
		request_fpkj.setGHF_NSRSBH(invoiceHeaderEntity.getBuyerTaxpayerIdentifyNo());     //购货方纳税人识别号
		request_fpkj.setFKFKHYH_FKFYHZH(invoiceHeaderEntity.getInfoClientBankAccount());  //付款方银行帐号
		request_fpkj.setFKFDZ_FKFDH(invoiceHeaderEntity.getInfoClientAddressPhone());     //付款单位地址电话
		request_fpkj.setXHFKHYH_SKFYHZH(invoiceHeaderEntity.getInfoSellerBankAccount());  //销货方银行帐号
		request_fpkj.setXHFDZ_XHFDH(invoiceHeaderEntity.getInfoSellerAddressPhone());     //销货单位地址电话
		request_fpkj.setFHR(invoiceHeaderEntity.getInfoChecker());                        //复核人
		request_fpkj.setKPY(invoiceHeaderEntity.getBillingStaff());                       //开票员
		request_fpkj.setSKY(invoiceHeaderEntity.getCashier());                            //收款员
		request_fpkj.setJSHJ(String.valueOf(invoiceHeaderEntity.getBillingAmount()));     //价税合计
		request_fpkj.setHJJE(String.valueOf(invoiceHeaderEntity.getInfoAmount()));        //合计金额
		request_fpkj.setHJSE(String.valueOf(invoiceHeaderEntity.getInfoTaxAmount()));     //合计税额
		request_fpkj.setYFP_DM(invoiceEntity.getOldInvoiceCode());                        //原发票代码
        request_fpkj.setYFP_HM(invoiceEntity.getOldInvoiceNo());                         //原发票号码
        request_fpkj.setXHQD(invoiceHeaderEntity.getGoodsListFlag());                      //销货清单
        request_fpkj.setBMB_BBH(versionNo.equals("1")?null:invoiceHeaderEntity.getInfoSellerVersion());			 //编码表版本号
        request_fpkj.setFP_KJMXS(fpkjmxs);                                                // 发票明细信息放入协议bean中
        //2017-08-10 添加备注字段，传递给税局
        request_fpkj.setBZ(invoiceHeaderEntity.getMemo());

		// 修改发票开票类型，因为增值税的蓝票开票类型是0，红票开票类型是1，跟普通发票的不一样，发票赋码的时候修改开票类型
		if ("1".equals(invoiceEntity.getBillingType().toString())) {
			request_fpkj.setKPLX("0");
		} else if("2".equals(invoiceEntity.getBillingType().toString())) {
			request_fpkj.setKPLX("1");
		} else{
			request_fpkj.setKPLX(invoiceEntity.getBillingType().toString());
		}
	}

	private boolean taxCoceFoot(Long invoiceId, String responseMessage,
			String taxpayerIdentifyNo, InvoiceEntity invoiceEntity,
			REQUEST_FPKJ request_fpkj) throws Exception {

		// 处理返回结果(返回信息不使用CA加密)
		final Map<String, Object> map = EIProtocolFactory.getInterface(responseMessage, null, true);

		Data data1 = (Data) map.get(XmlPar.DATA);
		ReturnStateInfo returnStateInfo = (ReturnStateInfo) map.get(XmlPar.RETURNSTATEINFO);
		//返回的状态不为空
		if (returnStateInfo == null) {
			final StringBuilder sb = new StringBuilder("");
			sb.append("发票:");
			sb.append(invoiceId);
			sb.append(",赋码失败:");
			sb.append("解析开票服务器接口返回的数据失败.");
			LOGGER.error(sb.toString());

			throw new FpztException(Integer.parseInt(INV_TAXCODE_FAILURE.toString()), sb.toString());
		}
		final RESPONSE_FPKJ returnInfo;

		GlobalInfo globalInf = (GlobalInfo) map.get(XmlPar.GLOBALINFO);
		returnInfo = (RESPONSE_FPKJ) EIProtocolFactory.getDataRoot(data1.getContent()).get(0);
		//返回的数据不能为空
        if (returnInfo == null) {
            LOGGER.error("(发票赋码)此发票{}返回的returninfo为空.", invoiceId);

            throw new FpztException(FpztConstants.JCISNULL,
                    FpztConstants.getFpzt(FpztConstants.JCISNULL));
        }
        
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("(发票赋码)税纳人{}, 获取到的returninfo信息:{}",taxpayerIdentifyNo, returnInfo.toString());
        }

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("(发票赋码)WebService返回结果, ReturnCode: {}",returnInfo.getRETURNCODE());
            LOGGER.debug("(发票赋码)WebService返回结果, ReturnMessage: {}",returnInfo.getRETURNCODE());
        }
		//判断返回的发票代码和号码是否为空,如果为空就是失败数据,不为空则为成功数据
		if (!isNullOrEmpty(returnInfo.getFP_DM())&& !isNullOrEmpty(returnInfo.getFP_HM())) {

			String kprq = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new SimpleDateFormat("yyyyMMddHHmmss").parse(returnInfo
							.getKPRQ().toString())).toString();
			invoiceEntity.setFjh(globalInf.getFjh());
			invoiceEntity.setJqbh(globalInf.getJqbh());
			invoiceEntity.setInvoiceCode(returnInfo.getFP_DM());
			invoiceEntity.setInvoiceNo(returnInfo.getFP_HM());
			invoiceEntity.setInvoiceSerialNo(returnInfo.getFP_DM() + returnInfo.getFP_HM());
			invoiceEntity.setInfoAmount(Double.valueOf(returnInfo.getHJBHSJE()));
			invoiceEntity.setInfoTaxAmount(Double.valueOf(returnInfo.getHJSE()));
			invoiceEntity.setInfoDate(kprq);
			invoiceEntity.setInfoMonth(returnInfo.getSSYF().length()>4?returnInfo.getSSYF().substring(4):returnInfo.getSSYF());
			invoiceEntity.setGoodsListFlag(returnInfo.getXHQDBZ());
			invoiceEntity.setRetCode(returnInfo.getRETCODE());
			invoiceEntity.setCiphertext(returnInfo.getFWMW());
			invoiceEntity.setTwoDimensionCode(returnInfo.getEWM());
			invoiceEntity.setCheckCode(returnInfo.getJYM());
			invoiceEntity.setInfoInvoicer(returnInfo.getSZQM());
			invoiceEntity.setInvoiceKindCode(request_fpkj.getFPZL_DM());
			invoiceEntity.setGoodsList(request_fpkj.getXHQD());

			// 将balance返回的发票信息信息(发票号码，发票代码，发票种类，开票日期)持久化
			invoiceDataAccessManagerService.saveInvoiceEntityBalanceInfo(invoiceEntity);
			// 修改发票赋码状态2100
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("(发票赋码)发票{}, 获取发票赋码结果成功：开票服务器返回的状态{}，message{}......",invoiceId, returnInfo.getRETURNCODE(),
						returnInfo.getRETURNMESSAGE());
			}
			return invoiceDataAccessManagerService.updateInvoiceTaxCodeState(invoiceId, INV_TAXCODE_SUCCESS.getParameterValue());
		} else {
			LOGGER.error("(发票赋码)发票{}, 返回的代码号码为空开票服务器返回的状态{}，message{}......",invoiceId, returnInfo.getRETURNCODE(),
					returnInfo.getRETURNMESSAGE());
			String str ="开具红票时的蓝票不存在,或蓝票的合计金额为空";
			if(("1500".equals(returnInfo.getRETURNCODE())) && (returnInfo.getRETURNMESSAGE().indexOf(str) != -1)){
				
				boolean flag =	invoiceDataAccessManagerService.updateInvoiceBillingType(invoiceId,"9");
				if(flag){
					
					LOGGER.error("(发票赋码)发票{}, 在开票业务库存在蓝票,底层arm库未找到对应蓝票,开票类型置为9......",invoiceId);
				}else{
					LOGGER.error("(发票赋码)发票{}, 在开票业务库存在蓝票,底层arm库未找到对应蓝票,开票类型置为9未成功......",invoiceId);
					
				}
			}
			throw new FpztException(FpztConstants.TAXCODE_BALANCE_RETURN_FAIL,FpztConstants.getFpzt(FpztConstants.TAXCODE_BALANCE_RETURN_FAIL)
							+ returnInfo.getRETURNMESSAGE());
		}


	}
	
	private void zctaxCodeHeader(Long invoiceId, String taxpayerIdentifyNo,
            InvoiceEntity invoiceEntity,
            InvoiceHeaderEntity invoiceHeaderEntity, REQUEST_COMMON_FPKJ request_fpkj,String versionNo)
            throws FpztException, UnsupportedEncodingException {
        // 协议Bean

        /*
         * 
         * 根据发票开具的id，获取明细数据
         */
	    COMMON_FPKJ_FPT common_fpkj_fpt = new COMMON_FPKJ_FPT();
        EInvoiceQueryCondition condition = new EInvoiceQueryCondition();
        condition.setInvoiceId(invoiceId);
        condition.setTaxpayerIdentifyNo(taxpayerIdentifyNo);
        final List<InvoiceDetailEntity> fpkjmx = invoiceDataAccessManagerService
                .queryInvoiceDetailInfo(condition);
        final COMMON_FPKJ_XMXX[] fpkjmxs = new COMMON_FPKJ_XMXX[fpkjmx.size()];
        if (fpkjmx != null) {
        	if("1".equals(versionNo)){
        		for (int i = 0; i < fpkjmx.size(); i++) {
                    final InvoiceDetailEntity eachBean = fpkjmx.get(i);
                    final COMMON_FPKJ_XMXX fpkjmxxx = new COMMON_FPKJ_XMXX();
                    fpkjmxxx.setXMMC(CharUtil.replaceX(eachBean.getItemName()));
//                    fpkjmxxx.setSM(eachBean.getListTaxItem());
                    fpkjmxxx.setSL(String.valueOf(Double.parseDouble(eachBean
                            .getInfoTaxRate().toString().replaceAll("\\%", "")) / 100));
                    fpkjmxxx.setGGXH(CharUtil.replaceX(eachBean.getSpecificationModel()));
                    fpkjmxxx.setDW(eachBean.getListUnit());
                    fpkjmxxx.setXMSL(String.valueOf(eachBean.getItemCount()));
                    fpkjmxxx.setXMDJ(String.valueOf(eachBean.getItemUnitCost()));
                    fpkjmxxx.setXMJE(String.valueOf(eachBean.getItemAmount()));
                    fpkjmxxx.setFPHXZ(eachBean.getInvoiceLineProperty());
                    fpkjmxxx.setSE(String.valueOf(eachBean.getListTaxAmount()));
                    if (String.valueOf(eachBean.getItemName()).startsWith("折扣行数") || String.valueOf(eachBean.getItemName()).startsWith("折扣(")||String.valueOf(eachBean.getInvoiceLineProperty()).equals("1")) {
                        fpkjmxxx.setGGXH("");
                        fpkjmxxx.setDW("");
                        fpkjmxxx.setXMSL("");
                        fpkjmxxx.setXMDJ("");
                    }
                    fpkjmxs[i] = fpkjmxxx;
        		}
        	}else{
        		for (int i = 0; i < fpkjmx.size(); i++) {
                    final InvoiceDetailEntity eachBean = fpkjmx.get(i);
                    final COMMON_FPKJ_XMXX fpkjmxxx = new COMMON_FPKJ_XMXX();
                    fpkjmxxx.setXMMC(CharUtil.replaceX(eachBean.getItemName()));
//                    fpkjmxxx.setSM(eachBean.getListTaxItem());
                    fpkjmxxx.setSL(String.valueOf(Double.parseDouble(eachBean
                            .getInfoTaxRate().toString().replaceAll("\\%", "")) / 100));
                    fpkjmxxx.setGGXH(CharUtil.replaceX(eachBean.getSpecificationModel()));
                    fpkjmxxx.setDW(eachBean.getListUnit());
                    fpkjmxxx.setXMSL(String.valueOf(eachBean.getItemCount()));
                    fpkjmxxx.setXMDJ(String.valueOf(eachBean.getItemUnitCost()));
                    fpkjmxxx.setXMJE(String.valueOf(eachBean.getItemAmount()));
                    fpkjmxxx.setFPHXZ(eachBean.getInvoiceLineProperty());
                    fpkjmxxx.setSE(String.valueOf(eachBean.getListTaxAmount()));
                    if (String.valueOf(eachBean.getItemName()).startsWith("折扣行数") || String.valueOf(eachBean.getItemName()).startsWith("折扣(")||String.valueOf(eachBean.getInvoiceLineProperty()).equals("1")) {
                        fpkjmxxx.setGGXH("");
                        fpkjmxxx.setDW("");
                        fpkjmxxx.setXMSL("");
                        fpkjmxxx.setXMDJ("");
                    }
                    //新增商品编码明细字段 2017年3月6日 21:00 宋雪冬
                    fpkjmxxx.setSPBM(eachBean.getGoodsCode());
                    fpkjmxxx.setZXBM(eachBean.getSelfCoding());
                    fpkjmxxx.setYHZCBS(eachBean.getPreferentialMarking());
                    fpkjmxxx.setLSLBS(eachBean.getZeroTariff());
                    fpkjmxxx.setZZSTSGL(eachBean.getSpecialManagement());
                    fpkjmxxx.setKCE(eachBean.getDeductions());
                    fpkjmxs[i] = fpkjmxxx;
        		}
        		
        	}
            
        } else {
            LOGGER.error("（发票赋码）发票明细信息查询，发票明细信息为空！");
            throw new FpztException(FpztConstants.TAXCODE_FPKJMX_FAIL,
                    FpztConstants.getFpzt(FpztConstants.TAXCODE_FPKJMX_FAIL));
        }
        //组织报文头
        common_fpkj_fpt.setFPQQLSH(invoiceHeaderEntity.getInvoiceRequestSerialNo());
        if ("1".equals(invoiceEntity.getBillingType().toString())) {
            common_fpkj_fpt.setKPLX("0");
        } else {
            common_fpkj_fpt.setKPLX("1");
        }
        common_fpkj_fpt.setXSF_NSRSBH(taxpayerIdentifyNo);
        common_fpkj_fpt.setXSF_MC(CharUtil.replaceX(invoiceHeaderEntity.getTaxpayer()));
        common_fpkj_fpt.setXSF_DZDH(CharUtil.replaceX(invoiceHeaderEntity.getInfoSellerAddressPhone()));
        common_fpkj_fpt.setXSF_YHZH(CharUtil.replaceX(invoiceHeaderEntity.getInfoSellerBankAccount()));
        common_fpkj_fpt.setGMF_NSRSBH(invoiceHeaderEntity.getBuyerTaxpayerIdentifyNo());
        common_fpkj_fpt.setGMF_MC(CharUtil.replaceX(invoiceHeaderEntity.getBuyerName()));
        common_fpkj_fpt.setGMF_DZDH(CharUtil.replaceX(invoiceHeaderEntity.getInfoClientAddressPhone()));
        common_fpkj_fpt.setGMF_YHZH(CharUtil.replaceX(invoiceHeaderEntity.getInfoClientBankAccount()));
        common_fpkj_fpt.setKPR(CharUtil.replaceX(invoiceHeaderEntity.getBillingStaff()));
        common_fpkj_fpt.setSKR(CharUtil.replaceX(invoiceHeaderEntity.getCashier()));
        common_fpkj_fpt.setFHR(CharUtil.replaceX(invoiceHeaderEntity.getInfoChecker()));
        common_fpkj_fpt.setYFP_DM(invoiceEntity.getOldInvoiceCode());
        common_fpkj_fpt.setYFP_HM(invoiceEntity.getOldInvoiceNo());
//        common_fpkj_fpt.setJSHJ(String.valueOf(invoiceHeaderEntity.getInfoAmount() + invoiceHeaderEntity.getInfoTaxAmount()));
        common_fpkj_fpt.setJSHJ(String.valueOf(invoiceHeaderEntity.getBillingAmount()));
        common_fpkj_fpt.setHJJE(String.valueOf(invoiceHeaderEntity.getInfoAmount()));
        common_fpkj_fpt.setHJSE(String.valueOf(invoiceHeaderEntity.getInfoTaxAmount()));
        common_fpkj_fpt.setBZ(CharUtil.replaceX(invoiceHeaderEntity.getMemo()));
        //新增商品编码明细字段 2017年3月6日 21:00 宋雪冬
        common_fpkj_fpt.setBMB_BBH(versionNo.equals("1")?null:invoiceHeaderEntity.getInfoSellerVersion());			 //编码表版本号
        request_fpkj.setCOMMON_FPKJ_FPT(common_fpkj_fpt);
        request_fpkj.setCOMMON_FPKJ_XMXXS(fpkjmxs);
        
        
    }

    private boolean zctaxCodeFoot(Long invoiceId, String responseMessage,
            String taxpayerIdentifyNo, InvoiceEntity invoiceEntity,
            REQUEST_COMMON_FPKJ request_fpkj) throws Exception {

        
        
        final RESPONSE_COMMON_FPKJ returnInfo;
        XStream stream = new XStream(new DomDriver());
        stream.alias("business", TaxcodeResponse.class);
        TaxcodeResponse res = (TaxcodeResponse)stream.fromXML(responseMessage.replace("class", "class1"));
        returnInfo = res.getRESPONSE_COMMON_FPKJ();
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("(发票赋码)WebService返回结果, ReturnCode: {}",
                    returnInfo.getRETURNCODE());
            LOGGER.debug("(发票赋码)WebService返回结果, ReturnMessage: {}",
                    returnInfo.getRETURNCODE());
        }

        if (!isNullOrEmpty(returnInfo.getFP_DM())
                && !isNullOrEmpty(returnInfo.getFP_HM())) {

            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("(发票赋码)税纳人{}, 获取到的returninfo信息:{}",
                        taxpayerIdentifyNo, returnInfo.toString());
            }

            if (returnInfo == null) {
                LOGGER.error("(发票赋码)此发票{}返回的returninfo为空.", invoiceId);

                throw new FpztException(FpztConstants.JCISNULL,
                        FpztConstants.getFpzt(FpztConstants.JCISNULL));
            }

            // String kprq = new
            // SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new
            // SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy",
            // Locale.US)
            // .parse(returnInfo.getKPRQ()));
            String kprq = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(
                    new SimpleDateFormat("yyyyMMddHHmmss").parse(returnInfo
                            .getKPRQ().toString())).toString();
            invoiceEntity.setJqbh(returnInfo.getJQBH());
            invoiceEntity.setInvoiceCode(returnInfo.getFP_DM());
            invoiceEntity.setInvoiceNo(returnInfo.getFP_HM());
            invoiceEntity.setInfoDate(kprq);
            invoiceEntity.setCiphertext(returnInfo.getFP_MW());
            invoiceEntity.setCheckCode(returnInfo.getJYM());
            if ("iVBORw0KGgoAAAANSUhEUgAAAIsAAACLCAIAAADee/2o/////0lEQVR42g==".equals(returnInfo.getEWM())) {
                LOGGER.error("erweimawenti"+"invoiceId:"+invoiceId+"返回报文为:"+responseMessage);
            }
            invoiceEntity.setTwoDimensionCode(returnInfo.getEWM());
            invoiceEntity.setInvoiceSerialNo(returnInfo.getFP_DM() + returnInfo.getFP_HM());
            invoiceEntity.setRetCode(returnInfo.getRETURNCODE());
//            if ("2".equals(invoiceEntity.getBillingType().toString())) {
//                StringBuffer bz = new StringBuffer();
//                bz.append("对应正数发票代码:")
//                        .append(invoiceEntity.getOldInvoiceCode())
//                        .append("号码:").append(invoiceEntity.getOldInvoiceNo());
//                invoiceEntity.setBz(bz.toString());
//            } else {
////                invoiceEntity.setBz(returnInfo.getRETURNMSG());
//            }

            // 将balance返回的发票信息信息(发票号码，发票代码，发票种类，开票日期)持久化
            invoiceDataAccessManagerService.saveInvoiceEntityBalanceInfo(invoiceEntity);
            // 修改发票赋码状态2100

            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(
                        "(发票赋码)发票{}, 获取发票赋码结果成功：balance返回的状态{}，message{}......",
                        invoiceId, returnInfo.getRETURNCODE(),
                        returnInfo.getRETURNMSG());
            }
            return invoiceDataAccessManagerService.updateInvoiceTaxCodeState(
                    invoiceId, INV_TAXCODE_SUCCESS.getParameterValue());
        } else {
            LOGGER.error("(发票赋码)发票{}, 返回的代码号码为空balance返回的状态{}，message{}......",
                    invoiceId, returnInfo.getRETURNCODE(),
                    returnInfo.getRETURNMSG());
            throw new FpztException(FpztConstants.TAXCODE_BALANCE_RETURN_FAIL,
                    FpztConstants
                            .getFpzt(FpztConstants.TAXCODE_BALANCE_RETURN_FAIL)
                            + returnInfo.getRETURNMSG());
        }


    }
	
	/**
     * 发票赋码
     * 
     * @param invoiceHeaderEntity
     *            发票头实体
     * @return 数据库执行是否成功
     * @throws Exception
     */
    @Override
    public Boolean zctaxcodeInvoice(final InvoiceHeaderEntity invoiceHeaderEntity)
            throws Exception {
        Boolean result = false;
        // 发票实体
        final InvoiceEntity invoiceEntity = invoiceHeaderEntity.getInvoiceEntity();
        // 发票ID
        final Long invoiceId = invoiceEntity.getInvoiceId();
        // 税纳人识别号(NSRSBH)
        final String taxpayerIdentifyNo = invoiceEntity.getTaxpayerIdentifyNo();

        // 验证签章ID是否已经配置，如果没有配置修改发票记录错误日志
        final Boolean verifyResult = invoiceDataAccessManagerService
                .verifyIssueInvoice(invoiceEntity);
        if (!verifyResult) {
            LOGGER.error("(发票赋码)签章ID未配置,发票赋码失败.");

            throw new FpztException(FpztConstants.TAXCODE_FAIL,FpztConstants.getFpzt(FpztConstants.TAXCODE_FAIL));
        }

        // 验证当前发票是否已经拥有结存信息
        final InvoiceEntity queryInvoiceEntity = invoiceDataAccessManagerService.getInvoiceEntity(invoiceId);

//        if (!isNullOrEmpty(queryInvoiceEntity.getInvoiceCode()) && !isNullOrEmpty(queryInvoiceEntity.getInvoiceNo())) {
//
//            // 已经持久化过“发票号码等信息”
//            invoiceEntity.setBillingDate(queryInvoiceEntity.getBillingDate());
//            invoiceEntity.setInvoiceKindCode(queryInvoiceEntity.getInvoiceKindCode());
//            invoiceEntity.setInvoiceCode(queryInvoiceEntity.getInvoiceCode());
//            invoiceEntity.setInvoiceNo(queryInvoiceEntity.getInvoiceNo());
//            invoiceEntity.setInfoAmount(Double.valueOf(queryInvoiceEntity.getInfoAmount()));
//            invoiceEntity.setInfoTaxAmount(Double.valueOf(queryInvoiceEntity.getInfoTaxAmount()));
//            invoiceEntity.setInfoDate(queryInvoiceEntity.getInfoDate());
//            invoiceEntity.setInfoMonth(queryInvoiceEntity.getInfoMonth());
//            invoiceEntity.setGoodsListFlag(queryInvoiceEntity.getGoodsListFlag());
//            invoiceEntity.setRetCode(queryInvoiceEntity.getRetCode());
//            invoiceEntity.setCiphertext(queryInvoiceEntity.getCiphertext());
//            invoiceEntity.setCheckCode(queryInvoiceEntity.getCheckCode());
//            invoiceEntity.setInfoInvoicer(queryInvoiceEntity.getInfoInvoicer());
//            invoiceEntity.setBz(queryInvoiceEntity.getBz());
//            invoiceHeaderEntity.setMemo(queryInvoiceEntity.getBz());
//            invoiceEntity.setInvoiceSerialNo(queryInvoiceEntity.getInvoiceCode() + queryInvoiceEntity.getInvoiceNo());
//            result = true;
//            invoiceDataAccessManagerService.updateInvoiceTaxCodeState(invoiceId, INV_TAXCODE_SUCCESS.getParameterValue());
//
//        } else {

            // 组织报文协议头
            String xmlhead="<?xml version=\"1.0\" encoding=\"gbk\"?><business id=\"FPKJ\" comment=\"发票开具\">";
            
            String xmlfoot="</business>";

            final REQUEST_COMMON_FPKJ request_fpkj =new REQUEST_COMMON_FPKJ();
            final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            try {
                final DateTime begin11 = now();
                
                final TaxcodeRouteEntity taxcodeRoute = invoiceDataAccessManagerService.queryTaxcodeRouteInfo(taxpayerIdentifyNo);

                String taxpayerUrlLp = null; 
                String taxpayerUrlHp = null;
				if (!StringUtils.isNullOrEmpty(taxcodeRoute.getLpurl()) && !StringUtils.isNullOrEmpty(taxcodeRoute.getHpurl())) {

					 taxpayerUrlLp = taxcodeRoute.getLpurl();
					 taxpayerUrlHp = taxcodeRoute.getHpurl();
				} else {
					LOGGER.error("发票赋码路由表没有配置！");
					throw new FpztException(FpztConstants.TAXCODE_FAIL,"发票赋码路由表没有配置！");
				}
                
                // 组织发票赋码业务的请求信息
				//形参部分传递versionNo的值  2017年3月6日 21:00 宋雪冬
                zctaxCodeHeader(invoiceId, taxpayerIdentifyNo, invoiceEntity,
                        invoiceHeaderEntity, request_fpkj,taxcodeRoute.getVersionNo());

                String testcard = SystemConfig.TESTCARD;
                if ("y".equals(testcard)) {
                    invoiceEntity.setFjh("1");
                    invoiceEntity.setJqbh("661505060904");
                    invoiceEntity.setTwoDimensionCode("Qk1+BgAAAAAAAD4AAAAoAAAAZAAAAGQAAAABAAEAAAAAAEAGAAAAAAAAAAAAAAAAAAACAAAAAAAA///////////////////////wAAAA////////////////8AAAAP////////////////AAAAD////////////////wAAAA////////////////8AAAAP////////////////AAAAD////////////////wAAAA////////////////8AAAAP////////////////AAAAD////////////////wAAAA////////////////8AAAAP////////////////AAAAD////////////////wAAAA////////////////8AAAAP////////////////AAAAD////////////////wAAAA////////////////8AAAAP////////////////AAAAD////////////////wAAAA////////////////8AAAAP////////////////AAAAD////////////////wAAAA////////////////8AAAAP////////////////AAAAD////////////////wAAAA////////////////8AAAAAADPAAAzPPzwD////AAAAAAAzwAAMzz88A////wAAAAP/P8P8/DM//A////8AAAAD/z/D/PwzP/wP////AAAAAwM8D/DzPMM/D////wAAAAMDPA/w8zzDPw////8AAAADAzw888P8Dz/P////AAAAAwM8PPPD/A8/z////wAAAAMDM8/PDzAwAw////8AAAADAzPPzw8wMAMP////AAAAA/8/DMAPAwPwD////wAAAAP/PwzADwMD8A////8AAAAAADDzAPPD8zAP////AAAAAAAw8wDzw/MwD////wAAAA//8DDP8APD88////8AAAAP//Awz/ADw/PP////AAAAD8MAD/Aw/zAAD////wAAAA/DAA/wMP8wAA////8AAAAAw8PwAAAP8MwD////AAAAAMPD8AAAD/DMA////wAAAAAAAzPMDMMA/A////8AAAAAAAMzzAzDAPwP////AAAADPPwPz8MM8zDD////wAAAAzz8D8/DDPMww////8AAAAM8AwP8/P/MzwD////AAAADPAMD/Pz/zM8A////wAAAAPz8MPDwM8M/D////8AAAAD8/DDw8DPDPw/////AAAAD/AMMA88D8zPD////wAAAA/wDDAPPA/Mzw////8AAAAPz8PwzPPADMPP////AAAAD8/D8MzzwAzDz////wAAAA//M8PADM8zPDP///8AAAAP/zPDwAzPMzwz////AAAADPDMMDw8MwzMD////wAAAAzwzDA8PDMMzA////8AAAAPDA/88PM/wMwP////AAAADwwP/PDzP8DMD////wAAAA8w8wwzw/zMw8////8AAAAPMPMMM8P8zMPP////AAAADPMM8P8/MzM8A////wAAAAzzDPD/PzMzPAP///8AAAAD888PwD8P/PwD////AAAAA/PPD8A/D/z8A////wAAAAwwMADM/88PwA////8AAAAMMDAAzP/PD8AP////AAAAAz/Azw/wAMzAD////wAAAAM/wM8P8ADMwA////8AAAAMwA8DPDD/Mzwz////AAAADMAPAzww/zM8M////wAAAAwD8PDAwA8z8D////8AAAAMA/DwwMAPM/A/////AAAADA8Dz8zMAwD/D////wAAAAwPA8/MzAMA/w////8AAAAPwMPM/w8wzMP/////AAAAD8DDzP8PMMzD/////wAAAAPzAADD8/8wAPP///8AAAAD8wAAw/P/MADz////AAAAD//w8//wz8P//////wAAAA//8PP/8M/D//////8AAAAAADMzMzMzMwAD////AAAAAAAzMzMzMzMAA////wAAAAP/MD88w/A/P/P///8AAAAD/zA/PMPwPz/z////AAAAAwMz//zzAM8wM////wAAAAMDM//88wDPMDP///8AAAADAzAAD/MwzzAz////AAAAAwMwAA/zMM8wM////wAAAAMDPDDA/DM/MDP///8AAAADAzwwwPwzPzAz////AAAAA/888/APwDM/8////wAAAAP/PPPwD8AzP/P///8AAAAAADPDM8P8zwAD////AAAAAAAzwzPD/M8AA////wAAAA");
                    invoiceEntity.setInvoiceCode("114011015609");
                    invoiceEntity
                            .setInvoiceNo(String.format("%08d", invoiceId));
                    invoiceEntity.setInvoiceSerialNo("114011015609"
                            + String.format("%08d", invoiceId));
                    invoiceEntity.setInfoAmount(Double.valueOf(invoiceEntity
                            .getInfoAmount()));
                    invoiceEntity.setInfoTaxAmount(Double.valueOf(invoiceEntity
                            .getInfoTaxAmount()));
                    invoiceEntity.setInfoDate(new SimpleDateFormat(
                            "yyyy-MM-dd HH:mm:ss").format(new Date()));
                    invoiceEntity.setInfoMonth("");
                    invoiceEntity.setGoodsListFlag("0");
                    invoiceEntity.setRetCode("4011");
                    invoiceEntity
                            .setCiphertext("6134*3>950+75<8/1208-85*</*14>-65-142/+7390*24855672678136749+92184+/8*553-9-8-+5*</*1/670433467<<3<0*24<4*6");
                    invoiceEntity.setCheckCode("65560891271390877510");
                    invoiceEntity
                            .setInfoInvoicer("+Iz43YnOX/LX3+OYplTn1hgCTTbLjbbefgaplmsD0p7EgIxU3yGvJyURK4ndVpk9/cVivoyx3yIyKot3cS6N9ZClI1R9NgwHhTwciDFjfihaRF94wICl");
                    invoiceEntity.setInvoiceKindCode("2");
                    invoiceEntity.setGoodsList("");

                    // 将balance返回的发票信息信息(发票号码，发票代码，发票种类，开票日期)持久化
                    invoiceDataAccessManagerService
                            .saveInvoiceEntityBalanceInfo(invoiceEntity);
                    return invoiceDataAccessManagerService
                            .updateInvoiceTaxCodeState(invoiceId,
                                    INV_TAXCODE_SUCCESS.getParameterValue());
                } else {
                    // 生成XML(第二层报文)
                    XMLShellFactory.newInstance().saveXml(outputStream,request_fpkj);
                    String reqcon = new String(outputStream.toByteArray(), StaticConstant.CHARSET);
                    if (!isNullOrEmpty(reqcon) && !reqcon.equals("null")) {
                        reqcon = reqcon.substring(reqcon.indexOf("<ROOT>") + 6, reqcon.lastIndexOf("</ROOT>"));
                    } else {
                        reqcon = "";
                    }
                    String reqXml = xmlhead + reqcon + xmlfoot;

                    final Long millSeconds11 = new Duration(begin11, now())
                            .getMillis();
                    if (LOGGER.isDebugEnabled()) {
                        LOGGER.debug("(发票赋码)第二层报文Content:{}", reqXml);
                        LOGGER.debug("(发票赋码)发票{}组织第二层报文用时{}毫秒......",
                                invoiceId, millSeconds11);
                    }
                   


                    final DateTime begin2 = now();

//                    String a = "<?xml version=\"1.0\" encoding=\"gbk\"?><business id=\"FPKJ\" comment=\"发票开具\"><RESPONSE_COMMON_FPKJ class=\"RESPONSE_COMMON_FPKJ\"><RETURNCODE>0001</RETURNCODE><RETURNMSG>价税合计不等于合计金额+合计税额</RETURNMSG></RESPONSE_COMMON_FPKJ></business>";
//                    String b = "<?xml version=\"1.0\" encoding=\"gbk\"?><business id=\"FPKJ\" comment=\"发票开具\"><RESPONSE_COMMON_FPKJ class=\"RESPONSE_COMMON_FPKJ\"><RETURNCODE>0001</RETURNCODE><RETURNMSG>第1行的SL不正确，税率只能为0或0.03或0.04或0.06或0.11或0.13或0.17</RETURNMSG></RESPONSE_COMMON_FPKJ></business>";
//                    String c = "<?xml version=\"1.0\" encoding=\"gbk\"?><business id=\"FPKJ\" comment=\"发票开具\"><RESPONSE_COMMON_FPKJ class=\"RESPONSE_COMMON_FPKJ\"><RETURNCODE>0001</RETURNCODE><RETURNMSG>开具合计金额不等于明细行合计金额！</RETURNMSG></RESPONSE_COMMON_FPKJ></business>";
//                    String d = "<?xml version=\"1.0\" encoding=\"gbk\"?><business id=\"FPKJ\" comment=\"发票开具\"><RESPONSE_COMMON_FPKJ class=\"RESPONSE_COMMON_FPKJ\"><RETURNCODE>0001</RETURNCODE><RETURNMSG>红票金额必须为负数！</RETURNMSG></RESPONSE_COMMON_FPKJ></business>";
//                    
//                    String e = "<?xml version=\"1.0\" encoding=\"gbk\"?>"+
//"<business id=\"FPKJ\" comment=\"发票开具\">"+
//    "<RESPONSE_COMMON_FPKJ class=\"RESPONSE_COMMON_FPKJ\">"+
//        "<RETURNCODE>0000</RETURNCODE>"+
//        "<RETURNMSG>开票成功</RETURNMSG>"+
//        "<FPQQLSH>00000000001</FPQQLSH>"+
//        "<JQBH>499099900020</JQBH>"+
//        "<FP_DM>111001490026</FP_DM>"+
//        "<FP_HM>00000011</FP_HM>"+
//        "<KPRQ>20150717104946</KPRQ>"+
//        "<FP_MW><![CDATA[035<-07+3<6-273-745-/3753>>212<**96+7767226</<-2/2*<-</>96+8<-126>6/+1-88<926*1/<86-9078-6/57000906>192<7441-*7>]]></FP_MW>"+
//        "<JYM>05523224229431727977</JYM>"+
//        "<EWM>iVBORw0KGgoAAAANSUhEUgAAAIsAAACLCAIAAADee/2oAAADtklEQVR42u3bUXasIBBFUec/aTOApJdQ91Sp6eNnnvEB2yVQlxyn17OvwyF4h9CRXX8899c/rdy89V+sPKc4KDv9WulyMqoKKaQQK0T1ZKWVoVB4z9Y7hHOuP1AhhRRqEtoa2dqIUPNHbYYLp0x8ZlJIIYVeKhSupMOXYOvXa/MiNT4KKaTQ24WoFTm1Y++rDiikkEKvEKotc/s26rUnh+vmvm3AbVUfhRT6eqHwU+5Pnp7g+ROFvlUIO5hCp9e1UmyYYE2mQatnfRRSSKFMiNpFh4WDvq88vhSmKrCXzVBIIYXmhcJjN+ExoLASQb0EY/ULhRRSCBEKRy383FPnb/A21+4Jay4X+yGFFFKoJBTmOlSyEg4xtbbGyZO3UyGFFGoSCteyYe0yjKjD7vRVIgqDqZBCCo0JUVMU1ROqElGbDgemOoUUUggU6ss/+mDCgcBrJU0Tm0IKKYQIhWXEcKcdSvcF5LeEZAoppFCrEL6ZDz/l1Dw0mUVR5RiFFFIIFKLqgNQ6dbI4S9VkmxI1hRRSCBEK98PhmRhqAU3h4a9pUslVSCGFEKEw6cFTHJwBr2j0BVf1lYJCCin0WeigrzO7wq98eHgoDMjDZqxWfRRSSKFbM9YwQ0L6dnJ/TkwVQwsvikIKKcQKUadbkAAYTKfweYhqT32loJBCCu0IUYVFajuNz3kDcRcOrJBCCnUI4Zt5vNt4M/C4qzcfUkghhWihWxpHDR9VUwiz/OQehRRSCBHCwxLq1A5ejqTiLmrGveypQgophAhRMwo1AYQvwWShk2rhp+copJBCrFDf2RpqQY+XYmvbgDDvX2+PQgopNC9EdTKcovqGL4z5w8MBqwmeQgoptCM0UBSgqgNUJRc/9NP0eimkkEKIEF4MxRnO0oXnQ+G2pDAaCimkECJEZcz4ljskx4sUfbnX6kpBIYUUKglR52bCPKavyhDOQ2EWlbxMCimkUJNQuEoOf4tamuOlBCpVWn/vFVJIIVYI3xiHH25qjY6HQH1VWuA0lkIKKVTKh6jwhup2H1W406AORSmkkEKgEHXV+oakxeX2UEXVsPFATUEhhRTayYf6wt1bIp+wfkFVTwqVXIUUUogVChMaCo/qbS3dr02reH6mkEIKtQpRB1aoU0QDJdS+nUayCVFIIYX+gRBerqXydarckMymCimk0FuE7i19UmvigTlYIYUUmhGi4pxaZB7eTJVQqReuNq0qpJBCHUL4SRrkW5zb95V9Q9fLJyukkEKIkNdjL4Wefv0AeVHfqRlqooMAAAAASUVORK5CYII=</EWM>"+
//    "</RESPONSE_COMMON_FPKJ> "+
//"</business>";
                    
                    String responseMessage = null;
                    for (int i = 1; i < SystemConfig.default_retry_count; i++) {
                        final DateTime mill1 = now();
                        if ("1".equals(invoiceEntity.getBillingType()
                                .toString())) {
//                            responseMessage = HttpClientUtil.postSubmit(
//                                    taxpayerUrlLp, reqXml);
                            responseMessage = webServiceClient.webclientData("doService", reqXml,
                            		taxpayerUrlLp);
                        } else {
//                            responseMessage = HttpClientUtil.postSubmit(
//                                    taxpayerUrlHp, reqXml);
                            responseMessage = webServiceClient.webclientData("doService", reqXml,
                            		taxpayerUrlHp);
                        }
                        final Long mill2 = new Duration(mill1, now())
                                .getMillis();
                        LOGGER.debug("请求COM组件的时间------" + mill2);

                        if (!isNullOrEmpty(responseMessage)) {

                            if (LOGGER.isDebugEnabled()) {
                                LOGGER.debug("从企业返回的消息:{}", responseMessage);
                            }

                            break;
                        }

                        LOGGER.error(
                                "(发票赋码)发票{}推送第{}次失败: balance服务接口返回为空......",
                                invoiceId, i);
                    }

                    final Long millSeconds2 = new Duration(begin2, now())
                            .getMillis();
                    if (LOGGER.isDebugEnabled()) {
                        LOGGER.debug("(发票推送)电商平台{}调用电商接口用时{}毫秒......",
                                invoiceHeaderEntity.getEshopCode(),
                                millSeconds2);
                    }

                    // 返回结果为空处理
                    if (isNullOrEmpty(responseMessage)) {
                        final StringBuilder sb = new StringBuilder("");
                        sb.append("发票:");
                        sb.append(invoiceId);
                        sb.append(",赋码失败:");
                        sb.append("balance服务接口返回空数据.");
                        LOGGER.error(sb.toString());

                        throw new FpztException(FpztConstants.JDJKSB,
                                sb.toString());
                    }
                    
                    // 处理返回的信息
                    result = zctaxCodeFoot(invoiceId, responseMessage,taxpayerIdentifyNo, invoiceEntity, request_fpkj);
                }
            } finally {
                LOGGER.info("(发票推送)关闭输出流.");
                outputStream.close();
            }
//        }

        return result;
    }
public static void main(String[] args) {
    Double s = 12.12D;
    Double s2 = 17.89D;
    System.out.println(String.valueOf(s+s2));
}

}
