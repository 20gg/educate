package com.hysm.tools;

import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;

public class Video_url {

	public static String MD5(String inStr) {   
	    MessageDigest md5 = null;   
	    try {   
	     md5 = MessageDigest.getInstance("MD5");   
	    } catch (Exception e) {   
	     System.out.println(e.toString());   
	     e.printStackTrace();   
	     return "";   
	    }   
	    char[] charArray = inStr.toCharArray();   
	    byte[] byteArray = new byte[charArray.length];   
	    
	    for (int i = 0; i < charArray.length; i++)   
	     byteArray[i] = (byte) charArray[i];   
	    
	    byte[] md5Bytes = md5.digest(byteArray);   
	    
	    StringBuffer hexValue = new StringBuffer();   
	    
	    for (int i = 0; i < md5Bytes.length; i++) {   
	     int val = ((int) md5Bytes[i]) & 0xff;   
	     if (val < 16)   
	      hexValue.append("0");   
	     hexValue.append(Integer.toHexString(val));   
	    }   
	    
	    return hexValue.toString();   
	} 
	
	/**
	 * 生成秒钟的16进制时间
	 * @return
	 */
	public static String Get_t16(){
		
		long str =System.currentTimeMillis()/1000;
		
		return Long.toHexString(str) ;
	}
	
	/**
	 * 创建防盗链的视频链接
	 * @param file_path
	 * @param path
	 * @return
	 */ 
	public static Map<String,String> Create_video_url(String file_path){
		
		String t = Get_t16(); 
		String path = get_filename(file_path);
		
		String sign = MD5(QQ_code.Video_url_key+path+t);
		
		Map<String,String> map = new HashMap<String, String>();
		
		map.put("sign", sign);
		map.put("t", t);
		map.put("path", path);
		map.put("video_url", file_path+"?sign="+sign+"&t="+t);
		
		return map;
	}
	
	
	public static String get_filename(String str){
		
		String arr[] = str.split("/");
		
		int len = arr.length;
		
		return arr[len-1];
	}
	
	public static void main(String[] args) {
	    
		//System.out.println(Create_video_url("http://123.vod.myqcloud.com/vodfile.mp4","vodfile.mp4"));
		
		System.out.println(get_filename("http://1253790856.vod2.myqcloud.com/355e2b2dvodgzp1253790856/e0dc21e79031868222951683144/f0.mp4"));
	}
	
}
