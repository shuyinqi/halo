package org.mapbar.halo.client;

import java.net.InetAddress;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mapbar.halo.servlet.HaloRequest;

import com.google.common.base.Preconditions;
import com.google.common.net.InetAddresses;
/***
 * 客户端生命周期
 * @（#）:ClientContext.java 
 * @description:  
 * @author:  sunjy@mapbar.com  2013-5-7 
 * @version: [SVN] 
 * @modify: 
 * @Copyright:  图吧
 */
public class ClientContext implements IClientContext {

	 private final HaloRequest request;
	 private final HttpServletResponse response;
     private String relativeUrl = null;
     private InetAddress address = null;

     public ClientContext(HttpServletRequest request,HttpServletResponse response) {
         this.request = (HaloRequest) request;
         this.response = response;
         Preconditions.checkNotNull(request);
     }
     
     @Override
     public String getRelativeUrl() {
         if (relativeUrl != null)
             return relativeUrl;
         String uri = request.getRequestURI();
         String contextPath = request.getContextPath();
         relativeUrl = uri.substring(contextPath.length());
         return relativeUrl;
     }

 	@Override
 	public String getMethod() {
 		return request.getMethod();
 	}
    
 	public Map<String,String> getParamMap(){
 		Map<String,String> param = new HashMap<String,String>();
 		if("post".equals(getMethod().toLowerCase())){
 			Enumeration enumparam = request.getParameterNames();
	    	while(enumparam.hasMoreElements()){
	    		String paramName = (String) enumparam.nextElement();
	    		String paramValue = request.getParameter(paramName);
	    		param.put(paramName, paramValue);
	    	}
 		}else{
 			String name = request.getQueryString();
 			String[] keyvalue = name.split("&");
 			for(int i=0;i<keyvalue.length;i++){
 				if(keyvalue[i].contains("=")){
 					param.put(keyvalue[i].split("=")[0], keyvalue[i].split("=")[1]);
 				}
 			}
 		}
 		return param;
 	}
 	
     @Override
     public String queryString(String name) {
         return request.queryString(name);
     }
     @Override
     public InetAddress getAddress() {
         if (address != null)
             return address;

         //TODO:synchronized
         address = gerRemoteAddress();
             return address;

     }
     public HttpServletResponse getResponse() {
		return response;
	}
     
	public HaloRequest getRequest() {
		return request;
	}
	protected InetAddress gerRemoteAddress() {
         return InetAddresses.forString(request.getRemoteAddr());
     }
 }

