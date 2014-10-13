package org.mapbar.halo.route;


/**
 * 路由对象类，主要用于
 * @author "Daniel Zhang"
 * @email "mailto-zhangdp@mapbar.com"
 */
public interface IRoute {
    /***
     * 通过客户端的上下文环境来获得结果
     * @return
     *
     */
    Object matchAndInvoke();
    /***
     * 选择routeEntity
     * @return
     */
    RouteEntity getRouteEntity();
    
}
