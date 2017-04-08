package com.yuanhao.config;

/**
 * Jdbc抽象类
 * @author YuanHao
 * @since 2016年12月26日
 */
public class JdbcInfo {
	private String driver_class;
	private String url_path;
	private String db_username;
	private String db_password;
	public String getDriver_class() {
		return driver_class;
	}
	public void setDriver_class(String driver_class) {
		this.driver_class = driver_class;
	}
	public String getUrl_path() {
		return url_path;
	}
	public void setUrl_path(String url_path) {
		this.url_path = url_path;
	}
	public String getDb_username() {
		return db_username;
	}
	public void setDb_username(String db_username) {
		this.db_username = db_username;
	}
	public String getDb_password() {
		return db_password;
	}
	public void setDb_password(String db_password) {
		this.db_password = db_password;
	}
	
}
