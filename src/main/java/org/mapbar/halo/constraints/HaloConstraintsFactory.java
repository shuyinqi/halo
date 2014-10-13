package org.mapbar.halo.constraints;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

import org.mapbar.halo.annotations.HaloConstraintsAnnotation;
import org.mapbar.halo.internal.HaloConstraints;

/***
 * @（#）:HaloConstraintsFactory.java 
 * @description:  
 * @author:  sunjy@mapbar.com  2013-4-27 
 * @version: [SVN] 
 * @modify: 
 * @Copyright:  图吧
 */
public class HaloConstraintsFactory {

	public static IHaloConstraints getHaloConstraints(){
		/****
		 * 获得组织级别约束的类名称
		 */
		String classname = IHaloConstraints.annotatedHaloConstrainsBinder;
		Class<?> clazz=null;
		IHaloConstraints haloConstraints;
		 try {
			clazz = HaloConstraintsFactory.class.getClassLoader().loadClass(classname);
			haloConstraints = IHaloConstraints.class.cast(clazz);
		 } catch (ClassNotFoundException e) {
			 haloConstraints = null;
		}
		/***
		 * 自定义所有约束实现
		 */
		if(haloConstraints!=null){
			return haloConstraints;
		}
		if(clazz==null || clazz.getAnnotation(HaloConstraintsAnnotation.class)==null){
			clazz = HaloConstraints.class;
		}

		HaloConstraintsAnnotation haloConstraintsAnnotation = clazz.getAnnotation(HaloConstraintsAnnotation.class);
		
		
		ClassLoader cl = Thread.currentThread().getContextClassLoader();
		URL url = cl.getResource("."); 
		File folder=null;
		try {
			folder = new File(url.toURI());
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	
		return new HaloConstraints(haloConstraintsAnnotation,folder);
	}
}
