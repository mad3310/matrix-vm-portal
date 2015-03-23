/*
 * @Title: RemoteCacheServiceImpl.java
 * @Package com.letv.mms.cache
 * @Description: TODO
 * @author chenguang 
 * @date 2012-12-19 下午3:15:55
 * @version V1.0
 *
 * Modification History:  
 * Date         Author      Version     Description  
 * -------------------------------------------------------------- 
 * 2012-12-19                          
 */
package com.letv.mms.cache.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.Callable;

import cache.memcached.CacheUtil;

import com.letv.mms.cache.ICacheManager;
import com.letv.mms.cache.ICacheService;
import com.letv.mms.cache.IDBOperationService;
import com.letv.mms.cache.IMemcachedCache;
import com.letv.mms.cache.retry.IRetryHandler;
import com.letv.mms.cache.retry.Retry;
import com.letv.mms.cache.retry.ThrowOnFailureRetryHandler;

public class RemoteCacheServiceImpl <T> implements ICacheService <T>{
	private IMemcachedCache cache;
	
	private String file;
	
	private String client;
	
	private IRetryHandler<String> retryHandler;
	
	public RemoteCacheServiceImpl(){
		ResourceBundle rb = ResourceBundle.getBundle("letvCacheConfig");
		setClient(rb.getString("com.letv.cache.implMemcachedClienteName"));
		setFile(rb.getString("com.letv.cache.implConfigFileName"));
		
		retryHandler = new ThrowOnFailureRetryHandler<String>();
	}
	
	public IMemcachedCache getCache() {
		return cache;
	}
	
	public void setCache(IMemcachedCache cache) {
		this.cache = cache;
	}
	
	public String getFile() {
		return file;
	}
	
	public void setFile(String file) {
		this.file = file;
	}
	
	public String getClient() {
		return client;
	}
	
	public void setClient(String client) {
		this.client = client;
	}
	
	@Override
	public void set(String key, Object obj) {
		Retry.retry(new SetOper(key,obj), retryHandler, key);
	}

	@Override
	public void set(String key, Object obj,Date expire) {
		Retry.retry(new SetOper(key,obj,expire), retryHandler, key);
	}
	
	private class SetOper implements Callable<T>
	{
		private String key;
		private Object obj;
		private Date expire;
		
		public SetOper(String key,Object obj)
		{
			this(key,obj,null);
		}
		
		public SetOper(String key,Object obj, Date expire)
		{
			this.key = key;
			this.obj = obj;
			this.expire = expire;
		}
		
		@Override
		public T call() throws Exception {
			if (cache.containsKey(key)) {
				cache.replace(key, obj,expire);
			} else {
				cache.add(key, obj,expire);
			}
			return null;
		}
	}

	
	@Override
	public T get(String key, IDBOperationService<T> dbOperationServiceImpl) {
		return Retry.retry(new GetOper(key,dbOperationServiceImpl), retryHandler, key);
	}
	
	private class GetOper implements Callable<T>
	{
		private String key;
		private IDBOperationService<T> dbOperationServiceImpl;
		
		public GetOper(String key, IDBOperationService<T> dbOperationServiceImpl){
			this.key = key;
			this.dbOperationServiceImpl = dbOperationServiceImpl;
		}
		
		@SuppressWarnings("unchecked")
		@Override
		public T call() throws Exception {
			if(cache.containsKey(key))
				return (T) cache.get(key);
			if(null == dbOperationServiceImpl)
				return null;
			return (T)dbOperationServiceImpl.getDataFromDbByKey(key);
		}
	}

	@Override
	public void update(String key, Object obj) {
		Retry.retry(new UpdateOper(key,obj), retryHandler, key);
	}
	
	@Override
	public void update(String key, Object obj, Date expire) {
		Retry.retry(new UpdateOper(key,obj,expire), retryHandler, key);
	}
	
	private class UpdateOper implements Callable<T>
	{
		private String key;
		private Object obj;
		private Date expire;
		
		public UpdateOper(String key,Object obj)
		{
			this(key, obj, null);
		}
		
		public UpdateOper(String key,Object obj,Date expire)
		{
			this.key = key;
			this.obj = obj;
			this.expire = expire;
		}
		
		@Override
		public T call() throws Exception {
			if (cache.containsKey(key)) {
				cache.replace(key, obj, expire);
			} else {
				cache.add(key, obj, expire);
			}
			return null;
		}
	}

	@Override
	public void delete(String key) {
		Retry.retry(new DeleteOper(key), retryHandler, key);
	}
	
	private class DeleteOper implements Callable<T>
	{
		private String key;
		
		public DeleteOper(String key)
		{
			this.key = key;
		}
		
		@SuppressWarnings("unchecked")
		@Override
		public T call() throws Exception {
			return (T) cache.remove(key);
		}
	}

	@Override
	public List<T> getDataListByKeyprefix(String prefixl,IDBOperationService<T> dbOperationServiceImpl) {
		List<T> list = new ArrayList<T>();
		Iterator<String> iterator=cache.keySet().iterator();
		while(iterator.hasNext()){
			String cacheKey = iterator.next();
			if(cacheKey.indexOf(prefixl) != -1){
				T model = (T)get(cacheKey,null);
				if(null != model){
					list.add(model);
				}
			}
		}
		if(list.isEmpty()){
			list = dbOperationServiceImpl.loadData();
		}
		return list;
	}
	public String toString(){
		return "远程缓存总记录数:"+cache.keySet().size()+"  远程key集合:"+cache.keySet();
	}
	/**
	 * 缓存初始化接口
	 */
	@Override
	public void init() {
		ICacheManager<IMemcachedCache> manager = CacheUtil.getCacheManager(IMemcachedCache.class,MemcachedCacheManagerImpl.class.getName());
		manager.setConfigFile(getFile());
		manager.setResponseStatInterval(5*1000);
		manager.start();
		setCache(manager.getCache(getClient()));
	}
	/**
	 * 清空缓存数据接口
	 */
	@Override
	public void removeAll() {
		cache.clear();
	}
}
