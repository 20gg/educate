package com.hysm.wecat.tools;

import java.security.*;
import java.util.TreeSet;
public class SHA1
{

	public static String digest(String inStr) 
	{
		MessageDigest md = null;
		String outStr = null;
		try
		{
			md = MessageDigest.getInstance("SHA-1");     //选择SHA-1，也可以选择MD5
			byte[] digest = md.digest(inStr.getBytes());       //返回的是byet[]，要转化为String存储比较方便
			outStr = bytetoString(digest);
		}
		catch (NoSuchAlgorithmException nsae)
		{
			nsae.printStackTrace();
		}
		return outStr;
	}


	public static String bytetoString(byte[] digest) 
	{
		String str = "";
		String tempStr = "";

		for (int i = 1; i < digest.length; i++) 
		{
			tempStr = (Integer.toHexString(digest[i] & 0xff));
			if (tempStr.length() == 1) {
				str = str + "0" + tempStr;
			}
			else
			{
				str = str + tempStr;
			}
		}
		return str.toLowerCase();
	}
	
	public static boolean authWXDEV(String signature,String timestamp,String nonce,String token)
	{

		TreeSet<String> set = new TreeSet<String>();
		set.add(token);
		set.add(timestamp);
		set.add(nonce);
		
		StringBuilder sb = new StringBuilder();
		for(String str: set)
		{
		   sb.append(str);
		}
		String sha = SHA1.digest(sb.toString());
		
		if(sha.equals(signature) || signature.indexOf(sha) == 2)
		{
			return true;
		}
		return false;
	}
	
	
	public static void main(String[] args)
	{
		TreeSet<String> set = new TreeSet<String>();
		set.add("140219489812222222222222adfassafdsafdsQQ666666sfasafsfsafsa");
		set.add("584327103");
		set.add("aaren0125ITREN");
		StringBuilder sb = new StringBuilder();
		for(String str : set)
		{
			sb.append(str);
		}
		String str = SHA1.digest(sb.toString());
		System.out.println(str+"["+str.length()+"]");
		
		
	}

}
