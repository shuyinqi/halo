package org.mapbar.halo.inject;

import java.util.Set;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class HaloAnnotationApplicationContext implements IHaloBeanFactory {

	private AnnotationConfigApplicationContext annotationContext;
	
	public HaloAnnotationApplicationContext(
			AnnotationConfigApplicationContext annotationContext) {
		this.annotationContext = annotationContext;
	}

	@Override
	public void register(Class<?> clazz) {
		annotationContext.register(clazz);
	}

	@Override
	public void register(Set<Class<?>> clazzes) {
		for (Class<?> resource : clazzes) {
			annotationContext.register(resource);
		}
	}

	@Override
	public Object getBean(String beanName) {
		return annotationContext.getBean(beanName);
	}

	@Override
	public boolean contains(String beanName) {
		return annotationContext.containsBean(beanName);
	}

	@Override
	public boolean contains(Class<?> clazz) {
		// TODO Auto-generated method stub
		
		return false;
	}

	@Override
	public <T> T getBean(String name, Class<T> requiredType) {
		return annotationContext.getBean(name,requiredType);
	}

	@Override
	public <T> T getBean(Class<T> requiredType) {
		return annotationContext.getBean(requiredType);
	}

}
