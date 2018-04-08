package com.thulab.read.benchmark;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by zhangjinrui on 2018/4/8.
 */
public class ReadMain {
    public static void main(String args[]) throws SQLException, IOException, ClassNotFoundException {
        if (args.length < 3) {
            System.out.println("There should be 3 args: <datasourceFilePath> <cmdFilePath> <outputFilePath>");
            System.exit(0);
        }
        QueryWorker worker = new QueryWorker(args[0],args[1],args[2]);
        System.out.println("Starting...");
        worker.execute();
        System.out.println("Done");
    }
}
