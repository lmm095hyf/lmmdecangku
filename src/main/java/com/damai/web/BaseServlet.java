package com.damai.web;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

//@WebServlet("/BaseServlet")
public abstract class BaseServlet extends HttpServlet {
	//private static final long serialVersionUID = 1L;
	/**
	 * ProductServlet 商品操作的servlet, 产品查询\修改\删除...
	 * product.do?op=query   查询
	 * product.do?op=add     新增
	 * product.do?op=del     删除
	 */
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		//获取操作字段
		String op=request.getParameter("op");
		//Java的黑科技--反射技术
		//通过op获取方法对象
		try {
			System.out.println(op);
			System.out.println(this.getClass());
			Method method = this.getClass().getDeclaredMethod(op, HttpServletRequest.class, HttpServletResponse.class);
			// 设置方法的可以被访问
			method.setAccessible(true);
			// 执行方法
			method.invoke(this, request, response);
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			response.getWriter().append("系统错误!");
		
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	// 输出页面内容
		protected void print (HttpServletResponse response, Object obj) throws IOException {
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
			response.getWriter().print(gson.toJson(obj));
		}

}
