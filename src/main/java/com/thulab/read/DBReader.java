package com.thulab.read;

import java.sql.SQLException;

/**
 * Created by zhangjinrui on 2018/4/8.
 */
public abstract class DBReader {
    public long query(String sql) throws SQLException {
        long startTimestamp = System.currentTimeMillis();
        executeQuery(sql);
        long endTimestamp = System.currentTimeMillis();
        return (endTimestamp - startTimestamp);
    }

    protected abstract void executeQuery(String sql) throws SQLException;
}
