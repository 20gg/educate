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

public class CalendarTest {

	 public static void main(String[] args) {  
	         
		 getLastDayOfMonth(2017, 5);
		 System.out.println(getLastDayOfMonth(2017, 2));
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
}
