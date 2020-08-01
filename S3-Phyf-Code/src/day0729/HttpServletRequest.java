package day0729;

import java.util.HashMap;
import java.util.Map;

public class HttpServletRequest {
	private String method;
	private String requestUri;
	private String protocol;
	private String parameter;
	//存放头域键值对的map集合
	private Map<String,String> headerMap=new HashMap<>();
	
    public HttpServletRequest(String requestText) {
    	//完成对报文请求的解析
    	String lines[]=requestText.split("\\n");
		String items[]=lines[0].split("\\s");
		method=items[0];
		requestUri=items[1];
		protocol=items[2];
		
		for(int i=1;i<lines.length;i++) {
			lines[i]=lines[i].trim();
			if(lines[i].isEmpty()) {
				break;
			}
            items=lines[i].split(":");
            headerMap.put(items[0], items[1].trim());
		}
    }
	
	public String getMethods() {
		return method;
	};
	public String getRequestURI() {
		
		return requestUri;
	};
	
	public String getProtocol() {
		return protocol;
	};

	/**
	 * 获取头域键值对
	 * @param name
	 * @return
	 */
	public String getHeader(String name) {
		return headerMap.get(name);
	};
	/**
	 * 获取请求参数
	 * @param name
	 * @return
	 */
	public String getParameter(String name) {
		String names[]=requestUri.split("\\?");
		parameter=names[1].split(name)[1].split("=")[1];
		return parameter;
	};
	/**
	 * 获取cookie
	 * @param name
	 * @return
	 */
	public Cookie[] getCookies() {
		return null;
	};

}
