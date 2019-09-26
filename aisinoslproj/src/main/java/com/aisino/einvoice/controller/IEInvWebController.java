package com.aisino.einvoice.controller;

/**
 * <p>发票处理接口入库点，用于接收电商端传递发票开具请求信息 </p>
 * @author wuyong@aisino.com
 * @version 1.0 Created on Nov 4, 2013 7:47:20 PM
 */
public interface IEInvWebController {

    /**
     * 电商发票开具信息请求入口点
     * @param requestMessage
     * @return String
     */
    String eiInterface(String requestMessage);
    
    /**
     * 发票下载接口
     * @param requestMessage
     * @return String
     */
    String eiInterfpxz(String requestMessage);
}
