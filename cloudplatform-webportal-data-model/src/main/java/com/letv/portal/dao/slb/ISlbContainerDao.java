package com.letv.portal.dao.slb;

import java.util.List;

import com.letv.common.dao.IBaseDao;
import com.letv.portal.model.gce.GceContainer;
import com.letv.portal.model.slb.SlbContainer;

public interface ISlbContainerDao extends IBaseDao<SlbContainer> {
	
	List<SlbContainer> selectBySlbClusterId(Long clusterId);

}
