package com.aisino.hedwig.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aisino.hedwig.IAisinoDzfpYHDInterface;
import com.aisino.trans.service.ITransService;
import com.aisino.trans.util.Data;
import com.aisino.trans.util.DzfpInterfaceBean;
import com.aisino.trans.util.ReturnStateInfo;
import com.aisino.trans.util.XmlPar;
import com.aisino.trans.util.YHDResPonsePackage;
@Service(value="aisinoDzfpYHDInterface")
public class AisinoDzfpYHDInterface implements IAisinoDzfpYHDInterface{
	
	@Autowired
	private ITransService transService;

	public DzfpInterfaceBean eiInterface(DzfpInterfaceBean dzfpInterfaceBean) {
		if(dzfpInterfaceBean==null || dzfpInterfaceBean.getGlobalInfo()==null||dzfpInterfaceBean.getReturneStateInfo()==null || dzfpInterfaceBean.getData() == null){
			return YHDResPonsePackage.packageRes(XmlPar.BUSI_FAIL, "接收到数据存在问题");
		}else{
			Map<String,Object> map = transService.YHDTransreq(dzfpInterfaceBean);
			return YHDResPonsePackage.packageRes(dzfpInterfaceBean, (ReturnStateInfo)map.get(XmlPar.RETURNSTATEINFO), (Data)map.get(XmlPar.DATA));
		}
	}

}
