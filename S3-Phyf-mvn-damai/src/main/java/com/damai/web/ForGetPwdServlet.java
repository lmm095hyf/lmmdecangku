package com.damai.web;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.damai.dao.UserDao;

@WebServlet("/ForGetPwdServlet")
public class ForGetPwdServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserDao udao=new UserDao();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//解决乱码问题
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
				
		String password=request.getParameter("password");
		String passwordTwo=request.getParameter("passwordTwo");
		String phone=request.getParameter("phone");
		
		if(password==null) {
			response.getWriter().print("请重置您的密码");
		}else if(phone==null) {
			response.getWriter().print("您输入的账号用户不存在，请检查账号是否正确！");
		}else if(!(passwordTwo.equals(password))) {
			response.getWriter().print("两次密码不一致，请重新输入！");
		}else {
			udao.forgetpwd(password,phone);
			response.getWriter().print("您的密码已重置成功，请登录！");
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
