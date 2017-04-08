package com.yuanhao.config;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;


/**
 * or_mapping.xml解析类
 * @author YuanHao
 * @since 2016年12月26日
 */
public class Configuration {
	private ORMapping orMapping;
	
	public Configuration(){
		this.Init();
	}
	
	/**
	 * 解析过程
	 */
	public void Init(){
		try {
			orMapping = new ORMapping();
			//dom4j解析
			SAXReader reader = new SAXReader();
			InputStream in = this.getClass().getResourceAsStream("/or_mapping.xml");
			Document doc = reader.read(in);
			
			Element root = doc.getRootElement();
			
			Element ele_jdbc = root.element("jdbc");
			
			//读取jdbc的property属性，存入list
			@SuppressWarnings("unchecked")
			List<Element> propertis = ele_jdbc.elements("property");
			//解析出的name+value放入map集合
			Map<String, String> map = new HashMap<>();
			for (Element element : propertis) {
				String name = element.element("name").getText();
				String value = element.element("value").getText();
				map.put(name, value);
			}
			//设置jdbcInfo的各项属性
			JdbcInfo jdbcInfo = new JdbcInfo();
			//jar包提供的方法【org.apache.commons.beanutils.BeanUtils】
			BeanUtils.populate(jdbcInfo, map);
			orMapping.setJdbcInfo(jdbcInfo);
			
			@SuppressWarnings("unchecked")
			List<Element> classList = root.elements("class");
			//封装class
			List<ClassInfo> classInfos = new ArrayList<>();
			for (Element element : classList) {
				//封装classInfo
				ClassInfo classInfo = new ClassInfo();
				classInfo.setName(element.element("name").getText());
				classInfo.setTable(element.element("table").getText());
				classInfo.setId_name(element.element("id").element("name").getText());
				
				//封装table的列
				@SuppressWarnings("unchecked")
				List<Element> columnList = element.elements("property");
				//封装列属性
				List<ColumnInfo> columnInfos = new ArrayList<>();
				for (Element column : columnList) {
					ColumnInfo columnInfo = new ColumnInfo();
					columnInfo.setName(column.element("name").getText());
					columnInfo.setColumn(column.element("column").getText());
					columnInfo.setType(column.element("type").getText());
					columnInfo.setLazy(column.element("lazy").getText());
					//添加列属性到集合
					columnInfos.add(columnInfo);
				}
				//设置table的列属性
				classInfo.setColumnInfos(columnInfos);
				//添加class到集合
				classInfos.add(classInfo);
			}
			orMapping.setClassInfos(classInfos);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 获得Conversation单例对象
	 */
	public Conversation createConversation() {
		Conversation conversation = Conversation.getInstance();
		conversation.setOrMapping(orMapping);
		return conversation;
	}
}
