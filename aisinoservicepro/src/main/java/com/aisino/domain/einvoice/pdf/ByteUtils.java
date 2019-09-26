package com.aisino.domain.einvoice.pdf;

/**
 * Created by Martin.Ou on 2014/9/11.
 */
public class ByteUtils {
    /*
    * 将int转化成字节
    */
    public static byte[] int2byte(int i) {
        return new byte[]{(byte) (i & 0xFF), (byte) ((i >> 8) & 0xFF), (byte) ((i >> 16) & 0xFF), (byte) ((i >> 24) & 0xFF)};
    }

    /*
     * 将字节转成 int
     */
    public static int byte2int(byte[] intByte) {
        int addr = intByte[0] & 0xFF;
        addr |= ((intByte[1] << 8) & 0xFF00);
        addr |= ((intByte[2] << 16) & 0xFF0000);
        addr |= ((intByte[3] << 24) & 0xFF000000);

        return addr;
    }
}
