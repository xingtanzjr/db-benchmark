package com.thulab.data.template;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import com.thulab.data.template.DatabaseType;

/**
 * Created by zhangjinrui on 2018/3/25.
 */
public class DataSourceTemplate {

    private List<MeasurementTemplate> templateList;
    private Map<DatabaseType, DatabaseConfig> databaseConfig;

    public DataSourceTemplate() {
        this.templateList = new ArrayList<>();
        this.databaseConfig = new LinkedHashMap<>();
    }

    public List<MeasurementTemplate> getTemplateList() {
        return templateList;
    }

    public void setTemplateList(List<MeasurementTemplate> templateList) {
        this.templateList = templateList;
    }

    public void addMeasurementTemplate(MeasurementTemplate template) {
        this.templateList.add(template);
    }


    public Map<DatabaseType, DatabaseConfig> getDatabaseConfig() {
        return databaseConfig;
    }

    public void setDatabaseConfig(Map<DatabaseType, DatabaseConfig> databaseConfig) {
        this.databaseConfig = databaseConfig;
    }
}
