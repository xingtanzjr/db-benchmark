package com.thulab.data.template;

/**
 * Created by zhangjinrui on 2018/3/26.
 */
public class DatabaseConfig {
    private String url;
    private String username;
    private String passwd;
    private String database;

    public DatabaseConfig() {

    }

    public DatabaseConfig(String url, String username, String passwd, String database) {
        this.url = url;
        this.username = username;
        this.passwd = passwd;
        this.database = database;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }
}
