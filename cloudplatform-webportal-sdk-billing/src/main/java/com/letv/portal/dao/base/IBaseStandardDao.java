package com.letv.portal.dao.base;

import java.util.List;
import java.util.Map;

import com.letv.common.dao.IBaseDao;
import com.letv.portal.model.base.BaseStandard;

public interface IBaseStandardDao extends IBaseDao<BaseStandard> {
	List<Map<String, Object>> selectStadardInfoWithFatherId(Map<String, Object> map);
	List<BaseStandard> selectBaseStandardWithPrice(Long baseElementId);
}
