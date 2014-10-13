package org.mapbar.halo.internal;

import static org.junit.Assert.fail;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mapbar.halo.HaloLoaderFactory;
import org.mapbar.halo.annotations.HaloConstraintsAnnotation;

public class HaloConstraintsImplTest {
	
	@Before
	public void setUp() throws Exception {
	}
	
	@Test
	public void testHaloConstraintsImpl() {
		String customerName = HaloConstraints.annotatedHaloConstrainsBinder;
		Class<?> clazz = null;
		HaloConstraints constraints;
		try {
			clazz = HaloLoaderFactory.class.getClassLoader().loadClass(
					customerName);
			constraints = HaloConstraints.class.cast(clazz);
		} catch (Exception e) {
			constraints = null;
		}
		if (constraints != null)
			System.out.println("111111");
		// 如果clazz是否为空，或者clazz的注解为HaloAnnotation
		if (clazz == null
				|| clazz.getAnnotation(HaloConstraintsAnnotation.class) == null) {
			clazz = HaloConstraints.class;
		}
		HaloConstraintsAnnotation constraintsAnnotation = clazz.getAnnotation(HaloConstraintsAnnotation.class);
		URL url = Thread.currentThread().getContextClassLoader().getResource(".");
		File file = null;
		try {
			file = new File(url.toURI());
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		HaloConstraints hl = new HaloConstraints(constraintsAnnotation, file);
	}

	@Test
	@Ignore
	public void testParseHaloConstraintsPath() {
		fail("Not yet implemented");
	}

	@Test
	@Ignore
	public void testGetCurrentProjectConfig() {
		fail("Not yet implemented");
	}

	@Test
	@Ignore
	public void testGetHaloConfig() {
		fail("Not yet implemented");
	}

}
