package com.yuanhao.interceptor;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.yuanhao.dao.LogWriterDao;
import com.yuanhao.interfaces.Interceptor;
import com.yuanhao.model.LogInfo;


public class LogWriter implements Interceptor{
	/**
	 * 记录客户端每次访问action名称、类型、开始时间、结束时间、请求返回的result值
	 * <log>
	 * 		<action>
	 * 			<name>login</name>
	 *  		<s-time>2013-12-04 14:20:56</s-time>
	 *   		<e-time>2013-12-04 14:20:59</e-time>
	 *   		<result>success</result>
	 * 		</action>
	 * </log>
	 */
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public void log(ActionInvocation invocation,Object result,String startTime) {
		LogInfo logInfo = new LogInfo();
		logInfo.setActionName(invocation.getActionName());
		if (result!=null) {	
			// 开始之后
			logInfo.setStartTime(startTime);
			logInfo.setEndTime(sdf.format(new Date()));
			logInfo.setResult((String)result);
		}
		//测试输出
		System.out.println("LogInfo:"+logInfo.getActionName()+" "+logInfo.getStartTime()+" "+logInfo.getEndTime());
		//写入日志,调用dao方法
		LogWriterDao logWriterDao = new LogWriterDao();
		logWriterDao.addLog(logInfo);
	}

	@Override
	public Object intercept(ActionInvocation invocation) throws Throwable {
		System.out.println("LogWriter拦截器执行之前...");
		String startTime = sdf.format(new Date());
		//记录返回的result
		Object result;
		result = invocation.invoke();
		System.out.println("LogWriter拦截器执行之后..."+result);
		//写入result
		log(invocation, result,startTime);	
		return result;
	}
}
