package com.thulab.data.template;

import com.thulab.data.template.FieldTemplate;

import java.util.List;
import java.util.Map;

/**
 * Created by zhangjinrui on 2018/3/25.
 */
public class MeasurementTemplate {

    private String name;
    private long startTimestamp;
    private List<FieldTemplate> fieldTemplateList;
    private Map<String, String> tags;
    private long amount;
    private long seed;
    private int batchSize;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getStartTimestamp() {
        return startTimestamp;
    }

    public void setStartTimestamp(long startTimestamp) {
        this.startTimestamp = startTimestamp;
    }

    public List<FieldTemplate> getFieldTemplateList() {
        return fieldTemplateList;
    }

    public void setFieldTemplateList(List<FieldTemplate> fieldTemplateList) {
        this.fieldTemplateList = fieldTemplateList;
    }

    public Map<String, String> getTags() {
        return tags;
    }

    public void setTags(Map<String, String> tags) {
        this.tags = tags;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public long getSeed() {
        return seed;
    }

    public void setSeed(long seed) {
        this.seed = seed;
    }

    public int getBatchSize() {
        return batchSize;
    }

    public void setBatchSize(int batchSize) {
        this.batchSize = batchSize;
    }
}
