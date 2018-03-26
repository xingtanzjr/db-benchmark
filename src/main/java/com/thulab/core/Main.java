package com.thulab.core;

import java.io.IOException;

/**
 * Created by zhangjinrui on 2018/3/26.
 */
public class Main {

    public static void main(String args[]) throws IOException {
        if (args.length < 1) {
            System.out.println("Please input config file Path as first argument");
            System.exit(1);
        }
        String filePath = args[0];
        Pipeline pipeline = new Pipeline(filePath);
        pipeline.start();
    }
}
