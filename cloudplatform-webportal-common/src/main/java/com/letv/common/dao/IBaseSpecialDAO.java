package com.letv.common.dao;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.letv.common.paging.IPage;

public interface IBaseSpecialDAO {
	
	public <K,V> Map<K,V> queryByPageWithMap(final String statement, 
			final Map<String,Object> params, 
			final String mapKey, 
			final IPage page);
	
	public <T> List<T> queryByPageWithList (final String statement, 
			final Map<String,Object> params, 
			final IPage page);
	
	public <T> Iterator<T> queryByPageWithIterator(final String statement, 
			final Map<String,Object> params, 
			final IPage page);
	
	public <K,V> Map<K,V> queryByPageWithMap(final String countStatement, 
			final String statement, 
			final Map<String,Object> params, 
			final String mapKey, 
			final IPage page);
	
	public <T> List<T> queryByPageWithList (final String countStatement, 
			final String statement,
			final Map<String,Object> params, 
			final IPage page);
	
	public <T> Iterator<T> queryByPageWithIterator(final String countStatement, 
			final String statement, 
			final Map<String,Object> params, 
			final IPage page);
	
//	public <T> List<T> executeQueryBySql(Class<?> clazz, final String sql, final IPage page, final Map<String, ?> map);
	public <T> List<T> queryBySqlAndPageWithList(Class<?> clazz, final String sql, final IPage page);
}
