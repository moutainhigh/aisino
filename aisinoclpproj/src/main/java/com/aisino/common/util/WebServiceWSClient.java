package com.aisino.common.util;

import static org.joda.time.DateTime.now;

import java.io.ByteArrayOutputStream;
import java.net.URL;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.codehaus.xfire.client.Client;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.aisino.cxf.client.IWebServiceClient;
import com.aisino.cxf.client.ReceiveShangHai;
import com.aisino.domain.SystemConfig;

public class WebServiceWSClient implements IWebServiceClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebServiceWSClient.class);

    public String beanToXml(Object obj) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        XMLShellFactory.newInstance().saveXml(out, obj);
        Data data = ProXml.getData(out);
        return data.getContent();
    }

    public GlobalInfoPush assemblingGlobalInfopush(String interfaceCode, String dsptbm, String dsptbmPwd, String taxpayerIdentifyNo) {
//        final String changeId = UUID.randomUUID().toString().replace("-", "").substring(0, 9);

        final GlobalInfoPush globalInfo = new GlobalInfoPush();

        globalInfo.setTerminalCode("0");//终端类型标识代码 0=B/S请求来源
        globalInfo.setAppId(XmlPar.DZFP);
        globalInfo.setInterfaceCode(interfaceCode);
        globalInfo.setVersion("2.0");//接口版本
        globalInfo.setUserName(taxpayerIdentifyNo);
        globalInfo.setPassWord(dsptbmPwd);
        globalInfo.setTaxpayerId(taxpayerIdentifyNo);
        globalInfo.setAuthorizationCode("");//纳说人授权码暂空
        globalInfo.setRequestCode(dsptbm);
        globalInfo.setRequestTime(now().toString(SystemConfig.long_millisecond_date_format));
        globalInfo.setResponseCode("");

        return globalInfo;
    }
    
    public GlobalInfo assemblingGlobalInfo(String interfaceCode, String dsptbm, String dsptbmPwd, String taxpayerIdentifyNo) {
//      final String changeId = UUID.randomUUID().toString().replace("-", "").substring(0, 9);

      final GlobalInfo globalInfo = new GlobalInfo();

      globalInfo.setTerminalCode("0");//终端类型标识代码 0=B/S请求来源
      globalInfo.setAppId(XmlPar.DZFP);
      globalInfo.setInterfaceCode(interfaceCode);
      globalInfo.setVersion("1.0");//接口版本
      globalInfo.setUserName(taxpayerIdentifyNo);
      globalInfo.setPassWord(dsptbmPwd);
      globalInfo.setNSRSBH(taxpayerIdentifyNo);
      globalInfo.setSQM("");//纳说人授权码暂空
      globalInfo.setRequestCode(dsptbm);
      globalInfo.setRequestTime(now().toString(SystemConfig.long_millisecond_date_format));
      globalInfo.setResponseCode("");

      return globalInfo;
  }
    public String clientData(String reqMethod, String reqXml, String url) {

        Object[] results;
        Client client = null;
        try {

            final DateTime begin1 = now();

            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("(发票推送)WebServiceClient, URL:{}", url);
            }

            client = new Client(new URL(url));

            results = client.invoke(reqMethod, new Object[]{reqXml});

            LOGGER.debug("(发票推送)WebServiceClient, results:{}", results);

            final Long millSeconds2 = new Duration(begin1, now()).getMillis();
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("(发票推送)WebServiceClient, invoke用时{}毫秒......", millSeconds2);
            }


            if (results != null && results.length > 0 && results[0] != null) {
                return results[0].toString();
            }

        } catch (Exception e) {
            LOGGER.error("未知：" + e);
        } finally {
            if (client != null) {
                client.close();
            }
        }

        return "";
    }
    
    public String webclientData(String reqMethod, String reqXml, String url) {

        Object[] results;
        Client client = null;
        try {

            final DateTime begin1 = now();

            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("(发票推送)WebServiceClient, URL:{}", url);
            }

            client = new Client(new URL(url));

            results = client.invoke(reqMethod, new Object[]{reqXml});

            LOGGER.debug("(发票推送)WebServiceClient, results:{}", results);

            final Long millSeconds2 = new Duration(begin1, now()).getMillis();
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("(发票推送)WebServiceClient, invoke用时{}毫秒......", millSeconds2);
            }


            if (results != null && results.length > 0 && results[0] != null) {
                return results[0].toString();
            }

        } catch (Exception e) {
            LOGGER.error("未知：" + e);
        } finally {
            if (client != null) {
                client.close();
            }
        }

        return "";
    }


}
