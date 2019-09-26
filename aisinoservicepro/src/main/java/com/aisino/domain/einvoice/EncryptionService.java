package com.aisino.domain.einvoice;

import com.aisino.domain.BaseService;
import com.aisino.domain.einvoice.entity.SecurityCodeCondition;

import java.util.Map;

/**
 * Created by Martin.Ou on 2014/9/4.
 * <p/>
 * 加密服务接口
 *
 * @see com.aisino.domain.BaseService
 */
public interface EncryptionService extends BaseService {

    /**
     * 根据条件获取加密信息
     *
     * @param codeCondition 加密条件
     * @return 加密成功后的信息
     */
    Map<String, String> obtainSecurityCode(SecurityCodeCondition codeCondition);
}
