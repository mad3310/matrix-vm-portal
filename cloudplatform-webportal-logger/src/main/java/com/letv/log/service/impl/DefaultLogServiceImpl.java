/*
 * @Title: DefaultLogServiceImpl.java
 * @Package com.letv.log.service.impl
 * @Description: TODO
 * @author xufei1 <xufei1@letv.com>
 * @date 2012-12-5 下午11:17:17
 * @version V1.0
 *
 * Modification History:  
 * Date         Author      Version     Description  
 * -------------------------------------------------------------- 
 * 2012-12-5                          
 */
package com.letv.log.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.letv.log.bean.LogBean;
import com.letv.log.service.ILogService;

/**
 * <p>
 * 默认的log实现：目前采用logback
 * </p>
 * 
 * @author xufei1 <xufei1@letv.com> Create at:2012-12-5 下午11:17:17
 */
public class DefaultLogServiceImpl implements ILogService {
	private Logger logger;
	
	public DefaultLogServiceImpl(Class<?> clazz)
	{
		logger = LoggerFactory.getLogger(clazz);
	}

	@Override
	public void info(LogBean log) {
		logger.info(log.toString());
	}

	@Override
	public void error(LogBean log) {
		logger.error(log.toString());
	}

	@Override
	public void debug(LogBean log) {
		logger.debug(log.toString());
	}
}