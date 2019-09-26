
/*
 * 
 */

package com.aisino.cxf.client;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 2.2.12
 * Wed Apr 09 11:11:59 GMT+08:00 2014
 * Generated source version: 2.2.12
 * 
 */


@WebServiceClient(name = "ReceiveShangHaiService", 
                  wsdlLocation = "http://eicore.test.jd.com/services/receiveShangHai?wsdl",
                  targetNamespace = "http://www.jd.com") 
public class ReceiveShangHaiService extends Service {

    public final static URL WSDL_LOCATION;
    public final static QName SERVICE = new QName("http://www.jd.com", "ReceiveShangHaiService");
    public final static QName ReceiveShangHaiPort = new QName("http://www.jd.com", "ReceiveShangHaiPort");
    static {
        URL url = null;
        try {
            url = new URL("http://eicore.test.jd.com/services/receiveShangHai?wsdl");
        } catch (MalformedURLException e) {
            System.err.println("Can not initialize the default wsdl from http://eicore.test.jd.com/services/receiveShangHai?wsdl");
            // e.printStackTrace();
        }
        WSDL_LOCATION = url;
    }

    public ReceiveShangHaiService(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public ReceiveShangHaiService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public ReceiveShangHaiService() {
        super(WSDL_LOCATION, SERVICE);
    }
    

    /**
     * 
     * @return
     *     returns ReceiveShangHai
     */
    @WebEndpoint(name = "ReceiveShangHaiPort")
    public ReceiveShangHai getReceiveShangHaiPort() {
        return super.getPort(ReceiveShangHaiPort, ReceiveShangHai.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns ReceiveShangHai
     */
    @WebEndpoint(name = "ReceiveShangHaiPort")
    public ReceiveShangHai getReceiveShangHaiPort(WebServiceFeature... features) {
        return super.getPort(ReceiveShangHaiPort, ReceiveShangHai.class, features);
    }

}
