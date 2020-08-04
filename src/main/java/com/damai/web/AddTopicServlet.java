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



@WebServlet("/AddTopicServlet.do")
public class AddTopicServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private TopicDao tDao=new TopicDao();
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//解决乱码问题
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");

		String pid=request.getParameter("pid");
		String content=request.getParameter("content");
		String name=request.getParameter("name");
		String images=request.getParameter("images");
		
		
//		@SuppressWarnings("unchecked")
//		Map<String,Object> user=(Map<String,Object>) request.getSession().getAttribute("loginedUser");
//		
//		String name=""+user.get("name");
		
		if(content==null || content.trim().isEmpty()) {
			response.getWriter().print("请填写评价内容");
		}else {
			tDao.insert(pid, name, content, images);
			response.getWriter().print("评价成功！！！");
		}
		
	}

//	try {
//		@SuppressWarnings("unchecked")
//		Map<String, Object> user = (Map<String, Object>) request.getSession().getAttribute("loginedUser");
//		String name = "" + user.get("uname");
//		String uid = "" + user.get("id");
//		List<User>list=u.queryimg(uid);
//		String images = null;
//		for(User uu:list) {
//			images=uu.getHead();
//		}
//		String image1=request.getParameter("image1");
//		
//		String pid=request.getParameter("pid");
//		String content=request.getParameter("content");
//		if(image1=="" ) {
//			r.insert(pid, name, content, images);
//			response.getWriter().append("评价成功");
//		}else {
//			b.insert(pid, name, content, image, images);
//			response.getWriter().append("评价成功");
//		}
//	}catch(Exception e) {
//		e.printStackTrace();
//		response.getWriter().append("评价失败");
//	}
//}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
