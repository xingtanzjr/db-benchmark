package com.thulab.read;

import com.thulab.data.template.DatabaseConfig;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.SQLException;

/**
 * Created by zhangjinrui on 2018/4/8.
 */
public class OpenTSDBReader extends DBReader {

    private String url;

    private OpenTSDBReader(DatabaseConfig dbconfig) {
        url = "http://" + dbconfig.getUrl() + "/api/query?";
    }

    @Override
    protected void executeQuery(String sql) throws SQLException {
        String[] args = sql.split(",");
        String start = args[0];
        String end = args[1];
        String m = args[2];
        StringBuilder param = new StringBuilder("start=").append(start).append("&");
        if (!end.equals("")) {
            param.append("end=").append(end).append("&");
        }
        param.append("m=").append(m);
        try {
            URL url = null;
            url = new URL(String.format("%s%s", url, param));
            URLConnection connection = url.openConnection();
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            br.close();
            System.out.println(sb.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static OpenTSDBReader getConn(DatabaseConfig dbconfig) {
        return new OpenTSDBReader(dbconfig);
    }
}
