package day0729;

import java.io.PrintWriter;

public class HelloServlet extends HttpServlet {
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) {
         String name=request.getParameter("name");
		PrintWriter out=response.getwWriter();
         //输出到页面
         out.print("<h1>hello "+name+"</h1>");
	}
}
