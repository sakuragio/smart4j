package org.smart4j.framework.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by wangteng on 2019/5/31.
 */
public class PropsUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(PropsUtil.class);

    public static Properties loadProps(String path) {
        Properties prop = null;
        InputStream resourceAsStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
        if(resourceAsStream == null) {
            LOGGER.error("file not found:" + path);
            throw new RuntimeException("file not found:" + path);
        }
        try {
            prop.load(resourceAsStream);
        } catch (IOException e) {
            LOGGER.error("load input stream failure ", e);
            throw new RuntimeException(e);
        } finally {
            if(resourceAsStream != null) {
                try {
                    resourceAsStream.close();
                } catch (IOException e) {
                    LOGGER.error("close input stream failure ", e);
                    throw new RuntimeException(e);
                }
            }
        }
        return prop;
    }

    public static String getString(Properties prop, String key) {
        return getString(prop, key, "");
    }

    public static String getString(Properties prop, String key, String defaultValue) {
        return prop.getProperty(key, defaultValue);
    }

}
