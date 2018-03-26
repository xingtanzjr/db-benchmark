package com.thulab.data;

import com.thulab.data.template.FieldTemplate;
import com.thulab.data.template.MeasurementTemplate;
import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangjinrui on 2018/3/25.
 */
public class RandomRecordGeneratorTest {

    @Test
    public void test() {
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
        RecordGenerator recordGenerator = new RandomRecordGenerator(measurementTemplate);
        while (recordGenerator.hasNext()) {
            Record record = recordGenerator.next();
            System.out.println(record);
        }
    }
}
