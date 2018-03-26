package com.thulab.write;

import cn.edu.tsinghua.tsfile.timeseries.write.record.DataPoint;
import com.thulab.data.Record;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.BatchPoints;
import org.influxdb.dto.Point;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by zhangjinrui on 2018/3/24.
 */
public class InfluxDBWriter implements DBWriter {

    private int batchSize = 5000;
    private InfluxDB influxDB;
    private List<Point> cachedPoint;
    private String database;

    public InfluxDBWriter(String url, String username, String passwd, String database) {
        influxDB = InfluxDBFactory.connect(url, "root", "root");
        cachedPoint = new ArrayList<>();
        this.database = database;
    }

    public InfluxDBWriter(String url, String username, String passwd, String database, int batchSize) {
        this(url, username, passwd, database);
        this.batchSize = batchSize;
    }

    public void write(Record record) {
        cachedPoint.add(transformRecord(record));
        if (cachedPoint.size() >= batchSize) {
            writeBatch();
            cachedPoint.clear();
        }
    }

    private void writeBatch() {
        BatchPoints batchPoints = BatchPoints
                .database(database)
                .build();
        for (Point point : cachedPoint) {
            batchPoints.point(point);
        }
        influxDB.write(batchPoints);
    }

    @Override
    public void close() {
        influxDB.close();
    }


    private Point transformRecord(Record record) {
        Map<String, Object> fieldsMap = new HashMap<>();
        for (DataPoint dataPoint : record.fields) {
            fieldsMap.put(dataPoint.getMeasurementId(), dataPoint.getValue());
        }

        Point point = Point.measurement(record.measurement)
                .time(record.timestamp, TimeUnit.MILLISECONDS)
                .fields(fieldsMap)
                .tag(record.tags)
                .build();
        return point;
    }
}
