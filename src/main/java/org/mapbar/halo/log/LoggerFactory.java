package org.mapbar.halo.log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.PropertyConfigurator;
import org.mapbar.halo.HaloException;
import org.mapbar.halo.constraints.HaloConstraintsUtils;
import org.mapbar.halo.constraints.IHaloConstraints;
import org.mapbar.halo.internal.HaloLogger;
import org.springframework.stereotype.Component;

/**
 * 产生日志类
 * 
 * @author "Daniel Zhang"
 * @email "mailto-zhangdp@mapbar.com"
 */
public class LoggerFactory {
	private static final String configfile = "log4j.properties";

	private Map<String, ILogger> loggerMap = new ConcurrentHashMap<String, ILogger>();

	public LoggerFactory(ILoggerConfig config) {
		config.config();
	}

	/** 通过类名来获得logger */
	public ILogger getLogger(String name) {
		ILogger logger = null;
		synchronized (this) {
			logger = loggerMap.get(name);
			if (logger != null)
				return logger;
			logger = new HaloLogger(org.apache.log4j.Logger.getLogger(name));
			loggerMap.put(name, logger);
		}
		return logger;

	}

	public ILogger getLogger(Class<?> clazz) {
		return getLogger(clazz.getName());
	}

	@Component
	public static class HaloLogConfig implements ILoggerConfig {

		private IHaloConstraints haloConstraints;

		public HaloLogConfig(IHaloConstraints haloConstraints) {
			this.haloConstraints = haloConstraints;
		}

		@Override
		public void config() {
			// 先从约定中的文件夹去寻找log4j.properties
			Properties properties = getConfigProperties();
			if (properties == null)
				properties = getDefaultProperties();
			PropertyConfigurator.configure(properties);
		}

		Properties getDefaultProperties() {
			Properties properties = new Properties();

            properties.put("log4j.rootLogger", "debug, file,stdout");
            properties.put("log4j.appender.file.File", getHaloLogFile());
            properties.put("log4j.appender.file.DatePattern","'.'yyyy-MM-dd");
            properties.put("log4j.appender.stdout", "org.apache.log4j.ConsoleAppender");
            properties.put("log4j.appender.stdout.Target", "System.out");
            properties.put("log4j.appender.stdout.layout", "org.apache.log4j.PatternLayout");
            properties.put("log4j.appender.stdout.layout.ConversionPattern", "[%d{ABSOLUTE}] %5p %c{1}:%L - %m%n");
            properties.put("log4j.appender.file", "org.apache.log4j.DailyRollingFileAppender");
            properties.put("log4j.appender.file.Append", "true");
            properties.put("log4j.appender.file.Threshold", "INFO");
            properties.put("log4j.appender.file.layout", "org.apache.log4j.PatternLayout");
            properties.put("log4j.appender.file.layout.ConversionPattern", "%d{ABSOLUTE} %5p %c{1}:%L - %m%n");

			return properties;
		}

		/** 通过约束获得日志配置文件路径，并解析之 */
		Properties getConfigProperties() {
			File fpath = null;
			// 获得配置日志文件信息
			fpath = HaloConstraintsUtils.configFolder(haloConstraints);
			File file = new File(fpath + configfile);
			if (file.exists()) {
				// 存在的话就要实例化出Properties
				Properties proterties = new Properties();
				try {
					FileReader reader = new FileReader(file);
					proterties.load(reader);
					// 判断是否含有log4j.appender.file.File,如果有则支持用户定义，如果没有则根据约束规定一个默认的日志目录
					if (proterties.containsKey("log4j.appender.file.File")) {
						proterties.put("log4j.appender.file.File",
								getHaloLogFile());
					}
				} catch (FileNotFoundException e) {
					throw HaloException.raise("没有发现该文件目录", e);
				} catch (IOException e) {
					throw HaloException.raise("I/O错误", e);
				}
				return proterties;

			} else
				return null;
		}

		String getHaloLogFile() {
			File logFolder = HaloConstraintsUtils.logFolder(haloConstraints);
			String projectName = haloConstraints.getCurrentProjectConfig()
					.getName();
			if (projectName == null)
				projectName = "halo";
			String fPath = logFolder + projectName + ".log";
			File file = new File(fPath);

			return file.getAbsolutePath();
		}
	}
}
