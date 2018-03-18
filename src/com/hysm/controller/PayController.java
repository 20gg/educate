package com.hysm.controller;

import java.io.IOException;
import java.io.StringReader;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

 
















import org.bson.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping; 
import org.xml.sax.InputSource;

import com.hysm.bean.MessageType; 
import com.hysm.db.Area_db;
import com.hysm.db.Msg_db;
import com.hysm.db.Pay_db;
import com.hysm.db.Top_db;
import com.hysm.model.WeChatVO;
import com.hysm.tools.DateTool;
import com.hysm.tools.DateUtil;
import com.hysm.wxpay.PayTool;
import com.hysm.wxpay.demo.Demo;
import com.hysm.wxpay.demo.WxPayDto;
import com.hysm.wxpay.demo.WxPayResult;
import com.hysm.wxpay.utils.ClientCustomSSL;
import com.hysm.wxpay.utils.GetWxOrderno;
import com.hysm.wxpay.utils.Red_packet;

@Controller
@RequestMapping("/wxpay")
public class PayController extends BaseController {
	
	@Autowired
	private Pay_db pay_db;
	
	@Autowired
	private Area_db area_db;
	
	@Autowired
	private Top_db top_db;
	
	@Autowired
	private Msg_db msg_db;
	
	@RequestMapping(value = { "/into_join" }) 
	public String into_join(HttpServletRequest request,
			HttpServletResponse response, Map<String, Object> model){
		
		String openid = request.getParameter("openid");
		
		String top_openid = request.getParameter("top_openid"); //通过分享传的上级openid
		
		Document user = area_db.query_user_byopenid(openid); 
		
		if(top_openid != null){
			Map<String,Object> top_map = top_db.query_my_top(openid);
			
			int back_code =0;
			
			Document top_user = area_db.query_user_byopenid(top_openid); 
			
			 
			
			if(Integer.valueOf(top_map.get("safe").toString()) <= 0
					&& !openid.equals(top_openid)  && !openid.equals(top_user.getString("top_openid"))){
				//上级没有 或 已经失效 
				//设置用户的上级 
				
				//不是本人      我的上级的上级   == 不是我
				
				Document config = top_db.query_config_s();
				 
				
				user.put("top_openid", top_openid);
				
				String safe_date = DateTool.addDate(DateTool.fromDateY(), config.getInteger("safe_date"));
				
				Map<String,Object> map = new HashMap<String, Object>();
				map.put("open_id", top_openid);
				map.put("head", top_user.getString("head"));
				map.put("name", top_user.getString("name"));
				map.put("safe_date", safe_date);
				
				user.put("top_member", map);
				user.put("p1_date", safe_date);
				
				String _id = user.get("_id").toString();
				
				top_db.update_user(_id, user);
				
				back_code =100;
				
			}else{
				back_code =200;
			}
			
			
			model.put("back_code", back_code);
		}
		
		model.put("user", user);
		
		return "/wx/join";
	}
	
	
	
	@RequestMapping(value = { "/into_join2" }) 
	public String into_join2(HttpServletRequest request,
			HttpServletResponse response, Map<String, Object> model){
		
		String openid = request.getParameter("openid");
		
		String top_openid = request.getParameter("top_openid"); //通过分享传的上级openid
		
		Document user = area_db.query_user_byopenid(openid); 
		
		if(top_openid != null){
			Map<String,Object> top_map = top_db.query_my_top(openid);
			
			int back_code =0;
			
			Document top_user = area_db.query_user_byopenid(top_openid); 
			
			 
			
			if(Integer.valueOf(top_map.get("safe").toString()) <= 0
					&& !openid.equals(top_openid)  && !openid.equals(top_user.getString("top_openid"))){
				//上级没有 或 已经失效 
				//设置用户的上级 
				
				//不是本人      我的上级的上级   == 不是我
				
				Document config = top_db.query_config_s();
				 
				
				user.put("top_openid", top_openid);
				
				String safe_date = DateTool.addDate(DateTool.fromDateY(), config.getInteger("safe_date"));
				
				Map<String,Object> map = new HashMap<String, Object>();
				map.put("open_id", top_openid);
				map.put("head", top_user.getString("head"));
				map.put("name", top_user.getString("name"));
				map.put("safe_date", safe_date);
				
				user.put("top_member", map);
				user.put("p1_date", safe_date);
				
				String _id = user.get("_id").toString();
				
				top_db.update_user(_id, user);
				
				back_code =100;
				
			}else{
				back_code =200;
			}
			
			
			model.put("back_code", back_code);
		}
		
		model.put("user", user);
		
		return "/wx/one_price";
	}
	
		
	@RequestMapping(value = { "/into_xf" }) 
	public String into_xf(HttpServletRequest request,
			HttpServletResponse response, Map<String, Object> model){
		
		String openid = request.getParameter("openid");
		
		
		
		Document user = area_db.query_user_byopenid(openid); 
		
			Document doc =new Document();
			doc.put("open_id", openid);
			doc.put("is_order", 1);
				
			Document vip_safe_date=area_db.find_vip_order("vip_order", doc);
		
		
			model.put("vip_order", vip_safe_date);
		model.put("user", user);
		
		return "/wx/Renew";
	}
	
	@RequestMapping(value = { "/into_buy" }) 
	public String into_buy(HttpServletRequest request,
			HttpServletResponse response, Map<String, Object> model){
		
		String openid = request.getParameter("openid"); 
		String c_no = request.getParameter("c_no"); 
		int kind = Integer.valueOf(request.getParameter("kind"));//1课程2专题
		
		Document user = area_db.query_user_byopenid(openid);
		
		Document doc = null; 
		if(kind == 1){
			doc = pay_db.quert_course(c_no);
		}else{
			doc= pay_db.quert_special(c_no);
		}
		
		int count = pay_db.query_is_buy(openid, c_no);
		
		model.put("user", user);
		model.put("doc", doc);
		model.put("count", count);
		model.put("c_no", c_no);
		model.put("kind", kind);
		
		return "/wx/Guyang/buy";
	}
	
