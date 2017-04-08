package com.yuanhao.dependency;
/**
 * <property>节点抽象类
 * @author YuanHao
 * @since 2017年1月6日
 */
public class DIProperty {
	private String name;
	private String refClass;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRefClass() {
		return refClass;
	}
	public void setRefClass(String refClass) {
		this.refClass = refClass;
	}
}
