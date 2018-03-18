package com.hysm.service;

import java.util.List; 

import com.hysm.model.User; 
 

public interface ILoginService {
 
	public List<User> query_user_login(String user_id);
	
	public List<User> query_phone_login(String phone);
	
	public List<User> query_open_login(String open_id);
	
	 
}