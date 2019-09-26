package com.aisino.common.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.PrivateKey;
import java.security.cert.Certificate;

import com.aisino.common.util.CustomerException;
import com.aisino.common.util.StaticConstant;
import org.apache.commons.codec.binary.Base64;

//import com.sheca.safeengine.javasafeengine;

public class SJCaCryptUtil {
	/**
	 * 税局端 corpCert对应的公钥证书
	 */
//	private javasafeengine jSE = null;
	private KeyStore kStore = null;
	private String sAlias = null;
	private byte[] corpCert = null;
	private String sStoreName = null;
	private String sPin = null;
	/**
	 * @param corpCert
//	 * @param type TRUE:加密机;false:本地证书
	 */
	public SJCaCryptUtil(byte[] corpCert) {
//		if("1".equals(StaticConstant.TAX_CA_TYPE)){
//			this.corpCert = corpCert;
//			this.sStoreName = StaticConstant.CA_TEST_PATH+StaticConstant.SJ_StoreName;  //密钥库名称
//			this.sPin = StaticConstant.SJ_Pin;              //密钥库私钥密码
//			jSE = new javasafeengine();
//			kStore = jSE.getKeyStore("JKS", "SUN", this.sStoreName, this.sPin);	//调用上海加密机
//			sAlias = (String)jSE.getAliasEnum(kStore).nextElement();
//		}else{
//			this.corpCert = corpCert;
//			this.sStoreName = StaticConstant.CA_TEST_PATH + StaticConstant.SJ_StoreName; // 密钥库名称
//			this.sPin = StaticConstant.SJ_Pin; // 密钥库私钥密码
//			jSE = new javasafeengine();
//			kStore = jSE.getKeyStore("PKCS12", "SUN", this.sStoreName, this.sPin);// 本地证书读取
//			sAlias = (String) jSE.getAliasEnum(kStore).nextElement();
//		}
		

	}
	
	/**
	 * 解密
	 * 
	 * @param content
	 * @return
	 * @throws KeyStoreException
	 * @throws IOException byte[]
	 * @author: jerome.wang
	 * @throws CustomerException
	 * @date: Created on 2013-11-27 上午09:58:34
	 */
	public byte[] decrypt(byte[] content) throws KeyStoreException, IOException, CustomerException {
		if("1".equals(StaticConstant.TAX_CA_TYPE)){
			return decryptMachine(content);
		}else{
			return decryptLocal(content);
		}
	}

	/**
	 * 解密 读取本地证书 cryptograph信封数据 signData签名数据
	 * @throws KeyStoreException
	 * @throws CustomerException 
	 */
	public byte[] decryptLocal(byte[] content) throws KeyStoreException, CustomerException {
		String[] con=new String(content).split(",");
		String cryptograph=con[0];
		String signData=con[1]; 
		byte[] encryptData = null;
		byte[] decrypt_data = null;
		byte[] signedData = null;	
		encryptData = Base64.decodeBase64(cryptograph.getBytes());		
		signedData =  Base64.decodeBase64(signData.getBytes());				
//		PrivateKey oPrivateKey = (PrivateKey)jSE.getPrivateKeyFromStore(this.kStore, this.sAlias, this.sPin);  //获取私钥
//
//		decrypt_data = jSE.envelope(2, encryptData, this.kStore.getCertificate(this.sAlias), oPrivateKey, "RSA/ECB/PKCS1Padding", "SUN", "BC");
//        // 用 私钥解信封
		boolean isTrue = false;
//		isTrue = jSE.verifySign(decrypt_data, signedData, "SHA1/RSA", this.corpCert, "BC");
		// 验签
		if(isTrue){
			return decrypt_data;
		}else {
			/**
			 * author:peterli date:2013-12-02
			 */
			throw new CustomerException("CA验签失败------------------");
		}

	}

