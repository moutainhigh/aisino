package com.aisino;

import com.aisino.common.util.CaConstant;
import com.google.common.io.Closer;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static com.aisino.common.util.CaConstant.DEFAULT_CHARSET;
import static com.google.common.base.Strings.isNullOrEmpty;

public final class PKCS7 {
    private final static Logger LOGGER = LoggerFactory.getLogger(PKCS7.class);

    //设置信任链
    public static native boolean setTrusts(String trusts);

    //设置解密证书
    public static native boolean setDecryptPfx(byte[] decPfx, String passwd);

    //设置签名证书
    public static native boolean setSignedPfx(byte[] sigPfx, String passwd);

    //验证证书，成功返回1
    public static native int validateCert(String base64Cert);

    //打包数字信封，传递加密证书(即接收者的证书)
    public synchronized static native byte[] signedAndEnveloped(String encBase64Cert, byte[] inData);

    //解包数字信封
    public synchronized static native PKCS7 unpack(byte[] inData);

    //获取错误码
    public static native int getLastError();

    /**
     * sigCert、serial、subject、data以下参数 请不要有任何幻想修改，包括你看不惯的命名！！！！！！！！！！！！！！
     */
    private String sigCert; //签名证书
    private String serial; //证书序列号
    private String subject; //证书主题
    private byte[] data; //原文

    static {
        System.load(CaConstant.getProperty("DLLADDRESS1"));
        System.load(CaConstant.getProperty("DLLADDRESS2"));
    }


    public PKCS7() {
    }

    /**
     * @param trustsBytes     证书信任链
     * @param privatePFXBytes 加密/签名私钥
     * @param privatePFXKey   私钥密码
     * @throws Exception
     */
    public PKCS7(byte[] trustsBytes, byte[] privatePFXBytes, String privatePFXKey) throws Exception {
        if (!setTrusts(new String(trustsBytes))) {
            throw new Exception("" + getLastError());
        }

        if (!setDecryptPfx(privatePFXBytes, privatePFXKey)) {
            throw new Exception("" + getLastError());
        }

        if (!setSignedPfx(privatePFXBytes, privatePFXKey)) {
            throw new Exception("" + getLastError());
        }
    }

    /**
     * 依据文件绝对路径, 读取文件
     *
     * @param fileUri 文件绝对路径
     * @return byte[] 读取成功的文件字节流
     */
    private byte[] readFile(String fileUri) {
        final Closer closer = Closer.create();
        try {
            final BufferedInputStream bufferedInputStream = closer.register(new BufferedInputStream(new FileInputStream(fileUri)));
            final byte[] bufferedBytes = new byte[bufferedInputStream.available()];

            bufferedInputStream.read(bufferedBytes, 0, bufferedBytes.length);

            return bufferedBytes;
        } catch (IOException e) {
            LOGGER.error("read file ioException:", e.fillInStackTrace());
        } finally {
            try {
                closer.close();
            } catch (IOException e) {
                LOGGER.error("close file ioException:", e.fillInStackTrace());
            }
        }

        return new byte[0];
    }

    /**
     * 签名加密
     *
     * @param plainContent   预加密的原文
     * @param publicPFXBytes 公钥加/解密证书的绝对路径
     * @return 加密后的密文数据
     */
    public byte[] pkcs7Encrypt(String plainContent, byte[] publicPFXBytes) {
        try {
            final byte[] certBytes = publicPFXBytes;

            if (certBytes == null) {
                throw new Exception("传入参数公钥为NULL,不可用");
            }

            final String encCert = new String(certBytes);
            if (1 != validateCert(encCert)) {//证书无效
                throw new Exception("" + getLastError());
            }

            if (isNullOrEmpty(plainContent)) {
                throw new Exception("传入参数原文为NULL,不可用");
            }

            return signedAndEnveloped(encCert, plainContent.getBytes(DEFAULT_CHARSET));
        } catch (Exception e) {
            LOGGER.error("pkcs7Encrypt Exception:", e.fillInStackTrace());
        }

        return new byte[0];
    }

    /**
     * 解密验签
     *
     * @param decodeBase64EncryptTxtBytes 经过Base64解压后的密文字节流
     * @return byte[] 经过解密的明文字节流
     */
    public byte[] pkcs7Decrypt(byte[] decodeBase64EncryptTxtBytes) {
        byte[] decryptBytes = new byte[0];
        try {
            //解密
            if (decodeBase64EncryptTxtBytes == null) {
                throw new Exception("传入参数密文为NULL,不可用");
            }

            final PKCS7 pkcs7 = unpack(decodeBase64EncryptTxtBytes);

            if (pkcs7 == null) {
                throw new Exception("" + getLastError());
            }

            decryptBytes = pkcs7.data;
        } catch (Exception e) {
            LOGGER.error("pkcs7Decrypt Exception:", e.fillInStackTrace());
        }

        return decryptBytes;
    }


    /**
     * 测试入口方法2 2014.12.02
     * 总结：
     * 客户端事件需要  客户端私钥(pfx)、pwd + 平台公钥(cer)
     * 平台端事件需要  平台端私钥(pfx)、pwd + 客户端公钥(cer)
     */
    public static void main(String[] args) throws Exception {
        //加密使用的对方加密公钥（客户端调用传入服务端公钥，服务端调用传入客户端公钥）

        final String trustsBytes = CaConstant.getProperty("PUBLIC_TRUSTS");

        /*客户端解密过程 :*客户端私钥(pfx)、pwd*/
        String decryptPFXBytes = CaConstant.getProperty("CLIENT_DECRYPTPFX");
        String decryptPFXKey = CaConstant.getProperty("CLIENT_DECRYPTPFX_KEY");

        final PKCS7 pkcs7Client2 = new PKCS7(FileUtils.readFileToByteArray(new File(trustsBytes)), FileUtils.readFileToByteArray(new File(decryptPFXBytes)), decryptPFXKey);

        final String base64EncryptTxt2 = "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";

//        final byte[] decodeData2 = pkcs7Client2.pkcs7Decrypt(Base64.decodeBase64(base64EncryptTxt2));
//
//        LOGGER.info("客户端解密:{}", new String(decodeData2));

    }
}