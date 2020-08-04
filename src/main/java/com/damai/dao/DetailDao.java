package com.damai.dao;

import java.util.List;
import java.util.Map;

import com.damai.util.DBHelper;

public class DetailDao {
	public List<Map<String,Object>> details(String detail){
		String sql="select * from dm_product where id=?";
		return new DBHelper().query(sql,detail);
	}
}
