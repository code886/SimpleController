package com.yuanhao.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;

import com.yuanhao.config.Conversation;
import com.yuanhao.config.ORMapping;

/**
 * jdbc工具类
 */
public class JdbcUtil {
	private static String url = null;
	private static String user = null;
	private static String password = null;
	private static String driverClass = null;
	
	/**
	 * 静态代码块中（只加载一次）
	 */
	static{
		try {
			Conversation conversation = Conversation.getInstance();
			if (conversation != null) {
				ORMapping orMapping = conversation.getOrMapping();
				if (orMapping != null) {
					url = orMapping.getJdbcInfo().getUrl_path();
					user = orMapping.getJdbcInfo().getDb_username();
					password = orMapping.getJdbcInfo().getDb_password();
					driverClass = orMapping.getJdbcInfo().getDriver_class();
				}
			}
			//注册驱动程序
			Class.forName(driverClass);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("驱程程序注册出错");
		}
	}

	/**
	 * 抽取获取连接对象的方法
	 */
	public static Connection getConnection(){
		try {
			Connection conn = DriverManager.getConnection(url, user, password);
			return conn;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 释放资源的方法
	 */
	public static boolean close(Connection conn,PreparedStatement stmt){
		if(stmt!=null){
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
		if(conn!=null){
			try {
				conn.close();
				return true;
			} catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
		return false;
	}
	
	public static void close(Connection conn,PreparedStatement stmt,ResultSet rs){
		if(rs!=null)
			try {
				rs.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
				throw new RuntimeException(e1);
			}
		if(stmt!=null){
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
		if(conn!=null){
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
	}
}