	@RequestMapping(value = { "/into_qrcode" }) 
	public String into_qrcode(HttpServletRequest request,
			HttpServletResponse response, Map<String, Object> model){
		
		return "/wx/qrcode_buy"; 
	}
	
	
	
	@RequestMapping(value = { "/check_qrcode" }) 
	public void check_qrcode(HttpServletRequest request,
			HttpServletResponse response, Map<String, Object> model){
		
		String c_no = "1501063396584";//专题号
		
		String openid = request.getParameter("openid"); 
		Document user = area_db.query_user_byopenid(openid); 
		
		if(user == null){
			
			model.put("user", user);  
			
			sendjson(model, response);
			
			return ;
			
		}
		
		int buy_num = pay_db.query_is_buy(openid, c_no);
		
		//Document order = pay_db.query_qrcode_order(c_no, openid);
		
		
		
		if(buy_num < 1){
			//创建订单 
			Document doc= pay_db.quert_special(c_no);
			
			int price = doc.getInteger("price");  //订单总金额
			
			String order_id=String.valueOf(System.currentTimeMillis());
			
			Document  course_order=new Document();
			
			course_order.put("order_id", order_id);
			course_order.put("open_id", openid);
			course_order.put("c_no", c_no);
			course_order.put("name", user.getString("name"));
			
			course_order.put("img", doc.getString("img"));
			course_order.put("date", new Date());
			
			course_order.put("teacher", doc.getString("teacher"));
			course_order.put("text", doc.getString("text"));
			course_order.put("safe_date", doc.getString("safe_date")); 
			String safe_year = DateTool.addDate(DateTool.fromDateY(), doc.getInteger("safe_year")*365); 
			course_order.put("safe_year",safe_year); 
			course_order.put("price", doc.getInteger("price"));
			
			course_order.put("rebate", 0);
			course_order.put("scholship_money", 0);
			course_order.put("pay_money", price);
			
			course_order.put("c_date", DateTool.fromDateY());
			course_order.put("date_1", DateTool.fromDateY());
			 
			course_order.put("score_money", 0);
			course_order.put("watch", doc.getInteger("watch")); 
			
			course_order.put("type", 2);
			course_order.put("c_name", doc.getString("special_name"));
			
			course_order.put("is_order", 0);
		  
			course_order.put("is_over", 0); 
			area_db.add_db_info("course_order", course_order);
			 
			
			//添加订购数
			
			if(doc.get("order_count")!= null){
				
				int order_count = doc.getInteger("order_count")+1;
				
				String _id = doc.get("_id").toString();
				
				doc.put("order_count", order_count);
				 
				pay_db.update_special(_id, doc);
				 
			}
			
			model.put("order", course_order);
			
		}
		
		model.put("user", user); 
		model.put("buy_num", buy_num);
		
		
		sendjson(model, response);
		
	}
	
	/**
	 * 异步微信支付
	 * @return
	 */ 
	@RequestMapping(value = { "/ajaxnewpay" })
	//@ResponseBody
	public void ajaxnewpay(HttpServletRequest req,
			HttpServletResponse response, Map<String, Object> model){
		
		 String order_id = req.getParameter("order_id"); 
		 
		 Document order = pay_db.query_order(order_id); 
		
		String spbillCreateIp=this.getRemoteHost(req);
		
		String body="订单号是"+order_id;
		
		int paymoney=order.getInteger("pay_money");
		//测试
		//paymoney=1;
		
		double total_fee=(Double.valueOf(paymoney)/100);
		
		String openid = order.getString("open_id");
		
		String str ="";
		
		//未支付
		if(order.getInteger("is_order")  == 0){
			
			//订单支付记录 
			
			Document pay_log = new Document();
			pay_log.put("order_id", order_id);
			pay_log.put("kind", 1);
			pay_log.put("c_time", DateTool.fromDate24H());
			pay_log.put("c_date", new Date()); 
			pay_log.put("openid", openid);
			pay_log.put("name", order.getString("name"));
			pay_log.put("pay_money", paymoney); 
			pay_log.put("state", 0);
			pay_log.put("way", 1);//微信支付  2 奖学金支付
			
			area_db.add_db_info("pay_log", pay_log);
			
			str = this.getPackage(order_id, body, openid, spbillCreateIp, total_fee+"");
		}else {
			 
			str ="has_pay";
		}
		 
		   
		sendMassage(str, response);
	}
	
	 
	
	
	
	
	
	@RequestMapping(value = { "/get_tx" }) 
	public void get_tx(HttpServletRequest request,
			HttpServletResponse response, Map<String, Object> model){
		
		String openid = request.getParameter("openid");
		
		Document user = area_db.query_user_byopenid(openid); 
		
		model.put("user", user);
		
		sendjson(model, response);
	}
	
