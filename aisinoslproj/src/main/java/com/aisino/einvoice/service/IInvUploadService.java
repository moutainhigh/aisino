package com.aisino.einvoice.service;

import com.aisino.common.util.EShopCertificateBytesInfo;
import com.aisino.domain.einvoice.entity.EShopInfo;

import java.io.IOException;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Schiffer.huang
 * Date: 14-９-25
 * Time: 上午10:10
 * 电商平台信息处理-数据持久化与纳税人查验接口
 */
public interface IInvUploadService {

    /***
     * 获取 电商平台信息,通过 电商平台编码与 税务机关路由 选择
     * @param eshopCode  电商平台编码与
     * @return EShopInfo  电商平台信息实体
     */
    EShopInfo getEShopInfo(String eshopCode);

    /***
     * 处理电商客户端、web端请发票开具请求，先校验电商客户端、web端请求数据Map持久化数据等待下一步生产电子发票
     * @param map 电商请求数据Map(报文)协议对象,包含开具信息，冲红信息,支付信息，开具明细,物流（详细）信息，订单（详细）信息.
     * @return  Map<String,Object> 原电商请求数据Map(报文)协议对象，带处理状态.
     * @throws Exception 抛出异常供方法调用链上层catch.
     */
    Map<String, Object> processEShopInvoice(Map<String, Object> map) throws Exception;

    /***
     * 发票开具信息下载pdf
     * @param map  电商请求数据Map(报文)协议对象
     * @return  Map<String,Object> 原电商请求数据Map(报文)协议对象，带处理状态.
     */
    Map<String, Object> processGetInvoicePdf(Map<String, Object> map);

    /**
     * 获取电商平台信息
     * @param map  电商请求数据Map(报文)协议对象
     * @return  原电商请求数据Map(报文)协议对象，带处理状态.
     */
    Map<String, Object> processGetEShopPlatform(Map<String, Object> map);

    /**
     * 获取电商企业信息
     * @param map  电商请求数据Map(报文)协议对象
     * @return 原电商请求数据Map(报文)协议对象，带处理状态.
     */
    Map<String, Object> processGetEShopEnterprise(Map<String, Object> map);

    /**
     * 根据税控码获取发票pdf Byte[]
     * @param fiscalCode
     * @return byte[]  pdf Byte[]
     */
    byte[] getPdfBytes(String fiscalCode);


    /**
     * 根据纳税人识别码获取CA证书的相关信息
     */
    Map<String, Object> processGetEShopCAInfo(Map<String, Object> paramMap);

    /***
     * 根据纳税人识别码 获取平台CA证书信息
     * @param taxpayerIdentifyNo 纳税人识别码
     * @return  EShopCertificateBytesInfo 证书字节(byte)信息
     * @throws IOException  当执行IO操作证书异常时throw
     */
    EShopCertificateBytesInfo obtainEShopCAInfo(String taxpayerIdentifyNo) throws IOException;
}
