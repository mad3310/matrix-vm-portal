/*
 * @Title: ILogService.java
 * @Package com.letv.log.service
 * @Description: TODO
 * @author xufei1 <xufei1@letv.com>
 * @date 2012-12-5 下午11:12:21
 * @version V1.0
 *
 * Modification History:  
 * Date         Author      Version     Description  
 * -------------------------------------------------------------- 
 * 2012-12-5                          
 */
package com.letv.log.service;

import com.letv.log.bean.LogBean;


/** 
 * <p></p>
 * 
 * @author xufei1 <xufei1@letv.com>
 * Create at:2012-12-5 下午11:12:21
 */
public interface ILogService {
	/**
	 * <p>该方法用于输出日志对象(默认为控制台)</p>
	 * @author xufei <xufei1@letv.com>
	 * @param log
	 */
	public void info(LogBean log);
	/**
	 * <p>该方法用于输出日志对象(默认为文件)</p>
	 * @author xufei <xufei1@letv.com>
	 * @param log
	 */
	public void error(LogBean log);
	/**
	 * <p>该方法用于输出日志对象debug级别</p>
	 * @author xufei <xufei1@letv.com>
	 * @param log
	 */
	public void debug(LogBean log);
}
