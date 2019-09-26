package com.aisino.domain.einvoice;

import com.aisino.domain.BaseService;

import java.io.IOException;
import java.util.Map;

/**
 * Created by Martin.Ou on 2014/9/4.
 * <p/>
 * 系统签章管理服务
 *
 * @see com.aisino.domain.BaseService
 */
public interface SignatureService extends BaseService {
    /**
     * 获取签章服务数据
     *
     * @param pdfBytes 签章条件
     * @return 签章数据
     */
    byte[] obtainSignature(final byte[] pdfBytes, final Map<String, Object> signatureParamMap) throws IOException;
}
