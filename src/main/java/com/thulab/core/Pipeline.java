package com.thulab.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thulab.data.template.DataSourceTemplate;
import com.thulab.data.template.DatabaseType;
import com.thulab.data.template.MeasurementTemplate;

import java.io.File;
import java.io.IOException;

/**
 * Created by zhangjinrui on 2018/3/26.
 */
public class Pipeline {

    private DataSourceTemplate dataSourceTemplate;

    public Pipeline(String configFilePath) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        dataSourceTemplate = objectMapper.readValue(new File(configFilePath), DataSourceTemplate.class);
    }

    public void start() {
        for(MeasurementTemplate template : dataSourceTemplate.getTemplateList()) {
            startOneMeasurementTemplate(template);
        }
    }

    private void startOneMeasurementTemplate(MeasurementTemplate template) {
        for(DatabaseType type : dataSourceTemplate.getDatabaseConfig().keySet()) {
            Worker worker = new Worker(type, dataSourceTemplate.getDatabaseConfig().get(type), template);
            Thread thread = new Thread(worker);
            thread.start();
            System.out.println(type + " -> " + template.getName() + " starts.");
        }
    }
}
