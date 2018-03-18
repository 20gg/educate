package com.hysm.wxpay.common;

import com.hysm.wxpay.demo.Demo;





public class Secret {
	
	public static void configuration(String appid,String appsecret,String partner,String partnerkey,String openId,String notifyurl){
		Demo.setAppid(appid);
		Demo.setAppsecret(appsecret);
		

		Demo.setPartner(partner);
		Demo.setPartnerkey(partnerkey);
		Demo.setOpenId(openId);
		Demo.setNotifyurl(notifyurl);
	}
}
