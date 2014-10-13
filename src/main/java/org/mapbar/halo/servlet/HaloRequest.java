package org.mapbar.halo.servlet;

import java.io.Closeable;
import java.io.IOException;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.MultipartConfigElement;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import org.mapbar.halo.annotations.RequestType;
import org.mapbar.halo.data.ClientInfo;

public class HaloRequest extends HttpServletRequestWrapper implements Closeable {

    private int maxFormKeys = 1000;  // 最大的key数量
    private int maxFormContentSize = 200000; // post form数据的最大尺寸
    public static int bufferSize = 64*1024;

    private RequestType type= RequestType.UNKNOW;
    private Map<String, Collection<String>> queryStrings = null;
	private final HttpServletRequest request;
	private  MultipartConfigElement config;
	private ClientInfo clientInfo;
	public ClientInfo getClientInfo() {
		return clientInfo;
	}
	//获得需要的客户端信息
	public void setClientInfo(HttpServletRequest request) {
              //根据request构建clientInfo
		clientInfo=new ClientInfo(request);
	}
	/**
	 * 生成自己的request
	 * @param request http的请求对象
	 * @param config  http的相关配置对象
	 */
	public HaloRequest(HttpServletRequest request, MultipartConfigElement config) {
		super(request);
		this.request=request;
		this.config=config;
	}
	
	public HaloRequest(HttpServletRequest request) {
		super(request);
		this.request=request;
	}
	
	public String form(String name) {
		
		return String.valueOf(request.getAttribute(name));
	}
	public String queryString(String name) {
		
		return String.valueOf(request.getAttribute(name));
	}
	public Map<String, Collection<String>> queryStrings() {
       Enumeration<String> names= request.getAttributeNames();
          Map<String, Collection<String>> querys=new HashMap<String, Collection<String>>();
       while(names.hasMoreElements()){
    	   
       }
		return null;
	}
	public Map<String, Collection<String>> forms() {
		
		return null;
	}

   private void parsePost() {
        if(RequestType.UNKNOW!= type){
        	return;
        }
        type = (!isPost(request)) ? RequestType.GET: RequestType.POST;
        if (RequestType.GET == type){
        	return;
        }
        queryStrings();
    }
   private Boolean isPost(HttpServletRequest request){
	  return "post".equals(request.getMethod().toLowerCase());
   }
	@Override
	public void close() throws IOException {
		// TODO Auto-generated method stub
		
	} 
	  
}
