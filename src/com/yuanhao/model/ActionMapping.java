package com.yuanhao.model;

import java.util.ArrayList;
import java.util.HashMap;


public class ActionMapping {
	//action <name>属性
	private String actionName;
	//class <name>属性
	private String actionClassName;
	//class <method>属性
	private String actionMethod;
	//result结果集合
	private HashMap<String, Result> results;
	//拦截器集合
	private ArrayList<InterceptorMapping> interceptors;
	
	public String getActionName() {
		return actionName;
	}
	public void setActionName(String actionName) {
		this.actionName = actionName;
	}
	public String getActionClassName() {
		return actionClassName;
	}
	public void setActionClassName(String actionClassName) {
		this.actionClassName = actionClassName;
	}
	public String getActionMethod() {
		return actionMethod;
	}
	public void setActionMethod(String actionMethod) {
		this.actionMethod = actionMethod;
	}
	public HashMap<String, Result> getResults() {
		return results;
	}
	public void setResults(HashMap<String, Result> results) {
		this.results = results;
	}
	public ArrayList<InterceptorMapping> getInterceptors() {
		return interceptors;
	}
	public void setInterceptors(ArrayList<InterceptorMapping> interceptors) {
		this.interceptors = interceptors;
	}
		
}
