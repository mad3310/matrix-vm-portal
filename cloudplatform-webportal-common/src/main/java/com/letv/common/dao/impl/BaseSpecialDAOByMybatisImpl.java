package com.letv.common.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.mybatis.spring.support.SqlSessionDaoSupport;

import com.letv.common.dao.IBaseSpecialDAO;
import com.letv.common.dao.Limit;
import com.letv.common.dao.QueryParam;
import com.letv.common.exception.CommonException;
import com.letv.common.exception.ValidateException;
import com.letv.common.paging.IPage;
import com.letv.common.util.CommonUtil;

public class BaseSpecialDAOByMybatisImpl extends SqlSessionDaoSupport implements IBaseSpecialDAO{

	@Override
	public <K,V> Map<K,V> queryByPageWithMap(
			final String statement,
			final Map<String,Object> params, 
			final String mapKey,
			final IPage page)
	{
		QueryParam queryParam = new QueryParam();
		queryParam.setParams(params);
		
		Limit limit = new Limit(0);
		
		if(null == page)
			queryParam.setLimit(limit);
		else
		{
			Map<String,Object> mapCount = getSqlSession().selectOne(statement, queryParam);
			String countStr = String.valueOf(mapCount.get("count(1)"));
			page.setTotalRecords(Integer.valueOf(countStr));
			
			limit.setStart(page.getStartRowPosition());
	        if (page.getRecordsPerPage() > 0)
	        	limit.setMaxRows(page.getRecordsPerPage());
	        queryParam.setLimit(limit);
		}
        
		Map<K,V> map = getSqlSession().selectMap(statement, queryParam, mapKey);
		return map;
	}

	@Override
	public <T> List<T> queryByPageWithList(final String statement,
			final Map<String, Object> params, final IPage page) {
		QueryParam queryParam = new QueryParam();
		queryParam.setParams(params);
		
		Limit limit = new Limit(0);
		
		if(null == page)
			queryParam.setLimit(limit);
		else
		{
			Map<String, Object> mapCount = getSqlSession().selectOne(statement, queryParam);
			if(!mapCount.isEmpty())
			{
				String countStr = String.valueOf(mapCount.get("count(1)"));
				page.setTotalRecords(Integer.valueOf(countStr));
			}
			
			limit.setStart(page.getStartRowPosition());
	        if (page.getRecordsPerPage() > 0)
	        	limit.setMaxRows(page.getRecordsPerPage());
	        queryParam.setLimit(limit);
		}
        
		List<T> lists = getSqlSession().selectList(statement, queryParam);
		return lists;
	}

	@Override
	public <T> Iterator<T> queryByPageWithIterator(
			final String statement, final Map<String, Object> params, final IPage page) {
		QueryParam queryParam = new QueryParam();
		queryParam.setParams(params);
		
		Limit limit = new Limit(0);
		
		if(null == page)
			queryParam.setLimit(limit);
		else
		{
			Map<String, Object> mapCount = getSqlSession().selectOne(statement, queryParam);
			if(!mapCount.isEmpty())
			{
				String countStr = String.valueOf(mapCount.get("count(1)"));
				page.setTotalRecords(Integer.valueOf(countStr));
			}
			
			limit.setStart(page.getStartRowPosition());
	        if (page.getRecordsPerPage() > 0)
	        	limit.setMaxRows(page.getRecordsPerPage());
	        queryParam.setLimit(limit);
		}
		List<T> lists = getSqlSession().selectList(statement, queryParam);
		return lists.iterator();
	}

	@Override
	public <K, V> Map<K, V> queryByPageWithMap(final String countStatement,
			final String statement, final Map<String, Object> params, final String mapKey,
			final IPage page) {
		QueryParam queryParam = new QueryParam();
		queryParam.setParams(params);
		
		Limit limit = new Limit(0);
		
		if(null == page)
			queryParam.setLimit(limit);
		else
		{
			Integer totalRecords = getSqlSession().selectOne(countStatement, queryParam);
			page.setTotalRecords(totalRecords);
			
			limit.setStart(page.getStartRowPosition());
	        if (page.getRecordsPerPage() > 0)
	        	limit.setMaxRows(page.getRecordsPerPage());
	        queryParam.setLimit(limit);
		}
        
		Map<K,V> map = getSqlSession().selectMap(statement, queryParam, mapKey);
		return map;
	}

