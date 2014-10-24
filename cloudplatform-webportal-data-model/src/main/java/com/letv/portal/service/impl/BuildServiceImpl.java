package com.letv.portal.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.letv.common.dao.IBaseDao;
import com.letv.portal.dao.IBuildDao;
import com.letv.portal.enumeration.BuildStatus;
import com.letv.portal.model.BuildModel;
import com.letv.portal.service.IBuildService;

@Service("buildService")
public class BuildServiceImpl extends BaseServiceImpl<BuildModel> implements
		IBuildService{
	
	private final static Logger logger = LoggerFactory.getLogger(BuildServiceImpl.class);
	
	@Resource
	private IBuildDao buildDao;

	public BuildServiceImpl() {
		super(BuildModel.class);
	}

	@Override
	public IBaseDao<BuildModel> getDao() {
		return this.buildDao;
	}

	@Override
	public List<BuildModel> selectByMclusterId(Long mclusterId) {
		return this.buildDao.selectByMclusterId(mclusterId);
	}

	@Override
	public void initStatus(Long mclusterId) {
		
		String[] stepMsgs = new String[]{"创建Mcluster节点","检查Mcluster节点状态","初始化Zookeeper节点","初始化mcluster管理用户名密码","提交mcluster集群信息","初始化集群","同步节点1信息","提交节点1信息","同步节点2信息","提交节点2信息 ","启动集群","检查集群状态","启动gbalancer:8888","启动gbalancer:3306","固资系统推送","zabbix系统监控推送"};
		
		for (int i = 0; i < stepMsgs.length; i++) {
			BuildModel buildModel = new BuildModel();
			buildModel.setStep(i+1);
			buildModel.setStepMsg(stepMsgs[i]);
			buildModel.setStatus(BuildStatus.WAITTING.getValue());
			buildModel.setMclusterId(mclusterId);
			this.buildDao.insert(buildModel);
		}
		
	}

	@Override
	public void updateByStatus(BuildModel buildModel) {
		this.buildDao.updateByStatus(buildModel);
		
	}

	@Override
	public void deleteByMclusterId(Long mclusterId) {
		this.buildDao.deleteByMclusterId(mclusterId);
	}

	@Override
	public List<BuildModel> selectByDbId(Long dbId) {
		return this.buildDao.selectByDbId(dbId);
	}

	@Override
	public void updateByStep(BuildModel nextBuild) {
		this.buildDao.updateByStep(nextBuild);
		
	}


}