	@RequestMapping(value = { "/send_red_packet" }) 
	public void send_red_packet(HttpServletRequest request,
			HttpServletResponse response, Map<String, Object> model){
		
		String openid = request.getParameter("openid"); 
		String txmoney = request.getParameter("txmoney"); 
		 
		
		double m = Double.valueOf(txmoney)*100; 
		int red_money = (int)m;
		 
		Document user = area_db.query_user_byopenid(openid);
		
		int back_code = 200;
		
		if(red_money > 20000){
			back_code = 100;
		} 
		
		if(red_money > user.getInteger("scholarship")){
			back_code = 10;
		}
		
		if(back_code == 200){
			
			Document doc = new Document();
			
			String red_id = String.valueOf(System.currentTimeMillis());
			
			doc.put("openid", openid);
			doc.put("money", red_money);
			doc.put("state", 0);
			doc.put("red_id", red_id);
			doc.put("ctime", DateTool.fromDate24H());
			doc.put("cdate", new Date()); 
			area_db.add_db_info("red_packet", doc);
			
			//测试  1分
			
			//调用程序 发红包 
			String xml_str = Red_packet.create_date(openid, 1, red_id);
			
			String back_xml = "";
			
			try {
				  back_xml = ClientCustomSSL.doRefund(MessageType.Red_packet_url, xml_str, MessageType.cert_path, MessageType.partner);
				  
				  System.out.println("-----tx------"+back_xml);
				  
				 Map map = GetWxOrderno.doXMLParse(back_xml);
				 
				 if((map.get("result_code").toString()).equals("SUCCESS")){
					 //发红包成功 
					 pay_db.update_red_packet(red_id,xml_str,back_xml,1);
					 
					 pay_db.tx_scholarship(openid, red_money);
					 
				 }else{
					 
					 //发红包失败
					 pay_db.update_red_packet(red_id,xml_str,back_xml,-1);
					 back_code = 300;
				 }
				  
			}catch(Exception e){ 
				e.printStackTrace();
			}
			
		}
		
		 model.put("back_code", back_code);
		 
		 sendjson(model, response);
		
	}
	
	
	@RequestMapping(value = { "/check_user" })
	//@ResponseBody
	public void check_user(HttpServletRequest request,
			HttpServletResponse response, Map<String, Object> model){
		
		String openid = request.getParameter("openid");
		
		Document user = area_db.query_user_byopenid(openid); 
		
		model.put("user", user);
		
		sendjson(model, response);
	}
	
	/**
	 * 异步微信支付
	 * @return
	 */ 
	@RequestMapping(value = { "/ajaxWXpay" })
	//@ResponseBody
	public void goPayByWX(HttpServletRequest req,
			HttpServletResponse response, Map<String, Object> model){
		
		 String order_id = req.getParameter("order_id");
		 int kind = Integer.valueOf(req.getParameter("kind"));
		 
		 Document order =null;
		 
		 if(kind == 1){
			 order = pay_db.query_order(order_id);
		 }else  if(kind == 2){
			 order = pay_db.query_vip_order(order_id);
		 }else  if(kind == 3){
			 //查询续费订单
			 order = pay_db.query_xf_order(order_id);
		 }
		  
		
		String spbillCreateIp=this.getRemoteHost(req);
		
		String body="订单号是"+order_id;
		
		int paymoney=order.getInteger("pay_money");
		
		
		//paymoney=1;
		
		double total_fee=(Double.valueOf(paymoney)/100);
		
		String openid = order.getString("open_id");
		
		String str ="";
		
		//未支付
		if(order.getInteger("is_order")  == 0 ){
			
			//订单支付记录 
			
			Document pay_log = new Document();
			pay_log.put("order_id", order_id);
			pay_log.put("kind", kind);
			pay_log.put("c_time", DateTool.fromDate24H());
			pay_log.put("c_date", new Date()); 
			pay_log.put("openid", openid);
			pay_log.put("name", order.getString("name"));
			pay_log.put("pay_money", paymoney); 
			pay_log.put("state", 0);
			pay_log.put("way", 1);//微信支付  2 奖学金支付
			
			area_db.add_db_info("pay_log", pay_log);
			
			str = this.getPackage(order_id, body, openid, spbillCreateIp, total_fee+"");
		}else {
			 
			str ="has_pay";
		}
		 
		   
		sendMassage(str, response);
	}
	 
	
/**
 * 
 * @param ono ,订单号(不能有空格，下划线等)
 * @param body,订单说明
 * @param openid,
 * @param spbillCreateIp,请求的ip
 * @param total_fee,订单金额(如0.01)
 * @return
 */
	
