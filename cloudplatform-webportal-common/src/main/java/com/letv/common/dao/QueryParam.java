package com.letv.common.dao;

import java.util.Map;

public class QueryParam {
	
	private Limit limit;
	
	private Map<String,Object> params;
	
	public Map<String,Object> getParams() {
		return params;
	}

	public void setParams(Map<String,Object> params) {
		this.params = params;
	}

	public Limit getLimit() {
		return limit;
	}

	public void setLimit(Limit limit) {
		this.limit = limit;
	}
	
	

}
