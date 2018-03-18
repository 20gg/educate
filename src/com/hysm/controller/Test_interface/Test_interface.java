/**
 * 
 */
package com.hysm.controller.Test_interface;
import java.io.*;
import java.net.*;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.*;

import org.apache.commons.codec.binary.Base64;
/**
 * XXXXXXXX
 * @author cgb
 * @date 2017年10月19日
 */

public class Test_interface {

   public static String  url="https://www.shenfupay.com:38443/ SFQuickPayJH/v1.0/PublicConsume";//消费接口地址
   
 // public static String  url="https://www.shenfupay.com:38443/ SFQuickPayJH/PublicOpenCard";//开通快捷接口地址
   
   
   public static String  mercId="485171018009623";//商户ID
   
   public static String  cardNo="6217856100027827887";//银行卡卡号
    public static String  orderNo="59798aafff4a7b4ed3f22dbc";//商户订单号。
     
   public static String  notifyUrl="www.baidu.com";//交易成功后，异步调用该url（用于接收返回参数）
   public static String  frontUrl="www.baidu.com";//返回项目里的页面
    
   public static String private_key="MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBALGYETcJY6WWbAEFIL2JjFXqjEYt79M6Jk5PgqNeD+TNUO/wvRwwV5AmFcDuSuSvLMBQaK1wfwDoifdhgBgOloP6CnVryp5TCpUpCLQqnoYZmYYugOr6l8nYiHNzjjH/TkdYUO5eYU7S8IAgSXlvuKsqI0LlBxx5avWZfHdKDnqxAgMBAAECgYB8WIwdK/7QTx06LZv5+df/xCUJclqMXBe8FyHSPycDvdpg1f+jQBnfPTtYBcD8k8bStLpGThEu0qLfSi5FKdmqTeo2zlIs88uYof2xeuZF0aP/7rVzziY/+KhCMralyKwAEtqS9TQrEPESaqew2ZTFacE+ZSYIEWo6vq/YI0H9nQJBANvq+6ZZ+mNHsVsGT2fGZUgomACX6GYHGoy5OMdBKJWALerSPjEXOUuhuHFY1MNX5dF8wrMnihs7PEP4tIDn+J8CQQDOu2QwYekWZGtp9IQlEt7H6dEdYh66bGYVnsE26Py4Lz8byySN8YodwmPYMKFz6nVuTdnRaVBqv51YVZc4KLqvAkEAiDAv4SetiJfYPD9fIyvYguQopY1tLOnPRkBZ6gLu6A414UqLmwj/wVeICiFxuPsQ5jc1t46P1ydKvekhlwaQjwJBAJXPbjcxfLvhYYWS0AAdwJuWcRed9y6ZRxD7zoCALkM62bzGZWMHWlNefwQ3mYhqq1aXy/TZIJF68gcFX2Qu/7kCQHW03pvtq6UYExQfUFVo3Y8Ztg/IIvsBzu7tMPUG8QBR3T53R4d+66GqhNW1crcYOVu4zT2/DWjRNEYmX0MAFOY=";
	
	 
   /**
    * 向指定 URL 发送POST方法的请求
    * 
    * @param url
    *            发送请求的 URL
    * @param param
    *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
    * @return 所代表远程资源的响应结果
    */
   public static String sendPost(String url, String param) {
       PrintWriter out = null;
       BufferedReader in = null;
       String result = "";
       try {
           URL realUrl = new URL(url);
           // 打开和URL之间的连接
           URLConnection conn = realUrl.openConnection();
           // 设置通用的请求属性
           conn.setRequestProperty("accept", "*/*");
           conn.setRequestProperty("connection", "Keep-Alive");
           conn.setRequestProperty("user-agent",
                   "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
           // 发送POST请求必须设置如下两行
           conn.setDoOutput(true);
           conn.setDoInput(true);
           // 获取URLConnection对象对应的输出流
           out = new PrintWriter(conn.getOutputStream());
           // 发送请求参数
           out.print(param);
           // flush输出流的缓冲
           out.flush();
           // 定义BufferedReader输入流来读取URL的响应
           in = new BufferedReader(
                   new InputStreamReader(conn.getInputStream(),"utf-8"));
           String line;
           while ((line = in.readLine()) != null) {
               result += line;
           }
       } catch (Exception e) {
           System.out.println("发送 POST 请求出现异常！"+e);
           e.printStackTrace();
       }
       //使用finally块来关闭输出流、输入流
       finally{
           try{
               if(out!=null){
                   out.close();
               }
               if(in!=null){
                   in.close();
               }
           }
           catch(IOException ex){
               ex.printStackTrace();
           }
       }
       return result;
   } 
   
    
	
	//测试  获取短信验证码
	public static String  send_phone(String mercId,String txnAmt,String realName,String identityCard,String settleCardNo,
			String cardNo,String phoneNo){
		String back="";
		Map<String,String> paraMap = new HashMap<String,String>();  
		   paraMap.put("mercId",mercId);  //商户ID
	       paraMap.put("txnAmt", txnAmt);  //订单交易金额 
	       paraMap.put("realName", realName);  //结算卡姓名
	       paraMap.put("identityCard",identityCard);//身份证
	       paraMap.put("settleCardNo",settleCardNo);//结算银行卡
	       paraMap.put("cardNo",cardNo);  //消费卡号
	       paraMap.put("phoneNo", phoneNo);  //手机号
	       try {
	    	   
	    	   String url = ApplicationBase.coverMap2String(paraMap);
	    	   
	    	   String signature = RSAUtil.sign(url.getBytes(), private_key);
	    	   paraMap.put("signature", signature);  
	     
	    	   back = HttpClientUtil.doPost("https://www.shenfupay.com:38443/SFQuickPayJH/PublicGetSMSCode", paraMap, ApplicationBase.encoding_UTF8);
	    	    
	    	  System.out.println(back);
	       } catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	       return back;
	}
	
	
	//		消费接口
	
	public static String  xf_interface(String mercId,String txnAmt,String orderNo,String notifyUrl,String txnTime,
			String cardNo,String orderId,String txnAmtType,String D0rate,String T1rate){
		String back="";
		String phoneNo="";
		String realName="";
		String identityCard="";
		String settleCardNo="";
		Map<String,String> paraMap = new HashMap<String,String>();  
		   paraMap.put("mercId",mercId);  //商户ID
	       paraMap.put("txnAmt", txnAmt);  //订单交易金额 
	       paraMap.put("orderNo", orderNo);  //商户订单号
	       paraMap.put("notifyUrl",notifyUrl);//交易成功后，异步调用该url。
	       paraMap.put("smsCode",send_phone(mercId,txnAmt,cardNo,realName,identityCard,settleCardNo,phoneNo));//短信验证码
	       paraMap.put("cardNo",cardNo);  //消费卡号
	       paraMap.put("orderId", orderId);  //同获取消费短信验证码时商户订单号一致
	       paraMap.put("txnTime", txnTime);  //yyyyMMddHHmmss 同获取消费短信验证码时订单提交时间一致
	       paraMap.put("txnAmtType", txnAmtType);  //交易类型T1T1  D0:D0:
	       paraMap.put("D0rate", D0rate);  //不能小于0.0026
	       paraMap.put("T1rate", T1rate);  //不能小于0.0024
	       try {
	    	   String url = ApplicationBase.coverMap2String(paraMap);
	    	   
	    	   String signature = RSAUtil.sign(url.getBytes(), private_key);
	    	   paraMap.put("signature", signature);  
		back = HttpClientUtil.doPost("https://www.shenfupay.com:38443/SFQuickPayJH/v1.0/PublicConsume",paraMap,ApplicationBase.encoding_UTF8);
		
			System.out.println(back);
		
	       } catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	       return back;
	}
	
	
	
		//单笔查询
	
	public static String  one_order(String mercId,String logNo,String orderNo){
		String back="";
		Map<String,String> paraMap = new HashMap<String,String>();  
		   paraMap.put("mercId",mercId);  //商户ID
	       paraMap.put("logNo", logNo);  //交易流水号
	       paraMap.put("orderNo", orderNo);  //商户订单号
	       try {
	    	   String url = ApplicationBase.coverMap2String(paraMap);
	    	   
	    	   String signature = RSAUtil.sign(url.getBytes(), private_key);
	    	   paraMap.put("signature", signature);
		back = HttpClientUtil.doPost("https://www.shenfupay.com:38443/SFQuickPayJH/QueryTrade",paraMap,ApplicationBase.encoding_UTF8);
	//	JSONObject json = JSONObject.fromObject(back);  
		System.out.println(back);
	       } catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	       return back;
	}
	
	
	
	//开卡查询
	
	public static String  open_card(String mercId,String cardNo){
		String back="";
		Map<String,String> paraMap = new HashMap<String,String>();  
		   paraMap.put("mercId",mercId);  //商户ID
	       paraMap.put("cardNo",cardNo);  //消费卡号
	       try {
	    	   
	    	   String url = ApplicationBase.coverMap2String(paraMap);
	    	   
	    	   String signature = RSAUtil.sign(url.getBytes(), private_key);
	    	   paraMap.put("signature", signature);
		back = HttpClientUtil.doPost("https://www.shenfupay.com:38443/SFQuickPayJH/QueryCardOrder",paraMap,ApplicationBase.encoding_UTF8);
			 
			System.out.println(back);
	       } catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	       return back;
	}
	
	//测试开通快捷支付
	
	public static String  open_pay(String mercId,String orderNo,String frontUrl,String notifyUrl
			,String cardNo){
		String back="";
		 Map<String,String> paraMap = new HashMap<String,String>();  
	       paraMap.put("mercId",mercId); //商户ID
	       paraMap.put("orderNo", orderNo);  //商户订单号
	       paraMap.put("cardNo", cardNo);  //消费卡号
	       paraMap.put("frontUrl",frontUrl);//该url表示交易成功后，页面上【返回商户】对应的url。此参数只在交易成功有效
	       paraMap.put("notifyUrl",notifyUrl);//交易成功后，异步调用该url。
	       try {
	    	   String url = ApplicationBase.coverMap2String(paraMap);
	    	   
	    	   String signature = RSAUtil.sign(url.getBytes(), private_key);
	    	   
	    	   paraMap.put("signature", signature);
	    	   System.out.println("https://www.shenfupay.com:38443/SFQuickPayJH/PublicOpenCard?"+url+"&signature="+signature);
			//  back = HttpClientUtil.doPost("https://www.shenfupay.com:38443/SFQuickPayJH/PublicOpenCard",paraMap,ApplicationBase.encoding_UTF8);
			
			System.out.println(back);
	       } catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	       return back;
	}
	
	
	/**
	 * 本地测试签名是否通过
	* XXXXX
	* @param map
	* @return
	 */
	
	public  static  void test_qm()
	{
		 String privatekey="MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBALGYETcJY6WWbAEFIL2JjFXqjEYt79M6Jk5PgqNeD+TNUO/wvRwwV5AmFcDuSuSvLMBQaK1wfwDoifdhgBgOloP6CnVryp5TCpUpCLQqnoYZmYYugOr6l8nYiHNzjjH/TkdYUO5eYU7S8IAgSXlvuKsqI0LlBxx5avWZfHdKDnqxAgMBAAECgYB8WIwdK/7QTx06LZv5+df/xCUJclqMXBe8FyHSPycDvdpg1f+jQBnfPTtYBcD8k8bStLpGThEu0qLfSi5FKdmqTeo2zlIs88uYof2xeuZF0aP/7rVzziY/+KhCMralyKwAEtqS9TQrEPESaqew2ZTFacE+ZSYIEWo6vq/YI0H9nQJBANvq+6ZZ+mNHsVsGT2fGZUgomACX6GYHGoy5OMdBKJWALerSPjEXOUuhuHFY1MNX5dF8wrMnihs7PEP4tIDn+J8CQQDOu2QwYekWZGtp9IQlEt7H6dEdYh66bGYVnsE26Py4Lz8byySN8YodwmPYMKFz6nVuTdnRaVBqv51YVZc4KLqvAkEAiDAv4SetiJfYPD9fIyvYguQopY1tLOnPRkBZ6gLu6A414UqLmwj/wVeICiFxuPsQ5jc1t46P1ydKvekhlwaQjwJBAJXPbjcxfLvhYYWS0AAdwJuWcRed9y6ZRxD7zoCALkM62bzGZWMHWlNefwQ3mYhqq1aXy/TZIJF68gcFX2Qu/7kCQHW03pvtq6UYExQfUFVo3Y8Ztg/IIvsBzu7tMPUG8QBR3T53R4d+66GqhNW1crcYOVu4zT2/DWjRNEYmX0MAFOY="; 
		  String publickey="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCxmBE3CWOllmwBBSC9iYxV6oxGLe/TOiZOT4KjXg/kzVDv8L0cMFeQJhXA7krkryzAUGitcH8A6In3YYAYDpaD+gp1a8qeUwqVKQi0Kp6GGZmGLoDq+pfJ2Ihzc44x/05HWFDuXmFO0vCAIEl5b7irKiNC5QcceWr1mXx3Sg56sQIDAQAB"; 
		   
		  Map<String,String> paraMap = new HashMap<String,String>();  
		  paraMap.put("mercId",mercId); //商户ID
	       paraMap.put("orderNo", orderNo);  //商户订单号
	       paraMap.put("cardNo", cardNo);  //消费卡号
	       paraMap.put("frontUrl",frontUrl);//该url表示交易成功后，页面上【返回商户】对应的url。此参数只在交易成功有效
	       paraMap.put("notifyUrl",notifyUrl);//交易成功后，异步调用该url
		   
		   String toverifstring=ApplicationBase.coverMap2String(paraMap);
		   try {
			String  signature=RSAUtil.sign(toverifstring.getBytes(), privatekey);
			
			if(!RSAUtil.verify(toverifstring.getBytes(ApplicationBase.encoding_UTF8), publickey, signature)){
				
				System.out.println("签名失败");
				return;			
			}else{
				
				System.out.println("签名成功");
			}
			
			return;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
   public static void main(String[] args) {
	  
	//  send_phone(mercId, "100", "宋凯", "320821199104142937", "6222021110006401463", cardNo, "18260348087");
          
	 //  open_pay(mercId, orderNo, "www.baidu.com", "www.baidu.com", cardNo);
	   //test_qm();
	   
	//   open_card("485171018009623", "6217856100027827887");
	   
	   //one_order("485171018009623", "", "59798aafff4a7b4ed3f22dbc");
	   
	 //  xf_interface("485171018009623", "100", orderNo, notifyUrl, txnTime, cardNo, orderId, txnAmtType, D0rate, T1rate);
   }
   
   
   
   
   

}
