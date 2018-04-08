package com.thulab.read.benchmark;

import com.thulab.data.template.DatabaseType;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by zhangjinrui on 2018/4/8.
 */
public class QueryCmdReaderFileImpl implements QueryCmdReader {

    private BufferedReader bufferedReader;
    private String cmd = null;
    private boolean hasCachedCmd;

    public QueryCmdReaderFileImpl(String filePath) throws FileNotFoundException {
        bufferedReader = new BufferedReader(new FileReader(filePath));
    }

    @Override
    public boolean hasNext() throws IOException {
        if (!hasCachedCmd) {
            cmd = bufferedReader.readLine();
            hasCachedCmd = cmd != null;
        }
        if (cmd != null && cmd.trim().startsWith("#")) {
            hasCachedCmd = false;
            return hasNext();
        }
        return hasCachedCmd;
    }

    @Override
    public QueryCmd getNext() throws IOException {
        if (hasNext()) {
            String[] args = cmd.split("\\|");
            QueryCmd cmd = new QueryCmd();
            cmd.setType(DatabaseType.valueOf(args[0]));
            cmd.setSql(args[1].trim());
            cmd.setRepeatTime(Integer.valueOf(args[2]));
            hasCachedCmd = false;
            return cmd;
        }
        throw new IOException("no more cmds");
    }

    public void close() throws IOException {
        this.bufferedReader.close();
    }
}
