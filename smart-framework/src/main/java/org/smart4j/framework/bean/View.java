package org.smart4j.framework.bean;

import java.util.Map;

/**
 * Created by wangteng on 2019/5/31.
 */
public class View {

    private String path;

    private Map<String, Object> model;

    public View(String path, Map<String, Object> model) {
        this.path = path;
        this.model = model;
    }

    public String getPath() {
        return path;
    }

    public Map<String, Object> getModel() {
        return model;
    }
}
