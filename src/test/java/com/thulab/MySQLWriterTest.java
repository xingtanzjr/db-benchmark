package com.thulab;

import cn.edu.tsinghua.tsfile.timeseries.write.record.datapoint.FloatDataPoint;
import cn.edu.tsinghua.tsfile.timeseries.write.record.datapoint.IntDataPoint;
import com.thulab.data.FieldTemplateFactory;
import com.thulab.data.Record;
import com.thulab.data.template.DatabaseConfig;
import com.thulab.data.template.FieldTemplate;
import com.thulab.data.template.MeasurementTemplate;
import com.thulab.write.MySQLWriter;
import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangjinrui on 2018/3/26.
 */
public class MySQLWriterTest {

    @Test
    public void testGenSQL() {
        MySQLWriter writer = new MySQLWriter();
        Record record = new Record(100, "cpu")
                .addDataPoint(new IntDataPoint("speed", 10))
                .addDataPoint(new FloatDataPoint("hz", 1.5f))
                .addTag("location", "Beijing")
                .addTag("district", "haidian");
        record.database = "zjr";
        System.out.println(writer.genSQL(record));
    }

    @Test
    public void testPrepareSchema() {
        Map<String, String> tags = new LinkedHashMap<>();
        tags.put("l1", "china");
        tags.put("l2", "beijing");
        String[] names = new String[]{"temp", "wind"};
        String[] types = new String[]{"FLOAT", "INT32"};
        String[] lowerBounds = new String[]{"-10.0", "0"};
        String[] upperBounds = new String[]{"40", "10"};
        List<FieldTemplate> fieldTemplateList = FieldTemplateFactory.getTemplates(names, types, lowerBounds, upperBounds);

        MeasurementTemplate measurementTemplate = new MeasurementTemplate();
        measurementTemplate.setName("haidian");
        measurementTemplate.setStartTimestamp(System.currentTimeMillis());
        measurementTemplate.setFieldTemplateList(fieldTemplateList);
        measurementTemplate.setTags(tags);
        measurementTemplate.setSeed(100L);
        measurementTemplate.setAmount(10);
        MySQLWriter writer = new MySQLWriter();
        System.out.println(writer.genSQLForSchema(new DatabaseConfig("", "", "", "zjr"), measurementTemplate));
    }
}
