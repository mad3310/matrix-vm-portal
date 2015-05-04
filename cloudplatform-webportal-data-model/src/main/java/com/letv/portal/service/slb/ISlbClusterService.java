package com.letv.portal.service.slb;

import com.letv.portal.model.slb.SlbCluster;
import com.letv.portal.service.IBaseService;

public interface ISlbClusterService extends IBaseService<SlbCluster> {

	Boolean isExistByName(String string);

}
