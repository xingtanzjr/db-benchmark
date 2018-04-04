package com.thulab.core;

import com.thulab.data.RandomRecordGenerator;
import com.thulab.data.RecordGenerator;
import com.thulab.data.template.DatabaseConfig;
import com.thulab.data.template.DatabaseType;
import com.thulab.data.template.MeasurementTemplate;
import com.thulab.write.*;

import java.sql.SQLException;

/**
 * Created by zhangjinrui on 2018/3/26.
 */
public class Worker implements Runnable {

    private DatabaseType type;
    private DatabaseConfig dbconfig;
    private MeasurementTemplate template;

    private long currentCount = 0;

    public Worker(DatabaseType type, DatabaseConfig dbconfig, MeasurementTemplate template) {
        this.type = type;
        this.dbconfig = dbconfig;
        this.template = template;
    }

    @Override
    public void run() {
        RecordGenerator recordGenerator = new RandomRecordGenerator(template);
        try {
            DBWriter writer = genWriter(type);
            while (recordGenerator.hasNext()) {
                writer.write(recordGenerator.next());
                currentCount++;
                if (currentCount % 100000 == 0) {
                    System.out.println(type + " -> " + template.getName() + ": " + currentCount + " / " + recordGenerator.getTotalAmount());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private DBWriter genWriter(DatabaseType type) throws SQLException, ClassNotFoundException {
        switch (type) {
            case IOTDB:
                return new IoTDBWriter(dbconfig, template);
            case INFLUXDB:
                return new InfluxDBWriter(dbconfig.getUrl(), dbconfig.getUsername(), dbconfig.getPasswd(), dbconfig.getDatabase());
            case MYSQL:
                return new MySQLWriter(dbconfig, template);
            case OPENTSDB:
                return new OpenTSDBWriter(dbconfig);
            default:
                return null;
        }
    }
}
