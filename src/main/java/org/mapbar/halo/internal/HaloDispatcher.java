package org.mapbar.halo.internal;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.mapbar.halo.ApplicationContext;
import org.mapbar.halo.Halo;
import org.mapbar.halo.client.ClientContext;
import org.mapbar.halo.client.IClientContext;
import org.mapbar.halo.log.ILogger;
import org.mapbar.halo.route.IRoute;
import org.mapbar.halo.route.Route;
import org.mapbar.halo.route.RouteContext;
import org.mapbar.halo.route.RouteEntity;
import org.mapbar.halo.servlet.HaloRequest;
import org.mapbar.halo.servlet.IHaloDispatcher;
import com.mapbar.techapi.core.mvc.Dispatcher;
import com.mapbar.techapi.core.mvc.ViewContext;
/**
 * 
 * @author "Daniel Zhang"
 * @email "mailto-zhangdp@mapbar.com"
 */
public class HaloDispatcher extends Dispatcher implements IHaloDispatcher {
	private  ILogger logger;
    private Halo halo;
	private MultipartConfigElement config;
	

	
	private HaloDispatcher(){}
	
	@Override
	public void service(HttpServletRequest request, HttpServletResponse response,ServletContext servletContent) {
		HaloRequest haloRequest = new HaloRequest(request,config);
		IClientContext clientContext = new ClientContext(haloRequest,response);
		IRoute route = new Route(clientContext);
		Object object = route.matchAndInvoke();
		/**
		 * 请求转发
		 * 
		 */
		forward(route.getRouteEntity(),new RouteContext(clientContext,servletContent),object);
	}
	


	public void forward(RouteEntity routeEntity,RouteContext rouetContext,Object object) {
		ViewContext viewContext = new ViewContext();
		viewContext.setRequest(rouetContext.getRequest());
		viewContext.setResponse(rouetContext.getResponse());
		viewContext.setServletContext(rouetContext.getServletContext());
		viewContext.setViewType("json");
		/**
		 * 渲染上下文对象
		 */
		try {
			renderView(routeEntity.getMethod().getReturnType(), object, viewContext);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public ILogger getLogger() {
		return logger;
	}


	@Override
	public void init() {
		halo=Halo.instance;
		this.logger=halo.getContext().getLogger(this.getClass());
		System.out.println("HaloDispatcher is initiling");
	}
	@Override
	public void destory() {

	}
}
