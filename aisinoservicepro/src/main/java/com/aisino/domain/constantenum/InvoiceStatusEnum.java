package com.aisino.domain.constantenum;

/**
 * Created by Bourne.Lv on 2014/09/04.
 * <p/>
 * 发票状态枚举类
 */
public enum InvoiceStatusEnum {

    INV_UNSOLVED("0", "发票未处理状态"),
    INV_OBTAIN_TAXCODE_DATA_SUCCESS("1000", "从电商获取数据成功"),
    INV_OBTAIN_ESHOP_DATA_SUCCESS("1000", "从电商获取数据成功"),
    INV_OBTAIN_ESHOP_DATA_FAILURE("1001", "从电商获取数据失败"),
    INV_TAXCODE_SUCCESS("2100", "发票赋码成功"),
    INV_TAXCODE_FAILURE("2101", "生成发票失败（可重试）"),
    INV_TAXCODE_FAILURE_HANDWORK("2102", "生成发票失败（手工处理）"),
    INV_CREATE_SUCCESS("2000", "生成发票成功"),
    INV_CREATE_FAILURE("2001", "生成发票失败"),
    INV_UPLOAD_TAX_SUCCESS("4002", "发票上传税局成功"),
    INV_UPLOAD_TAX_FAILED("4001", "发票上传税局失败"),
    INV_UPLOAD_51_SUCCESS("5000","发票上传51平台成功"),
    INV_UPLOAD_51_FAILED("5001","发票上传51平台失败");

    private String parameterValue;

    private String parameterName;

    private InvoiceStatusEnum(String code, String name) {
        parameterValue = code;
        parameterName = name;
    }

    public String getParameterValue() {
        return parameterValue;
    }

    public String getParameterName() {
        return parameterName;
    }
}
