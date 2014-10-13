package org.mapbar.halo.route;

import java.util.Map;

import org.mapbar.halo.client.IClientContext;

import com.google.common.collect.Maps;
/***
 * route
 * @（#）:Route.java 
 * @description:  
 * @author:  sunjy@mapbar.com  2013-5-7 
 * @version: [SVN] 
 * @modify: 
 * @Copyright:  图吧
 */
public class Route implements IRoute{
    /***
     * 匹配执行routeinvoke方法
     * @param clientContext
     * @return
     */
	public RouteEntity routeEntity =null;
	
	public IClientContext clientContext =null;
	public Route(IClientContext clientContext) {
		RouteEntity routeEntity = RouteTable.getInsatnce().selectRoute(clientContext);
		this.routeEntity = routeEntity;
		this.clientContext = clientContext;
	}

	public Object matchAndInvoke(){
		//System.out.println(routeEntity.toString());
		Map<String, String> uriTemplateVariables = Maps.newHashMap();
		if(routeEntity.match(clientContext.getRelativeUrl(), uriTemplateVariables)){
		  for(String key :uriTemplateVariables.keySet()){
			  System.out.println("uriTemplateVariablesMap["+key+":"+uriTemplateVariables.get(key));
		  }	
		}
		return routeEntity.invoke(uriTemplateVariables,clientContext);
	}

	public RouteEntity getRouteEntity() {
		return routeEntity;
	}

	public void setRouteEntity(RouteEntity routeEntity) {
		this.routeEntity = routeEntity;
	}

	public IClientContext getClinetContext() {
		return clientContext;
	}

	public void setClinetContext(IClientContext clientContext) {
		this.clientContext = clientContext;
	}
}
