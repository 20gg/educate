package com.hysm.controller;

 
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Formatter;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.bson.BasicBSONObject;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hysm.bean.MessageType;
import com.hysm.bean.WxInfo;
import com.hysm.db.Login_db;
import com.hysm.tools.CookieDo;
import com.hysm.tools.DateTool;
import com.hysm.tools.StringUtil;
import com.hysm.tools.phone.AuthCodeGenerator;
import com.hysm.tools.phone.HYSM_TEMPCacher;
import com.hysm.tools.phone.Mobile_Bind;
import com.hysm.tools.phone.SendMsg;
import com.hysm.tools.wx.GetOpenid;
import com.hysm.wecat.GetJsticket;
import com.hysm.wecat.GetToken;
import com.hysm.wecat.HttpUtil;
import com.hysm.wecat.tools.pushmessage;
import com.hysm.wecat.wxdo.DOM4JTool;
import com.hysm.wecat.wxdo.EmojiFilter;
import com.hysm.wecat.wxdo.SHA1;

@Controller
@RequestMapping("/wx")
public class WxController extends BaseController {

	@Autowired
	private Login_db userdb;
	@RequestMapping(value = { "/goIndex" })
	public String goIndex(HttpServletRequest request,HttpServletResponse resp){
		
//		String path = request.getContextPath();
//	 	 String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
	 	return "redirect:/jsp/pt/a_edit/show/1495714795.html?v="+System.currentTimeMillis()/1000;
	 	 
	 	
		
	}
	
	/**
	 * 是否关注
	* XXXXX
	* @param map
	* @return
	 */
	@RequestMapping("ajaxIsSub")
	@ResponseBody
	public String ajaxIsSub(HttpServletRequest request){
		String openid=request.getParameter("openid");
	       	String stoken=GetToken.getAccessToken();
	       	
	        String  url="https://api.weixin.qq.com/cgi-bin/user/info?access_token="+stoken+"&openid="+openid+"&lang=zh_CN";
	          
	        String str=HttpUtil.getUrl(url);
	        
	        
	        try {
				str = new String(str.getBytes("ISO-8859-1"),"utf-8");
				 
				 
				 JSONObject js=JSONObject.fromObject (str);
				 if(js.get("subscribe")!=null){
					 int subscribe=StringUtil.toNum(js.get("subscribe"));
					 
					 if(subscribe==1){
						return "200"; 
					 }
					 
				 }
				
				 
				 
				 
	        }catch(Exception e){
	        	
	        }
				
	    	return "400"; 
	        
	}
	
