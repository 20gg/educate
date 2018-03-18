package com.hysm.tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class HttpUtil {
	//get请求
	public static String getUrl(String url){
        String result = null;
        try {
            // 根据地址获取请求
            HttpGet request = new HttpGet(url);
            HttpClient httpClient=null;
            try
            {
            	 // 获取当前客户端对象
                 httpClient = new DefaultHttpClient();
            }
            catch (Exception e) {
				e.printStackTrace();
			}
            
            // 通过请求对象获取响应对象
            HttpResponse response = httpClient.execute(request);
            
            // 判断网络连接状态码是否正常(0--200都数正常)
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                result= EntityUtils.toString(response.getEntity());
            } 
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }
	/**
     * 发起post请求并获取结果
     * 
     * @param Url 请求地址
     * @param json 请求json数据
     * 
     * 
     */
	public static String getPostUrl(String url,String json){
       
	     String responseContent = null; // 响应内容
        try {
           
           
             HttpPost  request =new HttpPost(url); // 根据地址获取请求
           
           
              StringEntity myEntity = new StringEntity(json,
	                     ContentType.APPLICATION_JSON);//构造请求数据
           
             request.setEntity(myEntity);
           
            HttpClient httpClient = new DefaultHttpClient();  
            HttpResponse response = httpClient.execute(request);
                
            // 判断网络连接状态码是否正常(0--200都数正常)     
             if (response.getStatusLine().getStatusCode()==HttpStatus.SC_OK){
                   
            	   HttpEntity entity = response.getEntity();
                  
                    responseContent = EntityUtils.toString(entity, "UTF-8");
                            return responseContent;
                       }
                   
                   
        }catch(Exception e){
        	
        }
        return null;          
    }
	

public static void main(String[] args){
	HttpUtil util=new HttpUtil();
	String str=util.getUrl("https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code");
	System.out.println(str);
}

}
