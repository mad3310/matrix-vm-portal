package com.letv.portal.dao.gce;

import com.letv.common.dao.IBaseDao;
import com.letv.portal.model.gce.GceServerExt;

public interface IGceServerExtDao extends IBaseDao<GceServerExt> {

	GceServerExt selectByGceServerId(Long gceServerId);
}
