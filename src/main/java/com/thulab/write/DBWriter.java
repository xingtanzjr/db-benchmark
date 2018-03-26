package com.thulab.write;

import com.thulab.data.Record;

import java.sql.SQLException;

/**
 * Created by zhangjinrui on 2018/3/24.
 */
public interface DBWriter {
    void write(Record record) throws SQLException;

    void close() throws SQLException;
}
