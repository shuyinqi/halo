package org.mapbar.halo.servlet;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mapbar.halo.HaloLoaderFactory;
import org.mapbar.halo.route.RouteContext;
import org.mapbar.halo.route.RouteEntity;
import org.mapbar.halo.route.RouteTable;
/**
 * 
 * @author "Daniel Zhang"
 * @email "mailto-zhangdp@mapbar.com"
 */
@WebFilter(urlPatterns = "/*", asyncSupported = false)
public class HaloFilter implements Filter {

	private IHaloDispatcher dispatcher;
	
	private ServletContext servletContext = null;
	
	/**
	 * 初始化环境
	 */
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	
		this.servletContext = filterConfig.getServletContext();
		
		// 获取上下文对象
		ServletContext servletContext = filterConfig.getServletContext();
		// 初始化dispatcher对象
		dispatcher = HaloLoaderFactory.createDispatcher(servletContext);
		dispatcher.init();
		System.out.println("Dispatcher init Success!");

		// 初始化RouteTable对象
		RouteTable.getInsatnce().initRoute(HaloLoaderFactory.getHaloContraints());
		System.out.println("RouteTable init Success!");
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest httpReq = (HttpServletRequest) request;
        HttpServletResponse httpResp = (HttpServletResponse) response;
        dispatcher.service(httpReq, httpResp,servletContext);
	}
	
	@Override
	public void destroy() {
		dispatcher=null;
	}
}
