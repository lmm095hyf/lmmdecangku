package day0729;

import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

public class HttpServletResponse {
     private OutputStream out;
     private CharArrayWriter caw=new CharArrayWriter();
     private PrintWriter pw=new PrintWriter(caw);
	private int status;
	private String msg;
     
     
	public HttpServletResponse(OutputStream out) {
    	 this.out=out;
     } 
	/**
	 * 获取响应输出流   临时保存servlet输出的内容
	 * @return
	 */
	public PrintWriter getwWriter() {
		return pw;
		
	}
	/**
	 * 设置结果码  和说明
	 * @param status
	 * @param msg
	 */
	public void setStatus (int status,String msg) {
		this.status=status;
		this.msg=msg;
		
	}
	/**
	 * 将响应报文传给服务器
	 * @throws IOException
	 */
	public void flushBuffer () throws IOException {
		//响应头行
				out.write(("HTTP/1.1 "+status+" "+msg+"\n").getBytes());
				//响应头域
				out.write(("Content-Type: text/html; charset=utf-8\n").getBytes());
				//空行
				out.write("\n".getBytes());
				//实体
				out.write(caw.toString().getBytes());
		
	}
	
}
