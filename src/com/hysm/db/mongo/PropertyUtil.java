package com.hysm.db.mongo;


import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by eldanote on 2017/2/9.
 */
public class PropertyUtil {

     Map<String, String> dbconURI() {
        Map<String, String> properties = new HashMap<String, String>();
        Properties prop = new Properties();
        try {
            FileInputStream fis = new FileInputStream(setPropertyFileName("jdbc"));
            prop.load(fis);
            String URI = prop.getProperty("dburi");
            properties.put("uri", URI);
            return properties;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Map<String, Object> getProperty() {
        Map<String, Object> properties = new HashMap<String, Object>();
        Properties prop = new Properties();
        try {
            FileInputStream fis = new FileInputStream(setPropertyFileName("jdbc"));
            prop.load(fis);
            String erpImageSrc = prop.getProperty("erpImageSrc");
            properties.put("erpImageSrc", erpImageSrc);
            return properties;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

     Map<String, String> dbconManual() {
        Map<String, String> properties = new HashMap<String, String>();
        Properties prop = new Properties();
        try {
            FileInputStream fis = new FileInputStream(setPropertyFileName("jdbc"));
            prop.load(fis);
            String mongoDbName = prop.getProperty("mongo.dbName");
            String ipadr = prop.getProperty("mongo.dbIp");
            String port = prop.getProperty("mongo.dbPort");
            String username = prop.getProperty("mongo.username");
            String password = prop.getProperty("mongo.password");
            properties.put("database", mongoDbName);
            properties.put("ip", ipadr);
            properties.put("port", port);
            properties.put("username", username);
            properties.put("password", password);
            return properties;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getURL() {
        return this.getClass().getResource("").getPath().replaceAll("%20", " ");
    }

    private String setPropertyFileName(String pName) {
        String  url=getURL(),
                path = "WEB-INF/classes/properties/",
                subfix = ".properties";

        return url.substring(0, getURL().indexOf("WEB-INF")) + path + pName + subfix;
    }
}
