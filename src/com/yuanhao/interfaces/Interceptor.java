package com.yuanhao.interfaces;

import com.yuanhao.interceptor.ActionInvocation;

public interface Interceptor {
	public Object intercept(ActionInvocation invocation) throws Throwable;
}
