package com.yuanhao.dao;

import java.io.File;
import java.io.FileOutputStream;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import com.yuanhao.model.LogInfo;

public class LogWriterDao {
	public void addLog(LogInfo logInfo){
		// TODO Auto-generated method stub
			try {
				File file = new File("F:/作业相关/J2EE/log.xml");
				Document doc = null;
				Element rootElem = null;
				if(!file.exists()){
					//如果没有xml文件，则创建xml文件
					doc = DocumentHelper.createDocument();
					//创建根标签
					rootElem = doc.addElement("log");
				}else{
					//如果有xml文件，则读取xml文件
					doc = new SAXReader().read(file);
					//如果有xml文件，读取根标签
					rootElem = doc.getRootElement();
				}
				//添加action标签
				Element actionElem = rootElem.addElement("action");
				actionElem.addElement("name").setText(logInfo.getActionName());
				actionElem.addElement("s-time").setText(logInfo.getStartTime());
				actionElem.addElement("e-time").setText(logInfo.getEndTime());
				actionElem.addElement("result").setText(logInfo.getResult());
				//写出到xml【绝对路径】
				FileOutputStream fileOutputStream = new FileOutputStream(file); 
				//设置写出格式和编码，保证中文不乱码
				OutputFormat outputFormat = OutputFormat.createPrettyPrint();
				outputFormat.setEncoding("utf-8");
				//获取写工具
				XMLWriter writer = new XMLWriter(fileOutputStream,outputFormat);
				writer.write(doc);
				//关闭流
				writer.close();
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
	}
