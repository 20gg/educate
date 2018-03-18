package com.hysm.tools;

import java.util.List; 
import org.bson.Document;
 
public class Word_tool {

	/**
	 * 生成指定长度发敏感字 替换字符串
	 * @param len
	 * @return
	 */
	public static String get_replace_word(int len){
		String str ="";
		
		if(len > 0){
			for(int i=0;i<len;i++){
				str = str+"*";
			}
		}
		
		return str;
	}
	
	/**
	 * mongodb 数据库 敏感词过滤
	 * @param input
	 * @param word_list
	 * @return
	 */ 
	public static String replace_check(String input ,List<Document> word_list){
		
		if(word_list != null && word_list.size() > 0){
			
			String r_word = "***";
			
			for(int i=0;i<word_list.size();i++){
				//敏感词
				String word = word_list.get(i).getString("word");
				
				//包含敏感词
				if(input.contains(word)){
					 
					//替换
					input = input.replace(word, r_word);
				}
				
			} 
		}
		
		return input;
	}
	
	public static void main(String[] args){
		 
		
	}
}
