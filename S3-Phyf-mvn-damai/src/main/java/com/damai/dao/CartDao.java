package com.damai.dao;

import java.util.List;

import com.damai.util.DBHelper;

public class CartDao {

	//给某用户添加新的购物车商品
	public int insert(String uid, String pid) {
		String sql = "insert into dm_cart values(null,?,?,1)";
		return new DBHelper().update(sql, uid, pid);
	}

	public List<?> queryByUid(String uid){
		String sql = "select * from dm_cart a join dm_product b on a.pid=b.id where a.uid=?";
		return new DBHelper().query(sql, uid); 
	}
	
	//给某个用户的购物车商品数量 + 1
	 
	public int update(String uid, String pid) {
		String sql = "update dm_cart set count=count + 1 where uid=? and pid=?";
		return new DBHelper().update(sql, uid, pid);
	}
	//删除操作
	public int del(String id) {
		String sql = "delete from dm_cart where id=?";
		return new DBHelper().update(sql, id);
	}

	public int deleteByUid(String uid) {
		String sql = "delete from dm_cart where uid=?";
		return new DBHelper().update(sql, uid);
	}
}