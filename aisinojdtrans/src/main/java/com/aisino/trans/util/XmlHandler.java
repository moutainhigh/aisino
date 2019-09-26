package com.aisino.trans.util;


import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aisino.protocol.bean.NSRXX;
import com.aisino.protocol.bean.ROUTE;
 

public class XmlHandler {
	private Logger log = LoggerFactory.getLogger(XmlHandler.class);
	private Document document;
	private String filePath;
	public XmlHandler(){
		filePath = XmlHandler.class.getClassLoader().getResource("route.xml").getFile();  
		SAXReader sax=new SAXReader();  
		try {
			this.document = sax.read(filePath);
			this.document.setXMLEncoding("utf-8"); 
			
		} catch (DocumentException e) {
			e.printStackTrace();
		} 
	}
	
	
	/**
	 * 新增节点
	 */
	public boolean addNode(ROUTE route) throws Exception {
		Field[] fields = route.getClass().getDeclaredFields();
		Element root=this.document.getRootElement();
		Element routesEle = root.element("ROUTES");
		List list = routesEle.elements("ROUTE");
		//配置文件中已经存在该电商平台
		//lableB:
		for (int i = 0; i < list.size(); i++) {
			Element routeEle = (Element)list.get(i);
			Element dsptbmEle = routeEle.element("DSPTBM");
			String dsptbmStr = dsptbmEle.getTextTrim();
			Element nsrxxsEle = routeEle.element("NSRXXS");
			List listNsrxx = nsrxxsEle.elements("NSRXX");
			if(dsptbmStr.equals(route.getDSPTBM())){
				//判断该平台下是否已经存在该税号
				Element dqmcEle = routeEle.element("DQMC");
				String dqmcStr = dqmcEle.getTextTrim();
				if(dqmcStr.equals(route.getDQMC())){ //一个平台对应多个地区时，如果地区已存在
				 for (int j = 0; j < listNsrxx.size(); j++) {
						Element nsrxxEle = (Element)listNsrxx.get(j);
						Element nsrsbhEle = nsrxxEle.element("NSRSBH");
						String nsrsbhEleStr = nsrsbhEle.getTextTrim();
						log.debug(nsrsbhEleStr);
						if(nsrsbhEleStr.equals(route.getNSRXXS()[0].getNSRSBH())){
							     log.debug(dsptbmStr+"---平台下存在该税号---不能重复添加---"+route.getNSRXXS()[0].getNSRSBH());
							     return false;
						}
				 }
				 
				 log.debug(dsptbmStr+":平台下不存在该税号---新增节点");
				 addNsrxx(route,nsrxxsEle,routesEle);
				 return true;
				}
				
			  }else{
					//判断平台下是否已经存在该税号
					 for (int j = 0; j < listNsrxx.size(); j++) {
							Element nsrxxEle = (Element)listNsrxx.get(j);
							Element nsrsbhEle = nsrxxEle.element("NSRSBH");
							String nsrsbhEleStr = nsrsbhEle.getTextTrim();
							log.debug(nsrsbhEleStr);
							if(nsrsbhEleStr.equals(route.getNSRXXS()[0].getNSRSBH())){
								     log.debug(dsptbmStr+"---平台下存在该税号---不能重复添加---"+route.getNSRXXS()[0].getNSRSBH());
								     return false;
							}
					  } 
			    }
		  }
		  //配置文件中不存在该电商平台	
		  Element newRouteEle = routesEle.addElement("ROUTE");		
		  newRouteEle.addElement("DSPTBM").setText(route.getDSPTBM());		
		  newRouteEle.addElement("DQMC").setText(route.getDQMC());		
		  newRouteEle.addElement("DQCODE").setText(route.getDQCODE());		
		  Element newNSRXXSEle = newRouteEle.addElement("NSRXXS");
		  //添加属性
		  newNSRXXSEle.addAttribute("class", "NSRXX;");
		  newNSRXXSEle.addAttribute("size", "0");
		  
		  String allNsr = routesEle.attribute("size").getValue();
		  routesEle.attribute("size").setValue(Integer.parseInt(allNsr)+1+"");
		  addNsrxx(route,newNSRXXSEle,routesEle);	 
          return true;
		 
		
	}
	/**
	 * 判断NSRSBH是否重复
	 */
	public boolean checkNsrIsDupl(String NSRSBH) throws Exception {
		Element root=document.getRootElement();
		Element routesEle = root.element("ROUTES");
		List list = routesEle.elements("ROUTE");
		//配置文件中已经存在该电商平台
		//lableB:
		for (int i = 0; i < list.size(); i++) {
			Element routeEle = (Element)list.get(i);
			Element dsptbmEle = routeEle.element("DSPTBM");
			String dsptbmStr = dsptbmEle.getTextTrim();
			Element nsrxxsEle = routeEle.element("NSRXXS");
			List listNsrxx = nsrxxsEle.elements("NSRXX");
		 
			//判断该平台下是否已经存在该税号
			for (int j = 0; j < listNsrxx.size(); j++) {
				Element nsrxxEle = (Element)listNsrxx.get(j);
				Element nsrsbhEle = nsrxxEle.element("NSRSBH");
				String nsrsbhEleStr = nsrsbhEle.getTextTrim();
				log.debug(nsrsbhEleStr);
				if(nsrsbhEleStr.equals(NSRSBH)){
					log.debug(dsptbmStr+"---平台下存在该税号---不能重复添加---"+NSRSBH);
					return true;
				}
			}
		}
		return false;
	}
 
	
	/**
	 * 新增纳税人节点
	 */
	public void addNsrxx(ROUTE route,Element nsrxxsEle,Element routesEle) throws Exception{
		Element nsrxxEle = nsrxxsEle.addElement("NSRXX");
		NSRXX ns = new NSRXX();
		Field[] fieldss = ns.getClass().getDeclaredFields();
		int len = route.getNSRXXS().length;
 	 	for (int j = 0; j < fieldss.length; j++) {
			String fieldName = fieldss[j].getName();
		    PropertyDescriptor pd = new PropertyDescriptor(fieldName,NSRXX.class);  
		    Method rM = pd.getReadMethod();//获得读方法  
		    String num = (String) rM.invoke(route.getNSRXXS()[0]); 
		    if(num!=null){
			    	if(!fieldName.equals("DQMC")&&!fieldName.equals("DSPTBM")){
			          nsrxxEle.addElement(fieldName).setText(num);
			    	}
		    }
			if (fieldName.startsWith("class$")) 
			{
				continue;
			}
		}  
 	 	 	 	
 	 	Element fppzxxEle =  nsrxxEle.addElement("FP_XSS");
 	 	fppzxxEle.addAttribute("class", "FP_XS;");
 	 	fppzxxEle.addAttribute("size", "1");
 	 	Element fpxsEle = fppzxxEle.addElement("FP_XS");
 	 	fpxsEle.addElement("FP_DM").setText("151011420001");
 	 	fpxsEle.addElement("FP_QH").setText("");
 	 	fpxsEle.addElement("FP_ZH").setText("");
 	 	
 	    String sizeNsr = nsrxxsEle.attribute("size").getValue();
		log.debug("sizeNsr:"+sizeNsr);
		nsrxxsEle.attribute("size").setValue(Integer.parseInt(sizeNsr)+1+"");
		
        saveDocument();
	}
	
