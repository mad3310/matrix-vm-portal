/*
 * @Title: CacheFactory.java
 * @Package com.letv.mms.cache.factory
 * @Description: 缓存实现工厂,通过读取配置文件获得不同类型的缓存实例
 * @author  陈光
 * @date 2012-12-5 下午4:27:11
 * @version V1.0
 *
 * Modification History:  
 * Date         Author      Version     Description  
 * -------------------------------------------------------------- 
 * 2012-12-5                          
 */
package com.letv.mms.cache.factory;



import java.util.ResourceBundle;
import com.letv.common.exception.CommonException;
import com.letv.mms.cache.ICacheService;

public class CacheFactory  {
	private static ICacheService<?> cache;
	private static CacheFactory cacheFactory;
	public static final String CACHE_TYPE = "com.letv.cache.implStyle";
	public static final String CLASS_PATH = "com.letv.mms.cache.impl.";
	public static final String SUFFIX = "CacheServiceImpl";
	/**
	 * 保证不能通过构造方法创建实例
	 */
	private CacheFactory(){
		
	}
	/**
	 * 获得唯一的实例
	 * @return
	 */
	private static CacheFactory getInstance(){
		if(null == cacheFactory){
			synchronized (CacheFactory.class){
				if(null == cacheFactory){
					ResourceBundle bundl = ResourceBundle.getBundle("letvCacheConfig");
					String cacheType = bundl.getString(CACHE_TYPE);
					String path =  CLASS_PATH + cacheType + SUFFIX;
					try {
						cache = (ICacheService<?>) Class.forName(path).newInstance();
						cache.init();
					} catch (InstantiationException e) {
						throw new CommonException("反射实例异常",e);
					} catch (IllegalAccessException e) {
						throw new CommonException("访问安全异常",e);
					} catch (ClassNotFoundException e) {
						throw new CommonException("找不到类异常",e);
					}
				}
				cacheFactory = new CacheFactory();
			}
		}
		return cacheFactory;
	}
	/**
	 * 获取缓存实现
	 * @return
	 */
	public static ICacheService<?> getCache() {
		cacheFactory = getInstance();
		return CacheFactory.cache;
	}

}
