package com.damai.web;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.damai.dao.TopicDao;
import com.google.gson.Gson;

@WebServlet("/ShowTopicServlet")
public class ShowTopicServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	private TopicDao tDao=new TopicDao();   
  
	protected void ShowTopic(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//解决乱码问题
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");	
		
		String pid=request.getParameter("pid");
		
        List<Map<String,Object>> list=tDao.queryByBoard(pid);

        System.out.println(list);
		//使用Gson 将数据以json格式方式返回给局面
		Gson gson=new Gson();
		//将集合转为json格式的字符串
		String json=gson.toJson(list);
		response.getWriter().append(json);
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