	@RequestMapping(value = { "/check_openid" })
	public String check_openid(String code, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String openid = GetOpenid.getOpenid(code);
			 
			if (openid != null && !openid.equals("")) {

				Document user = userdb.query_userByopenid(openid);

				if (user != null) {
					// 将登录时间录入 
					CookieDo.addCookie(response, MessageType.OPENID, openid, (int) 360000000);

					return "200";
				} else {
					user = new Document();
					user.append("open_id", openid);
					user.append("state", 0);
					user.append("role", 0);
					user.append("appid", MessageType.appid);
					user.append("name", "");// 随机分配用户名
					user.append("scholarship", 0);
					user.append("score", 0);
					user.append("count_scholarship", 0);
					user.append("is_sign", 0);
					user.append("forbit", 0);
					user.append("ctime", DateTool.fromDate12H());
					user.append("classmatecircle", 0);
					user.append("u_no", System.currentTimeMillis()+"");
					int ss = userdb.insertUser(user);
					if (ss > 0) {

						CookieDo.addCookie(response, MessageType.OPENID, openid, (int) 360000000);

						return "300";
					}

				}

			} else {

				return "404";
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "404";
	}
	
	@RequestMapping(value = { "/sq_info" })
	public String sq_info( HttpServletRequest request,
			HttpServletResponse response){
		
		String code = request.getParameter("code");
		 
		String back_code ="200";
		
		try {
			String openid = GetOpenid.getOpenid(code);
			CookieDo.addCookie(response, MessageType.OPENID, openid, (int) 360000000);
			
			
			String access_token = GetToken.getAccessToken(); 
			
			String  url2="https://api.weixin.qq.com/cgi-bin/user/info?access_token="+access_token+"&openid="+openid+"&lang=zh_CN";
	          
	        String str=HttpUtil.getUrl(url2);
	        
	        try {
				str = new String(str.getBytes("ISO-8859-1"),"utf-8");
			}catch (UnsupportedEncodingException e){ 
				e.printStackTrace();
			}
			 
	        System.out.println(str);
			 
			JSONObject js=JSONObject.fromObject (str);
			 
			 Document doc=new Document();
			 
			String nickname=js.getString("nickname"); 
			String open_id =js.getString("openid");
			 
			doc.append("sex",js.getInt("sex"));
			doc.append("open_id",openid);
			doc.append("province",js.getString("province"));
			doc.append("city",js.getString("city")); 
			doc.append("country",js.getString("country"));
				 
			doc.append("head",js.getString("headimgurl"));
			
			 nickname=EmojiFilter.filterEmoji(nickname);
	         doc.append("name",nickname); 
	         doc.append("state",0); 
	        
	         userdb.check_user(open_id, doc);
	         
	         back_code="200";
			 
			  
		} catch (Exception e){
			 
			e.printStackTrace();
			
			back_code ="404";
		}
		
		return back_code;
	}
	
	@RequestMapping(value = { "/ajaxopenid" })
	public void ajaxopenid(String code, HttpServletRequest request,
			HttpServletResponse response) {
		
		String back_code ="";
		
		try {
			String openid = GetOpenid.getOpenid(code);
			
			//System.out.println("-------openid-------"+openid);

//			String ctime = DateUtil.fromDate24H();
			
			
			
			if (openid != null && !openid.equals("")) {

				Document user = userdb.query_userByopenid(openid);

				if (user != null) {
					// 将登录时间录入

					CookieDo.addCookie(response, MessageType.OPENID, openid,
							(int) 360000000);

					back_code = "200";
				} else {
					
					
					
					user = new Document();
					user.append("open_id", openid);
					user.append("state", 0);
					user.append("role", 0);
					user.append("appid", MessageType.appid);
					user.append("name", "你好");// 随机分配用户名
					user.append("scholarship", 0);
					user.append("score", 0);
					user.append("count_scholarship", 0);
					user.append("is_sign", 0);
					user.append("forbit", 0);
					user.append("classmatecircle", 0); 
					user.append("create_date", new Date()); 			
					user.append("u_no", System.currentTimeMillis()+"");
					int ss = userdb.insertUser(user);
					if (ss > 0) {

						CookieDo.addCookie(response,MessageType.OPENID, openid,
								(int) 360000000);

						back_code = "200";
					}

				}

			} else {

				back_code = "404";
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		sendMassage(back_code, response);
		
		
	}

	 
	@RequestMapping(value = { "/ajax_info" }, method = { RequestMethod.POST })
	public void ajax_info(HttpServletRequest request,
			HttpServletResponse response, Map<String,Object> model){
		
		String code = request.getParameter("code");
		 
		String back_code ="200";
		
		try {
			
			
			String  url1="https://api.weixin.qq.com/sns/oauth2/access_token?appid="+MessageType.appid+"&secret="+MessageType.appsecret+"&code="+code+"&grant_type=authorization_code";
			
			 String str1=HttpUtil.getUrl(url1);
			 System.out.println("-----------"+str1);
			 
			JSONObject js1=JSONObject.fromObject (str1);
			 
			String openid = js1.getString("openid");
			
			model.put("openid", openid);
			
			CookieDo.addCookie(response, MessageType.OPENID, openid, (int) 360000000);
			 
			String access_token = js1.getString("access_token");
			String refresh_token = js1.getString("refresh_token");
			
			Document user = userdb.query_userByopenid(openid);
			
			if(user == null  || (user!= null && user.get("head")==null)){
				 
				String  url2="https://api.weixin.qq.com/sns/userinfo?access_token="+access_token+"&openid="+openid+"&lang=zh_CN";
		          
		        String str=HttpUtil.getUrl(url2);
		        
		        try {
					str = new String(str.getBytes("ISO-8859-1"),"utf-8");
				}catch (UnsupportedEncodingException e){ 
					e.printStackTrace();
				}
				 
		        System.out.println("-----------"+str);
				 
				JSONObject js=JSONObject.fromObject (str);
				 
				 Document doc=new Document();
				 
				String nickname=js.getString("nickname"); 
				String open_id =js.getString("openid");
				 
				doc.append("sex",js.getInt("sex"));
				doc.append("open_id",openid);
				doc.append("province",js.getString("province"));
				doc.append("city",js.getString("city")); 
				doc.append("country",js.getString("country"));
					 
				doc.append("head",js.getString("headimgurl"));
				
				 nickname=EmojiFilter.filterEmoji(nickname);
		         doc.append("name",nickname); 
		         doc.append("state",0); 
		        
		         userdb.check_user(open_id, doc);
				
			}
			 
	         
	         back_code="200";
	         
	         model.put("back_code", back_code);
	 		
	 		sendjson(model, response); 
		  
			  
		} catch (Exception e){
			 
			e.printStackTrace();
			
			back_code ="404";
			
			model.put("back_code", back_code);
			
			sendjson(model, response);
		}
		 
	}
	
	
	/**
	 * 发送短信验证码
	 * @param req
	 * @param resp
	 */
	
	@RequestMapping(value = { "sendMess" }, method = { RequestMethod.POST })
	@ResponseBody
	public void sendMess(HttpServletRequest req,HttpServletResponse resp) {
		
			 
		
		try {
			req.setCharacterEncoding("utf-8");
			resp.setCharacterEncoding("utf-8");
			PrintWriter out=resp.getWriter();
	
	
			String phone=req.getParameter("phone");
	    	String openid=req.getParameter("openid");
	    	Mobile_Bind mb=(Mobile_Bind)HYSM_TEMPCacher.getCache(openid+"_MAUTH");
	    	if(mb!=null){
	    		
	    		 
				long cTime=mb.getCtime();
				long nowTime=System.currentTimeMillis();
				long dtime=nowTime-cTime;
				if(dtime>60000){
	    		mb.setCheckSubmit(0);
				}
			}

	    	if(mb==null||mb.getCheckSubmit()==0){
	    	 
	    	
	    		long ctime=System.currentTimeMillis();
	    		Mobile_Bind mb2=new Mobile_Bind();
	    		String code =AuthCodeGenerator.randomAuthCode();
	    		mb2.setCode(code);
	    		mb2.setOpenid(openid);
	    		mb2.setPhone(phone);
	    		mb2.setCheckSubmit(1);
				mb2.setCtime(ctime);
	    		HYSM_TEMPCacher.cache(openid+"_MAUTH",mb2);
	    		String codes="您的验证码是:" + code+" ";
	    		System.out.println("sengMess："+codes);
	    		
	    		String ip=req.getRemoteAddr();
	    		SendMsg sm = new SendMsg(phone, codes, 8, ip);
	    		Thread thread=new Thread(sm);
	    		thread.start();
	    		
	    	/*	if(Integer.valueOf(result)==1){
	    			checkmobile.insertM(phone);
	    		}
	    		*/
	    		
	    		
	    		out.write("发送成功");
    	
	    	}else {
				
				HYSM_TEMPCacher.cache(openid+"_MAUTH",mb);
				out.write("一分钟内请不要重复提交!");
			
			}
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	
	}
	
	
	/**
	 * 手机验证
	 */
	/*@RequestMapping(value = { "authUser" })
	public String  authUser(HttpServletRequest req,HttpServletResponse resp, Map<String, Object> model){
		String openid=req.getParameter("openid");
		String mobile=req.getParameter("mobile");
		String message=req.getParameter("message");
		if(openid!=null&&!openid.equals("")){
			
			Mobile_Bind mb=(Mobile_Bind)HYSM_TEMPCacher.getCache(openid+"_MAUTH");
			if(mb!=null){
				if((mb.getCode()).equals(message)){
					//用户
					Document user=userdb.query_userByMobile(mobile);
				
				
					if(user!=null&&user.getIschecked()!=1){
						//验证通过
						user.setIschecked(1);
						user.setOpenid(openid);
						int s1=dmService.updateUser(user);
						
					if(user.getRole()==1){
							customer cus=new customer();
							cus.setOpenid(openid);
							cus.setApartment(user.getApartment());
							cus.setIschecked(user.getIschecked());
							cus.setPhone(user.getPhone());
							cus.setName(user.getName());
							cus.setUid(user.getId());
							
							dmService.addCustomer(cus);
						
						
					}else if(user.getRole()==2){
						manager man=new manager();
						man.setIschecked(user.getIschecked());
						man.setName(user.getName());
						man.setOpenid(user.getOpenid());
						man.setPhone(user.getPhone());
						man.setUid(user.getId());
						dmService.addManager(man);
						
						getSession().setAttribute(BaseController.MANAGER,
								user.getName());
					}else if(user.getRole()==3 ){
						
						repair re=new repair();
						re.setIschecked(user.getIschecked());
						re.setName(user.getName());
						re.setOpenid(openid);
						re.setPhone(user.getPhone());
						re.setUid(user.getId());
						re.setApment(user.getApartment());
						re.setType(user.getRole());
						dmService.addRepair(re);
					}else if(user.getRole()==4 ){
						
						repair re=new repair();
						re.setIschecked(user.getIschecked());
						re.setName(user.getName());
						re.setOpenid(openid);
						re.setPhone(user.getPhone());
						re.setUid(user.getId());
						re.setApment(user.getApartment());
						re.setType(user.getRole());
						dmService.addRepair(re);
					}
						
					CookieDo.addCookie(resp, BaseController.C_OPENID, openid, 3600*24*360);
					CookieDo.addCookie(resp, BaseController.USERTYPE, user.getRole()+"", 3600*24*360);
				// 1员工2派单人3维修责任人
						
					model.put("result", 200);
					if(user.getRole()==1){
						return "redirect:http://www.dofei.net/jsp/page/report_list.jsp";
					}else{
						return "/domain/userlist/login_res";
					}
					
					
					}else{
						
						model.put("result", 500);
					}
					
				}else{
					//输入验证不一致
					
					HYSM_TEMPCacher.cache(openid+"_MAUTH",mb);
					model.put("result", 404);
				}
				
			}else{
				
				model.put("result", 404);
				
			}
			
		}else{
			model.put("result", 404);
			
		}
		
		
		return "/domain/userlist/login_res";
		
	}*/
	
	
	/**
	 * 验证 
	 * @param request
	 * @param response
	 * @param model
	 */
	
	@RequestMapping(value = { "/authdev" })
	public void  authdev(HttpServletRequest request,
			HttpServletResponse response, Map<String,Object> model) {
		
		 String signature = request.getParameter("signature");//微信加密签名
         String timestamp = request.getParameter("timestamp");//时间戳
         String nonce = request.getParameter("nonce");//随机数	        		
		 String token = "xJ4EPvdb7Pk8bm85y8JN";        		
		 String echostr = request.getParameter("echostr");//随机字符串
		
		 System.out.println("*****signature:"+signature);
		 System.out.println("*****timestamp:" +timestamp);
		 System.out.println("*****nonce:"+nonce);
		 System.out.println("*****echostr:"+echostr);
		
		 if(signature==null || timestamp == null || nonce == null )
		 {
		    return;
		 }
		
		System.out.print("1111111111111111111111111111111111111111111");
		 
		//表示是验证开发者
		if(echostr!=null && SHA1.authWXDEV(signature, timestamp, nonce, token))
		{
			sendMessage(echostr, response);
		  return;	 
		}
		
		
		byte[] buff = null;
		int length = request.getContentLength();
		String encoding = request.getContentType();
		if(length > 0)
		{
		   buff = new byte[length];
		}
		else
		{
		   buff = new byte[1024*1024*2];
		}
		
		int temp = 0,readedLen = 0;
		
		String reqStr ="";
		
		InputStream in;
		try {
			in = request.getInputStream();
			 
			while((temp =in.read(buff,readedLen,buff.length - readedLen)) >0)
			{
			     readedLen += temp;
			}
			
			reqStr = new String(buff,0,readedLen,"UTF-8");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		BasicBSONObject reqBSON = DOM4JTool.decodeXML(reqStr);
		String from = reqBSON.getString("FromUserName");
		 
		
		String  MsgType = reqBSON.getString("MsgType");//获取用户的信息类型
		String to=reqBSON.getString("ToUserName");//获取公众号id 
		 
		
		pushmessage pm= new pushmessage();

		System.out.println("nihao"+MsgType);
        System.out.println("********"+reqBSON.toString());
        
        System.out.print("2222");
        
        //处理事件消息
		if(MsgType.equalsIgnoreCase("event"))
		{	
			String Event  = reqBSON.getString("Event");		
			if(Event == null){return;}
			if(Event.equalsIgnoreCase("subscribe")) //关注含扫码关注
			{
			  Document doc=null;
			 
			  String content="";
			
			//判断是否是含场景值得二维码关注
			if(reqBSON.getString("EventKey")!=null&&!(reqBSON.getString("EventKey")).equals(""))
			{
	             //二维码值(引荐人的标志Key值)
			    String EventKey =reqBSON.getString("EventKey").substring(8); 
			    doc=new Document(); 
			
			}else{
			  //正常关注事件
			  doc=new Document(); 
			 
			}
			 System.out.print("33333");    
		          		
			content=content+"欢迎关注到答课堂！";
			
			
			 sendMessage(pm.pushmess(from, to, content), response);
			 
	           doc.append("open_id", from);     
	           doc.append("MsgType",MsgType);
	           
	           CookieDo.addCookie(response, MessageType.OPENID, from, (int) 360000000);
		         
		        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		        
		        String time = dateFormat.format(new Date());
		        
		        doc.append("appid",to);//关注的公众号
		        doc.append("CreateTime",time);
		          //获取用户昵称
		        GetToken  gtoken=new GetToken();     
		       	String stoken=gtoken.gettoken();
		       	
		        String  url="https://api.weixin.qq.com/cgi-bin/user/info?access_token="+stoken+"&openid="+from+"&lang=zh_CN";
		          
		        String str=HttpUtil.getUrl(url);
		        
		        String nickname ="";
		         
		        String open_id ="";
		        
 		        try {
					str = new String(str.getBytes("ISO-8859-1"),"utf-8");
					 
					 
					 JSONObject js=JSONObject.fromObject (str);
					 
					 nickname=js.getString("nickname"); 
					 open_id =js.getString("openid");
					 
					doc.append("sex",js.getInt("sex"));
					doc.append("province",js.getString("province"));
					doc.append("city",js.getString("city")); 
					doc.append("country",js.getString("country"));
						 
					doc.append("head",js.getString("headimgurl"));
					
					 nickname=EmojiFilter.filterEmoji(nickname);
			         doc.append("name",nickname); 
			         doc.append("state",0); 
			        
			         userdb.check_user(open_id, doc);
			         
			        System.out.println("------user-info---222-----"+doc);
					
				} catch (Exception e) {
					 
					e.printStackTrace();
				}
 		       
 		      
	             
			} 
			if(Event.equalsIgnoreCase("unsubscribe")) //取消关注
			{
			   
//				  DBManager.executeDBUpdate("update ec_concern_user set untime=now()  where openid='"+from+"'");
			}
			if(Event.equalsIgnoreCase("SCAN")) //扫描二维码
			{
			   String scanKey=reqBSON.getString("EventKey");
			   BasicBSONObject bson=null;
			  
			   String massage="欢迎关注到答课堂！";
			 
	           sendMessage(pm.pushmess(from, to,massage), response);   
				
	                 
			
			}
			if(Event.equalsIgnoreCase("location")) //扫描上报地理位置
			{
			 
			}
			if(Event.equalsIgnoreCase("CLICK"))//点击自定义菜单事件
			{
			  // 事件KEY值，与创建自定义菜单时指定的KEY值对应 
				String eventkey=reqBSON.getString("EventKey");
			
		    }
		    if(Event.equalsIgnoreCase("VIEW"))
		    {//点击超链接的点击事件
		    
		    }
		    
		}    
		//普通文本消息
		else if(MsgType.equalsIgnoreCase("text"))
		{	
		
		   String content=null;
		  // massage mass=null;
		   String text=reqBSON.getString("Content");
		   /*
		  if(text.equalsIgnoreCase("1")||text.equals("苏和盛"))
		  {
		     mass=new massage();
		     mass.setTitle("查看最全面与最详尽的苏和盛介绍资料");
             mass.setDescription("苏和盛珍珠博物馆，即华东淡水珍珠研究院，地处古城苏州，与著名的寒山寺隔水相望，总占地面积6000余平，是国内唯一以“淡水珍珠”为主题展示的专业化博物馆。"+
             "苏和盛博物馆前身为“苏和盛堂”珍珠老铺，始创于1886年，因其人工培育的太湖珍珠“满月浑圆、七彩虹光、无裂无瑕、刀刮无痕、富养无核”，而被慈禧太后册封为“五品御珠”，专供朝廷使用，苏和盛堂因此名噪一时。");
             mass.setPicurl("http://www.dofei.net/suhesheng/images/bowuguan1.png");
             mass.setUrl("http://www.dofei.net/suhesheng/wenhua/navijieshao.html");
             List<massage> list=new ArrayList<massage>();
             list.add(mass);
             out.print(pm.pushImageandTest(from, to,list));
		  
		  }
		  else if(text.equalsIgnoreCase("2")||text.equals("珍珠"))
		  {
		     mass=new massage();
		     mass.setTitle("查看最优质的珍珠知识与实用小常识");
             mass.setDescription("苏和盛以其厚重的历史文化底蕴，对珍珠文化的独特见解以及对现代时尚美的极致追求，立足古典之美与现代之美相融合，塑造具有人文情怀的高端艺术格调。");
             mass.setPicurl("http://www.dofei.net/suhesheng/images/bowuguan2.png");
             mass.setUrl("http://www.dofei.net/suhesheng/wenhua/navizhenzhu.html");
             List<massage> list=new ArrayList<massage>();
             list.add(mass);
             out.print(pm.pushImageandTest(from, to,list));
		  
		  
		  }else if(text.equalsIgnoreCase("3")||text.indexOf("导游")!=-1||text.indexOf("认证")!=-1)
		  {
		  
		     mass=new massage();
		     mass.setTitle("快速完成导游身份认证，了解更多导游资讯");
             mass.setDescription("苏和盛立志为每位导游提供最贴心的服务，以秉持互助团结、共同发展为目标，塑造工业旅游的优秀典范。");
             mass.setPicurl("http://www.dofei.net/suhesheng/images/bowuguan3.png");
             mass.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxda9d561e87ff218c&redirect_uri=http://www.dofei.net/suhesheng/guiderApply.jsp&response_type=code&scope=snsapi_base&state=1#wechat_redirect");
             List<massage> list=new ArrayList<massage>();
             list.add(mass);
             out.print(pm.pushImageandTest(from, to,list));
		  
		  }else if(text.equalsIgnoreCase("4")||text.indexOf("活动")!=-1)
		  {
		     mass=new massage();
		     mass.setTitle("了解最新活动，参与活动，赢大奖");
             mass.setDescription("每一次活动，都是一次温暖的回馈。我们不企盼你能时刻念我在心，但至少，我们希望，苏和盛能让你偶然回想时，心头有一丝温暖。欢迎所有导游朋友们加入苏和盛导游之家，参与活动，赢取大奖！");
             mass.setPicurl("http://www.dofei.net/suhesheng/images/bowuguan4.png");
             mass.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxda9d561e87ff218c&redirect_uri=http://www.dofei.net/suhesheng/recommendGift/recomGift.jsp&response_type=code&scope=snsapi_base&state=1#wechat_redirect");
             List<massage> list=new ArrayList<massage>();
             list.add(mass);
             out.print(pm.pushImageandTest(from, to,list));
		  
		  }else if(text.equalsIgnoreCase("5")||text.indexOf("地址")!=-1)
		  {
		   content="苏州市高新区滨河路枫桥大街1号（导航搜索“滨河皇冠大酒店”即可）；具体路线：寒山寺南金门路往西过京杭大运河，红绿灯向北，到第一个红绿灯路口右转即可。";
		  
		  }
		  else if(text.equalsIgnoreCase("6")||text.indexOf("电话")!=-1)
		  {
		   content="来团预约电话：0512-6632 6466\n落单查询电话：0512-6723 3789\n导游服务电话：181 3617 8559";
		  
		  }else if(text.equalsIgnoreCase("7")||text.indexOf("时间")!=-1)
		  {
		   content="早晨7:30~下午17:00（营业时间随季节变动而变化，具体请联系0512-6632 6466，或者在导游来团预约版块提前进行预约）";
		  
		  }else if(text.equalsIgnoreCase("客服"))
		  {
		      content="请直接回复您的问题，尽快为你解答！";
		 
		  }
		  else if(reqBSON.getString("Content").equalsIgnoreCase("cxyy"))
		  {
		
		        content="查询今天预约请点击:\n"
		           +"<a  href=\"http://www.dofei.net/suhesheng/querytable\">查询预约</a>";
		     
		     
	       }
	       else if(reqBSON.getString("Content").equalsIgnoreCase("cxrz"))
	       {
            
              
                      mass=new massage();
                      mass.setTitle("前往查询");
                      mass.setDescription("查询");
                      mass.setPicurl("http://www.dofei.net/suhesheng/images/banner7.png");
                      mass.setUrl("http://www.dofei.net/suhesheng/verifi.jsp?admin="+from);
                      List<massage> list=new ArrayList<massage>();
                      list.add(mass);
                   out.print(pm.pushImageandTest(from, to,list));
         
           
              }else{
		         
		          content="欢迎你，不时不食的朋友！";
		  }*/
		   
		   	content="欢迎你，到答课堂的朋友！";
		   
		    sendMessage(pm.pushmess(from, to, content), response);
	            
		}
		//普通图片消息
		else if(MsgType.equalsIgnoreCase("image"))
		{
			String content="欢迎你，到答课堂的朋友！";
			   
		    sendMessage(pm.pushmess(from, to, content), response);
		}
		//普通语音消息
		else if(MsgType.equalsIgnoreCase("voice"))
		{	
			String content="欢迎你，佳安美母婴的朋友！";
			   
		    sendMessage(pm.pushmess(from, to, content), response);
		}
		//普通视频消息
		else if(MsgType.equalsIgnoreCase("video"))
		{		
			String content="欢迎你，到答课堂的朋友！";
			   
		    sendMessage(pm.pushmess(from, to, content), response);
		}
		//地理位置消息
		else if(MsgType.equalsIgnoreCase("location"))
		{	
			String content="欢迎你，到答课堂的朋友！";
			   
		    sendMessage(pm.pushmess(from, to, content), response);
		}
		 
		
	}
		/**
	 * 调动微信sdk
	 * 
	* XXXXX
	* @param map
	* @return
	 */
	@RequestMapping("wxsign")
	@ResponseBody
	public JSONObject wxsign(String url){
		
		try {
		return weChatTool(url);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return null;
	
		
	}
	
	public JSONObject weChatTool(String url) throws NoSuchAlgorithmException
	{
		JSONObject data=new JSONObject();
		Date date = new java.util.Date();
		long time = date.getTime();

		// 时间戳只有10位 要做处理
		String dateline = time + "";
		dateline = dateline.substring(0, 10);
       String  timeStamp = dateline; 
     /*   System.out.println(timeStamp);*/
		/*String url = request.getScheme()+"://"+ request.getServerName()+request.getRequestURI();
        if(request.getQueryString()!= null)
        {
        	url = url+"?"+request.getQueryString();
        }*/
/*		System.out.println("url:"+url);*/
		//获取前台的时间戳
		
		String jsticket=GetJsticket.getJSApiTicket();
		//String 

		//ticket="sM4AOVdWfPE4DxkXGEs8VOzZLwOOuvTVRsa4UeyjiEQ3A8-f_VLl3GaJEj8z738rLEP_EzWMYBClwfIhB7I1Og";
		
		String noncestr=UUID.randomUUID().toString();
		
		//注意这里参数名必须全部小写，且必须有序
		String[] paramArr = new String[] { "jsapi_ticket=" + jsticket,"timestamp=" + timeStamp, "noncestr=" + noncestr, "url=" + url };
		Arrays.sort(paramArr);
		// 将排序后的结果拼接成一个字符串
		String content = paramArr[0].concat("&"+paramArr[1]).concat("&"+paramArr[2]).concat("&"+paramArr[3]);
        MessageDigest crypt = MessageDigest.getInstance("SHA-1");
        byte[] digest = crypt.digest(content.toString().getBytes());
        Formatter formatter = new Formatter();
        for (byte b : digest)
        {
            formatter.format("%02x", b);
        }
      String  signature = formatter.toString();
        formatter.close();
        
        data.put("appId",MessageType.appid);
		data.put("timestamp", timeStamp);
		data.put("nonceStr", noncestr);
		data.put("signature", signature);
		return data;
	}
	

}
