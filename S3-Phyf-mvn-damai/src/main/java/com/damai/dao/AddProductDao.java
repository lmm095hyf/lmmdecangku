package com.damai.dao;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import com.damai.util.DBHelper;
import com.google.gson.JsonElement;

public class AddProductDao {
	char c=98;
	float d=12.98f;
	byte m=12;
	
	public int AddProduct(String pname,Double mprice,Double sprice,String image,String pdesc,Integer ishot,Timestamp createtime,Integer cid){
		String sql="insert into dm_product values(null,?,?,?,?,?,?,?,?)";
		return new DBHelper().update(sql,pname,mprice,sprice,image,pdesc,ishot,createtime,cid);
	}
	
	

}
