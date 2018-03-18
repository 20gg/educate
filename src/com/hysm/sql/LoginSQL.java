package com.hysm.sql;
  
public class LoginSQL {
	 
	public String query_user_login() {
		String sql = "select a.*,b.role_name from user as a left join role as b on a.role_id = b.id where user_id=#{user_id}";
		return sql;
	}
	
	public String query_phone_login() {
		String sql = "select a.*,b.role_name from user as a left join role as b on a.role_id = b.id where phone=#{phone}";
		return sql;
	}
	
	public String query_open_login() {
		String sql = "select a.*,b.role_name from user as a left join role as b on a.role_id = b.id where open_id=#{open_id}";
		return sql;
	}
  
}
