package com.damai.dao;

import org.apache.ibatis.annotations.Insert;

import com.damai.bean.DmOrders;

public interface DmOrdersMapper {
	//int insert(DmOrders dos);
	
	@Insert("insert into dm_orders values (null,)")
}
