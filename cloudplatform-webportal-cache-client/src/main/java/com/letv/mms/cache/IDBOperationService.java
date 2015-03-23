/*
 * @Title: IDBOperationService.java
 * @Package com.letv.mms.cache
 * @Description: 定义根据key从数据库中查询数据的方法
 * @author 陈光
 * @date 2012-12-12 下午2:57:49
 * @version V1.0
 *
 * Modification History:  
 * Date         Author      Version     Description  
 * -------------------------------------------------------------- 
 * 2012-12-12                          
 */
package com.letv.mms.cache;

import java.util.List;


public interface IDBOperationService <T>{
	public T getDataFromDbByKey(String key);
	public List<T> loadData();
}
