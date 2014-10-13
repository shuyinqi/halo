package org.mapbar.halo.internal;

import java.io.File;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import org.mapbar.halo.HaloException;
import org.mapbar.halo.annotations.HaloConstraintsAnnotation;
import org.mapbar.halo.constraints.IHaloConstraints;
import org.mapbar.halo.constraints.ProjectConstraints;
import org.mapbar.halo.resource.HaloResource;
import org.mapbar.halo.utils.ClassUtils;
import org.mapbar.halo.utils.CollectionUtils;
import org.mapbar.halo.utils.InjectUtils;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;



/**
 * 约束类的实现类，主要是定义一些halo的约束，比如包名或类名等
 * @author "Daniel Zhang"
 * @email "mailto-zhangdp@mapbar.com"
 * 
 */
@HaloConstraintsAnnotation
public class HaloConstraints implements IHaloConstraints {

	private static final String HALO_CONFIG_FOLDER = "haloConfigFolder";
    public static final String HALO_LOG_FOLDER = "haloLogFolder";
    private static final String PROJECT_ID = "projectId";
    public static final String PACKAGES_PREFIX = "haloPackagesPrefix";
	
	private File folder;
	
	private HaloConfig haloConfig;
	private ProjectConfig projectConfig;
	
	public HaloConstraints(HaloConstraintsAnnotation haloConstraintsAnnotation,File folder) {
		this.folder = folder;
		ProjectConstraints projectConstraints = parseProjectConstraints(haloConstraintsAnnotation);
		Map<String, String> configInfoMap = parseHaloConstraintsPath(haloConstraintsAnnotation,projectConstraints);
		haloConfig = praseHaloConfig(haloConstraintsAnnotation,configInfoMap);
		projectConfig = praseProjectConfig(haloConstraintsAnnotation,projectConstraints);
	}
	
	private HaloConfig praseHaloConfig(HaloConstraintsAnnotation haloConstraintsAnnotation
            , Map<String, String> configInfoMap){
		String configPath = configInfoMap.get(HALO_CONFIG_FOLDER);
		String logPath = configInfoMap.get(HALO_LOG_FOLDER);
		
	// return new HaloConfigImpl(getDir(configPath),getDir(logPath),);
		return null;
	}
	private ProjectConfig praseProjectConfig(HaloConstraintsAnnotation haloConstraintsAnnotation,ProjectConstraints projectConstraints){
		Set<Class<?>> haloResourceSet = parseResources(haloConstraintsAnnotation);
		return new ProjectConfigImpl(projectConstraints.getProjectName(),haloResourceSet,projectConstraints);
	}
	/***
	 * 解析url注解，拿到resource的类
	 * @param groupConventionAnnotation
	 * @return
	 *
	 */
	@SuppressWarnings("unchecked")
	private Set<Class<?>> parseResources(HaloConstraintsAnnotation haloConstraintsAnnotation){
		//根据包路径获取所有的class 	
		Set<Class<?>> classSet = ClassUtils.getClasses(haloConstraintsAnnotation.getPackagePrefix());
		//根据resource命名规范，过滤
		Pattern resourcePattern  = Pattern.compile(haloConstraintsAnnotation.resourcePattern());
		
		Set<Class<?>> haloResourceSet = new HashSet<Class<?>>(); 
		
		for(Class<?> clazz:classSet){
			if(applyArgoResource(clazz,resourcePattern)){
				haloResourceSet.add((Class<?>) clazz);
			}
		}
		return haloResourceSet;
	}
	
	/***
	 * 验证类是否为resource的子类，是否符合正则匹配，是否为接口，是否为抽象的，是否为公共的
	 * @param clazz  
	 * @param resourcePattern
	 * @return
	 */
	private boolean applyArgoResource(Class<?> clazz, Pattern resourcePattern){
		 return  resourcePattern.matcher(clazz.getName()).matches()
	                && !Modifier.isInterface(clazz.getModifiers())
	                && !Modifier.isAbstract(clazz.getModifiers())
	                && Modifier.isPublic(clazz.getModifiers());
	}
	
	
	private ProjectConstraints parseProjectConstraints(HaloConstraintsAnnotation haloConstraintsAnnotation){
		String classname = haloConstraintsAnnotation.projectConstraintsClass();
		Class<?> clazz=null;
		try {
			clazz = Thread.currentThread().getContextClassLoader().loadClass(classname);
		} catch (ClassNotFoundException e) {
			return new ProjectConstraints(){
				@Override
				public void inject(AnnotationConfigApplicationContext context) {
					
				}
				@Override
				public String getProjectName() {
					return "";
				}
			};
		}
		
		ProjectConstraints projectConstraints = null;
		if(ProjectConstraints.class.isAssignableFrom(clazz)){
			projectConstraints=ProjectConstraints.class.cast(newInstanceByClass(clazz,"case class error"));
		}
		if(projectConstraints!=null){
			return projectConstraints;
		}
		throw HaloException.raise(String.format("Class %s not implement ProjectConvention!", classname));
	}
	
	private <T> T newInstanceByClass(Class<T> clazz, String message){
		try {
			return clazz.newInstance();
		} catch (Exception e) {
			throw HaloException.raise(message);
		} 
	}
	
