package com.damai.web;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;

import com.damai.dao.AddProductDao;
import com.damai.dao.DetailDao;
import com.damai.dao.ProductDao;
import com.damai.dao.cataDao;
import com.damai.po.DmProduct;
import com.damai.util.DBHelper;
import com.google.gson.Gson;

@WebServlet("/product.do")
public class ProductServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;	
	private cataDao dao=new cataDao();
	private AddProductDao addproduct=new AddProductDao();
	private DetailDao ddao=new DetailDao();
	private ProductDao pdao=new ProductDao();
	protected void query(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String sql = "select * from dm_product where is_hot=1 limit 0,10";
		List<?> list = new DBHelper().query(sql);
		HashMap<String,Object> page = new HashMap<>();
		page.put("list", list);
		print( response, page);
	}
	protected void query1(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, IllegalAccessException, InvocationTargetException {
		String page = request.getParameter("page");
		String rows = request.getParameter("rows"); 
		
		/**
		 * dp  要装载的 实体 对象
		 * properties 存放属性值的 map 集合
		 */
		DmProduct dp = new DmProduct();
		// 装载方法
		BeanUtils.populate(dp, request.getParameterMap());
		
		List<?> list = pdao.query1(dp,page, rows);
		int total = pdao.count1(dp);		
		HashMap<String,Object> data = new HashMap<>();
		data.put("rows", list);
		data.put("total", total);
		print( response, data);
	}
	protected void queryById(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		System.out.println("id:" + id);
		String sql = "select * from dm_product where id = ?";
		List<?> list = new DBHelper().query(sql, id);
		print( response, list.get(0));
	}
	
	protected void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String ename = request.getParameter("username");
		String upass = request.getParameter("password");
		HttpSession session = request.getSession();
		Map<String,Object> user = pdao.queryOne(ename,upass);
		if(user.size()==0) {
			response.getWriter().append("请您先注册");
			
		}else {
			request.getSession().setAttribute("logined",user);
			response.getWriter().print("登录成功");
		}
	}
	
	protected void getlogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String,Object> user = (Map<String,Object>)request.getSession().getAttribute("logined");
		Gson gson=new Gson();
		String json=gson.toJson(user);
		response.getWriter().print(json);
	}
	
	protected void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String sql = "select * from dm_product ORDER BY createtime DESC LIMIT 0,10";
		List<?> list = new DBHelper().query(sql);
		HashMap<String,Object> page = new HashMap<>();
		page.put("list", list);
		print( response, page);
	}

	protected void getcata(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String cid = request.getParameter("cid");
		List<Map<String,Object>> list = dao.catagory(cid);
		HashMap<String,Object> page = new HashMap<>();
		page.put("list", list);
		print( response, page);
		System.out.println(cid);
		System.out.println(list);
	}
	
	protected void getdetails(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		List<Map<String,Object>> list = ddao.details(id);
		HashMap<String,Object> page = new HashMap<>();
		page.put("list", list);
		print( response, page);
		System.out.println(id);
		System.out.println(list);
	}
	
	protected void AddProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String pname = request.getParameter("pname");
		Double mprice = Double.valueOf(request.getParameter("mprice"));
		Double sprice = Double.valueOf(request.getParameter("sprice"));
		String image = request.getParameter("image");
		String pdesc = request.getParameter("pdesc");
		Integer ishot = Integer.valueOf(request.getParameter("ishot"));
		Integer cid = Integer.valueOf(request.getParameter("cid"));
		Timestamp createtime = new Timestamp(System.currentTimeMillis()); 
		
		try {
			addproduct.AddProduct(pname, mprice, sprice, image, pdesc, ishot,createtime,cid);
			response.getWriter().print("保存成功");
		} catch (Exception e) {
			response.getWriter().print("保存失败");
		}
		
		
}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}