package org.mapbar.halo;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

import javax.servlet.ServletContext;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;
import org.mapbar.halo.annotations.HaloConstraintsAnnotation;
import org.mapbar.halo.constraints.IHaloConstraints;
import org.mapbar.halo.inject.HaloAnnotationApplicationContext;
import org.mapbar.halo.internal.HaloConstraints;
import org.mapbar.halo.internal.HaloDispatcher;
import org.mapbar.halo.servlet.IHaloDispatcher;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @Description
 * @author <a href='huhj@mapbar.com'>胡洪佳</a>
 * @date 2013-5-2
 */
public class TestHalo {

	private ServletContext servletContext;

	private IHaloConstraints constraints;

	private IHaloDispatcher haloDispatcher;

	@Before
	public void setUp() throws Exception {
		servletContext = EasyMock.createMock(ServletContext.class);
		constraints = getHaloContraints();
	}

	@Test
	public void testInit() {
		AnnotationConfigApplicationContext annotationContext = new AnnotationConfigApplicationContext();
		// 首先实例化Context
		ApplicationContext context = new ApplicationContext(servletContext,
				constraints, new HaloAnnotationApplicationContext(
						annotationContext));
		context.init();

		System.out.println(" The end of ApplicationContext..");

		// 注入dispatcher
		System.out.println("The start of dispatch preprocess");
		HaloDispatcher haloDispatcher = (HaloDispatcher) context
				.injectResource(HaloDispatcher.class);
			System.out.println(haloDispatcher);
		System.out.println("The end of dispatch preprocess");

	}

	public static IHaloConstraints getHaloContraints() {
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
