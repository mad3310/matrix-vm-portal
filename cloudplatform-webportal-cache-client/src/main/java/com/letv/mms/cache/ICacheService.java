/*
 * @Title: ICacheService.java
 * @Package com.letv.mms.cache
 * @Description: 定义缓存接口常用方法  存储、更新、删除、查询、初始化等
 * @author 陈光 
 * @date 2012-12-5 下午4:39:58
 * @version V1.0
 *
 * Modification History:  
 * Date         Author      Version        Description  
 * -------------------------------------------------------------- 
 * 2012-12-5                          
 * 2012-12-14   陈光       V1.0.1214    1.增加获得缓存中根据key前缀获取缓存中数据的方法
 * 													  2.变更get()方法,以及传入参数
 */
package com.letv.mms.cache;

import java.util.Date;
import java.util.List;

public interface ICacheService<T> {
	/**
	 * 将数据存放到缓存中
	 * @param obj
	 * @return
	 */
	public void set(String key,Object obj);
	/**
	 * 将数据存放到缓存中,可以设定失效时间
	 * @param obj
	 * @return
	 */
	public void set(String key,Object obj,Date expire);
	/**
	 * 从缓存中取得数据
	 * @param <T>
	 * @param <T>
	 * @param key
	 * @return
	 */
	public  T get(String key,IDBOperationService<T> dbOperationServiceImpl);
	/**
	 * 更新缓存中的数据
	 * @param obj
	 * @return
	 */
	public void update(String key,Object obj);
	
	/**
	 * 更新缓存中的数据,同时可以设置缓存失效时间
	 * @param key
	 * @param obj
	 * @param expire
	 */
	public void update(String key, Object obj, Date expire);
	
	/**
	 * 删除缓存中的数据
	 * @param key
	 * @return
	 */
	public void delete(String key);
	/**
	 * 获取取缓存中的key前缀的数据集合
	 */
	public List<T> getDataListByKeyprefix(String prefixl,IDBOperationService<T> dbOperationServiceImpl);
	/**
	 * 缓存初始化接口
	 */
	public void init();
	/**
	 * 清空缓存接口
	 */
	public void removeAll();
}
