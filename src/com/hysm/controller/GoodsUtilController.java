package com.hysm.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hysm.db.GoodsDB;
import com.hysm.tools.PageBean;
import com.hysm.tools.StringUtil;

@Controller
@RequestMapping("/goodsutil")
public class GoodsUtilController  extends BaseController{
	@Autowired
	private GoodsDB gdb;
	
	/**
	 * 获取专题、课程、排行
	 * @return
	 */
	@RequestMapping("queryAllGoods")
	@ResponseBody
	public Object queryAllGoods(HttpServletRequest req){
		
		String typestr=req.getParameter("type");
		
		String page=req.getParameter("page");
		
		
		String likename=req.getParameter("likename");
		String goodsstr=req.getParameter("goods");
		
		String kindstr=req.getParameter("kind");
		
		
		int type=2;
		if(StringUtil.bIsNotNull(typestr)){
			type=StringUtil.toNum(typestr);
		}
		int pn=1;
		if(StringUtil.bIsNotNull(page)){
			pn=StringUtil.toNum(page);
			if(pn<1){
				pn=1;
			}
		}
		
		
		
		
		
		
		
		if(type==1){
			//课程
			PageBean<JSONObject> pb=new PageBean<JSONObject>();
			pb.setPageNum(pn);
			pb.setPageSize(10);
			
			int rc=gdb.countNumForCourse(likename,goodsstr,0,kindstr);
			pb.setRowCount(rc);
			List<JSONObject> listja=gdb.queryForCourse(pb,likename,goodsstr,0,kindstr);
			
			if(listja!=null){
				pb.setList(listja);
			}
			
			return pb;
			
		}else if(type==2){
			//免费课程
			PageBean<JSONObject> pb=new PageBean<JSONObject>();
			pb.setPageNum(pn);
			pb.setPageSize(10);
			
			int rc=gdb.countNumForCourse(likename,goodsstr,2,kindstr);
			pb.setRowCount(rc);
			List<JSONObject> listja=gdb.queryForCourse(pb,likename,goodsstr,2,kindstr);
			
			if(listja!=null){
				pb.setList(listja);
			}
			
			return pb;
		}else if(type==3){
			//专题
			
			PageBean<JSONObject> pb=new PageBean<JSONObject>();
			pb.setPageNum(pn);
			pb.setPageSize(10);
			
			int rc=gdb.countNumForSpecial(likename,goodsstr);
			pb.setRowCount(rc);
			List<JSONObject> listja=gdb.queryForSpecial(pb,likename,goodsstr);
			
			if(listja!=null){
				pb.setList(listja);
			}
			
			return pb;
			
			
			
		}else if(type==4){
			//排行
			//只取前10
			
			PageBean<JSONObject> pb=new PageBean<JSONObject>();
			pb.setPageNum(1);
			pb.setPageSize(10);
			
			pb.setRowCount(10);
			
			List<JSONObject> all=new ArrayList<JSONObject>();
			List<JSONObject> listc=gdb.queryForCourseTop10();
			
			List<JSONObject> lists=gdb.queryForSpecialTop10();
			
			if(listc!=null&&listc.size()>0){
				all.addAll(listc);
			}
			if(lists!=null&&lists.size()>0){
				all.addAll(lists);
			}
			
			
			//排序取前10;
			List<JSONObject> listnow=paixu(all);
			if(listnow!=null&&listnow.size()>0){
				pb.setList(listnow);
			}
			return pb;
			
			
			
			
			
		}
		
		
		
		return null;
		
	}

	/**
	 * 排序
	 * @param all
	 * @return
	 */
	private List<JSONObject> paixu(List<JSONObject> all) {
		
		 int i = all.size(), j;
		    JSONObject  tempExchangVal;
		    while (i > 0) {
		        for (j = 0; j < i - 1; j++) {
		        	JSONObject onej=all.get(j);
		        	JSONObject onej1=all.get(j+1);
		            if (Integer.valueOf(String.valueOf(onej.get("watch")))< Integer.valueOf(String.valueOf(onej1.get("watch")))) {
		                tempExchangVal = onej;
		                all.set(j, onej1);
		                all.set(j+1, tempExchangVal);
		            }
		        }
		        i--;
		    }
		    
		   
		    return all;
	}

}
