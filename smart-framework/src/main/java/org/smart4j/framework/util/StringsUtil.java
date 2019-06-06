package org.smart4j.framework.util;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by wangteng on 2019/5/31.
 */
public class StringsUtil {

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
