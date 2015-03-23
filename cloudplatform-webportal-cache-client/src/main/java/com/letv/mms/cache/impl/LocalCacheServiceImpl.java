/*
 * @Title: LocalCacheServiceImpl.java
 * @Package com.letv.mms.cache
 * @Description: 缓存接口的本地缓存类型实现,实现了ConcurrentHashMap作为本地缓存的基本操作
 * @author 陈光 
 * @date 2012-12-5 下午4:43:10
 * @version V1.0
 *
 * Modification History:  
 * Date         Author      Version     Description  
 * -------------------------------------------------------------- 
 * 2012-12-5                          
 */
package com.letv.mms.cache.impl;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.letv.mms.cache.ICacheService;
import com.letv.mms.cache.IDBOperationService;

public class LocalCacheServiceImpl<T> implements ICacheService <T>{
	static Map<String, Object> map  = new ConcurrentHashMap<String, Object>();
	/**
	 * 缓存中添加数据
	 */
	public void set(String key, Object obj) {
		map.put(key, obj);
	}
	/**
	 * 缓存中添加数据 带过期时间
	 */
	public void set(String key, Object obj, Date expire) {
		throw new RuntimeException("the method not implementation!");
	}
	/**
	 * 缓存中取得数据
	 * @param <T>
	 */
	@SuppressWarnings("unchecked")
	public  T get(String key, IDBOperationService<T> dbOperationServiceImpl) {
		if (map.containsKey(key)) 
			return  (T) map.get(key);
		if(null == dbOperationServiceImpl)
			return null;
		return dbOperationServiceImpl.getDataFromDbByKey(key);
	}
	/**
	 * 删除缓存中数据
	 */
	public void delete(String key) {
		map.remove(key);
	}
	/**
	 * 更新缓存中数据
	 */
	public void update(String key, Object obj) {
		delete(key);
		set(key, obj);
	}
	
	public String toString() {
		return "Cache Data Count:" + map.size() + "  " + map.toString();
	}
	/**
	 * 根据缓存中key的前缀获得该类型的数据集合
	 */
	public List<T> getDataListByKeyprefix(String prefix,IDBOperationService<T> dbOperationServiceImpl){
		List<T> list = new ArrayList<T>();
		Iterator<String> iterator=map.keySet().iterator();
		while(iterator.hasNext()){
			String cacheKey = iterator.next();
			if(cacheKey.indexOf(prefix) != -1){
				T model = (T)get(cacheKey,null);
				if(null != model){
					list.add(model);
				}
			}
		}
		if(null == list || list.size() == 0){
			list = dbOperationServiceImpl.loadData();
		}
		return list;
	}
	/**
	 * 缓存初始化接口
	 */
	@Override
	public void init() {
		
	}
	/**
	 * 清空缓存中全部数据
	 */
	@Override
	public void removeAll() {
		map.clear();
	}
	@Override
	public void update(String key, Object obj, Date expire) {
		throw new RuntimeException("the method not implementation!");
	}

}