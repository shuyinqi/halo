package org.mapbar.halo.bean;

import org.springframework.stereotype.Component;

/** 
 * @Description
 * @author <a href='huhj@mapbar.com'>胡洪佳</a>
 * @date 2013-5-2
 */
@Component
public class Student {

	private String userName="胡洪佳";
	
	private String passWord;

	public Student(String userName, String passWord) {
		this.userName = userName;
		this.passWord = passWord;
	}
	
	

	public Student() {
		super();
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the passWord
	 */
	public String getPassWord() {
		return passWord;
	}

	/**
	 * @param passWord the passWord to set
	 */
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
}
