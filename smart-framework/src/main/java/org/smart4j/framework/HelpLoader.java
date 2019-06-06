package org.smart4j.framework;

import org.smart4j.framework.annotation.Controller;
import org.smart4j.framework.helper.*;
import org.smart4j.framework.util.ClassUtil;

/**
 * Created by wangteng on 2019/5/31.
 */
public class HelpLoader {

    public static void init() {
        Class<?>[] classes = {
                BeanHelper.class,
                ClassHelper.class,
                ConfigHelper.class,
                ControllerHelper.class,
                IocHelper.class
        };

        for(Class cls : classes) {
            ClassUtil.loadClass(cls.getName(), true);
        }
    }

}
