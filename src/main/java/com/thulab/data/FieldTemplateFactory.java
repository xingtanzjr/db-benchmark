package com.thulab.data;

import com.thulab.data.template.FieldTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangjinrui on 2018/3/25.
 */
public class FieldTemplateFactory {
    public static List<FieldTemplate> getTemplates(String[] names, String[] dataTypes, String[] lowwerBuound, String[] upperBound) {
        List<FieldTemplate> templateList = new ArrayList<>();
        int length = names.length;
        for (int i = 0; i < length; i++) {
            FieldTemplate template = new FieldTemplate(names[i], dataTypes[i], lowwerBuound[i], upperBound[i]);
            templateList.add(template);
        }
        return templateList;
    }
}
