package com.aisino.einvoice.controller;

import com.aisino.einvoice.service.IInvUploadService;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

public final class EInvoiceDownServlet extends HttpServlet {

    private static final long serialVersionUID = -1316143439495747121L;

    private IInvUploadService service;

    public EInvoiceDownServlet() {
        super();
    }

    public void destroy() {
        super.destroy();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (service == null) {
            ApplicationContext app = WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext());
            service = (IInvUploadService) app.getBean("eShopGeneratorInvoiceServiceImpl");
        }

        final String fiscalCode = request.getParameter("fiscalCode");

        final byte[] stream = service.getPdfBytes(fiscalCode);

        response.setContentType("application/pdf");
        response.addHeader("Content-Disposition", "attachment; filename=" + fiscalCode + ".pdf");
        response.setContentLength(stream.length);

        final OutputStream os = response.getOutputStream();
        try {
            os.write(stream);
            os.flush();
        } finally {
            os.close();
        }

    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.doGet(request, response);
    }

    public void init() throws ServletException {

    }

}
