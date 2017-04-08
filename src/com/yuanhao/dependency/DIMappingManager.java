package com.yuanhao.dependency;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;


public class DIMappingManager {
	private Map<String, DIMapping> beans;
	
	//构造方法获取所有bean节点对象
	public DIMappingManager(){
		beans = new HashMap<>();
		this.Init();
	}
	
	//匹配<bean>节点
	public DIMapping getDIMapping(String name){
		if(name==null){
			throw new RuntimeException("传入参数有误，请查看di.xml文件路径是否在src目录下。");
		}
		DIMapping diMapping = beans.get(name);
		if(diMapping==null){
			throw new RuntimeException("没有请求的bean资源。");
		}
		return diMapping;
	}
	
	//解析过程
	public void Init(){
		try {
			SAXReader reader = new SAXReader();
			InputStream inputStream = this.getClass().getResourceAsStream("/di.xml");
			Document document = reader.read(inputStream);
			Element root = document.getRootElement();
			
			//封装bean
			@SuppressWarnings("unchecked")
			List<Element> beanList = root.elements("bean");
			for (Element element : beanList) {
				DIMapping diMapping = new DIMapping();
				String name =  element.elementText("name");
				String classPath = element.elementText("class");
				//把property封装成集合对象
				Map<String, DIProperty> properties = new HashMap<>();
				@SuppressWarnings("unchecked")
				List<Element> propertyList = element.elements("property");
				for (Element element2 : propertyList) {
					//封装单个property对象
					DIProperty diProperty = new DIProperty();
					String propertyName = element2.elementText("name");
					String refClass = element2.elementText("ref-class");
					diProperty.setName(propertyName);
					diProperty.setRefClass(refClass);
					//property写入集合
					properties.put(propertyName, diProperty);
				}
				//封装Bean
				diMapping.setName(name);
				diMapping.setClasspath(classPath);
				diMapping.setProperties(properties);
				//bean写入集合对象
				beans.put(name, diMapping);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}	