	/**
	 * 修改节点信息
	 */
	public  void updateNode(ROUTE route) throws Exception {
			Field[] fields = route.getClass().getDeclaredFields();
			//Document document= loadXml();
			Element root=document.getRootElement();
			Element routesEle = root.element("ROUTES");
			List list = routesEle.elements("ROUTE");
			lableB:  
			for (int i = 0; i < list.size(); i++) {
				Element routeEle = (Element)list.get(i);
				log.debug(routeEle.getName());
				Element dsptbmEle = routeEle.element("DSPTBM");
				String dsptbmStr = dsptbmEle.getTextTrim();
				log.debug(dsptbmStr);
				
				 Element nsrxxsEle = routeEle.element("NSRXXS");
				 List listNsrxx = nsrxxsEle.elements("NSRXX");
				 //没修改所在平台，在原平台下修改纳税人信息
				 if(dsptbmStr.equals(route.getDSPTBM())){
					 for (int j = 0; j < listNsrxx.size(); j++) {
							Element nsrxxEle = (Element)listNsrxx.get(j);
							Element nsrsbhEle = nsrxxEle.element("NSRSBH");
							String nsrsbhEleStr = nsrsbhEle.getTextTrim();
							log.debug(nsrsbhEleStr);
						    //平台下存在该税号，直接修改
							if(nsrsbhEleStr.equals(route.getNSRXXS()[0].getNSRSBH())){
								nsrxxEle.element("NSRMC").setText(route.getNSRXXS()[0].getNSRMC());
								nsrxxEle.element("SFKTZZS").setText(route.getNSRXXS()[0].getSFKTZZS());
								nsrxxEle.element("ZZSDZFPURL").setText(route.getNSRXXS()[0].getZZSDZFPURL());
								nsrxxEle.element("ZZSDZFPMETHOD").setText(route.getNSRXXS()[0].getZZSDZFPMETHOD());
								nsrxxEle.element("ZZSFPBL").setText(route.getNSRXXS()[0].getZZSFPBL());
								nsrxxEle.element("PTDZFPURL").setText(route.getNSRXXS()[0].getPTDZFPURL());
								nsrxxEle.element("PTDZFPMETHOD").setText(route.getNSRXXS()[0].getPTDZFPMETHOD());
								nsrxxEle.element("SFKTPTDZFP").setText(route.getNSRXXS()[0].getSFKTPTDZFP());
							    
						        saveDocument();
							    return; 
							}
					 }
				}
			}
		}
	
	/**
	* 把改变的domcument对象保存到指定的xml文件中 
	* @author chenleixing 
	* @throws IOException  
	*/  
	public void saveDocument(){  
		XMLWriter writer = null;
		try{
		OutputFormat format = OutputFormat.createPrettyPrint();  //获取输出的指定格式    
		format.setEncoding("UTF-8");//设置编码 ，确保解析的xml为UTF-8格式  
	    writer = new XMLWriter(new FileOutputStream(new File(filePath)),format);//XMLWriter 指定输出文件以及格式    
		writer.write(document);//把document写入xmlFile指定的文件(可以为被解析的文件或者新创建的文件)  
		}catch(Exception e){
			e.printStackTrace();
		}finally{
		   try {
			if(writer!=null)
			writer.close();
		  } catch (IOException e) {
			e.printStackTrace();
		}  
		}
	}
}
