package com.hysm.bean;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

 
public class Storage_start extends HttpServlet{

	
	
	public Storage_start(){
		super();
	}
	
	public void init() throws ServletException {
		
		
		
		Storage_data.set_storage(99);
		
	
	}
}
