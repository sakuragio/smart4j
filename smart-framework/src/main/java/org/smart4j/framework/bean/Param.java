package org.smart4j.framework.bean;

import java.util.Map;

/**
 * Created by wangteng on 2019/5/31.
 */
public class Param {

    private Map<String, Object> paramMap;

    public Param(Map<String, Object> paramMap) {
        this.paramMap = paramMap;
    }

    public Map<String, Object> getParamMap() {
        return paramMap;
    }

}
