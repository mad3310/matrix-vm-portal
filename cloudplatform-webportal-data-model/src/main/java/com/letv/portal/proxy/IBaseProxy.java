package com.letv.portal.proxy;

import java.util.List;
import java.util.Map;

import com.letv.common.paging.impl.Page;

public interface IBaseProxy<T> {
	
	/**Methods Name: selectPageByParams <br>
	 * Description:	根据查询条件查询分页数据<br>
	 * Detail:根据传入的page（currentPage，recordsPerPage）及parmas查询相关数据。
	 * @author name: liuhao1
	 * @param page
	 * @param params
	 * @return
	 */
	<K, V> Page selectPageByParams(int currentPage,int recordsPerPage,Map<K,V> params);
	<K, V> Page selectPageByParams(int currentPage,int recordsPerPage,Map<K,V> params,String orderBy,Boolean isAsc);
	
	/**
	 * 插入记录
	 * @return
	 * @throws Exception
	 */
	void insert(T t);
	
	/**
	 * 修改记录
	 * @return
	 * @throws Exception
	 */
	 void update(T t);
	
	/**
	 * 修改记录，至修改不为空的字段
	 * @param t
	 * @throws Exception
	 */
	void updateBySelective(T t) ;
	
	/**
	 * 根据id删除记录
	 * @param ids
	 * @throws Exception
	 */
	void delete(T t);
	/**
	 * 根据id查找记录记录，返回单条记录
	 * @param Id
	 * @return
	 * @throws Exception
	 */
	T selectById(Long id);
	
	/**
	 * 根据model查询总数
	 * @param Id
	 * @return
	 * @throws Exception
	 */
	Integer selectByModelCount(T t);
	
	/**
	 * 根据map查询总数
	 * @param Id
	 * @return
	 * @throws Exception
	 */
	<K,V> Integer selectByMapCount(Map<K,V>  map);
	
	/**
	 * 根据map查询list记录，分页,默认10条
	 * @param Id
	 * @return
	 * @throws Exception
	 */
	List<T> selectByModel(T t);
	
	/**
	 * 根据map查询list记录，不分页。
	 * @param Id
	 * @return
	 * @throws Exception
	 */
	<K,V> List<T> selectByMap(Map<K,V>  map);
	
}
