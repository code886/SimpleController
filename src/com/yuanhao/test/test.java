package com.yuanhao.test;

import com.yuanhao.dao.UserDao;
import com.yuanhao.model.UserBean;
import com.yuanhao.utils.*;


public class test {
	public static void main(String[] args) {
		test4();
		test5();
	}
	
	public static void test1(){
		XMLUtils xmlUtils = new XMLUtils();
		//得到路径：/E:/workspace/workspace_J2EE/SimpleController/build/classes/
		String projectPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
		System.out.println(projectPath);
        projectPath = projectPath.substring(0, projectPath.lastIndexOf("/"));  
        projectPath = projectPath.substring(0, projectPath.lastIndexOf("/"));
        //WebRoot根路径：/E:/workspace/workspace_J2EE/SimpleController/
        projectPath = projectPath.substring(0, projectPath.lastIndexOf("/") + 1); 
        System.out.println(projectPath);
        String xslPath = projectPath+"WebContent/pages/success_view.xsl"; 
        String xmlPath = projectPath+"WebContent/pages/success_view.xml";
        String str = xmlUtils.getHtmlString(xmlPath, xslPath);  
        System.out.println("str=="+str);
	}
	
	public static void test2(){
		UserDao userDao = new UserDao();
		UserBean userBean = userDao.queryUser("admin");
		System.out.println(userBean.getUsername()+userBean.getPassword());
	}
	
	public static void test3(){
		UserDao userDao = new UserDao();
		UserBean userBean = new UserBean("sb1","123");
		userDao.insertUser(userBean);
		System.out.println("insert success!");
	}
	
	public static void test4(){
		UserDao userDao = new UserDao();
		UserBean userBean = new UserBean("sb1","123");
		userDao.deleteUser(userBean);
		System.out.println("delete success!");
	}
	
	public static void test5(){
		UserDao userDao = new UserDao();
		UserBean userBean = new UserBean("sb1","123456");
		userDao.updateUser(userBean);
		System.out.println("update success!");
	}
}
