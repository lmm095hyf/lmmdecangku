package com.damai.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.damai.bean.DmProduct;

public interface DmProductMapper {
	List<DmProduct> selectAll();
	DmProduct selectById(int id);
	List<DmProduct> selectByObj(DmProduct dp);
	List<DmProduct> selectByCids(@Param("cids") int [] cids);
	int update(DmProduct dp);
}
