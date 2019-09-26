package com.aisino.itext.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.aisino.itext.ItestFactory;
import com.aisino.itext.pojo.StencilPlageMx;
import com.aisino.itext.pojo.StencilPlate;
import com.aisino.itext.dao.IItextDao;
//
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import com.aisino.itext.ItestFactory;
//import com.aisino.itext.pojo.StencilPlageMx;
//import com.aisino.itext.pojo.StencilPlate;
//
public class Test {
	
	public static void main(String[] args) {
		StencilPlate StencilPlate = new StencilPlate();
		StencilPlate.setBz("备注");
		StencilPlate.setJym("12343123413");
		StencilPlate.setGhdwmc("张双超");
		StencilPlate.setGhdwnsrsbh("310114312356647");
		StencilPlate.setGhdwdz_dh("北京海淀，15652241402");
		StencilPlate.setGhdwkhyh_zh("北京银行，134135143512341241");
		StencilPlate.setMmq("<><<LLU*&^%*JJ12341243214");
		StencilPlate.setHjje("1324.02");
		StencilPlate.setHjse("224.03");
		StencilPlate.setJshj("1100.00.");
		StencilPlate.setJshjdx("一千一百元整");
		StencilPlate.setKprq("2012年01月01日");
		StencilPlate.setKpy("开票员");
	    StencilPlate.setXhdwmc("张双超1");
	    StencilPlate.setXhdwnsrsbh("1111123213413");
	    StencilPlate.setXhdwdz_dh("北京海淀1，15652241402");
	    StencilPlate.setXhdwkhyh_zh("北京银行1，134135143512341241");
		StencilPlate.setSky("lll丽丽");
		StencilPlate.setFhr("小名");
		StencilPlate.setEwm("kasdjfair3o923rielkfjsdfpoiwe");
		StencilPlageMx[] mxs = new StencilPlageMx[3];
		for(int i=0;i<3;i++){
			StencilPlageMx StencilPlageMx = new StencilPlageMx();
			StencilPlageMx.setSpmc("地地地地地地地地地地地地地地地地地地地地地地121224344444444444444444444444444444444444444");
			StencilPlageMx.setDj("200.00");
			StencilPlageMx.setSl("1");
			StencilPlageMx.setJe("200.00");
			StencilPlageMx.setDw("个1");
			StencilPlageMx.setGgxh("个1");
			StencilPlageMx.setSlv("个1");
			StencilPlageMx.setSe("个1");
			mxs[i] = StencilPlageMx;
		}
		StencilPlate.setStencilPlageMxs(mxs);
		StencilPlate.setFp_dm("132098765432");
		StencilPlate.setFp_hm("00000001");
		long startTime = System.currentTimeMillis();
			byte[] fileByte = ItestFactory.getDocument().producePDF(StencilPlate,null,null);
			File file = new File("f:\\生成结果.pdf");
			try {
				FileOutputStream out = new FileOutputStream(file);
				out.write(fileByte);
				out.flush();
				out.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		long endTime = System.currentTimeMillis();
		System.err.println((endTime-startTime)/1000);
	}
	
}
