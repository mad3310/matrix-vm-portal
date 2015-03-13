package com.letv.portal.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.letv.common.dao.IBaseDao;
import com.letv.common.dao.QueryParam;
import com.letv.common.paging.impl.Page;
import com.letv.portal.dao.IMclusterDao;
import com.letv.portal.model.MclusterModel;
import com.letv.portal.service.IBackupService;
import com.letv.portal.service.IBuildService;
import com.letv.portal.service.IContainerService;
import com.letv.portal.service.IDbService;
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
	
	@Autowired
	private IMclusterDao mclusterDao;
	
	@Autowired
	private IContainerService containerService;
	@Autowired
	private IBuildService buildService;
	@Autowired
	private IDbService dbService;
	@Autowired
	private IBackupService backupService;
	
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
	
	@Override
	public void delete(MclusterModel mcluster) {
		this.containerService.deleteByMclusterId(mcluster.getId());
		this.buildService.deleteByMclusterId(mcluster.getId());
		this.dbService.deleteByMclusterId(mcluster.getId());
		this.backupService.deleteByMclusterId(mcluster.getId());
		this.mclusterDao.delete(mcluster);
	}

	@Override
	public List<MclusterModel> select4Run() {
		return this.mclusterDao.select4Run();
	}

	@Override
	public boolean isExistByName(String mclusterName) {
		List<MclusterModel> mclusters = this.selectByName(mclusterName);
		return mclusters.size() == 0?true:false;
	}

	@Override
	public List<MclusterModel> selectValidMclustersByMap(Map<String, Object> params) {
		return this.mclusterDao.selectValidMclustersByMap(params);
	}

	@Override
	public List<MclusterModel> selectValidMclusters(int stage) {
		if(stage == 0)
			return this.mclusterDao.selectValidMclusters();
		Page page = new Page(stage,25);
		if(stage == 4)
			return this.mclusterDao.selectLastValidMclustersByPage(page);
		return this.mclusterDao.selectValidMclustersByPage(page);
	}

	@Override
	public List<MclusterModel> selectValidMclusters() {
		return this.mclusterDao.selectValidMclusters();
	}
}
