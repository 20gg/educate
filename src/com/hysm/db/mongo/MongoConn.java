package com.hysm.db.mongo;

import com.mongodb.*;
import com.mongodb.client.MongoDatabase;

import java.util.Arrays;
import java.util.Map;

/**
 * Created by eldanote on 2017/2/9.
 */
public class MongoConn {

    private static String _connIP;
    private static String _user;
    private static String _database;
    private static String _password;
    private static int _port;

    private static String get_database() {
        PropertyUtil mp = new PropertyUtil();
        Map<String, String> Manualmaps = mp.dbconManual();
        if (Manualmaps.containsKey("database"))
            if (!Manualmaps.get("database").equals("")) {
                _database = Manualmaps.get("database");
            }
        return _database;
    }

    public static void set_database(String _database) {
        MongoConn._database = _database;
    }

    static MongoDatabase getConn() {
        String dburi = "";
        PropertyUtil mp = new PropertyUtil();
        Map<String, String> URImaps = mp.dbconURI();
        Map<String, String> Manualmaps = mp.dbconManual();
        _connIP= Manualmaps.get("ip");
        _port = Integer.valueOf(Manualmaps.get("port"));
        _user=Manualmaps.get("username");
        _password=Manualmaps.get("password");
        _database=Manualmaps.get("database");
        if (URImaps != null)
            dburi = URImaps.get("uri");
        try {
            MongoClientOptions options = getDefaultOptions();
            MongoClient _mongoClient;
            if (_user != null && !"".equals(_user)) {
                if (dburi!=null&&!"".equals(dburi)) {
                    MongoClientURI muri = new MongoClientURI(dburi);
                    _mongoClient = new MongoClient(muri);
                } else {
                    MongoCredential credential = MongoCredential.createCredential(_user, _database, _password.toCharArray());

                    _mongoClient = new MongoClient(new ServerAddress(_connIP, _port), Arrays.asList(credential),
                            options);
                }
            } else {
                _mongoClient = new MongoClient(new ServerAddress(_connIP, _port), options);
            }
            return _mongoClient.getDatabase(get_database());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void set_user(String _user) {
        MongoConn._user = _user;
    }

    public static void set_connIP(String _connIP) {
        MongoConn._connIP = _connIP;
    }

    public static void set_password(String _password) {
        MongoConn._password = _password;
    }

    public static void set_port(int _port) {
        MongoConn._port = _port;
    }

    private static MongoClientOptions getDefaultOptions() {
        return MongoClientOptions.builder()
                .sslEnabled(false)
                .build();
    }

}
