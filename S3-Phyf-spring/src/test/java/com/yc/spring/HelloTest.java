package com.yc.spring;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.yc.spring.dao.UserDao;

public class HelloTest {
	
	@Test
	public void test() {
		//Hello h=new Hello();
		
		//从spring 框架中获取一个Hello对象
		//创建Spring容器对象
		
		ClassPathXmlApplicationContext ctx=new ClassPathXmlApplicationContext("beans.xml");
		
		Hello h=(Hello) ctx.getBean("hello");
		Hello h1=(Hello) ctx.getBean("hello");
		Hello h2=(Hello) ctx.getBean("hello");
		
		System.out.println(h1==h2);
		h.sayHello();
		
		ctx.close();
	}
	@Test
	public void test1() {
		ClassPathXmlApplicationContext ctx=new ClassPathXmlApplicationContext("beans.xml");
		
		UserDao udao1=(UserDao) ctx.getBean("mdao");
		UserDao udao2=(UserDao) ctx.getBean("mdao");

		udao1.selectUserId("zhangsan");
		udao2.selectUserId("zhangsan");
		
		ctx.close();
	}
}
