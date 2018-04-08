package com.thulab.read;

import com.thulab.data.template.DatabaseConfig;
import org.influxdb.InfluxDB;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by zhangjinrui on 2018/4/8.
 */
public class InfluxDBReader extends DBReader {

    private InfluxDB influxDB;
    private String database;

    private InfluxDBReader(String url, String username, String passwd, String database) {
        influxDB = MyInfluxDBFactory.connect(url, "root", "root", 3600, TimeUnit.SECONDS);
        this.database = database;
    }

    @Override
    protected void executeQuery(String sql) throws SQLException {
        QueryResult queryResult = influxDB.query(new Query(sql, database));
        List<QueryResult.Result> results = queryResult.getResults();
        for (int i = 0; i < results.size(); i++) {
            for (QueryResult.Series series : results.get(i).getSeries()) {
//                System.out.println(series.getColumns());
                for (int j = 0; j < series.getValues().size(); j++) {
//                    System.out.println(series.getValues().get(j));
                }
            }
        }
    }

    public static InfluxDBReader getConn(DatabaseConfig dbconfig) {
        return new InfluxDBReader(dbconfig.getUrl(), dbconfig.getUsername(), dbconfig.getPasswd(), dbconfig.getDatabase());
    }
}
