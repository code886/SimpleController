package com.yuanhao.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.yuanhao.config.Conversation;
import com.yuanhao.model.UserBean;
import com.yuanhao.utils.JdbcUtil;


public class UserDao {
	/*//配置文件取数据库链接参数
	HashMap<String, String> map = new DbUtil().getProperty();
	//mysql数据库用户名和密码
	private String USER = map.get("USER");
	private String PASSWORD = map.get("PASSWORD");
	//mysql驱动
	private String DirverName = map.get("DriverName");
	//mysql链接地址
	private String URL = map.get("URL");*/
	
	/*
	 * private String username = "postgres";
	 * private String password = "admin";
	 * private String DirverName = "org.postgresql.Driver";
	 * private String linkAddress = "jdbc:postgresql://localhost:5432/test?useUnicode=true&characterEncoding=utf-8";
	 * */
	
	
	private Connection ct = null;//数据库链接
	private PreparedStatement ps = null;//sql语句
	private ResultSet rs = null;//执行sql结果集
		
	Conversation conversation = Conversation.getInstance();
	
	/**
	 * 打开数据库链接
	 * @return
	 */
	public Connection openDBConnection(){
		/*try {
			//connect to database
			Class.forName(DirverName);
			//get connection
			ct=DriverManager.getConnection(URL,USER,PASSWORD);
		}catch(Exception e){
			e.printStackTrace();
		}
		return ct;*/
		return JdbcUtil.getConnection();
	}
	
	/**
	 * 查询用户
	 * @param username 
	 * @return
	 */
	public UserBean queryUser(String username){
		/*UserBean userBean = null;
		try {
			ct = this.openDBConnection();
			System.out.println(ct==null);
			ps = ct.prepareStatement("select password from user where username='"+username+"'");
			System.out.println(ps.toString());
			rs = ps.executeQuery();
			if(rs.next()){
				//存在该用户名
				userBean = new UserBean();
				userBean.setUsername(username);
				userBean.setPassword(rs.getString(1));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.closeDBConnection();
		}
		return userBean;*/
		return conversation.queryUser(username);
	}
	
	/**
	 * 添加用户
	 * @param userBean
	 * @return
	 */
	public boolean insertUser(UserBean userBean){
		/*boolean b = false;
		//添加之前判断该用户名是否已经存在
		UserBean u = this.queryUser(userBean.getUsername());
		if(u==null){
			try {
				ct = this.openDBConnection();
				ps = ct.prepareStatement("insert into user (username,password) values ("
						+"'"+userBean.getUsername()+"','"+userBean.getPassword()+"')");
				int a  = ps.executeUpdate();
				if(a==1){
					b = true;
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally{
				this.closeDBConnection();
			}
		}
		return b;*/
		return conversation.insertUser(userBean);
	}
	
	/**
	 * 修改用户
	 * @param userBean
	 * @return
	 */
	public boolean updateUser(UserBean userBean){
		/*boolean b = false;
		//修改密码之前查询是否存在此用户
		UserBean u = this.queryUser(userBean.getUsername());
		if(u!=null){
			//存在
			try {
				ct = this.openDBConnection();
				ps = ct.prepareStatement("update user set password='"+userBean.getPassword()+
						"' where username='"+userBean.getUsername()+"'");
				int a = ps.executeUpdate();
				if(a==1){
					b = true;
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally{
				this.closeDBConnection();
			}
		}
		return b;*/
		return conversation.updateUser(userBean);
	}
	
	/**
	 * 删除用户
	 * @param userBean
	 * @return
	 */
	public boolean deleteUser(UserBean userBean){
		/*boolean b = false;
		//删除用户之前盘点该用户是否存在
		UserBean u = this.queryUser(userBean.getUsername());
		if(u!=null){
			try {
				ct = this.openDBConnection();
				ps = ct.prepareStatement("delete from user where username = '"+userBean.getUsername()+"'");
				int a  = ps.executeUpdate();
				if(a==1){
					b = true;
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally{
				this.closeDBConnection();
			}
		}
		return b;*/
		return conversation.deleteUser(userBean);
	}

	/**
	 * 关闭数据库链接
	 * @return
	 */
	public void closeDBConnection(){
		/*try {
			if(rs!=null){
				rs.close();
				rs=null;
			}
			if(ps!=null){
				ps.close();
				ps=null;
			}
			if(ct!=null){
				ct.close();
				ct=null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		JdbcUtil.close(ct, ps,rs);
	}
	
	//imitate login
		public UserBean login(UserBean userBean){
			if("admin".equals(userBean.getUsername())&&"admin".equals(userBean.getPassword())){
				return userBean;
			}else {
				return null;
			}
		}
		// imitate register
		public UserBean register(UserBean userBean){
			System.out.println("注册成功!用户名:"+userBean.getUsername());
			return userBean;
		}
}