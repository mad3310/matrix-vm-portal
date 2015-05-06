package com.letv.portal.service.slb.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.letv.common.dao.IBaseDao;
import com.letv.common.paging.impl.Page;
import com.letv.portal.dao.slb.ISlbBackendServerDao;
import com.letv.portal.enumeration.SlbBackEndType;
import com.letv.portal.model.gce.GceCluster;
import com.letv.portal.model.gce.GceContainer;
import com.letv.portal.model.gce.GceServer;
import com.letv.portal.model.slb.SlbBackendServer;
import com.letv.portal.model.slb.SlbConfig;
import com.letv.portal.model.slb.SlbServer;
import com.letv.portal.service.gce.IGceClusterService;
import com.letv.portal.service.gce.IGceContainerService;
import com.letv.portal.service.gce.IGceServerService;
import com.letv.portal.service.impl.BaseServiceImpl;
import com.letv.portal.service.slb.ISlbBackendServerService;
import com.letv.portal.service.slb.ISlbServerService;

@Service("slbBackendServerService")
public class SlbBackendServerServiceImpl extends BaseServiceImpl<SlbBackendServer> implements ISlbBackendServerService{
	
	private final static Logger logger = LoggerFactory.getLogger(SlbBackendServerServiceImpl.class);
	
	@Resource
	private ISlbBackendServerDao slbBackendServerDao;
	@Autowired
	private IGceServerService gceServerService;
	@Autowired
	private IGceClusterService gceClusterService;
	@Autowired
	private IGceContainerService gceContainerService;
	
	private SlbBackEndType type;

	public SlbBackendServerServiceImpl() {
		super(SlbBackendServer.class);
	}

	@Override
	public IBaseDao<SlbBackendServer> getDao() {
		return this.slbBackendServerDao;
	}

	@Override
	public List<SlbBackendServer> selectBySlbServerId(Long slbServerId) {
		return this.slbBackendServerDao.selectBySlbServerId(slbServerId);
	}

	@Override
	public List<SlbBackendServer> selectBySlbConfigId(Long slbConfigId) {
		return this.slbBackendServerDao.selectBySlbConfigId(slbConfigId);
	}

	@Override
	public <K, V> Page selectPageByParams(Page page, Map<K, V> params) {
		page = super.selectPageByParams(page, params);
		List<SlbBackendServer> data = (List<SlbBackendServer>) page.getData();
		
			for (SlbBackendServer slbBackendServer : data) {
				if(slbBackendServer.getType().equals(type.WEB)){
					if(slbBackendServer.getGceId() != null){
						GceServer gceServer = gceServerService.selectGceAndProxyById(slbBackendServer.getGceId());
						Long gceClusterId = gceServer.getGceServerProxy()==null?gceServer.getGceClusterId():gceServer.getGceServerProxy().getGceClusterId();
						List<GceContainer> gceContainers = this.gceContainerService.selectByGceClusterId(gceClusterId);
						slbBackendServer.setGceContainers(gceContainers);
						page.setData(data);
					}
				}
			}
		return page;
	}
}
