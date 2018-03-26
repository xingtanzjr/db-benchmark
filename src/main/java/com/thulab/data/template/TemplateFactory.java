package com.thulab.data.template;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

/**
 * Created by zhangjinrui on 2018/3/25.
 */
public class TemplateFactory {
    public static DataSourceTemplate fromJsonFile(String filePath) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(new File(filePath), DataSourceTemplate.class);
    }
}
