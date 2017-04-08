package com.yuanhao.config;

import java.util.List;

/**
 * 数据库表与UserBean的映射
 * @author YuanHao
 * @since 2016年12月26日
 */
public class ClassInfo {	
	private String name;
	private String table;
	private String id_name;
	private List<ColumnInfo> columnInfos;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTable() {
		return table;
	}
	public void setTable(String table) {
		this.table = table;
	}
	public String getId_name() {
		return id_name;
	}
	public void setId_name(String id_name) {
		this.id_name = id_name;
	}
	public List<ColumnInfo> getColumnInfos() {
		return columnInfos;
	}
	public void setColumnInfos(List<ColumnInfo> columnInfos) {
		this.columnInfos = columnInfos;
	}
	
}
