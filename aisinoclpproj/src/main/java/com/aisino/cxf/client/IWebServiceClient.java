package com.aisino.cxf.client;

import com.aisino.common.util.GlobalInfo;
import com.aisino.common.util.GlobalInfoPush;

public interface IWebServiceClient {

    /**
     * 调用企业webservice协议
     *
     * @param reqMethod 请求方法名
     * @param reqXml               请求参数
     * @param url                  请求URL
     * @return
     * @throws Exception
     */
    String webclientData(String reqMethod, String reqXml, String url) throws Exception;
    /**
     * 
     * 调用cxf协议
     * 
     * @param reqMethod
     * @param reqXml
     * @param url
     * @return
     * @throws Exception String
     * @author: 张双超
     * @date: Created on 2015-7-23 下午01:41:16
     */
    String clientData(String reqMethod, String reqXml, String url) throws Exception;

    /**
     * BEAN转化为xml
     *
     * @param obj
     * @return
     */
    String beanToXml(Object obj);

    /**
     * 组织装协议头
     *
     * @param interfaceCode
     * @param dsptbm
     * @param dsptbmPwd
     * @param taxpayerIdentifyNo
     * @return
     */
    GlobalInfo assemblingGlobalInfo(String interfaceCode, String dsptbm, String dsptbmPwd, String taxpayerIdentifyNo);
    
    /**
     * 组织装协议头
     *
     * @param interfaceCode
     * @param dsptbm
     * @param dsptbmPwd
     * @param taxpayerIdentifyNo
     * @return
     */
    GlobalInfoPush assemblingGlobalInfopush(String interfaceCode, String dsptbm, String dsptbmPwd, String taxpayerIdentifyNo);
}
