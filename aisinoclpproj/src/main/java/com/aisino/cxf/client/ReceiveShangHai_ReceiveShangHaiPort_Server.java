
package com.aisino.cxf.client;

import javax.xml.ws.Endpoint;

/**
 * This class was generated by Apache CXF 2.2.12
 * Wed Apr 09 11:11:59 GMT+08:00 2014
 * Generated source version: 2.2.12
 * 
 */
 
public class ReceiveShangHai_ReceiveShangHaiPort_Server{

    protected ReceiveShangHai_ReceiveShangHaiPort_Server() throws Exception {
        System.out.println("Starting Server");
        Object implementor = new ReceiveShangHaiImpl();
        String address = "http://eicore.test.jd.com/services/receiveShangHai";
        Endpoint.publish(address, implementor);
    }
    
    public static void main(String args[]) throws Exception { 
        new ReceiveShangHai_ReceiveShangHaiPort_Server();
        System.out.println("Server ready..."); 
        
        Thread.sleep(5 * 60 * 1000); 
        System.out.println("Server exiting");
        System.exit(0);
    }
}
