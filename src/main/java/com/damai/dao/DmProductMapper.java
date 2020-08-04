package com.damai.dao;

import java.util.List;

import com.damai.bean.DmProduct;



public interface DmProductMapper {
	List<DmProduct> selectAll();
	DmProduct selectById(int id);
}
