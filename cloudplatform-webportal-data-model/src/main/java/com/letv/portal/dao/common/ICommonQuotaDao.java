package com.letv.portal.dao.common;

import com.letv.common.dao.IBaseDao;
import com.letv.portal.model.common.CommonQuota;

import java.util.List;
import java.util.Map;

/**
 * Created by zhouxianguang on 2015/11/10.
 */
public interface ICommonQuotaDao extends IBaseDao<CommonQuota> {
    List<CommonQuota> selectByMapForSync(Map<String, Object> map);
}
