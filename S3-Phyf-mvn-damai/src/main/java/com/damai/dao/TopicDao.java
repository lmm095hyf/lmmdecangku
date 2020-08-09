package com.damai.dao;

import java.util.List;
import java.util.Map;
import com.damai.util.DBHelper;

public class TopicDao {
	public List<Map<String,Object>> queryByBoard(String pid){
		//预编译sql语句
		String sql="select * from dm_topic where pid=?";
		return new DBHelper().query(sql,pid);
	}
//	public List<Map<String,Object>> queryByDetail(String topicid){
//		//预编译sql语句
//		String sql="SELECT\n" +
//				"	*\n" +
//				"FROM\n" +
//				"	dm_topic a\n" +
//				"left JOIN dm_user b ON a.uid = b.id\n" +
//				"WHERE\n" +
//				"	topicid = ?\n" +
//				"UNION ALL\n" +
//				"	SELECT\n" +
//				"		*\n" +
//				"	FROM\n" +
//				"		dm_reply\n" +
//				"left JOIN dm_user ON dm_reply.uid = dm_user.id\n" +
//				"	WHERE\n" +
//				"		topicid = ?";
//		return new DBHelper().query(sql, topicid,topicid);
//		          
//	}
	//发表帖子
//	public void insert(String content,String uid,String boardid){
//		String sql="insert into dm_topic values(null,null,?,null,?,?)";
//		DBHelper dbh=new DBHelper();
//		dbh.update(sql, content,uid,boardid);
//	}
	public void insert(String pid,String name,String content,String images){
		String sql="insert into dm_topic values(null,?,?,?,?,now(),?)";
		DBHelper dbh=new DBHelper();
		dbh.update(sql,  pid, name, content, images,0);
	}
}
