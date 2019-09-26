package com.aisino.test;

import com.aisino.hessian.service.IHessianService;
import com.caucho.hessian.client.HessianProxyFactory;

import java.net.MalformedURLException;

public class Test_hessian {
    
    public static void main(String[] args) throws MalformedURLException {
//        ApplicationContext aContext = new ClassPathXmlApplicationContext("config/hessian-servlet.xml");
//        IHessianService test = (IHessianService) aContext.getBean("HessianBean");
//        ApplicationContext aContext = ContextLoader.getCurrentWebApplicationContext();
        HessianProxyFactory heFactory = new HessianProxyFactory();
        IHessianService test = (IHessianService) heFactory.create(IHessianService.class,"http://192.168.15.235:8888/zzseshop/hessian/hessian");
        
        
        
        String a = test.HessianProcess();
        
        System.out.println("返回结果:"+a);
        
        
    }

}
