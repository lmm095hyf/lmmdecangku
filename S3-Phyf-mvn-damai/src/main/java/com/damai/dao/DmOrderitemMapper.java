package com.damai.dao;

import java.util.List;

import com.damai.bean.DmOrderitem;
import com.damai.bean.DmProduct;

public interface DmOrderitemMapper {
	List<DmOrderitem> selectAll();
	DmOrderitem selectById(int id);
	
	//新增订单明细
	int insert(DmOrderitem doi);
	
}
