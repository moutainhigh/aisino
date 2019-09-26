package com.aisino.domain.einvoice;

import com.aisino.domain.BaseService;

/**
 * Created by Martin.Ou on 2014/8/28.
 * <p/>
 * 获取唯一资源服务接口-带路由分库信息
 */
@Deprecated
public interface UniqueResourceManagerRoutingService extends BaseService {

    /**
     * 获取唯一性ID信息
     *
     * @param businessIdType ID类别,可以查看T_UNIQUEID查看当前支持几种类型的ID
     * @param taxRouting     税务机关路由 信息
     * @return ID数据
     */
    Long obtainBusinessId(String businessIdType, String taxRouting);

    /**
     * 获取唯一性ID信息
     *
     * @param businessIdType ID类别,可以查看T_UNIQUEID查看当前支持几种类型的ID
     * @param cacheSize      这次需要获取的ID总数
     * @param taxRouting     税务机关路由 信息
     * @return ID数据
     */
    Long[] obtainBusinessId(String businessIdType, Integer cacheSize, String taxRouting);


    /**
     * 根据税纳人识别号(NSRSBH)校验是否有结存信息
     *
     * @param taxpayerIdentifyNo 税纳人识别号
     * @param taxRouting         税务机关路由 信息
     * @return 是否通过检验，True存在结存，False不足
     */
    Boolean verifyInvoiceBalance(String taxpayerIdentifyNo, String taxRouting);
}
