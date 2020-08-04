package com.damai.dao;

import java.util.List;
import java.util.Map;

import com.damai.util.DBHelper;

public class cataDao {
	public List<Map<String,Object>> catagory(String cata){
		String sql="select * from dm_product where cid=?";
		return new DBHelper().query(sql,cata);
	}
}
