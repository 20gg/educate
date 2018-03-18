package com.hysm.wecat.wxdb;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Pattern;
//import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.text.SimpleDateFormat;



public class StringTool
{

	public static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public static String getDateString()
	{
		return simpleDateFormat.format(new Date());
	}

	public static Date str2date(String str)
	{

		try 
		{
			java.util.Date date = simpleDateFormat.parse(str);
			//System.out.println(date);
			return date;
		} 
		catch (Exception e)
		{
			//e.printStackTrace();
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		try 
		{
			java.util.Date date = df.parse(str);
			//System.out.println(date);
			return date;
		} 
		catch (Exception e)
		{
			//e.printStackTrace();
		}
		return null;

	}


	public static String replace(String original ,String find,String replace)
	{
		if (original==null || find==null ||replace==null)
		{
			return original;
		}
		int findLen = find.length();
		int originalLen = original.length();
		if (originalLen==0 || findLen==0)
		{
			return original;
		}
		StringBuffer sb = new StringBuffer();
		int begin = 0; //下次检索开始的位置
		int i = original.indexOf(find); //找到的子串位置
		while (i!=-1)
		{
			sb.append(original.substring(begin,i));
			sb.append(replace);
			begin = i + findLen;
			i = original.indexOf(find,begin);
		}
		if (begin<originalLen)
		{
			sb.append(original.substring(begin));
		}
		return sb.toString();
	}



	/**
	 * 在字符串中查找某个字符的位置，但忽略包含在双引号对里的。
	 * @param src 被查询的字符串。
	 * @param offset 从哪个位置开始查找(包括该位置)(offset>=0，包括该位置)。
	 * @param length 查找的长度。
	 * @param c 待查询的字符。
	 * @return 返回查找到的位置，如果没找到返回-1。
	 */
	public static int indexOfBesideQuote(String src,int offset,char c)
	{

		boolean beside = true; //当前搜索位置在引号对外面
		if (src == null)
		{
			return -1;
		}
		int srcLength = src.length();
		if (offset >= srcLength || offset < 0)
		{
			return -1;
		}
		for (int i=offset;i<srcLength;i++)
		{
			char cur = src.charAt(i);

			//遇到一个非转义的引号进入，再遇到一个非转义的引号出来
			if ((cur == '\"')&& ((i==offset) || (src.charAt(i-1) != '\\')))
			{
				beside = !beside;
				continue;
			}

			//在引号外发现匹配字符
			if (cur==c && beside)
			{
				return i;
			}
		}
		return -1;
	}

	public static boolean isSpace(String str)
	{
		return (str== null) ||(str.trim().equals(""));
	}

	public static String randomPasswd()
	{
		int num = (int)(Math.random()*100000000)+500000000;
		//long num = System.nanoTime()/1000;
		return Long.toString(num,32);
	}

	
	public static String convertStrQuote(String str)
	{

		if(str == null )
		{
			return null;
		}
		//return str.replace('\'','\"');
		StringBuffer sBuff = new StringBuffer();

		int leng = str.length();
		int numOfbash = 0;//用于记录'前的连续\个数
		for(int i = 0; i<leng;i++)
		{
			char c = str.charAt(i);
			if(c == '\\')
			{
				numOfbash++;
			}
			else if((c== '\''||c=='\"'||c=='\r' ||c=='\n') && numOfbash%2 == 0)
			{
				sBuff.append('\\');
				numOfbash = 0;
			}
			else
			{
				numOfbash = 0;
			}
			sBuff.append(c);
		}
		return sBuff.toString();
	}



	public static String convertPureString(String str)
	{

		if(str == null )
		{
			return null;
		}
		//return str.replace('\'','\"');
		int leng = str.length();
		StringBuffer sBuff = new StringBuffer(leng+100);
		char c = 0;
		for(int i = 0; i<leng;i++)
		{
			c = str.charAt(i);

			if(c== '>')
			{
				sBuff.append("&gt;");
				continue;
			}
			if(c == '<')
			{
				sBuff.append("&lt;");
				continue;
			}
			if(c == '&')
			{
				sBuff.append("&amp;");
				continue;
			}
			if(c == '\"')
			{
				sBuff.append("&quot;");
				continue;
			}
			if(c == '\'')
			{
				sBuff.append("&apos;");
				continue;
			}
			if(c == '\r' || c== '\n')
			{
				sBuff.append("<BR>");
			}

			sBuff.append(c);

		}
		return sBuff.toString();
	}




	public static String concat(Object... args)
	{
		StringBuffer sb = new StringBuffer();
		for(int i=0;i<args.length;i++)
		{
			sb.append(args[i]);
		}
		return sb.toString();
	}


	public static int str2num(String str)
	{
		return (str.charAt(0)-'A')*10+(str.charAt(1)-48);
	}
	static char[] swChars = {'A','B','C','D','E','F','G','H','I','J'};
	public static String num2str(int num)
	{
		return String.valueOf(swChars[num/10])+num%10;
	}
	
	
	public static int ipstr2num(String ip)
	{
		String[] arr = ip.split("\\.");
		if(arr.length != 4){return 0;}
		return ((Integer.parseInt(arr[0])<<24)&0xff000000) + ((Integer.parseInt(arr[1])<<16)&0xff0000)
		  +((Integer.parseInt(arr[2])<<8)&0xff00) + (Integer.parseInt(arr[2])&0xff);
	}
	
	public static String addSlash(String str)
	{
		StringBuffer sb = new StringBuffer();
		char c = 0;
		for(int i=0;i<str.length();i++)
		{   
			c = str.charAt(i);
			if(c == '\''){sb.append("\\\'");}
			else if(c == '\"'){sb.append("\\\"");}
			else{sb.append(c);}
		}
		return sb.toString();
	}
	
	
	
	public static String encodeTostr(Map<String,String[]> map)
	{
		StringBuffer sb = new StringBuffer();
		Iterator<String> it = map.keySet().iterator();
		String[] strs = null;
		String key = null;
		int l = 0;
		while(it.hasNext())
		{
			key = it.next();
			sb.append(key).append("&");
			strs = map.get(key);
			sb.append(strs.length).append("&");
			for(l=0;l<strs.length;l++)
			{
				sb.append(strs[l]).append("&");
			}
		}
		return sb.toString();
	}
	
	public static Map<String,String[]>decode2Map(String str)
	{
		Map<String,String[]> map = new HashMap<String,String[]>();
		String[] strs = str.split("&");
		String key = null;
		String[] values = null;
		int len = 0;
		int ind = 0;
		for(int i = 0;i<strs.length;i++)
		{
			key = strs[i];
			len = Integer.parseInt(strs[i+1]);
			values = new String[len];
			ind = len;
			while(len>=ind)
			{
				values[len-ind] = strs[i+2+len-ind];
				ind++;
			}
			i = i+2+len;
			map.put(key, values);
		}
		
		return map;
	}

	//移动号码判断：
	//13,15,18,排除154,181,183,184,加上147共27号段
	public static boolean isMobile(String value)
	{
		if(value == null){return false;}
		//Pattern pt = Pattern.compile("((13[0-9]{1})|(15[0-1,3,5-9]{1})|(18[0,5-9]{1}))+\\d{8}\\b");  
		Pattern pt = Pattern.compile("((13[0-9]{1})|(147)|(15[0-3,5-9]{1})|(17[0-9]{1})|(18[0,2,5-9]{1}))+\\d{8}\\b");  
        return pt.matcher(value).find();
        /*
		if(value == null || value.length() != 11)
		{
			return true;
		}
		if(value.charAt(0) != '1')
		{
			return false;
		}

		char c = 0;

		if((c=value.charAt(0)) != '3'
				&& c != 5 
				&& c != 8)
		{
			return false;
		}
		
		for(int i =3;i<11;i++)
		{
			c = value.charAt(i);
			if(c<48 || c>57){return false;}
		}
		if(value.indexOf("152") != -1 )
		{
			return false;
		}

		if(value.indexOf("154") != -1  )
		{
			return false;
		}
		if(value.indexOf("181") != -1  )
		{
			return false;
		}
		if(value.indexOf("182") != -1  )
		{
			return false;
		}
		if(value.indexOf("183") != -1  )
		{
			return false;
		}
		if(value.indexOf("184") != -1  )
		{
			return false;
		}
		return true;
		*/
	}
	
	public static boolean isEmail(String value)
	{
		if(value== null || value.endsWith(".")){return false;}
        Pattern pt = Pattern.compile("\\w+@\\w+(\\.\\w){1,}");  
        return pt.matcher(value).find();		
	}
	
	public static boolean checkPassword(String str,int length)
	{
		if(str == null)
		{
			return false;
		}
		if(str.trim().length()<length)
		{
			return false;
		}
		return true;
	}
	
	
	

	public static int converYear2Grade(int yearNum)
	{
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);
		if(month >= 7)
		{
			return year - yearNum+1;
		}
		else
		{
			return year - yearNum ;
		}
	}

	
	public static int converGrade2Year(int grade)
	{
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);
		if(month >= 7)
		{
			year = year - grade+1;
		}
		else
		{
			year = year - grade ;
		}
		return year;
	}
	
	public static String trimStr(String str)
	{
		if(str == null){return null;}
		return str.trim();
	}
	/** 
	 * 字符串转换unicode 
	 */  
	public static String string2Unicode(String string) {  
	   
	    StringBuffer unicode = new StringBuffer();  
	   
	    for (int i = 0; i < string.length(); i++) {  
	   
	        // 取出每一个字符  
	        char c = string.charAt(i);  
	   
	        // 转换为unicode  
	        unicode.append("\\u" + Integer.toHexString(c));  
	    }  
	   
	    return unicode.toString();  
	}  
	 
	  
	/** 
	 * unicode 转字符串 
	 */  
	public static String unicode2String(String unicode) {  
	   
	    StringBuffer string = new StringBuffer();  
	   
	    String[] hex = unicode.split("\\\\u");  
	   
	    for (int i = 1; i < hex.length; i++) {  
	   
	        // 转换出每一个代码点  
	        int data = Integer.parseInt(hex[i], 16);  
	   
	        // 追加成string  
	        string.append((char) data);  
	    }  
	   
	    return string.toString();  
	}  

	public static void main(String[] args)
	{
		/*
		System.out.println("**********************");
		System.out.println(StringTool.isMobile("13951021945"));
		System.out.println(StringTool.isMobile("17951021945"));
		System.out.println(StringTool.isMobile("27951021945"));
		System.out.println(StringTool.isMobile("1395102196"));
		System.out.println(StringTool.isMobile("139510219623"));
		System.out.println(StringTool.isMobile("18112345678"));
		System.out.println(StringTool.isMobile("15212345678"));
		System.out.println(StringTool.isMobile("14712345678"));
		*/
		/*
		String s="r.a.b.c";
		String[] arr = s.split("[.]");
		for(int i= 0;i<arr.length;i++)
		{
			System.out.println(arr[i]);
		}
		*/
		
        /*
		System.out.println(StringTool.converGrade2Year(3));
		System.out.println(StringTool.converYear2Grade(2013));
		System.out.println(StringTool.converYear2Grade(2014));
		System.out.println(StringTool.converGrade2Year(4));
		*/
		
		/*
		URI uri  = URI.create("file://d:/image/120344/12345678901234.jpg");
		System.out.println(uri.getScheme());
		System.out.println(uri.getAuthority());
		System.out.println(uri.getRawAuthority());
		System.out.println(uri.getHost());
		System.out.println(uri.getPath());
		System.out.println(uri.getRawPath());
		
		System.out.print(Base64.encode("image/120344/12345678901234.jpg"));
		*/
		
		//Pattern pt = Pattern.compile("^\\w+((.jpg){1}|(.jpeg){1}|(.png){1})$",Pattern.CASE_INSENSITIVE);
		//System.out.println(pt.matcher("assesd.jpEg").find());
		/*
		for(int i = 0;i<30;i++)
		{
			System.out.println(StringTool.randomPasswd());
		}
		*/
		System.out.println(System.currentTimeMillis());
		
		
	}
	
	
	
	

}

