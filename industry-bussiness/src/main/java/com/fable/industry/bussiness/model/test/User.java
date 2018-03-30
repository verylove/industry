package com.fable.industry.bussiness.model.test;

import java.util.HashMap;
import java.util.Map;

/**
 * 测试文件
 * @author duyang
 *
 */
public class User {

	private String id;
	
	private String userName;
	
	private String password;
	
	private Integer age;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}
	
	
	public Map<String,Object> toMap() {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("id", getId());
		map.put("userName", getUserName());
		map.put("password", getPassword());
		map.put("age", getAge());
		return map;
	}
}
