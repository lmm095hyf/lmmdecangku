package com.yc.damai.dao;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Assert;
import org.junit.Test;

import com.damai.bean.DmCategory;
import com.damai.bean.DmProduct;

public class DMProductTest {
	private SqlSession session;
	//动态块
	{
		try {
			//mybatis配置文件
			String resource ="mybatis.xml";
			//读入配置文件
			InputStream inputStream=Resources.getResourceAsStream(resource);
			//构建会话工厂===设计模式  工厂模式
			SqlSessionFactory sqlSessionFactory=new SqlSessionFactoryBuilder().build(inputStream);

			session=sqlSessionFactory.openSession();
			
		}catch(IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	
	@Test
	public void test1() throws IOException {	
		List<DmProduct> list=session.selectList("com.yc.damai.dao.DMProductTest.selectAll");
		Assert.assertEquals(true,list.size()>0);
	}
	
	
	@Test
	public void test2() throws IOException {
		DmProduct dc=new DmProduct();
		dc.setId(2);
		dc.setPname("修改后的测试分类");
		dc.setCid(2);		
		session.update("com.yc.damai.dao.DMProductTest.update",dc);
		//不commit ，会话会在关闭时自动回滚
		session.commit();
		
	}
	

	@Test
	public void test3() throws IOException {
		DmProduct dc=new DmProduct();
		dc.setCid(555);
		dc.setCreatetime(null);
		dc.setImage(null);
		dc.setIsHot(1);
		dc.setMarketPrice(null);
		dc.setPdesc(null);
		dc.setShopPrice(null);
		dc.setId(null);			
		session.insert("com.yc.damai.dao.DMProductTest.insert",dc);
		session.commit();
	}
	
	@Test
	public void test4() throws IOException {
		DmProduct dc=new DmProduct();
		dc.setId(3);			
		session.delete("com.yc.damai.dao.DMProductTest.delete",dc);
		//不commit ，会话会在关闭时自动回滚
		session.commit();
		
	}
}
