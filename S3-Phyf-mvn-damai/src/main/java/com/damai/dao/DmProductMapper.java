package com.damai.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.damai.bean.DmProduct;

public interface DmProductMapper {
	
//	DmProduct selectById(int id);
//	List<DmProduct> selectByObj(DmProduct dp);
//	List<DmProduct> selectByCids(@Param("cids") int [] cids);
//	int update(DmProduct dp);
	
	@Select("select * from dm_product")
	@Results(id="rmdp",value= {
			@Result(column="id",property = "id",id=true),
			@Result(column="market_price",property = "marketPrice"),
			@Result(column="shop_price",property = "shopPrice"),
			@Result(column="id_hot",property = "isHot")
	})List<DmProduct> selectAll();
	
	@Select("select * from dm_product where id=#{id}")
	@ResultMap("rmdp")
	DmProduct selectById(int id);
	
}
