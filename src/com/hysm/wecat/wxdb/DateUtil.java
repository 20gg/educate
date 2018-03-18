package com.hysm.wecat.wxdb;

import java.util.Calendar;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateUtil
{
	public static SimpleDateFormat YMDFormmater = new SimpleDateFormat("yyyy-MM-dd");
	//public static SimpleDateFormat TimeFormmater = new SimpleDateFormat("YYYY-mm-dd HH:MM:SS");
	
	//比较日期
	public static int compareToday(String date)
	{
		try
		{ 
		  Calendar c = Calendar.getInstance();
		  c.set(Calendar.HOUR_OF_DAY,0);
		  c.set(Calendar.MINUTE,0);
		  c.set(Calendar.SECOND,0);
		  c.set(Calendar.MILLISECOND,0);
		  long cur = c.getTime().getTime();
		  long _date = c.getTime().getTime();
		  //System.out.println(_date+":"+c.toString());
		  if(_date > cur){return 1;}
		  if(_date < cur){return -1;}
		  return 0;
		}
		catch(Exception ex)
		{
			return -100;
		}
	}	
	
	public static int compareToday(String from,String to)
	{
		if(compareToday(from) > 0){return 1;}
		if(compareToday(to) < 0){return -1;}
		return 0;
	}
	
	public static int getCurWeekNum()
	{	
		return Calendar.getInstance().get(Calendar.WEEK_OF_YEAR);
	}
	
	//获取一周中某天对应的日期。如date为null则是当前周。
	public static String getWeekDay(int day,Date date)
	{	
		Calendar c = Calendar.getInstance();
		if(date != null)
		{
			c.setTime(date);
		}
		c.set(Calendar.DAY_OF_WEEK,day);
		if(day == Calendar.SUNDAY)
		{
			c.add(Calendar.DAY_OF_MONTH,7);
		}
		return  YMDFormmater.format(c.getTime());		
	}
	
	//获取一周中某天对应的日期。如date为null则是当前周。
	public static String getWeekDay2(int day,String date)
	{  
		try
		{
		    return getWeekDay(day,YMDFormmater.parse(date));			
		}
		catch(Exception e)
		{
			return null;
		}
	}
	
	//获取一个月中某天对应的日期。如date为null则是当前周。
	public static String getMonthDay(int day,Date date)
	{	
		Calendar c = Calendar.getInstance();
		if(date != null)
		{
			c.setTime(date);
		}
		c.set(Calendar.DAY_OF_MONTH,day);
		return  YMDFormmater.format(c.getTime());		
	}
	//获取两个时间点相差的天数
	public static int getSpanDays(String d1,String d2)
	{
		try
		{
			//System.out.println(YMDFormmater.parse(d1).getTime());
			//System.out.println(YMDFormmater.parse(d2).getTime());
		    return 1+Math.abs((int)((YMDFormmater.parse(d1).getTime()-YMDFormmater.parse(d2).getTime())/(24*60*60*1000)));			
		}
		catch(Exception e)
		{ 
			e.printStackTrace();
			return 0;
		}
	}
	
	//获得第X周的开始与结束日期.参数一是周，参数二是年,默认为本周
	public static String[] getWeekSpan(int... dArr)
	{
		String[] res = new String[2];
		Calendar c = Calendar.getInstance();		
		if(dArr.length >= 1)
		{
			c.set(Calendar.WEEK_OF_YEAR, dArr[0]);
		}		
		if(dArr.length >= 2)
		{
			c.set(Calendar.YEAR, dArr[1]);
		}
		c.set(Calendar.DAY_OF_WEEK,Calendar.MONDAY);
		res[0] = YMDFormmater.format(c.getTime());
		c.add(Calendar.DAY_OF_YEAR, 7);
		res[1] = YMDFormmater.format(c.getTime());
		return res;
	}
	
	//获得第X月的开始与结束日期.参数一是月，参数二是年,默认为本月
	public static String[] getMonthSpan(int... dArr)
	{
		String[] res = new String[2];
		Calendar c = Calendar.getInstance();		
		if(dArr.length >= 1)
		{
			c.set(Calendar.MONTH, dArr[0]-1);
		}		
		if(dArr.length >= 2)
		{
			c.set(Calendar.YEAR, dArr[1]);
		}
		c.set(Calendar.DAY_OF_MONTH,1);
		res[0] = YMDFormmater.format(c.getTime());
		c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
		res[1] = YMDFormmater.format(c.getTime());
		return res;
	}
	
	//获得第X季度的开始与结束日期.参数一是季度，参数二是年,默认为本季
	public static String[] getSquareSpan(int... dArr)
	{
		String[] res = new String[2];
		Calendar c = Calendar.getInstance();	
		if(dArr.length == 0)
		{
			int month = c.get(Calendar.MONTH);
			if(month < 3){month = 0;}
			else if(month < 6){month = 3;}
			else if(month < 9){month = 6;}
			else if(month < 12){month = 9;}
			c.set(Calendar.MONTH, month);
		}
		if(dArr.length >= 1)
		{
			c.set(Calendar.MONTH, (dArr[0]-1)*3);
		}		
		if(dArr.length >= 2)
		{
			c.set(Calendar.YEAR, dArr[1]);
		}
		c.set(Calendar.DAY_OF_MONTH,1);
		res[0] = YMDFormmater.format(c.getTime());
		c.add(Calendar.MONTH,2);
		c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
		res[1] = YMDFormmater.format(c.getTime());
		return res;
	}
	

	
	//获得年的开始与结束日期.参数一年,默认为本年
	public static String[] getYearSpan(int... dArr)
	{
		String[] res = new String[2];
		Calendar c = Calendar.getInstance();			
		if(dArr.length >= 1)
		{
			c.set(Calendar.YEAR, dArr[0]);
		}	
		c.set(Calendar.MONTH,Calendar.JANUARY);
		c.set(Calendar.DAY_OF_MONTH,1);
		res[0] = YMDFormmater.format(c.getTime());
		c.set(Calendar.MONTH,Calendar.DECEMBER);
		c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
		res[1] = YMDFormmater.format(c.getTime());
		return res;
	}
	//获取当前日期的前一天
	public static String getNextDay(String date){
		try {
			Date time=YMDFormmater.parse(date);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(time);
			calendar.add(Calendar.DAY_OF_MONTH, -1);
		
			return YMDFormmater.format(calendar.getTime());
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
	public static String getlastDay(String date){
		try {
			Date time=YMDFormmater.parse(date);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(time);
			calendar.add(Calendar.DAY_OF_MONTH, 1);
		
			return YMDFormmater.format(calendar.getTime());
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
	public static void main(String[] args)
	{	
		System.out.println(DateUtil.getlastDay("2015-01-06"))	;
		System.out.println(DateUtil.getNextDay("2015-01-06"))	;
		System.out.println(DateUtil.compareToday("2011-12-5"));
		System.out.println(DateUtil.compareToday("2011-04-5"));	
		System.out.println(DateUtil.compareToday("2011-05-16"));	
		System.out.println(DateUtil.compareToday("2011-5-16"));		
		System.out.println(DateUtil.getWeekDay(Calendar.FRIDAY,null));	
		System.out.println(DateUtil.getWeekDay2(Calendar.SUNDAY,"2011-08-15"));			
		String[] span = DateUtil.getWeekSpan(55);
		System.out.println(span[0] +"-"+span[1]);
		System.out.println(DateUtil.getSpanDays("2011-08-17", "2011-08-16"));
		
		span = DateUtil.getMonthSpan(2,2000);
		System.out.println(span[0] +"-"+span[1]);
		
		span = DateUtil.getSquareSpan(17,2007);
		System.out.println(span[0] +"-"+span[1]);
		
		span = DateUtil.getYearSpan();
		System.out.println(span[0] +"-"+span[1]);
		
		System.out.println(getCurWeekNum());
		
		
		String from = null;
		String to = null;

		if(from == null || to == null)
		{	
			span = DateUtil.getMonthSpan();
			from = span[0];
			to = span[1];
		}
		int flag = DateUtil.compareToday(from, to);
		System.out.println(flag);
	}
	

}
