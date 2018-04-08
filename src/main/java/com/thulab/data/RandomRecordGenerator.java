package com.thulab.data;

import cn.edu.tsinghua.tsfile.file.metadata.enums.TSDataType;
import cn.edu.tsinghua.tsfile.timeseries.write.record.DataPoint;
import cn.edu.tsinghua.tsfile.timeseries.write.record.datapoint.DoubleDataPoint;
import cn.edu.tsinghua.tsfile.timeseries.write.record.datapoint.FloatDataPoint;
import cn.edu.tsinghua.tsfile.timeseries.write.record.datapoint.IntDataPoint;
import cn.edu.tsinghua.tsfile.timeseries.write.record.datapoint.LongDataPoint;
import com.thulab.data.template.FieldTemplate;
import com.thulab.data.template.MeasurementTemplate;

import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by zhangjinrui on 2018/3/25.
 */
public class RandomRecordGenerator implements RecordGenerator {
    private String measurement;
    private List<FieldTemplate> fieldList;
    private Map<String, String> tags;
    private long amount;
    private long currentCount;
    private long startTimestamp;
    private Random rand;

    public RandomRecordGenerator(MeasurementTemplate template) {
        this.measurement = template.getName();
        this.fieldList = template.getFieldTemplateList();
        this.amount = template.getAmount();
        this.startTimestamp = template.getStartTimestamp();
        this.rand = new Random(template.getSeed());
        this.tags = template.getTags();
        this.currentCount = 0;
    }

    @Override
    public boolean hasNext() {
        return currentCount < amount;
    }

    @Override
    public Record next() {
        Record record = new Record(startTimestamp, measurement);
        record.tags = tags;
        for (FieldTemplate template : fieldList) {
            record.addDataPoint(genDataPointByTemplate(template));
        }
        startTimestamp += 1000;
        currentCount++;
        return record;
    }

    @Override
    public long getTotalAmount() {
        return amount;
    }

    public DataPoint genDataPointByTemplate(FieldTemplate template) {
        TSDataType dataType = TSDataType.valueOf(template.getDataType());
        switch (dataType) {
            case INT32:
                int li = Integer.valueOf(template.getLowerBound());
                int ri = Integer.valueOf(template.getUpperBound());
                return new IntDataPoint(template.getName(), li + rand.nextInt(ri - li));
            case INT64:
                long ll = Long.valueOf(template.getLowerBound());
                long rl = Long.valueOf(template.getUpperBound());
                return new LongDataPoint(template.getName(), ll + (long) ((rl - ll) * rand.nextFloat()));
            case FLOAT:
                float lf = Float.valueOf(template.getLowerBound());
                float rf = Float.valueOf(template.getUpperBound());
                return new FloatDataPoint(template.getName(), lf + (rf - lf) * rand.nextFloat());
            case DOUBLE:
                double ld = Double.valueOf(template.getLowerBound());
                double rd = Double.valueOf(template.getUpperBound());
                return new DoubleDataPoint(template.getName(), ld + (rd - ld) * rand.nextDouble());
        }
        return null;
    }
}














