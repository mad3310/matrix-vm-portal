/*
 * @Title: MemcachedErrorHandler.java
 * @Package com.letv.mms.cache.client.memcached
 * @Description: TODO
 * @author chenguang 
 * @date 2012-12-18 下午6:27:33
 * @version V1.0
 *
 * Modification History:  
 * Date         Author      Version     Description  
 * -------------------------------------------------------------- 
 * 2012-12-18                          
 */
package com.letv.mms.cache.handler.impl;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.letv.mms.cache.handler.IErrorHandler;

import cache.memcached.main.MemCachedClient;

/**
 * 基本的出错处理
 * @author wenchu.cenwc<wenchu.cenwc@alibaba-inc.com>
 *
 */
public class MemcachedErrorHandler implements IErrorHandler
{
	private static final Log Logger = LogFactory.getLog(MemcachedErrorHandler.class);
	
	public void handleErrorOnDelete(MemCachedClient client, Throwable error,
			String cacheKey)
	{
		Logger.error(new StringBuilder("ErrorOnDelete, cacheKey: ")
				.append(cacheKey).toString(),error);
	}

	public void handleErrorOnFlush(MemCachedClient client, Throwable error)
	{
		Logger.error("ErrorOnFlush",error);
	}

	public void handleErrorOnGet(MemCachedClient client, Throwable error,
			String cacheKey)
	{
		Logger.error(new StringBuilder("ErrorOnGet, cacheKey: ")
			.append(cacheKey).toString(),error);
	}

	public void handleErrorOnGet(MemCachedClient client, Throwable error,
			String[] cacheKeys)
	{
		Logger.error(new StringBuilder("ErrorOnGet, cacheKey: ")
			.append(cacheKeys).toString(),error);
	}

	public void handleErrorOnInit(MemCachedClient client, Throwable error)
	{
		Logger.error("ErrorOnInit",error);
	}

	public void handleErrorOnSet(MemCachedClient client, Throwable error,
			String cacheKey)
	{
		Logger.error(new StringBuilder("ErrorOnSet, cacheKey: ")
			.append(cacheKey).toString(),error);
	}

	public void handleErrorOnStats(MemCachedClient client, Throwable error)
	{
		Logger.error("ErrorOnStats",error);
	}

}
