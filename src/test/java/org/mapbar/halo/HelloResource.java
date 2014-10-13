package org.mapbar.halo;

import org.mapbar.halo.annotations.Path;
import org.mapbar.halo.bean.Student;

/** 
 * @Description
 * @author <a href='huhj@mapbar.com'>胡洪佳</a>
 * @date 2013-5-3
 */
@Path(value = "/hello")
public class HelloResource{

	@Path(value = "/create")
	public Student create(){
		
		return new Student();
	}
	
	@Path(value = "/delete")
	public void delete() {
		
	}
	
	@Path(value = "/user/{name}/password")
    public Student user(Student student,String name) {
		System.out.println("hello:"+name);
		return new Student(name,"111111111");
    }
	@Path(value = "/user/{name}/{value}/{test}")
    public void user1(String name,String value) {
		System.out.println("hello:"+name+","+value);
    }     
}
