package com.letv.portal.service.cloudvm.impl;

import com.letv.common.dao.IBaseDao;
import com.letv.common.exception.ValidateException;
import com.letv.portal.dao.cloudvm.ICloudvmRcCountDao;
import com.letv.portal.model.cloudvm.CloudvmRcCount;
import com.letv.portal.model.cloudvm.CloudvmRcCountType;
import com.letv.portal.service.cloudvm.ICloudvmRcCountService;
import com.letv.portal.service.impl.BaseServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhouxianguang on 2015/10/29.
 */
@Service("cloudvmRcCountService")
public class CloudvmRcCountServiceImpl extends BaseServiceImpl<CloudvmRcCount> implements ICloudvmRcCountService {

    @Resource
    private ICloudvmRcCountDao cloudvmRcCountDao;

    public CloudvmRcCountServiceImpl() {
        super(CloudvmRcCount.class);
    }

    @Override
    public IBaseDao<CloudvmRcCount> getDao() {
        return cloudvmRcCountDao;
    }

    @Override
    public CloudvmRcCount selectByType(long tenantId, String region, CloudvmRcCountType type) {
        if (StringUtils.isEmpty(region)) {
            throw new ValidateException("地域不能为空");
        }
        if (type == null) {
            throw new ValidateException("资源类型不能为空");
        }
        Map<String, Object> paras = new HashMap<String, Object>();
        paras.put("tenantId", tenantId);
        paras.put("region", region);
        paras.put("type", type);
        List<CloudvmRcCount> resultList = cloudvmRcCountDao.selectByMap(paras);
        if (!resultList.isEmpty()) {
            return resultList.get(0);
        }
        return null;
    }
}
