package org.smart4j.chapter3.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by wangteng on 2019/5/30.
 */
public class PropUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(PropUtil.class);

    public static Properties loadProps(String fileName) {
        Properties prop = null;
        InputStream resourceAsStream = null;
        try {
            resourceAsStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
            if(resourceAsStream == null) {
                LOGGER.error("file not found: " + fileName);
                throw new FileNotFoundException(fileName + " file not found");
            }
            prop = new Properties();
            prop.load(resourceAsStream);
        } catch (IOException e) {
            LOGGER.error("load properties failure", e);
        } finally {
            if(resourceAsStream != null) {
                try {
                    resourceAsStream.close();
                } catch (IOException e) {
                    LOGGER.error("close input stream failure", e);
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
