package com.aisino.ds.WS.impl;

import com.aisino.common.util.*;
import com.aisino.ds.ProDdxx;
import com.aisino.ds.WS.IWsService;
import com.aisino.protocol.bean.REQUEST_FPKJXX_FPJGXX;

import java.util.Map;

public class WsService implements IWsService {

	public String saveFp(String requestStr) throws Exception {
		String returnSTr = "";
		Map map = ProXml.getInterface(requestStr);
		try {
			ReturnStateInfo rs = (ReturnStateInfo) map.get(XmlPar.RETURNSTATEINFO);
			Data data = (Data) map.get(XmlPar.DATA);
			REQUEST_FPKJXX_FPJGXX REQUEST_FPKJXX_FPJGXX = (com.aisino.protocol.bean.REQUEST_FPKJXX_FPJGXX) (ProXml.getDataRoot(data.getContent()).get(0));
			REQUEST_FPKJXX_FPJGXX.getDDH();
//			String pdfFile = REQUEST_FPKJXX_FPJGXX.getPDF_FILE();
//			
//			Base64 base = new Base64();
//			byte[] origiFile = base.decode(pdfFile.getBytes("UTF-8"));
//			
			String url = "d:\\dzfp\\" + REQUEST_FPKJXX_FPJGXX.getFP_DM() + REQUEST_FPKJXX_FPJGXX.getFP_HM() + ".pdf";
//			FileOutputStream out = new FileOutputStream(new File(url));
//			out.write(origiFile);
//			out.flush();
//			out.close();
			
//			DataOutputStream dos = new DataOutputStream(new FileOutputStream(url));
//			dos.write(ProXml.decode(pdfFile).getBytes());
//			dos.flush();
//			dos.close();

			// String url = "d:\\dzfp\\" + REQUEST_FPKJXX_FPJGXX.getFP_DM() +
			// REQUEST_FPKJXX_FPJGXX.getFP_HM() + ".pdf";
			// FileUtils.writeByteArrayToFile(new File(url),
			// REQUEST_FPKJXX_FPJGXX.getPDF_FILE().getBytes());
			ProDdxx prod = new ProDdxx();
			prod.updateDdxx(REQUEST_FPKJXX_FPJGXX.getDDH(), url);

		} catch (Exception e) {
			ReturnStateInfo rs = new ReturnStateInfo();
			rs.setReturnCode("9999");
			e.printStackTrace();
			try{
				returnSTr = ProXml.getXml((GlobalInfo) map.get(XmlPar.GLOBALINFO), rs, new Data());
			}catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		ReturnStateInfo rs = new ReturnStateInfo();
		rs.setReturnCode("0000");
		try{
			returnSTr = ProXml.getXml((GlobalInfo) map.get(XmlPar.GLOBALINFO), rs, new Data());
		}catch (Exception e1) {
			e1.printStackTrace();
		}
		return returnSTr;
		
	}

}
