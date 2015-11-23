/*
 * @Title: LoggerFactory.java
 * @Package com.letv.log
 * @Description: TODO
 * @author xufei1 <xufei1@letv.com>
 * @date 2012-12-9 下午3:00:47
 * @version V1.0
 *
 * Modification History:  
 * Date         Author      Version     Description  
 * -------------------------------------------------------------- 
 * 2012-12-9                          
 */
package com.letv.log;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.slf4j.Logger;
import com.letv.log.service.ILogService;
import com.letv.log.util.Configuration;

/**
 * <p>
 * 提供日志组件的单例工厂
 * </p>
 * 
 * @author xufei1 <xufei1@letv.com> Create at:2012-12-9 下午3:00:47
 */
public final class LoggerFactory {
	
	public static final String CONFIG_IMPL_NAME = "com.letv.log.implStyle";
	
	public static final String CLASS_PATH = "com.letv.log.service.impl.";
	
	public static final String BACK_SUFFIX = "ServiceImpl";
	
	private static String IMPL_CLASS_NAME;
	// log引入
	private static Logger log = org.slf4j.LoggerFactory.getLogger(LoggerFactory.class);
	private static LoggerFactory loggerFactory;

	/**
	 * <p>
	 * 类加载时初始化属性文件实例日志实现类
	 * </p>
	 * 
	 * @author xufei<xufei1@letv.com>
	 */
	public static ILogService getLogger(Class<?> implClazz) {
		loggerFactory = getInstance();
		String url = CLASS_PATH + IMPL_CLASS_NAME + BACK_SUFFIX;
		ILogService logger = null;
		try {
			Class<?> clazz = Class.forName(url);
			Constructor<?> con = clazz.getConstructor(Class.class);
			logger = (ILogService)con.newInstance(implClazz);
		} catch (InstantiationException e) {
			log.error(e.getMessage(),e);
		} catch (IllegalAccessException e) {
			log.error(e.getMessage(),e);
		} catch (SecurityException e) {
			log.error(e.getMessage(),e);
		} catch (NoSuchMethodException e) {
			log.error(e.getMessage(),e);
		} catch (IllegalArgumentException e) {
			log.error(e.getMessage(),e);
		} catch (InvocationTargetException e) {
			log.error(e.getMessage(),e);
		} catch (ClassNotFoundException e) {
			log.error(e.getMessage(),e);
		}
		
		return logger;
	}

	/**
	 * 限制对象个数
	 */
	private LoggerFactory() {}
	
	/**
	 * @author xufei<xufei1@letv.com>
	 *         <p>
	 *         提供单例对象的静态访问方法,双重验证单例
	 *         </p>
	 * 
	 * @return 单例
	 */
	private static LoggerFactory getInstance() {
		if (loggerFactory == null) {
			synchronized (LoggerFactory.class) {
				if (loggerFactory == null) {
					IMPL_CLASS_NAME = Configuration.getInstance().getValue(CONFIG_IMPL_NAME);
					loggerFactory = new LoggerFactory();
				}
			}
		}
		return loggerFactory;
	}

}
