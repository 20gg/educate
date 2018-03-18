package com.hysm.mapper;

import java.util.List; 

import org.apache.ibatis.annotations.SelectProvider;
  

import com.hysm.model.User;
import com.hysm.sql.LoginSQL;
 
public interface LoginMapper {
	
	/**
	 * 查询登录信息
	 * 
	 * @param obj
	 */
	@SelectProvider(method = "query_user_login", type = LoginSQL.class)
	public List<User> query_user_login(String user_id);
	
	@SelectProvider(method = "query_phone_login", type = LoginSQL.class)
	public List<User> query_phone_login(String phone);
	
	@SelectProvider(method = "query_open_login", type = LoginSQL.class)
	public List<User> query_open_login(String open_id);
	
	 
}
