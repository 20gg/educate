package com.hysm.controller.Test_interface;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;


/**
 */
public class ApplicationBase {

	//默认配置的是UTF-8
	public static String encoding_UTF8 = "UTF-8";
	
	public static String encoding_GBK = "GBK";
	

//	public static final String MERCID="307110059990026";
//	public static final String PRIVATEKEY="MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAIX0GkKNyddRNiuBzBVG9grm42L2+uSfySaXapNx8w0n1q/WUEebh5/NGYNwXqA1xxs0rsLBerQhJwJFdSF/ud44cDHsUUfne03fqUS5WchMlhlJKGCnQzG8FYG1g8/+VW5w0aA4NCdn9Xnk57CnRAJZi36xgw9CllgV7zzl6WGVAgMBAAECgYBC4bhi1oya10+mtW4CiaW7l8Cc3xgyIp26iQuLhu+F+Ag3T1hSCJPU9ADzIZ39fBxntwwy7YuoeI0Tkz6CdVWFp8ofHD45jOL6ln04q/l00apCEZvS4WcxOZNGLE9VuYXVUQUaR2X4HkKFqpbL4hFLuSxcVVh8lmCKPYj1o9HwgQJBAL3LBh1mVXzortaA7/gX1L1ugcrhYd1kJS2UAm1S4bcWyQYTqwP86wnnPqNFhCWAtIwq2xcDfmzNBVVMDIPs0JECQQC0rnlaDkeG78GOoK2u0xIf5XRB4YFWVWHTnVXKHJrnkSVjBlie8RggktsQ17bNvD+vXa+HXw1IyGUvHH2gL8LFAkAgZDMB94IllEyqnUzXSbHsNpz+NCMQttPFiJo1cEzHg6MBX3/zaUnv4cCF+wpHoESwG2xS7Cy6gu8jFJyaM2ORAkB7BSifBoXw/CGfyQs4s4Htsb88dBwp+iyHduLIoD1gWdVI3TNN1P4Gf7ckept+7SO3dWRkGXXkDn3GW/G1NY+5AkAluFcmawIQmoo+WeIUzAQyWbwr0i5o+S5C1XgeVrPa/vdaxiMKAbq4ruZHGxaEaSxqkQDMb9ta2KB8zZ4+Mb6f";//上游平台提供
//	public static final String PUBLICKEY="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCF9BpCjcnXUTYrgcwVRvYK5uNi9vrkn8kml2qTcfMNJ9av1lBHm4efzRmDcF6gNccbNK7CwXq0IScCRXUhf7neOHAx7FFH53tN36lEuVnITJYZSShgp0MxvBWBtYPP/lVucNGgODQnZ/V55Oewp0QCWYt+sYMPQpZYFe885elhlQIDAQAB";
//	
	public static final String MERCID="483290072990797";
	public static final String PRIVATEKEY="MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAMIaJI1FKVY/5i3iiFAysWF3el0orIpb1PR81aQf2kLeY8NYe1G+Ek2+eRSkIvIslwuqxiNC4uArbB7BBaVON3K8Qotv5Y0NHIUrXSn41lwYDT6ko35H7NFgAPCkBZugE+bG1nQQBegKqVHBPQ4DHBWqJLOQj8ae4iu1kSacCieRAgMBAAECgYA/KiDA9EPKdIrkXba8WnLM5AOm80iK0e2QDlu7yD3Pyu0uMcoXxMHSGOzwIaXZp1cUDePir66DAr5xl6DNtZFnLIpd1xYt1NgBh2eNUwo7n/SGMYWMZucvHyQrL6MVMiC1aIuAYJQ9TjYsnaUv+UhVMloVgmt8QQ5mm/7ujytFwQJBAPB9PJmIvJgr7fCVzjTAA/KsdxEyHha9L5ZQWX7sZcUx6dydp0e+0dlUw8VUCNKnXwhZ74UVw6WCL894puwDPCkCQQDOnwABJ6//twd4Ue5MYYz4r96JyHl6bL3ohgKE5UwF55lj7VWfOueKm2bXsqYRQUW68royI+DB6R3gSp52qf0pAkEAvHo6m036GP0c31hZIyh2L6QRMS3pPQMKn7DbY0kcvb7PvXLlnqojx0PSpv2kaIx6Q45Q1SnoiveMRvsVP5WNeQJBAMR+ly2CBbEk6Zi2Ke4/kyXpcrQLj+MqSMw5rWme+lZfMxPB3/xms02wjfQxNBcSwPKR5RrL3fHq9PuZWuu0q0kCQE+O9mm9D96TzL82nDX5ZYSTWda1+oV73QKhxxttg73yvjvnGmFfyL0p8v6QJZbtpTDVM4gdEDj97MIEwtAg5cA=";//上游平台提供
	public static final String PUBLICKEY="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDCGiSNRSlWP+Yt4ohQMrFhd3pdKKyKW9T0fNWkH9pC3mPDWHtRvhJNvnkUpCLyLJcLqsYjQuLgK2wewQWlTjdyvEKLb+WNDRyFK10p+NZcGA0+pKN+R+zRYADwpAWboBPmxtZ0EAXoCqlRwT0OAxwVqiSzkI/GnuIrtZEmnAonkQIDAQAB";
	
	 
	public static final String CONSUMEURL="https://www.shenfupay.com:38443/SFQuickPayJH/v1.0/PublicConsume";
	public static final String QUERYTRADEURL="https://www.shenfupay.com:38443/SFQuickPayJH/QueryTrade";
	public static final String QUERYORDERURL="https://www.shenfupay.com:38443/SFQuickPayJH/qryOrder"; 
	public static final String OPENCARDURL="https://www.shenfupay.com:38443/SFQuickPayJH/PublicOpenCard"; 
	public static final String QUERYOPENCARDURL="https://www.shenfupay.com:38443/SFQuickPayJH/QueryCardOrder";
	public static final String MOBLIPHONE="https://www.shenfupay.com:38443/SFQuickPayJH/PublicGetSMSCode";  
	 
	  

	// 商户发送交易时间 格式:YYYYMMDDhhmmss
	public static String getCurrentTime() {
		return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
	}
	
	// AN8..40 商户订单号，不能含"-"或"_"
	public static String getOrderId() {
		return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
	}
	
	/*
	 * 参数名按ASCII码从小到大排序，使用”&“连接，参数名signature不参与拼接，
	 * */
	public static String coverMap2String(Map<String, String> data) {
		TreeMap<String, String> tree = new TreeMap<String, String>();
		Iterator<Entry<String, String>> it = data.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, String> en = it.next();
			if ("signature".equals(en.getKey().trim())) {
				continue;
			}
			tree.put(en.getKey(), en.getValue());
		}
		it = tree.entrySet().iterator();
		StringBuffer sf = new StringBuffer();
		while (it.hasNext()) {
			Entry<String, String> en = it.next();
			sf.append(en.getKey() + "=" + en.getValue() +"&");
		}
		return sf.substring(0, sf.length() - 1);
	}
  
}