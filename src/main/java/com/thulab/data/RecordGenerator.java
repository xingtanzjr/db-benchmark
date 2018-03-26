package com.thulab.data;

/**
 * Created by zhangjinrui on 2018/3/25.
 */
public interface RecordGenerator {

    boolean hasNext();

    Record next();

    long getTotalAmount();

}
