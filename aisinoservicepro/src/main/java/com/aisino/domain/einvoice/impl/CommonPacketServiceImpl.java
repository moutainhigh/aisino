package com.aisino.domain.einvoice.impl;

import com.aisino.common.util.CRCUtil;
import com.aisino.common.util.TranslateUtil;
import com.aisino.domain.AbstractBaseService;
import com.aisino.domain.SystemConfig;
import com.aisino.domain.einvoice.CommonPacketService;
import com.aisino.domain.security.SecurityCodeException;
import com.aisino.domain.security.entity.CommonPacket;
import com.aisino.domain.security.entity.UserRequestPacket;
import com.aisino.domain.security.entity.UserResponsePacket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;

import static com.aisino.domain.security.SecurityConstant.*;

/**
 * Created by Martin.Ou on 2014/9/5.
 */
public class CommonPacketServiceImpl extends AbstractBaseService implements CommonPacketService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CommonPacketServiceImpl.class);

    public CommonPacket newInstanceByRequest(UserRequestPacket packet)
            throws Exception {
        CommonPacket commonPacket = new CommonPacket();

        commonPacket.setUserRequest(packet);
        if (packet == null) {
            throw new SecurityCodeException("请求数据不合法");
        }

        try {
            commonPacket.setPacketHeader(SystemConfig.headerBytes);
            commonPacket.setCommandClass(commonPacket.getUserRequest().getCommandClass());

            if (commonPacket.getUserRequest().getData() == null) commonPacket.getUserRequest().setData(new byte[0]);

            commonPacket.setDataLength(commonPacket.getUserRequest().getData().length);
            commonPacket.setData(commonPacket.getUserRequest().getData());

            final ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            byte[] temp = TranslateUtil.int2Bytes(commonPacket.getDataLength());
            bytes.write(commonPacket.getPacketHeader());
            bytes.write(commonPacket.getCommandClass());
            bytes.write(temp);
            bytes.write(commonPacket.getData());

            temp = CRCUtil.crc16(commonPacket.getData());
            bytes.write(temp);
            bytes.flush();

            commonPacket.setDataPacket(bytes.toByteArray());
        } catch (Exception e) {
            LOGGER.error("newInstanceByRequest失败：", e);

            final SecurityCodeException auExp = new SecurityCodeException();
            auExp.setOriginalExceptionMsg(e.getMessage());
            auExp.setExceptionMsg("生成请求数据包出现异常");
        }

        return commonPacket;
    }

    public CommonPacket newInstanceByResponse(byte[] bytes)
            throws Exception {
        CommonPacket commonPacket = new CommonPacket();
        commonPacket.setDataPacket(bytes);
        if (commonPacket.getDataPacket() == null) {
            throw new SecurityCodeException("响应数据不合法");
        }

        commonPacket.setPacketHeader(SystemConfig.headerBytes);
        try {
            if (bytes.length < SystemConfig.headerLength + 6) {
                throw new SecurityCodeException("响应数据格式不合法");
            }

            byte commandClass = bytes[SystemConfig.headerLength - 1];
            commonPacket.setCommandClass(commandClass);
            byte[] temp = new byte[4];
            System.arraycopy(bytes, SystemConfig.headerLength, temp, 0, 4);

            commonPacket.setDataLength(TranslateUtil.byte2Int(temp));
            if (commonPacket.getDataLength() < 2) {
                throw new SecurityCodeException("响应数据格式不合法");
            }

            temp = new byte[commonPacket.getDataLength()];
            System.arraycopy(bytes, SystemConfig.headerLength + 4, temp, 0, temp.length);

            commonPacket.setUserResponse(new UserResponsePacket());

            final byte[] temp_bytes = new byte[2];

            System.arraycopy(bytes, SystemConfig.headerLength + 4, temp_bytes, 0, temp_bytes.length);
            commonPacket.getUserResponse().setState(new String(temp_bytes, SystemConfig.dataCharset));

            temp = new byte[commonPacket.getDataLength() - 2];
            System.arraycopy(bytes, SystemConfig.headerLength + 4 + 2, temp, 0, temp.length);
            commonPacket.getUserResponse().setResponseData(temp);

            if (Result_OK.equals(commonPacket.getUserResponse().getState())) {
                commonPacket.getUserResponse().setResponseText(new String(commonPacket.getUserResponse().getResponseData(), SystemConfig.dataCharset));
            } else if (Result_ER.equals(commonPacket.getUserResponse().getState())) {
                if ((commonPacket.getUserResponse().getResponseData() == null)
                        || (commonPacket.getUserResponse().getResponseData().length < PACKET_ERRORCODELENGTH)) {
                    throw new SecurityCodeException("响应格式不正确");
                }

                temp = new byte[PACKET_ERRORCODELENGTH];

                System.arraycopy(commonPacket.getUserResponse().getResponseData(), 0, temp,
                        0, temp.length);

                commonPacket.getUserResponse().setErrorCode(new String(temp,
                        SystemConfig.dataCharset));
            }

        } catch (Exception e) {
            LOGGER.error("newInstanceByResponse失败：", e);

            final SecurityCodeException auExp = new SecurityCodeException();
            auExp.setOriginalExceptionMsg(e.getMessage());
            auExp.setExceptionMsg("生成请求数据包出现异常");
        }

        return commonPacket;
    }
}