	public String  getPackage(String ono,String body,String openid,String spbillCreateIp,String total_fee){
		//user=(UsersVO)session.getAttribute("user");
	
	
		try { 
			//配置参数
			PayTool pt=new PayTool();
			
			//pt.setOpenId(user.getOpenid());//
			pt.configuration();
			WxPayDto wpd=new WxPayDto();
			//传入参数openId,orderId,totalFee(以分为单位),spbill_create_ip(订单生成的机器 IP),body得到prepay_id
			wpd.setOpenId(openid);
			
			wpd.setBody(body);//订单说明
			wpd.setNotifyUrl(MessageType.wx_notify_url);
			wpd.setOrderId(ono);
			wpd.setSpbillCreateIp(spbillCreateIp);
			wpd.setTotalFee("￥"+total_fee);
			
			String packages=Demo.getPackage(wpd);
			
				
			return packages;
					 
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	} 
	
	 
	
	/**
	 * 获取访问ip
	 * @param request
	 * @return
	 */ 
	public String getRemoteHost(javax.servlet.http.HttpServletRequest request){
	    String ip = request.getHeader("x-forwarded-for");
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
	        ip = request.getHeader("Proxy-Client-IP");
	    }
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
	        ip = request.getHeader("WL-Proxy-Client-IP");
	    }
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
	        ip = request.getRemoteAddr();
	    }
	    return ip.equals("0:0:0:0:0:0:0:1")?"127.0.0.1":ip;
	}
	
	 
	
	
	/**
	 * 微信支付通知
	 */
	@RequestMapping(value = {"/WXnotify"})
	public void WXnotify(HttpServletRequest request,
			HttpServletResponse response, Map<String, Object> model){
		
		System.out.print("微信支付回调数据开始");
		//示例报文
//		String xml = "<xml><appid><![CDATA[wxb4dc385f953b356e]]></appid><bank_type><![CDATA[CCB_CREDIT]]></bank_type><cash_fee><![CDATA[1]]></cash_fee><fee_type><![CDATA[CNY]]></fee_type><is_subscribe><![CDATA[Y]]></is_subscribe><mch_id><![CDATA[1228442802]]></mch_id><nonce_str><![CDATA[1002477130]]></nonce_str><openid><![CDATA[o-HREuJzRr3moMvv990VdfnQ8x4k]]></openid><out_trade_no><![CDATA[1000000000051249]]></out_trade_no><result_code><![CDATA[SUCCESS]]></result_code><return_code><![CDATA[SUCCESS]]></return_code><sign><![CDATA[1269E03E43F2B8C388A414EDAE185CEE]]></sign><time_end><![CDATA[20150324100405]]></time_end><total_fee>1</total_fee><trade_type><![CDATA[JSAPI]]></trade_type><transaction_id><![CDATA[1009530574201503240036299496]]></transaction_id></xml>";
		
		String notityXml = "";
		String resXml = "";
		String inputLine="";
		try {
			while ((inputLine = request.getReader().readLine()) != null) {
				notityXml += inputLine;
			}
			request.getReader().close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("接收到的报文：" + notityXml);
		
		Map m = parseXmlToList2(notityXml);
		WxPayResult wpr = new WxPayResult();
		if(m!=null){
			if(m.get("appid")!=null){
			wpr.setAppid(m.get("appid").toString());
			}
			if(m.get("bank_type")!=null){
				wpr.setBankType(m.get("bank_type").toString());	
			}
			if(m.get("cash_fee")!=null){
				wpr.setCashFee(m.get("cash_fee").toString());
			}
			if(m.get("fee_type")!=null){
				wpr.setFeeType(m.get("fee_type").toString());
			}
			if(m.get("is_subscribe")!=null){
				wpr.setIsSubscribe(m.get("is_subscribe").toString());
			}
			if(m.get("mch_id")!=null){
				wpr.setMchId(m.get("mch_id").toString());	
			}
			if(m.get("nonce_str")!=null){
				wpr.setNonceStr(m.get("nonce_str").toString());
			}
		if(m.get("openid")!=null){
			wpr.setOpenid(m.get("openid").toString());
		}
			if(m.get("out_trade_no")!=null){
				wpr.setOutTradeNo(m.get("out_trade_no").toString());//商户订单号、id
			}
		if(m.get("result_code")!=null){
			wpr.setResultCode(m.get("result_code").toString());
			
		}
		if(m.get("return_code")!=null){
			wpr.setReturnCode(m.get("return_code").toString());	
		}	
			if(m.get("sign")!=null){
				wpr.setSign(m.get("sign").toString());	
			}
			if(m.get("time_end")!=null){
				wpr.setTimeEnd(m.get("time_end").toString());	
			}
			if(m.get("total_fee")!=null){
				wpr.setTotalFee(m.get("total_fee").toString());
			}
			if(m.get("trade_type")!=null){
				wpr.setTradeType(m.get("trade_type").toString());
			}
			if(m.get("transaction_id")!=null){
				wpr.setTransactionId(m.get("transaction_id").toString());//微信支付订单号	
			}
			
			String orderid= wpr.getOutTradeNo();
			
			 Document order =null;
			 
			 int kind = 1;

			if(orderid.contains("vip")){
				  
				 order = pay_db.query_vip_order(orderid); 
				 kind = 2;
			}else if(orderid.contains("xf")){
				order = pay_db.query_xf_order(orderid);
				 kind = 3;
				 
				  
			}else{
				 order = pay_db.query_order(orderid);
				 kind = 1;
			}
			
			
			if("SUCCESS".equals(wpr.getResultCode())){
				//支付成功
				resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"
				+ "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";
				 
				//查询支付日志，订单，支付状态， 奖学金，消息
				if(order.getInteger("is_order") == 0){ 
					//未支付
					
					order.put("is_order", 1);
					if(kind == 1){
						pay_db.update_order(orderid, order);
					}else if(kind == 2){ 
						pay_db.update_vip(orderid, order);
					}else if(kind == 3){
						 
						pay_db.update_xf_order(orderid, order);
						
						String vip_orderid = order.getString("vip_order");
						
						 Document vip_order = pay_db.query_vip_order(vip_orderid); 
						 vip_order.put("safe_date", order.getString("safe_year"));
						 vip_order.put("is_xf", 1);
						 
						 pay_db.update_vip(vip_orderid, vip_order);
						 
						
						
					}
					
					Document pay_log = pay_db.query_pay_log(orderid);
					pay_log.put("state", 1);
					pay_db.update_pay_log(orderid, pay_log);
					
					Document user = area_db.query_user_byopenid(order.getString("open_id"));
					
					if(kind==3){
						
						user.put("role", 1);
						pay_db.update_user(order.getString("open_id"), user);
						
						//购买续费模板消息 
						Map<String,Object> map = new HashMap<String, Object>();
						
						map.put("msg_type", 2);
						map.put("c_first", "恭喜续费到答课堂VIP，欢迎您的加入！");
						map.put("openid", order.getString("open_id")); 
						map.put("pay_money", order.getInteger("pay_money"));
						map.put("safe_date",  order.getString("safe_date"));
						
						msg_db.add_msg(map); 
						
					} 
					
					if(kind == 2){
						user.put("role", 1);
						user.put("vip_date", new Date());
						pay_db.update_user(order.getString("open_id"), user);
						
						//购买 vip模板消息 
						Map<String,Object> map = new HashMap<String, Object>();
						
						map.put("msg_type", 2);
						map.put("c_first", "恭喜你成为到答课堂VIP学员，欢迎您的加入！");
						map.put("openid", order.getString("open_id")); 
						map.put("pay_money", order.getInteger("pay_money"));
						map.put("safe_date",  order.getString("safe_date"));
						
						msg_db.add_msg(map); 
					}
					
					if(kind == 1){
						//扣除奖学金  用户成会员 
						if(order.getInteger("scholship_money") > 0){
							pay_db.res_scholarship(order.getString("open_id"), order.getInteger("scholship_money") ,order.getString("c_name"));
						}
						
						if(order.getInteger("score_money") > 0){
							//减少用户学分
							pay_db.res_score(order.getString("open_id"), order.getInteger("score_money"), order.getString("c_name"));
						}
						
						Document user2 = area_db.query_user_byopenid(order.getString("open_id"));
						
						if(user2.getInteger("role") == 0){
							user2.put("role", 3);
							user2.put("member_date", new Date()); 
							pay_db.update_user(order.getString("open_id"), user2);
						}
						
						//购买 课程 模板消息 
						Map<String,Object> map = new HashMap<String, Object>();
						
						map.put("msg_type", 1);
						map.put("openid", order.getString("open_id")); 
						map.put("pay_money", order.getInteger("pay_money")); 
						map.put("c_name", order.getString("c_name")); 
						map.put("order_id", order.getString("order_id")); 
						map.put("c_kind",  order.getInteger("type"));
						map.put("safe_date",  order.getString("safe_year"));
						
						msg_db.add_msg(map); 
					}
					
					
					if(user.get("top_openid") != null){
						if(order.getInteger("rebate") > 0){
							pay_db.add_top_scholarship(user.getString("top_openid"), order.getInteger("pay_money"),
									order.getInteger("rebate"), order.getString("open_id"), order.getString("name"), order.getString("c_name"));
						}
						
					}
					 
					
				}
				
				// kind = 2;vip  is_order== 1, is_xf = 0  续费状态  日期加   //修改续费记录
				
				 
			}else{
				resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
				+ "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
				
				//支付失败 
				
				//查询支付日志，
				if(order.getInteger("is_order") == 0){ 
					order.put("is_order", -1);
					if(kind == 1){
						pay_db.update_order(orderid, order);
					}else if(kind==2){
						pay_db.update_vip(orderid, order);
					}else if(kind==3){
						
						pay_db.update_xf_order(orderid, order);
					}
					
					Document pay_log = pay_db.query_pay_log(orderid);
					pay_log.put("state", -1);
					pay_db.update_pay_log(orderid, pay_log);
				}
				 
			}

		 
			try {
				this.sentXML(resXml,response);
			} catch (Exception e) {
				 
				e.printStackTrace();
			}
			
		}
		 
	}
	
 
	private void sentXML(String resXml, HttpServletResponse response) {
		 response.setContentType("text/xml;charset=utf-8");
			response.setHeader("Cache-Control","no-cache");
			
			try {
				response.getWriter().write(resXml);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}

	@RequestMapping(value = {"/goPayResult"})
	public void goPayResult(HttpServletRequest req,
			HttpServletResponse response, Map<String, Object> model){
		
		 String order_id = req.getParameter("order_id");
		 int kind = Integer.valueOf(req.getParameter("kind"));
		 
		 Document order =null;
		 
		 if(kind == 1){
			 order = pay_db.query_order(order_id);
		 }else if(kind == 2){
			 order = pay_db.query_vip_order(order_id);
		 }else if(kind==3){
			 
			 order = pay_db.query_xf_order(order_id);
			  
		 }
		
		 //查询支付结果
		 int pay_back =checkOrderno(order_id);
		
		 if(pay_back == 1){
			 //支付成功 
			 
			//查询支付日志，订单，支付状态， 奖学金，消息
				if(order.getInteger("is_order") == 0){ 
					//未支付
					
					order.put("is_order", 1);
					if(kind == 1){
						pay_db.update_order(order_id, order);
					}else if(kind == 2){
						
						
						
						pay_db.update_vip(order_id, order);
					}else if(kind == 3){
						
						pay_db.update_xf_order(order_id, order);
						
						String vip_orderid = order.getString("vip_order");
						
						 Document vip_order = pay_db.query_vip_order(vip_orderid); 
						 vip_order.put("safe_date", order.getString("safe_year"));
						 vip_order.put("is_xf", 1);
						 
						 pay_db.update_vip(vip_orderid, vip_order);		
					}
					
					Document pay_log = pay_db.query_pay_log(order_id);
					pay_log.put("state", 1);
					pay_db.update_pay_log(order_id, pay_log);
					
					Document user = area_db.query_user_byopenid(order.getString("open_id"));
					
						
					
					
					if(kind == 2){
						user.put("role", 1);
						user.put("vip_date", new Date());
						pay_db.update_user(order.getString("open_id"), user);
						
						//购买 vip模板消息 
						Map<String,Object> map = new HashMap<String, Object>();
						
						map.put("msg_type", 2);
						map.put("c_first", "恭喜你成为到答课堂VIP学员，欢迎您的加入！");
						map.put("openid", order.getString("open_id")); 
						map.put("pay_money", order.getInteger("pay_money"));
						map.put("safe_date",  order.getString("safe_date"));
						
						msg_db.add_msg(map); 
					}
					
					
						if(kind==3){
						
						user.put("role", 1);
						pay_db.update_user(order.getString("open_id"), user);
						
						//购买续费模板消息 
						Map<String,Object> map = new HashMap<String, Object>();
						
						map.put("msg_type", 2);
						map.put("c_first", "恭喜续费到答课堂VIP，欢迎您的加入！");
						map.put("openid", order.getString("open_id")); 
						map.put("pay_money", order.getInteger("pay_money"));
						map.put("safe_date",  order.getString("safe_date"));
						
						msg_db.add_msg(map);
						
					}
					
					if(kind == 1){
						//扣除奖学金  用户成会员 
						if(order.getInteger("scholship_money") > 0){
							pay_db.res_scholarship(order.getString("open_id"), order.getInteger("scholship_money") ,order.getString("c_name"));
						}
						
						if(order.getInteger("score_money") > 0){
							//减少用户学分
							pay_db.res_score(order.getString("open_id"), order.getInteger("score_money"), order.getString("c_name"));
						}
						
						Document user2 = area_db.query_user_byopenid(order.getString("open_id"));
						
						if(user2.getInteger("role") == 0){
							user2.put("role", 3);
							user2.put("member_date", new Date()); 
							pay_db.update_user(order.getString("open_id"), user2);
						}
						
						//购买 课程 模板消息 
						Map<String,Object> map = new HashMap<String, Object>();
						
						map.put("msg_type", 1);
						map.put("openid", order.getString("open_id")); 
						map.put("pay_money", order.getInteger("pay_money")); 
						map.put("c_name", order.getString("c_name")); 
						map.put("order_id", order.getString("order_id")); 
						map.put("c_kind",  order.getInteger("type"));
						map.put("safe_date",  order.getString("safe_year"));
						
						msg_db.add_msg(map);
						 
					}
					
					
					if(user.get("top_openid") != null){
						
						if(order.getInteger("rebate") > 0){
							pay_db.add_top_scholarship(user.getString("top_openid"), order.getInteger("pay_money"),
									order.getInteger("rebate"), order.getString("open_id"), order.getString("name"), order.getString("c_name"));
						} 
						
					}
					 
					
				}
			 
				 
			 
		 }else{
			 //支付失败
			 
			//查询支付日志，订单，支付状态，维修改则修改
			 
			//查询支付日志，
				if(order.getInteger("is_order") == 0){ 
					order.put("is_order", -1);
					if(kind == 1){
						pay_db.update_order(order_id, order);
					}else if(kind==2){
						pay_db.update_vip(order_id, order);
					}else if(kind==3){
						
						pay_db.update_xf_order(order_id, order);
					}
					
					Document pay_log = pay_db.query_pay_log(order_id);
					pay_log.put("state", -1);
					pay_db.update_pay_log(order_id, pay_log);
				}
		 }
		 
		 model.put("pay_back", pay_back);
		 sendjson(model, response);
		
	}
	
	
	@RequestMapping(value = {"/buy_vip"})
	public void buy_vip(HttpServletRequest req,
			HttpServletResponse response, Map<String, Object> model){
		
		String openid = req.getParameter("openid");
		String u_name = req.getParameter("name");
		String phone = req.getParameter("phone");
		String code = req.getParameter("code");
		String job = req.getParameter("job");
		String company = req.getParameter("company");
		
		int back_code = 0;
		
		if(getSession().getAttribute("yzphone")!= null && getSession().getAttribute("yzcode") !=null){
			
			String yzphone=getSession().getAttribute("yzphone").toString();
			String yzcode=getSession().getAttribute("yzcode").toString();
			
			if(phone.equals(yzphone) && code.equals(yzcode)){
				Document user = area_db.query_user_byopenid(openid);
				user.put("u_name", u_name);
				user.put("phone", phone);
				user.put("job", job);
				user.put("company", company);
				
				pay_db.update_user(openid, user);
				
				Document config_s = pay_db.quert_config();
				
				int phone_count = pay_db.phone_count(phone);
				
				if(user.getInteger("role") != 1){
					
					String order_id="vip"+String.valueOf(System.currentTimeMillis()); 
					Document doc = new Document();
					doc.put("order_id", order_id);
					doc.put("open_id", openid);
					doc.put("name", user.getString("name"));
					doc.put("c_name", "全站VIP");
					String safe_date = DateTool.addDate(DateTool.fromDateY(), config_s.getInteger("vip_date"));
					int rebate =0;
						if(phone_count>0){
							doc.put("pay_money", 100);
							rebate=0;
						}else{
							
							doc.put("pay_money", config_s.getInteger("vip_money"));
							rebate = config_s.getInteger("scholarship");
						}
					 
					doc.put("rebate", rebate);
					doc.put("c_date", DateTool.fromDateY());
					doc.put("date", new Date());
					
					doc.put("safe_date", safe_date);
					doc.put("is_order", 0);
					doc.put("is_over", 0);
					
					area_db.add_db_info("vip_order", doc);
					
					model.put("order_id", order_id);
					
					back_code = 1;
				}else{
					back_code = -2;
				}
			}else{
				back_code = -1;
			}
		}
		
		
		
		model.put("back_code", back_code);
		
		sendjson(model, response);
	}

	
	@RequestMapping(value = {"/buy_vip2"})
	public void buy_vip2(HttpServletRequest req,
			HttpServletResponse response, Map<String, Object> model){
		
		String openid = req.getParameter("openid");
		String u_name = req.getParameter("name");
		String phone = req.getParameter("phone");
		String code = req.getParameter("code");
		String job = req.getParameter("job");
		String company = req.getParameter("company");
		
		int back_code = 0;
		
		if(getSession().getAttribute("yzphone")!= null && getSession().getAttribute("yzcode") !=null){
			
			String yzphone=getSession().getAttribute("yzphone").toString();
			String yzcode=getSession().getAttribute("yzcode").toString();
			
			if(phone.equals(yzphone) && code.equals(yzcode)){
				Document user = area_db.query_user_byopenid(openid);
				user.put("u_name", u_name);
				user.put("phone", phone);
				user.put("job", job);
				user.put("company", company);
				
				pay_db.update_user(openid, user);
				
				Document config_s = pay_db.quert_config();
				
				int pay_money=100;
				
				if(user.getInteger("role") != 1){
					
					String order_id="vip"+String.valueOf(System.currentTimeMillis()); 
					Document doc = new Document();
					doc.put("order_id", order_id);
					doc.put("open_id", openid);
					doc.put("name", user.getString("name"));
					doc.put("c_name", "全站VIP");
					String safe_date = DateTool.addDate(DateTool.fromDateY(), config_s.getInteger("vip_date"));
					
					doc.put("pay_money",pay_money);
					
				//	int rebate = 100 * config_s.getInteger("percentage")/100+config_s.getInteger("scholarship");
					
					doc.put("rebate", 0);
					doc.put("c_date", DateTool.fromDateY());
					doc.put("date", new Date());
					
					doc.put("safe_date", safe_date);
					doc.put("is_order", 0);
					doc.put("is_over", 0);
					
					area_db.add_db_info("vip_order", doc);
					
					model.put("order_id", order_id);
					
					back_code = 1;
				}else{
					back_code = -2;
				}
			}else{
				back_code = -1;
			}
		}
		
		
		
		model.put("back_code", back_code);
		
		sendjson(model, response);
	}
	
	@RequestMapping(value = {"/xf_vip"})
	public void xf_vip(HttpServletRequest req,
			HttpServletResponse response, Map<String, Object> model){
		
		String openid = req.getParameter("openid");
		String u_name = req.getParameter("name");
		String phone = req.getParameter("phone");
		String code = req.getParameter("code");
		String job = req.getParameter("job");
		String company = req.getParameter("company");
		//String safe_date = req.getParameter("safe_date");
		
		int back_code = 0;
		
		if(getSession().getAttribute("yzphone")!= null && getSession().getAttribute("yzcode") !=null){
			
			String yzphone=getSession().getAttribute("yzphone").toString();
			String yzcode=getSession().getAttribute("yzcode").toString();
			
			if(phone.equals(yzphone) && code.equals(yzcode)){
				
				Document user = area_db.query_user_byopenid(openid);
				user.put("u_name", u_name);
				user.put("phone", phone);
				user.put("job", job);
				user.put("company", company);
				
				pay_db.update_user(openid, user);;
				
				Document config_s = pay_db.quert_config();
				  
				if(user.getInteger("role") == 1) {
					
					Document doc =new Document();
						 
						doc.put("open_id", openid);
						doc.put("is_order", 1);
							
					Document vip_order=area_db.find_vip_order("vip_order", doc);
					int rebate = config_s.getInteger("vip_money") * config_s.getInteger("percentage")/100;
					//vip_order.put("order_id", vip_order.getString("order_id"));
					vip_order.put("is_xf", 0);
					vip_order.put("rebate", rebate);
					//设置续费金额
					
					pay_db.update_vip(vip_order.getString("order_id"), vip_order);
					
					Document xf_log=new Document();
					
					String order_id="xf"+String.valueOf(System.currentTimeMillis()); 
					xf_log.put("order_id", order_id);
					xf_log.put("vip_order", vip_order.getString("order_id"));
					xf_log.put("open_id", openid);
					xf_log.put("name", user.getString("name"));
					xf_log.put("c_name", "续费VIP");
					String safe_date = DateTool.addDate(vip_order.getString("safe_date"), config_s.getInteger("vip_date"));
					
					xf_log.put("pay_money", config_s.getInteger("vip_money"));
					
					int rebate2 = config_s.getInteger("vip_money") * config_s.getInteger("percentage")/100;
					
					xf_log.put("rebate", rebate2);
					xf_log.put("c_date", DateTool.fromDateY());
					xf_log.put("date", new Date());
					
					xf_log.put("safe_date", safe_date);
					xf_log.put("is_order", 0);
					xf_log.put("is_over", 0);
					
					area_db.add_db_info("xf_log", xf_log);
					
					model.put("order_id", order_id);
					
					back_code = 1;
				}else{
					back_code = 404;
				}
				
			}else{
				back_code = -1;
			}
		}
		 
		
		model.put("back_code", back_code);
		
		sendjson(model, response);
	}
	
	
	@RequestMapping(value = {"/buy_order"})
	public void buy_order(HttpServletRequest req,
			HttpServletResponse response, Map<String, Object> model){
		
		String openid = req.getParameter("openid");
		String u_name = req.getParameter("name");
		String phone = req.getParameter("phone");
		String job = req.getParameter("job");
		String company = req.getParameter("company");
		String code = req.getParameter("code");
		
		String c_no = req.getParameter("c_no"); 
		int kind = Integer.valueOf(req.getParameter("kind"));//1课程2专题
		
		int scholship = Integer.valueOf(req.getParameter("scholship"));//1使用奖学金，0不使用
		int score = Integer.valueOf(req.getParameter("score"));//1使用学分，0不使用
		
		int back_code = 0; 
		if(getSession().getAttribute("yzphone")!= null && getSession().getAttribute("yzcode") !=null){
			
			String yzphone=getSession().getAttribute("yzphone").toString();
			String yzcode=getSession().getAttribute("yzcode").toString();
			
			if(phone.equals(yzphone) && code.equals(yzcode)){
				
				Document user = area_db.query_user_byopenid(openid);
				user.put("u_name", u_name);
				user.put("phone", phone);
				user.put("job", job);
				user.put("company", company);
				
				pay_db.update_user(openid, user);
				
				Document config_s = pay_db.quert_config();
				 
				
				Document doc = null; 
				if(kind == 1){
					doc = pay_db.quert_course(c_no);
				}else{
					doc= pay_db.quert_special(c_no);
				}
				
				System.out.println(doc);
				
				int price = doc.getInteger("price");  //订单总金额
				
				int rebate = price * config_s.getInteger("percentage")/100;
				
				int pay_money = 0;  //消费支付金额  
				int scholship_money = 0;//支付奖学金金额 
				int need_pay = 0;//0 不需要微信支付 1需要
				int score_money=0;//支付学分金额
				
				if(scholship == 0 && score == 0){
					//0不使用
					scholship_money = 0;
					score_money = 0;
					pay_money = price;
					need_pay = 1;		
				}else if(scholship == 1 && score == 1){
					
					int my_scholship = user.getInteger("scholarship");
					int my_score = user.getInteger("score");
					
					if(my_scholship >= price  ){
					 scholship_money = price;
						pay_money = 0;
						need_pay = 0;
					}else{
						//部分支付奖学金
									
						scholship_money =  my_scholship; 
						pay_money = price-scholship_money;
							need_pay = 1;
							if(pay_money<=my_score){
								
								score_money=pay_money;
								pay_money = 0;						
								need_pay = 0;				
						}else{
							score_money=my_score;
							pay_money=price-scholship_money-score_money;
							need_pay = 1;	
						}
										
					}
					 
				}else if(score == 0 && scholship == 1){
					int my_scholship = user.getInteger("scholarship");
					if(my_scholship >= price  ){
						 scholship_money = price;
							pay_money = 0;
							need_pay = 0;
						}else{
							//部分支付奖学金
										
							scholship_money =  my_scholship; 
							pay_money = price-scholship_money;
								need_pay = 1;						
						}
					
					
				}else if(score == 1 && scholship == 0){
					
					int my_score = user.getInteger("score");
					
					if(my_score >= price  ){
						score_money = price;
							pay_money = 0;
							need_pay = 0;
						}else{
							//部分支付奖学金
										
							score_money =  my_score; 
							pay_money = price-score_money;
								need_pay = 1;						
						}
				
				}
				
				
				
				String order_id=String.valueOf(System.currentTimeMillis());
				
				Document  course_order=new Document();
				
				course_order.put("order_id", order_id);
				course_order.put("open_id", openid);
				course_order.put("c_no", c_no);
				course_order.put("name", user.getString("name"));
				
				course_order.put("img", doc.getString("img"));
				course_order.put("date", new Date());
				
				course_order.put("teacher", doc.getString("teacher"));
				course_order.put("text", doc.getString("text"));
				course_order.put("safe_date", doc.getString("safe_date")); 
				String safe_year = DateTool.addDate(DateTool.fromDateY(), doc.getInteger("safe_year")*365); 
				course_order.put("safe_year",safe_year); 
				course_order.put("price", doc.getInteger("price"));
				
				course_order.put("rebate", rebate);
				course_order.put("scholship_money", scholship_money);
				course_order.put("pay_money", pay_money);
				
				course_order.put("c_date", DateTool.fromDateY());
				course_order.put("date_1", DateTool.fromDateY());
				 
				course_order.put("score_money", score_money);
				course_order.put("watch", doc.getInteger("watch")); 
				//course_order.put("role", user.getInteger("role"));
				
				String info ="";
				
				int type = 0;
				
				if(kind == 1){
					type = doc.getInteger("type");
					course_order.put("type", doc.getInteger("type"));
					course_order.put("c_name", doc.getString("c_name"));
					info = doc.getString("c_name");
				}else{
					course_order.put("type", 2);
					course_order.put("c_name", doc.getString("special_name"));
					
					info = doc.getString("special_name");
					
					type = 2;
				}
				
				if(need_pay == 0){
					course_order.put("is_order", 1);
				}else{
					course_order.put("is_order", 0);
				}
				 
				course_order.put("is_over", 0); 
				area_db.add_db_info("course_order", course_order);
				
				if(need_pay == 0){
					//减少用户的奖学金  ，加上级奖学金
					pay_db.res_scholarship(openid, scholship_money, info);
					//减少用户学分
					pay_db.res_score(openid, score_money, info);
					
					if(user.get("top_openid") != null){
						
						pay_db.add_top_scholarship(user.getString("top_openid"), course_order.getInteger("pay_money"),
								course_order.getInteger("rebate"), course_order.getString("open_id"), course_order.getString("name"), course_order.getString("c_name"));
					}
					
					Document user2 = area_db.query_user_byopenid(openid);
					
					if(user2.getInteger("role") != 1){
						user2.put("role", 3);
						pay_db.update_user(openid, user2);
					}
					
				}
				
				//添加订购数
				
				if(doc.get("order_count")!= null){
					
					int order_count = doc.getInteger("order_count")+1;
					
					String _id = doc.get("_id").toString();
					
					doc.put("order_count", order_count);
					
					if(kind == 1){ 
						pay_db.update_course(_id, doc); 
					}else{
						pay_db.update_special(_id, doc);
					}
				}
				
				back_code = 1; 
				model.put("order_id", order_id);
				model.put("need_pay", need_pay); 
				model.put("type", type);
				
			}else{
				back_code = -1; 
			}
		}
		
		
		model.put("back_code", back_code);
		
		sendjson(model, response);
		
	}
	
 
	/**
	 * 订单支付结果
	 * 
	 * @return
	 */
	
	public int checkOrderno(String orderno){
		
		WeChatVO w=PayTool.regs_pay(orderno);
		
		if(w!=null&&w.getTrade_state()!=null&&(w.getTrade_state()).equals("SUCCESS")){ 
			//添加记录 
			return 1; 
		}
		
		return  0;
		
	}

	

	/**
	 * description: 解析微信通知xml
	 * 
	 * @param xml
	 * @return
	 * @author ex_yangxiaoyi
	 * @see
	 */
	
	private static Map parseXmlToList2(String xml) {
		Map retMap = new HashMap();
		try {
			StringReader read = new StringReader(xml);
			// 创建新的输入源SAX 解析器将使用 InputSource 对象来确定如何读取 XML 输入
			InputSource source = new InputSource(read);
			// 创建一个新的SAXBuilder
			SAXBuilder sb = new SAXBuilder();
			// 通过输入源构造一个Document
			org.jdom.Document doc = (org.jdom.Document) sb.build(source);
			Element root = doc.getRootElement();// 指向根节点
			List<Element> es = root.getChildren();
			if (es != null && es.size() != 0) {
				for (Element element : es) {
					retMap.put(element.getName(), element.getValue());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retMap;
	}
	
	
}