	@Override
	public <T> List<T> queryByPageWithList(final String countStatement,
			final String statement, final Map<String, Object> params, final IPage page) {
		QueryParam queryParam = new QueryParam();
		queryParam.setParams(params);
		
		Limit limit = new Limit(0);
		
		if(null == page)
			queryParam.setLimit(limit);
		else
		{
			Integer totalRecords = getSqlSession().selectOne(countStatement, queryParam);
			page.setTotalRecords(totalRecords);
			
			limit.setStart(page.getStartRowPosition());
	        if (page.getRecordsPerPage() > 0)
	        	limit.setMaxRows(page.getRecordsPerPage());
	        queryParam.setLimit(limit);
		}
        
		List<T> lists = getSqlSession().selectList(statement, queryParam);
		return lists;
	}

	@Override
	public <T> Iterator<T> queryByPageWithIterator(final String countStatement,
			final String statement, final Map<String, Object> params, final IPage page) {
		QueryParam queryParam = new QueryParam();
		queryParam.setParams(params);
		
		Limit limit = new Limit(0);
		
		if(null == page)
			queryParam.setLimit(limit);
		else
		{
			Integer totalRecords = getSqlSession().selectOne(countStatement, queryParam);
			page.setTotalRecords(totalRecords);
			
			limit.setStart(page.getStartRowPosition());
	        if (page.getRecordsPerPage() > 0)
	        	limit.setMaxRows(page.getRecordsPerPage());
	        queryParam.setLimit(limit);
		}
		List<T> lists = getSqlSession().selectList(statement, queryParam);
		return lists.iterator();
	}

	@Override
	public <T> List<T> queryBySqlAndPageWithList(Class<?> clazz, final String sql, final IPage page) {
		return processQueryForSql(clazz,sql,page,null);
	}
	
	private <T> List<T> processQueryForSql(Class<?> clazz,String sql,IPage page,Map<String,?> params)
	{
		List<Map<String,String>> lists = new ArrayList<Map<String,String>>();
		
		QueryParam queryParam = new QueryParam();
		Map<String,Object> tempMap = new HashMap<String,Object>();
		queryParam.setParams(tempMap);
		
		if(null != params)
			queryParam.getParams().putAll(params);
		
		Limit limit = new Limit(0);
		try {
			String[] queries = setupSqlQuery(sql,params,page);
			String query = queries[0];
	        String countQuery = queries[1];
	        if (page != null && countQuery != null) {
//	            long begin = System.currentTimeMillis();
	            
	            queryParam.getParams().put("pageCount", countQuery);

				Integer totalRecords = getSqlSession().selectOne("SqlMapper.selectByMapCount", queryParam);
	           
//	            long time = System.currentTimeMillis() - begin;
	            if (totalRecords != null) {
	                page.setTotalRecords(totalRecords.intValue());
	            }
	            
	            limit.setStart(page.getStartRowPosition());
		        if (page.getRecordsPerPage() > 0)
		        	limit.setMaxRows(page.getRecordsPerPage());
		        queryParam.setLimit(limit);
	        }
	        queryParam.getParams().put("pageList", query);
//	        long begin = System.currentTimeMillis();
	        lists = getSqlSession().selectList("SqlMapper.selectPageWithList", queryParam);
//	        long time = System.currentTimeMillis() - begin;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		List<T> resultLists = new ArrayList<T>();
		for(Map<String,String> map : lists)
		{
			T t = CommonUtil.convertMapToBean(clazz, map);
			resultLists.add(t);
		}
		
		return resultLists;
	}
	
	private String[] setupSqlQuery(final String sql,  Map<String, ?> map, IPage page){
		String countQueryStr = null;
		try {
            if (page != null) {
            	countQueryStr = getSqlCountQuery(sql); 
            }
            String[] ret = { sql, countQueryStr };
            return ret;
        }
        catch (Exception e) {
            throw new CommonException(e);
        }
    }
    
    private String getSqlCountQuery(final String sql) {
    	if(StringUtils.isEmpty(sql))
    		throw new ValidateException("待计算count的sql原语不可为空！");
    	
    	int c = sql.indexOf("from");
    	if(c == -1)
    		throw new ValidateException("传入的sql异常，需要包含from关键字!");
    	
    	String queryCountString = "select count(1) " + sql.substring(c, sql.length());
        return queryCountString;
    }
}
