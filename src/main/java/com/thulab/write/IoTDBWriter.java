package com.thulab.write;

import cn.edu.tsinghua.tsfile.timeseries.write.record.DataPoint;
import com.thulab.data.Record;
import com.thulab.data.template.DatabaseConfig;
import com.thulab.data.template.FieldTemplate;
import com.thulab.data.template.MeasurementTemplate;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangjinrui on 2018/3/25.
 */
public class IoTDBWriter implements DBWriter {

    private Connection conn;
    private List<String> cachedSQL;
    private int batchSize = 5000;

    public IoTDBWriter() {

    }

    public IoTDBWriter(DatabaseConfig dbconfig, MeasurementTemplate template) throws ClassNotFoundException, SQLException {
        cachedSQL = new ArrayList<>();
        Class.forName("cn.edu.tsinghua.iotdb.jdbc.TsfileDriver");
        conn = DriverManager.getConnection(dbconfig.getUrl(), dbconfig.getUsername(), dbconfig.getPasswd());
        this.batchSize = template.getBatchSize();
        prepareSchema(template);
    }

    @Override
    public void write(Record record) throws SQLException {
        cachedSQL.add(genSQL(record));
        if (cachedSQL.size() >= batchSize) {
            Statement statement = conn.createStatement();
            for (String sql : cachedSQL) {
                statement.addBatch(sql);
            }
            statement.executeBatch();
            statement.close();
            cachedSQL.clear();
        }
    }

    @Override
    public void close() throws SQLException {
        conn.close();
    }

    private void prepareSchema(MeasurementTemplate template) throws SQLException {
        List<String> sqls = genSQLForSchema(template);
        Statement statement = conn.createStatement();
        for (String sql : sqls) {
            statement.addBatch(sql);
        }
        statement.executeBatch();
        statement.close();
    }

    public List<String> genSQLForSchema(MeasurementTemplate template) {
        String sqlTemplate = "create timeseries root%s with datatype=%s, encoding=RLE";
        StringBuilder prefix = new StringBuilder();
        for (String value : template.getTags().values()) {
            prefix.append(".").append(value);
        }
        prefix.append(".").append(template.getName());
        List<String> ret = new ArrayList<>();
        for (FieldTemplate fieldTemplate : template.getFieldTemplateList()) {
            ret.add(String.format(sqlTemplate, prefix + "." + fieldTemplate.getName(), fieldTemplate.getDataType()));
        }
        return ret;
    }

    public String genSQL(Record record) {
        StringBuilder stringBuilder = new StringBuilder("insert into root");
        for (String key : record.tags.keySet()) {
            stringBuilder.append(".").append(record.tags.get(key));
        }
        stringBuilder.append(".").append(record.measurement);

        stringBuilder.append("(timestamp");
        for (DataPoint dataPoint : record.fields) {
            stringBuilder.append(",").append(dataPoint.getMeasurementId());
        }
        stringBuilder.append(")");

        stringBuilder.append(" values(").append(record.timestamp);
        for (DataPoint dataPoint : record.fields) {
            stringBuilder.append(",").append(dataPoint.getValue());
        }
        stringBuilder.append(");");
        return stringBuilder.toString();
    }
}















