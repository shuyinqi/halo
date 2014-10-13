package org.mapbar.halo.log;
/**
 * 日志文件
 * 
 */
public interface ILogger {
	/**调试日志 Level*/
     void debug(Object message);
     void debug(Object message,Throwable t);
     /**信息日志Level*/
     void info(Object message);
     void info(Object message,Throwable t);
     /**警告日志Level*/
     void warn(Object message);
     void warn(Object message,Throwable t);
     /**错误日志Level*/
     void error(Object message);
     void error(Object message,Throwable t);
     /**致命日志Level*/
     void fatal(Object message);
     void fatal(Object message,Throwable t);
}
