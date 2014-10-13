package org.mapbar.halo;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
/***
 * 自定义异常处理
 * @（#）:HaloException.java 
 * @description:  
 * @author:  sunjy@mapbar.com  2013-4-27 
 * @version: [SVN] 
 * @modify: 
 * @Copyright:  图吧
 */
public class HaloException extends RuntimeException{
	
	public static HaloExceptionBuilder newBuilder(String message) {
        return newBuilder(message, null);
    }

    public static HaloExceptionBuilder newBuilder(Throwable cause) {
        return newBuilder("", cause);
    }

    public static HaloExceptionBuilder newBuilder() {
        return newBuilder("", null);
    }

    public static HaloExceptionBuilder newBuilder(String message, Throwable cause) {
        return new HaloExceptionBuilder(message, cause);
    }
	HaloException(){
		super();
	}
    HaloException(String message){
		super(message);
	}
    HaloException(Throwable cause){
		super(cause);
  	}
    HaloException(String message,Throwable cause){
		super(message,cause);
  	}
    public static HaloException raise(String message){
    	return new HaloException(message);
    }
    public static HaloException raise(Throwable cause){
    	return new HaloException(cause);
    }
    public static HaloException raise(String message,Throwable cause){
    	return new HaloException(message,cause);
    }
    
    public static class HaloExceptionBuilder{
    	
    	 private final Map<String, Object> contextInfos = new HashMap<String,Object>();
    	 private final Throwable cause;
    	 private final String currentMessage;
    	
    	 HaloExceptionBuilder(String message, Throwable cause) {
             this.currentMessage = message;
             this.cause = cause;
         }

    	 HaloExceptionBuilder(Throwable cause) {
             this("", cause);
         }

    	 HaloExceptionBuilder(String message) {
             this(message, null);
         }
    	 
    	 public <T> T raise(Class<T> clazz){
    		 throw build();
    	 }
    	 public HaloExceptionBuilder addContextVariable(String name,Object message){
    		 contextInfos.put(name, message);
    		 return this;
    	 }
    	 public HaloExceptionBuilder addContextVariable(Map<?,?> variables){
    		  for (Map.Entry entry : variables.entrySet()){
    			  addContextVariable(entry.toString(), entry.getValue());
    		  }
    		 return this;
    	 }
    	 
    	 /**
          * 创建一个HaloException
          */
         public HaloException build() {
             return new HaloException(getContextInfo(), cause);
         }
    	 private String getContextInfo(){
    		 return this.currentMessage+(contextInfos.size() > 0  ?  "\ncontext: "  + contextInfos.toString()
                     : "");
    	 }
    }

    
}
