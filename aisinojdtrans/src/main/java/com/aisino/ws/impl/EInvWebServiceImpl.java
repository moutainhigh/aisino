package com.aisino.ws.impl;

import static org.joda.time.DateTime.now;
import javax.jws.WebService;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.aisino.trans.service.ITransService;
import com.aisino.ws.IEInvWebService;

@WebService
public class EInvWebServiceImpl implements IEInvWebService{
	@Autowired
	private ITransService transService;
	
	private Logger log = LoggerFactory.getLogger(EInvWebServiceImpl.class);

	public String eiInterface(String requestMessage) {
		log.debug("接收到报文为:{}开始处理",requestMessage);
		final DateTime begin = now();
		String respnoseXml = transService.transReq(requestMessage);
		final Long millSeconds = new Duration(begin, now()).getMillis();
		log.info("转发发票开具耗时{}毫秒",millSeconds);
		return respnoseXml;
	}
	
	
}
