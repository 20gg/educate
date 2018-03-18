package com.hysm.tools;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mysql.jdbc.StringUtils;

public class DateTool {
	/**
	 * 返回当前时间 格式：yyyy-MM-dd HH:mm:ss
	 * 
	 * @return String
	 */
	public static String fromDate24H() {
		// SimpleDateFormat ss = new SimpleDateFormat("yyyy-MM-dd
		// hh:mm:ss");//12小时制
		SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 24小时制
		return sdformat.format(new Date());
	}

	public static String fromDate12H() {
		SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");// 12小时制
		return sdformat.format(new Date());
	}

	/**
	 * 返回当前时间 格式：yyyy-MM-dd
	 * 
	 * @return String
	 */
	public static String fromDateY() {
		DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		return format1.format(new Date());
	}
	
	public static long stringTolong(String time){
		SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		Date dt2;
		long lTime = 0;
		try {
			if (!StringUtils.isNullOrEmpty(time)) {
				dt2 = sdf.parse(time);
				lTime = dt2.getTime();
				
			}else{
				return lTime;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return lTime;
	}
	
	public static long get_long(String happen2, String now_time2) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		long diff = 0;
		try{

		    Date d1 = df.parse(now_time2);
		    Date d2 = df.parse(happen2);
		    diff = d1.getTime() - d2.getTime();

		}catch (Exception e){
			e.printStackTrace();
		}
		return diff;
	}
	
	public static String get_yesterday(){
		Calendar   cal   =   Calendar.getInstance();
		cal.add(Calendar.DATE,   -1);
		String yesterday = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
		
		return yesterday;
	}
	
	//推迟一年时间
	public static String get_next_year(){
		 Calendar c = Calendar.getInstance();
		  SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		  c.setTime(new Date());
	        c.add(Calendar.YEAR, +1);
	        Date y = c.getTime();
	        String year = format.format(y);
	      
		
		return year;
	}
	
	public static String addDate(String s_date,int d){
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // 日期格式
		
		Date date = null;
		try {
			date = dateFormat.parse(s_date);
		} catch (ParseException e) {
			 
			e.printStackTrace();
		}
		
		long time = date.getTime(); // 得到指定日期的毫秒数
		long day = (long)d*24*60*60*1000; // 要加上的天数转换成毫秒数
		
		time += day; // 相加得到新的毫秒数
		
		return dateFormat.format(new Date(time)); // 将毫秒数转换成日期
		
		}
	
	public static int compare_date(String d1, String d2){
		
		  DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		  
		  int back =0;
		  
		  try {
			Date dt1 = df.parse(d1);
			Date dt2 = df.parse(d2);
			
			 if (dt1.getTime() > dt2.getTime()) {
				 
				 back = 1;
			 }else if(dt1.getTime() == dt2.getTime()){
				 
				 back = 0;
			 }else{
				 back = -1;
			 } 
			 
		} catch (ParseException e) { 
			e.printStackTrace();
		}
       
		 return back;
	}

	
	 public static int getLastDayOfMonth(int year,int month)
	    {
	        Calendar cal = Calendar.getInstance();
	        //设置年份
	        cal.set(Calendar.YEAR,year);
	        //设置月份
	        cal.set(Calendar.MONTH, month-1);
	        //获取某月最大天数
	        int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
	         
	         
	        return lastDay;
	    }
	 public static Date get_the_date(String date_time){
			
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			Date date = null;
			 try {
				 date = df.parse(date_time);
			} catch (ParseException e){ 
				e.printStackTrace();
			}
			 
			return date;
		}
		
	 
 public static  Map<String,Object> get_month_week(int year,int month){
		 
		 Calendar cal = Calendar.getInstance();
        //设置年份
        cal.set(Calendar.YEAR,year);
        //设置月份
        cal.set(Calendar.MONTH, month-1);
        //获取某月最大天数
        int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        
       
        
        List<Map<String,String>> min_list = new ArrayList<Map<String,String>>(); 
        List<Map<String,String>> max_list = new ArrayList<Map<String,String>>();
         
        
        for(int d=1;d<= lastDay;d++){
        	
        	 Calendar cal2 = Calendar.getInstance();
        	 
        	 String m ="";
        	 if(month <10){
        		 m="0"+month;
        	 }else{
        		 m=""+month;
        	 }
        	 
        	 String day ="";
        	 if(d <10){
        		 day="0"+d;
        	 }else{
        		 day=""+d;
        	 }
        	 
        	 try {
				Date date =  df.parse(year+"-"+m+"-"+day); 
				cal2.setTime(date);
		        int w = cal2.get(Calendar.DAY_OF_WEEK) - 1;
		        
		        Map<String,String> map = new HashMap<String, String>();
		        
		        map.put("week", weekDays[w]);
		        map.put("date", year+"-"+m+"-"+day);
		       
		        
		        if(d==1 && !weekDays[w].equals("星期一")){ 
	        		min_list.add(map); 
	        	}
	        	
	        	if(weekDays[w].equals("星期一")){
	        		min_list.add(map);
	        	}
	        	
	        	if(weekDays[w].equals("星期日")){
	        		max_list.add(map);
	        	}
	        	
	        	if(d == lastDay && !weekDays[w].equals("星期日")){ 
	        		max_list.add(map);
	        	}
				
			} catch (ParseException e) {
				 
				e.printStackTrace();
			}
        	
        }
         
      //  System.out.println(min_list.toString());
      //  System.out.println(max_list.toString());
        
        Map<String,Object> back = new HashMap<String, Object>();
        back.put("min", min_list);
        back.put("max", max_list);
        
        return back;
		 
	 }
 	public static void main(String[] args) {
 		System.out.print(fromDateY());
	}
}
