package com.dq.holder;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * * @Author: RyouA
 * * @Date: 2020/8/30
 **/
public class ApplicationContextHolder implements ApplicationContextAware {
    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        ApplicationContextHolder.context = context;
    }

    public static Object getBeanByName(String beanName) {
        if (beanName == null || context == null) {
            return null;
        }
        return context.getBean(beanName);
    }

    public static Object getBeanByType(Class clazz) {
        if (clazz == null || context == null) {
            return null;
        }
        return context.getBean(clazz);
    }

    public static String[] getBeanDefinitionNames() {
        return context.getBeanDefinitionNames();
    }
}
