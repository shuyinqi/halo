package org.mapbar.halo.route;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import org.mapbar.halo.HaloException;
import org.mapbar.halo.annotations.RequestType;
import org.mapbar.halo.client.IClientContext;
import org.mapbar.halo.resource.HaloResource;
import org.mapbar.halo.utils.AntPathMatcher;
import org.mapbar.halo.utils.ClassUtils;
import org.mapbar.halo.utils.PathMatcher;
import org.mapbar.halo.utils.converter.ConverterFactory;

import com.google.common.collect.ImmutableList;

/** 
 * @author <a href='huhj@mapbar.com'>胡洪佳</a>
 * @date 2013-5-3
 */
public class RouteEntity {
	
	/**
	 * 类全名：com.xxx.xxx
	 */
	private final Class<?> clazz;
	/**
	 * 注解方法结合，使用@PATH注解的
	 */
	private final Method method;
	/**
	 * 请求类型POST、GET、PUT...
	 */
	private final  RequestType requestType;
	/**
	 * roueKey
	 */
	private final String routeKey;
	/***
	 * 参数名称
	 */
	private final List<String> paramNames;
	/**
	 * 参数类型
	 */
	private final List<Class<?>> paramTypes;
	
	private final PathMatcher pathMatcher = new AntPathMatcher();
	
    private final ConverterFactory converter = new ConverterFactory();
	
	public RouteEntity(Class<?> clazz, Method method,
			RequestType requestType, String routeKey) {
		this.clazz = clazz;
		this.method = method;
		this.requestType = requestType;
		this.routeKey = routeKey;
		this.paramTypes = ImmutableList.copyOf(method.getParameterTypes());
		this.paramNames = ImmutableList.copyOf(ClassUtils.getMethodParamNames(clazz, method));
	}
	
	/***
	 * 将方法及对应参数转化，实现resource方法
	 * @param urlParams  get或者post传递过来的参数 
	 * @param clientContext client上下文环境，主要是注入
	 * @return
	 */
	Object invoke(Map<String, String> urlParams,IClientContext clientContext) {
		//参数
        Object[] param = new Object[getParamTypes().size()];
        //参数类型与参数个数一样多
        for(int index = 0; index < getParamNames().size(); index++){
            //拿到参数名称
        	String paramName = getParamNames().get(index);
            //参数类型
        	Class<?> clazz = getParamTypes().get(index);
        	
            //从传参或者url路径中获得的参数值
            String v = urlParams.get(paramName);
            //从参数传递过来的基本类型
            if(v!=null){
            	  // fixMe: move to init
                if(!getConverter().canConvert(clazz))
                    throw HaloException.newBuilder("Invoke cannot convert parameter.")
                            .addContextVariable(paramName, "expect " + clazz.getName() + " but value is " + v)
                            .build();
                //将传递过来的String转化为需要的类型
                param[index] = getConverter().convert(clazz, v);
            }
            //封装的对象或者request，reponse
            else{
                 try {
                	 //如果是request
					if(clazz.isAssignableFrom(ServletRequest.class)){
						param[index] = clientContext.getRequest().getRequest();
					 }
					//如果是response
					else if(clazz.isAssignableFrom(ServletResponse.class)){
						 param[index] = clientContext.getResponse();
					 }
					//只能是自定义对象了，实体对象不应该是接口，不应该是抽象类，应该是public的
					else if(!Modifier.isInterface(clazz.getModifiers())
                           &&!Modifier.isAbstract(clazz.getModifiers())
						   && Modifier.isPublic(clazz.getModifiers())){
						param[index] =userDefinedParamConvert(urlParams,clazz);
					 }
					//自定义的对象明显不符合格式
					else{
						 throw HaloException.newBuilder("Invoke cannot convert parameter.")
                         .addContextVariable(paramName, "expect " + clazz.getName() + " but value is " + v)
                         .build();
					 }
				} catch (Exception e) {
					e.printStackTrace();
				} 
            }
        }
        try {
            Object result = getMethod().invoke(getClazz().newInstance(), param);
            return result;
        } catch (Exception e) {
            throw HaloException.newBuilder("invoke exception.", e).build();
        }
    }
	/***
	 * 拼接用户自定义bean对象
	 * @param urlParams 传递过来的参数
	 * @param clazz 用户自定义bean class对象
	 * @return  
	 */
	private Object userDefinedParamConvert(Map<String, String> urlParams,Class<?> clazz){
		Object obj=null;
		try {
			obj = clazz.newInstance();
		    Field[] fields = clazz.getDeclaredFields();
		    for(Field f :fields){
		    	if(urlParams.containsKey(f.getName())){
		    		f.setAccessible(true);
			    	Class<?> type = f.getType();
			    	if(!getConverter().canConvert(type)){
			    		  throw HaloException.newBuilder("Invoke cannot convert parameter.")
                          .addContextVariable(urlParams.get(f.getName()), "bean param mast base type")
                          .build();
			    	}
			    	f.set(obj, getConverter().convert(clazz, urlParams.get(f.getName())));
		    	}
		    }
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return obj;
	}
	
	
	
	boolean match(String simplyPath, Map<String, String> uriTemplateVariables) {
        return getPathMatcher().doMatch(getRouteKey(),simplyPath, uriTemplateVariables);
    }
	
	PathMatcher getPathMatcher() {
	    return pathMatcher;
	}
	  
	private ConverterFactory getConverter() {
		return converter;
	}
	/**
	 * @return the routeKey
	 */
	public String getRouteKey() {
		return routeKey;
	}
	/**
	 * @return the method
	 */
	public Method getMethod() {
		return method;
	}

	public Class<?> getClazz() {
		return clazz;
	}
	
	/**
	 * @return the requestType
	 */
	public RequestType getRequestType() {
		return requestType;
	}
	public List<String> getParamNames() {
		return paramNames;
	}
	public List<Class<?>> getParamTypes() {
		return paramTypes;
	}
	@Override
	public String toString() {
		String sb = "RouteEntity [clazz=" + clazz + ", method=" + method.toString()
				+"routeKey="+ routeKey+ ", requestType=" + requestType.name()+","+"[";
		 for(int i=0;i<paramNames.size();i++){
			 sb+="paramNames=" +paramNames.get(i)+", paramTypes="+paramTypes.get(i)+";";
		 }
		 sb+="]]";
		return sb;
	}
}
