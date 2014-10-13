package org.mapbar.halo.client;

import java.net.InetAddress;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.mapbar.halo.servlet.HaloRequest;
/***
 * 客户端上下文
 * @（#）:IClientContext.java 
 * @description:  
 * @author:  sunjy@mapbar.com  2013-5-9 
 * @version: [SVN] 
 * @modify: 
 * @Copyright:  图吧
 */
public interface IClientContext {
	
	/****
	 * 获得当前的http方法 
	 * @return
	 *
	 */
    String getMethod();
    /**
     * 得到当前请求的url,不包括参数
     * @return 当前请求的url
     */
    String getRelativeUrl();

    /**
     * 获得url中的参数
     * @param name 参数名
     * @return url中的参数值
     */
    String queryString(String name);
    
    /***
     * 获取参数，拼接成k-v形式
     * @return
     */
    Map<String,String> getParamMap();
    /**
     * 获得用户ip
     * @return 用户ip
     */
    InetAddress getAddress();
    /***
     * @return response
     */
    public HttpServletResponse getResponse();
     
    /***
     * @return request
     */
	public HaloRequest getRequest();
    
}
