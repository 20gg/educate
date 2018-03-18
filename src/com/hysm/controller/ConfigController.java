/**
 * 
 */
package com.hysm.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hysm.db.CbgDB;
import com.mongodb.client.model.Filters;

/**
 * XXXXXXXX
 * @author cgb
 * @date 2017年6月1日
 */

@Controller
@RequestMapping("config_1")
public class ConfigController extends BaseController {

	@Autowired
	private CbgDB cgbdb;
	
	
	
	@RequestMapping("show_config")
	public String show_config(HttpServletRequest req,HttpServletResponse response, Map<String, Object> model){
		
		Document config=cgbdb.find_config("config_s");
		
		model.put("config", config);
		
		
		return "/pt/config/config";
	}
	
	
	@RequestMapping("update_config")
	public void  update_config(HttpServletRequest req,HttpServletResponse response, Map<String, Object> model){
		String safe_date =req.getParameter("safe_date");
		String percentage =req.getParameter("percentage");
		String sign_score =req.getParameter("sign_score");
		String video_score =req.getParameter("video_score");
		String scholarship =req.getParameter("scholarship");
		String vip_date =req.getParameter("vip_date");
		String share_score =req.getParameter("share_ss");
		String share_scholarship =req.getParameter("share_scp");
		String vip_money =req.getParameter("vip_money");
		
		Document config=cgbdb.find_config("config_s");
			if(safe_date!=null&&!safe_date.equals("")){
				config.put("safe_date", Integer.valueOf(safe_date));
			}
			
			if(percentage!=null&&!percentage.equals("")){
				config.put("percentage", Integer.valueOf(percentage));
			}
			if(sign_score!=null&&!sign_score.equals("")){
				config.put("sign_score", Integer.valueOf(sign_score));
			}
			if(scholarship!=null&&!scholarship.equals("")){
				config.put("video_score", Integer.valueOf(video_score));
			}
			if(video_score!=null&&!video_score.equals("")){
				config.put("scholarship", Integer.valueOf(scholarship)*100);
			}
		 
			if(vip_date!=null&&!vip_date.equals("")){
				config.put("vip_date", Integer.valueOf(vip_date));
			}
		
			if(share_score!=null&&!share_score.equals("")){
				config.put("share_score", Integer.valueOf(share_score));
			}
			if(share_scholarship!=null&&!share_scholarship.equals("")){
				config.put("share_scholarship", Integer.valueOf(share_scholarship)*100);
				
				
			}
			
			if(vip_money!=null&&!vip_money.equals("")){
				config.put("vip_money", Integer.valueOf(vip_money)*100);
				
				
			}
		cgbdb.replaceOne("config_s", Filters.eq("_id", config.get("_id")), config);
		
		model.put("result", "success");
		
		sendjson(model, response);
		
		
	}
	
}
