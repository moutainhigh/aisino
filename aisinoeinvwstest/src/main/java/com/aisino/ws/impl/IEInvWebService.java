package com.aisino.ws.impl;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by the JAX-WS RI. JAX-WS RI 2.1.3-hudson-390-
 * Generated source version: 2.0
 * 
 */
@WebService(name = "IEInvWebService", targetNamespace = "http://ws.aisino.com/")
public interface IEInvWebService {

    /**
     * 
     * @param requestXml
     * @return returns java.lang.String
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "eiInterface", targetNamespace = "http://ws.aisino.com/", className = "com.aisino.ws.EiInterface")
    @ResponseWrapper(localName = "eiInterfaceResponse", targetNamespace = "http://ws.aisino.com/", className = "com.aisino.ws.EiInterfaceResponse")
    public String eiInterface(
            @WebParam(name = "requestXml", targetNamespace = "")
                    String requestXml);

}
