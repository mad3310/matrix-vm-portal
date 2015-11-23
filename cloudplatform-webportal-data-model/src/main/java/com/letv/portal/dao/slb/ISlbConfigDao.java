package com.letv.portal.dao.slb;

import java.util.List;

import com.letv.common.dao.IBaseDao;
import com.letv.portal.model.slb.SlbConfig;

public interface ISlbConfigDao extends IBaseDao<SlbConfig> {

	List<SlbConfig> selectBySlbServerId(Long id);

}
