package com.thulab.read;

import com.thulab.data.template.DatabaseConfig;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by zhangjinrui on 2018/4/8.
 */
public class OpenTSDBReader extends DBReader {

    private String url;
    private HttpClient client;

    private OpenTSDBReader(DatabaseConfig dbconfig) {
        url = "http://" + dbconfig.getUrl() + "/api/query?";
        client = HttpClients.createDefault();
    }

    @Override
    protected void executeQuery(String sql) throws SQLException {
        HttpGet get = new HttpGet(String.format("%s%s", url, sql));
        try {
            HttpResponse response = client.execute(get);
            HttpEntity entity = response.getEntity();
            String result = EntityUtils.toString(entity, "UTF-8");
//            System.out.println(result);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static OpenTSDBReader getConn(DatabaseConfig dbconfig) {
        return new OpenTSDBReader(dbconfig);
    }
}
