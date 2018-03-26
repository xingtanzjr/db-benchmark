import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.BatchPoints;
import org.influxdb.dto.Point;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Created by zhangjinrui on 2018/3/1.
 */
public class Main {

    public static String host = "192.168.3.92";
    public static String dbName = "zjr";
    public static String rpName = "aRetentionPolicy";
    public static final long TOTAL_POINT_COUNT = 1000000000;
    public static final int BATCH_SIZE = 10000;
    public static long timestamp;
    public static int value;

    public static void main(String args[]) {
        prepareDatabase();
        write();
    }

    public static void prepareDatabase() {
        InfluxDB influxDB = InfluxDBFactory.connect("http://" + host + ":8086", "root", "root");
        influxDB.createDatabase(dbName);
//        influxDB.createRetentionPolicy(rpName, dbName, "7d", "1d", 1, true);
        influxDB.close();
    }

    public static void query() {

    }

    public static void write() {
        timestamp = System.currentTimeMillis();
        Random rand = new Random();
        InfluxDB influxDB = InfluxDBFactory.connect("http://" + host + ":8086", "root", "root");
//        influxDB.enableBatch(10000, 5, TimeUnit.SECONDS);
        for (int i = 0; i < TOTAL_POINT_COUNT / BATCH_SIZE; i++) {
            BatchPoints batchPoints = genBatch(BATCH_SIZE);
            influxDB.write(batchPoints);
            if (i % 10 == 0) {
                System.out.println("Processed " + i * BATCH_SIZE);
            }
        }
        influxDB.close();
        System.out.println("Write done");
    }

    public static BatchPoints genBatch(int batchSize) {
        BatchPoints batchPoints = BatchPoints
                .database(dbName)
//                .retentionPolicy(rpName)
                .build();
        for (int i = 0; i < batchSize; i++) {
            batchPoints.point(genPoint(timestamp ++, value ++));
        }
        return batchPoints;
    }

    public static Point genPoint(long timestamp, int speed) {
        Point point = Point.measurement("cpu2")
                .time(timestamp, TimeUnit.MILLISECONDS)
                .tag("computer", "c1")
                .addField("speed", speed)
                .build();
        return point;
    }
}
