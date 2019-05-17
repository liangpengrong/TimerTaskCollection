package com.my.timertask.util.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class BeanFactoryUtils {
    private static ApplicationContext applicationContext;
    @Autowired
    public BeanFactoryUtils(ApplicationContext context) {
        applicationContext = context;
    }
    
    public static Object getBean(String bName) {
        Object bean = applicationContext.getBean(bName);
        return bean;
    }
    public static <T> T getBean(String bName, Class<T> cla) {
        Object bean = applicationContext.getBean(bName);
        if(cla.isInstance(bean)) {
            return (T)bean;
        }else {
            return null;
        }
    }
    public static <T> T getBean(Class<T> cla) {
        T bean = applicationContext.getBean(cla);
        return bean;
    }
}
