package org.smart4j.chapter2.util;

/**
 * Created by wangteng on 2019/5/29.
 */
public class CastUtil {

    public static String castString(Object obj) {
        return castString(obj, "");
    }

    public static String castString(Object obj, String defaultValue) {
        return obj != null ? String.valueOf(obj) : defaultValue;
    }

    public static double castDouble(Object obj) {
        return Double.parseDouble(castString(obj));
    }

    public static long castLong(Object obj) {
        return Long.parseLong(castString(obj));
    }

    public static int castInt(Object obj) {
        return Integer.parseInt(castString(obj));
    }

    public static boolean castBoolean(Object obj) {
        return Boolean.parseBoolean(castString(obj));
    }

}