	/**
	 * 解密 调用加密机 cryptograph信封数据 signData签名数据
	 * @throws KeyStoreException
	 * @throws CustomerException 
	 */
	public byte[] decryptMachine(byte[] content) throws KeyStoreException, CustomerException {
		String[] con=new String(content).split(",");
		String cryptograph=con[0];
		String signData=con[1]; 
		byte[] encryptData = null;
		byte[] decrypt_data = null;
		byte[] signedData = null;	
		
		encryptData = Base64.decodeBase64(cryptograph.getBytes());		
		signedData =  Base64.decodeBase64(signData.getBytes());				
//		PrivateKey oPrivateKey = (PrivateKey)jSE.getPrivateKeyFromStore(this.kStore, this.sAlias, this.sPin);  //获取私钥
//		// 用 私钥解信封
//		decrypt_data = jSE.envelope(2, encryptData, this.kStore.getCertificate(this.sAlias), oPrivateKey, "RSA/ECB/PKCS1Padding", "DatechCrypto", "DatechCrypto", "RsaKey1", "");
		// 验签
		boolean isTrue = false;
//		 isTrue = jSE.verifySign(decrypt_data, signedData, "SHA1/RSA", this.corpCert, "DatechCrypto");
		//---------------------------------调用加密机结束---------------------------------
		
		if(isTrue){
			return decrypt_data;
		}else {
			/**
			 * author:peterli date:2013-12-02
			 */
			throw new CustomerException("CA验签失败------------------");
		}
	}
	
	
	/**
	 * 加密
	 * 
	 * @param cleartext
	 * @return
	 * @throws IOException byte[]
	 * @author: jerome.wang
	 * @date: Created on 2013-11-27 上午09:58:23
	 */
	public byte[] encrypt(byte[] cleartext) throws IOException{
		if("1".equals(StaticConstant.TAX_CA_TYPE)){
			return encryptMachine(cleartext);
		}else{
			return encryptLocal(cleartext);
		}
	}

	/**
	 * 加密读取本地证书 cleartext明文
	 * @throws IOException
	 */
	public byte[] encryptLocal(byte[] cleartext) throws IOException {
		byte[] encryptData = null;
		byte[] signedData = null;		
		byte[] returnData=null;
		//公钥证书
//		Certificate cert = jSE.getCertFromBuffer(this.corpCert);
//		// 用公钥封信封
//		encryptData = jSE.envelope(1, cleartext, cert, null, "RSA/ECB/PKCS1Padding", "SUN","BC");
//		// 获取私钥
//		PrivateKey oPrivateKey = (PrivateKey)jSE.getPrivateKeyFromStore(this.kStore, this.sAlias, this.sPin);
//
//		signedData = jSE.sign(cleartext, oPrivateKey, "SHA1/RSA", "BC");
		ByteArrayOutputStream baos=new ByteArrayOutputStream();
		baos.write(Base64.encodeBase64(encryptData));
		baos.write(",".getBytes());
		baos.write(Base64.encodeBase64(signedData));
		returnData=baos.toByteArray();
		//用私钥签名
		return returnData;
	}
	
	
	/**
	 * 加密调用加密机 cleartext明文
	 * 
	 * @throws IOException
	 */
	public byte[] encryptMachine(byte[] cleartext) throws IOException {
		byte[] encryptData = null;
		byte[] signedData = null;		
		byte[] returnData=null;
//		Certificate cert = jSE.getCertFromBuffer(this.corpCert);
//		//证书
//		encryptData = jSE.envelope(1, cleartext, cert, null, "RSA/ECB/PKCS1Padding", "DatechCrypto", "DatechCrypto", "RsaKey1", "");
//		// 用公钥封信封
//		PrivateKey oPrivateKey = (PrivateKey)jSE.getPrivateKeyFromStore(this.kStore, this.sAlias, this.sPin);
//		// 获取私钥
//		signedData = jSE.sign(cleartext, oPrivateKey, "SHA1/RSA", "DatechCrypto");
//		//---------------------------------------调用加密机结束---------------------------------------
		
		ByteArrayOutputStream baos=new ByteArrayOutputStream();
		baos.write(Base64.encodeBase64(encryptData));
		baos.write(",".getBytes());
		baos.write(Base64.encodeBase64(signedData));
		returnData=baos.toByteArray();
		//用私钥签名
		return returnData;
	}
/**
	public static void main(String[] args) throws IOException {

		String cleartext = "航天信息";
		String machineNo = "0000016500313026";
		QYCaCryptUtil qyutil = new QYCaCryptUtil(FileUtils.readFileToByteArray(new File("E:\\skskjca\\ca\\CorpCA\\UserCert.der")));
		SJCaCryptUtil sjutil = new SJCaCryptUtil(FileUtils.readFileToByteArray(new File("E:\\skskjca\\ca\\qyCA\\UserCert.der")));
		byte[] res = sjutil.encrypt(cleartext.getBytes());
		System.out.println("encrypt加密成功:"+ new String(sjutil.encrypt(cleartext.getBytes())));
		String strs[] = { "", "" };
		strs = new String(res).split(",");
		System.out.println("decrypt解密成功: " + new String(qyutil.decrypt(res)));


	}
*/
}
