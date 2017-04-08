package com.yuanhao.interceptor;

import java.lang.reflect.Method;
import java.util.ArrayList;

import com.yuanhao.interfaces.Interceptor;
import com.yuanhao.model.InterceptorMapping;

public class ActionInvocation {
	ArrayList<Interceptor> interceptors = new ArrayList<Interceptor>();
	ArrayList<InterceptorMapping> infos = new ArrayList<InterceptorMapping>();
	
	private String actionName;	// action名称
	
	public String getActionName() {
		return actionName;
	}
	
	int index = -1;          //堆栈执行拦截器
	ActionProxy actionProxy;
	
	/**
	 * 构造方法
	 * @param actionProxy ActionProxy参数
	 * @param interceptors 拦截器列表
	 * @throws Exception 
	 */
	public ActionInvocation(ActionProxy actionProxy,ArrayList<InterceptorMapping> interceptors,String actionName, String actionType) {
		
		convertInterceptors(interceptors);
		this.actionProxy = actionProxy;
		this.infos = interceptors;
		this.actionName = actionName;
	}
	
	// 转换
	private void convertInterceptors(ArrayList<InterceptorMapping> infos) {
		
		for (InterceptorMapping interceptorInfo : infos) {
			Class<?> clazz;
			try {
				clazz = Class.forName(interceptorInfo.getInterceptorClass());
				Object obj = clazz.newInstance();
				this.interceptors.add((Interceptor) obj);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		}
		
	}
	
	//此方法实现了递归执行拦截器，用index判断是否有剩余拦截器未执行
	public Object invoke() throws Throwable {
		index ++;
		Object result = null;
		if(index >= this.interceptors.size()) {
			Method method = actionProxy.getMethod();
			Object[] args = actionProxy.getArgs();
			Object object = actionProxy.getObj();
			result = method.invoke(object, args);
		} else {
			result = this.interceptors.get(index).intercept(this);
		}
		return result;
	}
}
