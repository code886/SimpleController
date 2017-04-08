package com.yuanhao.interceptor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.ArrayList;

import com.yuanhao.model.InterceptorMapping;
import com.yuanhao.interceptor.ActionInvocation;
public class ActionProxy implements InvocationHandler{

	private Object obj;		// 调用对象
	private Object[] args;	// 参数
	private Method method;	// 调用方法
	
	// 调用方式
	private ActionInvocation invocation;
	
	/**
	 * 构造方法
	 * @param object	对象
	 * @param interceptors 拦截器列表
	 * @param actionName 事件名称
	 * @param methodName 方法名称
	 * @param executeResult 是否返回执行结果
	 */
	public ActionProxy(Object object, ArrayList<InterceptorMapping> interceptors,String actionName, String methodName) {
		this.obj = object;
		this.invocation = new ActionInvocation(this,interceptors,actionName,methodName);
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		// TODO Auto-generated method stub
		this.method = method;
		this.args = args;
		
		System.out.println("before");
		Object result = invocation.invoke();
		System.out.println("after");
		return result;
	}

	public Method getMethod() {
		// TODO Auto-generated method stub
		return method;
	}

	public Object[] getArgs() {
		// TODO Auto-generated method stub
		return args;
	}

	public Object getObj() {
		// TODO Auto-generated method stub
		return obj;
	}

}
