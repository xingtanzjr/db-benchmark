package com.thulab.read.benchmark;

import com.thulab.data.template.DatabaseType;

/**
 * Created by zhangjinrui on 2018/4/8.
 */
public class QueryCmd {
    private DatabaseType type;
    private String sql;
    private int repeatTime;
    private long avgTimeUsed = -1;

    public DatabaseType getType() {
        return type;
    }

    public void setType(DatabaseType type) {
        this.type = type;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public int getRepeatTime() {
        return repeatTime;
    }

    public void setRepeatTime(int repeatTime) {
        this.repeatTime = repeatTime;
    }

    public long getAvgTimeUsed() {
        return avgTimeUsed;
    }

    public void setAvgTimeUsed(long avgTimeUsed) {
        this.avgTimeUsed = avgTimeUsed;
    }

    public String toString() {
        return String.format("%s,%s,%s,%s", type, sql, repeatTime, avgTimeUsed);
    }
}
