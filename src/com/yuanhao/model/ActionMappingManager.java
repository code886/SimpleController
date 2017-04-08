package com.yuanhao.model;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.dom4j.io.SAXReader;

import org.dom4j.Document;
import org.dom4j.Element;


//解析controller.xml文件
public class ActionMappingManager {
	//保存action集合
	private HashMap<String,ActionMapping> actions;
	public ActionMappingManager(){
		actions = new HashMap<String,ActionMapping>();
		this.Init();
	}
	
	public ActionMapping getActionMapping(String actionName){
		if(actionName==null){
			throw new RuntimeException("传入参数有误，请查看controller.xml文件路径是否在src目录下。");
		}
		ActionMapping actionMapping = actions.get(actionName);
		if(actionMapping==null){
			throw new RuntimeException("没有请求的action资源。");
		}
		return actionMapping;
	}
	
	//DOM4J读取配置文件
	private void Init() {
		// TODO Auto-generated method stub
		try {
			//1.得到阅读器
			SAXReader reader = new SAXReader();
			
			//2.加载xml文档
			InputStream in = this.getClass().getResourceAsStream("/controller.xml");
			Document document = reader.read(in);
			
			//3.获取根标签
			Element root = document.getRootElement();
			
			//4.1  得到根标签下所有action标签
			@SuppressWarnings("unchecked")
			List<Element> actionList = root.elements("action"); 
			//4.2  得到跟标签下的所有interceptor标签
			@SuppressWarnings("unchecked")
			List<Element> interceptorList = root.elements("interceptor");
			
			//5.遍历封装action
			for(Element ele_action : actionList){
				//5.1 新建ActionMapping对象
				ActionMapping actionMapping = new ActionMapping();
				
				//5.2 设置属性
				actionMapping.setActionName(ele_action.element("name").getText());
				actionMapping.setActionClassName(ele_action.element("class").element("name").getText());
				actionMapping.setActionMethod(ele_action.element("class").element("method").getText());
				
				// 5.3 封装当前action下的拦截器
				ArrayList<InterceptorMapping> interceptors = new ArrayList<InterceptorMapping>();
				@SuppressWarnings("unchecked")
				Iterator<Element> iterator = ele_action.elementIterator("interceptor-ref");
				while (iterator.hasNext()) {
					Element element = iterator.next();
					String interceptor_ref_name = element.element("name").getText();
					// 定义了拦截器
					if (interceptor_ref_name != null) {	
						// 开始对拦截器列表进行搜索过滤
						for (Element interceptor : interceptorList) {
							Element ele_interceptor = interceptor.element("name");
							String interceptorName = ele_interceptor.getText();
							if (interceptor_ref_name.equals(interceptorName)) {	
								// 匹配
								InterceptorMapping info = new InterceptorMapping();
								info.setInterceptorName(interceptorName);
								info.setInterceptorClass(interceptor.element("class").element("name").getText());
								info.setInterceptorMethod(interceptor.element("class").element("method").getText());
								interceptors.add(info);
								break;
							}
						}
					}
				}
				
				//5.3 结果视图
				HashMap<String, Result> results = new HashMap<String, Result>();
				
				//5.4 获取action下的所有可能结果视图
				@SuppressWarnings("unchecked")
				Iterator<Element> it = ele_action.elementIterator("result");
				while(it.hasNext()){
					//遍历result标签
					Element ele_result = it.next();
					
					//新建result对象，设置属性
					Result result = new Result();
					result.setResultName(ele_result.element("name").getText());
					result.setResultType(ele_result.element("type").getText());
					result.setResultValue(ele_result.element("value").getText());
					
					//添加到results集合
					results.put(result.getResultName(),result);
				}
				//5.5 设置actionMapping对象的results属性
				actionMapping.setResults(results);
				//5.5 设置actionMapping对象的interceptor属性
				actionMapping.setInterceptors(interceptors);
				//5.6 actionMapping添加到结果集合
				actions.put(actionMapping.getActionName(), actionMapping);
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}
}
