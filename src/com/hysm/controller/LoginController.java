package com.hysm.controller;

import java.util.ArrayList;
import java.util.List;




import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;









import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;








import org.springframework.web.bind.annotation.ResponseBody;

import com.hysm.db.CbgDB;

@Controller
@RequestMapping("/login")
public class LoginController extends BaseController {
	Logger logger = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	private CbgDB cgbdb;
	
	 /**
	  * 平台管理员登录，app登录
	  * @param request
	  * @param response
	  * @param model
	  */
	@ResponseBody
	@RequestMapping(value = { "/login" }, method = { RequestMethod.POST })
	public void user_login(HttpServletRequest request,
			HttpServletResponse response, Map<String, Object> model){
		
		String name = request.getParameter("name");
		String passwd = request.getParameter("passwd"); 
		//passwd=StringUtil.MD5(passwd);
		
		 
		
		Document doc=new Document();
			doc.put("name", name);	
			doc.put("passwd", passwd);
		
				Document user_m=cgbdb.findbobyinfo("user_m", doc);
				
				List<Map<String, Object>> list=null;
				
				list=cgbdb.query_user_name(name);
				
				if(user_m!=null&&user_m.getInteger("state")==0){
					
					if(user_m.getString("passwd").equals(passwd)){
						
						model.put("error", 1);
						request.getSession().setAttribute("user", list.get(0));
						 
					}else{
						model.put("error", 2);//密码不正确
					}
					
					
				}else{
					
					model.put("error", 3);//用户名不存在
				}
				sendjson(model, response);
}
	
	
	/**
	 * 进入平台
	 * @param request
	 * @param response
	 * @param model
	 */ 
	@RequestMapping(value = { "/into_pt" }, method = { RequestMethod.POST })
	public void into_pt(HttpServletRequest request,
			HttpServletResponse response, Map<String,Object> model){
		
		int error_code = 0;
		if(getSession().getAttribute("user")!= null){
			error_code = 1;
			
			Map<String,Object> user = (Map<String,Object>)getSession().getAttribute("user");
			
			int role_id = Integer.valueOf(user.get("role_id").toString());
			 
			
			Map<String,Object> role_info = cgbdb.query_role_info(role_id);
			
			List<Integer> menu_list = (List<Integer>)role_info.get("menu_list");
			
			List<Map<String,Object>> list = cgbdb.query_menu();
			
			//找出角色对应的菜单
			
			List<Map<String,Object>> menu_arr = new ArrayList<Map<String,Object>>();
			
			for(int i=0;i<menu_list.size();i++){
				
				int mid = menu_list.get(i);
				
				for(int j=0;j<list.size();j++){ 
					 int menu_id = Integer.valueOf(list.get(j).get("menu_id").toString());
					 
					 int state = Integer.valueOf(list.get(j).get("state").toString());
					 
					 int type = Integer.valueOf(list.get(j).get("type").toString());
					 
					 if(mid == menu_id && state == 1 && type == 1){
						 menu_arr.add(list.get(j));
					 }
				}
			}
			
			model.put("user", user);
			model.put("menu_arr", menu_arr);
			
		}
		
		model.put("error_code", error_code);
		
		 sendjson(model, response);
	}
	
   
}
