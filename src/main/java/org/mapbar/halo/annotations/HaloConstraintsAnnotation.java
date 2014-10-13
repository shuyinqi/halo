package org.mapbar.halo.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * 这是halo软件级的约束，主要包括
 * 1.配置文件
 * 2.日志文件
 * 3.约束类
 * 4.包名  org.mapbar.halo
 * 5.类名  .*Resource
 * 这是约束大于配置的关键部分
 * @author "Daniel Zhang"
 * @email "mailto-zhangdp@mapbar.com"
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface HaloConstraintsAnnotation {
	
	/**
	 * 组织级必须要实现的类
	 * @see org.mapbar.halo.constraints.IHaloConstraints.ProjectConfig
	 * 
	 * @return
	 */
	 String projectConstraintsClass() default "org.mapbar.halo.ProjectConfigBinder";
	
	/**
	 * 包名前缀
	 * @return
	 */
     String getPackagePrefix() default "org.mapbar.halo";
     
     /***
      * 配置文件的根路径
      * @return
      */
     String haloConfigFolder() default "/opt/halo";
     /***
      * 日志文件的根路径
      * @return
      */
     String haloLogFolder() default "${haloConfigFolder}/log";
     
     /**
      * resource的类名强制检查约定
      * 只有符合匹配条件的resource才能被Halo管理，保证组织级代码风格一致
      *
      */
     String resourcePattern() default ".*\\.resource\\..*Resource";
     
}
