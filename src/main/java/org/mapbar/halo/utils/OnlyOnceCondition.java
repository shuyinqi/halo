package org.mapbar.halo.utils;

import java.util.concurrent.atomic.AtomicBoolean;

import org.mapbar.halo.HaloException;
import org.mapbar.halo.HaloException.HaloExceptionBuilder;

/**
 * 
 * @author "Daniel Zhang"
 * @email "mailto-zhangdp@mapbar.com"
 */
public class OnlyOnceCondition {

	private String message;

public OnlyOnceCondition(String message) {
	super();
	this.message = message;
}

public static  OnlyOnceCondition create(String message){
	return new OnlyOnceCondition(message);
}
   private final AtomicBoolean haschecked=new AtomicBoolean(false);
   
   public void check(){
	   
	   if(!haschecked.compareAndSet(false, true)){
		   throw HaloException.newBuilder(message).build();
	   }
   }
   
}
