package com.letv.portal.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.letv.common.dao.IBaseDao;
import com.letv.common.dao.QueryParam;
import com.letv.common.paging.impl.Page;
import com.letv.portal.dao.IMclusterDao;
import com.letv.portal.model.MclusterModel;
import com.letv.portal.service.IMclusterService;

/**Program Name: MclusterServiceImpl <br>
 * Description:  <br>
 * @author name: liuhao1 <br>
 * Written Date: 2014年8月14日 <br>
 * Modified By: <br>
 * Modified Date: <br>
 */
@Service("mclusterService")
public class MclusterServiceImpl extends BaseServiceImpl<MclusterModel> implements
		IMclusterService{
	
	@Resource
	private IMclusterDao mclusterDao;
	
	public MclusterServiceImpl() {
		super(MclusterModel.class);
	}

	@Override
	public IBaseDao<MclusterModel> getDao() {
		return this.mclusterDao;
	}

	@Override
	public Page findPagebyParams(Map<String, Object> params, Page page) {
		QueryParam param = new QueryParam(params,page);
		page.setData(this.mclusterDao.selectPageByMap(param));
		page.setTotalRecords(this.mclusterDao.selectByMapCount(params));
		return page;
		
	}


	@Override
	public void audit(MclusterModel mclusterModel) {
		this.mclusterDao.updateBySelective(mclusterModel);
	}

	@Override
	public List<MclusterModel> selectByName(String mclusterName) {
		return this.mclusterDao.selectByName(mclusterName);
	}

}
