package com.yuanhao.dependency;

import java.util.HashMap;
import java.util.Map;
/**
 * <bean>节点抽象类
 * @author YuanHao
 * @since 2017年1月6日
 */
public class DIMapping {
	private String name;
	private String classpath;
	private Map<String, DIProperty> properties = new HashMap<>();
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getClasspath() {
		return classpath;
	}
	public void setClasspath(String classpath) {
		this.classpath = classpath;
	}
	public Map<String, DIProperty> getProperties() {
		return properties;
	}
	public void setProperties(Map<String, DIProperty> properties) {
		this.properties = properties;
	}
	
}
