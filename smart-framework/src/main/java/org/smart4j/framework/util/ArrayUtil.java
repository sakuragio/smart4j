package org.smart4j.framework.util;

import org.apache.commons.lang3.ArrayUtils;

/**
 * Created by wangteng on 2019/5/31.
 */
public class ArrayUtil {

    public static boolean isEmpty(Object[] objects) {
        return ArrayUtils.isEmpty(objects);
    }

    public static boolean isNotEmpty(Object[] objects) {
        return ! isEmpty(objects);
    }

}
