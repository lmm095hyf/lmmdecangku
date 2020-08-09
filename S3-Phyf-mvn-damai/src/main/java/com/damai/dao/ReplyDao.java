package com.damai.dao;

import java.util.List;

import com.damai.util.DBHelper;
import com.damai.web.Reply;

public class ReplyDao {
	DBHelper dbh=new DBHelper();
	
	public int countPages(String pid) {
		DBHelper dbh = new DBHelper();
		// System.out.println(cid);
		String sql = "select * from dm_reply where pid=?";
		int count = dbh.count(sql, pid);
		// System.out.println(count);
		if (count % 8 == 0) {
			return count / 8;
		} else {
			return count / 8 + 1;
		}

	}
	public List<Reply> query(String pid,int page){
		int begin = (page - 1) * 8;
		String sql="select * from dm_reply where pid=? ORDER BY zan desc limit ?,?";
		return dbh.query(sql, Reply.class, pid, begin, 8);
	}
	
	public void insert(String pid,String name,String content,String images){
		String sql="insert into dm_reply values(null,?,?,?,?,now(),?)";
		 dbh.update(sql,  pid, name, content, images,0);
	}
	public void update(String images,String name){
		String sql="update dm_reply set images=? where name=?";
		 dbh.update(sql,  images, name);
	}
	public List<Reply> query1(String pid){
		String sql="select * from dm_reply where pid=? ";
		return dbh.query(sql, Reply.class, pid);
	}
	public void updatezan(String zan,String id){
		String sql="update dm_reply set zan=? where id=?";
		 dbh.update(sql,  zan, id);
	}
}
