package com.aisino.itext.dao;

public interface IItextDao {
    Object getTemplate(String departCode, String len);

    /**
     * 通过纳税人识别码获取签章ID
     *
     * @param taxpayerIdentifyNo 纳税人识别码
     * @return 有效的签章ID
     */
    String getSignCAIdByTaxpayerIdentifyNo(String taxpayerIdentifyNo);

}
