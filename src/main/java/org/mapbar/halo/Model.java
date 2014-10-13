package org.mapbar.halo;

import java.util.Map;

/**
 * 一个K/V的对象,主要用做使用数据模型。
 * 是一个Map结构
 *  
 */
public interface Model {
      
	  /**通过关键字来获得相对应的对象*/
	  <T> T get(String key);
      
      void put(String key,Object value);
      
      void addAll(Map<?, ?> map);
      
      boolean contains(String key);
}
