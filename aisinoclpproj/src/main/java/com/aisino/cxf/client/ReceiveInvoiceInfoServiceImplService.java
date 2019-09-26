
package com.aisino.cxf.client;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.1.6 in JDK 6
 * Generated source version: 2.1
 * 
 */
@WebServiceClient(name = "ReceiveInvoiceInfoServiceImplService", targetNamespace = "http://impl.receiveInvoiceInfo.service.ws.einvoice.jd.com/", wsdlLocation = "http://eideal.fm.jd.com/ws/receiveInvoiceService?wsdl")
public class ReceiveInvoiceInfoServiceImplService
    extends Service
{

    private final static URL RECEIVEINVOICEINFOSERVICEIMPLSERVICE_WSDL_LOCATION;
    private final static Logger logger = Logger.getLogger(ReceiveInvoiceInfoServiceImplService.class.getName());

    static {
        URL url = null;
        try {
            URL baseUrl;
            baseUrl = ReceiveInvoiceInfoServiceImplService.class.getResource(".");
            url = new URL(baseUrl, "http://eideal.fm.jd.com/ws/receiveInvoiceService?wsdl");
        } catch (MalformedURLException e) {
            logger.warning("Failed to create URL for the wsdl Location: 'http://eideal.fm.jd.com/ws/receiveInvoiceService?wsdl', retrying as a local file");
            logger.warning(e.getMessage());
        }
        RECEIVEINVOICEINFOSERVICEIMPLSERVICE_WSDL_LOCATION = url;
    }

    public ReceiveInvoiceInfoServiceImplService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public ReceiveInvoiceInfoServiceImplService() {
        super(RECEIVEINVOICEINFOSERVICEIMPLSERVICE_WSDL_LOCATION, new QName("http://impl.receiveInvoiceInfo.service.ws.einvoice.jd.com/", "ReceiveInvoiceInfoServiceImplService"));
    }

    /**
     * 
     * @return
     *     returns ReceiveInvoiceInfoService
     */
    @WebEndpoint(name = "ReceiveInvoiceInfoServiceImplPort")
    public ReceiveInvoiceInfoService getReceiveInvoiceInfoServiceImplPort() {
        return super.getPort(new QName("http://impl.receiveInvoiceInfo.service.ws.einvoice.jd.com/", "ReceiveInvoiceInfoServiceImplPort"), ReceiveInvoiceInfoService.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns ReceiveInvoiceInfoService
     */
    @WebEndpoint(name = "ReceiveInvoiceInfoServiceImplPort")
    public ReceiveInvoiceInfoService getReceiveInvoiceInfoServiceImplPort(WebServiceFeature... features) {
        return super.getPort(new QName("http://impl.receiveInvoiceInfo.service.ws.einvoice.jd.com/", "ReceiveInvoiceInfoServiceImplPort"), ReceiveInvoiceInfoService.class, features);
    }

}
