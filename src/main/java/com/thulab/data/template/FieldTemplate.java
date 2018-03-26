package com.thulab.data.template;

/**
 * Created by zhangjinrui on 2018/3/25.
 */
public class FieldTemplate {
    private String name;
    private String dataType;
    private String lowerBound;
    private String upperBound;

    public FieldTemplate() {

    }

    public FieldTemplate(String name, String dataType, String lowerBound, String upperBound) {
        this.name = name;
        this.dataType = dataType;
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getLowerBound() {
        return lowerBound;
    }

    public void setLowerBound(String lowerBound) {
        this.lowerBound = lowerBound;
    }

    public String getUpperBound() {
        return upperBound;
    }

    public void setUpperBound(String upperBound) {
        this.upperBound = upperBound;
    }
}
