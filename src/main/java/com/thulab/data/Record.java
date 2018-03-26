package com.thulab.data;

import cn.edu.tsinghua.tsfile.timeseries.write.record.DataPoint;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangjinrui on 2018/3/24.
 */
public class Record {
    public String database;
    public String measurement;
    public long timestamp;
    public List<DataPoint> fields;
    public Map<String, String> tags;

    public Record(long timestamp, String measurement) {
        fields = new ArrayList<>();
        tags = new LinkedHashMap<>();
        this.timestamp = timestamp;
        this.measurement = measurement;
    }

    public Record addDataPoint(DataPoint dataPoint) {
        fields.add(dataPoint);
        return this;
    }

    public Record addTag(String key, String value) {
        tags.put(key, value);
        return this;
    }

    public static class Field {
        String key;
        String value;
    }

    public static class Tag {
        String key;
        String value;
    }

    public String toString() {

        StringBuilder stringBuilder = new StringBuilder("root");
        for (String value : tags.values()) {
            stringBuilder.append(".").append(value);
        }
        stringBuilder.append(".").append(measurement).append(",\t").append(timestamp).append(",\t");
        for (DataPoint dataPoint : fields) {
            stringBuilder.append(dataPoint.getMeasurementId()).append("=").append(dataPoint.getValue());
            stringBuilder.append(",\t");
        }
        return stringBuilder.toString();
    }
}
