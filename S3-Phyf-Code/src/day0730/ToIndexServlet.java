package day0730;

import java.io.IOException;

public class ToIndexServlet extends HttpServlet {

	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.sendredirect("/photo/index.html");
	}

}
