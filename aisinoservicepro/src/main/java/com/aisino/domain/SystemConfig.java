package com.aisino.domain;

import org.omg.CORBA.PUBLIC_MEMBER;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by Martin.Ou on 2014/9/15.
 */
public final class SystemConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(SystemConfig.class);

    private SystemConfig() {
    }

    public static String localCharset;
    public static String dbCharset;
    public static String xmlCharset;
    // public static String swjgdm = "";
    // public static int timerIsValible = 1;
    // public static String uri = "";
    public static String qzfw;
    public static int PDF_QZ_ZB_LEFT;
    public static int PDF_QZ_ZB_TOP;
    public static int PDF_QZ_ZB_RIGHT;
    public static int PDF_QZ_ZB_BOTTOM;
    public static int PDF_QZ_ZB_PAGEINDEX;
    //	public static String PDF_QZ_ZB_ID = "";
    public static String PDF_QZ_ZB_IP;
    public static int PDF_QZ_ZB_PROT;

    public static String PDF_QZ_ZB_AISINO_LEFT;
    public static String PDF_QZ_ZB_AISINO_TOP;
    public static String PDF_QZ_ZB_AISINO_RIGTH;
    public static String PDF_QZ_ZB_AISINO_BOTTOM;
    public static String PDF_QZ_ZB_AISINO_PAGEINDEX;
    public static String PDF_QZ_DISJUNCTOR;
    /**
     * 下发成功之后pdf是否删除标志
     */
    public static String PDF_DELETE;

    public static String serverIp = "127.0.0.1";
    public static Integer serverPort = 4000;

    public static String dataCharset = "UTF-8";
    public static byte[] headerBytes = null;
    public static int errorCodeLength = 4;
    public static int headerLength = 3;

    public static String PROTOCOL_ENCRYPTCODE_NONE = "0";
    public static String PROTOCOL_ENCRYPTCODE_3DES = "1";
    public static String PROTOCOL_ENCRYPTCODE_CA = "2";

    public static final String default_date_format = "yyyy-MM-dd HH:mm:ss";
    public static final String short_date_format = "yyyyMMdd";
    public static final String long_date_format = "yyyyMMddHHmmss";
    public static final String long_millisecond_date_format = "yyyy-MM-dd hh:mm:ss:SS";

    //进入MQ队列的机会次数
    public static final Long default_error_count = 4L;
    //调用外部WS接口的机会次数
    public static final Long default_retry_count = 5L;

    public static String commandClass;
    public static String is_commandClass;
    public static String fscodeVersion;
    public static String dcodeVersion;
    public static String swjgbm;
    public static String phone_commandClass;
    // public static String phoneUrl;
    public static String pdfDisjunctor;
    public static String pdfAppend;

    public static String KpScNf;
    public static String VERSION;
    public static String PROTOCOL_APPID;
    public static String PROTOCOL_APPIDTwo;
    public static String PROTOCOL_REQUESTCODE;
    public static String APP_PREFIX = "";

    public static String SL_CA_TRUST;
    public static String SL_CA_PRIVATE_KEY;
    public static String SL_CA_PUBLIC_KEY;
    public static String SL_CA_PASSWORD;

    //开票类型 (1正票 2红票)
    public static final Long invoiceNote = 1L;
    public static final Long creditNote = 2L;

    //冲红类型(是否特殊冲红)
    public static final String specRedInvoiceFlag = "0";

    //份数为1
    public static final String invoiceCopy = "1";

    //电商平台(51)状态可用
    public static final String eshopEnabled = "1";

    //四个定时器的获取发票条数
    public static final Long SIZE_GET_ESHOP_UPLOAD = 1000L;

    public static final String INV_GETDATA_SUCCESS = "1000";

    //税局证书路径
    public static String certificateTaxUrl = "";
    //客户端证书路径
    public static String certificateClientUrl = "";
    
  //发票零税率标识对应需要在PDF文件上显示***的纳税人识别号(处理工程)
    public static String FPKJ_LSLBS_NSRSBH = "";

    //发票推送状态位
    public static final String INVOICE_PUSH_STATE_WAIT = "2000";//未推送
    public static final String INVOICE_PUSH_STATE_SUCCESS = "3000";//成功
    public static final String INVOICE_PUSH_STATE_FAILURE = "3001";//失败
    //(处理工程)
    public static final String INVOICE_UPLOAD_STATE_FAILURE = "2";//上传大象失败状态 -20171009-FWH 
    public static final String INVOICE_UPLOAD_STATE_WAIT = "0";//上传大象未推送状态 -20171009-FWH
    
    public static String TESTCARD = "";
    
    //每次取数据库获取ID的个数
    public static int getIdMxaNum = 2000;
    
    //是否反算折扣率 默认为N（不反算折扣率）
    public static String getDiscountFlag = "N";
    //是否为pdf添加广告
    public static String flagAd = "n";
  //最大开票限额Map，一个税号对应一个最大开票金额(处理工程)
    public static String[] nsrsbhList = null ;
    public static String[] maxAmountList = null ;
    public static Map<String, String> maxLimitMap = new HashMap<String, String>();
    
    //TODO 大象签章地址URL (处理工程)
    public static String jdCloudUrlQZ;
    
  //添加纸质发票系统接口地址;冲红时校验纸票系统对应蓝票是否存在
 //   public static String zzfpUrl;//纸质发票URL
    
    //本地可以特殊冲红的税号
    public static String nsrsbhTSCH;//纳税人识别号特殊冲红
    public static String RushRedWay;//特殊冲红方式 
    
