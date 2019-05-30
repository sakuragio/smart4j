package org.smart4j.chapter3.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by wangteng on 2019/5/30.
 */
public class StringUtil {

    public static String str(Object obj) {
        return str(obj, "");
    }

    public static String str(Object obj, String defaultValue) {
        if(obj == null) {
            return defaultValue;
        }
        return String.valueOf(obj);
    }

    public static boolean isEmpty(String str) {
        if(str != null) {
            str = str.trim();
        }
        return StringUtils.isEmpty(str);
    }

    public static boolean isNotEmpty(String str) {
        return ! isEmpty(str);
    }

}
