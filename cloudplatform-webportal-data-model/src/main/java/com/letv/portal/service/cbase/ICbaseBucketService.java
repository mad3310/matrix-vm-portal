package com.letv.portal.service.cbase;

import java.util.Map;

import com.letv.common.paging.impl.Page;
import com.letv.portal.model.cbase.CbaseBucketModel;
import com.letv.portal.service.IBaseService;

public interface ICbaseBucketService extends IBaseService<CbaseBucketModel> {

	Map<String, Object> save(CbaseBucketModel cbaseBucketModel);

	/**
	 * Methods Name: findPagebyParams <br>
	 * Description: 根据查询条件查出分页数据<br>
	 * 
	 * @author name: liyunhui
	 * @param params
	 * @param page
	 * @return
	 */
	public Page findPagebyParams(Map<String, Object> params, Page page);

}
