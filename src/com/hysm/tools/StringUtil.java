package com.hysm.tools;

import java.security.MessageDigest;
import java.util.Random;

/**
 * 字符串相关方法
 *
 */
public class StringUtil {
	/***
	 * 判读字符串是否为空值
	 * @param str
	 * @return 为true 表示不是空值，否则是空值
	 */
	public static boolean bIsNotNull(Object obj) {
		if(obj==null){
			return false;
		}
		String str=(String)obj;
		if(str==null || str.trim().equalsIgnoreCase("null") || str.trim().equals(""))
			return false;
		else 
			return true;
	}
	
	public static String getRandomString(int length){ //length表示生成字符串的长度  
	    String base = "0123456789";     
	    Random random = new Random();     
	    StringBuffer sb = new StringBuffer();     
	    for (int i = 0; i < length; i++) {     
	        int number = random.nextInt(base.length());     
	        sb.append(base.charAt(number));     
	    }     
	    return sb.toString();     
	 } 
	
	/**
	 * 将以逗号分隔的字符串转换成字符串数组
	 * @param valStr
	 * @return String[]
	 */
	public static String[] StrList(String valStr){
	    int i = 0;
	    String TempStr = valStr;
	    String[] returnStr = new String[valStr.length() + 1 - TempStr.replace(",", "").length()];
	    valStr = valStr + ",";
	    while (valStr.indexOf(',') > 0)
	    {
	        returnStr[i] = valStr.substring(0, valStr.indexOf(','));
	        valStr = valStr.substring(valStr.indexOf(',')+1 , valStr.length());
	        
	        i++;
	    }
	    return returnStr;
	}
	
	/**获取字符串编码
	 * @param str
	 * @return
	 */
	public static String getEncoding(String str) {      
	       String encode = "GB2312";      
	      try {      
	          if (str.equals(new String(str.getBytes(encode), encode))) {      
	               String s = encode;      
	              return s;      
	           }      
	       } catch (Exception exception) {      
	       }      
	       encode = "ISO-8859-1";      
	      try {      
	          if (str.equals(new String(str.getBytes(encode), encode))) {      
	               String s1 = encode;      
	              return s1;      
	           }      
	       } catch (Exception exception1) {      
	       }      
	       encode = "UTF-8";      
	      try {      
	          if (str.equals(new String(str.getBytes(encode), encode))) {      
	               String s2 = encode;      
	              return s2;      
	           }      
	       } catch (Exception exception2) {      
	       }      
	       encode = "GBK";      
	      try {      
	          if (str.equals(new String(str.getBytes(encode), encode))) {      
	               String s3 = encode;      
	              return s3;      
	           }      
	       } catch (Exception exception3) {      
	       }      
	      return "";      
	   } 
	
	
	
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
	
	public static void main(String[] args){
		
		System.out.println(MD5("123456"));
	}
		public static int toNum(Object obj){
		
			int  i=0;
			if(obj!=null){
				try{
					i=Integer.valueOf(String.valueOf(obj));
					
				}catch (Exception e) {
					
				}
			}
			
			
		return i;
		
	}
	
}
