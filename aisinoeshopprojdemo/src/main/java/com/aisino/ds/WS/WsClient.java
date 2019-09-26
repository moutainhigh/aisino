package com.aisino.ds.WS;

import com.aisino.common.Constants;
import com.aisino.common.util.ProXml;
import com.aisino.common.util.ReturnStateInfo;
import com.aisino.common.util.XmlPar;
import org.codehaus.xfire.client.Client;

import java.net.URL;
import java.util.Map;

public class WsClient {
	
	private static Client client = null;
	
	public static void getClient(String fplx) {
		try {
			if("增值税发票".equals(fplx)){
				client = new Client(new URL(Constants.webserviceURlZzs));
			} else {
				client = new Client(new URL(Constants.webserviceURl));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static String pushData(String xml) {
		try {
			//Client client = new Client(new URL(Constants.webserviceURl));
			Object[] objs = client.invoke("eiInterface", new Object[] { xml });
			String res = objs[0].toString();
			Map map =ProXml.getInterface(res);
			ReturnStateInfo rs = (ReturnStateInfo)map.get(XmlPar.RETURNSTATEINFO);
			if(rs.getReturnCode().equals("0000")){
				return "0000";
			}else{
				return rs.getReturnMessage();
			}
		} catch (Exception e) {
			return e.getMessage();
		}
	}
}
