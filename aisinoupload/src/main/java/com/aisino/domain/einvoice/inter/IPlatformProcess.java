package com.aisino.domain.einvoice.inter;

import com.aisino.domain.sys.model.Route;
import com.aisino.protocol.bean.REQUEST_FPKJSCGLOBLE;

public interface IPlatformProcess {
	/**
	 * @param busiType
	 * @param REQUEST_FPKJSCGLOBLE
	 * @param route
	 * @param zcm
	 * @return
	 */
	boolean process(String busiType, REQUEST_FPKJSCGLOBLE REQUEST_FPKJSCGLOBLE, Route route, String zcm, String fp_id, int scpt_xl);
}
