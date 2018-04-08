package com.thulab.read;

import com.thulab.data.template.DatabaseConfig;
import org.junit.Test;

import java.sql.SQLException;

/**
 * Created by zhangjinrui on 2018/4/8.
 */
public class OpenTSDBReaderTest {

    @Test
    public void test() throws SQLException {
        DatabaseConfig dbconfig = new DatabaseConfig();
        dbconfig.setUrl("192.168.3.91:4242");
        DBReader dbReader = OpenTSDBReader.getConn(dbconfig);
        System.out.println(dbReader.query("start=1522028101&end=1623028101&m=sum:dev_c.s_cf"));
    }
}
