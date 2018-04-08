package com.thulab.read;

import com.thulab.data.template.DatabaseConfig;
import com.thulab.read.InfluxDBReader;
import org.junit.Test;

import java.sql.SQLException;

/**
 * Created by zhangjinrui on 2018/4/8.
 */
public class InfluxDBReaderTest {

    @Test
    public void test() throws SQLException {
        DatabaseConfig dbconfig = new DatabaseConfig();
        dbconfig.setUrl("http://192.168.3.91:8086");
        dbconfig.setDatabase("zjr");
        InfluxDBReader reader = InfluxDBReader.getConn(dbconfig);

        long timeUsed = reader.query("select count(s_di) from dev_d");
        System.out.println(timeUsed);
    }
}
