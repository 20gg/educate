package com.hysm.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hysm.bean.MessageType;
import com.hysm.db.PageDB;
import com.hysm.tools.FileUpAndWr;
import com.hysm.tools.PageBean;
import com.hysm.tools.StringUtil;

@Controller
@RequestMapping("aedit")
public class PageController extends BaseController {

	@Autowired
	private PageDB pdb;

	private String realPath="/jsp/pt/a_edit/show";
	private String ylPath="/jsp/pt/a_edit/yl";
	/**
	 * 
	 * 页面列表 后台管理
	 * 
	 * @return
	 * 
	 */
	@RequestMapping("querySjPages")
	public String querySjPages(HttpServletRequest req, Map<String, Object> model) {
		try {
			Map<String, Object> m = (Map<String, Object>) getSession()
					.getAttribute(MessageType.sessionManager);

			if (m == null) {

				return "login";
			}

			Map<String, Object> map = new HashMap<String, Object>();
			String page = req.getParameter("page");
			String type = req.getParameter("type");// 页面类型
			int pn = 1;

			if (StringUtil.bIsNotNull(page)) {

				pn = Integer.valueOf(page);
			}
			map.put("pn", pn);
			map.put("ps", 20);

			if (StringUtil.bIsNotNull(type)) {

				map.put("type", Integer.valueOf(type));
			} else {
				map.put("type", 1);
			}

			PageBean<Document> pb = new PageBean<Document>();

			pb.setPageNum((Integer) map.get("pn"));
			pb.setPageSize((Integer) map.get("ps"));
			int rc = pdb.queryUrlListNum(map);
			pb.setRowCount(rc);

			List<Document> list = pdb.queryUrlList(map);

			if (list != null && list.size() > 0) {

				pb.setList(list);
			}

			model.put("pb", pb);
			model.put("map", map);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "/pt/a_edit/edit/pages";
	}

	
	
	
	/**
	 * 删除页面
	 * 
	 */
	@RequestMapping("delthisPage")
	@ResponseBody
	public String delthisPage(HttpServletRequest req){
		String id=req.getParameter("id");
		
		pdb.delPages(id);
		
		
		return "200";
		
	}
	/**
	 * 使用状态
	 */
	@RequestMapping("updatePagesUse")
	@ResponseBody
	public String updatePagesUse(HttpServletRequest request){
		String id=request.getParameter("id");
		String use=request.getParameter("use");
		pdb.updatePagesUse(id,use);
		
		return "200";
	}
	/**
	 * 修改
	 * @return
	 */
	@RequestMapping("updatePageDetail")
	@ResponseBody
	public  String updatePageDetail(HttpServletRequest request){
		String id=request.getParameter("id");
		String name=request.getParameter("name");
		String url=request.getParameter("url");
		Document ap=pdb.queryUrlOne(id);
		ap.put("name", name);
		ap.put("realpath", url);
		pdb.updatePageDetail(ap);
		
		return "200";
	}
	
	/**
	 * 设置为主页
	 * @return
	 */
	@RequestMapping("mainpage")
	@ResponseBody
	public  String mainpage(HttpServletRequest request){
		String id=request.getParameter("id");
		//非当前页置为非主页
	
		pdb.mainpage(id);
		
		return "200";
	}
		
		
	/**
	 * 进入修改页面
	 * 
	 */
	
	@RequestMapping("edit2page")
	public  String edit2page(HttpServletRequest request, Map<String, Object> model){
		
//		String id=request.getParameter("id");
		String du=pdb.queryUrlOneStr("592646e2ccf5c630560f7055");
		JSONObject jo=JSONObject.fromObject(du);
		model.put("pid", "592646e2ccf5c630560f7055");
		model.put("data", jo.get("data"));
		
		return "/pt/a_edit/edit/edit";
		
	}


	
	
	/**
	 * 预览
	 */
	@RequestMapping("showpage")
		public void showpage(HttpServletRequest request,HttpServletResponse resp){
			String path = request.getContextPath();
			String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
			String pid=request.getParameter("pid");
			Document json=pdb.queryUrlOne(pid);
			
			
			try {
				String url=basePath+json.get("ylpath");
				resp.sendRedirect(url);
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
		/**
		 * 创建页面
		 * @return
		 */
		@RequestMapping("createPage")
		@ResponseBody
		public String createPage(HttpServletRequest request,HttpServletResponse resp){
			String path = request.getContextPath();
			String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;


			try {
				request.setCharacterEncoding("utf-8");
				resp.setCharacterEncoding("utf-8");
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			String data=request.getParameter("data");
//			String pid=request.getParameter("pid");
			String pid="592646e2ccf5c630560f7055";
			Document json=null;
			if(pid!=null){
				json=pdb.queryUrlOne(pid);
			}
			if(data!=null){
				JSONObject p=JSONObject.fromObject(data);
				json.put("name", p.getString("title")+"("+p.getString("desc")+")");
				String str=createPageHtml(p,1,basePath);//1真实文件
				String str2=createPageHtml(p,2,basePath);//2预览文件
				if(str!=null){
					int res=0;
					if(json.get("_id")!=null){
					
						String real=updatePath(json.get("realpath").toString(),str);
						String yl=updatePath(json.get("ylpath").toString(),str2);
						
						
						
						json.put("data", p);
						
					
						
						pdb.updatePageDetail(json);
							
						}else{
							String real=savePath(realPath,str,request);
							String yl=savePath(ylPath,str2,request);
							
							
							//shopid,offshop,isUse,data,isMain,typeid,realpath,ylpath
							json.put("isUse", 1);
							json.put("data", p);
							json.put("isMain", 0);//1主页0非主页	
							json.put("realpath", real);
							json.put("ylpath", yl);
							
							 pdb.createPage(json);
						}
				
					
					
						
						
						
						
						
						
					}
				return json.get("_id").toString();
				}
				
				
			
		
			
			return null;
		}
		/**
		 * 保存菜单json数据
		 * @return
		 */
	@RequestMapping("updateBTNS")
		public String  updateBTNS(HttpServletRequest request,HttpServletResponse resp){
			 String data=request.getParameter("data");
			 FileUpAndWr.createJson("/a_edit/edit/json/btn.json",data);
				 String path = request.getContextPath();
				 String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
				
			return "200";	 
			
		}
	
		private String updatePath(String path, String str) {
			try {
				return FileUpAndWr.createHtml2(path, str);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		/**
		 * 保存路径
		 * @param realPath2
		 * @param str
		 * @param req 
		 * @return
		 */
		private String savePath(String realPath, String str, HttpServletRequest req) {
		
			try {
				return FileUpAndWr.createHtml(realPath, str);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}


		/**
		 * 将json数据转化成html文件字符
		 * @param data
		 * @return
		 */
		private String createPageHtml(JSONObject json,int option,String basepath) {
			try{
				
			String time=System.currentTimeMillis()/1000+"";
			StringBuffer sb=new StringBuffer();
			sb.append("<!DOCTYPE html><html><head><meta http-equiv=\"Content-Type\" content=\"text/html;charset=utf-8\" ><meta content=\"width=device-width, initial-scale=1.0, minimum-scale=1.0,maximum-scale=1.0, user-scalable=none\" name=\"viewport\" />");
			sb.append("<title>"+json.getString("title")+"</title>");
			
			
			
		
			
			sb.append("<link rel=\"stylesheet\" type=\"text/css\" href=\""+basepath+"/css/wx/discuss/discuss.css?v="+time+"\" />");
			sb.append("<link rel=\"stylesheet\" type=\"text/css\" href=\""+basepath+"/jsp/pt/a_edit/edit/css/common.css?v="+time+"\" />");
			sb.append("<link rel=\"stylesheet\" type=\"text/css\" href=\""+basepath+"/jsp/pt/a_edit/edit/css/reset.css?v="+time+"\" />");
			sb.append("<link rel=\"stylesheet\" type=\"text/css\" href=\""+basepath+"/jsp/pt/a_edit/edit/css/shop.css?v="+time+"\" />");
			sb.append("<link rel=\"stylesheet\" type=\"text/css\" href=\""+basepath+"/jsp/pt/a_edit/edit/css/swiper.min.css?v="+time+"\" />");
			sb.append("<link rel=\"stylesheet\" type=\"text/css\" href=\""+basepath+"/jsp/pt/a_edit/edit/css/star-list.css?v="+time+"\" />");
			sb.append("<link rel=\"stylesheet\" type=\"text/css\" href=\""+basepath+"/jsp/pt/a_edit/edit/css/app-content.css?v="+time+"\" />");
			//sb.append("<link rel=\"stylesheet\" type=\"text/css\" href=\""+basepath+"/jsp/pt/a_edit/edit/css/ad.css?v="+time+"\" />");
			
			sb.append("<script src=\""+basepath+"/js/jquery-2.1.1.min.js?v="+time+"\" type=\"text/javascript\" charset=\"utf-8\"></script>");
			sb.append("<script src=\""+basepath+"/js/url.js?v="+time+"\" type=\"text/javascript\" charset=\"utf-8\"></script>");
			
			if(option==1){
				
				sb.append("<script src=\""+basepath+"/js/wx/wx_empower.js?v="+time+"\" type=\"text/javascript\" charset=\"utf-8\"></script>");
			}
			
			
			sb.append("</head>");
			sb.append("<body style=\"background:"+json.getString("bgcolor")+"; -webkit-text-size-adjust: 100%;text-size-adjust: 100%; -moz-text-size-adjust: 100% \"    data='"+json.getString("desc")+"'>");
			sb.append("<audio id=\"music\" ></audio>");
			JSONArray models=json.getJSONArray("models");
			for(int i=0;i<models.size();i++){
				JSONObject one=models.getJSONObject(i);
				int type=Integer.valueOf(String.valueOf(one.get("type")));
				switch (type) {
					case 1:
						sb.append(" <div class='top_box'> <div class='top'> <div class='head'> <img id='head_img' src=''></div> <div class='name' id='my_name'></div> <div class='lel' id='leve_1'> <img id='role_img' src='"+basepath+"/img/role/role0.png'> </div> <div class='clearfix'></div> </div>	 </div>");
					
						 
						break;
					case 2:
						
						
				
						
						sb.append("<div class=\"textdiv\">");
						
						sb.append("<div class=\"texttou\"><img src='"+basepath+"/jsp/pt/a_edit/edit/img/live.png' ><b id=\"checktimeone\"  stime='"+one.get("time")+"'>直播未开始</b></div>");
						
						sb.append("<a h='"+one.get("url")+"' id=\"yg_link\" onclick=\"into_yg(this);\" ><p>"
								+ one.get("title")
								+ "</p></a></div>");
					
						
						break;
					case 3:
						
						sb.append("<div class=\"caidan\">");
						
						JSONArray cd=one.getJSONArray("cd");
						
						if (cd != null && cd.size() > 0) {
							int widthstr = 100 / cd.size();
							for (int j = 0; j < cd.size(); j++) {
								JSONObject onej = cd.getJSONObject(j);
								String name=onej.getString("name");
								String icon=onej.getString("icon");
								String url=onej.getString("url");
								sb.append("<div class=\"onecd\" style=\"width: " + widthstr + "%\" onclick='window.location.href=\""+url+"\"'>");
								
								sb.append("<img src='"+basepath+icon+"' class='icons'>");
								sb.append("<div class=\"cdname\">" + name + "</div>");
								sb.append("<div class=\"bordercd\"></div></div>");
							
							}
						}
						
						sb.append("</div>");
						
						
						
					
					break;
					case 4:
						Object height=one.get("height");
						if (height!=null) {
							sb.append("<div class=\"jgdiv\" style=\"height:" + height + "px\" > </div>");
						} else {
							sb.append("<div class=\"jgdiv\" > </div>");
						}
						
						
						
					break;
					case 5:
						
						
						
						
						int kind1=StringUtil.toNum(one.get("kind"));//1视频2音频
						if(kind1==1){
							
							
							sb.append("<div class=\"couponlist\">");
							
							Object bgimg=one.get("img");
							if(StringUtil.bIsNotNull(bgimg)){
								sb.append("<img src='"+bgimg+"' class=\"bgimg\"/>");
							}
							sb.append("<div class=\"couponmian\" id=\"free_page\" >");
							JSONArray coupons=one.getJSONArray("courses");
							
							if (coupons!= null&&coupons.size()>0) {
								
								for (int j = 0; j < coupons.size(); j++) {
									JSONObject onej = coupons.getJSONObject(j);
									sb.append("<div class=\"couponone img"+(j+1)+"\" style=\"width:30%;\" onclick=\"goCurser('" + onej.getString("c_no")+ "')\">");
									sb.append("<div class=\"imgdiv\"><img src=\"" +onej.getString("img") + "\" /><img class=\"videoicon\" src=\""+basepath+"/jsp/pt/a_edit/edit/img/hot/video.png\"></div>");
									
									if(onej.getString("c_name").length()<6){
										sb.append("<b>"+onej.getString("c_name")+"</b>");
									}else{
										sb.append("<b>"+onej.getString("c_name").substring(0, 6)+"..</b>");
									}
								
									
									
									sb.append("</div>");
								}

							}
							
							sb.append("</div>");
							
							sb.append("<div style='clear:both'></div>");
							sb.append("</div>");
							
							
							
						}else{
							
							sb.append("<div class=\"courselist\"><div class=\"maincourse\" id=\"music_page\" > ");
							
								Object bgimg=one.get("img");
								if(bgimg!=null&&(!bgimg.equals(""))){
									sb.append("<img src='"+bgimg+"' class=\"bgimg\"/>");
								}
							
								JSONArray coupons=one.getJSONArray("courses");
								
							if (coupons!= null && coupons.size() > 0) {
								
								sb.append("<div class=\"leftcorse\" style='width:100%'>");
								for ( int j = 0; j < coupons.size(); j++) {
									JSONObject onej = coupons.getJSONObject(j);
									
									sb.append("<div class=\"courseone\"  onclick=\"goCurser('" + onej.getString("c_no")+ "')\">");
									sb.append("<img src=\"" +basepath + "/jsp/pt/a_edit/edit/img/sanjiao.png\" />");
									
									if(onej.getString("c_name").length()<25){
										sb.append("<b>"+onej.getString("c_name")+"</b>");
									}else{
										sb.append("<b>"+onej.getString("c_name").substring(0, 25)+"..</b>");
									}
									sb.append("</div>");
									
								}
								sb.append("</div>");
							/*	sb.append("<div class=\"leftcorse\" >");
								for ( int j = 0; j < coupons.size(); j++) {
									JSONObject onej = coupons.getJSONObject(j);
									
									sb.append("<div class=\"courseone\"  onclick=\"goCurser('" + onej.getString("c_no")+ "')\">");
									sb.append("<img src=\"" +basepath + "/jsp/pt/a_edit/edit/img/sanjiao.png\" />");
									
									if(onej.getString("c_name").length()<15){
										sb.append("<b>"+onej.getString("c_name")+"</b>");
									}else{
										sb.append("<b>"+onej.getString("c_name").substring(0, 15)+"..</b>");
									}
									sb.append("</div>");
								}
								sb.append("</div>");
								sb.append("<div class=\"rightcorse\" >");
								sb.append("<img class=\"startVoice\" src='" +basepath+ "/jsp/pt/a_edit/edit/img/index_start2.png' />");
								sb.append("<b><img src='"+basepath+"/jsp/pt/a_edit/edit/img/index_erji.png' class='icon' /><span onclick=\"go_course_list()\">查看全部</span></b>");
							
								sb.append("</div>");*/
								
								//听的人数
//								sb.append("<script src=\""+basepath+"/js/common/listennum.js?v="+time+"\" type=\"text/javascript\" charset=\"utf-8\"></script>");
								
							}
							
							sb.append("</div><b class='commonb'  onclick='window.location.href=\""+basepath+"/jsp/wx/course/course_list.html\"'>查看全部</b></div>");
							
							
							
							
							
						}
						
						
						
					break;
					case 6:
						sb.append("<div class=\"piclist app-field clearfix\" mod=\"banner\" effect=\"\" loop=\"loop\" ><div class=\"control-group\"><div class=\"swiper-container\"><ul class=\"swiper-wrapper\">");
						JSONArray imgs=one.getJSONArray("imgs");
						if (imgs != null && imgs.size() > 0) {
							for (int j = 0; j < imgs.size(); j++) {
								JSONObject onej = imgs.getJSONObject(j);
								String url=onej.getString("url");
								Object title=onej.get("title");
								if(title==null){
									title="";
								}
								String img=onej.getString("img");
								sb.append("<li class=\"swiper-slide\">");
								sb.append("<a href=\"" + url + "\" title=\"" + title + "\" class=\"custom-url\"><img src=\""+basepath + img + "\"></a></li>");
							}
						}
						sb.append("</ul><div class=\"swiper-pagination\"></div></div></div> </div>");
						
					break;
					case 7:
						sb.append("<div class=\"onetitle\">");
						
						sb.append("<img src=\"" +basepath+ one.getString("img") + "\" />");
						sb.append("<h2 style=\"color:" +one.getString("color") + ";\">" +one.getString("name") + "</h2></div>");
						
					break;
					case 8:
						int kind8=1;
						Object k=one.get("kind");
						if(k!=null&&StringUtil.toNum(k)!=0){
							kind8=StringUtil.toNum(k);
						}
						
						sb.append("<div class=\"tqmarea   ");
						if(kind8==1){
							sb.append(" imglist1 ");

						}else if(kind8==2){
							
							
							sb.append(" imglist2 ");
							
							
						}else{
							
							sb.append(" imglist1 ");
						}
						
						sb.append(" \">");
						
						JSONArray imgsobj=one.getJSONArray("imgs");
						for(int j=0,len=imgsobj.size();j<len;j++){
							JSONObject onej=imgsobj.getJSONObject(j);
							sb.append("<a class='imgs oimg"+(j+1)+"' href=\"" + onej.getString("url") + "\"><img src=\""+basepath + onej.getString("img") + "\" /></a>");
							
							
						}
						
						sb.append("</div>");
						
					
						
	
					break;
					case 9:
						/* kind: 2, //1大图，2小图3，一大两小4列表
						*/
						
						
						int kind = Integer.valueOf(String.valueOf(one.get("kind")));
						String title=String.valueOf(one.get("title"));
						sb.append("<div class=\"goodsarea app-field clearfix\" mod=\"shoppingList\" ><div class=\"control-group\">");
					
						sb.append("<div class=\"title\" onclick=\"huan_huan();\" >"+title+"<b><img src=\""+basepath+"/jsp/pt/a_edit/edit/img/genghuan.png\"  />换一换</b></div>");
						
						if (kind == 4) {
							sb.append("<ul class=\"sc-goods-list clearfix card pic list\" id=\"sj_special\">");
						} else {
							sb.append("<ul class=\"sc-goods-list clearfix card pic\" id=\"sj_special\">");
						}
						JSONArray goods=one.getJSONArray("goods");
						if (goods != null && goods.size() > 0) {
							int count=0;
							for (int j = 0; j < goods.size(); j++) {
								JSONObject onej = goods.getJSONObject(j);
								
								Object gid=onej.get("special_no");
								
							
								if(StringUtil.bIsNotNull(gid)){
									if (kind == 3 && count%3 == 0) {
										sb.append("<li class=\"goods-card card big-pic\">");
									} else  if(kind==1){
										sb.append("<li class=\"goods-card card big-pic\">");
									}else{
										
										sb.append("<li class=\"goods-card card small-pic\">");
									}
									
									sb.append("<a onclick=\"gointoGoodsInfo('" + gid + "')\" class=\"js-goods clearfix\">");
									sb.append("<div class=\"photo-block\"><img class=\"goods-photo js-goods-lazy\" tmp=\"\" src=\"/educate/img/logo.png\" data-echo=\""+onej.getString("img")+"\"><img class=\"videoicon\" src=\""+basepath+"/jsp/pt/a_edit/edit/img/hot/video.png\"></div>");
									
									if (kind == 4) {
										
										sb.append("<div class=\"info  btn1 info-price info-no-price\">");
										
									} else {
										
										sb.append("<div class=\"info clearfix btn1 info-price info-no-price\">");
									}
									
									sb.append("<p class=\"goods-title toutitle \">");
										if(onej.getString("special_name").length()<8){
											sb.append( onej.getString("special_name"));
										}else{
											sb.append( onej.getString("special_name").substring(0,6)+"...");
										}
									
										
										sb.append("</p>");
										sb.append("<p class=\"goods-title\">");
										if(onej.getString("text").length()<12){
											sb.append( onej.getString("text"));
										}else{
											sb.append( onej.getString("text").substring(0,15)+"");
										}
										
										sb.append( "</p>");
										
										
										sb.append("<p class=\"goods-title price\">￥" + onej.getString("price") + "</p>");
										if(onej.get("bq")!=null){
											sb.append("<p class=\"goods-title\"> <span class=\"bq\">" + onej.getString("bq") + "</p>");
												
										}	
										
										
										sb.append("<p class=\"goods-title weizhujiang\"> 主讲:" + onej.getString("teacher") + "</p>");
									
									sb.append("</div></a>");
								
										
									sb.append("<label class=\"goodsbtn\" >"+onej.getString("num")+"人订阅</label>");

									
									sb.append("</li>");
									count++;
								}
								
								
								
								
								
							}

						}
 
						sb.append("</ul></div><div class=\"weiquankan\" onclick=\"go_special();\"><span id=\"quan_bu\" style=\"border:none\"></span></div></div>  ");
 
						sb.append("<br><div id='little_special' class=\"goodsarea app-field clearfix\" ><div class=\"title\"  >小课题<b  onclick=\"show_little_special(1);\"><img src=\""+basepath+"/jsp/pt/a_edit/edit/img/genghuan.png\"    />换一换</b></div><ul class=\"sc-goods-list clearfix card pic list\" id=\"sj_special2\"> </ul></div>");
						
 
						/*sb.append("</ul></div><div class=\"weiquankan\" onclick=\"go_special();\"><span>查看全部专题<b id=\"numsshow\"></b>个</span></div></div>");
 
						
*/
						/*sb.append("<a class=\"shopping_cart_lu\" href=\"javascript:void(0);\"onclick=\"showShopingCart();\">购物车<i style=\"display:none\">0</i> </a>");
*/

						
						
						
						
					
						
						

						
					break;
					case 10:
						int kind2=Integer.valueOf(String.valueOf(one.get("kind")));
						if (kind2 == 1) {
							sb.append("<div class=\"xxdiv\" style=\"border:1px solid " + one.getString("color") + "; \">");
						} else {
							sb.append("<div class=\"xxdiv\" style=\"border-color:" + one.getString("color") + ";\">");
						}
							sb.append("</div>");
						break;
					case 11:
						sb.append("<div class=\"hotdiv\">");
						
						sb.append("<div class=\"hottou\"><span>热门排行榜</span></div>");
						
						sb.append("<div class=\"hotdivgoods\" id=\"hot_page\" >");
				
						JSONArray goodsja=one.getJSONArray("goods");
						if (goodsja!= null && goodsja.size() > 0) {
							
							
							for ( int j = 0; j < goodsja.size(); j++) {
								JSONObject  onej = goodsja.getJSONObject(j);
								sb.append("<div class=\"hotone img"+(j+1)+"\" style='width:30%;'  onclick=\"goPh('" + onej.get("id") + "',"+onej.get("kind")+")\" >");
								sb.append("<div class=\"imgdiv\"><img src='" + onej.getString("img") + "' /> <img class='videoicon' src=\""+basepath+"/jsp/pt/a_edit/edit/img/hot/");
							
								
								
								if(onej.get("type")!=null&&StringUtil.toNum(onej.get("type"))==1){
									sb.append("video.png");
								}else{
									sb.append("audio.png");
								}
									
										
										
										
										
								sb.append("\"></div><span class=\"pmspan\">"+onej.getString("pm")+"</span>");
								if(onej.getString("name").length()<7){
									sb.append("<b>"+onej.getString("name")+"</b>");
								}else{
									sb.append("<b>"+onej.getString("name").subSequence(0, 6)+".."+"</b>");
								}
								
							
								sb.append("</div>");
							}

						}
						
						sb.append("</div>");
						sb.append("<b class='commonb'  onclick=\"go_hot();\">查看全部</b>");
						sb.append("</div>");
						
					

						
						
						
						
						break;
					case 12:
						sb.append("<div class=\"nav\" ><nav><div id=\"nav_ul\" class=\"nav_\"><ul class=\"box\">");
						JSONArray btns=one.getJSONArray("btns");
						if (btns != null&&btns.size()>0) {
							for (int j = 0; j < btns.size(); j++) {
								JSONObject onej = btns.getJSONObject(j);
								sb.append("<li onclick=\"window.location.href=\'" + onej.getString("url") + "\'\"><a class=\"\"><label style=\"font-size: 12px;\">" + onej.getString("name") + "</label></a></li>");
							}
						}
						sb.append("</ul></div></nav></div>");
						break;
						
				
				}
				
			}
			sb.append("</body>");
			
			sb.append("<script src=\""+basepath+"/js/common/hide_music.js?v="+time+"\" type=\"text/javascript\" charset=\"utf-8\"></script>");
			
			sb.append("<script src=\""+basepath+"/jsp/pt/a_edit/edit/js/swiper-3.2.5.min.js?v="+time+"\" type=\"text/javascript\" charset=\"utf-8\"></script>");
			
			sb.append("<script src=\""+basepath+"/jsp/pt/a_edit/edit/js/banner-swiper.js?v="+time+"\" type=\"text/javascript\" charset=\"utf-8\"></script>");
			sb.append("<script src=\""+basepath+"/jsp/pt/a_edit/edit/js/echo.min.js?v="+time+"\" type=\"text/javascript\" charset=\"utf-8\"></script>");
			
			sb.append("<script src=\""+basepath+"/jsp/pt/a_edit/edit/js/nav.js?v="+time+"\" type=\"text/javascript\" charset=\"utf-8\"></script>");
			
			//sb.append("<script src=\""+basepath+"/jsp/pt/a_edit/edit/js/ad.js?v="+time+"\" type=\"text/javascript\" charset=\"utf-8\"></script>");
			sb.append("<script src=\""+basepath+"/js/wx/home_wx.js?v="+time+"\" type=\"text/javascript\" charset=\"utf-8\"></script>");
			sb.append("<script src=\""+basepath+"/js/common/wx_menu.js?v="+time+"\" type=\"text/javascript\" charset=\"utf-8\"></script>");
			sb.append("<script src=\""+basepath+"/js/common/wxshop.js?v="+time+"\" type=\"text/javascript\" charset=\"utf-8\"></script>");
			sb.append("<script src=\""+basepath+"/js/common/echoutil.js?v="+time+"\" type=\"text/javascript\" charset=\"utf-8\"></script>");
			sb.append("<script src=\""+basepath+"/js/common/swiperUtil.js?v="+time+"\" type=\"text/javascript\" charset=\"utf-8\"></script>");
			sb.append("<script src=\""+basepath+"/js/common/checktime.js?v="+time+"\" type=\"text/javascript\" charset=\"utf-8\"></script>");
			sb.append("<script src=\""+basepath+"/js/common/onlinetime.js?v="+time+"\" type=\"text/javascript\" charset=\"utf-8\"></script>");
			sb.append("<script src=\""+basepath+"/js/wx/tan_c.js?v="+time+"\" type=\"text/javascript\" charset=\"utf-8\"></script>");
			sb.append("</html>");
			return sb.toString();
			}catch (Exception e) {
				e.printStackTrace();
			}
		return null;
		}
}
