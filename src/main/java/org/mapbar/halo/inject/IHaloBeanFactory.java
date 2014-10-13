package org.mapbar.halo.inject;

import java.util.Set;
/**
 * 注入的容器类
 * 
 */
public interface IHaloBeanFactory {
       void register(Class<?> clazz);
       
       void register(Set<Class<?>> clazzes);
       
       Object getBean(String beanName);
       
       <T> T getBean(String name,Class<T> requiredType);
       
       <T> T getBean(Class<T> requiredType);
       
       boolean contains(String beanName);
       
       boolean contains(Class<?> clazz);
}
