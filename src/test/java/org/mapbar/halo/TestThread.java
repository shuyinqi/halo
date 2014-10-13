package org.mapbar.halo;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.junit.Test;
import org.mapbar.halo.bean.Student;

/** 
 * @Description
 * @author <a href='huhj@mapbar.com'>胡洪佳</a>
 * @date 2013-5-3
 */
public class TestThread {
	
	@Test
	public void testMain(){
		String str = "/user/{name}";
		str = str.replaceAll("\\{.*?}","");
		System.out.println(str);
	}
}
