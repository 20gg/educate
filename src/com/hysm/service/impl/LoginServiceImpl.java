package com.hysm.service.impl;
  
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
   
import com.hysm.mapper.LoginMapper;
import com.hysm.model.User; 
import com.hysm.service.ILoginService;

@Service("loginService")
public class LoginServiceImpl implements ILoginService {
	@Autowired
	private LoginMapper loginMapper;

	@Override
	public List<User> query_user_login(String user_id) { 
		return loginMapper.query_user_login(user_id);
	}

	@Override
	public List<User> query_phone_login(String phone) { 
		return loginMapper.query_phone_login(phone);
	}

	@Override
	public List<User> query_open_login(String open_id) { 
		return loginMapper.query_open_login(open_id);
	}

	 
	 
}
