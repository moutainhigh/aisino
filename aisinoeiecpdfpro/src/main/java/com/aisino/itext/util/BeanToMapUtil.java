package com.aisino.itext.util;

import com.aisino.itext.pojo.StencilPlageMx;
import org.apache.commons.lang.StringUtils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class BeanToMapUtil {
//    public static void main(String[] args) throws IntrospectionException, IllegalAccessException, InvocationTargetException {
//        StencilPlageMx[] bean = new StencilPlageMx[]{new StencilPlageMx()};
//        Map map = new HashMap();
//        bean[0].setDj("30.00");
//        bean[0].setSl("3.0000");
//        convertBean1(bean, map);
//    }
	public static void convertBean(Object bean, Map<String, Object> map)
			throws IntrospectionException, IllegalAccessException,
			InvocationTargetException {
		Class<? extends Object> type = bean.getClass();
		BeanInfo beanInfo = Introspector.getBeanInfo(type);
		PropertyDescriptor[] propertyDescriptors = beanInfo
				.getPropertyDescriptors();
		for (int i = 0; i < propertyDescriptors.length; i++) {
			PropertyDescriptor descriptor = propertyDescriptors[i];
			String propertyName = descriptor.getName();
			if (!propertyName.equals("class")) {
				Method readMethod = descriptor.getReadMethod();
				Object result = readMethod.invoke(bean, new Object[0]);
				if (result != null && result instanceof StencilPlageMx[]) {
					convertBean1(result, map);
				}else{
					if (result != null) {
						map.put(propertyName, result);
					} else {
						map.put(propertyName, "");
					}
				}
			}
		}
	}
	public static void convertBean1(Object bean, Map<String, Object> map)
			throws IntrospectionException, IllegalAccessException,
			InvocationTargetException {
		Map<String, String> mapMx=new HashMap<String, String>();
		StencilPlageMx[] stencilPlageMx = (StencilPlageMx[])bean;
		for(int j = 0; j < stencilPlageMx.length;j++){
			Object obj = stencilPlageMx[j];
			Class<? extends Object> type = obj.getClass();
			BeanInfo beanInfo = Introspector.getBeanInfo(type);
			PropertyDescriptor[] propertyDescriptors = beanInfo
					.getPropertyDescriptors();
			for (int i = 0; i < propertyDescriptors.length; i++) {
				PropertyDescriptor descriptor = propertyDescriptors[i];
				String propertyName = descriptor.getName();
				if (!propertyName.equals("class")) {
					Method readMethod = descriptor.getReadMethod();
					Object result = readMethod.invoke(obj, new Object[0]);
						if (result != null) {
						    /**
	                         * 商品单价 小数点后都是0时，只显示2位即可，若小数点后不都为零，只显示到最后一个不为零的位。
	                         * @author 李帅
	                         * @Date 2013-12-10 14:40
	                         */
	                        String resStr = (String)result;
	                      //修改String串为空的判断
	                        if(( StringUtils.isNotEmpty(resStr) && "dj".equals(propertyName))){
	                            if(new Integer(resStr.substring(resStr.indexOf(".")+1, resStr.length())) == 0 && resStr.indexOf(".")>0){
	                                //如果小数点后全是0，则保留2位
	                                resStr = resStr.substring(0, resStr.indexOf("."));
	                                result = resStr + ".00  ";
	                            }else if(new Integer(resStr.substring(resStr.indexOf(".")+1, resStr.length())) > 0 && resStr.indexOf(".")>0){
	                                //把小数点后末尾 无效的0 去掉
	                                result = resStr.replaceAll("0*$","");
	                                //如果小数点后只有一位，补一个0
	                                if((((String)result).substring(((String)result).indexOf(".")+1, ((String)result).length())).length()==1){
	                                    result = result +"0  ";
	                                }else if((((String)result).substring(((String)result).indexOf(".")+1, ((String)result).length())).length()==0){
	                                    result = result +"00  ";//如果小数点后只有一位，补2个0
	                                }
	                            }else if(resStr.indexOf(".") < 0){   /*如果没有小数位*/
	                                result = resStr + ".00  ";
	                            }
	                          //修改String串为空的判断
	                        }else if( (StringUtils.isNotEmpty(resStr) && "sl".equals(propertyName))){
	                            if(new Integer(resStr.substring(resStr.indexOf(".")+1, resStr.length())) == 0 && resStr.indexOf(".")>0){
                                    //如果小数点后全是0，则取整
                                    resStr = resStr.substring(0, resStr.indexOf("."));
                                    result = resStr;
                                }else if(new Integer(resStr.substring(resStr.indexOf(".")+1, resStr.length())) > 0 && resStr.indexOf(".")>0){
                                    //把小数点后末尾 无效的0 去掉
                                    result = resStr.replaceAll("0*$","");
                                }else if(resStr.indexOf(".") < 0){   /*如果没有小数位 取整*/
                                    result = resStr;
                                }
	                        }
							mapMx.put(propertyName+"_"+j, (String)result);
						} else {
							mapMx.put(propertyName+"_"+j, "");
						}
					
				}
			}
		}
		map.put("mapMx", mapMx);
	}
}
