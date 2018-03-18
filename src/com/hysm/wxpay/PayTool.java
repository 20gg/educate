package com.hysm.wxpay;

import java.util.Map;


import com.hysm.tools.StringUtil;
import com.hysm.wxpay.common.Secret;
import com.hysm.wxpay.demo.Demo;
import com.hysm.wxpay.utils.RequestHandler;
import com.hysm.bean.MessageType;
import com.hysm.model.WeChatVO; 


public class PayTool {
	
	private static String appid=MessageType.appid,appsecret=MessageType.appsecret,partner=MessageType.partner,partnerkey=MessageType.partnerkey,openId;
	
	
	/**
	 * 微信配置
	 */
	public  void configuration(){
		//微信支付商户开通后 微信会提供appid和appsecret和商户号partner
		appid=MessageType.appid;
		appsecret = MessageType.appsecret;
		partner = MessageType.partner;
		//这个参数partnerkey是在商户后台配置的一个32位的key,微信商户平台-账户设置-安全设置-api安全
		partnerkey = MessageType.partnerkey;
		
		//微信支付成功后通知地址 必须要求80端口并且地址不能带参数
		String notifyurl = MessageType.wx_notify_url;	
		Secret secret=new Secret();
		secret.configuration(appid, appsecret, partner, partnerkey, openId, notifyurl);
		
	}
	
	/**
	 * 微信支付验证
	 * @return
	 */
	public static WeChatVO regs_pay(String out_trade_no){
		RequestHandler reqHandler = new RequestHandler(null, null);
		reqHandler.init(appid, appsecret, partnerkey);
		String mch_id=MessageType.partner;	//微信支付分配的商户号
		
		Map map=Demo.regs_pay(mch_id,StringUtil.getRandomString(6),out_trade_no);
		System.out.println(map.toString());
		WeChatVO weChatVO;
		String return_code=(String) map.get("return_code");
		if(return_code!=null&&return_code.equals("SUCCESS")){
			weChatVO=new WeChatVO();
			weChatVO.setBank_type((String)map.get("bank_type"));
			weChatVO.setCash_fee((String)map.get("cash_fee"));
			weChatVO.setDevice_info((String)map.get("device_info"));
			weChatVO.setIs_subscribe((String)map.get("is_subscribe"));
			weChatVO.setOpenid((String)map.get("openid"));
			weChatVO.setOut_trade_no((String)map.get("out_trade_no"));
			weChatVO.setTime_end((String)map.get("time_end"));
			weChatVO.setTotal_fee((String)map.get("total_fee"));
			weChatVO.setTrade_state((String)map.get("trade_state"));
			weChatVO.setTrade_state_desc((String)map.get("trade_state_desc"));
			weChatVO.setTrade_type((String)map.get("trade_type"));
			weChatVO.setTransaction_id((String)map.get("transaction_id"));
			return weChatVO;
		}
		return null;
		
	}

	
	
	

	
	
	
}

