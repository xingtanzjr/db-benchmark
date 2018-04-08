package com.thulab.read.benchmark;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thulab.data.template.DataSourceTemplate;
import com.thulab.data.template.DatabaseConfig;
import com.thulab.data.template.DatabaseType;
import com.thulab.read.DBReader;
import com.thulab.read.InfluxDBReader;
import com.thulab.read.IoTDBReader;
import com.thulab.read.OpenTSDBReader;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhangjinrui on 2018/4/8.
 */
public class QueryWorker {

    private Map<DatabaseType, DBReader> dbReaderMap;
    private QueryCmdReaderFileImpl cmdReader;
    private BufferedWriter output;

    public QueryWorker(String configFilePath, String cmdfilePath, String outputFilePath)
            throws IOException, SQLException, ClassNotFoundException {
        dbReaderMap = new HashMap<>();
        ObjectMapper objectMapper = new ObjectMapper();
        DataSourceTemplate dataSourceTemplate = objectMapper.readValue(new File(configFilePath), DataSourceTemplate.class);
        for (DatabaseType type : dataSourceTemplate.getDatabaseConfig().keySet()) {
            addADBReader(type, dataSourceTemplate.getDatabaseConfig().get(type));
        }
        cmdReader = new QueryCmdReaderFileImpl(cmdfilePath);
        output = new BufferedWriter(new FileWriter(outputFilePath));
    }

    public void execute() throws IOException {
        while (cmdReader.hasNext()) {
            QueryCmd cmd = cmdReader.getNext();
            try {
                System.out.println("start execute -> " + cmd);
                cmd.setAvgTimeUsed(calAvgUsedTime(cmd));
                output.write(formatOutput(cmd));
                output.newLine();
                output.flush();
            } catch (SQLException e) {
                output.write("Error: " + cmd);
                e.printStackTrace();
            }
        }
        output.close();
        cmdReader.close();
    }

    private String formatOutput(QueryCmd cmd) {
        StringBuilder sb = new StringBuilder();
        sb.append(cmd.getType()).append(",")
                .append(cmd.getSql().replace(',', ' ')).append(",")
                .append(cmd.getRepeatTime()).append(",")
                .append(cmd.getAvgTimeUsed());
        return sb.toString();
    }

    private long calAvgUsedTime(QueryCmd cmd) throws SQLException {
        long total = 0;
        for (int i = 1; i <= cmd.getRepeatTime(); i++) {
            System.out.print("\r");
            System.out.print(String.format("%s/%s", i, cmd.getRepeatTime()));
            total += dbReaderMap.get(cmd.getType()).query(cmd.getSql());
        }
        System.out.println();
        return total / cmd.getRepeatTime();
    }

    private void addADBReader(DatabaseType type, DatabaseConfig dbconfig) throws SQLException, ClassNotFoundException {
        switch (type) {
            case IOTDB:
                dbReaderMap.put(type, IoTDBReader.getConn(dbconfig));
                break;
            case INFLUXDB:
                dbReaderMap.put(type, InfluxDBReader.getConn(dbconfig));
                break;
            case OPENTSDB:
                dbReaderMap.put(type, OpenTSDBReader.getConn(dbconfig));
                break;
        }
    }
}
