package com.aisino;

import static org.apache.commons.io.FileUtils.readFileToByteArray;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aisino.trans.util.SystemConfig;
import com.aisino.util.CaConstant;
import com.aisino.util.EShopCertificateBytesInfo;

public final class PKCS7 {
	private final static Logger LOGGER = LoggerFactory.getLogger(PKCS7.class);

	// 错误代码
	// ERROR_UNKNOWN = 1100,
	// ERROR_PARAM,
	// ERROR_TRUSTS,
	// ERROR_CERT,
	// ERROR_CERVERIFY,
	// ERROR_MEMORY,
	// ERROR_PKCS7,
	// ERROR_UNINIT,
	// ERROR_PRIKEY,
	// ERROR_PFX,
	// ERROR_ENCRYPT = 1110,
	// ERROR_DECRYPT,
	// ERROR_SIGN,
	// ERROR_VERIFY,
	// ERROR_ALGOR

	// 加载动态库
	static {
		System.load(CaConstant.getProperty("DLLADDRESS1"));
		System.load(CaConstant.getProperty("DLLADDRESS2"));

		/**
		 * description：实例化对象前，注入西部CA相关证书信息 date:2015-06-24 author：lichunhui
		 */
		EShopCertificateBytesInfo certificate = null;
		try {
			certificate = getPlatformCertificateEntity();
		} catch (IOException e) {
			LOGGER.error("获取平台证书实体对象时出现异常，详情为：" + e);
			e.printStackTrace();
		}

		if (!setTrusts(new String(certificate.getTrustsBytes()))) {
			LOGGER.error("注入证书信任链失败！！！");
		}

		if (!setDecryptPfx(certificate.getPrivatePFXBytes(), certificate.getPrivatePFXKey())) {
			LOGGER.error("注入解密私钥证书失败！！！");
		}

		if (!setSignedPfx(certificate.getPrivatePFXBytes(), certificate.getPrivatePFXKey())) {
			LOGGER.error("注入签名私钥证书失败！！！");
		}
	}

	// 设置信任链
	private static native boolean setTrusts(String trusts);

	// 设置解密证书
	private static native boolean setDecryptPfx(byte[] decPfx, String passwd);

	// 设置签名证书
	private static native boolean setSignedPfx(byte[] sigPfx, String passwd);

	// 验证证书，成功返回1
	public static native int validateCert(String base64Cert);

	// 打包数字信封，传递加密证书(即接收者的证书)
	public synchronized static native byte[] signedAndEnveloped(
			String encBase64Cert, byte[] inData);

	// 解包数字信封
	public synchronized static native PKCS7 unpack(byte[] inData);

	// 获取错误码
	public static native int getLastError();

	/**
	 * 
	 * <p>得到平台证书实体对象</p>
	 * 
	 * @return CertificateEntity
	 * @author: lichunhui lichunhui1314@126.com
	 * @throws IOException
	 * @date: Created on 2015-6-24 下午03:44:19
	 */
	private static EShopCertificateBytesInfo getPlatformCertificateEntity()
			throws IOException {
		
		final EShopCertificateBytesInfo certificateBytesInfo = new EShopCertificateBytesInfo();
		// 读取平台私钥证书，并注入到EShopCertificateBytesInfo对象中
		certificateBytesInfo.setPrivatePFXBytes(readFileToByteArray(new File(SystemConfig.SL_CA_PRIVATE_KEY)));
		// 读取平台公钥证书，并注入到EShopCertificateBytesInfo对象中
		certificateBytesInfo.setPublicPFXBytes(readFileToByteArray(new File(SystemConfig.SL_CA_PUBLIC_KEY)));
		// 读取平台证书信任链，并注入到EShopCertificateBytesInfo对象中
		certificateBytesInfo.setTrustsBytes(readFileToByteArray(new File(SystemConfig.SL_CA_TRUST)));
		// 读取平台私钥证书密码，并注入到EShopCertificateBytesInfo对象中
		certificateBytesInfo.setPrivatePFXKey(SystemConfig.SL_CA_PASSWORD);

		return certificateBytesInfo;
	}

	public String sigCert; // 签名证书
	public String serial; // 证书序列号
	public String subject; // 证书主题
	public byte[] data; // 原文

	public static byte[] read(String file) {
		try {
			return FileUtils.readFileToByteArray(new File(file));
		} catch (IOException e) {
			LOGGER.error("Read File Error.");
			return null;
		}
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

            if (StringUtils.isEmpty(plainContent)) {
                throw new Exception("传入参数原文为NULL,不可用");
            }

            return signedAndEnveloped(encCert, plainContent.getBytes(CaConstant.DEFAULT_CHARSET));
        } catch (Exception e) {
            LOGGER.error("pkcs7Encrypt Exception:", e.fillInStackTrace());
            return new byte[0];
        }
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
}
