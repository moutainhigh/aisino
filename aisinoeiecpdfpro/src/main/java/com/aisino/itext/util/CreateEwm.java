package com.aisino.itext.util;

import com.aisino.common.util.CRCUtil;
import com.aisino.itext.pojo.StencilPlageMx;
import com.aisino.itext.pojo.StencilPlate;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CreateEwm {
	private static CreateEwm createEwm;

	public static CreateEwm getCreateEwm() {
		if (createEwm == null) {
			return createEwm=new CreateEwm();
		}
		return createEwm;
	}

	public String getEwm(StencilPlate fpkj, String mac)
			throws UnsupportedEncodingException, ParseException {
		StringBuffer ewm = new StringBuffer("");
		StringBuffer crcBt = new StringBuffer("");
		SimpleDateFormat sdfYY = new SimpleDateFormat("yyyy");
		SimpleDateFormat dateSdf = new SimpleDateFormat("yyyy-MM-dd");
		Date kprq = dateSdf.parse(fpkj.getKprq());
		String kplx = fpkj.getKplx();
		String fphm = fpkj.getFp_hm();
		double kphjje = new Double(fpkj.getJshj())*100;//(fpkj.getKphjje().doubleValue())*100;
		String xhfsbh = fpkj.getXhdwnsrsbh();
		String fpdm = fpkj.getFp_dm();
		String khmc = fpkj.getXhdwmc();
		StencilPlageMx[] mxList = fpkj.getStencilPlageMxs();
		String zyspmc = mxList[0].getSpmc();
		ewm.append("0200");
		crcBt.append(kplx);
		int kprqInt = new Integer(sdfYY.format(kprq)).intValue()-2000;
		crcBt.append("0");
		crcBt.append((Integer.toHexString(kprqInt)).toUpperCase());
		crcBt.append(getValue(kprq.getMonth() + 1));
		crcBt.append(getValue(kprq.getDate()));
		crcBt.append(fphm);
		String kphjjeStr = Long.toHexString((long)kphjje).toUpperCase();
		while(kphjjeStr.length()<10){
			kphjjeStr ="0"+kphjjeStr;
		}
		crcBt.append(kphjjeStr);
		crcBt.append(mac.trim());
		crcBt.append(getValue(xhfsbh.length()));
		crcBt.append(getValue(fpdm.length()));
		crcBt.append(xhfsbh.trim());
		crcBt.append(fpdm.trim());
		crcBt.append("~");
		String khmcStr = khmc.substring(0, strLength(khmc.getBytes("GBK"), 40));
		crcBt.append(khmcStr);
		int zyspmcLenth = 50 - khmcStr.getBytes().length;
		crcBt.append("~");
		String zyspmcStr = zyspmc.substring(0,strLength(zyspmc.getBytes("GBK"), zyspmcLenth));
		crcBt.append(zyspmcStr);
		byte [] crcByte = CRCUtil.crc16(crcBt.toString().getBytes("GBK"));
		StringBuffer crc = new StringBuffer("");
		for (int i = 0; i < crcByte.length; i++) { 
			String hex = Integer.toHexString(crcByte[i] & 0xFF); 
		     if (hex.length() == 1) { 
		       hex = '0' + hex; 
		     } 
		     crc.append(hex.toUpperCase());
		   } 
		ewm.append(crc.toString());
		ewm.append(crcBt.toString());
		return ewm.toString();
	}

	private String getValue(int ch) {
		String res = "";
		byte[] v = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16,
				17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32,
				33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48,
				49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61 };
		String[] c = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A",
				"B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M",
				"N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y",
				"Z", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k",
				"l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w",
				"x", "y", "z" };
		for (int i = 0; i < v.length; i++) {
			if (v[i] == ch) {
				res = c[i];
			}
		}
		return res;
	}

	private int strLength(byte[] buf, int n) {
		int num = 0;
		boolean bChineseFirstHalf = false;
		for (int i = 0; i < n; i++) {
			if(i==buf.length){
				break;
			}
			if (buf[i] < 0 && !bChineseFirstHalf) {
				bChineseFirstHalf = true;
			} else {
				num++;
				bChineseFirstHalf = false;
			}
		}
		return num;
	}
}
