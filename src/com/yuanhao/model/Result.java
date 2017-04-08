package com.yuanhao.model;

public class Result {
	//result  <name>属性
	private String resultName;
	//result  <type>属性-->forward/redirect....
	private String resultType;
	//result  <value>属性-->结果视图  xxx。jsp
	private String resultValue;
	
	public String getResultName() {
		return resultName;
	}
	public void setResultName(String resultName) {
		this.resultName = resultName;
	}
	public String getResultType() {
		return resultType;
	}
	public void setResultType(String resultType) {
		this.resultType = resultType;
	}
	public String getResultValue() {
		return resultValue;
	}
	public void setResultValue(String resultValue) {
		this.resultValue = resultValue;
	}
}
