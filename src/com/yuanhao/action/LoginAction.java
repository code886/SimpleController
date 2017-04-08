package com.yuanhao.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yuanhao.interfaces.Action;
import com.yuanhao.model.UserBean;
import com.yuanhao.service.UserService;

public class LoginAction implements Action{
	private UserBean userBean;
	public UserBean getUserBean() {
		return userBean;
	}
	public void setUserBean(UserBean userBean) {
		this.userBean = userBean;
	}

	@Override
	public Object execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {		
		String flag = null;
		
		/*//1.1 获取用户名和密码
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		//1.2 封装到userbean对象
		UserBean userBean = new UserBean();
		userBean.setUsername(username);
		userBean.setPassword(password);*/
		
		//2 调用service方法
		UserService userService = new UserService();
		UserBean userInfo = new UserBean();
		userInfo = userService.login(userBean);
		
		//3 判断
		if(userInfo!=null&&userInfo.getPassword()==userBean.getPassword()){
			//login succeed
			request.getSession().setAttribute("userInfo", userInfo);
			flag = "success";
		}else {
			flag = "fail";
		}
		return flag;
	}
}
