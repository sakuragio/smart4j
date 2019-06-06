package org.smart4j.framework.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * Created by wangteng on 2019/5/31.
 */
public class CodecUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(CodecUtil.class);

    public static String encodeURL(String source) {
        String target;
        try {
            target = URLEncoder.encode(source, "UTF-8");
            return target;
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("encode source failure", e);
            throw new RuntimeException(e);
        }
    }

    public static String decodeURL(String source) {
        String target;
        try {
            target = URLDecoder.decode(source, "UTF-8");
            return target;
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("decode source failure", e);
            throw new RuntimeException(e);
        }
    }

}
