package org.mapbar.halo;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

import javax.servlet.ServletContext;

import org.mapbar.halo.annotations.HaloConstraintsAnnotation;
import org.mapbar.halo.constraints.IHaloConstraints;
import org.mapbar.halo.internal.HaloConstraints;
import org.mapbar.halo.servlet.IHaloDispatcher;

/**
 * 初始化Halo的地方。主要包括
 * 
 * 1.约束的初始化
 * 2.halo的初始化
 * @author "Daniel Zhang"
 * @email "mailto-zhangdp@mapbar.com"
 */
public class HaloLoaderFactory {
	
	private static IHaloConstraints constraints;
	
	public static IHaloDispatcher createDispatcher(ServletContext servletContext) {
		// 初始化约束,产生约束
		constraints = getHaloContraints();

		// 初始化halo
		return Halo.instance.init(servletContext, constraints);

	}

	/**
	 * 获得halo级别的约束实体类
	 * 
	 * @return 返回一个组织级约束
	 */
	public static IHaloConstraints getHaloContraints() {
		
		if(constraints != null){
			return constraints;
		}
		
		String customerName = IHaloConstraints.annotatedHaloConstrainsBinder;
		Class<?> clazz = null;
		IHaloConstraints constraints;
		try {
			clazz = HaloLoaderFactory.class.getClassLoader().loadClass(
					customerName);
			constraints = IHaloConstraints.class.cast(clazz);
		} catch (Exception e) {
			constraints = null;
		}
		if (constraints != null)
			return constraints;

		// 如果clazz是否为空，或者clazz的注解为HaloAnnotation
		if (clazz == null
				|| clazz.getAnnotation(HaloConstraintsAnnotation.class) == null) {
			clazz = HaloConstraints.class;
		}
		HaloConstraintsAnnotation constraintsAnnotation = clazz
				.getAnnotation(HaloConstraintsAnnotation.class);
		URL url = Thread.currentThread().getContextClassLoader()
				.getResource(".");
		File file = null;
		try {
			file = new File(url.toURI());
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return new HaloConstraints(constraintsAnnotation, file);
	}
}
