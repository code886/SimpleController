package com.yuanhao.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;

public class DbUtil {
	
	public HashMap<String, String> getProperty(){
		HashMap<String, String> map = new HashMap<>();
		try {
			Properties properties = new Properties();
			InputStream in = this.getClass().getResourceAsStream("/mysql.properties");
			properties.load(in);
			Iterator<String> it = properties.stringPropertyNames().iterator();
			while(it.hasNext()){
				String key = it.next();
				map.put(key, properties.getProperty(key));
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}
}
