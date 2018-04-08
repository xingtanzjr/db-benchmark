package com.thulab.read.benchmark;

import java.io.IOException;

/**
 * Created by zhangjinrui on 2018/4/8.
 */
public interface QueryCmdReader {

    boolean hasNext() throws IOException;

    QueryCmd getNext() throws IOException;
}
