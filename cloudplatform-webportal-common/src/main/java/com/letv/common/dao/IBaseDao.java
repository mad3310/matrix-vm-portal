package com.letv.common.dao;

import java.util.List;
import java.util.Map;

import com.letv.common.dao.QueryParam;

public interface IBaseDao<T> {
	 
	public void insert(T t);
	
	public void update(T t);
	
	public void updateBySelective(T t);
	
	public void delete(T t);
	
	public void deleteFlag(Long id);
	
	public T selectById(Long id);
	
	public Integer selectByModelCount(T  t);
	
	public <K,V> Integer selectByMapCount(Map<K,V>  map);
	
	public List<T> selectByModel(T t);
	
	public <K,V> List<T> selectByMap(Map<K,V>  map);
	
	
	/**Methods Name: selectPageByMap <br>
	 * Description: 获取分页数据<br>
	 * @author name: liuhao1
	 * @param params
	 * @return
	 */
	public List<T> selectPageByMap(QueryParam params);
	
	/**
	  * @Title: selectByMapCount
	  * @Description: 列表查询时，根据条件查询所有记录数（定义此方法是为了和selectPageByMap使用相同的参数）
	  * @param params
	  * @return Integer   
	  * @throws 
	  * @author lisuxiao
	  * @date 2015年7月27日 上午11:41:04
	  */
	public <K,V> Integer selectByMapCount(QueryParam params);
	
}
