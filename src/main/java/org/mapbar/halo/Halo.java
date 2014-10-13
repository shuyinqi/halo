package org.mapbar.halo;

import javax.servlet.ServletContext;

import org.mapbar.halo.constraints.IHaloConstraints;
import org.mapbar.halo.inject.HaloAnnotationApplicationContext;
import org.mapbar.halo.internal.HaloDispatcher;
import org.mapbar.halo.log.ILogger;
import org.mapbar.halo.servlet.HaloContextListener;
import org.mapbar.halo.servlet.IHaloDispatcher;
import org.mapbar.halo.utils.OnlyOnceCondition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

/**
 * 项目的核心,根对象，所有的对象都可以通过该对象获得。
 * tomcat启动即实例化该类
 * 
 * 
 */
public class Halo {

	public  static final Halo instance = new Halo();

	private static ApplicationContext context;

	private volatile IHaloDispatcher haloDispatcher;

	private HaloContextListener haloListener;

	private IHaloConstraints haloConstraints;

	public IHaloConstraints getHaloConstraints() {
		return haloConstraints;
	}

	public void setHaloConstraints(IHaloConstraints haloConstraints) {
		this.haloConstraints = haloConstraints;
	}

	public ApplicationContext getContext() {

		return context;
	}

	private OnlyOnceCondition onlyOnce=OnlyOnceCondition.create("Halo has been initialized.");
	/**
	 * 对外不可实例化
	 */
	private Halo() {
	}

	public IHaloDispatcher init(ServletContext servletContext,
			IHaloConstraints constraints) {
		onlyOnce.check();
		AnnotationConfigApplicationContext annotationContext = new AnnotationConfigApplicationContext();

		// 首先实例化Context
		context = new ApplicationContext(servletContext, constraints,
				new HaloAnnotationApplicationContext(annotationContext));
		context.init();

		ILogger logger = context.getLogger(Halo.class.getSimpleName());
		logger.info(" The end of ApplicationContext..");

		// 注入dispatcher
		logger.info("The start of dispatch preprocess");
		haloDispatcher = (HaloDispatcher) context.injectResource(HaloDispatcher.class);
		return this.haloDispatcher;

	}

}
