package com.aisino.domain.security;

import com.aisino.domain.SystemConfig;
import com.aisino.domain.security.entity.UserRequestPacket;

import static com.aisino.domain.security.utils.Convert.hexStringToByte;

/**
 * Created by Martin.Ou on 2014/9/4.
 */
public final class InvSign {
    private byte[] input;
/*
    public static String KEY_STATE = "state";
    public static String KEY_ERRORMESSAGE = "errorMessage";
    public static String KEY_SKM = "skm";
    public static String KEY_EWM = "ewm";
*/

    public InvSign(byte[] input) {
        this.input = input;
    }

    public UserRequestPacket getUserRequestPacket() throws Exception {
        final UserRequestPacket requestPack = new UserRequestPacket();
        final byte b = hexStringToByte(SystemConfig.is_commandClass);
        requestPack.setCommandClass(b);
        requestPack.setData(this.input);

        return requestPack;
    }
}
