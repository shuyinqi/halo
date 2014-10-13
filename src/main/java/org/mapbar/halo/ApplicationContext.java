package org.mapbar.halo;

import java.util.Set;

import javax.servlet.ServletContext;

import org.mapbar.halo.constraints.IHaloConstraints;
import org.mapbar.halo.constraints.IHaloConstraints.ProjectConfig;
import org.mapbar.halo.inject.HaloAnnotationApplicationContext;
import org.mapbar.halo.inject.IHaloBeanFactory;
import org.mapbar.halo.log.ILogger;
import org.mapbar.halo.log.LoggerFactory;
import org.mapbar.halo.log.LoggerFactory.HaloLogConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 项目的上下文环境
 * to-zhangdp@mapbar.com
 */
public class ApplicationContext {
	// 日志
	private ILogger logger;

	private LoggerFactory loggerFactory;

	private ServletContext servletContext;

	private IHaloConstraints constraints;

	public ServletContext getServletContext() {
		return servletContext;
		
	}

	// IOC容器类
	private IHaloBeanFactory annotationContext;

	public IHaloBeanFactory getAnnotationContext() {
		return annotationContext;
	}

	public ApplicationContext(ServletContext servletContext,
			IHaloConstraints constraints,
			IHaloBeanFactory annotationContext) {
		this.servletContext = servletContext;
		this.constraints = constraints;
		if (annotationContext == null)
			annotationContext =new HaloAnnotationApplicationContext( new AnnotationConfigApplicationContext());
		this.annotationContext = annotationContext;
	}

	public void init() {
		
		if (logger == null || loggerFactory == null) {
			// 实例化Logger
			HaloLogConfig logConfig = new LoggerFactory.HaloLogConfig(
					constraints);
			this.loggerFactory = new LoggerFactory(logConfig);
			logger = loggerFactory
					.getLogger(ApplicationContext.class.getName());
		}
		logger.info("Initializing  ApplicationContext..");
		logger.debug("The start of inject the resources class");

		// 提取约束，进行注入
		ProjectConfig pConfig = constraints.getCurrentProjectConfig();
		injectResources(pConfig.getHaloResources());
		logger.debug("the end of inject of the resources class");

	}

	public Object injectResource(Class<?> clazz) {
		if(!annotationContext.contains(clazz))
		annotationContext.register(clazz);
		return annotationContext.getBean(clazz);
	}

	public ILogger getLogger(String name) {
		return loggerFactory.getLogger(name);
	}
	
	public ILogger getLogger(Class<?> clazz){
		return loggerFactory.getLogger(clazz);
	}

	/**注入所有的资源类*/
	private void injectResources(Set<? extends Class<?>> set) {
		for (Class<?> resource : set) {
			injectResource(resource);
		}
	}

}
