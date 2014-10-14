package com.letv.portal.proxy.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.letv.portal.enumeration.MclusterStatus;
import com.letv.portal.model.MclusterModel;
import com.letv.portal.proxy.IMclusterProxy;
import com.letv.portal.python.service.IBuildTaskService;
import com.letv.portal.service.IBaseService;
import com.letv.portal.service.IMclusterService;

/**Program Name: MclusterServiceImpl <br>
 * Description:  <br>
 * @author name: liuhao1 <br>
 * Written Date: 2014年8月14日 <br>
 * Modified By: <br>
 * Modified Date: <br>
 */
@Component
public class MclusterProxyImpl extends BaseProxyImpl<MclusterModel> implements
		IMclusterProxy{
	
	private final static Logger logger = LoggerFactory.getLogger(MclusterProxyImpl.class);
	
	@Autowired
	private IMclusterService mclusterService;
	@Autowired
	private IBuildTaskService buildTaskService;
	@Override
	public IBaseService<MclusterModel> getService() {
		return mclusterService;
	}	
	
	@Override
	public void insert(MclusterModel mclusterModel) {
		String mclusterName = mclusterModel.getMclusterName();
		mclusterModel.setAdminUser(mclusterName);
		mclusterModel.setAdminPassword(mclusterName);
		mclusterModel.setDeleted(true);
		mclusterModel.setStatus(MclusterStatus.BUILDDING.getValue());
		super.insert(mclusterModel);
		
		buildTaskService.buildMcluster(mclusterModel);
	}

	@Override
	public Boolean isExistByName(String mclusterName) {
		List<MclusterModel> mclusters = this.mclusterService.selectByName(mclusterName);
		return mclusters.size() == 0?true:false;
	}
	
}
