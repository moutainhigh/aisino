package com.aisino.ws.impl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for eiInterface complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="eiInterface">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="requestMessage" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "eiInterface", propOrder = { "requestMessage" })
public class EiInterface {

    protected String requestMessage;

    /**
     * Gets the value of the requestMessage property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getRequestMessage() {
        return requestMessage;
    }

    /**
     * Sets the value of the requestMessage property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setRequestMessage(String value) {
        this.requestMessage = value;
    }

}
