package com.yuanhao.config;

import java.util.List;

/**
 * or_mapping.xml文件的抽象类
 * @author YuanHao
 * @since 2016年12月26日
 */
public class ORMapping {
	private List<ClassInfo> classInfos;
	private JdbcInfo jdbcInfo;
	public List<ClassInfo> getClassInfos() {
		return classInfos;
	}
	public void setClassInfos(List<ClassInfo> classInfos) {
		this.classInfos = classInfos;
	}
	public JdbcInfo getJdbcInfo() {
		return jdbcInfo;
	}
	public void setJdbcInfo(JdbcInfo jdbcInfo) {
		this.jdbcInfo = jdbcInfo;
	}
	
}
