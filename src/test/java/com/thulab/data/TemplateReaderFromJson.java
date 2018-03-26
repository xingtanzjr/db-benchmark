package com.thulab.data;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thulab.data.template.*;
import org.junit.Test;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangjinrui on 2018/3/25.
 */
public class TemplateReaderFromJson {

    @Test
    public void test() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
//        MeasurementTemplate measurementTemplate = objectMapper.readValue(new File(""), MeasurementTemplate.class);
    }

    @Test
    public void writeToJson() throws JsonProcessingException {
        Map<String, String> tags = new LinkedHashMap<>();
        tags.put("l1", "china");
        tags.put("l2", "beijing");
        String[] names = new String[]{"temp", "wind"};
        String[] types = new String[]{"FLOAT", "INT32"};
        String[] lowerBounds = new String[]{"-10.0", "0"};
        String[] upperBounds = new String[]{"40", "10"};
        List<FieldTemplate> fieldTemplateList = FieldTemplateFactory.getTemplates(names, types, lowerBounds, upperBounds);
        MeasurementTemplate measurementTemplate = new MeasurementTemplate();
        measurementTemplate.setFieldTemplateList(fieldTemplateList);
        measurementTemplate.setName("haidian");
        measurementTemplate.setStartTimestamp(System.currentTimeMillis());
        measurementTemplate.setSeed(0);
        measurementTemplate.setTags(tags);
        DataSourceTemplate dataSourceTemplate = new DataSourceTemplate();
        dataSourceTemplate.addMeasurementTemplate(measurementTemplate);
        dataSourceTemplate.getDatabaseConfig().put(DatabaseType.INFLUXDB,
                new DatabaseConfig("url", "username", "passwd", "zjr"));
        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println(objectMapper.writeValueAsString(dataSourceTemplate));
    }
}
