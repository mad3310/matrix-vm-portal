/*
 * @Title: ICacheManager.java
 * @Package com.letv.mms.cache.client
 * @Description: TODO
 * @author chenguang 
 * @date 2012-12-18 下午6:12:04
 * @version V1.0
 *
 * Modification History:  
 * Date         Author      Version     Description  
 * -------------------------------------------------------------- 
 * 2012-12-18                          
 */
package com.letv.mms.cache;


/**
 * CacheManager 统一接口
 * @author wenchu.cenwc<wenchu.cenwc@alibaba-inc.com>
 *
 */
public interface ICacheManager<T extends ICache<?,?>>
{
	/**
	 * 获取配置在memcached.xml中的Cache客户端
	 * @param name
	 * @return
	 */
	public T getCache(String name);
	
	
	/**
	 * 配置Cache的系统文件
	 * @param configFile
	 */
	public void setConfigFile(String configFile);
	
	/**
	 * 做一定的初始化工作
	 */
	public void start();
	
	/**
	 * 做资源回收工作
	 */
	public void stop();
	
	/**
	 * 重新载入Cache配置
	 * @param configFile
	 */
	public void reload(String configFile);
	
	/**
	 * 设置响应统计时间间隔(单位秒,默认为0,0表示不需要做响应统计)
	 * @param seconds
	 */
	public void setResponseStatInterval(int seconds);
	
}

