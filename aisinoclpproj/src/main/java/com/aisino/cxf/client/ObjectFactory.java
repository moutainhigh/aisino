
package com.aisino.cxf.client;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.aisino.cxf.client package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Receive_QNAME = new QName("http://www.jd.com", "receive");
    private final static QName _GetHashTime_QNAME = new QName("http://www.jd.com", "getHashTime");
    private final static QName _GetHashTimeResponse_QNAME = new QName("http://www.jd.com", "getHashTimeResponse");
    private final static QName _ReceiveResponse_QNAME = new QName("http://www.jd.com", "receiveResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.aisino.cxf.client
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ReceiveResponse }
     * 
     */
    public ReceiveResponse createReceiveResponse() {
        return new ReceiveResponse();
    }

    /**
     * Create an instance of {@link GetHashTime }
     * 
     */
    public GetHashTime createGetHashTime() {
        return new GetHashTime();
    }

    /**
     * Create an instance of {@link GetHashTimeResponse }
     * 
     */
    public GetHashTimeResponse createGetHashTimeResponse() {
        return new GetHashTimeResponse();
    }

    /**
     * Create an instance of {@link EiActualDetail }
     * 
     */
    public EiActualDetail createEiActualDetail() {
        return new EiActualDetail();
    }

    /**
     * Create an instance of {@link EiActualBill }
     * 
     */
    public EiActualBill createEiActualBill() {
        return new EiActualBill();
    }

    /**
     * Create an instance of {@link Receive }
     * 
     */
    public Receive createReceive() {
        return new Receive();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Receive }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.jd.com", name = "receive")
    public JAXBElement<Receive> createReceive(Receive value) {
        return new JAXBElement<Receive>(_Receive_QNAME, Receive.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetHashTime }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.jd.com", name = "getHashTime")
    public JAXBElement<GetHashTime> createGetHashTime(GetHashTime value) {
        return new JAXBElement<GetHashTime>(_GetHashTime_QNAME, GetHashTime.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetHashTimeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.jd.com", name = "getHashTimeResponse")
    public JAXBElement<GetHashTimeResponse> createGetHashTimeResponse(GetHashTimeResponse value) {
        return new JAXBElement<GetHashTimeResponse>(_GetHashTimeResponse_QNAME, GetHashTimeResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReceiveResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.jd.com", name = "receiveResponse")
    public JAXBElement<ReceiveResponse> createReceiveResponse(ReceiveResponse value) {
        return new JAXBElement<ReceiveResponse>(_ReceiveResponse_QNAME, ReceiveResponse.class, null, value);
    }

}
