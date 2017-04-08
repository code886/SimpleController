package com.yuanhao.dependency;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;

import javax.servlet.http.HttpServletRequest;

public class IntrospectManager {
	
	public static void getValueFromRequest(Object bean,String proName,HttpServletRequest request){		
		try {
			//从request中得到该属性的值
			String value = request.getParameter(proName);
			//BeanInfo 为beans中提供的Bean信息，PropertyDescriptor[]中为该属性所有的信息
			BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass(), Object.class);
			PropertyDescriptor[] descriptor = beanInfo.getPropertyDescriptors();
			for (int i = 0; i < descriptor.length; i++) {
				PropertyDescriptor propertyDescriptor = descriptor[i];
				//根据属性名得到该属性的setter方法，进行属性值注入
				if (propertyDescriptor.getName().equals(proName)) {
					propertyDescriptor.getWriteMethod().invoke(bean, value);
				}				
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void setBeanToProperty(Object bean,String proName,Object proOwner){
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(proOwner.getClass(),Object.class);
			PropertyDescriptor[] descriptor = beanInfo.getPropertyDescriptors();
			for (int i = 0; i < descriptor.length; i++) {
				PropertyDescriptor propertyDescriptor = descriptor[i];
				if (propertyDescriptor.getName().equals(proName)) {
					propertyDescriptor.getWriteMethod().invoke(proOwner, bean);
				}				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