//    public static String switchQZFL;//签章分流开关Y/N
//    public static String newQZID;//新签章ID
//    public static String newQZUrl;//新签章地址
    
    
    static {
        InputStream io = null;
        try {
            Properties properties = new Properties();
            io = SystemConfig.class.getResourceAsStream("/general.properties");
            properties.load(io);

            dbCharset = properties.getProperty("application.conf.db.charset");
            localCharset = properties.getProperty("application.conf.local.charset");
            xmlCharset = properties.getProperty("application.conf.xml.charset");
            qzfw = properties.getProperty("application.qz.qzfw");
            PDF_QZ_ZB_LEFT = Integer.parseInt(properties.getProperty("application.qz.zb.left"));
            PDF_QZ_ZB_TOP = Integer.parseInt(properties.getProperty("application.qz.zb.top"));
            PDF_QZ_ZB_RIGHT = Integer.parseInt(properties.getProperty("application.qz.zb.right"));
            PDF_QZ_ZB_BOTTOM = Integer.parseInt(properties.getProperty("application.qz.zb.bottom"));
            PDF_QZ_ZB_PAGEINDEX = Integer.parseInt(properties.getProperty("application.qz.zb.pageindex"));
            //PDF_QZ_ZB_ID = properties.getProperty("application.qz.zb.Id");
            PDF_QZ_ZB_IP = properties.getProperty("application.qz.zb.Ip");
            PDF_QZ_ZB_PROT = Integer.parseInt(properties.getProperty("application.qz.zb.port"));

            PDF_QZ_ZB_AISINO_LEFT = properties.getProperty("application.qz.zb.aisino.left");
            PDF_QZ_ZB_AISINO_TOP = properties.getProperty("application.qz.zb.aisino.top");
            PDF_QZ_ZB_AISINO_RIGTH = properties.getProperty("application.qz.zb.aisino.right");
            PDF_QZ_ZB_AISINO_BOTTOM = properties.getProperty("application.qz.zb.aisino.bottom");
            PDF_QZ_ZB_AISINO_PAGEINDEX = properties.getProperty("application.qz.zb.aisino.pageindex");

            serverIp = properties.getProperty("socket.serverIp");
            serverPort = Integer.valueOf(properties.getProperty("socket.serverPort").trim()).intValue();
            dataCharset = properties.getProperty("command.charset");
            is_commandClass = properties.getProperty("command.header.is.commandClass");
            phone_commandClass = properties.getProperty("command.header.phone.commandClass");
            TESTCARD = properties.getProperty("testCard");
            //(处理工程)
            FPKJ_LSLBS_NSRSBH= properties.getProperty("application.conf.fpkj.nsrsbh");

            final String headerStr = properties.getProperty("command.header");
            final String[] headerStrList = headerStr.split(",");
            headerBytes = new byte[headerStrList.length];

            for (int i = 0; i < headerStrList.length; i++) {
                String temp = headerStrList[i];
                if ((temp == null) || (temp.trim().equalsIgnoreCase(""))) {
                    headerBytes[i] = 0;
                } else {
                    temp = temp.toLowerCase();
                    if (temp.startsWith("0x")) {
                        temp = temp.substring(2);
                    }

                    headerBytes[i] = Integer.valueOf(temp, 16).byteValue();
                }
            }

            final String temp = properties.getProperty("command.header.error.length");
            if ((temp != null) && (!temp.trim().equalsIgnoreCase("")))
                errorCodeLength = Integer.valueOf(temp.trim()).intValue();

            headerLength = headerBytes.length + 1;
            commandClass = properties.getProperty("command.header.skmewm.commandClass");
            fscodeVersion = properties.getProperty("default.fscodeVersion");
            swjgbm = properties.getProperty("default.swjgbm");
            dcodeVersion = properties.getProperty("default.dcodeVersion");
            //phoneUrl=properties.getProperty("default.phone.url");
            pdfDisjunctor = properties.getProperty("application.pdf.disjunctor");
            pdfAppend = properties.getProperty("application.pdf.append");

            KpScNf = properties.getProperty("fpkjsc.year");
            VERSION = properties.getProperty("VERSION");
            PROTOCOL_APPID = properties.getProperty("application.conf.protocol_appid");
            PROTOCOL_APPIDTwo = properties.getProperty("application.conf.protocol_appidtwo");
            PROTOCOL_REQUESTCODE = properties.getProperty("application.conf.protocol_requestCode");
            APP_PREFIX = properties.getProperty("app_prefix");
            SL_CA_TRUST = properties.getProperty("platform_trusts");
            SL_CA_PRIVATE_KEY = properties.getProperty("platform_decryptpfx");
            SL_CA_PUBLIC_KEY = properties.getProperty("platform_decryptcer");
            SL_CA_PASSWORD = properties.getProperty("platform_decryptpfx_key");

            certificateTaxUrl = properties.getProperty("certificate_tax_url");
            certificateClientUrl = properties.getProperty("certificate_client_url");
            
            PDF_DELETE = properties.getProperty("application.pdf.delete");
            
            getDiscountFlag = properties.getProperty("discount.flag");
            flagAd = properties.getProperty("flag.ad");
            
//          (处理工程)
            final String nsrsbhString = properties.getProperty("max.limit.nsrsbh");
            final String maxAmountString = properties.getProperty("max.limit.amount");

            jdCloudUrlQZ = properties.getProperty("JDQZ.URL");
            
  //          zzfpUrl = properties.getProperty("zzfpUrl");
            
            nsrsbhTSCH = properties.getProperty("nsrsbhTSCH");
            RushRedWay = properties.getProperty("RushRedWay");
            
//            switchQZFL = properties.getProperty("switchQZFL");
//            newQZID = properties.getProperty("newQZID");
//            newQZUrl = properties.getProperty("newQZUrl");
            
           /* nsrsbhList = nsrsbhString.split(",");
            maxAmountList = maxAmountString.split(",");
            if (nsrsbhList.length == maxAmountList.length) {
                for (int j = 0; j < nsrsbhList.length; j++) {
                    maxLimitMap.put(nsrsbhList[j], maxAmountList[j]);
                }
            } else {
                LOGGER.error("最大开票限额配置有误!!!");
            }
           */
        } catch (IOException e) {
            LOGGER.error("未知：", e);
        } finally {
            if (io != null) {
                try {
                    io.close();
                } catch (IOException e) {
                    LOGGER.error("未知：", e);
                }
            }
        }
    }

}