package org.smart4j.framework.helper;

import org.smart4j.framework.annotation.Inject;
import org.smart4j.framework.util.ArrayUtil;
import org.smart4j.framework.util.CollectionsUtil;
import org.smart4j.framework.util.ReflectionUtil;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * Created by wangteng on 2019/5/31.
 */
public final class IocHelper {

    static {
        Map<Class<?>, Object> beanMap = BeanHelper.getBeanMap();
        if(CollectionsUtil.isNotEmpty(beanMap)) {
            for(Map.Entry<Class<?>, Object> beanEntry : beanMap.entrySet()) {
                Class<?> beanClass = beanEntry.getKey();
                Object beanInstance = beanEntry.getValue();
                Field[] fields = beanClass.getFields();
                if(ArrayUtil.isEmpty(fields)) {
                    continue;
                }
                for(Field field : fields) {
                    if(field.isAnnotationPresent(Inject.class)) {
                        Class<?> fieldClass = field.getType();
                        Object bean = BeanHelper.getBean(fieldClass);
                        if(bean != null) {
                            ReflectionUtil.setField(beanInstance, field, bean);
                        }
                    }
                }
            }
        }
    }

}
