package org.mapbar.halo.internal;

import org.mapbar.halo.log.ILogger;
/**
 * 实现日志类
 * @author "Daniel Zhang"
 * @email "mailto-zhangdp@mapbar.com"
 */
public class HaloLogger implements ILogger {

	private org.apache.log4j.Logger log;
	public HaloLogger(org.apache.log4j.Logger log){
		this.log=log;
	}
	@Override
	public void debug(Object message) {
	            log.debug(message);
	}

	@Override
	public void debug(Object message, Throwable t) {
		log.debug(message, t);

	}

	@Override
	public void info(Object message) {
		log.info(message);
	}

	@Override
	public void info(Object message, Throwable t) {
	       log.info(message,t);
	}

	@Override
	public void warn(Object message) {
		log.warn(message);
	}

	@Override
	public void warn(Object message, Throwable t) {
           log.warn(message,t);
	}

	@Override
	public void error(Object message) {
		log.error(message);
	}

	@Override
	public void error(Object message, Throwable t) {
		log.error(message,t);
	}

	@Override
	public void fatal(Object message) {
		log.fatal(message);
	}

	@Override
	public void fatal(Object message, Throwable t) {
		log.fatal(message,t);
	}

}
