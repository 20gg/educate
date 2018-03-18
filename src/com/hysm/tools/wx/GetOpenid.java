package com.hysm.tools.wx;

import java.io.UnsupportedEncodingException;

import com.hysm.bean.MessageType;
import com.hysm.tools.HttpUtil;

import net.sf.json.JSONObject;

//根据code 获取 openid
public class GetOpenid
{
	
    private static String APPID = MessageType.appid;// 微信公众号下的AppID
    private static String SECRIET = MessageType.appsecret;// 微信公众号下的secret

    public static String getOpenid(String code)
    {
        String get_access_token_url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="
                + APPID
                + "&secret="
                + SECRIET
                + "&code="
                + code
                + "&grant_type=authorization_code";

        String json = HttpUtil.getUrl(get_access_token_url);
        try
        {
            json = new String(json.getBytes("ISO-8859-1"), "utf-8");

        }
        catch (UnsupportedEncodingException e)
        {

            e.printStackTrace();
        }

        JSONObject js = null;
        String openid = null;

        try
        {

            js = JSONObject.fromObject(json);

            openid = js.getString("openid");

        }
        catch (Exception e1)
        {

            return null;
        }

        return openid;

    }
    
    public static void main(String[] args) {
		System.out.println(GetOpenid.getOpenid("041qy5hz1W76O20RRGfz10r9hz1qy5hh"));
	}
}
