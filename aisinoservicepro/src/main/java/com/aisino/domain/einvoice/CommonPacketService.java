package com.aisino.domain.einvoice;

import com.aisino.domain.BaseService;
import com.aisino.domain.security.entity.CommonPacket;
import com.aisino.domain.security.entity.UserRequestPacket;

/**
 * Created by Martin.Ou on 2014/9/5.
 * <p/>
 * 请求包和应答信息封装服务提供
 *
 * @see com.aisino.domain.BaseService
 */
public interface CommonPacketService extends BaseService {

    /**
     * 获取请求包信息
     *
     * @param packet 原始请求信息
     * @return 封装后的请求信息
     * @throws Exception
     */
    CommonPacket newInstanceByRequest(UserRequestPacket packet) throws Exception;

    /**
     * 获取应答包信息
     *
     * @param bytes 原始请求信息
     * @return 封装后的应答信息
     * @throws Exception
     */
    CommonPacket newInstanceByResponse(byte[] bytes) throws Exception;
}
