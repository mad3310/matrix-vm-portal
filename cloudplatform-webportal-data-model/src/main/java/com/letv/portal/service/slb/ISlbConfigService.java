package com.letv.portal.service.slb;

import java.util.List;

import com.letv.portal.model.slb.SlbConfig;
import com.letv.portal.service.IBaseService;

public interface ISlbConfigService extends IBaseService<SlbConfig> {

	List<SlbConfig> selectBySlbServerId(Long id);
}
