package com.thulab.data;

import com.thulab.data.template.DataSourceTemplate;
import com.thulab.data.template.TemplateFactory;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by zhangjinrui on 2018/3/25.
 */
public class TemplateFactoryTest {

    @Test
    public void test() throws IOException {
        DataSourceTemplate dataSourceTemplate = TemplateFactory.fromJsonFile("/Users/zhangjinrui/Desktop/Template.json");

    }
}
