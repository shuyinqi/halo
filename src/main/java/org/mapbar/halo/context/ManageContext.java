package org.mapbar.halo.context;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mapbar.halo.client.ClientContext;
import org.mapbar.halo.client.IClientContext;

/**
 * 管理客户端的请求处理周期对象
 * @author "Daniel Zhang"
 * @email "mailto-zhangdp@mapbar.com"
 * 
 */
public class ManageContext {
     private HttpServletRequest httpRequest;
     
     private HttpServletResponse httpResponse;
     
     private IClientContext clientContext;

	public HttpServletRequest getHttpRequest() {
		return httpRequest;
	}

	public void setHttpRequest(HttpServletRequest httpRequest) {
		this.httpRequest = httpRequest;
	}

	public HttpServletResponse getHttpResponse() {
		return httpResponse;
	}

	public void setHttpResponse(HttpServletResponse httpResponse) {
		this.httpResponse = httpResponse;
	}

	public IClientContext getClientContext() {
		return clientContext;
	}

	public void setClientContext(IClientContext clientContext) {
		this.clientContext = clientContext;
	}
}
