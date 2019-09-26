package com.aisino.domain.einvoice;

import com.aisino.domain.BaseService;
import com.aisino.domain.einvoice.entity.InvoiceBalance;

/**
 * Created by Martin.Ou on 2014/8/28.
 * <p/>
 * 获取唯一资源服务接口
 */
public interface UniqueResourceManagerService extends BaseService {

    /**
     * 获取发票结存信息
     *
     * @param taxpayerIdentifyNo 纳税人识别号
     * @param taxOrgCode         组织机构代码,暂时可以为空，也不使用
     * @return 纳税人的结存信息
     */
    InvoiceBalance obtainInvoiceBalance(String taxpayerIdentifyNo, String taxOrgCode);

    /**
     * 获取唯一性ID信息
     *
     * @param businessIdType ID类别,可以查看T_UNIQUEID查看当前支持几种类型的ID
     * @return ID数据
     */
    Long obtainBusinessId(String businessIdType);

    /**
     * 获取唯一性ID信息
     *
     * @param businessIdType ID类别,可以查看T_UNIQUEID查看当前支持几种类型的ID
     * @param cacheSize      这次需要获取的ID总数
     * @return ID数据
     */
    Long[] obtainBusinessId(String businessIdType, Integer cacheSize);

    /**
     * 从queue中获取唯一性ID信息
     * 一旦其中一个queue中没有id可用，就统一取数据库获取n（2000）条。
     * @param businessIdType ID类别,可以查看T_UNIQUEID查看当前支持几种类型的ID
     * @return ID数据
     */
    Long obtainBusinessIdFromQueue(String businessIdType);
    
    /**
     * 根据税纳人识别号(NSRSBH)校验是否有结存信息
     *
     * @param taxpayerIdentifyNo 税纳人识别号
     * @return 是否通过检验，True存在结存，False不足
     */
    Boolean verifyInvoiceBalance(String taxpayerIdentifyNo);

    /**
     * 生成唯一性51平台编码
     *
     * @return 51平台编码
     */
    String obtainPlatformCode();

    /**
     * 生成虚拟电商平台的注册号
     *
     * @return 注册号
     */
    String obtainVirtualRegisterCode();

    /**
     * 生成纳税人在平台的授权码
     *
     * @return 授权码
     */
    String obtainPlatformAuthCode();
}
