package com.aisino.ws.impl;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;

/**
 * This class was generated by the JAX-WS RI. JAX-WS RI 2.1.3-hudson-390-
 * Generated source version: 2.0
 * <p>
 * An example of how this class may be used:
 * 
 * <pre>
 * EInvWebServiceImplService service = new EInvWebServiceImplService();
 * IEInvWebService portType = service.getEInvWebServiceImplPort();
 * portType.eiInterface(...);
 * </pre>
 * 
 * </p>
 * 
 */
@WebServiceClient(name = "EInvWebServiceImplService", targetNamespace = "http://impl.ws.aisino.com/", wsdlLocation = "http://192.168.8.146:8080/zzs_jdProxy/webservice/eInvWS?wsdl")
public class EInvWebServiceImplService extends Service {

    private final static URL EINVWEBSERVICEIMPLSERVICE_WSDL_LOCATION;

    private final static Logger logger = Logger
            .getLogger(EInvWebServiceImplService.class
                    .getName());

    static {
        URL url = null;
        try {
            URL baseUrl;
            baseUrl = EInvWebServiceImplService.class
                    .getResource(".");
            url = new URL(baseUrl,
                    "http://192.168.8.146:8080/zzs_jdProxy/webservice/eInvWS?wsdl");
        } catch (MalformedURLException e) {
            logger.warning("Failed to create URL for the wsdl Location: 'http://192.168.8.146:8080/zzs_jdProxy/webservice/eInvWS?wsdl', retrying as a local file");
            logger.warning(e.getMessage());
        }
        EINVWEBSERVICEIMPLSERVICE_WSDL_LOCATION = url;
    }

    public EInvWebServiceImplService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public EInvWebServiceImplService() {
        super(EINVWEBSERVICEIMPLSERVICE_WSDL_LOCATION, new QName(
                "http://impl.ws.aisino.com/", "EInvWebServiceImplService"));
    }

    /**
     * 
     * @return returns IEInvWebService
     */
    @WebEndpoint(name = "EInvWebServiceImplPort")
    public IEInvWebService getEInvWebServiceImplPort() {
        return super.getPort(new QName("http://impl.ws.aisino.com/",
                "EInvWebServiceImplPort"), IEInvWebService.class);
    }

}
