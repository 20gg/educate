package com.hysm.tools;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class Jiami {

	public static void main(String[] args) {  
        try {  
            //需要加密的数据  
            String data = "GETcvm.api.qcloud.com/v2/index.php?Action=DescribeInstances&Nonce=11886&Region=gz&SecretId=AKIDz8krbsJ5yKBZQpn74WFkmLPx3gnPhESA&Timestamp=1465185768&instanceIds.0=ins-09dx96dg&limit=20&offset=0";  
            //密钥  
            String key = "Gu5t9xGARNpq86cd98joQYCN3Cozk1qA";  
            our(data,key);  
            standard(data, key);//没有用到    
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }
	 
	//http://1253790856.vod2.myqcloud.com/355e2b2dvodgzp1253790856/e0dc21e790318682229516
	
	public static String our(String data, String key){//用这个  
        byte[] b = HMACSHA1.getHmacSHA1(data, key);  
        String s = new BASE64Encoder().encode(b);  
       // System.out.println("hmacsha1 = " + s); 
        return s;
    } 
    
	public static String standard(String data, String key) {  
        byte[] byteHMAC = null;  
        try {  
            Mac mac = Mac.getInstance("HmacSHA1");  
            SecretKeySpec spec = new SecretKeySpec(key.getBytes(), "HmacSHA1");  
            mac.init(spec);  
            byteHMAC = mac.doFinal(data.getBytes());  
        } catch (InvalidKeyException e) {  
            e.printStackTrace();  
        } catch (NoSuchAlgorithmException ignore) {  
        }  
        String oauth = new BASE64Encoder().encode(byteHMAC);
        
        System.out.println("standard = "+oauth); 
        
        return oauth;
    }  
}
