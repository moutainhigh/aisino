package com.aisino.hessian.service.impl;

import com.aisino.hessian.service.IHessianService;
import com.caucho.hessian.server.HessianServlet;
//import com.yihaodian.backendInvoiceService.interfaces.enums.ServiceResult;
//import com.yihaodian.backendInvoiceService.interfaces.inputvo.invoice.AisinoInvoiceCallbackVo;
//import com.yihaodian.backendInvoiceService.interfaces.outputvo.BackendInvoiceServiceResult;

public class HessianServiceImpl extends HessianServlet implements IHessianService {

    private static final long serialVersionUID = 1L;

    /**
     * hessian测试
     */
    @Override
    public String HessianProcess() {
        return "this is test!";
    }

//    @Override
//    public BackendInvoiceServiceResult<String> aisinoInvoiceCallback(AisinoInvoiceCallbackVo paramAisinoInvoiceCallbackVo) {
//        BackendInvoiceServiceResult<String> result = new BackendInvoiceServiceResult<String>();
//        ServiceResult sResult = ServiceResult.SUCCESS;
//        result.setResult("d:/123.pdf");
//        result.setServiceResult(sResult);
//        result.setServiceResultMsg("success");
//        return result;
//    }

}
