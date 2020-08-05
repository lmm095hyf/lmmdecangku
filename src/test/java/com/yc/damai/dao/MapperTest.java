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
import com.damai.bean.DmOrders;
import com.damai.bean.DmProduct;
import com.damai.dao.DmCategoryMapper;
import com.damai.dao.DmOrderitemMapper;
import com.damai.dao.DmOrdersMapper;
import com.damai.dao.DmProductMapper;

public class MapperTest {
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
		/**
		 * 1. 先查出一个订单明细记录
		 * 2. 查出该订单明细对应的商品信息
		 */
		
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
		/**
		 * 测试驱动开发  ==> 先写好所有的测试代码 ==> 再业务代码 
		 */
		DmOrderitemMapper dom=session.getMapper(DmOrderitemMapper.class);
		
		DmOrderitem doi=dom.selectById(59);
		//java黑科技==>反射==>动态代理技术
		DmProduct dp=doi.getDmProduct();//调用dmProduct属性的get方法
		
		System.out.println(dp);
		session.close();
		
	}
	
	@Test
	public void test6() throws IOException {
		/**
		 * 1. 先查出一个订单明细记录
		 * 2. 查出该订单明细对应的商品信息
		
		 * 测试驱动开发  ==> 先写好所有的测试代码 ==> 再业务代码 
		 */
		
		DmCategoryMapper mapper=session.getMapper(DmCategoryMapper.class);
		List<DmCategory> dcList=mapper.selectAll();
		System.out.println("===========1=========");
		DmCategory dc=dcList.get(1);
		System.out.println("===========2=========");
		Assert.assertEquals("珠宝配饰", dc.getCname());
		System.out.println("===========3=========");
		Assert.assertEquals(4, dc.getChildren().size());
		System.out.println("===========3========");
	}
	
	@Test
	public void test7() throws IOException {
		/**
		 * 1. 先查出一个订单明细记录
		 * 2. 查出该订单明细对应的商品信息
		
		 * 测试驱动开发  ==> 先写好所有的测试代码 ==> 再业务代码 
		 */
		DmProductMapper mapper=session.getMapper(DmProductMapper.class);
		System.out.println("=====================");
		mapper.selectByObj(null);
		DmProduct dp=new DmProduct();
		System.out.println("=============");
		mapper.selectByObj(dp);
		
		dp.setPname("测试");
		System.out.println("=============");
		mapper.selectByObj(dp);
		
		dp.setPdesc("测试描述");
		System.out.println("=============");
		mapper.selectByObj(dp);
		
		dp.setIsHot(-1);
		System.out.println("=============");
		mapper.selectByObj(dp);
		
		dp.setIsHot(1);
		System.out.println("=============");
		mapper.selectByObj(dp);
	}
	
	@Test
	public void test8() throws IOException {
		DmProductMapper mapper=session.getMapper(DmProductMapper.class);
		int [] cids= {1,2,3};
		mapper.selectByCids(cids);
	
	}
	
	@Test
	public void test9() throws IOException {
		DmProductMapper mapper=session.getMapper(DmProductMapper.class);
		DmProduct dp=new DmProduct();
		
		dp.setId(4);
		dp.setMarketPrice(885d);
		mapper.update(dp);
		
		DmProduct dbdp=mapper.selectById(4);
		
		Assert.assertEquals((Double)885d, dbdp.getMarketPrice());
		Assert.assertEquals((Double)119d, dbdp.getShopPrice());
		Assert.assertEquals("韩版女装翻领羔绒夹棉格子毛呢外套", dbdp.getPname());

	}
	
	@Test
	public void test10() throws IOException {
		DmOrderitemMapper mapper=session.getMapper(DmOrderitemMapper.class);
		
		DmOrderitem doi=new DmOrderitem();
		
		doi.setId(68);
		
		DmOrderitem db=mapper.selectById(68);
	

	}
	
	@Test
	public void test11() throws IOException {
		DmOrdersMapper dosm=session.getMapper(DmOrdersMapper.class);
		DmOrderitemMapper doim=session.getMapper(DmOrderitemMapper.class);
		
		DmOrderitem doi1=new DmOrderitem();
		doi1.setPid(1);
		doi1.setCount(1);
		doi1.setTotal(100d);
		DmOrderitem doi2=new DmOrderitem();
		doi2.setPid(2);
		doi2.setCount(1);
		doi2.setTotal(200d);
		
		DmOrders dos=new DmOrders();
		dos.setTotal(300d);
		dos.setAid(1);
		dos.setState(1);
		dos.setUid(1);
		
		try {
			dosm.insert(dos);
			doi1.setOid(dos.getId());
			doi2.setOid(dos.getId());
			
			doim.insert(doi1);
			doim.insert(doi2);
			session.commit();
		}catch(Exception e) {
			e.printStackTrace();
			session.rollback();
		}finally {
			session.close();
		}
		
		
	}
	
	

}
