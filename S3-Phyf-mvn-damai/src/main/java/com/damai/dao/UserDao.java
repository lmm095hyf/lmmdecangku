package com.damai.dao;

import java.util.List;
import java.util.Map;

import com.damai.util.DBHelper;

public class UserDao {
	/*
	 * 将用户数据创建到数据库中
	 * 用户名
	 * 密码
	 * 头像
	 * 性别
	 * */
	public void insertuser(String ename,String cname,String password,String email,String phone,String sex) {
		String sql="insert into dm_user values(?,?,?,?,?,?,?,?)";
		DBHelper dbh=new DBHelper();
		dbh.update(sql,ename,cname,password,email,phone,sex);
	}

	public Map<String,Object> queryuser(String ename,String password){
    	String sql="select * from dm_user where ename=? and password=?";
    	DBHelper db=new DBHelper();
    	List<Map<String,Object>> list=db.query(sql, ename,password);
    	if(list.size()==0) {
    		return null;
    	}else {
    		Map<String,Object> user=list.get(0);
    		return user;
    	}	
    }
	
	public void forgetpwd(String password,String phone) {
		String sql="update dm_user set password=? where phone=?";
		new DBHelper().update(sql,password,phone);
	}
}
