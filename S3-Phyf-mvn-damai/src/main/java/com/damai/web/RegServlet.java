package com.damai.web;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.damai.dao.UserDao;




@WebServlet("/RegServlet")
public class RegServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	private UserDao udao=new UserDao();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//解决乱码问题
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/html;charset=utf-8");
			
			String ename=request.getParameter("ename");
			String cname=request.getParameter("cname");
			String password=request.getParameter("password");
			String email=request.getParameter("email");
			String phone=request.getParameter("phone");
			String sex=request.getParameter("sex");
			
			if(cname==null ||cname.trim().isEmpty()) {
				response.getWriter().append("请填写用户名！");
			}else if(password==null || password.trim().isEmpty()) {
				response.getWriter().append("请填写密码！");
			}else if(password.equals(password)==false) {
				response.getWriter().append("两次密码不一致！");
			}else {
				udao.insertuser(ename,cname,password,email,phone,sex);
				response.getWriter().append("用户注册成功");
			}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
