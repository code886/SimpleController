package com.yuanhao.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.yuanhao.config.Configuration;
import com.yuanhao.config.Conversation;

/**
 * Tomcat监听器，服务器启动时执行，使用注解的方式注册
 * @author YuanHao
 * @since 2016年12月27日
 */
@WebListener
public class TomcatListener implements ServletContextListener {

    public void contextInitialized(ServletContextEvent sce)  { 
    	System.out.println("Tomcat starting....");
    	Configuration configuration = new Configuration();
    	Conversation conversation = configuration.createConversation();
    	System.out.println("Conversation初始化成功..."+conversation);
    	System.out.println("===="+conversation.getOrMapping());
    }

    public void contextDestroyed(ServletContextEvent sce)  { 
    	System.out.println("Tomcat destory....");
    }
}
