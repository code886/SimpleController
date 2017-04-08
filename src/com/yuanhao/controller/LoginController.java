package com.yuanhao.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yuanhao.dependency.DIMapping;
import com.yuanhao.dependency.DIMappingManager;
import com.yuanhao.dependency.DIProperty;
import com.yuanhao.dependency.IntrospectManager;
import com.yuanhao.interceptor.ActionProxy;
import com.yuanhao.interfaces.Action;
import com.yuanhao.model.ActionMapping;
import com.yuanhao.model.ActionMappingManager;
import com.yuanhao.model.Result;
import com.yuanhao.utils.XMLUtils;

/**
 * Servlet implementation class LoginController
 */
@WebServlet("/LoginController")
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ActionMappingManager actionMappingManager;
	private DIMappingManager diMappingManager;
    
	//run only once when starting
    @Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
    	System.out.println("LoginController.init()");
		actionMappingManager = new ActionMappingManager();
		System.out.println("di.xml init()");
		diMappingManager = new DIMappingManager();
	}

	/**
     * @see HttpServlet#HttpServlet()
     */
    public LoginController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			Object obj = null;
			//1. 获取uri
			String uri = request.getRequestURI();
			System.out.println("1. uri:"+uri);
			String actionName = uri.substring(uri.lastIndexOf("/")+1, uri.indexOf(".scaction"));
			//打印actionName
			System.out.println("2. actionName:"+actionName);
			
			//2. 根据actionName得到类
			ActionMapping actionMapping = actionMappingManager.getActionMapping(actionName);
			String actionClassName = actionMapping.getActionClassName();
			System.out.println("3. actionClassName:"+actionClassName);
			//得到处理请求的方法
			String actionMethod = actionMapping.getActionMethod();
			System.out.println("4. actionMedthod:"+actionMethod);
			//3. 反射
			Class<?> clazz = Class.forName(actionClassName);//return a class
			obj = clazz.newInstance();//return an object
			//得到di.xml中的bean
			DIMapping diMapping = diMappingManager.getDIMapping(actionName);
			if(diMapping!=null){
				//di.xml有此action映射，尝试获取它的依赖bean
				Map<String, DIProperty> properties = diMapping.getProperties();
				//存放存在依赖的对象
				List<String> list_dependency = new ArrayList<String>();
				//遍历propertis，判断是否有依赖
				Iterator<Entry<String, DIProperty>> it = properties.entrySet().iterator();
				while (it.hasNext()) {
					Map.Entry<String,DIProperty> entry = (Entry<String, DIProperty>) it.next();
					String className = entry.getKey();
					DIProperty diProperty = entry.getValue();
					if(diProperty.getRefClass()!=null){
						//如果有依赖，则需先通过反射构造被依赖的<bean>实例，之后再构造依赖<bean>
						list_dependency.add(className);
						DIMapping refBean = diMappingManager.getDIMapping(diProperty.getRefClass());
						String refBean_classPath = refBean.getClasspath();
						Object obj_refBean = Class.forName(refBean_classPath).newInstance();
						
						Map<String, DIProperty> ref_properties = refBean.getProperties();
						Iterator<Entry<String, DIProperty>> ref_it = ref_properties.entrySet().iterator();
						while (ref_it.hasNext()) {
							Map.Entry<String, DIProperty> ref_enty = (Entry<String, DIProperty>) ref_it.next();
							DIProperty ref_property = ref_enty.getValue();
							IntrospectManager.getValueFromRequest(obj_refBean, ref_property.getName(), request);
						}
						IntrospectManager.setBeanToProperty(obj_refBean, className, obj);
					}else {
						IntrospectManager.getValueFromRequest(obj, className, request);
					}
				}
			}
			//动态代理
			ActionProxy handler = new ActionProxy(obj,actionMapping.getInterceptors(), actionName, actionMethod);
			Action proxyObj = (Action)Proxy.newProxyInstance(obj.getClass().getClassLoader(), obj.getClass().getInterfaces(), handler);
			//4. 根据返回标记选择相应结果视图【success】
			String returnFlag = (String)proxyObj.execute(request, response);
			Result result = actionMapping.getResults().get(returnFlag);
			//返回类型 【forward/redirect...】
			String resultType = result.getResultType();
			//返回视图 【login_success.jsp...】
			String resultValue = result.getResultValue();
			
			//作单个判断，请求为login，返回值为success才解析xml并推送
			if("login".equals(actionName)&&"success".equals(returnFlag)){
				XMLUtils xmlUtils = new XMLUtils();
				//路径：/F:/apache-tomcat-8.0.30/webapps/SimpleController/WEB-INF/classes/
				String projectPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
				System.out.println(projectPath);
				//拼接路径
				projectPath = projectPath.substring(0, projectPath.lastIndexOf("/"));  
		        projectPath = projectPath.substring(0, projectPath.lastIndexOf("/"));
		        //WebRoot根路径：/F:/apache-tomcat-8.0.30/webapps/SimpleController/
		        projectPath = projectPath.substring(0, projectPath.lastIndexOf("/") + 1); 
		        System.out.println(projectPath);
		        String xslPath = projectPath+"pages/success_view.xsl"; 
		        String xmlPath = projectPath+resultValue;
		        String str = xmlUtils.getHtmlString(xmlPath, xslPath);
		        PrintWriter printWriter = response.getWriter();
		        printWriter.write(str);
		        return;
			}
			//跳转
			if("redirect".equals(resultType)){
				response.sendRedirect(request.getContextPath()+"/"+resultValue);
			}else {
				request.getRequestDispatcher(resultValue).forward(request, response);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
