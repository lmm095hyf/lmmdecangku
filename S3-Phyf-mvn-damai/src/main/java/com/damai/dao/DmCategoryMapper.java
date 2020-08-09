package com.damai.dao;

import java.util.List;

import com.damai.bean.DmCategory;

public interface DmCategoryMapper {
	
	List<DmCategory> selectAll();
	
	int insert(DmCategory dc);
	
	int update(DmCategory dc);
	
	int delete(int id);
}
