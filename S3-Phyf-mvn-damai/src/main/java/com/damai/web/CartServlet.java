package com.damai.web;


import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.damai.dao.AddProductDao;
import com.damai.dao.CartDao;
import com.damai.dao.DetailDao;
import com.damai.dao.cataDao;
import com.damai.util.DBHelper;

@WebServlet("/cart.do")
public class CartServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	
	private CartDao cdao = new CartDao();

	// cart.do?op=add&pid=???
	protected void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String,Object> user=(Map<String,Object>) request.getSession().getAttribute("logined");
		String uid = ""+user.get("id") ; // 用户id 从会话中获取 loginedUser.getId(), 
		String pid = request.getParameter("pid");
		if( cdao.update(uid, pid) == 0) {
			// 结果为0 ,说明该用户还没有添加过改商品
			cdao.insert(uid, pid);
		}
		response.getWriter().append("{\"msg\":\"购物车添加成功!\"}");
	}
	
	
	protected void query(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String,Object> user=(Map<String,Object>) request.getSession().getAttribute("logined");
		String uid = ""+user.get("id") ; // 用户id 从会话中获取 loginedUser.getId(),
		List<?> list = cdao.queryByUid(uid);
		print(response, list);
	}
	
	protected void del(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id"); // 用户id 从会话中获取 loginedUser.getId(),
		int list = cdao.del(id);
		print(response, list);
	}
	
}