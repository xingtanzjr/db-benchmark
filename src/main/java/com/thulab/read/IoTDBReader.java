package com.thulab.read;

import com.thulab.data.template.DatabaseConfig;

import java.sql.*;

/**
 * Created by zhangjinrui on 2018/4/8.
 */
public class IoTDBReader extends DBReader {

    private Connection conn;

    private IoTDBReader(DatabaseConfig dbconfig) throws ClassNotFoundException, SQLException {
        Class.forName("cn.edu.tsinghua.iotdb.jdbc.TsfileDriver");
        conn = DriverManager.getConnection(dbconfig.getUrl(), dbconfig.getUsername(), dbconfig.getPasswd());
    }

    @Override
    protected void executeQuery(String sql) throws SQLException {
        Statement stat = conn.createStatement();
        boolean hasResultSet = stat.execute(sql);
        if (!hasResultSet) {
            throw new SQLException("no result set for sql " + sql);
        }
        if (hasResultSet) {
            ResultSet res = stat.getResultSet();
            ResultSetMetaData metaData = res.getMetaData();
            int cnt = metaData.getColumnCount();
            while (res.next()) {

            }
        }
        stat.close();
    }

    public static IoTDBReader getConn(DatabaseConfig dbconfig) throws SQLException, ClassNotFoundException {
        return new IoTDBReader(dbconfig);
    }
}
