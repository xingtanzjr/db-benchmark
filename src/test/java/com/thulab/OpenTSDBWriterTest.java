package com.thulab;

import cn.edu.tsinghua.tsfile.timeseries.write.record.datapoint.FloatDataPoint;
import cn.edu.tsinghua.tsfile.timeseries.write.record.datapoint.IntDataPoint;
import com.thulab.data.FieldTemplateFactory;
import com.thulab.data.Record;
import com.thulab.data.template.DatabaseConfig;
import com.thulab.data.template.FieldTemplate;
import com.thulab.data.template.MeasurementTemplate;
import com.thulab.write.MySQLWriter;
import com.thulab.write.OpenTSDBWriter;
import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangjinrui on 2018/3/26.
 */
public class OpenTSDBWriterTest {

    @Test
    public void testGenSQL() {
        OpenTSDBWriter writer = new OpenTSDBWriter();
        Record record = new Record(100, "cpu")
                .addDataPoint(new IntDataPoint("speed", 10))
                .addDataPoint(new FloatDataPoint("hz", 1.5f))
                .addTag("location", "Beijing")
                .addTag("district", "haidian");
        record.database = "zjr";
        System.out.println(writer.genSQL(record));
    }
}
