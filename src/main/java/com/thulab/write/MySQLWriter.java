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
 * Created by zhangjinrui on 2018/3/26.
 */
public class MySQLWriter implements DBWriter {

    private Connection conn;
    private List<String> cachedSQL;
    private int batchSize = 5000;
    private String database;

    public MySQLWriter() {

    }

    public MySQLWriter(DatabaseConfig dbconfig, MeasurementTemplate template) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        conn = DriverManager.getConnection(dbconfig.getUrl(), dbconfig.getUsername(), dbconfig.getPasswd());
        this.batchSize = template.getBatchSize();
        this.cachedSQL = new ArrayList<>();
        this.database = dbconfig.getDatabase();
        prepareSchema(dbconfig, template);
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

    private void prepareSchema(DatabaseConfig dbconfig, MeasurementTemplate template) {
        String sql = genSQLForSchema(dbconfig, template);
        Statement statement = null;
        try {
            statement = conn.createStatement();
            statement.addBatch(sql);
            statement.executeBatch();
            statement.close();
        } catch (SQLException e) {
            System.out.println("WARN:" + e.getMessage());
        }
    }

    public String genSQLForSchema(DatabaseConfig dbconfig, MeasurementTemplate template) {
        String createTableTemplate = "CREATE TABLE `%s`.`%s` (%s);";

        StringBuilder columnInfo = new StringBuilder("`timestamp` BIGINT(20) NOT NULL, ");
        for (FieldTemplate fieldTemplate : template.getFieldTemplateList()) {
            columnInfo.append(genColumnInfoForOneColumn(fieldTemplate.getName(), fieldTemplate.getDataType()));
        }
        columnInfo.append("PRIMARY KEY (`timestamp`)");
        return String.format(createTableTemplate, dbconfig.getDatabase(), template.getName(), columnInfo.toString());
    }

    private String genColumnInfoForOneColumn(String name, String type) {
        if (type.equals("INT64")) {
            return String.format("`%s` BIGINT(20) NULL, ", name);
        }
        if (type.equals("INT32")) {
            return String.format("`%s` INT NULL, ", name);
        }
        return String.format("`%s` %s NULL, ", name, type);
    }

    public String genSQL(Record record) {
        String sqlTemplate = "insert into %s.%s(%s) values(%s)";
        StringBuilder columnNames = new StringBuilder("timestamp");
        StringBuilder columnValues = new StringBuilder(String.valueOf(record.timestamp));

        int i = 0;
        for (DataPoint dataPoint : record.fields) {
            columnNames.append(",").append(dataPoint.getMeasurementId());
            columnValues.append(",").append(dataPoint.getValue());
        }
        return String.format(sqlTemplate, database, record.measurement, columnNames.toString(), columnValues.toString());
    }
}
