package org.mapbar.halo.constraints;

import java.io.File;
import java.util.Set;

import org.mapbar.halo.resource.HaloResource;
import org.mapbar.halo.utils.InjectUtils;

/**
 * 组织级别的约束，项目的约束大于配置的特性就是根据此接口来实现的
 * 
 */
public interface IHaloConstraints {
	/**
	 * 
	 * @return
	 */
	public static final String annotatedHaloConstrainsBinder="org.mapbar.halo.HaloConstrainsBinder";
	
	
    HaloConfig getHaloConfig();
    
    ProjectConfig getCurrentProjectConfig();
    
    /**针对公司级别的配置信息，日志信息*/
    public interface HaloConfig{
    	/**
    	 * 公司级别的配置文件夹
    	 * */
    	File getConfigFolder() ;
    	
    	/**
    	 * 
    	 * 日志文件夹
    	 */
    	File getLogFolder();
    	
    	/**
    	 * 组织级别的注入配置，组织内的对象是怎么注入到容器的。
    	 */
    	InjectUtils inject();
    }
    /***
     * 项目级配置，Halo如果说是一个平台的话，那么基于这个平台的所有项目必须要有此约束
     * 
     */
    public interface ProjectConfig{
    	/**
    	 * 
    	 *项目名称，便于开发与运维管理项目
    	 */
    	 String getName();
    	 /**
    	  * 所有的Resource类,且Resource必须满足某种约束
    	  */
    	 Set<Class<?>> getHaloResources();
    	 
    	 /**
    	  * 注入级别的项目配置  ,这里主要是怎么实现项目级别的配置
    	  */
    	
     	InjectUtils inject();
    	 
    }
    
}
