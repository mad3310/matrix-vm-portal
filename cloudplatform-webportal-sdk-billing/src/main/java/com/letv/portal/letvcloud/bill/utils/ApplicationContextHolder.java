package com.letv.portal.letvcloud.bill.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Created by chenliusong on 2015/6/19.
 */
public class ApplicationContextHolder  implements ApplicationContextAware{
    private static  ApplicationContext applicationContext = null;
    private static Logger logger = LoggerFactory.getLogger(ApplicationContextHolder.class);
    public  static <T> T getBean(String beanId){
        if (existApplicationContext()) {
            return (T) applicationContext.getBean(beanId);
        }
        return null;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ApplicationContextHolder.applicationContext = applicationContext;
    }


    private static boolean existApplicationContext(){
        if (applicationContext != null){
            return true;
        } else {
            logger.info("ApplicationContext is not be injection");
            return false;
        }
    }
}
