package com.yuanhao.interfaces;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Action {
	//action接口，所有action都要实现此接口的方法
	public Object execute(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException;
}
