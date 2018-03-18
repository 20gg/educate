package com.hysm.wxpay.utils;


import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import com.hysm.wxpay.utils.http.HttpClientConnectionManager;

public class GetWxOrderno
{
  public static DefaultHttpClient httpclient;

  static
  {
    httpclient = new DefaultHttpClient();
    httpclient = (DefaultHttpClient)HttpClientConnectionManager.getSSLInstance(httpclient);
  }


  /**
   *description:获取预支付id
   *@param url
   *@param xmlParam
   *@return
   * @author ex_yangxiaoyi
   * @see
   */
  public static String getPayNo(String url,String xmlParam){
	  DefaultHttpClient client = new DefaultHttpClient();
	  client.getParams().setParameter(ClientPNames.ALLOW_CIRCULAR_REDIRECTS, true);
	  HttpPost httpost= HttpClientConnectionManager.getPostMethod(url);
	  String prepay_id = "";
     try {
		 httpost.setEntity(new StringEntity(xmlParam, "UTF-8"));
		 HttpResponse response = httpclient.execute(httpost);
	     String jsonStr = EntityUtils.toString(response.getEntity(), "UTF-8");
	 
	    if(jsonStr.indexOf("FAIL")!=-1){
	    	return prepay_id;
	    }
	    
	    Map map = doXMLParse(jsonStr);
	    String err_code=(String)map.get("err_code");
	    
	    if(err_code!=null&&err_code.equals("ORDERPAID")){
	    	
	    	return "ORDERPAID";
	    }
	    prepay_id  = (String) map.get("prepay_id");
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return prepay_id;
  }
  
  public static Map getPayInfo(String url,String xmlParam){
	  Map map=null;
	  DefaultHttpClient client = new DefaultHttpClient();
	  client.getParams().setParameter(ClientPNames.ALLOW_CIRCULAR_REDIRECTS, true);
	  HttpPost httpost= HttpClientConnectionManager.getPostMethod(url);
	  String prepay_id = "";
     try {
		 httpost.setEntity(new StringEntity(xmlParam, "UTF-8"));
		 HttpResponse response = httpclient.execute(httpost);
	     String jsonStr = EntityUtils.toString(response.getEntity(), "UTF-8");
	     
	     
	     if(jsonStr.indexOf("FAIL")!=-1){
	    	
	    }
	    map = doXMLParse(jsonStr);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return map;
  }
  public static Map getPayInfo2(String url,String xmlParam){
	  Map map=null;
	  try {
	  KeyStore keyStore  = KeyStore.getInstance("PKCS12");
      FileInputStream instream = new FileInputStream(new File("/D:/apiclient_cert.p12"));//P12文件目录
      try {
          keyStore.load(instream, "1219554501".toCharArray());//这里写密码..默认是你的MCHID
      } finally {
          instream.close();
      }

      // Trust own CA and all self-signed certs
      SSLContext sslcontext = SSLContexts.custom()
              .loadKeyMaterial(keyStore, "1219554501".toCharArray())//这里也是写密码的
              .build();
      // Allow TLSv1 protocol only
      SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
              sslcontext,
              new String[] { "TLSv1" },
              null,
              SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
      CloseableHttpClient httpclient = HttpClients.custom()
              .setSSLSocketFactory(sslsf)
              .build();
	 
	  DefaultHttpClient client = new DefaultHttpClient();
	  client.getParams().setParameter(ClientPNames.ALLOW_CIRCULAR_REDIRECTS, true);
	  HttpPost httpost= HttpClientConnectionManager.getPostMethod(url);
	
     
		 httpost.setEntity(new StringEntity(xmlParam, "UTF-8"));
		 HttpResponse response = httpclient.execute(httpost);
	     String jsonStr = EntityUtils.toString(response.getEntity(), "UTF-8");
	     if(jsonStr.indexOf("FAIL")!=-1){
	    	
	    }
	    map = doXMLParse(jsonStr);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return map;
  }
  /**
   *description:获取扫码支付连接
   *@param url
   *@param xmlParam
   *@return
   * @author ex_yangxiaoyi
   * @see
   */
  public static String getCodeUrl(String url,String xmlParam){
	  DefaultHttpClient client = new DefaultHttpClient();
	  client.getParams().setParameter(ClientPNames.ALLOW_CIRCULAR_REDIRECTS, true);
	  HttpPost httpost= HttpClientConnectionManager.getPostMethod(url);
	  String code_url = "";
     try {
		 httpost.setEntity(new StringEntity(xmlParam, "UTF-8"));
		 HttpResponse response = httpclient.execute(httpost);
	     String jsonStr = EntityUtils.toString(response.getEntity(), "UTF-8");
	    if(jsonStr.indexOf("FAIL")!=-1){
	    	return code_url;
	    }
	    Map map = doXMLParse(jsonStr);
	    code_url  = (String) map.get("code_url");
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return code_url;
  }
  
  public static String doRefund(String url,String data){
  	try{
      KeyStore keyStore  = KeyStore.getInstance("PKCS12");
      FileInputStream instream = new FileInputStream(new File("/D:/apiclient_cert.p12"));//P12文件目录
      try {
          keyStore.load(instream, "1219554501".toCharArray());//这里写密码..默认是你的MCHID
      } finally {
          instream.close();
      }

      // Trust own CA and all self-signed certs
      SSLContext sslcontext = SSLContexts.custom()
              .loadKeyMaterial(keyStore, "1219554501".toCharArray())//这里也是写密码的
              .build();
      // Allow TLSv1 protocol only
      SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
              sslcontext,
              new String[] { "TLSv1" },
              null,
              SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
      CloseableHttpClient httpclient = HttpClients.custom()
              .setSSLSocketFactory(sslsf)
              .build();
      
      
      	
//      	HttpPost httpost = new HttpPost(url); // 设置响应头信息
//      	httpost.addHeader("Connection", "keep-alive");
//      	httpost.addHeader("Accept", "*/*");
//      	httpost.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf8");
//      	httpost.addHeader("Host", "api.mch.weixin.qq.com");
//      	httpost.addHeader("X-Requested-With", "XMLHttpRequest");
//      	httpost.addHeader("Cache-Control", "max-age=0");
//      	httpost.addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.0) ");
//  		httpost.setEntity(new StringEntity(data, "UTF-8"));
//          CloseableHttpResponse response = httpclient.execute(httpost);
      	 DefaultHttpClient client = new DefaultHttpClient();
     	  client.getParams().setParameter(ClientPNames.ALLOW_CIRCULAR_REDIRECTS, true);
     	  HttpPost httpost= HttpClientConnectionManager.getPostMethod(url);
        
          	 httpost.setEntity(new StringEntity(data, "UTF-8"));
      		 HttpResponse response = httpclient.execute(httpost);
      	     String jsonStr = EntityUtils.toString(response.getEntity(), "UTF-8");
      	     if(jsonStr.indexOf("FAIL")!=-1){
      	    	
      	  
            
         
      	     } 
      	     return jsonStr;
  	}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
  }
  
  /**
	 * 解析xml,返回第一级元素键值对。如果第一级元素有子节点，则此节点的值是子节点的xml数据。
	 * @param strxml
	 * @return
	 * @throws JDOMException
	 * @throws IOException
	 */
	public static Map doXMLParse(String strxml) throws Exception {
		if(null == strxml || "".equals(strxml)) {
			return null;
		}
		
		Map m = new HashMap();
		InputStream in = String2Inputstream(strxml);
		SAXBuilder builder = new SAXBuilder();
		Document doc = builder.build(in);
		Element root = doc.getRootElement();
		List list = root.getChildren();
		Iterator it = list.iterator();
		while(it.hasNext()) {
			Element e = (Element) it.next();
			String k = e.getName();
			String v = "";
			List children = e.getChildren();
			if(children.isEmpty()) {
				v = e.getTextNormalize();
			} else {
				v = getChildrenText(children);
			}
			
			m.put(k, v);
		}
		
		//关闭流
		in.close();
		
		return m;
	}
	/**
	 * 获取子结点的xml
	 * @param children
	 * @return String
	 */
	public static String getChildrenText(List children) {
		StringBuffer sb = new StringBuffer();
		if(!children.isEmpty()) {
			Iterator it = children.iterator();
			while(it.hasNext()) {
				Element e = (Element) it.next();
				String name = e.getName();
				String value = e.getTextNormalize();
				List list = e.getChildren();
				sb.append("<" + name + ">");
				if(!list.isEmpty()) {
					sb.append(getChildrenText(list));
				}
				sb.append(value);
				sb.append("</" + name + ">");
			}
		}
		
		return sb.toString();
	}
  public static InputStream String2Inputstream(String str) {
		return new ByteArrayInputStream(str.getBytes());
	}
  
  
  public static void main(String[] args) {
	Map s=null;
	try {
		s = new GetWxOrderno().doXMLParse("<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg><appid><![CDATA[wxbedd9f6ba44c9ec5]]></appid><mch_id><![CDATA[1219554501]]></mch_id><nonce_str><![CDATA[flVGHIzK9m5ZHAc5]]></nonce_str><sign><![CDATA[71894910E5D6963A0CA6D156D6962F00]]></sign><result_code><![CDATA[FAIL]]></result_code><err_code><![CDATA[ORDERNOTEXIST]]></err_code><err_code_des><![CDATA[订单不存在]]></err_code_des></xml>");
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
  System.out.println(s.toString());
  }
}