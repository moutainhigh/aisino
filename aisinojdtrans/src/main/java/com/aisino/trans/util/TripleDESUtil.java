package com.aisino.trans.util;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 3DES加解密
 * @author howard
 *
 */
public class TripleDESUtil {


	private static final String Algorithm = "DESede"; //定义 加密算法,可用 DES,DESede,Blowfish
	private final static Logger log = LoggerFactory.getLogger(TripleDESUtil.class);
    
		/**
		 * 加密算法
		 * password为加密密钥，长度为24字节
		 * src为被加密的数据缓冲区（源）
		 */


	    public static byte[] encryptMode(String password, byte[] src) {
	       try {
	            //生成密钥
	            SecretKey deskey = new SecretKeySpec(password.getBytes(), Algorithm);
	            //加密
	            Cipher c1 = Cipher.getInstance(Algorithm);
	            c1.init(Cipher.ENCRYPT_MODE, deskey);
	            return c1.doFinal(src);
	        } catch (java.security.NoSuchAlgorithmException e1) {
	        	log.error("未知",e1);
	            e1.printStackTrace();
	        } catch (javax.crypto.NoSuchPaddingException e2) {
	        	log.error("未知",e2);
	            e2.printStackTrace();
	        } catch (Exception e3) {
	        	log.error("未知",e3);
	            e3.printStackTrace();
	        }
	        return null;
	    }

	    
	    /**
		 * 解密算法
		 * keybyte为加密密钥，长度为24字节
		 * src为被加密的数据缓冲区（源）
		 */
	    public static byte[] decryptMode(String password, byte[] src) {      
	    try {
	            //生成密钥
	            SecretKey deskey = new SecretKeySpec(password.getBytes(), Algorithm);
	            //解密
	            Cipher c1 = Cipher.getInstance(Algorithm);
	            c1.init(Cipher.DECRYPT_MODE, deskey);
	            return c1.doFinal(src);
	        } catch (java.security.NoSuchAlgorithmException e1) {
	        	log.error("未知",e1);
	            e1.printStackTrace();
	        } catch (javax.crypto.NoSuchPaddingException e2) {
	        	log.error("未知",e2);
	            e2.printStackTrace();
	        } catch (Exception e3) {
	        	log.error("未知",e3);
	            e3.printStackTrace();
	        }
	        return null;
	    }

	    //转换成十六进制字符串
	    public static String byte2hex(byte[] b) {
	        StringBuffer hs=new StringBuffer("");
	        String stmp="";

	        for (int n=0;n<b.length;n++) {
	            stmp=(Integer.toHexString(b[n] & 0XFF));
	            if (stmp.length()==1) hs.append("0").append(stmp);
	            else hs.append(stmp);
	            if (n<b.length-1)  hs.append(":");
	        }
	        return hs.toString().toUpperCase();
	    }

    /**
     * BASE64加密
     */
    private static byte[] encode(byte[] res) {
        try {
            /*Base64 base = new Base64();

            return base.encode(res);*/
            return org.apache.commons.codec.binary.Base64.encodeBase64(res);
        } catch (Exception e) {
            return null;
        }
    }

	    @SuppressWarnings("unused")
		public static void main(String[] args) throws Exception{

            /*
            String org="\n" +
                    "  <RESPONSE_COMMON_DJDSQYXXGLOBLE class=\"RESPONSE_COMMON_DJDSQYXXGLOBLE\">\n" +
                    "    <RETURNCODE>0000</RETURNCODE>\n" +
                    "    <RETURNMESSAGE/>\n" +
                    "    <RESPONSE_COMMON_DJDSQYXXS class=\"RESPONSE_COMMON_DJDSQYXX;\" size=\"1\">\n" +
                    "      <RESPONSE_COMMON_DJDSQYXX>\n" +
                    "        <NSRSBH>510107698868804</NSRSBH>\n" +
                    "        <DSPTBM/>\n" +
                    "        <RETURNCODE/>\n" +
                    "        <RETURNMESSAGE/>\n" +
                    "        <NSRDZDAH>330209000003687611</NSRDZDAH>\n" +
                    "        <NSRMC>OOOOX-COMPANY</NSRMC>\n" +
                    "        <HYDM>02</HYDM>\n" +
                    "        <HYMC>222</HYMC>\n" +
                    "        <NSRZT>20</NSRZT>\n" +
                    "        <DJZCLX>159</DJZCLX>\n" +
                    "        <ZGSWRYDM>13855555555</ZGSWRYDM>\n" +
                    "        <FDDBR>JIM</FDDBR>\n" +
                    "        <BSRMC>JIM</BSRMC>\n" +
                    "        <SWDJBLX_DM></SWDJBLX_DM>\n" +
                    "        <SWJG_DM>13101144800</SWJG_DM>\n" +
                    "        <SCJYDZ>shanghai</SCJYDZ>\n" +
                    "        <KPZT>1</KPZT>\n" +
                    "      </RESPONSE_COMMON_DJDSQYXX>\n" +
                    "    </RESPONSE_COMMON_DJDSQYXXS>\n" +
                    "    <RESPONSE_COMMON_DSQYPZHDXXS class=\"RESPONSE_COMMON_DSQYPZHDXX;\" size=\"0\"/>\n" +
                    "  </RESPONSE_COMMON_DJDSQYXXGLOBLE>\n";
                    */
            String org="AACC";

            String password="1234567890Ko1ztjNt4xGg76Tqxl4rvg==";

            byte[] temp_content = org.getBytes(SystemConfig.charSet);//. org == null ? "".getBytes() : org.getBytes(StaticConstant.CHARSET);
             temp_content = encryptMode(password.substring(SystemConfig.SJMYBEFORELENG, password.length()), temp_content);

            String  cc=new String(encode(temp_content), SystemConfig.charSet);
//            System.out.print(cc);

	    	/**
	    	org.apache.commons.codec.binary.Base64 base = new org.apache.commons.codec.binary.Base64();
	        //添加新安全算法,如果用JCE就要把它添加进去
	        Security.addProvider(new com.sun.crypto.provider.SunJCE());
	        String szSrc = FileUtils.readFileToString(new File("d:/NewFile.xml"), StaticConstant.CHARSET);
	        Password password=PassWordCheck.passWordCreate("12345678");
	        long start=System.currentTimeMillis();
	        //将串3des
	        byte[] encoded = encryptMode(password.getPass(),szSrc.getBytes());
	        //再zip
	     //   String zip=ProXml.zip(new String(encoded,StaticConstant.CHARSET));
	        
	        
	        
	        
//	        byte[] zip = ProXml.zip(encoded);
	        byte[] zip = GZipUtils.compress(encoded);
	        
	        //再base64
	        byte[] e=base.encode(zip);
	        
	        
	        long end=System.currentTimeMillis();
//			System.out.println("加密使用了"+(end-start)+"毫秒");
//	        System.out.println("加密后的字符串:" + new String(e));
	        start=System.currentTimeMillis();
	        //加密后的串解bae64
	        byte[] data = base.decode(e);
	        //加密后的串解bae64再解zip在同一个方法里
	        
	        
	        
	        
	        
//	        byte[] temp = ProXml.unZip(data);
	        byte[] temp = GZipUtils.decompress(data);
	        
	        
	        
	        
	        
	        
	        byte[] srcBytes = decryptMode(password.getPass(),temp);
	        end=System.currentTimeMillis();
//			System.out.println("解密使用了"+(end-start)+"毫秒");
//	        System.out.println("解密后的字符串:" + (new String(srcBytes)));
			*/
	    }

}
