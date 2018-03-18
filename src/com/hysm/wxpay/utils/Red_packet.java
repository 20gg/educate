package com.hysm.wxpay.utils;

import java.util.Map;
import java.util.Random;

import com.hysm.bean.MessageType;

/**
 * 微信红包  （普通红包）
 * @author songkai
 *
 */
public class Red_packet {

	/**
	 * 生成24位随机字符串
	 * @return
	 */
	public static String get_nonce_str(){
		
		String base = "abcdefghijklmnopqrstuvwxyz0123456789";     
	    Random random = new Random();     
	    StringBuffer sb = new StringBuffer();     
	    for (int i = 0; i < 24; i++) {     
	        int number = random.nextInt(base.length());     
	        sb.append(base.charAt(number));     
	    }     
	    return sb.toString(); 
	}
	
	/**
	 * 微信xml 生成 与生成加密
	 * @param openid
	 * @param money
	 * @return
	 */
	public static String create_date(String openid,int money,String red_id){
		
		String nonce_str = get_nonce_str();
		
		String str = 
				
				"act_name="+MessageType.act_name
				+"&client_ip="+MessageType.client_ip 
				+"&mch_billno="+red_id
				+"&mch_id="+MessageType.partner
				+"&nonce_str="+nonce_str
				
				+"&re_openid="+openid
				+"&remark="+MessageType.remark
				+"&send_name="+MessageType.send_name
				+"&total_amount="+money
				+"&total_num=1"
				 
				+"&wishing="+MessageType.wishing 
				+"&wxappid="+MessageType.appid;
		
		String sign_back = str+"&key="+MessageType.partnerkey; 
		
		String charset = "UTF-8"; 
		String sign=MD5Util.MD5Encode(sign_back, charset).toUpperCase();
		
		//9514F84FA79EAB350D24A5C3290FDB59  //9514F84FA79EAB350D24A5C3290FDB59
				
		
		String xml_str = "<xml>"
				+"<act_name><![CDATA["+MessageType.act_name+"]]></act_name>"
				+"<client_ip><![CDATA["+MessageType.client_ip+"]]></client_ip>"
				+"<mch_billno><![CDATA["+red_id+"]]></mch_billno>"
				+"<mch_id><![CDATA["+MessageType.partner+"]]></mch_id>"
				+"<nonce_str><![CDATA["+nonce_str+"]]></nonce_str>"
				
				+"<re_openid><![CDATA["+openid+"]]></re_openid>"
				+"<remark><![CDATA["+MessageType.remark+"]]></remark>"
				+"<send_name><![CDATA["+MessageType.send_name+"]]></send_name>" 
				+"<total_amount><![CDATA["+money+"]]></total_amount>"
				+"<total_num><![CDATA[1]]></total_num>"
				
				+"<wishing><![CDATA["+MessageType.wishing+"]]></wishing>" 
				+"<wxappid><![CDATA["+MessageType.appid+"]]></wxappid>" 
				+"<sign><![CDATA["+sign+"]]></sign>"
				+"</xml>";
		 
		return xml_str;
	}
	
	 
	
	public static void main(String[] args) {
		
		/*String xml_str = "<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[发放成功.]]></return_msg>"
			+ "<result_code><![CDATA[SUCCESS]]></result_code><err_code><![CDATA[0]]></err_code><err_code_des><![CDATA[发放成功.]]></err_code_des>"
			+ "<mch_billno><![CDATA[0010010404201411170000046545]]></mch_billno> <mch_id>10010404</mch_id> <wxappid><![CDATA[wx6fa7e3bab7e15415]]></wxappid>"
			+ "<re_openid><![CDATA[onqOjjmM1tad-3ROpncN-yUfa6uI]]></re_openid><total_amount>1</total_amount></xml>";*/
		
		String xml_str = create_date("oLir8jsYupMTLDJnaWjdVC-l7OJE", 1, "1497875162662");
		
		/*Map m = null;
		try {
			m = GetWxOrderno.doXMLParse(xml_str);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		System.out.println(xml_str);
		
	}
}
