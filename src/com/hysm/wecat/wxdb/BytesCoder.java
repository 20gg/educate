package com.hysm.wecat.wxdb;

/*
 * 该类是用于将byte[]按位异或后再base64转码。
 * 解码与方相反。
 * */

import java.io.UnsupportedEncodingException;

public class BytesCoder 
{
	public static byte[] encode(byte[] bytes)
	{
		for(int i = 0;i<bytes.length;i++)
		{
			bytes[i] = (byte)((bytes[i]^0x8F)&0x00FF);
		}
		return bytes;
		
	}
	

	public static String encode2base64(byte[] bytes)
	{
		for(int i = 0;i<bytes.length;i++)
		{
			bytes[i] = (byte)((bytes[i]^0x8F)&0x00FF);
		}
		return Base64.encode(bytes);		
	}
	
	

	public static byte[] decode(byte[] bytes)
	{	
		for(int i = 0;i<bytes.length;i++)
		{
			bytes[i] = (byte)((bytes[i]^0x8F)&0x00FF);
		}
		return bytes;			
	}
	
	
	public static byte[] decode(byte[] bytes,int offset,int length)
	{	
		byte[] res = new byte[length];
		for(int i = offset;i<length;i++)
		{
			res[i] = (byte)((bytes[i]^0x8F)&0x00FF);
		}
		return res;			
	}
	
	public static byte[] decodebase64(String str)
	{
		byte[] bytes = Base64.decode(str);
		for(int i = 0;i<bytes.length;i++)
		{
			bytes[i] = (byte)((bytes[i]^0x8F)&0x00FF);
		}
		return bytes;		
	}
	
	public static void main(String[] args)
	{
		String str = "这就是一个test!";
		byte[] bytes;
		try {
			bytes = BytesCoder.encode(str.getBytes("UTF-8"));
			System.out.println(new String(BytesCoder.decode(bytes),"UTF-8"));
		}
		catch (UnsupportedEncodingException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
