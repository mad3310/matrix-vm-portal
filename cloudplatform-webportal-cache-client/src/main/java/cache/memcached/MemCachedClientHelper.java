/*
 * @Title: MemCachedClientHelper.java
 * @Package com.letv.mms.cache.client.memcached
 * @Description: TODO
 * @author chenguang 
 * @date 2012-12-18 下午6:26:08
 * @version V1.0
 *
 * Modification History:  
 * Date         Author      Version     Description  
 * -------------------------------------------------------------- 
 * 2012-12-18                          
 */
package cache.memcached;



import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cache.memcached.main.MemCachedClient;

import com.letv.mms.cache.IMemcachedCache;
import com.letv.mms.cache.impl.MemcachedCacheManagerImpl;

/**
 * 为封装的MemCache提供实际处理的帮助类
 * @author wenchu.cenwc<wenchu.cenwc@alibaba-inc.com>
 *
 */
public class MemCachedClientHelper
{
	private static final Log Logger = LogFactory.getLog(MemCachedClientHelper.class);
	private MemCachedClient cacheClient;
	private MemcachedCacheManagerImpl cacheManager;
	private IMemcachedCache memcachedCache;
	private String cacheName;
	
	
	public MemCachedClient getInnerCacheClient()
	{
		if (cacheClient == null)
		{
			Logger.error("cacheClient can't be injected into MemcachedCacheHelper");
			throw new RuntimeException("cacheClient can't be injected into MemcachedCacheHelper");
		}
		
		return cacheClient;
	}
	
	public MemCachedClient getCacheClient(String key)
	{
		if (cacheClient == null)
		{
			Logger.error("cacheClient can't be injected into MemcachedCacheHelper");
			throw new RuntimeException("cacheClient can't be injected into MemcachedCacheHelper");
		}
		
		return cacheClient;
	}

	public void setCacheClient(MemCachedClient cacheClient)
	{
		this.cacheClient = cacheClient;
	}

	public MemcachedCacheManagerImpl getCacheManager()
	{
		return cacheManager;
	}

	public void setCacheManager(MemcachedCacheManagerImpl cacheManager)
	{
		this.cacheManager = cacheManager;
	}

	public IMemcachedCache getMemcachedCache()
	{
		return memcachedCache;
	}

	public void setMemcachedCache(IMemcachedCache memcachedCache)
	{
		this.memcachedCache = memcachedCache;
	}

	public String getCacheName()
	{
		return cacheName;
	}

	public void setCacheName(String cacheName)
	{
		this.cacheName = cacheName;
	}


}
