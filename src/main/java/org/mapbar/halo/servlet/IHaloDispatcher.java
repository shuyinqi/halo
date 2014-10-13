package org.mapbar.halo.servlet;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mapbar.halo.context.ManageContext;
import org.mapbar.halo.route.RouteContext;
import org.mapbar.halo.route.RouteEntity;
import org.mapbar.halo.route.RouteResult;

/**
 * 
 * 
 * rest请求分发接口，
 * 
 */
public interface IHaloDispatcher {
	/** 初始化 */
	void init();

	void service(HttpServletRequest request, HttpServletResponse response,ServletContext servletContent);

	void destory();

	void forward(RouteEntity routeEntity, RouteContext rouetContext,Object object);


}
