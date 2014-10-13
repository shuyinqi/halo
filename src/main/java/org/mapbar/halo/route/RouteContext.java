package org.mapbar.halo.route;

import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mapbar.halo.client.IClientContext;

/** 
 * @Description
 * @author <a href='huhj@mapbar.com'>胡洪佳</a>
 * @date 2013-5-3
 */
public class RouteContext {
	
	private HttpServletRequest request;
	
	private HttpServletResponse response;
	
	private ServletContext servletContext;

	public RouteContext(IClientContext clientContext, ServletContext servletContext) {
		this.request = clientContext.getRequest();
		this.response = clientContext.getResponse();
		this.servletContext = servletContext;
	}
	/**
	 * @return the servletContext
	 */
	public ServletContext getServletContext() {
		return servletContext;
	}

	/**
	 * @param servletContext the servletContext to set
	 */
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	/**
	 * @return the request
	 */
	public HttpServletRequest getRequest() {
		return request;
	}

	/**
	 * @param request the request to set
	 * 
	 */
	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	/**
	 * @return the response
	 */
	public HttpServletResponse getResponse() {
		return response;
	}

	/**
	 * @param response the response to set
	 */
	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}
}
