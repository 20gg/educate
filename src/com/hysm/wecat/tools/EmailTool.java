package com.hysm.wecat.tools;

import java.util.regex.Pattern;

public class EmailTool
{
	
	//移动手机邮箱
	public static String getMobileEmail(String mobile)
	{
		if(StringTool.isSpace(mobile)){return null;}
		Pattern ydPt = Pattern.compile("((134)|(135)|(136)|(137)|(138)|(147)|(150)|(151)|(152)|(157)|(158)|(159)|(182)|(187)|(188))+\\d{8}\\b");
		Pattern dxPt = Pattern.compile("((133)|(153)|(180)|(189))+\\d{8}\\b");
		Pattern ltPt = Pattern.compile("((130)|(131)|(132)|(155)|(156)|(185)|(186))+\\d{8}\\b");
		
		//Pattern pt = Pattern.compile("((13[0-9]{1})|(147)|(15[0-3,5-9]{1})|(17[0-9]{1})|(18[0,2,5-9]{1}))+\\d{8}\\b");  
		if(ydPt.matcher(mobile).find()){return mobile+"@139.com";}
		if(dxPt.matcher(mobile).find()){return mobile+"@189.cn";}
		if(ltPt.matcher(mobile).find()){return mobile+"@wo.cn";}
		
		return null;

	}
	
	public void sendEmail(String destEmail,String content)
	{
		
	}
	
	
	public static void main(String[] args)
	{
		System.out.println(EmailTool.getMobileEmail("1341234567"));
	}

}
