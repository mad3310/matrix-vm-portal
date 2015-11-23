package com.letv.portal.proxy.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.letv.common.exception.PythonException;
import com.letv.common.exception.ValidateException;
import com.letv.common.result.ApiResultObject;
import com.letv.common.session.SessionServiceImpl;
import com.letv.portal.enumeration.MclusterStatus;
import com.letv.portal.enumeration.MclusterType;
import com.letv.portal.model.ContainerModel;
import com.letv.portal.model.MclusterModel;
import com.letv.portal.proxy.IMclusterProxy;
import com.letv.portal.python.service.IBuildTaskService;
import com.letv.portal.python.service.IPythonService;
import com.letv.portal.service.IBaseService;
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
@Component("mclusterProxy")
public class MclusterProxyImpl extends BaseProxyImpl<MclusterModel> implements
		IMclusterProxy{
	
	private final static Logger logger = LoggerFactory.getLogger(MclusterProxyImpl.class);
	
	@Autowired
	private IMclusterService mclusterService;
	@Autowired
	private IBuildTaskService buildTaskService;
	@Autowired(required=false)
	private SessionServiceImpl sessionService;
	@Autowired
	private IContainerService containerService;
	@Autowired
	private IDbService dbService;
	@Autowired
	private IBuildService buildService;
	@Autowired
	private IPythonService pythonService;
	
	@Override
	public IBaseService<MclusterModel> getService() {
		return mclusterService;
	}	
	
	@Override
	public void insert(MclusterModel mclusterModel) {
		String mclusterName = mclusterModel.getMclusterName();
		mclusterModel.setAdminUser("root");
		mclusterModel.setAdminPassword(mclusterName);
		mclusterModel.setDeleted(true);
		mclusterModel.setType(MclusterType.AUTO.getValue());
		mclusterModel.setStatus(MclusterStatus.BUILDDING.getValue());
		super.insert(mclusterModel);
	}

	@Override
	public void insertAndBuild(MclusterModel mclusterModel) {
		mclusterModel.setCreateUser(sessionService.getSession().getUserId());
		this.insert(mclusterModel);
	}

	@Override
	public void deleteAndRemove(Long mclusterId) {
		MclusterModel mcluster = this.mclusterService.selectById(mclusterId);
		mcluster.setStatus(MclusterStatus.DESTROYING.getValue());
		this.mclusterService.updateBySelective(mcluster);
		this.buildTaskService.removeMcluster(mcluster);
	}

	@Override
	public void start(Long mclusterId) {
		MclusterModel mcluster = this.mclusterService.selectById(mclusterId);
		mcluster.setStatus(MclusterStatus.STARTING.getValue());
		this.mclusterService.updateBySelective(mcluster);
		this.buildTaskService.startMcluster(mcluster);
	}

	@Override
	public void stop(Long mclusterId) {
		MclusterModel mcluster = this.mclusterService.selectById(mclusterId);
		mcluster.setStatus(MclusterStatus.STOPPING.getValue());
		this.mclusterService.updateBySelective(mcluster);
		this.buildTaskService.stopMcluster(mcluster);
	}

	@Override
	public void checkStatus() {
		List<MclusterModel> list = this.mclusterService.selectByMap(null);
		for (MclusterModel mcluster : list) {
			if(MclusterStatus.BUILDDING.getValue() == mcluster.getStatus() || MclusterStatus.BUILDFAIL.getValue() == mcluster.getStatus() || MclusterStatus.DEFAULT.getValue() == mcluster.getStatus()
					|| MclusterStatus.AUDITFAIL.getValue() == mcluster.getStatus()) {
				continue;
			}
			this.buildTaskService.checkMclusterStatus(mcluster);
		}
	}
	
	@Override
	public void checkCount() {
		this.buildTaskService.checkMclusterCount();
	}

	@Override
	public void restartDb(Long mclusterId) {
		if(mclusterId == null) 
			throw new ValidateException("参数不合法");
		MclusterModel mcluster = this.selectById(mclusterId);
		if(mcluster == null)
			throw new ValidateException("参数不合法");
		ContainerModel container = this.containerService.selectValidVipContianer(mclusterId, "mclustervip",null);
		if(container == null)
			throw new ValidateException("vip节点不存在");
		ApiResultObject resultObject = this.pythonService.restartMcluster(container.getIpAddr(),mcluster.getAdminUser(),mcluster.getAdminPassword());
		String result = resultObject.getResult();
		if(StringUtils.isEmpty(result)) {
			throw new PythonException("call restart db service API error:connect out");
		}
		if(!result.contains("\"code\": 200")) {
			throw new PythonException("call restart db service API error:" + result.substring(result.indexOf("\"response\""),  result.length()));
		}
	}
}
