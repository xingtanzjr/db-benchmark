package com.thulab.read;

import com.thulab.data.template.DatabaseConfig;
import com.thulab.read.InfluxDBReader;
import com.thulab.read.IoTDBReader;
import org.junit.Test;

import java.sql.SQLException;

/**
 * Created by zhangjinrui on 2018/4/8.
 */
public class IoTDBReaderTest {

    @Test
    public void test() throws SQLException, ClassNotFoundException {
        DatabaseConfig dbconfig = new DatabaseConfig();
        dbconfig.setUrl("jdbc:tsfile://192.168.3.93:6667/");
        dbconfig.setUsername("root");
        dbconfig.setPasswd("root");
        dbconfig.setDatabase("zjr");
        IoTDBReader reader = IoTDBReader.getConn(dbconfig);

        long timeUsed = reader.query("select count(s_di) from root.zjr.fake.test.dev_d");
        System.out.println(timeUsed);
    }
}
