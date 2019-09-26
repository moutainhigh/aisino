package com.aisino.domain.einvoice.impl;

import com.aisino.common.util.TranslateUtil;
import com.aisino.domain.AbstractBaseService;
import com.aisino.domain.SystemConfig;
import com.aisino.domain.einvoice.CommonPacketService;
import com.aisino.domain.einvoice.EncryptionService;
import com.aisino.domain.einvoice.entity.SecurityCodeCondition;
import com.aisino.domain.security.SecurityCode;
import com.aisino.domain.security.SecurityCodeException;
import com.aisino.domain.security.entity.CommonPacket;
import com.aisino.domain.security.entity.UserRequestPacket;
import com.aisino.domain.security.entity.UserResponsePacket;
import com.aisino.domain.security.utils.Convert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import static com.aisino.domain.security.SecurityConstant.*;
import static com.aisino.domain.security.utils.Convert.fillWithZero;
import static com.google.common.base.Strings.isNullOrEmpty;

/**
 * Created by Martin.Ou on 2014/9/4.
 */
public final class EncryptionServiceImpl extends AbstractBaseService implements EncryptionService {
    private static final Logger LOGGER = LoggerFactory.getLogger(EncryptionServiceImpl.class);

    private static final String fscodeVersion = SystemConfig.fscodeVersion;
    private static final String dcodeVersion = SystemConfig.dcodeVersion;
    private static final String swjgbm = SystemConfig.swjgbm;
    private static final String commandClass = SystemConfig.commandClass;
    private static final String dataCharset = SystemConfig.dataCharset;
    private static final String serverIp = SystemConfig.serverIp;
    private static final Integer serverPort = SystemConfig.serverPort;
    private static final Integer headerLength = SystemConfig.headerLength;

    private CommonPacketService commonPacketService;

    public void setCommonPacketService(CommonPacketService commonPacketService) {
        this.commonPacketService = commonPacketService;
    }

    public Map<String, String> obtainSecurityCode(SecurityCodeCondition codeCondition) {
        final SecurityCode securityCode;
        if (!isNullOrEmpty(codeCondition.getSwjgbm())) {
            securityCode = new SecurityCode(codeCondition.getFscodeVersion(), codeCondition.getDcodeVersion(),
                    codeCondition.getNsrsbhLength(), codeCondition.getNsrsbh(),
                    codeCondition.getKprq(), codeCondition.getFpdm(), codeCondition.getFphm(),
                    codeCondition.getMoney(), codeCondition.getSwjgbm(),
                    codeCondition.getKplx(), codeCondition.getDsptbm());
        } else {
            securityCode = new SecurityCode(fscodeVersion, dcodeVersion,
                    codeCondition.getNsrsbhLength(), codeCondition.getNsrsbh(),
                    codeCondition.getKprq(), codeCondition.getFpdm(),
                    codeCondition.getFphm(), codeCondition.getMoney(), swjgbm,
                    codeCondition.getKplx(), codeCondition.getDsptbm());
        }

        return getSecurityCodeMap(securityCode);
    }


    private UserRequestPacket getUserRequestPacket(SecurityCode securityCode) throws Exception {
        final UserRequestPacket requestPack = new UserRequestPacket();
        final byte b = Convert.hexStringToByte(commandClass);
        requestPack.setCommandClass(b);

        final StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(securityCode.getFscodeVersion()).append(securityCode.getDcodeVersion());

        stringBuilder.append(fillWithZero(securityCode.getNsrsbhLength(), 10));
        stringBuilder.append(securityCode.getNsrsbh());
        stringBuilder.append(fillWithZero(securityCode.getKprq(), 8));
        stringBuilder.append(fillWithZero(securityCode.getFphm(), 8));
        stringBuilder.append(fillWithZero(securityCode.getFpdm(), 20));
        stringBuilder.append(fillWithZero(securityCode.getJe(), 20));

        stringBuilder.append(securityCode.getSwjgbm()).append(securityCode.getKplx()).append(securityCode.getDsptbm());

        requestPack.setData(stringBuilder.toString().getBytes(dataCharset));

        return requestPack;
    }

    private Map<String, String> getSecurityCodeMap(SecurityCode securityCode) {
        final Map<String, String> map = new HashMap<String, String>();
        try {
            final UserResponsePacket packet = authenticate(getUserRequestPacket(securityCode));
            if (Result_OK.equals(packet.getState())) {
                final byte[] by = packet.getResponseData();
                byte[] tmp = new byte[20];

                System.arraycopy(by, 0, tmp, 0, 20);
                map.put(KEY_STATE, Result_OK);
                map.put(KEY_SKM, new String(tmp, dataCharset));

                tmp = new byte[by.length - 20];
                System.arraycopy(by, 20, tmp, 0, by.length - 20);

                map.put(KEY_EWM, new String(tmp, dataCharset));
            } else if (Result_ER.equals(packet.getState())) {
                map.put(KEY_STATE, Result_ER);
                map.put(KEY_ERRORMESSAGE, packet.getErrorCode());
            }
        } catch (Exception e) {
            LOGGER.error("getSecurityCodeMap失败", e);
            map.put(KEY_STATE, Result_ER);
            map.put(KEY_ERRORMESSAGE, e.getMessage());
        }

        return map;
    }

    private UserResponsePacket authenticate(UserRequestPacket request)
            throws Exception {
        final CommonPacket requestPack = commonPacketService.newInstanceByRequest(request);
        byte[] bytes = null;
        Socket socket = null;
        try {
            socket = new Socket(serverIp, serverPort);

            final OutputStream os = socket.getOutputStream();
            os.write(requestPack.getDataPacket());
            os.flush();

            final InputStream ins = socket.getInputStream();
            final ByteArrayOutputStream byteOs = new ByteArrayOutputStream();
            int i = 0;
            int value = 0;
            while (i < headerLength + 4) {
                i++;
                value = ins.read();
                if (value == -1) {
                    throw new Exception("响应数据不符合规范长度");
                }

                byteOs.write(value);
            }

            byte[] temp = new byte[4];
            byteOs.flush();
            System.arraycopy(byteOs.toByteArray(), headerLength, temp, 0, temp.length);
            int length = TranslateUtil.byte2Int(temp);
            i = 0;
            value = 0;
            while (i < length + 2) {
                i++;
                value = ins.read();
                if (value == -1) {
                    throw new Exception("响应数据不符合规范长度");
                }

                byteOs.write(value);
            }

            byteOs.flush();
            bytes = byteOs.toByteArray();

        } catch (Exception e) {
            LOGGER.error("生成请求数据包出现异常：", e);

            final SecurityCodeException auExp = new SecurityCodeException();
            auExp.setOriginalExceptionMsg(e.getMessage());
            auExp.setExceptionMsg("生成请求数据包出现异常");
        } finally {
            if (socket != null) {
                socket.close();
            }
        }

        final CommonPacket responsePack = commonPacketService.newInstanceByResponse(bytes);

        return responsePack.getUserResponse();
    }
}
