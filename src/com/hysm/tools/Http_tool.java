package com.hysm.tools;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.Document;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.hysm.db.Video_db;

 

public class Http_tool {

	 /**
     * 向指定URL发送GET方法的请求
     * 
     * @param url
     *            发送请求的URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendGet(String url, String param) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
           /* for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }*/
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }
    
    /**
     * 对请求加密
     * @param fileId
     * @return
     */
    public static Map<String,String> create_ask(String fileId){
    	
    	int Nonce = random_num();
    	String Timestamp = Timestamp();
    	
    	String parame = "Action="+QQ_code.Action+"&Nonce="+Nonce+"&Region="+QQ_code.Region+"&SecretId="+QQ_code.SecretId
    			+"&SignatureMethod="+QQ_code.SignatureMethod+"&Timestamp="+Timestamp+"&fileId="+fileId;
    	
    	String word = QQ_code.ask_way+QQ_code.ask_url+"?"+parame;
    	
    	String  Signature =  Jiami.our(word, QQ_code.SecretKey); 
    	
    	//String si2 =  Jiami.standard(word, QQ_code.SecretKey);
  
    	Map<String,String> map = new HashMap<String, String>();
    	
    	map.put("parame", parame);
    	map.put("Signature", Signature);
    
    	return map;
    			
    }
    
    public static Map<String,String> create_ask2(Document doc,String fileId){
    	
    	int Nonce = random_num();
    	String Timestamp = Timestamp();
    	
    	String parame = "Action="+doc.getString("Action")+"&Nonce="+Nonce+"&Region="+doc.getString("Region")+"&SecretId="+doc.getString("SecretId")
    			+"&SignatureMethod="+doc.getString("SignatureMethod")+"&Timestamp="+Timestamp+"&fileId="+fileId;
    	
    	String word = doc.getString("ask_way")+doc.getString("ask_url") +"?"+parame;
    	
    	String  Signature =  Jiami.our(word, doc.getString("SecretKey"));
    	
    	//String si2 =  Jiami.standard(word, doc.getString("SecretKey"));
    	
    	Map<String,String> map = new HashMap<String, String>();
    	
    	map.put("parame", parame);
    	map.put("Signature", Signature);
    
    	return map;
    }
    
   /* public static void main(String[] args) {
        //发送 GET 请求
       // String s =sendGet("https://vod.api.qcloud.com/v2/index.php", "Action=GetVideoInfo&fileId=9031868222951107222&COMMON_PARAMS");
        //System.out.println(s);
    	
    	 
    	Map<String,String> map = create_ask("9031868222975619668");
    	
    	while (map.get("Signature").contains("+") || map.get("Signature").contains("/")) {
			 
    		 map = create_ask("9031868223225775862");
		}
    	 
    	
    	String all_parame = map.get("parame")+"&Signature="+map.get("Signature");
    	
    	System.out.println(all_parame);
    	
    	String s =sendGet(QQ_code.https_url, all_parame);
    	 
       System.out.println(s);
        
       System.out.println( analysis_info(s).toString());
    	 
    }*/
    
    public static Map<String,Object> analysis_info(String str){
    	
    	Map<String,Object> map = new HashMap<String, Object>();
    	
    	try {
			JSONObject json = new JSONObject(str);
			
			int code = json.getInt("code");
			map.put("code", code);
			
			if(code == 0){
				//视频信息获取成功
				map.put("v_name", json.getJSONObject("basicInfo").getString("name"));
				map.put("v_size", json.getJSONObject("basicInfo").getInt("size"));
				map.put("time_long", json.getJSONObject("basicInfo").getInt("duration")); 
				map.put("status", json.getJSONObject("basicInfo").getString("status"));
				map.put("img", json.getJSONObject("basicInfo").getString("coverUrl")); 
				map.put("v_type", json.getJSONObject("basicInfo").getString("type"));
				map.put("sourceurl", json.getJSONObject("basicInfo").getString("sourceVideoUrl")); 
				
				//视频转码信息
				
				JSONArray js = json.getJSONObject("transcodeInfo").getJSONArray("transcodeList");
				
				List<Map<String,Object>> trans_list = new ArrayList<Map<String,Object>>();
				
				for(int i=0;i<js.length();i++){
					
					Map<String,Object> m = new HashMap<String, Object>();
					
					m.put("url", js.getJSONObject(i).getString("url"));
					m.put("definition", js.getJSONObject(i).getInt("definition"));
					
					trans_list.add(m);
				}
				
				map.put("trans_list", trans_list);
				
			}
			
			
		} catch (JSONException e){ 
			e.printStackTrace();
		}
    	
    	return map;
    }
     
    /**
     * 获取系统时间 秒值
     */
    public static String Timestamp(){ 
    	return String.valueOf(System.currentTimeMillis()/1000);
    }
    
    //获取5位随机数
    public static int random_num(){
    	
    	int num = (int)(Math.random() * 100000); 
    	if(num < 10000){
    		num = num +10000;
    	} 
    	return num;
    }
    
    public static Map<String,Object> http_video_info(Document doc,String fileId){
    	
    	Map<String,String> map = create_ask2(doc,fileId);
    	
    	while (map.get("Signature").contains("+") || map.get("Signature").contains("/")) {
			 
    		 map = create_ask2(doc,fileId);
		} 
    	
    	String all_parame = map.get("parame")+"&Signature="+map.get("Signature");
    	
    	String s =sendGet(QQ_code.https_url, all_parame);
   	  
        return analysis_info(s);
    }
    
    public static String http_video_json(Document doc,String fileId){
    	
    	Map<String,String> map = create_ask2(doc,fileId);
    	
    	while (map.get("Signature").contains("+") || map.get("Signature").contains("/")) {
			 
    		 map = create_ask2(doc,fileId);
		} 
    	
    	String all_parame = map.get("parame")+"&Signature="+map.get("Signature");
    	
    	String s =sendGet(QQ_code.https_url, all_parame);
   	  
        return s;
    }
    
    
    
    public static void main(String[] args) {
    	Document qq_code = new Video_db().query_qq_code();
    	
    	String back = Http_tool.http_video_json(qq_code, "9031868223390505942");
    	
    	System.out.println(back.toString());
	}

}
