package day0729;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class Tomcat {
	//servlet容器
	HashMap<String,Servlet>servletMap;
	public void startup() throws IOException {
		servletMap=new HashMap<>();
		servletMap.put("/photo/hello?name=Tom", new HelloServlet());
		
        ServerSocket sever=new ServerSocket(8080);
		System.out.println("服务器启动，监听8080");
		boolean runing=true;
		while(runing) {
			Socket socket=sever.accept();
			new Thread(){
				public void run() {
				try {
					byte[]buffer=new byte[1024];
					int count;
					InputStream in =socket.getInputStream(); 
					
					OutputStream out=socket.getOutputStream();
					count=in.read(buffer);
					if(count>0) {
						//打印请求报文
						String requestText=new String(buffer,0,count);
						System.out.println(requestText);
						
						//解析请求报文
						HttpServletRequest request=bulidRequest(requestText);
						HttpServletResponse response=new HttpServletResponse(out);
						//使用资源地址区分动态请求和静态请求
						//使用资源地址到servlet容器里面获取servlet对象
						String uri=request.getRequestURI();
						Servlet servlet=servletMap.get(uri);
						if(servlet!=null) {
							//使用servlet处理动态请求
							servlet.service(request, response);
						}else {
							processStaticRequest(request,out);
						}
						
					}
					socket.close();
				}catch(IOException e) {
					e.printStackTrace();
				}	
				}

				
			}.start();
		}
		
		sever.close();
	}
    public void shutdown() {
		
	}
    private void processStaticRequest(HttpServletRequest request,OutputStream out) throws IOException {
    	//如果没有找到servlet对象视为静态请求
		//处理静态请求
		//判断地址是否存在,如果不存在返回404
		String webpath=request.getRequestURI();
		String path="E:/Tomcat/webapps/"+webpath;
		String ContentType;
		//结果码
		int statuscode=200;
		File file=new File(path);
		if(!file.exists()) {
			statuscode=404;
			path="E:/Tomcat/webapps/photo/404.html";
		}
		if(webpath.endsWith(".js")) {
			ContentType="application/javascript; charset=utf-8";
		}else if(webpath.endsWith(".css")) {
			ContentType="text/css; charset=utf-8";
		}else {//图片能被html解析
			ContentType="text/html; charset=utf-8";
		}
		
		//响应头行
		out.write(("HTTP/1.1 "+statuscode+" OK\n").getBytes());
		//响应头域
		out.write(("Content-Type: "+ContentType+"\n").getBytes());
		//空行
		out.write("\n".getBytes());
		//实体
		//out.write("<h1>Hi好</h1>".getBytes());
		byte[]buffer=new byte[1024];
		int count;
		
		FileInputStream fis=new FileInputStream(path);
		while((count=fis.read(buffer))>0) {
			
			out.write(buffer,0,count);
			
		}
		fis.close();
	}
    private HttpServletRequest bulidRequest(String requestText) {
		
		return new HttpServletRequest(requestText);
	}
    
    public static void main(String[] args) throws IOException {
		new Tomcat().startup();
	}
}