	public Map<String,String> parseHaloConstraintsPath(HaloConstraintsAnnotation haloConstraintsAnnotation,ProjectConstraints projectConstraints){
		Map<String, String> paths = CollectionUtils.newHashMap();
		paths.put(PACKAGES_PREFIX, haloConstraintsAnnotation.getPackagePrefix());
		paths.put(PROJECT_ID, projectConstraints.getProjectName());
		paths.put(HALO_CONFIG_FOLDER, haloConstraintsAnnotation.haloConfigFolder());
		paths.put(HALO_LOG_FOLDER,haloConstraintsAnnotation.haloLogFolder());
		return matchPath(paths);
	}
	
	/***
	 * 解析配置
	 * @param paths
	 * @return
	 * 
	 */
	private static Map<String, String> matchPath(Map<String, String> paths){
		 Map<String, String> values =CollectionUtils.newHashMap();
		 Map<String, String> templates =CollectionUtils.newHashMap();
		 Map<String, String> result =CollectionUtils.newHashMap();
		 classify(paths, values, templates);
		
		while(values.size()>0){
			values=migrate(values, templates, result);
		}
		if(templates.size()>0){
			throw HaloException.newBuilder("GroupConventionAnnotation contains nested expression").addContextVariable(templates).build();
		}
		return result;
	}
	/**
     * 分类数据，若路径中存在"{"，刚归类到模板数据，否则归类到定值数据
     * @param paths 路径数据集合
     * @param values 定值数据集合
     * @param templates 模板数据集合
     * 
     */
	private static void classify(Map<String, String> paths, Map<String, String> values,Map<String, String> templates){
		for(Map.Entry<String, String> entry:paths.entrySet()){
			if(entry.getValue().contains("{")){
				templates.put(entry.getKey(), entry.getValue());
			}else{
				values.put(entry.getKey(), entry.getValue());
			}
		}
	}
	/***
	 * 将定值数据保存到结果数据集合，并将模板数据用定值数据进行替换，并返回替换后的定值
	 * 将模版类型：如：${xxxx}，替换为实际值
	 * @param values  定值数据集合
	 * @param templates  模板数据集合
	 * @param result  结果数据集合
	 * @return 由模板替换后生成的定值数据
	 */
	private static Map<String,String> migrate(Map<String, String> values, Map<String, String> templates, Map<String, String> result){
		 result.putAll(values);
		for(Map.Entry<String, String> templateItem:templates.entrySet()){
			String v = templateItem.getValue();
			for(Map.Entry<String, String> valueItem:values.entrySet()){
				String exp = "{"+valueItem.getKey()+"}";
				if(v.contains(exp)){
					v = v.replace(exp,valueItem.getValue());
				}
			}
			if(!v.equals(templateItem.getValue())){
				templateItem.setValue(v);
			}
		}
		Map<String, String> newValues = CollectionUtils.newHashMap();
        Map<String, String> newTemplates = CollectionUtils.newHashMap();
        classify(templates, newValues, newTemplates);
        templates.clear();
        templates.putAll(newTemplates);
		return newValues;
	}
	
	private File getDir(String path){
		boolean isSub = path.charAt(0)== '.';
		File dir = isSub ?new File(folder,path):new File(path);
		if(dir.isDirectory()){
			return dir;
		}
		if(dir.exists()){
			throw HaloException.raise(String.format("File %s has exist, but not directory.", path));
		}
		if(!dir.mkdirs()){
			 throw HaloException.raise(String.format("Failed to getLogger directory %s.", path));
		}
		return dir;
	}

	@Override
	public ProjectConfig getCurrentProjectConfig() {
		return projectConfig;
	}


	@Override
	public HaloConfig getHaloConfig() {
		return haloConfig;
	}
	 /***
	  * 
	  * 全局配置的实例化
	  * @（#）:HaloConstraintsImpl.java 
	  * @description:  
	  * @author:  sunjy@mapbar.com  2013-4-28 
	  * @version: [SVN] 
	  * @modify: 
	  * @Copyright:  图吧
	  */
	 private class HaloConfigImpl implements HaloConfig {

	        private final File configFolder;
	        private final File logFolder;
	        private final InjectUtils inject;
	       
	        private HaloConfigImpl(File configFolder, File logFolder,InjectUtils inject) {
	            this.configFolder = configFolder;
	            this.logFolder = logFolder;
	            this.inject = inject;
	        }
	        @Override
	        public File getConfigFolder() {
	            return configFolder;
	        }
	        @Override
	        public File getLogFolder() {
	            return logFolder;
	        }
	        @Override
	        public InjectUtils inject() {
	            return inject;
	        }
	    }
	 /***
	  * 项目级别配置的实例化
	  * @（#）:HaloConstraintsImpl.java 
	  * @description:  
	  * @author:  sunjy@mapbar.com  2013-4-28 
	  * @version: [SVN] 
	  * @modify: 
	  * @Copyright:  图吧
	  */
	 private class ProjectConfigImpl implements ProjectConfig {

	        private final String name;
	        private final Set<Class<?>> haloResourceClass;
	        private final InjectUtils inject;

	        private ProjectConfigImpl(String name, Set<Class<?>> haloResourceClass,InjectUtils inject) {
	            this.name = name;
	            this.haloResourceClass = haloResourceClass;
	            this.inject = inject;
	        }
			@Override
			public String getName() {
				return name;
			}
			@Override
			public Set<Class<?>> getHaloResources() {
				return haloResourceClass;
			}
			@Override
			public InjectUtils inject() {
				return inject;
			}
	    }
}
