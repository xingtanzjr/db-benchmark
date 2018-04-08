package com.thulab.read;

import com.thulab.read.benchmark.QueryCmdReader;
import com.thulab.read.benchmark.QueryCmdReaderFileImpl;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by zhangjinrui on 2018/4/8.
 */
public class QueryCmdReaderTest {

    @Test
    public void test() throws IOException {
        QueryCmdReader reader = new QueryCmdReaderFileImpl("/Users/zhangjinrui/Desktop/db-benchmark/querycmd.txt");
        while(reader.hasNext()) {
            System.out.println(reader.getNext());
        }
    }
}
