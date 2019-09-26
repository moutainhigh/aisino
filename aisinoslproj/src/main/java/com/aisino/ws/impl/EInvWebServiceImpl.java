package com.aisino.ws.impl;

import javax.jws.WebService;

import com.aisino.einvoice.controller.IEInvWebController;
import com.aisino.ws.IEInvWebService;

/**
 * <p> 获取电商平台传递的webservice协议接口类</p>
 * @author wuyong@aisino.com
 * @version 1.0 Created on Sep 6, 2013 9:59:15 AM
 */
@WebService
public final class EInvWebServiceImpl implements IEInvWebService {

    private IEInvWebController webController;
    
    public IEInvWebController getWebController() {
        return webController;
    }
    
    public void setWebController(IEInvWebController webController) {
        this.webController = webController;
    }

    public String eiInterface(String requestMessage) {
       return this.webController.eiInterface(requestMessage);
    }
    
    /**
     * <p>供httpServlet接口下载发票pdf文件</p>
     * 
     * @param requestMessage
     * @return String
     * @author: summer.wang
     * @date: Created on 2015-8-14 下午2:17:51
     */
    public String eiInterfpxz(String requestMessage) {
        return this.webController.eiInterfpxz(requestMessage);
     }

}
