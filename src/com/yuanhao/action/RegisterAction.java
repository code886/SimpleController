package com.yuanhao.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yuanhao.interfaces.Action;
import com.yuanhao.model.UserBean;
import com.yuanhao.service.UserService;

public class RegisterAction implements Action{
	@Override
	public Object execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		//返回标记
		String flag = null;
		// 获取用户名和密码
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		// 封装到userbean
		UserBean userBean = new UserBean();
		userBean.setUsername(username);
		userBean.setPassword(password);
		
		// 调用service方法
		UserService userService = new UserService();
		UserBean userInfo = userService.register(userBean);
		if(userInfo!=null){
			flag = "success";
			request.getSession().setAttribute("userInfo", userInfo);
		}else {
			flag = "fail";
		}
		return flag;
	}
}