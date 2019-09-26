package com.aisino.common.util;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.PrivateKey;
import java.security.cert.Certificate;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//import com.sheca.safeengine.javasafeengine;

public class QYCaCryptUtil {

	/**企业端
	 * corpCert对应的公钥证书
	 */
	private final static Logger log = LoggerFactory.getLogger(QYCaCryptUtil.class);
//    private javasafeengine jSE = null;
	private KeyStore kStore = null;
	private String sAlias = null;
	private byte[] corpCert = null;	
	private String sStoreName = null;
	private String sPin = null;
	
	public QYCaCryptUtil(byte[] corpCert) {
		
		/*this.corpCert = corpCert;
		//this.sStoreName = StaticConstant.QY_StoreName;  //密钥库名称	
		this.sStoreName = StaticConstant.CA_TEST_PATH+StaticConstant.QY_StoreName;  //密钥库名称	
	    this.sPin = StaticConstant.QY_Pin;              //密钥库私钥密码
		jSE = new javasafeengine();
		kStore = jSE.getKeyStore("PKCS12", "SUN", this.sStoreName, this.sPin);		
		sAlias = (String)jSE.getAliasEnum(kStore).nextElement();
	*/
	}
	
	/**
	 * 解密
	 * cryptograph信封数据
	 * signData签名数据
	 */
	public byte[] decrypt(byte[] content) throws KeyStoreException, CustomerException { 
		/*String[] con=new String(content).split(",");
		String cryptograph=con[0];
		String signData=con[1];
		byte[] encryptData = null;
		byte[] decrypt_data = null;
		byte[] signedData = null;

		encryptData = Base64.decodeBase64(cryptograph.getBytes());
		signedData =  Base64.decodeBase64(signData.getBytes());

		PrivateKey oPrivateKey = (PrivateKey)jSE.getPrivateKeyFromStore(this.kStore, this.sAlias, this.sPin);  //获取私钥

		// 用私钥解信封
		decrypt_data = jSE.envelope(2, encryptData, this.kStore.getCertificate(this.sAlias), oPrivateKey, "RSA/ECB/PKCS1Padding", "SUN", "BC");

		boolean isTrue = jSE.verifySign(decrypt_data, signedData, "SHA1/RSA", this.corpCert, "BC");

		if(isTrue){
			return decrypt_data;
		}else {
			*//**
			 * author:peterli date:2013-12-02
			 *//*
			throw new CustomerException("CA验签失败------------------");
		}*/
		return null;
	}		
		
	/**
	 * 加密
	 * cleartext明文
	 */
	public byte[] encrypt(byte[] cleartext){ 
		/*byte[] encryptData = null;
		byte[] signedData = null;		
		byte[] returnData = null;
		
		// 获取证书
		Certificate cert = jSE.getCertFromBuffer(this.corpCert);
		
		// 用公钥封信封
		encryptData = jSE.envelope(1, cleartext, cert, null, "RSA/ECB/PKCS1Padding", "SUN","BC");

		// 获取私钥
		PrivateKey oPrivateKey = (PrivateKey)jSE.getPrivateKeyFromStore(this.kStore, this.sAlias, this.sPin);
		
		// 用私钥签名
		signedData = jSE.sign(cleartext, oPrivateKey, "SHA1/RSA", "BC");
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			baos.write(Base64.encodeBase64(encryptData));
			baos.write(",".getBytes());
			baos.write(Base64.encodeBase64(signedData));
		} catch (IOException e) {
			e.printStackTrace();
			log.error("对信封数据和签名数据进行base64编码时出错-----", e);
		}finally{
			try {
				baos.close();
			} catch (IOException e) {
				log.error("关闭ByteArrayOutputStream时出错-----", e);
			}
		}
		returnData = baos.toByteArray();
		//用私钥签名
		return returnData;*/
		return null;
	}
	/**
	public static void main(String[] args) throws IOException, KeyStoreException {
		String cleartext="航天信息";
		String machineNo = "0000016500313026";
		QYCaCryptUtil qyutil = new QYCaCryptUtil(FileUtils.readFileToByteArray(new File(StaticConstant.CA_TEST_PATH+"\\CorpCA\\UserCert.der")));
		byte[] res=qyutil.encrypt(cleartext.getBytes());
//		System.out.println("encrypt success:" +new String(qyutil.encrypt(cleartext.getBytes())));
		SJCaCryptUtil sjutil = new SJCaCryptUtil(FileUtils.readFileToByteArray(new File(StaticConstant.CA_TEST_PATH+"\\qyCA\\UserCert.der")));
		String strs[] = {"",""};
		strs = new String(res).split(",");		
//		System.out.println("decrypt success: " + new String(sjutil.decrypt(res)));
	}
*/
}
