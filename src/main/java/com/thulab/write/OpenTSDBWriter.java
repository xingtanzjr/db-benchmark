package com.thulab.write;

import cn.edu.tsinghua.tsfile.timeseries.write.record.DataPoint;
import com.thulab.data.Record;
import com.thulab.data.template.DatabaseConfig;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.sql.SQLException;

/**
 * Created by zhangjinrui on 2018/3/29.
 */
public class OpenTSDBWriter implements DBWriter {

    private Socket socket;
    private BufferedWriter bw;

    public OpenTSDBWriter() {

    }

    public OpenTSDBWriter(DatabaseConfig dbconfig) throws SQLException {
        try {
            String[] hostport = dbconfig.getUrl().split(":");
            socket = new Socket(hostport[0], Integer.valueOf(hostport[1]));
            bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (IOException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public void write(Record record) throws SQLException {
        String sqls = genSQL(record);
        try {
            bw.write(sqls);
        } catch (IOException e) {
            throw new SQLException(e);
        }
    }

    public String genSQL(Record record) {
        StringBuilder sqls = new StringBuilder();
        String sqlTemplate = "put %s %s %s %s";
        for (DataPoint dataPoint : record.fields) {
            String metrics = new StringBuilder().append(record.measurement).append(".")
                    .append(dataPoint.getMeasurementId()).toString();
            StringBuilder tags = new StringBuilder();
            for (String key : record.tags.keySet()) {
                tags.append(key).append("=").append(record.tags.get(key)).append(" ");
            }
            sqls.append(String.format(sqlTemplate, metrics, record.timestamp, dataPoint.getValue(), tags));
            sqls.append("\n");
        }
        return sqls.toString();
    }

    @Override
    public void close() throws SQLException {
        try {
            socket.close();
        } catch (IOException e) {
            throw new SQLException(e);
        }
    }
}
