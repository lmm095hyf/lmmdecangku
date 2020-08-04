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
import com.damai.bean.DmOrderitem;
import com.damai.bean.DmProduct;
import com.damai.dao.DmCategoryMapper;
import com.damai.dao.DmOrderitemMapper;
import com.damai.dao.DmProductMapper;

public class Mapper {
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
			
		List<DmProduct> list=session.selectList("com.damai.dao.DmProductMapper.selectAll");

		Assert.assertEquals(true,list.size()>0);
		
		for(DmProduct dp:list) {
			System.out.println(dp);
		}
	}
	
	@Test
	public void test2() throws IOException {
		DmCategory dc=new DmCategory();
		dc.setCname("测试分类");
		dc.setPid(1);
					
		//session.insert("com.yc.damai.dao.DMProductMapper.insert",dc);
		
		DmCategoryMapper mapper=session.getMapper(DmCategoryMapper.class);
		mapper.insert(dc);
		
		session.commit();
		session.close();
		
	}
	
	@Test
	public void test3() throws IOException {
		DmCategory dc=new DmCategory();
		dc.setId(55);
		dc.setCname("修改后的测试分类");
		dc.setPid(1);
					
		//session.update("com.yc.damai.dao.DMProductMapper.update",dc);
		
		DmCategoryMapper mapper=session.getMapper(DmCategoryMapper.class);
		mapper.update(dc);
		//不commit ，会话会在关闭时自动回滚
		session.commit();
		session.close();
		
	}
	
	@Test
	public void test4() throws IOException {
//		DmCategory dc=new DmCategory();
//		dc.setId(44);
//		dc.setCname("修改后的测试分类");
//		dc.setPid(1);		
		//session.delete("com.yc.damai.dao.DMProductMapper.delete",dc);
		DmCategoryMapper mapper=session.getMapper(DmCategoryMapper.class);
		mapper.delete(2);
		//不commit ，会话会在关闭时自动回滚
		session.commit();
		session.close();
		
	}
	
	@Test
	public void test5() throws IOException {
//		DmCategory dc=new DmCategory();
//		dc.setId(44);
//		dc.setCname("修改后的测试分类");
//		dc.setPid(1);		
		//session.delete("com.yc.damai.dao.DMProductMapper.delete",dc);
		//DmCategoryMapper mapper=session.getMapper(DmCategoryMapper.class);
		//mapper.delete(2);
		//不commit ，会话会在关闭时自动回滚
		//session.commit();
		
		/*DmOrderitemMapper dom=session.getMapper(DmOrderitemMapper.class);
		DmProductMapper dpm=session.getMapper(DmProductMapper.class);
		
		DmOrderitem doi=dom.selectById(59);
		DmProduct dp=dpm.selectById(doi.getPid());*/
		
		DmOrderitemMapper dom=session.getMapper(DmOrderitemMapper.class);
		DmOrderitem doi=dom.selectById(59);
		//java黑科技==>反射==>动态代理技术
		DmProduct dp=doi.getDmProduct();//调用dmProduct属性的get方法
		
		System.out.println(dp);
		session.close();
		
	}
	
	
	

}
