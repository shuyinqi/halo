package org.mapbar.halo.route;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import org.mapbar.halo.HaloException;
import org.mapbar.halo.annotations.Path;
import org.mapbar.halo.client.IClientContext;
import org.mapbar.halo.constraints.IHaloConstraints;
import org.mapbar.halo.resource.HaloResource;

/**
 * @Description
 * @author <a href='huhj@mapbar.com'>胡洪佳</a>
 * @date 2013-5-3
 * @changeDate 2013-6-21 zhangdp@mapbar.com
 */
public class RouteTable {

	private static  RouteTable instance;
	
	public static synchronized RouteTable getInsatnce(){
		if(instance==null)
		instance=new RouteTable();
		return instance;
	}
	
	/**
	 * routeMap
	 */
	private  Map<String, RouteEntity> routeMap = new HashMap<String, RouteEntity>();

	
	/**
	 * 防止通过new创建对象
	 */
	private RouteTable() {

	}

	public  void initRoute(IHaloConstraints haloConstraints) {
		if (routeMap.size() > 0) {
			return;
		}

		Set<Class<?>> haloResources = haloConstraints.getCurrentProjectConfig().getHaloResources();

		if (haloResources == null || haloResources.isEmpty()) {
			// log.debug。。。
		}

		Iterator<Class<?>> it = haloResources.iterator();
		while (it.hasNext()) {
			routerResources(it.next());
		}
	}

	/**
	 * 注册Resource
	 * 
	 * @param Class
	 *            <? extends HaloResource>
	 */
	private  void routerResources(Class<?> cls) {
		
		String basePath = "";
		if (cls.isAnnotationPresent(Path.class)) {
			Path path = cls.getAnnotation(Path.class);
			basePath = path.value();
		}

		Method[] methods = cls.getDeclaredMethods();
		for (Method method : methods) {
			String routeKey = "";
			Path path = method.getAnnotation(Path.class);
			if (path == null) {
				continue;
			}
			// 拼接routeKey
			routeKey = basePath + path.value();
			RouteEntity entity = new RouteEntity(cls,method,path.type(),routeKey);
			// 去掉模版字段
			routeKey = getSimpleKey(routeKey);
			// 去重，出现重复后抛出异常提醒
			if (!routeMap.containsKey(routeKey)) {
				routeMap.put(routeKey, entity);
			} else {
				RouteEntity route = routeMap.get(routeKey);
				throw HaloException.newBuilder(
						"Multi-Resource define @Path with value:" + routeKey
								+ ",please check:/r/n"
								+ route.getClazz().getCanonicalName()
								+ route.getMethod().getName() + "/r/n"
								+ entity.getClazz().getCanonicalName()
								+ entity.getMethod().getName()).build();
			}

		}
	}
	

	/**
	 * 
	 * 路由选择方法
	 * 
	 * @param routeContext
	 * @return RouteEntity对象
	 * 
	 */
	public  RouteEntity selectRoute(RouteContext routeContext) {
		RouteEntity entity = null;

		String routeKey = routeContext.getRequest().getServletPath();
		//全路径的优先级高于通配符的
		entity = routeMap.get(routeKey.trim());
		if(entity!=null){
			return entity;
		}
		Iterator<String> it = routeMap.keySet().iterator();
		while (it.hasNext()) {
			String key = it.next();
			//通配符不判断顺序
			if (matchKeyForRouteKey(key,routeKey)) {
				entity = routeMap.get(key);
				break;
			}
		}

		if (entity == null) {
			throw HaloException.newBuilder(
					routeKey + " not found in your resource definition")
					.build();
		}
		return entity;
	}
	
	
	/**
	 * 路由选择方法
	 * 
	 * @param routeContext
	 * @return RouteEntity对象
	 */
	public  RouteEntity selectRoute(IClientContext clientContext) {
		RouteEntity entity = null;
		String routeKey = clientContext.getRelativeUrl();
		//全路径的优先级高于通配符的
		entity = routeMap.get(routeKey.trim());
		if(entity!=null){
			return entity;
		}
		Iterator<String> it = routeMap.keySet().iterator();
		while (it.hasNext()) {
			String key = it.next();
			//通配符不判断顺序
			if (matchKeyForRouteKey(key,routeKey)) {
				entity = routeMap.get(key);
				break;
			}
		}

		if (entity == null) {
			throw HaloException.newBuilder(
					routeKey + " not found in your resource definition")
					.build();
		}
		return entity;
	}
	/**
	 * test
	 * @param routeKey
	 * @return
	 *
	 */
	public  RouteEntity selectRoute(String routeKey) {
		RouteEntity entity = null;
		//全路径的优先级高于通配符的
		entity = routeMap.get(routeKey.trim());
		if(entity!=null){
			return entity;
		}
		Iterator<String> it = routeMap.keySet().iterator();
		while (it.hasNext()) {
			String key = it.next();
			System.out.println(key);
			//通配符不判断顺序
			if (matchKeyForRouteKey(key,routeKey)) {
				entity = routeMap.get(key);
				break;
			}
		}

		if (entity == null) {
			throw HaloException.newBuilder(
					routeKey + " not found in your resource definition")
					.build();
		}
		return entity;
	}
	

	/**
	 * 裁剪key： 入参：api/service/{username} 返回：api/service
	 * 
	 * @param routeKey
	 * @return
	 */
	static String getSimpleKey(String routeKey) {

		if (routeKey != null && routeKey.contains(RouteKey.PREFIX)
				&& routeKey.contains(RouteKey.SUFFIX)) {
			routeKey = routeKey.replaceAll("\\" + RouteKey.PREFIX + ".*?"
					+ RouteKey.SUFFIX, RouteKey.COMMON_PREFIX);
		}
		return routeKey;
	}
	
	/***
	 * 匹配route的url为通配符的
	 * @param key    存储在Map中的key，存储格式为正则的
	 * @param routeKey  传递过来的route's path
	 * @return  该route的path与routeMap中匹配结果
	 */
	public  Boolean matchKeyForRouteKey(String key,String routeKey){
		Pattern s1Pattern  = Pattern.compile(key);
		return  s1Pattern.matcher(routeKey).matches();
	}
	/***
	 * test  Method
	 * @return
	 */
	private static Boolean matchPath(){
		String s1 = "/hello/user/\\w*/\\w*";
		String s2 = "/hello/user/2111/1111";
		Pattern s1Pattern  = Pattern.compile(s1);
		boolean a = s1Pattern.matcher(s2).matches();
		System.out.println(a);
		return null;
	}
}
