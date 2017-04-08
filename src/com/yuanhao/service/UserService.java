package com.yuanhao.service;


import com.yuanhao.dao.UserDao;
import com.yuanhao.model.UserBean;

public class UserService {
	private UserDao userDao = new UserDao();
	//imitate login
	public UserBean login(UserBean userBean){
		return userDao.queryUser(userBean.getUsername());
	}
	//imitate resiter
	public UserBean register(UserBean userBean){
		 boolean b = userDao.insertUser(userBean);
		 if(!b){
			 //插入失败
			 userBean = null; 
		 }
		 return userBean;
	}
}
