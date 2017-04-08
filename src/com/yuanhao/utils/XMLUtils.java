package com.yuanhao.utils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamSource;

import org.dom4j.Document;
import org.dom4j.io.DocumentResult;
import org.dom4j.io.DocumentSource;
import org.dom4j.io.HTMLWriter;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;

public class XMLUtils {
		      
	    /** 
	     * 将xml以xsl样式转化为html字符串  
	     * @param xmlString xml字符串 
	     * @param xslPath xsl路径 
	     * @return 
	     */  
	    public String getHtmlString(String xmlPath,String xslPath){  
	    	System.out.println("开始执行getHtmlString(...)方法");
	    	String xmlString = readToBuffer(xmlPath);
	        String returnDocStr = "";  
	        try {  
	            SAXReader reader = new SAXReader();  
	            ByteArrayInputStream bais = new ByteArrayInputStream(xmlString.getBytes());  
	            Document doc = reader.read(bais);  
	            Document transformDoc = this.transformDocument(doc,xslPath);  
	            returnDocStr = this.write2String(transformDoc);  
	            System.out.println("getHtmlString(...)执行成功!");
	        } catch (Exception e) {  
	        	e.printStackTrace();
	        }  
	        return returnDocStr;  
	    }  
	      
	    /** 
	     * 通过xsl将xml数据文件转化doc对象 
	     * @param doc xml文档对象 
	     * @param xslPath xsl文件路径 
	     * @return 
	     */  
	    public Document transformDocument(Document doc,String xslPath){  
	    	System.out.println("开始执行 transformDocument(...)方法");
	        TransformerFactory factory = TransformerFactory.newInstance();  
	        Document transformerDoc = null;  
	        try {  
	            Transformer transformer = factory.newTransformer(new StreamSource(xslPath));  
	            DocumentSource docSource = new DocumentSource(doc);  
	            DocumentResult docResult = new DocumentResult();  
	            transformer.transform(docSource, docResult);  
	            transformerDoc = docResult.getDocument();  
	            System.out.println("transformDocument(...)执行成功!");
	        } catch (Exception e) { 
	        	e.printStackTrace();
	        }  
	        return transformerDoc;  
	    }  
	      
	    /** 
	     * 将doc文档对象转化为html字符串 
	     * @param transformDoc doc文档 
	     * @return 
	     */  
	    public String write2String(Document transformDoc){  
	        System.out.println("开始执行 write2String(...)方法");
	        StringWriter strWriter = new StringWriter();  
	        OutputFormat format = OutputFormat.createPrettyPrint();  
	        format.setEncoding("UTF-8");  
	        format.setXHTML(true);  
	        HTMLWriter htmlWriter = new HTMLWriter(strWriter,format);  
	        format.setExpandEmptyElements(false);  
	        try {  
	            htmlWriter.write(transformDoc);  
	            htmlWriter.flush();  
	            System.out.println("write2String(...)执行成功!");
	        } catch (IOException e) {  
	        	e.printStackTrace();
	        }  
	        return strWriter.toString();  
	    } 
	    
	    /**
	     * 通过文件路径读出文件内容到字符串
	     * @param buffer
	     * @param filePath 文件路径
	     * @throws IOException
	     */
	    public String readToBuffer(String filePath){
	    	String str = "";
	        try {
				InputStream is = new FileInputStream(filePath);
				// 用来保存每行读取的内容
				String line; 
				BufferedReader reader = new BufferedReader(new InputStreamReader(is));
				// 读取第一行
				line = reader.readLine();
				while (line != null) { 
					str+=line; 
					str+="\n"; 
				    line = reader.readLine(); 
				}
				reader.close();
				is.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        return str;
	    } 
}
